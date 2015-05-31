package com.serendio.textanalyzer;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.diskoverorta.entities.EntityManager;
import com.diskoverorta.vo.EntityObject;
import com.serendio.categotyfinder.TweetCategoryModel;
import com.serendio.categotyfinder.TweetPaser;


@Path("category")
public class EntityCategoryService {

	@GET
	@Path("all")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getAll(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);		
		TweetPaser t = new TweetPaser();
		TweetCategoryModel tm = t.tweetClassifier(inputText);
		
			
		return Response.ok().entity(tm).build();
	}
	
}
