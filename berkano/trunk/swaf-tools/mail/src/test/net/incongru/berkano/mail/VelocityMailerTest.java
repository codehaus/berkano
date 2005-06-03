package net.incongru.berkano.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.velocity.app.VelocityEngine;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class VelocityMailerTest extends MockObjectTestCase {
    private Mock velocity;
    private Mock mailConfig;
    private VelocityMailer mailer;

    protected void setUp() throws Exception {
        velocity = mock(VelocityEngine.class, "velocity");
        mailConfig = mock(MailConfig.class);
        mailer = new VelocityMailer((VelocityEngine) velocity.proxy(), (MailConfig) mailConfig.proxy());
    }

    // tests for template merging
    public void testSendingHtmlMail() throws Exception {
        FakeVelocityMailer mailer = configureMailer();
        Map ctx = new HashMap();
        ctx.put("name", "Greg");
        ctx.put("password", "secret");
        mailer.mail("to@to.to", null, null, "net/incongru/berkano/mail/test", ctx);

        assertTrue(mailer.email instanceof HtmlEmail);
        HtmlEmail htmlEmail = (HtmlEmail) mailer.email;

        assertFieldEquals(htmlEmail, "text",
                "Hi Greg,\n\n" +
                        "Your password is secret.\n\n" +
                        "Cheers,\n\n" +
                        "Your beloved BOFH.\n");
        assertFieldEquals(htmlEmail, "html", "<html>\n" +
                        "<body>\n" +
                        "<p>Hi Greg,</p>\n" +
                        "<p>Your password is secret.</p>\n" +
                        "<p>Cheers,</p>\n" +
                        "<p>Your beloved BOFH.</p>\n" +
                        "</body>\n" +
                        "</html>\n");
    }

    public void testShouldSendPlainTextMailIfNoHtmlTemplateAvailable() throws Exception {
        FakeVelocityMailer mailer = configureMailer();
        Map ctx = new HashMap();
        ctx.put("foo", "message");
        ctx.put("bar", "plain text");
        mailer.mail("to@to.to", null, null, "net/incongru/berkano/mail/textonly", ctx);

        assertTrue(mailer.email instanceof SimpleEmail);
        SimpleEmail email = (SimpleEmail) mailer.email;

        assertFieldEquals(email, Email.class, "content", "This message only exists in plain text.\n");
    }

    private static void assertFieldEquals(Object o, String fieldName, Object expectedValue) throws NoSuchFieldException, IllegalAccessException {
        assertFieldEquals(o, o.getClass(), fieldName, expectedValue);
    }

    private static void assertFieldEquals(Object o, Class clazz, String fieldName, Object expectedValue) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        assertEquals(expectedValue, f.get(o));
    }

    private FakeVelocityMailer configureMailer() throws Exception {
        Properties p = new Properties();
        p.setProperty("mail.host", "localhost");
        MailConfig config = new PropertiesMailConfig(p);
        Properties velocityConfig = new Properties();
        velocityConfig.setProperty("resource.loader", "classpath");
        velocityConfig.setProperty("class.resource.loader.description", "Velocity Classpath Resource Loader");
        velocityConfig.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ConstructableVelocityEngine v = new ConstructableVelocityEngine(velocityConfig);
        FakeVelocityMailer mailer = new FakeVelocityMailer(v, config);
        return mailer;
    }

    private static class FakeVelocityMailer extends VelocityMailer {
        private Email email;

        public FakeVelocityMailer(VelocityEngine velocityEngine, MailConfig config) {
            super(velocityEngine, config);
        }

        protected void sendMail(Email email, String toEmail, String toName, String subject) throws Exception {
            this.email = email;
        }
    }


    // tests for template naming
    public void testGetPlainTextTemplateNameWithoutExtensionAndSuffixNorExtensionExistShouldUseOriginalName() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(false));

        String res = mailer.getPlainTextTemplateName("tmpl");
        assertEquals("tmpl", res);
        velocity.verify();
    }

    public void testGetPlainTextTemplateNameWithoutExtensionAndExtensionExistShouldUseExtension() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(true));

        String res = mailer.getPlainTextTemplateName("tmpl");
        assertEquals("tmpl.vm", res);
        velocity.verify();
    }

    public void testGetPlainTextTemplateNameWithoutExtensionAndSuffixesExistWithoutExtension() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text")).will(returnValue(true));
        String res = mailer.getPlainTextTemplateName("tmpl");
        assertEquals("tmpl-text", res);
        velocity.verify();
    }

    public void testGetPlainTextTemplateNameWithoutExtensionAndSuffixesExist() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        String res = mailer.getPlainTextTemplateName("tmpl");
        assertEquals("tmpl-text.vm", res);
        velocity.verify();
    }

    public void testGetPlainTextTemplateNameWithExtensionAndSuffixesDontExistShouldStillUseOriginalName() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(false));

        String res = mailer.getPlainTextTemplateName("tmpl.vm");
        assertEquals("tmpl.vm", res);
        velocity.verify();
    }

    public void testGetPlainTextTemplateNameWithExtensionAndExtensionExistShouldUseExtension() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(true));

        String res = mailer.getPlainTextTemplateName("tmpl.vm");
        assertEquals("tmpl.vm", res);
        velocity.verify();
    }

    public void testGetPlainTextTemplateNameWithExtensionAndSuffixesExist() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        String res = mailer.getPlainTextTemplateName("tmpl.vm");
        assertEquals("tmpl-text.vm", res);
        velocity.verify();
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndSuffixNorExtensionExistShouldUseOriginalName() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName("tmpl");
        assertEquals(null, res);
        velocity.verify();
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndExtensionExistShouldUseExtension() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName("tmpl");
        assertEquals(null, res);
        velocity.verify();
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndSuffixesExistWithoutExtension() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html")).will(returnValue(true));
        String res = mailer.getHtmlTemplateName("tmpl");
        assertEquals("tmpl-html", res);
        velocity.verify();
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndSuffixesExist() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        String res = mailer.getHtmlTemplateName("tmpl");
        assertEquals("tmpl-html.vm", res);
        velocity.verify();
    }

    public void testGetHtmlTemplateNameWithExtensionAndSuffixesDontExistShouldStillUseOriginalName() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName("tmpl.vm");
        assertEquals(null, res);
        velocity.verify();
    }

    public void testGetHtmlTemplateNameWithExtensionAndExtensionExistShouldUseExtension() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName("tmpl.vm");
        assertEquals(null, res);
        velocity.verify();
    }

    public void testGetHtmlTemplateNameWithExtensionAndSuffixesExist() {
        velocity.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        String res = mailer.getHtmlTemplateName("tmpl.vm");
        assertEquals("tmpl-html.vm", res);
        velocity.verify();
    }

}
