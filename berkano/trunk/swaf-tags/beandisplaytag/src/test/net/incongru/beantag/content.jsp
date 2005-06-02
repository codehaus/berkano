<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="/target/classes/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object">
 <b:property label="blah">some content</b:property>
 <b:property label="empty"></b:property>
 <b:property name="someProperty"/>
 <%--tr><td colspan="2">YOOHOO</td></tr--%>
 <b:property name="anotherProperty" label="Another Property"/>
</b:beandisplay>
