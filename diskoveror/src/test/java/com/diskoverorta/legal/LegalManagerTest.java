package com.diskoverorta.legal;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class LegalManagerTest {

	@Test
	public void testTagLegalTextAnalyticsComponentsString() {
		LegalManager lobj = new LegalManager();
        String content = "";
        try {
            content = Files.toString(new File("temp1.txt"), Charsets.UTF_8);
            content = content.replace("\n"," ");
            content = content.replace("\r"," ");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        Map<String,String> config1 = new TreeMap<String,String>();
        config1.put("chunksize","5");
        System.out.println("Will fail because connction to database is hardcoded ... ");
        String s = lobj.tagLegalTextAnalyticsComponents(content,config1);
        System.out.println(s);		
	}

	@Test
	public void testTagLegalTextAnalyticsComponentsStringMapOfStringString() {
		fail("Not yet implemented");
	}
}
