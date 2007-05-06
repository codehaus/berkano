<div id="berkano-profile">

<h1><@ww.text name="profile.form.title"/></h1>
<form action="profile.save.action" method="post">
<fieldset>
<@ww.textfield label="%{getText('profile.form.fullname')}" name="fullName" id="fullName" /><br />
<@ww.textfield label="%{getText('profile.form.email')}" name="email" id="fullName" /><br />

<p><@ww.text name="profile.form.password.hint"/></p>
<@ww.password label="%{getText('profile.form.password')}" name="password" id="password" /><br />
<@ww.password label="%{getText('profile.form.password.confirm')}" name="passwordConfirm" id="passwordConfirm" /><br />

    <div class="form-buttons">
    <@ww.submit value="%{getText('profile.form.submit')}" />
    </div>
</fieldset>
</form>

</div>
