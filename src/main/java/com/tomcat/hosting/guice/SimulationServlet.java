package com.tomcat.hosting.guice;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.tomcat.hosting.dao.Answer;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Question;


public class SimulationServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;
	static HtmlCompressor compressor = new HtmlCompressor();
	
	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		Exam exam = service.getExamByName(pageName); 
		req.getSession().setAttribute(pageName, exam);
		String text = "";
		String choice = "radio";
		List<Answer> ans = new ArrayList<Answer>();
		List<Question> questions = exam.getQuestionsSet();
		if (!questions.isEmpty())
		{
			Question q = questions.get(0);
			List<Exhibit> exs = q.getExhibits();
			if (!exs.isEmpty())
			{
				text = exs.get(0).getText();
			}
			ans = q.getAnswers();
			Vector<String> results = (Vector<String>)req.getSession().getAttribute("results");
			if (null == results) results = new Vector<String>();
			String result="";
			if (!ans.isEmpty()) {
				int i=0;				
				for (Answer an : ans)
				{
					if (an.getIsCorrect() == 1) result = result +i+",";
					i++;
				}
			}
			results.add(result);
			req.getSession().setAttribute("results", results);
			choice = (1 == q.getChoice()) ? "checkbox" : "radio";
		}
		
		
		ST bodyST = getTemplate(getContainer(), "simulator-test");
		bodyST.add("examName", exam.getExamName());
		bodyST.add("examTitle", exam.getExamTitle());
		bodyST.add("exam", exam);
		bodyST.add("exhibit", text);
		bodyST.add("answers", ans);
		bodyST.add("choice", choice);
		bodyST.add("timer", 2);
		
		return bodyST;
	}

	@Override
	protected String getHtmlTitle(String pageName) {
		// TODO Auto-generated method stub
		return "Simulation Test";
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		// TODO Auto-generated method stub
		return "Certification Simulation Test";
	}
	
}
