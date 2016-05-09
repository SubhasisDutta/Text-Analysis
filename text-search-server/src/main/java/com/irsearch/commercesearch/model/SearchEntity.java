package com.irsearch.commercesearch.model;

public class SearchEntity {
	private String title;
	private String description;
	private String url;
	private String imageUrl;
	
	public SearchEntity(){
		this.url = "";
		this.title = "";
		this.description = "";
		this.imageUrl ="";
	}
	
	public SearchEntity(String url, String title, String description,String imageUrl) {		
		this.url = url;
		this.title = title;
		this.description = description;
		this.imageUrl =imageUrl;
	}
	public SearchEntity(String url, String title, String description) {		
		this.url = url;
		this.title = title;
		this.description = description;
		this.imageUrl ="";
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
