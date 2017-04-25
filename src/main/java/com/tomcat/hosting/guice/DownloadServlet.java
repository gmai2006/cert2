package com.tomcat.hosting.guice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.tomcat.hosting.dao.Exam;
import com.tomcat.hosting.service.ExamService;
import com.tomcat.hosting.utils.StreamCopier;

public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1666L;
	protected Log logger = LogFactory.getLog(DownloadServlet.class);

	@Inject
	ExamService service;
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process (request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process (request, response);
	}
	
	private void process(HttpServletRequest req,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String examKey = req.getParameter("id");
		if (Utils.isStringNull(examKey)) return;
		BufferedInputStream in = null;
		BufferedOutputStream output = null;
			try 
			{
				Exam exam = service.getExamByExamKey(examKey);
				File file = new File("/home/tomcat/data/cert2/zip/" + exam.getExamName() + ".zip");
				in = new BufferedInputStream(new FileInputStream(file));

				response.reset();
				response.setContentType("application/x-download");
				response.setContentLength(in.available());
				response.setHeader("Content-Disposition", "attachment; filename=" + exam.getExamName() + ".zip");
				response.setHeader("referer", exam.getExamName() + ".zip");
				output = new BufferedOutputStream(response.getOutputStream());
				StreamCopier.copy(in, output);
			}
			catch (Exception e) {
	            e.printStackTrace();
			}
			finally {
				try {
					if (null != output)
					{
						output.flush();
						output.close();
					}
					if (null != in) in.close();
				}catch (Exception ignored) {}
			}
	}
}
