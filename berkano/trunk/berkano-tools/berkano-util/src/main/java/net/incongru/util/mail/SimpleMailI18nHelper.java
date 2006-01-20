package net.incongru.util.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class SimpleMailI18nHelper implements MailI18nHelper {
    private final Map<Locale, ResourceBundle> bundles = new HashMap<Locale, ResourceBundle>();
    private final String bundleName;

    public SimpleMailI18nHelper(String bundleName) {
        this.bundleName = bundleName;
    }

    public String getSubject(String subjectKey, Locale locale) {
        try {
            return getBundle(locale).getString(subjectKey);
        } catch (MissingResourceException e) {
            return subjectKey;
        }
    }

    private ResourceBundle getBundle(Locale locale) {
        ResourceBundle b = bundles.get(locale);
        if (b == null) {
            b = ResourceBundle.getBundle(bundleName, locale, this.getClass().getClassLoader());
            bundles.put(locale, b);
        }
        return b;
    }
}
