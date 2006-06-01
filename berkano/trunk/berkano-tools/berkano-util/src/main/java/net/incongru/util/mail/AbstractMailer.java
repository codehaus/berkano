package net.incongru.util.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import java.util.Locale;

/**
 * An abstract Mailer based on commons-email. Implementations should
 * implement the TemplateRenderer interface.
 *
 * As a convention, the template should be available in both plain text
 * and html format using the <code>-text</code> and <code>-html</code>
 * suffixes to the given template name. If the given template name
 * ends with an extension, the suffixes will be inserted before the
 * extension. If the given template name doesn't have an extension,
 * <code>.vm</code> will be appended by default, but if that template
 * doesn't exist, the same name without extension will also be tried.
 * If all this would fail, the given template name would also be tried
 * without any suffix, and used as a plain text template.
 *
 * TODO : handle i18n of templates
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public abstract class AbstractMailer implements Mailer {
    private static final String PLAINTEXT_SUFFIX = "-text";
    private static final String HTML_SUFFIX = "-html";
    protected final MailLocalizer localizer;
    protected final MailConfig config;

    protected AbstractMailer(MailLocalizer localizer, MailConfig config) {
        this.localizer = localizer;
        this.config = config;
    }

    protected void renderAndSendMail(TemplateEngine engine, MailBean mail) {
        final Locale locale = getLocale(mail);
        final String fromEmail = mail.getFromEmail() != null ? mail.getFromEmail() : config.getFromEmail();
        final String fromName = mail.getFromName() != null ? mail.getFromName() : config.getFromName();

        final String translatedSubject = localizer.getSubject(mail.getSubject(), locale);

        final String plainTextTemplateName = getPlainTextTemplateName(engine, mail.getTemplateName());
        final String htmlTemplateName = getHtmlTemplateName(engine, mail.getTemplateName());

        final String plainText = engine.renderTemplate(plainTextTemplateName);

        try {
            final Email email;
            if (htmlTemplateName != null) {
                email = new HtmlEmail();
                final String html = engine.renderTemplate(htmlTemplateName);
                ((HtmlEmail) email).setHtmlMsg(html);
                ((HtmlEmail) email).setTextMsg(plainText);
            } else {
                email = new SimpleEmail();
                email.setMsg(plainText);
            }
            email.setHostName(config.getMailHost());
            email.setFrom(fromEmail, fromName);
            email.addTo(mail.getToEmail(), mail.getToName());
            email.setSubject(translatedSubject);
            if (mail.getReplyToEmail() != null) {
                email.addReplyTo(mail.getReplyToEmail());
            }
            sendMail(email);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    protected Locale getLocale(MailBean mail) {
        return mail.getLocale() != null ? mail.getLocale() : localizer.resolveLocale();
    }

    protected void sendMail(Email email) throws EmailException {
        email.send();
    }

    protected String getPlainTextTemplateName(TemplateEngine engine, String templateName) {
        return getTemplateName(engine, templateName, PLAINTEXT_SUFFIX, true);
    }

    protected String getHtmlTemplateName(TemplateEngine engine, String templateName) {
        return getTemplateName(engine, templateName, HTML_SUFFIX, false);
    }

    protected String getTemplateName(TemplateEngine engine, String templateName, String suffix, boolean useOriginalNameIfTemplateDoesNotExistWithSuffix) {
        int extIdx = templateName.lastIndexOf('.');
        String basename, extension;
        if (extIdx < 0) {
            // TODO : should not have .vm has a default extension
            extension = ".vm";
            basename = templateName;
        } else {
            extension = templateName.substring(extIdx);
            basename = templateName.substring(0, extIdx);
        }
        String plainTextTemplateName = basename + suffix + extension;
        if (engine.templateExists(plainTextTemplateName)) {
            return plainTextTemplateName;
        }
        if (extIdx < 0) {
            plainTextTemplateName = basename + suffix;
            if (engine.templateExists(plainTextTemplateName)) {
                return plainTextTemplateName;
            }
        }
        if (useOriginalNameIfTemplateDoesNotExistWithSuffix) {
            plainTextTemplateName = basename + extension;
            if (engine.templateExists(plainTextTemplateName)) {
                return plainTextTemplateName;
            }
            return templateName;
        } else {
            return null;
        }
    }

    protected interface TemplateEngine {
        String renderTemplate(String templateName);

        boolean templateExists(String templateName);
    }
}
