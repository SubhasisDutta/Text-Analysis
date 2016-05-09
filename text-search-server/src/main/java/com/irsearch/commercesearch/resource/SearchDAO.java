package com.irsearch.commercesearch.resource;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TreeMap;

import com.irsearch.commercesearch.service.cluster.Cluster;
import com.irsearch.commercesearch.service.cluster.ClusterFileUtil;
import org.apache.lucene.queryparser.classic.ParseException;
import org.json.JSONException;

import com.irsearch.commercesearch.model.SearchClusterResults;
import com.irsearch.commercesearch.model.SearchExpansionResults;
import com.irsearch.commercesearch.model.SearchResults;
import com.irsearch.commercesearch.service.queryretrival.Searcher;

public class SearchDAO implements iSearchDAO {

	public final static Cluster cluster = new Cluster();

	public final static HashMap<String, Double[]> clusterAssignments =
			(HashMap<String, Double[]>) ClusterFileUtil.tryToLoadModel("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/clusterAssignments");
	public final static HashMap<Integer, TreeMap<Double, String>> bestDocuments =
			(HashMap<Integer, TreeMap<Double, String>>) ClusterFileUtil.tryToLoadModel("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/bestDocuments");

	public final static HashMap<Integer, String> clusterTitles =
			(HashMap<Integer, String>) ClusterFileUtil.tryToLoadModel("/Users/wyatt.chastain/Code/UTD/CS6322/text-search/irsearch/src/main/java/com/irsearch/commercesearch/config/clusterTitles");

	public SearchResults getQuerySearch(String query){
		//TODO: This method will do what ever you want to do to get the result 
		//... for reference pls chcl SearchStubDAO 
		//... for checking if results are comming please use the SearchDAOTest and SearchStubDAOTest 
		//... u can run them as jUnit test files 
		long startTime = Calendar.getInstance().getTimeInMillis();
		SearchResults results = new SearchResults();
		Searcher sc = new Searcher();
		try {
			results = sc.searchFiles(query);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = Calendar.getInstance().getTimeInMillis();
		results.setExecutionTime(endTime-startTime);
		return results;
	}
	public SearchExpansionResults getQueryExpansionSearch(String query){
		//TODO: Ram and Mathew pls fill this up
		long startTime = Calendar.getInstance().getTimeInMillis();
		SearchExpansionResults results = new SearchExpansionResults();
		Searcher sc = new Searcher();
		try {
			results = sc.searchExpandedQuery(query);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		results.setInitialQuery(query);
		long endTime = Calendar.getInstance().getTimeInMillis();
		results.setExecutionTime(endTime-startTime);
		return results;
	}

	public SearchClusterResults getClusterSearch(String query){
		long startTime = Calendar.getInstance().getTimeInMillis();
		SearchResults originalResults = new SearchResults();
		SearchClusterResults results = new SearchClusterResults();
		Searcher sc = new Searcher();
		try {
			originalResults = sc.searchFiles(query);
			Cluster cluster = new Cluster();
			cluster.setBestDocuments(bestDocuments);
			cluster.setClusterAssignments(clusterAssignments);
			cluster.setClusterTitles(clusterTitles);
			System.out.println("Loaded cluster2");
			results = cluster.addResults(originalResults.getResults());
			System.out.println("Done");
			System.out.println(results.getResults().get(0).getUrl());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		results.setInitialQuery(query);
		long endTime = Calendar.getInstance().getTimeInMillis();
		results.setExecutionTime(endTime-startTime);
		return results;
	}
}
