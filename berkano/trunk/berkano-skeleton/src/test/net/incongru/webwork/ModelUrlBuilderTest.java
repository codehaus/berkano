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
    
    public void testWithArray() throws Exception {
        SomeComplexModel m = new SomeComplexModel(123, new String[]{"abc", "def"});
        ModelUrlBuilder b = new ModelUrlBuilder("http://host/test");
        b.setModel(m);
        assertEquals("http://host/test?foo=abc&foo=def&bar=123", b.buildUrl());
    }

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
    public static class SomeComplexModel {
        private int bar;
        private String[] foo;

        public SomeComplexModel(int bar, String[] foo) {
            this.bar = bar;
            this.foo = foo;
        }

        public int getBar() {
            return bar;
        }

        public String[] getFoo() {
            return foo;
        }
    }
}
