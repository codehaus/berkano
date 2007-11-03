package net.incongru.util.webwork;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import ognl.OgnlException;
import ognl.OgnlRuntime;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.4 $
 */
public class ModelUrlBuilder {
    private String urlBase;
    private Object model;

    public ModelUrlBuilder(String urlBase) {
        this.urlBase = urlBase;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public String buildUrl() throws OgnlException, UnsupportedEncodingException {
        StringBuffer result = new StringBuffer(urlBase);
        if (urlBase.indexOf('?') < 0) {
            result.append('?');
        } else {
            result.append('&');
        }

        OgnlValueStack stack = ActionContext.getContext().getValueStack();
        if (stack == null) {
            stack = new OgnlValueStack();
            ActionContext.getContext().setValueStack(stack);
        }
        stack.push(model);
        // let converters now where we are
        stack.getContext().put(ModelUrlBuilder.class, ModelUrlBuilder.class);

        Map fields = OgnlRuntime.getFields(model.getClass());
        Iterator it = fields.keySet().iterator();
        while (it.hasNext()) {
            String field = (String) it.next();
            // todo : understand where these fields are coming from - seems like the model is some sort of wrapper/proxy around the complete request?
            if (fields.get(field).getClass().equals(Object.class)) {
                continue;
            }
            String[] value = (String[]) stack.findValue(field, String[].class);
            if (value != null && value.length > 0) {
                for (int i = 0; i < value.length; i++) {
                    result.append(field);
                    result.append('=');
                    result.append(URLEncoder.encode(value[i], "UTF-8")); // todo : use type converters : well they're used...
                    if (i < value.length - 1) {
                        result.append('&');
                    }
                }
                if (it.hasNext()) {
                    result.append('&');
                }
            }
        }

        // todo : maybe this should happen in a finally block to avoid leaving the stack in a messed up state in case of exception?
        stack.pop();
        stack.getContext().remove(ModelUrlBuilder.class);

        return result.toString();
    }

}
