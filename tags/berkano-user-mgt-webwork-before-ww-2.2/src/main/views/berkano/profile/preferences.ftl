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
    <form action="preferences.save.action" method="post">
    <fieldset>
    <@ww.iterator value="app.preferencesInfo">
    <#switch preferenceWidget.name>
    <#case 'dropdown'>
      <@ww.select name="keyName" id="pref-${keyName}" list="allowedValues" listKey="key" listValue="getText(value)" label="getText(keyName)"/>
      <br />
      <#break>
    <#case 'combobox'>
      combobox support needs to be implemented !
      <br />
      <#break>
    <#case 'radios'>
      <@ww.radio name="keyName" id="pref-${keyName}" list="allowedValues" listKey="key" listValue="getText(value)" label="getText(keyName)"/>
      <br />
      <#break>
    <#case 'radios-and-text'>
      <div class="radios-and-text">
      <@ww.radio name="'${keyName}predefined'" id="pref-${keyName}-predefined" value="${keyName}" list="allowedValues" listKey="key" listValue="getText(value)" label="getText(keyName)" onclick="'${keyName}.readOnly=true;${keyName}.value=this.value'"/>
      <input type="radio" name="${keyName}predefined" value="" id="pref-${keyName}-custom" onclick="${keyName}.readOnly=false"/>
      <@ww.textfield name="keyName" id="pref-${keyName}" value="${keyName}"/>
      </div>
      <br />
      <#break>
    <#case 'checkboxes'>
      checkboxes support to be implemented !
      <br />
      <#break>
    <#case 'text'>
      <@ww.textfield name="keyName" id="pref-${keyName}" label="getText(keyName)" value="${keyName}"/>
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
    </form>
  </div>
</#if>
</div>
