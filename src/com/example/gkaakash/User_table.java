package com.example.gkaakash;

import java.util.ArrayList;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.User;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class User_table extends Activity {
	String[] ColumnNameList;
	TableRow tr;
	TextView label, tv, tv1;
	LinearLayout Ll;
	TableLayout manager_table, operator_table;
	private User user;
	Integer client_id;
	RadioButton rbmanager, rboperator, rbRoleChecked, rbGenderChecked;
	ArrayList<String> manager_list, operator_list;
	RadioGroup radiogender, radiorole;
	static String userrole;
	module m;
	EditText oldpass, newpass, confirmpass;
	AlertDialog dialog;
	Context c = User_table.this;
	View layout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = new User();
		client_id = Startup.getClient_id();
		m = new module();

		// set the layout signup view to this content
		setContentView(R.layout.user_table);
		manager_table = (TableLayout) findViewById(R.id.manager_table);
		operator_table = (TableLayout) findViewById(R.id.operator_table);
		addTable(manager_table, "");

		addTable(operator_table, "operator");

		if (menu.userrole.equals("manager")) {
			manager_table.setVisibility(View.GONE);
		} else {
			manager_table.setVisibility(View.VISIBLE);
		}

		Button add_user = (Button) findViewById(R.id.add_user);
		add_user.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.sign_up, null);
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						User_table.this);
				builder.setView(layout);
				builder.setTitle("Add role");
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
				// get the id of cancel button and change the text to Reset
				Button btncancel = (Button) layout.findViewById(R.id.btnCancel);
				btncancel.setText("Reset");
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

								Intent intent = new Intent(User_table.this,
										User_table.class);
								// To pass on the value to the next page
								startActivity(intent);

								reset();
							} else {
								eusername.setText("");
								String message = "username is already exist";
								tvwarning.setVisibility(TextView.VISIBLE);
								tvwarning.setText(message);
							}
						}

					}

					private void reset() {
						eusername.setText("");
						epassword.setText("");
						econfpassword.setText("");

					}

					// add Checked change listner on radioGroup

				});

				AlertDialog dialog = builder.create();
				dialog.show();

			}
		});

	}// end onCreate method

	private void addTable(TableLayout tableID, String flag) {

		Object[] managernames = user.getUserNemeOfManagerRole(client_id);
		Object[] operatornames = user.getUserNemeOfOperatorRole(client_id);
		manager_list = new ArrayList<String>();
		for (Object mnames : managernames) {
			System.out.println("names:" + mnames.toString());
			manager_list.add(mnames.toString());

		}
		System.out.println("list_m" + manager_list);
		operator_list = new ArrayList<String>();
		for (Object onames : operatornames) {
			System.out.println("oprea names:" + onames.toString());

			operator_list.add(onames.toString());

		}
		System.out.println("list_o" + operator_list);
		if (!flag.equals("operator")) {
			addHeader(manager_table);
			for (int i = 0; i < manager_list.size(); i++) {
				tr = new TableRow(this);
				addRow(manager_list.get(i), i);

				manager_table.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
				popup();
			}

		} else {
			addHeader(operator_table);
			for (int i = 0; i < operator_list.size(); i++) {
				tr = new TableRow(this);
				addRow(operator_list.get(i), i);
				operator_table.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
				popup();
			}
		}

	}

	void popup() {
		int count = manager_table.getChildCount();
		System.out.println("count table 1:" + count);
		for (int i = 1; i < count; i++) {
			final View row = manager_table.getChildAt(i);

			row.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					LinearLayout l = (LinearLayout) ((ViewGroup) row)
							.getChildAt(0);
					tv = (TextView) l.getChildAt(0);
					System.out.println(tv.getText());
					Toast.makeText(User_table.this, "lll:" + tv.getText(),
							Toast.LENGTH_SHORT).show();

					dialog_builder(tv.getText().toString(), "manager");
				}
			});
		}

		int count1 = operator_table.getChildCount();
		System.out.println("count table 2:" + count1);
		for (int i = 1; i < count1; i++) {
			final View row = operator_table.getChildAt(i);

			row.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					LinearLayout l = (LinearLayout) ((ViewGroup) row)
							.getChildAt(0);
					tv1 = (TextView) l.getChildAt(0);
					System.out.println(tv1.getText());
					Toast.makeText(User_table.this, "lll:" + tv1.getText(),
							Toast.LENGTH_SHORT).show();

					dialog_builder(tv1.getText().toString(), "operator");
				}
			});
		}
	}

	void addHeader(TableLayout table) {
		ColumnNameList = new String[] { "Username" };
		tr = new TableRow(this);
		for (int k = 0; k < ColumnNameList.length; k++) {
			/** Creating a TextView to add to the row **/
			addRow(ColumnNameList[k], k);
			tr.setClickable(false);
			label.setBackgroundColor(Color.parseColor("#348017"));
			label.setGravity(Gravity.CENTER);
			tr.setClickable(false);
		}
		table.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT));
	}

	private void addRow(String param, int k) {

		label = new TextView(this);
		label.setText(param);
		label.setTextColor(Color.WHITE);
		label.setTextSize(18);
		// label.setBackgroundColor(Color.);
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		label.setBackgroundColor(Color.BLACK);
		label.setPadding(2, 2, 2, 2);

		Ll = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.setMargins(1, 1, 1, 1);
		// Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label, params);
		tr.addView((View) Ll);

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
						old_user_name.setText(tv.getText());
					} else {
						old_user_name.setText(tv1.getText());
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
									manager_table.removeAllViews();
									operator_table.removeAllViews();
									addTable(manager_table, "");
									addTable(operator_table, "operator");
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