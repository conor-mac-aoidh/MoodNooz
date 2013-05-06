package core;

import org.apache.lucene.index.Term;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import java.util.Date;

public class Searcher extends LuceneSearcher{

	public ScoreDoc[] search(BooleanQuery q) {
		ScoreDoc[] hits = null;
		collector = TopScoreDocCollector.create(hitsPerPage, true);
		try {
			searcher.search(q, collector);
			hits = collector.topDocs().scoreDocs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hits;
	}

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
	 * 
	 */
	public ScoreDoc[] query(String query, String when) {

		BooleanQuery booleanQuery = new BooleanQuery();

		String[] terms = query.split(" and ");
		for (String s : terms) {
			// System.out.println(s);
			if (s.charAt(0) == '(') {
				BooleanQuery innerQuery = new BooleanQuery();
				s = s.substring(2, s.length() - 2);
				String[] inner = s.split(" or ");
				for (String i : inner) {
					booleanQuery.add(new TermQuery(new Term("body", i)),
							BooleanClause.Occur.SHOULD);
					// System.out.println(i);
				}
				booleanQuery.add(innerQuery, BooleanClause.Occur.MUST);
			} else {
				booleanQuery.add(new TermQuery(new Term("body", s)),
						BooleanClause.Occur.MUST);
			}

		}

		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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

		return this.search(booleanQuery);
	}

	public static void main(String[] args) {
		Searcher s = new Searcher();
		ScoreDoc[] d = s.query("government", "year");
		s.printHits(d);
	}

}