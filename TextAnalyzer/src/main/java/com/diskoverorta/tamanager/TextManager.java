package com.diskoverorta.tamanager;

import com.diskoverorta.entities.EntityManager;
import com.diskoverorta.lifesciences.LSInterface;
import com.diskoverorta.osdep.SerendioNLP;
import com.diskoverorta.utils.EntityUtils;
import com.diskoverorta.vo.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//import com.serendio.diskoverer.lifesciences.document.LifeScienceDocument;


import java.util.List;

/**
 * Created by praveen on 11/11/14.
 */
public class TextManager
{
	static SerendioNLP serendioNlp = null;
    DocumentObject doc = null;
    public TextManager()
    {
    	if(serendioNlp==null)
        {
        	serendioNlp = new SerendioNLP();
        }
    }

    public DocSentObject tagTextAnalyticsComponents(String sDoc,TAConfig config)
    {
        DocSentObject doc = new DocSentObject();
        List<String> sentList = serendioNlp.splitSentencesINDocument(sDoc);

        for(int i=0; i < sentList.size(); i++)
        {
            SentenceObject temp = new SentenceObject();
            temp.sentenceText = sentList.get(i);

            if(config.analysisConfig.get("Entity") == "TRUE")
                temp.entities = (new EntityManager().getSelectedEntitiesForSentence(temp.sentenceText,config.entityConfig));

            doc.docSentences.add(temp);
        }
        return doc;
    }

    public DocumentObject aggregateDocumentComponentsFromSentences(DocSentObject docSent)
    {
        DocumentObject docObject = new DocumentObject();
        for (SentenceObject sentObj : docSent.docSentences)
        {
            docObject.entities.currency.addAll(sentObj.entities.currency);
            docObject.entities.date.addAll(sentObj.entities.date);
            docObject.entities.location.addAll(sentObj.entities.location);
            docObject.entities.organization.addAll(sentObj.entities.organization);
            docObject.entities.percent.addAll(sentObj.entities.percent);
            docObject.entities.person.addAll(sentObj.entities.person);
            docObject.entities.time.addAll(sentObj.entities.time);
        }
        docObject.entitiesMeta = EntityUtils.extractEntityMap(docObject.entities);
        return docObject;
    }

    public String tagTextAnalyticsComponentsINJSON(String sDoc,TAConfig config)
    {
        DocSentObject doc = tagTextAnalyticsComponents(sDoc,config);
        DocumentObject docObject = aggregateDocumentComponentsFromSentences(doc);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(docObject);
        return jsonOutput;
    }

    public String tagUniqueTextAnalyticsComponentsINJSON(String sDoc,TAConfig config)
    {
        String jsonOutput = "";
        APIOutput apiOut = new APIOutput();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if(config.analysisConfig.get("Entity") == "TRUE")
        {
            DocSentObject doc = tagTextAnalyticsComponents(sDoc, config);
            DocumentObject docObject = aggregateDocumentComponentsFromSentences(doc);
            EntityAPISet apiSet = EntityUtils.getEntitySet(docObject);
            apiOut.entity_general = apiSet;
        }

        if(config.analysisConfig.get("LSEntity") == "TRUE"){
        	//apiOut.entity_lifesciences = gson.fromJson(LSInterface.getLSEntitiesinJSON(sDoc),LifeScienceDocument.class);
        }
        return gson.toJson(apiOut);
    } 
}
