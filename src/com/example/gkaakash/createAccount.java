package com.example.gkaakash;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class createAccount extends Activity implements OnItemSelectedListener {
	//Declaring variables
	String accCodeCheckFlag;
	TextView tvaccCode, tvDbOpBal, tvOpBal;
	EditText etaccCode , etDtOpBal, etOpBal;
	Spinner sgrpName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling create_account.xml
		setContentView(R.layout.create_account);
		//Request a reference to the button from the activity by calling “findViewById” and assign the retrieved button to an instance variable
		tvaccCode = (TextView) findViewById(R.id.tvAccCode);
		etaccCode = (EditText) findViewById(R.id.etAccCode);
		//Retrieving the account code flag value from the previous page(preferences page)
		accCodeCheckFlag = getIntent().getExtras().getString("flag");
		//Setting visibility on load
		etaccCode.setVisibility(EditText.VISIBLE);
		tvaccCode.setVisibility(TextView.VISIBLE);
		//Setting visibility depending upon account code flag value
		if(accCodeCheckFlag == null) {
			etaccCode.setVisibility(EditText.GONE);
			tvaccCode.setVisibility(TextView.GONE);
		}
		else {
			etaccCode.setVisibility(EditText.VISIBLE);
			tvaccCode.setVisibility(TextView.VISIBLE);
		}
		sgrpName = (Spinner) findViewById(R.id.sGroupNames);
		//Attach a listener to the group name Spinner
		sgrpName.setOnItemSelectedListener((OnItemSelectedListener) this);
	}

	
	public void onItemSelected(AdapterView<?> parent, View v, int position,
	   long id) {
		//Retrieving the selected name from the group name Spinner and assigning it to a variable 
		String selectedGrpName = parent.getItemAtPosition(position).toString();
		tvOpBal = (TextView) findViewById(R.id.tvOpBal);
		etOpBal = (EditText) findViewById(R.id.etOpBal);
		
		//Comparing the variable value to group name and setting visibility
		if("Current Asset".equals(selectedGrpName) | "Investment".equals(selectedGrpName) | "Loans(Asset)".equals(selectedGrpName) | "Fixed Assets".equals(selectedGrpName) | "Miscellaneous Expenses(Asset)".equals(selectedGrpName)) {
			etOpBal.setVisibility(EditText.VISIBLE);
			tvOpBal.setVisibility(TextView.VISIBLE);
			tvOpBal.setText("Debit Opening Balance");
			
		}
		else if ("Direct Income".equals(selectedGrpName) | "Direct Expense".equals(selectedGrpName) | "Indirect Income".equals(selectedGrpName) | "Indirect Expense".equals(selectedGrpName)) {
			etOpBal.setVisibility(EditText.GONE);
			tvOpBal.setVisibility(TextView.GONE);
		}
		else {
			etOpBal.setVisibility(EditText.VISIBLE);
			tvOpBal.setVisibility(TextView.VISIBLE);
			tvOpBal.setText("Credit Opening Balance");
		}
	 }

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		//IGNORE THIS METHOD!!!
	}
	
}
