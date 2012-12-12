package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams; 
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;

public class projectStatement extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] projectStatementResult;
    TableLayout projectStatementTable;
    TableRow tr;
    TextView label;
    ArrayList<ArrayList<String>> projectStatementGrid;
    ArrayList<String> projectStatementResultList;
    String ToDateString;
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.project_statement_table);
        report = new Report();
	    client_id= Startup.getClient_id();
	    
	    try {
	    	/*
		      * get financial from and to date from startup page
		      */
		    String financialFromDate =Startup.getfinancialFromDate();
		    String financialToDate=Startup.getFinancialToDate();
		    ToDateString = reportMenu.givenToDateString;
		    
		    String projectName = reportMenu.selectedProject;
		    /*
		     * set financial from date and to date in textview
		     */
		    TextView tvfinancialFromDate = (TextView) findViewById( R.id.tvTfinancialFromDate );
		    TextView tvfinancialToDate = (TextView) findViewById( R.id.tvTfinancialToDate );
		      
		    //tvfinancialFromDate.setText("Financial from: " +financialFromDate);
		   // tvfinancialToDate.setText("Financial to: " +ToDateString);
		    tvfinancialToDate.setText("Period : "+financialFromDate+" to "+ToDateString);
		    /*
		     * send params to controller report.getProjectStatementReport to get the result
		     */
		    Object[] params = new Object[]{projectName, financialFromDate,financialFromDate,ToDateString};
	        projectStatementResult = (Object[]) report.getProjectStatementReport(params,client_id);
	       
	        projectStatementGrid = new ArrayList<ArrayList<String>>();
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
	    } catch (Exception e) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(projectStatement.this);
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
                    
                        final SpannableString rsSymbol = new SpannableString(projectStatement.this.getText(R.string.Rs)); 
                        label.setText(rsSymbol+" "+columnValue.get(j).toString());
                    }
                }
                else{
                    label.setGravity(Gravity.CENTER);
                }
            }
            // Add the TableRow to the TableLayout
            projectStatementTable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
        
        /*
         * display the difference between total dr and total cr
         */
        ArrayList<String> lastrow=projectStatementGrid.get(projectStatementGrid.size()-1);
        Float result=Float.parseFloat(lastrow.get(4))-Float.parseFloat(lastrow.get(3));
        TextView difference = (TextView) findViewById(R.id.tvdifference);
        
        final SpannableString rsSymbol = new SpannableString(projectStatement.this.getText(R.string.Rs));
        difference.setText("Difference in Opening Balances : "+rsSymbol+" "+(String.format("%.2f", Math.abs(result))));
    }

    /*
     * add column heads to the table
     */
    void addHeader(){
        /** Create a TableRow dynamically **/
        String[] ColumnNameList = new String[] { "Sr. no.","Account name","Group name","Total debit","Total credit"};
       
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
                LayoutParams.WRAP_CONTENT));
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