package com.serendio.textanalyzer;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.diskoverorta.pyinterface.ThriftClient;
import com.diskoverorta.vo.EntityObject;




@Path("topic")
public class TextTopicResource {
	
	static ThriftClient pyClient = new ThriftClient("localhost",19090);
	
	@GET
	@Path("all")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getTopics(@DefaultValue("")@QueryParam("text") String inputText){		
		
		if(pyClient != null && inputText.length() > 1)
        {
			List<String> topics = pyClient.getTopics(inputText);
			EntityObject e = new EntityObject();
			e.topics=topics;
			return Response.ok().entity(e).build();
        }
		return Response.status(404).build();
	}
}
