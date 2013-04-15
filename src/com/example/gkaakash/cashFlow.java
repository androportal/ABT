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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
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


public class cashFlow extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] cashFlowResult;
    TableLayout cashFlowtable1, cashFlowtable2;
    TableRow tr;
    TextView label;
    ArrayList<String> cashFlowResultList;
    ArrayList<ArrayList> cashFlowGrid;
    ArrayList<ArrayList> cashFlow1,cashFlow2;
    Date date;
    String[] ColumnNameList;
    String getSelectedOrgType,financialFromDate,financialToDate,fromDateString,toDateString;
    TextView Netdifference ;
    Boolean updown=false;
    Boolean alertdialog = false;
    ObjectAnimator animation2;
    boolean reportmenuflag;
    int oneTouch = 1;
    TableLayout floating_heading_table1, floating_heading_table2;
    LinearLayout Ll;
    ScrollView sv;
    String 	OrgPeriod,balancePeriod,sFilename,OrgName,result;
	String[] pdf_params; 
	static String acc_name; 
	private int group1Id = 1;
	int PDF = Menu.FIRST;
	int CSV = Menu.FIRST + 1;
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
			m.generate_pdf1(cashFlow.this, pdf_params, sFilename, cashFlow1,
					cashFlow2);
			return true;

		case 2:
			m.csv_writer1(cashFlow1, cashFlow2,sFilename);
			m.toastValidationMessage(cashFlow.this, "CSV exported");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	    
	
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.cash_flow_table);
        report = new Report();
        client_id= Startup.getClient_id();
        m=new module();
        //customizing title bar
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
   
        try {
        	floating_heading_table1 = (TableLayout)findViewById(R.id.mainheadertable1);
        	floating_heading_table2 = (TableLayout)findViewById(R.id.mainheadertable2);
	   
        	floating_heading_table1.setVisibility(TableLayout.GONE);
        	floating_heading_table2.setVisibility(TableLayout.GONE);
        	sv = (ScrollView)findViewById(R.id.ScrollCashFlow);
   	
        	/*
        	 * display the difference between total dr and total cr
        	 */
        	Netdifference = (TextView) findViewById(R.id.tvdifference);
    
        	/*
        	 * get reference of all table IDs
        	 */
        	cashFlowtable1 = (TableLayout)findViewById(R.id.maintable1);
        	cashFlowtable2 = (TableLayout)findViewById(R.id.maintable2);
   	
        	/*
        	 * get financial from and to date from startup page
        	 */
        	
        	financialFromDate =Startup.getfinancialFromDate();
        	financialToDate=Startup.getFinancialToDate();
        	fromDateString = reportMenu.givenfromDateString;
        	toDateString = reportMenu.givenToDateString;
   
        	/*
        	 * set financial from date and to date in textview
        	 */
        	
        	TextView tvfinancialFromDate = (TextView) findViewById( R.id.tvTfinancialFromDate );
        	TextView tvfinancialToDate = (TextView) findViewById( R.id.tvTfinancialToDate );
     
        	//tvfinancialFromDate.setText();
        	tvfinancialToDate.setText("Period : " +fromDateString+" to " +toDateString);
        	/*
        	 * send params to controller report.getCashFlow to get the result
        	 */
 
        	Object[] params = new Object[]{financialFromDate,fromDateString,toDateString};
   
        	cashFlowResult = (Object[]) report.getCashFlow(params,client_id);
   
        	//cashFlowResult is 3 dimensional list 
        	int count = 0;
        	for(Object cf : cashFlowResult){
       	
        		Object[] c = (Object[]) cf;
        		count = count + 1;
        		cashFlowGrid = new ArrayList<ArrayList>();
        		if(count !=3)   
        		{
        			for(Object cf1 : c){
           	
        				Object[] c1 = (Object[]) cf1;
        				cashFlowResultList = new ArrayList<String>();
           	
        				for(int j=0;j<c1.length;j++){
           	
        					cashFlowResultList.add((String) c1[j].toString());
        				}
           	
        				cashFlowGrid.add(cashFlowResultList);
        			}
        		}
        		if(count == 1){
        			addTable(cashFlowtable1);
        			cashFlow1 = cashFlowGrid;
        		}
        		if(count == 2){
        			addTable(cashFlowtable2);
        			cashFlow2 = cashFlowGrid;
        		}
        		if(count == 3)
        		{
        			final SpannableString rsSymbol = new SpannableString(cashFlow.this.getText(R.string.Rs)); 
        			result = "Net Flow: "+rsSymbol+" "+c[0].toString();
        			Netdifference.setText(result);
           
        		}
        	}
        	 if(reportmenuflag==true){ 
  	    		OrgName = createOrg.organisationName;
             }
             else {
          	    OrgName= selectOrg.selectedOrgName;
             }
        	final TextView tvReportTitle = (TextView)findViewById(R.id.tvReportTitle);
            tvReportTitle.setText("Menu >> "+"Report >> "+"Cash Flow");
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
            
            date= new Date();
			String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
			OrgPeriod = "Financial Year:\n "+financialFromDate+" to "+financialToDate;
			balancePeriod = fromDateString+" to "+toDateString;
			sFilename = "CashFlow"+"_"+date_format;
			pdf_params = new String[]{"cash",sFilename,OrgName,OrgPeriod,"Cash Flow",balancePeriod,"",result};
			
            animated_dialog();
            //floatingHeader();
		} catch (Exception e) {
			m.toastValidationMessage(cashFlow.this, "Please try again");
		}
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


    private void addTable(TableLayout tableID) {
        /** Create a TableRow dynamically **/
        for(int i=0;i<cashFlowGrid.size();i++){
       
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(cashFlowGrid.get(i));
            //create new row
            tr = new TableRow(this);
            if(columnValue.get(0).equalsIgnoreCase("Account Name")){
               	//for heading pass green color code
            	// System.out.println("iam in chaninging color "+columnValue.get(1));
               	setRowColorSymbolGravity(columnValue, Color.parseColor("#348017"), true);
            }
            else{
            	//for remaining rows pass black color code
               	setRowColorSymbolGravity(columnValue, Color.BLACK, false);
            }
            // Add the TableRow to the TableLayout
            tableID.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }  
    
    /*
     * 1. set the green background colour for heading and black for remaining rows
     * 2. set right aligned gravity for amount and centre aligned for other values
     * 3. set rupee symbol for amount
     */
    private void setRowColorSymbolGravity(ArrayList<String> columnValue, int color, Boolean headerFlag) {
    	for(int j=0;j<columnValue.size();j++){
            /** Creating a TextView to add to the row **/
    		if(headerFlag == true){
    			if(j == 1){ //amount column
    				//For adding rupee symbol
    				final SpannableString rsSymbol = new SpannableString(cashFlow.this.getText(R.string.Rs));
    				addRow(rsSymbol+" "+columnValue.get(j),j);   
    			}else{
    				addRow(columnValue.get(j),j);   
    			}
    		}
    		else{
    			addRow(columnValue.get(j),j);   
    		}
    		label.setBackgroundColor(color);
            String amount = columnValue.get(1).toString();
            String name = columnValue.get(0).toString();
            if(j==1){//for amount coloumn
            	if(!amount.equalsIgnoreCase("Amount(Inflow)")&&!amount.equalsIgnoreCase("Amount(Outflow)"))
            	{
            		label.setGravity(Gravity.RIGHT);
               
            		if(columnValue.get(j).length() > 0){
            			DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
            			String colValue;
            			((TextView)label).setGravity(Gravity.RIGHT);
            			//For adding rupee symbol
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
            	}else 
            	{
            		label.setGravity(Gravity.CENTER);
              
            	}
            }
            else{
            	if(!name.equalsIgnoreCase("Total"))
            	{
            		label.setGravity(Gravity.CENTER);
           
            	}else{
             
            		label.setGravity(Gravity.RIGHT);
            	}
            }
        }
    }
 
    /*
     * this function add the value to the row
     */
	void addRow(String param, final int i) {
		drillDown();
		label = new TextView(this);
		label.setText(param);
		label.setTextSize(18);
		label.setTextColor(Color.WHITE);
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label.setPadding(2, 2, 2, 2);
		LinearLayout Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(1, 1, 1, 1);
		Ll.addView(label, params);
		tr.addView((View) Ll); // Adding textView to tablerow.
	}
    
	/*
     * this method is used to add the drill down functionality on clicking the table row.
     * basically we want account name value from selected row.
     * In cash flow report we have two tables, so get the value of account name textview from selected table.
     */
	private void drillDown() {
		int count = cashFlowtable1.getChildCount();
		for (int i = 0; i < count; i++) {
			final View row = cashFlowtable1.getChildAt(i);
			
			row.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					LinearLayout l = (LinearLayout) ((ViewGroup) row)
							.getChildAt(0);
					final TextView tv = (TextView) l.getChildAt(0);
					checkForAccountName(tv.getText().toString(), row);
				}
			});
		}

		int count1 = cashFlowtable2.getChildCount();
		for (int i = 0; i < count1; i++) {
			final View row = cashFlowtable2.getChildAt(i);

			row.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					LinearLayout l = (LinearLayout) ((ViewGroup) row)
							.getChildAt(0);
					final TextView tv = (TextView) l.getChildAt(0);
					checkForAccountName(tv.getText().toString(), row);
					}
			});
		}

	}
	
	/*
     * below method helps to find out the selected string is account name or not.
     * check for empty string, string "Total" and header column "account name".
     */
	private void checkForAccountName(String accname, View row) {
    	if(!(accname.equalsIgnoreCase("") || 
    							accname.equalsIgnoreCase("Total") ||
    							accname.equalsIgnoreCase("Account Name"))){
    		//Toast.makeText(balanceSheet.this, "account name", Toast.LENGTH_SHORT).show();
    		//change the row color(black/gray to orange) when clicked
    		
			for (int j = 0; j < 2; j++) {
				LinearLayout l = (LinearLayout) ((ViewGroup) row)
						.getChildAt(j);
				TextView t = (TextView) l.getChildAt(0);
				t.setBackgroundColor(Color.parseColor("#FBB117"));
			}
    		acc_name = accname;
			Intent intent = new Intent(getApplicationContext(),
					ledger.class);
			intent.putExtra("flag", "from_cashflow");
			startActivity(intent);
    	}
		
	}
    
    
}