<%@ page import="net.incongru.beantag.SomeObject"%>
<%@ taglib prefix="b" uri="../../../../../../target/classes/META-INF/taglib.tld"%>
<%
SomeObject someObject = new SomeObject("SomeValue", "Other Value", 123);
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

<b:beandisplay name="test.obj" label="Some Object With Dynamic Style">
 <b:property name="someProperty" test="integerProperty==123"/>
 <b:property name="anotherProperty" label="Another Property" test="anotherProperty=='ThisValue'"/>
 <b:property name="integerProperty" dynClass="integerProperty==123?'foo':'bar'"/>
 <b:property name="integerProperty" test="2==5" dynClass="integerProperty==123?'hahaha':'ohohoh'"/>
 <b:property name="integerProperty" test="5==5" dynClass="integerProperty!=123?'hahaha':'ohohoh'"/>
 <b:property name="someProperty" dynClass="'yop_'+someProperty"/>
</b:beandisplay>
