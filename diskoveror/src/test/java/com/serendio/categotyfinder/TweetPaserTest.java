package com.serendio.categotyfinder;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TweetPaserTest {

	/*@Test
	public void testTweetsCategoriser() {
		List<String> tweets = new ArrayList<String>();
		tweets.add("Narendra Modi is the 14th Prime Minister of India.");
		tweets.add("Chilly chicken is a Chineese dish.");
		tweets.add("Avengers : The Age of Ultron will release on May 1 2015.");
		tweets.add("Nike shoes are great for running.");
		TweetPaser t = new TweetPaser();
		Map<String,Map<String,List<String>>>m=t.tweetsCategoriser(tweets);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(m));
		assertEquals("","");
	}*/
	/*
	@Test
	public void testTweetCategories() {
		TweetPaser t = new TweetPaser();
		try{
			FileInputStream fstream = new FileInputStream("sample_tweets.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));			
			File file = new File("result_output1.txt");			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}						
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  System.out.println (strLine);
			  bw.write(strLine+'\n');
			  Map<String,List<String>> m = t.tweetCategories(strLine);
			  Gson gson = new GsonBuilder().setPrettyPrinting().create();
			  System.out.println(gson.toJson(m));
			  bw.write(gson.toJson(m)+'\n');
			}
			//Close the input stream
			br.close();
			bw.close();			
			assertEquals("","");
		}catch(Exception e){
			
		}
	}
	*/
	@Test
	public void testTweetClassifier() {
		TweetPaser t = new TweetPaser();
		try{
			FileInputStream fstream = new FileInputStream("sample_tweets.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));			
			File file = new File("result_output.txt");			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}						
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			String strLine;
			//Read File Line By Line
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console	
			  boolean writeToFile=true;	 
			  //System.out.println (strLine+'\n');
			  TweetCategoryModel tm = t.tweetClassifier(strLine);
			  Set<String> m = tm.getCategories();
			  for(String s:m){
				  if(s.equals(TweetPaser.NO_KEY_FOUND_IN_TWEET)){
					  writeToFile=false;
				  }				  
			  }
			  if(writeToFile){				  
				  bw.write(strLine+'\n');				  
				  bw.write(gson.toJson(tm)+'\n');
			  }			  
			}
			//Close the input stream
			br.close();
			bw.close();		
			System.out.println("COMPLEATE");
			assertEquals("","");
		}catch(Exception e){
			
		}
	}

}
