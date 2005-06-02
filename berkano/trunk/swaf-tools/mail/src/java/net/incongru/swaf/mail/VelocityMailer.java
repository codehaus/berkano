package net.incongru.swaf.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;
import java.util.Map;

/**
 * This simple tool allows sending emails whose content is taken from velocity templates.
 *
 * TODO : support i18n in a way or another. (looking up locale(lang/country) then just lang is easyMU
 * * question is what default we should use? Locale.getDefault or a configured one?
 * * should we get default resource or be strict and fail ?
 * TODO : exception handling
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class VelocityMailer implements Mailer {
    private static final String PLAINTEXT_SUFFIX = "-text";
    private static final String HTML_SUFFIX = "-html";

    private VelocityEngine velocityEngine;
    private MailConfig config;

    public VelocityMailer(VelocityEngine velocityEngine, MailConfig config) {
        this.velocityEngine = velocityEngine;
        this.config = config;
    }

    /**
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
     * @param templateName
     * @param values
     *
     * TODO : manage encoding
     */
    public void mail(String toEmail, String toName, String subject, String templateName, Map values) throws Exception {
        Context ctx = new VelocityContext(values);

        String plainTextTemplateName = getPlainTextTemplateName(templateName);
        StringWriter plainText = new StringWriter();
        velocityEngine.mergeTemplate(plainTextTemplateName, "ISO-8859-15", ctx, plainText);

        String htmlTemplateName = getHtmlTemplateName(templateName);
        if (htmlTemplateName != null) {
            StringWriter html = new StringWriter();
            velocityEngine.mergeTemplate(htmlTemplateName, "ISO-8859-15", ctx, html);
            HtmlEmail email = new HtmlEmail();
            email.setHtmlMsg(html.getBuffer().toString());
            email.setTextMsg(plainText.getBuffer().toString());
            sendMail(email, toEmail, toName, subject);
        } else {
            SimpleEmail email = new SimpleEmail();
            email.setMsg(plainText.getBuffer().toString());
            sendMail(email, toEmail, toName, subject);
        }
    }

    protected void sendMail(Email email, String toEmail, String toName, String subject) throws Exception {
        email.setHostName(config.getMailHost());
        email.setFrom(config.getFromEmail(), config.getFromName());
        email.addTo(toEmail, toName);
        email.setSubject(subject);
        email.send();
    }

    String getPlainTextTemplateName(String templateName) {
        return getTemplateName(templateName, PLAINTEXT_SUFFIX, true);
    }

    String getHtmlTemplateName(String templateName) {
        return getTemplateName(templateName, HTML_SUFFIX, false);
    }

    private String getTemplateName(String templateName, String suffix, boolean useOriginalNameIfTemplateDoesNotExistWithSuffix) {
        int extIdx = templateName.lastIndexOf('.');
        String basename, extension;
        if (extIdx < 0) {
            extension = ".vm";
            basename = templateName;
        } else {
            extension = templateName.substring(extIdx);
            basename = templateName.substring(0, extIdx);
        }
        String plainTextTemplateName = basename + suffix + extension;
        if (velocityEngine.templateExists(plainTextTemplateName)) {
            return plainTextTemplateName;
        }
        if (extIdx < 0) {
            plainTextTemplateName = basename + suffix;
            if (velocityEngine.templateExists(plainTextTemplateName)) {
                return plainTextTemplateName;
            }
        }
        if (useOriginalNameIfTemplateDoesNotExistWithSuffix) {
            plainTextTemplateName = basename + extension;
            if (velocityEngine.templateExists(plainTextTemplateName)) {
                return plainTextTemplateName;
            }
            return templateName;
        } else {
            return null;
        }
    }

}
