/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author shane
 */
public class Indexer {
    
    public static void main( String [ ] args ) throws IOException, Exception{
        
        
        ArrayList<LuceneDocument> RSSentries = new ArrayList();
		RSSReader reader = new RSSReader(RSSentries);
            try {
                reader.fetch("http://www.rte.ie/news/rss/news-headlines.xml");
                reader.fetch("http://feeds.guardian.co.uk/theguardian/rss");
                reader.fetch("http://rss.feedsportal.com/c/266/f/3492/index.rss");
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            } catch( Exception e ){
                System.out.println( e.getMessage( ) );            	
            }
            System.out.println(reader.entries.size());
            LuceneIndexer indexer = new LuceneIndexer(false);
            for(LuceneDocument doc:RSSentries){
                indexer.AddDoc(doc);
            }
            indexer.RemoveDuplicates();
            indexer.Close();
        
        
    }
    
}
