package net.dasouk.puzzles.readers;

import net.dasouk.puzzles.PackageReader;
import net.dasouk.puzzles.PluginDescriptorException;
import net.dasouk.puzzles.ResourceNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @todo doc
 */
public class ZipPackageReader implements PackageReader {
    private String descriptorName;

    public ZipPackageReader(String descriptorName) {
        this.descriptorName = descriptorName;
    }

    public URL getPluginDescriptor(URL pluginUrl) throws PluginDescriptorException {
        try {
            return buildJarUrl(pluginUrl, descriptorName);
        } catch (MalformedURLException e) {
            throw new PluginDescriptorException(e, pluginUrl);
        }
    }

    public URL getResource(URL pluginUrl, String name) throws ResourceNotFoundException {
        try {
            return buildJarUrl(pluginUrl, name);
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException(pluginUrl, null, name);
        }
    }

    private URL buildJarUrl(URL pluginUrl, String resourceName) throws MalformedURLException {
        String urlString = "jar:" + pluginUrl.toString() + "!";
        if (!resourceName.startsWith("/")) {
            urlString += "/";
        }
        return new URL(urlString + resourceName);
    }

}
