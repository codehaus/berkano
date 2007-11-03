package net.incongru.beantag;

import java.util.Locale;

/**
 * Extends this decorator and you get support for webwork's i18n support.
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public abstract class WebWorkI18nPropertyDecorator extends PropertyDecorator {

    protected String translate(String key) {
        return WebWorkI18nHelper.translate(key);
    }

    protected Locale getLocale() {
        return WebWorkI18nHelper.getLocale();
    }
}
