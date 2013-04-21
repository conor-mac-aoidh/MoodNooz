package core;
import java.util.Vector;

/**
 * Test
 * 
 * used for running main application tests 
 * 
 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
 * class:	COMP30050 Software Engineering Project 3
 */
public class Test {

	/**
	 * main
	 * 
	 * runs the application
	 * 
	 * @return void
	 * @throws Exception 
	 * @access public
	 */
	public static void main( String [ ] args ){

		// test the news document class
		//NewsDocumentTest( );
		
		// test the affect map class
		//AffectMapTest( );
		
		// test the mood dictionary class
		//MoodDictionaryTest( );
		
		// test the ress reader
		RSSReaderTest( );
	}

	/**
	 * NewsDocumentTest
	 * 
	 * tests the NewsDocument class
	 * 
	 * @return void
	 * @access public
	 */
	public static void NewsDocumentTest( ){

		try{
			// @todo
		}
		catch( Exception e ){ // catch exceptions, display errors
			System.out.println( e.getMessage( ) );
		}
		
	}
	
	/**
	 * AffectMapTest
	 * 
	 * tests the affect map class
	 * 
	 * @access public
	 * @return void
	 */
	public static void AffectMapTest( ){
		
		try{
			AffectMap map = null;

			// test negative map
			map = new AffectMap( "negative map tabbed.idx" );
			//map.printMap( );
			
			// check that it contains a few random words
			String[] negative = {
				"potential",
				"academic",
				"subjugated",
				"victory",
				"sword",
				"rupee",
				"despoliation",
				"innocently",
				"topology",
				"imagery"
			};
			
			for( String word : negative ){
				// if word not found, throw exception
				if( !map.containsWord( word ) )
					throw new Exception( "[TEST]: negative word not found in map: \"" + word + "\"" );
				
			}
		
			// test positive map
			map = new AffectMap( "positive map tabbed.idx" );
			//map.printMap( );
			
			// check that it contains a few random words
			String[] positive = {
				"heartfelt",
				"achieve",
				"acuity",
				"stratagem",
				"cache",
				"crumpet",
				"overview",
				"pounce"
			};
			
			for( String word : positive ){
				// if word not found, throw exception
				if( !map.containsWord( word ) )
					throw new Exception( "[TEST]: positive word not found in map: \"" + word + "\"" );
				
			}
			
			
			System.out.println( "[TEST]: AffectMap all tests OK" );
			
		} catch( Exception e ){
			System.out.println( e.getMessage( ) );
			System.out.println( "[TEST]: AffectMap tests FAILED" );
		}
		
	}
	
	
	/**
	 * MoodDictionaryTest
	 * 
	 * tests the MoodDictionary class
	 * 
	 * @return void
	 * @access public
	 */
	public static void MoodDictionaryTest( ){

		try{
			// create affect maps
			AffectMap pos = new AffectMap( "positive map tabbed.idx" );
			AffectMap neg = new AffectMap( "negative map tabbed.idx" );
			
			// create mood dictionary
			MoodDictionary dic = new MoodDictionary( pos, neg );
			
			// test root words
			String[] words = {
				"testing",
				"started",
				"caught",
				"animals",
				"retreated",
				"melting",
				"retired",
				"begining",
				"awoken",
				"chosen",
			};
			System.out.println( "[ROOT WORDS TEST]" );
			for( String word : words ){
				System.out.println( word + " -> " + dic.getRootForm( word ) );
			}
			
			// test positive and negative readings
			String[] negative = {
					"potential",
					"academic",
					"victory",
					"sword",
					"rupee",
					"innocently",
					"topology",
					"imagery"
			};
			System.out.println( "[NEGATIVE WORDS NEGATIVITY TEST]" );
			for( String word : negative ){
				System.out.println( word + ": " + dic.getNegativity( word ) );
			}

			// check that it contains a few random words
			String[] positive = {
				"achieve",
				"acuity",
				"stratagem",
				"cache",
				"overview",
				"pounce"
			};
			System.out.println( "[POSITIVE WORDS POSITIVITY TEST]" );
			for( String word : positive ){
				System.out.println( word + ": " + dic.getPositivity( word ) );
			}
			
			System.out.println( "[TEST]: MoodDictionary all tests OK" );
		
		} catch( Exception e ){
			System.out.println( e.getMessage( ) );
			System.out.println( "[TEST]: MoodDictionary tests FAILED" );
		}		
	}
	
	/**
	 * RSSReaderTest
	 */
	public static void RSSReaderTest( ){
		// @todo
	}
}
