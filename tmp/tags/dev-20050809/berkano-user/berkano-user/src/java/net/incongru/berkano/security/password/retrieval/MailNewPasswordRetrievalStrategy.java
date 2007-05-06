package net.incongru.berkano.security.password.retrieval;

import net.incongru.berkano.security.password.PasswordGenerator;
import net.incongru.berkano.user.User;
import net.incongru.berkano.user.UserDAO;
import net.incongru.util.mail.Mailer;

import java.util.Map;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class MailNewPasswordRetrievalStrategy extends AbstractMailPasswordRetrievalStrategy {
    private UserDAO userDAO;
    private PasswordGenerator passwordGenerator;

    public MailNewPasswordRetrievalStrategy(UserDAO userDAO, PasswordGenerator passwordGenerator, Mailer mailer) {
        super(mailer);
        this.userDAO = userDAO;
        this.passwordGenerator = passwordGenerator;
    }

    protected Map prepare(User u) {
        Map m = super.prepare(u);
        String newPassword = passwordGenerator.generate();
        m.put("newPassword", newPassword);
        userDAO.changePassword(u.getUserId(), newPassword);
        return m;
    }

    protected String getMailSubject() {
        return "New password";
    }

    protected String getTemplateName() {
        return "berkano/lostpw/mail/new_password";
    }
}
