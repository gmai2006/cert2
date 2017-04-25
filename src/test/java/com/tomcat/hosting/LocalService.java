package com.tomcat.hosting;

import java.util.List;

import javax.persistence.EntityManager;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.Vendor;
import com.tomcat.hosting.service.ExamPersistenceInitializer;
import com.tomcat.hosting.service.ExamService;

public class LocalService {
	Injector injector = Guice.createInjector(new StandaloneModule());
	@Inject ExamService service;
	@Inject EntityManager em;
	
	public LocalService()
	{
		injector.getInstance(ExamPersistenceInitializer.class);
		service = injector.getInstance(ExamService.class);
		em = injector.getInstance( EntityManager.class ); 
	}
	
	public static void main(String[] s)
	{
		LocalService local = new LocalService();
		
		local.updateVendorURL("", "-training-certification");
	}
	public void updateVendorURL(String prefix, String suffix)
    {
    	List<Vendor> vendors = service.getAllVendors();
    	for (Vendor v : vendors)
    	{
    		String name = prefix + v.getVendorName().trim().replaceAll(" ", "-") + suffix;
    		v.setPageName(name);
    		System.out.println(v.getVendorId() + " " + v.getPageName());
    		service.saveOrUpdate(v);
    	}
    }
	public void updateExamURL(String prefix)
    {
    	List<Exam> exams = service.getAllExams();
    	for (Exam v : exams)
    	{
    		String name = prefix + "-" + v.getExamName().replaceAll(" ", "-");
    		v.setPageName(name);
    		System.out.println(v.getExamName() + " " + v.getPageName());
    		service.saveOrUpdate(v);
    	}
    }
}