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
	
	// search submit
	$( "#query-form" ).submit(function( ){ 
		return QueryFactory.exec.apply( QueryFactory, [ ] );
	});

	// events
	$( ".article" ).live( "click", function( ){
		// load page by id
		$( "#page" ).css( "left", "1000px" );
		$( "#slider" ).animate({ "height" : parseInt( $( "#page" ).css( "height" ) ) + 20 + "px" }, 1500 );
		$( "#results" ).animate({ "left" : "-1000px" }, 1500 );
		$( "#page" ).animate({ "left" : "-1px" }, 1500 );
	});

	$( ".return" ).live( "click", function( ){
		// load page by id
		$( "#slider" ).animate({ "height" : parseInt( $( "#results" ).css( "height" ) ) + 20 + "px" }, 1500 );
		$( "#results" ).animate({ "left" : 0 }, 1500 );
		$( "#page" ).animate({ "left" : "1001px" }, 1500 );
	});

	// show toggle
	$( "#see-more" ).click(function(){
		$( this ).hide( );
		$( this ).parent( ).css({
			"overflow-y" : "normal",
			"height" : "auto"
		});
	});
});
