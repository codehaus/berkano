package net.incongru.berkano.bookmarks.reader;

import net.incongru.berkano.bookmarks.Bookmark;
import net.incongru.berkano.bookmarks.BookmarksTree;
import net.incongru.berkano.bookmarks.DefaultBookmark;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class XmlBookmarksTreeReader implements BookmarksTreeReader {
    private final Document doc;

    public XmlBookmarksTreeReader(InputStream in) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        this.doc = db.parse(in);
    }

    public BookmarksTree readBookmarksTree() {
        BookmarksTree tree = new BookmarksTree();
        Element root = (Element) doc.getFirstChild();
        addChildren(root, tree, null);
        return tree;

    }

    private void addChildren(Element element, BookmarksTree tree, Bookmark parent) {
        NodeList nodeList = element.getChildNodes();//ElementsByTagName("bookmark");
        for (int size = nodeList.getLength(), i = 0; i < size; i++) {
            if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element node = (Element) nodeList.item(i);
            int id = Integer.parseInt(node.getAttribute("id"));
            String href = node.getAttribute("href");
            String name = node.getAttribute("name");
            String highlight = node.getAttribute("highlight");
            String desc = node.getTextContent();
            if (desc != null) {
                desc = desc.trim();
            }
            if (desc != null && desc.trim().length() == 0) {
                desc = null;
            }
            Bookmark bookmark = new DefaultBookmark(id, href, name, desc, highlight);
            tree.add(bookmark, parent);
            addChildren(node, tree, bookmark);
        }
    }
}
