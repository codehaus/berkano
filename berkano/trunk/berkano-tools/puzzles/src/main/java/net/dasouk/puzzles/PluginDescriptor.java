package net.dasouk.puzzles;

import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * @todo (I know...still a lot to do >_<
 */
public class PluginDescriptor implements Serializable {
    private final String name;
    private final String author;
    private final URL url;
    private final Map<Locale,String> descriptions;
    private final String defaultDescription;
    private final String version;
    private Collection<String> publicResources;
    private Collection<String> jars;

    public PluginDescriptor(String name, String author, URL url, Map<Locale, String> descriptions, String defaultDescription, String version, Collection<String> publicResources, Collection<String> jars) {
        this.name = name;
        this.author = author;
        this.url = url;
        this.descriptions = descriptions;
        this.defaultDescription = defaultDescription;
        this.version = version;
        this.publicResources = Collections.unmodifiableCollection(publicResources);
        this.jars = Collections.unmodifiableCollection(jars);
    }

    public String getName() {
        return name;
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

    public String getDescription(Locale locale){
        String description = descriptions.get(locale);
        if (description == null){
            return defaultDescription;
        }else{
            return description;
        }
    }

    public Collection<String> getPublicResources() {
        return publicResources;
    }

    public Collection<String> getJars() {
        return jars;
    }

    public boolean hasPublicResource(String resourceName){
        return true;
    }
}
