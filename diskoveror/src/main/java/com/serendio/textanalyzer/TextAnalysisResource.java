package com.serendio.textanalyzer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;

import com.diskoverorta.entities.EntityManager;
import com.diskoverorta.vo.EntityObject;



@Path("all")
public class TextAnalysisResource {
	
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getTextAnalysis(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){
		nlpType=CommonUtils.processNlpType(nlpType);		
		EntityManager temp = new EntityManager(nlpType);
		List<EntityObject> result=temp.getALLEntitiesForDocument(inputText);		
		return Response.ok().entity(new GenericEntity<List<EntityObject>>(result){}).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getTextAnalysisPost(MultivaluedMap<String, String> formParams,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){
		String inputText = formParams.getFirst("text");		
		nlpType=CommonUtils.processNlpType(nlpType);		
		EntityManager temp = new EntityManager(nlpType);		
		List<EntityObject> result=temp.getALLEntitiesForDocument(inputText);		
		return Response.ok().entity(new GenericEntity<List<EntityObject>>(result){}).build();
	}
}
