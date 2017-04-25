package com.tomcat.hosting.guice;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.stringtemplate.v4.NumberRenderer;
import org.stringtemplate.v4.STGroup;

import com.google.inject.Inject;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.service.UserService;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1666L;
	protected Log logger = LogFactory.getLog(LoginServlet.class);

	@Inject
	UserService userService;
	
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
			HttpServletResponse resp) throws ServletException, IOException {
				resp.setContentType("text/html;charset=utf-8");
			STGroup group = Utils.getSTGroup(getServletContext());
			group.registerRenderer(Number.class, new NumberRenderer());
			String pageName = "index.xhtml";
			int result = authenticate(req, resp);

			if (1 == result){
				pageName = "userinfo/";
				User user = (User)req.getSession().getAttribute("user");
				userService.insertUserSession(user.getUserId(), req.getSession().getId());
			}
			try {
				RequestDispatcher rd= req.getRequestDispatcher("/" + pageName);
				rd.forward(req, resp);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
	}
	private String getCookie(HttpServletRequest req, String cName){
		Cookie cookie = null;
		  Cookie[] cookies = null;
	      // Get an array of Cookies associated with this domain
	      cookies = req.getCookies();
	      if( cookies != null ){	          
	          for (int i = 0; i < cookies.length; i++){
	             cookie = cookies[i];
	             if (cName.equals(cookie.getName( ))) {	            	 
	            	 return cookie.getValue();	            	
	             }	            		            
	          }
	       }
	      return "";
	}
	
	private void setCookie(String uid, String pw, int days, HttpServletResponse resp, HttpServletRequest req){

		Cookie cpw = new Cookie("CERT1@pw",pw);
		cpw.setPath("/");
        cpw.setMaxAge(60*60*24*days); 
        resp.addCookie(cpw);
	}
	
	private int authenticate(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String userId = req.getParameter("userId");
			String password = req.getParameter("password");			
			User temp = new User();
			temp.setUserId(userId);
			temp.setPassword(password);
					
			User user = userService.getUserByIdAndPassword(userId, password);			
			if (null == user) {
				logger.warn("user NOT FOUND:" + temp.getUserId()) ;  		
				String cuid = getCookie(req, "CERT1@uid");
				if (cuid != null){
					setCookie(userId, password,0, resp, req);
				}					
				return -1;
			}else{
				req.getSession().setAttribute("user", user);
			}
			return user.getUserRole();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
}
