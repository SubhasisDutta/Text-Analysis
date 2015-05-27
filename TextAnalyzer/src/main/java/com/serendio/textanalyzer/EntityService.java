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

import com.diskoverorta.entities.EntityManager;
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
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAll(@DefaultValue("")@QueryParam("text") String inputText){		
		
		EntityManager temp = new EntityManager(SerendioNLP.STANFORD_NLP);
		List<EntityObject> result=temp.getALLEntitiesForDocument(inputText);
		
		return Response.ok().entity(new GenericEntity<List<EntityObject>>(result){}).build();
	}
	
}
