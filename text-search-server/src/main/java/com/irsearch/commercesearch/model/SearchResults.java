package com.irsearch.commercesearch.model;

import java.util.List;

public class SearchResults {
	private int resultCount;
	private List<SearchEntity> results;
	private long executionTime;
	private String initialQuery;

	public String getInitialQuery() {
		return initialQuery;
	}
	public void setInitialQuery(String initialQuery) {
		this.initialQuery = initialQuery;
	}
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
}
