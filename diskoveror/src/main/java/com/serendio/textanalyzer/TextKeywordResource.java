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

@Path("keyword")
public class TextKeywordResource {
	static ThriftClient pyClient = new ThriftClient("localhost",19090);
	
	@GET
	@Path("all")	
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getKeyWords(@DefaultValue("")@QueryParam("text") String inputText){		
		
		if(pyClient != null && inputText.length() > 1)
        {
			List<String> keyWords = pyClient.getKeywords(inputText);
			EntityObject e = new EntityObject();
			e.keyWords=keyWords;
			return Response.ok().entity(e).build();
        }
		return Response.status(404).build();
	}
}
