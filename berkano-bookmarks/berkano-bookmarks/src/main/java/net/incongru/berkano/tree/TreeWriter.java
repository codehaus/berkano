package net.incongru.berkano.tree;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public interface TreeWriter {
    public void write(Tree tree, Writer writer) throws IOException;
}
