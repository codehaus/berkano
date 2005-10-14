<#include "header.ftl">

<h1>View Group : ${group.groupName}</h1>

<h2>${group.users?size} user(s) in group ${group.groupName}</h2>
<ol>
<#list group.users as user>
    <li><a href="user.view.action?userId=${user.userId}">${user.userName}</a></li>
</#list>
</ol>

<h2>Roles</h2>
<form action="group.roles.action" method="post">
  <@ww.hidden name="'groupId'" value="group.groupId"/>
  <@ww.checkboxlist label="getText('usermgt.form.group.roles')" name="'roleNames'" value="group.roles.{name}" list="allRoles" listKey="name" listValue="name" /><br />

  <input type="submit" value="Send Form">
</form>

<h2>Properties</h2>
<table>
  <#assign props=group.properties />
  <#list props?keys as propKey>
    <tr>
      <td>${propKey}</td>
      <td><@showProps props[propKey] /></td>
      <!--TODO : url encoding with ?url-->
      <td>[<a href="group.delproperty.action?groupId=${group.groupId}&propertyKey=${propKey}">Del</a>]</td>
    </tr>
  </#list>
</table>
<hr/>

<h2>Add Property</h2>
<form action="group.addproperty.action" method="post">
<input type="hidden" name="groupId" value="${group.groupId}">
Key: <input name="propertyKey" type="text"><br />
Value: <input name="newPropertyValue" type="text"><br />
<input type="submit" name="propertyAdd" value="Add Property">
</form>

<#include "footer.ftl">
