package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.gkaakash.controller.Account;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class createAccount<group> extends Activity{
	// Declaring variables
	String accCodeCheckFlag;
	TextView tvaccCode, tvDbOpBal, tvOpBal,tvAccName,tvAccCode;
	EditText etaccCode, etDtOpBal, etOpBal,etAccCode;
	Spinner sgrpName,sSearchBy,sAccName;
	Button btnCreateAccSave,btnCreateAccFinish,btnokdialog;
	static Integer client_id;
		private int group1Id = 1;
		int Edit = Menu.FIRST;
		int Delete = Menu.FIRST +1;
		int Finish = Menu.FIRST +2;
		AlertDialog dialog;
		final Context context = this;
		Dialog screenDialog;
		private Group group;
		private Startup startup;
		private Spinner ssubGrpName;
		private TextView tvSubGrp;
		private EditText etSubGrp;
		protected String selGrpName;
		protected String selSubGrpName;
		private EditText etAccName;
		protected String accountname;
		protected String accountcode;
		protected String openingbalance;
		private Account account;
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
		    	final CharSequence[] items = {  "Account name","Account code" };
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
		// create the object of Group class
		group = new Group();
		account = new Account();
		// create the object of Group class
		startup = new Startup();
		System.out.println("groups :"+group);
		// get Client_id return by Deploy method
		client_id = startup.getClient_id();
		//Object[] params = new Object[]{"eee","2012-8-27 ","2013-8-26 "};
		
		// Request a reference to the button from the activity by calling
		// “findViewById” and assign the retrieved button to an instance variable
		tvaccCode = (TextView) findViewById(R.id.tvAccCode);
		etaccCode = (EditText) findViewById(R.id.etAccCode);
		tvSubGrp = (TextView) findViewById(R.id.tvSubGrp);
		etSubGrp = (EditText) findViewById(R.id.etSubGrp);
		etAccName= (EditText) findViewById(R.id.etAccName);
		// Setting visibility on load
		//tvSubGrp.setVisibility(EditText.GONE);
		//etSubGrp.setVisibility(TextView.GONE);
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
		ssubGrpName = (Spinner) findViewById(R.id.sSubGrpNames);
		
		addListeneronButton();
		getExistingGroupNames();
		//creating interface to listen activity on Item 
		addListenerOnItem();
	}
	// getExistingGroupNames()()
	void getExistingGroupNames(){
			
		//call the getAllGroups method to get all groups
		Object[] groupnames = (Object[]) group.getAllGroups(client_id);
		// create new arraylist of type String to add gropunames
		List<String> groupnamelist = new ArrayList<String>();
		// create new arraylist of type Integer to add gropcode
    	List<Integer> groupcodelist = new ArrayList<Integer>();
    	
		for(Object gs : groupnames)
		{	
			Object[] g = (Object[]) gs;
			groupcodelist.add((Integer) g[0]); //groupcode
			groupnamelist.add((String) g[1]); //groupname
			//groupdesc.add(g[2]); //description
		}	
    	// creating array adaptor to take list of existing group name
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    			android.R.layout.simple_spinner_item, groupnamelist);
    	//set resource layout of spinner to that adaptor
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    //set adaptor with groupname list in spinner
    	sgrpName.setAdapter(dataAdapter);
    	
	}// End getExistingGroupNames()
	
	
	void addListenerOnItem(){
		//Attach a listener to the states Type Spinner to get dynamic list of subgroupname
		sgrpName.setOnItemSelectedListener(new OnItemSelectedListener() {
			

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				// Retrieving the selected name from the group name Spinner and
				// assigning it to a variable
				selGrpName = parent.getItemAtPosition(position).toString();
				tvOpBal = (TextView) findViewById(R.id.tvOpBal);
				etOpBal = (EditText) findViewById(R.id.etOpBal);

				// Comparing the variable value to group name and setting visibility
				if ("Current Asset".equals(selGrpName)
						| "Investment".equals(selGrpName)
						| "Loans(Asset)".equals(selGrpName)
						| "Fixed Assets".equals(selGrpName)
						| "Miscellaneous Expenses(Asset)".equals(selGrpName)) {
					etOpBal.setVisibility(EditText.VISIBLE);
					tvOpBal.setVisibility(TextView.VISIBLE);
					tvOpBal.setText("Debit opening balance");

				} else if ("Direct Income".equals(selGrpName)
						| "Direct Expense".equals(selGrpName)
						| "Indirect Income".equals(selGrpName)
						| "Indirect Expense".equals(selGrpName)) {
					etOpBal.setVisibility(EditText.GONE);
					tvOpBal.setVisibility(TextView.GONE);
				} else {
					etOpBal.setVisibility(EditText.VISIBLE);
					tvOpBal.setVisibility(TextView.VISIBLE);
					tvOpBal.setText("Credit opening balance");
				}
				
			
				// checks for the selected value of item is not null
				if(selGrpName!=null){
					// create new arraylist of type String to add subgroupnames
					List<String> subgroupnamelist = new ArrayList<String>();
					// input params contains groupname
					Object[] params = new Object[]{selGrpName};
					// call com.gkaakash.controller.Group.getSubGroupsByGroupName pass params
					Object[] subgroupnames = (Object[])group.getSubGroupsByGroupName(params,client_id);
					// loop through subgroupnames list 
					for(Object sbgrp : subgroupnames)
			    	
			    		subgroupnamelist.add((String)sbgrp);

			    	// creating array adaptor to take list of subgroups 
			    	ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(context,
			    			android.R.layout.simple_spinner_item, subgroupnamelist);
			    	// set resource layout of spinner to that adaptor
			    	dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    	// set Adaptor contain subgroups list to spinner 
			    	ssubGrpName.setAdapter(dataAdapter1);
		    	}// End of if condition
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});// End of sgrpName.setOnItemSelectedListener
		
		//Attach a listener to the states Type Spinner to show or hide Subgroupname text filed
		ssubGrpName.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				// get the current value of subgroup spinner
				selSubGrpName = parent.getItemAtPosition(position).toString();
				
				if("Create New Sub-Group".equals(selSubGrpName))
				{
					tvSubGrp.setVisibility(EditText.VISIBLE);
					etSubGrp.setVisibility(TextView.VISIBLE);
					//etSubGrp.setFocusable(true);
				}// End of if condition
				else{
					tvSubGrp.setVisibility(EditText.GONE);
					etSubGrp.setVisibility(TextView.GONE);
				}// End of else condition
					
			}// End of onItemSelected

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void addListeneronButton() {
		// TODO Auto-generated method stub
		btnCreateAccSave = (Button) findViewById(R.id.btnCreateAccSave);
		btnCreateAccFinish = (Button) findViewById(R.id.btnCreateAccFinish);
		final Context context = this;
		btnCreateAccFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.out.println("accCodeCheckFlag "+accCodeCheckFlag);
				// To pass on the activity to the next page
				Intent intent = new Intent(context, menu.class);
				intent.putExtra("flag", accCodeCheckFlag);
				// To pass on the value to the next page
				startActivity(intent);
			}

		});
		btnCreateAccSave.setOnClickListener(new OnClickListener() {

			private String newsubgrpname;

			@Override
			public void onClick(View arg0) {
				
				newsubgrpname = etSubGrp.getText().toString();
				accountname = etAccName.getText().toString();
				accountcode = etaccCode.getText().toString();
				openingbalance= etOpBal.getText().toString();
				etOpBal.setText("0.00");
				if("".equals(accountname)|"".equals(openingbalance))
				{
					
					Toast.makeText(context,"TextFields cannot be blank",Toast.LENGTH_SHORT).show();
					
				}
				else
				{
					Object[] params = new Object[]{accCodeCheckFlag,selGrpName,selSubGrpName,newsubgrpname,accountname,accountcode,openingbalance}; 
					//call the getAllGroups method to get all groups
					String setaccount = (String) account.setAccount(params,client_id);
					// To pass on the activity to the next page
					Intent intent = new Intent(context, createAccount.class);
					intent.putExtra("flag", accCodeCheckFlag);
					// To pass on the value to the next page
					startActivity(intent);
				}
			}

		});
	}
	
}