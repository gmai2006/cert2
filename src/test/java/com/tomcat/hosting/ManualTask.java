package com.tomcat.hosting;

import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.dao.Vendor;
import com.tomcat.hosting.service.ExamPersistenceInitializer;
import com.tomcat.hosting.service.ExamService;
import com.tomcat.hosting.service.UserService;
import com.tomcat.hosting.utils.STypes;

public class ManualTask {
	
	public static void main(String[] args) {
		updateUserPassword();
	  }
	
	public void updateExamRanking() {
		Injector injector = Guice.createInjector(new StandaloneModule());
	    injector.getInstance(ExamPersistenceInitializer.class);
	    ExamService service = injector.getInstance(ExamService.class);
	    List<Vendor> vendors = service.getTopVendors();
	    for (Vendor v : vendors)
	    {
	    	System.out.println(v.getVendorName());
	    	List<Exam> exams = service.getExamByVendorId(v.getVendorId());
	    	if (null != exams && !exams.isEmpty()) {
	    		Exam exam = exams.get(0);
	    		exam.setRank(100);
	    		service.saveOrUpdate(exam);
	    	}
	    }
	  }
	
	public static void updateUserPassword() {
		Injector injector = Guice.createInjector(new StandaloneModule());
	    injector.getInstance(ExamPersistenceInitializer.class);
	    UserService service = injector.getInstance(UserService.class);
	    List<User> users = service.getAllUsers();
	    for (User v : users)
	    {
	    	System.out.println(v.getUserId());
	    	v.setPassword(STypes.SHA512Encypter(v.getPassword()));
	    	service.saveOrUpdate(v);
	    }
	  }
}
