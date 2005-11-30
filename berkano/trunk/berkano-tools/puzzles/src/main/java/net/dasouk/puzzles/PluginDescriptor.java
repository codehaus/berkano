package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * A Plugin descriptor contains the metadata of the plugin.
 * The actual name of the plugin, the family, to allow for plugins' classifications,
 * the author's name, the URL for additional information about the plugin, or where it was
 * downloaded from in the first place, a default description, localized descriptions,
 * a version number (which for now is only for information purposes since no dependency mechanism
 * is built in Puzzles, maybe it'll come later when we have something up and running) and finally
 * a collection of public resources' names (that is, resources that are accessible from the outside
 * of a PluginRegistry), a list of jar files' names to instanciate the plugin's class, and the name
 * of the plugin's main class to be instanciated. Note that the main class needs to have a no argument
 * constructor.
 *
 * @author souk
 * @version 0.1
 */
public class PluginDescriptor implements Serializable {
    private final String name;
    private final String family;
    private final String author;
    private final String url;
    private final Map<Locale, String> descriptions;
    private final String defaultDescription;
    private final String version;
    private final Collection<String> publicResources;
    private final Collection<String> jars;
    private final String mainClass;

    public PluginDescriptor(String name, String family, String author, String url, Map<Locale, String> descriptions, String defaultDescription, String version, Collection<String> publicResources, Collection<String> jars, String mainClass) {
        this.name = name;
        this.family = family;
        this.author = author;
        this.url = url;
        this.descriptions = descriptions;
        this.defaultDescription = defaultDescription;
        this.version = version;
        this.publicResources = Collections.unmodifiableCollection(publicResources);
        this.jars = Collections.unmodifiableCollection(jars);
        this.mainClass = mainClass;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription(Locale locale) {
        if (descriptions==null) return defaultDescription;
        String description = descriptions.get(locale);
        if (description == null) {
            return defaultDescription;
        } else {
            return description;
        }
    }

    public Collection<String> getPublicResources() {
        return publicResources;
    }

    public Collection<String> getJars() {
        return jars;
    }

    public String getMainClass() {
        return mainClass;
    }

    public boolean isPublicResource(String resourceName) {
        return publicResources.contains(resourceName);
    }

    public Set<Locale> getAvailableLocales(){
        if (descriptions==null) {
            return new HashSet<Locale>();//would returning null be better ?
        }else{
            return descriptions.keySet();
        }
    }
}
