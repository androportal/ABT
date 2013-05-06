package com.example.gkaakash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.gkaakash.R.string;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.User;

import android.R.color;
import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class User_table extends Activity {
	String[] ColumnNameList;
	private User user;
	Integer client_id;
	RadioButton rbmanager, rboperator, rbRoleChecked, rbGenderChecked;
	RadioGroup radiogender, radiorole;
	static String userrole;
	module m;
	EditText oldpass, newpass, confirmpass;
	Context c = User_table.this;
	View layout;
	ListView role_list;
	ArrayList<ArrayList> Grid;
	ArrayList<String> ResultList;
	RadioGroup radioUserGroup;
	Object[] role_names;
	AlertDialog dialog;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = new User();
		client_id = Startup.getClient_id();
		m = new module();

		// set the layout signup view to this content
		setContentView(R.layout.user_table);
		

		if (menu.userrole.equals("manager") || menu.userrole.equals("operator")) {
			// get the id of table row of user role and visible it
			TableRow truserrole = (TableRow) findViewById(R.id.trUserRole);
			truserrole.setVisibility(View.GONE);
			if(menu.userrole.equals("manager")){
				role_names = user.getUserNemeOfOperatorRole(client_id);
				setRoleList();
			}
		}else if(menu.userrole.equals("admin")){
			//set list for manager names
			role_names = user.getUserNemeOfManagerRole(client_id);
			setRoleList();
		}
		radioUserGroup = (RadioGroup)findViewById(R.id.radioRole);
		rbmanager = (RadioButton) findViewById(R.id.rbManager);
		rboperator = (RadioButton) findViewById(R.id.rbOperator);
		
		radioUserGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup rg, int selctedId) {
			
				if(rbmanager.isChecked()){
					role_names = user.getUserNemeOfManagerRole(client_id);
					setRoleList();
				}else if(rboperator.isChecked()){
					role_names = user.getUserNemeOfOperatorRole(client_id);
					setRoleList();
				}
			}
		});
		
		addNewUser();
		
		
	}// end onCreate method


	
	private void addNewUser() {
		Button add_user = (Button) findViewById(R.id.add_user);
		add_user.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.sign_up, null);
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						User_table.this);
				builder.setView(layout);
				// get the id of signup.xml header
				TextView tvheader = (TextView) layout
						.findViewById(R.id.tvalertHead1);
				tvheader.setText("Add role");
				// tvheader.setVisibility(View.GONE); // set visibility gone of
				// header
				// get the id of table row of user role and visible it
				TableRow truserrole = (TableRow) layout
						.findViewById(R.id.trUserRole);
				truserrole.setVisibility(View.VISIBLE);
				if (menu.userrole.equals("manager")) {
					// get only operator visible to manager
					rbmanager = (RadioButton) layout
							.findViewById(R.id.rbManager);
					rbmanager.setVisibility(View.GONE);

					// get only operator visible to manager
					rboperator = (RadioButton) layout
							.findViewById(R.id.rbOperator);
					rboperator.setChecked(true);

				}
				
				Button btncancel = (Button) layout.findViewById(R.id.btnCancel);
				btncancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				// get the id of question row and answer row and invisible it
				TableRow transwer = (TableRow) layout
						.findViewById(R.id.trAnswer);
				TableRow trquestion = (TableRow) layout
						.findViewById(R.id.trQuestion);
				transwer.setVisibility(View.GONE);
				trquestion.setVisibility(View.GONE);
				// get all widget id's to use
				Button btndone = (Button) layout.findViewById(R.id.btnSignUp);
				final EditText eusername = (EditText) layout
						.findViewById(R.id.eUserName);
				final EditText epassword = (EditText) layout
						.findViewById(R.id.ePassword);
				final EditText econfpassword = (EditText) layout
						.findViewById(R.id.eConfPassword);
				radiogender = (RadioGroup) layout
						.findViewById(R.id.radioGender);
				radiorole = (RadioGroup) layout.findViewById(R.id.radioRole);
				TableRow radiouserole = (TableRow) layout
						.findViewById(R.id.trUserRole);
				final TextView tvwarning = (TextView) layout
						.findViewById(R.id.tvWarning);
				TextView tvmessage = (TextView) layout
						.findViewById(R.id.tvSignUp);
				int rbRoleCheckedId = radiorole.getCheckedRadioButtonId();
				rbRoleChecked = (RadioButton) layout
						.findViewById(rbRoleCheckedId);
				int rbGenderCheckedId = radiogender.getCheckedRadioButtonId();
				rbGenderChecked = (RadioButton) layout
						.findViewById(rbGenderCheckedId);
				radiorole
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							public void onCheckedChanged(RadioGroup rg,
									int selctedId) {

								rbRoleChecked = (RadioButton) layout
										.findViewById(selctedId);
								// userrole =
								// rbRoleChecked.getText().toString();

							}
						});
				radiogender
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							public void onCheckedChanged(RadioGroup rg,
									int selctedId) {

								rbGenderChecked = (RadioButton) layout
										.findViewById(selctedId);
								// gender =
								// rbGenderChecked.getText().toString();

							}
						});

				btndone.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						addOnClickListnereOnButton();
					}

					private void addOnClickListnereOnButton() {
						String gender = rbGenderChecked.getText().toString();
						String username = eusername.getText().toString();
						String password = epassword.getText().toString();
						String confpassword = econfpassword.getText()
								.toString();
						userrole = rbRoleChecked.getText().toString();
						tvwarning.setVisibility(TextView.GONE);
						Object[] params = new Object[] { username, password,
								gender, userrole, "null", "null" };
						System.out.println(username);
						System.out.println(password);
						System.out.println(gender);
						System.out.println(userrole);

						// System.out.println("list :"+);
						if (m.isEmpty(params)
								|| m.isEmpty(new Object[] { confpassword })) {
							String message = "please fill blank field";
							tvwarning.setVisibility(TextView.VISIBLE);
							tvwarning.setText(message);

						} else if (!password.equals(confpassword)) {
							epassword.setText("");
							econfpassword.setText("");
							String message = "Please enter correct password";
							tvwarning.setVisibility(TextView.VISIBLE);
							tvwarning.setText(message);
						} else {
							boolean unique = user.isUserUnique(
									new Object[] { username }, client_id);
							if (unique == true) {
								String setuser = user
										.setUser(params, client_id);
								tvwarning.setText(username
										+ " added successfully as " + userrole);

								reset();
								
								if(rbmanager.isChecked()){
									role_names = user.getUserNemeOfManagerRole(client_id);
									setRoleList();
								}else if(rboperator.isChecked()){
									role_names = user.getUserNemeOfOperatorRole(client_id);
									setRoleList();
								}
							} else {
								eusername.setText("");
								String message = "username is already exist";
								tvwarning.setVisibility(TextView.VISIBLE);
								tvwarning.setText(message);
							}
						}

					}

					private void reset() {
						RadioButton male = (RadioButton)layout.findViewById(R.id.rbMale);
						eusername.setText("");
						epassword.setText("");
						econfpassword.setText("");
						male.setChecked(true);
					}

					// add Checked change listner on radioGroup

				});

				dialog = builder.create();
				dialog.show();

			}
		});
	
	}



	private void setRoleList() {
		// for setting role list
		role_list = (ListView) findViewById(R.id.role_list);
		role_list.setTextFilterEnabled(true);
		role_list.setCacheColorHint(color.transparent);
		
		Grid = new ArrayList<ArrayList>();
		for(Object tb : role_names)
    	{
    		Object[] t = (Object[]) tb;
    		ResultList = new ArrayList<String>();
    		for(int i=0;i<t.length;i++){
           	
    			ResultList.add((String) t[i].toString());
              
    		}
    		Grid.add(ResultList);
    	} 
    	System.out.println("grid1:"+Grid);
		
		String[] abc = new String[] { "srno", "username", "login", "logout", "Total" };
		int[] pqr = new int[] { R.id.tvRowTitle0, R.id.tvRowTitle1, R.id.tvSubItem0, R.id.tvSubItem1, R.id.tvSubItem2 };
		//System.out.println("grid size"+Grid.get(0).size());
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < Grid.size(); i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("srno", (i+1)+" ");
			map.put("username", Grid.get(i).get(0).toString());
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			java.util.Date loginDate = null;
			java.util.Date logoutDate = null;
		    try {
		    	loginDate = formatter.parse(Grid.get(i).get(1).toString());
		    	logoutDate = formatter.parse(Grid.get(i).get(2).toString());
		    	System.out.println("utilDate:" + loginDate);
		    	int diffInDays = (int)( (loginDate.getTime() - logoutDate.getTime()) 
		                 / (1000 * 60 * 60 * 24) );
		    	if(loginDate != null && logoutDate != null){
		    		map.put("login", loginDate.toLocaleString());
					map.put("logout", logoutDate.toLocaleString());
					map.put("Total", diffInDays+" days");
		    	}
		    	
			} catch (ParseException e) {
				
			}
		    
			fillMaps.add(map);
		}
		SimpleAdapter Adapter = new SimpleAdapter(this, fillMaps, R.layout.voucher_child_row,
				abc, pqr);
		role_list.setAdapter(Adapter);
		
		role_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TextView username = (TextView)arg1.findViewById(R.id.tvRowTitle1);
//				TextView login = (TextView)arg1.findViewById(R.id.tvSubItem0);
//				TextView logout = (TextView)arg1.findViewById(R.id.tvSubItem1);
//				TextView total = (TextView)arg1.findViewById(R.id.tvSubItem2);
//				System.out.println(username.getText()+" "+login.getText());
				dialog_builder(username.getText().toString(), "manager");
			}
		});
		
	}

	private void dialog_builder(final String username, final String userrole1) {
		final CharSequence[] items = { "Change username", "Change password" };
		// creating a dialog box for popup
		AlertDialog.Builder builder = new AlertDialog.Builder(User_table.this);
		// setting title
		builder.setTitle("Change username/password");
		// adding items
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int pos) {
				if (pos == 0) {

					final View layout1 = m.builder_with_inflater(c, "",
							R.layout.change_password);
					LinearLayout l1 = (LinearLayout) layout1
							.findViewById(R.id.changepassword);
					l1.setVisibility(View.GONE);

					final TextView error_msg = (TextView) layout1
							.findViewById(R.id.tverror_msg1);
					Button save = (Button) layout1.findViewById(R.id.btnSave);
					TextView header = (TextView) layout1
							.findViewById(R.id.tvheader1);
					header.setText("Change username");

					EditText old_user_name = (EditText) layout1
							.findViewById(R.id.etOldUsername);
					if ("manager".equals(userrole1)) {
						old_user_name.setText(username);
					} else {
						old_user_name.setText(username);
					}

					Button cancel = (Button) layout1
							.findViewById(R.id.btnCancel);
					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							m.dialog.cancel();
						}
					});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							Toast.makeText(c, "hii", Toast.LENGTH_SHORT).show();
							EditText olduser_name = (EditText) layout1
									.findViewById(R.id.etOldUsername);
							String olduserName = olduser_name.getText()
									.toString();
							System.out.println("olduserName:" + olduserName);
							EditText newuser_name = (EditText) layout1
									.findViewById(R.id.etNewUsername);
							String newusername = newuser_name.getText()
									.toString();
							System.out.println("new username:" + newusername);

							EditText password = (EditText) layout1
									.findViewById(R.id.etPassword);
							String password1 = password.getText().toString();
							System.out.println("password:" + password1);
							// System.out.println("username:"+username);
							// System.out.println("userrole:"+userrole1);

							if (!"".equals(olduserName)
									& !"".equals(newusername)
									& !"".equals(password1)) {
								Boolean username_result = user.changeUserName(
										new Object[] { olduserName,
												newusername, password1,
												userrole1 }, client_id);
								System.out.println("r:" + username_result);
								if (username_result == true) {
									error_msg.setVisibility(TextView.VISIBLE);
									error_msg
											.setText("Username updated successully");
									m.dialog.cancel();
									
									olduser_name.setText("");
									newuser_name.setText("");
									password.setText("");

								} else {
									error_msg.setVisibility(TextView.VISIBLE);
									error_msg
											.setText("Invalid username or password");
									olduser_name.setText("");
									password.setText("");
								}
							} else {
								error_msg.setVisibility(TextView.VISIBLE);
								error_msg.setText("Fill the empty fields");

							}
						}
					});
				}
				if (pos == 1) {

					final View layout = m.builder_with_inflater(c, "",
							R.layout.change_password);

					Button save = (Button) layout.findViewById(R.id.btnSave);
					TextView header = (TextView) layout
							.findViewById(R.id.tvheader1);
					header.setText("Change password");
					final TextView error_msg = (TextView) layout
							.findViewById(R.id.tverror_msg1);
					LinearLayout l2 = (LinearLayout) layout
							.findViewById(R.id.changeusername);
					l2.setVisibility(LinearLayout.GONE);
					Button cancel = (Button) layout
							.findViewById(R.id.btnCancel);
					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							m.dialog.cancel();
						}
					});
					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {

							Toast.makeText(c, "hii", Toast.LENGTH_SHORT).show();
							oldpass = (EditText) layout
									.findViewById(R.id.etOldPass);
							String old_pass = oldpass.getText().toString();
							System.out.println("oldpass:" + old_pass);
							newpass = (EditText) layout
									.findViewById(R.id.etNewPass);
							String new_pass = newpass.getText().toString();
							System.out.println("newpass:" + new_pass);

							confirmpass = (EditText) layout
									.findViewById(R.id.etconfirmPass);
							String confirm_pass = confirmpass.getText()
									.toString();
							System.out.println("confirm_pass:" + confirm_pass);
							System.out.println("username:" + username);
							System.out.println("userrole:" + userrole1);

							if (!"".equals(old_pass) & !"".equals(new_pass)
									& !"".equals(confirm_pass)) {
								if (new_pass.equals(confirm_pass)) {

									Boolean result = user.changePassword(
											new Object[] { username, old_pass,
													new_pass, userrole },
											client_id);
									System.out.println("r:" + result);
									if (result == false) {
										error_msg
												.setVisibility(TextView.VISIBLE);
										error_msg.setText("Invalid password");
										oldpass.setText("");
										newpass.setText("");
										confirmpass.setText("");
									} else {
										error_msg
												.setVisibility(TextView.VISIBLE);
										error_msg
												.setText("Password updated successully");

										m.dialog.cancel();
										oldpass.setText("");
										newpass.setText("");
										confirmpass.setText("");
									}
								} else {
									error_msg.setVisibility(TextView.VISIBLE);
									error_msg
											.setText("New password and confirm password fields doesnot match!");

									newpass.setText("");
									confirmpass.setText("");
								}

							} else {
								error_msg.setVisibility(TextView.VISIBLE);
								error_msg.setText("Fill the empty fields");
							}

						}
					});

				}

			}
		});
		dialog = builder.create();
		((Dialog) dialog).show();

	}


	public void onBackPressed() {

		Intent intent = new Intent(getApplicationContext(), menu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

	}
}// end User_table class