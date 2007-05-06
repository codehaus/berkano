package net.incongru.util.mail;

/**
 * A generic config object for mailing. Implementations should provide their
 * loading mechanisms (properties, ...).
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface MailConfig {
    String getMailHost();

    String getFromName();

    String getFromEmail();
}
