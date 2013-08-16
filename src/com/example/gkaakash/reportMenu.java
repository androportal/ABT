package com.example.gkaakash;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Text;

import com.gkaakash.controller.Account;
import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class reportMenu extends Activity{
	//adding report list items
	
	final Context context = this;
	static AlertDialog dialog;
	DecimalFormat mFormat;
	static String financialFromDate;
	static String financialToDate;
	static String givenfromDateString;
	static String givenToDateString;
	static Integer client_id;
	private Account account;
	private Organisation organisation;
	static String selectedAccount;
	static String selectedProject;
	static boolean cheched = false;
	static String fromday, frommonth, fromyear, today, tomonth, toyear; 
	static boolean validateDateFlag;
	static String trialbalancetype;
	String[] reportType;
	static String orgtype;
	boolean reportmenuflag;
	String orgname;
	static String reportTypeFlag;
	static String balancetype;
	module m;
	TextView tvLedgerWarning;
	static String IPaddr;
	static final int FROM_DATE_DIALOG_ID = 0, TO_DATE_DIALOG_ID = 1;
	Button from_btn_ID, to_btn_ID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.report);
		IPaddr = MainActivity.IPaddr;
		System.out.println("in createorg"+IPaddr);
		account = new Account(IPaddr);
		organisation = new Organisation(IPaddr);
		m= new module();
		
       	client_id= Startup.getClient_id();
       	
       	/*
       	 * get org type 
       	 */
       	orgtype = menu.orgtype;
       	
        if("NGO".equals(orgtype))
        {
            reportType = new String[] { "Ledger","Trial Balance","Project Statement",
                 "Cash Flow","Balance Sheet","Income and Expenditure" };
        }
        else{
            reportType = new String[] { "Ledger","Trial Balance","Project Statement",
                 "Cash Flow","Balance Sheet","Profit and Loss account" };
        }
       	
       	//get financial from and to date, split and store day, month and year in seperate variable
       	financialFromDate =Startup.getfinancialFromDate();  	   	
	   	String dateParts[] = financialFromDate.split("-");
	   	fromday  = dateParts[0];
	   	frommonth = dateParts[1];
	   	fromyear = dateParts[2];
	   	
	   	financialToDate = Startup.getFinancialToDate();
	   	String dateParts1[] = financialToDate.split("-");
	   	today  = dateParts1[0];
	   	tomonth = dateParts1[1];
	   	toyear = dateParts1[2];
		
	   	//set title
	   	String OrgName = MainActivity.organisationName;
		TextView org = (TextView)findViewById(R.id.org_name);
		org.setText(OrgName + ", "+orgtype);
		TextView tvdate = (TextView)findViewById(R.id.date);
		tvdate.setText(m.changeDateFormat(financialFromDate)+" To "+m.changeDateFormat(financialToDate));
	   	
		//getting the list view and setting background
		final ListView listView = (ListView)findViewById(R.id.ListReportType);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reportType);
		 
		listView.setAdapter(adapter);
		listView.setTextFilterEnabled(true);
		listView.setBackgroundColor(Color.parseColor("#2f2f2f")); //gray theme
		listView.setCacheColorHint(Color.TRANSPARENT);
		
		//for two digit format date for dd and mm
		mFormat= new DecimalFormat("00");
		mFormat.setRoundingMode(RoundingMode.DOWN);
		
		//when report list items are clicked, code for respective actions goes here ...
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//for "Ledger"
				if(position == 0)
				{
					//call the allAccountNames method to get all account names
					Object[] accountnames = (Object[]) account.getAllAccountNames(client_id);
					// create new array list of type String to add account names
					List<String> accountnamelist = new ArrayList<String>();
					for(Object an : accountnames)
					{	
						accountnamelist.add((String) an); 
					}	

					
					if(accountnamelist.size() <= 0){
						String message = "Ledger cannot be displayed, Please create account!";
						m.toastValidationMessage(reportMenu.this,message);
					}
					else{
						//call the getAllProjects method to get all projects
						Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
						// create new array list of type String to add projectnames
						List<String> projectnamelist = new ArrayList<String>();
						projectnamelist.add("No Project");
						for(Object pn : projectnames)
						{	
							Object[] p = (Object[]) pn;
							projectnamelist.add((String) p[1]); //p[0] is project code & p[1] is projectname
						}	
						
						LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
						View layout = inflater.inflate(R.layout.ledger, (ViewGroup) findViewById(R.id.layout_root));
						//Building DatepPcker dialog
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setView(layout);
						builder.setTitle("Ledger");
						
						//populate all account names in accountname dropdown(spinner)
						final Spinner accountNames = (Spinner)layout.findViewById(R.id.sAccountNameinLedger);
						ArrayAdapter<String> da = new ArrayAdapter<String>(reportMenu.this, 
													android.R.layout.simple_spinner_item,accountnamelist);
				  	   	da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				  	   	accountNames.setAdapter(da);
				  	   	
				  	   	
				  	   	//populate all project names in project dropdown(spinner)
						final Spinner projectNames = (Spinner)layout.findViewById(R.id.sLedgerProject);
						ArrayAdapter<String> da1 = new ArrayAdapter<String>(reportMenu.this, 
													android.R.layout.simple_spinner_item,projectnamelist);
				  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				  	   	projectNames.setAdapter(da1);
				  	   	
				  	    tvLedgerWarning = (TextView)layout.findViewById(R.id.tvLedgerWarning);
					   	
				  	   	final CheckBox cbNarration = (CheckBox)layout.findViewById(R.id.cbNarrations);
					   	
				  	   	from_btn_ID = (Button)layout.findViewById(R.id.btnsetLedgerFromdate);
				  	   	from_btn_ID.setText(fromday+"-"+frommonth+"-"+fromyear);
				  	   	from_btn_ID.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								showDialog(FROM_DATE_DIALOG_ID);
							}
						});
				  	   	to_btn_ID= (Button)layout.findViewById(R.id.btnsetLedgerTodate);
				  	   	to_btn_ID.setText(today+"-"+tomonth+"-"+toyear);
				  	   	to_btn_ID.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								showDialog(TO_DATE_DIALOG_ID);
							}
						});
					   	
					   	Button btnView = (Button)layout.findViewById(R.id.btnView);
					   	Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
					   	btnView.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								selectedAccount = accountNames.getSelectedItem().toString();
								selectedProject = projectNames.getSelectedItem().toString();
								cheched = cbNarration.isChecked();
								validateDateFlag = m.validateDate(financialFromDate, financialToDate, from_btn_ID.getText().toString(), 
										to_btn_ID.getText().toString(), "validatebothFromToDate",tvLedgerWarning);
								givenfromDateString = m.givenfromDateString;
								givenToDateString = m.givenToDateString;
								
								if(validateDateFlag){
									Intent intent = new Intent(context, ledger.class);
									// To pass on the value to the next page
									startActivity(intent);
								}
								else{
									System.out.println("");
								}
								
							}
						});
					   	btnCancel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						dialog=builder.create();
						dialog.show();						
					}
					
					
				}
				//trial balance
				if(position == 1)
				{
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.trial_balance, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Trial Balance");	
					
					tvLedgerWarning = (TextView)layout.findViewById(R.id.tvTrialWarning);
					
					to_btn_ID= (Button)layout.findViewById(R.id.btnsetLedgerTodate);
			  	   	to_btn_ID.setText(today+"-"+tomonth+"-"+toyear);
			  	   	to_btn_ID.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							showDialog(TO_DATE_DIALOG_ID);
						}
					});
					
					final Spinner strialBalanceType = (Spinner)layout.findViewById(R.id.strialBalanceType);
					
					Button btnView = (Button)layout.findViewById(R.id.btnView);
				   	Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
				   	btnView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							trialbalancetype=strialBalanceType.getSelectedItem().toString();
							
							validateDateFlag = m.validateDate(financialFromDate, financialToDate, null, to_btn_ID.getText().toString(), null,tvLedgerWarning);
							givenToDateString = m.givenToDateString;
							
						   	if(validateDateFlag){
								Intent intent = new Intent(context, trialBalance.class);
								// To pass on the value to the next page
								startActivity(intent);
							}
						}
					});
				   	btnCancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
					});
					
					dialog=builder.create();
	        		dialog.show();
				}
				//project statement
				if(position == 2)
				{
					
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.project_statement, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Project Statement");
					
					tvLedgerWarning = (TextView)layout.findViewById(R.id.tvProjStateWarning);
				   	
					to_btn_ID= (Button)layout.findViewById(R.id.btnsetLedgerTodate);
			  	   	to_btn_ID.setText(today+"-"+tomonth+"-"+toyear);
			  	   	to_btn_ID.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							showDialog(TO_DATE_DIALOG_ID);
						}
					});
					
					//call the getAllProjects method to get all projects
					Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
					// create new array list of type String to add projectnames
					List<String> projectnamelist = new ArrayList<String>();
					projectnamelist.add("No Project");
					for(Object pn : projectnames)
					{	
						Object[] p = (Object[]) pn;
						projectnamelist.add((String) p[1]); //p[0] is project code & p[1] is projectname
					}	
					
					//populate all project names in project dropdown(spinner)
					final Spinner projectNames = (Spinner)layout.findViewById(R.id.sProject);
					ArrayAdapter<String> da1 = new ArrayAdapter<String>(reportMenu.this, 
												android.R.layout.simple_spinner_item,projectnamelist);
			  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			  	   	projectNames.setAdapter(da1);
			  	    Button btnView = (Button)layout.findViewById(R.id.btnView);
				   	Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
				   	btnView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							selectedProject = projectNames.getSelectedItem().toString();
							
							validateDateFlag = m.validateDate(financialFromDate, financialToDate, null, to_btn_ID.getText().toString(), null,tvLedgerWarning);
							givenToDateString = m.givenToDateString;
							
						   	if(validateDateFlag){
								Intent intent = new Intent(context, projectStatement.class);
								// To pass on the value to the next page
								startActivity(intent);
							}
							
						}
					});
				   	
				   	btnCancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
					});
				   	
					dialog=builder.create();
	        		dialog.show();
	        		
				}
				if(position == 3)
				{
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.cash_flow, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Cash Flow");
					
			        tvLedgerWarning = (TextView)layout.findViewById(R.id.tvCashFlowWarning);
				   	
			        from_btn_ID = (Button)layout.findViewById(R.id.btnsetLedgerFromdate);
			  	   	from_btn_ID.setText(fromday+"-"+frommonth+"-"+fromyear);
			  	   	from_btn_ID.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							showDialog(FROM_DATE_DIALOG_ID);
						}
					});
			  	   	to_btn_ID= (Button)layout.findViewById(R.id.btnsetLedgerTodate);
			  	   	to_btn_ID.setText(today+"-"+tomonth+"-"+toyear);
			  	   	to_btn_ID.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							showDialog(TO_DATE_DIALOG_ID);
						}
					});
					
				   	Button btnView = (Button)layout.findViewById(R.id.btnView);
				   	Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
				   	btnView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							validateDateFlag = m.validateDate(financialFromDate, financialToDate, from_btn_ID.getText().toString(), 
									to_btn_ID.getText().toString(), "validatebothFromToDate",tvLedgerWarning);
							givenfromDateString = m.givenfromDateString;
							givenToDateString = m.givenToDateString;
							
							if(validateDateFlag){
								Intent intent = new Intent(context, cashFlow.class);
								// To pass on the value to the next page
								startActivity(intent);
							}
						}
					});
				   	btnCancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
					});
					
					 dialog=builder.create();
					 dialog.show();
					
				}
				if(position == 4)
				{
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.balance_sheet, (ViewGroup) findViewById(R.id.layout_root));
					
					to_btn_ID= (Button)layout.findViewById(R.id.btnsetLedgerTodate);
			  	   	to_btn_ID.setText(today+"-"+tomonth+"-"+toyear);
			  	   	to_btn_ID.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							showDialog(TO_DATE_DIALOG_ID);
						}
					});
					
					tvLedgerWarning = (TextView)layout.findViewById(R.id.tvBalWarning);
				   	
					final Spinner sbalanceSheetType = (Spinner)layout.findViewById(R.id.sbalanceSheetType);
					
					TextView tvbalanceSheetType = (TextView)layout.findViewById(R.id.tvbalanceSheetType);
					
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Balance Sheet");
					
					Button btnView = (Button)layout.findViewById(R.id.btnView);
				   	Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
				   	
				   	btnView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							validateDateFlag = m.validateDate(financialFromDate, financialToDate, null, to_btn_ID.getText().toString(), null,tvLedgerWarning);
							givenToDateString = m.givenToDateString;
							
						   	if(validateDateFlag){
								balancetype=sbalanceSheetType.getSelectedItem().toString();
								Intent intent = new Intent(context, balanceSheet.class);
								// To pass on the value to the next page
								startActivity(intent);
						   	}
						}
					});
				   	btnCancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
					});
					
					dialog=builder.create();
	        		dialog.show();
	        		
				}
				if(position == 5)
				{
				    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.income_expenditure, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					if(orgtype.equalsIgnoreCase("NGO")){
						builder.setTitle("Income and Expenditure");
						reportTypeFlag = "Income and Expenditure";
					}
					else{
						builder.setTitle("Profit and Loss");
						reportTypeFlag = "Profit and Loss";
					}
					
					final TextView tvLedgerWarning = (TextView)layout.findViewById(R.id.tvIEWarning);
					
					to_btn_ID= (Button)layout.findViewById(R.id.btnsetLedgerTodate);
			  	   	to_btn_ID.setText(today+"-"+tomonth+"-"+toyear);
			  	   	to_btn_ID.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							showDialog(TO_DATE_DIALOG_ID);
						}
					});
			  	   	
					Button btnView = (Button)layout.findViewById(R.id.btnView);
				   	Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
				   	
				   	btnView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							validateDateFlag = m.validateDate(financialFromDate, financialToDate, null, to_btn_ID.getText().toString(), null,tvLedgerWarning);
							givenToDateString = m.givenToDateString;
							
						   	if(validateDateFlag){
								Intent intent = new Intent(context, incomeExpenditure.class);
								// To pass on the value to the next page
								startActivity(intent);
							}
							
						}
					});
				   	
				   	btnCancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							dialog.dismiss();
						}
					});
					
					dialog=builder.create();
	        		dialog.show();
	        	
				}
			} 
		});
	}  
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case FROM_DATE_DIALOG_ID:
			
				// set date picker as current date
				return new DatePickerDialog(this, fromdatePickerListener, 
						Integer.parseInt(fromyear), Integer.parseInt(frommonth)-1,Integer.parseInt(fromday));
		
		case TO_DATE_DIALOG_ID:
		
				// set date picker as current date
				return new DatePickerDialog(this, todatePickerListener, 
						Integer.parseInt(toyear), Integer.parseInt(tomonth)-1,Integer.parseInt(today));
		}
		return null;
	}
	private DatePickerDialog.OnDateSetListener fromdatePickerListener 
    	= new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
				int year = selectedYear; 
				int month = selectedMonth; 
				int day = selectedDay;
				from_btn_ID.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(String.format("%02d", day)).append("-").append(String.format("%02d", month+1)).append("-")
				.append(year));
				givenfromDateString = String.format("%02d", day)+"-"+String.format("%02d", month+1)+"-"+(year-1);
		}
	};  
	
	private DatePickerDialog.OnDateSetListener todatePickerListener 
	= new DatePickerDialog.OnDateSetListener() {

	// when dialog box is closed, below method will be called.
	public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
			int year = selectedYear; 
			int month = selectedMonth; 
			int day = selectedDay;
			to_btn_ID.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(String.format("%02d", day)).append("-").append(String.format("%02d", month+1)).append("-")
			.append(year));
			givenToDateString = String.format("%02d", day)+"-"+String.format("%02d", month+1)+"-"+(year-1);
	}
};  
	
	
	
}
