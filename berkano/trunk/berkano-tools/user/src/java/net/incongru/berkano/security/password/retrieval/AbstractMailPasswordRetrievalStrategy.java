package net.incongru.berkano.security.password.retrieval;

import net.incongru.berkano.mail.Mailer;
import net.incongru.berkano.security.password.PasswordRetrievalStrategy;
import net.incongru.berkano.user.User;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract strategy that sends a mail to the user's
 * registered email address.
 *
 * TODO : set subject (i18n)
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public abstract class AbstractMailPasswordRetrievalStrategy implements PasswordRetrievalStrategy {
    private Mailer mailer;

    protected AbstractMailPasswordRetrievalStrategy(Mailer mailer) {
        this.mailer = mailer;
    }

    public void retrievePassword(User u) throws Exception {
        Map map = prepare(u);
        mailer.mail(u.getEmail(), u.getFullName(), getMailSubject(), getTemplateName(), map);
    }

    /**
     * Use this method to change any setting of the user if needed and
     * add items in the map passed to the template engine.
     */
    protected Map prepare(User u) {
        Map map = new HashMap();
        map.put("user", u);
        return map;
    }

    protected abstract String getMailSubject();

    protected abstract String getTemplateName();

    public String getRequiredField() {
        return "email";
    }
}
