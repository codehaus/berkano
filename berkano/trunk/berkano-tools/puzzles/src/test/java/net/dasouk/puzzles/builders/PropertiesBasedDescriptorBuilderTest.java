package net.dasouk.puzzles.builders;

import net.dasouk.puzzles.PluginDescriptor;
import net.dasouk.puzzles.PluginDescriptorException;

import java.util.Collection;
import java.util.HashSet;
import java.net.URL;
import java.net.MalformedURLException;

import junit.framework.TestCase;


public class PropertiesBasedDescriptorBuilderTest extends TestCase {
    private PropertiesBasedDescriptorBuilder builder;

    public void testBuildDescriptor(){
        builder = new PropertiesBasedDescriptorBuilder();
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL propertiesUrl = classLoader.getResource("net/dasouk/puzzles/builders/descriptor.properties");
        try {
            PluginDescriptor descriptor = builder.buildDescriptor(propertiesUrl);
            assertEquals("name",descriptor.getName());
            assertEquals("family",descriptor.getFamily());
            assertEquals("souk",descriptor.getAuthor());
            assertEquals("http://dasouk.free.fr/puzzles/plugins/family/name",descriptor.getUrl().toString());
            assertEquals("this is the description",descriptor.getDefaultDescription());
            assertEquals("1.0-SNAPSHOT",descriptor.getVersion());
            assertEquals("net.dasouk.puzzles.example.Something",descriptor.getMainClass());
            Collection<String> col = descriptor.getJars();
            assertTrue(col.contains("lib/dep1.jar"));
            assertTrue(col.contains("lib/dep2.jar"));
            assertTrue(col.contains("lib/popo.zip"));
            col = descriptor.getPublicResources();
            assertTrue(col.contains("images/logo.png"));
            assertTrue(col.contains("sounds/various/couak.wav"));
        } catch (PluginDescriptorException e) {
            fail("nope");
        } 
    }
}
