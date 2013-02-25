package com.example.gkaakash;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import com.itextpdf.text.DocumentException;

public class incomeExpenditure extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] IEResult;
    TableLayout IEtable1, IEtable2, IEtable3, IEtable4;
    TableRow tr;
    TextView label;
    ArrayList<String> IEResultList;
    ArrayList<ArrayList> IEGrid;
    ArrayList<ArrayList> IEGrid1,IEGrid2;
    String[] ColumnNameList;
    String getSelectedOrgType;
    String IEToDateString;
    Boolean updown=false;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
 	String colValue;
 	Boolean alertdialog = false;
    ObjectAnimator animation2;
    boolean reportmenuflag;
    String financialToDate,financialFromDate,OrgName,date_format,OrgPeriod ,TrialPeriod,sFilename,Reporttypeflag;
    String[] pdf_params;
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.income_expenditure_table);
        report = new Report();
        client_id= Startup.getClient_id();
   
        //customizing title bar
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
   
        try {
        	/*
        	 * get financial from and to date from startup page
        	 */
        	financialFromDate =Startup.getfinancialFromDate();
        	financialToDate=Startup.getFinancialToDate();
   
        	/*
        	 * get given to date from previous page
        	 */
        	IEToDateString = reportMenu.givenToDateString;
   
        	getSelectedOrgType = reportMenu.orgtype;
   
        	/*
        	 * get reference of all table IDs
        	 */
        	IEtable1 = (TableLayout)findViewById(R.id.maintable1);
        	IEtable2 = (TableLayout)findViewById(R.id.maintable2);
  
        	/*
        	 * set financial from date and to date in textview
        	 */
        	TextView tvfinancialFromDate = (TextView) findViewById( R.id.tvTfinancialFromDate );
        	TextView tvfinancialToDate = (TextView) findViewById( R.id.tvTfinancialToDate );
        	//tvfinancialFromDate.setText("Financial from : " +financialFromDate);
        	//tvfinancialToDate.setText("Financial to : " +IEToDateString);
        	tvfinancialToDate.setText("Period : "+financialFromDate+" to "+IEToDateString);   
   
        	/*
        	 * send params to controller report.getProfitLossDisplay to get the result
        	 */
        	Object[] params = new Object[]{financialFromDate,financialFromDate,IEToDateString,"profitloss",getSelectedOrgType};
        	IEResult = (Object[]) report.getProfitLossDisplay(params,client_id);
   
        	//IEResult is 3 dimensional list 
        	int count = 0;
        	for(Object tb : IEResult){
       	
        		Object[] t = (Object[]) tb;
        		count = count + 1;
        		IEGrid = new ArrayList<ArrayList>();
           
        		for(Object tb1 : t){
           	
        			Object[] t1 = (Object[]) tb1;
        			IEResultList = new ArrayList<String>();
           	
        			for(int j=0;j<t1.length;j++){
           	
        				IEResultList.add((String) t1[j].toString());
        			}
           	
        			IEGrid.add(IEResultList);
        		}
        		/*
        		 * set 4 tables for Di, DE, II and IE respectively
        		 */
        		if(count == 1){
        			addTable(IEtable1);
        			IEGrid1=IEGrid;
        		}
        		else if(count == 2){
        			addTable(IEtable2);
        			IEGrid2=IEGrid;
        		}
           
        	}
        	
		   
        	 Date date= new Date();
	   		 date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
	         OrgPeriod = "Financial Year:\n "+financialFromDate+" to "+financialToDate;
	         TrialPeriod = financialFromDate+" to "+IEToDateString;
	        // trialBalGrid = new ArrayList<ArrayList>();
        	Reporttypeflag = reportMenu.reportTypeFlag;
        	final TextView tvReportTitle = (TextView)findViewById(R.id.tvReportTitle);
            tvReportTitle.setText("Menu >> "+"Report >> "+Reporttypeflag);
            final Button btnSaveRecon = (Button)findViewById(R.id.btnSaveRecon);
            btnSaveRecon.setVisibility(Button.GONE);
            final Button btnPdf = (Button)findViewById(R.id.btnPdf);
            final Button btnScrollDown = (Button)findViewById(R.id.btnScrollDown);
            if(reportmenuflag==true){
    	    	
	    		OrgName = createOrg.organisationName;
	    		
           }
           else {
        	    OrgName= selectOrg.selectedOrgName;
         
           }
            btnScrollDown.setOnClickListener(new OnClickListener() {
 	
            	@Override
            	public void onClick(View v) {
            		if(updown==false){
            			ScrollView sv = (ScrollView)findViewById(R.id.ScrollIncome);
            			sv.fullScroll(ScrollView.FOCUS_DOWN); 
            			btnScrollDown.setBackgroundResource(R.drawable.up);
            			updown=true;
            		}else {
            			ScrollView sv = (ScrollView)findViewById(R.id.ScrollIncome);
            			sv.fullScroll(ScrollView.FOCUS_UP); 
            			btnScrollDown.setBackgroundResource(R.drawable.down);
            			updown=false;
            		}
            	}
            });
            if(Reporttypeflag.equalsIgnoreCase("Income and Expenditure"))
			{
					sFilename = "IE"+"_"+date_format;
		        	pdf_params = new String[]{"I&E",sFilename,OrgName,OrgPeriod,Reporttypeflag,TrialPeriod,"",""};
			}else
		    {
					sFilename = "PL"+"_"+date_format;
		        	pdf_params = new String[]{"P&L",sFilename,OrgName,OrgPeriod,Reporttypeflag,TrialPeriod,"",""};
		    }
            btnPdf.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				AlertDialog.Builder builder = new AlertDialog.Builder(incomeExpenditure.this);
    				   builder.setMessage("Do you want to create PDF")
    				           .setCancelable(false)
    				           .setPositiveButton("Yes",
    				                   new DialogInterface.OnClickListener() {
    				                       public void onClick(DialogInterface dialog, int id) {
    				                    	   	PdfGenaretor pdfgen = new PdfGenaretor();
    				       						try {
    				       						
    				       		 					pdfgen.generateBalancePDFFile(IEGrid1,IEGrid2,pdf_params);
    				       		 			        AlertDialog.Builder builder1 = new AlertDialog.Builder(incomeExpenditure.this);
    				       		 			        builder1.setMessage("Pdf genration completed ..see /mnt/sdcard/"+sFilename);
    				       		 			        AlertDialog alert1 = builder1.create();
    				       		 			        alert1.show();
    				       		 			        alert1.setCancelable(true);
    				       		 			        alert1.setCanceledOnTouchOutside(true);
    											} catch (DocumentException e) {
    												// TODO Auto-generated catch block
    												e.printStackTrace();
    											}
    				                       } 
    				                   })
    				               .setNegativeButton("No", new DialogInterface.OnClickListener() {
    						       public void onClick(DialogInterface dialog, int id) {
    						         
    						       }
    						   });
    				   AlertDialog alert = builder.create();
                       alert.show();
    			}
    		});
           
            animated_diolog();
        } catch (Exception e) {
        	//System.out.println("I am an error"+e);
        	AlertDialog.Builder builder = new AlertDialog.Builder(incomeExpenditure.this);
   	
        	builder.setMessage("Please try again")
                  .setCancelable(false)
                  .setPositiveButton("Ok",
                          new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                             
                              }
                          });
                  
        	AlertDialog alert = builder.create();
        	alert.show();	
        }
    }
   
    
    private void animated_diolog() {
    	try {
            final LinearLayout Llalert = (LinearLayout)findViewById(R.id.Llalert);
            Llalert.setVisibility(LinearLayout.GONE);
            animation2 = ObjectAnimator.ofFloat(Llalert,
                    "x", 1000);
            animation2.setDuration(1000);
            animation2.start();
            
            final Button btnOrgDetailsDialog = (Button) findViewById(R.id.btnOrgDetailsDialog);
        
           
            btnOrgDetailsDialog.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                   btnOrgDetailsDialog.setAlpha(100);
                    if(alertdialog==false){
                        Llalert.setVisibility(LinearLayout.VISIBLE);
                        TextView tvOrgNameAlert = (TextView)findViewById(R.id.tvOrgNameAlert);
                        
                        if(reportmenuflag==true){
                            tvOrgNameAlert.setText(createOrg.organisationName);
                        }
                        else {
                        	tvOrgNameAlert.setText(selectOrg.selectedOrgName);
                        }
                        TextView tvOrgTypeAlert = (TextView)findViewById(R.id.tvOrgTypeAlert);
                        tvOrgTypeAlert.setText(reportMenu.orgtype);
                        
                        TextView tvFinancialYearAlert = (TextView)findViewById(R.id.tvFinancialYearAlert);
                        tvFinancialYearAlert.setText(reportMenu.financialFromDate+" to "+ reportMenu.financialToDate);
                        
                        animation2 = ObjectAnimator.ofFloat(Llalert,
                                  "x", 300);
                        alertdialog=true;
                     }else {
                         
                        animation2 = ObjectAnimator.ofFloat(Llalert,
                                  "x", 1000);
                        alertdialog=false;
                     }
                      
                     animation2.setDuration(1000);
                     animation2.start();
                }
                
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void addTable(TableLayout tableID) {
        /** Create a TableRow dynamically **/
        for(int i=0;i<IEGrid.size();i++){
       
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(IEGrid.get(i));
            //create new row
            tr = new TableRow(this);
            //System.out.println("i am row"+columnValue);
            
            if(columnValue.get(2).equalsIgnoreCase("Amount")){
            	//for heading pass green color code
            	setRowColorSymbolGravity(columnValue, Color.parseColor("#348017"));
            }
            else{
            	//for remaining rows pass black color code
            	setRowColorSymbolGravity(columnValue, Color.BLACK);
            }
            
            // Add the TableRow to the TableLayout
            tableID.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }
    
    
    /*
     * 1. set the green background color for heading and black for remaining rows
     * 2. set right aligned gravity for amount and center aligned for other values
     * 3. set rupee symbol for amount
     */
    private void setRowColorSymbolGravity(ArrayList<String> columnValue, int color) {
    	for(int j=0;j<columnValue.size();j++){
            /** Creating a TextView to add to the row **/
           
            
    		if(j==2){//for amount coloumn
    			if(columnValue.get(j).equalsIgnoreCase("Amount")){ // for heading "Amount"
    				//For adding rupee symbol
    				final SpannableString rsSymbol = new SpannableString(incomeExpenditure.this.getText(R.string.Rs));
    				addRow(rsSymbol+" "+columnValue.get(j));   
    				label.setBackgroundColor(color);
    				label.setGravity(Gravity.CENTER);
    			}
    			else{
    				addRow(columnValue.get(j));   
    				label.setBackgroundColor(color);
    				label.setGravity(Gravity.RIGHT);
                   
                    if(columnValue.get(j).length() > 0){///
                    	colValue=columnValue.get(j);
                    	if(!"".equals(colValue)){
                    		//System.out.println("m in ");
                    		if(!"0.00".equals(colValue)){
                    			Pattern pattern = Pattern.compile("\\n");
                    			Matcher matcher = pattern.matcher(colValue);
                    			boolean found = matcher.find();
                    			//System.out.println("value:"+found);
                    			if(found==false){
                    				double amount1 = Double.parseDouble(colValue);	
                    				//System.out.println("A:"+amount1);
                    				((TextView) label).setText(formatter.format(amount1));
                    			}else {
                    				((TextView) label).setText(colValue);
                    			}
                    		}
                    	}
                    }
    			}
            }
            else if (j==1){
           
            	if(columnValue.get(j).equalsIgnoreCase("Direct Expense")
            			||columnValue.get(j).equalsIgnoreCase("Direct Income")
            			||columnValue.get(j).equalsIgnoreCase("Indirect Expense")
            			||columnValue.get(j).equalsIgnoreCase("Indirect Income")){ // for heading "Amount"
            		label.setGravity(Gravity.LEFT);
            		addRow(columnValue.get(j));   
            		label.setBackgroundColor(color);
            	}else
            	{
            		if(!columnValue.get(j).equalsIgnoreCase("Total"))
            		{
            			addRow("          "+columnValue.get(j));   
            			label.setBackgroundColor(color);
       	       
            		}else
            		{
            			addRow(columnValue.get(j));   
            			label.setBackgroundColor(color);
            			label.setGravity(Gravity.RIGHT);
            		}
            	}
            }else{
            	addRow(columnValue.get(j));   
            	label.setBackgroundColor(color);
            }
        }
    }

    /*
     * this function add the value to the row
     */
    void addRow(String param){
        label = new TextView(this);
        label.setText(param);
        label.setTextSize(18);
        label.setTextColor(Color.WHITE);
        label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        label.setPadding(2, 2, 2, 2);
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 1, 1, 1);
        Ll.addView(label,params);
        tr.addView((View)Ll); // Adding textView to tablerow.
    }

}