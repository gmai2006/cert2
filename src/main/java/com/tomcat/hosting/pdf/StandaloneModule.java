package com.tomcat.hosting.pdf;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

public class StandaloneModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("examJPA"));
	}

}
