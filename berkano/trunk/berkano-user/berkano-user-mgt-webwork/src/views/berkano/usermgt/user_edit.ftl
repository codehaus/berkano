<#include "header.ftl">

<#if (actionErrors?size > 0) >
  <ul class="error">
  <#list actionErrors as error>
    <li>${error}</li>
  </#list>
  </ul>
</#if>

<h1>Add/Edit user</h1>
<form action="user.save.action" method="post">
<table>
<@ww.hidden name="'userId'" value="userId"/>
<@ww.textfield label="getText('usermgt.form.username')" name="'userName'" id="userName" value="userName" /><br />
<@ww.textfield label="getText('usermgt.form.fullname')" name="'fullName'" id="fullName" value="fullName" /><br />
<@ww.textfield label="getText('usermgt.form.email')" name="'email'" id="email" value="email" /><br />

<#if user?exists>
<tr><td colspan="2">Don't fill these fields if you don't want to change the user's password:</td></tr>
</#if>
<@ww.password label="getText('usermgt.form.password')" name="'password'" id="password" /><br />
<@ww.password label="getText('usermgt.form.password.confirm')" name="'passwordConfirm'" id="passwordConfirm" /><br />

<tr><th colspan="2"><input type="submit" value="Save User"></th></tr>
</table>
</form>

<#include "footer.ftl">
