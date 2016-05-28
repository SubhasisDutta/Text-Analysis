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
