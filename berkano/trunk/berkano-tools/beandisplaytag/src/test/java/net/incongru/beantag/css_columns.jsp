<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="../../../../../../target/xdoclet/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object" style="some style" cssclass="some cssclass" id="someid"
        valueclass="the-val" valuestyle="the-val-style" keyclass="the-key" keystyle="the-key-style">
 <b:property name="someProperty" style="pouet" />
 <b:property name="anotherProperty" label="Another Property" cssclass="tralala"/>
 <b:property name="integerProperty"/>
</b:beandisplay>
