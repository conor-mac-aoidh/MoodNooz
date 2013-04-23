<%@page 
	import="core.AffectMap"
%>
<%	
//	try{
		String words[ ] = request.getParameter( "words" ).split( "-" );
		String affects[ ] = request.getParameter( "affects" ).split( "-" );

		// neg, pos maps
		AffectMap neg = new AffectMap( "negative map tabbed.idx" );
		neg.printMap( );

	/*	AffectMap pos = new AffectMap( "positive map tabbed.idx" );
		
		Vector<String> associations = new Vector<String>( );
		for( int i = 0; i < words.length( ); ++i ){
			if( affetcs[ i ].compareTo( "pos" ) == 0 )	
				associations.add( pod.getAssociations( w ) );
			else
				associations.add( neg.getAssociations( w ) );
			
		}
*/
		// output associations as JSON
	//	Serialize.toJSON( associations );	

//	} catch( Exception e ){
//		out.write( e.getMessage( ) );
//		out.write( "{ error : 200, response : { } }" );
//	}
	

%>
