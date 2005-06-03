<%@ taglib prefix="s" uri="/target/classes/META-INF/taglib.tld"%>

Wrong order, throws exception:
<s:numericoptions end="2" start="6"/>

<!--select> JspException ~~> normal, end is not set
<-s:numericoptions/>
<-s:numericoptions end="-10" selected="-5"/>

<!--select> JspException ~~> normal, step can't be 0
<-s:numericoptions end="23" start="3" step="0"/>
</select>
<hr/-->


