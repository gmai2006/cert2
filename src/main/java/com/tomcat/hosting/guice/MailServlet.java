package com.tomcat.hosting.guice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.utils.EmailClient;

public class MailServlet extends BaseServlet {
	private static final long serialVersionUID = 1666L;
	private String title = "";
//	public void processRequest(HttpServletRequest req,
//			HttpServletResponse resp) throws ServletException, IOException {
//		String from = req.getParameter("email");
//		String subject = req.getParameter("subject");
//		String message = req.getParameter("message");
//		EmailClient emailer = new EmailClient("support@tomcathostingservice.com", 
//				new String[] {"support@tomcathostingservice.com"}, subject, "["+ from + "]:\n" + message);
//		new Thread(emailer).run();
//		String contextpath = getServletContext().getContextPath();
//		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath.concat("/");
//		StringTemplateGroup templates = new StringTemplateGroup("defaults", getTemplatePath());
//		StringTemplate pageST = templates.getInstanceOf("template");
//		String pageName = "confirm";
//		pageST.setAttribute("prefix", contextpath);
//		pageST.setAttribute("title", "Confirmation");
//		pageST.setAttribute("names", getListOfVendors());
//		pageST.setAttribute("body", getBody(templates, pageName));
//		
//		PrintWriter out = resp.getWriter();
//		out.write(pageST.toString());
//		out.flush();
//	}
	
	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		String from = req.getParameter("email");
		String subject = req.getParameter("subject");
		String message = req.getParameter("message");
		EmailClient emailer = new EmailClient("support@tomcathostingservice.com", 
				new String[] {"support@tomcathostingservice.com"}, subject, "["+ from + "]:\n" + message);
		new Thread(emailer).run();
		
		ST bodyST = getTemplate(getContainer(), "confirm");
		title = "Confirmation";
		return bodyST;
	}
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		return title;
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}
}
