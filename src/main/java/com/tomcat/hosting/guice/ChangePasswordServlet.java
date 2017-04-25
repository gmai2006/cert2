package com.tomcat.hosting.guice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;

import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.utils.EmailClient;
import com.tomcat.hosting.utils.STypes;
import com.tomcat.hosting.utils.StreamCopier;

public class ChangePasswordServlet extends BaseServlet {
	private static final long serialVersionUID = 1666L;
	
	@Override
	protected String getHtmlTitle(String pageName)
	{
		return "IT Certification - Request password page";
	}
	@Override
	protected String gethtmlDescr(String pageName)
	{
		return "IT Certification - Request password page";
	}

	@Override
	public ST processBody(HttpServletRequest req,
			HttpServletResponse resp, String pageName) throws Exception {
		String email = req.getParameter("user");
		if (null != email) {
			User user = userService.getUserById(email);
			StringWriter writer = new StringWriter();
			String emailtext = "";
			if (null == user)
			{
				notifyAdmin("User requests  password but NOT FOUND", "User requests password but email NOT FOUND: " + email);
			}
			else {
				BufferedReader reader = null;
		        try
		        {
					String pass = UUID.randomUUID().toString();
		        	user.setPassword(STypes.SHA512Encypter(pass));
		        	service.saveOrUpdate(user);
		        	reader = new BufferedReader(
		        			new InputStreamReader(ChangePasswordServlet.class.getResourceAsStream("/password_reset.txt")));
		        	StreamCopier.copy(reader, writer);
		        	emailtext = writer.toString();
		        	emailtext = emailtext.replaceAll("@userid@", user.getUserId());
		        	emailtext = emailtext.replaceAll("@password@", pass);
		        	EmailClient emailer = new EmailClient("support@tomcathostingservice.com", 
		        			new String[] {"support@tomcathostingservice.com", user.getEmailAddress()}, 
		        			"Your Password Reset Information", 
		        			emailtext);
		        	new Thread(emailer).run();
		        }
	        	catch (Exception e) {
	        		catchException(e, ChangePasswordServlet.class.getName());
	        	}
		        finally {
		        	try {
		        		if (null != reader) reader.close();
		        	}catch (Exception ignored) {}
		        }
			}
		}

		ST st = getTemplate(getContainer(), "request-password");
		st.add("action", true);
		st.add("cssId", "alert-success");
		st.add("message", "Your temporary password has been emailed to your email");
		return st;
	}
}
