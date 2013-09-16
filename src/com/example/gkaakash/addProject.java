package com.example.gkaakash;
import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.gkaakash.controller.Account;
import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;

public class addProject extends MainActivity {
	private Integer client_id;
	static int idCount;
	final Context context = this;
	Button add;
	private TableLayout projectTable;
	int rowsSoFar = 0;
	int count; 
	AlertDialog dialog;
	EditText etProject, etdynamic;
	private Account account;
	private Preferences preferences;
	private Organisation organisation;
	private ListView ltProjectNames;
	private Object[] projectnames;
	List projectNameList, projectCodeList;
	ArrayList<String> finalProjlist;
	protected String projectname;
	protected ArrayList<String>[] projectnamelist;
	boolean projectExistsFlag = false;
	private boolean setProject;
	module m;
	TextView tveditWarning;
	static String IPaddr;

	// on load...
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_projectnames);
		IPaddr = MainActivity.IPaddr;
		System.out.println("in createorg"+IPaddr);
		account = new Account(IPaddr);
		preferences = new Preferences(IPaddr);
		organisation = new Organisation(IPaddr);
		client_id = Startup.getClient_id();
		ltProjectNames = (ListView) findViewById(R.id.ltProjectNames);
		ltProjectNames.setCacheColorHint(color.transparent);
		ltProjectNames.setTextFilterEnabled(true);
		m = new module();

		//set title
		TextView org = (TextView)findViewById(R.id.org_name);
		org.setText(menu.OrgName);
		TextView tvdate = (TextView)findViewById(R.id.date);
		tvdate.setText(m.changeDateFormat(menu.financialFromDate)+" To "+m.changeDateFormat(menu.financialToDate));
		
		Button btn_optionsMenu= (Button) findViewById(R.id.btn_optionsMenu);
		btn_optionsMenu.setVisibility(View.GONE);
		Button btn_changeInputs= (Button) findViewById(R.id.btn_changeInputs);
		btn_changeInputs.setVisibility(View.GONE);
		
		// get all project names in list view on load
		projectnames = (Object[]) organisation.getAllProjects(client_id);
		getResultList(projectnames);

		addProject();
		editProject();
	}

	private void editProject() {
		ltProjectNames = (ListView) findViewById(R.id.ltProjectNames);
		ltProjectNames.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				final CharSequence[] items = { "Edit project name","Delete project" };
				// creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(addProject.this);
				// setting title
				builder.setTitle("Edit/Delete project");
				// adding items
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface which, int pos) {
						// code for the actions to be performed on clicking
						// popup item goes here ...
						switch (pos) {
						// edit project
						case 0: {
							// Toast.makeText(edit_account.this,"Clicked on:"+items[pos],Toast.LENGTH_SHORT).show();
							LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
							View layout = inflater.inflate(R.layout.edit_projectname,(ViewGroup) findViewById(R.id.layout_root));
							AlertDialog.Builder builder = new AlertDialog.Builder(addProject.this);
							builder.setView(layout);
							builder.setTitle("Edit project name");
							// get account details
							final String old_projectname = ltProjectNames.getItemAtPosition(position).toString();

							final EditText edit_project_name = (EditText) layout.findViewById(R.id.edit_project_name);
							edit_project_name.setText(old_projectname);
							final Button btnSave = (Button) layout.findViewById(R.id.btnSave);
							final Button btnCancel = (Button) layout.findViewById(R.id.btnCancel);
							tveditWarning = (TextView) layout.findViewById(R.id.tveditWarning);
							
							btnSave.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									String new_project_name = edit_project_name.getText().toString();

									/*
									 * validation to check whether
									 * project exists or blank
									 */
									for (int i = 0; i < projectNameList.size(); i++) {
										if (new_project_name.equalsIgnoreCase((String) projectNameList.get(i))) {
											projectExistsFlag = true;
											break;
										} else {
											projectExistsFlag = false;
										}
									}

									
									if (new_project_name.equalsIgnoreCase(old_projectname)) {
										tveditWarning.setVisibility(View.VISIBLE);
										tveditWarning.setText("No changes made");
										
									} else if (projectExistsFlag == true) {
										tveditWarning.setVisibility(View.VISIBLE);
										tveditWarning.setText("Project '"+ new_project_name+ "' already exists");
										
									} else {
										if (new_project_name.length() < 1) {
											tveditWarning.setVisibility(View.VISIBLE);
											tveditWarning.setText("Please enter project name");
										}else
										{
										Integer projCode = (Integer) projectCodeList.get(position);
										Object[] params = new Object[] {projCode,new_project_name };
										String edited = (String) preferences.editProject(params,client_id);
										// get all project names in list
										// view on load
										projectnames = (Object[]) organisation.getAllProjects(client_id);
										getResultList(projectnames);
										m.toastValidationMessage(addProject.this,"Project name has been changed from '"+ old_projectname
														+ "' to '"+ new_project_name+ "'");
										dialog.dismiss();
										}
									}
									
								}
							});
						
							btnCancel.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							
							dialog = builder.create();
							((Dialog) dialog).show();
							  WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				                // customizing the width and location of the dialog on screen
				                lp.copyFrom(dialog.getWindow().getAttributes());
				                lp.width = 400;
				                
				                dialog.getWindow().setAttributes(lp);

						}
						break;
						// delete existing project name
						case 1: 
						{
							String proj = ltProjectNames.getItemAtPosition(position).toString();
							Object[] params = new Object[] { proj };
							String edited = (String) preferences.deleteProjectName(params, client_id);
							if (edited.equalsIgnoreCase("project deleted")) {
								m.toastValidationMessage(addProject.this,"Project '" + proj+ "' deleted successfully");
								projectnames = (Object[]) organisation.getAllProjects(client_id);
								getResultList(projectnames);
							} else {
								m.toastValidationMessage(addProject.this,"Project '"+ proj+ "' can't be deleted, it has transactions");
							}

						}
							break;
						}
					}
				});
				// building a complete dialog
				dialog = builder.create();
				dialog.show();
			}
		});
	}

	private void addProject() {
		Button addProject = (Button) findViewById(R.id.add_project);
		addProject.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				etProject = (EditText) findViewById(R.id.etProjectname);
			
				EditText projectName;
				String proj_name;
				//View v1 = null;
				// List<String> secondProjlist = new ArrayList<String>();
				projectname = etProject.getText().toString();

			
				Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
				// create new array list of type String to add gropunames
				List<String> projectnamelist = new ArrayList<String>();
				projectnamelist.add("No Project");
				for (Object pn : projectnames) {
					Object[] p = (Object[]) pn;
					projectnamelist.add((String) p[1]); // p[0]is project code & p[1] is projectname
				}

				if (projectname.equals("")) {
					m.toastValidationMessage(context, "Please enter project name");
					
				}
				else
				{
				for (int j = 0; j < projectnamelist.size(); j++) {
					if (projectname.equalsIgnoreCase(projectnamelist.get(j).toString())) {
						projectExistsFlag = true;
						break;
					} else {
						projectExistsFlag = false;
					}
				}	
				if (projectExistsFlag == true) {
					m.toastValidationMessage(context, "Project '"+ projectname + "' already exists");
					etProject.setText("");
				} else {
					
					setProject = preferences.setProjects(new Object[]{projectname},client_id);
					// To pass on the activity to the next page
					m.toastSuccessfulMessage(addProject.this,"Project added successfully");
					// get all project names in list view on load
					projectnames = (Object[]) organisation.getAllProjects(client_id);
					getResultList(projectnames);
					etProject.setText("");
				}
				}
			
			}		
			
		});

		
	}

	void getResultList(Object[] param) {
		projectNameList = new ArrayList();
		projectCodeList = new ArrayList();
		for (Object pn : param) {
			Object[] p = (Object[]) pn;
			projectCodeList.add((Integer) p[0]);
			projectNameList.add((String) p[1]); // p[1] is project_name and p[0]
			// is project code
		}
		ltProjectNames.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, projectNameList));

	}
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), menu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}