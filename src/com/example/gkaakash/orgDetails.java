package com.example.gkaakash;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.gkaakash.controller.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 
	public class orgDetails extends Activity{
		//Declaring variables 
		Button btnorgDetailSave, btnRegDate, btnFcraDate,btnSkip;
		int year, month, day;
		static final int REG_DATE_DIALOG_ID = 0;
		static final int FCRA_DATE_DIALOG_ID = 1;
		String getSelectedOrgType,getToDate,getOrgName, getFromDate, selectedCounrty;
		TextView tvRegNum, tvRegDate, tvFcraNum, tvFcraDate, tvMVATnum, tvServiceTaxnum;
		EditText etRegNum, etFcraNum, etMVATnum, etServiceTaxnum;
		String selectedStateName;
		String selectedCityName;
		private int group1Id = 1;
		int Edit = Menu.FIRST; 
		int Delete = Menu.FIRST +1;
		int Finish = Menu.FIRST +2;
		AlertDialog dialog;
		final Context context = this;
		private String orgaddress;
		Spinner getstate, getcity;
		static Integer client_id;
		private Startup startup;
		private Object[] deployparams;
		protected ProgressDialog progressBar;
		private EditText etGetAddr;
		private EditText sGetPostal;
		private EditText eGetPhone;
		private EditText eGetEmailid;
		private EditText etPanNo;
		private EditText etGetWebSite;
		private EditText eGetFax;
		private Spinner scountry;
		protected Object[] orgparams;
		private String getAddr, getPin,eGetTelNo,eGetFaxNO,etGetWeb,eGetEmail,
		etPan,etMVATno,etServiceTaxno,etRegNo,RegDate,FcraDate,etFcraNo;
		private Organisation org;
		private boolean setOrgDetails;
		Boolean editDetailsflag;
		String save_edit;
		ArrayAdapter<String> dataAdapter;
		ArrayAdapter<String> dataAdapter1;
		String setfromday,setfrommonth,setfromyear,setfromday1,setfrommonth1,setfromyear1;
		ArrayList<String> detailsList_foredit ;
		String orgcode;
		String reg_date,fcra_date;
		static String orgtype;
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
				//Toast msg = Toast.makeText(orgDetails.this, "Menu 1", Toast.LENGTH_LONG);
				//msg.show();
				return true;
			}
			return super.onOptionsItemSelected(item);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Calling org_details.xml
			setContentView(R.layout.org_details);
			// creating instance of startup to get the connection
			startup = new Startup();
			org = new Organisation();
		
			editDetailsflag = MainActivity.editDetails;
			btnorgDetailSave = (Button) findViewById(R.id.btnOrgDetailSave);
			getstate = (Spinner) findViewById(R.id.sGetStates);
			getcity = (Spinner) findViewById(R.id.sGetCity);
			btnSkip = (Button) findViewById(R.id.btnSkip);
			btnSkip.setText("Skip");
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
			etGetAddr =(EditText) findViewById(R.id.etGetAddr);
			tvServiceTaxnum = (TextView) findViewById(R.id.tvServiceTaxnum);
			etServiceTaxnum = (EditText) findViewById(R.id.etServiceTaxnum);
			sGetPostal=(EditText) findViewById(R.id.sGetPostal);
			eGetFax=(EditText) findViewById(R.id.eGetFax);
			eGetPhone=(EditText) findViewById(R.id.eGetPhone);
			    scountry=(Spinner)findViewById(R.id.sGetCountry);
			eGetEmailid=(EditText) findViewById(R.id.eGetEmailid);
			etPanNo =(EditText) findViewById(R.id.etPanNo);
		
			etGetWebSite=(EditText) findViewById(R.id.etGetWebSite);
		
			if(editDetailsflag==true){
				detailsList_foredit=menu.accdetailsList;
				System.out.println("cuming from menu page:"+menu.orgtype);

				orgtype=detailsList_foredit.get(1);
				System.out.println("OT"+orgtype);

				orgcode=detailsList_foredit.get(0);
				System.out.println("org code:"+orgcode);
				etGetAddr.setText(detailsList_foredit.get(3));
				sGetPostal.setText(detailsList_foredit.get(5));
				eGetPhone.setText(detailsList_foredit.get(8));
				eGetFax.setText(detailsList_foredit.get(9));
				eGetEmailid.setText(detailsList_foredit.get(11));
				etGetWebSite.setText(detailsList_foredit.get(10));
				etMVATnum.setText(detailsList_foredit.get(13));
				etServiceTaxnum.setText(detailsList_foredit.get(14));
				etPanNo.setText(detailsList_foredit.get(12));
				etMVATnum.setText(detailsList_foredit.get(13));
				etServiceTaxnum.setText(detailsList_foredit.get(14)); 
				etRegNum.setText(detailsList_foredit.get(15)); 
				etFcraNum.setText(detailsList_foredit.get(17)); 
				btnRegDate.setText(detailsList_foredit.get(16));
				System.out.println("Reg Date:"+detailsList_foredit.get(16));
				reg_date=detailsList_foredit.get(16);
				btnFcraDate.setText(detailsList_foredit.get(18));
				fcra_date=detailsList_foredit.get(18);
				//setting text for skip button 
				btnSkip.setText("Reset");
			}
		 
			// Retrieving the organisation type flag value from the previous page(create organisation page)
			if(editDetailsflag==false){ 
				getSelectedOrgType=createOrg.orgTypeFlag;
				}
		        else {
			    getSelectedOrgType=orgtype; 
		        }
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
			if(editDetailsflag==false){
				// Declaring new method for setting current date into "Registration Date"
				setCurrentDateOnButton();
			}
			// creating new method do event on button 
			addListenerOnButton();
			// Method to get list Of States
			getStates();
			//creating interface to listen activity on Item 
			addListenerOnItem();
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
			.append(year));
			//set current date to FCRA registration button
			btnFcraDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year));
		}


		//Attach a listener to the click event for the button
		private void addListenerOnButton() {
			final Context context = this;
			// get flag values which is static
			getOrgName=createOrg.organisationName;
			//getOrgName =Startup.getOrgansationname();
			System.out.println("orgname (create org) :"+getOrgName);
			getFromDate=createOrg.fromdate;
			getToDate=createOrg.todate;
			//Create a class implementing “OnClickListener” and set it as the on click listener for the button
			btnSkip.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(editDetailsflag==false){
						savedeatils();
					}else {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
				        builder.setMessage("Are you sure, you want to reset all fields? ")
				                .setCancelable(false)
				                .setPositiveButton("Yes",
				                        new DialogInterface.OnClickListener() {
				                            public void onClick(DialogInterface dialog, int id) {
												etGetAddr.setText("");
												sGetPostal.setText("");
												eGetPhone.setText("");
												eGetFax.setText("");
												eGetEmailid.setText("");
												etGetWebSite.setText("");
												etMVATnum.setText("");
												etServiceTaxnum.setText("");
												etPanNo.setText("");
												etMVATnum.setText("");
												etServiceTaxnum.setText("");
												etRegNum.setText(""); 
												etFcraNum.setText("");
												btnRegDate.setText(Startup.getfinancialFromDate());
												btnFcraDate.setText(Startup.getfinancialFromDate());
												getstate.setSelection(0);
												getcity.setSelection(0);
				                            }
		                        })
			                .setNegativeButton("No", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                        dialog.cancel();
			                    }
		                });
		        AlertDialog alert = builder.create();
		        alert.show();
					}
				}
			}); 
			btnorgDetailSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					savedeatils();
				}
			}); 
			btnRegDate.setOnClickListener(new OnClickListener() {
				@Override	
				public void onClick(View v) {
					//for showing a date picker dialog that allows the user to select a date (Registration Date)
					String regDate = (String) btnRegDate.getText();
					String dateParts[] = regDate.split("-");
					setfromday  = dateParts[0];
					setfrommonth = dateParts[1];
					setfromyear = dateParts[2];
		 
					System.out.println("regdate is:"+regDate);
					showDialog(REG_DATE_DIALOG_ID);
				}
			});
			btnFcraDate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//for showing a date picker dialog that allows the user to select a date (FCRA Registration Date)
					String fcraDate = (String) btnFcraDate.getText();
					String dateParts[] = fcraDate.split("-");
					setfromday1  = dateParts[0];
					setfrommonth1 = dateParts[1];
					setfromyear1 = dateParts[2];
	 
					//System.out.println("fcradate is:"+setfromday1);
					//System.out.println("fcradate is:"+setfrommonth1);
					//System.out.println("fcradate is:"+setfromyear1);
					//System.out.println("fcradate is:"+fcraDate);
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
							Integer.parseInt(setfromyear), Integer.parseInt(setfrommonth)-1,Integer.parseInt(setfromday));
				
			case FCRA_DATE_DIALOG_ID:
				
					// set date picker as current date
					return new DatePickerDialog(this, fcradatePickerListener, 
							Integer.parseInt(setfromyear1), Integer.parseInt(setfrommonth1)-1,Integer.parseInt(setfromday1));
				
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
					.append(year));
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
				.append(year));
			}
		};
		// Method getStates
		void getStates(){
			// call the getStates method to get all States
			Object[] StateList =  startup.getStates();
			List<String> statelist = new ArrayList<String>();
	   
			// for loop to iterate list of state name and add to list
			for(Object st : StateList)
			{
				Object[] s = (Object[]) st;
				statelist.add((String) s[0]);
			}
			if(editDetailsflag==false){
				// creating array adaptor to take list of state
				dataAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, statelist);
				//set resource layout of spinner to that adaptor
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				//set adaptor contain states list to spinner 
				getstate.setAdapter(dataAdapter);
			}else {
				String state1 = detailsList_foredit.get(6);
				dataAdapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, statelist);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				int pos1 = dataAdapter.getPosition(state1);
				getstate.setAdapter(dataAdapter);
				getstate.setSelection(pos1);
			}
	   
		}// End of getStates()
		void addListenerOnItem(){
			//Attach a listener to the states Type Spinner to get dynamic list of cities
			getstate.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					//Retrieving the selected state from the Spinner and assigning it to a variable 
					selectedStateName = parent.getItemAtPosition(position).toString();
					Object[] stateparmas;
					// checks for the selected value of item is not null
					if(selectedStateName!=null){
						// array of selected state name of type Object
						stateparmas = new Object[]{selectedStateName};
						// call the getCities method to get all related cities of given selected state name 
						Object[] CityList = startup.getCities(stateparmas);
						List<String> citylist = new ArrayList<String>();
		   
						// for loop to iterate list of city name and add to list
						for(Object st : CityList)
							citylist.add((String) st);
								if(editDetailsflag==false){
									// creating array adaptor to take list of city 
									dataAdapter1 = new ArrayAdapter<String>(context,
											android.R.layout.simple_spinner_item, citylist);
									// set resource layout of spinner to that adaptor
									dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									// set Adaptor contain cities list to spinner 
									getcity.setAdapter(dataAdapter1);
								}else {
									String city = detailsList_foredit.get(4).trim();
									dataAdapter1 = new ArrayAdapter<String>(context,
											android.R.layout.simple_spinner_item, citylist);
									dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   
									//System.out.println("city name"+dataAdapter1.getItem(2).trim().);
									int pos = dataAdapter1.getPosition(city);
									getcity.setAdapter(dataAdapter1);
									getcity.setSelection(pos);
		   
		   
								}
		   
					}// End of if condition
				} // End of onItemSelected()

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					}
			});// End of getstate.setOnItemSelectedListener
			getcity.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					//Retrieving the selected state from the Spinner and assigning it to a variable 
					selectedCityName = parent.getItemAtPosition(position).toString();
				}
		
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});
			scountry.setOnItemSelectedListener(new OnItemSelectedListener(){
		
				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					//Retrieving the selected state from the Spinner and assigning it to a variable 
					selectedCounrty = parent.getItemAtPosition(position).toString();
				}
		
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});
		} // end of addListenerOnItem()
		/*
		* get all values and pass to the backend through controller
		*/
	private void savedeatils() {
		// TODO Auto-generated method stub
		getOrgName = createOrg.organisationName;
		getFromDate = createOrg.fromdate;
		getToDate = createOrg.todate;
		getAddr = etGetAddr.getText().toString();
		getPin = sGetPostal.getText().toString();
		eGetTelNo = eGetPhone.getText().toString();
		eGetFaxNO = eGetFax.getText().toString();
		etGetWeb = etGetWebSite.getText().toString();
		eGetEmail = eGetEmailid.getText().toString();	
		etPan = etPanNo.getText().toString();
		etMVATno = etMVATnum.getText().toString();
		etServiceTaxno = etServiceTaxnum.getText().toString();
		etRegNo = etRegNum.getText().toString();
		RegDate = btnRegDate.getText().toString();
		etFcraNo = etFcraNum.getText().toString();
		FcraDate = btnFcraDate.getText().toString();
		/*//progress bar moving image to show wait state
		progressBar = new ProgressDialog(context);
		        progressBar.setCancelable(false);
		        progressBar.setMessage("Please Wait, Saving Organisation Details ...");
		        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        progressBar.setProgress(0);
		        progressBar.setMax(1000);
		        progressBar.show();*/ 
		      //list of input parameters type of Object 
		deployparams = new Object[]{getOrgName,getFromDate,getToDate,getSelectedOrgType}; // parameters pass to core_engine xml_rpc functions
			
			if(editDetailsflag==false){
				orgparams = new Object[]{getOrgName,getSelectedOrgType,selectedCounrty,selectedStateName,
						selectedCityName,getAddr,getPin,eGetTelNo,eGetFaxNO,etGetWeb,eGetEmail,
						etPan,etMVATno,etServiceTaxno,etRegNo,
						RegDate,etFcraNo,FcraDate }; 
				//call method deploy from startup.java 
				client_id = startup.deploy(deployparams);
				setOrgDetails = org.setOrganisation(orgparams,client_id);
			}else {
				orgparams = new Object[]{orgcode,getAddr,selectedCounrty,selectedStateName,selectedCityName,getPin, 
						eGetTelNo,eGetFaxNO,eGetEmail,etGetWeb,etMVATno,etServiceTaxno,etRegNo,RegDate,etFcraNo,FcraDate ,
						etPan};
				client_id = startup.login(deployparams);
				save_edit = (String)org.updateOrg(orgparams, client_id);
				toastValidationMessage("Organisation details edited successfully");
				
			}
		           
			if (setOrgDetails==true){
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
		        builder.setMessage("Organisation "+getOrgName+" with details saved successfully") 
		                .setCancelable(false)
		                .setPositiveButton("Ok",
		                        new DialogInterface.OnClickListener() {
		                            public void onClick(DialogInterface dialog, int id) {
		                            	 //To pass on the activity to the next page
		                		        Intent intent = new Intent(context, preferences.class);
		                		        startActivity(intent); 
		                            }
		                        });
		                
		        AlertDialog alert = builder.create();
		        alert.show();
		        
		        }
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
	
	
	public void onBackPressed() {
		if(editDetailsflag==false){
			Intent intent = new Intent(getApplicationContext(), createOrg.class);
			    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    startActivity(intent);
			}else {
			Intent intent = new Intent(getApplicationContext(), menu.class);
			    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    startActivity(intent);
			};
        }
	} // End of Class
