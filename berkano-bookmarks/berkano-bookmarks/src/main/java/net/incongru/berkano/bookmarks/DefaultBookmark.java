package net.incongru.berkano.bookmarks;

/**
 *
 * @todo do we want to validate url? Maybe that can be done in a DAO or Factory class
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class DefaultBookmark implements Bookmark {
    private int id;
    private String url;
    private String name;
    private String description;
    private String highlightMatchingExpression;

    public DefaultBookmark(int id, String url, String name, String description, String highlightMatchingExpression) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.description = description;
        this.highlightMatchingExpression = highlightMatchingExpression;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getHighlightMatchingExpression() {
        return highlightMatchingExpression;
    }

}
