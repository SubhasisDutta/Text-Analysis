package com.irsearch.commercesearch.model;

import java.util.List;

public class SearchExpansionResults {
	private int resultCount;
	private List<SearchEntity> results;
	private long executionTime;
	private String initialQuery;
	private String expandedQuery;
	
	//TODO: Mathew - pls add any more attributes that you think needs to be displayed
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public List<SearchEntity> getResults() {
		return results;
	}
	public void setResults(List<SearchEntity> results) {
		this.results = results;
	}
	public long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}
	public String getInitialQuery() {
		return initialQuery;
	}
	public void setInitialQuery(String initialQuery) {
		this.initialQuery = initialQuery;
	}
	public String getExpandedQuery() {
		return expandedQuery;
	}
	public void setExpandedQuery(String expandedQuery) {
		this.expandedQuery = expandedQuery;
	}
	
	
	
}
