package com.tomcat.hosting.guice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.ExamInfo;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.service.ExamService;

public class ExamHistoryViewServlet extends UserServlet {
	private static final long serialVersionUID = 16366L;
	private int val = 0; 
	@Override
	protected ST getPage(HttpServletRequest req, HttpServletResponse resp, STGroup group)
			throws IOException {
		ST bodyST = group.getInstanceOf("exam_info");
		User user = (User)req.getSession().getAttribute("user");		
		String userId = user.getUserId();
		List<Object[]> results = service.getQuestionHistoriesByUser(userId);
		ArrayList<ExamInfo> arr = new ArrayList<ExamInfo>();
		for (Object[] result : results) {	
			arr.add(map(service, result));
		}
		val = arr.size();
		bodyST.add("exams", arr);
		bodyST.add("sessionId", req.getSession().getId());
		return bodyST;
	}
	
	@Override
	protected String getPageStyle() {
		return "exammenu";
	}

	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	protected String getExamCount() {
		return String.valueOf(val);
	}
	
	static ExamInfo map(ExamService service, Object[] result) {
		System.out.println("result:" + result[0]);
		Exam exam = service.getExamByName((String)result[0]);
		int totalQuestionNumber = exam.getQuestions();
		int finishedNumber = ((Number)result[1]).intValue();
		int correctNumber = ((Number)result[2]).intValue();			
		ExamInfo e = new ExamInfo();
		e.setExamName((String)result[0]);
		//e.setQuestionNumber(((Number)result[1]).intValue())  ;
		e.setQuestionNumber(totalQuestionNumber);
		e.setCorrectNumber(correctNumber);
		e.setIncorrectNumber(finishedNumber - correctNumber);
		e.setSessionId((String)result[4]);
		return e;
	}
	
}
