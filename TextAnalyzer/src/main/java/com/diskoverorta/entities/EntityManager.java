package com.diskoverorta.entities;


import com.diskoverorta.osdep.SerendioNLP;
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
    public EntityManager()
    {
        if(serendioNlp==null)
        {
        	serendioNlp = new SerendioNLP();
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
        entities.person = (new PersonEntity()).getEntities(sSentence);
        entities.organization = (new OrganizationEntity()).getEntities(sSentence);
        entities.location = (new LocationEntity()).getEntities(sSentence);
        entities.date = (new DateEntity()).getEntities(sSentence);
        entities.time = (new TimeEntity()).getEntities(sSentence);
        entities.currency = (new CurrencyEntity()).getEntities(sSentence);
        entities.percent = (new PercentEntity()).getEntities(sSentence);

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
