package net.incongru.taskman.def.xstream;

import net.incongru.taskman.AbstractTaskManTestCase;
import net.incongru.taskman.def.TaskDef;

import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class XStreamTaskDefParserTest extends AbstractTaskManTestCase {
    public void testReadAndWrite() throws IOException {
        final TaskDef taskDefImpl = getDummyTaskDef();

        new XStreamTaskDefParser(null).storeTaskDef(taskDefImpl, "/tmp/foo.bar");

        final FileInputStream in = new FileInputStream("/tmp/foo.bar");
        final TaskDef taskDef = new XStreamTaskDefParser(in).loadTaskDesk();

        assertEquals(taskDefImpl, taskDef);

        //assertTrue(new File("/tmp/foo.bar").delete());
    }
}
