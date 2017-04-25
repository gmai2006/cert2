package com.tomcat.hosting.guice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.Certification;

public class CertificationDispatchServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;
	private String title = "";
//	@Inject
//	  private ExamService service;
//	@Override
//	  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//	    processRequest(req, res);
//	  }
//	 
//	  @Override
//	  public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//	    processRequest(req, res);
//	  }

//	  @Override
//	public void processRequest(HttpServletRequest req,
//			HttpServletResponse resp) throws ServletException, IOException {
//
//		String contextpath = getServletContext().getContextPath();
//		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath.concat("/");
//		StringTemplateGroup templates = new StringTemplateGroup("defaults", getTemplatePath());
//		StringTemplate pageST = templates.getInstanceOf("template");
//		String pageName = req.getRequestURI();
//		pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
//
//		Certification cert = service.getCertificationByPageName(pageName);
//		StringTemplate bodyST = templates.getInstanceOf("certification");
//		
//		pageST.setAttribute("prefix", contextpath);
//		pageST.setAttribute("title", cert.getName());
//		pageST.setAttribute("names", getListOfVendors());
//		pageST.setAttribute("body", bodyST);
//		
//		PrintWriter out = resp.getWriter();
//		out.flush();
//	}

	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		// TODO Auto-generated method stub
		ST bodyST = getTemplate(getContainer(), getPageName(req));
		Certification cert = service.getCertificationByPageName(getPageName(req));
		title = cert.getName();
		return bodyST;
	}

	@Override
	protected String getHtmlTitle(String pageName) {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
