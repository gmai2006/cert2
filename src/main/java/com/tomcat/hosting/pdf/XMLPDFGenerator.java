package com.tomcat.hosting.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.ListItem;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.tomcat.hosting.guice.Utils;

public class XMLPDFGenerator implements PDFGenerator
{
	public static final Font FONT_TITLE = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, Font.BOLD, Color.blue); //new Font(Font.HELVETICA, 18, Font.BOLD, Color.blue);
	public static final Font FONT_HEADER = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, Color.blue);//new Font(Font.HELVETICA, 10, Font.BOLD, Color.blue);
	public static final Font FONT_SMALL = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, Color.black);//new Font(Font.HELVETICA, 10, Font.BOLD, Color.black);
	public static final Font FONT_ANSWER= FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, Color.red);//new Font(Font.HELVETICA, 12, Font.BOLD, Color.red);
	public static final Font FONT_QUESTION= FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, Color.blue);//new Font(Font.HELVETICA, 14, Font.BOLD, Color.blue);
	public static final Font FONT_CHOICE= FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, Color.black);
	public static final Font FONT_REG= FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, Color.black);

	/** A template that will hold the total number of pages. */
    public PdfTemplate tpl;
    
	static final float QUESTION_HEIGHT = (float)(14*2.54)/72;
	boolean endparagraph = false;

	static SAXReader reader = new SAXReader();
	
	static final File IMAGE_DIR = new File("/home/tomcat/xml/images/");
	
	String courseName = "";
	public static void main(String[] s) throws Exception
	{
		XMLPDFGenerator generator = new XMLPDFGenerator();
		
		File dir = new File("/home/tomcat/data/cert2/xml/");
		File outputdir = new File("/home/tomcat/data/cert2/pdf");
		
		if (!outputdir.exists()) outputdir.mkdir();
		
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File f)
			{
				return f.getName().endsWith(".xml");
			}
		});
		
		for (int i = 0; i < files.length; i++) {
			String filename = Utils.getFileNameWithoutExtension(files[i].getName());
			File output = new File(outputdir, filename + ".pdf");
//			if (output.exists()) continue;
			System.out.println("generate pdf " + files[i].getAbsolutePath());
			generator.generatePDF(files[i], output, 10000);
		}
	}
	
	public void generatePDF(File inputfile, File outputfile, int pages) throws Exception
	{
		OutputStream out = null;
		PdfWriter writer = null;
		com.lowagie.text.Document outdoc = null;
		int count = 0;
		try
		{
			out = new FileOutputStream(outputfile);
			org.dom4j.Document document = reader.read(inputfile);
	        Element root = document.getRootElement();
	        courseName = root.attributeValue("c");
	        List attribs = root.attributes();
	        File imageDir = new File(IMAGE_DIR, courseName);
				        
	        outdoc = new com.lowagie.text.Document();
			writer = createDocument(root, outdoc, out);
			writer.setFooter(PDFUtil.getFooter(outdoc));
		    ReportEvent events = new ReportEvent();
		    writer.setPageEvent(events);
		    
		    outdoc.open();
		    List<Element> questions = root.elements("q");
		    
			//first page
			createFirstPage(outdoc, root, writer, new Integer(root.attributeValue("n")).intValue());
			
			//intro if any
			List<Element> objectives = root.elements("o");
			if (!objectives.isEmpty())
			{
				Paragraph p = new Paragraph(new Chunk("Topics: ", FONT_ANSWER));
				p.setSpacingAfter(10.0f);
				outdoc.add(p);
				com.lowagie.text.List list = new com.lowagie.text.List(true, 20);
				for (Iterator<Element> iter = objectives.iterator(); iter.hasNext();)
				{
					Element child = iter.next();
					
					if (null != child.getText())
					{
						list.add(new ListItem(child.getText()));
					}
				}
				outdoc.add(list);
				outdoc.add(new Paragraph("\n"));
			}
			
//			Paragraph p = null;
//			if (null != intro)
//			{
//				List<Item> list = intro.getParagraph();
//				for (int i = 0, n = list.size(); i < n; i++)
//				{
//					Item item = list.get(i);
//					p = new Paragraph(item.getText());
//					document.add(p);
//					List<String> images = item.getImage();
//					for (int j = 0, m = images.size(); j < m; j++)
//					{
//						String image = images.get(j);
//						p = new Paragraph(image);
//						document.add(p);
//					}
//				}
//				
//			}
			
			//questions
			 
			if (null != questions)
			{
				int number = 1;
				for (Iterator<Element> ele = questions.iterator(); ele.hasNext(); ) 
				{
					Element question = (Element) ele.next();
					Paragraph p = new Paragraph(new Chunk("QUESTION: " + number++ + "\n", FONT_QUESTION));
					p.setSpacingAfter(10.0f);
					outdoc.add(p);
					
					List<Element> children = question.elements();
					for (Iterator<Element> iter = children.iterator(); iter.hasNext();)
					{
						Element child = iter.next();
						if ("t".equals(child.getName()))
						{
							if (null != child.getText())
							{
								p = new Paragraph(child.getText() +"\n", FONT_REG);
								p.setFirstLineIndent(20.0f);
								p.setSpacingAfter(10.0f);
								outdoc.add(p);
							}
						}
						else if ("e".equals(child.getName()))
						{
							String imagename = child.attributeValue("ip");
							System.out.println("add image " + imagename);
							addImage(imageDir, root.attributeValue("c"), imagename, outdoc);
						}
					}

//					answers
					List<Element> answers = question.elements("a");
					if (null != answers)
					{
						StringBuffer buffer = new StringBuffer();
						com.lowagie.text.List list = new com.lowagie.text.List(false, true, 20.0f);

						for (int j = 0, m = answers.size(); j < m; j++)
						{
							Element answer = (Element)answers.get(j);
							String s = answer.elementText("t");
							String r = answer.attributeValue("r");
							ListItem listitem = new ListItem(s, FONT_CHOICE);
							list.add(listitem);
							
							if ("1".equals(r))
							{
								buffer.append(Character.valueOf((char)(j+65)) + ", ");
							}
						}
						outdoc.add(list);
						outdoc.add(new Paragraph("\n"));
						
						//correct answer
						String correctanswer = buffer.toString().trim();
						if (correctanswer.endsWith(","))
						{
							correctanswer = correctanswer.substring(0, correctanswer.lastIndexOf(","));
						}
						p = new Paragraph(new Chunk("Answer: " + correctanswer, FONT_ANSWER));
						p.setSpacingAfter(10.0f);
						outdoc.add(p);
					}
						
					
					//explanation
					List<Element> explanations = question.elements("exp");
					
					if (!explanations.isEmpty())
					{
						p = new Paragraph(new Chunk("Explanation:\n", FONT_ANSWER));
						outdoc.add(p);
						
						for (int j = 0, m = explanations.size(); j < m; j++)
						{
							Element element = explanations.get(j);
							Element t = element.element("t");
							if (null != t)
							{
								generateParagraph(t, outdoc);
							}
						}
					}	
					outdoc.add(new Paragraph("\n"));
					
					endparagraph = true;
					

					if (count >= pages)
					{
						break;
					}
					count++;
				}
			}
			
 
			outdoc.close();
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			try {
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
			} catch (Exception ignored) {}
		}
	}
	
	
	PdfWriter createDocument(Element root, Document document, OutputStream out) throws Exception
	{
		PdfWriter writer = PdfWriter.getInstance(document, out);
//		writer.setEncryption(true, null, null, 0);
		document.addTitle(root.attributeValue("d"));
		document.addAuthor("datascience9.com");
		document.addSubject(root.attributeValue("c"));
		document.addKeywords(root.attributeValue("c"));
		document.addCreator("datascience9.com");
		return writer;
	}
	
	void createFirstPage(Document document, Element root, PdfWriter writer, int questions) throws Exception
	{

		for (int i = 0; i < 8; i++)
		{
			document.add(new Paragraph("\n"));
		}
		
		Paragraph p = new Paragraph(new Chunk(root.attributeValue("d"), FONT_TITLE));
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		document.add(new Paragraph("\n"));
		p = new Paragraph(new Chunk(root.attributeValue("c"), FONT_TITLE));
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		document.add(new Paragraph("\n"));
		
		p = new Paragraph(new Chunk(root.attributeValue("ver"), FONT_TITLE));
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		document.add(new Paragraph("\n"));
		
		p = new Paragraph(new Chunk(questions + " questions", FONT_TITLE));
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		for (int i = 0; i < 20; i++)
		{
			document.add(new Paragraph("\n"));
		}
		String headertext = "www.itcertification2.com";
		p = new Paragraph(new Chunk(headertext, FONT_SMALL));
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		drawRectangle(document, writer);
		document.newPage();
	}
	
	void generateParagraph(Element e, Document document) throws Exception
	{
		Paragraph p = null;
		if (null != e)
		{
			document.add(new Paragraph("\n"));
			p = new Paragraph(e.getText()+"\n", FONT_REG);
			p.setSpacingAfter(10.0f);
			document.add(p);
		}
	}
	
	void addImage(File imageDir, String examname, String imagename, Document document) throws Exception
	{
//		examname = examname.toLowerCase();
		File file = new File(imageDir, imagename);
System.out.println(examname + ":" + file.getAbsolutePath());		
		Image jpeg = Image.getInstance(file.getAbsolutePath());
		
		jpeg.scaleAbsoluteWidth(document.getPageSize().getWidth() - 50); //border

        jpeg.setAlignment(Image.MIDDLE);
		document.add(jpeg);
		document.add(new Paragraph("\n"));
	}
	
	static HeaderFooter getHeader(String coursename)
	  {
	    HeaderFooter header = null;

	    try {
			Phrase phrase = new Phrase();
			String prefix = "www.itcertification2.com";
			
			String headertext = coursename;
			phrase.add(new Chunk(prefix + "\n" + headertext, FONT_HEADER));
			
			header = new HeaderFooter(phrase, false);
			header.setAlignment(HeaderFooter.ALIGN_CENTER);
	    }
	    catch(Exception e) {
			System.err.println(e);
		}

	    return header;
	  }

	  static HeaderFooter getFooter(Document doc)
	  {
	    HeaderFooter footer = null;

	    try {
			footer = new HeaderFooter(new Phrase("Page: " + (doc.getPageNumber() +1), FONT_HEADER), false);
			footer.setAlignment(HeaderFooter.ALIGN_CENTER);
	    }
	    catch(Exception e) {
			System.err.println(e);
		}

	    return footer;
	  }
	  
	  
	  static private void drawHorizontalLine(Document document, PdfWriter writer, float ycoordinate)
	  {
		  float ypos = ycoordinate + 14 + 15.0f;
		  PdfContentByte cb = writer.getDirectContent();
		  cb.setLineWidth(2f);
		  cb.setColorStroke(Color.blue);
		  cb.moveTo(document.left(), ypos);
		  cb.lineTo(document.right(), ypos);
		  cb.stroke();
	  }
	  
	  static private void drawRectangle(Document document, PdfWriter writer)
	  {
		  PdfContentByte cb = writer.getDirectContent();
			cb.setColorStroke(Color.darkGray);
	        cb.setLineWidth(0.4f);
			cb.rectangle(20, 20, document.getPageSize().getWidth() - 40, document.getPageSize().getHeight() - 40);
			cb.stroke();
	  }
	  
	class ReportEvent extends PdfPageEventHelper 
	  {
	    
	    public void onOpenDocument(PdfWriter writer, Document document) {
	    }       
	    public void onStartPage(PdfWriter writer, com.lowagie.text.Document document)
	    {
		  document.setHeader(getHeader(courseName));
	    }

	    public void onEndPage(PdfWriter writer, com.lowagie.text.Document document)
	    {
		  document.setFooter(getFooter(document));
		  drawRectangle(document, writer);
	    }

	    
	    /**
	     * @see com.lowagie.text.pdf.PdfPageEventHelper#onCloseDocument(com.lowagie.text.pdf.PdfWriter, com.lowagie.text.Document)
	     */
	    public void onCloseDocument(PdfWriter writer, Document document) {
//	       tpl.beginText();
//	       tpl.setFontAndSize(helv, 12);
//	       tpl.setTextMatrix(0, 0);
//	       tpl.showText("" + (writer.getPageNumber() - 1));
//	       tpl.endText();
	    }

	    
	    public void onParagraph(PdfWriter writer, com.lowagie.text.Document m_document, float paragraphPosition) {}
	    public void onParagraphEnd(PdfWriter writer,com.lowagie.text.Document m_document,float paragraphPosition) 
	    {
	    	if (endparagraph)
	    	{
	    		drawHorizontalLine(m_document, writer, paragraphPosition);
	    		endparagraph = false;
	    	}
	    }
	    public void onChapter(PdfWriter writer,com.lowagie.text.Document m_document,float paragraphPosition, Paragraph title) {}
	    public void onChapterEnd(PdfWriter writer,com.lowagie.text.Document m_document,float paragraphPosition) {}
	    public void onSection(PdfWriter writer,com.lowagie.text.Document m_document,float paragraphPosition, int depth, Paragraph title) {}
	    public void onSectionEnd(PdfWriter writer,com.lowagie.text.Document m_document,float paragraphPosition) {}
	    public void onGenericTag(PdfWriter writer, com.lowagie.text.Document m_document, Rectangle rect, String text) {}
	  }
}
