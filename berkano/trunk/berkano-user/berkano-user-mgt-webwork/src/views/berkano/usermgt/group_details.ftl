<#include "header.ftl">

<h1><@ww.text name="'groupdetails.title'"><@ww.param value="group.groupName"/></@ww.text></h1>

<h2><@ww.text name="'groupdetails.members.title'"/></h2>
<p><@ww.text name="'groupdetails.members.count'"><@ww.param value="group.users.size()"/></@ww.text></p>
<ol>
<#list group.users as user>
    <li><a href="user.view.action?userId=${user.userId}">${user.userName}</a></li>
</#list>
</ol>

<h2><@ww.text name="'groupdetails.roles.title'"/></h2>
<form action="group.roles.action" method="post">
  <fieldset>
  <@ww.hidden name="'groupId'" value="group.groupId"/>
  <@ww.checkboxlist label="getText('groupdetails.roles.label')" name="'roleNames'" value="group.roles.{name}" list="allRoles" listKey="name" listValue="name" /><br />
  <@ww.submit value="getText('groupdetails.roles.submit')" />
  </fieldset>
</form>

<h2><@ww.text name="'groupdetails.properties.title'"/></h2>
<table class="berkano-properties">
    <tr>
        <th><@ww.text name="'groupdetails.properties.name'"/></th>
        <th><@ww.text name="'groupdetails.properties.value'"/></th>
        <th><@ww.text name="'groupdetails.properties.actions'"/></th>
    </tr>
  <#assign props=group.properties />
  <#list props?keys as propKey>
    <tr>
      <td>${propKey}</td>
      <td><@showProps props[propKey] /></td>
      <!--TODO : url encoding with ?url-->
      <td>[<a href="group.delproperty.action?groupId=${group.groupId}&propertyKey=${propKey}"><@ww.text name="'groupdetails.properties.actions.del'"/></a>]</td>
    </tr>
  </#list>
</table>
<hr/>

<h2><@ww.text name="'groupdetails.property.add'"><@ww.param value="group.groupName"/></@ww.text></h2>
<form action="group.addproperty.action" method="post">
<fieldset>
<input type="hidden" name="groupId" value="${group.groupId}">
<@ww.text name="'groupdetails.property.add.name'"/>: <input name="propertyKey" type="text"><br/>
<@ww.text name="'groupdetails.property.add.value'"/>: <input name="newPropertyValue" type="text"><br/>
<@ww.submit value="getText('groupdetails.property.add.submit')" />
</fieldset>
</form>

<#include "footer.ftl">
