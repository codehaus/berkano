package net.incongru.swaf.profile;

import com.opensymphony.webwork.views.jsp.ui.AbstractListTag;
import com.opensymphony.webwork.views.jsp.ui.CheckboxListTag;
import com.opensymphony.webwork.views.jsp.ui.RadioTag;
import com.opensymphony.webwork.views.jsp.ui.SelectTag;
import com.opensymphony.webwork.views.jsp.ui.TextFieldTag;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.TextProvider;
import com.opensymphony.xwork.util.OgnlValueStack;
import net.incongru.swaf.app.PreferenceInfo;
import net.incongru.swaf.app.PreferenceWidget;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class PreferenceTag extends AbstractListTag {
    private static final Map templatesMap = new HashMap();

    static {
        templatesMap.put(PreferenceWidget.checkboxes, CheckboxListTag.TEMPLATE);
        templatesMap.put(PreferenceWidget.dropdown, SelectTag.TEMPLATE);
        templatesMap.put(PreferenceWidget.radios, RadioTag.TEMPLATE);
        templatesMap.put(PreferenceWidget.text, TextFieldTag.TEMPLATE);
    }

    private PreferenceInfo preferenceInfo;

    public void evaluateParams(OgnlValueStack stack) {
        Object o = ActionContext.getContext().getValueStack().peek();
        if (!(o instanceof PreferenceInfo)) {
            throw new IllegalStateException("There should be a PreferenceInfo on top of the value stack!!");
        }
        preferenceInfo = (PreferenceInfo) o;
        setList("allowedValues");
        setName("keyName");
        String translatedPrefLabel = translate(findString("keyName"), stack);
        setLabel("'" + translatedPrefLabel + "'");
        super.evaluateParams(stack);
    }

    private String translate(String key, OgnlValueStack stack) {
        Iterator it = stack.getRoot().iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof TextProvider) {
                TextProvider tp = (TextProvider) o;
                return tp.getText(key, key, null, stack);
            }
        }
        return key;
    }

    protected String getDefaultTemplate() {
        if (preferenceInfo == null) {
            throw new IllegalStateException("PreferenceInfo should have been set!");
        }
        PreferenceWidget widget = preferenceInfo.getPreferenceWidget();
        return (String) templatesMap.get(widget);
    }
}
