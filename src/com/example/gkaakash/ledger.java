package com.example.gkaakash;

import java.util.ArrayList;

import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ledger extends Activity{
    TableLayout ledgertable;
    TableRow tr;
    TextView label,tvaccontName,tvfinancialFromDate,tvfinancialToDate;
    ArrayList<ArrayList<String>> ledgerGrid;
    static Object[] ledgerResult;
    static Integer client_id;
    private Report report;
    ArrayList<String> ledgerResultList;
   
     
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ledger_table);
       
        report = new Report();
           client_id= Startup.getClient_id();
           System.out.println("param ledger are.......");
          
           String financialFromDate =Startup.getfinancialFromDate();
           String financialToDate=Startup.getFinancialToDate();
           String accountName = reportMenu.selectedAccount;
           String projectName = reportMenu.selectedProject;
           String fromDate = reportMenu.LedgerFromDateString;
           String toDate = reportMenu.LedgerToDateString;
       
           tvaccontName = (TextView) findViewById( R.id.tvaccountName );
           tvfinancialFromDate = (TextView) findViewById( R.id.tvfinancialFromDate );
           tvfinancialToDate = (TextView) findViewById( R.id.tvfinancialToDate );
          
           tvaccontName.setText("Account Name: "+accountName);
           tvfinancialFromDate.setText("Financial from date: " +financialFromDate);
           tvfinancialToDate.setText("Financial to date: " +financialToDate);
          
           System.out.println(accountName+""+projectName+""+fromDate+""+financialFromDate+""+toDate);
          
        Object[] params = new Object[]{accountName,financialFromDate,fromDate,toDate,projectName};
        ledgerResult = (Object[]) report.getLedger(params,client_id);
       
        ledgerGrid = new ArrayList<ArrayList<String>>();
        for(Object tb : ledgerResult) 
        {
           
            Object[] t = (Object[]) tb;
            ledgerResultList = new ArrayList<String>();
            for(int i=0;i<(t.length-1);i++){
               
                ledgerResultList.add((String) t[i].toString());
               
            }
            ledgerGrid.add(ledgerResultList);
        }   
        System.out.println(ledgerResultList);
        System.out.println(ledgerGrid);
       
        ledgertable = (TableLayout)findViewById(R.id.maintable);
        addTable();
       
    }
   
    private void addTable() {
        addHeader();
        /** Create a TableRow dynamically **/
        for(int i=0;i<ledgerGrid.size();i++){
            System.out.println(ledgerGrid.get(i));
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(ledgerGrid.get(i));
            tr = new TableRow(this);
           
            for(int j=0;j<columnValue.size();j++){
                System.out.println("this is me"+j);
                System.out.println(columnValue.get(j));
                /** Creating a TextView to add to the row **/
                addRow(columnValue.get(j));   
                label.setBackgroundColor(Color.BLACK);
                if(j == 3 || j == 4){
                       label.setGravity(Gravity.RIGHT);
                }
                else{
                    label.setGravity(Gravity.CENTER);
                }
               
            }
           
            // Add the TableRow to the TableLayout
            ledgertable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    void addHeader(){
        /** Create a TableRow dynamically **/
        String[] ColumnNameList = new String[] {"Date","Particulars","Reference No","Debit","Credit","Narration"};
       
        tr = new TableRow(this);
        for(int k=0;k<ColumnNameList.length;k++){
            /** Creating a TextView to add to the row **/
            addRow(ColumnNameList[k]);
            label.setBackgroundColor(Color.parseColor("#348017"));
            label.setGravity(Gravity.CENTER);
        }
       
         // Add the TableRow to the TableLayout
        ledgertable.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
       
    }
   
    void addRow(String param){
        label = new TextView(this);
        label.setText(param);
        label.setTextColor(Color.WHITE);
        label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        label.setPadding(2, 2, 2, 2);
      
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 1, 1, 1);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(label,params);
        tr.addView((View)Ll);
       
       
    }

   
}