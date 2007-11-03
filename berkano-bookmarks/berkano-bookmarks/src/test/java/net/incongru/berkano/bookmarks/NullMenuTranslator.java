package net.incongru.berkano.bookmarks;

/**
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $ 
 */
public class NullMenuTranslator implements MenuTranslator {
    public String translate(String key) {
        return key;
    }
}
