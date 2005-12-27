<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ page import="java.util.Enumeration"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/styles.css" type="text/css"/>
</head>
<body>

<%
/*    User user = (User) session.getAttribute("seraph_defaultauthenticator_user");
    BookmarksTree bookmarks;
    if (user != null) {
        bookmarks = (BookmarksTree) user.getPropertySet().getObject("berkano.bookmarks");
    } else {
        bookmarks = (BookmarksTree) application.getAttribute("berkano.default.bookmarks");
    }
    if (bookmarks == null) {
        bookmarks = new BookmarksTree();
        bookmarks.add(new DefaultBookmark(1, "http://www.google.com", "Google", "Google, search engine"), null);
        Bookmark bands = new DefaultBookmark(2, "http://www.allmusic.com", "All music", "All music");
        bookmarks.add(bands, null);
        bookmarks.add(new DefaultBookmark(3, "http://www.flexalyndo.net", "Flexa Lyndo", "Flexa Lyndo"), bands);
        bookmarks.add(new DefaultBookmark(4, "http://www.elliottsmith.net", "Elliott Smith", "Elliott Smith"), bands);
    }
*/
    /*
    BookmarksTree bookmarks = new UserBookmarksAccessor().getSelfBookmarks(session);

    BookmarksTreeWriter w = new HtmlListBookmarksWriter();
    w.write(bookmarks, out);
*/
%>
<hr/>
<decorator:body />
<hr/>
<h2>session</h2>
<ul>
<%
    Enumeration sen = session.getAttributeNames();
    while (sen.hasMoreElements()) {
        String n = (String) sen.nextElement();
        %><li><%=n%> : <%=session.getAttribute(n)%></li><%
    }
%>
</ul>

</body>
</html>
