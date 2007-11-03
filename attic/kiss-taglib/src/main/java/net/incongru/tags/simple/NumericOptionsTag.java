package net.incongru.tags.simple;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * This tag generates <option> html (xhtml compliant) tags with numeric
 * values and content, from a given number to a given number, with a given step.
 * Example:
 * <option value="3">3</option>
 * <option value="5">5</option>
 * <option value="7">7</option>
 * would be generated from <k:numoptions start="3" end="8" step="2"/>
 * One can also select a number of values using the selected attribute:
 * <k:numoptions start="3" end="9" step="2" selected="5;7"/> would generate
 * <option value="3">3</option>
 * <option value="5" selected="selected">5</option>
 * <option value="7" selected="selected">7</option>
 * <option value="9">9</option>
 * All three start, end and step attributes can take negative values, but end must
 * either be equals to start (in which cas there will only be one <option> tag output)
 * or bigger/equals than start+step
 *
 * TODO : move to SimpleTag(jsp2)
 *
 * @jsp.tag name="numericoptions"
 *          body-content="empty"
 *
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.2 $
 */
public class NumericOptionsTag extends TagSupport {
    private int start = 0;
    private int end = -1;
    private int step = 1;
    private String selectedStr = null;
    private IntArray selected = null;

    /**
     * @jsp.attribute required="false" rtexprvalue="true" type="int"
     *                description="default value is 0."
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @jsp.attribute required="true" rtexprvalue="true" type="int"
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * @jsp.attribute required="false" rtexprvalue="true" type="int"
     *                description="default value is 1. can not be 0."
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * @jsp.attribute required="false" rtexprvalue="true" type="java.lang.String"
     *                description="default is none."
     */
    public void setSelected(String selectedStr) {
        this.selectedStr = selectedStr;
    }

    /**
     * TODO: avoid the last \n ?
     * TODO: move attribute values check outside this? (can JspException be thrown by the set methods?)
     *
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        // check correctness of "end" attribute
        if ((end < start && step > 0) || (end > start && step < 0) && end != start) {
            throw new JspException("The end attribute must be set to a correct value, regarding step and start attributes(start=" + start + ", end=" + end + ", step=" + step + ").");
        }
        if (step == 0) {
            throw new JspException("The step attribute can't be set to 0 (start=" + start + ", end=" + end + ", step=" + step + ").");
        }

        // parse the selected values
        try {
            if (selectedStr != null)
                selected = parseCommaSeparatedValues(selectedStr);
        } catch (NumberFormatException ex) {
            throw new JspException("The selected values can not be parsed: " + ex.getMessage() + "(attribute value is \"" + selectedStr + "\").");
        }

        try {
            JspWriter out = pageContext.getOut();

            // loop through values and print the <option> tags
            int i = start;
            while ((step > 0 && i <= end) || (step < 0 && i >= end)) {
                out.write("<option value=\"" + i + "\"");
                if (selected != null && selected.contains(i)) {
                    out.write(" selected=\"selected\"");
                }
                out.write(">" + i + "</option>\n");

                i += step;
            }
        } catch (IOException ex) {
            throw new JspException("Could not output: " + ex.getMessage());
        }

        return SKIP_BODY;
    }

    /**
     * Parses a String that contains a list of comma separated integers and
     * returns an array with these integers.
     * Accepted "comma" characters include , ; and space
     *
     * Todo: was in berkano.util. Move this back to a berkano-commons package?
     *
     * @param s the string to parse
     * @return an array of int's
     */
    private int[] parseCommaSeparatedValuesAsArray(String s) throws NumberFormatException {
        return parseCommaSeparatedValues(s).toArray();
    }

    public static IntArray parseCommaSeparatedValues(String s)
            throws NumberFormatException {
        IntArray arr = new IntArray();
        char[] chars = s.toCharArray();
        StringBuffer tmp = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ',' && chars[i] != ';' && chars[i] != ' ') {
                tmp.append(chars[i]);
            } else if (tmp.length() > 0) {
                arr.add(Integer.parseInt(tmp.toString()));
                tmp.setLength(0);
            }
        }
        if (tmp.length() > 0) {
            arr.add(Integer.parseInt(tmp.toString()));
        }

        return arr;
    }
}
