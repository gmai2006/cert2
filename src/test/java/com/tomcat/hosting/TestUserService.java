package com.tomcat.hosting;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tomcat.hosting.dao.Certification;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.ExamView;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Question;
import com.tomcat.hosting.dao.QuestionPK;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.dao.Vendor;
import com.tomcat.hosting.service.ExamPersistenceInitializer;
import com.tomcat.hosting.service.ExamService;
import com.tomcat.hosting.service.UserService;

public class TestUserService {
	Injector injector = Guice.createInjector(new StandaloneModule());
	
		  @Test
		  public void testGetUserByUserIdandPassword() throws SQLException {
		    injector.getInstance(ExamPersistenceInitializer.class);
		    UserService service = injector.getInstance(UserService.class);
		    
		    String userId = "test453xxx@test.com";
		    String password = "test";

		    User user = service.getUserByIdAndPassword(userId, password);
		    if (null == user) {
		    	service.addUser(userId, password);
		    }
		    user = service.getUserByIdAndPassword(userId, password);
		    Assert.assertEquals(userId, user.getUserId());
		    try {
		    service.delete(user);
		    } catch (Exception ex) {ex.printStackTrace();}
		  }
}

