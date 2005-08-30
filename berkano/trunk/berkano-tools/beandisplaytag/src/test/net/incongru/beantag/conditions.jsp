<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="/target/xdoclet/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("Some Value", "Other Value", 123);
pageContext.setAttribute("test.obj", someObject);
%>
<b:beandisplay name="test.obj" label="Some Object">
 <b:property name="someProperty"/>
 <b:property name="anotherProperty" label="Another Property"/>
 <b:property name="integerProperty" test="3 > 5"/>
</b:beandisplay>

<b:beandisplay name="test.obj" label="Some Object">
 <b:property name="someProperty" test="2+2==4"/>
 <b:property name="anotherProperty" label="Another Property" test="2+2==5"/>
 <b:property name="integerProperty"/>
</b:beandisplay>

<b:beandisplay name="test.obj" label="Some Object">
 <b:property name="someProperty" test="integerProperty==123"/>
 <b:property name="anotherProperty" label="Another Property" test="anotherProperty=='ThisValue'"/>
 <b:property name="integerProperty"/>
</b:beandisplay>

<b:beandisplay name="test.obj" label="Some Object With Conditional Style">
 <b:property name="someProperty" test="integerProperty==123"/>
 <b:property name="anotherProperty" label="Another Property" test="anotherProperty=='ThisValue'"/>
 <b:property name="integerProperty" test="integerProperty==123" conditionalClass="foo"/>
</b:beandisplay>
