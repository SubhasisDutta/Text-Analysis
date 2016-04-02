package com.serendio.textanalyzer;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.diskoverorta.entities.BaseEntity;
import com.diskoverorta.entities.PersonEntity;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource1")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	String exSentence = "Barak Obama is the president of America.";
        BaseEntity ex = new PersonEntity();
        System.out.println("Input Sentence : "+exSentence);
        List<String> names = ex.getEntities(exSentence);
        System.out.println("Person Names : "+ names);
    	
        return names.toString();
    }
}
