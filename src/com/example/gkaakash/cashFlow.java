package com.example.gkaakash;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
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

import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;

public class cashFlow extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] cashFlowResult;
    TableLayout cashFlowtable1, cashFlowtable2;
    TableRow tr;
    TextView label;
    ArrayList<String> cashFlowResultList;
    ArrayList<ArrayList<String>> cashFlowGrid;
    String[] ColumnNameList;
    String getSelectedOrgType;
    TextView Netdifference ;
    Boolean updown=false;
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.cash_flow_table);
        report = new Report();
	    client_id= Startup.getClient_id();
	    
	  //customizing title bar
	    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
	    
	    try {
	    	
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
		    String financialFromDate =Startup.getfinancialFromDate();
		    String financialToDate=Startup.getFinancialToDate();
		    String fromDateString = reportMenu.givenfromDateString;
		    String toDateString = reportMenu.givenToDateString;
		    
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
	            cashFlowGrid = new ArrayList<ArrayList<String>>();
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
	           // System.out.println("i am cash flow "+count+cashFlowGrid);
	           
	            if(count == 1){
	            	addTable(cashFlowtable1);
	            }
	            if(count == 2){
	            	addTable(cashFlowtable2);
	            }
	            if(count == 3)
	            {
	            	 final SpannableString rsSymbol = new SpannableString(cashFlow.this.getText(R.string.Rs)); 
	            	 Netdifference.setText("Net Flow: "+rsSymbol+" "+c[0].toString());
	            
	            }
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
                    ScrollView sv = (ScrollView)findViewById(R.id.ScrollCashFlow);
                    sv.fullScroll(ScrollView.FOCUS_DOWN); 
                    btnScrollDown.setBackgroundResource(R.drawable.up);
                    updown=true;
               }else {
                    ScrollView sv = (ScrollView)findViewById(R.id.ScrollCashFlow);
                    sv.fullScroll(ScrollView.FOCUS_UP); 
                    btnScrollDown.setBackgroundResource(R.drawable.down);
                    updown=false;
               }
 				}
            });
        
	    } catch (Exception e) {
	    	System.out.println("I am an error"+e);
	    	AlertDialog.Builder builder = new AlertDialog.Builder(cashFlow.this);
	    	
	           builder.setMessage("Please try again")
	                   .setCancelable(false)
	                   .setPositiveButton("Ok",
	                           new DialogInterface.OnClickListener() {
	                               public void onClick(DialogInterface dialog, int id) {
	                               	
	                               }
	                           });
	                   
	           AlertDialog alert = builder.create();
	           alert.show();		}
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
     * 1. set the green background color for heading and black for remaining rows
     * 2. set right aligned gravity for amount and center aligned for other values
     * 3. set rupee symbol for amount
     */
    private void setRowColorSymbolGravity(ArrayList<String> columnValue, int color, Boolean headerFlag) {
    	for(int j=0;j<columnValue.size();j++){
            /** Creating a TextView to add to the row **/
    		if(headerFlag == true){
    			if(j == 1){ //amount column
    				//For adding rupee symbol
    		    	final SpannableString rsSymbol = new SpannableString(cashFlow.this.getText(R.string.Rs));
    				addRow(rsSymbol+" "+columnValue.get(j));   
    			}else{
    				addRow(columnValue.get(j));   
    			}
    			
    		}
    		else{
    			addRow(columnValue.get(j));   
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
		            		System.out.println("m in ");
                    		if(!"0.00".equals(colValue)){
                    			Pattern pattern = Pattern.compile("\\n");
                    			Matcher matcher = pattern.matcher(colValue);
                    			boolean found = matcher.find();
                    			System.out.println("value:"+found);
                    			if(found==false){
                    				double amount1 = Double.parseDouble(colValue);	
                    				System.out.println("A:"+amount1);
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
    void addRow(String param){
        label = new TextView(this);
        label.setText(param);
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
