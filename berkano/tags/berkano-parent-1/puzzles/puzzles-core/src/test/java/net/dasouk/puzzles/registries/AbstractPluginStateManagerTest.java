package net.dasouk.puzzles.registries;

import junit.framework.TestCase;
import net.dasouk.puzzles.PluginState;
import net.dasouk.puzzles.PluginStateManager;
import net.dasouk.puzzles.PluginStatePersistenceException;

import java.io.IOException;

/**
 * Abstract implementation of the PluginStateManager interface, all subclasses of this abstract
 * class now only have to deal with persisting the states and loading the states.
 * Note that all persistence operations *SHOULD* be transactional
 */
public abstract class AbstractPluginStateManagerTest extends TestCase {
    protected PluginStateManager pluginStateManager;

    public void testSimpleSetGet() throws Exception, IOException, PluginStatePersistenceException {
        pluginStateManager = newInstance();
        final String pluginName = "myPlugin";
        final String family = "family";
        pluginStateManager.setPluginState(family, pluginName, PluginState.DEPLOYED);
        assertEquals(PluginState.DEPLOYED, pluginStateManager.getPluginState(family, pluginName));
        pluginStateManager.setPluginState(family, pluginName, PluginState.UNDEPLOYED);
        assertEquals(PluginState.UNDEPLOYED, pluginStateManager.getPluginState(family, pluginName));
    }

    public void testAbsentPlugin() throws Exception {
        pluginStateManager = newInstance();
        final String pluginName = "myPlugin";
        final String family = "family";
        final PluginState pluginState = pluginStateManager.getPluginState(family, pluginName);
        assertEquals("the plugin is not declared in the plugin state manager, its state should be returned as undeployed", PluginState.UNDEPLOYED.name(), pluginState.name());

    }

    public void testPeristence() throws Exception, IOException, PluginStatePersistenceException {
        pluginStateManager = newInstance();
        final String pluginName = "myPlugin";
        final String family = "family";
        pluginStateManager.setPluginState(family, pluginName, PluginState.DEPLOYED);
        assertEquals(PluginState.DEPLOYED, pluginStateManager.getPluginState(family, pluginName));
        //instanciate a new PluginStateManager
        pluginStateManager = newInstance();
        final PluginState pluginState = pluginStateManager.getPluginState(family, pluginName);
        assertEquals(PluginState.DEPLOYED.name(), pluginState.name());//use name() on the enum values since they won't be equals (different class loader
    }

    public abstract PluginStateManager newInstance() throws Exception;
}
