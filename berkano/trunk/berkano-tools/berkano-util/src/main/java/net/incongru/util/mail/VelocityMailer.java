package net.incongru.util.mail;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * This simple tool allows sending emails whose content is taken from velocity templates.
 *
 * TODO : support i18n in a way or another. (looking up locale(lang/country) then just lang is easyMU
 * * question is what default we should use? Locale.getDefault or a configured one?
 * * should we get default resource or be strict and fail ?
 *
 * TODO : manage encoding
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class VelocityMailer extends AbstractMailer {
    private static final String ENCODING = "ISO-8859-15";
    private final VelocityEngine velocityEngine;

    public VelocityMailer(VelocityEngine velocityEngine, MailI18nHelper i18nHelper, MailConfig config) {
        super(i18nHelper, config);
        this.velocityEngine = velocityEngine;
    }

    public void mail(String toEmail, String toName, String subject, String templateName, Map values, Locale locale) {
        final Context ctx = new VelocityContext(values);
        final VelocityTemplateEngine engine = new VelocityTemplateEngine(velocityEngine, ctx);

        renderAndSendMail(engine, toEmail, toName, subject, templateName, locale);
    }

    private static final class VelocityTemplateEngine implements TemplateEngine {
        private final VelocityEngine velocityEngine;
        private final Context ctx;

        public VelocityTemplateEngine(VelocityEngine velocityEngine, Context ctx) {
            this.velocityEngine = velocityEngine;
            this.ctx = ctx;
        }

        public boolean templateExists(String templateName) {
            return velocityEngine.templateExists(templateName);
        }

        public String renderTemplate(String templateName) {
            try {
                StringWriter buffer = new StringWriter();
                velocityEngine.mergeTemplate(templateName, ENCODING, ctx, buffer);
                return buffer.getBuffer().toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
