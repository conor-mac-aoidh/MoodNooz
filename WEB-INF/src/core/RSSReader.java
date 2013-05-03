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

	ArrayList<LuceneDocument> entries;
	
	/**
	 * RSSReader
	 * 
	 * initialises the RSS reader. must provide
	 * list of entries
	 * 
	 * @param e
	 */
	public RSSReader( ArrayList<LuceneDocument> entries ){
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
	public void fetch( String url ) throws ParserConfigurationException, SAXException, IOException, Exception{
		URL feed = new URL( url );
		DocumentBuilder builder = DocumentBuilderFactory.newInstance( ).newDocumentBuilder( );
		Document doc = builder.parse( feed.openStream( ) );
		NodeList nodes = doc.getElementsByTagName( "item" );
		Element element;
		String t, d, l, b, p;
		
		for( int i = 0; i < nodes.getLength( ); ++i ){
			element = (Element) nodes.item( i );
                        t=element.getElementsByTagName("title").item(0).getTextContent();
			d=element.getElementsByTagName("description").item(0).getTextContent();
                        l=element.getElementsByTagName("link").item(0).getTextContent();
                        p=element.getElementsByTagName("pubDate").item(0).getTextContent();
                        
                        NewsDocument nd = new NewsDocument(l);
                        b = nd.getBodyText();
                        if(b.equals("")){continue;}
                        System.out.println(b);
			this.entries.add( new LuceneDocument( t, d, l, p, b ) );
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
		return "h";
	}
}
