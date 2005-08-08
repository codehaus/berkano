<#assign ww=JspTaglibs["/WEB-INF/lib/webwork-2.1.7.jar"] />

<div id="berkano-preferences">
  <h1><@ww.text name="'preferences.title'"/></h1>

  <div id="berkano-preferences-apps">
    <ul>
    <@ww.iterator value="apps">
      <li><a href="?app=${name}">${action.getTranslatedAppName(name)}</a></li>
    </@ww.iterator>
    </ul>
  </div>

<#if app.name?exists>
  <div id="berkano-preferences-prefs" class="prefs-${app.name}">
    <@ww.form action="'preferences.save.action'" method="'post'">
    <fieldset>
    <@ww.iterator value="app.preferencesInfo">
    <#switch preferenceWidget.name>
    <#case 'dropdown'>
      <@ww.select name="keyName" id="keyName" list="allowedValues" label="getText(keyName)"/>
      <br />
      <#break>
    <#case 'radios'>
      radios to be implemented
      <br />
      <#break>
    <#case 'checkboxes'>
      checkboxes to be implemented
      <br />
      <#break>
    <#case 'text'>
      <@ww.textfield name="keyName" id="keyName" label="getText(keyName)"/><br />
      <br />
      <#break>
    <#default>
      should not be here !
      <br />
    </#switch>
    </@ww.iterator>

    <@ww.hidden name="'app'" value="app.name"/>
    <div class="form-buttons">
    <@ww.submit value="getText('preferences.form.submit')" />
    </div>
    </fieldset>
    </@ww.form>
  </div>
</#if>
</div>
