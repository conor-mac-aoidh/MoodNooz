/**
 * NewsDocument, MoodNooz
 * 
 * a model to represent fetched news documents
 * 
 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
 * class:	COMP30050 Software Engineering Project 3
 */

package core;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class NewsDocument {

	/**
	 * doc
	 * 
	 * Document object to hold NewsDocument
	 */
	Document doc;
	
	/**
	 * NewsDocument
	 * 
	 * creates a document with the properties provided
	 * as arguments
	 * 
	 * @param t
	 * @param d
	 * @param l
	 * @param a
	 * @param p
	 */
	public NewsDocument( String t, String d, String l, String a, String p ){
		doc = new Document( );
		
		// note: StringField or just Field types may be used here also,
		// not sure which one is suited better for these fields
		doc.add( new TextField( "title", t, Field.Store.YES ) );
		doc.add( new TextField( "description", d, Field.Store.YES ) );
		doc.add( new TextField( "link", l, Field.Store.YES ) );
		doc.add( new TextField( "author", a, Field.Store.YES ) );
		doc.add( new TextField( "date", p, Field.Store.YES ) );
				
	}
	
}
