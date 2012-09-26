package com.example.gkaakash;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.xmlrpc.android.XMLRPCClient;

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

public class selectOrg extends Activity implements OnItemSelectedListener {
	Object[] orgNameList;
	
	Spinner getOrgNames;
	private Spinner getFinancialyear;
	
	private Object[] orgparmas;
	///private String selectedOrgName;
	private String existingOrg;
	private Startup startup;
	private Button bProceed;
	//private String OrgNameList;
	private String orgNameListflag;
	Object[] financialyearList;
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.select_org);
    	startup =new Startup();
    	getOrgNames = (Spinner) findViewById(R.id.sGetOrgNames);
    	getFinancialyear = (Spinner) findViewById(R.id.sGetFinancialYear);
    	bProceed = (Button) findViewById(R.id.bProceed);
    	System.out.println("welcome");
    	orgNameList = startup.getOrgnisationName();//call getOrganisationNames method 
    	//System.out.println("o"+orgNameList);
    	//orgNameList = getIntent().getExtras().getStringArrayList(orgNameListflag);
    	List<String> list = new ArrayList<String>();
    	System.out.println("orgNameList:"+orgNameList);
    	for(Object st : orgNameList)
    		list.add((String) st);
				
				
  
    	//System.out.println("list2 :"+list);
    	//set list into adaptor
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, list);
    	
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	//set adaptor with orglist in spinner
    	getOrgNames.setAdapter(dataAdapter);
    	getOrgNames.setOnItemSelectedListener((OnItemSelectedListener) this);
    	
    	
    	addListenerOnButton();
    	
    	
    	
    		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int position,
			   long id) {
		//Retrieving the selected org type from the Spinner and assigning it to a variable 
				String selectedOrgName = parent.getItemAtPosition(position).toString();
				////existingOrg = selectedOrgName;
				
				System.out.println("selected org"+selectedOrgName);
				if(selectedOrgName!=null){
				
					
					//call getFinancialYear method from startup.java 
			    	//it will give you financialYear list according to orgname
			    	financialyearList = startup.getFinancialYear(selectedOrgName);
			   
			    	List< String>list2 = new ArrayList<String>();
			    	
			    	for(Object fy : financialyearList)
			    	{
			    		System.out.println("inside financial list");
			    		Object[] y = (Object[]) fy;
			    		list2.add(y[0]+" to "+y[1]);
			    	}
			    	System.out.println("befor loop "+list2);
			    	
			    	ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
			    			android.R.layout.simple_spinner_item, list2);
			    	
			    	dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    	getFinancialyear.setAdapter(dataAdapter1);
		    	}
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
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
	
}
