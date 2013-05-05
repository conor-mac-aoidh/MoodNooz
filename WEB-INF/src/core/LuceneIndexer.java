
package core;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author shane
 */
public class LuceneIndexer {
    
    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42, analyzer);
    IndexWriter writer;
    Directory index;
    //IndexReader reader = DirectoryReader.open(index);//read the index
    
    public LuceneIndexer(boolean create) throws IOException{
        
        index = FSDirectory.open(new File("index"));
        if (create) {
	    		config.setOpenMode(OpenMode.CREATE);
	    	} else {
	    		config.setOpenMode(OpenMode.CREATE_OR_APPEND);
	    	}
	    	writer = new IndexWriter(index, config);
        
    }
    
    public void AddDoc(LuceneDocument doc) throws IOException{
        if(doc != null){
            Document luceneDoc = doc.getLuceneDoc();
            writer.addDocument(luceneDoc);
        }
    }
    
    public void Close(){
        try {
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LuceneIndexer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void RemoveDuplicates(){
            
            //to do
        }
    
}
