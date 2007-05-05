package net.incongru.berkano.usermgt.webwork.lostpw;

import com.opensymphony.xwork.ActionSupport;
import net.incongru.berkano.security.password.PasswordRetrievalStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * Returns a view corresponding to the field required by the configured
 * {@link PasswordRetrievalStrategy}.
 *
 * @see PasswordRetrievalStrategy
 *
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class FormAction extends ActionSupport {
    private static final List SUPPORTED_FIELDS = Arrays.asList(new String[]{"username", "email"});

    private PasswordRetrievalStrategy passwordRetrievalStrategy;

    public FormAction(PasswordRetrievalStrategy passwordRetrievalStrategy) {
        this.passwordRetrievalStrategy = passwordRetrievalStrategy;
    }

    //TODO : write test
    public String execute() throws Exception {
        String fieldName = passwordRetrievalStrategy.getRequiredField();
        if (SUPPORTED_FIELDS.contains(fieldName)) {
            return fieldName;
        }
        return ERROR;
    }
}
