package net.incongru.util.webwork;

import com.opensymphony.xwork.ActionContext;
import net.incongru.util.mail.AbstractSimpleMailLocalizer;

import java.util.Locale;

/**
 * A simple {@link net.incongru.util.mail.MailLocalizer} which returns the
 * Locale from Webwork's {@link ActionContext}.
 *
 * Note that this doesn't use Webwork's  translation system, it just uses
 * a simple ResourceBundle based translation (for titles).
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class WebworkMailLocalizer extends AbstractSimpleMailLocalizer {
    public WebworkMailLocalizer(String bundleName) {
        super(bundleName);
    }

    public Locale resolveLocale() {
        return ActionContext.getContext().getLocale();
    }
}
