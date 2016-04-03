/*******************************************************************************
 *   Copyright 2016 Serendio Inc. ( http://www.serendio.com/ )
 *   Author - Subhasis Dutta
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
package com.diskoverorta.entities;


import com.diskoverorta.osdep.SerendioNLP;
import com.diskoverorta.pyinterface.ThriftClient;
import com.diskoverorta.vo.EntityObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by praveen on 17/10/14.
 */
public class EntityManager
{	
    //to do : Get all selected entities at runtime and process only them, Make use of array of baseentities
    static SerendioNLP serendioNlp = null;
    static ThriftClient pyClient = null;
    
    public EntityManager()
    {
        if(serendioNlp==null)
        {
        	serendioNlp = new SerendioNLP();
        }
        if(pyClient==null)
        {
            pyClient = new ThriftClient("localhost",19090);
        }
    } 
    
    public EntityManager(String packeagChoice)
    {
        if(serendioNlp==null)
        {
        	serendioNlp = new SerendioNLP(packeagChoice);
        }
        if(pyClient==null)
        {
            pyClient = new ThriftClient("localhost",19090);
        }
    }

    public String getALLDocumentEntitiesINJSON(String sDoc)
    {
        List<EntityObject> allEntities = getALLEntitiesForDocument(sDoc);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(allEntities);
        return jsonOutput;
    }

    public List<EntityObject> getALLEntitiesForDocument(String sDoc)
    {
        List<EntityObject> allEntities = new ArrayList<EntityObject>();
        List<String> sentences = serendioNlp.splitSentencesINDocument(sDoc);
        for(String temp : sentences)
        {
            allEntities.add(getALLEntitiesForSentence(temp));
        }
        return allEntities;
    }

    private EntityObject getALLEntitiesForSentence(String sSentence)
    {
        EntityObject entities = new EntityObject();
        entities.sentence = sSentence;
        entities.person = (new PersonEntity()).getEntities(sSentence,serendioNlp);
        entities.organization = (new OrganizationEntity()).getEntities(sSentence,serendioNlp);
        entities.location = (new LocationEntity()).getEntities(sSentence,serendioNlp);
        entities.date = (new DateEntity()).getEntities(sSentence,serendioNlp);
        entities.time = (new TimeEntity()).getEntities(sSentence,serendioNlp);
        entities.currency = (new CurrencyEntity()).getEntities(sSentence,serendioNlp);
        entities.percent = (new PercentEntity()).getEntities(sSentence,serendioNlp);
        
        //For Seniment,topics and keywods
        entities.sentimentScore = pyClient.getSentimentScore(sSentence);
        entities.keyWords = pyClient.getKeywords(sSentence);
        entities.topics = pyClient.getTopics(sSentence);
        
        

        return entities;
    }

    public EntityObject getSelectedEntitiesForSentence(String sSentence,Map<String,String> entityConfig)
    {
        EntityObject entities = new EntityObject();              

        if(entityConfig.get("Person")== "TRUE")
            entities.person = (new PersonEntity()).getEntities(sSentence);
        if(entityConfig.get("Organization")== "TRUE")
            entities.organization = (new OrganizationEntity()).getEntities(sSentence);
        if(entityConfig.get("Location")== "TRUE")
            entities.location = (new LocationEntity()).getEntities(sSentence);
        if(entityConfig.get("Date")== "TRUE")
            entities.date = (new DateEntity()).getEntities(sSentence);
        if(entityConfig.get("Time")== "TRUE")
            entities.time = (new TimeEntity()).getEntities(sSentence);
        if(entityConfig.get("Currency")== "TRUE")
            entities.currency = (new CurrencyEntity()).getEntities(sSentence);
        if(entityConfig.get("Percent")== "TRUE")
            entities.percent = (new PercentEntity()).getEntities(sSentence);

        return entities;
    }
}
