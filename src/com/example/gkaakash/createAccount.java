package com.example.gkaakash;

import java.util.Vector;

import com.gkaakash.controller.Group;
import com.gkaakash.controller.Startup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class createAccount<group> extends Activity implements OnItemSelectedListener {
	// Declaring variables
	String accCodeCheckFlag;
	TextView tvaccCode, tvDbOpBal, tvOpBal,tvAccName,tvAccCode;
	EditText etaccCode, etDtOpBal, etOpBal,etAccCode;
	Spinner sgrpName,sSearchBy,sAccName;
	Button btnCreateAccSave,btnCreateAccFinish,btnokdialog;
	Integer client_id;
		private int group1Id = 1;
		int Edit = Menu.FIRST;
		int Delete = Menu.FIRST +1;
		int Finish = Menu.FIRST +2;
		AlertDialog dialog;
		final Context context = this;
		Dialog screenDialog;
		private Group group;
		static final int ID_SCREENDIALOG = 1;
		
		 
		//adding options to the options menu
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    menu.add(group1Id, Edit, Edit, "Edit");
	    menu.add(group1Id, Delete, Delete, "Delete");
	    menu.add(group1Id, Finish, Finish, "Finish");
	    return super.onCreateOptionsMenu(menu); 
	    }
		
		//code for the actions to be performed on clicking options menu goes here ...
		 @Override
		 public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    case 1:
		    	final CharSequence[] items = {  "Account Name","Account Code" };
				//creating a dialog box for popup
		        AlertDialog.Builder builder = new AlertDialog.Builder(context);
		        //setting title
		        builder.setTitle("Search Account By");
		       
		        //adding items
		        builder.setItems(items, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int pos) {
		        	//code for the actions to be performed on clicking popup item goes here ...
		            switch (pos) {
		                case 0:
		                              {
		                            	  final CharSequence[] items = {  "ABC","PQR","LMN" };
		                  				//creating a dialog box for popup
		                  		        AlertDialog.Builder builder = new AlertDialog.Builder(context);
		                  		        //setting title
		                  		        builder.setTitle("Account Name");
		                  		       
		                  		        //adding items
		                  		        builder.setItems(items, new DialogInterface.OnClickListener() {
		                  		        public void onClick(DialogInterface dialog, int pos) {
		                  		        	//code for the actions to be performed on clicking popup item goes here ...
		                  		            switch (pos) {
		                  		                case 0:
		                  		                              {
		                  		                            	 
		                  		                            	 
		                  										
		                  		                      }break;
		                  		            }
		                  		        }});
		                  		        //building a complete dialog
		                  				dialog=builder.create();
		                  				((Dialog) dialog).show();
		                  				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

		                				lp.copyFrom(((Dialog) dialog).getWindow().getAttributes());
		                				lp.width = 310;
		                				
		                				
		                				((Dialog) dialog).getWindow().setAttributes(lp);
		                  				
		                            	 
										
		                      }break;
		                case 1:
                        {
                      	  final CharSequence[] items = {  "ACC01","ACC02","ACC03" };
            				//creating a dialog box for popup
            		        AlertDialog.Builder builder = new AlertDialog.Builder(context);
            		        //setting title
            		        builder.setTitle("Account Code");
            		       
            		        //adding items
            		        builder.setItems(items, new DialogInterface.OnClickListener() {
            		        public void onClick(DialogInterface dialog, int pos) {
            		        	//code for the actions to be performed on clicking popup item goes here ...
            		            switch (pos) {
            		                case 0:
            		                              {
            		                            	 
            		                            	 
            										
            		                      }break;
            		            }
            		        }});
            		        //building a complete dialog
            				dialog=builder.create();
            				((Dialog) dialog).show();
            				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

          				lp.copyFrom(((Dialog) dialog).getWindow().getAttributes());
          				lp.width = 310;
          				
          				
          				((Dialog) dialog).getWindow().setAttributes(lp);
             }break;
		            }
		        }});
		        //building a complete dialog
				dialog=builder.create();
				dialog.show();
				
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.width = 310;
				dialog.getWindow().setAttributes(lp);
			 
		    return true;
		    }
		    return super.onOptionsItemSelected(item);
		}
		
		 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Calling create_account.xml
		setContentView(R.layout.create_account);
		/*client_id =Startup.getClient_id();
		//create the object of Group class
				/*group = new Group();
				//call the getAllGroups method to get all groups
				Object[] groupnames = (Object[]) group.getAllGroups(client_id);
				System.out.println(groupnames +" present");
				for(Object gs : groupnames)
				{	
					Object[] g = (Object[]) gs;
						System.out.println(g[0]); //groupcode
						System.out.println(g[1]); //groupname
						System.out.println(g[2]); //description
				}	
				*/
		// Request a reference to the button from the activity by calling
		// “findViewById” and assign the retrieved button to an instance
		// variable
		tvaccCode = (TextView) findViewById(R.id.tvAccCode);
		etaccCode = (EditText) findViewById(R.id.etAccCode);
		// Retrieving the account code flag value from the previous
		// page(preferences page)
		accCodeCheckFlag = getIntent().getExtras().getString("flag");
		// Setting visibility on load
		etaccCode.setVisibility(EditText.VISIBLE);
		tvaccCode.setVisibility(TextView.VISIBLE);
		// Setting visibility depending upon account code flag value
		if (accCodeCheckFlag == null) {
			etaccCode.setVisibility(EditText.GONE);
			tvaccCode.setVisibility(TextView.GONE);
		} else {
			etaccCode.setVisibility(EditText.VISIBLE);
			tvaccCode.setVisibility(TextView.VISIBLE);
		}
		
		sgrpName = (Spinner) findViewById(R.id.sGroupNames);
		// Attach a listener to the group name Spinner
		sgrpName.setOnItemSelectedListener((OnItemSelectedListener) this);
		addListeneronButton();
	}

	private void addListeneronButton() {
		// TODO Auto-generated method stub
		btnCreateAccSave = (Button) findViewById(R.id.btnCreateAccSave);
		btnCreateAccFinish = (Button) findViewById(R.id.btnCreateAccFinish);
		final Context context = this;
		btnCreateAccFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// To pass on the activity to the next page
				Intent intent = new Intent(context, menu.class);
				intent.putExtra("flag", accCodeCheckFlag);
				// To pass on the value to the next page
				startActivity(intent);
			}

		});
	}

	public void onItemSelected(AdapterView<?> parent, View v, int position,
			long id) {
		// Retrieving the selected name from the group name Spinner and
		// assigning it to a variable
		String selectedGrpName = parent.getItemAtPosition(position).toString();
		tvOpBal = (TextView) findViewById(R.id.tvOpBal);
		etOpBal = (EditText) findViewById(R.id.etOpBal);

		// Comparing the variable value to group name and setting visibility
		if ("Current Asset".equals(selectedGrpName)
				| "Investment".equals(selectedGrpName)
				| "Loans(Asset)".equals(selectedGrpName)
				| "Fixed Assets".equals(selectedGrpName)
				| "Miscellaneous Expenses(Asset)".equals(selectedGrpName)) {
			etOpBal.setVisibility(EditText.VISIBLE);
			tvOpBal.setVisibility(TextView.VISIBLE);
			tvOpBal.setText("Debit Opening Balance");

		} else if ("Direct Income".equals(selectedGrpName)
				| "Direct Expense".equals(selectedGrpName)
				| "Indirect Income".equals(selectedGrpName)
				| "Indirect Expense".equals(selectedGrpName)) {
			etOpBal.setVisibility(EditText.GONE);
			tvOpBal.setVisibility(TextView.GONE);
		} else {
			etOpBal.setVisibility(EditText.VISIBLE);
			tvOpBal.setVisibility(TextView.VISIBLE);
			tvOpBal.setText("Credit Opening Balance");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// IGNORE THIS METHOD!!!
	}
}