<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="/target/xdoclet/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" bean="<%=someObject%>" label="Some Object">
 <b:property name="someProperty"/>
 <b:property name="anotherProperty" label="Another Property"/>
 <b:property name="integerProperty"/>
</b:beandisplay>
