package com.tomcat.hosting.guice;

/*import java.util.ArrayList;
import java.util.List;*/

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.utils.STypes;
/*import com.tomcat.hosting.dao.Answer;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Question;*/

public class ExamSearchServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;
	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		// TODO Auto-generated method stub
		
		int moveType = STypes.parseInt(req.getParameter("movetype"));
		int pageNumber = STypes.parseInt(req.getParameter("pagenumber"));
		
		int PAGE_SIZE=STypes.parseInt(req.getParameter("rownum"));
		String name = req.getParameter("search");
		
		long total = service.getTotalCount(name);
		
		if (total == 0) {
			ST page = getTemplate(getContainer(), getPageName(req));
			page.add("exams", service.getTopExams());		
			//page.add("hasrecord", "norecord");
			page.add("message", "Exam not found.");
			page.add("action", 1);
			
			page.add("searchstring", name);
			return page;
		}
		
		
		int totalpage = (int) (total / PAGE_SIZE);
		
		if (totalpage * PAGE_SIZE < total) {
			totalpage += 1;
		}
		
		switch (moveType) {
		case 0 :
			pageNumber = 0;
			break;
		case 1 :
			pageNumber -= 1;
			break;
		case 2 :
			pageNumber += 1;
			break;
		case 3:
			pageNumber = totalpage-1;
			break;
		}		
//		if (pageNumber < 0)
//			pageNumber = 0;					
		if (pageNumber * PAGE_SIZE >= total)
			pageNumber = pageNumber - 1;
		
		if (pageNumber < 0)
			pageNumber = 0;
		
		ST page = getTemplate(getContainer(), getPageName(req));
		page.add("exams", service.getExamByPage(name, pageNumber, PAGE_SIZE));			
		
		if ((totalpage -1) > 0 && pageNumber >0)	
			page.add("eFirst", 1);
		else	
			page.add("dFirst", 1);
		
		if (pageNumber >0 )
			page.add("ePrev", 1);
		else 
			page.add("dPrev", 1);
		
		if (pageNumber < (totalpage-1))	
			page.add("eNext", 1);
		else 
			page.add("dNext", 1);
		
		if ((totalpage -1) > 0 && pageNumber < (totalpage-1))	
			page.add("eLast", 1);
		else
			page.add("dLast", 1);
		page.add("dOption", 1);
		
		switch (PAGE_SIZE) {
		case 10 :
			page.add("e10", 1);
			break;
		case 20 :
			page.add("e20", 1);
			break;
		case 30 :
			page.add("e30", 1);
			break;
		case 40 :
			page.add("e40", 1);
			break;	
		case 50:
			page.add("e50", 1);
			break;
		}
		
		//page.add("total", total);
		page.add("totalpage", totalpage - 1);
		//page.add("startseq", Integer.valueOf("" + PAGE_SIZE * pageNumber));
		page.add("pagenumber", pageNumber);
		//page.add("pagesize", PAGE_SIZE);
		page.add("searchstring", name);
		page.add("hasrecord", "");
		
		return page;
				
	}

	@Override
	protected String getHtmlTitle(String pageName) {
		return getTitle();
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	protected String getPageName(HttpServletRequest req) {
//		String pageName = req.getRequestURI();
//		pageName = pageName.substring(pageName.lastIndexOf("/") + 1);
//		return pageName;
//	}
	
}
