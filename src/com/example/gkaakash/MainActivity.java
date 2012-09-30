package com.example.gkaakash;
import com.gkaakash.controller.Startup;
import android.os.Bundle;
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
	private static Object[] orgNameList;
	Spinner getOrgNames;
	final Context context = this;	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	//Calling activity_main.xml which is first page of GNUKhata
    	setContentView(R.layout.activity_main);
    	//create object of Startup to access connection  
    	startup = new Startup();
    	// call the getOrganisationName method from startup
    	orgNameList = startup.getOrgnisationName(); // return lists of existing organisations
    	System.out.println(orgNameList);
    	//Request a reference to the button from the activity by calling “findViewById” 
    	//and assign the retrieved button to an instance variable
        create_org = (Button) findViewById(R.id.bcreateOrg);
        select_org =(Button) findViewById(R.id. bselectOrg);
        //Request a reference to the spinner from the activity by calling “findViewById” 
        //and assign the retrieved spinner to an instance variable
    	getOrgNames = (Spinner)findViewById(R.id.sGetOrgNames);
        //creating new method do event on button 
        addListenerOnButton();
    }// End Of onCreate method
    
    	//Attach a listener to the click event for the button
		void addListenerOnButton() {
			
			//Create a class implementing “OnClickListener” 
			//and set it as the on click listener for the button
			create_org.setOnClickListener(new OnClickListener() {
				 
				public void onClick(View arg0) {
					//To pass on the activity to the next page
				    Intent intent = new Intent(context, createOrg.class);
				    startActivity(intent);   
	 
				}// end of onClick
			});// end of create_org.setOnClickListener
			select_org.setOnClickListener(new OnClickListener() {
			
				public void onClick(View arg0) {
					// check existing organisation name list is null
					if(orgNameList == null || orgNameList.length == 0)
					{
						Toast.makeText(context,"Organisation Not Exist",Toast.LENGTH_LONG).show();
					}
					else
					{
						//To pass on the activity to the next page
						Intent intent = new Intent(context, selectOrg.class);
						startActivity(intent);   
					}
				}// end of onClick
		});// end of select_org.setOnClickListener
	}// end of addListenerOnButton() method
}
