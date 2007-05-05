<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="../../../../../../target/classes/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
%>
<b:beandisplay bean="<%=someObject%>" label="Some Object">
 <b:property name="someProperty"/>
 <b:property name="anotherProperty" label="Another Property"/>
 <b:property name="integerProperty"/>
</b:beandisplay>
