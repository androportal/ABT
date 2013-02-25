package com.gkaakash.controller;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenaretor {
	Font smallBold,bigBold,smallNormal;
	String date_format,accountname,sFilename;
	Date date;
	Document document;
	ArrayList<ArrayList>BalanceGrid,cashflow;
	public PdfGenaretor() 
	{
		smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10);
		smallBold.setStyle(Font.UNDERLINE);
		smallBold.setStyle(Font.BOLD);
		bigBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
		smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL); 
		date= new Date();
		date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
		// create document object to pass pdfwriter
		document = new Document();
	}

	public void generatePDFFile(ArrayList<ArrayList> Grid,String[] pdf_params) throws DocumentException
	{
		try
		{
		   /* call createTitle method*/
		   cerateTitle(pdf_params);
		   /* Create table for body content */
		   PdfPTable table;
		   String[] ColumnName;
		   float[] columnWidths;
		   if(pdf_params[0].equalsIgnoreCase("L"))
		   {
		    	table = new PdfPTable(5);
		    	
		    	ColumnName = new String[] {"Date","Particulars","Ref.no","Debit","Credit"};
		    	columnWidths = new float[] {20f, 40f, 10f, 30f, 30f};
		   }
		   else
		   {
			   if(pdf_params[0].equalsIgnoreCase("GrossT")||pdf_params[0].equalsIgnoreCase("ProjeST"))
			   {
			    	table = new PdfPTable(4);
			    	
			    	ColumnName = new String[] {"AccountName","GroupName","₹Total Debit","Total Credit"};
			    	columnWidths = new float[] {40f,40f,30f,30f};
			   }
			   else
			   {
				   table = new PdfPTable(7);
			    	ColumnName = new String[] {"AccountName","GroupName","Opening Balance","Total Dr","Total Cr","Debit","Credit"};
			    	columnWidths = new float[] {40f,40f,40f,30f,30f,30f,30f};
			   }
		    }
			table.setWidthPercentage(100f);
			
			for(int k=0;k<ColumnName.length;k++)
			{
			
				PdfPCell c1 = new PdfPCell(new Phrase(ColumnName[k],bigBold));
				if(pdf_params[0].equalsIgnoreCase("GrossT")||pdf_params[0].equalsIgnoreCase("ProjeST"))
				{
					if(ColumnName[k]!="Total Debit"||ColumnName[k]!="Total Credit")
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					else
						c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}else
				{
					if(ColumnName[k]!="Debit"||ColumnName[k]!="Credit")
						c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					else
						c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				}
		   		c1.setBorder(Rectangle.NO_BORDER);
		   		c1.setUseBorderPadding(true);
		   		c1.setBorderWidthTop(1f);
		   		c1.setBorderWidthBottom(1f);
		   		table.addCell(c1);
		   		table.setWidths(columnWidths);
		   }
			 
			for(int i=0;i<Grid.size();i++)
			{
		    	ArrayList<String> column = new ArrayList<String>();
	            column.addAll(Grid.get(i));
	            PdfPCell c;
	            if(!pdf_params[0].equalsIgnoreCase("L"))
	            	column.remove(0);
	            if(pdf_params[0].equalsIgnoreCase("L")&&(column.size()==6))
	            	column.remove(5);
	            
	            for(int j=0;j<column.size();j++)
	            {
	            		String value = column.get(j);
	            		System.out.println("project st grid :"+value);
	            		if(column.get(1).equalsIgnoreCase("Total of Transactions")||
	            		   column.get(1).equalsIgnoreCase("Grand Total")||
	            		   column.get(0).equalsIgnoreCase(""))
	            		{
	            			
	            			if(value.equals("0.00"))
	            				c = new PdfPCell(new Phrase("",smallBold));
	            			else
	            				c = new PdfPCell(new Phrase(value,smallBold));
	            			c.setBorder(Rectangle.NO_BORDER);
	            			
	            			if(column.get(1).equalsIgnoreCase("Grand Total")||column.get(1).equalsIgnoreCase(""))
	            			{
	            				c.setUseBorderPadding(true);
	            				c.setBorderWidthBottom(1f);
	            			}
	            		}
	            		else
	            		{
	            			
	            			if(value.equals("0.00"))
	            				c = new PdfPCell(new Phrase("",smallNormal));
	            			else
	            				c = new PdfPCell(new Phrase(value,smallNormal));
	            			c.setBorder(Rectangle.NO_BORDER);
	            		}
	            		if(!pdf_params[0].equalsIgnoreCase("L"))
	            		{
	            			if(j==2||j==3)
	            			{
	            				c.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            			}
	            			else
	            			{
	            				c.setHorizontalAlignment(Element.ALIGN_CENTER);
	            			}
	            		}
	            		else if (pdf_params[0].equalsIgnoreCase("ExtendedT"))
	            		{
	            			if(j!=0||j!=1)
	            			{
	            				c.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            			}
	            			else
	            			{
	            				c.setHorizontalAlignment(Element.ALIGN_CENTER);
	            			}
	            		}else
	            		{
	            			if(j==3||j==4)
	            			{
	            				c.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            			}
	            			else
	            			{
	            				c.setHorizontalAlignment(Element.ALIGN_CENTER);
	            			}
	            		}
	            			
			 		    table.addCell(c);
			            table.setWidths(columnWidths);
			    	}
			    }
			document.add(table);
			if(!pdf_params[0].equalsIgnoreCase("L"))
			{
			    // add a couple of blank line
				document.add(new Paragraph("\n"));
				Paragraph p = new Paragraph("Difference in Opening Balances: "+ pdf_params[7],smallBold);
				p.setAlignment(Element.ALIGN_RIGHT);
				 document.add(p);
			}
			document.close();
	   }
	   catch (Exception e) 
	   {
		   System.out.println(e.getMessage());
	   }
	}
	public void generateBalancePDFFile(ArrayList<ArrayList> Grid1,ArrayList<ArrayList> Grid2,String[] pdf_params) throws DocumentException
	{
		
		   try {
			   /* call createTitle method*/
			   cerateTitle(pdf_params);
			   /* Create table for body content */
			   PdfPTable table = null;
			   PdfPCell c1,c;
			   PdfPTable table1;
			   String[] ColumnName;
			   float[] columnWidths;
			  
				 //Create table for body content 
			    	table = new PdfPTable(2);
			 
		        	for(int k=0;k<2;k++){
		        		
		        		if(pdf_params[0].equalsIgnoreCase("Conv_bal"))
					   	{
					   		
					   		table1 = new PdfPTable(4);
					    	columnWidths = new float[] {80f,50f,50f,50f};
					   	}
		        		else if(pdf_params[0].equalsIgnoreCase("cash"))
		        		{
		        			table1 = new PdfPTable(2);
		        			columnWidths = new float[] {60f,60f};
		        		}else
		        		{
		        			table1 = new PdfPTable(3);
		        			columnWidths = new float[] {10f,60f,60f};
		        		}
		        		
		        		if(k==0)
		        		{
		        			BalanceGrid = Grid1;
		        		}else
		        		{
		        			BalanceGrid = Grid2;
		        		}
		        		
		        		for(int i=0;i<BalanceGrid.size();i++){
		        			//System.out.println("coloumn size: "+BalanceGrid.size());
		        			ArrayList<String> column = new ArrayList<String>();
		    	            column.addAll(BalanceGrid.get(i));
		    	            Integer val = BalanceGrid.size()-1;
		    	            for(int j=0;j<column.size();j++){
		    	            	String value = column.get(j);
			        			if(i==0)
			        			{
			        				System.out.println("value f col :"+value);
			        				c = new PdfPCell(new Phrase(value,smallBold));
			        				c.setBorder(Rectangle.NO_BORDER);
			        				c.setBorderWidthTop(1f);
			        				c.setBorderWidthBottom(1f);
			        				c.setNoWrap(true);
			        				c.setHorizontalAlignment(Element.ALIGN_CENTER);
			        				
		    	            	}else if(i==val)
		    	            	{
		    	            		c = new PdfPCell(new Phrase(column.get(j),smallBold));
		    	            		if(pdf_params[0].equalsIgnoreCase("Conv_bal"))
		    	            		{
			    	            		if(j==1||j==2||j==3)
			    	            		{	
			    	            			
			    	            			c.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    	            		}else
			    	            		{
			    	            			c.setHorizontalAlignment(Element.ALIGN_CENTER);
			    	            		}
			    	            		c.setBorder(Rectangle.NO_BORDER);
			    	            		c.setBorderWidthBottom(1f);
		    	            		}else
		    	            		{	
				        				c.setBorder(Rectangle.NO_BORDER);
				        				c.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        				c.setBorderWidthBottom(1f);
		    	            		}
		    	            	}else
		    	            	{
		    	            		if(pdf_params[0].equalsIgnoreCase("Conv_bal"))
		    	            		{
			    	            		if(value == ""){
			    	            			System.out.println("blankspace");
			    	            			c = new PdfPCell(new Paragraph("\n"));
			    	            		}else
			    	            		{	
			    	            			c = new PdfPCell(new Phrase(value,smallNormal));
			    	            		}
			    	            		if(j==1||j==2||j==3)
			    	            		{	
			    	            			if(column.get(1)==""&&column.get(2)=="")
			    	            				c = new PdfPCell(new Phrase(value,smallBold));
			    	            			
				        					c.setHorizontalAlignment(Element.ALIGN_RIGHT);
				        					
			    	            		}
			    	            		c.setBorder(Rectangle.NO_BORDER);
		    	            		}else
				        			{
		    	            			if(pdf_params[0].equalsIgnoreCase("cash"))
		    	            			{
					        				c = new PdfPCell(new Phrase(column.get(j),smallNormal));
						        			//c.setBorder(Rectangle.NO_BORDER);
						        			if(j==1)
						        				c.setHorizontalAlignment(Element.ALIGN_RIGHT);
						        			else
						        				c.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	            			}else
		    	            			{
		    	            				
						        			
						        			if(value.equalsIgnoreCase("Direct Expense")||
						        					value.equalsIgnoreCase("Direct Income")||
						        					value.equalsIgnoreCase("Indirect Expense")||
						        					value.equalsIgnoreCase("Indirect Income")
						        					)
						        			{
						        				c = new PdfPCell(new Phrase(column.get(j),smallBold));
						        				c.setHorizontalAlignment(Element.ALIGN_CENTER);
						        			}
						        			else if(value.equalsIgnoreCase("Total")||(column.get(1).equalsIgnoreCase("Total")&&j==2))
						        			{
						        				c = new PdfPCell(new Phrase(column.get(j),smallBold));
						        				c.setHorizontalAlignment(Element.ALIGN_RIGHT);
						        			}else
						        			{
						        				c = new PdfPCell(new Phrase(column.get(j),smallNormal));
						        				c.setHorizontalAlignment(Element.ALIGN_CENTER);
						        			}
						        			c.setBorder(Rectangle.NO_BORDER);
		    	            			}
				        			}
		    	            	}
			        			
			        			table1.addCell(c);
		    	            }
		    	         
		        			table1.setWidthPercentage(100f);
		        			table1.setWidths(columnWidths);
		        		
		        		}
			        		c1 = new PdfPCell(table1);
			         		c1.setBorder(Rectangle.NO_BORDER);
			         		if(k==0)
			         			c1.setBorderWidthRight(1f);
			         		table.addCell(c1);
			         			
			         			
		        		
		        		}	
			    		    // add a couple of blank line
		        			table.setWidthPercentage(100f);
			    	       	document.add(table);
			    		    document.add(new Paragraph("\n"));
							Paragraph p = new Paragraph(pdf_params[7],smallBold);
							p.setAlignment(Element.ALIGN_RIGHT);
							document.add(p);
			    		    document.close();
			   
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}
	void cerateTitle(String[] pdf_params) throws FileNotFoundException, DocumentException
	{
				/* Get the /mnt/sdcard */
				File root = Environment.getExternalStorageDirectory();
				/* Create new file with sFilename*/
				File pdffile = new File(root,pdf_params[1]+".pdf");
				PdfWriter.getInstance(document, new FileOutputStream(pdffile));
			    // open the document 
			    document.open();
			    /* Create Header Table*/
		        PdfPTable title_table = new PdfPTable(3);
		        title_table.setWidthPercentage(100f);
		        float[] columnWidths1 = new float[] {25f,45f,30f};
		        PdfPCell h1 = new PdfPCell(new Phrase("Genrated Date:\n"+new SimpleDateFormat("EEE d-MMM-yyyy HH:mm:ss").format(date),smallBold));
		        h1.setBorder(Rectangle.NO_BORDER);
		    	PdfPCell h2 = new PdfPCell(new Phrase(pdf_params[2]+"\n"+pdf_params[3],bigBold));
		    	h2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    	h2.setBorder(Rectangle.NO_BORDER);
		    	PdfPCell h3 = new PdfPCell(new Phrase(pdf_params[4]+"\n"+pdf_params[5]+"\n"+pdf_params[6],smallBold));
		    	h3.setHorizontalAlignment(Element.ALIGN_LEFT);
		    	h3.setBorder(Rectangle.NO_BORDER);
		    	title_table.addCell(h1);
		    	title_table.addCell(h2);
		    	title_table.addCell(h3);
		    	title_table.setWidths(columnWidths1);
		    	document.add(title_table);
	    		// add a couple of blank line
	    	    document.add(new Paragraph("\n"));
	}
	
}

