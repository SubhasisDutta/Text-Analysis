package com.irsearch.commercesearch;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.irsearch.commercesearch.config.SearchConstants;
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
		Properties prop = new Properties();
		InputStream input = null;
		
		try {

			input = new FileInputStream(args[0]);
			// load a properties file
			prop.load(input);
			SearchConstants.BASE_URI=prop.getProperty("BASE_URI");	
			SearchConstants.INPUT_DAT_FOLDER=prop.getProperty("INPUT_DAT_FOLDER");
			SearchConstants.OUTPUT_SEPERATE_DATA_FILES=prop.getProperty("OUTPUT_SEPERATE_DATA_FILES");
			SearchConstants.DATA_FILE=prop.getProperty("DATA_FILE");
			SearchConstants.WEB_PAGES=prop.getProperty("WEB_PAGES");
			SearchConstants.INDEX_DIRECTORY_PATH=prop.getProperty("INDEX_DIRECTORY_PATH");
			//System.out.println(SearchConstants.INDEX_DIRECTORY_PATH);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%s \nHit enter to stop it...", SearchConstants.BASE_URI));
        System.in.read();
        server.stop();		
	}

}
