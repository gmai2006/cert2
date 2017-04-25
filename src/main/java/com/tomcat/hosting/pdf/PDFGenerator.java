package com.tomcat.hosting.pdf;

import java.io.File;

public interface PDFGenerator
{
	public void generatePDF(File inputfile, File outputfile, int pages) throws Exception;
}
