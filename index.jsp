<%@include file="header.jsp"%>

<!-- Search Box Container -->
<div id="query">
<form id="query-form">
	<input id="query-input" type="text" name="Search" value="" style="width:65%"/>
	<select name="when" style="width:20%">
		<option>Today</option>
		<option>This Week</option>
		<option>This Month</option>
		<option>This Year</option>
	</select>
	<input id="query-submit" type="submit" name="go" value="Go" style="width:13%"/>
</form>
</div>
<!-- End Search Box Conatiner -->

<div id="expanded">
	<strong>Expanded Query: </strong><span id="expanded-query"></span>
</div>

<!-- Search Results -->
<div id="results"></div>
<!-- End Search Results -->

<%@include file="footer.jsp"%>
