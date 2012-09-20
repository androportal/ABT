package com.example.gkaakash;

import java.util.Calendar;

import org.xmlrpc.android.XMLRPCException;

import com.gkaakash.controller.Startup;
import com.gkaakash.coreconnection.CoreConnection;

import android.annotation.SuppressLint;
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
 

public class createOrg extends MainActivity implements OnItemSelectedListener {
	//Declaring variables
	TextView tvDisplayFromDate, tvDisplayToDate;
	Button btnChangeFromDate, btnChangeToDate, btnNext;
	int year, month, day, toYear, toMonth, toDay;
	static final int FROM_DATE_DIALOG_ID = 0;
	static final int TO_DATE_DIALOG_ID = 1;
	Spinner orgType;
	private String organisationName,orgTypeFlag,selectedOrgType,fromdate,todate;
	AlertDialog dialog;
	final Calendar c = Calendar.getInstance();
	final Context context = this;
	private EditText orgName;
	private Startup startup;
	Object[] deployparams;
	Integer client_id ;
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
		//Attach a listener to the Organization Type Spinner
		orgType.setOnItemSelectedListener((OnItemSelectedListener) this);
	}

	private void setCurrentDateOnView() {
		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
		
		//for creating calendar object and linking with its 'getInstance' method, for getting a default instance of this class for general use
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		// set current date into "from date" textView
		tvDisplayFromDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(year).append("-").append(month).append("-")
			.append(day).append(" "));
		
		//Add one year to current date time
		c.add(Calendar.YEAR,1);
		//one day before
		c.add(Calendar.DAY_OF_MONTH, -1);
		toDay = c.get(Calendar.DAY_OF_MONTH);
		toMonth = c.get(Calendar.MONTH);
		toYear = c.get(Calendar.YEAR);
		tvDisplayToDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(toYear).append("-").append(toMonth).append("-")
		.append(toDay).append(" "));
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
					 String strDateTime = y + "-" + (m + 1) + "-" + d;
					 
					 //setting selected date into calender's object
					 c.set(y, m, d);
					 //subtracting one day
					 c.add(Calendar.DAY_OF_MONTH, -1);
					 
					 int mYear = c.get(Calendar.YEAR);
					 int mMonth = c.get(Calendar.MONTH);
					 int mDay = c.get(Calendar.DAY_OF_MONTH);
					 tvDisplayFromDate.setText(strDateTime);
					 tvDisplayToDate.setText(new StringBuilder()
					 .append((mYear + 1)).append("-").append((mMonth)+ 1).append("-").append((mDay)));
				}
				});
                dialog=builder.create();
        		dialog.show();
			}	
		});
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			   long id){
		//Retrieving the selected org type from the Spinner and assigning it to a variable 
		selectedOrgType = parent.getItemAtPosition(position).toString();
		orgTypeFlag = selectedOrgType;
		System.out.println(selectedOrgType);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Ignore this method!!!
	}

	private void addListeneronNextButton() {
		final Context context = this;
		//Request a reference to the button from the activity by calling “findViewById” and assign the retrieved button to an instance variable
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
				// list of input parameters type of Object 
				deployparams = new Object[]{organisationName,fromdate,todate,selectedOrgType}; // parameters pass to core_engine xml_rpc functions
				//call method deploy from startup.java 
				client_id = Startup.deploy(deployparams);
				//To pass on the activity to the next page
			    Intent intent = new Intent(context, orgDetails.class);
			    //To pass on the value to the next page
			    intent.putExtra("flag",orgTypeFlag);
			    startActivity(intent);   
			}
 
		});
 
	}
}
