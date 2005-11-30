package net.dasouk.puzzles.builders;

import net.dasouk.puzzles.PluginDescriptor;
import net.dasouk.puzzles.PluginDescriptorBuilder;
import net.dasouk.puzzles.PluginDescriptorException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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
 *      <li>description => default description</li>
 *      <li>description.#2 LETTER LOCALE# => for localized description</li>
 *      <li>version</li>
 *      <li>class => the main class of the plugin, the one to be instanciated</li>
 *      <li>jars => list of dependencies packaged with the plugin, comma separated values</li>
 *      <li>resources => list of public resources packaged with the plugin, comma separated values</li> 
 * </ul>
 */
public class PropertiesBasedDescriptorBuilder implements PluginDescriptorBuilder {
    public PluginDescriptor buildDescriptor(URL descriptorUrl) throws PluginDescriptorException {
        Properties properties = new Properties();
        try {
            properties.load(descriptorUrl.openStream());

            Map<Locale, String> localizedDescriptions = new HashMap<Locale, String>();
            Set allKeys = properties.keySet();
            for(Object key:allKeys){
                String keyString = (String) key;//safe cast: cf javadoc "Each key and its corresponding value in the property list is a string."
                if (keyString.startsWith("description.")){
                    try {
                        Locale locale = new Locale(keyString.substring("description.".length()));
                        localizedDescriptions.put(locale, (String) properties.get(key));
                    } catch (Exception e) {
                        //if the locale is not valid, the corresponding description is not taken into account
                    }
                }
            }

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

            return new PluginDescriptor(name, family, author, url, localizedDescriptions, defaultDescription, version, publicResources, jars, mainClass);
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
