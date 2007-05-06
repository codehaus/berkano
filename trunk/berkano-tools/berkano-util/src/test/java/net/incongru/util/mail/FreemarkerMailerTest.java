package net.incongru.util.mail;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import net.incongru.util.TestSupport;
import org.apache.commons.mail.Email;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.Collections;
import java.util.Locale;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class FreemarkerMailerTest extends MockObjectTestCase {
    private Mock mailCfg;
    private Configuration freemarkerCfg;

    protected void setUp() throws Exception {
        super.setUp();
        mailCfg = mock(MailConfig.class);
        mailCfg.expects(once()).method("getMailHost").withNoArguments().will(returnValue("localhost"));
        mailCfg.expects(once()).method("getFromName").withNoArguments().will(returnValue("test"));
        mailCfg.expects(once()).method("getFromEmail").withNoArguments().will(returnValue("test@localhost"));

        freemarkerCfg = new Configuration();
        final StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate("test.ftl", "${user}, this is the test.ftl template");
        loader.putTemplate("test-html.ftl", "${user}, this is the test-html.ftl template");
        loader.putTemplate("test-text.ftl", "${user}, this is the test-text.ftl template");
        loader.putTemplate("test-text_es.ftl", "${user}, eso es la test-text.ftl templata");
        freemarkerCfg.setTemplateLoader(loader);
    }

    public void testCanProcessTemplates() throws Exception {
        final FakeMailer mailer = new FakeMailer(new DummyMailLocalizer(Locale.PRC), (MailConfig) mailCfg.proxy(), freemarkerCfg);
        mailer.mail(new MailBean("test@test.com", "Unit Test", "Testing", "test.ftl", Collections.singletonMap("user", "Unit Test")));
        assertNotNull(mailer.email);
        TestSupport.assertFieldEquals(mailer.email, "html", "Unit Test, this is the test-html.ftl template");
        TestSupport.assertFieldEquals(mailer.email, "text", "Unit Test, this is the test-text.ftl template");
    }

    public void testFreemarkerIsASmartAssAndCanFindLocalizedTemplates() throws Exception {
        final FakeMailer mailer = new FakeMailer(new DummyMailLocalizer(new Locale("es", "AR")), (MailConfig) mailCfg.proxy(), freemarkerCfg);
        mailer.mail(new MailBean("test@test.com", "Unit Test", "Testing", "test.ftl", Collections.singletonMap("user", "Unit Test")));
        assertNotNull(mailer.email);
        TestSupport.assertFieldEquals(mailer.email, "html", "Unit Test, this is the test-html.ftl template");
        TestSupport.assertFieldEquals(mailer.email, "text", "Unit Test, eso es la test-text.ftl templata");
    }

    private static final class FakeMailer extends FreemarkerMailer {
        private Email email;

        public FakeMailer(MailLocalizer localizer, MailConfig config, Configuration freemarkerCfg) {
            super(localizer, config, freemarkerCfg);
        }

        protected void sendMail(Email email) {
            this.email = email;
        }
    }

    private static final class DummyMailLocalizer implements MailLocalizer {
        private final Locale locale;

        public DummyMailLocalizer(Locale locale) {
            this.locale = locale;
        }

        public Locale resolveLocale() {
            return locale;
        }

        public String getSubject(String subjectKey, Locale locale) {
            return subjectKey;
        }
    }
}
