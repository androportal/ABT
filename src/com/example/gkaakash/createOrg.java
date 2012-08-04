package com.example.gkaakash;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
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
	String orgTypeFlag;
	final Calendar c = Calendar.getInstance();
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
		//Attach a listener to the Organisation Type Spinner
		orgType.setOnItemSelectedListener((OnItemSelectedListener) this);
	}

	private void setCurrentDateOnView() {
		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
		
		//for creating calendar object and linking with its 'getInstance' method, for getting a default instance of this class for general use
		
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		// set current date into "from date" textview
		tvDisplayFromDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));
		
		//Add one year to current date time
		c.add(Calendar.YEAR,1);
		//one day before
		c.add(Calendar.DAY_OF_MONTH, -1);
		toDay = c.get(Calendar.DAY_OF_MONTH);
		toMonth = c.get(Calendar.MONTH);
		toYear = c.get(Calendar.YEAR);
		tvDisplayToDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(toDay).append("-").append(toMonth + 1).append("-")
		.append(toYear).append(" "));
	}

	private void addListeneronDateButton() {
		btnChangeFromDate = (Button) findViewById(R.id.btnChangeFromDate);
		btnChangeToDate = (Button) findViewById(R.id.btnChangeToDate);
		
		btnChangeFromDate.setOnClickListener(new OnClickListener() {
 
			public void onClick(View v) {
				//for showing a date picker dialog that allows the user to select a date (from date or financial yr start)
				showDialog(FROM_DATE_DIALOG_ID);
 
			}
 
		});
		btnChangeToDate.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View v) {
				////for showing a date picker dialog that allows the user to select a date (to date or financial yr to)
				showDialog(TO_DATE_DIALOG_ID);
 
			}
 
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case FROM_DATE_DIALOG_ID:
			// set 'from date' date picker as current date
			   return new DatePickerDialog(this, fromdatePickerListener, 
	                         year, month,day);
		case TO_DATE_DIALOG_ID:
			//add one year to current date in 'to date' date picker
			   return new DatePickerDialog(this, todatePickerListener, 
	                         toYear, toMonth,toDay);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener fromdatePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			tvDisplayFromDate.setText(new StringBuilder().append(day).append("-").append(month + 1)
					   .append("-").append(year)
			   .append(" "));
			
		}
	};
	
	private DatePickerDialog.OnDateSetListener todatePickerListener
    = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
		year = selectedYear;
		month = selectedMonth;
		day = selectedDay;
		
		// set selected date into textview
		tvDisplayToDate.setText(new StringBuilder().append(day).append("-").append(month + 1)
				   .append("-").append(year)
		   .append(" "));
		}
	};

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			   long id){
		//Retrieving the selected org type from the Spinner and assigning it to a variable 
		String selectedOrgType = parent.getItemAtPosition(position).toString();
		orgTypeFlag = selectedOrgType;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Ignore this method!!!
		
	}
	
	private void addListeneronNextButton() {
		final Context context = this;
		//Request a reference to the button from the activity by calling “findViewById” and assign the retrieved button to an instance variable
		btnNext = (Button) findViewById(R.id.btnNext);
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button "Next"
		btnNext.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				//To pass on the activity to the next page
			    Intent intent = new Intent(context, orgDetails.class);
			  //To pass on the value to the next page
			    intent.putExtra("flag",orgTypeFlag);
			    startActivity(intent);   
			}
 
		});
 
	}
}
