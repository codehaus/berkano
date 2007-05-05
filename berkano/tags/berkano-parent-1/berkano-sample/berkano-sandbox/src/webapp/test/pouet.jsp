<%@ page import="net.incongru.berkano.bookmarks.BookmarksTree,
                 net.incongru.berkano.bookmarks.BookmarksTreeWriter,
                 net.incongru.berkano.bookmarks.writer.HtmlListBookmarksWriter"%>
<%@ page import="net.incongru.berkano.bookmarks.model.BookmarksTree"%>
<%@ page import="net.incongru.berkano.bookmarks.writer.BookmarksTreeWriter"%>
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
