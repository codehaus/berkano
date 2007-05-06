<#include "header.ftl">

<h1><@ww.text name="groupdetails.title"><@ww.param value="group.groupName"/></@ww.text></h1>

<h2><@ww.text name="groupdetails.members.title"/></h2>
<p><@ww.text name="groupdetails.members.count"><@ww.param value="group.users.size()"/></@ww.text></p>
<ul>
<#list group.users as user>
    <li><a href="user.view.action?userId=${user.userId}">${user.userName}</a> (#${user.userId})</li>
</#list>
</ul>

<h2><@ww.text name="groupdetails.roles.title"/></h2>
<form action="group.roles.action" method="post">
  <fieldset id="berkano-roles">
  <@ww.hidden name="groupId" value="group.groupId"/>
  <@ww.checkboxlist label="%{getText('groupdetails.roles.label')}" name="roleNames" id="roleNames" value="group.roles.{name}" list="allRoles" listKey="name" listValue="name" /><br />
  <@ww.submit value="%{getText('groupdetails.roles.submit')}" />
  </fieldset>
</form>

<h2><@ww.text name="groupdetails.properties.title"/></h2>
<table class="berkano-properties">
    <tr>
        <th><@ww.text name="groupdetails.properties.name"/></th>
        <th><@ww.text name="groupdetails.properties.value"/></th>
        <th><@ww.text name="groupdetails.properties.actions"/></th>
    </tr>
  <#assign props=group.properties />
  <#list props.keySet() as propKey>
    <tr>
      <td>${propKey}</td>
      <td><@showProps props[propKey] /></td>
      <!--TODO : url encoding with ?url-->
      <td><a href="group.delproperty.action?groupId=${group.groupId}&propertyKey=${propKey}"><@ww.text name="groupdetails.properties.actions.delete"/></a></td>
    </tr>
  </#list>
</table>

<h2><@ww.text name="groupdetails.property.add"><@ww.param value="group.groupName"/></@ww.text></h2>
<form action="group.addproperty.action" method="post">
<fieldset>
<@ww.hidden name="groupId" value="group.groupId"/>
<p><@ww.text name="groupdetails.property.add.hint"/></p>
<@ww.textfield label="%{getText('groupdetails.property.add.name')}" name="propertyKey" id="propertyKey" /><br />
<@ww.textfield label="%{getText('groupdetails.property.add.value')}" name="newPropertyValue" id="newPropertyValue" /><br />
<@ww.submit value="%{getText('groupdetails.property.add.submit')}" />
</fieldset>
</form>

<#include "footer.ftl">
