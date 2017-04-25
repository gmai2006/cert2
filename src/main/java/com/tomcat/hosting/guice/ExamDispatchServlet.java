package com.tomcat.hosting.guice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.Answer;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Question;

public class ExamDispatchServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;
	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		// TODO Auto-generated method stub
//		User u = (User)req.getSession().getAttribute("user");
//		if (null == u) {			
//				ST bodyST = getTemplate(getContainer(), "index");
//				bodyST.add("exams", service.getTopExams());	
//				bodyST.add("invalidlogin","NL");
//				return bodyST;
//		}
		String contextpath = getServletContext().getContextPath();
		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath.concat("/");
		logger.info("Exam name:" + pageName);
		Exam exam = service.getExamByName(pageName);
		req.getSession().setAttribute(pageName, exam);
		String text = "";
		String choice = "radio";
		List<Answer> ans = new ArrayList<Answer>();
		List<Question> questions = exam.getQuestionsSet();
		if (null != questions && !questions.isEmpty())
		{
			Question q = questions.get(0);
			List<Exhibit> exs = q.getExhibits();
			if (null != exs && !exs.isEmpty())
			{
				text = exs.get(0).getText();
			}
			ans = q.getAnswers();
			choice = (1 == q.getChoice()) ? "checkbox" : "radio";
		}
		ST bodyST = getTemplate(getContainer(), "exam");
		bodyST.add("examName", exam.getExamName());
		bodyST.add("examTitle", exam.getExamTitle());
		bodyST.add("exam", exam); 
		setTitle(exam.getExamTitle());
		int questionNumber = exam.getQuestions();
		req.getSession().setAttribute("questionNumber", questionNumber);
		req.getSession().setAttribute("examName", exam.getExamName());
		return bodyST;
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
