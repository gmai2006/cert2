package com.tomcat.hosting.guice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;

public class Utils {
	static final String IMAGE = "gif,jpg,jpeg,png,ico,bmp";
	public static boolean isStringNull(String s)
	{
		return null == s || s.length() == 0;
	}
	public static String getFileNameWithoutExtension(String s)
	{
		if (null == s) return "";
		if (s.lastIndexOf(".") == -1) return "";
		return s.substring(0, s.lastIndexOf("."));
	}
	
	public static JSONObject getGeoLocationFromZipcode(String zipcode) throws Exception {
		String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + zipcode + "&sensor=false";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		// con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		Object output = JSONValue.parse(response.toString());

		return (JSONObject)output;
	}
	
	public static STGroup getSTGroup(ServletContext context) {
		return new STRawGroupDir(context.getRealPath(
				"/WEB-INF/template/"), '$', '$');
	}
	
	/**
	 * Returns the Mime Type of the file, depending on the extension of the filename
	 */
	public static String getMimeType(String fName)
	{
		fName = fName.toLowerCase();
		if (fName.endsWith(".jpg")||fName.endsWith(".jpeg")||fName.endsWith(".jpe")) return "image/jpeg";
		else if (fName.endsWith(".gif")) return "image/gif";
		else if (fName.endsWith(".pdf")) return "application/pdf";
		else if (fName.endsWith(".htm")||fName.endsWith(".html")||fName.endsWith(".shtml")) return "text/html";
		else if (fName.endsWith(".avi")) return "video/x-msvideo";
		else if (fName.endsWith(".mov")||fName.endsWith(".qt")) return "video/quicktime";
		else if (fName.endsWith(".mpg")||fName.endsWith(".mpeg")||fName.endsWith(".mpe")) return "video/mpeg";
		else if (fName.endsWith(".zip")) return "application/zip";
		else if (fName.endsWith(".tiff")||fName.endsWith(".tif")) return "image/tiff";
		else if (fName.endsWith(".rtf")) return "application/rtf";
		else if (fName.endsWith(".mid")||fName.endsWith(".midi")) return "audio/x-midi";
		else if (fName.endsWith(".xl")||fName.endsWith(".xls")||fName.endsWith(".xlv")||fName.endsWith(".xla")
				||fName.endsWith(".xlb")||fName.endsWith(".xlt")||fName.endsWith(".xlm")||fName.endsWith(".xlk"))
			return "application/excel";
		else if (fName.endsWith(".doc")||fName.endsWith(".dot")) return "application/msword";
		else if (fName.endsWith(".png")) return "image/png";
		else if (fName.endsWith(".xml")) return "text/xml";
		else if (fName.endsWith(".svg")) return "image/svg+xml";
		else if (fName.endsWith(".mp3")) return "audio/mp3";
		else if (fName.endsWith(".ogg")) return "audio/ogg";
		else return "text/plain";
	}
	public static  String getContextPath(HttpServletRequest req) {
		String contextpath = req.getContextPath();
		contextpath = (contextpath.endsWith("/")) ? contextpath : contextpath.concat("/");
		return contextpath;
	}
	public static String getFileType(String name)
	{
		String extension = "unknown";
		int index = name.lastIndexOf(".");
		if (index >=0)
		{
			extension = name.substring(index+1);
		}
		return extension;
	}
	
/*	public static boolean isImage(FileWrapper file)
	{
		return IMAGE.indexOf(file.getFileType()) != -1;
	}
	public static String convertFileSize (long size)
	{
		int divisor = 1;
		String unit = "bytes";
		if (size>= 1024*1024){
			divisor = 1024*1024;
			unit = "MB";
		}
		else if (size>= 1024){
			divisor = 1024;
			unit = "KB";
		}
		if (divisor ==1) return size /divisor + " "+unit;
		String aftercomma = ""+100*(size % divisor)/divisor;
		if (aftercomma.length() == 1) aftercomma="0"+aftercomma;
		return size /divisor + "."+aftercomma+" "+unit;
	}
	
	public static boolean editableFile (FileWrapper fileWrapper)
	{
		return ("jsp".equals(fileWrapper.getFileType()) || "txt".equals(fileWrapper.getFileType())
				|| "xml".equals(fileWrapper.getFileType())
				|| "sh".equals(fileWrapper.getFileType()));
	}
	
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
	public static int calculateDistance(double userLat, double userLng,
	  double venueLat, double venueLng) {

	    double latDistance = Math.toRadians(userLat - venueLat);
	    double lngDistance = Math.toRadians(userLng - venueLng);

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	      + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
	      * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));
	}
*/	
	public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
	      double theta = lon1 - lon2;
	      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) 
	    		  + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	      if (unit == 'K') {
	        dist = dist * 1.609344;
	      } else if (unit == 'N') {
	        dist = dist * 0.8684;
	        }
	      return (dist);
	    }

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts decimal degrees to radians             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    static private double deg2rad(double deg) {
	      return (deg * Math.PI / 180.0);
	    }

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts radians to decimal degrees             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    static private double rad2deg(double rad) {
	      return (rad * 180.0 / Math.PI);
	    }
}
