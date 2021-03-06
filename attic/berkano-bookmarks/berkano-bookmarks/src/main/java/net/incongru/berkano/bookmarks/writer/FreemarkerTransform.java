package net.incongru.berkano.bookmarks.writer;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;
import net.incongru.berkano.bookmarks.BookmarksTree;
import net.incongru.berkano.bookmarks.MenuTranslator;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class FreemarkerTransform implements TemplateTransformModel {
    private final MenuTranslator translator;

    public FreemarkerTransform(MenuTranslator translator) {
        this.translator = translator;
    }

    public Writer getWriter(Writer out, Map args) throws TemplateModelException, IOException {
        if (args == null || args.size() < 1 || args.size() > 3) {
            throw new TemplateModelException("supported args: bookmarks(mandatory), indent-start(optional) and indent-end(optional)");
        }
        BookmarksTree bookmarks = (BookmarksTree) args.get("bookmarks");
        if (bookmarks == null) {
            throw new TemplateModelException("bookmarks argument is mandatory");
        }
        String indentStart = (String) args.get("indent-start");
        String indentEnd = (String) args.get("indent-end");
        //todo have a maxLevel arg
        return new FreemarkerBookmarksTreeWriter(translator, bookmarks, indentStart, indentEnd, out);
    }

    private static class FreemarkerBookmarksTreeWriter extends Writer {
        private final MenuTranslator translator;
        private BookmarksTree bookmarks;
        private String indentStart;
        private String indentEnd;
        private Writer out;
        private StringBuffer buff;

        public FreemarkerBookmarksTreeWriter(MenuTranslator translator, BookmarksTree bookmarks, String indentStart, String indentEnd, Writer out) {
            this.translator = translator;
            this.bookmarks = bookmarks;
            this.indentStart = indentStart;
            this.indentEnd = indentEnd;
            this.out = out;
            this.buff = new StringBuffer();
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
            //buff.append(cbuf, off, len);
        }

        public void flush() throws IOException {
            // todo : stuff the tree in here using the buff as the template
            //out.write(buff.toString());
            HtmlListBookmarksWriter bookmarksWriter = new HtmlListBookmarksWriter(translator);
            bookmarksWriter.write(bookmarks, out);
            out.flush();
        }

        public void close() throws IOException {
        }

    }
}
