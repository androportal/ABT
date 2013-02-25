package com.example.gkaakash;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams; 
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import com.itextpdf.text.DocumentException;

public class projectStatement extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] projectStatementResult;
    TableLayout projectStatementTable;
    TableRow tr;
    TextView label;
    ArrayList<ArrayList> projectStatementGrid;
    ArrayList<String> projectStatementResultList;
    String ToDateString;
    Boolean updown=false;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
    String colValue;
    Boolean alertdialog = false;
    ObjectAnimator animation2;
    boolean reportmenuflag;
    int oneTouch = 1;
    TableLayout floating_heading_table;
    LinearLayout Ll;
    ScrollView sv;
    Float result;
    String financialFromDate,projectName;
   	String financialToDate,OrgName,date_format,OrgPeriod ,Period,sFilename;
    String[] pdf_params;
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.project_statement_table);
        report = new Report();
        client_id= Startup.getClient_id();
   
        reportmenuflag=MainActivity.reportmenuflag;
        //customizing title bar
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
   
        try {
        	floating_heading_table = (TableLayout)findViewById(R.id.floating_heading_table);
        	floating_heading_table.setVisibility(TableLayout.GONE);
	   
        	/*
        	 * get financial from and to date from startup page
        	 */
        	financialFromDate =Startup.getfinancialFromDate();
        	financialToDate=Startup.getFinancialToDate();
        	ToDateString = reportMenu.givenToDateString;
   
        	projectName = reportMenu.selectedProject;
   
        	if(!projectName.equalsIgnoreCase("No Project")){
        		TextView tvProjectName = (TextView) findViewById( R.id.tvProjectName );
        		tvProjectName.setText("Project name: " +projectName);
         		
            }
        	  if(reportmenuflag==true){
         	    	
  	    		OrgName = createOrg.organisationName;
  	    		
             }
             else {
          	    OrgName= selectOrg.selectedOrgName;
           
             }
        	/*
        	 * set financial from date and to date in textview
        	 */
        	TextView tvfinancialToDate = (TextView) findViewById( R.id.tvTfinancialToDate );
        	tvfinancialToDate.setText("Period : "+financialFromDate+" to "+ToDateString);
        	/*
        	 * send params to controller report.getProjectStatementReport to get the result
        	 */
        	Object[] params = new Object[]{projectName, financialFromDate,financialFromDate,ToDateString};
        	projectStatementResult = (Object[]) report.getProjectStatementReport(params,client_id);
      
        	projectStatementGrid = new ArrayList<ArrayList>();
        	for(Object tb : projectStatementResult)
        	{
        		Object[] t = (Object[]) tb;
        		projectStatementResultList = new ArrayList<String>();
        		for(int i=0;i<t.length;i++){
           	
        			projectStatementResultList.add((String) t[i].toString());
              
        		}
        		projectStatementGrid.add(projectStatementResultList);
        	}
        	projectStatementTable = (TableLayout)findViewById(R.id.maintable);
        	addTable();
        
        	final TextView tvReportTitle = (TextView)findViewById(R.id.tvReportTitle);
        	tvReportTitle.setText("Menu >> "+"Report >> "+"Project Statement");
        	final Button btnSaveRecon = (Button)findViewById(R.id.btnSaveRecon);
        	btnSaveRecon.setVisibility(Button.GONE);
        	 final TextView btnPdf = (TextView)findViewById(R.id.btnPdf);
        	final Button btnScrollDown = (Button)findViewById(R.id.btnScrollDown);
        	btnScrollDown.setOnClickListener(new OnClickListener() {
        		@Override
        		public void onClick(View v) {
        			if(updown==false){
        				ScrollView sv = (ScrollView)findViewById(R.id.ScrollProjStatement);
		                sv.fullScroll(ScrollView.FOCUS_DOWN); 
		                btnScrollDown.setBackgroundResource(R.drawable.up);
		                updown=true;
		           }else {
		                ScrollView sv = (ScrollView)findViewById(R.id.ScrollProjStatement);
		                sv.fullScroll(ScrollView.FOCUS_UP); 
		                btnScrollDown.setBackgroundResource(R.drawable.down);
		                updown=false;
		           }
        		}
        	});
        	 Date date= new Date();
	   		     String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
				 OrgPeriod = "Financial Year:\n "+financialFromDate+" to "+financialToDate;
	             Period = financialFromDate+" to "+ToDateString;
	             String project = projectName.replace(" ","");
				 sFilename = "ProjeST"+"_"+project+"_"+date_format;
		         pdf_params = new String[]{"ProjeST",sFilename,OrgName,OrgPeriod,"Project Statement",Period,projectName,String.format("%.2f", Math.abs(result))};
        	btnPdf.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				AlertDialog.Builder builder = new AlertDialog.Builder(projectStatement.this);
    				   builder.setMessage("Do you want to create PDF")
    				           .setCancelable(false)
    				           .setPositiveButton("Yes",
    				                   new DialogInterface.OnClickListener() {
    				                       public void onClick(DialogInterface dialog, int id) {
    				                    	   	PdfGenaretor pdfgen = new PdfGenaretor();
    				       						try {
    				       							
    				       	 	 					pdfgen.generatePDFFile(projectStatementGrid,pdf_params);
    				       	 	 			        AlertDialog.Builder builder1 = new AlertDialog.Builder(projectStatement.this);
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
        	
        	animated_dialog();
        	floatingHeader();
        
        } catch (Exception e) {
        	//System.out.println("error:"+e);
        	AlertDialog.Builder builder = new AlertDialog.Builder(projectStatement.this);
        	builder.setMessage("Please try again")
                  .setCancelable(false)
                  .setPositiveButton("Ok",
                          new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int id) {
                             
                              }
                          });
                  
        	AlertDialog alert = builder.create();
        	alert.show();	}
    }
   
    
    private void floatingHeader() {
    	projectStatementTable.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(oneTouch == 1){
					floating_heading_table.setVisibility(TableLayout.VISIBLE);
					
					//System.out.println("we are in if");
					
					int rowcount = projectStatementTable.getChildCount();    
	                View row = projectStatementTable.getChildAt(rowcount-1);
					
	                final SpannableString rsSymbol = new SpannableString(projectStatement.this.getText(R.string.Rs)); 
	                /** Create a TableRow dynamically **/
	                String[] ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Total debit",rsSymbol+" Total credit"};
	               
	                tr = new TableRow(projectStatement.this);
			        
			        for(int k=0;k<ColumnNameList.length;k++){
			            /** Creating a TextView to add to the row **/
			            addRow(ColumnNameList[k]);
			            label.setBackgroundColor(Color.parseColor("#348017"));
			            label.setGravity(Gravity.CENTER);
			            LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(k);
			            label.setWidth(l.getWidth());
			        }
			        
			         // Add the TableRow to the TableLayout
			        floating_heading_table.addView(tr, new TableLayout.LayoutParams(
			                LayoutParams.FILL_PARENT,
			                LayoutParams.MATCH_PARENT));
					
					//ledgertable.removeViewAt(0);
					projectStatementTable.getChildAt(0).setVisibility(View.INVISIBLE);
					
					View firstrow = projectStatementTable.getChildAt(0);
					for(int k=0;k<ColumnNameList.length;k++){
						LinearLayout l = (LinearLayout)((ViewGroup) firstrow).getChildAt(k);
						TextView tv = (TextView) l.getChildAt(0);
			            tv.setHeight(0);
			            
			            l.getLayoutParams().height = 0;
					}
					
					//ledgertable.getChildAt(0).setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 0));
				
				}
				oneTouch ++;
				
				return false;
			}
		});
	}

    
	private void animated_dialog() {
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

	
	private void addTable() {
        addHeader();
        /** Create a TableRow dynamically **/
        for(int i=0;i<projectStatementGrid.size();i++){
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(projectStatementGrid.get(i));
            tr = new TableRow(this);
           
            for(int j=0;j<columnValue.size();j++){
                /** Creating a TextView to add to the row **/
                addRow(columnValue.get(j));   
                label.setBackgroundColor(Color.BLACK);
                /*
                 * set right aligned gravity for amount and for others set center gravity
                 */
                if(j==3 || j==4){
                    label.setGravity(Gravity.RIGHT);
                    //For adding rupee symbol
                    if(columnValue.get(j).length() > 0){
                    	colValue=columnValue.get(j);
                    	if(!"".equals(colValue)){
                    		//System.out.println("m in ");
                    		if(!"0.00".equals(colValue)){
                    			// for checking multiple \n and pattern matching
                    			Pattern pattern = Pattern.compile("\\n");
                    			Matcher matcher = pattern.matcher(colValue);
                    			boolean found = matcher.find();
                    			//System.out.println("value:"+found);
                    			if(found==false){
                    				double amount = Double.parseDouble(colValue);	
                    				label.setText(formatter.format(amount));
                    			}else {
                    				label.setText(colValue);
                    			}
                   
                    		}else {
                    			label.setText(colValue);
                    		}
                    	}
                    }
                }
                else{
                    label.setGravity(Gravity.CENTER);
                }
            }
            // Add the TableRow to the TableLayout
            projectStatementTable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        
        /*
         * display the difference between total dr and total cr
         */
        ArrayList<String> lastrow=projectStatementGrid.get(projectStatementGrid.size()-1);
        result=Float.parseFloat(lastrow.get(4))-Float.parseFloat(lastrow.get(3));
        TextView difference = (TextView) findViewById(R.id.tvdifference);
        
        final SpannableString rsSymbol = new SpannableString(projectStatement.this.getText(R.string.Rs));
        difference.setText("Difference in Opening Balances : "+rsSymbol+" "+(String.format("%.2f", Math.abs(result))));
    }

	
    /*
     * add column heads to the table
     */
    void addHeader(){
    	//For adding rupee symbol
        final SpannableString rsSymbol = new SpannableString(projectStatement.this.getText(R.string.Rs)); 
        /** Create a TableRow dynamically **/
        String[] ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Total debit",rsSymbol+" Total credit"};
       
        tr = new TableRow(this);
       
        for(int k=0;k<ColumnNameList.length;k++){
            /** Creating a TextView to add to the row **/
            addRow(ColumnNameList[k]);
            label.setBackgroundColor(Color.parseColor("#348017"));
            label.setGravity(Gravity.CENTER);
        }
       
        // Add the TableRow to the TableLayout
        projectStatementTable.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.MATCH_PARENT));
    }
    
    
    /*
     * this function add the value to the row
     */
    void addRow(String param){
        label = new TextView(this);
        label.setText(param);
        label.setTextSize(18);
        label.setTextColor(Color.WHITE);
        label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        label.setPadding(2, 2, 2, 2);
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);
        Ll.addView(label,params);
        tr.addView((View)Ll); // Adding textView to tablerow.
       
    }

}
