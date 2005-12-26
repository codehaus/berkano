package net.incongru.berkano.bookmarks;

import java.io.Serializable;

/**
 * @todo For now, we only have internal (berkano only) and External bookmarks.
 * @todo We might be able to support other type of links in the future and
 * @todo it might make sense to have another type for theses (files to download, jnlp, email and what not)
 * @todo (in this case, external might be renamed to ExternalHTTP)
 * @todo Actually, we should decide on this quite quickly to avoid having to rename this in a production system
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public interface Bookmark extends Serializable {
    /**
     * @todo is it any useful? how unique should it be? 
     */
    public int getId();

    /**
     * @todo validate this method name: should it output "browser ready" link,
     * @todo or should we have a visitor which will manage the output of the link
     * @todo ... in which case the different implementations might be useless!?
     */
    public String getLink();

    /**
     * @todo we want i18n of bookmark names, so we might need to think about how to do that
     * @todo should each bookmark store its own translations, or should it be left to the
     * @todo web application layer to display these translated, and manage the translations itself?
     */
    public String getName();

    public String getDescription();

    /**
     * An expression which will be evaluated at runtime by an implementation of
     * {@link net.incongru.berkano.bookmarks.Highlighter} against the current url
     * or path, to check if we need to highlight this bookmark.
     */
    public String getHighlightMatchingExpression();

}
