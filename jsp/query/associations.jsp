<%@page 
	import="core.AffectMap"

	try{
		String words[ ] = request.getParamater( "words" ).split( "-" );
		String affects[ ] = request.getParamater( "affects" ).split( "-" );

		// neg, pos maps
		AffectMap neg = new AffectMap( "negative map tabbed.idx" );
		AffectMap pos = new AffectMap( "positive map tabbed.idx" );
		
		Vector<String> associations = new Vector<String>( );
		for( int i = 0; i < words.length( ); ++i ){
			if( affetcs[ i ].compareTo( "pos" ) == 0 )	
				associations.add( pod.getAssociations( w ) );
			else
				associations.add( neg.getAssociations( w ) );
		}

		// output associations as JSON
		
	} catch( Exception e ){
		out.write( "{ error : 200, response : { } }" );
	}
	

%>
