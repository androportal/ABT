Account management
==================

* Account management covers three major parts ie. Create,Search and Edit/Delete account .

* layout for hosting ``Create account`` and ``Edit account`` tabs is included in ``res/layout/tab.xml``.

Tab host for create/edit account
++++++++++++++++++++++++++++++++

**File res/layout/tab.xml**
        
	.. code-block:: xml

		<?xml version="1.0" encoding="utf-8"?>
		<TabHost xmlns:android="http://schemas.android.com/apk/res/android"
		    android:id="@android:id/tabhost"
		    android:layout_width="fill_parent"
		    android:layout_height="fill_parent">
		    <LinearLayout
			android:orientation="vertical"
			android:layout_width="fill_parent"
			android:layout_height="fill_parent">
			<TabWidget
			    android:id="@android:id/tabs"
			    android:layout_width="fill_parent"
			    android:layout_height="wrap_content"/>
			<FrameLayout
			    android:id="@android:id/tabcontent"
			    android:layout_width="fill_parent"
			    android:layout_height="fill_parent"/>
		    </LinearLayout>
		</TabHost>

* Associated activity for tab hosting is included in ``account_tab.java``.

**File src/com.example.gkaakash/account_tab.java**

* Its activity is explained below along with code.

* The activity contains the essential and required import like

	.. code-block:: java			

		import com.gkaakash.controller.Preferences;
		import com.gkaakash.controller.Startup;
		import android.app.TabActivity;
		import android.content.Intent;
		import android.graphics.Color;
		import android.os.Bundle;
		import android.widget.TabHost;
		import android.widget.TabHost.TabSpec;
		import android.widget.TextView;

* The activity intializes all the essential parameters and variables.

	.. code-block:: java
	
		String accCodeCheckFlag;
		TextView tab1 = null;
		TextView tab2 = null;
		private Integer client_id;
		private Preferences preferences;


* onCreate method creates two Tabspec and include them in Tabhost.

* It sets ``Create account`` as bydefault tab.

	.. code-block:: java
	
		public class account_tab extends TabActivity{
		
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.tab);
				//accCodeCheckFlag = getIntent().getExtras().getString("flag");
			 
				final TabHost tabHost = getTabHost();
			
				//creating TabSpec for create account
				TabSpec createspec = tabHost.newTabSpec("tab1");
			
				tab1 = new TextView(this);
				//setting properties in textView
				tab1.setGravity(android.view.Gravity.CENTER);
				tab1.setTextSize(18.0f);
				tab1.setHeight(50);
				tab1.setTextColor(Color.WHITE);
				tab1.setText("Create account");
				createspec.setIndicator(tab1);//assigning TextView to tab Indicator
			
				preferences = new Preferences();
			
				// this is client_id get after getConnetion method call for existing organisation 
				client_id = Startup.getClient_id();
			
				// call getPreferences to get flag for account code
				accCodeCheckFlag = preferences.getPreferences(new Object[]{2},client_id);
			
				//for visibility of account tab layout 
				MainActivity.tabFlag = true;
				Intent create = new Intent(this, createAccount.class);
			
				// flag for finish button of account page 
				//create.putExtra("finish_flag","menu");
				createspec.setContent(create);
				tabHost.addTab(createspec);  // Adding create tab
		
				//creating TabSpec for edit account
				TabSpec editspec = tabHost.newTabSpec("tab2");
				tab2 = new TextView(this);
				//setting properties in textView
				tab2.setGravity(android.view.Gravity.CENTER);
				tab2.setTextSize(18.0f);
				tab2.setHeight(50);
				tab2.setTextColor(Color.WHITE);
				tab2.setText("Search/Edit account");
				editspec.setIndicator(tab2);//assigning TextView to tab Indicator
			
				Intent edit = new Intent(this, edit_account.class);
				editspec.setContent(edit);
				tabHost.addTab(editspec); // Adding edit tab
				tabHost.setCurrentTab(0);//setting tab1 on load
		 	}
		}


Create account
++++++++++++++

**File res/layout/create_account.xml**

* The layout for create account page is included in ``create_account.xml``.

		.. code-block:: xml

			<?xml version="1.0" encoding="utf-8"?>
			<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			 android:layout_width="fill_parent" 
			 android:layout_height="fill_parent"
			 android:orientation="vertical"
			 android:weightSum="100"
			 android:background="@drawable/dark_gray_background">
			 
			    <LinearLayout
				   android:id="@+id/createacc_tab1"
				   android:orientation="horizontal"
				   android:layout_width="400dp"
				   android:layout_height="3dp"
				   android:paddingLeft="20dp"
				   android:paddingRight="20dp"
				   android:background="#60AFFE"
				   android:visibility="invisible"/>
			   
			    <LinearLayout
				    android:id="@+id/createacc_tab2"
				   android:orientation="horizontal"
				   android:layout_width="match_parent"
				   android:layout_height="3dp"
				   android:paddingLeft="20dp"
				   android:paddingRight="20dp"
				   android:background="#60AFFE"
				   android:visibility="invisible"/>
			    
			<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
			    android:layout_width="fill_parent"
			    android:layout_height="fill_parent"
			    android:layout_weight="80">

			    <TableLayout 
					android:layout_width="fill_parent"
					android:layout_height="wrap_content"
					android:paddingLeft="10dp"
					 android:paddingRight="10dp">
		
				<TableRow>
				    <TextView 
					android:layout_width="0dip"
					android:layout_weight="1"
					android:text="Group name"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				<Spinner 
				    android:id="@+id/sGroupNames"
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:prompt="@string/grpName_prompt"
				    />
				</TableRow>
		
				<TableRow>
				    <TextView 
					android:layout_width="0dip"
					android:layout_weight="1"
					android:text="Subgroup name"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				<Spinner 
				    android:id="@+id/sSubGrpNames"
				    android:layout_width="0dip"
				     android:prompt="@string/subgrpName_prompt"
				    android:layout_weight="1.3"
				    />
				</TableRow>
				 <TableRow>
				    <TextView 
					android:id="@+id/tvSubGrp"
					android:layout_width="0dip"
					android:layout_weight="1"
					android:text="Enter new subgroupname"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				<EditText 
				    android:id="@+id/etSubGrp"
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:hint="Tap to enter newsubgroup name"
				    android:inputType="textCapWords"/>
				</TableRow>
			 
				<TableRow>
				    <TextView 
					android:layout_width="0dip"
					android:layout_weight="1"
					android:text="Account name"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				<EditText 
				    android:id="@+id/etAccName"
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:hint="Tap to enter account name"
				    android:inputType="textCapWords" />
				</TableRow>
		
				<TableRow>
				    <TextView 
					android:id="@+id/tvAccCode"
					android:layout_width="0dip"
					android:layout_weight="1"
					android:text="Account code"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"
					android:visibility="invisible"/>
				<EditText 
				    android:id="@+id/etAccCode"
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:hint="Tap to enter account code"
				    android:visibility="invisible" />
				</TableRow>
		
				<TableRow>
				    <TextView 
					android:id="@+id/tvOpBal"
					android:layout_width="0dip"
					android:layout_weight="0.95"
					android:text="Opening balance"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				    
				 <TextView 
					android:id="@+id/tvOpBalRupeeSymbol"
					android:layout_width="0dip"
					android:layout_weight="0.05"
					android:text="\u20B9"
					android:textColor="#FFFFFF"
					android:textSize="22dp"
					android:layout_gravity="center_vertical"/>
				    
				<EditText 
				    android:id="@+id/etOpBal"
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:text="0.00"
				    android:inputType="phone"/>
				</TableRow>
		
				<TableRow>
				    <TextView 
					android:layout_width="0dip"
					android:layout_weight="0.95"
					android:text="Total debit opening balance"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				    
				 <TextView 
					android:layout_width="0dip"
					android:layout_weight="0.05"
					android:text="\u20B9"
					android:textColor="#FFFFFF"
					android:textSize="22dp"
					android:layout_gravity="center_vertical"/>
				    
				<EditText 
				    android:id="@+id/etDrBal"
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:editable="false"
				    android:text="0.00" />
				</TableRow>
		
				<TableRow>
				    <TextView 
					android:layout_width="0dip"
					android:layout_weight="0.95"
					android:text="Total credit opening balance"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				    
				  <TextView 
					android:layout_width="0dip"
					android:layout_weight="0.05"
					android:text="\u20B9"
					android:textColor="#FFFFFF"
					android:textSize="22dp"
					android:layout_gravity="center_vertical"/>  
				  
				<EditText
				    android:id="@+id/etCrBal" 
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:editable="false"
				    android:text="0.00" />
				</TableRow>
		
				<TableRow>
				    <TextView 
					android:layout_width="0dip"
					android:layout_weight="0.95"
					android:text="Difference in opening balances"
					android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:layout_gravity="center_vertical"/>
				    
				 <TextView 
					android:layout_width="0dip"
					android:layout_weight="0.05"
					android:text="\u20B9"
					android:textColor="#FFFFFF"
					android:textSize="22dp"
					android:layout_gravity="center_vertical"/>
				    
				<EditText 
				    android:id="@+id/etDiffBal"
				    android:layout_width="0dip"
				    android:layout_weight="1.3"
				    android:editable="false" 
				    android:text="0.00" />
				    
				</TableRow>
		
			    </TableLayout>
			</ScrollView>

			    <LinearLayout
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:orientation="horizontal"
				android:layout_weight="20"
				android:layout_alignParentBottom="true" 
				android:background="@drawable/blackbutton">

				<Button
				    android:id="@+id/btnCreateAccSave"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_alignParentBottom="true"
				    android:layout_weight="0.96"
				    android:text="Save"
				    android:textSize="20dp" 
				    android:layout_gravity="center_vertical"/>
		
				<Button
				    android:id="@+id/btnCreateAccFinish"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_alignParentBottom="true"
				    android:layout_weight="0.90"
				    android:text="Finish"
				    android:textSize="20dp"
				    android:layout_gravity="center_vertical" />

			</LinearLayout>
			</LinearLayout>

.. image:: images/create_account.png
	   :name: ABT main page
	   :align: center

* Associated activity for creating account is ``createAccount.java``.

**File src/com/example/gkaakash/createAccount.java**

* The activity contains the essential and required import like

	.. code-block:: java

		import java.util.ArrayList;
		import java.util.List;
		import com.gkaakash.controller.Account;
		import com.gkaakash.controller.Group;
		import com.gkaakash.controller.Preferences;
		import com.gkaakash.controller.Startup;
		import android.app.Activity;
		import android.app.AlertDialog;
		import android.app.Dialog;
		import android.content.Context;
		import android.content.DialogInterface;
		import android.content.Intent;
		import android.os.Bundle;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.widget.AdapterView;
		import android.widget.AdapterView.OnItemSelectedListener;
		import android.widget.ArrayAdapter;
		import android.widget.Button;
		import android.widget.EditText;
		import android.widget.LinearLayout;
		import android.widget.Spinner;
		import android.widget.TextView;
		import android.widget.Toast;

* The activity intializes all the essential parameters and variables.
	
	.. code-block:: java
	
		static String accCodeCheckFlag;
		TextView tvaccCode, tvDbOpBal, tvOpBal,tvOpBalRupeeSymbol,tvAccName,tvAccCode;
		EditText etaccCode, etDtOpBal, etOpBal,etAccCode;
		Spinner sgrpName,sSearchBy,sAccName;
		Button btnCreateAccSave,btnCreateAccFinish,btnokdialog;
		private String newsubgrpname;
		static Integer client_id;
		AlertDialog dialog;
		final Context context = this;
		Dialog screenDialog;
		private Group group;
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
		private EditText etDrBal;
		private EditText etCrBal;
		private EditText etDiffbal;
		private Object drbal;
		private Object crbal;
		private Object diffbal;
		private Preferences preferencObj;
		static String finishflag;
		static final int ID_SCREENDIALOG = 1;
		private static String groupChar;
		private String account_code;
		protected static Boolean tabflag;
		String sub_grp_name;
		private String subgroup_exist;
		private String accountcode_exist;
		protected String accountname_exist;  

* OnCreate method calls all required methods at load time.

	.. code-block:: java

		public class createAccount<group> extends Activity{
			@Override
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				// Calling create_account.xml
				setContentView(R.layout.create_account);

				try {
					//for visibility of account tab layout
					tabflag=MainActivity.tabFlag;
					LinearLayout l1=(LinearLayout)findViewById(R.id.createacc_tab1);  
					LinearLayout l2=(LinearLayout)findViewById(R.id.createacc_tab2);
					if(tabflag){
						l1.setVisibility(LinearLayout.VISIBLE);
						l2.setVisibility(LinearLayout.VISIBLE);
					}else {
						l1.setVisibility(LinearLayout.INVISIBLE);
						l2.setVisibility(LinearLayout.INVISIBLE);
					}

					// create the object of Group class
					group = new Group();
					account = new Account();
					preferencObj= new Preferences();

					// getting client id 
					client_id = Startup.getClient_id();

					// Request a reference to the button from the activity by calling
					// “findViewById” and assign the retrieved button to an instance variable
					tvaccCode = (TextView) findViewById(R.id.tvAccCode);
					etaccCode = (EditText) findViewById(R.id.etAccCode);
					tvSubGrp = (TextView) findViewById(R.id.tvSubGrp);
					etSubGrp = (EditText) findViewById(R.id.etSubGrp);
					etAccName= (EditText) findViewById(R.id.etAccName);
					sgrpName = (Spinner) findViewById(R.id.sGroupNames);
					ssubGrpName = (Spinner) findViewById(R.id.sSubGrpNames);
					etDrBal = (EditText) findViewById(R.id.etDrBal);
					etCrBal = (EditText) findViewById(R.id.etCrBal);
					etDiffbal = (EditText) findViewById(R.id.etDiffBal);

					// call getPrefernece to get set preference related to account code flag   
					accCodeCheckFlag = preferencObj.getPreferences(new Object[]{"2"},client_id);

					// Setting visibility depending upon account code flag value
					if (accCodeCheckFlag.equals("automatic")) {
						etaccCode.setVisibility(EditText.GONE);
						tvaccCode.setVisibility(TextView.GONE);
					} else {
						etaccCode.setVisibility(EditText.VISIBLE);
						tvaccCode.setVisibility(TextView.VISIBLE);
					}

					getTotalBalances();

					getExistingGroupNames();
				} catch (Exception e) {
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Please try again")
					       .setCancelable(false)
					       .setPositiveButton("Ok",
						       new DialogInterface.OnClickListener() {
							   public void onClick(DialogInterface dialog, int id) {
							       
							   }
						       });
					       
					AlertDialog alert = builder.create();
					alert.show();
				}

				addListeneronButton();

				//creating interface to listen activity on Item 
				addListenerOnItem();

				addEditTextListner();
			} 

* The below method sets the credit opening,debit opening and difference in opening balance in their respective text fields.  

	.. code-block:: java

		private void getTotalBalances() {
			// TODO Auto-generated method stub
			drbal = account.getDrOpeningBalance(client_id);
			crbal = account.getCrOpeningBalance(client_id);
			diffbal =  account.getDiffInBalance(client_id);
	
			// setting text values in respective Edit Text fields
			etDrBal.setText(drbal.toString());
			etCrBal.setText(crbal.toString());
			etDiffbal.setText(String.format("%.2f",diffbal ));
		}

* The below method populates all the existing groupnames in the spinner from the database.

	.. code-block:: java

		void getExistingGroupNames(){
		    
			//call the getAllGroups method to get all groups
			Object[] groupnames = (Object[]) group.getAllGroups(client_id);
			// create new array list of type String to add gropunames
			List<String> groupnamelist = new ArrayList<String>();
			// create new array list of type Integer to add gropcode
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


* The below method attaches OnItemSelectedListner to the spinner. 

	.. code-block:: java

		// method addListnerOnItem() will implement OnItemSelectedListner
		void addListenerOnItem(){
		    //Attach a listener to the states Type Spinner to get dynamic list of subgroup name
		    sgrpName.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
			// Retrieving the selected name from the group name Spinner and
			// assigning it to a variable
			selGrpName = parent.getItemAtPosition(position).toString();
			tvOpBal = (TextView) findViewById(R.id.tvOpBal);
			tvOpBalRupeeSymbol = (TextView) findViewById(R.id.tvOpBalRupeeSymbol);
			etOpBal = (EditText) findViewById(R.id.etOpBal);

			// Comparing the variable value to group name and setting visibility
			if ("Current Asset".equals(selGrpName)
				| "Investment".equals(selGrpName)
				| "Loans(Asset)".equals(selGrpName)
				| "Fixed Assets".equals(selGrpName)
				| "Miscellaneous Expenses(Asset)".equals(selGrpName)) {
			    etOpBal.setVisibility(EditText.VISIBLE);
			    tvOpBalRupeeSymbol.setVisibility(TextView.VISIBLE);
			    tvOpBal.setVisibility(TextView.VISIBLE);
			    tvOpBal.setText("Debit opening balance");

			} else if ("Direct Income".equals(selGrpName)
				| "Direct Expense".equals(selGrpName)
				| "Indirect Income".equals(selGrpName)
				| "Indirect Expense".equals(selGrpName)) {
			    etOpBal.setVisibility(EditText.GONE);
			    tvOpBalRupeeSymbol.setVisibility(TextView.GONE);
			    tvOpBal.setVisibility(TextView.GONE);
			} else {
			    etOpBal.setVisibility(EditText.VISIBLE);
			    tvOpBal.setVisibility(TextView.VISIBLE);
			    tvOpBalRupeeSymbol.setVisibility(TextView.VISIBLE);
			    tvOpBal.setText("Credit opening balance");
			}

			if (selGrpName.equals("Capital"))
			{
			    groupChar = "CP";
			    }else if (selGrpName.equals("Corpus"))
			{
			    groupChar = "CR";

			}else if (selGrpName.equals("Current Asset"))
			{
			    groupChar = "CA";

			}else if (selGrpName.equals("Current Liability"))
			{
			    groupChar = "CL";
		    
			}else if (selGrpName.equals("Direct Income"))
			{
			    groupChar = "DI";
		    
			}else if (selGrpName.equals("Direct Expense"))
			{
			    groupChar = "DE";

			}else if (selGrpName.equals("Fixed Assets"))
			{
			    groupChar = "FA";

			}else if (selGrpName.equals("Indirect Income"))
			{
			    groupChar = "II";
		    
			}else if (selGrpName.equals("Indirect Expense"))
			{
			    groupChar = "IE";

			}else if (selGrpName.equals("Investment"))
			{
			    groupChar = "IV";

			}else if (selGrpName.equals("Loans(Asset)"))
			{
			    groupChar = "LA";

			}else if (selGrpName.equals("Reserves"))
			{
			    groupChar = "RS" ;

			}else if (selGrpName.equals("Miscellaneous Expenses(Asset)"))
			{
			    groupChar = "ME";

			}else
			{
			    groupChar = "LL";

			}
			// checks for the selected value of item is not null
			if(selGrpName!=null){
			    // create new array list of type String to add subgroup names
			    List<String> subgroupnamelist = new ArrayList<String>();
			    // input params contains group name
			    Object[] params = new Object[]{selGrpName};
			    // call com.gkaakash.controller.Group.getSubGroupsByGroupName pass params
			    Object[] subgroupnames = (Object[])group.getSubGroupsByGroupName(params,client_id);
			    // loop through subgroup names list 
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

		    //Attach a listener to the states Type Spinner to show or hide subgroup name text filed
		    ssubGrpName.setOnItemSelectedListener(new OnItemSelectedListener() {
		    
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
			// get the current value of subgroup spinner
			selSubGrpName = parent.getItemAtPosition(position).toString();

			if("Create New Sub-Group".equals(selSubGrpName))
			{
			    tvSubGrp.setVisibility(EditText.VISIBLE);
			    etSubGrp.setVisibility(TextView.VISIBLE);

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

* The below method adds on click listner to finish and create account button.

* Checks for blank field(edit text).

* Checks the length of account name entered in the edit text.

	.. code-block:: java

		 private void addListeneronButton() {
			// TODO Auto-generated method stub
			btnCreateAccSave = (Button) findViewById(R.id.btnCreateAccSave);
			btnCreateAccFinish = (Button) findViewById(R.id.btnCreateAccFinish);
			btnCreateAccFinish.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
		    
				// To pass on the activity to the next page
				Intent intent = new Intent(context, menu.class);
				startActivity(intent);
			    }

			});
			
			// setListner on Save Button
			btnCreateAccSave.setOnClickListener(new OnClickListener() {
			    @Override
			    public void onClick(View arg0) {
			
				// get text values from respective Edit Text 
				newsubgrpname = etSubGrp.getText().toString();
				accountname = etAccName.getText().toString();
				accountcode = etaccCode.getText().toString();
				openingbalance= etOpBal.getText().toString();
			    
				// check for blank fields
				if("Create New Sub-Group".equals(selSubGrpName)&&newsubgrpname.length()<1||("manually".equals(accCodeCheckFlag)&& accountcode.length()<1))
				{
				    alertBlankField();
				    
				}else if((accountname.length()<1)||(openingbalance.length()<1))
				{
			
				    alertBlankField();
				    
				}
				else if("Create New Sub-Group".equals(selSubGrpName)&&newsubgrpname.length()>=1)
				{
				    subgroup_exist = group.subgroupExists(new Object[]{newsubgrpname},client_id);
				    if (subgroup_exist.equals("1"))
				    {
					alertSubGroupExist();
				    }else if(accountname.length()>=1)
				    {
					    accountname_exist = account.checkAccountName(new Object[]{accountname,accCodeCheckFlag,groupChar},client_id);
					    if (accountname_exist.equals("exist"))
					    {
					        alertAccountExist();
					    }else if("manually".equals(accCodeCheckFlag)&&accountcode.length()>=1)
					    {
					        accountcode_exist = account.checkAccountCode(new Object[]{accountcode},client_id);
					        if (accountcode_exist.equals("1"))
					        {
					            alertAccountCodeExist();
					        
					        }else
					        {    
					            SaveAccount();
					        }// close else
					    }else
					    {    
					        SaveAccount();
					    }// close else
					    
				    }else
				    {    
					SaveAccount();
				    }// close else
				    
				}
				else
				{
				    if(accountname.length()>=1)
				    {
					    accountname_exist = account.checkAccountName(new Object[]{accountname,accCodeCheckFlag,groupChar},client_id);
					    if (accountname_exist.equals("exist"))
					    {
					        alertAccountExist();
					    }else if("manually".equals(accCodeCheckFlag)&&accountcode.length()>=1)
					    {
					        accountcode_exist = account.checkAccountCode(new Object[]{accountcode},client_id);
					        if (accountcode_exist.equals("1"))
					        {
					            alertAccountCodeExist();
					            
					        }else
					        {
					            SaveAccount();
					        }
					    }else
					    {
					        SaveAccount();
					    }
				    }

				}
			    }
			}); // close setOnClickListener
		    }



* The below method manages activites when focus changes from one edit text to another.

	.. code-block:: java

		private void addEditTextListner()
			{
			etAccName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
			    accountname = etAccName.getText().toString();
			    
			    if(hasFocus)
			    {
				etaccCode.setText("");
			    }
			    else{
				if(accountname.length()>=1)
				{
				    accountcode = account.checkAccountName(new Object[]{accountname,accCodeCheckFlag,groupChar},client_id);
				    if(accountcode.equals("exist"))
				    {
					alertAccountExist();
				    }else{
					etaccCode.setText(accountcode);
					}
				}
	
			   }
			}
			});// close addEditTextListner()


			// It will check for new subgroup name exist 
			etSubGrp.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			  public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				sub_grp_name = etSubGrp.getText().toString();
	
					if(sub_grp_name.length()>=1)
					{
					    subgroup_exist = group.subgroupExists(new Object[]{sub_grp_name},client_id);
					    if (subgroup_exist.equals("1"))
					    {
						alertSubGroupExist();
					    }
					}
				}

			});// close setOnFocusChangeListener

			// It will check for account code exist 
			etaccCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			    @Override
			      public void onFocusChange(View v, boolean hasFocus) {
				    // TODO Auto-generated method stub
				    account_code = etaccCode.getText().toString();
				    
					    if(account_code.length()>=1)
					    {
						accountcode_exist = account.checkAccountCode(new Object[]{account_code},client_id);
						if (accountcode_exist.equals("1"))
						{
						    alertAccountCodeExist();
						    etaccCode.setText(account_code);
						}
					    
				     }
				}

			    });// close setOnFocusChangeListener
			} // close addEditTextListner()

* The below method bulids an alert box with a message to fill the blank textfield.

	.. code-block:: java
	
		// method for validation of blank fields
		    public void alertBlankField()
		    {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Please fill textfield")
				.setCancelable(false)
				.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int id) {
					        
					    }
					});
			
			AlertDialog alert = builder.create();
			alert.show();
		    }

* The below method builds an alert box with a message saying duplicate account name.

	.. code-block:: java
	
		public void alertAccountExist()
		    {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Account "+accountname+" already exist")
				.setCancelable(false)
				.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int id) {
						 
						etAccName.setText("");
						etAccName.requestFocus();
					    }
					});
		
			AlertDialog alert = builder.create();
			alert.show();
		    }

* The below method takes all the data filled in the fields and save them in the database.

	.. code-block:: java

		 public void SaveAccount(){
			Object[] params = new Object[]{accCodeCheckFlag,selGrpName,selSubGrpName,newsubgrpname,accountname,accountcode,openingbalance}; 
			// call the setAccount method and pass the above parameters
			account.setAccount(params,client_id);
			getTotalBalances();
			getExistingGroupNames();
			//creating interface to listen activity on Item 
			addListenerOnItem();
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Account "+accountname+" have been saved successfully");
			AlertDialog alert = builder.create();
			alert.show();
			alert.setCancelable(true);
			alert.setCanceledOnTouchOutside(true);
		    
			etSubGrp.setText("");
			etAccName.setText("");
			etaccCode.setText("");
			etOpBal.setText("0.00");
		    }

Edit account
++++++++++++

**File  res/layout/edit_account.xml**

* This file includes layout design for search/edit account.
	.. code-block:: xml

		   <ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="fill_parent"
		    android:layout_height="fill_parent">

			<LinearLayout 
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:orientation="vertical" 
				android:background="#FFFFFF"
				android:padding="10dp">

			    <TextView
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:background="#CCCCB2"
				android:text="Account name" 
				android:textSize="17dp"
				android:textColor="#000000"/>

			    <LinearLayout 
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:orientation="horizontal"
				android:weightSum="100">
			    <TextView
				android:id="@+id/tvEditAccountName"
				android:layout_width="fill_parent"
				android:layout_height="40dp"
				android:textSize="17dp"
				android:textColor="#000000"
				android:layout_weight="60"
				android:clickable="true"
				android:gravity="center_vertical"/>

			    <EditText
				android:id="@+id/etEditAccountName"
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:layout_weight="60"
				android:inputType="textCapWords">
			    </EditText>
			    
			    <Button 
				android:id="@+id/bEditAccountName"
				android:layout_width="50dp"
				android:layout_height="30dp"
				android:background="@drawable/edit"
				android:layout_weight="40"
				android:layout_gravity="center_vertical"
				android:clickable="true"/>
			   </LinearLayout>
			   
			    <TextView
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:background="#CCCCB2"
				android:textSize="17dp"
				android:text="Opening balance" 
				android:textColor="#000000"/>
			    
			     <LinearLayout 
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:orientation="horizontal"
				android:weightSum="100">
			    <TextView
				android:id="@+id/tvEditOpBal"
				android:layout_width="fill_parent"
				android:layout_height="40dp"
				android:textSize="17dp"
				android:textColor="#000000"
				android:layout_weight="60"
				android:clickable="true"
				android:gravity="center_vertical"/>

			    <EditText
				android:id="@+id/etEditOpBal"
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:layout_weight="60"
				android:inputType="numberDecimal" >
			    </EditText>
			    
			    <Button 
				android:id="@+id/bEditOpBal"
				android:layout_width="50dp"
				android:layout_height="30dp"
				android:background="@drawable/edit"
				android:layout_weight="40"
				android:layout_gravity="center_vertical"
				android:clickable="true"/>
			   </LinearLayout>
			     
			     <TextView
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:background="#CCCCB2"
				android:textSize="17dp"
				android:text="Account code" 
				android:textColor="#000000"/>
			    
			     <TextView
				android:id="@+id/tvEditAccountCode"
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:textColor="#000000"
				android:textSize="17dp"/>
			    
			     
			     <TextView
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:background="#CCCCB2"
				android:text="Group name" 
				android:textSize="17dp"
				android:textColor="#000000"/>
			    
			     <TextView
				android:id="@+id/tvEditGroupName"
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:textColor="#000000"
				android:textSize="17dp"/>
			     
			     <TextView
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:background="#CCCCB2"
				android:text="Subgroup name" 
				android:textSize="17dp"
				android:textColor="#000000"/>
			    
			     <TextView
				android:id="@+id/tvEditSubgroupName"
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:textColor="#000000"
				android:textSize="17dp"/>
			    
			    

			</LinearLayout>
			</ScrollView>

.. image:: images/edit_account.png
	   :name: ABT main page
	   :align: center

* The associated activity to search/edit account is ``edit_account.java``.

**File src/com/example/gkaakash/edit_account.java**

* The activity contains the essential and required import like

	.. code-block:: java

		import java.util.ArrayList;
		import java.util.List;
		import com.gkaakash.controller.Account;
		import com.gkaakash.controller.Preferences;
		import com.gkaakash.controller.Startup;
		import android.R.color;
		import android.app.Activity;
		import android.app.AlertDialog;
		import android.app.Dialog;
		import android.content.DialogInterface;
		import android.content.DialogInterface.OnClickListener;
		import android.os.Bundle;
		import android.text.Editable;
		import android.text.TextWatcher;
		import android.view.LayoutInflater;
		import android.view.View;
		import android.view.ViewGroup;
		import android.view.WindowManager;
		import android.widget.AdapterView;
		import android.widget.AdapterView.OnItemClickListener;
		import android.widget.AdapterView.OnItemSelectedListener;
		import android.widget.ArrayAdapter;
		import android.widget.Button;
		import android.widget.EditText;
		import android.widget.ListView;
		import android.widget.Spinner;
		import android.widget.TextView;

* The activity intializes all the essential parameters and variables.
	
	.. code-block:: java

		static String accCodeCheckFlag;
		private ListView List;
		private EditText etSearch;
		Spinner sSearchAccountBy;
		private ArrayList<String> array_sort= new ArrayList<String>();
		int textlength=0;
		static Integer client_id;
		private Account account;
		private Object[] accountnames;
		private Object[] accountcodes;
		List getList;
		List accCode_list;
		AlertDialog dialog;
		static Object[] accountDetail;
		ArrayList accountDetailList;
		static int flag = 1;

* OnCreate method calls all required methods at load time. 

	.. code-block:: java
	
		public class edit_account extends Activity{
		
			public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.edit_acc_tab);
		
				account = new Account();
				client_id = Startup.getClient_id();
		
				List = (ListView) findViewById(R.id.ltAccname);
				List.setCacheColorHint(color.transparent);
				List.setTextFilterEnabled(true);
		
				etSearch = (EditText) findViewById(R.id.etSearch);
				sSearchAccountBy = (Spinner) findViewById(R.id.sSearchAccountBy);
		
				Preferences preferencObj = new Preferences();
			  	// call getPrefernece to get set preference related to account code flag 
				accCodeCheckFlag = preferencObj.getPreferences(new Object[]{"2"},client_id);

				//set visibility of spinner
				if (accCodeCheckFlag.equals("automatic")) {
					sSearchAccountBy.setVisibility(Spinner.GONE);
				} else {
					sSearchAccountBy.setVisibility(Spinner.VISIBLE);
				}
		
				//when spinner(search by account name or code) item selected, set the hint in search edittext 
				setOnItemSelectedListener();
		
				//get all acoount names in list view on load
				accountnames = (Object[])account.getAllAccountNames(client_id);
				getResultList(accountnames);
		
				//search account
				searchAccount();
		
				//edit or delete account
				editAccount();
		 	}
			
* The below method attaches listener to spinner.

* Get all account names from the database and populates account name or code listview according to the search type.

	.. code-block:: java

		private void setOnItemSelectedListener() {
			  sSearchAccountBy.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
						if(position == 0){
							etSearch.setHint("Search by name");
							flag = 1;
							//get all acoount names in list view
							accountnames = (Object[])account.getAllAccountNames(client_id);
							getResultList(accountnames);
						}
						if(position == 1){
							etSearch.setHint("Search by code");
							flag = 2;
							//get all acoount codes in list view
							accountcodes = (Object[])account.getAllAccountCodes(client_id);
							getResultList(accountcodes);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// do nothing!!
			
					}
				});
	
			}


* The below method allows to edit previously filled acocunt details.

* Get details from the data base and fill them in the dialog created which includes fields such as Account name,
  Opening balance, Account code, Group name , Sub groupname.

* Allow to delete account, if that particular account is not under any transaction or the account is not having opening balance.

	.. code-block:: java			

		private void editAccount() {
			List = (ListView) findViewById(R.id.ltAccname);
			List.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
			
				final CharSequence[] items = { "Edit account", "Delete account" };
				
				//creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(edit_account.this);
				//setting title
				builder.setTitle("Edit/Delete Account");
				
				//adding items
				builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface which, int pos) {
					//code for the actions to be performed on clicking popup item goes here ...
				    	switch (pos) {
					case 0:
					   {
			
				      	    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				      	    View layout = inflater.inflate(R.layout.edit_account, (ViewGroup) findViewById(R.id.layout_root));
				      	    AlertDialog.Builder builder = new AlertDialog.Builder(edit_account.this);
				      	    builder.setView(layout);
				      	    builder.setTitle("Edit account");
				      	    
				      	    //get account details
				      	    String queryParam = List.getItemAtPosition(position).toString();
				      	    if(accCodeCheckFlag.equals("automatic")){
				      	  	//search by account name
				      	    	Object[] params = new Object[]{2,queryParam};
				 		accountDetail = (Object[]) account.getAccount(params,client_id);
				      	    }
				      	    else if (sSearchAccountBy.getVisibility() == View.VISIBLE) {
				      	    	// Its visible
			      	    		if(sSearchAccountBy.getSelectedItemPosition()== 0){
			      	    			//search by account name
			      	    			Object[] params = new Object[]{2,queryParam};
			      	    			accountDetail = (Object[]) account.getAccount(params,client_id);
			      	    			
			      	    		}
			      	    		else if(sSearchAccountBy.getSelectedItemPosition()== 1){
			      	    			//search by account code
			      	    			Object[] params = new Object[]{1,queryParam};
			      	    			accountDetail = (Object[]) account.getAccount(params,client_id);
			      	    			 
			      	    		}  
				      	    }
				      	    
				      	    accountDetailList = new ArrayList();
					    for(Object ad : accountDetail)
					    {
						Object a = (Object) ad;
						accountDetailList.add(a.toString());
					  
					    }
					    
					    //account name
					    final Button bEditAccountName = (Button)layout.findViewById(R.id.bEditAccountName);
					    final TextView tvEditAccountName = (TextView) layout.findViewById(R.id.tvEditAccountName);
					    final String oldAccountName = accountDetailList.get(3).toString();
					    tvEditAccountName.setText(oldAccountName);
					    final EditText etEditAccountName = (EditText)layout.findViewById(R.id.etEditAccountName);
					    etEditAccountName.setVisibility(EditText.GONE);
					    tvEditAccountName.setOnClickListener(new View.OnClickListener() {
				
						@Override
						public void onClick(View v) {
							tvEditAccountName.setVisibility(TextView.GONE);
							etEditAccountName.setVisibility(EditText.VISIBLE);
							etEditAccountName.setText(oldAccountName);
							bEditAccountName.setVisibility(Button.GONE);
						}
						});
				      	         
				      	        //opening balance
				      	        final Button bEditOpBal = (Button)layout.findViewById(R.id.bEditOpBal);
				    	        final TextView tvEditOpBal = (TextView) layout.findViewById(R.id.tvEditOpBal);
				    	        final String oldOpBal = String.format("%.2f",
				    	        		Float.valueOf(accountDetailList.get(4).toString().trim()).floatValue());
				    	        tvEditOpBal.setText(oldOpBal);
				    	        final EditText etEditOpBal = (EditText)layout.findViewById(R.id.etEditOpBal);
				    	        etEditOpBal.setVisibility(EditText.GONE);
				    	        
				    	        
				    	        if("Direct Income".equals(accountDetailList.get(1).toString()) 
											|| "Direct Expense".equals(accountDetailList.get(1).toString()) 
											|| "Indirect Income".equals(accountDetailList.get(1).toString()) 
											||  "Indirect Expense".equals(accountDetailList.get(1).toString())){
				    	        	//opening balance is always 0 for above 4 groups, hence set clickable=false
										etEditOpBal.setClickable(false);
										bEditOpBal.setVisibility(Button.GONE);
									}
				    	        else{
				    	        	//set visibility of edittext for editing opening balance
				    	        	tvEditOpBal.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								tvEditOpBal.setVisibility(TextView.GONE);
								etEditOpBal.setVisibility(EditText.VISIBLE);
								etEditOpBal.setText(oldOpBal);
								bEditOpBal.setVisibility(Button.GONE);
							}
					      	        });   
				    	        }
				    	        
				    	        
					    //set account code
					    final TextView tvEditAccountCode = (TextView) layout.findViewById(R.id.tvEditAccountCode);
					    tvEditAccountCode.setText(accountDetailList.get(0).toString());
					    
					    //set group name
					    final TextView tvEditGroupName = (TextView) layout.findViewById(R.id.tvEditGroupName);
					    tvEditGroupName.setText(accountDetailList.get(1).toString());
					    
					    //set subgroup name
					    final TextView tvEditSubgroupName = (TextView) layout.findViewById(R.id.tvEditSubgroupName);
					    tvEditSubgroupName.setText(accountDetailList.get(2).toString());
					    
				      	            
					    builder.setPositiveButton("Save", new OnClickListener() {
				
						public void onClick(DialogInterface dialog, int which) {
	
	
	
							//get all values
							String newAccountName;
							if(etEditAccountName.getVisibility() == View.VISIBLE){
								newAccountName = etEditAccountName.getText().toString().trim();
							}
							else{
								newAccountName = tvEditAccountName.getText().toString();
							}
	
							String newOpBal;
							if(etEditOpBal.getVisibility() == View.VISIBLE){
								newOpBal = etEditOpBal.getText().toString();
								if(newOpBal.length() < 1){
									newOpBal = "";
								}else
								{
									newOpBal = String.format("%.2f",
			    	        				Float.valueOf(newOpBal.trim()).floatValue());
								}
							} 
							else{ 
								newOpBal = tvEditOpBal.getText().toString();
							}
							
							String groupname = tvEditGroupName.getText().toString();
							String subgroupname = tvEditSubgroupName.getText().toString();
							String accountcode = tvEditAccountCode.getText().toString();
	
							if((newAccountName.length()<1)&&("".equals(newOpBal)))
							{
								String message = "Please fill field";
								toastValidationMessage(message);
							   
							}
							else if("".equals(newOpBal))
							{
								String message = "Please fill amount field";
								toastValidationMessage(message);
							}
							else if((newAccountName.length()<1)){
								String message = "Please fill accountname field";
								toastValidationMessage(message);
							}
							if((newAccountName.length()>=1)&&(!"".equals(newOpBal)))
							{ 
								String accountcode_exist = account.checkAccountName(new Object[]{newAccountName,"",""},client_id);
							if (!newAccountName.equalsIgnoreCase(oldAccountName)&&accountcode_exist.equals("exist"))
							{
								String message = "Account '"+ newAccountName+"' already exist";
												toastValidationMessage(message);
		
							}else
							{
								Object[] params;
								if("Direct Income".equals(accountDetailList.get(1).toString()) 
										|| "Direct Expense".equals(accountDetailList.get(1).toString()) 
										|| "Indirect Income".equals(accountDetailList.get(1).toString()) 
										||  "Indirect Expense".equals(accountDetailList.get(1).toString())){
									params = new Object[]{newAccountName,accountcode,groupname};
	      	    			
								}
								else{
									params = new Object[]{newAccountName,accountcode,groupname,newOpBal};
								}
								account.editAccount(params,client_id);
		
								//set alert messages after account edit
								if(!newAccountName.equalsIgnoreCase(oldAccountName) &&
										!newOpBal.equals(oldOpBal)){
			
									String message = "Account name has been changed from '"+
											oldAccountName+"' to '"+ newAccountName+
											"' and opening balance has been changed from '"+ 
											oldOpBal + "' to '"+ newOpBal+"'";
									toastValidationMessage(message);
								}
								else if(!newAccountName.equalsIgnoreCase(oldAccountName)){
									String message = "Account name has been changed from '"+
											oldAccountName+"' to '"+ newAccountName+"'";
									toastValidationMessage(message);
								}
								else if(!newOpBal.equals(oldOpBal)){
									String message = "Opening balance has been changed from '"+
											oldOpBal+"' to '"+ newOpBal+"'";
									toastValidationMessage(message);
								}
								else{
									String message = "No changes made";
									toastValidationMessage(message);
								}
		
								setaccountlist();
			
								}
							}
	
						}//end of onclick
					    });// end of onclickListener
					     
					    builder.setNegativeButton("Cancel", new OnClickListener() {
				
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
	
						}
					        });
				      	            
					        dialog=builder.create();
					        ((Dialog) dialog).show();
					        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
					        //customizing the width and location of the dialog on screen 
					        lp.copyFrom(dialog.getWindow().getAttributes());
					        lp.height = 600;
					        lp.width = 400;
					        dialog.getWindow().setAttributes(lp);						
				            }
					    break;
					case 1:
					      {
					    	  if(accCodeCheckFlag.equals("automatic")){
					    		  flag = 1;
					    	  }
					    	  
					    	  Object[] params = new Object[]{List.getItemAtPosition(position).toString(),flag};
					    	  System.out.println(List.getItemAtPosition(position));
						  String accountDeleteValue =  (String) account.deleteAccount(params,client_id);
						  System.out.println("value"+accountDeleteValue);
						  if("account deleted".equals(accountDeleteValue)){
							  String message = "Account '"+List.getItemAtPosition(position).toString()+"' has been deleted successfully";
							  toastValidationMessage(message);
							  setaccountlist();
						  }
						  else if("has both opening balance and trasaction".equals(accountDeleteValue)){
							  String message = "Account '"+List.getItemAtPosition(position).toString()
									  			+"' has both opening balance and transaction, it can not be deleted";
							  toastValidationMessage(message); 
						  }
						  else if("has opening balance".equals(accountDeleteValue)){
							  String message = "Account '"+List.getItemAtPosition(position).toString()
		      					  			+"' has opening balance, it can not be deleted";
							  toastValidationMessage(message);
						  }
						  else if("has transaction".equals(accountDeleteValue)){
							  String message = "Account '"+List.getItemAtPosition(position).toString()
								  					+"' has transaction, it can not be deleted";
				  			  toastValidationMessage(message);
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

* The below method adds text watcher listener to edit text.

* It helps to search account names,that is typed inside the edit text.

	.. code-block:: java
		
		//search account
		private void searchAccount() {
			//attaching listener to textView
			etSearch.addTextChangedListener(new TextWatcher()
			{
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{
					// Abstract Method of TextWatcher Interface.
				}
				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
					//for loop for search
					textlength = etSearch.getText().length();
					array_sort.clear();
				       
					for (Object acc : getList)
					{
					    if (textlength <= acc.toString().length())
					    {
						if(etSearch.getText().toString().equalsIgnoreCase((String) ((String) acc).subSequence(0,textlength)))
						{
						    array_sort.add((String)acc);
						}
					    }
					}
				       
					List.setAdapter(new ArrayAdapter<String>(edit_account.this,android.R.layout.simple_list_item_1, array_sort));
				}
				@Override
				public void afterTextChanged(Editable arg0) {
				    // Abstract Method of ArrayAdapter Interface
				}
			});

		}//end of search account by name
							   

* The below method gets the final list of account names or account and populates the account name or code listview.

	.. code-block:: java

		//get all acoount names or account codes depending upon parameter
		void getResultList(Object[] param){
			getList = new ArrayList();
			for(Object an : param)
			{   
			    getList.add(an); //acc_names
			}   
			List.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getList));
		}

* The below method updates listview with updated account names or code. 

	.. code-block:: java

		void setaccountlist(){
		    	if(accCodeCheckFlag.equals("automatic")){
				//get all updated account names in list view
				accountnames = (Object[])account.getAllAccountNames(client_id);
				getResultList(accountnames);
		  	}
		 	else if (sSearchAccountBy.getVisibility() == View.VISIBLE) {
	  	    	    	// Its visible
	  	    		if(sSearchAccountBy.getSelectedItemPosition()== 0){
	  	    			//for search by account name, get all updated acoount names in list view
	  	    			accountnames = (Object[])account.getAllAccountNames(client_id);
					getResultList(accountnames);
	  	    			
	  	    		}
	  	    		else if(sSearchAccountBy.getSelectedItemPosition()== 1){
	  	    			//search by account code
	  	    			accountnames = (Object[])account.getAllAccountCodes(client_id);
					getResultList(accountnames);
	  	    			 
	  	    		}  
		 	}
	        }
    
* Resume method resumes the activity from where it was stoped.

* For example: tab change activity,resume method saves the state and reloads the method when the tab is changed.

	.. code-block:: java

		/*
		* (non-Javadoc)
		* @see android.app.Activity#onResume()
		*  to execute code when tab is changed because 
		*  when the tab is clicked onResume is called for that activity
		*/
		@Override
		protected void onResume() {
			super.onResume();
			//get all acoount names in list view on load
			accountnames = (Object[])account.getAllAccountNames(client_id);
			getResultList(accountnames);
			setaccountlist();
		}


