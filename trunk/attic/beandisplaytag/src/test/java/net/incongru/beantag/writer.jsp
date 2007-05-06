<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="../../../../../../target/classes/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object" writer="net.incongru.beantag.FakeTableWriter">
 <b:property name="someProperty"/>
 <b:property name="anotherProperty" label="Another Property"/>
 <b:property name="integerProperty"/>
</b:beandisplay>
