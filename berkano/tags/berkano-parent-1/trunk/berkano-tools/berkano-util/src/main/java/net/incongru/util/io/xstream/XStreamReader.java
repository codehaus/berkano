package net.incongru.util.io.xstream;

import com.thoughtworks.xstream.XStream;
import net.incongru.util.io.IOProcessor;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class XStreamReader<T> implements IOProcessor<Reader> {
    private final XStream xstream;
    private T result;

    public XStreamReader(XStream xstream) {
        this.xstream = xstream;
    }

    public void process(Reader in) throws IOException {
        result = (T) xstream.fromXML(in);
    }

    public T getResult() {
        return result;
    }
}
