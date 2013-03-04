package com.example.gkaakash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.animation.ObjectAnimator;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ledger extends Activity{
    TableLayout ledgertable;
    TableRow tr;
    TextView label,tvaccontName,tvfinancialFromDate,tvfinancialToDate,infoTxtCredits;
    static ArrayList<ArrayList> ledgerGrid;
    static Object[] ledgerResult;
    static Integer client_id;
    private Report report;
    ArrayList<String> ledgerResultList;
    private ArrayList accountlist;
	String[] pdf_params;
    Boolean updown=false;
    boolean checked;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
    String colValue,financialFromDate,financialToDate;
    Boolean alertdialog = false;
    ObjectAnimator animation2;
    boolean reportmenuflag;
    int oneTouch = 1;
    String OrgName;
    TableLayout floating_heading_table;
    LinearLayout Ll;
    ScrollView sv;
    Object[] ledger_params;
    static String[] ColumnNameList;
    String accountName,projectName,fromDate,toDate;
    Font smallBold,smallNormal,bigBold;
    String OrgPeriod,LedgerPeriod ;
    String Ledger_project,sFilename;
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       setContentView(R.layout.ledger_table);
      
       report = new Report();
       client_id= Startup.getClient_id();
       reportmenuflag = MainActivity.reportmenuflag;
       //customizing title bar
       getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
       smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10);
	   smallBold.setStyle(Font.UNDERLINE);
	   smallBold.setStyle(Font.BOLD);
	   bigBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
	   smallNormal= new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.NORMAL);    
       try {
    	   floating_heading_table = (TableLayout)findViewById(R.id.floating_heading_table);
    	   floating_heading_table.setVisibility(TableLayout.GONE);
    	   sv = (ScrollView)findViewById(R.id.ScrollLedger);
    	   
    	   financialFromDate =Startup.getfinancialFromDate();
           financialToDate=Startup.getFinancialToDate();
           accountName = reportMenu.selectedAccount;
           projectName = reportMenu.selectedProject;
           checked = reportMenu.cheched;
           fromDate = reportMenu.givenfromDateString;
           toDate = reportMenu.givenToDateString;
       
           tvaccontName = (TextView) findViewById( R.id.tvaccountName );
           tvfinancialToDate = (TextView) findViewById( R.id.tvfinancialToDate );
           tvaccontName.setText("Account name: "+accountName);
           tvfinancialToDate.setText("Period : "+fromDate+" to "+toDate);   
          
           if(!projectName.equalsIgnoreCase("No Project")){
		   		TextView tvProjectName = (TextView) findViewById( R.id.tvProjectName );
		   		tvProjectName.setText("Project name: " +projectName);
		   		Ledger_project = "for the "+projectName+" project";
           }
           else
           {
        	   Ledger_project = "No Project";
           }
           //System.out.println("ledger with project"+accountName+financialFromDate+fromDate+toDate+projectName);
           ledger_params = new Object[]{accountName,financialFromDate,fromDate,toDate,projectName};
           ledgerResult = (Object[]) report.getLedger(ledger_params,client_id);
          
           if(reportmenuflag==true){
   	    	
	    		OrgName = createOrg.organisationName;
	    		
           }
           else {
        	    OrgName= selectOrg.selectedOrgName;
         
           }
           Date date= new Date();
   		   String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
           OrgPeriod = "Financial Year:\n "+financialFromDate+" to "+financialToDate;
           LedgerPeriod = fromDate+" to "+toDate;
           String account = accountName.replace(" ","");
   	 	   sFilename = "L"+"_"+account+"_"+date_format;
           pdf_params = new String[]{"L",sFilename,OrgName,OrgPeriod,"Ledger for: "+accountName,LedgerPeriod,"Project: "+Ledger_project,};
           ledgerGrid = new ArrayList<ArrayList>();
           for(Object tb : ledgerResult) 
           {
               
        	   Object[] t = (Object[]) tb;
        	   ledgerResultList = new ArrayList<String>();
        	   for(int i=0;i<(t.length-1);i++) 
        	   {
        		   if(i == 5){ //****************
        			   if(checked == true){
        				   ledgerResultList.add((String) t[i].toString());
        			   }
        			    
        		   }else{
        			   ledgerResultList.add((String) t[i].toString());
        		   }
        	   }
                
        	   ledgerGrid.add(ledgerResultList);
           }  
           
           ledgertable = (TableLayout)findViewById(R.id.maintable);
           addTable();
            
           final TextView tvReportTitle = (TextView)findViewById(R.id.tvReportTitle);
           tvReportTitle.setText("Menu >> "+"Report >> "+"Ledger");
           final Button btnSaveRecon = (Button)findViewById(R.id.btnSaveRecon);
           btnSaveRecon.setVisibility(Button.GONE);
           final Button btnPdf = (Button)findViewById(R.id.btnPdf);
          // btnSaveRecon.setVisibility(Button.GONE);
           final Button btnScrollDown = (Button)findViewById(R.id.btnScrollDown);
           btnScrollDown.setOnClickListener(new OnClickListener() {
 	
        	   @Override
        	   public void onClick(View v) {
        		   if(updown==false){
        			   
        			   sv.fullScroll(ScrollView.FOCUS_DOWN); 
        			   btnScrollDown.setBackgroundResource(R.drawable.up);
        			   updown=true;
        		   }else {
        			   sv.fullScroll(ScrollView.FOCUS_UP); 
        			   btnScrollDown.setBackgroundResource(R.drawable.down);
        			   updown=false;
        		   }
        	   }
           });
         
         btnPdf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(ledger.this);
				   builder.setMessage("Do you want to create PDF")
				           .setCancelable(false)
				           .setPositiveButton("Yes",
				                   new DialogInterface.OnClickListener() {
				                       public void onClick(DialogInterface dialog, int id) {
				                    	   	PdfGenaretor pdfgen = new PdfGenaretor();
				       						try {
												pdfgen.generatePDFFile(ledgerGrid,pdf_params);
												AlertDialog.Builder builder1 = new AlertDialog.Builder(ledger.this);
					       						builder1.setMessage("PDF genration completed ..see /mnt/sdcard/"+sFilename);
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
    	   AlertDialog.Builder builder = new AlertDialog.Builder(ledger.this);
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
   
    
    private void floatingHeader() {
    	ledgertable.setOnTouchListener(new OnTouchListener() {
			
    		@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(oneTouch == 1){
					floating_heading_table.setVisibility(TableLayout.VISIBLE);
					
					//System.out.println("we are in if");
					
					int rowcount = ledgertable.getChildCount();    
	                View row = ledgertable.getChildAt(rowcount-1);
					
					final SpannableString rsSymbol = new SpannableString(ledger.this.getText(R.string.Rs));
			        /** Create a TableRow dynamically **/
			        String[] ColumnNameList = new String[] {"Date","Particulars","Reference no.",rsSymbol+" Debit",rsSymbol+" Credit","Narration"};
			       
			        tr = new TableRow(ledger.this);
			        
			        int len;
			        if(checked == true){
			        	len = ColumnNameList.length;
			        }
			        else{
			        	len = ColumnNameList.length-1;
			        }
			        
			        for(int k=0;k<len;k++){
			            /** Creating a TextView to add to the row **/
			            addRow(ColumnNameList[k]);
			            label.setBackgroundColor(Color.parseColor("#348017"));
			            label.setGravity(Gravity.CENTER);
			            
			            LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(k);
			            label.setWidth(l.getWidth());
		                //System.out.println("size is"+l.getWidth());
			        }
			        
			        // Add the TableRow to the TableLayout
			        floating_heading_table.addView(tr, new TableLayout.LayoutParams(
			                LayoutParams.FILL_PARENT,
			                LayoutParams.MATCH_PARENT));
					
					//ledgertable.removeViewAt(0);
					ledgertable.getChildAt(0).setVisibility(View.INVISIBLE);
					
					View firstrow = ledgertable.getChildAt(0);
					for(int k=0;k<len;k++){
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
            
            Button btnOrgDetailsDialog = (Button) findViewById(R.id.btnOrgDetailsDialog);
            btnOrgDetailsDialog.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    
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
        //System.out.println("ledgerGrid."+ledgerGrid);
        
        /** Create a TableRow dynamically **/
        for(int i=0;i<ledgerGrid.size();i++){
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(ledgerGrid.get(i));
            tr = new TableRow(this);
            Integer lastIndex = ledgerGrid.size()-1;
            for(int j=0;j<columnValue.size();j++){
            	addRow(columnValue.get(j)); 
            	 if((i+1)%2==0)
             		label.setBackgroundColor(Color.parseColor("#474335"));
             	else
             		label.setBackgroundColor(Color.BLACK);
            	if (lastIndex.equals(i)) {
            		label.setBackgroundColor(Color.parseColor("#348017"));
				}
            		
            	if(j == 3 || j == 4){
            		label.setGravity(Gravity.RIGHT);
                    
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
            ledgertable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.MATCH_PARENT));
           
        } 
     
	
    }
	
	
    void addHeader(){
    	//For adding rupee symbol
    	final SpannableString rsSymbol = new SpannableString(ledger.this.getText(R.string.Rs));
        /** Create a TableRow dynamically **/
        ColumnNameList = new String[] {"Date","Particulars","Reference no.",rsSymbol+" Debit",rsSymbol+" Credit","Narration"};
       
        tr = new TableRow(this);
        
        int len;
        if(checked == true){
        	len = ColumnNameList.length;
        }
        else{
        	len = ColumnNameList.length-1;
        }
        
        for(int k=0;k<len;k++){
            /** Creating a TextView to add to the row **/
            addRow(ColumnNameList[k]);
            label.setBackgroundColor(Color.parseColor("#348017"));
            label.setGravity(Gravity.CENTER);
        }
       
        // Add the TableRow to the TableLayout
        ledgertable.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.MATCH_PARENT));
    }
   
    
    void addRow(String param){ 
        label = new TextView(this);
        label.setText(param);
        label.setTextColor(Color.WHITE);
        label.setTextSize(18);
        //label.setBackgroundColor(Color.);
        label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT));
        label.setBackgroundColor(Color.BLACK);
        label.setPadding(2, 2, 2, 2);
      
        Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(label,params);
        tr.addView((View)Ll);
        
    }

   
  
}