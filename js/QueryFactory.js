/**
 * QueryFactory
 *
 * executes queries on the server
 *
 * @author Conor Mac Aoidh <conormacaoidh@gmail.com>
 */
var QueryFactory = {

	/**
	 * exec
	 *
	 * fetches a query from the server
	 */
	exec : function( ){
		
		// get query info
		var query = $( "#query-input" ).val( );
		var when = $( "#query-form select" ).val( );
		
		// expand query
		this.expand( query, function( query ){

			// get results from server
			$.getJSON( "/jsp/query/get.jsp?query=" + query + "&when=" + when, function( results ){
				QueryFactory.display.apply( QueryFactory, [ results ] );
			});

		});

		return false;
	},

	/**
	 * display
	 *
	 * shows the results for the query
	 *
	 * @param object results
	 */
	display : function( results ){
		// @todo
	},

	/**
	 * expand_query
	 *
	 * @param string query
	 * @param func callback
	 */
	expand : function( query, callback ){
		
		// get associations for words
		var terms = query.split( " " );
		var words = [ ];
		var affects = [ ], affect;

		for( var i in terms ){
			affect = terms[ i ].substr( 0, 1 );
			if( affect == "-" || affect == "+" ){
				affects.push( ( affect == "+" ) ? "pos" : "neg" );
				words.push( terms[ i ].substr( 1 ) );
			}
		}

		// if no words, call callback
		if( words.length == 0 ){
			return callback( query );
		}

		// get associations
		this.getAssociations( words, affects, function( result ){	

			var query = "";

			for( var i in words ){
				query += words[ i ] + " and ";

			}

			// show expanded query
			$( "#expanded-query" ).html( query );
			$( "#expanded" ).show( );

			callback( query );
		});

	},

	/**
	 * get_associations
	 *
	 * returns a list of all positive associations of
	 * a word
	 *
	 * @param array word
	 * @param array affect
	 */
	getAssociations : function( words, affects, callback ){

		// join words, affects into array
		words = words.join( "," );
		affects = affects.join( "," );

		// get associations of words
		$.getJSON( "/query/associations.jsp?words=" + words + "&affects=" + affects, function( result ){
			// exec callback function
			callback( result );
		});

	},

};
