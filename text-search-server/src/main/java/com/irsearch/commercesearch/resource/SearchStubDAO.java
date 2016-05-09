package com.irsearch.commercesearch.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.irsearch.commercesearch.model.ClusterEntity;
import com.irsearch.commercesearch.model.SearchClusterResults;
import com.irsearch.commercesearch.model.SearchEntity;
import com.irsearch.commercesearch.model.SearchExpansionResults;
import com.irsearch.commercesearch.model.SearchResults;

public class SearchStubDAO implements iSearchDAO{
	public SearchResults getQuerySearch(String query){
		long startTime = Calendar.getInstance().getTimeInMillis();
		SearchResults results = new SearchResults();
		
		List<SearchEntity> entity = new ArrayList<SearchEntity>();
		for(int i=0;i<50;i++){
			SearchEntity e= new SearchEntity();
			e.setTitle("Search Result with Rank "+(i+1));
			e.setDescription("Version 4.4.4 KitKat appeared as a security-only update; it was released on June 19, "
					+ "2014, shortly after 4.4.3 was released. As of November 2014 [update],"
					+ " the newest version of the Android operating system, ."
					+ "Android 5.0 Lollipop, is available for selected devices.");
			e.setImageUrl("");
			e.setUrl("https://en.wikipedia.org/wiki/Android_(operating_system)");
			entity.add(e);
		}
		results.setResults(entity);
		results.setInitialQuery(query);
		results.setResultCount(1234567);
		long endTime = Calendar.getInstance().getTimeInMillis();
		results.setExecutionTime(endTime-startTime);
		return results;
	}
	public SearchExpansionResults getQueryExpansionSearch(String query){
		long startTime = Calendar.getInstance().getTimeInMillis();
		SearchExpansionResults results = new SearchExpansionResults();
		
		results.setInitialQuery(query);
		results.setExpandedQuery(query+ "blah blah blah blah");
		
		List<SearchEntity> entity = new ArrayList<SearchEntity>();
		for(int i=0;i<50;i++){
			SearchEntity e= new SearchEntity();
			e.setTitle("Query Expansion Result with Rank "+(i+1));
			e.setDescription("Version 4.4.4 KitKat appeared as a security-only update; it was released on June 19, "
					+ "2014, shortly after 4.4.3 was released. As of November 2014 [update],"
					+ " the newest version of the Android operating system, ."
					+ "Android 5.0 Lollipop, is available for selected devices.");
			e.setImageUrl("");
			e.setUrl("https://en.wikipedia.org/wiki/Android_(operating_system)");
			entity.add(e);
		}
		
		results.setResults(entity);
		
		results.setResultCount(1234567);
		long endTime = Calendar.getInstance().getTimeInMillis();
		results.setExecutionTime(endTime-startTime);
		return results;
	}
	public SearchClusterResults getClusterSearch(String query){
		long startTime = Calendar.getInstance().getTimeInMillis();
		SearchClusterResults results = new SearchClusterResults();
		
		//results.setInitialQuery(query);
		
		List<SearchEntity> entity = new ArrayList<SearchEntity>();
		for(int i=0;i<50;i++){
			SearchEntity e= new SearchEntity();
			e.setTitle("Search after clustering with Rank "+(i+1));
			e.setDescription("Version 4.4.4 KitKat appeared as a security-only update; it was released on June 19, "
					+ "2014, shortly after 4.4.3 was released. As of November 2014 [update],"
					+ " the newest version of the Android operating system, ."
					+ "Android 5.0 Lollipop, is available for selected devices.");
			e.setImageUrl("");
			e.setUrl("https://en.wikipedia.org/wiki/Android_(operating_system)");
			entity.add(e);
		}
		
		results.setResults(entity);
		
		List<ClusterEntity> clusters = new ArrayList<ClusterEntity>();
		for(int i=0; i< 5 ;i++){
			ClusterEntity c = new ClusterEntity(i+1);
			c.setClusterNo(i+1);
			Random rand = new Random();
			int randomNum = rand.nextInt((50 - 2) + 1) + 2;
			c.setSize(randomNum);
			c.setTitle("Cluster Name "+(i+1));
			List<SearchEntity> ce = new ArrayList<SearchEntity>();
			for(int j=0;j< randomNum;j++){
				SearchEntity ec= new SearchEntity();
				ec.setTitle("Cluster Name "+(i+1)+ "Search after clustering with Rank "+(j+1));
				ec.setUrl("https://en.wikipedia.org/wiki/Android_(operating_system)");
				//ec.setDescription("");
				//ec.setImageUrl("");
				ce.add(ec);
			}
			clusters.add(c);
		}
		
		results.setClusters(clusters);
		
		results.setResultCount(1234567);
		long endTime = Calendar.getInstance().getTimeInMillis();
		results.setExecutionTime(endTime-startTime);
		return results;
	}
}
