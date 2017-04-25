package com.tomcat.hosting.guice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.NumberRenderer;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

import com.google.inject.Inject;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.service.ExamService;
import com.tomcat.hosting.service.UserService;
import com.tomcat.hosting.utils.EmailClient;

public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1555L;
	private STGroup group;
	protected Log logger = LogFactory.getLog(BaseServlet.class);
	protected String error = "";
	private String title;
	@Inject
	ExamService service;
	@Inject
	UserService userService;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(req, res);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		processRequest(req, res);
	}

	protected static String getContextPath(HttpServletRequest req) {
		String contextpath = req.getContextPath();
		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath
				.concat("/");
		return contextpath; 
	}

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

	
	protected void processRequest(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		logger = LogFactory.getLog(this.getClass());
		logger.info("calling " + this.getClass().getSimpleName());
		String context = getContextPath(req);
		String pageName = getPageName(req);
		ST pageST = getLayout();
		
		try {
			pageST.add("desc", gethtmlDescr(pageName));
			pageST.add("context", context);
			// process body here
			pageST.add("body", processBody(req, resp, pageName));
			// end process body here

			User user = (User)req.getSession().getAttribute("user");
			if (null != user) pageST.add("userId", user.getUserId());
			
		} 
		catch (Exception e) {
			error = e.getMessage() + " " + pageName;
			ST errorPage = getContainer().getInstanceOf("error");
			errorPage.add("error", error);
			pageST.add("body", errorPage);
			catchException(e, this.getClass().getName());
		}

		PrintWriter out = resp.getWriter();
		out.write(pageST.render());
		out.flush();
	}
	
	protected abstract ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception;
	
	
	protected ST getLayout() {
		return getContainer().getInstanceOf("layout");
	}
	
	
	protected ST getTemplate(STGroup template, String pageName)
	{
		File test = new File(template.getRootDirURL().getFile(), pageName + ".st");
		if (!test.exists())
		{	
			logger.info("page NOT FOUND:" + pageName);
			pageName = "404";
		}
		return template.getInstanceOf(pageName);
	}
	protected void notifyAdmin(String subject, String message)
	{
		EmailClient emailer = new EmailClient("support@tomcathostingservice.com", 
				new String[] {"support@tomcathostingservice.com"}, subject, message);
		new Thread(emailer).run();
	}
	protected User getUser(HttpServletRequest request)
	{
		HttpSession session = request.getSession(true);
		return (User)session.getAttribute("user");
	}
	protected String getReferer(HttpServletRequest request)
	{
		HttpSession session = request.getSession(true);
		return (String)session.getAttribute("referer");
	}
	
	protected void catchException (Exception e, String className) {
		StringWriter output = new StringWriter();
		PrintWriter writer = new PrintWriter(output);
		if (null != e) {
			e.printStackTrace(writer);
		} else {
			writer.write("Null exception");
		}
		writer.flush();
		writer.close();
		logger.error("ERROR: " + output.toString());
//		notifyAdmin(className + ": ERROR ", output.toString());
	}
	
	protected void setUser(HttpServletRequest request, HttpServletResponse response, User user)
	{
		addData(request, "user", user);
	}
	
	protected void addData(HttpServletRequest request, String id, Object o)
	{
		HttpSession session = request.getSession(true);
		session.setAttribute(id, o);
	}
	protected abstract String getHtmlTitle(String pageName);
	protected abstract String gethtmlDescr(String pageName);
	
	protected STGroup getContainer() {
		if (null == group) {
			group = 
				new STRawGroupDir(getServletContext().getRealPath("/WEB-INF/template/"), '$', '$');
			group.registerRenderer(Number.class, new NumberRenderer());
		}
		return group;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
