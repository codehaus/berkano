<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="../../../../../../target/xdoclet/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object" style="some_style" cssclass="some_cssclass" id="someid">
 <b:property name="someProperty" style="pouet" />
 <b:property name="anotherProperty" label="Another Property" cssclass="tralala"/>
 <b:property name="integerProperty"/>
</b:beandisplay>
