<div id="berkano-user-mgt">
<#include "nav.ftl">

#if( $actionErrors.size() > 0 )
  <ul class="error">
  #foreach( $error in $actionErrors )
    <li>$error</li>
  #end
  </ul>
#end

<h1>Add/Edit user</h1>
<form action="user.save.action" method="post">
<table>
#tag( Hidden "name='userId'" "value=userId")<br />
#tag( TextField "label='User Name'" "name='userName'" "value=userName")<br />
#tag( TextField "label='Full Name'" "name='fullName'" "value=fullName")<br />
#tag( TextField "label='Email'" "name='email'" "value=email")<br />

#if ($user)
<tr><td colspan="2">Don't fill these fields if you don't want to change the user's password:</td></tr>
#end
#tag( Password "label='Password'" "name='password'")<br />
#tag( Password "label='Confirm Password'" "name='passwordConfirm'")<br />

<tr><th colspan="2"><input type="submit" value="Save User"></th></tr>
</table>
</form>

</div>
