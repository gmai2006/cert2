package com.tomcat.hosting.guice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.tomcat.hosting.dao.User;
import com.tomcat.hosting.utils.STypes;


public class UserInfoServlet extends UserServlet {
	private static final long serialVersionUID = 2L;
	@Override
	protected String getHtmlTitle(String pageName) {
		return "User Info";
	}

	@Override
	protected String gethtmlDescr(String pageName) {
		return "User Information";
	}

	@Override
	protected String getPageStyle() {
		return "userinfo";
	}


	@Override
	protected ST getPage(HttpServletRequest req, HttpServletResponse resp,
			STGroup group) throws IOException {
		String action = req.getParameter("action");
		String password = req.getParameter("password");
		User user = (User)req.getSession().getAttribute("user");
		
		if (StringUtils.isNotEmpty(action)
				&& StringUtils.isNotEmpty(password)) {
			user = userService.getUserByEmailAndPassword(user.getUserId(), user.getPassword());
			user.setPassword(STypes.SHA512Encypter(password));
			service.saveOrUpdate(user);
		}
		ST page = group.getInstanceOf("user_info");
		page.add("username", user.getUserId());
		return page;
	}

	@Override
	protected String getExamCount() {
		return "";
	}
	
}
