package net.dasouk.puzzles.builders;

import net.dasouk.puzzles.PluginDescriptor;
import net.dasouk.puzzles.PluginDescriptorBuilder;
import net.dasouk.puzzles.PluginDescriptorException;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Builds a PluginDescriptor from a properties file.
 * The only mandatory field is the plugin's name, the other fields are optional.
 *
 * possible fields:
 * <ul>
 *      <li>name</li>
 *      <li>family</li>
 *      <li>author</li>
 *      <li>url</li>
 *      <li>description</li>
 *      <li>version</li>
 *      <li>class => the main class of the plugin, the one to be instanciated</li>
 *      <li>jars => list of dependencies packaged with the plugin, comma separated values</li>
 *      <li>resources => list of public resources packaged with the plugin, comma separated values</li> 
 * </ul>
 *
 * @todo add support for localized descriptions
 */
public class PropertiesBasedDescriptorBuilder implements PluginDescriptorBuilder {
    public PluginDescriptor buildDescriptor(URL descriptorUrl) throws PluginDescriptorException {
        Properties properties = new Properties();
        try {
            properties.load(descriptorUrl.openStream());
            String name = properties.getProperty("name");
            if (name == null || name.trim().length()==0){
                throw new PluginDescriptorException("plugin name cannot be empty",descriptorUrl);
            }
            String family = properties.getProperty("family");
            String author = properties.getProperty("author");
            String url = properties.getProperty("url");
            String defaultDescription = properties.getProperty("description");
            String version = properties.getProperty("version");
            String mainClass = properties.getProperty("class");
            Collection<String> jars = explode(properties.getProperty("jars")," ,");
            Collection<String> publicResources = explode(properties.getProperty("resources")," ,");

            return new PluginDescriptor(name, family, author, url, null, defaultDescription, version, publicResources, jars, mainClass);
        } catch (IOException e) {
            throw new PluginDescriptorException(e,descriptorUrl);
        }
    }

    private Collection<String> explode(String concatenatedString, String separators) {
        StringTokenizer tokenizer = new StringTokenizer(concatenatedString,separators);
        Collection<String> elements = new HashSet<String>();
        while(tokenizer.hasMoreTokens()){
            elements.add(tokenizer.nextToken());
        }
        return elements;
    }
}
