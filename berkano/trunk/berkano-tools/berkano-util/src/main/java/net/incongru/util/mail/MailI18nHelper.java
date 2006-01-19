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
public interface MailI18nHelper {
    public static final MailI18nHelper NULL = new NullI18NHelper();

    public String getSubject(String subjectKey, Locale locale);

    public final static class NullI18NHelper implements MailI18nHelper {
        public String getSubject(String subjectKey, Locale locale) {
            return subjectKey;
        }
    }
}
