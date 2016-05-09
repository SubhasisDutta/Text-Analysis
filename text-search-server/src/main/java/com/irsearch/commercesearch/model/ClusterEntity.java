package com.irsearch.commercesearch.model;

import java.util.List;

public class ClusterEntity {
	private int clusterNo;
	private String title;
	private int size;

	public ClusterEntity(){
		this.clusterNo = 0;
		this.title = "";
		this.size = 0;
	}


	public ClusterEntity(int clusterNo){
		this.clusterNo = clusterNo;
		this.title = "";
		this.size = 0;
	}

	public ClusterEntity(int clusterNo, String title, int size) {
		this.clusterNo = clusterNo;
		this.title = title;
		this.size = size;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getClusterNo() {
		return clusterNo;
	}
	public void setClusterNo(int clusterNo) {
		this.clusterNo = clusterNo;
	}
	
}
