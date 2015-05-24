package com.diskoverorta.osdep;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

class OpenNLP {

	public OpenNLP(){
		
	}
	
	public List<String> splitSentencesINDocument(String document){
		List<String> sentenceList = new ArrayList<String>();
		try{
			InputStream is = new FileInputStream("opennlpmodel/en-sent.bin");
			SentenceModel model = new SentenceModel(is);
			SentenceDetectorME sdetector = new SentenceDetectorME(model);
			
			String sentences[] = sdetector.sentDetect(document);
			is.close();
			for(String s : sentences){
				sentenceList.add(s);
			}
			return sentenceList;
		}catch(InvalidFormatException e1){
			e1.printStackTrace();
			return null;
		}catch(IOException e2){
			e2.printStackTrace();
			return null;
		}
	}
	
	public List<String> getEntities(String sentence,String entityType){
		if(entityType.equals("PERSON")){
			return findName(sentence,"opennlpmodel/en-ner-person.bin");
		}else if(entityType.equals("MONEY")){
			return findName(sentence,"opennlpmodel/en-ner-money.bin");
		}else if(entityType.equals("LOCATION")){
			return findName(sentence,"opennlpmodel/en-ner-location.bin");
		}else if(entityType.equals("DATE")){
			return findName(sentence,"opennlpmodel/en-ner-date.bin");
		}else if(entityType.equals("ORGANIZATION")){
			return findName(sentence,"opennlpmodel/en-ner-organization.bin");
		}else if(entityType.equals("PERCENT")){
			return findName(sentence,"opennlpmodel/en-ner-percentage.bin");
		}else if(entityType.equals("TIME")){
			return findName(sentence,"opennlpmodel/en-ner-time.bin");
		}else{
			return null;
		}		
	}
	
	public List<String> findName(String sentence,String modelFile){
		
		String [] tokens=null;
		try{
			InputStream is = new FileInputStream("opennlpmodel/en-token.bin");
			TokenizerModel model = new TokenizerModel(is);
			Tokenizer tokenizer = new TokenizerME(model);
			tokens = tokenizer.tokenize(sentence);	
			/*for(String t:tokens){
				System.out.println(t);
			}*/
			
		}catch(InvalidFormatException e1){
			e1.printStackTrace();
			return null;
		}catch(IOException e2){
			e2.printStackTrace();
			return null;
		}
		
		List<String> entityList = new ArrayList<String>();
		try{
			InputStream is = new FileInputStream(modelFile);
			TokenNameFinderModel model = new TokenNameFinderModel(is);
			is.close();
			NameFinderME nameFinder = new NameFinderME(model);	
			
			Span nameSpans[] = nameFinder.find(tokens);			
			for(Span s: nameSpans){
				//System.out.println(s.toString());
				StringBuilder sb = new StringBuilder();
				for(int i=s.getStart();i<s.getEnd();i++){
					sb.append(tokens[i]+" ");
				}
				sb.deleteCharAt(sb.length()-1);				
				entityList.add(sb.toString());				
			}
			return entityList;
		}catch(IOException e1){
			e1.printStackTrace();
			return null;
		}
	}
}
