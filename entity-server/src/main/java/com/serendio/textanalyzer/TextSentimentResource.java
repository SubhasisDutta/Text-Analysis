package com.serendio.textanalyzer;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.diskoverorta.pyinterface.ThriftClient;
import com.diskoverorta.vo.EntityObject;

@Path("sentiment")
public class TextSentimentResource {
	static ThriftClient pyClient = new ThriftClient("localhost",19090);
	
	@GET
	@Path("all")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getSentiment(@DefaultValue("")@QueryParam("text") String inputText){		
		
		if(pyClient != null && inputText.length() > 1)
        {
			String sentiment = pyClient.getSentimentScore(inputText);
			EntityObject e = new EntityObject();
			e.sentimentScore=sentiment;
			return Response.ok().entity(e).build();
        }
		return Response.status(404).build();
	}
}
