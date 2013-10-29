package com.example.gkaakash;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkaakash.controller.PdfGenaretor;
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;
import com.itextpdf.text.DocumentException;

public class incomeExpenditure extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] IEResult;
    TableLayout IEtable1, IEtable2;
    TableRow tr;
    TextView label;
    ArrayList<String> IEResultList;
    ArrayList<ArrayList> IEGrid;
    ArrayList<ArrayList> IEGrid1,IEGrid2;
    String[] ColumnNameList;
    String getSelectedOrgType;
    String IEToDateString;
    Boolean updown=false;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
 	String colValue;
 	Boolean alertdialog = false;
    ObjectAnimator animation2;
    boolean reportmenuflag;
    String financialToDate,financialFromDate,OrgName,date_format,OrgPeriod ,TrialPeriod,sFilename,Reporttypeflag;
    String[] pdf_params;
    private int group1Id = 1;
	int PDF = Menu.FIRST;
	int CSV = Menu.FIRST + 1;
	module m;
	SpannableString rsSymbol;
	static String IPaddr;	
	static String acc_name; 
		
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
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			String password = m.setPasswordForPdfFile(incomeExpenditure.this,inflater, R.layout.sign_up, 1, pdf_params, IEGrid1,IEGrid2);
			return true;

		case 2:
			m.csv_writer1(pdf_params,IEGrid1, IEGrid2);
			m.toastValidationMessage(incomeExpenditure.this, "CSV exported");

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	 
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.income_expenditure_table);
        IPaddr = MainActivity.IPaddr;
    	System.out.println("in createorg"+IPaddr);
        report = new Report(IPaddr);
        client_id= Startup.getClient_id();
        m=new module();
        rsSymbol = new SpannableString(incomeExpenditure.this.getText(R.string.Rs));
        //customizing title bar
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
   
        try {
        	/*
        	 * get financial from and to date from startup page
        	 */
        	financialFromDate =Startup.getfinancialFromDate();
        	financialToDate=Startup.getFinancialToDate();
   
        	/*
        	 * get given to date from previous page
        	 */
        	IEToDateString = reportMenu.givenToDateString;
   
        	getSelectedOrgType = reportMenu.orgtype;
   
        	/*
        	 * get reference of all table IDs
        	 */
        	IEtable1 = (TableLayout)findViewById(R.id.maintable1);
        	IEtable2 = (TableLayout)findViewById(R.id.maintable2);
  
        	/*
        	 * set financial from date and to date in textview
        	 */
        	TextView tv = (TextView) findViewById( R.id.tvfinancialToDate );
        	//to get month in words
			SimpleDateFormat read = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat write = new SimpleDateFormat("dd-MMM-yyyy");
			String str_fromDate = write.format(read.parse(financialFromDate));
			String str_toDate = write.format(read.parse(IEToDateString));
	    	tv.setText("Period : "+str_fromDate+" to "+str_toDate);   
	    	
	    	
   
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
        		IEGrid = new ArrayList<ArrayList>();
           
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
        			IEGrid1=IEGrid;
        		}
        		else if(count == 2){
        			addTable(IEtable2);
        			IEGrid2=IEGrid;
        		}
           
        	}
        	
		   
        	 
	         OrgPeriod = "Financial Year:  "+financialFromDate+" to "+financialToDate;
	         TrialPeriod = financialFromDate+" to "+IEToDateString;
	        // trialBalGrid = new ArrayList<ArrayList>();
        	Reporttypeflag = reportMenu.reportTypeFlag;
        	
        	OrgName = MainActivity.organisationName;
        	
        	//set title
			TextView org = (TextView)findViewById(R.id.org_name);
			org.setText(OrgName + ", "+getSelectedOrgType);
			TextView tvdate = (TextView)findViewById(R.id.date);
			tvdate.setText(m.changeDateFormat(financialFromDate)+" To "+m.changeDateFormat(financialToDate));
			
			tv = (TextView) findViewById(R.id.tvaccountName);
	    	tv.setText(Reporttypeflag);
			
//            final Button btnScrollDown = (Button)findViewById(R.id.btnScrollDown);            
//            btnScrollDown.setOnClickListener(new OnClickListener() {
// 	
//            	@Override
//            	public void onClick(View v) {
//            		if(updown==false){
//            			ScrollView sv = (ScrollView)findViewById(R.id.ScrollIncome);
//            			sv.fullScroll(ScrollView.FOCUS_DOWN); 
//            			btnScrollDown.setBackgroundResource(R.drawable.up);
//            			updown=true;
//            		}else {
//            			ScrollView sv = (ScrollView)findViewById(R.id.ScrollIncome);
//            			sv.fullScroll(ScrollView.FOCUS_UP); 
//            			btnScrollDown.setBackgroundResource(R.drawable.down);
//            			updown=false;
//            		}
//            	}
//            });
          
           
            //animated_diolog();
            createMenuOptions();
            changeInputs();
        } catch (Exception e) {
        	m.toastValidationMessage(incomeExpenditure.this, "Please try again"); 
        }
    }
   
    private void changeInputs() {
		Button btn_changeInputs = (Button)findViewById(R.id.btn_changeInputs);
		btn_changeInputs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportMenu reportM = new reportMenu();
				reportM.callReport(incomeExpenditure.this,"I",incomeExpenditure.class);
			}
		});
	}
    
    public void createMenuOptions() {
    	Button btn_optionsMenu = (Button)findViewById(R.id.btn_optionsMenu);
		
		btn_optionsMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Date date = new Date();
		   		date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
		   	  if(Reporttypeflag.equalsIgnoreCase("Income and Expenditure"))
				{
						sFilename = "IE"+"_"+ OrgName.replace(" ", "")+ "_" +
								financialFromDate.substring(8)+"-"+financialToDate.substring(8)+"_"+date_format;
			        	pdf_params = new String[]{"I&E",sFilename,OrgName,OrgPeriod,Reporttypeflag,TrialPeriod,"","",rsSymbol.toString()};
				}else
			    {
						sFilename = "PL"+"_"+date_format;
			        	pdf_params = new String[]{"P&L",sFilename,OrgName,OrgPeriod,Reporttypeflag,TrialPeriod,"","",rsSymbol.toString()};
			    }
	         
				CharSequence[] items = new CharSequence[]{ "Export as PDF","Export as CSV"};
				
				AlertDialog dialog;
				//creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(incomeExpenditure.this);
				//setting title
				builder.setTitle("Select");
				//adding items
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int pos) {
						if(pos == 0){
							LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
							String password = m.setPasswordForPdfFile(incomeExpenditure.this,inflater, R.layout.sign_up, 1, pdf_params, IEGrid1,IEGrid2);
							
						}else if(pos == 1){
							m.csv_writer1(pdf_params,IEGrid1, IEGrid2);
							m.toastValidationMessage(incomeExpenditure.this, "CSV exported please see at /mnt/sdcard/"+pdf_params[1]);
						}
					}
				});
				dialog=builder.create();
				((Dialog) dialog).show();
			}
			
		});
    }

    private void addTable(TableLayout tableID) {
        /** Create a TableRow dynamically **/
        for(int i=0;i<IEGrid.size();i++){
       
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(IEGrid.get(i));
            //create new row
            tr = new TableRow(this);
            //System.out.println("i am row"+columnValue);
            
            if(columnValue.get(2).equalsIgnoreCase("Amount")){
            	//for heading pass green color code
            	setRowColorSymbolGravity(columnValue, Color.WHITE);
            }
            else{
            	int row_color;
            	if ((i + 1) % 2 == 0)
            		row_color = Color.parseColor("#085e6b"); //blue theme
        		else
        			row_color = Color.parseColor("#2f2f2f"); //gray theme
            	
            	//for remaining rows pass black color code
               	setRowColorSymbolGravity(columnValue, row_color);
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
           
            
    		if(j==2){//for amount coloumn
    			if(columnValue.get(j).equalsIgnoreCase("Amount")){ // for heading "Amount"
    				//For adding rupee symbol
    				
    				addRow(rsSymbol+" "+columnValue.get(j));   
    				label.setBackgroundColor(color);
    				label.setTextColor(Color.BLACK);
    				label.setGravity(Gravity.CENTER);
    			}
    			else{
    				addRow(columnValue.get(j));   
    				label.setBackgroundColor(color);
    				label.setGravity(Gravity.RIGHT);
                   
                    if(columnValue.get(j).length() > 0){///
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
    			}
            }
            else if (j==1){
            	if(columnValue.get(j).equalsIgnoreCase("Particulars")){
            		addRow(columnValue.get(j));  
            		label.setGravity(Gravity.CENTER);
            		label.setTextColor(Color.BLACK);
            	}else if(columnValue.get(j).equalsIgnoreCase("Direct Expense")
            			||columnValue.get(j).equalsIgnoreCase("Direct Income")
            			||columnValue.get(j).equalsIgnoreCase("Indirect Expense")
            			||columnValue.get(j).equalsIgnoreCase("Indirect Income")){ // for heading "Amount"
            		label.setGravity(Gravity.LEFT);
            		addRow(columnValue.get(j));   
            		label.setBackgroundColor(color);
            	}else
            	{
            		if(!columnValue.get(j).equalsIgnoreCase("Total"))
            		{
            			//for accounts
            			addRow("          "+columnValue.get(j));   
            			label.setBackgroundColor(color);
       	       
            		}else
            		{
            			addRow(columnValue.get(j)); 
            			label.setBackgroundColor(color);
            			label.setGravity(Gravity.RIGHT);
            		}
            	}
            }else{
            	addRow(columnValue.get(j));   
            	label.setBackgroundColor(color);
            }
        }
    }

    /*
     * this function add the value to the row
     */
    void addRow(String param){
    	drillDown();
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
    private void drillDown() {
    	int count = IEtable1.getChildCount();
		for (int i = 0; i < count; i++) {
			final View row = IEtable1.getChildAt(i);
			
			row.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {

					LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(1);
					final TextView tv = (TextView) l.getChildAt(0);
					checkForAccountName(tv.getText().toString(), row);
					return false;
				}
			});
		}

		int count1 = IEtable2.getChildCount();
		for (int i = 0; i < count1; i++) {
			final View row = IEtable2.getChildAt(i);

			row.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {

					LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(1);
					final TextView tv = (TextView) l.getChildAt(0);
					checkForAccountName(tv.getText().toString(), row);
					return false;
					}
			});
		}
	}
    
    /*
     * below method helps to find out the selected string is account name or not.
     * check for empty string, string "Total" and header column "account name".
     */
	private void checkForAccountName(String accname, View row) {
		accname = accname.trim();
    	if(!(accname.trim().equalsIgnoreCase("") || 
    			accname.equalsIgnoreCase("Particulars") ||
    			accname.equalsIgnoreCase("Total") ||
    			accname.equalsIgnoreCase("Direct Expense") ||
   				accname.equalsIgnoreCase("Direct Income") ||
   				accname.equalsIgnoreCase("Indirect Income") ||
   				accname.equalsIgnoreCase("Indirect Expense") ||
   				accname.equalsIgnoreCase("Gross Deficit C/O") ||
   				accname.equalsIgnoreCase("Gross Deficit B/F") ||
   				accname.equalsIgnoreCase("Net Deficit") ||
   				accname.equalsIgnoreCase("Gross Profit C/O") ||
   				accname.equalsIgnoreCase("Gross Loss C/O") ||
   				accname.equalsIgnoreCase("Gross Loss B/F") ||
   				accname.equalsIgnoreCase("Net Profit") ||
   				accname.equalsIgnoreCase("Net Surplus") ||
   				accname.equalsIgnoreCase("Gross Profit B/F") ||
   				accname.equalsIgnoreCase("Gross Surplus B/F") ||
   				accname.equalsIgnoreCase("Net Loss") ||
   				accname.equalsIgnoreCase("Gross Surplus C/O"))){
    		//Toast.makeText(balanceSheet.this, "account name", Toast.LENGTH_SHORT).show();
    		//change the row color(black/gray to orange) when clicked
    		
			for (int j = 0; j <= 2; j++) {
				LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(j);
				TextView t = (TextView) l.getChildAt(0);
				ColorDrawable drawable = (ColorDrawable)t.getBackground();
		
				ObjectAnimator colorFade = ObjectAnimator.ofObject(t, "backgroundColor", new ArgbEvaluator(), Color.parseColor("#FBB117"),drawable.getColor());
				colorFade.setDuration(100);
				colorFade.start();
			}
    		acc_name = accname;
			Intent intent = new Intent(getApplicationContext(),ledger.class);
			intent.putExtra("flag", "from_IncExp");
			startActivity(intent);
    	}
		
	}

	public void onBackPressed() {
		
		MainActivity.nameflag = false;
		Intent intent = new Intent(getApplicationContext(),reportMenu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

}
}