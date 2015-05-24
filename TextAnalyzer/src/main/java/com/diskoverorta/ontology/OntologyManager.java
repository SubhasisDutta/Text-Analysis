package com.diskoverorta.ontology;

import com.diskoverorta.vo.TAConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by naren on 4/2/15.
 */
public class OntologyManager {

    static OntologyLookup ontology = new OntologyLookup();
    
    public static Map<String, Set<String>> getOntologyForSelectedTerms(String content, Map<String, String> ontologyConfig)
    {
        // gets the ontology for given terms like events or topics
        Map<String, Set<String>> ontology_map = new HashMap<String, Set<String>>();

        if ((ontologyConfig.get("Topics") != null) && (ontologyConfig.get("Topics") == "TRUE"))
        {
            ontology_map.put("Topics",ontology.matchOntologies(content,"topics"));
        }
        if ((ontologyConfig.get("Events") != null) && (ontologyConfig.get("Events") == "TRUE"))
        {
            ontology_map.put("Events",ontology.matchOntologies(content,"events"));
        }
        return ontology_map;
    }

}
