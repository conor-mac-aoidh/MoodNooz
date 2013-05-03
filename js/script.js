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

});
