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

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class reportMenu extends ListActivity{
	//adding report list items
	String[] reportType = new String[] { "Ledger","Trial balance","Project statement",
			"Cash flow","Balance sheet","Profit and loss account" };
	final Context context = this;
	AlertDialog dialog;
	DecimalFormat mFormat;
	static String trialToDateString;
	static String LedgerFromDateString;
	static String LedgerToDateString;
	static Integer client_id;
	private Account account;
	private Organisation organisation;
	static String selectedAccount;
	static String selectedProject;
	String day, month, year, day1, month1, year1; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		account = new Account();
		organisation = new Organisation();
		
       	client_id= Startup.getClient_id();
       	
       	final String financialFromDate =Startup.getfinancialFromDate();  	   	
	   	String dateParts[] = financialFromDate.split("-");
	   	day  = dateParts[0];
	   	month = dateParts[1];
	   	year = dateParts[2];
	   	
	   	final String financialToDate = Startup.getFinancialToDate();
	   	String dateParts1[] = financialToDate.split("-");
	   	day1  = dateParts1[0];
	   	month1 = dateParts1[1];
	   	year1 = dateParts1[2];
	   	
		
		//calling report.xml page
		setListAdapter(new ArrayAdapter<String>(this, R.layout.report,reportType));
		
		//getting the list view and setting background
		final ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setBackgroundColor(R.drawable.dark_gray_background);
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
					accountnamelist.add("Please select account name");
					for(Object an : accountnames)
					{	
						accountnamelist.add((String) an); 
					}	

					
					if(accountnamelist.size() <= 1){
						String message = "Ledger can not be displayed, Please create account!";
						toastValidationMessage(message);
						}
					else{
						//call the getAllProjects method to get all projects
						Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
						// create new array list of type String to add gropunames
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
				  	   	
				  	   	final String financialFromDate =Startup.getfinancialFromDate();  	   	
					   	String dateParts[] = financialFromDate.split("-");
					   	String setfromday  = dateParts[0];
					   	String setfrommonth = dateParts[1];
					   	String setfromyear = dateParts[2];
					   	
					   	final String financialToDate = Startup.getFinancialToDate();
					   	String dateParts1[] = financialToDate.split("-");
					   	String settoday  = dateParts1[0];
					   	String settomonth = dateParts1[1];
					   	String settoyear = dateParts1[2];
					   	
				  	   	CheckBox cbNarration = (CheckBox)layout.findViewById(R.id.cbNarration);
				  	   	cbNarration.setVisibility(CheckBox.GONE);
				  	   	TextView tvcbNarration= (TextView)layout.findViewById(R.id.tvcbNarration);
				  	   	tvcbNarration.setVisibility(TextView.GONE);
					   	
					   	DatePicker LedgerFrom = (DatePicker) layout.findViewById(R.id.dpsetLedgerFromdate);
					   	LedgerFrom.init(Integer.parseInt(setfromyear),(Integer.parseInt(setfrommonth)-1),Integer.parseInt(setfromday), null);
					   	
					   	DatePicker LedgerT0 = (DatePicker) layout.findViewById(R.id.dpsetLedgerT0date);
					   	LedgerT0.init(Integer.parseInt(settoyear),(Integer.parseInt(settomonth)-1),Integer.parseInt(settoday), null);
					   	
						builder.setPositiveButton("View",new  DialogInterface.OnClickListener(){

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								final   DatePicker LedgerFromdate = (DatePicker) dialog.findViewById(R.id.dpsetLedgerFromdate);
							   	int LedgerFromDay = LedgerFromdate.getDayOfMonth();
							   	int LedgerFromMonth = LedgerFromdate.getMonth();
							   	int LedgerFromYear = LedgerFromdate.getYear();
							   	
							   	LedgerFromDateString = mFormat.format(Double.valueOf(LedgerFromDay))+ "-" 
							   	+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(LedgerFromMonth))))+ 1))) + "-" 
							   	+ LedgerFromYear;
							   	
							   	final   DatePicker LedgerT0date = (DatePicker) dialog.findViewById(R.id.dpsetLedgerT0date);
							   	int LedgerT0Day = LedgerT0date.getDayOfMonth();
							   	int LedgerT0Month = LedgerT0date.getMonth();
							   	int LedgerT0Year = LedgerT0date.getYear();
							   	
							   	LedgerToDateString = mFormat.format(Double.valueOf(LedgerT0Day))+ "-" 
							   	+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(LedgerT0Month))))+ 1))) + "-" 
							   	+ LedgerT0Year;
							   	
							   	selectedAccount = accountNames.getSelectedItem().toString();
				        		selectedProject = projectNames.getSelectedItem().toString();
				        		
							   	
							   	try {
							   		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						        	Date date1 = sdf.parse(financialFromDate);
						        	Date date2 = sdf.parse(financialToDate);
						        	Date date3 = sdf.parse(LedgerFromDateString);
						        	Date date4 = sdf.parse(LedgerToDateString);
						        	
						        	System.out.println("all dates are...........");
						        	System.out.println(financialFromDate+"---"+financialToDate+"---"+LedgerFromDateString+"---"+LedgerToDateString);
						        	Calendar cal1 = Calendar.getInstance(); //financial from date
						        	Calendar cal2 = Calendar.getInstance(); //financial to date
						        	Calendar cal3 = Calendar.getInstance(); //from date
						        	Calendar cal4 = Calendar.getInstance(); //to date
						        	cal1.setTime(date1);
						        	cal2.setTime(date2);
						        	cal3.setTime(date3);
						        	cal4.setTime(date4);  
						        	
						        	
						        	if(selectedAccount.equalsIgnoreCase("Please select account name")){
						        		String message = "Please select account name";
						        		toastValidationMessage(message);
						        	}
						        	else if(((cal3.after(cal1)&&(cal3.before(cal2))) || (cal3.equals(cal1) || (cal3.equals(cal2)))) 
						        			&& ((cal4.after(cal1) && (cal4.before(cal2))) || (cal4.equals(cal2)) || (cal4.equals(cal1)))){
						        		
						        		
										
										Intent intent = new Intent(context, ledger.class);
										// To pass on the value to the next page
										startActivity(intent);
						        	}
						        	else{
						        		String message = "Please enter proper date";
						        		toastValidationMessage(message);
						        	}
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
						});
						
						builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
							
							}	
						});
						dialog=builder.create();
						dialog.show();
						WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
						//customizing the width and location of the dialog on screen 
						lp.copyFrom(dialog.getWindow().getAttributes());
						lp.width = 700;
						dialog.getWindow().setAttributes(lp);
						
					}
					
					
				}
				if(position == 1)
				{
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.trial_balance, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Trial balance");	
					
					final String financialToDate = Startup.getFinancialToDate();
				   	String dateParts1[] = financialToDate.split("-");
				   	String setday  = dateParts1[0];
				   	String setmonth = dateParts1[1];
				   	String setyear = dateParts1[2];
					
				   	System.out.println(setday+""+setmonth+""+setyear);
				   	
					DatePicker trialFrom = (DatePicker) layout.findViewById(R.id.dpTrialsetT0date);
					trialFrom.init(Integer.parseInt(setyear),(Integer.parseInt(setmonth)-1),Integer.parseInt(setday), null);
					
					Spinner strialBalanceType = (Spinner)layout.findViewById(R.id.strialBalanceType);
					strialBalanceType.setVisibility(Spinner.GONE);
					
					TextView tvtrialBalanceType = (TextView)layout.findViewById(R.id.tvtrialBalanceType);
					tvtrialBalanceType.setVisibility(TextView.GONE);
					
					builder.setPositiveButton("View",new  DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							
							
							
							final   DatePicker trialToDate = (DatePicker) dialog.findViewById(R.id.dpTrialsetT0date);
						   	int trialDay = trialToDate.getDayOfMonth();
						   	int trialMonth = trialToDate.getMonth();
						   	int trialYear = trialToDate.getYear();
						   	
						   	trialToDateString = mFormat.format(Double.valueOf(trialDay))+ "-" 
						   	+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(trialMonth))))+ 1))) + "-" 
						   	+ trialYear;
						   	
						   	/*
						   	 * compare financial from date and trial to date
						   	 * trial to date should be greater than equal to financial from date
						   	 */
						   	
						   	try {
						   		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					        	Date date1 = sdf.parse(financialFromDate);
					        	Date date2 = sdf.parse(financialToDate);
					        	Date date3 = sdf.parse(trialToDateString);
					        	System.out.println(financialFromDate);
					        	
					        	Calendar cal1 = Calendar.getInstance();
					        	Calendar cal2 = Calendar.getInstance();
					        	Calendar cal3 = Calendar.getInstance();
					        	cal1.setTime(date1);
					        	cal2.setTime(date2);
					        	cal3.setTime(date3);
					        	
					        	if((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal1) || cal3.equals(cal2) ){
									
									Intent intent = new Intent(context, trialBalance.class);
									// To pass on the value to the next page
									startActivity(intent);
					        	}
					        	else{
					        		String message = "Please enter proper date";
					        		toastValidationMessage(message);
					        	}
							} catch (Exception e) {
								// TODO: handle exception
							}
						   	
							
							
						}
						
					});
					builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					dialog=builder.create();
	        		dialog.show();
				}
				if(position == 2)
				{
					String message = "This functionality is not yet implemented";
	        		toastValidationMessage(message);
					/*
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.project_statement, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Project statement");
					builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					
					builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					dialog=builder.create();
	        		dialog.show();
	        		*/
				}
				if(position == 3)
				{
					String message = "This functionality is not yet implemented";
	        		toastValidationMessage(message);
	        		/*
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.cash_flow, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Cash flow");
					
					 builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
						 
					 });
					 builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
							 
						 });
					 dialog=builder.create();
						dialog.show();
						WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
						//customizing the width and location of the dialog on screen 
						lp.copyFrom(dialog.getWindow().getAttributes());
						lp.width = 600;
						dialog.getWindow().setAttributes(lp);
					*/
				}
				if(position == 4)
				{
					String message = "This functionality is not yet implemented";
	        		toastValidationMessage(message);
	        		/*
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.balance_sheet, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Balance sheet");
					builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
						
						}
						
					});
					
					builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
						
					});
					dialog=builder.create();
	        		dialog.show();
	        		*/
				}
				if(position == 5)
				{
					String message = "This functionality is not yet implemented";
	        		toastValidationMessage(message);
	        		/*
				    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.income_expenditure, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					builder.setTitle("Profit and loss account");
					builder.setPositiveButton("Set",new  DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
						 
					 });
					 builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								
							}
							 
						 });
					dialog=builder.create();
	        		dialog.show();
	        	*/
				}
			} 
		});
	}
	
	public void toastValidationMessage(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
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
