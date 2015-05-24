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
