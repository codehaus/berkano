<#include "header.ftl">

<h1><@ww.text name="'userlist.title'"/></h1>

<ol>
<#list allUsers as user>
  <li>${user.userName} (${user.email})
        [<a href="user.view.action?userId=${user.userId}"><@ww.text name="'userlist.link.view'"/></a>
        | <a href="user.edit.action?userId=${user.userId}"><@ww.text name="'userlist.link.edit'"/></a>
        | <a href="usergroups.view.action?userId=${user.userId}"><@ww.text name="'userlist.link.groups'"/></a>
        | <a href="user.remove.action?userId=${user.userId}"><@ww.text name="'userlist.link.delete'"/></a>]
  </li>
</#list>
</ol>

<p><a href="user.new.action"><@ww.text name="'userlist.link.newuser'"/></a></p>

<#include "footer.ftl">
