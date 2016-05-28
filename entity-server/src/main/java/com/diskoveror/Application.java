package com.diskoveror;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.serendio.textanalyzer.EntityDAO;

public class Application extends ResourceConfig {
	public Application(final EntityDAO e) {
		 packages("com.serendio.textanalyzer");
		 register(new AbstractBinder() {			
			@Override
			protected void configure() {
				bind(e).to(EntityDAO.class);
			}
		});
	}

}
