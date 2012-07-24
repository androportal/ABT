package com.example.gkaakash;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
 

public class createOrg extends MainActivity{
	private TextView tvDisplayFromDate;
	private Button btnChangeFromDate;
	private TextView tvDisplayToDate;
	private Button btnChangeToDate;
	private int year;
	private int month;
	private int day;
	static final int FROM_DATE_DIALOG_ID = 999;
	static final int TO_DATE_DIALOG_ID = 998;
	Button btnNext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_org);
		setCurrentDateOnView();
		addListenerButton();
		addListeneronNextButton();
	}

	private void addListeneronNextButton() {
		// TODO Auto-generated method stub
		final Context context = this;
		 
		btnNext = (Button) findViewById(R.id.btnNext);
 
		btnNext.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, orgDetails.class);
                            startActivity(intent);   
 
			}
 
		});
 
	}

	private void addListenerButton() {
		// TODO Auto-generated method stub
		btnChangeFromDate = (Button) findViewById(R.id.btnChangeFromDate);
		btnChangeToDate = (Button) findViewById(R.id.btnChangeToDate);
		
		btnChangeFromDate.setOnClickListener(new OnClickListener() {
 
			public void onClick(View v) {
 
				showDialog(FROM_DATE_DIALOG_ID);
 
			}
 
		});
		btnChangeToDate.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View v) {
 
				showDialog(TO_DATE_DIALOG_ID);
 
			}
 
		});
	}

	private void setCurrentDateOnView() {
		// TODO Auto-generated method stub
		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		// set current date into "from date" textview
		tvDisplayFromDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));
		
		// set current date into "to date" textview
		tvDisplayToDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(day).append("-").append(month + 1).append("-")
		.append(year).append(" "));
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case FROM_DATE_DIALOG_ID:
			// set date picker as current date
			   return new DatePickerDialog(this, fromdatePickerListener, 
	                         year, month,day);
		case TO_DATE_DIALOG_ID:
			// set date picker as current date
			   return new DatePickerDialog(this, todatePickerListener, 
	                         year, month,day);
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
	
}
