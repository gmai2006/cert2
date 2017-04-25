package com.tomcat.hosting.guice;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.stringtemplate.v4.ST;


public class NewsServlet extends BaseServlet {
	private static final long serialVersionUID = 2L;
	@Override
	protected ST processBody(HttpServletRequest req, HttpServletResponse resp,
			String pageName) throws Exception {
		ST page = getTemplate(getContainer(), "news");
		URL url = new URL(
			    "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&"
			    + "start=0&num=10&q=IT%20Certification&userip=" + req.getLocalAddr());
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("Referer", req.getLocalName());

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONArray arr = json.getJSONObject("responseData").getJSONArray("results");
			List<Pair<String, String>> result = new ArrayList<Pair<String, String>>();
			
			for (int i = 0; i < arr.length(); i++) {
				JSONObject hash = arr.getJSONObject(i);
				
				Pair<String, String> pair = new ImmutablePair<String, String>(hash.getString("content")
						,hash.getString("url"));
				
				result.add(pair);
			}
			page.add("result", result);
			return page;	
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
	
}
