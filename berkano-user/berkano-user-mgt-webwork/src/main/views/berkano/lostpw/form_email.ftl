<div id="berkano-lostpassword">
<p><@ww.text name="lostpw.email.explain"/></p>
<form action="retrieve.action" method="get">
<fieldset>
    <label for="lostpw-email"><@ww.text name="lostpw.email.form.email" /> :</label>
    <input id="lostpw-email" type="text" name="email"/>
    <br />

    <div class="form-buttons">
    <@ww.submit value="%{getText('lostpw.email.form.submit')}" />
    </div>
</fieldset>
</form>
</div>
