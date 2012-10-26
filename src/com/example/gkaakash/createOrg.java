package com.example.gkaakash;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.Toast;
 

public class createOrg extends MainActivity {
	//Declaring variables
	TextView tvDisplayFromDate, tvDisplayToDate;
	Button btnChangeFromDate, btnChangeToDate, btnNext;
	int year, month, day, toYear, toMonth, toDay;
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
	private static Object[] orgNameList;
	Object[] financialyearList;
	boolean orgExistFlag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling create_org.xml
		setContentView(R.layout.create_org);		
		//Declaring new method for setting date into "from date" and "to date" textview
		setCurrentDateOnView();
		//creating a new interface for showing a date picker dialog that allows the user to select financial year start date and to date
		addListeneronDateButton();
		//creating interface to pass on the activity to next page
		addListeneronNextButton();
		orgType = (Spinner) findViewById(R.id.sOrgType);
		org  = (String) orgType.getSelectedItem();
		//creating interface to listen activity on Item 
		addListenerOnItem();
	}

	private void setCurrentDateOnView() {
		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
		
		//for creating calendar object and linking with its 'getInstance' method, for getting a default instance of this class for general use
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		//System.out.println("date is");
		//System.out.println(year+"-"+month+"-"+day);
		
		//for two digit format date for dd and mm
		mFormat= new DecimalFormat("00");
		mFormat.setRoundingMode(RoundingMode.DOWN);
		
		// set current date into "from date" textView
		tvDisplayFromDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(mFormat.format(Double.valueOf(day))).append("-")
		.append(mFormat.format(Double.valueOf(month+1))).append("-")
		.append(year));
		
		//Add one year to current date time
		c.add(Calendar.YEAR,1);
		//one day before
		c.add(Calendar.DAY_OF_MONTH, -1);
		toDay = c.get(Calendar.DAY_OF_MONTH);
		toMonth = c.get(Calendar.MONTH);
		toYear = c.get(Calendar.YEAR);
		
		
		tvDisplayToDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(mFormat.format(Double.valueOf(toDay))).append("-")
		.append(mFormat.format(Double.valueOf(toMonth+1))).append("-")
		.append(toYear));
		
		
	}

	private void addListeneronDateButton() {
		btnChangeFromDate = (Button) findViewById(R.id.btnChangeFromDate);
		btnChangeFromDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//Preparing views
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.datepiker, (ViewGroup) findViewById(R.id.layout_root));
				//Building DatepPcker dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
	            builder.setTitle("Set Date");
	            builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					 final   DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker1);
					 
					 int y = dp.getYear();
					 int m = dp.getMonth();
					 int d =  dp.getDayOfMonth();
					 String strDateTime = mFormat.format(Double.valueOf(d)) + "-" 
					 + (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(m))))+ 1))) + "-" 
					 + y;
					 
					 //setting selected date into calender's object
					 c.set(y, m, d);
					 //subtracting one day
					 c.add(Calendar.DAY_OF_MONTH, -1);
					 
					 int mYear = c.get(Calendar.YEAR);
					 int mMonth = c.get(Calendar.MONTH);
					 int mDay = c.get(Calendar.DAY_OF_MONTH);
					 tvDisplayFromDate.setText(strDateTime);
					 
					 tvDisplayToDate.setText(new StringBuilder()
					 .append(mFormat.format(Double.valueOf(mDay)))
					 .append("-").append(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(mMonth))))+ 1)))
					 .append("-").append(mYear + 1));
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
					Toast.makeText(context, "please enter the organisation name", Toast.LENGTH_SHORT).show();
				}
				else if(orgExistFlag == true){
					Toast.makeText(context, "organisation name "+organisationName+" with this financial year exist", Toast.LENGTH_SHORT).show();
				}
				else{
					//To pass on the activity to the next page
					Intent intent = new Intent(context, orgDetails.class);
				    startActivity(intent); 
				}
			}
 
		}); //End of btnNext.setOnClickListener
 
	}// End of addListeneronNextButton()
	
	public void onBackPressed() {
		 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 startActivity(intent);
	}

}// End of Class
