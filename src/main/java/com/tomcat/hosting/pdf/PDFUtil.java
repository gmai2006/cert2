package com.tomcat.hosting.pdf;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.tomcat.hosting.guice.Utils;

public class PDFUtil
{
//	header
	  public final static String DCACFLAG = "This is a DCAC Flagged Product.";
	  public final static String ISFLAG = "IS Definition Seleted.";
	  public final static String BUSFLAG = "Business Definition Seleted.";
	  public final static String HEADER = "BPR Combined 170 - 320 Report for Products, Attributes, and Rules";
	  
	  public static final Font FONT_BOLD = new Font(Font.HELVETICA, 8, Font.BOLD);
	  public static final Font FONT_BOLD_UNDERLN = new Font (Font.HELVETICA, 8, Font.BOLD|Font.UNDERLINE);
	  public static final Font FONT_REGULAR = new Font (Font.HELVETICA, 8, Font.NORMAL);
	  public static final Font FONT_VERY_SMALL = new Font(Font.HELVETICA, 6, Font.NORMAL);
	  public static final Paragraph NEWLINE = new Paragraph(" " , FONT_VERY_SMALL);
	  public static final Font FONT_HEADER = new Font(Font.HELVETICA, 9, Font.ITALIC);
	/**
	   * use in getProductBasedOnListOfProduct
	   */
	  public static HeaderFooter getHeader(String product, String text)
	  {
	    return getHeader(product, HEADER, text);
	  }

	  /**
	   * use in getProductBasedOnCriteria
	   */
	  public static HeaderFooter getHeader(String product, String firstline, String text) 
	  {
	    HeaderFooter header = null;
	    try
	    {
	      Phrase phrase = new Phrase();

	      phrase.add(new Chunk(firstline, new Font(Font.HELVETICA, 11, Font.BOLDITALIC)));
	      phrase.add(Chunk.NEWLINE);
	      if (!Utils.isStringNull(text))
	      {
	        phrase.add(new Chunk(text, FONT_HEADER));
	        phrase.add(Chunk.NEWLINE);
	      }
	      phrase.add(new Chunk(product, FONT_REGULAR));

	      header = new HeaderFooter(phrase, false);
	      header.setBorder(Rectangle.TOP + Rectangle.BOTTOM);
	      }
	      catch(Exception e) { System.err.println(e); }
	      return header;
	  }

	  
	  public static HeaderFooter getFooter(Document d) 
	  {
	    HeaderFooter footer = null;
	    try {
	    Phrase phrase = new Phrase();
	    DateFormat formatter =
	      DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.US);
	    Date date = new Date(System.currentTimeMillis());
	    String result = formatter.format(date);
	    phrase.add(new Chunk(result, FONT_REGULAR));
	    phrase.add(new Chunk("                                                                                                                                                                                                                                                                                 Page: " + (d.getPageNumber() + 1), FONT_REGULAR));

	    footer = new HeaderFooter(phrase, false);
	    footer.setBorder(Rectangle.TOP);
	    }
	    catch (Exception e) { System.err.println(e); }
	    return footer;
	  }

}
