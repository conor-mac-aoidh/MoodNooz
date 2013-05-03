<%@page 
	import="java.util.*"
	import="core.MoodNooz"
	import="core.AffectMap;"
%>
<%	

	/**
	 * associations.jsp
	 * 
	 * returns a list of associations to words passed
	 * in the url
	 * 
	 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
	 * @date	20-02-13
	 * class:	COMP30050 Software Engineering Project 3
	 */
	try{
		// get query parameters
		String words[ ] = request.getParameter( "words" ).split( "," );
		String affects[ ] = request.getParameter( "affects" ).split( "," );

		// neg, pos maps
		AffectMap neg = new AffectMap( MoodNooz.positiveMap );
		AffectMap pos = new AffectMap( MoodNooz.negativeMap );

		// get associations		
		Vector<String> association = new Vector<String>( );
		Vector<Vector> associations = new Vector<Vector>( );
		String w;
		for( int i = 0; i < words.length; ++i ){
			w = words[ i ];
			if( affects[ i ].compareTo( "neg" ) == 0 )
				associations.add( pos.getAssociationsOfWord( w ) );
			else if( affects[ i ].compareTo( "pos" ) == 0 )
				associations.add( neg.getAssociationsOfWord( w ) );
		}

		// print output as JSON
		String output = "{ \"response\" : 0, \"result\" : [";
		for( Vector<String> v : associations ){	
			output += "[";
			for( String s : v ){
				output += "\"" + s + "\",";
			}
			output = output.substring( 0, output.length( ) - 1 );
			output += "],";
		}
		output = output.substring( 0, output.length( ) - 1 );
		output += "]}";
		out.write( output );

	}
	// catch and print errors in JSON
	catch( Exception e ){
		out.write( "{ \"response\" : 200, \"result\" : \"" + e.getMessage( ) + "\" }" );
	}
	

%>
