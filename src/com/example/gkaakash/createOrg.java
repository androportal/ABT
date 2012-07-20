package com.example.gkaakash;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
 

public class createOrg extends MainActivity{
	private TextView tvDisplayDate;
	private Button btnChangeDate;
	private TextView tvDisplayToDate;
	private Button btnChangeToDate;
	private int year;
	private int month;
	private int day;
	static final int FROM_DATE_DIALOG_ID = 999;
	static final int TO_DATE_DIALOG_ID = 999;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_org);
		setCurrentDateOnView();
		addListenerButton();
	}

	private void addListenerButton() {
		// TODO Auto-generated method stub
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
		btnChangeToDate = (Button) findViewById(R.id.btnChangeToDate);
		
		btnChangeDate.setOnClickListener(new OnClickListener() {
 
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
		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
 
		// set current date into "from date" textview
		tvDisplayDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(month + 1).append("-").append(day).append("-")
			.append(year).append(" "));
		
		// set current date into "to date" textview
		tvDisplayToDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(month + 1).append("-").append(day).append("-")
		.append(year).append(" "));
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case FROM_DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
                = new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
 
			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
			   .append("-").append(day).append("-").append(year)
			   .append(" "));
 
			
 
		}
	};
	
	
	
}
