package net.incongru.webwork;

import junit.framework.TestCase;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class ModelUrlBuilderTest extends TestCase {
    public void testSimpleModelMakesSimpleUrl() throws Exception {
        SomeSimpleModel m = new SomeSimpleModel(123, "blah");
        ModelUrlBuilder b = new ModelUrlBuilder("http://host/test");
        b.setModel(m);
        assertEquals("http://host/test?foo=blah&bar=123", b.buildUrl());
    }

    public void testNullValuesShouldNotEndupInUrl() throws Exception {
        SomeSimpleModel m = new SomeSimpleModel(123, null);
        ModelUrlBuilder b = new ModelUrlBuilder("http://host/test");
        b.setModel(m);
        assertEquals("http://host/test?bar=123", b.buildUrl());
    }

    public void testWithObjectAndArray() throws Exception {
        SomeComplexModel m = new SomeComplexModel(123, new String[]{"abc", "def"});
        ModelUrlBuilder b = new ModelUrlBuilder("http://host/test");
        b.setModel(m);
        assertEquals("http://host/test?arr=abc&arr=def&i=123", b.buildUrl());
    }

    public void testWithArrayWrapper() throws Exception {
        ArrayWrapper m = new ArrayWrapper(new String[]{"abc", "def", "xyz"});
        ModelUrlBuilder b = new ModelUrlBuilder("http://host/test");
        b.setModel(m);
        assertEquals("http://host/test?arr=abc&arr=def&arr=xyz", b.buildUrl());
    }

//    public void testWithArrayAndNestedModel() throws Exception {
//        SomeComplexModel m = new SomeComplexModel(123, new String[]{"abc", "def"}, new SomeSimpleModel(123, "blah"));
//        ModelUrlBuilder b = new ModelUrlBuilder("http://host/test");
//        b.setModel(m);
//        assertEquals("http://host/test?arr=abc&arr=def&i=123", b.buildUrl());
//    }

    public static class SomeSimpleModel {
        private int bar;
        private String foo;

        public SomeSimpleModel(int bar, String foo) {
            this.bar = bar;
            this.foo = foo;
        }

        public int getBar() {
            return bar;
        }

        public String getFoo() {
            return foo;
        }
    }

    public static class ArrayWrapper {
        private String[] arr;

        public ArrayWrapper(String[] arr) {
            this.arr = arr;
        }

        public String[] getArr() {
            return arr;
        }
    }

    public static class SomeComplexModel {
        private int i;
        private String[] arr;
        private SomeSimpleModel nested;

        public SomeComplexModel(int i, String[] arr) {
            this.i = i;
            this.arr = arr;
        }
        public SomeComplexModel(int i, String[] arr, SomeSimpleModel nested) {
            this.i = i;
            this.arr = arr;
            this.nested=nested;
        }

        public int getI() {
            return i;
        }

        public String[] getArr() {
            return arr;
        }

        public SomeSimpleModel getNested() {
            return nested;
        }
    }
}
