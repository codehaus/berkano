package net.incongru.util.mail;

import java.util.Locale;

/**
 * This interface allows Mailer implementations to translate mail
 * subjects and contents.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public interface MailLocalizer {
    public static final MailLocalizer NULL = new NullI18NHelper();

    Locale resolveLocale();

    String getSubject(String subjectKey, Locale locale);

    public final static class NullI18NHelper implements MailLocalizer {
        public Locale resolveLocale() {
            return Locale.getDefault();
        }

        public String getSubject(String subjectKey, Locale locale) {
            return subjectKey;
        }
    }
}
