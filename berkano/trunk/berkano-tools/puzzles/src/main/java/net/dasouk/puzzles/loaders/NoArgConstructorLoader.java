package net.dasouk.puzzles.loaders;

import net.dasouk.puzzles.*;
import net.dasouk.puzzles.util.TempJarExtractor;

import java.net.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.io.IOException;

/**
 *
 */
public class NoArgConstructorLoader<PluginClass> implements PluginLoader<PluginClass> {
    private PluginDescriptorBuilder pluginDescriptorBuilder;
    private PackageReader packageReader;
    private TempJarExtractor jarExtractor;

    public NoArgConstructorLoader(PluginDescriptorBuilder pluginDescriptorBuilder,
                                  PackageReader packageReader) {
        this.pluginDescriptorBuilder = pluginDescriptorBuilder;
        this.packageReader = packageReader;
        jarExtractor = new TempJarExtractor();
    }

    public PackagedPlugin<PluginClass> load(URL pluginPackageUrl) throws PluginException {
        PluginDescriptor descriptor = pluginDescriptorBuilder.buildDescriptor(packageReader.getPluginDescriptor(pluginPackageUrl));
        String mainClassName = descriptor.getMainClass();
        if (mainClassName == null) {
            //no class, the plugin is only made of resources
            return new PackagedPlugin<PluginClass>(descriptor, null);
        } else {
            Collection<String> jars = descriptor.getJars();
            //retrieve jars' URLs
            Collection<URL> urls = new HashSet<URL>(jars.size());
            for (String jar : jars) {
                urls.add(packageReader.getResource(pluginPackageUrl, jar));
            }

            try {
                //load them
                ClassLoader classLoader = loadJars(urls);
                //retrieve class
                Class mainClass = classLoader.loadClass(mainClassName);
                //instanciate
                PluginClass pluginInstance = (PluginClass) mainClass.newInstance();
                return new PackagedPlugin<PluginClass>(descriptor, pluginInstance);
            } catch (ClassNotFoundException e) {
                throw new PluginInstanciationException(e, pluginPackageUrl);
            } catch (IllegalAccessException e) {
                throw new PluginInstanciationException(e, pluginPackageUrl);
            } catch (InstantiationException e) {
                throw new PluginInstanciationException(e, pluginPackageUrl);
            } catch (IOException e) {
                throw new PluginInstanciationException(e, pluginPackageUrl);
            }
        }
    }

    private ClassLoader loadJars(Collection<URL> urls) throws IOException {
        URL[] urlsArray = new URL[urls.size()];
        int i = 0;
        for (URL url : urls) {
            URL tmpUrl = jarExtractor.extractJarToTempFile(url);
            urlsArray[i] = tmpUrl;
            i++;
        }

        ClassLoader classLoader = URLClassLoader.newInstance(urlsArray,this.getClass().getClassLoader());
        return classLoader;
    }

    public URL getResource(URL pluginUrl, String resourceName) throws ResourceNotFoundException {
        return packageReader.getResource(pluginUrl, resourceName);
    }
}
