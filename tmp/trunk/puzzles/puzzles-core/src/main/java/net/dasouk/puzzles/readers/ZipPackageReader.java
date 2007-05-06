package net.dasouk.puzzles.readers;

import net.dasouk.puzzles.PackageReader;
import net.dasouk.puzzles.PluginDescriptorException;
import net.dasouk.puzzles.ResourceNotFoundException;

import java.io.IOException;
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
            throw new PluginDescriptorException(e.getMessage(), e, pluginUrl);
        }
    }

    public URL getResource(URL pluginUrl, String name) throws ResourceNotFoundException {
        try {
            final URL url = buildJarUrl(pluginUrl, name);
            url.openConnection().connect(); //to check if the resource actually exists
            return url;
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException(e.getMessage(), e, pluginUrl, name);
        } catch (IOException e) {
            throw new ResourceNotFoundException(e.getMessage(), e, pluginUrl, name);
        }
    }

    protected URL buildJarUrl(URL pluginUrl, String resourceName) throws MalformedURLException {
        String urlString = "jar:" + pluginUrl.toString() + "!";
        if (!resourceName.startsWith("/")) {
            urlString += "/";
        }
        return new URL(urlString + resourceName);
    }

}
