package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class selectOrg extends Activity{
	Object[] orgNameList;
	Spinner getOrgNames;
	private Spinner getFinancialyear;
	private Startup startup;
	private User user;
	private Button bProceed,btnSelLogIn;
	Object[] financialyearList;
	final Context context = this;
	private Button btnDeleteOrg;
	protected AdapterView<SpinnerAdapter> parent;
	protected Object selectedFinancialYear;
	//static String existingOrgFlag;
	protected static Integer client_id;
	protected static String selectedOrgName;
	protected static String fromDate;
	protected static String  toDate;
	RadioGroup radioUserGroup,radioUserAdminGroup;
	RadioButton rb_admin,rb_guest,rb_manager,rb_operator;
	private EditText eloginPassword;
	private EditText eloginUsername;
	static String user_role;
	String loginUsername,login_user,login_password;
	TextView tvwarning,tvLoginWarning,tvSignUp,tvuserrole;
	private module module;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_org);

		MainActivity.no_dailog = true; //comment this line if running this app on emulator

		// set flag to true , if we are in existing organisation
		//existingOrgFlag="true";
		// call startup to get client connection 
		startup = new Startup();
		user= new User();
		module = new module();
		getOrgNames = (Spinner) findViewById(R.id.sGetOrgNames);
		getFinancialyear = (Spinner) findViewById(R.id.sGetFinancialYear);
		getOrgNames.setMinimumWidth(100);
		getFinancialyear.setMinimumWidth(250);
		//bProceed = (Button) findViewById(R.id.bProceed);
		btnSelLogIn = (Button) findViewById(R.id.btnSelLogIn);
		//btnDeleteOrg = (Button) findViewById(R.id.btnDeleteOrg);
		getExistingOrgNames();
		addListenerOnItem();
		addListenerOnButton();

	}// End of onCreate

	// getExistingOrgNames()
	void getExistingOrgNames(){

		//call getOrganisationNames method 
		orgNameList = startup.getOrgnisationName();

		System.out.println(orgNameList);
		List<String> list = new ArrayList<String>();

		for(Object st : orgNameList)
			list.add((String) st);

		// creating array adaptor to take list of existing organisation name
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		//set resource layout of spinner to that adaptor
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//set adaptor with orglist in spinner
		getOrgNames.setAdapter(dataAdapter);

	}// End of getExistingOrgNames()


	//Attach a listener to the click event for the button
	private void addListenerOnButton(){
		final Context context = this;
		btnSelLogIn.setOnClickListener(new OnClickListener() {

			private Object[] deployparams;
			private AlertDialog dialog;

			@Override
			public void onClick(View v) {

				//parameters pass to core_engine xml_rpc functions
				deployparams=new Object[]{selectedOrgName,fromDate,toDate};
				//call method login from startup.java 
				client_id = startup.login(deployparams);
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.login, (ViewGroup) findViewById(R.id.layout_login));
				final AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
				builder.setTitle("Log In to "+selectedOrgName);
				builder.setMessage("Log In For Financial Year "+fromDate+" to "+toDate);
				radioUserGroup = (RadioGroup)layout.findViewById(R.id.radioUser);
				rb_admin =(RadioButton) layout.findViewById(R.id.rbAdmin);
				rb_guest =(RadioButton) layout.findViewById(R.id.rbGuest);
				rb_manager =(RadioButton) layout.findViewById(R.id.rbManager);
				rb_operator =(RadioButton) layout.findViewById(R.id.rbOperator);
				eloginUsername =(EditText) layout.findViewById(R.id.eLoginUser);
				eloginPassword =(EditText) layout.findViewById(R.id.eLoginPassword);
				tvLoginWarning =(TextView) layout.findViewById(R.id.tvLoginWarning);
				rb_manager.setVisibility(View.VISIBLE);
				rb_operator.setVisibility(View.VISIBLE);

				Button login =  (Button) layout.findViewById(R.id.btnLogin);

				//radioUserAdminGroup = (RadioGroup)layout.findViewById(R.id.radioAdminUser);
				addRadioListnerOnItem(layout);
				//Toast.makeText(createOrg.this,"exist "+is_user_exist, Toast.LENGTH_SHORT).show();
				login.setOnClickListener(new View.OnClickListener(){
					
					public void onClick(View v) {
						login_user = eloginUsername.getText().toString();
						login_password = eloginPassword.getText().toString();

						Toast.makeText(selectOrg.this,user_role, Toast.LENGTH_SHORT).show();

						boolean isadmin = user.isAdmin(client_id);
						Object[] params = new Object[]{login_user,login_password,user_role};
						if(!module.isEmpty(params)){
							if(isadmin==true)
							{

								boolean is_user_exist = user.isUserExist(params, client_id);
								if(is_user_exist==true)
								{
									//To pass on the activity to the next page  
									Intent intent = new Intent(context,menu.class); 
									startActivity(intent); 


								}else{
									if(user_role.equals("guest")){
										tvLoginWarning.setText("Authentication Failed!!");
									}else
									{
										tvLoginWarning.setText("Please enter correct username and password or choose proper role");
									}
								}
							}else{
								if(!user_role.equals("guest")){
									tvLoginWarning.setText("Authentication Failed!! Please Choose correct role " );
								}else
								{

									//To pass on the activity to the next page  
									Intent intent = new Intent(context,menu.class);
									startActivity(intent); 
								}
							}
						}
						else{
							tvLoginWarning.setText("Please fill empty field");
						}
						// tvuserrole = (TextView) layout.findViewById(R.id.tvUserRole);
						//System.out.println("login "+ client_id);

					}
				});

				dialog = builder.create();
				dialog.show();
				dialog.setCanceledOnTouchOutside(true);
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				//customizing the width and location of the dialog on screen 
				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.width = 750;
				dialog.getWindow().setAttributes(lp);
			}

		});

		/*btnDeleteOrg.setOnClickListener(new OnClickListener() {

			private Object[] deleteprgparams;
			private Boolean deleted;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
		        builder.setMessage("Are you sure you want to permanetly delete "+selectedOrgName+" for financialyear "+fromDate+" To "+toDate+"?\n" +
		        		"if you will delete an item , It will be permanetly lost ")
		                .setCancelable(false)
		                .setPositiveButton("Delete",
		                        new DialogInterface.OnClickListener() {
		                            public void onClick(DialogInterface dialog, int id) {
		                            	//parameters pass to core_engine xml_rpc functions
		                				deleteprgparams=new Object[]{selectedOrgName,fromDate,toDate};
		                				deleted = startup.deleteOrgnisationName(deleteprgparams);
		                		    	String[] list = new String[]{""};
		                				getExistingOrgNames();
		                				addListenerOnItem();
	                					addListenerOnButton();

		                		    	Object value = getOrgNames.getSelectedItem();

		                				if(value==null)
		                				{
		                					System.out.println("in");
		                					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
		                			    			android.R.layout.simple_spinner_item, list);

		                			    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		                					getFinancialyear.setAdapter(dataAdapter);
		                				}

		                            }
		                        })
		                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                        dialog.cancel();
		                    }
		                });
		        AlertDialog alert = builder.create();
		        alert.show();

			}


		});*/

	}

	void addListenerOnItem(){
		//Attach a listener to the states Type Spinner to get dynamic list of cities
		getOrgNames.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				//Retrieving the selected org type from the Spinner and assigning it to a variable 
				selectedOrgName = parent.getItemAtPosition(position).toString();
				//call getFinancialYear method from startup.java 
				//it will give you financialYear list according to orgname
				financialyearList = startup.getFinancialYear(selectedOrgName);

				List<String> financialyearlist = new ArrayList<String>();

				for(Object fy : financialyearList)
				{
					Object[] y = (Object[]) fy;
					// concatination From and To date 
					financialyearlist.add(y[0]+" to "+y[1]);
					//fromDate=y[0].toString();
					//toDate=y[1].toString();
				}

				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, financialyearlist);

				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

				getFinancialyear.setAdapter(dataAdapter);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});// End of getOrgNames.setOnItemSelectedListener
		getFinancialyear.setOnItemSelectedListener(new OnItemSelectedListener() {

			private String[] finallist;


			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				// TODO Auto-generated method stub
				selectedFinancialYear = parent.getItemAtPosition(position).toString();
				finallist = selectedFinancialYear.toString().split(" to ");
				fromDate = finallist[0];
				toDate = finallist[1];

				//String fromDate = Startup.setOrgansationname((String)fromDate);
				Startup.setfinancialFromDate(fromDate);
				Startup.setFinancialToDate(toDate);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	} // end of addListenerOnItem()

	// add Checked change listner on radioGroup 
	public void addRadioListnerOnItem(final View layout)
	{
		radioUserGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup rg, int selctedId) {

				//int selcetdRadoiId = radioUserGroup.getCheckedRadioButtonId();
				RadioButton selctedradio =(RadioButton) layout.findViewById(selctedId);
				user_role = selctedradio.getText().toString();
				if(user_role.equals("guest"))
				{
					eloginUsername.setText("guest");
					eloginPassword.setText("guest");
				}
				if(rb_admin.isChecked()){
					eloginUsername.setText("");
					eloginPassword.setText("");
					Toast.makeText(selectOrg.this,"admin checked", Toast.LENGTH_SHORT).show();
				}
				

			}



		});
	}

}// End of Class 
