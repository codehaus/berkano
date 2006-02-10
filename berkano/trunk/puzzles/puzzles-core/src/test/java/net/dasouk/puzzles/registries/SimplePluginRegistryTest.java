package net.dasouk.puzzles.registries;

import junit.framework.TestCase;
import net.dasouk.puzzles.*;
import static org.easymock.EasyMock.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

//TODO: TEST TEST TEST TEST !!!!!!!!!!!!!!!

public class SimplePluginRegistryTest extends TestCase {
    private SimplePluginRegistry registry;
    private PluginStore mockStore;
    private PluginLoader mockLoader;
    private PluginStateManager mockStateManager;
    private PluginListener mockListener;
    private PluginListener mockListener2;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mockStore = createMock(PluginStore.class);
        mockLoader = createMock(PluginLoader.class);
        mockStateManager = createMock(PluginStateManager.class);
        mockListener = createMock(PluginListener.class);
        mockListener2 = createMock(PluginListener.class);
    }

    public void testStartUpWithNoPluginInStore() {
        expect(mockStore.getPluginsInStore()).andReturn(new ArrayList<URL>());
        replay(mockStore);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        final Map<URL, PluginException> exceptions = registry.startUp();
        assertEquals(0, exceptions.size());
    }

    public void testStartUpWithSomePluginsInStore() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        URL url2 = new URL("http://www.google.com");
        expect(mockStore.getPluginsInStore()).andReturn(Arrays.asList(url1, url2));
        String plugin1 = "ThePluginAsString_1";
        final PackagedPlugin packagedPlugin1 = new PackagedPlugin(new PluginDescriptor("name1", "family1", null, null, null, null, null, null, null, null), plugin1);
        expect(mockLoader.load(url1)).andReturn(packagedPlugin1);
        String plugin2 = "ThePluginAsString_2";
        final PackagedPlugin packagedPlugin2 = new PackagedPlugin(new PluginDescriptor("name2", "family2", null, null, null, null, null, null, null, null), plugin2);
        expect(mockLoader.load(url2)).andReturn(packagedPlugin2);
        expect(mockStateManager.isDeployed("family1", "name1")).andReturn(true);//plugin1 was deployed
//        mockStateManager.setPluginState("family1", "name1", PluginState.DEPLOYED);
        expect(mockStateManager.isDeployed("family2", "name2")).andReturn(false);//plugin2 was not deployed
        mockListener.deployedPlugin(packagedPlugin1);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        replay(mockListener);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        registry.addPluginListener(mockListener, "family1");
        registry.addPluginListener(mockListener2, "family1");
        boolean removed = registry.removePluginListener(mockListener2, "family2");//wrong family cannot be removed
        assertFalse(removed);
        removed = registry.removePluginListener(mockListener2, "family1");//should be removed
        assertTrue(removed);
        final Map<URL, PluginException> exceptions = registry.startUp();
        assertEquals(0, exceptions.size());
        Object pluginInstance = registry.getPluginInstance("family1", "name1");
        assertEquals(plugin1, pluginInstance);
    }

    public void testStartUpWithOnePluginInException() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        expect(mockStore.getPluginsInStore()).andReturn(Arrays.asList(url1));
        final PluginDescriptorException expectedException = new PluginDescriptorException("Descriptor not valid", url1);
        expect(mockLoader.load(url1)).andThrow(expectedException);
        mockStore.remove(url1);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        final Map<URL, PluginException> exceptions = registry.startUp();
        assertEquals(1, exceptions.size());
        PluginException exception = exceptions.get(url1);
        assertEquals(expectedException, exception);
    }

    public void testInstallWithoutDeploy() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        expect(mockStore.store(url1)).andReturn(url1);
        final PackagedPlugin packagedPlugin1 = new PackagedPlugin(new PluginDescriptor("name1", "family1", null, null, null, null, null, null, null, null), "ThePluginAsString_1");
        expect(mockLoader.load(url1)).andReturn(packagedPlugin1);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        registry.addPluginListener(mockListener, "family1");
        final PackagedPlugin packagedPlugin = registry.installPlugin(url1, false);
        assertEquals(packagedPlugin, packagedPlugin1);
    }

    public void testInstallWithAutomaticDeployment() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        expect(mockStore.store(url1)).andReturn(url1);
        final PackagedPlugin packagedPlugin1 = new PackagedPlugin(new PluginDescriptor("name1", "family1", null, null, null, null, null, null, null, null), "ThePluginAsString_1");
        expect(mockLoader.load(url1)).andReturn(packagedPlugin1);
        expect(mockStateManager.isDeployed("family1", "name1")).andReturn(false);
        mockStateManager.setPluginState("family1", "name1", PluginState.DEPLOYED);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        mockListener.deployedPlugin(packagedPlugin1);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        registry.addPluginListener(mockListener, "family1");
        final PackagedPlugin packagedPlugin = registry.installPlugin(url1, true);
        assertEquals(packagedPlugin, packagedPlugin1);
    }

    public void testInstallWithAutomaticDeploymentOfAnAlreadyPresentPlugin() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        expect(mockStore.store(url1)).andReturn(url1).times(2);
        mockStore.remove(url1);
        final PackagedPlugin packagedPlugin1 = new PackagedPlugin(new PluginDescriptor("name1", "family1", null, null, null, null, null, null, null, null), "ThePluginAsString_1");
        expect(mockLoader.load(url1)).andReturn(packagedPlugin1).times(2);
        expect(mockStateManager.isDeployed("family1", "name1")).andReturn(false);
        mockStateManager.setPluginState("family1", "name1", PluginState.DEPLOYED);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        final PackagedPlugin packagedPlugin = registry.installPlugin(url1, true);
        assertEquals(packagedPlugin, packagedPlugin1);

        try {
            registry.installPlugin(url1, true);
            fail("there is already an installed plugin with the same name and family");
        } catch (PluginException e) {
            assertEquals("there is already an installed plugin with the same name and family", e.getMessage());
        }
    }

    public void testGetUnexistingPluginAndResources() {
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        try {
            registry.getPluginDescriptor("family", "name");
            fail("the plugin did not exist");
        } catch (PluginNotFoundException e) {
            assertEquals("the plugin could not be found in the registry", e.getMessage());
            assertEquals("family", e.getPluginFamily());
            assertEquals("name", e.getPluginName());
        }

        try {
            registry.getPluginInstance("family", "name");
            fail("the plugin did not exist");
        } catch (PluginNotFoundException e) {
            assertEquals("the plugin could not be found in the registry", e.getMessage());
            assertEquals("family", e.getPluginFamily());
            assertEquals("name", e.getPluginName());
        }

        try {
            registry.getPluginResource("family", "name", "popo");
            fail("the plugin did not exist");
        } catch (PluginNotFoundException e) {
            assertEquals("the plugin could not be found in the registry", e.getMessage());
            assertEquals("family", e.getPluginFamily());
            assertEquals("name", e.getPluginName());
        }


            String family = "family";
            String pluginName = "name";
        try {
            registry.undeployPlugin(family, pluginName); //does nothing
        } catch (PluginNotFoundException e) {
            assertEquals(family,e.getPluginFamily());
            assertEquals(pluginName,e.getPluginName());
        }
    }

    public void testAccessPluginResourcesAfterSuccessfullDeployment() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        expect(mockStore.store(url1)).andReturn(url1).times(2);
        mockStore.remove(url1);
        PluginDescriptor pluginDescriptor = new PluginDescriptor("name1", "family1", null, null, null, null, null, null, null, null);
        final PackagedPlugin packagedPlugin1 = new PackagedPlugin(pluginDescriptor, "ThePluginAsString_1");
        expect(mockLoader.load(url1)).andReturn(packagedPlugin1).times(2);
        expect(mockStateManager.isDeployed("family1", "name1")).andReturn(false);
        mockStateManager.setPluginState("family1", "name1", PluginState.DEPLOYED);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        final PackagedPlugin packagedPlugin = registry.installPlugin(url1, true);
        assertEquals(packagedPlugin1, packagedPlugin);
        assertEquals(pluginDescriptor, registry.getPluginDescriptor("family1", "name1"));
    }

    public void testDeployAlreadyDeployedPlugin() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        expect(mockStore.store(url1)).andReturn(url1);
        final PackagedPlugin packagedPlugin1 = new PackagedPlugin(new PluginDescriptor("name1", "family1", null, null, null, null, null, null, null, null), "ThePluginAsString_1");
        expect(mockLoader.load(url1)).andReturn(packagedPlugin1);
        expect(mockStateManager.isDeployed("family1", "name1")).andReturn(false);
        mockStateManager.setPluginState("family1", "name1", PluginState.DEPLOYED);
        expect(mockStateManager.isDeployed("family1", "name1")).andReturn(true);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        registry.installPlugin(url1, true);
        registry.addPluginListener(mockListener, "family1");
        registry.deployPlugin("family1", "name1");
    }

    public void testPluginInstanceAccess() throws MalformedURLException {
        URL url1 = new URL("http://www.google.fr");
        expect(mockStore.store(url1)).andReturn(url1);
        PluginDescriptor pluginDescriptor = new PluginDescriptor("name1", "family1", null, null, null, null, null, null, null, null);
        String pluginInstance = "ThePluginAsString_1";
        final PackagedPlugin packagedPlugin1 = new PackagedPlugin(pluginDescriptor, pluginInstance);
        expect(mockLoader.load(url1)).andReturn(packagedPlugin1);
        expect(mockStateManager.isDeployed("family1", "name1")).andReturn(false);
        mockStateManager.setPluginState("family1", "name1", PluginState.DEPLOYED);
        replay(mockStore);
        replay(mockLoader);
        replay(mockStateManager);
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        registry.installPlugin(url1, true);

        assertEquals(pluginInstance, registry.getPluginInstance("family1", "name1"));

    }

    public void testDeployNonExistingPlugin() {
        registry = new SimplePluginRegistry(mockStore, mockLoader, mockStateManager);
        String family = "family";
        String pluginName = "name";
        try {
            registry.deployPlugin(family, pluginName);
            fail("there is no such plugin");
        } catch (PluginNotFoundException e) {
            assertEquals(family,e.getPluginFamily());
            assertEquals(pluginName,e.getPluginName());
        }

    }
}
