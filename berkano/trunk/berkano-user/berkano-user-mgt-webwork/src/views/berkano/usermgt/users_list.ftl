<div id="berkano-user-mgt">
<#include "nav.ftl">

<h1>Users</h1>

<ol>
<#list allUsers as user>
  <li>${user.userName} (${user.email})
        [<a href="user.view.action?userId=${user.userId}">View</a>
        | <a href="user.edit.action?userId=${user.userId}">Edit</a>
        | <a href="usergroups.view.action?userId=${user.userId}">Groups</a>
        | <a href="user.remove.action?userId=${user.userId}">Del</a>]
  </li>
</#list>
</ol>

<p><a href="user.new.action">New user</a></p>

</div>
