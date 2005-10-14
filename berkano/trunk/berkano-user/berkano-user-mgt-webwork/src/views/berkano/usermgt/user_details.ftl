<#assign userGroups=user.groups />

<#include "header.ftl">

<h1><@ww.text name="'userdetails.title'"><@ww.param value="user.userName"/></@ww.text></h1>

<h2><@ww.text name="'userdetails.details'"/></h2>
<ul>
  <li><@ww.text name="'userdetails.username'"/>: ${user.userName}</li>
  <li><@ww.text name="'userdetails.fullname'"/>: ${user.fullName}</li>
  <li><@ww.text name="'userdetails.email'"/>: ${user.email}</li>
</ul>

<h2><@ww.text name="'userdetails.groups'"><@ww.param value="user.userName"/></@ww.text></h2>
<p><@ww.text name="'userdetails.groupscount'"><@ww.param value="user.groups.size()"/></@ww.text></p>

<ul>
<#list userGroups as group>
   <li><a href="group.view.action?groupId=${group.groupId}">(#${group.groupId}) ${group.groupName}</a></li>
</#list>
</ul>

<p><a href="usergroups.view.action?userId=${user.userId}"><@ww.text name="'userdetails.groups.addeditlink'"/></a></p>

<h2><@ww.text name="'userdetails.properties'"><@ww.param value="user.userName"/></@ww.text></h2>
<table class="berkano-properties">
    <tr>
        <th colspan="2"><@ww.text name="'userdetails.properties.origin'"/></th>
        <th><@ww.text name="'userdetails.properties.name'"/></th>
        <th><@ww.text name="'userdetails.properties.value'"/></th>
        <th><@ww.text name="'userdetails.properties.actions'"/></th>
    </tr>

  <#assign props=user.properties />
  <#list props?keys as propKey>
      <tr>
          <td colspan="2">user</td>
          <td>${propKey}</td>
          <td><@showProps props[propKey] /></td>
          <!--TODO : url encoding with ?url-->
          <td>[<a href="user.delproperty.action?userId=${user.userId}&propertyKey=${propKey}"><@ww.text name="'userdetails.properties.actions.del'"/></a>]</td>
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
          <td>[<a href="user.delproperty.action?userId=${user.userId}&propertyKey=${propKey}"><@ww.text name="'userdetails.properties.actions.del'"/></a>]</td>
      </tr>
    </#list>
  </#list>
</table>

<h2><@ww.text name="'userdetails.property.add'"><@ww.param value="user.userName"/></@ww.text></h2>
<form action="user.addproperty.action" method="post">
<fieldset>
<input type="hidden" name="userId" value="${user.userId}">
<@ww.text name="'userdetails.property.add.name'"/>: <input name="propertyKey" type="text"><br/>
<@ww.text name="'userdetails.property.add.value'"/>: <input name="newPropertyValue" type="text"><br/>
<@ww.submit value="getText('userdetails.property.add.submit')" />
</fieldset>
</form>

<#include "footer.ftl">
