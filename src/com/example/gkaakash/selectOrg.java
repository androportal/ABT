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
import android.text.Html;
import android.text.LoginFilter.UsernameFilterGeneric;
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
	String loginUsername,login_password;
	static String login_user;
	TextView tvwarning,tvLoginWarning,tvSignUp,tvuserrole,link;
	private module m;
	String get_extra_flag;
	static boolean reset_password_flag = false;
	static String IPaddr;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_org);

//		MainActivity.username_flag=true;
		//MainActivity.no_dailog = true; //comment this line if running this app on emulator

		// set flag to true , if we are in existing organisation
		//existingOrgFlag="true";
		// call startup to get client connection 
		IPaddr = MainActivity.IPaddr;
		System.out.println("in createorg"+IPaddr);
		startup = new Startup(IPaddr);
		user= new User(IPaddr);
		m = new module();

//		Bundle extras = getIntent().getExtras();
//		if (extras == null) {
//			System.out.println("don hav xtra");
//		} else {  
//
//			get_extra_flag = extras.getString("flag");
//			System.out.println("hav xtra");
//			System.out.println("content:"+get_extra_flag);
//
//		}


//		getOrgNames = (Spinner) findViewById(R.id.sGetOrgNames);
//		getFinancialyear = (Spinner) findViewById(R.id.sGetFinancialYear);
//		getOrgNames.setMinimumWidth(100);
//		getFinancialyear.setMinimumWidth(250);
//		btnSelLogIn = (Button) findViewById(R.id.btnSelLogIn);
//		
//		getExistingOrgNames();
//		addListenerOnItem();
//		addListenerOnButton();

	}// End of onCreate

//	// getExistingOrgNames()
//	void getExistingOrgNames(){
//
//		//call getOrganisationNames method 
//		orgNameList = startup.getOrgnisationName();
//
//		System.out.println(orgNameList);
//		List<String> list = new ArrayList<String>();
//
//		for(Object st : orgNameList)
//			list.add((String) st);
//
//		if (get_extra_flag == null) {
//			// creating array adaptor to take list of existing organisation name
//			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//					android.R.layout.simple_spinner_item, list);
//			//set resource layout of spinner to that adaptor
//			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			//set adaptor with orglist in spinner
//			getOrgNames.setAdapter(dataAdapter);
//			
//		}else if ("from_menu".equals(get_extra_flag)) {
//			String orgname = menu.OrgName;
//				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//					android.R.layout.simple_spinner_item, list);
//			dataAdapter
//			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			int position = dataAdapter.getPosition(orgname);
//			
//			getOrgNames.setAdapter(dataAdapter);
//			getOrgNames.setSelection(position);
//			//Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
//		}
//
//	}// End of getExistingOrgNames()


//	//Attach a listener to the click event for the button
//	private void addListenerOnButton(){
//		final Context context = this;
//		btnSelLogIn.setOnClickListener(new OnClickListener() {
//
//			private Object[] deployparams;
//			private AlertDialog dialog;
//
//			@Override
//			public void onClick(View v) {
//
//				//parameters pass to core_engine xml_rpc functions
//				deployparams=new Object[]{selectedOrgName,fromDate,toDate};
//				//call method login from startup.java 
//				client_id = startup.login(deployparams);
//				final boolean isadmin = user.isAdmin(client_id);
//				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//				final View layout = inflater.inflate(R.layout.login, (ViewGroup) findViewById(R.id.layout_login));
//				final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//				builder.setView(layout);
//				TextView tvalertHead1 =(TextView) layout.findViewById(R.id.tvalertHead1);
//				tvalertHead1.setText(Html.fromHtml("Log In to <b>"+selectedOrgName+"</b>"));
//				TextView tvalertHead2 =(TextView) layout.findViewById(R.id.tvalertHead2);
//				tvalertHead2.setText(Html.fromHtml("Financial Year: <b>"+fromDate+"</b> to <b>"+toDate+"</b>"));
//
//				radioUserGroup = (RadioGroup)layout.findViewById(R.id.radioUser);
//				rb_admin =(RadioButton) layout.findViewById(R.id.rbAdmin);
//				rb_guest =(RadioButton) layout.findViewById(R.id.rbGuest);
//				rb_manager =(RadioButton) layout.findViewById(R.id.rbManager);
//				rb_operator =(RadioButton) layout.findViewById(R.id.rbOperator);
//				eloginUsername =(EditText) layout.findViewById(R.id.eLoginUser);
//				eloginPassword =(EditText) layout.findViewById(R.id.eLoginPassword);
//				tvLoginWarning =(TextView) layout.findViewById(R.id.tvLoginWarning);
//				link =(TextView) layout.findViewById(R.id.tvlink);
//
//				tvLoginWarning.setVisibility(TextView.GONE);
//
//				tvSignUp =(TextView) layout.findViewById(R.id.tvSignUp);
//				tvSignUp.setVisibility(TextView.GONE);
//				if(isadmin)
//				{
//					rb_guest.setVisibility(View.GONE);
//					rb_manager.setVisibility(View.VISIBLE);  
//					rb_operator.setVisibility(View.VISIBLE);
//				}
//				else{
//					rb_guest.setVisibility(View.VISIBLE);
//				
//					rb_admin.setVisibility(View.GONE);
//					
//				}
//				
//
//				link.setOnClickListener(new OnClickListener() { 
//
//					@Override 
//					public void onClick(View arg0) { 
//						if(!(eloginUsername.getText().toString().equals(""))){
//							Object[] unique = (Object[]) user.getUserRole(new Object[]{eloginUsername.getText().toString()},client_id);
//							if((unique.length != 0) && (unique[0].toString().equalsIgnoreCase("Admin"))){
//								LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//								final View layout = inflater.inflate(R.layout.forgot_password, null);
//								final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//								builder.setView(layout);
//								final EditText answer =(EditText) layout.findViewById(R.id.etAnswer);
//								TextView header =(TextView) layout.findViewById(R.id.tvheader);
//								final Spinner Question =(Spinner) layout.findViewById(R.id.SpQuestions);
//
//								header.setText("Answer the security question to get access");
//								Button Ok =(Button) layout.findViewById(R.id.btnOK);
//								Button Cancel =(Button) layout.findViewById(R.id.btnCancel);
//								Cancel.setOnClickListener(new OnClickListener() {
//
//									@Override
//									public void onClick(View arg0) {
//										dialog.cancel();
//										tvLoginWarning.setVisibility(View.GONE);
//									}
//								});
//
//								Ok.setOnClickListener(new OnClickListener() {
//
//									@Override
//									public void onClick(View arg0) {
//
//										int position = Question.getSelectedItemPosition();
//										String ans =answer.getText().toString();
//										TextView errormsg =(TextView) layout.findViewById(R.id.tverror_msg);
//										Boolean result = user.AdminForgotPassword(new Object[]{position,ans,"admin"},client_id);
//										
//										if(result==true){
//											Intent intent = new Intent(context,menu.class); 
//											reset_password_flag = true;
//											login_user = eloginUsername.getText().toString();
//											startActivity(intent); 
//										}else if ("".equals(ans)) {
//											errormsg.setVisibility(View.VISIBLE);
//											errormsg.setText("Please fill the field!");
//										}else {
//											errormsg.setVisibility(View.VISIBLE);
//											errormsg.setText("Invalid input,please try again!");
//											answer.setText("");
//										}
//									}
//								});
//
//								dialog = builder.create();
//								dialog.show();
//							}else{
//								eloginUsername.setText("");
//								eloginPassword.setText("");
//								tvLoginWarning.setVisibility(View.VISIBLE);
//								tvLoginWarning.setText("User is not present");
//							}
//							
//							
//						}
//						else{
//							tvLoginWarning.setVisibility(View.VISIBLE);
//							tvLoginWarning.setText("Please enter username");
//						}
//						
//					}
//				});
//
//				Button login =  (Button) layout.findViewById(R.id.btnLogin);
//				addRadioListnerOnItem(layout);
//				login.setOnClickListener(new View.OnClickListener(){
//
//					public void onClick(View v) {
//						login_user = eloginUsername.getText().toString();
//						login_password = eloginPassword.getText().toString();
//
//						Object[] params = new Object[]{login_user,login_password,user_role};
//					
//							if(!m.isEmpty(params)){
//								if(isadmin==true)
//								{
//									if(rb_admin.isChecked()||rb_manager.isChecked()||rb_operator.isChecked())
//									{
//									boolean is_user_exist = user.isUserExist(params, client_id);
//									if(is_user_exist==true)
//									{
//										//To pass on the activity to the next page  
//										Intent intent = new Intent(context,menu.class); 
//										startActivity(intent); 
//
//
//									}else{
//										
//										
//											tvLoginWarning.setVisibility(TextView.VISIBLE);
//											tvLoginWarning.setText("Please enter correct username and password or choose proper role");
//										
//									}
//									}else
//									{
//										tvLoginWarning.setVisibility(TextView.VISIBLE);
//										tvLoginWarning.setText("Please select role");
//									}
//								}else{
//									
//									if ((login_user.equals("guest"))&&(login_password.equals("guest")))
//									{
//										//To pass on the activity to the next page  
//										Intent intent = new Intent(context,menu.class);
//										startActivity(intent); 
//									}else
//									{
//										tvLoginWarning.setVisibility(TextView.VISIBLE);
//										tvLoginWarning.setText("Username and Password is incorrect");
//
//									}		
//								}
//							}
//							else{
//								tvLoginWarning.setVisibility(TextView.VISIBLE);
//								tvLoginWarning.setText("Please fill empty field");
//							}
//						}
//
//
//				});
//
//				dialog = builder.create();
//				dialog.show();
//				dialog.setCanceledOnTouchOutside(true);
//				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//				//customizing the width and location of the dialog on screen 
//				lp.copyFrom(dialog.getWindow().getAttributes());
//				lp.width = 750;
//				dialog.getWindow().setAttributes(lp);
//			}
//
//		});
//
//	
//	}

//	void addListenerOnItem(){
//		//Attach a listener to the states Type Spinner to get dynamic list of cities
//		getOrgNames.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
//				//Retrieving the selected org type from the Spinner and assigning it to a variable 
//				selectedOrgName = parent.getItemAtPosition(position).toString();
//				//call getFinancialYear method from startup.java 
//				//it will give you financialYear list according to orgname
//				financialyearList = startup.getFinancialYear(selectedOrgName);
//
//				List<String> financialyearlist = new ArrayList<String>();
//
//				for(Object fy : financialyearList)
//				{
//					Object[] y = (Object[]) fy;
//					// concatination From and To date 
//					financialyearlist.add(y[0]+" to "+y[1]);
//					//fromDate=y[0].toString();
//					//toDate=y[1].toString();
//				}
//
//				if(get_extra_flag==null){
//					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
//							android.R.layout.simple_spinner_item, financialyearlist);
//
//					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//					getFinancialyear.setAdapter(dataAdapter);
//				}else {
//					String date = menu.rollover+" to "+menu.givenToDateString;
//					System.out.println("date:" + date);
//					ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
//							android.R.layout.simple_spinner_item, financialyearlist);
//					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//					int postion1=dataAdapter.getPosition(date);
//					System.out.println("position:"+postion1);
//					getFinancialyear.setAdapter(dataAdapter);
//					getFinancialyear.setSelection(postion1);
//
//				} 
//			}
//
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});// End of getOrgNames.setOnItemSelectedListener
//		getFinancialyear.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			private String[] finallist;
//
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
//				// TODO Auto-generated method stub
//				selectedFinancialYear = parent.getItemAtPosition(position).toString();
//				finallist = selectedFinancialYear.toString().split(" to ");
//				fromDate = finallist[0];
//				toDate = finallist[1];
//
//				//String fromDate = Startup.setOrgansationname((String)fromDate);
//				Startup.setfinancialFromDate(fromDate);
//				Startup.setFinancialToDate(toDate);
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	} // end of addListenerOnItem()

//	// add Checked change listner on radioGroup 
//	public void addRadioListnerOnItem(final View layout)
//	{
//		radioUserGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			public void onCheckedChanged(RadioGroup rg, int selctedId) {
//				tvLoginWarning.setVisibility(TextView.GONE);
//				//int selcetdRadoiId = radioUserGroup.getCheckedRadioButtonId();
//				RadioButton selctedradio =(RadioButton) layout.findViewById(selctedId);
//				user_role = selctedradio.getText().toString();
//				if(user_role.equals("guest"))
//				{
//					eloginUsername.setText("guest");
//					eloginPassword.setText("guest");
//				}
//				if(rb_admin.isChecked()){
//					eloginUsername.setText("");
//					eloginPassword.setText("");
//					link.setVisibility(View.VISIBLE);
//					//Toast.makeText(selectOrg.this,"admin checked", Toast.LENGTH_SHORT).show();
//				}else {
//					link.setVisibility(View.INVISIBLE);
//				}
//
//
//			}
//
//
//
//		});
//	}

}// End of Class 