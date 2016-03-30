package com.diskoverorta.coreference;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.diskoverorta.utils.WriteToCSV;
import com.diskoverorta.vo.TAConfig;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class CorefManagerTest {

	@Test
	public void testGetCorefForSelectedEntites() {
		/*
		TAConfig config = new TAConfig();
        config.analysisConfig.put("Coref","TRUE");
        config.corefConfig.put("Person","TRUE");
        config.corefConfig.put("Organization", "FALSE");
        config.corefConfig.put("CorefMethod","SUBSTRING");
        String text = "Teresa H Meng founded Atheros communications. Atheros communications is also called as atheros inc. or simply atheros. \nMeng served on the board of Atheros. \nCharles barratt was the CEO of Atheros inc., Barratt and Meng were directors of Atheros.";
        try {
            CorefManager coref = new CorefManager();
            String content = Files.toString(new File("/home/naren/Desktop/kreiger1_trimmed.txt"), Charsets.UTF_8);
            Map<String, Set<String>> coref_map = coref.getCorefForSelectedEntites(content, config.corefConfig);
            for(String temp : coref_map.keySet())
            {
                if(!coref_map.get(temp).isEmpty() && !coref_map.get(temp).contains(temp)) {
                    System.out.println(temp + " : " + coref_map.get(temp));
                }
            }
            WriteToCSV csv = new WriteToCSV();
            csv.writeMapAsCSV(coref_map);
        } catch (IOException e) {
            e.printStackTrace();
        } 
		 */
		fail("Not yet implemented");
	}

}
