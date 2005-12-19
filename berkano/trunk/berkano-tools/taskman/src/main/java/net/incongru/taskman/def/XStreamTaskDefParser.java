package net.incongru.taskman.def;

import com.thoughtworks.xstream.XStream;
import net.incongru.util.io.SmartStreamProcessor;
import net.incongru.util.io.StreamProcessor;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author greg
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class XStreamTaskDefParser implements TaskDefParser {
    private InputStream in;

    public XStreamTaskDefParser(InputStream in) {
        this.in = in;
    }

    public TaskDef loadTaskDesk() {
        final XStream xStream = getXStream();
        return (TaskDef) xStream.fromXML(new InputStreamReader(in));
    }

    public void storeTaskDef(final TaskDef taskDef, String fullPathForOutput) throws IOException {
        final FileWriter out = new FileWriter(fullPathForOutput);
        new SmartStreamProcessor().processFlushAndClose(out, new StreamProcessor() {
            public void process(final Closeable stream) throws IOException {
                final XStream xStream = getXStream();
                xStream.toXML(taskDef, out);
            }
        });
    }

    private XStream getXStream() {
        final XStream xStream = new XStream();
        xStream.alias("TaskDef", TaskDefImpl.class);
        return xStream;
    }
}
