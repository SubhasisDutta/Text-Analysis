package com.irsearch.commercesearch.resource;

import com.irsearch.commercesearch.model.SearchClusterResults;
import com.irsearch.commercesearch.model.SearchExpansionResults;
import com.irsearch.commercesearch.model.SearchResults;

public interface iSearchDAO {
	public SearchResults getQuerySearch(String query);
	public SearchExpansionResults getQueryExpansionSearch(String query);
	public SearchClusterResults getClusterSearch(String query);
	
}
