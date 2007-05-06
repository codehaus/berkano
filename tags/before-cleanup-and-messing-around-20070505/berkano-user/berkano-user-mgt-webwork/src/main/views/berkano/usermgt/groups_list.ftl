<#include "header.ftl">

<h1><@ww.text name="grouplist.title"/></h1>

<ol>
<#list allGroups as group>
    <li>${group.groupName}
      [<a href="group.view.action?groupId=${group.groupId}"><@ww.text name="grouplist.link.view"/></a>
      | <a href="group.remove.action?groupId=${group.groupId}"><@ww.text name="grouplist.link.delete"/></a>]
    </li>
</#list>
</ol>

<hr>

<h2><@ww.text name="addgroup.title"/></h2>
<form action="group.add.action">
<fieldset>
<@ww.textfield label="%{getText('addgroup.name')}" name="groupName" id="groupName" /><br />
<@ww.submit value="%{getText('addgroup.submit')}" />
</fieldset>
</form>

<#include "footer.ftl">
