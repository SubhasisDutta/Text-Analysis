/*******************************************************************************
 *   Copyright 2015 Serendio Inc. ( http://www.serendio.com/ )
 *   Author - Praveen Jesudhas
 *    
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package com.diskoverorta.osdep;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;


import java.io.Reader;
import java.io.StringReader;
import java.util.*;


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
    
}
