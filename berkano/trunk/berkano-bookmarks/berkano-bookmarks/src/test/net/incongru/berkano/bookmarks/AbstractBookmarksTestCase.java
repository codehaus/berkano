package net.incongru.berkano.bookmarks;

import junit.framework.TestCase;

/**
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public abstract class AbstractBookmarksTestCase extends TestCase {
    protected Bookmark b1;
    protected Bookmark b2;
    protected Bookmark b3;
    protected Bookmark b4;
    protected Bookmark b5;
    protected Bookmark b6;
    protected Bookmark b7;
    protected Bookmark dir;
    protected Bookmark subdir;
    protected Bookmark notadded;
    protected Bookmark notadded2;

    protected void setUp() throws Exception {
        b1 = new DummyBookmark("url1", "name1");
        b2 = new DummyBookmark("url2", "name2");
        b3 = new DummyBookmark("url3", "name3");
        b4 = new DummyBookmark("url4", "name4");
        b5 = new DummyBookmark("url5", "name5");
        b6 = new DummyBookmark("url6", "name6");
        b7 = new DummyBookmark("url7", "name7");
        notadded = new DummyBookmark("notadded-url", "notadded");
        notadded2 = new DummyBookmark("notadded2-url", "notadded2");
        dir = new DummyBookmark(null, "dir");
        subdir = new DummyBookmark(null, "subdir");
    }

    protected BookmarksTree setupSimpleTree() {
        BookmarksTree t = new BookmarksTree();
        t.add(b1, null);
        t.add(b2, null);
        t.add(dir, null);
        t.add(b3, dir);
        t.add(subdir, dir);
        t.add(b4, subdir);
        t.add(b5, subdir);
        t.add(b6, dir);
        t.add(b7, null);

        return t;
    }

    private class DummyBookmark implements Bookmark {
        private String link;
        private String name;

        public DummyBookmark(String link, String name) {
            this.link = link;
            this.name = name;
        }

        public int getId() {
            return -1;
        }

        public String getLink() {
            return link;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return null;
        }
    }
}
