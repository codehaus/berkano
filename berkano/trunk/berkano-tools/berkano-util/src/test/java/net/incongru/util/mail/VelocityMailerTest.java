package net.incongru.util.mail;

import junit.framework.TestCase;
import net.incongru.util.TestSupport;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.velocity.app.VelocityEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class VelocityMailerTest extends TestCase {

    // tests for template merging
    public void testSendingHtmlMail() throws Exception {
        FakeMailer mailer = configureMailer();
        Map ctx = new HashMap();
        ctx.put("name", "Greg");
        ctx.put("password", "secret");
        mailer.mail("to@to.to", null, null, "net/incongru/util/mail/test", ctx, null);

        assertTrue(mailer.email instanceof HtmlEmail);
        HtmlEmail htmlEmail = (HtmlEmail) mailer.email;

        TestSupport.assertFieldEquals(htmlEmail, "text",
                "Hi Greg,\n\n" +
                        "Your password is secret.\n\n" +
                        "Cheers,\n\n" +
                        "Your beloved BOFH.\n");
        TestSupport.assertFieldEquals(htmlEmail, "html", "<html>\n" +
                "<body>\n" +
                "<p>Hi Greg,</p>\n" +
                "<p>Your password is secret.</p>\n" +
                "<p>Cheers,</p>\n" +
                "<p>Your beloved BOFH.</p>\n" +
                "</body>\n" +
                "</html>\n");
    }

    public void testShouldSendPlainTextMailIfNoHtmlTemplateAvailable() throws Exception {
        FakeMailer mailer = configureMailer();
        Map ctx = new HashMap();
        ctx.put("foo", "message");
        ctx.put("bar", "plain text");
        mailer.mail("to@to.to", null, null, "net/incongru/util/mail/textonly", ctx, null);

        assertTrue(mailer.email instanceof SimpleEmail);
        SimpleEmail email = (SimpleEmail) mailer.email;

        TestSupport.assertFieldEquals(email, Email.class, "content", "This message only exists in plain text.\n");
    }

    private FakeMailer configureMailer() throws Exception {
        Properties p = new Properties();
        p.setProperty("mail.host", "localhost");
        MailConfig config = new PropertiesMailConfig(p);
        Properties velocityConfig = new Properties();
        velocityConfig.setProperty("resource.loader", "classpath");
        velocityConfig.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
        velocityConfig.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ConstructableVelocityEngine v = new ConstructableVelocityEngine(velocityConfig);
        FakeMailer mailer = new FakeMailer(v, config);
        return mailer;
    }

    private static class FakeMailer extends VelocityMailer {
        private Email email;

        public FakeMailer(VelocityEngine velocity, MailConfig config) {
            super(velocity, MailI18nHelper.NULL, config);
        }

        protected void sendMail(Email email, String toEmail, String toName, String subject) {
            this.email = email;
        }
    }
}
