package com.tomcat.hosting.pdf;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.OutputFormat;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tomcat.hosting.dao.Answer;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.dao.Exhibit;
import com.tomcat.hosting.dao.Explanation;
import com.tomcat.hosting.dao.Objective;
import com.tomcat.hosting.dao.Question;
import com.tomcat.hosting.service.ExamPersistenceInitializer;
import com.tomcat.hosting.service.ExamService;

/**
 * write to xml files
 * @author dung
 *
 */
public class ExamXMLWriter
{
	Injector injector = Guice.createInjector(new StandaloneModule());
	public static void main(String[] s) {
		ExamXMLWriter writer = new ExamXMLWriter();
		writer.generate(new File("/home/tomcat/data/cert2/xml"));
	}
	
	public void generate(File outputdir)
	{
		injector.getInstance(ExamPersistenceInitializer.class);
		ExamService service = injector.getInstance(ExamService.class);
		List<Exam> exams = service.getAllExams();
		if (!outputdir.exists()) {
			System.out.println("create a new directory:" + outputdir.getAbsolutePath());
			outputdir.mkdir();
		}
		for (Exam exam : exams) {
			try {
				File output = new File(outputdir, exam.getExamName() + ".xml");
				if (output.exists()) continue;
				System.out.println("generating.... " + output.getAbsolutePath());
				write(service, exam, output);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void write(ExamService service, Exam c, File output) throws Exception
	{
		Document document = DocumentHelper.createDocument();
        Element root = document.addElement("exam");
        root.addAttribute("v", c.getVendorName())
        .addAttribute("c", c.getExamName())
        .addAttribute("d", c.getExamTitle())
        .addAttribute("ver", c.getVer())
        .addAttribute("n", String.valueOf(c.getQuestions()))
        .addAttribute("t", String.valueOf(c.getExamTime()));
        
        List<Objective> objectives = service.getObjectivesByExamName(c.getExamName());
        if (!objectives.isEmpty()) {
	        for (Iterator<Objective> iter = objectives.iterator(); iter.hasNext();)
			{
				Element e = root.addElement("o");
				writeObjectives(service, iter.next(), e);
			}
        }
        
        List<Question> questions = service.getQuestionsByExam(c.getExamName());
        for (Question question : questions)
		{
			Element q = root.addElement("q");
			writeQuestion(service, question, q);
		}
		
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setNewlines(true);
//		XMLWriter writer = new XMLWriter( System.out, format );
//		XMLWriter writer = new XMLWriter(new FileWriter(output), format );
//		writer.setEscapeText(false);
//        writer.write( document );
//        writer.flush();
//        writer.close();
        
        DOMWriter writer = new DOMWriter();
        org.w3c.dom.Document doc = writer.write( document );
        writeXmlFile(doc, output);
	}
	
	public static void writeObjectives(ExamService service, Objective q, Element obj) throws Exception
	{
				obj.setText(q.getText());
				obj.addAttribute("n", String.valueOf(q.getOrderNumber()));
	}
	
	public static void writeQuestion(ExamService service, Question q, Element question) throws Exception
	{
		question.addAttribute("o", String.valueOf(q.getTopic()));
		List<Exhibit> exhibits = service.getExhibitsByExamNameAndQuestion(q.getExam().getExamName(), q.getId().getId());
		for (Exhibit ex : exhibits)
		{
			if (0 == ex.getExhibitType()) //text
			{
				StringBuilder builder = new StringBuilder();
				builder.append(ex.getText());
				if (null != ex.getText2()) builder.append(ex.getText2());
				if (null != ex.getText3()) builder.append(ex.getText3());
				Element t = question.addElement("t");
//				System.out.println(builder.toString());
				t.setText(builder.toString());
			}
			else	//image???
			{
				Element ip = question.addElement("e");
				ip.addAttribute("ip", ex.getIp());
			}
		}
		
		List<Answer> answers = service.getAnswersByExamNameAndQuestion(q.getExam().getExamName(), q.getId().getId());
		for (Iterator<Answer> iter = answers.iterator(); iter.hasNext();)
		{
			Answer answer = iter.next();
			Element ans = question.addElement("a");
			byte correct = answer.getIsCorrect(); 
			ans.addAttribute("r", String.valueOf(correct));
			Element t = ans.addElement("t");
			t.setText(answer.getText());
		}
		List<Explanation> exps = service.getExplanationsByExamNameAndQuestion(q.getExam().getExamName(), q.getId().getId());
		for (Iterator<Explanation> iter = exps.iterator(); iter.hasNext();)
		{
			Explanation explanation = iter.next();
			Element exp = question.addElement("exp");
			if (1 == explanation.getExpType())
			{
				Element e = exp.addElement("e");
				e.addAttribute("ip", explanation.getImage());
			}
			else
			{
				Element t = exp.addElement("t");
				t.setText(explanation.getText());
			}
		}
	}
	
	public static void writeXmlFile(org.w3c.dom.Document doc, File output) {
        try {
            // Prepare the DOM document for writing
            Source source = new DOMSource(doc);
    
            Result result = new StreamResult(output);
    
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }
    }
}
