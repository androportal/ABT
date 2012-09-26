package com.example.gkaakash;

import java.util.ArrayList;

import org.xmlrpc.android.XMLRPCClient;
import com.gkaakash.controller.Startup;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;


public class MainActivity extends Activity {
	//Add a class property to hold a reference to the button
	Button create_org;
	Startup startup;
	private View select_org;
	private Object[] orgNameList;
	Spinner getOrgNames;
	ArrayList<String> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//Calling activity_main.xml which is first page of GNUKhata
    	setContentView(R.layout.activity_main);
    	
		startup = new Startup();
    	getOrgNames = (Spinner)findViewById(R.id.sGetOrgNames);
    	orgNameList = startup.getOrgnisationName();//call getOrganisationNames method 
    	
       //Request a reference to the button from the activity by calling “findViewById” and assign the retrieved button to an instance variable
        create_org = (Button) findViewById(R.id.bcreateOrg);
        select_org =(Button) findViewById(R.id. bselectOrg);
        //creating a new interface  
        addListenerOnButton();
    }
    
    	//Attach a listener to the click event for the button
		void addListenerOnButton() {
		final Context context = this;
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button
		create_org.setOnClickListener(new OnClickListener() {
			 
			public void onClick(View arg0) {
				//To pass on the activity to the next page
			    Intent intent = new Intent(context, createOrg.class);
			    startActivity(intent);   
 
			}
		});
		select_org.setOnClickListener(new OnClickListener() {
			
			//To pass on the activity to the next page
			public void onClick(View arg0) {
				if(orgNameList == null)
				{
					Toast.makeText(context,"Organisation Not Exist",Toast.LENGTH_LONG).show();
				}
				else
				{
					Intent intent = new Intent(context, selectOrg.class);
					/*intent.putExtra("orgNameListflag",list);*/
					startActivity(intent);   
				}
 
			}
		});
	}
}
