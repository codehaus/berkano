package net.incongru.util;

import junit.framework.TestCase;

import java.lang.reflect.Field;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class TestSupport {

    public static void assertFieldEquals(Object o, String fieldName, Object expectedValue) throws NoSuchFieldException, IllegalAccessException {
        assertFieldEquals(o, o.getClass(), fieldName, expectedValue);
    }

    public static void assertFieldEquals(Object o, Class clazz, String fieldName, Object expectedValue) throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        TestCase.assertEquals(expectedValue, f.get(o));
    }

}
