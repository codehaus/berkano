<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="/target/classes/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object">
 <b:property name="someProperty"/>
 <b:property name="anotherProperty"/>
 <b:property name="integerProperty"/>
</b:beandisplay>
<b:beandisplay name="test.obj">
 <b:property name="integerProperty"/>
 <b:property name="anotherProperty"/>
</b:beandisplay>
