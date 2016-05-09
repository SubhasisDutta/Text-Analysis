package com.irsearch.commercesearch.init;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import com.irsearch.commercesearch.config.SearchConstants;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class breakUpPage {
	

	public static Set<String> tempSet = new HashSet<String>();

	public static long counter = 0;

	public static int breakOutFiles(String crawlerFile, int i) {


		   try {
		      BufferedReader br = new BufferedReader(new FileReader(crawlerFile));
		      String line;

		      while ((line = br.readLine()) != null) {
		         JSONObject obj = new JSONObject(line).getJSONObject("Data");
		         String title = Jsoup.parse(obj.getString("Body")).title();
		         //JSONObject obj2 = new JSONObject(line);
		         if(tempSet.add(title)){
			         obj.put("TITLE", title);

			         PrintWriter out = new PrintWriter(SearchConstants.OUTPUT_SEPERATE_DATA_FILES+String.format("%06d.data", counter));
			         out.write(obj.toString());
			         out.close();
			         counter++;
		         }
		      }
		      br.close();
		      
		   } catch (Exception e) {
		      e.printStackTrace();
		   }

		return i;
	}
	
	public static void main(String[] args){		 
		File files = new File(SearchConstants.INPUT_DAT_FOLDER);
		int i = 0;
		for(File f : files.listFiles()){
			System.out.println("Starting " + f.getAbsolutePath());
			i = breakOutFiles(f.getAbsolutePath(), i);
		}
	}
}
