<%@ page import="com.opensymphony.user.User,
                 net.incongru.berkano.bookmarks.BookmarksTree,
                 net.incongru.berkano.bookmarks.BookmarksTreeWriter,
                 net.incongru.berkano.bookmarks.HtmlListBookmarksWriter,
                 net.incongru.berkano.bookmarks.ExternalBookmark,
                 net.incongru.berkano.bookmarks.Bookmark"%>
<%@ taglib uri="webwork" prefix="ww" %>

< w w : bean name="'webwork.util.Counter'" id="year">
  < w w : param name="'first'" value="text('firstBirthYear')"/>
  < w w : param name="'last'" value="2000"/>

  < w w : combobox label="'Birth year'" size="6" maxlength="4" name="'birthYear'" list="#year"/>
< / ww:bean>


<%
    User user = (User) session.getAttribute("seraph_defaultauthenticator_user");
    if (user == null) {
        throw new ServletException("no user found");
    }
    BookmarksTree bookmarks = (BookmarksTree) user.getPropertySet().getObject("berkano.bookmarks");
    if (bookmarks == null) {
        throw new ServletException("no bookmarks found for user " + user);
    }

    String actionTemplate = " (<a href=\"bookmark.up.action?userName=greg&bookmarkId={0}\">up</a> " +
            "/ <a href=\"bookmark.down.action?userName=greg&bookmarkId={0}\">down</a>" +
            "/ move this <input type=\"radio\" name=\"bookmarkToMove\" value=\"{0}\">" +
            "/ new parent <input type=\"radio\" name=\"newParentBookmarkId\" value=\"{0}\">" +
            ")";
    HtmlListBookmarksWriter bookmarksWriter = new HtmlListBookmarksWriter();//actionTemplate);
%>

<ww:url value="bookmark"/>

<h2>Move bookmarks around:</h2>
<form action="bookmark.changeparent.action" method="post">
 <input type="hidden" name="userName" value="greg">
<%
    bookmarksWriter.write(bookmarks, out);
%>
 <input type="submit" value="Move Bookmark">
</form>

<h2>Add bookmark:</h2>
<form action="user.addbookmark.action" method="post">
 <input type="hidden" name="userName" value="greg">
 id: <input name="bookmarkId"><br/>
 url: <input name="url"><br/>
 desc: <input name="description"><br/>
 longDesc: <input name="longDescription"><br/>
 <input type="submit" value="Add Bookmark">
</form>
