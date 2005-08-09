package net.incongru.berkano.bookmarks;

import freemarker.template.TemplateModelException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

/**
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class FreemarkerTransformTest extends AbstractBookmarksTestCase {
    public void testStuffJustWorks() throws TemplateModelException, IOException {
        StringWriter out = new StringWriter();
        FreemarkerTransform freemarkerTransform = new FreemarkerTransform();
        HashMap args = new HashMap();
        args.put("bookmarks", setupSimpleTree());
        Writer transform = freemarkerTransform.getWriter(out, args);
        transform.write("hello");
        transform.flush();
        assertEquals("<ul>\n" +
                "<li><a href=\"url1\" title=\"name1\">name1</a></li>\n" +
                "<li><a href=\"url2\" title=\"name2\">name2</a></li>\n" +
                "<li><a href=\"null\" title=\"dir\">dir</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"url3\" title=\"name3\">name3</a></li>\n" +
                "<li><a href=\"null\" title=\"subdir\">subdir</a></li>\n" +
                "<ul>\n" +
                "<li><a href=\"url4\" title=\"name4\">name4</a></li>\n" +
                "<li><a href=\"url5\" title=\"name5\">name5</a></li>\n" +
                "</ul>\n" +
                "<li><a href=\"url6\" title=\"name6\">name6</a></li>\n" +
                "</ul>\n" +
                "<li><a href=\"url7\" title=\"name7\">name7</a></li>\n" +
                "</ul>\n", out.toString());
    }

    public void testShouldThrowTemplateModelExceptionWhenInvalidArgumentsCount() throws TemplateModelException, IOException {
        StringWriter out = new StringWriter();
        FreemarkerTransform freemarkerTransform = new FreemarkerTransform();
        HashMap args = new HashMap();
        try {
            freemarkerTransform.getWriter(out, args);
            fail();
        } catch (TemplateModelException e) {
            assertEquals("supported args: bookmarks(mandatory), indent-start(optional) and indent-end(optional)", e.getMessage());
        }

        try {
            freemarkerTransform.getWriter(out, null);
            fail();
        } catch (TemplateModelException e) {
            assertEquals("supported args: bookmarks(mandatory), indent-start(optional) and indent-end(optional)", e.getMessage());
        }

        args.put("A", "a");
        args.put("B", "a");
        args.put("C", "a");
        args.put("D", "a");
        try {
            freemarkerTransform.getWriter(out, args);
            fail();
        } catch (TemplateModelException e) {
            assertEquals("supported args: bookmarks(mandatory), indent-start(optional) and indent-end(optional)", e.getMessage());
        }
    }

    public void testShouldThrowTemplateModelExceptionWhenNoBookmarksArgument() throws IOException {
        FreemarkerTransform freemarkerTransform = new FreemarkerTransform();
        HashMap args = new HashMap();
        args.put("blah", "blah");
        try {
            freemarkerTransform.getWriter(new StringWriter(), args);
        } catch (TemplateModelException e) {
            assertEquals("bookmarks argument is mandatory", e.getMessage());
        }
    }
}
