//Shane Dawson 10383355
package core;

import java.net.*;
import java.io.*;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Collection;
import java.util.Vector;


public class QueryBasedSummarizer {
    private MoodDictionary dictionary;
    public QueryBasedSummarizer(MoodDictionary dictionary){
    this.dictionary = dictionary;
    }
    public static void main(String[] args) throws Exception{
        String query = "Fitzpatrick +money";//query
        WebNewsDocument doc = new WebNewsDocument("http://www.irishtimes.com/business/sectors/financial-services/fitzpatrick-says-he-paid-education-fees-of-anglo-officials-children-as-gifts-1.1326192");//doc
        QueryBasedSummarizer Qbs = new QueryBasedSummarizer(new MoodDictionary(new AffectMap("positive map tabbed.idx"),new AffectMap("negative map tabbed.idx")));
        Vector matches = Qbs.getMatchingsentences(query,doc);
        System.out.println(matches);
        System.out.println(Qbs.getBestMatchingSentence(query, doc));
    }
    public Vector getMatchingsentences(String query, WebNewsDocument doc) throws Exception{
        Vector matchingSentences = new Vector();
        Vector<String> docSentences = doc.getSentencesOfText();
        Hashtable words = new Hashtable();//hashtable to store query
        String[] bag = query.split("\\s+");//bag of words
        // Take the indiviual words in the query and store them in a hashtable with the value being the prefix.
        for (String j:bag){
           if (j.startsWith("+")){
               j = (j.substring(1));
               words.put(j, "+");
           }
           else if (j.startsWith("-")){
               j = j.substring(1);
               words.put(j, "-");
           }
           else if (j.startsWith("?")){
               j = j.substring(1);
               words.put(j, "?");
           }
           else{
                
               words.put(j, 0);  
           }
    }//split the doc into individual sentences and each sentence into words.
        for(Object sentence: docSentences){
            String[] sentenceWords = sentence.toString().split("\\s+");
            //iterate through the sentence for each word in the query
            for(Object key: words.keySet()){
                String root = dictionary.getRootForm(key.toString());// the root of query word
                String q = key.toString(); //the query word
                if (words.get(key).equals("0")){
                    for(String word: sentenceWords){ //iterate through the sentence
                        if (word.equals(q) || dictionary.getRootForm(word).equals(root)){ //if no prefix and words match, add the sentence
                            matchingSentences.add(sentence);
                    }
                  }}
                else if (words.get(key) == "+"){ //if + prefix if word and query  match or word or root form of word is a positve association of query or root form of query

                    for(String word: sentenceWords){
                        if (word.equals(q) ){
                            matchingSentences.add(sentence);
                        }
                        else if(dictionary.positive.containsWord(q)){ //if the query is in the dictionary get its assosiations
                            Vector posAssos = dictionary.positive.getAssosiationsOfWord(q);
                            for(Object pos:posAssos){//iterate through the assosiations
                                if(word.equals(pos.toString()) || dictionary.getRootForm(word).equals(dictionary.getRootForm(pos.toString()))){
                                    matchingSentences.add(sentence);
            }}}}}
                else if (words.get(key) == "-"){//if + prefix if word and query  match or word or root form of word is a negative association of query or root form of query
                    for(String word: sentenceWords){
                        if (word.equals(q) ){
                            matchingSentences.add(sentence);
                        }
                        else if(dictionary.negative.containsWord(q)){//if the query is in the dictionary get its assosiations
                            Vector<String> negAssos = dictionary.negative.getAssociationsOfWord(q);
                            for(Object neg:negAssos){ //iterate through the assosiations
                                if(word.equals(neg.toString())|| dictionary.getRootForm(word).equals(dictionary.getRootForm(neg.toString()))){
                                    matchingSentences.add(sentence);
            }}}}}
                else if (words.get(key) == "?"){ //if + prefix if word and query  match or word or root form of word is a positve association or negative assosiation of query or root form of query
                    for(String word: sentenceWords){
                        if (word.equals(q) ){
                            matchingSentences.add(sentence);
                        }                                                                   //same as above
                        else if(dictionary.negative.containsWord(q)){
                            Vector negAssos = dictionary.negative.getAssociationsOfWord(q);
                            for(Object neg:negAssos){
                                if(word.equals(neg.toString())|| dictionary.getRootForm(word).equals(dictionary.getRootForm(neg.toString()))){
                                    matchingSentences.add(sentence);
            }}}
                        else if(dictionary.positive.containsWord(q)){
                            Vector posAssos = dictionary.positive.getAssociationsOfWord(q);
                            for(Object pos:posAssos){
                                if(word.equals(pos.toString()) || dictionary.getRootForm(word).equals(dictionary.getRootForm(pos.toString()))){
                                    matchingSentences.add(sentence);
            }}}}}
        
            
       
    }
            
}
    Collection noDuplicates = new LinkedHashSet(matchingSentences);//remove duplicate sentences from the vector
    matchingSentences.clear();
    matchingSentences.addAll(noDuplicates);
    return matchingSentences; 
    }

    //Same code as above with the score being added to instead of adding sentences to the vector
       public int getMatchScore(String query, String sentence){
           int score = 0;
           Hashtable words = new Hashtable();
        String[] bag = query.split("\\s+");
        
        for (String j:bag){
           if (j.startsWith("+")){
               j = (j.substring(1));
               words.put(j, "+");
           }
           else if (j.startsWith("-")){
               j = j.substring(1);
               words.put(j, "-");
           }
           else if (j.startsWith("?")){
               j = j.substring(1);
               words.put(j, "?");
           }
           else{
                
               words.put(j, 0);  
           }
    }
        
        String[] sentenceWords = sentence.toString().split("\\s+");
            for(Object key: words.keySet()){
                String root = dictionary.getRootForm(key.toString());
                String q = key.toString();
                if (words.get(key) == 0){
                    for(String word: sentenceWords){
                        if (word.equals(q) || dictionary.getRootForm(word).equals(root)){
                            score += 10;
                    }
                  }}
                else if (words.get(key) == "+"){
                    for(String word: sentenceWords){
                        if (word.equals(q) ){
                            score += 10;
                        }
                        else if(dictionary.positive.containsWord(q)){
                            Vector posAssos = dictionary.positive.getAssociationsOfWord(q);
                            for(Object pos:posAssos){
                                if(word.equals(pos.toString()) || dictionary.getRootForm(word).equals(dictionary.getRootForm(pos.toString()))){
                                    score += 3;
            }}}}}
                else if (words.get(key) == "-"){
                    for(String word: sentenceWords){
                        if (word.equals(q) ){
                            score += 10;
                        }
                        else if(dictionary.negative.containsWord(q)){
                            Vector negAssos = dictionary.negative.getAssociationsOfWord(q);
                            for(Object neg:negAssos){
                                if(word.equals(neg.toString())|| dictionary.getRootForm(word).equals(dictionary.getRootForm(neg.toString()))){
                                   score += 3;
            }}}}}
                else if (words.get(key) == "?"){
                    for(String word: sentenceWords){
                        if (word.equals(q) ){
                            score += 10;
                        }
                        else if(dictionary.negative.containsWord(q)){
                            Vector negAssos = dictionary.negative.getAssociationsOfWord(q);
                            for(Object neg:negAssos){
                                if(word.equals(neg.toString())|| dictionary.getRootForm(word).equals(dictionary.getRootForm(neg.toString()))){
                                    score += 1;
            }}}
                        else if(dictionary.positive.containsWord(q)){
                            Vector posAssos = dictionary.positive.getAssociationsOfWord(q);
                            for(Object pos:posAssos){
                                if(word.equals(pos.toString()) || dictionary.getRootForm(word).equals(dictionary.getRootForm(pos.toString()))){
                                    score += 1;
            }}}}}
        
            
       
    }
           
           
           return score;
       }
       
       public String getBestMatchingSentence(String query, WebNewsDocument doc)throws Exception{
           String bestMatch = null; //the best match
           int currentBest = 0; //recors the current best score
           Vector sentences = this.getMatchingsentences(query, doc);
           for(Object sentence:sentences){ // for each sentence get the score and if the highest then make the current sentence our best, otherwise move on.
               int score = this.getMatchScore(query, sentence.toString());
               if (currentBest <= score){
                   currentBest = score;
                   bestMatch = sentence.toString();
               }
           }
           
           //return the best sentence
           return bestMatch;
       }
}
