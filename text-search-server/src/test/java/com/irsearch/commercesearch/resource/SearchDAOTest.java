package com.irsearch.commercesearch.resource;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.irsearch.commercesearch.model.SearchClusterResults;
import com.irsearch.commercesearch.model.SearchExpansionResults;
import com.irsearch.commercesearch.model.SearchResults;

public class SearchDAOTest {

	SearchDAO s = new SearchDAO();
	@Test
	public void testGetQuerySearch() {
		SearchResults r =s.getQuerySearch("chair and able blow price $100");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(r);
		//System.out.println(r.getResults().size());
		System.out.println(jsonOutput);
		assertEquals("", "");
	}

	@Test
	public void testGetQueryExpansionSearch() {
		SearchExpansionResults r = s.getQueryExpansionSearch("smarttv size 32 inch");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(r);
		System.out.println(jsonOutput);
		assertEquals("", "");
	}

	@Test
	public void testGetClusterSearch() {
		SearchClusterResults r = s.getClusterSearch("books on computer Science");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(r);
		System.out.println(jsonOutput);
		assertEquals("", "");
	}

}
