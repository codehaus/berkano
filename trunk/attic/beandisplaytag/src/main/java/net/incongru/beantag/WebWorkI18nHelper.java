package net.incongru.beantag;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.TextProvider;
import com.opensymphony.xwork.LocaleProvider;

import java.util.Iterator;
import java.util.Locale;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
class WebWorkI18nHelper {
    static String translate(String key) {
        String result = null;
        OgnlValueStack vs = ActionContext.getContext().getValueStack();
        Iterator it = vs.getRoot().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof TextProvider) {
                TextProvider tp = (TextProvider) o;
                result = tp.getText(key);
                break;
            }
        }
        return result != null ? result : key;
    }

    static Locale getLocale() {
        OgnlValueStack vs = ActionContext.getContext().getValueStack();
        Iterator it = vs.getRoot().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof LocaleProvider) {
                LocaleProvider lp = (LocaleProvider) o;
                return lp.getLocale();
            }
        }
        return null;
    }
}
