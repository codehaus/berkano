package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * A Plugin descriptor contains the metadata of the plugin.
 * The actual name of the plugin, the family, to allow for plugins' classifications,
 * the author's name, the URL for additional information about the plugin, or where it was
 * downloaded from in the first place, a default description, localized descriptions,
 * a version number (which for now is only for information purposes since no dependency mechanism
 * is built in Puzzles, maybe it'll come later when we have something up and running) and finally
 * a collection of public resources' names (that is, resources that are accessible from the outside
 * of a PluginRegistry), a list of jar files' names to instanciate the plugin's class, and the name
 * and type of the plugin's blueprints (the type is here to indicate which PluginBuilder is to be used
 * in case one chooses to allow for multiple types of instanciations: some plugin may only require a simple
 * reflection powered instanciation while others may require much more powerfull mechanisms such as Spring's
 * or hivemind's etc)
 *
 * @author souk
 * @version 0.1
 */
public class PluginDescriptor implements Serializable {
    private final String name;
    private final String family;
    private final String author;
    private final URL url;
    private final Map<Locale, String> descriptions;
    private final String defaultDescription;
    private final String version;
    private final Collection<String> publicResources;
    private final Collection<String> jars;
    private final String bluePrintsName;
    private final String type;

    public PluginDescriptor(String name, String family, String author, URL url, Map<Locale, String> descriptions, String defaultDescription, String version, Collection<String> publicResources, Collection<String> jars, String bluePrintsName, String type) {
        this.name = name;
        this.family = family;
        this.author = author;
        this.url = url;
        this.descriptions = descriptions;
        this.defaultDescription = defaultDescription;
        this.version = version;
        this.publicResources = Collections.unmodifiableCollection(publicResources);
        this.jars = Collections.unmodifiableCollection(jars);
        this.bluePrintsName = bluePrintsName;
        this.type = type;
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

    public URL getUrl() {
        return url;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription(Locale locale) {
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

    public String getBluePrintsName() {
        return bluePrintsName;
    }

    public String getType() {
        return type;
    }

    public boolean isPublicResource(String resourceName) {
        return publicResources.contains(resourceName);
    }
}
