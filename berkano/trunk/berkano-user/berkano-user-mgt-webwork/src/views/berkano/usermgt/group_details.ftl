<div id="berkano-user-mgt">
<#include "nav.ftl">

<h1>View Group : $group.groupName</h1>

<h2>Details</h2>
<ul>
 <li>Name: $group.groupName</li>
## <li>Class: $group.class</li>
</ul>

<h2>Roles</h2>
#bodytag(Form "action='group.roles.action'" "method='post'")
  #tag(Hidden "value=group.groupId" "name='groupId'")
  #tag(CheckboxList "label='Roles assigned to this group'" "name='roleNames'" "value=group.roles.{name}" "list=allRoles" "listKey=name" "listValue=name" )
  #tag(Submit "value='Send Form'")
#end

<h2>$group.users.size() user(s) in group $group.groupName</h2>
<ol>
#foreach ($user in $group.users)
    <li><a href="user.view.action?userId=$!user.userId">$user.userName</a></li>
#end
</ol>

<h2>Properties</h2>
<table>
#foreach ($prop in ${group.properties.entrySet()})
    <tr>
        <td>$prop.key</td>
        <td>$prop.value</td>
        <td>[<a href="group.delproperty.action?groupId=$group.groupId&propertyKey=$webwork.urlEncode($prop.key)">Del</a>]</td>
    </tr>
#end
</table>
<hr/>

<h2>Add Property</h2>
<form action="group.addproperty.action" method="post">
<input type="hidden" name="groupId" value="$group.groupId">
Key: <input name="propertyKey" type="text"><br />
Value: <input name="newPropertyValue" type="text"><br />
<input type="submit" name="propertyAdd" value="Add Property">
</form>

</div>