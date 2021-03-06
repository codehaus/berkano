package net.incongru.util.hibernate;

import net.incongru.util.hibernate.testmodel.BasicVegetable;
import net.incongru.util.hibernate.testmodel.Sandwich;
import net.incongru.util.hibernate.testmodel.Sauce;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class EnumTypeTest extends AbstractHibernateTest {
    protected String[] getHbmFiles() throws HibernateException {
        return new String[]{"net/incongru/util/hibernate/testmodel/dummy-enum.hbm.xml"};
    }

    public void testBasic() throws Exception {
        Session s = sessionFactory.openSession();

        try {
            Sandwich sandwich = new Sandwich();
            sandwich.setName("basic");
            sandwich.setCheeseAmount(1);
            // a simple enum
            sandwich.setSauce(Sauce.mayonnaise);
            // an enum implementing an interface
            sandwich.setVegetable(BasicVegetable.salad);

            s.save(sandwich);
            s.flush();
        } finally {
            s.close();
        }

        s = sessionFactory.openSession();
        try {
            final Query query = s.createQuery("from pouet in class Sandwich");
            List resList = query.list();
            assertEquals(1, resList.size());
            Sandwich res = (Sandwich) resList.get(0);
            assertEquals("basic", res.getName());
            assertEquals(1, res.getCheeseAmount());
            assertEquals(Sauce.mayonnaise, res.getSauce());
            assertEquals(BasicVegetable.salad, res.getVegetable());
        } finally {
            s.close();
        }

        // test on jdbc
        s = sessionFactory.openSession();
        try {
            Connection conn = s.connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sandwich");
            assertTrue(rs.next());
            assertEquals("basic", rs.getString("name"));
            String sauceStr = rs.getString("sauce");
            assertEquals("net.incongru.util.hibernate.testmodel.Sauce/mayonnaise", sauceStr);
            String vegStr = rs.getString("vegetable");
            assertEquals("net.incongru.util.hibernate.testmodel.BasicVegetable/salad", vegStr);
            assertFalse(rs.next());
        } finally {
            s.close();
        }
    }

    public void testNullsCanBeStoredAndRetrieved() throws SQLException {
        Session s = sessionFactory.openSession();
        try {
            Sandwich sandwich = new Sandwich();
            sandwich.setName("basic");
            sandwich.setSauce(null);
            s.save(sandwich);
            s.flush();
        } finally {
            s.close();
        }

        Session s2 = sessionFactory.openSession();
        try {
            final Query q = s2.createQuery("from p in class Sandwich");
            List resList = q.list();
            assertEquals(1, resList.size());
            Sandwich res = (Sandwich) resList.get(0);
            assertEquals("basic", res.getName());
            assertEquals(null, res.getSauce());
        } finally {
            s2.close();
        }

        // test on jdbc
        Session s3 = sessionFactory.openSession();
        try {
            Connection conn = s2.connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sandwich");
            assertTrue(rs.next());
            assertEquals("basic", rs.getString("name"));
            String sauceStr = rs.getString("sauce");
            assertEquals(null, sauceStr);
            String vegStr = rs.getString("vegetable");
            assertEquals(null, vegStr);
            assertFalse(rs.next());
        } finally {
            s3.close();
        }
    }
}
