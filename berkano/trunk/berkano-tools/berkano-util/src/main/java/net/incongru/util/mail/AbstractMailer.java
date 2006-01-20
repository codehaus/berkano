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
    private final MailConfig config;
    private final MailI18nHelper i18nHelper;

    protected AbstractMailer(MailI18nHelper i18nHelper, MailConfig config) {
        this.i18nHelper = i18nHelper;
        this.config = config;
    }

    protected void renderAndSendMail(TemplateEngine engine, String toEmail, String toName, String subject, String templateName, Locale locale) {
        final String translatedSubject = i18nHelper.getSubject(subject, locale);

        final String plainTextTemplateName = getPlainTextTemplateName(engine, templateName);
        final String htmlTemplateName = getHtmlTemplateName(engine, templateName);

        final String plainText = engine.renderTemplate(plainTextTemplateName);

        try {
            if (htmlTemplateName != null) {
                HtmlEmail email = new HtmlEmail();
                final String html = engine.renderTemplate(htmlTemplateName);
                email.setHtmlMsg(html);
                email.setTextMsg(plainText);
                sendMail(email, toEmail, toName, translatedSubject);
            } else {
                SimpleEmail email = new SimpleEmail();
                email.setMsg(plainText);
                sendMail(email, toEmail, toName, translatedSubject);
            }
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sendMail(Email email, String toEmail, String toName, String subject) throws EmailException {
        email.setHostName(config.getMailHost());
        email.setFrom(config.getFromEmail(), config.getFromName());
        email.addTo(toEmail, toName);
        email.setSubject(subject);
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
