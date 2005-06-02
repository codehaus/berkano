package net.incongru.beantag;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * todo : use a LocaleProvider like displaytag does?
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class WebWorkI18nTableWriter extends OgnlTableWriter {
    public String getLabel(Property p) throws PropertyDecoratorException {
        String label = super.getLabel(p);
        return translate(label);
    }

    public void writeHeader(JspWriter out, String label, String htmlTableClass, String htmlTableStyle, String htmlTableId) throws IOException {
        super.writeHeader(out, translate(label), htmlTableClass, htmlTableStyle, htmlTableId);
    }

    protected String translate(String key) {
        return WebWorkI18nHelper.translate(key);
    }

    protected Locale getLocale() {
        return WebWorkI18nHelper.getLocale();
    }
}
