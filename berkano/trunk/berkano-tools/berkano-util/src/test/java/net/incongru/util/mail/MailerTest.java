package net.incongru.util.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Locale;

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

        mailer = new AbstractMailer(MailLocalizer.NULL, (MailConfig) mailConfig.proxy()) {
            public void mail(MailBean mail) {
                renderAndSendMail(engineProxy, mail);
            }
        };
    }

    public void testFromShouldComeFromConfigIfNotSpecified() {
        mailConfig.expects(once()).method("getMailHost").withNoArguments().will(returnValue("localhost"));
        mailConfig.expects(once()).method("getFromName").withNoArguments().will(returnValue("configfrom"));
        mailConfig.expects(once()).method("getFromEmail").withNoArguments().will(returnValue("configfrom@localhost"));

        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-text.vm")).will(returnValue("blabla"));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-html.vm")).will(returnValue("blabla"));

        FakeMailer fakeMailer = new FakeMailer(MailLocalizer.NULL, (MailConfig) mailConfig.proxy());
        fakeMailer.mail(new MailBean("foo@bar.com", "test", "test", "tmpl", Collections.EMPTY_MAP, "reply@bar.com"));
        Email emailToSend = fakeMailer.emailToSend;
        assertEquals("configfrom", emailToSend.getFromAddress().getPersonal());
        assertEquals("configfrom@localhost", emailToSend.getFromAddress().getAddress());
    }

    public void testFromShouldNotComeFromConfigIfSpecified() {
        mailConfig.expects(once()).method("getMailHost").withNoArguments().will(returnValue("localhost"));

        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-text.vm")).will(returnValue("blabla"));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-html.vm")).will(returnValue("blabla"));

        FakeMailer fakeMailer = new FakeMailer(MailLocalizer.NULL, (MailConfig) mailConfig.proxy());
        fakeMailer.mail(new MailBean("foo@bar.com", "test", "test", "tmpl", Collections.EMPTY_MAP, "reply@bar.com", "customfrom@localhost", "customfrom"));
        Email emailToSend = fakeMailer.emailToSend;
        assertEquals("customfrom", emailToSend.getFromAddress().getPersonal());
        assertEquals("customfrom@localhost", emailToSend.getFromAddress().getAddress());
    }

    public void testSubjectIsTranslated() throws MessagingException {
        Mock localizer = mock(MailLocalizer.class);
        FakeMailer fakeMailer = new FakeMailer((MailLocalizer) localizer.proxy(), (MailConfig) mailConfig.proxy());

        mailConfig.expects(once()).method("getMailHost").withNoArguments().will(returnValue("localhost"));
        mailConfig.expects(once()).method("getFromName").withNoArguments().will(returnValue("test"));
        mailConfig.expects(once()).method("getFromEmail").withNoArguments().will(returnValue("test@localhost"));

        engine.expects(once()).method("templateExists").with(eq("tmpl-text.vm")).will(returnValue(true));
        engine.expects(once()).method("templateExists").with(eq("tmpl-html.vm")).will(returnValue(true));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-text.vm")).will(returnValue("blabla"));
        engine.expects(once()).method("renderTemplate").with(eq("tmpl-html.vm")).will(returnValue("blabla"));

        localizer.expects(once()).method("resolveLocale").withNoArguments().will(returnValue(Locale.FRANCE));
        localizer.expects(once()).method("getSubject").with(eq("subject"), eq(Locale.FRANCE)).will(returnValue("*subject*"));

        fakeMailer.mail(new MailBean("to@kiala.com", "to", "subject", "tmpl", Collections.EMPTY_MAP));
        assertNotNull(fakeMailer.emailToSend);
        assertNotNull(fakeMailer.emailToSend.getSubject());
        assertEquals("*subject*", fakeMailer.emailToSend.getSubject());
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

    public void testReplyToShouldDefaultToFromIfNotSpecified() throws MessagingException, EmailException {
        engine.expects(once()).method("templateExists").with(eq("test-tmpl-html.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("test-tmpl-html")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("test-tmpl-text.vm")).will(returnValue(true));
        engine.expects(once()).method("renderTemplate").with(eq("test-tmpl-text.vm")).will(returnValue("plop"));

        mailConfig.expects(once()).method("getMailHost").withNoArguments().will(returnValue("localhost"));
        mailConfig.expects(once()).method("getFromName").withNoArguments().will(returnValue("test"));
        mailConfig.expects(once()).method("getFromEmail").withNoArguments().will(returnValue("test@localhost"));

        Mock localizer = mock(MailLocalizer.class);
        localizer.stubs();

        FakeMailer fakeMailer = new FakeMailer((MailLocalizer) localizer.proxy(), (MailConfig) mailConfig.proxy());

        fakeMailer.mail(new MailBean("to@toto.too", "toto", "yo!", "test-tmpl", Collections.EMPTY_MAP));
        ((FakeMailer) fakeMailer).emailToSend.buildMimeMessage();
        MimeMessage mail = ((FakeMailer) fakeMailer).emailToSend.getMimeMessage();
        assertEquals(1, mail.getReplyTo().length);
        assertEquals("test@localhost", ((InternetAddress) mail.getReplyTo()[0]).getAddress());
        assertEquals("test", ((InternetAddress) mail.getReplyTo()[0]).getPersonal());
    }

    public void testReplyToShouldBeAddedIfSpecified() throws MessagingException, EmailException {
        engine.expects(once()).method("templateExists").with(eq("test-tmpl-html.vm")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("test-tmpl-html")).will(returnValue(false));
        engine.expects(once()).method("templateExists").with(eq("test-tmpl-text.vm")).will(returnValue(true));
        engine.expects(once()).method("renderTemplate").with(eq("test-tmpl-text.vm")).will(returnValue("plop"));

        mailConfig.expects(once()).method("getMailHost").withNoArguments().will(returnValue("localhost"));
        mailConfig.expects(once()).method("getFromName").withNoArguments().will(returnValue("test"));
        mailConfig.expects(once()).method("getFromEmail").withNoArguments().will(returnValue("test@localhost"));

        Mock localizer = mock(MailLocalizer.class);
        localizer.stubs();

        FakeMailer fakeMailer = new FakeMailer((MailLocalizer) localizer.proxy(), (MailConfig) mailConfig.proxy());
        fakeMailer.mail(new MailBean("to@toto.too", "toto", "yo!", "test-tmpl", Collections.EMPTY_MAP, "reply@toto.too"));
        ((FakeMailer) fakeMailer).emailToSend.buildMimeMessage();
        MimeMessage mail = ((FakeMailer) fakeMailer).emailToSend.getMimeMessage();
        assertEquals(1, mail.getReplyTo().length);
        assertEquals("reply@toto.too", ((InternetAddress) mail.getReplyTo()[0]).getAddress());
        assertEquals("reply@toto.too", ((InternetAddress) mail.getReplyTo()[0]).getPersonal());
    }

    private class FakeMailer extends AbstractMailer {
        private Email emailToSend;

        public FakeMailer(MailLocalizer localizer, MailConfig mailConfig) {
            super(localizer, mailConfig);
        }

        public void mail(MailBean mail) {
            renderAndSendMail(engineProxy, mail);
        }

        protected void sendMail(Email email) throws EmailException {
            this.emailToSend = email;
        }
    }
}
