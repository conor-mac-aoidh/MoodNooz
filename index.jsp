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
	<strong>Expanded Query: </strong><span id="expanded-query"></span><span id="see-more"> Show</span>
</div>

<!-- Search Results Slider -->
<div id="slider">
	<div id="results"><i>Enter a search query above</i></div>
	<div id="page">
		<a class="return">Back to search results</a>
		<div class="entry">
		</div>
		<br style="clear:both"/>
	</div>
</div>
<!-- End Search Results Slider -->

<%@include file="footer.jsp"%>
