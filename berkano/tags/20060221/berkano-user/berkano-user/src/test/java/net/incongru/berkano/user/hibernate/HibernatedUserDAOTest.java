package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.security.password.PasswordMatchingStrategy;
import net.incongru.berkano.user.DuplicateUserException;
import net.incongru.berkano.user.UserImpl;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.SimpleExpression;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class HibernatedUserDAOTest extends MockObjectTestCase {
    private Mock session, passwordStrategy;

    protected void setUp() throws Exception {
        super.setUp();
        session = mock(Session.class);
        passwordStrategy = mock(PasswordMatchingStrategy.class);
    }

    public void testNewUserChecksNameUniqueness() {
        Mock criteria = mock(Criteria.class);

        final UserImpl expectedUser = new UserImpl();
        expectedUser.setUserName("user");
        expectedUser.setPassword("encrypted-pass");
        expectedUser.setEmail("boo@boo.com");
        expectedUser.setFullName("Some user");

        session.expects(once()).method("createCriteria").with(eq(UserImpl.class)).will(returnValue(criteria.proxy()));
        criteria.expects(once()).method("add").with(isA(SimpleExpression.class));
        criteria.expects(once()).method("uniqueResult").withNoArguments().will(returnValue(null));
        passwordStrategy.expects(once()).method("encode").with(eq("pass")).will(returnValue("encrypted-pass"));
        session.expects(once()).method("save").with(isA(UserImpl.class));

        final HibernatedUserDAO dao = new HibernatedUserDAO((Session) session.proxy(), (PasswordMatchingStrategy) passwordStrategy.proxy());
        dao.newUser("user", "pass", "boo@boo.com", "Some user");
    }

    public void testNewUserFailsIfUserExists() {
        final UserImpl expectedUser = new UserImpl();
        expectedUser.setUserName("user");
        expectedUser.setPassword("encrypted-pass");
        expectedUser.setEmail("boo@boo.com");
        expectedUser.setFullName("Some user");

        Mock criteria = mock(Criteria.class);
        session.expects(once()).method("createCriteria").with(eq(UserImpl.class)).will(returnValue(criteria.proxy()));
        criteria.expects(once()).method("add").with(isA(SimpleExpression.class));
        criteria.expects(once()).method("uniqueResult").withNoArguments().will(returnValue(expectedUser));

        final HibernatedUserDAO dao = new HibernatedUserDAO((Session) session.proxy(), (PasswordMatchingStrategy) passwordStrategy.proxy());
        try {
            dao.newUser("user", "pass2", "boo2@boo.com", "Some user 2");
            fail("Should have failed");
        } catch (DuplicateUserException e) {
            assertEquals("user", e.getUserName());
        }
    }
}
