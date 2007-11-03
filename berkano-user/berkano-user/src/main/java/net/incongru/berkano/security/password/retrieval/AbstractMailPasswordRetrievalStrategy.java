package net.incongru.berkano.security.password.retrieval;

import net.incongru.berkano.security.password.PasswordRetrievalStrategy;
import net.incongru.berkano.user.User;
import net.incongru.util.mail.Mailer;
import net.incongru.util.mail.MailBean;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract strategy that sends a mail to the user's
 * registered email address.
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public abstract class AbstractMailPasswordRetrievalStrategy implements PasswordRetrievalStrategy {
    private final Mailer mailer;

    protected AbstractMailPasswordRetrievalStrategy(Mailer mailer) {
        this.mailer = mailer;
    }

    public void retrievePassword(User u) throws Exception {
        final Map map = new HashMap();
        final MailBean mail = new MailBean(u.getEmail(), u.getFullName(), getMailSubject(), getTemplateName(), map);
        prepareMailContext(u, mail);
        mailer.mail(mail);
    }

    /**
     * Use this method to change any setting of the user if needed and
     * add items in the map passed to the template engine.
     */
    protected void prepareMailContext(User u, MailBean mail) {
        mail.getValues().put("user", u);
    }

    protected abstract String getMailSubject();

    protected abstract String getTemplateName();

    public String getRequiredField() {
        return "email";
    }
}
