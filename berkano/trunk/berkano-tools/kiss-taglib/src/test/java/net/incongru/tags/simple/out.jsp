<%@ taglib prefix="s" uri="../../../../../../../target/xdoclet/META-INF/taglib.tld"%>
<%
  application.setAttribute("test", new String("test@application"));
  session.setAttribute("test", new String("test@session"));
  request.setAttribute("test", new String("test@request"));
  pageContext.setAttribute("test", new String("test@page"));
  request.setAttribute("test-null", null);
%>
Simple:
<s:out key="test"/>
With scope:
<s:out key="test" scope="page"/>
<s:out key="test" scope="request"/>
<s:out key="test" scope="session"/>
<s:out key="test" scope="application"/>
Nulls:
-<s:out key="test-null"/>-
-<s:out key="test-null" displayNull="true"/>-
