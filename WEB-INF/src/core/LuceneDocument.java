package core;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;


public class LuceneDocument {

	public String title;
	private String description;
	private String link;
        private String creator;
        private String pubdate;
        private String body;
	private Document doc;
	
	public LuceneDocument(String t, String d, String l, String p, String b){
		this.title= t;
		this.description = d;
		this.link = l;
                this.pubdate = p;
                this.body = b;
	}
	
	public Document getLuceneDoc(){
            doc = new Document();
            
            doc.add(new TextField("title", title, Field.Store.YES));
	    doc.add(new TextField("description", description, Field.Store.YES));
	    doc.add(new StringField("link", link, Field.Store.YES));
            //doc.add(new TextField("creator", creator, Field.Store.YES));
            doc.add(new StringField("pubdate", pubdate, Field.Store.YES));
            doc.add(new TextField("body", body, Field.Store.YES));
            
	    
	    return doc;
	}
        
        
}