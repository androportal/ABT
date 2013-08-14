package com.example.gkaakash;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRouter.UserRouteInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class createOrg extends MainActivity {
	//Declaring variables 
	TextView tvDisplayFromDate, tvDisplayToDate,tvwarning,tvLoginWarning,tvSignUp,tvuserrole;
	Button btnChangeFromDate, btnChangeToDate, btnCreate,btnLogin,btnNext;
	static int year, month, day, toYear, toMonth, toDay;
	static final int FROM_DATE_DIALOG_ID = 0;
	static final int TO_DATE_DIALOG_ID = 1;
	Spinner orgType, sQuestions; 
	RadioButton rb_admin,rb_guest,radioButtonValue;
	RadioGroup radioUserGroup,radioGender,radioUserAdminGroup;
	String org;
	static String organisationName,orgTypeFlag,selectedOrgType,todate;
	static String fromdate,user_role;
	AlertDialog dialog;
	final Calendar c = Calendar.getInstance();
	final Context context = this;
	private EditText orgName;
	Object[] deployparams;
	private boolean setOrgDetails;
	DecimalFormat mFormat;
	private Object[] orgNameList;
	Object[] financialyearList;
	boolean orgExistFlag,createorgflag;
	static Integer client_id;
	int genderid, selectedQuestion;
	static String answer,lastname,username ,password ,confpassword,loginPassword;
	String loginUsername,login_password;
	static String login_user;
	private EditText eloginPassword ,eUserName ,ePassword , eConfPassword, eAnswer;
	private EditText eloginUsername;
	boolean adminflag=false;
	protected Object[] orgparams;
	private Organisation orgnisation;
	User user;
	private module module;
	static String IPaddr;
	Startup  startup;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling create_org.xml
		setContentView(R.layout.create_org);
		MainActivity.username_flag = false;
		System.out.println("in createorg1");
		IPaddr = MainActivity.IPaddr;
//		user = new User(IPaddr);
//		startup = new Startup(IPaddr);
//	    System.out.println("in createorg2");
//		
//		//for two digit format date for dd and mm
//		mFormat= new DecimalFormat("00");
//		mFormat.setRoundingMode(RoundingMode.DOWN);
//
//		//Declaring new method for setting date into "from date" and "to date" textview
//		setDateOnLoad();
//		/*
//		 * creating a new interface for showing a date picker dialog that
//		 * allows the user to select financial year start date and to date
//		 */
//		addListeneronDateButton();
//		addListeneronCreateButton();
//		orgType = (Spinner) findViewById(R.id.sOrgType);
//		org  = (String) orgType.getSelectedItem();
//		
//		module = new module();
//		//addListeneronCreateButton();
//		//creating interface to listen activity on Item 
//		addListenerOnItem();
		
	}

//	private void setDateOnLoad() {
//		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
//		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
//
//		/*
//		 * set "from date" and "to date" textView
//		 * for creating calendar object and linking with its 'getInstance' method, 
//		 * for getting a default instance of this class for general use
//		 */
//
//		year = c.get(Calendar.YEAR);
//		month = 3; //month = april
//		day = 1;
//
//		//set from date: day=01, month=April, year=current year
//		tvDisplayFromDate.setText(new StringBuilder()
//		.append(mFormat.format(Double.valueOf(1))).append("-")
//		.append(mFormat.format(Double.valueOf(4))).append("-")
//		.append(year));
//
//		//Add one year to current date time
//		c.add(Calendar.YEAR,1);
//		toYear = c.get(Calendar.YEAR);
//		toMonth = 2;
//		toDay = 31;
//
//		//set to date: day=31, month=March, year=current year+1
//		tvDisplayToDate.setText(new StringBuilder()
//		.append(mFormat.format(Double.valueOf(31))).append("-")
//		.append(mFormat.format(Double.valueOf(3))).append("-")
//		.append(toYear));
//
//
//	}

//	private void addListeneronDateButton() {
//		btnChangeFromDate = (Button) findViewById(R.id.btnChangeFromDate);
//		btnChangeToDate = (Button) findViewById(R.id.btnChangeToDate);
//
//
//		/*
//		 * when button is clicked, user can select from date(day, month and year) from datepicker,
//		 * selected date will set in 'from date' textview and set date in 'to date' text view
//		 * which is greater than from date by one year and minus one day(standard financial year format)
//		 * 
//		 */
//		btnChangeFromDate.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				//Preparing views
//				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//				View layout = inflater.inflate(R.layout.datepiker, (ViewGroup) findViewById(R.id.layout_root));
//				//Building DatepPcker dialog
//				AlertDialog.Builder builder = new AlertDialog.Builder(context);
//				builder.setView(layout);
//				builder.setTitle("Set from date");
//				final DatePicker dp = (DatePicker)layout.findViewById(R.id.datePicker1);
//				dp.init(year,month,day, null);
//
//				builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface arg0, int arg1) {
//						year = dp.getYear();
//						month = dp.getMonth();
//						day =  dp.getDayOfMonth();
//						String strDateTime = mFormat.format(Double.valueOf(day)) + "-" 
//								+ (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))) + "-" 
//								+ year;
//						//set date in from date textview
//						tvDisplayFromDate.setText(strDateTime);
//
//						//setting selected date into calender's object
//						c.set(year, month, day);
//						//add one year
//						c.add(Calendar.YEAR, +1);
//						//subtracting one day
//						c.add(Calendar.DAY_OF_MONTH, -1);
//
//						toYear = c.get(Calendar.YEAR);
//						toMonth = c.get(Calendar.MONTH);
//						toDay = c.get(Calendar.DAY_OF_MONTH);
//
//						//set date in to date textview
//						tvDisplayToDate.setText(new StringBuilder()
//						.append(mFormat.format(Double.valueOf(toDay)))
//						.append("-").append(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(toMonth+1)))))))
//						.append("-").append(toYear));
//					}
//				}); 
//				dialog=builder.create();
//				dialog.show();
//			}	
//		});
//
//		/*
//		 * when button clicked, user can change the 'to date' from datepicker,
//		 * it will set the selected date in 'to date' textview, if to date is greater than from date
//		 */
//		btnChangeToDate.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				//Preparing views
//				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//				View layout = inflater.inflate(R.layout.datepiker, (ViewGroup) findViewById(R.id.layout_root));
//				//Building DatepPicker dialog
//				AlertDialog.Builder builder = new AlertDialog.Builder(context);
//				builder.setView(layout);
//				builder.setTitle("Set to date");
//
//				final   DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker1);
//				dp.init(toYear,toMonth,toDay, null);
//
//				builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface arg0, int arg1) {
//
//						int Year = dp.getYear();
//						int Month = dp.getMonth();
//						int Day =  dp.getDayOfMonth();
//
//						try {
//							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//							Date date1 = sdf.parse(day+"-"+month+"-"+year); //from date
//							Date date2 = sdf.parse(Day+"-"+Month+"-"+Year); //to date
//
//							Calendar cal1 = Calendar.getInstance(); 
//							Calendar cal2 = Calendar.getInstance(); 
//
//							cal1.setTime(date1);
//							cal2.setTime(date2);
//
//							if(cal2.after(cal1)){
//								toYear = Year;
//								toMonth = Month;
//								toDay =  Day;
//								String strDateTime = mFormat.format(Double.valueOf(toDay)) + "-" 
//										+ (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(toMonth))))+ 1))) + "-" 
//										+ toYear;
//								tvDisplayToDate.setText(strDateTime);
//							}
//							else{
//								toastValidationMessage("Please enter proper date");
//							}
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
//						}
//				}); 
//				dialog=builder.create();
//				dialog.show();
//			}	
//		});   
//	}     


//	// method to take ItemSelectedListner interface as a argument  
//	void addListenerOnItem(){
//		//Attach a listener to the Organisation Type Spinner
//		orgType.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
//				//Retrieving the selected org type from the Spinner and assigning it to a variable 
//				selectedOrgType = parent.getItemAtPosition(position).toString();
//				orgTypeFlag = selectedOrgType;       
//				
//
//			}
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//
//			}
//
//		});// End of orgType.setOnItemSelectedListener
//
//	}// End of addListenerOnItem()


//	private void addListeneronCreateButton() {
//		final Context context = this;
//		//Request a reference to the button from the activity by calling “findViewById” 
//		//and assign the retrieved button to an instance variable
//		btnCreate = (Button) findViewById(R.id.btnCreate);
//		//btnLogin = (Button) findViewById(R.id.btnLogin);
//		//btnNext = (Button) findViewById(R.id.btnNext);
//		orgType = (Spinner) findViewById(R.id.sOrgType);
//		tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
//		tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
//		orgName = (EditText) findViewById(R.id.etOrgName);
//		System.out.println("ADD List"+IPaddr);
//		orgnisation= new Organisation(IPaddr);
//		//Create a class implementing “OnClickListener” and set it as the on click listener for the button "Next"
//		btnCreate.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//
//				organisationName = orgName.getText().toString();
//				fromdate = tvDisplayFromDate.getText().toString();
//				todate = tvDisplayToDate.getText().toString();
//
//				try{
//					// call the getOrganisationName method from startup
//					orgNameList = startup.getOrgnisationName(); // return lists of existing organisations
//
//					for(Object org : orgNameList){
//						if(organisationName.equals(org)){
//							orgExistFlag = false;
//
//							//call getFinancialYear method from startup.java 
//							//it will give you financialYear list according to orgname
//							financialyearList = startup.getFinancialYear(organisationName);
//
//							for(Object fy : financialyearList)
//							{
//								Object[] y = (Object[]) fy;
//								// concatination From and To date 
//								String fromDate=y[0].toString();
//								String toDate=y[1].toString();
//
//								if(fromDate.equals(fromdate) && toDate.equals(todate)){
//									orgExistFlag = true;
//									break;
//								}
//
//							}
//						}
//					}
//
//					if("".equals(organisationName)){
//						toastValidationMessage("Please enter the organisation name");
//					}
//					else if(orgExistFlag == true)
//					{
//						toastValidationMessage("Organisation name "+organisationName+" with this financial year exist");
//						orgExistFlag = false;
//					}
//					else{ 
//						//To pass on the activity to the next page
//						MainActivity.editDetails=false;
//						deployparams = new Object[]{organisationName,fromdate,todate,orgTypeFlag}; // parameters pass to core_engine xml_rpc functions
//						client_id = startup.deploy(deployparams);
//						orgparams = new Object[]{organisationName,orgTypeFlag,"","","","","","","","","","","","","",
//						                                                            "","","" }; 
//						//call method deploy from startup.java 
//							                                              
//					    setOrgDetails = orgnisation.setOrganisation(orgparams,client_id);
//						/* Inflater for LogIn Page after Organisation Deployment*/
//						LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//						final View layout = inflater.inflate(R.layout.login, (ViewGroup) findViewById(R.id.layout_login));
//						AlertDialog.Builder builder = new AlertDialog.Builder(context);
//						builder.setView(layout);
//						TextView tvalertHead1 =(TextView) layout.findViewById(R.id.tvalertHead1);
//						tvalertHead1.setText(Html.fromHtml("Organisation <b>"+organisationName+"</b> have been created successfully"));
//						TextView tvalertHead2 =(TextView) layout.findViewById(R.id.tvalertHead2);
//						tvalertHead2.setText(Html.fromHtml("Financial Year: <b>"+fromdate+"</b> to <b>"+todate+"</b>"));
//			
//						tvLoginWarning =(TextView) layout.findViewById(R.id.tvLoginWarning);
//						tvLoginWarning.setVisibility(TextView.GONE);
//			
//						radioUserGroup = (RadioGroup)layout.findViewById(R.id.radioUser);
//						tvSignUp =(TextView) layout.findViewById(R.id.tvSignUp);
//						tvSignUp.setVisibility(TextView.GONE);
//						rb_admin =(RadioButton) layout.findViewById(R.id.rbAdmin);
//						rb_guest =(RadioButton) layout.findViewById(R.id.rbGuest);
//						tvuserrole = (TextView) layout.findViewById(R.id.tvUserRole);
//						rb_guest.setChecked(false);
//						//call radio button listener method to do event on radio button Checked
//						addListenerOnRadioButton(layout);
//						Button login =  (Button) layout.findViewById(R.id.btnLogin);
//
//						login.setOnClickListener(new View.OnClickListener(){
//							public void onClick(View v) {
//								
//								eloginUsername =(EditText) layout.findViewById(R.id.eLoginUser);
//								eloginPassword =(EditText) layout.findViewById(R.id.eLoginPassword);
//								
//								if(adminflag == true || rb_admin.isChecked()||rb_guest.isChecked()){
//									tvLoginWarning.setVisibility(TextView.GONE);
//									login_user = eloginUsername.getText().toString();
//									login_password = eloginPassword.getText().toString();
//									Object[] params = new Object[]{login_user,login_password,user_role};
//									System.out.println("we are in login");
//									if(module.isEmpty(params))
//									{
//										String message = "Please fill blank field";
//										tvSignUp.setVisibility(TextView.GONE);
//										tvLoginWarning.setVisibility(TextView.VISIBLE);
//										tvLoginWarning.setText(message);
//
//									}else
//									{
//										
//										if(user_role.equals("guest"))
//										{
//											
//											if ((login_user.equals("guest"))&&(login_password.equals("guest")))
//											{
//												//Toast.makeText(createOrg.this,"exist "+user_role, Toast.LENGTH_SHORT).show();
//												Intent intent = new Intent(context,orgDetails.class);
//												startActivity(intent);
//											}else
//											{
//												String message = "Username and Password is incorrect";
//												tvSignUp.setVisibility(TextView.GONE);
//												tvLoginWarning.setVisibility(TextView.VISIBLE);
//												tvLoginWarning.setText(message);
//
//											}		
//										}
//
//										if(user_role.equals("admin") ){
//											boolean is_user_exist = user.isUserExist(params, client_id);
//											//Toast.makeText(createOrg.this,"exist "+is_user_exist, Toast.LENGTH_SHORT).show();
//
//											if(is_user_exist==true)
//											{	
//												//dialog.cancel();
//												Intent intent = new Intent(context,orgDetails.class);
//												startActivity(intent);
//											}else
//											{
//												String message = "Username and Password is incorrect";
//												tvSignUp.setVisibility(TextView.GONE);
//												tvLoginWarning.setVisibility(TextView.VISIBLE);
//												tvLoginWarning.setText(message);
//
//											}					        	 
//										}
//									}
//								}
//								else{
//									String message = "Please select your role";
//									tvSignUp.setVisibility(TextView.GONE);
//									tvLoginWarning.setVisibility(TextView.VISIBLE);
//									tvLoginWarning.setText(message);
//								}
//							
//
//							}
//						});
//
//						dialog = builder.create();
//						dialog.show();
//						dialog.setCanceledOnTouchOutside(false);
//						WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//						//customizing the width and location of the dialog on screen 
//						lp.copyFrom(dialog.getWindow().getAttributes());
//						lp.width = 750;
//						dialog.getWindow().setAttributes(lp);
//
//					}
//				}catch(Exception e)
//				{
//					String message = "Can not connect to server!! Please set IP again or check server is running!!";
//					AlertDialog.Builder builder = new AlertDialog.Builder(context);
//					builder.setMessage(message)
//					.setCancelable(false)
//					.setPositiveButton("Ok",
//							new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dialog, int id) {
//							MainActivity.menuOptionFlag = true;
//							Intent intent = new Intent(context,MainActivity.class);
//							startActivity(intent);  
//						}
//					});
//
//					AlertDialog alert = builder.create();
//					alert.show();
//					//m.toastValidationMessage(contex, msg)
//					
//				}
//			}
//		}); //End of btnCreate.setOnClickListener
//
//	}
//    
//	public void addListenerOnRadioButton(final View layout) {
//		
//		radioUserGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			public void onCheckedChanged(RadioGroup rg, int selectedId) {
//
//				System.out.println("add listener radio button Create");
//				if(adminflag==false){
//					rb_admin.setChecked(false);
//				}
//				selectedId = rg.getCheckedRadioButtonId();
//				radioButtonValue = (RadioButton)layout.findViewById(selectedId);
//				user_role = (String) radioButtonValue.getText();
//
//				//Toast.makeText(createOrg.this,"hello"+user_role, Toast.LENGTH_SHORT).show();
//				//Toast.makeText(createOrg.this,"isChecked();"+rb_admin.isChecked(), Toast.LENGTH_SHORT).show();
//				eloginUsername =(EditText) layout.findViewById(R.id.eLoginUser);
//				eloginPassword =(EditText) layout.findViewById(R.id.eLoginPassword);
//
//				//Toast.makeText(createOrg.this,user_role, Toast.LENGTH_SHORT).show();
//				if(user_role.equals("admin"))
//				{
//					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//					final View layout = inflater.inflate(R.layout.sign_up, (ViewGroup) findViewById(R.id.layout_signup));
//					AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//					builder.setView(layout);
//					TextView tvalertHead1 =(TextView) layout.findViewById(R.id.tvalertHead1);
//					tvalertHead1.setText("Sign Up");
//					builder.setCancelable(true);
//					Button done =  (Button) layout.findViewById(R.id.btnSignUp);
//					Button cancel =  (Button) layout.findViewById(R.id.btnCancel);
//					// call setOnClickListner on s
//					done.setOnClickListener(new View.OnClickListener(){
//						private RadioButton rbgender;
//						private String gender;
//
//						public void onClick(View v) {
//
//							radioGender =(RadioGroup) layout.findViewById(R.id.radioGender);
//
//							eUserName =(EditText) layout.findViewById(R.id.eUserName);
//							ePassword =(EditText)layout.findViewById(R.id.ePassword);
//							eConfPassword =(EditText) layout.findViewById(R.id.eConfPassword);
//							sQuestions = (Spinner) layout.findViewById(R.id.sQuestions);
//							eAnswer = (EditText) layout.findViewById(R.id.eAnswer);
//							tvwarning =(TextView) layout.findViewById(R.id.tvWarning);
//							
//							genderid = radioGender.getCheckedRadioButtonId();
//							rbgender =(RadioButton)layout.findViewById(genderid);
//							gender = rbgender.getText().toString();
//						 	answer = eAnswer.getText().toString(); 
//							username = eUserName.getText().toString();
//							password = ePassword.getText().toString();
//							confpassword= eConfPassword.getText().toString();
//							selectedQuestion = sQuestions.getSelectedItemPosition();
//   
//							//System.out.println("we are in signup");
//							Object[] params = new Object[]{username,password,gender,user_role,selectedQuestion, answer};
//							//Toast.makeText(createOrg.this,selectedQuestion, Toast.LENGTH_SHORT).show();
//							System.out.println("we are in signup1"+username+password+gender+user_role+selectedQuestion+answer);
//							if(module.isEmpty(params)||module.isEmpty(new Object[]{confpassword}))
//							{
//								String message = "please fill blank field";
//								tvwarning.setVisibility(TextView.VISIBLE);
//								tvwarning.setText(message);
//								//Toast.makeText(createOrg.this,user_role, Toast.LENGTH_SHORT).show();
//								// dialog.getLayoutInflater();
//							}else if(!password.equals(confpassword))
//							{
//								//Toast.makeText(createOrg.this,"password check", Toast.LENGTH_SHORT).show();
//								ePassword.setText("");
//								eConfPassword.setText("");
//								String message = "Please enter correct password";
//								tvwarning.setVisibility(TextView.VISIBLE);
//								tvwarning.setText(message);
//							}else
//							{
//								//Toast.makeText(createOrg.this,"else", Toast.LENGTH_SHORT).show();
//								boolean unique = user.isUserUnique(new Object[]{username},client_id);
//								// Toast.makeText(createOrg.this,"user"+unique, Toast.LENGTH_SHORT).show();
//								if(unique==true)
//								{	 
//									String setuser = user.setUser(params, client_id);
//								//String message = "Sign up successfully";
//								rb_guest.setVisibility(View.GONE);
//								rb_admin.setVisibility(View.GONE);
//								tvuserrole.setVisibility(View.GONE);
//								
//								adminflag = true;  
//								eloginUsername.setText(username);
//								eloginPassword.setText("");
//								tvLoginWarning.setVisibility(TextView.GONE);
//								tvSignUp.setVisibility(TextView.VISIBLE);
//								tvSignUp.setText("Signed up successfully as a Admin, Please Log In!!!");
//								
//								dialog.dismiss();
//								}
//								else{
//									String message = "User already exist";
//									tvwarning.setText(message);
//								}
//
//
//							} 
//
//						}
//					});
//
//					cancel.setOnClickListener(new View.OnClickListener(){
//						public void onClick(View v) {
//							adminflag = false;
//							tvLoginWarning.setVisibility(TextView.GONE);
//							eloginUsername.setText("");
//							eloginPassword.setText("");
//							dialog.dismiss();
//
//
//						}
//					});
//
//					dialog = builder.create();
//					dialog.show();
//					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//					//customizing the width and location of the dialog on screen 
//					lp.copyFrom(dialog.getWindow().getAttributes());
//					lp.width = 800;
//					dialog.getWindow().setAttributes(lp);
//
//
//				}else
//				{
//					tvLoginWarning.setVisibility(TextView.GONE);
//					eloginPassword.setText("guest");
//					eloginUsername.setText("guest");
//
//				}
//			}// end of onCheckedChanged() method of setOnCheckedChangeListener()
//		}); // end of setOnCheckedChangeListener
//	}// end of addListenerOnRadioButton()

//	public void onBackPressed() {
//		MainActivity.menuOptionFlag = true;
//		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(intent);
//	}
//
//	public void toastValidationMessage(String message) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setMessage(message)
//		.setCancelable(false)
//		.setPositiveButton("Ok",
//				new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//
//			}
//		});
//
//		AlertDialog alert = builder.create();
//		alert.show();
//
//	} 

	
}// End of Class
