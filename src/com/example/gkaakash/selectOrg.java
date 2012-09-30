package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;
import com.gkaakash.controller.Startup;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class selectOrg extends Activity{
	Object[] orgNameList;
	Spinner getOrgNames;
	private Spinner getFinancialyear;
	private Startup startup;
	private Button bProceed;
	Object[] financialyearList;
	final Context context = this;

	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.select_org);
    	startup = new Startup();
    	getOrgNames = (Spinner) findViewById(R.id.sGetOrgNames);
    	getFinancialyear = (Spinner) findViewById(R.id.sGetFinancialYear);
    	bProceed = (Button) findViewById(R.id.bProceed);
    	getExistingOrgNames();
    	addListenerOnButton();
    	addListenerOnItem();
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
			
			@Override
			public void onClick(android.view.View v) {
				//parameters pass to core_engine xml_rpc functions
				//call method deploy from startup.java 
				//client_id = Startup.login(deployparams);
				//To pass on the activity to the next page  
				Intent intent = new Intent(context,menu.class);
				intent.putExtra("flag","true");
                startActivity(intent); 
			}
		});
	}
	
	void addListenerOnItem(){
		//Attach a listener to the states Type Spinner to get dynamic list of cities
		getOrgNames.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				//Retrieving the selected org type from the Spinner and assigning it to a variable 
				String selectedOrgName = parent.getItemAtPosition(position).toString();
				
				if(selectedOrgName!=null){
					
					//call getFinancialYear method from startup.java 
			    	//it will give you financialYear list according to orgname
			    	financialyearList = startup.getFinancialYear(selectedOrgName);
			    	
			    	List<String> financialyearlist = new ArrayList<String>();
			    	
			    	for(Object fy : financialyearList)
			    	{
			    		Object[] y = (Object[]) fy;
			    		// concatination From and To date 
			    		financialyearlist.add(y[0]+" to "+y[1]);
			    	}
			    	
			    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
			    			android.R.layout.simple_spinner_item, financialyearlist);
			    	
			    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    	
			    	getFinancialyear.setAdapter(dataAdapter);
				    	}// End of if condition
				} // End of onItemSelected()

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});// End of getOrgNames.setOnItemSelectedListener
	} // end of addListenerOnItem()
}// End of Class 
