package com.serendio.textanalyzer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("all")
public class TextAnalysisResource {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getTextAnalysis(){
		return "sdsd";
	}
}
