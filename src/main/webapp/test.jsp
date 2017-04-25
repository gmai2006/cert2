<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.net.URL"%>
<%
URL url = new URL(
			    "https://ajax.googleapis.com/ajax/services/search/web?v=1.0&"
			    + "start=0&num=10&q=IT%20Certification&userip=" + request.getLocalAddr());
			URLConnection connection = url.openConnection();
			connection.addRequestProperty("Referer", request.getLocalName());

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while((line = reader.readLine()) != null) {
			 builder.append(line);
			}

			JSONObject json = new JSONObject(builder.toString());
			JSONArray obj = json.getJSONObject("responseData").getJSONArray("results");
			JSONArray arr = json.getJSONObject("responseData").getJSONArray("results");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject hash = arr.getJSONObject(i);
			}
			
 %>
 <%
 for (int i = 0; i < arr.length(); i++) {
				JSONObject hash = arr.getJSONObject(i);
				 %>
				  <pre><%=hash.toString(4)%></pre>
				  <p>Next</p>
				  <%
			}
 %>
