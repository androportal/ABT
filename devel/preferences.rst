Setting preferences
===================
* ABT provides preferences to add new ``projects`` for projectwise accounting and ``manual account code`` generation.

* if project checkbox is checked, new edittext will appear on the screen to add project names.

* and if manual account code checkbox is checked, manual account code field will be provided while creating account.

* User can also ``edit organisation details`` and ``add/edit/delete project name``.

* On selecting ``Master menu`` >> ``Preferences``, It provides a ``popup`` to select any one option from the above ``two``.

Preferences
+++++++++++

* Once you create a new organisation and save details of it, ABT asks for setting up project and account code preferences.

* The layout is included in ``res/layout/prefernces.xml``.

**File res/layout/preferences.xml**

	.. code-block:: xml
	
		<?xml version="1.0" encoding="utf-8"?>
		<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
		 android:layout_width="wrap_content"
		 android:layout_height="wrap_content"        
		 android:layout_centerHorizontal="true">
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			xmlns:tools="http://schemas.android.com/tools"
			android:layout_width="fill_parent"
			android:layout_height="fill_parent"
			android:background="@drawable/dark_gray_background"
			android:orientation="vertical">
		    <TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_gravity="center"
				    android:stretchColumns="2" >

			<TableRow
			    android:layout_width="wrap_content"
			    android:layout_height="match_parent" >

			    <CheckBox
				android:id="@+id/cbProject"
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:layout_weight="0.3"
				android:layout_marginRight="30dp"/>
			   
			    <TextView
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:layout_weight="1.7"
				android:text="Projectwise accounting"
				android:textColor="#FFFFFF"
				android:textSize="20dp" />

			</TableRow>           
		    </TableLayout>

		    <LinearLayout
			xmlns:android="http://schemas.android.com/apk/res/android"
			xmlns:tools="http://schemas.android.com/tools"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="vertical" >

			<TableLayout
			    xmlns:android="http://schemas.android.com/apk/res/android"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center"
			    android:stretchColumns="2" >

			    <TableRow
				android:layout_width="match_parent"
				android:layout_height="match_parent" >

				<EditText
				    android:id="@+id/etProject"
				    android:layout_width="fill_parent"
				    android:layout_height="wrap_content"
				    android:layout_weight="1.7"
				    android:hint="Tap to enter                        "
				    android:visibility="invisible" 
				    android:inputType="textCapWords"/>

				<Button
				    android:id="@+id/addProj"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:text="  +  "
				    android:visibility="invisible"
				    android:layout_weight="0.3"
				    />
			    </TableRow>
			</TableLayout>
		      
		   
			<TableLayout
			    xmlns:android="http://schemas.android.com/apk/res/android"
			    android:id="@+id/projtable"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center"
			    android:orientation="vertical"
			    android:paddingBottom="5dp"
			    android:paddingLeft="20dp"
			    android:paddingRight="20dp"
			    android:stretchColumns="2" />
		    </LinearLayout>
		      
		       
		       <TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    android:layout_width="112dp"
				    android:layout_height="wrap_content"
				    android:layout_gravity="center"
				    android:stretchColumns="2" >           
			    <TableRow 
				android:layout_width="112dp"
				android:layout_height="match_parent">
				  <Button
				    android:id="@+id/btnSavePref"
				    android:layout_width="112dp"
				    android:layout_height="46dp"
				    android:layout_gravity="center"
				    android:text="     Save      " 
				    android:textSize="20dp"
				    android:visibility="invisible"/>
				</TableRow>
			    </TableLayout>
			
			<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_gravity="center"
				    android:stretchColumns="2" >           
			    <TableRow 
				android:layout_width="wrap_content"
				android:layout_height="match_parent">

				<CheckBox
				    android:id="@+id/cbAccCode"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_weight="0.3"
					android:layout_marginRight="30dp" />

				<TextView
				    android:layout_width="wrap_content"
				    android:layout_weight="1.7"
				    android:text="Manual account code   "
				    android:textColor="#FFFFFF"
				    android:textSize="20dp" />

			    </TableRow>
			    </TableLayout>
			
		</LinearLayout>
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="horizontal"
			android:layout_alignParentBottom="true" >

			<Button
			    android:id="@+id/btnCreateAcc"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_alignParentBottom="true"
			    android:layout_weight="0.73"
			    android:text="Create account"
			    android:textSize="20dp" />
		       
			<Button
			    android:id="@+id/btnQuit"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_weight="1"
			    android:text="Quit"
			    android:textSize="20dp"
			   android:layout_alignParentBottom="true"/>
		</LinearLayout>

		</RelativeLayout> 

.. image:: images/preferences.png
	   :name: ABT main page
	   :align: center

* The associated java code to add projects and save preferences is given in below file,

**File src/com/example/gkaakash/preferences.java**

* The activity contains the essential and required import like

	.. code-block:: java
		package com.example.gkaakash;

		import java.util.ArrayList;
		import java.util.List;
		import com.gkaakash.controller.Organisation;
		import com.gkaakash.controller.Preferences;
		import com.gkaakash.controller.Startup;
		import android.app.Activity;
		import android.app.AlertDialog;
		import android.content.Context;
		import android.content.DialogInterface;
		import android.content.Intent;
		import android.os.Bundle;
		import android.text.InputType;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.view.ViewGroup.LayoutParams;
		import android.widget.Button;
		import android.widget.CheckBox;
		import android.widget.EditText;
		import android.widget.TableLayout;
		import android.widget.TableRow;
		import android.widget.Toast;
		

* The activity intializes all the essential parameters and variables.

* OnCreate method calls all required methods at load time.

	.. code-block:: java
	
		public class preferences extends Activity {
			//Declaring variables
			CheckBox cbProject, cbAccCode;
			EditText etProject,etdynamic;
			Button bCreateAcc;
			static String accCodeflag;
			private Button btnSavePref;
			private Preferences preference;
			private Integer client_id;
			protected Boolean setpref;
			protected String projectname;
			protected Context context;
			private Button btnQuit,btnaddproj;
			static String refNoflag;
			private TableLayout projectTable;
			int rowsSoFar=0;
			int count;
			static int idCount;
			public TableLayout list;
			public TableRow rowToBeRemoved;
			ArrayList<String> finalProjlist;
			protected ArrayList<String>[] projectnamelist;
			private Organisation organisation;
			boolean projectExistsFlag = false;


			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				//Calling preferences.xml
				setContentView(R.layout.preferences);
				// get Client_id return by Deploy method
				organisation = new Organisation();
				client_id= Startup.getClient_id();
				etProject = (EditText) findViewById(R.id.etProject);

				//for visibility of account tab layout
				MainActivity.tabFlag = false;

				//String from = Startup.getfinancialFromDate();
				//String to = Startup.getFinancialToDate();
				//System.out.println("from and to are...");
				//System.out.println(from+"-"+to);

				btnaddproj = (Button) findViewById(R.id.addProj);
				btnSavePref =(Button) findViewById(R.id.btnSavePref);
				btnSavePref.setVisibility(Button.GONE);
				//setting visibility
				etProject.setVisibility(EditText.GONE);
				btnaddproj.setVisibility(Button.GONE);
				// create object of Preference class
				preference = new Preferences();
				accCodeflag=preference.getPreferences(new Object[]{"2"},client_id );
				refNoflag=preference.getPreferences(new Object[]{"1"},client_id);
				
				addListenerOnButton();
				
				addListenerOnChkIos();
				
				Button addButton = (Button) findViewById( R.id.addProj );
				// Every time the "+" button is clicked,
				// add a new row to the table.
				addButton.setOnClickListener( new OnClickListener() {
					public void onClick(View view) {
						addButton(); }
					});
				   
				projectTable = (TableLayout) findViewById( R.id.projtable );
			}
			
* Below method explains activities happening on checking ``project name``/``account code`` checkbox.

* if project checkbox is checked, new edittext will appear on the screen to add project names.

* and if account code checkbox is checked, manual account code field will be provided while creating account.
		
	.. code-block:: java
			
		private void addListenerOnChkIos() {
			cbProject = (CheckBox) findViewById(R.id.cbProject);
			etProject = (EditText) findViewById(R.id.etProject);
			cbProject.setOnClickListener(new OnClickListener() {
			     
			    @Override
			    public void onClick(View v) {
				//for setting the visibility of EditText:'etProject' depending upon the condition
			    	   if (((CheckBox) v).isChecked()) {
				       etProject.setVisibility(EditText.VISIBLE);
				       btnaddproj.setVisibility(EditText.VISIBLE);
				       btnSavePref.setVisibility(Button.VISIBLE);
				       refNoflag = "optional";
				   }
				   else {
				       etProject.setVisibility(EditText.GONE);
				       btnaddproj.setVisibility(EditText.GONE);
				       btnSavePref.setVisibility(Button.GONE);
				       refNoflag = "mandatory";
				   }
			    }
			    });
		       
			cbAccCode = (CheckBox) findViewById(R.id.cbAccCode);
		       
			cbAccCode.setOnClickListener(new OnClickListener() {
			     
			    @Override
			    public void onClick(View v) {
				//Setting the account code flag value
			    	 if (((CheckBox) v).isChecked()) {
				     
				     accCodeflag = "manually";
				     Object[] params = new Object[]{"1",refNoflag,"2",accCodeflag};
				     //Object[] params1 = new Object[]{finalProjlist};
				     setpref = preference.setPreferences(params, finalProjlist, client_id);
				 }
				 else {
				     accCodeflag = "automatic";
				     Object[] params = new Object[]{"1",refNoflag,"2",accCodeflag};
				     //Object[] params1 = new Object[]{finalProjlist};
				     setpref = preference.setPreferences(params, finalProjlist, client_id);
				 }
			    }
			    });
		    }

* Below method explains listener on ``Create Account`` and ``Save`` button.

* Create Account button will call another activity ie. ``createAccount.class``.

* and ``Save`` button will save project names in database if it is not exist already.

	.. code-block:: java
   
		private void addListenerOnButton() {
			bCreateAcc = (Button) findViewById(R.id.btnCreateAcc);

			final Context context = this;
			//Create a class implementing “OnClickListener” and set it as the on click listener for the button
			bCreateAcc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(android.view.View v) {

					//To pass on the activity to the next page
					Intent intent = new Intent(context, createAccount.class);
					//intent.putExtra("finish_flag","login");
					startActivity(intent);  
				}
			});
			
			btnSavePref =(Button) findViewById(R.id.btnSavePref);
			//Create a class implementing “OnClickListener” and set it as the on click listener for the button
			btnSavePref.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(android.view.View v) {
					EditText projectName;
					String proj_name;
					View v1 = null;
					List<String> secondProjlist=new ArrayList<String>();
					projectname = etProject.getText().toString();
					//System.out.println("projectname:"+projectname);

					for(int i = 1; i <= idCount ; i++){  
						v1 = findViewById(i);
					    	if(v1 != null){
							projectName = (EditText) findViewById(i);
							proj_name= projectName.getText().toString();
							if(!"".equals(proj_name)){
							    secondProjlist.add(proj_name);
							}
					    	}
					}

					finalProjlist = new ArrayList<String>();
					if(!"".equals(projectname)){
					    finalProjlist.add(projectname);
					}

					finalProjlist.addAll(secondProjlist);
					//System.out.println("final project list");
					//System.out.println(finalProjlist);

					//call the getAllProjects method to get all projects
					Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
					// create new array list of type String to add gropunames
					List<String> projectnamelist = new ArrayList<String>();
					projectnamelist.add("No Project");
					for(Object pn : projectnames)
					{	
						Object[] p = (Object[]) pn;
						projectnamelist.add((String) p[1]); //p[0] is project code & p[1] is projectname
					}	
					//System.out.println("second project list");
					//System.out.println(projectnamelist);

					String ac;
					boolean  flag = false;
					String nameExists = "";
		
					for (int i = 0; i < finalProjlist.size(); i++) {
						ac = finalProjlist.get(i);
						for (int j = 0; j < finalProjlist.size(); j++)
						{
							if (i!=j)
							{
								System.out.println("next element"+finalProjlist.get(j));
								if(ac.equals(finalProjlist.get(j)))
								{
									System.out.println("equal");
									flag = true;
									break;
								}
							}
							else
							{
								flag = false;
								System.out.println("not equal");
							}
						}
					}
		
					if(flag == true){
						String message = "Project names can not be same";
						toastValidationMessage(message);
					}
					else{
						for(int i=0;i<finalProjlist.size();i++){
							for(int j=0;j<projectnamelist.size();j++){
								if((finalProjlist.get(i).toString()).equals(projectnamelist.get(j).toString())){
									projectExistsFlag = true;
									nameExists = finalProjlist.get(i).toString();
									break;
								}
								else{
									projectExistsFlag = false;
								}
							}
							if(projectExistsFlag == true){
								break;
							} 
						}

						 if(refNoflag.equals("optional")&& etProject.length()<1){
							 String message = "Please enter project name";
							 toastValidationMessage(message);
					}
					else if(projectExistsFlag == true){
					 	String message = "Project "+nameExists+" already exists";
					 	toastValidationMessage(message);
					}
					else
					{
						Object[] params = new Object[]{"1",refNoflag,"2",accCodeflag};
						//Object[] params1 = new Object[]{finalProjlist};
						setpref = preference.setPreferences(params, finalProjlist, client_id);
						//To pass on the activity to the next page

						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						       builder.setMessage("Preferences have been saved successfully!");
						       AlertDialog alert = builder.create();
						       alert.setCancelable(true);
						       alert.setCanceledOnTouchOutside(true);
						       alert.show();


							       cbAccCode.setChecked(false);
						cbProject.setChecked(false);
						etProject.setVisibility(EditText.GONE);
						etProject.setText("");
						btnaddproj.setVisibility(EditText.GONE);
						projectTable.removeAllViews();
						btnSavePref.setVisibility(Button.GONE);
					}
				}

			}
	
	
			/*
			 * The below method bulids an alert dialog for displaying message.
			 */
			public void toastValidationMessage(String message) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        builder.setMessage(message)
			                .setCancelable(false)
			                .setPositiveButton("Ok",
			                        new DialogInterface.OnClickListener() {
			                            public void onClick(DialogInterface dialog, int id) {
			                            	
			                            }
			                        });
			                
			        AlertDialog alert = builder.create();
			        alert.show();
		        } 
		        });
		        
		        /*
		         * ``Quite`` button takes you back to the ABT's welcome page.
		         */
			btnQuit =(Button) findViewById(R.id.btnQuit);
			btnQuit.setOnClickListener(new OnClickListener() {
			   
			    @Override
			    public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
			    }
			});
    		}
    		
* on pressing back button come back to the welcome page.
	
	.. code-block:: java
	
				
		public void onBackPressed(){
			//To pass on the activity to the next page
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
   

* Below class is used to delete ``project name`` edittext from screen.

* Gets all the information necessary to delete itself from the constructor.

* Deletes itself when the button is pressed.

	.. code-block:: java

    		private static class RowRemover implements OnClickListener {
        		public TableLayout list;
        		public TableRow rowToBeRemoved;
       
			/***
			 * @param list    The list that the button belongs to
			 * @param row    The row that the button belongs to
			 */
			public RowRemover( TableLayout list, TableRow row ) {
			    this.list = list;
			    this.rowToBeRemoved = row;
			}
		       
			public void onClick( View view ) {
			    list.removeView( rowToBeRemoved );
			   
			}
    		}

* Below method is used to generate a new ``project name edittext`` programatically when ``plus`` button is pressed.

	.. code-block:: java
	
    		public void addButton() {
			//projectTable.setVisibility(TableLayout.VISIBLE);
			TableRow newRow = new TableRow( projectTable.getContext() );
			newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			//newRow.addView(child, width, height)
		       
			EditText etdynamic = new EditText(newRow.getContext());
			etdynamic.setText( "" );
			etdynamic.setHint("Tap to enter                             ");
			etdynamic.setWidth(215); //for emulator 215
			etdynamic.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
			etdynamic.setId(++rowsSoFar);
		       
			idCount ++;
			 
			//actionButton.setText( "Action: " + ++rowsSoFar );
			Button removeSelfButton = new Button( newRow.getContext() );
			removeSelfButton.setText( "  -  " ); //for tablet ***** add  space
		       
			//removeSelfButton.setBackgroundResource(R.drawable.button_plus_green);
			//removeSelfButton.setBackgroundColor(color)
			// pass on all the information necessary for deletion
			removeSelfButton.setOnClickListener( new RowRemover( projectTable, newRow ));
		       
			newRow.addView(etdynamic);
		       
			newRow.addView( removeSelfButton );
			projectTable.addView(newRow);
       
    		}

* User can also ``edit organisation details`` and ``add/edit/delete project name``.

* On selecting ``Master menu`` >> ``Preferences``, It provides a ``popup`` to select any one option from the above ``two``.


Edit organisation details
+++++++++++++++++++++++++

* On clicking this option, It will take us to the edit organisation detail page.

* This page will display previously saved organisation information.

* User can edit these fields if required.

* and press ``Save`` button to save details in database.

* The layout is included in ``res/layout/org_details.xml`` and the associated code for saving details is included in ``src/com/example/gkaakash/orgDetails.java``.

* To refer these files go to `this <maintaining_organisation.html#create-new-organisation>`_ chapter.



Add/Edit/Delete project
+++++++++++++++++++++++

* On slecting this option, It will call a new activity and a new screen appers.

* Screen includes a ``Plus`` button at the top to add new project names and a list containing project names.

* Layout is included in ``res/layout/add_project.xml``.

**res/layout/add_projectnames.xml**

	.. code-block:: xml
	
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		android:layout_width="fill_parent"
		android:layout_height="fill_parent" 
		android:orientation="vertical">
		
			<LinearLayout  
				    android:layout_width="fill_parent"
					android:layout_height="50dp"
					android:minHeight="50dp"
				    android:orientation="horizontal"
				    android:paddingLeft="10dp"
					android:paddingRight="10dp"
					android:background="@android:color/darker_gray"
					android:layout_weight="0.1">
				     
					<Button
					android:id="@+id/add_project"
					android:layout_width="wrap_content"
					android:layout_height="wrap_content" 
					android:text="+"
					android:layout_gravity="center_vertical"
					android:layout_weight="0.1"
					android:gravity="center"/>
					
						<TextView
						    android:layout_width="wrap_content"
						    android:layout_height="wrap_content"
						    android:text="Add project"
						    android:textColor="#000000"
						    android:layout_gravity="center_vertical"
						    android:textSize="17dp"
						    android:gravity="center"
						    android:layout_weight="0.9"/>
			
					</LinearLayout>
		
				<ListView 
				    android:id="@+id/ltProjectNames"
					android:layout_width="fill_parent"
					android:layout_height="wrap_content"
					android:paddingLeft="10dp"
					android:paddingRight="10dp"
					android:layout_weight="1">
				</ListView>
		
    		</LinearLayout>

		
		
.. image:: images/project_list.png
	   :name: ABT main page
	   :align: center
	  
* On selecting any project name from list, it diplays a popup which having two options such as ``edit`` or ``delete`` project name.

* If project has transactions, it can not be deleted.

* The associated java code is included in 

**File src/com/example/gkaakash/addProject.java**
	   
* The activity is explained below along with code.

* It contains the essential and required import like

	.. code-block:: java
	
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

* The activity intializes all the essential parameters and variables.

* OnCreate method calls all required methods at load time.

	.. code-block:: java

		public class addProject extends MainActivity{
			private Integer client_id;
			static int idCount;
			final Context context = this;
			Button add;
			private TableLayout projectTable;
			int rowsSoFar=0;
			int count;
			AlertDialog dialog;
			EditText etProject,etdynamic;
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
	
			//on load...
    			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.add_projectnames);
				account = new Account();
				preferences = new Preferences();
				organisation = new Organisation();
				client_id= Startup.getClient_id();
				ltProjectNames = (ListView) findViewById(R.id.ltProjectNames);
				ltProjectNames.setCacheColorHint(color.transparent);
				ltProjectNames.setTextFilterEnabled(true);
		
				//get all project names in list view on load
				projectnames = (Object[])organisation.getAllProjects(client_id);
				getResultList(projectnames);
		
				addProject();
				editProject();
    			}

* ``editProject`` method used to ``edit/delete`` the project name.

* Initially it displays a popup which has two options namely edit and delete project name.

* Reference layout for building a customised edit project dialog is included in ``res/layout/edit_projectname.xml``.

**File res/layout/edit_projectname.xml**

	.. code-block:: xml
		
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		android:layout_width="fill_parent"
		android:layout_height="fill_parent" >

			<EditText
			    android:id="@+id/edit_project_name"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_weight="1"
			    android:inputType="textCapWords"
			    android:ems="10" >

			    <requestFocus />
			</EditText>

		</LinearLayout>


* validation is added for existing project name and for transaction check as well.

	.. code-block:: java

    		private void editProject() {
		    	ltProjectNames = (ListView) findViewById(R.id.ltProjectNames);
		    	ltProjectNames.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
				
				final CharSequence[] items = { "Edit project name", "Delete project" };
				//creating a dialog box for popup
		        	AlertDialog.Builder builder = new AlertDialog.Builder(addProject.this);
		        	//setting title
		        	builder.setTitle("Edit/Delete project");
		        	//adding items
		        	builder.setItems(items, new DialogInterface.OnClickListener() {
		        	public void onClick(DialogInterface which, int pos) {
		        		//code for the actions to be performed on clicking popup item goes here ...
			            	switch (pos) {
			            	//edit project
			                case 0:
			                	{
		                    		//Toast.makeText(edit_account.this,"Clicked on:"+items[pos],Toast.LENGTH_SHORT).show();
		                          	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		                          	View layout = inflater.inflate(R.layout.edit_projectname, (ViewGroup) findViewById(R.id.layout_root));
						AlertDialog.Builder builder = new AlertDialog.Builder(addProject.this);
						builder.setView(layout);
						builder.setTitle("Edit project");
						//get account details
    		                      	    	final String old_projectname = ltProjectNames.getItemAtPosition(position).toString();
    		                      	    
    		                      	    	final EditText edit_project_name = (EditText)layout.findViewById(R.id.edit_project_name);
    		                      	    	edit_project_name.setText(old_projectname);
		                          	    
		                  	       	builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
		    								
							public void onClick(DialogInterface dialog, int which) {
							
								String new_project_name = edit_project_name.getText().toString();
		    									
								/*
								 * validation to check whether project exists or blank
								 */
								for(int i=0;i<projectNameList.size();i++){
									if(new_project_name.equalsIgnoreCase((String) projectNameList.get(i))){
										projectExistsFlag = true;
										break;
									}
									else{
										projectExistsFlag = false;
									}
		    		                            	}
		    		                           
								if(new_project_name.length()<1){
								 	toastValidationMessage("Please enter project name");
								}
								if(new_project_name.equalsIgnoreCase(old_projectname)){
								 	toastValidationMessage("No changes made");
								}
								else if(projectExistsFlag == true){
								 	toastValidationMessage("Project '"+new_project_name+"' already exists");
								}
								else{
									Integer projCode = (Integer) projectCodeList.get(position);
									Object[] params = new Object[] {projCode, new_project_name};
									String edited = (String)preferences.editProject(params,client_id);
									//get all project names in list view on load
									projectnames = (Object[])organisation.getAllProjects(client_id);
									getResultList(projectnames);
									toastValidationMessage("Project name has been changed from '"+old_projectname+"' to '"+new_project_name+ "'");
		    		                             }
		    					}//end of onclick
		    				});// end of onclickListener
		                  	             
		                  	       	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								//do nothing
							}
		    				});
		                          	            
						dialog=builder.create();
						((Dialog) dialog).show();
		                  	           
		                		}
		                		break;
		                		
		                	//delete existing project name
			                case 1:
                				{
        					String proj = ltProjectNames.getItemAtPosition(position).toString();
        					Object[] params = new Object[] {proj};
	                            		String edited = (String)preferences.deleteProjectName(params,client_id);
			                    	if(edited.equalsIgnoreCase("project deleted")){
			                    		toastValidationMessage("Project '"+ proj +"' deleted successfully");
			                    		projectnames = (Object[])organisation.getAllProjects(client_id);
		    							    getResultList(projectnames);
			                    	}
			                    	else{
			                    		toastValidationMessage("Project '"+ proj +"' can't be deleted, it has transactions");
			                    	}
	                            	
                				}break;
			            	}
		        	}
		        	});
		        	//building a complete dialog
				dialog=builder.create();
				dialog.show();
			}
			});
		}

* ``addProject`` method used to add new project names.

* When ``plus`` button is pressed, it displays a dialog which contains edittext to add a project.

* Reference layout for building a customised add project dialog is included in ``res/layout/add_project.xml``.

**File res/layout/add_project.xml**

	.. code-block:: xml
		
		<?xml version="1.0" encoding="utf-8"?>
		<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
		 android:layout_width="wrap_content"
		 android:layout_height="wrap_content"        
		 android:layout_centerHorizontal="true">
		 
			<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
		    	android:layout_width="fill_parent"
		    	android:layout_height="fill_parent" >
		    	
			<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			xmlns:tools="http://schemas.android.com/tools"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="vertical"
			android:paddingTop="10dp">
			
		    		<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_gravity="center"
				    android:stretchColumns="2" >

					<TableRow
					    android:layout_width="wrap_content"
					    android:layout_height="wrap_content" >
			   
			    			<TextView
							android:layout_width="wrap_content"
							android:layout_height="wrap_content"
							android:layout_weight="1.7"
							android:text="           Add project           "
							android:textColor="#FFFFFF"
							android:textSize="20dp" />

					</TableRow>           
		    		</TableLayout>

			    <LinearLayout
				xmlns:android="http://schemas.android.com/apk/res/android"
				xmlns:tools="http://schemas.android.com/tools"
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:orientation="vertical" >

				<TableLayout
				    xmlns:android="http://schemas.android.com/apk/res/android"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_gravity="center"
				    android:stretchColumns="2" >

				    <TableRow
					android:layout_width="match_parent"
					android:layout_height="wrap_content" >

					<EditText
					    android:id="@+id/etProjectname"
					    android:layout_width="fill_parent"
					    android:layout_height="wrap_content"
					    android:layout_weight="1.7"
					    android:hint="Tap to enter                        " 
					    android:inputType="textCapWords"/>

					<Button
					    android:id="@+id/addProject"
					    android:layout_width="wrap_content"
					    android:layout_height="wrap_content"
					    android:text="  +  "
					    android:layout_weight="0.3"
					    />
				    </TableRow>
				</TableLayout>
		       
				<TableLayout
				    xmlns:android="http://schemas.android.com/apk/res/android"
				    android:id="@+id/projecttable"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_gravity="center"
				    android:orientation="vertical"
				    android:paddingBottom="5dp"
				    android:paddingLeft="20dp"
				    android:paddingRight="20dp"
				    android:stretchColumns="2" />
		   
		    	</LinearLayout>
		       
			<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_gravity="center"
				    android:stretchColumns="2" >           
			    <TableRow 
				android:layout_width="wrap_content"
				android:layout_height="wrap_content">

			    </TableRow>
			</TableLayout>
			</LinearLayout>
			</ScrollView>
		</RelativeLayout> 
		 

* Multiple project names can be added at a time.

* Validation is provided if project name already exists.

	.. code-block:: java
	
		private void addProject() {
			Button addProject = (Button)findViewById(R.id.add_project);
			addProject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.add_project, (ViewGroup) findViewById(R.id.layout_root));
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
				builder.setTitle("Add projects");
				etProject = (EditText)layout.findViewById(R.id.etProjectname);
				projectTable = (TableLayout)layout.findViewById( R.id.projecttable );
				Button add=(Button) layout.findViewById(R.id.addProject);
				add.setOnClickListener(new OnClickListener() {
				    
				@Override
				public void onClick(View v) {
					addButton();
				}
                		});
                		builder.setPositiveButton("Add",new  DialogInterface.OnClickListener(){

				public void onClick(DialogInterface arg0, int which) {
					EditText projectName;
					String proj_name;
					View v1 = null;
					List<String> secondProjlist=new ArrayList<String>();
					projectname = etProject.getText().toString();

					for(int i = 1; i <= idCount ; i++){  
						v1 = dialog.findViewById(i);
					    	if(v1 != null){
							projectName = (EditText)dialog.findViewById(i);
							proj_name= projectName.getText().toString();
							if(!"".equals(proj_name)){
							    	secondProjlist.add(proj_name);
							}
					    	}
					}
                        
				        finalProjlist = new ArrayList<String>();
				        if(!"".equals(projectname)){
				            	finalProjlist.add(projectname);
				        }
                       
				        finalProjlist.addAll(secondProjlist);
				        AlertDialog help_dialog;
				        //call the getAllProjects method to get all projects
				        Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
				        // create new array list of type String to add gropunames
				        List<String> projectnamelist = new ArrayList<String>();
				        projectnamelist.add("No Project");
				        for(Object pn : projectnames)
				        {    
				            Object[] p = (Object[]) pn;
				            projectnamelist.add((String) p[1]); //p[0] is project code & p[1] is projectname
				        }    
                        
				        String ac;
				        boolean  flag = false;
				        String nameExists = "";
                        
				        for (int i = 0; i < finalProjlist.size(); i++) {
				            	ac = finalProjlist.get(i);
				            	for (int j = 0; j < finalProjlist.size(); j++)
				            	{
				                	if (i!=j)
				                	{
						            	if(ac.equals(finalProjlist.get(j)))
						            	{
						                	flag = true;
						                	break;
						           	}
			                		}
				                	else
				                	{
				                    	flag = false;
				                	}
				            	}
				        }
                        
				        if(flag == true){
				            toastValidationMessage("Project names can not be same");
				        }
				        else{
				      		for(int i=0;i<finalProjlist.size();i++){
				                	for(int j=0;j<projectnamelist.size();j++){
				                    		if((finalProjlist.get(i).toString()).equalsIgnoreCase(projectnamelist.get(j).toString())){
								        projectExistsFlag = true;
								        nameExists = finalProjlist.get(i).toString();
								        break;
				                    		}
								else{
									projectExistsFlag = false;
								}
				                	}
						        if(projectExistsFlag == true){
						            	break;
						        }
				            	}
                            
						if(etProject.length()<1){
				            	 	toastValidationMessage("Please enter project name");
				             	}
				             	else if(projectExistsFlag == true){
				            	 	toastValidationMessage("Project '"+nameExists+"' already exists");
				       		}
		                     		else
		                      		{
						    	 setProject = preferences.setProjects(finalProjlist,client_id);
						    	 //To pass on the activity to the next page
						    	 toastValidationMessage("Project added successfully");
						    	 //get all project names in list view on load
						    	 projectnames = (Object[])organisation.getAllProjects(client_id);
						    	 getResultList(projectnames);
						    	 etProject.setText("");
						    	 projectTable.removeAllViews();
		                      		}
                        		}
                    		}  
                		});
                
                		builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//close the dialog box
					}
				});
				dialog=builder.create();
				((Dialog) dialog).show();
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				//customizing the width and location of the dialog on screen 
				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.height = 300;
                		dialog.getWindow().setAttributes(lp);
			}
			});
		}

* getResultList method used to get the list of all project names and to set it in a listview.

	.. code-block:: java

		void getResultList(Object[] param){
			projectNameList = new ArrayList();
			projectCodeList = new ArrayList();
			for(Object pn : param)
			{   
				Object[] p = (Object[]) pn;
				projectCodeList.add((Integer)p[0]);
			    	projectNameList.add((String)p[1]); //p[1] is project_name and p[0] is project code
			}   
			ltProjectNames.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projectNameList));
		}
	
* Below class is used to delete ``project name`` edittext from customised alert dialog.

* Gets all the information necessary to delete itself from the constructor.

* Deletes itself when the button is pressed.
	
	.. code-block:: java
    		private static class RowRemover implements OnClickListener {
			public TableLayout list;
			public TableRow rowToBeRemoved;
       
			/***
			 * @param list    The list that the button belongs to
			 * @param row    The row that the button belongs to
			 */
			public RowRemover( TableLayout list, TableRow row ) {
			    this.list = list;
			    this.rowToBeRemoved = row;
			}

			public void onClick( View view ) {
			    list.removeView( rowToBeRemoved );
			   
			}
    		}
    
* Below method is used to generate a new ``project name edittext`` programatically when ``plus`` button is pressed.

	.. code-block:: java
	
		public void addButton() {
			//projectTable.setVisibility(TableLayout.VISIBLE);
			TableRow newRow = new TableRow( projectTable.getContext() );
			newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			//newRow.addView(child, width, height)
		       
			EditText etdynamic = new EditText(newRow.getContext());
			etdynamic.setText( "" );
			etdynamic.setHint("Tap to enter                              ");
			etdynamic.setWidth(215); //for emulator 215
			etdynamic.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
			etdynamic.setId(++rowsSoFar);
		
			idCount ++;
			 
			//actionButton.setText( "Action: " + ++rowsSoFar );
			Button removeSelfButton = new Button( newRow.getContext() );
			removeSelfButton.setText( "  -  " ); //for tablet ***** add  space
		       
			//removeSelfButton.setBackgroundResource(R.drawable.button_plus_green);
			//removeSelfButton.setBackgroundColor(color)
			// pass on all the information necessary for deletion
			removeSelfButton.setOnClickListener( new RowRemover( projectTable, newRow ));
		       
			newRow.addView(etdynamic);
		       
			newRow.addView( removeSelfButton );
			projectTable.addView(newRow);
		       
    		}
    
* create a sample alert dialog which can be used all over the page to display validation messages.

	.. code-block:: java
		
		public void toastValidationMessage(String message) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("Ok",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				                
				            }
				        });
				
			AlertDialog alert = builder.create();
			alert.show();
        
    		} 
   
* On pressing ``Back`` button of device, go to the master menu page.

	.. code-block:: java
		
		@Override
		public void onBackPressed() {
			Intent intent = new Intent(getApplicationContext(), menu.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent); 
	       	}

