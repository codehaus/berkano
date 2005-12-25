<%@taglib uri="/target/classes/META-INF/taglib.tld" prefix="s"%>
Basic:
<s:numericoptions end="5" selected="3"/>
Out of bounds:
<s:numericoptions end="4" selected="34"/>
Multiple:
<s:numericoptions end="10" selected="3 8,5"/>
Multiple and out of bounds:
<s:numericoptions end="10" selected="13 4"/>
