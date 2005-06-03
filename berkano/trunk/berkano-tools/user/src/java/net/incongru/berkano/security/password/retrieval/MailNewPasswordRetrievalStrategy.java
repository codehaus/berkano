package net.incongru.swaf.security.password.retrieval;

import net.incongru.swaf.mail.Mailer;
import net.incongru.swaf.security.password.PasswordGenerator;
import net.incongru.swaf.user.User;
import net.incongru.swaf.user.UserDAO;

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
        return "swaf/lostpw/mail/new_password";
    }
}
