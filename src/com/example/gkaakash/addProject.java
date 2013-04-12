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
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
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

	// on load...
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_projectnames);
		account = new Account();
		preferences = new Preferences();
		organisation = new Organisation();
		client_id = Startup.getClient_id();
		ltProjectNames = (ListView) findViewById(R.id.ltProjectNames);
		ltProjectNames.setCacheColorHint(color.transparent);
		ltProjectNames.setTextFilterEnabled(true);
		m=new module();

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

				final CharSequence[] items = { "Edit project name",
				"Delete project" };
				// creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(
						addProject.this);
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
							View layout = inflater.inflate(
									R.layout.edit_projectname,
									(ViewGroup) findViewById(R.id.layout_root));
							AlertDialog.Builder builder = new AlertDialog.Builder(
									addProject.this);
							builder.setView(layout);
							builder.setTitle("Edit project");
							// get account details
							final String old_projectname = ltProjectNames
									.getItemAtPosition(position).toString();

							final EditText edit_project_name = (EditText) layout
									.findViewById(R.id.edit_project_name);
							edit_project_name.setText(old_projectname);

							builder.setPositiveButton("Save",
									new DialogInterface.OnClickListener() {

								public void onClick(
										DialogInterface dialog,
										int which) {

									String new_project_name = edit_project_name
											.getText().toString();

									/*
									 * validation to check whether
									 * project exists or blank
									 */
									for (int i = 0; i < projectNameList
											.size(); i++) {
										if (new_project_name
												.equalsIgnoreCase((String) projectNameList
														.get(i))) {
											projectExistsFlag = true;
											break;
										} else {
											projectExistsFlag = false;
										}
									}

									if (new_project_name.length() < 1) {
										m.toastValidationMessage(addProject.this,"Please enter project name");
									}
									if (new_project_name
											.equalsIgnoreCase(old_projectname)) {
										m.toastValidationMessage(addProject.this,"No changes made");
									} else if (projectExistsFlag == true) {
										m.toastValidationMessage(addProject.this,"Project '"
												+ new_project_name
												+ "' already exists");
									} else {
										Integer projCode = (Integer) projectCodeList
												.get(position);
										Object[] params = new Object[] {
												projCode,
												new_project_name };
										String edited = (String) preferences
												.editProject(params,
														client_id);
										// get all project names in list
										// view on load
										projectnames = (Object[]) organisation
												.getAllProjects(client_id);
										getResultList(projectnames);
										m.toastValidationMessage(addProject.this,"Project name has been changed from '"
												+ old_projectname
												+ "' to '"
												+ new_project_name
												+ "'");
									}
								}// end of onclick
							});// end of onclickListener

							builder.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {

								@Override
								public void onClick(
										DialogInterface dialog,
										int which) {
									// do nothing
								}
							});

							dialog = builder.create();
							((Dialog) dialog).show();

						}
						break;
						// delete existing project name
						case 1: {
							String proj = ltProjectNames.getItemAtPosition(
									position).toString();
							Object[] params = new Object[] { proj };
							String edited = (String) preferences
									.deleteProjectName(params, client_id);
							if (edited.equalsIgnoreCase("project deleted")) {
								m.toastValidationMessage(addProject.this,"Project '" + proj
										+ "' deleted successfully");
								projectnames = (Object[]) organisation
										.getAllProjects(client_id);
								getResultList(projectnames);
							} else {
								m.toastValidationMessage(addProject.this,"Project '"
										+ proj
										+ "' can't be deleted, it has transactions");
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
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.add_project,
						(ViewGroup) findViewById(R.id.layout_root));
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
				builder.setTitle("Add projects");
				etProject = (EditText) layout.findViewById(R.id.etProjectname);
				projectTable = (TableLayout) layout
						.findViewById(R.id.projecttable);
				Button add = (Button) layout.findViewById(R.id.addProject);
				add.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						addButton();
					}

				});
				builder.setPositiveButton("Add",
						new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface arg0, int which) {
						EditText projectName;
						String proj_name;
						View v1 = null;
						List<String> secondProjlist = new ArrayList<String>();
						projectname = etProject.getText().toString();

						for (int i = 1; i <= idCount; i++) {
							v1 = dialog.findViewById(i);
							if (v1 != null) {
								projectName = (EditText) dialog
										.findViewById(i);
								proj_name = projectName.getText()
										.toString();
								if (!"".equals(proj_name)) {
									secondProjlist.add(proj_name);
								}
							}
						}

						finalProjlist = new ArrayList<String>();
						if (!"".equals(projectname)) {
							finalProjlist.add(projectname);
						}

						finalProjlist.addAll(secondProjlist);
						AlertDialog help_dialog;
						// call the getAllProjects method to get all
						// projects
						Object[] projectnames = (Object[]) organisation
								.getAllProjects(client_id);
						// create new array list of type String to add
						// gropunames
						List<String> projectnamelist = new ArrayList<String>();
						projectnamelist.add("No Project");
						for (Object pn : projectnames) {
							Object[] p = (Object[]) pn;
							projectnamelist.add((String) p[1]); // p[0]
							// is
							// project
							// code
							// &
							// p[1]
							// is
							// projectname
						}

						String ac;
						boolean flag = false;
						String nameExists = "";

						for (int i = 0; i < finalProjlist.size(); i++) {
							ac = finalProjlist.get(i);
							for (int j = 0; j < finalProjlist.size(); j++) {
								if (i != j) {
									if (ac.equals(finalProjlist.get(j))) {
										flag = true;
										break;
									}
								} else {
									flag = false;
								}
							}
						}

						if (flag == true) {
							m.toastValidationMessage(addProject.this,"Project names can not be same");
						} else {
							for (int i = 0; i < finalProjlist.size(); i++) {
								for (int j = 0; j < projectnamelist
										.size(); j++) {
									if ((finalProjlist.get(i)
											.toString())
											.equalsIgnoreCase(projectnamelist
													.get(j).toString())) {
										projectExistsFlag = true;
										nameExists = finalProjlist.get(
												i).toString();
										break;
									} else {
										projectExistsFlag = false;
									}
								}
								if (projectExistsFlag == true) {
									break;
								}
							}

							if (etProject.length() < 1) {
								m.toastValidationMessage(addProject.this,"Please enter project name");
							} else if (projectExistsFlag == true) {
								m.toastValidationMessage(addProject.this,"Project '"
										+ nameExists
										+ "' already exists");
							} else {
								setProject = preferences.setProjects(
										finalProjlist, client_id);
								// To pass on the activity to the next
								// page
								m.toastValidationMessage(addProject.this,"Project added successfully");
								// get all project names in list view on
								// load
								projectnames = (Object[]) organisation
										.getAllProjects(client_id);
								getResultList(projectnames);
								etProject.setText("");
								projectTable.removeAllViews();
							}
						}
					}
				});

				builder.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// close the dialog box
					}
				});
				dialog = builder.create();
				((Dialog) dialog).show();
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				// customizing the width and location of the dialog on screen
				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.height = 300;
				dialog.getWindow().setAttributes(lp);
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

	/***
	 * Gets all the information necessary to delete itself from the constructor.
	 * Deletes itself when the button is pressed.
	 */
	private static class RowRemover implements OnClickListener {
		public TableLayout list;
		public TableRow rowToBeRemoved;

		/***
		 * @param list
		 *            The list that the button belongs to
		 * @param row
		 *            The row that the button belongs to
		 */
		public RowRemover(TableLayout list, TableRow row) {
			this.list = list;
			this.rowToBeRemoved = row;
		}

		public void onClick(View view) {
			list.removeView(rowToBeRemoved);

		}
	}

	public void addButton() {
		// projectTable.setVisibility(TableLayout.VISIBLE);
		TableRow newRow = new TableRow(projectTable.getContext());
		newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// newRow.addView(child, width, height)

		EditText etdynamic = new EditText(newRow.getContext());
		etdynamic.setText("");
		etdynamic.setHint("Tap to enter ");
		etdynamic.setWidth(215); // for emulator 215
		etdynamic.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
		etdynamic.setId(++rowsSoFar);

		idCount++;

		// actionButton.setText( "Action: " + ++rowsSoFar );
		Button removeSelfButton = new Button(newRow.getContext());
		removeSelfButton.setText(" - "); // for tablet ***** add space

		// removeSelfButton.setBackgroundResource(R.drawable.button_plus_green);
		// removeSelfButton.setBackgroundColor(color)
		// pass on all the information necessary for deletion
		removeSelfButton
		.setOnClickListener(new RowRemover(projectTable, newRow));

		newRow.addView(etdynamic);

		newRow.addView(removeSelfButton);
		projectTable.addView(newRow);

	}

	

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), menu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}