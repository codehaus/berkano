package net.incongru.berkano.usermgt.webwork;

import com.opensymphony.xwork.ActionSupport;

/**
 * A dummy empty action used for view-only actions,
 * in order to keep the i18n happy. Yes, this is a hack.
 *
 * @author gjoseph
 * @author $Author: $ (last edit)
 * @version $Revision: $
 */
public class NullAction extends ActionSupport {
    public String execute() {
        return SUCCESS;
    }
}
