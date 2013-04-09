package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;
import com.gkaakash.controller.Startup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class selectOrg extends Activity{
	Object[] orgNameList;
	Spinner getOrgNames;
	private Spinner getFinancialyear;
	private Startup startup;
	private Button bProceed;
	Object[] financialyearList;
	final Context context = this;
	private Button btnDeleteOrg;
	protected AdapterView<SpinnerAdapter> parent;
	protected Object selectedFinancialYear;
	//static String existingOrgFlag;
	protected static Integer client_id;
	protected static String selectedOrgName;
	protected static String fromDate;
	protected static String  toDate;

	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.select_org);
    	 
    	MainActivity.no_dailog = true; //comment this line if running this app on emulator
    	
    	// set flag to true , if we are in existing organisation
    	//existingOrgFlag="true";
    	// call startup to get client connection 
    	startup = new Startup();
    	getOrgNames = (Spinner) findViewById(R.id.sGetOrgNames);
    	getFinancialyear = (Spinner) findViewById(R.id.sGetFinancialYear);
    	getOrgNames.setMinimumWidth(100);
    	getFinancialyear.setMinimumWidth(250);
    	bProceed = (Button) findViewById(R.id.bProceed);
    	btnDeleteOrg = (Button) findViewById(R.id.btnDeleteOrg);
    	getExistingOrgNames();
    	addListenerOnItem();
    	addListenerOnButton();
    	
    }// End of onCreate
	
	// getExistingOrgNames()
	void getExistingOrgNames(){
			
		//call getOrganisationNames method 
    	orgNameList = startup.getOrgnisationName();
    	System.out.println(orgNameList);
    	List<String> list = new ArrayList<String>();
    	
    	for(Object st : orgNameList)
    		list.add((String) st);
		
    	// creating array adaptor to take list of existing organisation name
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, list);
    	//set resource layout of spinner to that adaptor
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //set adaptor with orglist in spinner
    	getOrgNames.setAdapter(dataAdapter);
    	
	}// End of getExistingOrgNames()
		
	
	//Attach a listener to the click event for the button
	private void addListenerOnButton(){
		final Context context = this;
		bProceed.setOnClickListener(new OnClickListener() {
			
			private Object[] deployparams;

			@Override
			public void onClick(android.view.View v) {
				
				if(orgNameList.length>0)
				{
					//parameters pass to core_engine xml_rpc functions
					deployparams=new Object[]{selectedOrgName,fromDate,toDate};
					//call method login from startup.java 
					client_id = startup.login(deployparams);
					//System.out.println("login "+ client_id);
					//To pass on the activity to the next page  
					Intent intent = new Intent(context,menu.class);
	                startActivity(intent); 
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        builder.setMessage("Please create organisation")
			                .setCancelable(false)
			                .setPositiveButton("Ok",
			                        new DialogInterface.OnClickListener() {
			                            public void onClick(DialogInterface dialog, int id) {
			                            	//parameters pass to core_engine xml_rpc functions
			                            	//To pass on the activity to the next page  
			            					Intent intent = new Intent(context,MainActivity.class);
			            	                startActivity(intent); 
			                            }
			                        });
			                
			        AlertDialog alert = builder.create();
			        alert.show();
				}
			}
		});
		btnDeleteOrg.setOnClickListener(new OnClickListener() {
			
			private Object[] deleteprgparams;
			private Boolean deleted;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
		        builder.setMessage("Are you sure you want to permanetly delete "+selectedOrgName+" for financialyear "+fromDate+" To "+toDate+"?\n" +
		        		"if you will delete an item , It will be permanetly lost ")
		                .setCancelable(false)
		                .setPositiveButton("Delete",
		                        new DialogInterface.OnClickListener() {
		                            public void onClick(DialogInterface dialog, int id) {
		                            	//parameters pass to core_engine xml_rpc functions
		                				deleteprgparams=new Object[]{selectedOrgName,fromDate,toDate};
		                				deleted = startup.deleteOrgnisationName(deleteprgparams);
		                				getExistingOrgNames();
		                		    	addListenerOnItem();
		                		    	addListenerOnButton();
		                				//Intent intent = new Intent(context,selectOrg.class);
		                				//startActivity(intent);
		                				
		                            }
		                        })
		                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                        dialog.cancel();
		                    }
		                });
		        AlertDialog alert = builder.create();
		        alert.show();
		       
			}
			
		
		});
		
	}
	
	void addListenerOnItem(){
		//Attach a listener to the states Type Spinner to get dynamic list of cities
		getOrgNames.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				//Retrieving the selected org type from the Spinner and assigning it to a variable 
				selectedOrgName = parent.getItemAtPosition(position).toString();
					//call getFinancialYear method from startup.java 
			    	//it will give you financialYear list according to orgname
			    	financialyearList = startup.getFinancialYear(selectedOrgName);
			    	
			    	List<String> financialyearlist = new ArrayList<String>();
			    	
			    	for(Object fy : financialyearList)
			    	{
			    		Object[] y = (Object[]) fy;
			    		// concatination From and To date 
			    		financialyearlist.add(y[0]+" to "+y[1]);
			    		//fromDate=y[0].toString();
			    		//toDate=y[1].toString();
			    	}
			    	
			    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
			    			android.R.layout.simple_spinner_item, financialyearlist);
			    	
			    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    	
			    	getFinancialyear.setAdapter(dataAdapter);
				    	}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});// End of getOrgNames.setOnItemSelectedListener
		getFinancialyear.setOnItemSelectedListener(new OnItemSelectedListener() {

			private String[] finallist;
		

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				// TODO Auto-generated method stub
				selectedFinancialYear = parent.getItemAtPosition(position).toString();
				finallist = selectedFinancialYear.toString().split(" to ");
				fromDate = finallist[0];
				toDate = finallist[1];
				
				//String fromDate = Startup.setOrgansationname((String)fromDate);
		    	Startup.setfinancialFromDate(fromDate);
		    	Startup.setFinancialToDate(toDate);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	} // end of addListenerOnItem()
}// End of Class 
