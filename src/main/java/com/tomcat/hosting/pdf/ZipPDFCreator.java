package com.tomcat.hosting.pdf;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.tomcat.hosting.guice.Utils;


public class ZipPDFCreator
{
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		File dir = new File("/home/tomcat/data/cert2/final/");
		File out = new File("/home/tomcat/data/cert2/zip/");
		createZipFiles(dir, out);
	}
	
	static void createZipFiles(File dir, File outputdir) throws Exception
	{
		File[] files = dir.listFiles(new FileFilter()
		{
			public boolean accept(File f)
			{
				return (f.getName().toLowerCase().endsWith(".pdf"));
			}
		});
		
		for (int i = 0; i < files.length; i++)
		{
			String name = Utils.getFileNameWithoutExtension(files[i].getName());
		    File output = new File(outputdir, name + ".zip");
		    System.out.println("create zip file " + output.getName());
		    createZipFile(files[i], output);
		}
	}
	
	public static void createZipFile(File inputfile, File output) throws Exception
	{    
		FileInputStream in = null;
		ZipOutputStream out = null;
	    // Create a buffer for reading the files
	    byte[] buf = new byte[1024];
	  
	    try {
	        // Create the ZIP file
	        out = new ZipOutputStream(new FileOutputStream(output));
	    
	        // Compress the files
	        in = new FileInputStream(inputfile);
	    
            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry(inputfile.getName()));
    
            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) 
            {
                out.write(buf, 0, len);
            }
    
            // Complete the entry
            out.closeEntry();
            in.close();
	    
	        // Complete the ZIP file
	        out.close();
	    } catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }

	    finally {
	    	try {
	    		if (null != in) in.close();
	    		if (null != out)
	    		{
	    			out.flush();
	    			out.close();
	    		}
	    	} catch (Exception ignored) {}
	    }
	}
	

}
