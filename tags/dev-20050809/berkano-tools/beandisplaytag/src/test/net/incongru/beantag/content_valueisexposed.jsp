<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="/target/classes/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object">
 <%-- check we wan use jsp el syntax--%>
 <b:property label="blah">${4*3}</b:property>
 <b:property name="someProperty" label="Seaume Preaupertie" ref="pouet">**${pouet}¤¤</b:property>
</b:beandisplay>
