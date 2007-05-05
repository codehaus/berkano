package net.dasouk.puzzles.registries;

import net.dasouk.puzzles.PluginState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.Set;

/**
 * This plugin manager uses a simple flat file to persist its data.
 * The file format is simple:
 * <length of family name>;<length of plugin name>;<family name>;<plugin name>;<state as string>
 */
public class SimpleFlatFilePluginStateManager extends AbstractPluginStateManager {
    private final static String NEW_LINE = System.getProperty("line.separator");
    private File storeFile;

    public SimpleFlatFilePluginStateManager(File storeFile) throws Exception {
        this.storeFile = storeFile;
        if (storeFile.exists() && storeFile.isDirectory()) {
            throw new IllegalArgumentException(storeFile.getAbsolutePath() + " is a folder");
        }
        loadStates();
    }

    protected void loadStates() throws Exception {
        if (storeFile.exists()) {
            BufferedReader reader = null;

            try {
                reader = new BufferedReader(new FileReader(storeFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    DataLine dataLine = fromLine(line);
                    pluginStates.put(dataLine.getFamily(), dataLine.getName(), dataLine.getState());
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

        }
    }

    protected void persistStates() throws Exception {
        StringBuffer buffer = new StringBuffer();
        Set<String> families = pluginStates.firstLevelSet();
        for (String family : families) {
            Set<String> names = pluginStates.secondLevelSet(family);
            for (String name : names) {
                PluginState state = pluginStates.get(family, name);
                DataLine dataLine = new DataLine(family, name, state);
                buffer.append(toLine(dataLine)).append(NEW_LINE);
            }
        }
        String toDump = buffer.toString().trim();
        FileWriter writer = null;
        try {
            writer = new FileWriter(storeFile);
            writer.write(toDump);
            writer.flush();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }


    protected String toLine(DataLine dataLine) {
        return new StringBuffer().append(dataLine.getFamily().length())
                .append(";")
                .append(dataLine.getName().length())
                .append(";")
                .append(dataLine.getFamily())
                .append(";")
                .append(dataLine.getName())
                .append(";")
                .append(dataLine.getState().name()).toString();
    }

    protected DataLine fromLine(String line) throws ParseException {
        String[] parts = line.split(";", 3);
        if (parts.length != 3) {
            throw new ParseException("could not parse line", -1);
        }
        int familyLength = Integer.parseInt(parts[0]);
        int nameLength = Integer.parseInt(parts[1]);
        String family = parts[2].substring(0, familyLength);
        String name = parts[2].substring(familyLength + 1, familyLength + 1 + nameLength);
        PluginState state = PluginState.valueOf(parts[2].substring(familyLength + 1 + nameLength + 1));
        return new DataLine(family, name, state);
    }


    static class DataLine {
        final private String family;
        final private String name;
        final private PluginState state;

        public DataLine(String family, String name, PluginState state) {
            this.family = family;
            this.name = name;
            this.state = state;
        }

        public String getFamily() {
            return family;
        }

        public String getName() {
            return name;
        }

        public PluginState getState() {
            return state;
        }
    }
}
