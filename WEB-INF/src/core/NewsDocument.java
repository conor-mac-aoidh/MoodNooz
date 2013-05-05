/**
 * NewsDocument, MoodNooz
 * 
 * a model to represent fetched news documents
 * 
 * @author	Conor Mac Aoidh <10339063@ucdconnect.ie>
 * class:	COMP30050 Software Engineering Project 3
 */

package core;


import java.net.*;
import java.io.*;
import java.util.Vector;
import java.lang.Object;
import java.text.BreakIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsDocument {
    URL url;

public NewsDocument(String url){
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(NewsDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        	
    
}


public String getRawText() throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inline;
        String out = "";
        while ((inline = reader.readLine()) != null){
            out = out + inline;}
        return out;
    }

public String getBodyText() throws Exception{
        String website = getWebSite();
        System.out.print(website);
        String text = getRawText();
        Document doc = Jsoup.parse(text);
        if(website.equals("www.rte.ie")){
           return doc.select("div.body_text").html(); 
        }
        else if(website.equals("feeds.guardian.co.uk")){
            return doc.select("div#article-body-blocks").html();
        }
        else if(website.equals("rss.feedsportal.com")){
            //String top = doc.select("span.top").text();
            return doc.select("div.body").html(); 
        }
        else{return "";}
        
}
	
 public String getWebSite() throws Exception{
        return url.getHost().toString();
    }
}