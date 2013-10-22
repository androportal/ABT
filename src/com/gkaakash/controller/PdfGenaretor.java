package com.gkaakash.controller;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.os.Environment;
import android.text.SpannableString;
import android.view.Gravity;

import com.example.gkaakash.R;
import com.example.gkaakash.bankReconciliation;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
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

	/***
	 * PdfGenarator() default constructor  
	 * It define fonts for different sizes and font families
	 * it will also sate current date format in dateMonthyear_houminutesecond
	 * it create object of Document class to pdfwriter
	 * pdf writer is the writer for pdf 
	 */
	public PdfGenaretor() 
	{
		smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
		//smallBold.setStyle(Font.UNDERLINE);
		///smallBold.setStyle(Font.BOLD);
		bigBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
		smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL); 
		date= new Date();
		date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
		// create document object to pass pdfwriter
		document = new Document(PageSize.A4.rotate());
	}
	/***
	 * method will generate pdf for reports
	 * it only create pdf for ledger, net trail balance , gross trial balance, extended trial balance,
	 * Project statement
	 * @param Grid ArrayList of ArrayList take all reports grid from view
	 * @param pdf_params list of String contain 7 values
	 * Example:{report_flag,sFilename,OrgName,OrgPeriod,"Ledger for: "+accountName,LedgerPeriod,"Project: "+Ledger_project,};
	 * @param password 
	 * @throws DocumentException 
	 * It takes Grid value from the its respective report.
	 * for example: ledegr.java will call this method and pass the ledger grid what ledger.java 
	 * already has, contain all the ledger values coming from back-end
	 * calls createTitle from this class,it will create Title of reports
	 * like its organisation Name, financial Year and report type etc..
	 * It also set the alignment depending on fields like if amount column then
	 * align it at right side and else centre etc..
	 */
	public void generatePDFFile(ArrayList<ArrayList> Grid,String[] pdf_params, String password) throws DocumentException
	{
		try
		{
			/* call createTitle method to set the title of pdf file*/
			this.cerateTitle(pdf_params, password);
			/* define table instances */
			PdfPTable table;
			PdfPTable table1;
			/* it is list of strings for header of table*/
			String[] ColumnName;
			float[] columnWidths;/*variable for column width of type float list*/
			/* check for the ledger as "L" first parameter of pdf_params list come
			 * from calling function */
			if(pdf_params[0].equalsIgnoreCase("L"))
			{
				table = new PdfPTable(5); // create table instance with column 5 
				ColumnName = new String[] {"Date","Particulars","Ref.no","Debit(Rs)","Credit(Rs)"};// list of string to add in column
				columnWidths = new float[] {20f, 40f, 10f, 30f, 30f};//list of floats to set column width 
			}
			else // if calling function is not ledger then
			{	
				// check for gross trial balance,project statement,Net tail balance
				if(pdf_params[0].equalsIgnoreCase("GrossT")||pdf_params[0].equalsIgnoreCase("ProjeST")||pdf_params[0].equalsIgnoreCase("NetT"))
				{
					table = new PdfPTable(4);// create table instance with column 4
					if(!pdf_params[0].equalsIgnoreCase("NetT"))
						ColumnName = new String[] {"AccountName","GroupName","Total Debit(Rs)","Total Credit(Rs)"};// list of string to add in column
					else
						ColumnName = new String[] {"AccountName","GroupName","Debit(Rs)","Credit(Rs)"};// list of string to add in column
					columnWidths = new float[] {40f,40f,30f,30f};//list of floats to set column width 
				}// end of if
				else if(pdf_params[0].equalsIgnoreCase("cash"))
				{
					// if it is extended trial balance then 
					table = new PdfPTable(3);// create table instance with column 7
					ColumnName = new String[] {"Particular","Debit Balance(Rs)","Credit Balance(Rs)"};
					columnWidths = new float[] {60f,50f,50f};
				}else
				{
					// if it is extended trial balance then 
					table = new PdfPTable(7);// create table instance with column 7
					ColumnName = new String[] {"AccountName","GroupName","Opening Balance","Total Dr(Rs)","Total Cr(Rs)","Debit(Rs)","Credit(Rs)"};
					columnWidths = new float[] {60f,60f,60f,50f,50f,40f,40f};
				}// end of second else
			}// end of first else

			/* for loop to add column name in table column */
			for(int k=0;k<ColumnName.length;k++)
			{
				PdfPCell column_header = new PdfPCell(new Phrase(ColumnName[k],bigBold));//create cells for first row till loop 
				/*check if it is gross trial balance or project statement */
				if(pdf_params[0].equalsIgnoreCase("GrossT")||pdf_params[0].equalsIgnoreCase("ProjeST"))
				{  
					if(ColumnName[k].equalsIgnoreCase("Total Debit(Rs)")||ColumnName[k].equalsIgnoreCase("Total Credit(Rs)"))
						column_header.setHorizontalAlignment(Element.ALIGN_RIGHT);
					else
						column_header.setHorizontalAlignment(Element.ALIGN_CENTER);
				}else
				{
					if(ColumnName[k].equalsIgnoreCase("Debit(Rs)")||ColumnName[k].equalsIgnoreCase("Credit(Rs)"))
						column_header.setHorizontalAlignment(Element.ALIGN_RIGHT);
					else
						column_header.setHorizontalAlignment(Element.ALIGN_CENTER);

				}
				column_header.setBorder(Rectangle.NO_BORDER); // cells with No BORDER
				column_header.setUseBorderPadding(true); // to set padding for entire row
				column_header.setBorderWidthTop(1f);// set border for at top of header
				column_header.setBorderWidthBottom(1f); // set border for at top of bottom
				table.addCell(column_header); // add cell in table
				table.setWidths(columnWidths);// set width of column
			}
			/* loop through the grid values coming from calling function
			 * it is the main body of table all the values of column  */ 
			System.out.println("grid vlaue "+Grid);
			for(int i=0;i<Grid.size();i++)
			{
				ArrayList<String> column = new ArrayList<String>();
				column.addAll(Grid.get(i));
				System.out.println("grid vlaue each"+Grid.get(i));
				PdfPCell column_table;
				if(!pdf_params[0].equalsIgnoreCase("L")&&!pdf_params[0].equalsIgnoreCase("cash")) // if not ledger it removes value of 0th index of grid
					column.remove(0);
				if(pdf_params[0].equalsIgnoreCase("L")&&(column.size()==6))//else size of grid will be 6 if narration flag has checked
					column.remove(5); // then remove narrartion column value

				/*loop throught all the column values*/
				for(int j=0;j<column.size();j++)
				{
					
					String value = column.get(j);
					System.out.println("clone");
					if(column.get(1).equalsIgnoreCase("Total of Transactions")||
							column.get(1).equalsIgnoreCase("Grand Total")||
							column.get(0).equalsIgnoreCase(""))
					{
						if(value.equals("0.00")) // if value is zero no need to display 
							column_table = new PdfPCell(new Phrase("",smallBold));
						else
							column_table = new PdfPCell(new Phrase(value,smallBold));
						column_table.setBorder(Rectangle.NO_BORDER);// set No BORDER
						if(column.get(1).equalsIgnoreCase("Grand Total")||column.get(1).equalsIgnoreCase(""))
						{
							column_table.setUseBorderPadding(true);
							column_table.setBorderWidthBottom(1f);// bottom border to grand total 
						}
					}
					else
					{
						if(value.equals("0.00")) // if value is zer dont show it for middel column values
							column_table = new PdfPCell(new Phrase("",smallNormal));
						else
							column_table = new PdfPCell(new Phrase(value,smallNormal));
						column_table.setBorder(Rectangle.NO_BORDER);
					}
					if(pdf_params[0].equalsIgnoreCase("NetT")||pdf_params[0].equalsIgnoreCase("GrossT")||pdf_params[0].equalsIgnoreCase("ProjeST"))
					{
						System.out.println("not in leder"+j);
						if(j==2||j==3)// 2nd and 3rd amount column align it at right 
						{
							column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else
						{
							column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
					}
					else if(pdf_params[0].equalsIgnoreCase("ExtendedT"))
					{
						if(j==0 ||j==1)// except 0th and 1st column align all at right side
						{
							column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else
						{
							column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
							
						}
					}else
					{   // ledger,extended tail balance
						
						if(j==3||j==4)// align column 3rd and 4th at right 
						{
							column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else
						{
							column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
					}

					table.addCell(column_table);// add cell to table
					table.setWidths(columnWidths);// set width to column
				}
			}
			table.setWidthPercentage(100f);// set the table width in float 100f it will take full size
			document.add(table); // add table in document

			/*To add difference in opening balance */
			if(!pdf_params[0].equalsIgnoreCase("L")&&!pdf_params[0].equalsIgnoreCase("cash"))
			{
				// add a couple of blank line
				document.add(new Paragraph("\n")); // put blank space after body of table 
				Paragraph p = new Paragraph("Difference in Opening Balances: "+ pdf_params[7],smallBold); // create paragraph for attaching diffrence
				p.setAlignment(Element.ALIGN_RIGHT);// align that difference at right 
				document.add(p);// add paragraph to document
			}
			document.close(); // finally close document
		}// end of try block
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}// end of atch block
	}

	/***
	 * This method is to genarate PDF for balncesheet,cash flow,profit&loss,Income&Expenditure,bankreconciliation
	 * @param Grid1 contain value for left hand side of tables
	 * @param Grid2 contain value for right hand side of tables
	 * @param pdf_params 
	 * @throws DocumentException
	 */
	public void generateBalancePDFFile(ArrayList<ArrayList> Grid1,ArrayList<ArrayList> Grid2,String[] pdf_params, String password) throws DocumentException
	{

		try {
			/* call createTitle method*/
			cerateTitle(pdf_params, password);
			/* Create table for body content */
			PdfPTable table;
			PdfPTable table1;
			/* check for the should not be sources balancesheet and not bank econciliation */
			if(!pdf_params[0].equalsIgnoreCase("Sources_bal")&&!pdf_params[0].equalsIgnoreCase("BankRec"))
			{
				PdfPCell column_header,column_table;
				String[] ColumnName;
				float[] columnWidths;

				//Create table with two columns right column in each of them will add table
				table = new PdfPTable(2);
				for(int k=0;k<2;k++) // loop to create two table if k=0 then left side table,and k=1 then right side table
				{
					if(pdf_params[0].equalsIgnoreCase("Conv_bal"))
					{
						table1 = new PdfPTable(4);// if canventional balancesheeet then it will have 4 columns
						columnWidths = new float[] {60f,50f,50f,70f};
					}
					else if(pdf_params[0].equalsIgnoreCase("cash"))
					{
						table1 = new PdfPTable(2);// if cash flow then it will have 2 columns
						columnWidths = new float[] {60f,60f};
					}else
					{
						table1 = new PdfPTable(3);// if P&L and I&E then it will have 3 columns
						columnWidths = new float[] {10f,60f,60f};
					}
					if(k==0)
					{
						BalanceGrid = Grid1;// left side table column value
					}else
					{
						BalanceGrid = Grid2;// right side column value
					}
					// this for loop to get the values of columns 
					for(int i=0;i<BalanceGrid.size();i++)
					{
						ArrayList<String> column = new ArrayList<String>();
						column.addAll(BalanceGrid.get(i)); // add list of values of balancegrid of ith index  
						Integer val = BalanceGrid.size()-1;// get the last index value of the balance-grid
						for(int j=0;j<column.size();j++)// now loop through each column value of that list
						{
							String value = column.get(j);
							if(i==0)// for the first list of balancegrid
							{
								if(j==1||j==2||j==3) // for the values if the list (first,second and thir column)
								{	
									column_table = new PdfPCell(new Phrase(value+"(Rs)",smallBold));// make it bold 
									column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);// its amount field right align 
								}else
								{
									column_table = new PdfPCell(new Phrase(value,smallBold));
									column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
								}
								column_table.setBorder(Rectangle.NO_BORDER);// 
								column_table.setBorderWidthTop(1f); // header need border at the top
								column_table.setBorderWidthBottom(1f); // and at the bottom
								column_table.setNoWrap(true);// it will wrap the value

							}else if(i==val) // if i is last list of grid then do below
							{
								column_table = new PdfPCell(new Phrase(column.get(j),smallBold)); // values bold 
								if(pdf_params[0].equalsIgnoreCase("Conv_bal"))
								{
									if(j==1||j==2||j==3)// for the values if the list (first,second and third column)
									{	
										column_table.setHorizontalAlignment(Element.ALIGN_RIGHT); // amount field at right
									}else
									{
										column_table.setHorizontalAlignment(Element.ALIGN_CENTER);// else center
									}
									column_table.setBorder(Rectangle.NO_BORDER);
									column_table.setBorderWidthBottom(1f); // give bottom border to last 
								}else // except conventional bal nothing is there in at first second and third place
								{	
									column_table.setBorder(Rectangle.NO_BORDER);
									column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
									column_table.setBorderWidthBottom(1f);
								}
							}else
							{
								if(pdf_params[0].equalsIgnoreCase("Conv_bal"))
								{   // to bring last "Total" to equal position need to give black spaces to next line
									if(value == ""){
										column_table = new PdfPCell(new Paragraph("\n"));
									}else
									{	
										column_table = new PdfPCell(new Phrase(value,smallNormal));
									}
									if(j==1||j==2||j==3)
									{	
										if(column.get(1)==""&&column.get(2)=="") // if 1st and 2nd value is empty then third value bold
											column_table = new PdfPCell(new Phrase(value,smallBold));

										column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
									}
									column_table.setBorder(Rectangle.NO_BORDER);
								}else
								{
									if(pdf_params[0].equalsIgnoreCase("cash"))// if cash flow then
									{
										column_table = new PdfPCell(new Phrase(column.get(j),smallNormal));
										if(j==1)// j=1 will be amount column
											column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
										else
											column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
										column_table.setBorder(Rectangle.NO_BORDER);
									}else // its for profir & loss or Income & Expenditure
									{
										if(value.equalsIgnoreCase("Direct Expense")||
												value.equalsIgnoreCase("Direct Income")||
												value.equalsIgnoreCase("Indirect Expense")||
												value.equalsIgnoreCase("Indirect Income"))// check for the all text if same then
										{
											column_table = new PdfPCell(new Phrase(column.get(j),smallBold)); // bold text
											column_table.setHorizontalAlignment(Element.ALIGN_CENTER); // align center 
										}
										else if(value.equalsIgnoreCase("Total")||
												(column.get(1).equalsIgnoreCase("Total")&&j==2))//if value of column is total and j=2 amount value
										{
											column_table = new PdfPCell(new Phrase(column.get(j),smallBold)); // bold 
											column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);// align amount and total to ight
										}else
										{
											column_table = new PdfPCell(new Phrase(column.get(j),smallNormal));
											if(j==2) //
												column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
											else
												column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
										}
										column_table.setBorder(Rectangle.NO_BORDER);
									}
								}
							}

							table1.addCell(column_table);
						}// end of j for loop

						table1.setWidthPercentage(100f);
						table1.setWidths(columnWidths);

					}// end of i for loop
					column_header = new PdfPCell(table1);
					column_header.setBorder(Rectangle.NO_BORDER);
					if(k==0)// put border width at the right so we can get the middel line
						column_header.setBorderWidthRight(1f);
					table.addCell(column_header);
				}// end of table k for loop	
				// add a couple of blank line
				table.setWidthPercentage(100f);
				//document.setPageSize(PageSize.A4);
				document.add(table);
				document.add(new Paragraph("\n"));
				Paragraph p = new Paragraph(pdf_params[7],smallBold); // differene in opening balances
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				
				document.close();
			}// end of if condition of not sources and fund balancesheet and bank reconciliation ()
			else if(pdf_params[0].equalsIgnoreCase("Sources_bal")) // its for sources and fund balacesheet(vertical balancesheet)
			{
				float[] columnWidths;
				String[] ColumnName;
				columnWidths = new float[] {80f, 30f, 30f, 30f, 30f};
				PdfPCell column_table;
				//Create table for body content 
				for(int k=0;k<2;k++){
					if(k==0)
					{
						BalanceGrid = Grid1;
					}else
					{
						BalanceGrid = Grid2;
					}

					table = new PdfPTable(5);
					for(int i=0;i<BalanceGrid.size();i++){
						ArrayList<String> column = new ArrayList<String>();
						column.addAll(BalanceGrid.get(i));
						Integer val = BalanceGrid.size()-1;
						for(int j=0;j<column.size();j++){
							String value = column.get(j);

							if(i==0)
							{
								if(j!=0)// except first column value 
								{
									column_table = new PdfPCell(new Phrase(value+"(Rs)",smallBold));// add Rs and coumn value bold
									column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);// algin it to right
								}
								else // for first column
								{
									column_table = new PdfPCell(new Phrase(value,smallBold));//
									column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
								}	
								column_table.setBorder(Rectangle.NO_BORDER);
								column_table.setBorderWidthTop(1f);// its heade column so put border at top 
								column_table.setBorderWidthBottom(1f); // and at bottom
							}else if(i==val)// this is the last grid list
							{
								if(j!=0)// except first column value
								{
									column_table = new PdfPCell(new Phrase(value,smallBold));
									column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
								}else
								{
									column_table = new PdfPCell(new Phrase(value,smallBold));

								}
								column_table.setBorder(Rectangle.NO_BORDER);
								column_table.setBorderWidthBottom(1f); // border at bottom fo 
							}
							else
							{
								if(j!=0) // check first column value
								{
									column_table = new PdfPCell(new Phrase(value,smallNormal));
									column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
									column_table.setBorder(Rectangle.NO_BORDER);
								}else
								{
									column_table = new PdfPCell(new Phrase(value,smallNormal));
									column_table.setBorder(Rectangle.NO_BORDER);
								}
							}
							table.addCell(column_table);// finally add cell to table
						}
						// add a couple of blank line
					}
					table.setWidthPercentage(100f);
					table.setWidths(columnWidths);
					document.add(table);
					document.add(new Paragraph("\n"));

				}
				Paragraph p = new Paragraph(pdf_params[7],smallBold);
				p.setAlignment(Element.ALIGN_RIGHT);
				document.add(p);
				document.close();
			}else // only for bank reconciliation 
			{
				float[] columnWidths,columnWidths1;
				String[] ColumnName;
				ColumnName = new String[] {"Date","Particulars","Ref.no","Debit(Rs)","Credit(Rs)","Clearence Date"};
				columnWidths = new float[] {40f,60f,20f,40f,40f,40f}; // for all the grid values
				columnWidths1 = new float[] {60f,40f}; //for display statement 
				PdfPCell column_table;
				table = new PdfPTable(6);
				/*loop through string of headers "cloumnName"*/
				for(int k=0;k<ColumnName.length;k++)
				{
					// create cell and pass the column value
					PdfPCell column_header = new PdfPCell(new Phrase(ColumnName[k],bigBold));
					// check for debit and credit value to align it right
					if(ColumnName[k].equalsIgnoreCase("Debit(Rs)")||ColumnName[k].equalsIgnoreCase("Credit(Rs)"))
						column_header.setHorizontalAlignment(Element.ALIGN_RIGHT);
					else
						column_header.setHorizontalAlignment(Element.ALIGN_CENTER);
					column_header.setBorder(Rectangle.NO_BORDER);
					column_header.setUseBorderPadding(true);
					column_header.setBorderWidthTop(1f);
					column_header.setBorderWidthBottom(1f);
					table.addCell(column_header);
					table.setWidths(columnWidths);
				} // end the for k loop of bank reconciliation header 

				/*Create table for body content 
				 *loop for k */

				for(int k=0;k<2;k++)
				{
					if(k==0) // k=0 is for bank reconcile values
					{
						BalanceGrid = Grid1;
						for(int i=0;i<BalanceGrid.size();i++)
						{
							ArrayList<String> column = new ArrayList<String>();
							column.addAll(BalanceGrid.get(i));
							column.remove(0);
							column.remove(6);
							Integer val = BalanceGrid.size()-1;
							for(int j=0;j<column.size();j++)
							{
								String value = column.get(j);
								System.out.println("value :"+value);
								if(i==val)
								{
									column_table = new PdfPCell(new Phrase(value,smallBold));
									if(j==2)
										column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
									else
										column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);

									column_table.setBorder(Rectangle.NO_BORDER);
									column_table.setBorderWidthBottom(1f);
									table.addCell(column_table);
								}else
								{
									column_table = new PdfPCell(new Phrase(value,smallNormal));
									if(j==3||j==4)
										column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);	
									else
										column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
									column_table.setBorder(Rectangle.NO_BORDER);
									table.addCell(column_table);
								}

							}
						}
						table.setWidthPercentage(100f);
						document.add(table);
					}else // else for statement
					{
						BalanceGrid = Grid2; // second table of second grid statement grid
						table1 = new PdfPTable(2);
						for(int i=0;i<BalanceGrid.size();i++)
						{
							ArrayList<String> column = new ArrayList<String>();
							column.addAll(BalanceGrid.get(i));

							for(int j=0;j<column.size();j++)
							{
								String value = column.get(j);
								if(i==0)
								{ //this is a header row
									if(j==1 || j==5)
									{//statement and amount column
										if(j==5)
										{
											column_table = new PdfPCell(new Phrase(value,smallBold));
											column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
											column_table.setBorder(Rectangle.NO_BORDER);
											column_table.setUseBorderPadding(true);
											column_table.setBorderWidthTop(1f);
											column_table.setBorderWidthBottom(1f);
											table1.addCell(column_table);
										}
										else
										{
											column_table = new PdfPCell(new Phrase(value,smallBold));
											column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
											column_table.setBorder(Rectangle.NO_BORDER);
											column_table.setUseBorderPadding(true);
											column_table.setBorderWidthTop(1f);
											column_table.setBorderWidthBottom(1f);
											table1.addCell(column_table);
										}
									}

								}
								else
								{	
									//remaining rows
									if(j==1 || j==5)
									{//statement and amount column
										column_table = new PdfPCell(new Phrase(value,smallNormal));
										if(j==5)
											column_table.setHorizontalAlignment(Element.ALIGN_RIGHT);
										else
											column_table.setHorizontalAlignment(Element.ALIGN_CENTER);
										column_table.setBorder(Rectangle.NO_BORDER);
										if(i==5){
											column_table.setUseBorderPadding(true);
											column_table.setBorderWidthBottom(1f);
										}
										table1.addCell(column_table);
									}
									
								}

							}

						}	
						table1.setWidthPercentage(100f);
						table1.setWidths(columnWidths1);
						// add a couple of blank line
						document.add(new Paragraph("\n"));
						document.add(table1);	
						
					} 

				}// end for k loop 
				document.close();
			}
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();		
		}
	}
	/***
	 * This function is to create title to each pdf  
	 * @param pdf_params
	 * @param password 
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	void cerateTitle(String[] pdf_params, String password) throws FileNotFoundException, DocumentException
	{
		/* Get the /mnt/sdcard */
		File root = Environment.getExternalStorageDirectory();
		/* Create new file with sFilename*/ 
		File pdffile = new File(root,pdf_params[1]+".pdf");
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdffile));
		//set password for pdf file
		if(password != null){
			writer.setEncryption("hello".getBytes(), password.getBytes(),
					PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
		}
		// open the document 
		document.open(); 
		/* Create Header Table*/
		PdfPTable title_table = new PdfPTable(3);
		title_table.setWidthPercentage(100f);
		float[] columnWidths1 = new float[] {25f,45f,30f};
		/* first cell contain genarated date which will the current date */
		PdfPCell h1 = new PdfPCell(new Phrase("Date of PDF generation:\n"+new SimpleDateFormat("EEE d-MMM-yyyy HH:mm:ss").format(date),smallBold));
		h1.setBorder(Rectangle.NO_BORDER);
		/* second cell will print organisation name and financile year*/
		PdfPCell h2 = new PdfPCell(new Phrase(pdf_params[2]+"\n"+pdf_params[3],bigBold));
		h2.setHorizontalAlignment(Element.ALIGN_CENTER); // align it to centre
		h2.setBorder(Rectangle.NO_BORDER);
		/* this will have name of report,period of report,and project */
		PdfPCell h3 = new PdfPCell(new Phrase(pdf_params[4]+"\n"+pdf_params[5]+"\n"+pdf_params[6],smallBold));
		h3.setHorizontalAlignment(Element.ALIGN_LEFT);// agin ti left
		h3.setBorder(Rectangle.NO_BORDER);
		/* add all three cell to table*/
		title_table.addCell(h1);
		title_table.addCell(h2);
		title_table.addCell(h3);
		title_table.setWidths(columnWidths1);
		// add table to document
		document.add(title_table);
		// add a couple of blank line
		document.add(new Paragraph("\n"));
	}

}

