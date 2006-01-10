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
    private final UserDAO userDAO;
    private final PasswordGenerator passwordGenerator;

    public MailNewPasswordRetrievalStrategy(UserDAO userDAO, PasswordGenerator passwordGenerator, Mailer mailer) {
        super(mailer);
        this.userDAO = userDAO;
        this.passwordGenerator = passwordGenerator;
    }

    protected void prepareMailContext(User u, Map context) {
        super.prepareMailContext(u, context);
        String newPassword = passwordGenerator.generate();
        context.put("newPassword", newPassword);
        userDAO.changePassword(u.getUserId(), newPassword);
    }

    protected String getMailSubject() {
        return "New password";
    }

    protected String getTemplateName() {
        return "berkano/lostpw/mail/new_password";
    }
}
