<%@page 
	import="java.util.*"
	import="core.MoodNooz"
	import="core.LuceneSearcher;"
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
		
		LuceneSearcher s = new LuceneSearcher( );
		ScoreDoc[ ] hits = s.search( 

		// print output as JSON
		String output = "{ \"response\" : 0, \"result\" : [";
		for( Vector<String> v : associations ){	
			output += "[";
			if( v.size( ) != 0 ){
				for( String s : v ){
					output += "\"" + s + "\",";
				}
				output = output.substring( 0, output.length( ) - 1 );
			}
			output += "],";
		}
		output = output.substring( 0, output.length( ) - 1 );
		output += "]}";
		out.write( output );

	}
	// catch and print errors in JSON
	catch( Exception e ){
		out.write( "{ \"response\" : 200, \"result\" : \"Associations retrieval failed: " + e.getMessage( ) + "\" }" );
	}
	

%>
