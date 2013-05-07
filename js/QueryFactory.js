/**
 * QueryFactory
 *
 * executes queries on the server
 *
 * @author Conor Mac Aoidh <conormacaoidh@gmail.com>
 */
var QueryFactory = {

	/**
	 * results
	 *
	 * holds list of results fetched from server
	 */
	results : [ ],
	positive : [ ],
	negative : [ ],
	words : [ ],
	query : "",

	/**
	 * exec
	 *
	 * fetches a query from the server
	 */
	exec : function( ){
		
		$( "#expanded" ).hide( );

		// get query info
		var query = $( "#query-input" ).val( );
		var when = $( "#query-form select" ).val( );
		this.results = [ ];
		this.query = query;

		// expand query
		this.expand( query, function( query ){
			// get results from server
			QueryFactory.get( "/query/search.jsp?query=" + query + "&when=" + when, function( results ){
				QueryFactory.results = results;
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

		// no results found
		if( results.length == 0 ){
			out = "<h1>No Results Found</h1>";
		}
		else{
			// display formatted results
			var doc, out = "";
			for( var i in results ){
				out += "<div class=\"entry\">";
				out += "<h3 class=\"header\"><span class=\"date\">" + results[ i ].date + "</span>";
				out += "<a class=\"article\" id=\"article-" + results[ i ].id + "\">" + results[ i ].title + ", " + results[ i ].host + "</a></h3>";
				out += "<p>" + this.highlight( results[ i ].description ) + "</p>";
				out += "</div>";
			}
		}

		$( "#results" ).html( out );
		$( "#slider" ).animate({ height : parseInt( $( "#results" ).css( "height" ) ) + 20 + "px" }, 1000 );
	},

	/**
	 * highlight
	 *
	 * highlights a string
	 *
	 * @param string content
	 * @return string
	 */
	highlight : function( content ){
		
		// parse output, show highlighting
		this.query = ( this.query.indexOf( " " ) === -1 ) ? [ this.query ] : this.query.split( " " );
		for( var i in this.query ){
			content = content.replace( this.query[ i ], "<span class=\"search-term\">" + this.query[ i ] + "</span>" );
		}
		
		for( var i in this.words ){
			content = content.replace( this.words[ i ], "<span class=\"search-term\">" + this.words[ i ] + "</span>" );
		}
		
		for( var i in this.positive ){
			content = content.replace( this.positive[ i ], "<span class=\"positive\">" + this.positive[ i ] + "</span>" );
		}
		
		for( var i in this.negative ){
			content = content.replace( this.negative[ i ], "<span class=\"negative\">" + this.negative[ i ] + "</span>" );
		}

		return content;

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
		this.words = this.positive = this.negative = [ ];
		var affects = [ ], affect;

		for( var i in terms ){
			affect = terms[ i ].substr( 0, 1 );
			if( affect == "-" || affect == "+" ){
				affects.push( ( affect == "+" ) ? "pos" : "neg" );
				this.words.push( terms[ i ].substr( 1 ) );
			}
		}

		// if no words, call callback
		if( this.words.length == 0 ){
			return callback( query );
		}

		// get associations
		this.getAssociations( this.words, affects, function( associations ){	

			var query = "";
			var affect = "";
			var a = 0;

			// build expanded query with associations
			for( var i in terms ){
				affect = terms[ i ].substr( 0, 1 );
				if( affect == "-" || affect == "+" ){
					query += terms[ i ].substr( 1 ) + " and ";
					if( associations[ a ].length == 0 ){
						query += terms[ i ].substr( 1 );
					}
					else{
						if( affect == "-" )
							QueryFactory.negative.push( associations[ a ] );
						else
							QueryFactory.positive.push( associations[ a ] );
						query += "( ";
						query += terms[ i ].substr( 1 ) + " or ";
						for( var j in associations[ a ] ){
							query += associations[ a ][ j ] + " or ";
						}
						// remove last or
						query = query.substr( 0, query.length - 3 ) + ")";
					}
					query += " and ";
					a++;
				}
				else
					query += terms[ i ] + " and ";
			}
			// remove last and
			query = query.substring( 0, query.length - 4 );

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
		this.get( "/query/associations.jsp?words=" + words + "&affects=" + affects, function( result ){
			// exec callback function
			callback( result );
		});

	},

	/**
	 * get
	 *
	 * gets a url with error handling
	 *
	 * @param string url
	 * @param func callback
	 */
	get : function( url, callback ){

		loading_anim( "start" );

		// get using JSON
		$.ajax({
			url : url,
			dataType : "json",
			error : function( jqXHR, textStatus, errorThrown ){
				loading_anim( "end" );
				$( "#expanded" ).show( );
				$( "#expanded-query" ).html( "<span style=\"color:red\">An error has occured fetching the query.</span>" );
			},
			success : function( data ){
				loading_anim( "end" );
				if( data.response == 0 ){	
					// exec callback function
					callback( data.result );
				}
				else{
					$( "#expanded" ).show( );
					$( "#expanded-query" ).html( "<span style=\"color:red\">There was a problem executing the query. Response: " + data.result + "</span>" );
				}
			}
		});

	},


	/**
	 * getResult
	 *
	 * gets a page by its id from the results list
	 */
	getResult : function( id ){
		for( var i in this.results ){
			if( this.results[ i ].id == id )
				return this.results[ i ];
		}
		return null;
	}

};
