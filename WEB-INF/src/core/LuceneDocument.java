package core;

import java.text.ParseException;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.DateTools;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LuceneDocument {

	public String title;
	private String description;
	private String link;
        private String creator;
        private String pubdate;
        private String body;
        private String host;
	private Document doc;
	
	public LuceneDocument(String t, String d, String l, String p, String b, String h) throws ParseException{
		this.title= t;
		this.description = d;
                this.host = h;
		this.link = l;
                this.pubdate = ConvertDate(p);
                this.body = b;
                

	}
        
        private String ConvertDate(String d) throws ParseException{
            String timezone;
            if(d.charAt(d.length()-1) == 'T'){
                timezone = "G";
            }
            else{timezone = "+1";}
            
            if(host.equals("www.rte.ie")){
                d = d.substring(5,24);
                if(d.charAt(1) == ' '){
                    d = '0' + d;
                }
            }
            else{
                d=d.substring(5, 25);
            }
            Date date = new SimpleDateFormat("dd MMM yyyy hh:mm:ss",Locale.ENGLISH).parse(d);
            String luceneDate = DateTools.dateToString(date,DateTools.Resolution.HOUR);
            System.out.println(luceneDate);
            return luceneDate;
        }
	
	public Document getLuceneDoc(){
            doc = new Document();
            
            doc.add(new TextField("title", title, Field.Store.YES));
	    doc.add(new TextField("description", description, Field.Store.YES));
	    doc.add(new StringField("link", link, Field.Store.YES));
            //doc.add(new TextField("creator", creator, Field.Store.YES));
            doc.add(new StringField("pubdate", pubdate, Field.Store.YES));
            doc.add(new TextField("body", body, Field.Store.YES));
            doc.add(new StringField("host", host, Field.Store.YES));
            
	    
	    return doc;
	}
        
        
}