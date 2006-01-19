package net.incongru.util.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import javax.mail.MessagingException;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Actually tests the AbstractMailer class, but since I'm a lazy ass, I called this
 * test class MailerTest to avoid that any smart-ass build system skips because of
 * its name.
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class MailerTest extends MockObjectTestCase {
    private Mock engine;
    private AbstractMailer.TemplateEngine engineProxy;
    private Mock mailConfig;
    private AbstractMailer mailer;

    protected void setUp() throws Exception {
        engine = mock(AbstractMailer.TemplateEngine.class, "velocity");
        engineProxy = (AbstractMailer.TemplateEngine) engine.proxy();
        mailConfig = mock(MailConfig.class);

        mailer = new AbstractMailer(MailI18nHelper.NULL, (MailConfig) mailConfig.proxy()) {
            public void mail(String toEmail, String toName, String subject, String templateName, Map values, Locale locale) {
                renderAndSendMail(engineProxy, toEmail, toName, subject, templateName, locale);
            }
        };
    }

    public void testSubjectIsTranslated() throws MessagingException {
        Mock i18nHelper = mock(MailI18nHelper.class);
        FakeMailer fakeMailer = new FakeMailer((MailI18nHelper) i18nHelper.proxy(), (MailConfig) mailConfig.proxy());
        mailConfig.stubs();
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-text.vm")).will(returnValue("blabla"));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-html.vm")).will(returnValue("blabla"));

        i18nHelper.expects(once()).method("getSubject").with(eq("subject"), eq(Locale.FRANCE)).will(returnValue("*subject*"));

        fakeMailer.mail("to@kiala.com", "to", "subject", "tmpl", Collections.EMPTY_MAP, Locale.FRANCE);
        assertNotNull(fakeMailer.emailToSend);
        assertNotNull(fakeMailer.subjectToSend);
        assertEquals("*subject*", fakeMailer.subjectToSend);
    }

    // tests for template naming
    public void testGetPlainTextTemplateNameWithoutExtensionAndSuffixNorExtensionExistShouldUseOriginalName() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl-text")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(false));

        String res = mailer.getPlainTextTemplateName(engineProxy, "tmpl");
        assertEquals("tmpl", res);
    }

    public void testGetPlainTextTemplateNameWithoutExtensionAndExtensionExistShouldUseExtension() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl-text")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(true));

        String res = mailer.getPlainTextTemplateName(engineProxy, "tmpl");
        assertEquals("tmpl.vm", res);
    }

    public void testGetPlainTextTemplateNameWithoutExtensionAndSuffixesExistWithoutExtension() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl-text")).will(returnValue(true));
        String res = mailer.getPlainTextTemplateName(engineProxy, "tmpl");
        assertEquals("tmpl-text", res);
    }

    public void testGetPlainTextTemplateNameWithoutExtensionAndSuffixesExist() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        String res = mailer.getPlainTextTemplateName(engineProxy, "tmpl");
        assertEquals("tmpl-text.vm", res);
    }

    public void testGetPlainTextTemplateNameWithExtensionAndSuffixesDontExistShouldStillUseOriginalName() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(false));

        String res = mailer.getPlainTextTemplateName(engineProxy, "tmpl.vm");
        assertEquals("tmpl.vm", res);
    }

    public void testGetPlainTextTemplateNameWithExtensionAndExtensionExistShouldUseExtension() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl.vm")).will(returnValue(true));

        String res = mailer.getPlainTextTemplateName(engineProxy, "tmpl.vm");
        assertEquals("tmpl.vm", res);
    }

    public void testGetPlainTextTemplateNameWithExtensionAndSuffixesExist() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        String res = mailer.getPlainTextTemplateName(engineProxy, "tmpl.vm");
        assertEquals("tmpl-text.vm", res);
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndSuffixNorExtensionExistShouldUseOriginalName() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl-html")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName(engineProxy, "tmpl");
        assertEquals(null, res);
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndExtensionExistShouldUseExtension() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl-html")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName(engineProxy, "tmpl");
        assertEquals(null, res);
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndSuffixesExistWithoutExtension() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("tmpl-html")).will(returnValue(true));
        String res = mailer.getHtmlTemplateName(engineProxy, "tmpl");
        assertEquals("tmpl-html", res);
    }

    public void testGetHtmlTemplateNameWithoutExtensionAndSuffixesExist() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        String res = mailer.getHtmlTemplateName(engineProxy, "tmpl");
        assertEquals("tmpl-html.vm", res);
    }

    public void testGetHtmlTemplateNameWithExtensionAndSuffixesDontExistShouldStillUseOriginalName() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName(engineProxy, "tmpl.vm");
        assertEquals(null, res);
    }

    public void testGetHtmlTemplateNameWithExtensionAndExtensionExistShouldUseExtension() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(false));

        String res = mailer.getHtmlTemplateName(engineProxy, "tmpl.vm");
        assertEquals(null, res);
    }

    public void testGetHtmlTemplateNameWithExtensionAndSuffixesExist() {
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        String res = mailer.getHtmlTemplateName(engineProxy, "tmpl.vm");
        assertEquals("tmpl-html.vm", res);
    }

    private class FakeMailer extends AbstractMailer {
        private Email emailToSend;
        private String subjectToSend;

        public FakeMailer(MailI18nHelper i18nHelper, MailConfig mailConfig) {
            super(i18nHelper, mailConfig);
        }

        public void mail(String toEmail, String toName, String subject, String templateName, Map values, Locale locale) {
            renderAndSendMail(engineProxy, toEmail, toName, subject, templateName, locale);
        }

        protected void sendMail(Email email, String toEmail, String toName, String subject) throws EmailException {
            this.emailToSend = email;
            this.subjectToSend = subject;
        }
    }
}
