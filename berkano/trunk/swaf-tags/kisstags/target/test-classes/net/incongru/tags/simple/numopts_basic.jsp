<%@ taglib prefix="s" uri="/target/classes/META-INF/taglib.tld"%>
Basic, just end attribute:
<s:numericoptions end="5"/>
Assert counter is reset:
<s:numericoptions end="14"/>
Use start attribute:
<s:numericoptions start="2" end="6"/>
Start and end are equals:
<s:numericoptions end="3" start="3"/>
