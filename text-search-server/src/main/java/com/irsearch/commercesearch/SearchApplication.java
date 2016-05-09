package com.irsearch.commercesearch;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.irsearch.commercesearch.resource.iSearchDAO;


public class SearchApplication extends ResourceConfig{
	public SearchApplication(final iSearchDAO dao) {
		 packages("com.irsearch.commercesearch");
		 register(new AbstractBinder() {			
			@Override
			protected void configure() {
				bind(dao).to(iSearchDAO.class);
			}
		});
	}
}
