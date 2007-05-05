package net.incongru.berkano.user.hibernate;

import net.incongru.berkano.user.GroupImpl;
import net.incongru.berkano.user.UserImpl;
import net.incongru.berkano.user.extensions.UserPropertyAccessor;
import org.hibernate.Session;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class UserPropertyAccessorTest extends MockObjectTestCase {
    private Mock sessionMock;
    private GroupImpl g1;
    private GroupImpl g2;
    private GroupImpl g3;
    private UserImpl u;
    private UserPropertyAccessor a;

    protected void setUp() throws Exception {
        g1 = new GroupImpl();
        g1.setProperty("foo", "foo-value-1");
        g1.setProperty("bar", "bar-value-1");
        g1.setProperty("baz", "baz-value-1");
        g2 = new GroupImpl();
        g2.setProperty("foo", "foo-value-2");
        g2.setProperty("bar", "bar-value-2");
        g3 = new GroupImpl();
        g3.setProperty("foo", "foo-value-3");
        g3.setProperty("blablabla", "blabla-value");
        u = new UserImpl();
        u.addGroup(g1);
        u.addGroup(g2);
        u.addGroup(g3);
        sessionMock = mock(Session.class);
        //sessionMock.expects(atLeastOnce()).method("lock").with(isA(User.class), isA(LockMode.class));
        a = new HibernatedUserPropertyAccessor();//(Session) sessionMock.proxy());
    }

    protected void tearDown() throws Exception {
        sessionMock.verify();
        super.tearDown();
    }

    public void testGetFirstPropertyShouldReturnAnyOfTheExistingPropertiesSetInGroup() {
        String valueBar = (String) a.getFirstValue(u, "bar");
        assertNotNull(valueBar);
        assertTrue(valueBar.startsWith("bar-value-"));
        assertEquals(11, valueBar.length());

        String valueFoo = (String) a.getFirstValue(u, "foo");
        assertNotNull(valueFoo);
        assertTrue(valueFoo.startsWith("foo-value-"));
        assertEquals(11, valueFoo.length());
    }

    public void testGetFirstPropertyShouldReturnTheOnlyExistingPropertySetInGroups() {
        String valueBlablabla = (String) a.getFirstValue(u, "blablabla");
        assertNotNull(valueBlablabla);
        assertEquals("blabla-value", valueBlablabla);
    }

    public void testGetFirstPropertyShouldReturnTheUserPropertyBeforeTheGroupProperties() {
        u.setProperty("foo", "user-foo");
        u.setProperty("user-only", "user-value");

        assertEquals("user-foo", a.getFirstValue(u, "foo"));
        assertEquals("user-value", a.getFirstValue(u, "user-only"));
    }

    public void testGetFirstPropertyShouldReturnNullForNonExistingProperties() {
        assertNull(a.getFirstValue(u, "non-existing"));
    }

    public void testGetSinglePropertyShouldReturnTheUserPropertyBeforeTheGroupProperties() {
        u.setProperty("foo", "user-foo");
        u.setProperty("user-only", "user-value");

        assertEquals("user-foo", a.getSingleValue(u, "foo"));
        assertEquals("user-value", a.getSingleValue(u, "user-only"));
    }

    public void testGetSinglePropertyShouldThrowExceptionIfPropertyDefinedSeveralTimesInGroupPropertiesAndNotInUserProperties() {
        try {
            a.getSingleValue(u, "foo");
            fail("should have thrown exception");
        } catch (IllegalStateException e) {
            assertEquals("Property foo is defined in several groups of the user", e.getMessage());
        }
    }

    public void testGetSinglePropertyShouldReturnTheOnlyExistingPropertySetInGroups() {
        assertEquals("baz-value-1", a.getSingleValue(u, "baz"));
        assertEquals("blabla-value", a.getSingleValue(u, "blablabla"));
    }

    public void testGetSinglePropertyShouldReturnNullForNonExistingProperties() {
        assertNull(a.getSingleValue(u, "non-existing"));
    }

    public void testGetValuesShouldReturnEmptyCollectionForNonExistingProperties() {
        Collection v = a.getValues(u, "non-existing");
        assertNotNull(v);
        assertEquals(0, v.size());
    }

    public void testGetValuesShouldReturnSingleElementCollectionForPropertyDefinedOnce() {
        u.setProperty("user-only", "user-value");
        Collection v = a.getValues(u, "user-only");
        assertEquals(1, v.size());
        assertEquals("user-value", v.iterator().next());

        Collection v2 = a.getValues(u, "baz");
        assertEquals(1, v2.size());
        assertEquals("baz-value-1", v2.iterator().next());
    }

    public void testGetValuesShouldReturnTwoElementsCollectionForPropertyDefinedOnceForUserAndOnceForGroup() {
        u.setProperty("baz", "user-baz");
        Collection v = a.getValues(u, "baz");
        assertEquals(2, v.size());
        assertTrue(v.contains("user-baz"));
        assertTrue(v.contains("baz-value-1"));
    }

    public void testGetValuesShouldReturnCollectionOfValuesWithBothUserAndGroupsProperties() {
        // only in groups
        Collection v = a.getValues(u, "foo");
        assertEquals(3, v.size());
        assertTrue(v.contains("foo-value-1"));
        assertTrue(v.contains("foo-value-2"));
        assertTrue(v.contains("foo-value-3"));

        // also at user's level
        u.setProperty("foo", "user-foo");
        Collection v2 = a.getValues(u, "foo");
        assertEquals(4, v2.size());
        assertTrue(v2.contains("user-foo"));
        assertTrue(v2.contains("foo-value-1"));
        assertTrue(v2.contains("foo-value-2"));
        assertTrue(v2.contains("foo-value-3"));
    }

    public void testGetUserOnlyValueShouldNotReturnValuesFromGroups() {
        u.setProperty("foo", "user-foo");
        assertEquals("user-foo", a.getUserOnlyValue(u, "foo"));
    }

    public void testGetGroupSingleValueShouldThrowExceptionWhenMultipleValuesEncountered() {
        try {
            a.getGroupSingleValue(u, "foo");
            fail("should have thrown exception");
        } catch (IllegalStateException e) {
            assertEquals("Property foo is defined in several groups of the user", e.getMessage());
        }
    }

    public void testGetGroupSingleValueShouldNotReturnUserValues() {
        u.setProperty("baz", "user-baz");
        assertEquals("baz-value-1", a.getGroupSingleValue(u, "baz"));
    }

    public void testGetGroupValuesShouldNotReturnUserValues() {
        u.setProperty("foo", "user-foo");
        Collection v = a.getGroupValues(u, "foo");
        assertEquals(3, v.size());
        assertTrue(v.contains("foo-value-1"));
        assertTrue(v.contains("foo-value-2"));
        assertTrue(v.contains("foo-value-3"));
    }

    public void testAggregateShouldThrowAnExceptionForNonSupportedTypes() {
        try {
            a.aggregate(u, "foo");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Unsupported property type : java.lang.String", e.getMessage());
        }
    }

    public void testAggregateShouldWorkWithCollections() {
        Collection c = Arrays.asList(new String[]{"1", "2", "3"});
        Collection c1 = Arrays.asList(new String[]{"a", "b", "c"});
        Collection c2 = Arrays.asList(new String[]{"A", "B", "C"});
        u.setProperty("test", c);
        g1.setProperty("test", c1);
        g2.setProperty("test", c2);
        Object o = a.aggregate(u, "test");
        assertTrue(o instanceof Collection);
        Collection result = (Collection) o;
        assertEquals(9, result.size());
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("a"));
        assertTrue(result.contains("b"));
        assertTrue(result.contains("c"));
        assertTrue(result.contains("A"));
        assertTrue(result.contains("B"));
        assertTrue(result.contains("C"));
    }

    public void testAggregateShouldWorkWithMaps() {
        Map m = new HashMap();
        m.put("a", "1");
        m.put("b", "2");
        m.put("c", "3");
        Map m1 = new TreeMap();
        m1.put("A", "0");
        m1.put("B", "000");
        u.setProperty("test", m);
        g1.setProperty("test", m1);
        Object o = a.aggregate(u, "test");
        assertTrue(o instanceof Map);
        Map result = (Map) o;
        assertEquals(5, result.size());
        assertTrue(result.containsKey("a"));
        assertTrue(result.containsKey("b"));
        assertTrue(result.containsKey("c"));
        assertTrue(result.containsKey("A"));
        assertTrue(result.containsKey("B"));
        assertEquals("1", result.get("a"));
        assertEquals("2", result.get("b"));
        assertEquals("3", result.get("c"));
        assertEquals("0", result.get("A"));
        assertEquals("000", result.get("B"));
    }

    /** works but commented out - don't like the way this works
     public void testAggregateShouldWorkWithArrays() {
     String[] s = new String[]{"1", "2", "3", "4"};
     String[] s1 = new String[]{"a", "b", "c", "d", "e"};
     String[] s2 = new String[]{"A", "B", "C"};
     u.setProperty("test", s);
     g1.setProperty("test", s2);
     g2.setProperty("test", s1);
     Object o = a.aggregate(u, "test");
     assertTrue(o instanceof Object[]);
     Object[] result = (Object[]) o;
     assertEquals(12, result.length);
     assertEquals("1", result[0]);
     assertEquals("2", result[1]);
     assertEquals("3", result[2]);
     assertEquals("4", result[3]);
     assertEquals("a", result[4]);
     assertEquals("b", result[5]);
     assertEquals("c", result[6]);
     assertEquals("d", result[7]);
     assertEquals("e", result[8]);
     assertEquals("A", result[9]);
     assertEquals("B", result[10]);
     assertEquals("C", result[11]);
     }
     */
}
