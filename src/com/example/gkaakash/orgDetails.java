package com.example.gkaakash;

import java.util.ArrayList;
import java.util.Calendar;

import com.gkaakash.controller.Startup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
 

public class orgDetails extends Activity implements OnItemSelectedListener{
	//Declaring variables 
	Button btnorgDetailSave, btnRegDate, btnFcraDate,btnSkip;
	int year, month, day;
	static final int REG_DATE_DIALOG_ID = 0;
	static final int FCRA_DATE_DIALOG_ID = 1;
	String getSelectedOrgType,getToDate,getOrgName, getFromDate;
	TextView tvRegNum, tvRegDate, tvFcraNum, tvFcraDate, tvMVATnum, tvServiceTaxnum;
	EditText etRegNum, etFcraNum, etMVATnum, etServiceTaxnum;
	
	private int group1Id = 1;
	int Edit = Menu.FIRST;
	int Delete = Menu.FIRST +1;
	int Finish = Menu.FIRST +2;
	AlertDialog dialog;
	final Context context = this;
	
	
	Spinner getcountry ;
	Spinner getcity;
	private Startup startup;
	private Integer client_id;
	private Object[] deployparams;
	protected ProgressDialog progressBar;

	
	//adding options to the options menu
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(group1Id, Edit, Edit, "Edit");
    menu.add(group1Id, Delete, Delete, "Delete");
    menu.add(group1Id, Finish, Finish, "Finish");
    return super.onCreateOptionsMenu(menu); 
    }
	
	//code for the actions to be performed on clicking options menu goes here ...
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case 1:
	    Toast msg = Toast.makeText(orgDetails.this, "Menu 1", Toast.LENGTH_LONG);
	    msg.show();
	    return true;
	    }
	    return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling org_details.xml
		setContentView(R.layout.org_details);
		startup = new Startup();
		btnorgDetailSave = (Button) findViewById(R.id.btnOrgDetailSave);
		getcountry = (Spinner) findViewById(R.id.sGetStates);
		getcity = (Spinner) findViewById(R.id.sGetCity);
		btnSkip = (Button) findViewById(R.id.btnSkip);
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
		getSelectedOrgType = getIntent().getExtras().getString("orgtypeflag");
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
		getCountry();
		getcountry.setOnItemSelectedListener((OnItemSelectedListener) this);
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
		//Retrieving the organisation type flag value from the previous page(create organisation page)
		getOrgName = getIntent().getExtras().getString("orgnameflag");
		getFromDate = getIntent().getExtras().getString("fdateflag");
		getToDate = getIntent().getExtras().getString("tdateflag");
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button
		btnSkip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(android.view.View v) {
				//progress bar moving image to show wait state
				progressBar = new ProgressDialog(context);
                progressBar.setCancelable(false);
                progressBar.setMessage("Please Wait, Saving Organisation Details ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(1000);
                progressBar.show();
				
                //To pass on the activity to the next page   
				Intent intent = new Intent(context, preferences.class);
                startActivity(intent); 
                
				//list of input parameters type of Object 
				deployparams = new Object[]{getOrgName,getFromDate,getToDate,getSelectedOrgType}; // parameters pass to core_engine xml_rpc functions
				//call method deploy from startup.java 
				client_id = Startup.deploy(deployparams);
				
                 
			}
		});
		btnorgDetailSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//progress bar moving image to show wait state
				progressBar = new ProgressDialog(context);
                progressBar.setCancelable(false);
                progressBar.setMessage("Please Wait, Saving Organisation Details ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(1000);
                progressBar.show();

				//To pass on the activity to the next page
				Intent intent = new Intent(context, preferences.class);
                startActivity(intent);  
                
				//list of input parameters type of Object 
				deployparams = new Object[]{getOrgName,getFromDate,getToDate,getSelectedOrgType}; // parameters pass to core_engine xml_rpc functions
				//call method deploy from startup.java 
				client_id = Startup.deploy(deployparams);
				 
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
	
	private Object[] stateparmas;
	
	public void getCountry() {
		Object[] countryList =  startup.getStates();//call getOrganisationNames method 
    	
    	ArrayList< String>list = new ArrayList<String>();
    	//System.out.println("state:"+countryList);
    	for(Object st : countryList)
    	{
    		System.out.println("inside Country");
    		Object[] s = (Object[]) st;
    		list.add((String) s[0]);
    	}
    	System.out.println(list);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, list);
    	
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	//set adaptor with orglist in spinner
    	getcountry.setAdapter(dataAdapter);
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			   long id) {
		//Retrieving the selected org type from the Spinner and assigning it to a variable 
				String selectedStateName = parent.getItemAtPosition(position).toString();
				//existingOrg = selectedOrgName;
				//System.out.println("selected org"+selectedOrgName);
				if(selectedStateName!=null){
					stateparmas = new Object[]{selectedStateName};
					//System.out.println(orgparmas);
			    	Object[] CityList = startup.getCities(stateparmas);//call getOrganisationNames method 
			    	//System.out.println("befor loop "+financialyearList);
			    	
			    	//System.out.println(financialyearList.toString());
			    	ArrayList< String>list1 = new ArrayList<String>();
			    	
			    	for(Object st : CityList)
			    	{
			    		System.out.println("inside city");
			    		//Object[] s = (Object[]) st;
			    			list1.add((String) st);
			    	}
			    	
			    	ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
			    			android.R.layout.simple_spinner_item, list1);
			    	
			    	dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    	getcity.setAdapter(dataAdapter1);
		    	}
		
	}

	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
 
}