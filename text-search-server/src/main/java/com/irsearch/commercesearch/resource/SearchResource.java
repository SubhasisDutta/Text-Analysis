package com.irsearch.commercesearch.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.irsearch.commercesearch.model.SearchClusterResults;
import com.irsearch.commercesearch.model.SearchExpansionResults;
import com.irsearch.commercesearch.model.SearchResults;

@Path("/api")
public class SearchResource {
	@Context iSearchDAO dao;
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public SearchResults getSearchResults(@QueryParam(value="query") String query){
		return dao.getQuerySearch(query);
	}
	
	@GET
	@Path("/queryexpansion")
	@Produces(MediaType.APPLICATION_JSON)
	public SearchExpansionResults getSearchExpansionResults(@QueryParam(value="query") String query){
		return dao.getQueryExpansionSearch(query);
	}
	
	@GET
	@Path("/clustering")
	@Produces(MediaType.APPLICATION_JSON)
	public SearchClusterResults getSearchClustersResults(@QueryParam(value="query") String query){
		return dao.getClusterSearch(query);
	}
}
