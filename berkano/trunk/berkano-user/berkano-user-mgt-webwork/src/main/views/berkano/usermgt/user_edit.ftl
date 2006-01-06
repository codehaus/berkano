<#include "header.ftl">

<#if (actionErrors?size > 0) >
  <ul class="error">
  <#list actionErrors as error>
    <li>${error}</li>
  </#list>
  </ul>
</#if>

<#if user?exists>
  <h1><@ww.text name="useredit.title"><@ww.param value="userName"/></@ww.text></h1>
<#else/>
  <h1><@ww.text name="useredit.new.title"/></h1>
</#if>

<form action="user.save.action" method="post">
<fieldset>
<@ww.hidden name="userId"/>
<@ww.textfield label="%{getText('useredit.username')}" name="userName" id="userName"/><br />
<@ww.textfield label="%{getText('useredit.fullname')}" name="fullName" id="fullName"/><br />
<@ww.textfield label="%{getText('useredit.email')}" name="email" id="email"/><br />

<#if user?exists>
  <p><@ww.text name="useredit.password.edithint"/></p>
</#if>
<@ww.password label="%{getText('useredit.password')}" name="password" id="password" /><br />
<@ww.password label="%{getText('useredit.password.confirm')}" name="passwordConfirm" id="passwordConfirm" /><br />

<@ww.submit value="%{getText('useredit.submit')}" />

</fielset>
</form>

<#include "footer.ftl">
