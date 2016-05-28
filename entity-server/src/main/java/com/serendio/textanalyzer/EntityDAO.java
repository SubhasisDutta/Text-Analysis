package com.serendio.textanalyzer;

import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import com.diskoverorta.entities.EntityManager;
import com.diskoverorta.vo.EntityObject;

public class EntityDAO {
	public Response getAll(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){
		
		nlpType=CommonUtils.processNlpType(nlpType);		
		EntityManager temp = new EntityManager(nlpType);
		List<EntityObject> result=temp.getALLEntitiesForDocument(inputText);		
		return Response.ok().entity(new GenericEntity<List<EntityObject>>(result){}).build();
	}
}
