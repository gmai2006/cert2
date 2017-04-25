package com.tomcat.hosting.utils;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class STypes {
	static public int parseInt(String value) {
		int val = -1;
		try {
			if (value != null)
				val = Integer.parseInt(value);
		} catch (Exception ex) {
			val = -1;
		}
		return val;
	}
	static public long parseLong(String value) {
		long val = 0;
		try {
			if (value != null)
				val = Long.parseLong(value);
		} catch (Exception ex) {
			val = 0;
		}
		return val;
	}
	static public String SHA512Encypter(String srcString) {
		MessageDigest md;   
		String encryptedString = "";
        try {
            md= MessageDigest.getInstance("SHA-512");

            md.update(srcString.getBytes());
            byte[] mb = md.digest();
            
            for (int i = 0; i < mb.length; i++) {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2) {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                encryptedString += s;
            }

        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
		return encryptedString;
	}
	
	static public String getIpAddress() {
		InetAddress ip;
        String ipAddress = "";
        try {
            ip = InetAddress.getLocalHost();
            ipAddress = ip.getHostAddress();
 
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
		return ipAddress;
	}
}
