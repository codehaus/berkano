package net.incongru.util.mail;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 *
 * TODO : manage encoding
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class FreemarkerMailer extends AbstractMailer {
    private static final String ENCODING = "ISO-8859-15";
    private final Configuration freemarkerCfg;

    public FreemarkerMailer(MailLocalizer localizer, MailConfig config, Configuration freemarkerCfg) {
        super(localizer, config);
        this.freemarkerCfg = freemarkerCfg;
    }

    public void mail(String toEmail, String toName, String subject, String templateName, Map values) {
        final SimpleHash model = new SimpleHash(values);
        final Locale locale = localizer.resolveLocale();
        final FreemarkerTemplateEngine engine = new FreemarkerTemplateEngine(freemarkerCfg, locale, model);
        renderAndSendMail(engine, toEmail, toName, subject, templateName, locale);
    }

    private static final class FreemarkerTemplateEngine implements TemplateEngine {
        private final Configuration freemarkerCfg;
        private final Locale locale;
        private final SimpleHash model;

        public FreemarkerTemplateEngine(Configuration freemarkerCfg, Locale locale, SimpleHash model) {
            this.freemarkerCfg = freemarkerCfg;
            this.locale = locale;
            this.model = model;
        }

        public String renderTemplate(String templateName) {
            try {
                StringWriter buffer = new StringWriter();
                final Template template = getTemplate(templateName, locale, ENCODING);
                if (template == null) {
                    throw new IllegalArgumentException("Template " + templateName + " not found");
                }
                template.process(model, buffer);
                return buffer.getBuffer().toString();
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public boolean templateExists(String templateName) {
            return getTemplate(templateName, locale, ENCODING) != null;
        }

        // yuck !
        private Template getTemplate(String templateName, Locale locale, String encoding) {
            try {
                return freemarkerCfg.getTemplate(templateName, locale, encoding);
            } catch (FileNotFoundException e) {
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
