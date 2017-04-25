package com.tomcat.hosting.guice;

import com.google.inject.Singleton;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

public class MainModule extends ServletModule {
	@Override
	protected void configureServlets() {
		install(new JpaPersistModule("examJPA"));  
	    filter("/*").through(PersistFilter.class);
	    
		bind(DispatchServlet.class).in(Singleton.class);
		bind(VendorDispatchServlet.class).in(Singleton.class);
		bind(CertificationDispatchServlet.class).in(Singleton.class);
		bind(ExamDispatchServlet.class).in(Singleton.class);
		bind(QuestionServlet.class).in(Singleton.class);
		bind(TestServlet.class).in(Singleton.class);
		bind(MailServlet.class).in(Singleton.class);
		bind(ExamSearchServlet.class).in(Singleton.class);
		bind(RegisterServlet.class).in(Singleton.class);
		bind(LoginServlet.class).in(Singleton.class);
		bind(SimulationServlet.class).in(Singleton.class);
		bind(SimulationTestServlet.class).in(Singleton.class);
		bind(UserInfoServlet.class).in(Singleton.class);
		bind(ExamDetailServlet.class).in(Singleton.class);
		bind(ExamHistoryViewServlet.class).in(Singleton.class);
		bind(LogoutServlet.class).in(Singleton.class);
		bind(NewsServlet.class).in(Singleton.class);
		bind(ChangePasswordServlet.class).in(Singleton.class);
		bind(DownloadServlet.class).in(Singleton.class);
		
		
		serve("/news.xhtml").with(NewsServlet.class);
		serve("/signout.asp").with(LogoutServlet.class);
		serve("/").with(DispatchServlet.class);
		serve("/mail").with(MailServlet.class);
		serve("*.xhtml").with(DispatchServlet.class);
		
		serve("/vendor/*").with(VendorDispatchServlet.class);
		serve("/certification/*").with(CertificationDispatchServlet.class);
		serve("/exam/*").with(ExamDispatchServlet.class);
		serve("/question/*").with(QuestionServlet.class);
		serve("/test/*").with(TestServlet.class);
		serve("/simulation/*").with(SimulationServlet.class);
		serve("/simulationtest/*").with(SimulationTestServlet.class);
		serve("/search/*").with(ExamSearchServlet.class);
		serve("/login/*").with(LoginServlet.class);
		serve("/register/*").with(RegisterServlet.class);
		serve("/examinfo/*").with(ExamHistoryViewServlet.class);
		serve("/examdetail/*").with(ExamDetailServlet.class);
		serve("/userinfo/*").with(UserInfoServlet.class);
		serve("/request_password.asp").with(ChangePasswordServlet.class);
		serve("/download/*").with(DownloadServlet.class);
		
	}
}
