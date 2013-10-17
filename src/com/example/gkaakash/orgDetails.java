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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
 
	public class orgDetails extends Activity{
		//Declaring variables 
		Button btnorgDetailSave, btnRegDate, btnFcraDate,btnSkip,btnDeleteOrg,btnDelete;
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
		String save_edit,financialFrom,financialTo;
		ArrayAdapter<String> dataAdapter;
		ArrayAdapter<String> dataAdapter1;
		String setfromday,setfrommonth,setfromyear,setfromday1,setfrommonth1,setfromyear1;
		ArrayList<String> detailsList_foredit ;
		String orgcode,message;
		String reg_date,fcra_date,fromDate,toDate;
		static String orgtype;
		module m;
		Object[] editDetails;
		static ArrayList<String> OrgdetailsList;
		TableRow trOrgnisation;
		Object[] financialyearList;
		Spinner getFinancialyear;
		Object[] deleteprgparams;
		Boolean deleted;
		static String IPaddr;
		
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
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			// Calling org_details.xml
			setContentView(R.layout.org_details);
			// creating instance of startup to get the connection
			IPaddr = MainActivity.IPaddr;
			System.out.println("in createorg"+IPaddr);
			startup = new Startup(IPaddr);
			org = new Organisation(IPaddr);
			m= new module();
		
			editDetailsflag = MainActivity.editDetails;
			btnorgDetailSave = (Button) findViewById(R.id.btnOrgDetailSave);
			//btnDeleteOrg = (Button) findViewById(R.id.btnDeleteOrg);
			getstate = (Spinner) findViewById(R.id.sGetStates);
			getcity = (Spinner) findViewById(R.id.sGetCity);
			btnSkip = (Button) findViewById(R.id.btnSkip);
			btnDeleteOrg = (Button) findViewById(R.id.btnDeleteOrg);
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
			client_id = Startup.getClient_id();	
			
		
			if(editDetailsflag==true){
				
				System.out.println("edit details :"+editDetailsflag);
				detailsList_foredit=menu.accdetailsList;
				System.out.println("cuming from menu page:"+detailsList_foredit);

				orgtype=detailsList_foredit.get(1);
				//System.out.println("OT"+orgtype);

				orgcode=detailsList_foredit.get(0);
				//System.out.println("org code:"+orgcode);
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
				btnFcraDate.setText(detailsList_foredit.get(18));
				if(btnRegDate.getText().equals("")&&btnFcraDate.getText().equals(""))
				{
					setCurrentDateOnButton();
				}
				reg_date=detailsList_foredit.get(16);
				fcra_date=detailsList_foredit.get(18);
				//setting text for skip button 
				btnSkip.setText("Reset");
				btnDeleteOrg.setVisibility(View.VISIBLE);
				getOrgName = menu.OrgName;
				financialFrom = menu.financialFromDate;
				financialTo = menu.financialToDate;
			}
			else
			{
				editDetails = (Object[])org.getOrganisation(client_id);
				OrgdetailsList = new ArrayList<String>();
		       	for(Object row2 : editDetails){
		       		Object[] a2=(Object[])row2;
		       		if(a2.length!=0)
		       		{
		       			ArrayList<String> accdetails = new ArrayList<String>();
			             for(int i=0;i<a2.length;i++){
			             	accdetails.add((String) a2[i].toString());
			             }
			             OrgdetailsList.addAll(accdetails);
		       		}
		       	} 
		       	orgcode=OrgdetailsList.get(0);
		       	getOrgName = MainActivity.organisationName;
		       	financialFrom  = MainActivity.fromdate;
		       	financialTo = MainActivity.todate;
			}
			// Retrieving the organisation type flag value from the previous page(create organisation page)
			if(editDetailsflag==false){ 
				getSelectedOrgType=MainActivity.orgTypeFlag;
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
			
			//set title
			TextView org = (TextView)findViewById(R.id.org_name);
			org.setText(getOrgName+", "+getSelectedOrgType);
			TextView tvdate = (TextView)findViewById(R.id.date);
			tvdate.setText(m.changeDateFormat(financialFrom)+" To "+m.changeDateFormat(financialTo));
			
			Button btn_optionsMenu= (Button) findViewById(R.id.btn_optionsMenu);
			btn_optionsMenu.setVisibility(View.GONE);
			Button btn_changeInputs= (Button) findViewById(R.id.btn_changeInputs);
			btn_changeInputs.setVisibility(View.GONE);
			
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
			
			getFromDate=MainActivity.fromdate;
			getToDate=MainActivity.todate;
			//Create a class implementing “OnClickListener” and set it as the on click listener for the button
			btnSkip.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(editDetailsflag==false){
						Intent intent = new Intent(getApplicationContext(), menu.class);
					    startActivity(intent);
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
			btnDeleteOrg.setOnClickListener(new OnClickListener() {
				
				private TextView tvWarning;

				@Override
				public void onClick(View arg0) {
                	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.import_organisation, (ViewGroup) findViewById(R.id.layout_root));
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
					//builder.setTitle("Delete orginsation for given financial year");
					trOrgnisation = (TableRow) layout.findViewById(R.id.trQuestion);
					tvWarning = (TextView) layout.findViewById(R.id.tvWarning);
					trOrgnisation.setVisibility(View.GONE);
					getFinancialyear = (Spinner)layout.findViewById(R.id.sYear);
					btnDelete = (Button)layout.findViewById(R.id.btnImport);
					Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);
			        TextView tvalertHead1 = (TextView) layout.findViewById(R.id.tvalertHead1);
			        tvalertHead1.setText("Delete "+getOrgName+" orgnisation for given financial year?");
					btnDelete.setText("Delete");
					System.out.println("print orgname : "+getOrgName);
					
					
						addListnerOnFinancialSpinner();
						btnDelete.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								
								
							if(fromDate.equals(financialFrom)&&toDate.equals(financialTo))
								{
									message = "Are you sure you want to permanently delete currently logged in "+getOrgName+" for financialyear "+fromDate+" To "+toDate+"?\n" +
							        		"your data will be permanetly lost and session will be closed !";
								}
								else{
									message = "Are you sure you want to permanently delete "+getOrgName+" for financialyear "+fromDate+" To "+toDate+"?\n" +
							        		"It will be permenantly lost !";
								}
								//tvalertHead1   
								
								AlertDialog.Builder builder = new AlertDialog.Builder(context);
							        builder.setMessage(message)
							                .setCancelable(false)
							                .setPositiveButton("Ok",
							                        new DialogInterface.OnClickListener() {
							                            public void onClick(DialogInterface dialog, int id) {
							                            	//parameters pass to core_engine xml_rpc functions
							                            	//addListnerOnFinancialSpinner();
							                            	//System.out.println("dlete params: "+getOrgName+""+fromDate+""+toDate);
							                				deleteprgparams=new Object[]{getOrgName,fromDate,toDate};
							                				
							                				deleted = startup.deleteOrgnisationName(deleteprgparams);
							                		    
							                				if(fromDate.equals(financialFrom)&&toDate.equals(financialTo))
															{
							                					//To pass on the activity to the next page
							                					Intent intent = new Intent(context,MainActivity.class);
							                					startActivity(intent);
															}else{
																addListnerOnFinancialSpinner();
																tvWarning.setVisibility(View.VISIBLE);
																tvWarning.setText("Deleted "+getOrgName+" for "+fromDate+" to "+toDate);
															}
							                				
							                            }  
							                        })
							                .setNegativeButton("No", new DialogInterface.OnClickListener() {
							                    public void onClick(DialogInterface dialog, int id) {
							                        dialog.cancel();
							                        dialog.dismiss();
							                    }
							                });
							        AlertDialog alert = builder.create();
							        alert.show();

								}
							
						});
						btnCancel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								dialog.cancel();
								dialog.dismiss();
								
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
		
		public void addListnerOnFinancialSpinner()
		{
			List<String> financialyearlist = getFinancialYearList();
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_spinner_item, financialyearlist);

			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			getFinancialyear.setAdapter(dataAdapter);
			getFinancialyear.setOnItemSelectedListener(new OnItemSelectedListener() {

				private String[] finallist;


				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					System.out.println("frm date");
					// TODO Auto-generated method stub
					String selectedFinancialYear = parent.getItemAtPosition(position).toString();
					finallist = selectedFinancialYear.toString().split(" to ");
					System.out.println("frm date"+finallist);
					fromDate = finallist[0];
					toDate = finallist[1];
					System.out.println("frm date"+fromDate+""+toDate);

					//String fromDate = Startup.setOrgansationname((String)fromDate);
					//Startup.setfinancialFromDate(fromDate);
					//Startup.setFinancialToDate(toDate);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		 public List<String> getFinancialYearList()
		       {
		                financialyearList = startup.getFinancialYear(getOrgName);
		               List<String> financialyearlist = new ArrayList<String>();
		                
		                for(Object fy : financialyearList)
		                {
		                       Object[] y = (Object[]) fy;
		                       
		                       financialyearlist.add(y[0]+" to "+y[1]);
		                      
		               }
		                return financialyearlist;
		        }

		/*
		* get all values and pass to the backend through controller
		*/
	private void savedeatils() {
		// TODO Auto-generated method stub
		
		getFromDate = MainActivity.fromdate;
		getToDate = MainActivity.todate;
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
		
		orgparams = new Object[]{orgcode,getAddr,selectedCounrty,selectedStateName,selectedCityName,getPin, 
						eGetTelNo,eGetFaxNO,eGetEmail,etGetWeb,etMVATno,etServiceTaxno,etRegNo,RegDate,etFcraNo,FcraDate ,
						etPan};
				
		save_edit = (String)org.updateOrg(orgparams, client_id);
		
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Organisation "+getOrgName+" with details saved successfully")
				.setCancelable(false)
				.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					if(editDetailsflag==false){
					//To pass on the activity to the next page
					Intent intent = new Intent(context, menu.class);
					startActivity(intent);
					}else{
						
					}
				}
				});

				AlertDialog alert = builder.create();
				alert.show();
		}
	
	

	
	
	public void onBackPressed() {
		if(editDetailsflag==false){
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    startActivity(intent);
			}else {
			Intent intent = new Intent(getApplicationContext(), menu.class);
			    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			    startActivity(intent);
			};
        }
	} // End of Class
