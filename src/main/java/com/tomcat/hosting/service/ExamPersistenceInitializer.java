package com.tomcat.hosting.service;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class ExamPersistenceInitializer {
	@Inject
	ExamPersistenceInitializer(PersistService service) {
		service.start();
	}
}
