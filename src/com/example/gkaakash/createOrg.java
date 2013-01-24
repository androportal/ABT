package com.example.gkaakash;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.gkaakash.controller.Startup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
 

public class createOrg extends MainActivity {
	//Declaring variables
	TextView tvDisplayFromDate, tvDisplayToDate;
	Button btnChangeFromDate, btnChangeToDate, btnNext;
	static int year, month, day, toYear, toMonth, toDay;
	static final int FROM_DATE_DIALOG_ID = 0;
	static final int TO_DATE_DIALOG_ID = 1;
	Spinner orgType; 
	String org;
	static String organisationName,orgTypeFlag,selectedOrgType,todate;
	static String fromdate;
	AlertDialog dialog;
	final Calendar c = Calendar.getInstance();
	final Context context = this;
	private EditText orgName;
	Object[] deployparams;
	DecimalFormat mFormat;
	private Object[] orgNameList;
	Object[] financialyearList;
	boolean orgExistFlag;
	static Integer client_id;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling create_org.xml
		setContentView(R.layout.create_org);
		client_id= Startup.getClient_id();
		
		
		MainActivity.no_dailog = true; //comment this line if running this app on emulator
		MainActivity.help_dialog.dismiss(); //comment this line if running this app on emulator
		
		//for two digit format date for dd and mm
		mFormat= new DecimalFormat("00");
		mFormat.setRoundingMode(RoundingMode.DOWN);
		
		//Declaring new method for setting date into "from date" and "to date" textview
		setDateOnLoad();
		/*
		 * creating a new interface for showing a date picker dialog that
		 * allows the user to select financial year start date and to date
		 */
		addListeneronDateButton();
		//creating interface to pass on the activity to next page
		addListeneronNextButton();
		orgType = (Spinner) findViewById(R.id.sOrgType);
		org  = (String) orgType.getSelectedItem();
		//creating interface to listen activity on Item 
		addListenerOnItem();
	}

	private void setDateOnLoad() {
		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
		
		/*
		 * set "from date" and "to date" textView
		 * for creating calendar object and linking with its 'getInstance' method, 
		 * for getting a default instance of this class for general use
		 */
		
		year = c.get(Calendar.YEAR);
		month = 3; //month = april
		day = 1;
		
		//set from date: day=01, month=April, year=current year
		tvDisplayFromDate.setText(new StringBuilder()
		.append(mFormat.format(Double.valueOf(1))).append("-")
		.append(mFormat.format(Double.valueOf(4))).append("-")
		.append(year));
		
		//Add one year to current date time
		c.add(Calendar.YEAR,1);
		toYear = c.get(Calendar.YEAR);
		toMonth = 2;
		toDay = 31;
		
		//set to date: day=31, month=March, year=current year+1
		tvDisplayToDate.setText(new StringBuilder()
		.append(mFormat.format(Double.valueOf(31))).append("-")
		.append(mFormat.format(Double.valueOf(3))).append("-")
		.append(toYear));
		
		
	}

	private void addListeneronDateButton() {
		btnChangeFromDate = (Button) findViewById(R.id.btnChangeFromDate);
		btnChangeToDate = (Button) findViewById(R.id.btnChangeToDate);


		/*
		 * when button is clicked, user can select from date(day, month and year) from datepicker,
		 * selected date will set in 'from date' textview and set date in 'to date' text view
		 * which is greater than from date by one year and minus one day(standard financial year format)
		 * 
		 */
		btnChangeFromDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Preparing views
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.datepiker, (ViewGroup) findViewById(R.id.layout_root));
				//Building DatepPcker dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
	            builder.setTitle("Set from date");
	            final DatePicker dp = (DatePicker)layout.findViewById(R.id.datePicker1);
	            dp.init(year,month,day, null);
	            
	            builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					 year = dp.getYear();
					 month = dp.getMonth();
					 day =  dp.getDayOfMonth();
					 String strDateTime = mFormat.format(Double.valueOf(day)) + "-" 
					 + (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))) + "-" 
					 + year;
					 //set date in from date textview
					 tvDisplayFromDate.setText(strDateTime);
					 
					 //setting selected date into calender's object
					 c.set(year, month, day);
					 //add one year
					 c.add(Calendar.YEAR, +1);
					 //subtracting one day
					 c.add(Calendar.DAY_OF_MONTH, -1);
					 
					 toYear = c.get(Calendar.YEAR);
					 toMonth = c.get(Calendar.MONTH);
					 toDay = c.get(Calendar.DAY_OF_MONTH);
					 
					//set date in to date textview
					 tvDisplayToDate.setText(new StringBuilder()
					 .append(mFormat.format(Double.valueOf(toDay)))
					 .append("-").append(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(toMonth+1)))))))
					 .append("-").append(toYear));
				}
				}); 
                dialog=builder.create();
        		dialog.show();
			}	
		});
		
		/*
		 * when button clicked, user can change the 'to date' from datepicker,
		 * it will set the selected date in 'to date' textview, if to date is greater than from date
		 */
		btnChangeToDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Preparing views
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.datepiker, (ViewGroup) findViewById(R.id.layout_root));
				//Building DatepPicker dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
	            builder.setTitle("Set to date");
	            
	            final   DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker1);
	            dp.init(toYear,toMonth,toDay, null);
	            
	            builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					 
					int Year = dp.getYear();
					int Month = dp.getMonth();
					int Day =  dp.getDayOfMonth();
					 
					 try {
						 	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							Date date1 = sdf.parse(day+"-"+month+"-"+year); //from date
					    	Date date2 = sdf.parse(Day+"-"+Month+"-"+Year); //to date
							
					    	Calendar cal1 = Calendar.getInstance(); 
					    	Calendar cal2 = Calendar.getInstance(); 
					    	
					    	cal1.setTime(date1);
					    	cal2.setTime(date2);
					    	
					    	if(cal2.after(cal1)){
					    		toYear = Year;
								toMonth = Month;
								toDay =  Day;
								String strDateTime = mFormat.format(Double.valueOf(toDay)) + "-" 
								 + (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(toMonth))))+ 1))) + "-" 
								 + toYear;
					    		tvDisplayToDate.setText(strDateTime);
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
                dialog=builder.create();
        		dialog.show();
			}	
		});
		
	}
	// method to take ItemSelectedListner interface as a argument  
	void addListenerOnItem(){
		//Attach a listener to the Organisation Type Spinner
		orgType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
				//Retrieving the selected org type from the Spinner and assigning it to a variable 
				selectedOrgType = parent.getItemAtPosition(position).toString();
				orgTypeFlag = selectedOrgType;
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
				}

		});// End of orgType.setOnItemSelectedListener
	
	}// End of addListenerOnItem()
	private void addListeneronNextButton() {
		final Context context = this;
		//Request a reference to the button from the activity by calling “findViewById” 
		//and assign the retrieved button to an instance variable
		btnNext = (Button) findViewById(R.id.btnNext);
		orgType = (Spinner) findViewById(R.id.sOrgType);
		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
		orgName = (EditText) findViewById(R.id.etOrgName);
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button "Next"
		btnNext.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				
				organisationName = orgName.getText().toString();
				fromdate = tvDisplayFromDate.getText().toString();
				todate = tvDisplayToDate.getText().toString();
				try{
				// call the getOrganisationName method from startup
		    	orgNameList = startup.getOrgnisationName(); // return lists of existing organisations
				
				for(Object org : orgNameList){
					if(organisationName.equals(org)){
						orgExistFlag = false;
						
						//call getFinancialYear method from startup.java 
				    	//it will give you financialYear list according to orgname
				    	financialyearList = startup.getFinancialYear(organisationName);
				    	
				    	for(Object fy : financialyearList)
				    	{
				    		Object[] y = (Object[]) fy;
				    		// concatination From and To date 
				    		String fromDate=y[0].toString();
				    		String toDate=y[1].toString();
				    		
				    		if(fromDate.equals(fromdate) && toDate.equals(todate)){
				    			orgExistFlag = true;
				    			break;
				    		}
				    		
				    	}
					}
				}
		    	
				if("".equals(organisationName)){
					String message = "Please enter the organisation name";
					toastValidationMessage(message);
				}
				else if(orgExistFlag == true){
					String message = "Organisation name "+organisationName+" with this financial year exist";
					toastValidationMessage(message);
					orgExistFlag = false;
					}
				else{
					//To pass on the activity to the next page
					MainActivity.editDetails=false;
					Intent intent = new Intent(context, orgDetails.class);
				    startActivity(intent); 
				}
			}catch(Exception e)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Please check server connection")
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int id) {
                                    	Intent intent = new Intent(context, MainActivity.class);
                    				    startActivity(intent); 
                                    }
                                });
                       
                AlertDialog alert = builder.create();
                alert.show();    
			}
			}
		}); //End of btnNext.setOnClickListener
 
	}// End of addListeneronNextButton()
	
	public void onBackPressed() {
		 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 startActivity(intent);
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
	
	/*
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) 
	    {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
			.setCancelable(false)
			.setPositiveButton("Yes",
			new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Object[] params = new Object[]{client_id}; // parameters pass to core_engine xml_rpc functions
				//call method closeConnection from startup.java 
				startup.closeConnection(params);
		        
			}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
			});
			AlertDialog alert = builder.create();
			alert.show();
			return true;
	    	
	    }
	    return super.onKeyLongPress(keyCode, event);
	}*/

}// End of Class
