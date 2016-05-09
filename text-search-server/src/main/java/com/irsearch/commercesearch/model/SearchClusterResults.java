package com.irsearch.commercesearch.model;

import java.util.List;

public class SearchClusterResults {
	private List<SearchEntity> results;
	private List<ClusterEntity> clusters;
	private String initialQuery;
	private int resultCount;
	private long executionTime;

	//TODO: Wyatt review list of Clusters add any other u feel relevant

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

	public List<ClusterEntity> getClusters() {
		return clusters;
	}

	public void setClusters(List<ClusterEntity> clusters) {
		this.clusters = clusters;
	}

	public String getInitialQuery() {
		return initialQuery;
	}

	public void setInitialQuery(String initialQuery) {
		this.initialQuery = initialQuery;
	}
	
	
	
}
