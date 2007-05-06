package net.dasouk.puzzles;

import java.io.Serializable;
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
        if (name == null || name.trim().equals(""))
            throw new IllegalArgumentException("the property name of the plugin descriptor cannot be empty");
        if (family == null || family.trim().equals(""))
            throw new IllegalArgumentException("the property family of the plugin descriptor cannot be empty");
        this.name = name;
        this.family = family;
        this.author = author;
        this.url = url;
        this.descriptions = descriptions;
        this.defaultDescription = defaultDescription;
        this.version = version;
        if (publicResources != null) {
            this.publicResources = Collections.unmodifiableCollection(publicResources);
        } else {
            this.publicResources = new HashSet<String>();
        }
        if (jars != null) {
            this.jars = Collections.unmodifiableCollection(jars);
        } else {
            this.jars = new HashSet<String>();
        }
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
        if (descriptions == null) return defaultDescription;
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

    public Set<Locale> getAvailableLocales() {
        if (descriptions == null) {
            return new HashSet<Locale>();//would returning null be better ?
        } else {
            return descriptions.keySet();
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PluginDescriptor that = (PluginDescriptor) o;

        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (!family.equals(that.family)) return false;
        if (mainClass != null ? !mainClass.equals(that.mainClass) : that.mainClass != null) return false;
        if (!name.equals(that.name)) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = name.hashCode();
        result = 29 * result + family.hashCode();
        return result;
    }
}
