<%@ taglib uri="webwork" prefix="ww" %>
<%@ page import="com.atlassian.seraph.config.SecurityConfig,
                 com.atlassian.seraph.auth.AuthenticationContext,
                 net.incongru.swaf.user.User,
                 java.util.Iterator,
                 net.incongru.swaf.user.Group"%><html>
<head>
  <title>Simple jsp page</title>
</head>
<body>
test profile: <a href="profile/preferences.action">plop</a>
<hr/>

foo: <ww:property value="foo"/><br>
bar: <ww:property value="bar"/><br>
<hr>
  <p><a href="test/test.jsp">simple test</a></p>
  <p><a href="pouet.action">pouet</a></p>
  <p><a href="bookmarks/edit.jsp">edit bookmarks</a></p>
  <p><a href="login.action">l'in</a></p>
  <p><a href="logout">l'out</a></p>
  <p><a href="manager/user.list.action">manager</a></p>

  <h1>File Upload</h1>

<form action="upload.action" method="POST" enctype="multipart/form-data">
<input type="file" name="file" value="Browse..." size="50"/><br/>
<input type="file" name="file" value="Browse..." size="50"/>
<input type="submit" value="Submit">
</form>

</body>
</html>