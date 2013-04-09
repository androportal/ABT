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
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
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

public class trialBalance extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] trialBalanceResult;
    TableLayout trialBaltable;
    TableRow tr;
    TextView label;
    ArrayList<ArrayList> trialBalGrid,trialBalGrid_with_header;
    ArrayList<String> trialBalanceResultList;
    String trialbalancetype;
    String[] ColumnNameList;
    String trialToDateString ;
    Boolean updown=false;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
   	String colValue;
   	Boolean alertdialog = false;
    ObjectAnimator animation2;
    Float result;
    boolean reportmenuflag;
    int oneTouch = 1;
    TableLayout floating_heading_table;
    LinearLayout Ll;
    ScrollView sv;
    String financialFromDate,trialbalType;
	String financialToDate,OrgName,date_format,OrgPeriod ,TrialPeriod,sFilename;
    String[] pdf_params;
    static String acc_name;
    ArrayList<String> stringList;
    private int group1Id = 1;
   	int PDF = Menu.FIRST;
   	int CSV=Menu.FIRST+1;
   	module m;
   	
   	
   	@Override
   	public boolean onCreateOptionsMenu(Menu menu) {

   		menu.add(group1Id, PDF, PDF, "Export as PDF");
   		menu.add(group1Id, CSV, CSV, "Export as CSV");
   		return super.onCreateOptionsMenu(menu);
   	}

   	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		switch (item.getItemId()) {
   		case 1:			
   			m.generate_pdf(trialBalance.this, pdf_params, sFilename, trialBalGrid);
   			return true;

   		case 2:
   			m.csv_writer(trialBalGrid_with_header,sFilename);
   			m.toastValidationMessage(trialBalance.this, "CSV exported");
   			return true;
   		}
   		return super.onOptionsItemSelected(item);
   	}
       
    
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	    setContentView(R.layout.trial_table);
	    report = new Report();
	    client_id= Startup.getClient_id();
		 m=new module();
	    reportmenuflag = MainActivity.reportmenuflag;
	    //customizing title bar
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
	   
	    try {
	    	floating_heading_table = (TableLayout)findViewById(R.id.floating_heading_table);
	    	floating_heading_table.setVisibility(TableLayout.GONE);
	    	sv = (ScrollView)findViewById(R.id.ScrollTrial);
			if (reportmenuflag == true) {

				OrgName = createOrg.organisationName;

			} else {
				OrgName = selectOrg.selectedOrgName;

			}
		   
	    	/*
	    	 * get financial from and to date from startup page
	    	 */
	    	financialFromDate =Startup.getfinancialFromDate();
	    	financialToDate=Startup.getFinancialToDate();
	    	trialToDateString = reportMenu.givenToDateString;
	    	// String trialFromoDateString = reportMenu.givenfromDateString;
	    	trialbalancetype=reportMenu.trialbalancetype;
	    	/*
		    * set financial from date and to date in textview
		    */
	    	TextView tvfinancialFromDate = (TextView) findViewById( R.id.tvTfinancialFromDate );
	    	TextView tvfinancialToDate = (TextView) findViewById( R.id.tvTfinancialToDate );
		   
	    	//tvfinancialFromDate.setText("Financial from : " +financialFromDate);
	    	//tvfinancialToDate.setText("Financial to : " +trialToDateString);
	    	tvfinancialToDate.setText("Period : "+financialFromDate+" to "+trialToDateString);   
	    	/*
		    * send params to controller report.getTrialBalance to get the result
		    */
		 
	    	Object[] params = new Object[]{financialFromDate,financialFromDate,trialToDateString};
	    	//System.out.println("Trial Balance Type: "+trialbalancetype);
	    	if("Net Trial Balance".equals(trialbalancetype)){
	    		trialBalanceResult = (Object[]) report.getTrialBalance(params,client_id);
	    		trialbalType = "NetT";
	    	}else if ("Gross Trial Balance".equals(trialbalancetype)) {
	    		trialBalanceResult = (Object[]) report.getGrossTrialBalance(params,client_id);
	    		trialbalType = "GrossT";
	    	}else if ("Extended Trial Balance".equals(trialbalancetype)) {
	    		trialBalanceResult = (Object[]) report.getExtendedTrialBalance(params,client_id);
	    		trialbalType = "ExtendedT";
	    	}
	    	 Date date= new Date();
	   		 date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
	         OrgPeriod = "Financial Year:\n "+financialFromDate+" to "+financialToDate;
	         TrialPeriod = financialFromDate+" to "+trialToDateString;
	         trialBalGrid = new ArrayList<ArrayList>();
	         trialBalGrid_with_header = new ArrayList<ArrayList>();
	    	for(Object tb : trialBalanceResult)
	    	{
	    		Object[] t = (Object[]) tb;
	    		trialBalanceResultList = new ArrayList<String>();
	    		for(int i=0;i<t.length;i++){
	           	
	    			trialBalanceResultList.add((String) t[i].toString());
	              
	    		}
	    		trialBalGrid.add(trialBalanceResultList);
	    	} 
	    	System.out.println("grid1:"+trialBalGrid);
	    	trialBaltable = (TableLayout)findViewById(R.id.maintable);
	    	addTable();
	        
	    	final TextView tvReportTitle = (TextView)findViewById(R.id.tvReportTitle);
	    	tvReportTitle.setText("Menu >> "+"Report >> "+trialbalancetype);
	    	final Button btnSaveRecon = (Button)findViewById(R.id.btnSaveRecon);
	    	btnSaveRecon.setVisibility(Button.GONE);
	    	 
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
	    	sFilename = trialbalType+"_"+date_format;
			pdf_params = new String[]{trialbalType,sFilename,OrgName,OrgPeriod,trialbalancetype,TrialPeriod,"",String.format("%.2f", Math.abs(result))};
					
	    	
	    	
	       animated_dialog();
	       floatingHeader();
	    } catch (Exception e) {
		   	//System.out.println("m in exte err"+e);
		   	AlertDialog.Builder builder = new AlertDialog.Builder(trialBalance.this);
		   	
		   	builder.setMessage("Please try again"+e.getLocalizedMessage())
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
   
    /*
     * this method adds the floating header to the table on touching it.
     * In this case, we have a main table which includes table rows and a header at the load time.
     * and another table(for floating header) is invisible at load time which is located at the top of main table.
     * on the very first touch of the main table, we will add floating header columns and
     * make it visible.
     * at the same time we will set width 0 for the main table header to avoid
     * double headers at the same time.
     */
    private void floatingHeader() {
    		trialBaltable.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(oneTouch == 1){
					floating_heading_table.setVisibility(TableLayout.VISIBLE);
					
					//System.out.println("we are in if");
					
					int rowcount = trialBaltable.getChildCount();    
	                View row = trialBaltable.getChildAt(rowcount-1);
					
	                //For adding rupee symbol
	                final SpannableString rsSymbol = new SpannableString(trialBalance.this.getText(R.string.Rs)); 
	               
	                    /** Create a TableRow dynamically **/
	                if ("Net Trial Balance".equals(trialbalancetype)){
	                	ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Debit",rsSymbol+" Credit"};	
	                }else if ("Gross Trial Balance".equals(trialbalancetype)) {
	                	ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Total debit",rsSymbol+" Total credit"};	
	                }else if ("Extended Trial Balance".equals(trialbalancetype)) {
	                	ColumnNameList = new String[] { "Sr. no.","Account name"," Group name ",rsSymbol+" Opening  Balance ",rsSymbol+" Total  debit  transaction ",rsSymbol+" Total  credit  transaction ",rsSymbol+" Debit  balance ",rsSymbol+" Credit  balance "};	
	                }  
	                   
                    tr = new TableRow(trialBalance.this);
                   
                    for(int k=0;k<ColumnNameList.length;k++){
                        /** Creating a TextView to add to the row **/
                        addRow(ColumnNameList[k],k);
                        label.setBackgroundColor(Color.parseColor("#348017"));
                        label.setGravity(Gravity.CENTER);
                        LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(k);
			            label.setWidth(l.getWidth());
			            tr.setClickable(false);
                    }
                     
                     // Add the TableRow to the TableLayout
                    floating_heading_table.addView(tr, new TableLayout.LayoutParams(
                            LayoutParams.FILL_PARENT,
                            LayoutParams.WRAP_CONTENT));
					  
					//ledgertable.removeViewAt(0);
					trialBaltable.getChildAt(0).setVisibility(View.INVISIBLE);
					
					View firstrow = trialBaltable.getChildAt(0);
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
        for(int i=0;i<trialBalGrid.size();i++){
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(trialBalGrid.get(i));
            tr = new TableRow(this);
           
            for(int j=0;j<columnValue.size();j++){
                /** Creating a TextView to add to the row **/
                addRow(columnValue.get(j),i);   
                label.setBackgroundColor(Color.BLACK);
                /*
                 * set right aligned gravity for amount and for others set center gravity
                 */
                if(!"Extended Trial Balance".equals(trialbalancetype)){
                	if(j==3 || j==4){
                		label.setGravity(Gravity.RIGHT);
                        
                        if(columnValue.get(j).length() > 0){
                           	colValue=columnValue.get(j);
	                        if(!"".equals(colValue)){
		                      //  System.out.println("m in ");
		                        if(!"0.00".equals(colValue)){
		                        	// for checking multiple \n and pattern matching
			                        Pattern pattern = Pattern.compile("\\n");
			                        Matcher matcher = pattern.matcher(colValue);
			                        boolean found = matcher.find();
			                       // System.out.println("value:"+found);
			                        if(found==false){
				                        double amount = Double.parseDouble(colValue);	
				                     //   System.out.println("A:"+amount);
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
                }else {
                if(j==3 || j==4 || j==5 || j==6 || j==7){
                        label.setGravity(Gravity.RIGHT);
                        //For adding rupee symbol
                        if(columnValue.get(j).length() > 0){
	                        colValue=columnValue.get(j);
	                        if(!"".equals(colValue)){
		                        if(!"0.00".equals(colValue)){
			                        // for checking multiple \n and pattern matching
			                        Pattern pattern = Pattern.compile("\\n");
			                        Matcher matcher = pattern.matcher(colValue);
			                        boolean found = matcher.find();
			                      //  System.out.println("value:"+found);
			                        if(j==3){
			                            if(found==false){
				                            String colValue1 = colValue.substring(0, colValue.length()-4);
				                            String last_four_Char=colValue.substring(colValue.length() - 4); 
				                           // System.out.println("lst:"+last_four_Char);
	                                        //System.out.println("after cuting:"+colValue1);
	                                        double amount = Double.parseDouble(colValue1);
	                                        label.setText(formatter.format(amount)+last_four_Char);
			                            }else {
			                            	label.setText(colValue);
			                            }
			                       
			                        }else {
				                        if(found==false){
				                        	double amount = Double.parseDouble(colValue);
				                            label.setText(formatter.format(amount));
				                        }else {
				                            label.setText(colValue);
				                            	}
			                        }
		                         
		                        }
	                       
	                       
	                        }
	                       
                        }
                    }
                    else{
                        label.setGravity(Gravity.CENTER);
                    }	
                }
                
            }
            // Add the TableRow to the TableLayout
            trialBaltable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
        
        /*
         * display the difference between total dr and total cr
         */
        TextView difference = (TextView) findViewById(R.id.tvdifference);
        final SpannableString rsSymbol = new SpannableString(trialBalance.this.getText(R.string.Rs));
        ArrayList<String> lastrow=trialBalGrid.get(trialBalGrid.size()-1);
        if(!"Extended Trial Balance".equals(trialbalancetype)){
        	result=Float.parseFloat(lastrow.get(4))-Float.parseFloat(lastrow.get(3));
            difference.setText("Difference in Opening Balances: "+rsSymbol+" "+(String.format("%.2f", Math.abs(result))));
        }else {
        	result=Float.parseFloat(lastrow.get(7))-Float.parseFloat(lastrow.get(6));
        	difference.setText("Difference in Opening Balances: "+rsSymbol+" "+(String.format("%.2f", Math.abs(result))));
        }
    }

    /*
     * add column heads to the table
     */
    void addHeader(){
	    //For adding rupee symbol
	    final SpannableString rsSymbol = new SpannableString(trialBalance.this.getText(R.string.Rs)); 
	   
	        /** Create a TableRow dynamically **/
	    if ("Net Trial Balance".equals(trialbalancetype)){
	    	ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Debit",rsSymbol+" Credit"};	
	    }else if ("Gross Trial Balance".equals(trialbalancetype)) {
	    	ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Total debit",rsSymbol+" Total credit"};	
	    }else if ("Extended Trial Balance".equals(trialbalancetype)) {
	    	ColumnNameList = new String[] { "Sr. no.","Account name"," Group name ",rsSymbol+" Opening  Balance ",rsSymbol+" Total  debit  transaction ",rsSymbol+" Total  credit  transaction ",rsSymbol+" Debit  balance ",rsSymbol+" Credit  balance "};	
	    }
	       
        tr = new TableRow(this);
       
        for(int k=0;k<ColumnNameList.length;k++){
            /** Creating a TextView to add to the row **/
            addRow(ColumnNameList[k],0);
            label.setBackgroundColor(Color.parseColor("#348017"));
            label.setGravity(Gravity.CENTER);
            tr.setClickable(false);
        }
       
        
		stringList = new ArrayList<String>();
		for (String s : ColumnNameList) {
			stringList.add(s);
		}
		trialBalGrid_with_header.add(stringList);
		trialBalGrid_with_header.addAll(trialBalGrid);
		
         // Add the TableRow to the TableLayout
        trialBaltable.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }
    /*
     * this function add the value to the row
     */
	void addRow(String param, final int i) {
		tr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String accname = trialBalGrid.get(i).get(1).toString();
				//System.out.println("name is"+accname);
				if(!(accname.equalsIgnoreCase("") || accname.equalsIgnoreCase("Account Name"))){
					acc_name = accname;
					Intent intent = new Intent(getApplicationContext(),ledger.class);
					intent.putExtra("flag", "from_trialBal");
					startActivity(intent); 
				}
			}
    	});
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