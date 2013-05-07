<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page 
	import="java.util.*"
	import="org.apache.lucene.document.Document"
	import="org.apache.lucene.search.ScoreDoc"
	import="core.MoodNooz"
	import="core.Searcher;"
%>
<%	

	/**
	 * search.jsp
	 * 
	 * searches for some terms using the lucene searcher
	 * 
	 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
	 * @date	20-02-13
	 * class:	COMP30050 Software Engineering Project 3
	 */
	try{
		// get query parameters
		String query = request.getParameter( "query" );
		String when = request.getParameter( "when" );
		
		Searcher s = new Searcher( );
		ArrayList<Document> hits = s.query( query, when );
		String output = s.serialize( hits );

		// print output as JSON
		out.write( "{ \"response\" : 0, \"result\" : " + output + "}" );

	}
	// catch and print errors in JSON
	catch( Exception e ){
		out.write( "{ \"response\" : 200, \"result\" : \"Search failed: " + e.getMessage( ) + "\" }" );
		e.printStackTrace( );
	}
	

%>
