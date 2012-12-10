package com.example.gkaakash;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.income_expenditure_table);
        report = new Report();
	    client_id= Startup.getClient_id();
	    
	    try {
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
		      
		    tvfinancialFromDate.setText("Financial from : " +financialFromDate);
		    tvfinancialToDate.setText("Financial to : " +financialToDate);
		    /*
		     * send params to controller report.getCashFlow to get the result
		     */
		  
		    Object[] params = new Object[]{financialFromDate,fromDateString,toDateString};
		    
		    cashFlowResult = (Object[]) report.getCashFlow(params,client_id);
		    
		  //cashFlowResult is 3 dimensional list 
	        int count = 0;
	        for(Object tb : cashFlowResult){
	        	
	            Object[] t = (Object[]) tb;
	            count = count + 1;
	            cashFlowGrid = new ArrayList<ArrayList<String>>();
	            
	            for(Object tb1 : t){
	            	
	            	Object[] t1 = (Object[]) tb1;
	            	cashFlowResultList = new ArrayList<String>();
	            	
	            	for(int j=0;j<t1.length;j++){
	            		
	            		cashFlowResultList.add((String) t1[j].toString());
	            	}
	            	
	            	cashFlowGrid.add(cashFlowResultList);
	            }
	           
	            if(count == 1){
	            	addTable(cashFlowtable1);
	            }
	            else if(count == 2){
	            	addTable(cashFlowtable2);
	            }
	        }
		   
        
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
            
        	//for remaining rows pass black color code
        	setRowColorSymbolGravity(columnValue, Color.BLACK);
            
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
            addRow(columnValue.get(j));   
            label.setBackgroundColor(color);
            
        	if(j==2){//for amount coloumn
        		
    			label.setGravity(Gravity.RIGHT);
                //For adding rupee symbol
                if(columnValue.get(j).length() > 0){
                
                    final SpannableString rsSymbol = new SpannableString(cashFlow.this.getText(R.string.Rs)); 
                    label.setText(rsSymbol+" "+columnValue.get(j).toString());
                }
            }
            else{
                label.setGravity(Gravity.CENTER);
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