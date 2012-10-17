package com.example.gkaakash;

import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class preferences extends Activity {
	//Declaring variables
	CheckBox cbProject, cbAccCode;
	EditText etProject;
	Button bCreateAcc;
	static String accCodeflag;
	private Button btnSavePref;
	private Preferences preference;
	private Integer client_id;
	protected Boolean setpref;
	protected String projectname;
	protected Context context;
	private Button btnQuit;
	static String refNoflag;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling preferences.xml
		setContentView(R.layout.preferences);
		// get Client_id return by Deploy method
		client_id = Startup.getClient_id();
		etProject = (EditText) findViewById(R.id.etProject);
		//setting visibility
		etProject.setVisibility(EditText.GONE);
		// create object of Preference class
		preference = new Preferences();
		accCodeflag=preference.getPreferences(new Object[]{"2"},client_id );
		refNoflag=preference.getPreferences(new Object[]{"1"},client_id);
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
					
					refNoflag = "optional";
				}
				else {
					etProject.setVisibility(EditText.GONE);
					refNoflag = "mandatory";
				}
			}
			});
		
		cbAccCode = (CheckBox) findViewById(R.id.cbAccCode);
		
		cbAccCode.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View v) {
				//Setting the account code flag value
				if (((CheckBox) v).isChecked()) {
					
					accCodeflag = "manually";
				}
				else {
					accCodeflag = "automatic";
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
				//intent.putExtra("finish_flag","login");
                startActivity(intent);   
			}
		});
		btnSavePref =(Button) findViewById(R.id.btnSavePref);
		//Create a class implementing “OnClickListener” and set it as the on click listener for the button
		btnSavePref.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(android.view.View v) {
						
						projectname = etProject.getText().toString();
						System.out.println("projectname:"+projectname);
						//if (projectname.equals(""))
						//{
							//Toast.makeText(context, "please enter the project name", Toast.LENGTH_SHORT).show();
						//}else
						//{
							System.out.println(projectname);
							System.out.println(accCodeflag);
							Object[] params = new Object[]{"1",refNoflag,"2",accCodeflag,projectname};
							setpref =(Boolean) preference.setPreferences(params,client_id);
							//To pass on the activity to the next page
							Toast.makeText(context, "Preferences have been saved successfully!	", Toast.LENGTH_SHORT).show();
							cbAccCode.setChecked(false);
						//} 
					}
				});
		btnQuit =(Button) findViewById(R.id.btnQuit);
		btnQuit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, MainActivity.class);
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
