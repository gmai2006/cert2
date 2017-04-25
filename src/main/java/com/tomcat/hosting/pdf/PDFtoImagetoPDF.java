package com.tomcat.hosting.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import com.tomcat.hosting.guice.Utils;

public class PDFtoImagetoPDF
{
	static String imageType = "png";
	public static void main(String[] s) throws Exception
	{
		Path path = Files.createTempDirectory(null);
		File dir = new File("/home/tomcat/data/cert2/pdf/");
		File imagedir = path.toFile();
		File outdir = new File("/home/tomcat/data/cert2/final/");
		
		if (!imagedir.exists())
		{
			imagedir.mkdir();
		}
		
		if (!outdir.exists())
		{
			outdir.mkdir();
		}
		
		File[] files = dir.listFiles(new FileFilter()
		{
			public boolean accept(File f)
			{
				return f.getName().endsWith(".pdf") && f.length() > 1;
			}
		});
		for (int i = 0; i < files.length; i++)
		{
			convertToImage(imagedir, files[i], outdir);	
		}
	}
	
	static void convertToImage(File imagedir, File pdf, File outdir) throws Exception
	{
//		int startPage = 1;
//		int endPage = Integer.MAX_VALUE;
		
		String outputPrefix = Utils.getFileNameWithoutExtension(pdf.getName());
		File subdir = new File(imagedir, outputPrefix);
		subdir.mkdir();
		
		PDDocument document = null;
		OutputStream out = null;
		PdfWriter writer = null;
		com.lowagie.text.Document outdoc = null;
		
        try
        {
        	File outputfile = new File(outdir, pdf.getName());
        	
//        	if (outputfile.exists()) return;
        	
        	out = new FileOutputStream(outputfile);	        
	        outdoc = new com.lowagie.text.Document();
	        outdoc.setMargins(5, 5, 5, 5);
			writer = createDocument(outdoc, out);
			writer.setFooter(PDFUtil.getFooter(outdoc));
		    outdoc.open();
		    
		    System.out.println("open PDF " + pdf.getName());
            document = PDDocument.load( pdf );
            File fileName = null;
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page)
            { 
            	BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
            	fileName = new File(subdir, outputPrefix  + "-" + (page+1) + ".png");
                // suffix in filename will be used as the file format
            	ImageIOUtil.writeImage(bim, fileName.getAbsolutePath(), 300);
            	Image png = Image.getInstance(fileName.getAbsolutePath());
            	png.scaleAbsolute(outdoc.getPageSize().getWidth(), outdoc.getPageSize().getHeight()); 
//        		jpeg.setAlignment(Image.MIDDLE);
                outdoc.add(png);
            }
            
//            PDPageTree pages = document.getDocumentCatalog().getPages();
//
//            for( int i=startPage-1; i<endPage && i<pages.getCount(); i++ )
//            {
//                ImageOutputStream output = null; 
//                ImageWriter imageWriter = null;
//                File fileName = null;
//                try
//                {
//                	
//                    PDPage page = (PDPage)pages.get( i );
//                  
//                    BufferedImage image = page.convertToImage();
//                    
//                    fileName = new File(subdir, outputPrefix + (i+1) + "." + imageType);
//                    
//                    if (!fileName.exists())
//                    {
//	                    System.out.println( "Writing:" + fileName );
//	                    output = ImageIO.createImageOutputStream( fileName );
//	                    
//	                    boolean foundWriter = false;
//	                    Iterator writerIter = ImageIO.getImageWritersByFormatName( imageType );
//	                    while( writerIter.hasNext() && !foundWriter )
//	                    {
//	                        try
//	                        {
//	                            imageWriter = (ImageWriter)writerIter.next();
//	                            ImageWriteParam writerParams = imageWriter.getDefaultWriteParam();
//	                            if(writerParams.canWriteCompressed() )
//	                            {
//	                                writerParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//	                                writerParams.setCompressionQuality(1.0f);
//	                            }
//	                            
//	                            
//	                            imageWriter.setOutput( output );
//	                            imageWriter.write( null, new IIOImage( image, null, null), writerParams );
//	                            foundWriter = true;
//	                        }
//	                     
//	                        finally
//	                        {
//	                            if( imageWriter != null )
//	                            {
//	                                imageWriter.dispose();
//	                            }
//	                        }
//	                    }
//	                    if( !foundWriter )
//	                    {
//	                        throw new RuntimeException( "Error: no writer found for image type '" + imageType + "'" );
//	                    }
//                    }
//                }
//                catch( Exception io )
//                {
//                    io.printStackTrace();
//                }
//                finally
//                {
//                    if( output != null )
//                    {
//                        output.flush();
//                        output.close();
//                    }
//                }
//                //add image to the new pdf document
//        		Image jpeg = Image.getInstance(fileName.getAbsolutePath());
//        		jpeg.scaleAbsolute(outdoc.getPageSize().getWidth(), outdoc.getPageSize().getHeight()); 
////        		jpeg.setAlignment(Image.MIDDLE);
//                outdoc.add(jpeg);
////                System.out.println("write image to PDF");
//            }
        }
        catch( Exception io )
        {
            io.printStackTrace();
        }
        finally
        {
        	if( document != null )
            {
                document.close();
            }
        	if (null != outdoc)
			{
				outdoc.close();
			}
			if (null != writer)
			{
				writer.close();
			}
			if (null != out)
			{
				out.flush();
				out.close();
			}
        }
		
	}
	
	static PdfWriter createDocument(Document document, OutputStream out) throws Exception
	{
		PdfWriter writer = PdfWriter.getInstance(document, out);
		return writer;
	}
}
