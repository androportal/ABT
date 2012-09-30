package com.example.gkaakash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class preferences extends Activity {
	//Declaring variables
	CheckBox cbProject, cbAccCode;
	EditText etProject;
	Button bCreateAcc;
	String accCodeflag;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling preferences.xml
		setContentView(R.layout.preferences);
		etProject = (EditText) findViewById(R.id.etProject);
		//setting visibility
		etProject.setVisibility(EditText.GONE);
		addListenerOnButton();
		addListenerOnChkIos();
	}

	private void addListenerOnChkIos() {
		cbProject = (CheckBox) findViewById(R.id.cbProject);
		etProject = (EditText) findViewById(R.id.etProject);
		cbProject.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				//for setting the visibility of EditText:'etProject' depending upon the condition
				if (((CheckBox) v).isChecked()) {
					etProject.setVisibility(EditText.VISIBLE);
				}
				else {
					etProject.setVisibility(EditText.GONE);
				}
			}
			});
		
		cbAccCode = (CheckBox) findViewById(R.id.cbAccCode);
		cbAccCode.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				//Setting the account code flag value
				if (((CheckBox) v).isChecked()) {
					accCodeflag = "true";
				}
				else {
					accCodeflag = null;
				}
			}
			});
	}
	
	private void addListenerOnButton() {
		bCreateAcc = (Button) findViewById(R.id.btnCreateAcc);
		final Context context = this;
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button
		bCreateAcc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(android.view.View v) {
				//To pass on the activity to the next page
				Intent intent = new Intent(context, createAccount.class);
				//To pass on the value to the next page
				intent.putExtra("flag",accCodeflag);
                startActivity(intent);   
			}
		});
	}
	public void onBackPressed(){
		//To pass on the activity to the next page
		Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); 
	}
	
}
