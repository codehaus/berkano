<%@ page import="net.incongru.swaf.bookmarks.BookmarksTree,
                 net.incongru.swaf.bookmarks.BookmarksTreeWriter,
                 net.incongru.swaf.bookmarks.HtmlListBookmarksWriter"%>
<%@taglib uri="webwork" prefix="ww" %>
<pre>
---------------------------------------
<ww:action name="'bookmarks.self'" id="bm"/>
<ww:set name="tree" value="#bm.selfBookmarks" scope="page"/>
<%
    BookmarksTree bookmarks = (BookmarksTree) pageContext.getAttribute("tree");
    out.print(bookmarks);
    out.print(">-------------------------------<");
    BookmarksTreeWriter w = new HtmlListBookmarksWriter();
    w.write(bookmarks, out);
%>
---------------------------------------
</pre>
