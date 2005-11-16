package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: olivier
 * Date: Nov 16, 2005
 * Time: 3:57:14 PM
 * To change this template use File | Settings | File Templates.
 *
 * @todo documentation
 */
public interface PackageReader extends Serializable {
    public URL getPluginDescriptor(URL pluginUrl) throws ResourceNotFoundException;

    public URL getPluginBluePrints(URL pluginUrl) throws ResourceNotFoundException;

    public URL getResource(String name) throws ResourceNotFoundException;
}
