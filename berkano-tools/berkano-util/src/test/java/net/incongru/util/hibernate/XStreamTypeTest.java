package net.incongru.util.hibernate;

import com.thoughtworks.xstream.XStream;
import net.incongru.util.hibernate.testmodel.DummyBean;
import org.hibernate.HibernateException;
import org.hibernate.Session;

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
public class XStreamTypeTest extends AbstractHibernateTest {
    protected String[] getHbmFiles() throws HibernateException {
        return new String[]{"net/incongru/util/hibernate/testmodel/dummy-xstream.hbm.xml"};
    }

    public void testWithLinkedHashSet() throws Exception {
        DummyBean dummy = new DummyBean();
        Set<String> complexObject = new LinkedHashSet<String>();
        complexObject.add("abc");
        complexObject.add("def");
        complexObject.add("ghi");
        complexObject.add("jkl");
        dummy.setComplexObject(complexObject);
        dummy.setName("test");

        Session s = sessionFactory.openSession();
        try {
            s.save(dummy);
            s.flush();
        } finally {
            s.close();
        }

        s = sessionFactory.openSession();
        try {
            List resList = s.createQuery("from pouet in class DummyBean").list();
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
        } finally {
            s.close();
        }

        // test on jdbc
        try {
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
        } finally {
            s.close();
        }
    }
}
