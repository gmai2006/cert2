package com.tomcat.hosting.guice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.utils.STypes;

public class RegisterServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;
	protected Log logger = LogFactory.getLog(RegisterServlet.class);

	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		ST bodyST = getTemplate(getContainer(), "index");
		bodyST.add("exams", service.getTopExams());	
		addUser(req);
		return bodyST;
	}
	
	private boolean addUser(HttpServletRequest req) throws Exception {
		User user = userService.getUserById(req.getParameter("email"));
		if (null != user) {
			throw new Exception("User already exist in the system.  Please use another email address");
		}
		user = new User();
		user.setUserId(req.getParameter("email"));
		user.setPassword(STypes.SHA512Encypter(req.getParameter("password")));
		user.setEmailAddress(req.getParameter("email"));
		user.setUserRole(1);
		user.setActivationCode(0);
		user.setStatus(1);
		userService.saveOrUpdate(user);
		return true;
	}
	protected String getHtmlTitle(String pageName) {
		return "Register Form";
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return "Certification Registration";
	}	
}
