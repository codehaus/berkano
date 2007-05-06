<#assign ww=JspTaglibs["/WEB-INF/lib/webwork-2.1.7.jar"] />

<#macro showProps value>
<#if value?is_enumerable>
  <#list value as v>${v}<#if v_has_next>, </#if></#list>
<#else />
  ${value}
</#if>
</#macro>

<div id="berkano-user-mgt">
<div id="berkano-usermgt-nav">
<ul>
  <li><a href="user.list.action"><@ww.text name="'menu.users'"/></a></li>
  <li><a href="group.list.action"><@ww.text name="'menu.groups'"/></a></li>
</ul>
</div>
