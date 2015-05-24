package com.diskoverorta.osdep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.diskoverorta.utils.ConfigurationUtils;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;

public class SerendioNLP {
	static String nlpPackage;
	static StanfordNLP stanfordNlp = null;
	static OpenNLP openNlp = null;
	
	public SerendioNLP(){
		nlpPackage=ConfigurationUtils.getNLPPackageChoice();
		if(nlpPackage.trim().equalsIgnoreCase("STANFORD_NLP")){
			stanfordNlp= new StanfordNLP();
		}else if(nlpPackage.trim().equalsIgnoreCase("OPEN_NLP")){
			openNlp = new OpenNLP();
		}else{
			// Do nothing
		}
	}
	public SerendioNLP(String packeagChoice){
		nlpPackage=packeagChoice;
		if(nlpPackage.trim().equalsIgnoreCase("STANFORD_NLP")){
			stanfordNlp= new StanfordNLP();
		}else if(nlpPackage.trim().equalsIgnoreCase("OPEN_NLP")){
			openNlp = new OpenNLP();
		}else{
			// Do nothing
		}
	}
	
	public List<String> getEntities(String text,String entity,int typeNERT){
		if(nlpPackage.trim().equalsIgnoreCase("STANFORD_NLP")){			
			return stanfordNlp.getEntities(text, entity,typeNERT);
		}else if(nlpPackage.trim().equalsIgnoreCase("OPEN_NLP")){
			return openNlp.getEntities(text, entity);
		}else{
			return null;
		}
	}
	public List<String> getEntities(String text,String entity,int typeNERT,int cnt){
		if(nlpPackage.trim().equalsIgnoreCase("STANFORD_NLP")){			
			return stanfordNlp.getEntities(text, entity,typeNERT,3);
		}else if(nlpPackage.trim().equalsIgnoreCase("OPEN_NLP")){
			return openNlp.getEntities(text, entity);
		}else{
			return null;
		}
	}
	
	public List<String> splitSentencesINDocument(String document){
		if(nlpPackage.trim().equalsIgnoreCase("STANFORD_NLP")){			
			return stanfordNlp.splitSentencesINDocument(document);
		}else if(nlpPackage.trim().equalsIgnoreCase("OPEN_NLP")){
			return openNlp.splitSentencesINDocument(document);
		}else{
			return null;
		}
	}
	
	public Map<String, Set<String>> getMentionsForEntities(Set<String> entities,String text){
		if(nlpPackage.trim().equalsIgnoreCase("STANFORD_NLP")){			
			// create an empty Annotation just with the given text
	        Annotation document = new Annotation(text);
	        // run all Annotators on this text
	        stanfordNlp.pipeline.annotate(document);       

	        Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
	        Map<String, Set<String>> mp = new HashMap<String, Set<String>>();
	        for (Integer temp : graph.keySet()) {
	            CorefChain c = graph.get(temp);
	            //CorefChain.CorefMention cm = c.getRepresentativeMention();
	            List<CorefChain.CorefMention> cms = c.getMentionsInTextualOrder();
	            List<String> cms_parsed = new ArrayList<String>();
	            // remove "in Sentences"
	            for (CorefChain.CorefMention each : cms) {
	                Pattern p = Pattern.compile("\"([^\"]*)\"");
	                Matcher m = p.matcher(each.toString());
	                while (m.find()) {
	                    cms_parsed.add(m.group(1));
	                }
	            }
	            //put it into a map container.
	            Set<String> tmp = new HashSet<String>();
	            if(entities.contains(cms_parsed.get(0).trim().toLowerCase())) { 
	            	//filters out the key that matches entity
	                for (String element : cms_parsed.subList(1, cms.size())) {
	                	//if(entities.contains(element.trim().toLowerCase()))   
	                	//filters out the values that matches entity
	                    tmp.add(element);
	                }
	                mp.put(cms_parsed.get(0), tmp);    //take the first element as key and rest (tmp) as values
	            }
	        }
	        return mp;
		}else if(nlpPackage.trim().equalsIgnoreCase("OPEN_NLP")){
			return null; // TODO
		}else{
			return null;
		}
	}
	
}
