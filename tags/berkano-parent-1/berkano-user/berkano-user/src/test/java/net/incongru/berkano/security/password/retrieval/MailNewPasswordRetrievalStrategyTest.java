package net.incongru.berkano.security.password.retrieval;

import net.incongru.berkano.security.password.PasswordGenerator;
import net.incongru.berkano.security.password.PasswordRetrievalStrategy;
import net.incongru.berkano.user.UserDAO;
import net.incongru.berkano.user.UserImpl;
import net.incongru.util.mail.Mailer;
import net.incongru.util.mail.MailBean;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class MailNewPasswordRetrievalStrategyTest extends MockObjectTestCase {
    public void testGeneratesMailsAndStoresNewPassword() throws Exception {
        final Mock userDao = mock(UserDAO.class);
        final Mock passGen = mock(PasswordGenerator.class);
        final Mock mail = mock(Mailer.class);

        UserImpl user = new UserImpl();
        user.setUserId(new Long(37));
        user.setEmail("mymail@user.com");
        user.setFullName("My Full Self");
        user.setUserName("myself");

        Map expectedCtx = new HashMap();
        expectedCtx.put("newPassword", "new pass");
        expectedCtx.put("user", user);

        passGen.expects(once()).method("generate").withNoArguments().will(returnValue("new pass"));
        userDao.expects(once()).method("changePassword").with(eq(new Long(37)), eq("new pass")).isVoid();
        // new Constraint[]{eq("mymail@user.com"), eq("My Full Self"), eq("NewPassword"), eq("berkano/lostpw/mail/new_password"), eq(expectedCtx)}
        MailBean mailBean = new MailBean("mymail@user.com", "My Full Self", "NewPassword", "berkano/lostpw/mail/new_password", expectedCtx);
        mail.expects(once()).method("mail").with(eq(mailBean)).isVoid();

        final PasswordRetrievalStrategy pwdRetrievalStrategy = new MailNewPasswordRetrievalStrategy((UserDAO) userDao.proxy(), (PasswordGenerator) passGen.proxy(), (Mailer) mail.proxy());
        pwdRetrievalStrategy.retrievePassword(user);
    }
}
