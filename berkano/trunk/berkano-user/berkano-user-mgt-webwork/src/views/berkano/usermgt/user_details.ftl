<#assign userGroups=user.groups />

<#include "header.ftl">

<h1>User: ${user.userName}</h1>

<h2>Details</h2>
<ul>
  <li>Name: ${user.userName}</li>
  <li>Full Name: ${user.fullName}</li>
  <li>Email: ${user.email}</li>
</ul>

<h2>Groups</h2>
<p>${userGroups.size()} groups</p>

<ul>
<#list userGroups as group>
   <li><a href="group.view.action?groupId=${group.groupId}">(${group.groupId}) ${group.groupName}</a></li>
</#list>
</ul>

<p><a href="usergroups.view.action?userId=${user.userId}">Add / Edit Groups</a></p>

<h2>Properties</h2>
<table>
    <tr>
        <th colspan="2">Origin</th>
        <th>Property name</th>
        <th>Value</th>
        <th>Actions</th>
    </tr>

<#assign props=user.properties />
<#list props?keys as propKey>
    <tr>
        <td colspan="2">user</td>
        <td>${propKey}</td>
        <td><@showProps props[propKey] /></td>
        <!--TODO : url encoding with ?url-->
        <td>[<a href="user.delproperty.action?userId=${user.userId}&propertyKey=${propKey}">Del</a>]</td>
    </tr>
</#list>
<#list userGroups as group>
  <#assign props=group.properties />
  <#list props?keys as propKey>
    <tr>
        <td>group</td>
        <td>${group.groupName}</td>
        <td>${propKey}</td>
        <td><@showProps props[propKey] /></td>
        <!--TODO : url encoding with ?url-->
        <td>[<a href="user.delproperty.action?userId=${user.userId}&propertyKey=${propKey}">Del</a>]</td>
    </tr>
  </#list>
</#list>
</table>

<h2>Add Property (strings only...)</h2>
<form action="user.addproperty.action" method="post">
<input type="hidden" name="userId" value="${user.userId}">
Key: <input name="propertyKey" type="text"><br/>
Value: <input name="newPropertyValue" type="text"><br/>
<input type="submit" name="propertyAdd" value="Add Property"/>
</form>

<#include "footer.ftl">
