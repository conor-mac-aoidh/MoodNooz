/**
 * RSSReader, MoodNooz
 * 
 * fetches a web page using it's url
 * 
 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
 * class:	COMP30050 Software Engineering Project 3
 */

package core;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class RSSReader{

	ArrayList entries;
	
	/**
	 * RSSReader
	 * 
	 * initialises the RSS reader. must provide
	 * list of entries
	 * 
	 * @param e
	 */
	public RSSReader( ArrayList entries ){
		this.entries = entries;
	}
	
	/**
	 * fetch
	 * 
	 * fetches an rss feed by url
	 * 
	 * @param url
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void fetch( String url ) throws ParserConfigurationException, SAXException, IOException{
		URL feed = new URL( url );
		DocumentBuilder builder = DocumentBuilderFactory.newInstance( ).newDocumentBuilder( );
		Document doc = builder.parse( feed.openStream( ) );
		NodeList nodes = doc.getElementsByTagName( "item" );
		Element element;
		String t, d, l, a, p;
		
		for( int i = 0; i < nodes.getLength( ); ++i ){
			element = (Element) nodes.item( i );
			t = this.getElementValue( element, "title" );
			d = this.getElementValue( element, "description" );
			l = this.getElementValue( element, "link" );
			a = this.getElementValue( element, "dc:creator" );
			p = this.getElementValue( element, "pubDate" );
			//this.entries.add( new MyDocument( t, d, l, a, p ) );
		}
	}
	
	/**
	 * getElementValue
	 * 
	 * gets the value of an element from the
	 * xml document
	 * 
	 * @return String
	 */
	private String getElementValue( Element e, String n ){
		NodeList sub = e.getChildNodes( );
		String t;
		for( int i = 0; i < sub.getLength( ); ++i ){
			t = sub.item( i ).getNodeName( );
			if( t.compareTo( n ) == 0 ){
				return sub.item( i ).getNodeValue( );
			}
		}
		return "";
	}
}
