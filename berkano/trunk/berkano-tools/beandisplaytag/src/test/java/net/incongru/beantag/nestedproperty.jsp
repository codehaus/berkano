<%@ page import="net.incongru.beantag.SomeOtherObject"%>
<%@ taglib prefix="b" uri="../../../../../../target/xdoclet/META-INF/taglib.tld"%>
<%
SomeOtherObject someObject = new SomeOtherObject();
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object">
 <b:property name="one"/>
 <b:property name="two"/>
 <b:property name="nestedObject.someProperty" label="nested str"/>
 <b:property name="nestedObject.anotherProperty"/>
 <b:property name="nestedObject.integerProperty" label="nested int"/>
</b:beandisplay>
