package core;

import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.index.Term;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Searcher {
    
        
	
	/**
	 * query
	 * 
	 * returns a list of results fetched from the lucene
	 * IR engine for the search query and within the search
	 * date
	 * 
	 * when - one of "today, week, month, year"
	 * 
	 * example query (passed in expanded form):
	 * 
	 * london +car -train
	 * 
	 * expanded:
	 * 
	 * london and car and ( car or cruise or babe or home or star or commission or team or cat or life or sport or movie or cruiser or 
	 * mansion or attraction or garden or pet or yacht or diamond or innovation or personality or love or guide or friend or speed or 
	 * score or landscape or parent or flower or player or luxury or sculpture or apple or superstar or sword or rally or accommodation
	 * or communication or benefit or enthusiast or prize or cake or villa or hope or fashion or craft or gift or restoration or vacation )
	 * and train and ( train or challenge or fight or dog or animal or terminal or battle or break or soldier or serf or gap or drill or
	 * problem )
	 * 
	 * @param query
	 * @param when
	 * @return
         * 
	 */
    int hitsPerPage = 10;
    Directory index;
    IndexReader reader;
    IndexSearcher searcher;
    TopScoreDocCollector collector;// = TopScoreDocCollector.create(hitsPerPage, true);
    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

        public Searcher(){
            try{
			index = FSDirectory.open(new File("index"));   //open the index 	
			reader = DirectoryReader.open(index);//read the index
			searcher = new IndexSearcher(reader);//search the index
		}catch(Exception e){e.printStackTrace();}
	
        }
        public ScoreDoc[] search(BooleanQuery q){
		ScoreDoc[] hits = null;
		collector = TopScoreDocCollector.create(hitsPerPage, true);
		try{
			searcher.search(q, collector);
			hits = collector.topDocs().scoreDocs;
		}catch(Exception e){e.printStackTrace();}
		return hits;
	}
	
	//returns a Query for the input query string and the given field
	public Query getQuery(String q, String field){
		try{
			return new QueryParser(Version.LUCENE_42, field, analyzer).parse(q);
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	// returns a Document given an id
	public Document get( int docId ) throws IOException	{
		return searcher.doc( docId );
	}
	
	//print results
	public void printHits(ScoreDoc[] hits){
		try{
			System.out.println("Found " + hits.length + " hits.");
			for(int i=0;i<hits.length;++i) {
			    int docId = hits[i].doc;
			    float score = hits[i].score;			 
			    Document d = searcher.doc(docId);			    
			    System.out.println((i + 1) + d.get("pubdate")+ ". " + d.get("link") + "\t" + d.get("title")+" --- "+score);
			}	
		}catch(Exception e){e.printStackTrace();}
	}
    
     
	public BooleanQuery query( String query, String when ){
            
            //ArrayList<BooleanQuery>
            
            BooleanQuery booleanQuery = new BooleanQuery();
            
		String[ ] terms = query.split( " and " );
		for( String s : terms ){
                    //System.out.println(s);
                    if(s.charAt(0) == '('){
                        BooleanQuery innerQuery = new BooleanQuery();
                        s = s.substring(2,s.length()-2);
                        String[ ] inner = s.split(" or ");
                        for(String i: inner){
                            booleanQuery.add(new TermQuery(new Term("body", i)),BooleanClause.Occur.SHOULD);
                            //System.out.println(i);
                        }
                        booleanQuery.add(innerQuery,BooleanClause.Occur.MUST);
                    }
                    else{
                        booleanQuery.add(new TermQuery(new Term("body", s)),BooleanClause.Occur.MUST);
                    }
			
                    
		}
                
                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                int iCurDate;
                Date limDate = date;
                String currentDate = DateTools.dateToString(date,DateTools.Resolution.HOUR);
                iCurDate = Integer.parseInt(currentDate);
                switch(when){
                    case "today": limDate.setDate(limDate.getDate()-1);
                        break;
                    case "week": limDate.setDate(limDate.getDate()-7);
                        break;
                    case "month": limDate.setMonth(limDate.getMonth()-1);
                        break;
                    case "year": limDate.setYear(limDate.getYear()-1);
                        break;
                }
                
                String limitDate = DateTools.dateToString(limDate,DateTools.Resolution.HOUR);
                System.out.print(limitDate);
                
                Term now = new Term("pubdate",currentDate);
                Term limit = new Term("pubdate",limitDate);
                TermRangeQuery dateQuery = new TermRangeQuery("pubdate",limit.bytes(),now.bytes(),true,true);
                booleanQuery.add(dateQuery,BooleanClause.Occur.MUST);
                String b =booleanQuery.toString();
                    System.out.println(b);
                    
                    return booleanQuery;
	}
        
        public static void main( String [ ] args ){
            Searcher s = new Searcher();
            BooleanQuery b = s.query("car","year");
            s.printHits(s.search(b));
        }
		
}