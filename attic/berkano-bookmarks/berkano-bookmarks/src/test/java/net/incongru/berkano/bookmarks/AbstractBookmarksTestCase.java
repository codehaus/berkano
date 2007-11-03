package net.incongru.berkano.bookmarks;

import junit.framework.TestCase;

/**
 *
 * @author gjoseph
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
        b1 = new DummyBookmark(1, "url1", "name1");
        b2 = new DummyBookmark(2, "url2", "name2");
        b3 = new DummyBookmark(3, "url3", "name3", "and a desc for link 3");
        b4 = new DummyBookmark(4, "url4", "name4");
        b5 = new DummyBookmark(5, "url5", "name5");
        b6 = new DummyBookmark(6, "url6", "name6");
        b7 = new DummyBookmark(7, "url7", "name7");
        notadded = new DummyBookmark(8, "notadded-url", "notadded");
        notadded2 = new DummyBookmark(9, "notadded2-url", "notadded2");
        dir = new DummyBookmark(10, null, "dir");
        subdir = new DummyBookmark(11, null, "subdir");
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
        private int id;
        private String link;
        private String name;
        private String desc;

        public DummyBookmark(int id, String link, String name) {
            this.id = id;
            this.link = link;
            this.name = name;
        }

        public DummyBookmark(int id, String link, String name, String desc) {
            this.id = id;
            this.link = link;
            this.name = name;
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public String getLink() {
            return link;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return desc;
        }

        public String getHighlightMatchingExpression() {
            return null;
        }
    }
}
