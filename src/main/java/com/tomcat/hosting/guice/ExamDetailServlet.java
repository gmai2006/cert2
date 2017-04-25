package com.tomcat.hosting.guice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import com.tomcat.hosting.dao.ExamInfo;
import com.tomcat.hosting.dao.QuestionInfo;
import com.tomcat.hosting.dao.User;


public class ExamDetailServlet extends UserServlet {
	private static final long serialVersionUID = 2L;
	@Override
	protected ST getPage(HttpServletRequest req, HttpServletResponse resp,
			STGroup group) throws IOException {
		ST bodyST = getTemplate(getContainer(), "exam_info_detail");
		User user = (User)req.getSession().getAttribute("user");		
		String userId = user.getUserId();
				
		String examName = req.getParameter("examName");
		String sessionId = req.getParameter("sessionId");
		logger.info("exam name:" + examName);
	
		Object[] o = service.getQuestionHistoryByUser(userId, examName, sessionId);
		System.out.println(null == o);
		System.out.println(o[0]);
		ExamInfo info = ExamHistoryViewServlet.map(service, o);
		bodyST = getTemplate(getContainer(), "exam_info_details");			
		int pc = (info.getCorrectNumber()*100)/info.getQuestionNumber();
		int pic = (info.getIncorrectNumber()*100)/info.getQuestionNumber();			
		bodyST.add("correct", pc);
		bodyST.add("incorrect", pic);
		List<Object[]> results = service.getIncorrectQuestions(userId, examName);
		ArrayList<QuestionInfo> arrIc = new ArrayList<QuestionInfo>();
		for (Object[] result : results) {
			QuestionInfo q = new QuestionInfo();
			q.setText((String)result[1]);
			q.setExamName((String)result[2])  ;
			q.setQuestionId((((Number)result[3]).intValue()));  ;
			arrIc.add(q);				
		}
		bodyST.add("userId", userId);
		bodyST.add("questions", arrIc);			
	
		return bodyST;
	}
	
	public void addUser(){
		
	}
	protected String getHtmlTitle(String pageName) {
		return "Exam Information";
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getPageStyle() {
		// TODO Auto-generated method stub
		return "examinfo";
	}

	@Override
	protected String getExamCount() {
		// TODO Auto-generated method stub
		return null;
	}	
}
