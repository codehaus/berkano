<#include "header.ftl">

<h1><@ww.text name="'usergroups.title'"><@ww.param value="user.userName"/></@ww.text></h1>

<form action="usergroups.edit.action" method="post">
<input type="hidden" name="userId" value="${user.userId}">

<table>
<tr>
  <th><@ww.text name="'usergroups.availablegroups'"/></th>
  <th/>
  <th><@ww.text name="'usergroups.memberofgroups'"/></th>
</tr>
<tr>
  <td>
    <select name="groupsToAdd" size="7" multiple>
    <#list availableGroups as group>
        <option value="${group.groupId}">(${group.groupId}) ${group.groupName}</option>
    </#list>
    </select>
  </td>
  <td>
    <input type="submit" name="add" value="&gt;&gt;">
    <br />
    <input type="submit" name="remove" value="&lt;&lt;">
  </td>
  <td>
    <select name="groupsToRemove" size="7" multiple>
    <#list memberOfGroups as group>
      <option value="${group.groupId}">(${group.groupId}) ${group.groupName}</option>
    </#list>
    </select>
  </td>
</tr>
</table>

</form>

<p><a href="user.view.action?userId=${user.userId}"><@ww.text name="'usergroups.backtouserdetails'"/></a>.</p>

<#include "footer.ftl">
