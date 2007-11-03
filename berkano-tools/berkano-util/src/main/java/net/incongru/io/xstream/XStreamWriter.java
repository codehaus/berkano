package net.incongru.io.xstream;

import com.thoughtworks.xstream.XStream;
import net.incongru.io.IOProcessor;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class XStreamWriter<T> implements IOProcessor<Writer> {
    private final XStream xstream;
    private final T objectToStore;

    public XStreamWriter(XStream xstream, T objectToStore) {
        this.xstream = xstream;
        this.objectToStore = objectToStore;
    }

    public void process(Writer flow) throws IOException {
        xstream.toXML(objectToStore, flow);
    }
}
