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


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by naren on 28/1/15.
 */
public class CorefManager {
    
    public Map<String,Map<String,Set<String>>> getCorefForSelectedEntites(String content,Set<String> pEntities,Set<String> oEntities, Map<String,String> corefConfig)
    {
        Map<String,Map<String, Set<String>>> coref_map = new HashMap<String,Map<String, Set<String>>>();
        //check for NPE
        String CorefMethod = "";
        if(corefConfig.get("CorefMethod") != null) {
            CorefMethod = corefConfig.get("CorefMethod");
        }
        else
        {
            CorefMethod = "SUBSTRING";
        }

        if((corefConfig.get("Person")!=null)&&(corefConfig.get("Person")== "TRUE"))
        {
            PersonCoref coref = new PersonCoref();
            coref_map.put("Person",coref.getPersonCoref(content,pEntities,CorefMethod));
        }
        if((corefConfig.get("Organization")!=null) && (corefConfig.get("Organization")== "TRUE"))
        {
            OrganizationCoref coref = new OrganizationCoref();
            coref_map.put("Organization",coref.getOrganizationCoref(content,oEntities,CorefMethod));
        }
        return coref_map;

    }


}
