package core;

import java.io.IOException;
import org.apache.lucene.index.Term;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.document.Document;
import org.jsoup.Jsoup;
import java.util.HashMap;
import java.util.*;

import net.sf.json.util.JSONUtils;

public class Searcher extends LuceneSearcher{

	HashMap<String, Document> results = new HashMap<String, Document>( );
	
	/**
	 * query
	 * 
	 * returns a list of results fetched from the lucene IR engine for the
	 * search query and within the search date
	 * 
	 * when - one of "today, week, month, year"
	 * 
	 * example query (passed in expanded form):
	 * 
	 * london +car -train
	 * 
	 * expanded:
	 * 
	 * london and car and ( car or cruise or babe or home or star or commission
	 * or team or cat or life or sport or movie or cruiser or mansion or
	 * attraction or garden or pet or yacht or diamond or innovation or
	 * personality or love or guide or friend or speed or score or landscape or
	 * parent or flower or player or luxury or sculpture or apple or superstar
	 * or sword or rally or accommodation or communication or benefit or
	 * enthusiast or prize or cake or villa or hope or fashion or craft or gift
	 * or restoration or vacation ) and train and ( train or challenge or fight
	 * or dog or animal or terminal or battle or break or soldier or serf or gap
	 * or drill or problem )
	 * 
	 * @param query
	 * @param when
	 * @return
	 * @throws IOException 
	 * 
	 */
	public ArrayList<Document> query(String query, String when) throws IOException {

		ScoreDoc[ ] docs;
		Document ldoc;
		String[] terms = query.split(" and ");
		for (String s : terms) {
			 
			if (s.charAt(0) == '(') {
				BooleanQuery innerQuery = new BooleanQuery();
				s = s.substring(2, s.length() - 2);
				String[] inner = s.split(" or ");
				for (String i : inner) {
					docs = this.search( i, "body" );
					for( ScoreDoc d : docs ){
						ldoc = this.get( d.doc );
						this.results.put( ldoc.get( "link" ), ldoc );
					}
				}

			} else {
				docs = this.search( s, "body" );
				for( ScoreDoc d : docs ){
					ldoc = this.get( d.doc );
					this.results.put( ldoc.get( "link" ), ldoc );
				}
			}

		}
		
		ArrayList<Document> ordered = new ArrayList<Document>( ); 

		for( Map.Entry<String, Document> entry : this.results.entrySet( ) ){
			System.out.print( entry.getKey( ) + "\t" );
			Document word = entry.getValue( );
			ordered.add( word );
		}
		
/*
		Date date = new Date();
		int iCurDate;
		Date limDate = date;
		String currentDate = DateTools.dateToString(date,
				DateTools.Resolution.HOUR);
		iCurDate = Integer.parseInt(currentDate);
		switch (when) {
		case "today":
			limDate.setDate(limDate.getDate() - 1);
			break;
		case "week":
			limDate.setDate(limDate.getDate() - 7);
			break;
		case "month":
			limDate.setMonth(limDate.getMonth() - 1);
			break;
		case "year":
			limDate.setYear(limDate.getYear() - 1);
			break;
		}

		String limitDate = DateTools.dateToString(limDate,
				DateTools.Resolution.HOUR);
		System.out.print(limitDate);

		Term now = new Term("pubdate", currentDate);
		Term limit = new Term("pubdate", limitDate);
		TermRangeQuery dateQuery = new TermRangeQuery("pubdate", limit.bytes(),
				now.bytes(), true, true);
		booleanQuery.add(dateQuery, BooleanClause.Occur.MUST);
		String b = booleanQuery.toString();
		System.out.println(b);
*/
		return ordered;
	}

	/**
	 * serialize
	 *
	 * serialize a score doc object to JSON
	 *
	 * @param ScoreDoc[ ] hits
	 * @return String
	 */
	public String serialize( ArrayList<Document> hits ) throws IOException{
		String output = "[", desc;
		Document doc;
		org.jsoup.nodes.Document jdoc;
		int end;
		for( Document d : hits ){
			doc = d;
			desc = doc.get( "description" );
			jdoc = Jsoup.parse( desc );
			end = desc.length( );
			end = ( end < 250 ) ? end : 250;
			desc = jdoc.text( );
			desc = desc.substring( 0, end ) + "...";
			output += "{";
			output += "\"id\":\"" + doc.get( "id" ) + "\",";
			output += "\"title\":" + JSONUtils.quote( doc.get( "title" ) ) + ",";
			output += "\"link\":" + JSONUtils.quote( doc.get( "link" ) ) + ",";
			output += "\"date\":" + JSONUtils.quote( doc.get( "pubdate" ) ) + ",";
			output += "\"host\":" + JSONUtils.quote( doc.get( "host" ) ) + ",";
			output += "\"score\":" + JSONUtils.quote( doc.get( "score" ) ) + ",";
			output += "\"body\":" + JSONUtils.quote( doc.get( "body" ) ) + ",";
			output += "\"description\":" + JSONUtils.quote( desc );
			output += "},";
		}
		if( output.length( ) != 1 )
			output = output.substring( 0, output.length( ) - 1 );
		output += "]";
		return output;
	}

	//print results
	public void printHits(ArrayList<Document> hits){
		try{
			System.out.println("Found " + hits.size( ) + " hits.");
			for( Document d : hits ) {
			    System.out.println( d.get("pubdate")+ ". " + d.get("link") + "\t" + d.get("title")+" --- ");
			}	
		}catch(Exception e){e.printStackTrace();}
	}
	
	public static void main(String[] args) throws IOException{
		Searcher s = new Searcher( );
		ArrayList<Document> hits = s.query( "irish and government and ( government or irish )", "today" );
		s.printHits( hits );
	}
	
}
