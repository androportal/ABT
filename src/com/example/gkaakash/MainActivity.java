package com.example.gkaakash;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;


public class MainActivity extends Activity {
	//Add a class property to hold a reference to the button
	Button create_org;
	//
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        //Calling activity_main.xml which is first page of GNUKhata
        setContentView(R.layout.activity_main);
        //Request a reference to the button from the activity by calling “findViewById” and assign the retrieved button to an instance variable
        create_org = (Button) findViewById(R.id.bcreateOrg);
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
	}
}
