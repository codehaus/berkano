package net.incongru.swaf;

import com.opensymphony.xwork.ActionSupport;

/**
 * 
 * @author greg
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.1 $
 */
public class DummyAction extends ActionSupport {
    private String bar;

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String execute() throws Exception {
        System.out.println("bar = " + bar);
        return super.execute();
    }
}
