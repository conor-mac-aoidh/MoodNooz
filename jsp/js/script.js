/**
 * General Javascript, MoodNooz
 * 
 * @author Conor Mac Aoidh <conormacaoidh@gmail.com>
 */

$(function(){
	// show active page
	var page = window.location.pathname.split( "/" ).pop( );
	var el = $( "#menu ul li a[href=\"" + page + "\"]" );
	if( el.length != 0 )
		el.addClass( "current" ); 
	else
		$( "#menu ul li a:first" ).addClass( "current" );

	// show row colors	
	$( ".row-color tr:not(:hidden):even" ).removeClass( "odd" ).addClass( "even" );
	$( ".row-color tr:not(:hidden):odd" ).removeClass( "even" ).addClass( "odd" );

	// init QueryFactory
	QueryFactory.init( );

	// search submit
	$( "#query-form" ).submit( QueryFactory.exec );

});


/**
 * QueryFactory
 *
 * executes queries on the server
 */
var QueryFactory = {

	/**
	 * q
	 *
	 * stores reference to object this
	 */
	q : null,

	/**
	 * init
	 *
	 */
	init : function( ){
		this.q = this;
	},

	/**
	 * exec
	 *
	 * fetches a query from the server
	 */
	exec : function( ){
		console.log( q );
		// get query info
		query = $( "#query-input" ).val( );
		when = $( "#query-form select" ).val( );
		
		// expand query
		q.expand( query, function( query ){

			// get results from server
			$.getJSON( "/jsp/query/get.jsp?query=" + query + "&when=" + when, function( results ){
				QueryFactory.display( results );
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
		alert( "test" );
		// show expanded query
		$( "#expanded" ).show( );
		$( "#expanded-query" ).html( query );

		// get associations for words
		var terms = query.split( " " );
		var words = [ ];
		var affects = [ ];

		for( var i in terms ){
			affects.push( terms.substr( 0, 1 ) == "-" ? "neg" : "pos" );
			words.push( terms.substr( 1 ) );
		}

		// if no words, call callback
		if( words.length == 0 ){
			return callback( query );
		}

		// get associations
		q.getAssociations( words, affacts, callback );

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

		console.log( words );
		console.log( affects );

		// join words, affects into array
		words = words.join( "," );
		affects = affects.join( "," );

		// get associations of words
//		$.getJSON( "/jsp/query/associations.jsp?words=" + words + "&affects=" + affects, function( result ){
			// exec callback function
//			callback( result );
//		});

	},

};
