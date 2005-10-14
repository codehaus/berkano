<#include "header.ftl">

<h1>Groups</h1>

<ol>
<#list allGroups as group>
    <li>
      ${group.groupName}
      [<a href="group.view.action?groupId=${group.groupId}">View</a>
      | <a href="group.remove.action?groupId=${group.groupId}">Del</a>]
    </li>
</#list>
</ol>

<hr>

<h2>Add group</h2>
<form action="group.add.action">
<table>
<tr><td>Name: </td><td><input type="text" name="groupName"></td></tr>
<tr><td colspan="2"><input type="submit" name="addGroup" value="Add group"></td></tr>
</table>
</form>

<#include "footer.ftl">
