package net.incongru.taskman.def.xstream;

import com.thoughtworks.xstream.XStream;
import net.incongru.taskman.TaskEvent;
import net.incongru.taskman.def.TaskDef;
import net.incongru.taskman.def.TaskDefImpl;
import net.incongru.taskman.def.TaskDefParser;
import net.incongru.util.io.Flusher;
import net.incongru.util.io.IOProcessor;

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
        new Flusher<FileWriter>().processFlushAndClose(out, new IOProcessor<FileWriter>() {
            public void process(final FileWriter c) {
                final XStream xStream = getXStream();
                xStream.toXML(taskDef, out);
            }
        });
    }

    private XStream getXStream() {
        final XStream xStream = new XStream();
        xStream.registerConverter(new PeriodConverter());
        xStream.alias("TaskDef", TaskDefImpl.class);
        xStream.alias("TaskEvent", TaskEvent.class);
        return xStream;
    }
}
