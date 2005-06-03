package net.incongru.hibernate;

import com.thoughtworks.xstream.XStream;
import net.incongru.hibernate.stuff.DummyBean;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.5 $
 */
public class XStreamTypeTest extends HibernateTestCase {
    protected void exportSchema() throws HibernateException {
        exportSchema(new String[]{"net/incongru/hibernate/dummy-xstream.hbm.xml"});
    }

    public void testWithLinkedHashSet() throws Exception {
        Session s = sessionFactory.openSession();

        DummyBean dummy = new DummyBean();
        Set complexObject = new LinkedHashSet();
        complexObject.add("abc");
        complexObject.add("def");
        complexObject.add("ghi");
        complexObject.add("jkl");
        dummy.setComplexObject(complexObject);
        dummy.setName("test");

        s.save(dummy);
        s.close();

        s = sessionFactory.openSession();
        List resList = s.find("from pouet in class DummyBean");
        assertEquals(1, resList.size());
        DummyBean res = (DummyBean) resList.get(0);
        assertEquals("test", res.getName());
        Object obj = res.getComplexObject();
        assertEquals(LinkedHashSet.class, obj.getClass());
        LinkedHashSet set = (LinkedHashSet) obj;
        Iterator it = set.iterator();
        assertTrue(it.hasNext());
        assertEquals("abc", it.next());
        assertTrue(it.hasNext());
        assertEquals("def", it.next());
        assertTrue(it.hasNext());
        assertEquals("ghi", it.next());
        assertTrue(it.hasNext());
        assertEquals("jkl", it.next());
        assertFalse(it.hasNext());
        s.close();

        // test on jdbc
        s = sessionFactory.openSession();
        Connection conn = s.connection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM dummy");
        assertTrue(rs.next());
        assertEquals("test", rs.getString("name"));
        String xml = rs.getString("complex_object");
        XStream xStream = new XStream();
        Object o = xStream.fromXML(xml);
        assertEquals(complexObject, o);
        assertFalse(rs.next());
        s.close();
    }
}
