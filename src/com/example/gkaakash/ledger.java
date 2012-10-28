package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;

import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Toast;

public class ledger extends Activity{
    TableLayout ledgertable;
    TableRow tr;
    TextView label,tvaccontName,tvfinancialFromDate,tvfinancialToDate;
    ArrayList<ArrayList> ledgerGrid;
    static Object[] ledgerResult;
    static Integer client_id;
    private Report report;
    ArrayList<String> ledgerResultList;
    private ArrayList accountlist;
   
     
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.ledger_table);
       
       report = new Report();
       client_id= Startup.getClient_id();
      
       try {
           String financialFromDate =Startup.getfinancialFromDate();
           String financialToDate=Startup.getFinancialToDate();
           String accountName = reportMenu.selectedAccount;
           String projectName = reportMenu.selectedProject;
           String fromDate = reportMenu.LedgerFromDateString;
           String toDate = reportMenu.LedgerToDateString;
       
           tvaccontName = (TextView) findViewById( R.id.tvaccountName );
           tvfinancialFromDate = (TextView) findViewById( R.id.tvfinancialFromDate );
           tvfinancialToDate = (TextView) findViewById( R.id.tvfinancialToDate );
          
           tvaccontName.setText("Account name: "+accountName);
           tvfinancialFromDate.setText("Financial from date: " +financialFromDate);
           tvfinancialToDate.setText("Financial to date: " +financialToDate);
              
              
            Object[] params = new Object[]{accountName,financialFromDate,fromDate,toDate,projectName};
            ledgerResult = (Object[]) report.getLedger(params,client_id);
           
            ledgerGrid = new ArrayList<ArrayList>();
            for(Object tb : ledgerResult) 
            {
               
                Object[] t = (Object[]) tb;
                ledgerResultList = new ArrayList<String>();
                for(int i=0;i<(t.length-1);i++)
                {
                    if (i==1)
                    {
                        Object[] acc = new Object[]{t[i]};
                        ArrayList accledgerGrid = new ArrayList();
                        ArrayList dateGrid = new ArrayList();
                        ArrayList refGrid = new ArrayList();
                        ArrayList debitGrid = new ArrayList();
                        ArrayList creditGrid = new ArrayList();
                        ArrayList narrationGrid = new ArrayList();
                        for(Object a : acc) 
                        {
                            Object[] abc= (Object[]) a;
                            int yy = abc.length;
                                // accledgerGrid.add(abc[]);
                                // System.out.println(accledgerGrid);
                             if(abc.length == 1){
                                 dateGrid.add((String) t[0].toString());
                                 accledgerGrid.add(abc[0]);
                                 refGrid.add((String) t[2].toString());
                                 debitGrid.add((String) t[3].toString());
                                 creditGrid.add((String) t[4].toString());
                                 narrationGrid.add((String) t[5].toString());
                                 //ledgerResultList.add(accledgerGrid.toString());
                             }
                             else if(abc.length > 1){
                                 String allaccnames = "";
                                 String date = (String) t[0].toString();
                                 String ref = (String) t[2].toString();
                                 String debit = (String) t[3].toString();
                                 String credit = (String) t[4].toString();
                                 String narr = (String) t[5].toString();
                                 for(int j=0;j<yy;j++){
                                     if(j==yy-1)
                                     {
                                         allaccnames = allaccnames+abc[j].toString() ;
                                     }
                                     else
                                     {
                                         allaccnames = allaccnames+abc[j].toString()+"\n";
                                         date = date+"\n";
                                         ref = ref+"\n";
                                         debit = debit+"\n";
                                         credit = credit+"\n";
                                         narr = narr+"\n";
                                     }
                                    
                                     //accledgerGrid.add(abc[j].toString());
                                 }
                                 accledgerGrid.add(allaccnames);
                                 dateGrid.add(date);
                                 refGrid.add(ref);
                                 debitGrid.add(debit);
                                 creditGrid.add(credit);
                                 narrationGrid.add(narr);
                             }
                             
                        }
                        ledgerResultList.add(dateGrid.toString());
                        ledgerResultList.add(accledgerGrid.toString());
                        ledgerResultList.add(refGrid.toString());
                        ledgerResultList.add(debitGrid.toString());
                        ledgerResultList.add(creditGrid.toString());
                        ledgerResultList.add(narrationGrid.toString());
                    }
                    /*
                    else
                    {
                        ledgerResultList.add((String) t[i].toString());
                    }
                    */
                    
                }
                
                ledgerGrid.add(ledgerResultList);
            } 
           
            ledgertable = (TableLayout)findViewById(R.id.maintable);
            addTable();
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
   
    private void addTable() {
        addHeader();
        System.out.println("ledgerGrid."+ledgerGrid);
        
        /** Create a TableRow dynamically **/
        for(int i=0;i<ledgerGrid.size();i++){
            ArrayList columnValue = new ArrayList();
            columnValue.addAll(ledgerGrid.get(i));
            tr = new TableRow(this);
           
            for(int j=0;j<columnValue.size();j++){
                
                    String[] accname = new String[]{columnValue.get(j).toString()}; 
                    for(String a : accname){
                    
                        String particular =  a.substring(1,(a.length()-1));
                        /** Creating a TextView to add to the row **/ 
                        addRow(particular);
                        
                    }
                    
                label.setBackgroundColor(Color.BLACK);
                if(j == 3 || j == 4){
                    String[] amount = new String[]{columnValue.get(j).toString()}; 
                    //For adding rupee symbol
                    for(String a : accname){
                        String drOrcramount =  a.substring(1,(a.length()-1));
                        String dr_cr_amount = drOrcramount.trim();
                        if(dr_cr_amount.length() > 0){
                            final SpannableString rsSymbol = new SpannableString(ledger.this.getText(R.string.Rs)); 
                            label.setText(rsSymbol+" "+drOrcramount); 
                        }
                    }
                    label.setGravity(Gravity.RIGHT);
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
                LayoutParams.MATCH_PARENT));
       
    }
   
    void addRow(String param){
        label = new TextView(this);
        label.setText(param);
        label.setTextColor(Color.WHITE);
        //label.setBackgroundColor(Color.);
        label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT));
        label.setBackgroundColor(Color.BLACK);
        label.setPadding(2, 2, 2, 2);
      
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);
        //Ll.setPadding(10, 5, 5, 5);
        Ll.addView(label,params);
        tr.addView((View)Ll);
        
    }

   
}