package com.example.gkaakash;
import com.gkaakash.controller.Startup;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
						Toast.makeText(context,"Organisation Not Exist",Toast.LENGTH_SHORT).show();
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
		
		
		public void onBackPressed() {
			 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setMessage("Are you sure you want to exit?")
		                .setCancelable(false)
		                .setPositiveButton("Yes",
		                        new DialogInterface.OnClickListener() {
		                            public void onClick(DialogInterface dialog, int id) {
		                            	 Intent intent = new Intent(Intent.ACTION_MAIN);
		                        		 intent.addCategory(Intent.CATEGORY_HOME);
		                        		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		                        		 startActivity(intent);
		                        		 
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
