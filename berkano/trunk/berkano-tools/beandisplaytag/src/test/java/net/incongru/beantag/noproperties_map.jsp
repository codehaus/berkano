<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ taglib prefix="b" uri="../../../../../../target/xdoclet/META-INF/taglib.tld"%>
<%
    Map map = new HashMap();
    map.put("someProperty", "Some Value");
    map.put("anotherProperty", "Other Value");
    map.put("bar", "bar");
pageContext.setAttribute("test.obj.map", map);
%>
<b:beandisplay name="test.obj.map" label="Some Map"/>
