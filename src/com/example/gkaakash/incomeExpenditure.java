package com.example.gkaakash;

import java.util.ArrayList;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;

public class incomeExpenditure extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] IEResult;
    TableLayout IEtable1, IEtable2, IEtable3, IEtable4;
    TableRow tr;
    TextView label;
    ArrayList<String> IEResultList;
    ArrayList<ArrayList<String>> IEGrid;
    String[] ColumnNameList;
    String getSelectedOrgType;
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.income_expenditure_table);
        report = new Report();
	    client_id= Startup.getClient_id();
	    
	    //customizing title bar
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.report_title);
        final TextView label = (TextView) findViewById(R.id.tvReportTitle);
        String Reporttypeflag = reportMenu.reportTypeFlag;
        label.setText("Menu >> Transaction >> " + Reporttypeflag);
        
        //set on click listener for home button
        final Button home = (Button) findViewById(R.id.btnhome);
        home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(incomeExpenditure.this, menu.class);
				// To pass on the value to the next page
				startActivity(intent);
				
			}
        	
        });
        
	    try {
	    	/*
		      * get financial from and to date from startup page
		      */
		    String financialFromDate =Startup.getfinancialFromDate();
		    String financialToDate=Startup.getFinancialToDate();
		    
		    /*
		     * get given to date from previous page
		     */
		    String IEToDateString = reportMenu.givenToDateString;
		    
		    getSelectedOrgType = reportMenu.orgtype;
		    
		    /*
		     * get reference of all table IDs
		     */
		    IEtable1 = (TableLayout)findViewById(R.id.maintable1);
	        IEtable2 = (TableLayout)findViewById(R.id.maintable2);
	        IEtable3 = (TableLayout)findViewById(R.id.maintable3);
	        IEtable4 = (TableLayout)findViewById(R.id.maintable4);
		    
		    /*
		     * set financial from date and to date in textview
		     */
		    TextView tvfinancialFromDate = (TextView) findViewById( R.id.tvTfinancialFromDate );
		    TextView tvfinancialToDate = (TextView) findViewById( R.id.tvTfinancialToDate );
		    tvfinancialFromDate.setText("Financial from : " +financialFromDate);
		    tvfinancialToDate.setText("Financial to : " +financialToDate);
		    
		    
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
	            IEGrid = new ArrayList<ArrayList<String>>();
	            
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
	            }
	            else if(count == 2){
	            	addTable(IEtable2);
	            }
	            else if(count == 3){
	            	addTable(IEtable3);
	            }
	            else if(count == 4){
	            	addTable(IEtable4);
	            }
	        }
        
        
	    } catch (Exception e) {
	    	System.out.println("I am an error"+e);
	    	AlertDialog.Builder builder = new AlertDialog.Builder(incomeExpenditure.this);
	    	
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
        for(int i=0;i<IEGrid.size();i++){
        	
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(IEGrid.get(i));
            //create new row
            tr = new TableRow(this);
            
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
            addRow(columnValue.get(j));   
            label.setBackgroundColor(color);
            
        	if(j==2){//for amount coloumn
        		if(columnValue.get(j).equalsIgnoreCase("Amount")){ // for heading "Amount"
        			label.setGravity(Gravity.CENTER);
        		}
        		else{
        			label.setGravity(Gravity.RIGHT);
                    //For adding rupee symbol
                    if(columnValue.get(j).length() > 0){
                    
                        final SpannableString rsSymbol = new SpannableString(incomeExpenditure.this.getText(R.string.Rs)); 
                        label.setText(rsSymbol+" "+columnValue.get(j).toString());
                    }
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