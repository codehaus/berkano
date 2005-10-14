<#assign ww=JspTaglibs["/WEB-INF/lib/webwork-2.1.7.jar"] />

<div id="berkano-profile">

<h1><@ww.text name="'profile.form.title'"/></h1>
<form action="profile.save.action" method="post">
<fieldset>
<@ww.textfield label="getText('profile.form.fullname')" name="'fullName'" id="fullName" value="fullName" /><br />
<@ww.textfield label="getText('profile.form.email')" name="'email'" id="fullName" value="email" /><br />

<p><@ww.text name="'profile.form.password.hint'"/></p>
<@ww.password label="getText('profile.form.password')" name="'password'" id="password" /><br />
<@ww.password label="getText('profile.form.password.confirm')" name="'passwordConfirm'" id="passwordConfirm" /><br />

    <div class="form-buttons">
    <@ww.submit value="getText('profile.form.submit')" />
    </div>
</fieldset>
</form>

</div>
