<div id="berkano-user-mgt">
<#include "nav.ftl">

<h1>Edit groups for user: $user.userName</h1>

<form action="usergroups.edit.action" method="post">
<input type="hidden" name="userId" value="$user.userId">

<table>
<tr>
  <th>Other Groups</th>
  <th/>
  <th>Member Of</th>
</tr>
<tr>
  <td>
    <select name="groupsToAdd" size="7" multiple>
    #foreach ($group in $allGroups)
        #if (!$user.groups.contains($group))
            <option value="$group.groupId">($group.groupId) $group.groupName</option>
        #end
    #end
    </select>
  </td>
  <td>
    <input type="submit" name="add" value="&gt;&gt;">
    <br />
    <input type="submit" name="remove" value="&lt;&lt;">
  </td>
  <td>
    <select name="groupsToRemove" size="7" multiple>
    #foreach ($group in $user.groups)
      <option value="$group.groupId">($group.groupId) $group.groupName</option>
    #end
    </select>
  </td>
</tr>
</table>

</form>

<p><a href="user.view.action?userId=$user.userId">back to user details</a>.</p>

</div>