package org.nanocontainer.nanowar.servlet;

/**
 * @author gjoseph
 * @version $Revision: 1.2 $
 */
public class PicoFilterProxyTest extends AbstractWebTestCase {
    protected void tearDown() throws Exception {
        FooFilter.resetInitCounter();
    }

    public void testSimplestCaseWithClassRegistration() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, FooFilter.class, null, null, false);
        initTest("pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.FooFilter)\n",
                "", "", f);
        String res = doTest();
        assertEquals("zip-empty-zap", res);
        assertEquals(1, FooFilter.getInitCounter());
    }

    public void testSimplestCaseWithKeyRegistration() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, null, "foo-filter", null, false);
        initTest("pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation('foo-filter', org.nanocontainer.nanowar.servlet.FooFilter)\n",
                "", "", f);
        String res = doTest();
        assertEquals("zip-empty-zap", res);
        assertEquals(1, FooFilter.getInitCounter());
    }

    public void testFilterRegisteredAtRequestScope() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, null, "foo-filter", null, false);
        initTest("", "",
                "pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation('foo-filter', org.nanocontainer.nanowar.servlet.FooFilter)\n",
                f);
        String res = doTest();
        assertEquals("zip-empty-zap", res);
        assertEquals(1, FooFilter.getInitCounter());
    }

    public void testFilterWithInitSetToContextShouldCallInitOnlyOncePerLookup() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, null, "foo-filter", null, true);
        initTest("pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation('foo-filter', org.nanocontainer.nanowar.servlet.FooFilter)\n",
                "", "", f);
        doTest();
        doTest();
        String res = doTest();
        assertEquals("zip-empty-zap", res);
        assertEquals(1, FooFilter.getInitCounter());
    }

    public void testFilterWithInitSetToContextShouldCallInitOnlyOncePerLookupWhichMakesItEachTimeIfLookupNotSetToOnlyOnce() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, null, "foo-filter", null, false);
        initTest("pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation('foo-filter', org.nanocontainer.nanowar.servlet.FooFilter)\n",
                "", "", f);
        doTest();
        doTest();
        String res = doTest();
        assertEquals("zip-empty-zap", res);
        assertEquals(3, FooFilter.getInitCounter());
    }

    public void testFilterWithInitSetToRequestShouldCallInitAtEachRequest() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, null, "foo-filter", "request", false);
        initTest("pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation('foo-filter', org.nanocontainer.nanowar.servlet.FooFilter)\n",
                "", "", f);
        doTest();
        doTest();
        String res = doTest();

        assertEquals("zip-empty-zap", res);
        assertEquals(3, FooFilter.getInitCounter());
    }

    public void testFilterRegisteredAtContextScopeWithInitSetToNeverShouldNeverCallInit() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, null, "foo-filter", "never", false);
        initTest("pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation('foo-filter', org.nanocontainer.nanowar.servlet.FooFilter)\n",
                "", "", f);
        doTest();
        doTest();
        String res = doTest();
        assertEquals("zip-empty-zap", res);
        assertEquals(0, FooFilter.getInitCounter());
    }

    public void testFilterRegisteredAtRequestScopeWithInitSetToNeverShouldNeverCallInit() throws Exception {
        FilterDef f = new FilterDef("pico-filter", PicoFilterProxy.class, null, "foo-filter", "never", false);
        initTest("", "",
                "pico.registerComponentImplementation(org.nanocontainer.nanowar.servlet.Foo)\n" +
                "pico.registerComponentImplementation('foo-filter', org.nanocontainer.nanowar.servlet.FooFilter)\n",
                f);
        doTest();
        doTest();
        String res = doTest();
        assertEquals("zip-empty-zap", res);
        assertEquals(0, FooFilter.getInitCounter());
    }
}