package net.incongru.swaf.security.password.retrieval;

import net.incongru.swaf.mail.Mailer;

/**
 * A simple PasswordRetrievalStrategy which will send an email
 * containing the current password of the user. This obviously
 * <strong>only works if we are able to retrieve the password
 * in clear text from the user data</strong>. (ie, the
 * {@link net.incongru.swaf.security.password.matching.CleanPasswordMatchingStrategy}
 * is used)
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class MailCurrentPasswordRetrievalStrategy extends AbstractMailPasswordRetrievalStrategy {
    public MailCurrentPasswordRetrievalStrategy(Mailer mailer) {
        super(mailer);
    }

    protected String getMailSubject() {
        return "Lost password";
    }

    protected String getTemplateName() {
        return "swaf/lostpw/mail/current_password";
    }
}
