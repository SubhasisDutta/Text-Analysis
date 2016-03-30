package com.serendio.textanalyzer;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.GenericEntity;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.diskoverorta.entities.BaseEntity;
import com.diskoverorta.entities.CurrencyEntity;
import com.diskoverorta.entities.DateEntity;
import com.diskoverorta.entities.EntityManager;
import com.diskoverorta.entities.LocationEntity;
import com.diskoverorta.entities.OrganizationEntity;
import com.diskoverorta.entities.PercentEntity;
import com.diskoverorta.entities.PersonEntity;
import com.diskoverorta.entities.TimeEntity;
import com.diskoverorta.osdep.SerendioNLP;
import com.diskoverorta.vo.EntityObject;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("entity")
public class EntityService {

	/*@GET
	@Path("all")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getAll(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@DefaultValue("")@FormDataParam("text") String inputText){
		String totalText="";
		try{
			if(uploadedInputStream!=null){
				totalText=CommonUtils.convetToString(uploadedInputStream);
			}
			if(inputText!=null && inputText.length()>0){
				totalText=inputText;
			}			
		}catch(IOException e1){
			return Response.status(Status.BAD_REQUEST).build();
		}		
		EntityManager temp = new EntityManager(SerendioNLP.STANFORD_NLP);
		List<EntityObject> result=temp.getALLEntitiesForDocument(totalText);
		
		return Response.ok().entity(new GenericEntity<List<EntityObject>>(result){}).build();
	}*/
	
	@GET
	@Path("all")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getAll(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);		
		EntityManager temp = new EntityManager(nlpType);
		List<EntityObject> result=temp.getALLEntitiesForDocument(inputText);		
		return Response.ok().entity(new GenericEntity<List<EntityObject>>(result){}).build();
	}
	
	@GET
	@Path("person")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getPerson(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);
		PersonEntity ex = new PersonEntity();		
		List<String> names = ex.getEntities(inputText,nlpType);	
		EntityObject e = new EntityObject();
		e.person=names;
		return Response.ok().entity(e).build();
	}
	
	@GET
	@Path("organization")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getOrganization(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);
		OrganizationEntity ex = new OrganizationEntity();		
		List<String> names = ex.getEntities(inputText,nlpType);		
		EntityObject e = new EntityObject();
		e.organization=names;
		return Response.ok().entity(e).build();
	}
	
	@GET
	@Path("location")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getLocation(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);
		LocationEntity ex = new LocationEntity();		
		List<String> names = ex.getEntities(inputText,nlpType);	
		EntityObject e = new EntityObject();
		e.location=names;
		return Response.ok().entity(e).build();
	}
	
	@GET
	@Path("currency")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getCurrency(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);
		CurrencyEntity ex = new CurrencyEntity();		
		List<String> names = ex.getEntities(inputText,nlpType);		
		EntityObject e = new EntityObject();
		e.currency=names;
		return Response.ok().entity(e).build();
	}
	
	@GET
	@Path("percent")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getPercent(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);
		PercentEntity ex = new PercentEntity();		
		List<String> names = ex.getEntities(inputText,nlpType);		
		EntityObject e = new EntityObject();
		e.percent=names;
		return Response.ok().entity(e).build();
	}
	
	@GET
	@Path("date")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getDate(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);
		DateEntity ex = new DateEntity();		
		List<String> names = ex.getEntities(inputText,nlpType);		
		EntityObject e = new EntityObject();
		e.date=names;
		return Response.ok().entity(e).build();
	}
	
	@GET
	@Path("time")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getTime(@DefaultValue("")@QueryParam("text") String inputText,@DefaultValue("stanfordnlp")@QueryParam("nlp") String nlpType){		
		nlpType=CommonUtils.processNlpType(nlpType);
		TimeEntity ex = new TimeEntity();		
		List<String> names = ex.getEntities(inputText,nlpType);	
		EntityObject e = new EntityObject();
		e.time=names;
		return Response.ok().entity(e).build();
	}
	
}
