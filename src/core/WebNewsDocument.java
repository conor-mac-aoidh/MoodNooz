package core;
/**
 * WebNewsDocument
 * 
 * fetches a web page using it's url
 * 
 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
 * @date	30-01-13
 * class:	COMP30050 Software Engineering Project 3
 */

import java.net.*;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Vector;

import org.jsoup.*;
import org.jsoup.nodes.Document;

public class WebNewsDocument {

	/**
	 * url
	 * 
	 * holds the url being accessed
	 * 
	 * @access private
	 */
	private URI url;
	
	/**
	 * rawText
	 * 
	 * holds the rawText, acts as a cache for
	 * the getRawText method
	 * 
	 * @access private
	 */
	private String rawText;
		
	/**
	 * WebNewsDocument
	 * 
	 * constructor for WebNewsDocument class
	 * 
	 * @param string url
	 * @access public
	 */
	public WebNewsDocument( String url ) throws Exception{
		this.url = new URI( url );
	}
	
	/**
	 * getRawText
	 * 
	 * returns the raw format of the web page, an
	 * accessor method for this.rawText
	 * 
	 * @return String
	 * @access public
	 */
	public String getRawText( ) throws Exception{

		// caching, so text isn't fetched twice
		if( this.rawText != null )
			return this.rawText;
		
		// connect to url, get raw html and store in string
		this.rawText = Jsoup.connect( this.url.toString( ) ).get( ).toString( );
		
		return this.rawText;
		
	}
	
	/**
	 * getURL
	 * 
	 * accessor method for the url
	 * 
	 * @return String
	 * @access public
	 */
	public String getURL( ){
		return this.url.toString( );
	}
	
	/**
	 * getWebSite
	 * 
	 * returns the name of the website, for example for a url:
	 * 
	 * http://www.testurl.com/news/feed/today
	 * 
	 * this method will return:
	 * 
	 * www.testurl.com
	 * 
	 * @return String
	 * @access public
	 */
	public String getWebSite( ){
		return this.url.getHost( );
	}
	
	/**
	 * getHeadlineText
	 * 
	 * returns the raw text of the headline, no HTML
	 * 
	 * @return String
	 */
	public String getHeadlineText( ) throws Exception{

		String text = this.getRawText( );
		
		// parse html
		Document doc = Jsoup.parse( text );
		
		// switch depending on host name
		String host = this.getWebSite( );
		String headline = "";
		switch( host ){
			// the guardian
			case "www.guardian.co.uk":	
			case "guardian.co.uk":
				// select meta tag with title property
				headline = doc.select( "meta[property=og:title" ).attr( "content" );
			break;
			// the irish times
			case "www.irishtimes.com":		
			case "irishtimes.com":
				// select meta tag with title name
				headline = doc.select( "meta[name=title]" ).attr( "content" );
			break;
			// throw exception, hostname not known
			default: 						
				throw new Exception( host + " is not a recogised hostname" );
		}
		
		// return the content attribute of the meta tag
		return headline;

	}
	
	/**
	 * getBodyText
	 * 
	 * returns the raw text of the story body, no HTML
	 * 
	 * @return String
	 */
	public String getBodyText( ) throws Exception{

		String text = this.getRawText( );
		
		// parse html
		Document doc = Jsoup.parse( text );
		
		// switch depending on host name
		String host = this.getWebSite( );
		String body = "";
		switch( host ){
			// the guardian
			case "www.guardian.co.uk":	
			case "guardian.co.uk":
				// select article body, convert to text
				body = doc.select( "div#article-body-blocks" ).text( ); 
			break;
			// the irish times
			case "www.irishtimes.com":		
			case "irishtimes.com":
				// a bit more work, get index of start and end, cut between
				body = doc.select( "div.left-column" ).toString( );
				int start = body.indexOf( "headline-info" ) - 10;	// start of article
				int end = body.indexOf( "social-link" ) - 8;		// end of article
				body = body.substring( start, end );				// cut middle
				body = Jsoup.parse( body ).text( );					// parse as text
			break;
			// throw exception, hostname not known
			default: 						
				throw new Exception( host + " is not a recogised hostname" );
		}
				
		return body;
		
	}
	
	/**
	 * getSentencesOfText
	 * 
	 * fetches the body of the url and splits
	 * the text into a vector of strings - one
	 * for each sentence
	 * 
	 * @access public
	 * @return Vector<String>
	 */
	public Vector<String> getSentencesOfText( ) throws Exception{
		
		// get text
		String text = this.getBodyText( );
		
		// initialise variables
		int start, end;
		Vector<String> sentences = new Vector<String>( );
		String sentence;
		
		// create instance of BreakIterator 
		BreakIterator it = BreakIterator.getSentenceInstance( Locale.ENGLISH );
		it.setText( text );
		start = it.next( );
		end = it.next( );
		
		while( end != BreakIterator.DONE ){	// keep going until BreakIterator.DONE
			
			// cut sentence from text 
			sentence = text.substring( start, end );
			
			// add sentence to vector
			sentences.add( sentence );
			
			// set up next iteration
			start = end;
			end = it.next( );
			
		}
		
		return sentences;
		
	}
	
}
