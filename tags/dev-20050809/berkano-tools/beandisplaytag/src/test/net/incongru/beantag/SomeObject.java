package net.incongru.beantag;

public class SomeObject {
    private String someProperty;
    private String anotherProperty;
    private int integerProperty;
    private String unknownProperty;

    public SomeObject(String someProperty, String anotherProperty, int integerProperty) {
        this.someProperty = someProperty;
        this.anotherProperty = anotherProperty;
        this.integerProperty = integerProperty;
        this.unknownProperty = "Unknown Value";
    }

    public String getSomeProperty() {
        return someProperty;
    }

    public String getAnotherProperty() {
        return anotherProperty;
    }

    public int getIntegerProperty() {
        return integerProperty;
    }

    public String getUnknownProperty() {
        return unknownProperty;
    }

    public String getBar() {
        return "bar";
    }
}
