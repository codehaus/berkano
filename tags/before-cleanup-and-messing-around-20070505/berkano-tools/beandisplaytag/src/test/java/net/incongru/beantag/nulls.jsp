<%@ page import="net.incongru.beantag.SomeOtherObject"%>
<%@ taglib prefix="b" uri="../../../../../../target/classes/META-INF/taglib.tld"%>
<%
SomeOtherObject soo = new SomeOtherObject();
pageContext.setAttribute("test.obj", soo);
%>
<b:beandisplay name="test.obj" label="S.O.O." shownulls="true">
 <b:property name="one"/>
 <b:property name="two"/>
</b:beandisplay>

<b:beandisplay name="test.obj" label="S.O.O." shownulls="skip">
 <b:property name="one"/>
 <b:property name="two"/>
</b:beandisplay>

<b:beandisplay name="test.obj" label="S.O.O." shownulls="false">
 <b:property name="one"/>
 <b:property name="two"/>
</b:beandisplay>

<b:beandisplay name="test.obj" label="default is to hide nulls">
 <b:property name="one"/>
 <b:property name="two"/>
</b:beandisplay>
