package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * MoodDictionary
 * 
 * A dictionary datastructure that allows for lookup of
 * words
 * 
 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
 * @date	27-02-13
 * class:	COMP30050 Software Engineering Project 3
 */

public class MoodDictionary{

	/**
	 * positive
	 * 
	 * holds positive affect map
	 * 
	 * @access private
	 */
	private AffectMap positive;
	
	/**
	 * negative
	 * 
	 * holds negative affect map
	 * 
	 * @access private
	 */
	private AffectMap negative;
	
	/**
	 * irregular
	 * 
	 * holds lists of irregular words
	 * 
	 * @access private
	 */
	private HashMap<String,Vector<String>> irregular;
		
	/**
	 * MoodDictionary
	 * 
	 * constructor, creats instances of affect map and
	 * reads file for irregulars
	 * 
	 * @param AffectMap positive
	 * @param AffectMap negative
	 * @return void
	 * @throws Exception 
	 */
	public MoodDictionary( AffectMap positive, AffectMap negative ) throws Exception{

		// create instance of affect maps
		this.positive = positive;
		this.negative = negative;
		
		// read irregular rules
		this.readIrregular( );

	}
	
	/**
	 * readIrregular
	 * 
	 * reads irregular words from the file
	 * 
	 * @throws FileNotFoundException
	 */
	private void readIrregular( ) throws Exception{
		// open file and create buffered reader
		FileReader fr = new FileReader( "irregular_verbs.idx" );
		BufferedReader buff = new BufferedReader( fr );
		
		// initialise hashmap and a vector to hold word associations
		irregular = new HashMap<String,Vector<String>>( );
		Vector<String> associations;
		
		// read file line by line
		String line = buff.readLine( );
		String words[];
		int i;
		do{
			// split by tab
			words = line.split( "\t" );
			
			// for each associated word, add to vector
			associations = new Vector<String>( );
			for( i = 1; i < ( words.length ); ++i ){
				associations.add( words[ i ] );
			}
			
			// add word and associations to hashmap
			irregular.put( words[ 0 ], associations );
			
			// read another line
			line = buff.readLine( );
			
		} while( line != null );
		
		// close buffer
		buff.close( );
	}
	
	/**
	 * containsWOrd
	 * 
	 * checks if the dictionary contains a word
	 * 
	 * @param String w
	 * @return boolean
	 */
	public boolean containsWord( String w ){
		// search maps for word
		if( positive.containsWord( w ) || negative.containsWord( w ) ){
			return true;
		}
		return false;
	
	}
	
	/**
	 * getPositivity
	 * 
	 * returns the positivity of the word
	 * 
	 * @param String w
	 * @return double
	 */
	public double getPositivity( String w ){
		double neg = negative.getAssociationsOfWord( w ).size( );
		double pos = positive.getAssociationsOfWord( w ).size( );
		
		return ( neg / pos );
	}
	
	/**
	 * getNegativity
	 * 
	 * returns the negativity of the word
	 * 
	 * @param String w
	 * @return double
	 */
	public double getNegativity( String w ){
		double neg = negative.getAssociationsOfWord( w ).size( );
		double pos = positive.getAssociationsOfWord( w ).size( );

		return ( pos / neg );
	}
	
	/**
	 * getRootForm
	 * 
	 * returns the root form of a word
	 * 
	 * @param String w
	 * @return String
	 */
	public String getRootForm( String w ) throws Exception{
		
		// word is already in dictionary
		if( this.containsWord( w ) )
			return w;
		
		int length = w.length( );
		String word = "";
		
		if( length > 4 ){ // use morphology rules

			if( w.endsWith( "thes" ) || w.endsWith( "zes") || w.endsWith( "ses") || w.endsWith( "xes" ) ){
				word = w.substring( 0, length - 2 );		// remove final -es
				if( this.containsWord( word ) )
					return word;
			}
			if( w.endsWith( "s" ) ){
				word = w.substring( 0, length - 1 );		// remove final -s
				if( this.containsWord( word ) )
					return word;
			}
			if( w.endsWith( "ies" ) ){
				word = w.substring( 0, length - 3 ) + "y";	// remove -ies, add y
				if( this.containsWord( word ) )
					return word;
			}
			if( w.endsWith( "ing" ) ){
				word = w.substring( 0, length - 3 ); 		// remove -ing
				if( this.containsWord( word ) )
					return word;
			}
			if( w.endsWith( "ed" ) ){
				word = w.substring( 0, length - 2 );		// remove -ed
				if( this.containsWord( word ) )
					return word;
				word = w.substring( 0, length - 1 );		// remove -e
				if( this.containsWord( word ) )
					return word;				
			}
			if( w.endsWith( "ied" ) ){
				word = w.substring( 0, length - 3 ) + "y";	// remove -ied, add y
				if( this.containsWord( word ) )
					return word;
			}
			if( w.endsWith( "ing" ) ){
				word = w.substring( 0, length - 4 );		// remove -ing plus last character
				if( this.containsWord( word ) )
					return word;
			}
			if( w.endsWith( "ed" ) ){
				word = w.substring( 0, length - 3 );		// remove -ed plus last character
				if( this.containsWord( word ) )
					return word;
			}
					
		}
		
		// irregular morphology	
		// search irregular map for word
		for( Map.Entry<String, Vector<String>> entry : irregular.entrySet( ) ){
			word = entry.getKey( );
			for( String val : entry.getValue( ) ){
				if( w.compareTo( val ) == 0 ){
					return word;
				}
			}
		}
		
		throw new Exception( "[ERROR] root word could not be found for \"" + w + "\"" );

	}
	
	/**
	 * printIrregular
	 * 
	 * used for debugging purposes
	 * 
	 * @access public
	 * @return void
	 */
	public void printIrregular( ){

		// print contents of hashmap
		for( Map.Entry<String, Vector<String>> entry : irregular.entrySet( ) ){
			System.out.print( "key: " + entry.getKey( ) + " values: \t" );
			for( String word : entry.getValue( ) ){
				System.out.print( word + "\t" );
			}
			System.out.print( "\n" );
		}
		
	}
}
