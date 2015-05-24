package com.diskoverorta.coreference;

import com.diskoverorta.entities.EntityManager;
import com.diskoverorta.osdep.SerendioNLP;
import com.diskoverorta.utils.EntityUtils;
import com.diskoverorta.vo.EntityObject;

import java.util.*;


/**
 * Created by naren on 28/1/15.
 */
public class PersonCoref {
	
	static SerendioNLP serendioNlp = null;
    public PersonCoref()
    {
        if(serendioNlp==null)
        {
        	serendioNlp = new SerendioNLP();
        }
    }

    public static Set<String> getPersonEntity(String content) {

        EntityManager entity = new EntityManager();
        Map<String,String> entityConfig = new HashMap<String,String>();

        entityConfig.put("Person","TRUE");
        EntityObject en = entity.getSelectedEntitiesForSentence(content, entityConfig);
        Set<String> entities = new HashSet<String>();
        // Add person entity to a list
        List<String> entities_raw = en.person;
        //trim and lower case
        for(String element : entities_raw)
        {
            entities.add(element.trim().toLowerCase());
        }
        System.out.println("Person entities : " + entities);
        return entities;

    }

    public static Map<String, Set<String>> getMentions(String text) {

        Set<String> entities =  getPersonEntity(text);
        return serendioNlp.getMentionsForEntities(entities,text);
    }

    public Map<String, Set<String>> getSubStringAlias(String content)
    {
        EntityUtils alias = new EntityUtils();
        Set<String> en_set = getPersonEntity(content);
        Map<String, Set<String>> mentions_map = alias.getAliasMapFromSet(en_set);
        return mentions_map;
    }
    public Map<String, Set<String>> getSubStringAlias(Set<String> enSet){

        // Using sub-string methods from utils and passing only entity set
        EntityUtils alias = new EntityUtils();
        Map<String, Set<String>> mentions_map = alias.getAliasMapFromSet(enSet);
        return mentions_map;

    }

    public Map<String, Set<String>> getPersonCoref(String content,Set<String> enSet, String corefMethod)
    {
        if( corefMethod.equals("SUBSTRING") == true)
            return getSubStringAlias(enSet);
        else
            return getMentions(content);
    }
}
