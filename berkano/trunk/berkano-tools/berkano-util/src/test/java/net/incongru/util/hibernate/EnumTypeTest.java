package net.incongru.util.hibernate;

import net.incongru.util.hibernate.stuff.BasicVegetable;
import net.incongru.util.hibernate.stuff.Sandwich;
import net.incongru.util.hibernate.stuff.Sauce;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class EnumTypeTest extends HibernateTestCase {
    protected String[] getXmlFiles() throws HibernateException {
        return new String[]{"net/incongru/util/hibernate/dummy-enum.hbm.xml"};
    }

    public void testBasic() throws Exception {
        Session s = sessionFactory.openSession();

        Sandwich sandwich = new Sandwich();
        sandwich.setName("basic");
        sandwich.setCheeseAmount(1);
        // a simple enum
        sandwich.setSauce(Sauce.mayonnaise);
        // an enum implementing an interface
        sandwich.setVegetable(BasicVegetable.salad);

        s.save(sandwich);
        s.close();

        s = sessionFactory.openSession();
        final Query query = s.createQuery("from pouet in class Sandwich");
        List resList = query.list();
        assertEquals(1, resList.size());
        Sandwich res = (Sandwich) resList.get(0);
        assertEquals("basic", res.getName());
        assertEquals(1, res.getCheeseAmount());
        assertEquals(Sauce.mayonnaise, res.getSauce());
        assertEquals(BasicVegetable.salad, res.getVegetable());
        s.close();

        // test on jdbc
        s = sessionFactory.openSession();
        Connection conn = s.connection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM sandwich");
        assertTrue(rs.next());
        assertEquals("basic", rs.getString("name"));
        String sauceStr = rs.getString("sauce");
        assertEquals(sauceStr, "net.incongru.util.hibernate.stuff.Sauce/mayonnaise");
        String vegStr = rs.getString("vegetable");
        assertEquals(vegStr, "net.incongru.util.hibernate.stuff.BasicVegetable/salad");
        assertFalse(rs.next());
        s.close();
    }
}
