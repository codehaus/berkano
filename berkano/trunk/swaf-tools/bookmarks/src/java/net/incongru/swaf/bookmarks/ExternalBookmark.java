package net.incongru.swaf.bookmarks;

/**
 *
 * @todo do we want to validate url? Maybe that can be done in a DAO or Factory class
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class ExternalBookmark implements Bookmark {
    private int id;
    private String url;
    private String description;
    private String longDescription;

    public ExternalBookmark(int id, String url, String description, String longDescription) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.longDescription = longDescription;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getLongDescription() {
        return longDescription;
    }

}
