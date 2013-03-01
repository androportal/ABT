Bank Reconciliation
===================

* This chapter explains how to generate Bank Reconciliation statement.

* On clicking ``Mater menu`` >> ``Bank Reconciliation``, user gets a alert dialog which asks for an account name, period and other options required to display bank reconciliation statement. 

* Click on ``View`` button will take user to the next page which contains Bank Reconciliation statement with ``cleared/uncleared`` transactions  in a ``tabular format``.

* Layout is included in ``res/layout/bank_recon_index.xml``.

**File res/layout/bank_recon_table.xml**

	.. code-block:: xml
	
		<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
		    android:id="@+id/Scroll"
		    xmlns:tools="http://schemas.android.com/tools"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    android:background="@drawable/dark_gray_background">
		   <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			    android:layout_width="fill_parent"
			    android:layout_height="wrap_content"
			    android:orientation="vertical"
			    android:id="@+id/layout_root"
			    android:layout_weight="50"
			    android:paddingTop="10dp">
		
		       <TextView
			   android:id="@+id/tvReconAccName"
			   android:layout_width="wrap_content"
			   android:layout_height="match_parent"
			   android:layout_gravity="center"
			   android:gravity="center_horizontal"
			   android:textColor="#FFFFFF"
			   android:textSize="18dp" />
		      
			<LinearLayout
			    xmlns:android="http://schemas.android.com/apk/res/android"
			    android:id="@+id/layout_root"
			    android:layout_width="fill_parent"
			    android:layout_height="wrap_content"
			    android:orientation="horizontal" >
			   
			 <TextView
			     android:id="@+id/tvfinancialFromDate"
			     android:layout_width="wrap_content"
			     android:layout_height="wrap_content"
			     android:layout_gravity="start"
			     android:layout_weight="50"
			     android:gravity="center_horizontal"
			     android:textColor="#FFFFFF"
			     android:textSize="18dp" />
			    
			  <TextView
			      android:id="@+id/tvfinancialToDate"
			      android:layout_width="wrap_content"
			      android:layout_height="wrap_content"
			      android:layout_gravity="right"
			      android:layout_weight="50"
			      android:gravity="right"
			      android:paddingRight="10dp"
			      android:textColor="#FFFFFF"
			      android:textSize="18dp" />
			  
			</LinearLayout>
		  
			 <LinearLayout
			    xmlns:android="http://schemas.android.com/apk/res/android"
			    android:id="@+id/layout_root"
			    android:layout_width="fill_parent"
			    android:layout_height="fill_parent"
			    android:padding="10dp" >
			     <HorizontalScrollView 
			      	android:layout_height="fill_parent"
			  		android:layout_width="fill_parent"
			    	android:fillViewport="true">
			      <ScrollView
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:fillViewport="true"
				    android:scrollbarFadeDuration="0"
				    android:scrollbarAlwaysDrawVerticalTrack="true" 
				    android:scrollbarAlwaysDrawHorizontalTrack="true">

			    	<LinearLayout
		       				android:layout_width="wrap_content"
			    		android:layout_height="0dp"
			    		android:orientation="vertical">
			    		
					    <LinearLayout
						android:layout_width="wrap_content"
						    android:layout_height="wrap_content"
						    android:orientation="vertical">

						<TableLayout
							    android:layout_width="wrap_content"
							    android:layout_height="wrap_content"
							    android:stretchColumns="0,1,2,3,4,5,6,7"
							    android:id="@+id/maintable"
							    android:background="#696565"
							    android:layout_weight="1" />

						    <!-- just add space line -->
						    	<Space
						    	    android:layout_width="10dip"
								android:layout_height="10dip"/>

						<TableLayout
							    android:id="@+id/statementtable"
							    android:layout_width="wrap_content"
							    android:layout_height="wrap_content"
							    android:stretchColumns="*"
							    android:background="#696565"
							    android:layout_weight="1" />
			       
					</LinearLayout>
				
				</LinearLayout>
			    </ScrollView>
			    </HorizontalScrollView>
			 </LinearLayout>  
		    </LinearLayout>

		</ScrollView>


.. image:: images/bank_reconciliation.png
	   :name: ABT main page
	   :align: center

* and the associated java code is given below,

**File src/com/example/gkaakash/bankReconciliation.java**

* Get values of all the required ``static variables`` from masterMenu.java to the activity ``bankReconciliation.java``.

* Send a ``request`` to the back-end server through controller functions and get the ``response``.

* Response will be the list containing Bank Reconciliation statement with cleared/uncleared transactions.

* Transaction list is displayed in a tabular formate using TableLayout.

* Set selected account name, period, transactions, reconcilation statement and cleared transaction dates programatically.

* The activity contains the essential and required import like

	.. code-block:: java
	
		package com.example.gkaakash;

		import java.math.RoundingMode;
		import java.text.BreakIterator;
		import java.text.DecimalFormat;
		import java.text.SimpleDateFormat;
		import java.util.ArrayList;
		import java.util.Calendar;
		import java.util.Date;
		import java.util.List;
		import com.gkaakash.controller.Report;
		import com.gkaakash.controller.Startup;
		import android.R.integer;
		import android.app.ActionBar.LayoutParams;
		import android.app.Activity;
		import android.app.AlertDialog;
		import android.app.DatePickerDialog;
		import android.app.Dialog;
		import android.content.DialogInterface;
		import android.graphics.Color;
		import android.os.Bundle;
		import android.text.SpannableString;
		import android.text.InputFilter.LengthFilter;
		import android.view.Gravity;
		import android.view.LayoutInflater;
		import android.view.View;
		import android.view.ViewGroup;
		import android.view.Window;
		import android.view.WindowManager;
		import android.view.View.OnClickListener;
		import android.widget.Button;
		import android.widget.CheckBox;
		import android.widget.DatePicker;
		import android.widget.EditText;
		import android.widget.LinearLayout;
		import android.widget.ScrollView;
		import android.widget.TableLayout;
		import android.widget.TableRow;
		import android.widget.TextView;
		import android.widget.Toast;
	
* The activity intializes all the essential parameters and variables.

* OnCreate method calls all required methods at load time.

	.. code-block:: java
	
		public class bankReconciliation extends Activity{
			TableLayout bankRecontable, statementtable;
			TableRow tr, tr1;
			LinearLayout Ll, Ll1;
			TextView label,label1,tvaccontName,tvfinancialFromDate,tvfinancialToDate;
			ArrayList<ArrayList> bankReconGrid, for_naration, statementGrid;
			static Object[] bankReconResult;
			static Integer client_id;
			private Report report;
			ArrayList<String> bankReconResultList, with_narration;
			private ArrayList accountlist, for_clearenceDate; 
			static boolean narration_flag;
			static boolean cleared_tran_flag;
			static String[] ColumnNameList;
			static int day, month, year, sel_day, sel_month, sel_year;
			static final int VOUCHER_DATE_DIALOG_ID = 1;
			TextView tvdate;
			static String date, financialFromDate, financialToDate, accountName, fromDate, toDate;
			DecimalFormat mFormat;
			static int rowid;
			boolean nara_flag=false;
			AlertDialog dialog;
			LinearLayout.LayoutParams params, params1;
			String clearence_date;
			String retrived_date;
			String Cdate;
			String result;
			String[] dateParts;
			Boolean updown=false;
			 
		     
		    	public void onCreate(Bundle savedInstanceState) {
			    	super.onCreate(savedInstanceState);
			    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			    	setContentView(R.layout.bank_recon_table);
			       
			    	report = new Report();
			    	client_id= Startup.getClient_id();
			       
			    	//customizing title bar
			    	getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
			       
			    	//two digit date format for dd and mm
			      	mFormat= new DecimalFormat("00");
					mFormat.setRoundingMode(RoundingMode.DOWN);
			      
					try {
						financialFromDate =Startup.getfinancialFromDate();
						String dateParts[] = financialFromDate.split("-");
					   	String fromday  = dateParts[0];
					   	String frommonth = dateParts[1];
					   	String fromyear = dateParts[2];
				   	
					   	year = Integer.parseInt(fromyear);
					   	month = Integer.parseInt(frommonth);
					   	day = Integer.parseInt(fromday);
				   
					   	financialToDate=Startup.getFinancialToDate();
					   	accountName = menu.selectedAccount;
					   	fromDate = menu.givenfromDateString;
					   	toDate = menu.givenToDateString;
					   	cleared_tran_flag = menu.cleared_tran_flag; 
					   	narration_flag = menu.narration_flag;
			       
					   	tvaccontName = (TextView) findViewById( R.id.tvReconAccName );
					   	tvfinancialFromDate = (TextView) findViewById( R.id.tvfinancialFromDate );
					   	tvfinancialToDate = (TextView) findViewById( R.id.tvfinancialToDate );
				  
				    
					   	tvaccontName.setText("Account name: "+accountName);
					   	tvfinancialToDate.setText("Period : "+fromDate+" to "+toDate);
				      
					   	Object[] params = new Object[]{accountName,financialFromDate,fromDate,toDate,"No Project"};
					   	Object[] flag = new Object[]{cleared_tran_flag};
				   
					   	setTableAndStatement(params,flag);
				    
					   	setbankRecon();
				   
					   	final Button btnSaveRecon = (Button)findViewById(R.id.btnSaveRecon);
					   	btnSaveRecon.setVisibility(Button.VISIBLE);
					   	final Button btnScrollDown = (Button)findViewById(R.id.btnScrollDown);
				   		btnScrollDown.setOnClickListener(new OnClickListener() {
			
				   		@Override
				   		public void onClick(View v) {
				   			if(updown==false){
				   				ScrollView sv = (ScrollView)findViewById(R.id.Scroll);
								sv.fullScroll(ScrollView.FOCUS_DOWN); 
								btnScrollDown.setBackgroundResource(R.drawable.up);
								updown=true;
							}else {
								ScrollView sv = (ScrollView)findViewById(R.id.Scroll);
								sv.fullScroll(ScrollView.FOCUS_UP); 
								btnScrollDown.setBackgroundResource(R.drawable.down);
								updown=false;
							}
				   		}
				   	});
				    
					} catch (Exception e) {
						toastValidationMessage("Please try again");
					}
		    	}
		

* get all the cleared and uncleared transactions from database for the given account, from date and to date along with bank reconciliation statement.

* add header and transaction rows in the first table and bank reconciliation statemnt in the second table.

	.. code-block:: java

		private void setTableAndStatement(Object[] params, Object[] flag) {
			bankReconResult = (Object[]) report.getLedgerForBankRecon(params,flag, client_id);

			bankReconGrid = new ArrayList<ArrayList>();
			for_naration = new ArrayList<ArrayList>();
			for_clearenceDate = new ArrayList();
			
			/* 
			  * bankReconResult.length-6 is for getting only uncleared transactions with
			  *  total debit and total credit and set in table  
			  * because last 6 rows are bank recon statement, 
			  * we are not adding these rows in table for now 
			 */
			 for(int k = 0; k < (bankReconResult.length-6); k++)
			 {
			     Object[] t = (Object[]) bankReconResult[k];
			     bankReconResultList = new ArrayList<String>();
			     with_narration = new ArrayList<String>();
			     for(int i=0;i<(t.length);i++)
			     {
			     	if(i == 6){//narration
			     		if(narration_flag){
				 		//bankReconResultList.add((String) t[i].toString());
			     			with_narration.add((String) t[i].toString());
						nara_flag = true;
				 	}
			     	}
			     	else{
			     		//bankReconResultList.add((String) t[i].toString());
			     		bankReconResultList.add((String) t[i].toString());
				    	with_narration.add((String) t[i].toString());
			     	}
			     	
			     }
			      
			     bankReconGrid.add(bankReconResultList);
			     for_naration.add(with_narration);
			 } 
			 if(cleared_tran_flag==true){
			      for(int i=0;i<bankReconGrid.size();i++){
				      Cdate = bankReconGrid.get(i).get(6).toString();
				      for_clearenceDate.add(Cdate);
			      }
			 }

			 bankRecontable = (TableLayout)findViewById(R.id.maintable);
			 addTable(bankRecontable,"");
			 
			 /*
			  * let add bank reconciliation statement
			  */
			 statementGrid = new ArrayList<ArrayList>();
			 for(int k = (bankReconResult.length-6); k < (bankReconResult.length); k++)
			 {
			     Object[] t = (Object[]) bankReconResult[k];
			     bankReconResultList = new ArrayList<String>();
			     for(int i=0;i<(t.length);i++)
			     {
			    	 bankReconResultList.add((String) t[i].toString());
			     	
			     }
			     statementGrid.add(bankReconResultList);
			 }
			 
			 statementtable = (TableLayout)findViewById(R.id.statementtable);
			 statementtable.removeAllViews();
			 addTable(statementtable, "statement");
		}
		
		
* Below method allows to clear transactions and unclear the cleared transactions

	.. code-block:: java
	
		private void setbankRecon() {
			Button btnSetBankRecon = (Button)findViewById(R.id.btnSaveRecon);
			btnSetBankRecon.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				int rowcount = bankRecontable.getChildCount();
				ArrayList<ArrayList> listOfRowsTobeClear= new ArrayList<ArrayList>();
				boolean flag = false;
				for(int i=0;i<rowcount-1;i++){
					if(i!=0){
						View row = bankRecontable.getChildAt(i);
						LinearLayout l5 = (LinearLayout)((ViewGroup) row).getChildAt(6);
						TextView tvclearanceDate = (TextView) l5.getChildAt(0); //clearance date
			
						ArrayList<String> rowArray = new ArrayList<String>();
			
						/*
						 * get the clear tranction rows from table
						 * and pass these rows to backend (set bank reconciliation)
						 */
						if(!tvclearanceDate.getText().toString().equals("")){
							flag = true;
							rowArray.clear();
				
							LinearLayout lv = (LinearLayout)((ViewGroup) row).getChildAt(0);
							TextView tvVoucherCode = (TextView) lv.getChildAt(0); //voucher code
				
							LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(1);
							TextView tvrefdate = (TextView) l.getChildAt(0); //ref date
				
							LinearLayout l1 = (LinearLayout)((ViewGroup) row).getChildAt(2);
							TextView tvparticular = (TextView) l1.getChildAt(0); //particular
				
							LinearLayout lr = (LinearLayout)((ViewGroup) row).getChildAt(3);
							TextView tvrefno = (TextView) lr.getChildAt(0); //ref no
				
				
							LinearLayout l3 = (LinearLayout)((ViewGroup) row).getChildAt(4);
							TextView tvdramount = (TextView) l3.getChildAt(0); //dr amount
				
							LinearLayout l4 = (LinearLayout)((ViewGroup) row).getChildAt(5);
							TextView tvcramount = (TextView) l4.getChildAt(0); //cr amount
				
							EditText etmemo= (EditText)((ViewGroup) row).getChildAt(7); //memo
				
							rowArray.add(tvVoucherCode.getText().toString());
							rowArray.add(tvrefdate.getText().toString());
							rowArray.add(tvparticular.getText().toString());
				
							//check for the dr and cr amount
							if(tvdramount.getText().toString().length() > 0){
								rowArray.add(tvdramount.getText().toString());
								rowArray.add(tvcramount.getText().toString());
							}
							else{
								rowArray.add(tvcramount.getText().toString());
								rowArray.add(tvdramount.getText().toString());
							}
				
							rowArray.add(tvclearanceDate.getText().toString());
							rowArray.add(etmemo.getText().toString());
				
							/*
							System.out.println("i am row "+ i+ tvrefdate.getText().toString()
									+tvparticular.getText().toString()
									+tvdramount.getText().toString()
									+tvcramount.getText().toString()
									+tvclearanceDate.getText().toString()
									+etmemo.getText().toString()); */
						
						} 
						/*
						 * unclear the cleared transaction(delete cleared rows)
						 */
						if(tvclearanceDate.getText().toString().equals(" ")){
							flag = false;
							rowArray.clear();
				
							LinearLayout l1 = (LinearLayout)((ViewGroup) row).getChildAt(2);
							TextView tvparticular = (TextView) l1.getChildAt(0); //particular
				
							LinearLayout lv = (LinearLayout)((ViewGroup) row).getChildAt(0);
							TextView tvVoucherCode = (TextView) lv.getChildAt(0);//voucher code
				
							rowArray.add(tvparticular.getText().toString());
							rowArray.add(tvVoucherCode.getText().toString());
							rowArray.add(toDate);
				
							if(rowArray.size() >0){
								report.deleteClearedRecon(rowArray, client_id);
							}
				
						}
						/*
						 * add all cleared rows to the new Array
						 * flag=true for set bank recon
						 * flag=false for unclear transaction
						 */
						if(rowArray.size()!=0 && flag == true){ 
							listOfRowsTobeClear.add(rowArray);
						} 
			
					}//end of if
				}//end of for
	
				/*
				 * pass array of rows to be cleared to the controller 
				 */
				if(listOfRowsTobeClear.size() >0){
					report.setBankReconciliation(listOfRowsTobeClear, client_id);
				}
	
	
				bankRecontable.removeAllViews();
	
				/*
				 * update table
				 */
				Object[] params = new Object[]{accountName,financialFromDate,fromDate,toDate,"No Project"};
				Object[] clear_flag = new Object[]{cleared_tran_flag};
				setTableAndStatement(params,clear_flag);
	
				toastValidationMessage("Changes saved successfully");
			}//end of onclick
			}); 
		}//end of function setbankrecon
		

* add header and transaction rows in the first table and bank reconciliation statemnt in the second table.

	.. code-block:: java
	
		private void addTable(TableLayout tableID, String flag) {  
			//System.out.println("reco grid"+bankReconGrid);
			//for adding only header and transaction rows
			if(!flag.equalsIgnoreCase("statement")){
				if(bankReconGrid.size() > 1){
					addHeader();
				}
		
				/** Create a TableRow dynamically **/
				for(int i=0;i<bankReconGrid.size();i++){
				    	ArrayList<String> columnValue = new ArrayList<String>();
			    
					tr = new TableRow(this);
					
					//for last row(total debit and total credit)
					if(i==bankReconGrid.size()-1){
						if(bankReconGrid.size() > 1){
							columnValue.addAll(bankReconGrid.get(i));
							for(int k=0;k<columnValue.size();k++){
							    	/** Creating a TextView to add to the row **/
								addRow(columnValue.get(k),k,k,0);
							    	params.height = 45;
		
							    	//hide vouchercode column
								if(k==0){
									Ll.setVisibility(LinearLayout.GONE);//voucher code
								}

								if(k == 4 || k == 5){// dr and cr amount
									label.setGravity(Gravity.CENTER|Gravity.RIGHT);
								} else {
									label.setGravity(Gravity.CENTER);
								}  
							}	
							//add empty field for narration
							if(narration_flag==true){
								addRow("",i,8,0);   //naration
							}

							// Add the TableRow to the TableLayout
							bankRecontable.addView(tr, new TableLayout.LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.MATCH_PARENT));
						}	
					}
					else{//for only uncleared transactions
						columnValue.addAll(bankReconGrid.get(i));
						/*
						* columnValue.size()-2 to ignore cleared date and memo
						* we are setting it manually after this loop
						*/
						for(int j=0;j<columnValue.size()-2;j++){
							if(j!=0){// 0 is voucher code
							    	addRow(columnValue.get(j),i,j,1);   
								label.setBackgroundColor(Color.BLACK);
								if(j == 4 || j == 5){// dr and cr amount
								    if(columnValue.get(j).trim().length() > 0){
									label.setText(columnValue.get(j)); 
								    } 
								    label.setGravity(Gravity.CENTER|Gravity.RIGHT);
								}
								else
								{
								    label.setGravity(Gravity.CENTER);
								}
							}
							else{
								addRow(columnValue.get(j),i,j,1);
								Ll.setVisibility(LinearLayout.GONE);//voucher code
							}

						}

						if(!cleared_tran_flag){
							if(narration_flag==false){
							    	addRow("",i,6,1);  //date
							    	//memo
							    	EditText e = new EditText(this);
							    	tr.addView(e);
							}
							else {
							    	addRow("",i,6,1);  //date
							    	//memo
							    	EditText e = new EditText(this);
								tr.addView(e);
							    	addRow(for_naration.get(i).get(6).toString(),i,8,1);   //naration
							}
						}
						else{ 
							/*
							 * if transaction is clear set clearance date and memo into the textview
							 */
							addRow(bankReconGrid.get(i).get(6).toString(),i,6,1);  //date
							//memo
							EditText e = new EditText(this);
							e.setText(bankReconGrid.get(i).get(7).toString());
							tr.addView(e);

							if(narration_flag==true){
								addRow(for_naration.get(i).get(6).toString(),i,8,1);   //naration
							}
						}

						// Add the TableRow to the TableLayout
						tableID.addView(tr, new TableLayout.LayoutParams(
						    LayoutParams.FILL_PARENT,
						    LayoutParams.MATCH_PARENT));
			    		}
				}
			}
			else{ //add reconciliation statement
				for(int i=0;i<statementGrid.size();i++){
					ArrayList<String> columnValue = new ArrayList<String>();
					columnValue.clear();
					columnValue.addAll(statementGrid.get(i));
					tr1 = new TableRow(this);
	            
	            			for(int j=0;j<columnValue.size();j++){
	            				if(i==0){//this is a header row
	            					if(j==1 || j==5){//statement and amount column
	            						if(j==5){
	            							final SpannableString rsSymbol = new SpannableString(bankReconciliation.this.getText(R.string.Rs));
	            							addStatementRow(rsSymbol+" "+columnValue.get(j));
	            						}
					    			else{
					    				addStatementRow(columnValue.get(j));
					    			}
			            	
			            				params1.height = LayoutParams.WRAP_CONTENT;
			            				label1.setBackgroundColor(Color.parseColor("#348017"));
		            				}
	            				}
					    	else{//remaining rows
					    		if(j==1 || j==5){//statement and amount column
					    			addStatementRow(columnValue.get(j)); 
							    	params1.height = LayoutParams.WRAP_CONTENT;
							    	if(j==5){//set right gravity for amount
							    		label1.setText(columnValue.get(j)); 
									label1.setGravity(Gravity.RIGHT);
							    	}
						    	}
					    	}
	            			} 
					// Add the TableRow to the TableLayout
					tableID.addView(tr1, new TableLayout.LayoutParams(
					    LayoutParams.FILL_PARENT,
					    LayoutParams.MATCH_PARENT));
				}	 
			}
    		}
			

* Below method is used to add header to the table.
	
	.. code-block:: java
	
		void addHeader(){
			//For adding rupee symbol
			final SpannableString rsSymbol = new SpannableString(bankReconciliation.this.getText(R.string.Rs));
			/** Create a TableRow dynamically **/
			tr = new TableRow(this);
			if(narration_flag){
				ColumnNameList = new String[] {"voucher code","Date","Particulars","Reference no.",
						rsSymbol+" Debit",rsSymbol+" Credit","Clearance date","Memo","Narration"};
			}
			else{
				ColumnNameList = new String[] {"voucher code","Date","Particulars","Reference no.",
						rsSymbol+" Debit",rsSymbol+" Credit","Clearance date","Memo"};
			}
			for(int k=0;k<ColumnNameList.length;k++){
			    /** Creating a TextView to add to the row **/
				addRow(ColumnNameList[k],k,k,0);
				label.setBackgroundColor(Color.parseColor("#348017"));
				label.setGravity(Gravity.CENTER);
				params.height = LayoutParams.WRAP_CONTENT;
				//hide vouchercode column
				if(k==0){
					Ll.setVisibility(LinearLayout.GONE);//voucher code
				}
	
			}

			// Add the TableRow to the TableLayout
			bankRecontable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.MATCH_PARENT));

		}

* Below method is used to set the table row.

* Make the column ``clearance date`` editable, so that to set the clearance date in the textview.

* It also includes code for adding few options on clicking clearance date column
		void addRow(String param, final int i, final int j, final int flag){
			label = new TextView(this);
			label.setText(param);
			label.setTextSize(18);
			label.setGravity(Gravity.CENTER);
			label.setTextColor(Color.WHITE);
			//label.setBackgroundColor(Color.);
			label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
			label.setBackgroundColor(Color.BLACK);
			label.setPadding(2, 2, 2, 2);
			label.setClickable(false);
		
			/*
			 * 6 is clerance column and flag 1 is for making textview clickable
			 */
			if(j == 6 && flag == 1){  
				label.setClickable(true); 
				label.setOnClickListener(new OnClickListener() {
				
						@Override
						public void onClick(View v) {
							/* set the rowid as index of parent row of the textview
							 */
							rowid = i; 
							/*
							 * if narration flag is false, get the column 6th text from textview(clearance date)
							 * and store in variable 'clearence_date'
							 */
				    if(nara_flag==false){
				    	int rowcount = bankRecontable.getChildCount();    
				        for(int k=0;k<rowcount;k++){
				        	View row = bankRecontable.getChildAt(rowid+1);
				        	LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(6);
				        	tvdate = (TextView) l.getChildAt(0);
				        	clearence_date = (String) tvdate.getText();
				        }
				    }
				    /*
							 * if narration flag is true but 'cleared_tran_flag' is true,
							 * get the column 6th text from textview(clearance date)
							 * and store in variable 'clearence_date'
							 * and when 'cleared_tran_flag' is false, set clearance date text as ""
							 */
				    else { 
				    	if(cleared_tran_flag){
				    		int rowcount = bankRecontable.getChildCount();    
				            for(int k=0;k<rowcount;k++){
				            	View row = bankRecontable.getChildAt(rowid+1);
				            	LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(6);
				            	tvdate = (TextView) l.getChildAt(0);
				            	clearence_date = (String) tvdate.getText();
				            }
				    	}  
				    	else{
				    		clearence_date ="";
				    	}
				    }
				    
				    
				    /*
				     * now, if clearance date has some date, user can edit, clear or set previous date
				     * in the clearance date textview
				     */
				    if(!clearence_date.equals("") && !clearence_date.equals(" ")){
				        final CharSequence[] items = { "Edit date", "Clear date","Set previous date"};
				        //creating a dialog box for popup
				        AlertDialog.Builder builder = new AlertDialog.Builder(bankReconciliation.this);
				        //setting title
				        builder.setTitle("Edit/Clear date");
				        //adding items
				        builder.setItems(items, new DialogInterface.OnClickListener() {

				            @Override
				            public void onClick(DialogInterface dialog,
				                    int pos) {
				            	/*
				            	 * 'edit text' will allow user to edit the cleared date of transaction
				            	 * on clicking the cleared date, 
				            	 * 1. it builds the datepicker dialog
				            	 * 2. set the respective cleared date in the datepicker
				            	 * 3. when dialogbox is closed, it sets the selected date in the clearance date
				            	 * textview
				            	 */
				                if(pos == 0){
				                	//get the cleared date from textview
				                    int rowcount = bankRecontable.getChildCount();    
				                    for(int k=0;k<rowcount;k++){
					                    View row = bankRecontable.getChildAt(rowid+1);
					                    LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(6);
					                    tvdate = (TextView) l.getChildAt(0);
					                    retrived_date = (String) tvdate.getText();
					                    result=retrived_date;
				                   }
				                   /*
				                    * if date is cleared, we are setting a space in the textview
				                    * so, to satisfy that case store financial from date in the string variable
				                    * else store the retrived date in that variable...
				                    */
				                   if(retrived_date==" "){
				                      
				                       dateParts = financialFromDate.split("-");
				                   }else{
				                      
				                       dateParts = result.split("-");
				                   }
				                  
				                   	/*
				                   	 * now, split the date into day, month and year and 
				                   	 * set in datepicker
				                   	 */
				                    String fromday  = dateParts[0];
				                    String frommonth = dateParts[1];
				                    String fromyear = dateParts[2];
				                 
				                    sel_year = Integer.parseInt(fromyear);
				                    sel_month = Integer.parseInt(frommonth);
				                    sel_day = Integer.parseInt(fromday);
				                   
				                    /*
				                     * Building DatepPicker dialog
				                     */
				                    AlertDialog dialog1;
				                   
				                    //Preparing views
				                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				                    View layout = inflater.inflate(R.layout.datepiker, null);
				                   
				                    AlertDialog.Builder builder = new AlertDialog.Builder(bankReconciliation.this);
				                    builder.setView(layout);
				                    builder.setTitle("Set clearance date");
				                   
				                    final   DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker1);
				                    dp.init(sel_year,sel_month-1,sel_day, null);
				                   
				                    builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener() {
				                        @Override
				                        public void onClick(DialogInterface arg0, int arg1) {
				                           //do nothing
				                        }
				                    });
				                    
				                    /*
				                     * 'set' button will get the date from datepicker dialog and 
				                     * will set into the clearance date textview
				                     */
				                    builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
				                        @Override
				                        public void onClick(DialogInterface arg0, int arg1) {
				                            try {
				                               
				                                int selectedYear = dp.getYear();
				                                int selectedMonth = dp.getMonth();
				                                int selectedDay =  dp.getDayOfMonth();
				                               
				                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				                                Date date1 = sdf.parse(financialFromDate);
				                                Date date2 = sdf.parse(financialToDate);
				                                Date date3 = sdf.parse(mFormat.format(Double.valueOf(selectedDay))+"-"
				                                                +mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(selectedMonth))))+ 1))+"-"
				                                                +mFormat.format(Double.valueOf(selectedYear)));
				                                Calendar cal1 = Calendar.getInstance(); //financial from date
				                                Calendar cal2 = Calendar.getInstance(); //financial to date
				                                Calendar cal3 = Calendar.getInstance(); //voucher date
				                              
				                                cal1.setTime(date1);
				                                cal2.setTime(date2);
				                                cal3.setTime(date3);
				                              
				                              
				                                if((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal1) || cal3.equals(cal2)){
				                                  
				                                  
				                                    date = mFormat.format(Double.valueOf(selectedDay))+"-"
				                                            +mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(selectedMonth))))+ 1))+"-"
				                                            +mFormat.format(Double.valueOf(selectedYear));
				                                  
				                                    bankRecontable = (TableLayout)findViewById(R.id.maintable);
				                                    int rowcount = bankRecontable.getChildCount();
				                                    for(int k=0;k<rowcount;k++){
				                                        View row = bankRecontable.getChildAt(rowid+1);
				                                        LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(6);
				                                        tvdate = (TextView) l.getChildAt(0);
				                                        tvdate.setText(date);
				                                    }
				                                }
				                                else{
				                                    toastValidationMessage("Please enter proper date");
				                                }
				                              
				                            } catch (Exception e) {
				                            
				                            }
				                             
				                        }}
				                    );
				                  
				                    dialog1=builder.create();
				                    dialog1.show();
				                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				                    //customizing the width and location of the dialog on screen
				                    lp.copyFrom(((Dialog) dialog1).getWindow().getAttributes());
				                    lp.height = 600;
				                    lp.width = 400;
				                    ((Dialog) dialog).getWindow().setAttributes(lp);   
				                }
				                /*
				                 * option 'clear date' will clear date in the textview and instead set just a space
				                 * (Note: when we tried to set nothing("") in the textview, it didn't work
				                 * so, we added just a space in textview)
				                 */
				                if(pos==1){
				                    int rowcount = bankRecontable.getChildCount();    
				                    for(int k=0;k<rowcount;k++){
				                        View row = bankRecontable.getChildAt(rowid+1);
				                        LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(6);
				                        tvdate = (TextView) l.getChildAt(0);
				                        tvdate.setText(" ");
				                    }
				                }
				                /*
				                 * 'set previous date' will set the previous cleared date of the transaction 
				                 */
				                if(pos==2){
				                		try {
				                			String Cdate1 = (String) for_clearenceDate.get(rowid);
				                            int rowcount = bankRecontable.getChildCount();    
				                            for(int k=0;k<rowcount;k++){
				                                View row = bankRecontable.getChildAt(rowid+1);
				                                LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(6);
				                                tvdate = (TextView) l.getChildAt(0);
				                                tvdate.setText(Cdate1);
				                            }
												} catch (Exception e) {
													toastValidationMessage("Please try again");
												}
				                }
				            }                           
				        });
				        dialog = builder.create();
				        dialog.show();
				        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				        //customizing the width and location of the dialog on screen
				        lp.copyFrom(dialog.getWindow().getAttributes());
				        lp.height = 600;
				        lp.width = 400;
				        dialog.getWindow().setAttributes(lp);   
				    }
				    /*
				     * if clearance date field is empty, 
				     * 1. directly show datepicker dialog
				     * 2. set financial from date in the datepicker
				     * 3. when dialogbox is closed, it sets the selected date in the clearance date
				     * textview
				     */
				    else{
				    	dateParts = financialFromDate.split("-");
				        
				        String fromday  = dateParts[0];
				        String frommonth = dateParts[1];
				        String fromyear = dateParts[2];
				   
				        sel_year = Integer.parseInt(fromyear);
				        sel_month = Integer.parseInt(frommonth);
				        sel_day = Integer.parseInt(fromday);
				        
				        AlertDialog dialog2;
				      
				        //Preparing views
				        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				        View layout = inflater.inflate(R.layout.datepiker, null);
				    
				   
				        //Building DatepPicker dialog
				        AlertDialog.Builder builder = new AlertDialog.Builder(bankReconciliation.this);
				        builder.setView(layout);
				        builder.setTitle("Set clearance date");
				      
				        final   DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker1);
				        dp.init(sel_year,sel_month-1,sel_day, null);
				      
				        builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener() {
				          	@Override
				          	public void onClick(DialogInterface arg0, int arg1) {
				        	   //do nothing
				          	}
				           	});
				    
				        builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
				        	@Override
				        	public void onClick(DialogInterface arg0, int arg1) {
				        		try {
				                
				        			int selectedYear = dp.getYear();
				        			int selectedMonth = dp.getMonth();
				        			int selectedDay =  dp.getDayOfMonth();
				                
				        			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				        			Date date1 = sdf.parse(financialFromDate);
				        			Date date2 = sdf.parse(financialToDate);
				        			Date date3 = sdf.parse(mFormat.format(Double.valueOf(selectedDay))+"-"
				                                   +mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(selectedMonth))))+ 1))+"-"
				                                   +mFormat.format(Double.valueOf(selectedYear)));
				        			Calendar cal1 = Calendar.getInstance(); //financial from date
				        			Calendar cal2 = Calendar.getInstance(); //financial to date
				        			Calendar cal3 = Calendar.getInstance(); //voucher date
				               
				        			cal1.setTime(date1);
				        			cal2.setTime(date2);
				        			cal3.setTime(date3);
				               
				               
				        			if((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal1) || cal3.equals(cal2)){
				                   
				                   
				        				date = mFormat.format(Double.valueOf(selectedDay))+"-"
				                               +mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(selectedMonth))))+ 1))+"-"
				                               +mFormat.format(Double.valueOf(selectedYear));
				                   
				        				bankRecontable = (TableLayout)findViewById(R.id.maintable);
				        				int rowcount = bankRecontable.getChildCount();
				        				for(int k=0;k<rowcount;k++){
				        					View row = bankRecontable.getChildAt(rowid+1);
				        					LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(6);
				        					tvdate = (TextView) l.getChildAt(0);
				        					tvdate.setText(date);
				        				}
				        			}
				        			else{
				        				toastValidationMessage("Please enter proper date");
				        			}
				               
				        		} catch (Exception e) {
				             
				        		}
				              
				        	}
				        });
				      
				        dialog2=builder.create();
				        dialog2.show();
				        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				        //customizing the width and location of the dialog on screen
				        lp.copyFrom(((Dialog) dialog2).getWindow().getAttributes());
				        lp.height = 600;
				        lp.width = 400;
				        ((Dialog) dialog2).getWindow().setAttributes(lp);
				       
				    }
				}
			    });
			}
		
			Ll = new LinearLayout(this);
			params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				45);
			params.setMargins(1, 1, 1, 1);
			//Ll.setPadding(10, 5, 5, 5);
			Ll.addView(label,params);
			Ll.setMinimumWidth(109);
			tr.addView((View)Ll);
		
		    }

