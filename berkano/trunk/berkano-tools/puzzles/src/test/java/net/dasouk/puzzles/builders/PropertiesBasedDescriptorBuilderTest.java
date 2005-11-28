package net.dasouk.puzzles.builders;

import junit.framework.TestCase;
import net.dasouk.puzzles.PluginDescriptor;
import net.dasouk.puzzles.PluginDescriptorException;

import java.net.URL;
import java.util.Collection;

public class PropertiesBasedDescriptorBuilderTest extends TestCase {

    public void testBuildDescriptor() {
        final PropertiesBasedDescriptorBuilder builder = new PropertiesBasedDescriptorBuilder();
        URL propertiesUrl = getClass().getResource("/net/dasouk/puzzles/builders/descriptor.properties");
        try {
            PluginDescriptor descriptor = builder.buildDescriptor(propertiesUrl);
            assertEquals("name", descriptor.getName());
            assertEquals("family", descriptor.getFamily());
            assertEquals("souk", descriptor.getAuthor());
            assertEquals("http://dasouk.free.fr/puzzles/plugins/family/name", descriptor.getUrl());
            assertEquals("this is the description", descriptor.getDefaultDescription());
            assertEquals("1.0-SNAPSHOT", descriptor.getVersion());
            assertEquals("net.dasouk.puzzles.example.Something", descriptor.getMainClass());
            final Collection<String> jars = descriptor.getJars();
            assertTrue(jars.contains("lib/dep1.jar"));
            assertTrue(jars.contains("lib/dep2.jar"));
            assertTrue(jars.contains("lib/popo.zip"));
            assertEquals(3, jars.size());
            final Collection<String> res = descriptor.getPublicResources();
            assertTrue(res.contains("images/logo.png"));
            assertTrue(res.contains("sounds/various/couak.wav"));
            assertEquals(2, res.size());
        } catch (PluginDescriptorException e) {
            fail("nope");
        }
    }
}
