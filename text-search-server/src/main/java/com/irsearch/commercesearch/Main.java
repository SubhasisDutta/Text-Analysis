package com.irsearch.commercesearch;

import java.io.IOException;
import java.net.URI;

import com.irsearch.commercesearch.config.SearchConstants;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.irsearch.commercesearch.resource.SearchStubDAO;
import com.irsearch.commercesearch.resource.SearchDAO;
import com.irsearch.commercesearch.resource.iSearchDAO;

public class Main {

	/**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
	public static HttpServer startServer() {
		
		final iSearchDAO dao = new SearchDAO(); //TODO: need to change this to SearchDAO
		// create a resource config that scans for JAX-RS resources and providers        
        final ResourceConfig rc = new SearchApplication(dao);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(SearchConstants.BASE_URI), rc);
	}
		
	/**
     * Main method.
     * @param args
     * @throws IOException
     */
	public static void main(String[] args)throws IOException {
		final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%s \nHit enter to stop it...", SearchConstants.BASE_URI));
        System.in.read();
        server.stop();

	}

}
