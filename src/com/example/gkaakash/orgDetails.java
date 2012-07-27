package com.example.gkaakash;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
 

public class orgDetails extends Activity {
	//Declaring variables 
	Button btnorgDetailSave, btnRegDate, btnFcraDate;
	int year, month, day;
	static final int REG_DATE_DIALOG_ID = 0;
	static final int FCRA_DATE_DIALOG_ID = 1;
	String getSelectedOrgType;
	TextView tvRegNum, tvRegDate, tvFcraNum, tvFcraDate, tvMVATnum, tvServiceTaxnum;
	EditText etRegNum, etFcraNum, etMVATnum, etServiceTaxnum;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling org_details.xml
		setContentView(R.layout.org_details);
		btnorgDetailSave = (Button) findViewById(R.id.btnOrgDetailSave);
		tvRegNum = (TextView) findViewById(R.id.tvRegNum);
		etRegNum = (EditText) findViewById(R.id.etRegNum);
		tvRegDate = (TextView) findViewById(R.id.tvRegDate);
		btnRegDate = (Button) findViewById(R.id.btnRegDate);
		tvFcraNum = (TextView) findViewById(R.id.tvFcraNum);
		etFcraNum = (EditText) findViewById(R.id.etFcraNum);
		tvFcraDate = (TextView) findViewById(R.id.tvFcraDate);
		btnFcraDate = (Button) findViewById(R.id.btnFcraDate);
		tvMVATnum = (TextView) findViewById(R.id.tvMVATnum);
		etMVATnum = (EditText) findViewById(R.id.etMVATnum);
		tvServiceTaxnum = (TextView) findViewById(R.id.tvServiceTaxnum);
		etServiceTaxnum = (EditText) findViewById(R.id.etServiceTaxnum);
		//Retrieving the organisation type flag value from the previous page(create organisation page)
		getSelectedOrgType = getIntent().getExtras().getString("flag");
		if("NGO".equals(getSelectedOrgType))
		{
			tvRegNum.setVisibility(TextView.VISIBLE);
			etRegNum.setVisibility(EditText.VISIBLE);
			tvRegDate.setVisibility(TextView.VISIBLE);
			btnRegDate.setVisibility(EditText.VISIBLE);
			tvFcraNum.setVisibility(TextView.VISIBLE);
			etFcraNum.setVisibility(EditText.VISIBLE);
			tvFcraDate.setVisibility(TextView.VISIBLE);
			btnFcraDate.setVisibility(EditText.VISIBLE);
			tvMVATnum.setVisibility(TextView.GONE);
			etMVATnum.setVisibility(EditText.GONE);
			tvServiceTaxnum.setVisibility(TextView.GONE);
			etServiceTaxnum.setVisibility(EditText.GONE);
		}
		else
		{
			tvRegNum.setVisibility(TextView.GONE);
			etRegNum.setVisibility(EditText.GONE);
			tvRegDate.setVisibility(TextView.GONE);
			btnRegDate.setVisibility(EditText.GONE);
			tvFcraNum.setVisibility(TextView.GONE);
			etFcraNum.setVisibility(EditText.GONE);
			tvFcraDate.setVisibility(TextView.GONE);
			btnFcraDate.setVisibility(EditText.GONE);
			tvMVATnum.setVisibility(TextView.VISIBLE);
			etMVATnum.setVisibility(EditText.VISIBLE);
			tvServiceTaxnum.setVisibility(TextView.VISIBLE);
			etServiceTaxnum.setVisibility(EditText.VISIBLE);
		}
		//Declaring new method for setting current date into "Registration Date"
		setCurrentDateOnButton();
		addListenerOnButton();
	}


	private void setCurrentDateOnButton() {
		//for creating calendar object and linking with its 'getInstance' method, for getting a default instance of this class for general use
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		//set current date to registration button
		btnRegDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(day).append("-").append(month + 1).append("-")
		.append(year).append(" "));
		
		//set current date to FCRA registration button
				btnFcraDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append("-").append(month + 1).append("-")
				.append(year).append(" "));
	}


	//Attach a listener to the click event for the button
	private void addListenerOnButton() {
		final Context context = this;
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button
		btnorgDetailSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(android.view.View v) {
				//To pass on the activity to the next page
				Intent intent = new Intent(context, preferences.class);
                startActivity(intent);   
			}
		});
		
		btnRegDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//for showing a date picker dialog that allows the user to select a date (Registration Date)
				showDialog(REG_DATE_DIALOG_ID);
			}
		});
		
		btnFcraDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//for showing a date picker dialog that allows the user to select a date (FCRA Registration Date)
				showDialog(FCRA_DATE_DIALOG_ID);
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case REG_DATE_DIALOG_ID:
			// set date picker as current date
			   return new DatePickerDialog(this, regdatePickerListener, 
	                         year, month,day);
		case FCRA_DATE_DIALOG_ID:
			// set date picker as current date
			   return new DatePickerDialog(this, fcradatePickerListener, 
	                         year, month,day);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener regdatePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

	// when dialog box is closed, below method will be called.
	public void onDateSet(DatePicker view, int selectedYear,
		int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			btnRegDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));
		}
	};
	
	private DatePickerDialog.OnDateSetListener fcradatePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

	// when dialog box is closed, below method will be called.
	public void onDateSet(DatePicker view, int selectedYear,
		int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			btnFcraDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year).append(" "));
		}
	};
 
}