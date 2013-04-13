package core;
/**
 * AffectMap
 * 
 * maps a word to a list of associated words 
 * 
 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
 * @date	20-02-13
 * class:	COMP30050 Software Engineering Project 3
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class AffectMap {

	/**
	 * map
	 * 
	 * hashmap of vectors to contain word
	 * associations
	 * 
	 * @access private
	 */
	private HashMap<String,Vector<String>> map;
	
	/**
	 * AffectMap
	 * 
	 * reads a word map from an input file
	 * 
	 * @return void
	 */
	public AffectMap( String file ) throws Exception{
		
		// open file and create buffered reader
		FileReader fr = new FileReader( file );
		BufferedReader buff = new BufferedReader( fr );
		
		// initialise hashmap and a vector to hold word associations
		map = new HashMap<String,Vector<String>>( );
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
			for( i = 1; i < words.length; ++i ){
				associations.add( words[ i ] );
			}
			
			// add word and associations to hashmap
			map.put( words[ 0 ], associations );
			
			// read another line
			line = buff.readLine( );
			
		} while( line != null );
		
		// close buffer
		buff.close( );
		
	}
	
	/**
	 * containsWord
	 * 
	 * checks if the hashmap contains a word
	 * 
	 * @param String word
	 * @return boolean
	 * @access public
	 */
	public boolean containsWord( String word ){
		return map.containsKey( word );
	}
	
	/**
	 * getAssociationsOfWord
	 * 
	 * returns the vector associated with a word
	 * 
	 * @param string word
	 * @return Vector<String>
	 */
	public Vector<String> getAssociationsOfWord( String word ){
		return map.get( word );				
	}
	
	/**
	 * printMap
	 * 
	 * used for debugging purposes
	 * 
	 * @access public
	 * @return void
	 */
	public void printMap( ){

		// print contents of hashmap
		for( Map.Entry<String, Vector<String>> entry : map.entrySet( ) ){
			System.out.print( entry.getKey( ) + "\t" );
			for( String word : entry.getValue( ) ){
				System.out.print( word + "\t" );
			}
			System.out.print( "\n" );
		}
		
	}
	
}
