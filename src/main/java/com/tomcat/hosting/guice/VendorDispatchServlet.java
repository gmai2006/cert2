package com.tomcat.hosting.guice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.Vendor;

public class VendorDispatchServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;
	static HtmlCompressor compressor = new HtmlCompressor();
	
//	@Inject
//	  private ExamService service;

//	  @Override
//	public void processRequest(HttpServletRequest req,
//			HttpServletResponse resp) throws ServletException, IOException {	
//		String contextpath = getServletContext().getContextPath();
//		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath.concat("/");
//		StringTemplateGroup templates = new StringTemplateGroup("defaults", getTemplatePath());
//		StringTemplate pageST = templates.getInstanceOf("template");
//		String pageName = req.getRequestURI();
//		pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
//		Vendor vendor = service.getVendorByPageName(pageName);
//		
//		pageST.setAttribute("prefix", contextpath);
//		pageST.setAttribute("title", vendor.getPageName().replaceAll("-", " "));
//		pageST.setAttribute("names", getListOfVendors());
//		
//		StringTemplate bodyST = templates.getInstanceOf("vendor");
//		
//		List<Exam> exams = service.getExamByVendorId(vendor.getVendorId());
//		bodyST.setAttribute("vendorName", vendor.getVendorName());
//		bodyST.setAttribute("exams", exams);
//		bodyST.setAttribute("prefix", contextpath);
//		bodyST.setAttribute("info", vendor.getInfo());
//		bodyST.setAttribute("resources", vendor.getResources());
//		pageST.setAttribute("body", bodyST);
//		
//		PrintWriter out = resp.getWriter();
//		out.write(pageST.toString());
//		out.flush();
//	}

	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		Vendor vendor = service.getVendorByPageName(pageName);
		ST bodyST = getTemplate(getContainer(), getPageName(req));
		List<Exam> exams = service.getExamByVendorId(vendor.getVendorId());
		bodyST.add("vendorName", vendor.getVendorName());
		bodyST.add("exams", exams);
		bodyST.add("info", vendor.getInfo());
		bodyST.add("resources", vendor.getResources());
		return bodyST;
	}

	@Override
	protected String getHtmlTitle(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
