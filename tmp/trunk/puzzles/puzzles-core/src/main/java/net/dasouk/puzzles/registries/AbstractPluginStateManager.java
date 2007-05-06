package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.PluginState;
import net.dasouk.puzzles.PluginStateManager;
import net.dasouk.puzzles.PluginStatePersistenceException;
import net.dasouk.puzzles.util.HashTwoLevelMap;
import net.dasouk.puzzles.util.TwoLevelMap;

/**
 * Abstract state manager: handles all the work except actual persistence
 */
public abstract class AbstractPluginStateManager implements PluginStateManager {
    protected TwoLevelMap<String, String, PluginState> pluginStates;

    public AbstractPluginStateManager() throws Exception {
        pluginStates = new HashTwoLevelMap<String, String, PluginState>();
    }

    protected abstract void loadStates() throws Exception;

    protected abstract void persistStates() throws Exception;

    public synchronized void setPluginState(String family, String pluginName, PluginState state) throws PluginStatePersistenceException {
        pluginStates.put(family, pluginName, state);
        try {
            persistStates();
        } catch (Exception e) {
            throw new PluginStatePersistenceException(e.getMessage(), e, null);
        }
    }

    public synchronized PluginState getPluginState(String family, String pluginName) {
        final PluginState pluginState = pluginStates.get(family, pluginName);
        if (pluginState == null) {
            return PluginState.UNDEPLOYED;
        }
        return pluginState;
    }

    public PluginState removePluginState(String family, String pluginName) throws PluginStatePersistenceException {
        final PluginState pluginState = pluginStates.remove(family, pluginName);
        if (pluginState == null) {
            return PluginState.UNDEPLOYED;
        } else {
            try {
                persistStates();
            } catch (Exception e) {
                throw new PluginStatePersistenceException(e.getMessage(), e, null);
            }
            return pluginState;
        }
    }

    public boolean isDeployed(String family, String name) {
        final PluginState pluginState = getPluginState(family, name);
        return (pluginState != null && pluginState.name().equals(PluginState.DEPLOYED.name()));
    }
}
