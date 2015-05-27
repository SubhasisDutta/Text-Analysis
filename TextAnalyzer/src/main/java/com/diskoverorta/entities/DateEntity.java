package com.diskoverorta.entities;

import java.util.List;
import com.diskoverorta.osdep.SerendioNLP;

/**
 * Created by praveen on 15/10/14.
 * Edited by Subhasis : added serendioNLP, removed reference to stanfordNLP.
 */
public class DateEntity implements BaseEntity
{
	public List<String> getEntities(String sentence)
    {
    	SerendioNLP nlp = new SerendioNLP();        
        return nlp.getEntities(sentence,"DATE",7);    	
    }    
	public List<String> getEntities(String sentence,SerendioNLP nlp)
    {    	     
        return nlp.getEntities(sentence,"DATE",7);    	
    } 
}
