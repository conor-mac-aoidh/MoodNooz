package core;

import java.util.ArrayList;

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
	 */
	public String query( String query, String when ){
            
            ArrayList nested = new ArrayList();
            return query;
            
            

	}
        
        public static void main( String [ ] args ){
            Searcher s = new Searcher();
            System.out.println(s.query("london and car and ( car or cruise)","now"));
        }
		
}