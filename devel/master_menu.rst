Master menu
===========

* Master menu is the ``main page`` of the application.

* This page contains ``links`` to Account management, Transaction management, Maintaining organisations, Setting up preferences, Help and About page.

* menu.xml contains the list item layout.

**File res/layout/menu.xml**

	.. code-block:: xml
		
		<?xml version="1.0" encoding="utf-8"?>
		<TextView xmlns:android="http://schemas.android.com/apk/res/android"
		    android:id="@+id/listItem"
		    android:layout_width="fill_parent"
		    android:layout_height="fill_parent"
		    android:padding="20dp"
		    android:textSize="20sp"
		    android:textColor="#FFFFFF">
		</TextView>

.. image:: images/master_menu.png
	   :name: ABT main page
	   :align: center

* Its activity is explained below, along with the code.

**File src/com/example/gkaakash/menu.java**

* The activity contains the essential and required import like

	.. code-block:: java

		import java.io.DataOutputStream;
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.math.RoundingMode;
		import java.text.DecimalFormat;
		import java.text.SimpleDateFormat;
		import java.util.ArrayList;
		import java.util.Calendar;
		import java.util.Date;
		import java.util.List;
		import com.gkaakash.controller.Account;
		import com.gkaakash.controller.Organisation;
		import com.gkaakash.controller.Preferences;
		import com.gkaakash.controller.Startup;
		import android.R.drawable;
		import android.app.AlertDialog;
		import android.app.Dialog;
		import android.app.ListActivity;
		import android.content.Context;
		import android.content.DialogInterface;
		import android.content.Intent;
		import android.graphics.Color;
		import android.os.Bundle;
		import android.preference.Preference;
		import android.text.InputType;
		import android.text.SpannableString;
		import android.text.method.LinkMovementMethod;
		import android.text.util.Linkify;
		import android.view.LayoutInflater;
		import android.view.Menu;
		import android.view.MenuItem;
		import android.view.View;
		import android.view.ViewGroup;
		import android.view.WindowManager;
		import android.view.View.OnClickListener;
		import android.view.ViewGroup.LayoutParams;
		import android.widget.AdapterView;
		import android.widget.AdapterView.OnItemClickListener;
		import android.widget.ArrayAdapter;
		import android.widget.Button;
		import android.widget.CheckBox;
		import android.widget.DatePicker;
		import android.widget.EditText;
		import android.widget.LinearLayout;
		import android.widget.ListView;
		import android.widget.Spinner;
		import android.widget.TableLayout;
		import android.widget.TableRow;
		import android.widget.TextView;
		import android.widget.Toast;


* The activity intializes all the essential parameters and variables.

	.. code-block:: java

		String  voucherTypeFlag;
		private int group1Id = 1;
		int Edit = Menu.FIRST;
		int Delete = Menu.FIRST +1;
		int Finish = Menu.FIRST +2;
		AlertDialog dialog;
		final Context context = this;
		static String fromday, frommonth, fromyear, today, tomonth, toyear; 
		private Integer client_id;
		private Account account;
		private Preferences preferences;
		private Organisation organisation;
		AlertDialog help_dialog;
		static String financialFromDate;
		static String financialToDate;
		static String givenfromDateString;
		static String givenToDateString;
		DecimalFormat mFormat;
		static boolean validateDateFlag;
		static String selectedAccount;
		static boolean cleared_tran_flag;
		static boolean narration_flag;
		static ArrayList<String> accdetailsList;
		static String orgtype;
		String orgname;
		    
* Below method is used to get back to the welcome page of ABT.	
	.. code-block:: java
	
		@Override
		public void onBackPressed() {
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent); 
		}
		    
* OnCreate method adds ``menu items`` and ``OnItemClickListener`` to listView.
	.. code-block:: java
			    
		//adding list items to the newly created menu list
		String[] menuOptions = new String[] { "Create account", "Transaction", "Reports",
		    "Preferences","Bank Reconciliation","Help","About" };

		//on load...
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			account = new Account();
			preferences = new Preferences();
			organisation = new Organisation();
			client_id= Startup.getClient_id();

			//get financial from and to date, split and store day, month and year in seperate variable
			financialFromDate =Startup.getfinancialFromDate();  	   	
		   	String dateParts[] = financialFromDate.split("-");
		   	fromday  = dateParts[0];
		   	frommonth = dateParts[1];
		   	fromyear = dateParts[2];
		   	
		   	financialToDate = Startup.getFinancialToDate();
		   	String dateParts1[] = financialToDate.split("-");
		   	today  = dateParts1[0];
		   	tomonth = dateParts1[1];
		   	toyear = dateParts1[2];
		   	
		   	//for two digit format date for dd and mm
		  	mFormat= new DecimalFormat("00");
		  	mFormat.setRoundingMode(RoundingMode.DOWN);

			//calling menu.xml and adding menu list into the page
			setListAdapter(new ArrayAdapter<String>(this, R.layout.menu,menuOptions));

			//getting the list view and setting background
			final ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setBackgroundColor(R.drawable.dark_gray_background);
			listView.setCacheColorHint(Color.TRANSPARENT);

			//when menu list items are clicked, code for respective actions goes here ...
			listView.setOnItemClickListener(new OnItemClickListener() {
			
			    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

* Below section of code takes the user to ``create/Edit acocunt`` page.

	.. code-block:: java		

			if(position == 0)
			{
				MainActivity.tabFlag = true;
				Intent intent = new Intent(context, account_tab.class);
				// To pass on the value to the next page
				startActivity(intent);
			}
	
* Below section of code takes the user to ``voucherMenu`` page.

	.. code-block:: java

			//for "transaction"
			if(position == 1)
			{
				Intent intent = new Intent(context, voucherMenu.class);
				// To pass on the value to the next page
				startActivity(intent);
			}

* Below section of code take the user to ``reportMenu`` page.

	.. code-block:: java

			AlertDialog help_dialog;
			//for "reports"
			if(position == 2)
			{
				Intent intent = new Intent(context, reportMenu.class);
				// To pass on the value to the next page
				startActivity(intent);                     
			}
	
* It builds a dialog with two new option ie. ``Edit organisation details`` and ``Add/Edit/Delete Project``.

	.. code-block:: java

			//for "edit organisation" and "adding project", adding popup menu ...
			if(position == 3)
			{                	
				final CharSequence[] items = { "Edit organisation details", "Add/Edit/Delete project" };
				//creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				//setting title
				builder.setTitle("Select preference");
				//adding items
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog1, int pos) {
						//code for the actions to be performed on clicking popup item goes here ...
						switch (pos) {
		      	        case 0:
		      	        {
		      	        	
		      	        	MainActivity.editDetails=true;
		      	        	Object[] editDetails = (Object[])organisation.getOrganisation(client_id);
		      	        	accdetailsList = new ArrayList<String>();
		      	        	for(Object row2 : editDetails){
		      	        		Object[] a2=(Object[])row2;
		      	        		ArrayList<String> accdetails = new ArrayList<String>();
					for(int i=0;i<a2.length;i++){
						accdetails.add((String) a2[i].toString());
					}
					accdetailsList.addAll(accdetails);
		      	        	}
					     
		      	        	//System.out.println("details:"+accdetailsList);
					   
		      	        	Intent intent = new Intent(context, orgDetails.class);
		      	        	// To pass on the value to the next page
		      	        	startActivity(intent);
		      	        }break;
		      	        case 1:
		      	        {
		      	        	Intent intent = new Intent(context, addProject.class);
		      	        	// To pass on the value to the next page
		      	        	startActivity(intent);
		      	        	
		      	        }break;
						}
					}
				});
				//building a complete dialog
				dialog=builder.create();
				dialog.show();

			}

* Below section of code creates the ``alert dialog`` for ``Bank Reconciliation`` Index which will look like this,

.. image:: images/bank_reco_before.png
	   :name: ABT main page
	   :align: center 

* The associated layout is included in ``res/layout/bank_recon_index.xml``.

**File res/layout/bank_recon_index.xml**

	.. code-block:: xml
	
		 <ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="fill_parent"
		    android:layout_height="fill_parent" >
		    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="fill_parent"
			android:layout_height="fill_parent" 
			android:orientation="vertical"
			 android:id="@+id/layout_root" >
			 
			 <TextView
			     android:layout_width="wrap_content"
			     android:layout_height="wrap_content"
			     android:layout_gravity="center"
			     android:text="Account name"
			     android:textColor="#FFFFFF"
			     android:textSize="20dp" />

			<Spinner
			    android:id="@+id/sBankAccounts"
			    android:layout_width="254dp"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center"
			    android:entries="@array/accountName_arrays"
			    android:prompt="@string/accountName_prompt" />
		
		
			   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="fill_parent"
			android:layout_height="fill_parent" 
			android:orientation="horizontal"
			 android:id="@+id/layout_root" >
		
			 
			 <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="fill_parent"
			android:layout_height="fill_parent" 
			android:orientation="vertical"
			 android:id="@+id/layout_root"
			 android:layout_gravity="left"
			  android:layout_weight="50" >
		
			 <TextView
			     android:id="@+id/tvsetFromdate"
			     android:layout_width="wrap_content"
			     android:layout_height="wrap_content"
			     android:layout_gravity="center"
			     android:text="From"
			     android:textColor="#FFFFFF"
			     android:textSize="20dp" />

			 <DatePicker
			     android:id="@+id/dpsetReconFromdate"
			     android:layout_width="wrap_content"
			     android:layout_height="wrap_content"
			     android:layout_gravity="center" />

			</LinearLayout>
		
		
			 <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			android:layout_width="fill_parent"
			android:layout_height="fill_parent" 
			android:orientation="vertical"
			 android:id="@+id/layout_root"
			 android:layout_weight="50" >
			 
			<TextView
			    android:id="@+id/tvsetT0date"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center"
			    android:text="To"
			    android:textColor="#FFFFFF"
			    android:textSize="20dp" />

			<DatePicker
			    android:id="@+id/dpsetReconT0date"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center" />
			</LinearLayout>
		
			 </LinearLayout>
			 
			   <TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    		android:layout_width="wrap_content"
							android:layout_height="wrap_content"
							android:layout_gravity="center">

				<TableRow>
				    <CheckBox
					android:id="@+id/cbClearedTransaction"
					android:layout_width="wrap_content"
					android:layout_height="wrap_content"
					android:layout_weight="0.3" 
					android:layout_marginRight="30dp"/>
				    
				    <TextView
					android:layout_width="wrap_content"
					android:layout_weight="1.7"
					android:text="with cleared transactions"
					android:textColor="#FFFFFF"
					android:textSize="20dp" />
				</TableRow>
		
				<TableRow>
				    <CheckBox
					android:id="@+id/cbReconNarration"
					android:layout_width="wrap_content"
					android:layout_height="wrap_content"
					android:layout_weight="0.3" 
					android:layout_marginRight="30dp"/>
				    
				    <TextView
					android:layout_width="wrap_content"
					android:layout_weight="1.7"
					android:text="with narration"
					android:textColor="#FFFFFF"
					android:textSize="20dp" />
				</TableRow>
				</TableLayout>

		    </LinearLayout>
		    </ScrollView>

.. image:: images/bank_reco_before.png
	   :name: ABT main page
	   :align: center

* Above alert dialog contains ``account name`` dropdown for which reconciliation to be done, two datepickers for ``from date`` and ``to date``, two checkboxes for ``narration`` and ``cleared transations`` and the ``view`` button.

* and the associated java code to build above dialog is given below,

* Initially get all account names from the database in list format.

* Check the ``length`` of the account name list. if list length is equal to ``0``, it throws validation message else places it in a account name dropdown.

* set ``financial year`` from and to date in datepicker.

	.. code-block:: java

		//bank reconcilition
		if(position == 4){
	
			//call the getAllBankAccounts method to get all bank account names
			Object[] accountnames = (Object[]) account.getAllBankAccounts(client_id);
			// create new array list of type String to add account names
			List<String> accountnamelist = new ArrayList<String>();
			for(Object an : accountnames)
			{	
				accountnamelist.add((String) an); 
			}	

			if(accountnamelist.size() <= 0){
				String message = "Bank reconciliation statement cannot be displayed, Please create bank account!";
				toastValidationMessage(message);
				}
			else{

			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.bank_recon_index, (ViewGroup) findViewById(R.id.layout_root));
				//Building DatepPcker dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
				builder.setTitle("Bank reconcilition");
	
				//populate all bank account names in accountname dropdown(spinner)
				final Spinner sBankAccounts = (Spinner)layout.findViewById(R.id.sBankAccounts);
				ArrayAdapter<String> da = new ArrayAdapter<String>(menu.this, 
											android.R.layout.simple_spinner_item,accountnamelist);
		  	   	da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  	   	sBankAccounts.setAdapter(da);
	
				final DatePicker ReconFromdate = (DatePicker) layout.findViewById(R.id.dpsetReconFromdate);
				ReconFromdate.init(Integer.parseInt(fromyear),(Integer.parseInt(frommonth)-1),Integer.parseInt(fromday), null);
			   	
			   	final DatePicker ReconT0date = (DatePicker) layout.findViewById(R.id.dpsetReconT0date);
			   	ReconT0date.init(Integer.parseInt(toyear),(Integer.parseInt(tomonth)-1),Integer.parseInt(today), null);
	
			   	final CheckBox cbClearedTransaction = (CheckBox)layout.findViewById(R.id.cbClearedTransaction);
			   	final CheckBox cbNarration = (CheckBox)layout.findViewById(R.id.cbReconNarration);
			   	
				builder.setPositiveButton("View",new  DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
		
						if(cbClearedTransaction.isChecked()){
					   		cleared_tran_flag = true;
					   	}
					   	else{
					   		cleared_tran_flag = false;
					   	}
					   	
					   	if(cbNarration.isChecked()){
					   		narration_flag = true;
					   	}
					   	else{
					   		narration_flag = false;
					   	}
			
						selectedAccount = sBankAccounts.getSelectedItem().toString();
			
						System.out.println("i am account"+selectedAccount);
						validateDate(ReconFromdate, ReconT0date, "validatebothFromToDate");
			
			
						if(validateDateFlag){
							Intent intent = new Intent(context, bankReconciliation.class);
							// To pass on the value to the next page
							startActivity(intent);
						}
					}

		
		
				});
	
				builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
		
				});
				dialog=builder.create();
				dialog.show();
	
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				//customizing the width and location of the dialog on screen 
				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.width = 700;
				dialog.getWindow().setAttributes(lp);
			}
		
		}

* Below section of code builds ``Help`` dialog for the application.

	.. code-block:: java

		//for help
		if(position == 5){
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.help_popup,
			    (ViewGroup) findViewById(R.id.layout_root));

			// builder
			AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
			builder.setView(layout);
			builder.setTitle("Help");
			
			CheckBox cbHelp = (CheckBox)layout.findViewById(R.id.cbHelp);
			cbHelp.setVisibility(CheckBox.GONE);
			help_dialog = builder.create();
			help_dialog.show();
			
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			// customizing the width and location of the dialog on screen
			lp.copyFrom(help_dialog.getWindow().getAttributes());
			lp.width = 700;
			help_dialog.getWindow().setAttributes(lp);
			help_dialog.setCancelable(true);
		}

* It builds ``About`` page dialog.

.. code-block:: java

		//for about
		if(position == 6){
			AlertDialog about_dialog;
		  	final SpannableString s = 
			new SpannableString(context.getText(R.string.about_para));
			Linkify.addLinks(s, Linkify.WEB_URLS);


			// Building DatepPcker dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(
			    context);
			builder.setTitle("Aakash Business Tool");
			builder.setMessage( s );
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			    // TODO Auto-generated method stub
			    
			}

			});

			about_dialog = builder.create();
			about_dialog.show();

			((TextView)about_dialog.findViewById(android.R.id.message))
			.setMovementMethod(LinkMovementMethod.getInstance());

			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			// customizing the width and location of the dialog on screen
			lp.copyFrom(about_dialog.getWindow().getAttributes());
			lp.width = 600;

			about_dialog.getWindow().setAttributes(lp);
		}
	    } 
	});
     }


