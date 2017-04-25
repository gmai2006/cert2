package com.tomcat.hosting.guice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.NumberRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import com.google.inject.Inject;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.service.UserService;
import com.tomcat.hosting.utils.EmailClient;

public abstract class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1666L;
	protected Log logger = LogFactory.getLog(UserServlet.class);
	protected String cssId = null;
	protected String message = null;
	
//	@Inject
//	protected UserService service;
	
	@Override
	protected void processRequest(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html;charset=utf-8");
		STGroup group = Utils.getSTGroup(getServletContext());
		group.registerRenderer(Number.class, new NumberRenderer());
		ST page = group.getInstanceOf("admin");
		ST container = group.getInstanceOf("layout");
		User user = (User)req.getSession().getAttribute("user");
		if (null != user) container.add("userId", user.getUserId());
		PrintWriter out = resp.getWriter();		
		
		String context = Utils.getContextPath(req);
		page.add("message", message);
		page.add("cssId", cssId);

		page.add("page", getPage(req, resp, group));
		
		container.add("body", page);
		container.add(getPageStyle(), "active");
		container.add("history", getExamCount());
		container.add("context", context);
		out.print(container.render());
		out.flush();
	}
	
	protected ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception {
		return null;
	}
	
	protected abstract ST getPage(HttpServletRequest req,
			HttpServletResponse resp, STGroup group) throws IOException;
	
	protected abstract String getPageStyle();
	protected abstract String getExamCount();
	
	protected String getPageName(HttpServletRequest req) {
		String pageName = req.getRequestURI().trim();
		System.out.println(this.getClass().getName() + " calling page "
				+ pageName);
		if ("/".equals(pageName) || "".equals(pageName)) return "index";
		pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
		if ("/".equals(pageName) || "".equals(pageName)) return "index";
		if (pageName.lastIndexOf(".") != -1) {
			pageName = pageName.substring(0, pageName.lastIndexOf("."));
		}
		return pageName;
	}
}
