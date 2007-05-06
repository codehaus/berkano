package net.incongru.beantag;

/**
 * @author gjoseph
 * @author $Author: gj $ (last edit)
 * @version $Revision: 1.6 $
 */
class Property {
    private Object value;
    private String propertyName;
    private String label;
    private String rowClass;
    private String rowStyle;
    private String labelClass;
    private String labelStyle;
    private String valueClass;
    private String valueStyle;
    private String condition;

    Property(Object value, String propertyName, String label, String rowClass, String rowStyle, String condition) {
        this.value = value;
        this.propertyName = propertyName;
        this.label = label;
        this.rowClass = rowClass;
        this.rowStyle = rowStyle;
        this.condition = condition;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRowClass() {
        return rowClass;
    }

    public void setRowClass(String rowClass) {
        this.rowClass = rowClass;
    }

    public String getRowStyle() {
        return rowStyle;
    }

    public void setRowStyle(String rowStyle) {
        this.rowStyle = rowStyle;
    }

    public String getLabelClass() {
        return labelClass;
    }

    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }

    public String getLabelStyle() {
        return labelStyle;
    }

    public void setLabelStyle(String labelStyle) {
        this.labelStyle = labelStyle;
    }

    public String getValueClass() {
        return valueClass;
    }

    public void setValueClass(String valueClass) {
        this.valueClass = valueClass;
    }

    public String getValueStyle() {
        return valueStyle;
    }

    public void setValueStyle(String valueStyle) {
        this.valueStyle = valueStyle;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String toString() {
        return "Property[" + value + "/" + label + "]";
    }
}
