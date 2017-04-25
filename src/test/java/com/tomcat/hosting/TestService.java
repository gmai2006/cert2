package com.tomcat.hosting;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tomcat.hosting.dao.Certification;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.ExamHistory;
import com.tomcat.hosting.dao.ExamHistoryPK;
import com.tomcat.hosting.dao.ExamView;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Question;
import com.tomcat.hosting.dao.QuestionHistory;
import com.tomcat.hosting.dao.QuestionHistoryPK;
import com.tomcat.hosting.dao.QuestionPK;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.dao.UserSession;
import com.tomcat.hosting.dao.UserSessionPK;
import com.tomcat.hosting.dao.Vendor;
import com.tomcat.hosting.service.ExamPersistenceInitializer;
import com.tomcat.hosting.service.ExamService;
import com.tomcat.hosting.service.UserService;

public class TestService {
	Injector injector = Guice.createInjector(new StandaloneModule());
	
//		  @Test
		  public void testGetAllVendors() throws SQLException {
		    
		    injector.getInstance(ExamPersistenceInitializer.class);
		    ExamService service = injector.getInstance(ExamService.class);
		    List<Vendor> vendors = service.getAllVendors();
		    for (Vendor v : vendors)
		    {
		    	System.out.println(v.getVendorName());
		    }
		  }
		  
//		  @Test
		  public void testgetVendorByName() throws SQLException {
		    injector.getInstance(ExamPersistenceInitializer.class);
		    ExamService service = injector.getInstance(ExamService.class);
		    List<Exam> exams = service.getAllExams();
		    Vendor vendor = service.getVendorByName(exams.get(0).getVendorName());
		    Assert.assertTrue(null != vendor);
		    System.out.println(vendor.getVendorName());
		    List<Certification> certs = vendor.getCertifications();
		    Assert.assertNotNull(null != certs);
//		    for (Certification c : certs)
//		    {
//		    	System.out.println(c.getName());
//		    }
		  }
		  @Test
		  public void testgetExams() throws SQLException {
		    injector.getInstance(ExamPersistenceInitializer.class);
		    ExamService service = injector.getInstance(ExamService.class);
		    List<Exam> exams = service.getAllExams();
		    
		    List<Exam> result = service.getExamByVendorId(exams.get(0).getVendorId());
		    Assert.assertTrue(null != result);
		  }
		  
//		  @Test
		  public void testgetQuestion() throws SQLException {
		    injector.getInstance(ExamPersistenceInitializer.class);
		    ExamService service = injector.getInstance(ExamService.class);
		    QuestionPK pk = new QuestionPK();
		    pk.setExamName("000-011");
		    pk.setId(0);
		    Question q = service.getQuestionByExamAndId(pk);
		    Assert.assertTrue(null != q);
		    System.out.println(q.getExam().getExamName());
		    List<Exhibit> ex = q.getExhibits();

		  }
		  @Test
		  public void testgetExamHistoryByUser() throws Exception {
			  injector.getInstance(ExamPersistenceInitializer.class);
			  ExamService service = injector.getInstance(ExamService.class);
			  UserService userService = injector.getInstance(UserService.class);
			  ExamHistory history = getExamHistory(service, userService);
			  Object[] o = service.getQuestionHistoryByUser(history.getId().getUserId()
					  , history.getId().getExamName()
					  , history.getId().getSessionId());
			  Assert.assertNotNull(o);
			  for (Object x : o)  System.out.println(x);
		  }
		  
//		  @Test
		  public void testGetExamViews() throws SQLException {
			  injector.getInstance(ExamPersistenceInitializer.class);
			    ExamService service = injector.getInstance(ExamService.class);
			    List<ExamView> exams = service.getExamViewByVendorRankByRank(100, 1);
		  }
		  
//		  @Test
		  public void testCreateandGetExamHistory() throws Exception {
			  injector.getInstance(ExamPersistenceInitializer.class);
			  ExamService service = injector.getInstance(ExamService.class);
			  UserService userService = injector.getInstance(UserService.class);
			  
			  String testUserId = "test@test.com";
			  
			  User user = userService.getUserById(testUserId);
			  if (null == user) userService.addUser(testUserId, "test");
			  List<Exam> exams = service.getAllExams();
			  String examName = exams.get(0).getExamName();
			  UserSessionPK sessionPk = new UserSessionPK();
			  UserSession session = null;
			  List<UserSession> sessions =userService.findUserSessionByUserId(user.getUserId());
			  if (sessions.isEmpty()) {
				  session = new UserSession();
				  sessionPk.setUserId(user.getUserId());
				  sessionPk.setSessionId("erwerwerwerqwerqwerqwesdf45423434");
				  session.setId(sessionPk);
			  }
			  else {
				  session = sessions.get(0);
				  System.out.println("FOUND user session:" + session.getId().getSessionId());
			  }

			  ExamHistoryPK pk = new ExamHistoryPK();
			  pk.setUserId(testUserId);
			  pk.setSessionId(session.getId().getSessionId());
			  pk.setExamName(examName);
			  
			  ExamHistory examhistory = service.findExamHistoryById(pk);
			  if (null == examhistory) {
				  examhistory = new ExamHistory();
				  examhistory.setId(pk);
				  examhistory.setCreateTime(new java.sql.Timestamp(new Date().getTime()));
				  service.saveOrUpdate(examhistory);
			  }

			  QuestionHistoryPK questionHistoryPK = new QuestionHistoryPK();
			  questionHistoryPK.setExamName(examName);
			  questionHistoryPK.setQuestionId(11222);
			  questionHistoryPK.setSessionId(session.getId().getSessionId());
			  questionHistoryPK.setUserId(user.getUserId());
			  
			  QuestionHistory questionHistory = service.findQuestionHistoryById(questionHistoryPK);
			  if (null == questionHistory) {
				  questionHistory = new QuestionHistory();
				  questionHistory.setId(questionHistoryPK);
				  questionHistory.setCorrect(0);
				  service.saveOrUpdate(questionHistory);
			  }
			  
			  
			  //clean up test
			  service.delete(questionHistory);
			  service.delete(examhistory);
//			  userService.delete(user);
		  }
		  
		  private ExamHistory getExamHistory(ExamService service, UserService userService) {
			  String testUserId = "test@test.com";
			  User user = userService.getUserById(testUserId);
			  if (null == user) userService.addUser(testUserId, "test");
			  List<Exam> exams = service.getAllExams();
			  String examName = exams.get(0).getExamName();
			  UserSessionPK sessionPk = new UserSessionPK();
			  UserSession session = null;
			  List<UserSession> sessions =userService.findUserSessionByUserId(user.getUserId());
			  if (sessions.isEmpty()) {
				  session = new UserSession();
				  sessionPk.setUserId(user.getUserId());
				  sessionPk.setSessionId("erwerwerwerqwerqwerqwesdf45423434");
				  session.setId(sessionPk);
			  }
			  else {
				  session = sessions.get(0);
				  System.out.println("FOUND user session:" + session.getId().getSessionId());
			  }

			  ExamHistoryPK pk = new ExamHistoryPK();
			  pk.setUserId(testUserId);
			  pk.setSessionId(session.getId().getSessionId());
			  pk.setExamName(examName);
			  
			  ExamHistory examhistory = service.findExamHistoryById(pk);
			  if (null == examhistory) {
				  examhistory = new ExamHistory();
				  examhistory.setId(pk);
				  examhistory.setCreateTime(new java.sql.Timestamp(new Date().getTime()));
				  service.saveOrUpdate(examhistory);
			  }
			  return examhistory;
		  }
}

