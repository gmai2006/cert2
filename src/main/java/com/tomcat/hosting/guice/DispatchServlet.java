package com.tomcat.hosting.guice;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.tomcat.hosting.dao.User;

public class DispatchServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	static Map<String, String> TITLE = new HashMap<String, String>();
	static HtmlCompressor compressor = new HtmlCompressor();
	static
	{
		TITLE.put("index", "IT Certification 2 Welcome!");
		TITLE.put("about-us", "About Us IT Certification 2");
		TITLE.put("contact-us", "Contact Us - Cert for Beginner");
		TITLE.put("log-in", "Sign in forCert for Beginner");
		TITLE.put("FAQ", "FAQ for Cert for Beginner");
		TITLE.put("news", "Certification News, Certification trend");
		TITLE.put("salaries", "Certification salaries ");
		TITLE.put("confirm", "Confirmation - Certifications ");
		TITLE.put("show-all-vendors", "IT Certification 2 All Vendors");
		TITLE.put("register", "Registration Form");
	}
	@Override
	protected ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception
	{		
			
		ST page = getTemplate(getContainer(), getPageName(req));

		page.add("exams", service.getTopExams());		
		
		String userName = req.getParameter("userid");
		String email = "";
		
		User user = (User)req.getSession().getAttribute("user");
		if (user != null) {//phuc
			String userId = user.getUserId();
			//String eaddr = (String)req.getSession().getAttribute("emailaddress");
			page.add("username", userId);
		}else{				
				page.add("invalidlogin","NL");	
			//page.add("email", eaddr);
		}
		if (userName != null && userName != "") {//from login page if login failed
			page.add("exams", service.getTopExams());		
			String loginResult = req.getSession().getAttribute("loginresult").toString();
			
			if (loginResult.equals("T")){
				
				page.add("invalidlogin","F");
			}else{
				page.remove("invalidlogin");
				page.add("invalidlogin","T");
			}
		}
		return page;
	}
	
	public String getTemplatePath() {
		return getServletContext().getRealPath("/WEB-INF/template/");
	}

	@Override
	protected String getHtmlTitle(String pageName)
	{
		String title = TITLE.get(pageName);		
		if (null == title) title = "CANNOT FIND TITLE";
		return title;
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}
}
