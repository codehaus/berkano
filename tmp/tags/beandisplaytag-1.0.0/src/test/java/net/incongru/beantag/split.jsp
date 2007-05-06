<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ taglib prefix="b" uri="../../../../../../target/classes/META-INF/taglib.tld"%>
<%
    Map map = new HashMap();
    map.put("someProperty", "Some Value");
    map.put("anotherProperty", "Other Value");
    map.put("bar", "bar");
    map.put("za", "A");
    map.put("zb", "B");
    map.put("zc", "C");
    map.put("zd", "D");
pageContext.setAttribute("test.obj.map", map);
%>
<b:beandisplay name="test.obj.map" label="Some Map" id="foo" split="3"/>
