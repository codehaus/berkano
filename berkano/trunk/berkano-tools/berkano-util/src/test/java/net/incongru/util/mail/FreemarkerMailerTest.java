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
        freemarkerCfg = new Configuration();
        final StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate("test.ftl", "${user}, this is the test.ftl template");
        loader.putTemplate("test-html.ftl", "${user}, this is the test-html.ftl template");
        loader.putTemplate("test-text.ftl", "${user}, this is the test-text.ftl template");
        loader.putTemplate("test-text_es.ftl", "${user}, eso es la test-text.ftl templata");
        freemarkerCfg.setTemplateLoader(loader);
    }

    public void testCanProcessTemplates() throws Exception {
        final FakeMailer mailer = new FakeMailer(MailI18nHelper.NULL, (MailConfig) mailCfg.proxy(), freemarkerCfg);
        mailer.mail("test@test.com", "Unit Test", "Testing", "test.ftl", Collections.singletonMap("user", "Unit Test"), Locale.PRC);
        assertNotNull(mailer.email);
        TestSupport.assertFieldEquals(mailer.email, "html", "Unit Test, this is the test-html.ftl template");
        TestSupport.assertFieldEquals(mailer.email, "text", "Unit Test, this is the test-text.ftl template");
    }

    public void testFreemarkerIsASmartAssAndCanFindLocalizedTemplates() throws Exception {
        final FakeMailer mailer = new FakeMailer(MailI18nHelper.NULL, (MailConfig) mailCfg.proxy(), freemarkerCfg);
        mailer.mail("test@test.com", "Unit Test", "Testing", "test.ftl", Collections.singletonMap("user", "Unit Test"), new Locale("es", "AR"));
        assertNotNull(mailer.email);
        TestSupport.assertFieldEquals(mailer.email, "html", "Unit Test, this is the test-html.ftl template");
        TestSupport.assertFieldEquals(mailer.email, "text", "Unit Test, eso es la test-text.ftl templata");
    }

    private static class FakeMailer extends FreemarkerMailer {
        private Email email;

        public FakeMailer(MailI18nHelper i18nHelper, MailConfig config, Configuration freemarkerCfg) {
            super(i18nHelper, config, freemarkerCfg);
        }

        protected void sendMail(Email email, String toEmail, String toName, String subject) {
            this.email = email;
        }
    }
}
