package com.diskoverorta.osdep;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by praveen on 15/10/14.
 */
class StanfordNLP
{
    public static StanfordCoreNLP pipeline = null;
    static AbstractSequenceClassifier<CoreLabel> ner7Classifier = null;
    static AbstractSequenceClassifier<CoreLabel> ner3Classifier = null;
    //URL url = StanfordNERTagger.class.getClassLoader().getResource("english.muc.7class.distsim.crf.ser.gz");
	String ner7classifierName = "stanfordnlpmodel/english.muc.7class.distsim.crf.ser.gz";
    String ner3classifierName = "stanfordnlpmodel/english.all.3class.distsim.crf.ser.gz";

    public StanfordNLP()
    {
        try {
            if (ner7Classifier == null)
                ner7Classifier = CRFClassifier.getClassifier(ner7classifierName);
            if(ner3Classifier == null)
                ner3Classifier = CRFClassifier.getClassifier(ner3classifierName);
            if(pipeline == null)
            {
                Properties props = new Properties();
                props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
                pipeline = new StanfordCoreNLP(props);
            }


        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public List<List<CoreLabel>> get7NERTaggedOutput(String str)
    {
        return ner7Classifier.classify(str);
    }

    public List<List<CoreLabel>> get3NERTaggedOutput(String str)
    {
        return ner3Classifier.classify(str);
    }

    public List<String> splitSentencesINDocument(String sDoc)
    {
        Reader reader = new StringReader(sDoc);
        DocumentPreprocessor dp = new DocumentPreprocessor(reader);
        List<String> sentenceList = new ArrayList<String>();
        Iterator<List<HasWord>> it = dp.iterator();

        while (it.hasNext())
        {
            StringBuilder sentenceSb = new StringBuilder();
            List<HasWord> sentence = it.next();
            for (HasWord token : sentence)
            {
                if(sentenceSb.length()>1)
                {
                    sentenceSb.append(" ");
                }
                sentenceSb.append(token);
            }
            sentenceList.add(sentenceSb.toString().trim());
        }
        return sentenceList;
    }
    
    
    
    /**
     * This function provides a list of entity for the particular tag from a given text.
     * 
     * @param sentence
     * @param entityType
     * @return
     */
    public List<String> getEntities(String sentence,String entityType,int typeNERT)
    {
        List<String> entityList = new ArrayList<String>();
        List<List<CoreLabel>> sentTags = null;
        if(typeNERT == 3){
        	sentTags=get3NERTaggedOutput(sentence);
        }else if(typeNERT == 7){
        	sentTags = get7NERTaggedOutput(sentence);
        }else{
        	return null;
        }
        		
        for (List<CoreLabel> lcl : sentTags)
        {
            for (int i = 0; i < lcl.size(); i++)
            {
                String key = lcl.get(i).get(CoreAnnotations.AnswerAnnotation.class);
                if(key.equals(entityType) == true)
                {
                    String temp1 = "";
                    while(key.equals(entityType) == true)
                    {
                        temp1 = temp1 + lcl.get(i).originalText() + " ";
                        i++;
                        if(i < lcl.size())
                            key = lcl.get(i).get(CoreAnnotations.AnswerAnnotation.class);
                        else
                            break;
                    }
                    temp1 = temp1.trim();
                    entityList.add(temp1);
                }
            }
        }
        return entityList;
    }
    
    public List<String> getEntities(String sentence,String entityType,int typeNERT,int cntNo)
    {
        List<String> entityList = new ArrayList<String>();
        List<List<CoreLabel>> sentTags = null;
        if(typeNERT == 3){
        	sentTags=get3NERTaggedOutput(sentence);
        }else if(typeNERT == 7){
        	sentTags = get7NERTaggedOutput(sentence);
        }else{
        	return null;
        }
        for (List<CoreLabel> lcl : sentTags)
        {
            for (int i = 0; i < lcl.size(); i++)
            {
                String key = lcl.get(i).get(CoreAnnotations.AnswerAnnotation.class);
                if(key.equals(entityType) == true)
                {
                    String temp1 = "";
                    int cnt =0;
                    while(key.equals(entityType) == true)
                    {
                        if(cnt >= cntNo)
                            break;
                        cnt++;
                        temp1 = temp1 + lcl.get(i).originalText() + " ";
                        i++;
                        if(i < lcl.size())
                            key = lcl.get(i).get(CoreAnnotations.AnswerAnnotation.class);
                        else
                            break;

                    }
                    temp1 = temp1.trim();
                    entityList.add(temp1);
                }
            }
        }
        return entityList;
    } 
    
    public static void main(String args[]) {
        String sample2 = "Investigation into the Saradha chit fund scam has so far not revealed any terror link with Bangladesh, the Centre said today, days after the BJP had alleged such a connection.\n" +
                "\n" +
                "â€œThe investigation has so far not revealed any such transaction where money was routed to Bangladesh to fund terrorist activities,â€� Union Minister of State for Personnel Jitendra Singh told the Lok Sabha in a written response.\n" +
                "\n" +
                "BJP chief Amit Shah had alleged that Saradha chit fund money had been used in the October 2, 2014 Bardhaman blast, which is being probed for link with the Jamaat-ul-Mujahideen Bangladesh (JMB) terror outfit.\n" +
                "\n" +
                "â€œSaradha chit fund money was used in the Burdwan (Bardhaman) blast. The NIA is not being allowed to probe the blast properly. Hurdles are being created. It is being done in order to save TMC leaders who are involved in the blast,â€� Mr. Shah had said, attacking the Trinamool Congress, at a BJP rally in Kolkata.\n" +
                "\n" +
                "The Union Minister was asked whether the government has sought details of the probe into the Saradha chit fund scam after reports indicated that a part of the money was routed to Bangladesh to fund terror activities. Mr. Singh replied that government had not sought details of the probe.\n" +
                "\n" +
                "To another question on whether the Saradha chief has admitted that he paid large sums to several people to influence the case in his favour, the Minister said, â€œThe matter is under investigation.â€�";
//        StanfordNLP obj = new StanfordNLP();
//        System.out.println(obj.splitSentencesINDocument(sample2));

    }
}
