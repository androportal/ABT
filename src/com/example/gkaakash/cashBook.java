package com.example.gkaakash;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.gkaakash.MainActivity;
import com.example.gkaakash.R;
import com.example.gkaakash.cashBook;
import com.example.gkaakash.module;
import com.example.gkaakash.reportMenu;
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;

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
import android.widget.Toast;


public class cashBook extends Activity{
	private module module ;
	private Report report ;
	static Integer client_id;
	private int group1Id = 1;
	int PDF = Menu.FIRST;
	int CSV = Menu.FIRST + 1;
	static String IPaddr;
	static String acc_name; 
	Date date;
	SpannableString rsSymbol;	
	ScrollView cashbook_sv;
	String[] pdf_params; 
	TableLayout floating_heading_table;
	TableLayout cashbooktable;
	String getSelectedOrgType,financialFromDate,financialToDate;
	static String fromDateString,toDateString;
	Object[] cashBookResult;
	ArrayList<String> cashBookResultList;
    ArrayList<ArrayList> cashBookGrid,cashBook_with_header;
    ArrayList<ArrayList> cashBook;
    String 	OrgPeriod,balancePeriod,sFilename,OrgName,result;
    TableRow tr;
    TextView label;
    
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
			String password = module.setPasswordForPdfFile(cashBook.this,inflater, R.layout.sign_up,0, pdf_params, cashBookGrid, null);
			return true;

		case 2:
			//module.csv_writer(pdf_params,ledgerGrid_with_header);
			module.toastValidationMessage(cashBook.this, "CSV exported");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cash_book_table);
       // System.out.println("in cash"+IPaddr);
        IPaddr = MainActivity.IPaddr;
       
        report = new Report(IPaddr);
      
        client_id= Startup.getClient_id();
       
        rsSymbol = new SpannableString(cashBook.this.getText(R.string.Rs)); 
        module = new module();   
        //customizing title bar
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
   
        
        	/**
        	 * get view of floating header and set it as GONE
        	 */
        	floating_heading_table = (TableLayout) findViewById(R.id.floating_heading_table);
			floating_heading_table.setVisibility(TableLayout.GONE);
			
			/** 
			 * get scroll view of cash book
			 */
        	cashbook_sv = (ScrollView)findViewById(R.id.ScrollCashBook);
   	
        	/*
        	 * get reference of table IDs
        	 */
        	cashbooktable = (TableLayout)findViewById(R.id.maintable);
   	
        	/*
        	 * get financial from and to date from startup page
        	 */
        	
        	financialFromDate = Startup.getfinancialFromDate();
        	financialToDate= Startup.getFinancialToDate();
        	fromDateString = reportMenu.givenfromDateString;
        	toDateString = reportMenu.givenToDateString;
        	OrgName = MainActivity.organisationName;
        	
        	//set title
			TextView org = (TextView)findViewById(R.id.org_name);
			org.setText(OrgName + ", "+reportMenu.orgtype);
			TextView tvdate = (TextView)findViewById(R.id.date);
			tvdate.setText(module.changeDateFormat(financialFromDate)+" To "+module.changeDateFormat(financialToDate));
   
        	/*
        	 * set financial from date and to date in textview
        	 */
        	
        	TextView tv = (TextView) findViewById( R.id.tvfinancialToDate );
     
        	//to get month in words
			SimpleDateFormat read = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat write = new SimpleDateFormat("dd-MMM-yyyy");
			String str_fromDate;
			String str_toDate;
			try {
				str_fromDate = write.format(read.parse(fromDateString));
				str_toDate = write.format(read.parse(toDateString));
				tv.setText("Period : "+str_fromDate+" to "+str_toDate);   
		    	
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	tv = (TextView) findViewById(R.id.tvaccountName);  
	    	tv.setText("Cash Book");
	    	
        	/*
        	 * send params to controller report.getcashBook to get the result 
        	 */
 
        	Object[] params = new Object[]{financialFromDate,fromDateString,toDateString};
   
        	cashBookResult = (Object[]) report.getCashBook(params,client_id);  
        	int length = cashBookResult.length;
        	cashBook_with_header = new ArrayList<ArrayList>();   
        	//cashBookResult is 3 dimensional list 
        	//int count = 0;
        	for(Object cf : cashBookResult){
        		
        		//if(count<length-1)
        		//{
	        		Object[] c = (Object[]) cf;
	        		
	        		cashBookGrid = new ArrayList<ArrayList>();   
	        		
	        				cashBookResultList = new ArrayList<String>();
	           	
	        				for(int j=0;j<c.length;j++){
	        					
	        					cashBookResultList.add((String) c[j].toString());
	        				}
	        				
	        				cashBookGrid.add(cashBookResultList);
	        		
	        			  addTable(cashbooktable);
	        			  cashBook_with_header.addAll(cashBookGrid);
        		//}
        			  //count++;
        	}
        	
  	    	OrgName = MainActivity.organisationName;
             
  	    	
			createMenuOptions();
			changeInputs();
		
	}
	
	
	private void addTable(TableLayout tableID) {
        /** Create a TableRow dynamically **/
        for(int i=0;i<cashBookGrid.size();i++){  
        	System.out.println("print grid add Table "+cashBookGrid);
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(cashBookGrid.get(i));
            //create new row
            tr = new TableRow(this);   

            if(columnValue.get(0).equalsIgnoreCase("Particulars")){
               	//for heading pass green color code
            	 System.out.println("i am in chaninging color "+columnValue.get(1));
               	setRowColorSymbolGravity(columnValue, Color.WHITE, true);
            }else if(columnValue.get(0).equalsIgnoreCase("Cash Accounts")||columnValue.get(0).equalsIgnoreCase("Bank Accounts"))
            {
            	setRowColorSymbolGravity(columnValue, Color.parseColor("#218d9d"), false);
            	
            }else{   
            	int row_color;
            	if ((i + 1) % 2 == 0)
            	{
            		row_color = Color.parseColor("#085e6b"); //blue theme
            		  
            	}
        		else
        		{
        			row_color = Color.parseColor("#2f2f2f"); //gray theme
        			
        		}
            	
            	//for remaining rows pass black color code
               setRowColorSymbolGravity(columnValue, row_color, false);
            }
            // Add the TableRow to the TableLayout
            tableID.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        }
    }  
    
	void addRow(String param, final int i) {
		
		System.out.println(" add roe params "+param);
		label = new TextView(this);
		label.setText(param);
		label.setTextSize(18);
		label.setTextColor(Color.WHITE);
		label.setBackgroundColor(Color.BLACK);
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		label.setPadding(2, 2, 2, 2);
		LinearLayout Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(1, 1, 1, 1);
		Ll.addView(label, params);
		tr.addView((View) Ll); // Adding textView to tablerow.
		drillDown();
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
    			if(j == 1 ||j == 2){ //amount column
    				addRow(rsSymbol+" "+columnValue.get(j),j);   
    			}else{
    				addRow(columnValue.get(j),j);   
    			}
    			label.setTextColor(Color.BLACK); //blue theme
    		}
    		else{
    			addRow(columnValue.get(j),j);   
    			
    		}
    		label.setBackgroundColor(color);
            String amount = columnValue.get(1).toString();
            String name = columnValue.get(0).toString();
            if(j==1){//for amount coloumn
            	if(!amount.equalsIgnoreCase("Debit Balance")&&!amount.equalsIgnoreCase("Credit Balance"))
            	{
            		label.setGravity(Gravity.RIGHT);
               
            		if(columnValue.get(j).length() > 0){
            			DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
            			String colValue;
            			((TextView)label).setGravity(Gravity.RIGHT);
            			//For adding rupee symbol
            			colValue=columnValue.get(j);
            			if(!"".equals(colValue)){
            				
            				if(!"0.00".equals(colValue)){
            					Pattern pattern = Pattern.compile("\\n");
            					Matcher matcher = pattern.matcher(colValue);
            					boolean found = matcher.find();
            					
            					if(found==false){
            						double amount1 = Double.parseDouble(colValue);	
            						
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
	
    private void drillDown() {
		int count = cashbooktable.getChildCount();
		System.out.println("drill down "+count);
		
		for (int i = 0; i < count; i++) {
			System.out.println("i value"+i);
			final View row = cashbooktable.getChildAt(i);
			
			row.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					
					///Toast.makeText(cashBook.this, "m on click", Toast.LENGTH_LONG).show();
					LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(0);
					final TextView tv = (TextView) l.getChildAt(0);
					String first_col_value = tv.getText().toString();
					if(first_col_value.equalsIgnoreCase("Particulars")||first_col_value.equalsIgnoreCase("Bank Accounts")||
							first_col_value.equalsIgnoreCase("Cash Accounts")){	
						
						System.out.println("do nothing");
					}else
					{
						checkForAccountName(first_col_value, row);
					}
					return false;
				}
			});
			if(i==count)
			{
				count++;
			}
		}

		
	}
	
	private void changeInputs() {
		Button btn_changeInputs = (Button)findViewById(R.id.btn_changeInputs);
		btn_changeInputs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportMenu reportM = new reportMenu();
				reportM.callLedgerOrCashFlowOrBankRecon(cashBook.this,"CB",cashBook.class); 
			}
		});
	}
	/*
     * below method helps to find out the selected string is account name or not.
     * check for empty string, string "Total" and header column "account name".
     */
	private void checkForAccountName(String accname, View row) {
    	if(!(accname.equalsIgnoreCase("") || 
    							accname.equalsIgnoreCase("Total") ||
    							accname.equalsIgnoreCase("Particulars")||accname.equalsIgnoreCase("Debit Balance")
    							||accname.equalsIgnoreCase("Credit Balance"))){
    		//Toast.makeText(balanceSheet.this, "account name", Toast.LENGTH_SHORT).show();
    		//change the row color(black/gray to orange) when clicked
    		
			for (int j = 0; j < 3; j++) {
				LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(j);
				TextView t = (TextView)l.getChildAt(0);
				ColorDrawable drawable = (ColorDrawable)t.getBackground();
				ObjectAnimator colorFade = ObjectAnimator.ofObject(t, "backgroundColor", new ArgbEvaluator(), Color.parseColor("#FBB117"),drawable.getColor());
				colorFade.setDuration(100);
				colorFade.start();
			}
    		acc_name = accname;
			Intent intent = new Intent(getApplicationContext(),ledger.class);
			intent.putExtra("flag", "from_cashbook");
			startActivity(intent);
    	}
		
	}
    
    public void createMenuOptions() {
    	Button btn_optionsMenu = (Button)findViewById(R.id.btn_optionsMenu);
		
		btn_optionsMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				date= new Date();
				String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
				OrgPeriod = "Financial Year: "+financialFromDate+" to "+financialToDate;
				balancePeriod = fromDateString+" to "+toDateString;
				sFilename = "cashBook"+"_"+ OrgName.replace(" ", "")+ "_" +financialFromDate.substring(8)+"-"+financialToDate.substring(8)+"_"+date_format;
				pdf_params = new String[]{"cash",sFilename,OrgName,OrgPeriod,"Cash Book",balancePeriod,"",result,rsSymbol.toString()};
			
				CharSequence[] items = new CharSequence[]{"Export as PDF","Export as CSV"};
				
				AlertDialog dialog;
				//creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(cashBook.this);
				//setting title
				builder.setTitle("Select");
				//adding items
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int pos) {
						
						if(pos == 0){
							System.out.println("onclick params "+pdf_params);
							LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
							String password = module.setPasswordForPdfFile(cashBook.this,inflater, 
									R.layout.sign_up, 0, pdf_params,cashBook_with_header,null);
				   			
						}else if(pos == 1){
							module.csv_writer(pdf_params,cashBook_with_header);
							module.toastValidationMessage(cashBook.this, "CSV exported please see at /mnt/sdcard/"+pdf_params[1]);
						}
					}    
				});
				dialog=builder.create();
				((Dialog) dialog).show();
			}
			
		});
		
	}
    public void onBackPressed() {
		
		MainActivity.nameflag = false;
		Intent intent = new Intent(getApplicationContext(),reportMenu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

}

}
