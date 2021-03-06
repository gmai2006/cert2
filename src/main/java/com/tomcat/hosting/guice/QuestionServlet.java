package com.tomcat.hosting.guice;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.Answer;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Question;
import com.tomcat.hosting.dao.QuestionPK;

public class QuestionServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;

	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pageName = req.getRequestURI();	
		String examName = pageName.substring(pageName.lastIndexOf("/") + 1);
		
		Exam exam = (Exam)req.getSession().getAttribute(examName);
		if (null == exam)
		{
			exam = service.getExamByName(examName);
		}
		List<Question> questions = exam.getQuestionsSet();
		String questionNumber = req.getParameter("question");
		String selected = req.getParameter("selected");
		String where = "m";
		String dir = req.getParameter("dir");
		int id = Integer.valueOf(questionNumber).intValue();
		id = ("n".equals(dir)) ? (id+1) : (id-1);
		if  (id <= 0)
		{
			id = 0;
			where = "b";
		}
		else if (id >= (questions.size() - 1))
		{
			id = (questions.size() - 1);
			where = "e";
		}
		
		QuestionPK pk = new QuestionPK();
		pk.setExamName(examName);
		pk.setId(id);
		Question q = service.getQuestionByExamAndId(pk);
		String text = "";

		List<Exhibit> exs = q.getExhibits();
		JSONArray exhibits = new JSONArray();
		if (!exs.isEmpty()) {
			for (Exhibit e : exs)
			{
				JSONObject o = new JSONObject();
				if (0 == e.getExhibitType())
				{
					text = e.getText();
					if (null != e.getText2()) {
						text += e.getText2();
					}
					if (null != e.getText3()) {
						text += e.getText3();
					}
				}
				else
				{
					text = e.getIp();
				}
				
				o.put("text", text);
				o.put("type", e.getExhibitType());
				exhibits.add(o);
			}
		}
		
		Exhibit e = q.getExhibits().get(0);
		
		
		List<Answer> ans = q.getAnswers();
		
		JSONArray answers = new JSONArray();
		//String result="";
		if (!ans.isEmpty()) {
			//int i=0;
			
			for (Answer an : ans)
			{
				JSONObject o = new JSONObject();
				o.put("text", an.getText());
				o.put("corrected", an.getIsCorrect());
				answers.add(o);
//				if (an.getIsCorrect() == 1) result = result +"," +i;
//				i++;
			}
		}
		
		JSONObject obj = new JSONObject();
		obj.put("examName", examName);
		obj.put("questions", exhibits);
		obj.put("answers", answers);
		obj.put("current", id);
		obj.put("value", (id+1));
		obj.put("choice", q.getChoice());
		obj.put("where", where);
		obj.put("type", e.getExhibitType());
logger.info(obj.toJSONString());
		PrintWriter out = resp.getWriter();
		out.write(obj.toJSONString());
		out.flush();
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
		return "Question";
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return null;
	}

}
