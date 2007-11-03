package net.incongru.util.mail;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;

/**
 * This simple tool allows sending emails whose content is taken from velocity templates.
 *
 * TODO : support i18n in a way or another. (looking up locale(lang/country) then just lang is easyMU
 * * question is what default we should use? Locale.getDefault or a configured one?
 * * should we get default resource or be strict and fail ?
 *
 * TODO : manage encoding
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class VelocityMailer extends AbstractMailer {
    private static final String ENCODING = "ISO-8859-15";
    private final VelocityEngine velocityEngine;

    public VelocityMailer(VelocityEngine velocityEngine, MailLocalizer localizer, MailConfig config) {
        super(localizer, config);
        this.velocityEngine = velocityEngine;
    }

    public void mail(MailBean mail) {
        final Context ctx = new VelocityContext(mail.getValues());
        final VelocityTemplateEngine engine = new VelocityTemplateEngine(velocityEngine, ctx);
        renderAndSendMail(engine, mail);
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
