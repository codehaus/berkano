<div id="berkano-lostpassword">
<p><@ww.text name="lostpw.username.explain"/></p>
<form action="retrieve.action" method="get">
<fieldset>
    <label for="lostpw-userName"><@ww.text name="lostpw.username.form.username" /> :</label>
    <input id="lostpw-userName" type="text" name="userName"/>
    <br />

    <div class="form-buttons">
    <@ww.submit value="%{getText('lostpw.username.form.submit')}" />
    </div>
</fieldset>
</form>
</div>
