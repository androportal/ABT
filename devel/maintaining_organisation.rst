Maintaining organisations
=========================
* This chapter explains maintainance of ``organisations`` in ABT. 
* It includes ``creation`` of organisation, ``saving`` organisation details and setting ``preferences``.

Welcome page of ABT
+++++++++++++++++++
The first layout of the android APK is activity_main.xml, which is located in ``res/layout/`` folder.
It contains the features for the first screen that displays two buttons:
	#. Create new organisation
	#. Select existing organisation, besides the main logo of Aakash Business Tool(ABT).
  	
**File res/layout/activity_main.xml**
        
	.. code-block:: xml
	
		<?xml version="1.0" encoding="utf-8"?>
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		    xmlns:tools="http://schemas.android.com/tools"
		    android:layout_width="match_parent"
		    android:layout_height="match_parent"
		    android:orientation="vertical" 
		    android:background="@drawable/black_main_page">

		    <LinearLayout
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center_horizontal"
			    android:paddingTop="79dp">
			    
			<TextView
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center_horizontal"
			    android:text="on Aakash"
			    android:textColor="#FFFFFF" />

		    </LinearLayout>
		    
			<LinearLayout
			    android:layout_width="match_parent"
			    android:layout_height="match_parent"
			    android:layout_gravity="right"
			    android:orientation="vertical" >

			    <LinearLayout
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:layout_gravity="right"
				android:orientation="vertical"
				android:paddingBottom="50dp"
				android:paddingRight="70dp"
				android:paddingTop="51dp" >

			    <Button
				android:id="@+id/bcreateOrg"
				android:layout_width="300dp"
				android:layout_height="50dp"
				android:layout_gravity="top"
				android:background="@drawable/button_yellow"
				android:clickable="true"
				android:gravity="center"
				android:text="@string/createOrgButton"
				android:textColor="#000000"
				android:textSize="20dp" />
			    </LinearLayout>

			    <LinearLayout
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="right"
			    android:orientation="vertical"
			    android:paddingRight="70dp" >
			    <Button
				android:id="@+id/bselectOrg"
				android:layout_width="300dp"
				android:layout_height="50dp"
				android:layout_gravity="bottom"
				android:background="@drawable/button_yellow"
				android:clickable="true"
				android:gravity="center"
				android:text="@string/selectOrgButton"
				android:textColor="#000000"
				android:textSize="20dp" />
			    </LinearLayout>

			    <TextView
				android:layout_width="wrap_content"
				android:layout_height="wrap_content"
				android:layout_gravity="center_horizontal|right"
				android:paddingRight="40dp"
				android:paddingTop="72dp"
				android:text="IIT Bombay"
				android:textColor="#FFFFFF" />

			</LinearLayout>
		   

		</LinearLayout>
			
			
.. image:: images/home_page.png
	   :name: ABT main page
	   :align: center
	   
	   

**src/com/example/gkaakash/MainActivity.java**

* The below method handles the ``click event`` of Create organization and Select existing organization.

	.. code-block:: java


		//Attach a listener to the click event for the button
		private void addListenerOnButton() {
			//Create a class implementing “OnClickListener”
			//and set it as the on click listener for the button
			create_org.setOnClickListener(new OnClickListener() {
	
				public void onClick(View arg0) {
						reportmenuflag = true;
					//To pass on the activity to the next page
					Intent intent = new Intent(context, createOrg.class);
					startActivity(intent);
				    
				    }// end of onClick
			});// end of create_org.setOnClickListener
		
			select_org.setOnClickListener(new OnClickListener() {
			
				public void onClick(View arg0) {
					reportmenuflag = false;
				    	// check existing organisation name list is null
					try{
					    	// call the getOrganisationName method from startup
					    	orgNameList = startup.getOrgnisationName(); // return lists of existing organisations
					    	if(orgNameList.length<1)
					    	{
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setMessage("Please create organisation")
								.setCancelable(false)
								.setPositiveButton("Ok",
									new DialogInterface.OnClickListener() {
									    public void onClick(DialogInterface dialog, int id) {
									       //do nothing
									    }
									});
							       
							AlertDialog alert = builder.create();
							alert.show();                    }
						else
						    {
							//To pass on the activity to the next page
							Intent intent = new Intent(context, selectOrg.class);
							startActivity(intent);  
						    }
						    
					   }catch(Exception e)
						{
							AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setMessage("Please check server connection")
							.setCancelable(false)
							.setPositiveButton("Ok",
								new DialogInterface.OnClickListener() {
								    public void onClick(DialogInterface dialog, int id) {
								       //do nothing
								    }
								});
						       
							AlertDialog alert = builder.create();
							alert.show();    
						}
					}// end of onClick
				});// end of select_org.setOnClickListener
			}// end of addListenerOnButton() method

	
Create new organisation
+++++++++++++++++++++++
	
* On clicking ``Create new organisation`` button, application loads  ``create_org.xml``.

**File  res/layout/create_org.xml**

	.. code-block:: xml

		?xml version="1.0" encoding="utf-8"?>
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		 android:layout_width="fill_parent" 
		 android:layout_height="fill_parent"
		 android:orientation="vertical"
		 android:weightSum="100"
		 android:background="@drawable/dark_gray_background">
		 
		<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
		    android:layout_width="fill_parent"
		    android:layout_height="fill_parent"
		    android:background="@drawable/dark_gray_background"
		    android:layout_weight="80">
		    
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		    	xmlns:tools="http://schemas.android.com/tools"
		    	android:layout_width="fill_parent"
		    	android:layout_height="fill_parent"
		    	android:orientation="vertical" 
		    	android:paddingLeft="10dp"
		 	android:paddingRight="10dp">

		    <TextView
			android:id="@+id/tvOrgName"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:text="Enter organisation name"
			android:textColor="#FFFFFF"
			android:textSize="20dp" />

		    <EditText
			android:id="@+id/etOrgName"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:ems="10"
			android:hint="Maximum 50 characters " 
			android:inputType="textCapWords"
			android:maxLength="50">

			<requestFocus android:layout_width="wrap_content" />

		    </EditText>

		    <TextView
			android:id="@+id/tvOrgType"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:text="Select organisation type"
			android:textColor="#FFFFFF"
			android:textSize="20dp" />

		    <Spinner
			android:id="@+id/sOrgType"
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:entries="@array/orgType_arrays"
			android:prompt="@string/orgType_prompt" />
		    
		    <TextView
			android:id="@+id/tvFnclYear"
			android:layout_width="fill_parent"
			android:layout_height="60dp"
			android:layout_weight="30"
			android:gravity="center"
			android:text="Financial year"
			android:textColor="#FFFFFF"
			android:textSize="20dp" />
		    
		      
		    <LinearLayout
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="horizontal"
			android:paddingLeft="40dp"
			android:paddingRight="40dp"
			android:weightSum="100" >

			<Button
			    android:id="@+id/btnChangeFromDate"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_weight="40"
			    android:textSize="20dp"
			    android:text="Set from date" />
				<View
				android:layout_width="0dp"
			android:layout_height="0dp"
			android:layout_weight="20" >
		    	</View>
			<Button
			    android:id="@+id/btnChangeToDate"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_weight="40"
			    android:textSize="20dp"
			    android:text=" Set to date " />
		    </LinearLayout>
		    
		    <LinearLayout
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="horizontal"
			android:weightSum="100" 
			android:paddingLeft="40dp"
			android:paddingRight="40dp">

			<TextView
			    android:id="@+id/tvFromDate"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_weight="40"
			    android:gravity="center"
			    android:text=""
			    android:textSize="20dp"
			    android:textColor="#FFFFFF" />

			<View
			android:layout_width="0dp"
			android:layout_height="0dp"
			android:layout_weight="20" >
		    	</View>

			<TextView
			    android:id="@+id/tvToDate"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_weight="40"
			    android:gravity="center"
			    android:text=""
			   	android:textSize="20dp"
			    android:textColor="#FFFFFF" />
		    </LinearLayout>

		    
		</LinearLayout>
		</ScrollView>

		<LinearLayout
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="horizontal"
			android:layout_weight="20"
			android:layout_alignParentBottom="true"
			android:background="@drawable/blackbutton" >

			<Button
			    android:id="@+id/btnNext"
			    android:layout_width="fill_parent"
			    android:layout_height="match_parent"
			    android:layout_gravity="center_vertical"
			    android:text="Next"
			    android:textSize="20dp" />
		    </LinearLayout>
		</LinearLayout>


.. image:: images/create_org.png
	   :name: ABT main page
	   :align: center

* The associated activity with the above xml page is ``createOrg.java`` which contains logic for adding new organisation.

**File src/com/example/gkaakash/createOrg.java**

* To get started with the application, there should be ``atleast one`` organization.

* This page allow the user to create organizaion with a particular ``financial year`` under certain organization type 
  such as,``NGO or Profit Making``.

* Its activiy is explained below along with code.

* The activity contains the essential and required import like 

	.. code-block:: java

		import java.math.RoundingMode;
		import java.text.DecimalFormat;
		import java.text.SimpleDateFormat;
		import java.util.Calendar;
		import java.util.Date;
		import com.gkaakash.controller.Startup;
		import android.app.AlertDialog;
		import android.content.Context;
		import android.content.DialogInterface;
		import android.content.Intent;
		import android.os.Bundle;
		import android.view.LayoutInflater;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.view.ViewGroup;
		import android.widget.AdapterView;
		import android.widget.AdapterView.OnItemSelectedListener;
		import android.widget.Button;
		import android.widget.DatePicker;
		import android.widget.EditText;
		import android.widget.Spinner;
		import android.widget.TextView;

* The activity intializes all the essential parameters and variables.

	.. code-block:: java

		TextView tvDisplayFromDate, tvDisplayToDate;
		Button btnChangeFromDate, btnChangeToDate, btnNext;
		static int year, month, day, toYear, toMonth, toDay;
		static final int FROM_DATE_DIALOG_ID = 0;
		static final int TO_DATE_DIALOG_ID = 1;
		Spinner orgType; 
		String org;
		static String organisationName,orgTypeFlag,selectedOrgType,todate;
		static String fromdate;
		AlertDialog dialog;
		final Calendar c = Calendar.getInstance();
		final Context context = this;
		private EditText orgName;
		Object[] deployparams;
		DecimalFormat mFormat;
		private Object[] orgNameList;
		Object[] financialyearList;
		boolean orgExistFlag;
		static Integer client_id;
		
* ``onCreate`` method loads all the required methods at load time.

	.. code-block:: java	
			
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			//Calling create_org.xml
			setContentView(R.layout.create_org);
			
			client_id= Startup.getClient_id();
	
			MainActivity.no_dailog = true; //comment this line if running this app on emulator
			MainActivity.help_dialog.dismiss(); //comment this line if running this app on emulator
	
			//for two digit format date for dd and mm
			mFormat= new DecimalFormat("00");
			mFormat.setRoundingMode(RoundingMode.DOWN);
	
			//Declaring new method for setting date into "from date" and "to date" textview
			setDateOnLoad();
			
			/*
			 * creating a new interface for showing a date picker dialog that
			 * allows the user to select financial year start date and to date
			 */
			addListeneronDateButton();
			
			//creating interface to pass on the activity to next page
			addListeneronNextButton();
			
			orgType = (Spinner) findViewById(R.id.sOrgType);
			org  = (String) orgType.getSelectedItem();
			
			//creating interface to listen activity on Item 
			addListenerOnItem();
		}

* The below method sets standard financial ``From`` and ``To`` date, when the page gets loaded ie. 1st April to 31st March.

* Once the ``From`` date is seted, ``To`` date gets automatically updated by ``12`` months and minus ``1`` day.

	.. code-block:: java	

		private void setDateOnLoad() {

			tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
			tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);

			/*
			 * set "from date" and "to date" textView
			 * for creating calendar object and linking with its 'getInstance' method, 
			 * for getting a default instance of this class for general use
			 */

			year = c.get(Calendar.YEAR);
			month = 3; //month = april
			day = 1;

			//set from date: day=01, month=April, year=current year
			tvDisplayFromDate.setText(new StringBuilder()
			.append(mFormat.format(Double.valueOf(1))).append("-")
			.append(mFormat.format(Double.valueOf(4))).append("-")
			.append(year));

			//Add one year to current date time
			c.add(Calendar.YEAR,1);
			toYear = c.get(Calendar.YEAR);
			toMonth = 2;
			toDay = 31;

			//set to date: day=31, month=March, year=current year+1
			tvDisplayToDate.setText(new StringBuilder()
			.append(mFormat.format(Double.valueOf(31))).append("-")
			.append(mFormat.format(Double.valueOf(3))).append("-")
			.append(toYear));

		}

* The below method builds date picker dialog on click and sets selected date on the 
  ``From`` date button(same with`` To`` date button).  

* We can also change the To date ``manually`` according to organization's rules or requirement.

	.. code-block:: java

		private void addListeneronDateButton() {
		
			btnChangeFromDate = (Button) findViewById(R.id.btnChangeFromDate);
			btnChangeToDate = (Button) findViewById(R.id.btnChangeToDate);

			/*
			 * when button is clicked, user can select from date(day, month and year) from datepicker,
			 * selected date will set in 'from date' textview and set date in 'to date' text view
			 * which is greater than from date by one year and minus one day(standard financial year format)
			 * 
			 */
			btnChangeFromDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					//Preparing views
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.datepiker, (ViewGroup) findViewById(R.id.layout_root));
					
					//Building DatepPcker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
			    		builder.setTitle("Set from date");
			    		
			    		final DatePicker dp = (DatePicker)layout.findViewById(R.id.datePicker1);
			    		dp.init(year,month,day, null);
			    
			    		builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						 year = dp.getYear();
						 month = dp.getMonth();
						 day =  dp.getDayOfMonth();
						 String strDateTime = mFormat.format(Double.valueOf(day)) + "-" 
						 + (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))) + "-" + year;
						 //set date in from date textview
						 tvDisplayFromDate.setText(strDateTime);
						 
						 //setting selected date into calender's object
						 c.set(year, month, day);
						 //add one year
						 c.add(Calendar.YEAR, +1);
						 //subtracting one day
						 c.add(Calendar.DAY_OF_MONTH, -1);
						 
						 toYear = c.get(Calendar.YEAR);
						 toMonth = c.get(Calendar.MONTH);
						 toDay = c.get(Calendar.DAY_OF_MONTH);
						 
						 //set date in to date textview
						 tvDisplayToDate.setText(new StringBuilder()
						 .append(mFormat.format(Double.valueOf(toDay)))
						 .append("-").append(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(toMonth+1)))))))
						 .append("-").append(toYear));
					}
					}); 
					dialog=builder.create();
					dialog.show();
				}	
			});
	
			/*
			 * when button clicked, user can change the 'to date' from datepicker,
			 * it will set the selected date in 'to date' textview, if to date is greater than from date
			 */
			btnChangeToDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					//Preparing views
					LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
					View layout = inflater.inflate(R.layout.datepiker, (ViewGroup) findViewById(R.id.layout_root));
					
					//Building DatepPicker dialog
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setView(layout);
			    		builder.setTitle("Set to date");
			    
			    		final   DatePicker dp = (DatePicker) layout.findViewById(R.id.datePicker1);
			    		dp.init(toYear,toMonth,toDay, null);
			    
			    		builder.setPositiveButton("Set",new  DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						 
						int Year = dp.getYear();
						int Month = dp.getMonth();
						int Day =  dp.getDayOfMonth();
						 
						 try {
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							Date date1 = sdf.parse(day+"-"+month+"-"+year); //from date
						    	Date date2 = sdf.parse(Day+"-"+Month+"-"+Year); //to date
						
						    	Calendar cal1 = Calendar.getInstance(); 
						    	Calendar cal2 = Calendar.getInstance(); 
						    	
						    	cal1.setTime(date1);
						    	cal2.setTime(date2);
						    	
						    	if(cal2.after(cal1)){
						    		toYear = Year;
								toMonth = Month;
								toDay =  Day;
								String strDateTime = mFormat.format(Double.valueOf(toDay)) + "-" 
									 + (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(toMonth))))+ 1))) + "-" 
									 + toYear;
						    		tvDisplayToDate.setText(strDateTime);
						    	}
						    	else{
						    		String message = "Please enter proper date";
								toastValidationMessage(messsage);
						    	}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					}); 
					dialog=builder.create();
					dialog.show();
				}	
			});
	    	}


* method to take ItemSelectedListner interface as a argument  

	.. code-block:: java	
		
		void addListenerOnItem(){
			//Attach a listener to the Organisation Type Spinner
			orgType.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
					//Retrieving the selected org type from the Spinner and assigning it to a variable 
					selectedOrgType = parent.getItemAtPosition(position).toString();
					orgTypeFlag = selectedOrgType;
			
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
			
					}

			});// End of orgType.setOnItemSelectedListener

		}// End of addListenerOnItem()
		
* creating an interface to pass on the activity to next page to fill organisation details.

* Add validation for organisation exist.

	.. code-block:: java	
	
		private void addListeneronNextButton() {
		
			final Context context = this;
			//Request a reference to the button from the activity by calling “findViewById” 
			//and assign the retrieved button to an instance variable
			btnNext = (Button) findViewById(R.id.btnNext);
			orgType = (Spinner) findViewById(R.id.sOrgType);
			tvDisplayFromDate = (TextView) findViewById(R.id.tvFromDate);
			tvDisplayToDate = (TextView) findViewById(R.id.tvToDate);
			orgName = (EditText) findViewById(R.id.etOrgName);
			
			//Create a class implementing “OnClickListener” and set it as the on click listener for the button "Next"
			btnNext.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
				
					organisationName = orgName.getText().toString();
					fromdate = tvDisplayFromDate.getText().toString();
					todate = tvDisplayToDate.getText().toString();
					try{
						// call the getOrganisationName method from startup
			    			orgNameList = startup.getOrgnisationName(); // return lists of existing organisations
				
						for(Object org : orgNameList){
							if(organisationName.equals(org)){
								orgExistFlag = false;
						
							//call getFinancialYear method from startup.java 
						    	//it will give you financialYear list according to orgname
						    	financialyearList = startup.getFinancialYear(organisationName);
						    	
						    	for(Object fy : financialyearList)
						    	{
						    		Object[] y = (Object[]) fy;
						    		// concatination From and To date 
						    		String fromDate=y[0].toString();
						    		String toDate=y[1].toString();
						    		
						    		if(fromDate.equals(fromdate) && toDate.equals(todate)){
						    			orgExistFlag = true;
						    			break;
						    		}
						    		
						    	}
						}
					}
			    	
					if("".equals(organisationName)){
						toastValidationMessage("Please enter the organisation name");
					}
					else if(orgExistFlag == true){
						toastValidationMessage("Organisation name "+organisationName+" with this financial year exist");
						orgExistFlag = false;
						}
					else{
						//To pass on the activity to the next page
						MainActivity.editDetails=false;
						Intent intent = new Intent(context, orgDetails.class);
					    	startActivity(intent); 
					}
				}catch(Exception e)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
		        		builder.setMessage("Please check server connection")
				        .setCancelable(false)
				        .setPositiveButton("Ok",
				                new DialogInterface.OnClickListener() {
				                     public void onClick(DialogInterface dialog, int id) {
				                    	Intent intent = new Intent(context, MainActivity.class);
				    				    startActivity(intent); 
				                    }
				                });
		               
					AlertDialog alert = builder.create();
					alert.show();    
						}
					}
				}); //End of btnNext.setOnClickListener
 
			}// End of addListeneronNextButton()
		
* On back pressed clear the history and get back to the welcome page.

	.. code-block:: java
			
		public void onBackPressed() {
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
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

* On pressing ``Next`` button, It calls a new activity ie. orgDetails.java to save organisation with details.

* The following fields will appear on the screen depending upon ``type`` of organisation,

	======================  =================
	**NGO**                 **Profit making**
	======================  =================
	Registration no.        Contry
	Registration date       State
	FCRA registration no.   City
	FCRA registration date  Address
	Contry                  Postal code
	State			Phone no.
	City			Fax no.
	Address			email ID
	Postal code		Website
	Phone no.		VAT no.
	Fax no.			Service Tax no.
	email ID		PAN no.
	Website			
	PAN no. 		
	======================  =================
	
* These fields are included in * The layout of list items is included in ``res/layout/org_details.xml``.

**File res/layout/org_details.xml**

	.. code-block:: xml

		<?xml version="1.0" encoding="utf-8"?>
		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
		 android:layout_width="fill_parent" 
		 android:layout_height="fill_parent"
		 android:orientation="vertical"
		 android:weightSum="100"
		 android:background="@drawable/dark_gray_background">
		 
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
				    	android:id="@+id/tvRegNum"
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="Registration no."
						android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:visibility="invisible" />
				<EditText 
				    android:id="@+id/etRegNum"
				    android:layout_width="0dip"
					android:layout_weight="2"
					android:hint="Tap to enter registration number"
					android:visibility="invisible"/>
				</TableRow>
			    
			    <TableRow>
				<TextView 
				    	android:id="@+id/tvRegDate"
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="Registration date"
						android:textColor="#FFFFFF"
					android:textSize="20dp" 
					android:visibility="invisible"/>
				<Button 
				    android:id="@+id/btnRegDate"
				    android:layout_width="0dip"
					android:layout_weight="2"
					android:gravity="left"
					android:text=""
					android:textSize="20dp"
					android:visibility="invisible" />
				</TableRow>
			    
			    <TableRow>
				<TextView 
				    	android:id="@+id/tvFcraNum"
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="FCRA registration no."
						android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:visibility="invisible" />
				<EditText 
				    android:id="@+id/etFcraNum"
				    android:layout_width="0dip"
					android:layout_weight="2"
					android:hint="Tap to enter FCRA registration number"
					android:visibility="invisible"/>
				</TableRow>
			    
			    <TableRow>
				<TextView 
				    	android:id="@+id/tvFcraDate"
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="FCRA registration date"
						android:textColor="#FFFFFF"
					android:textSize="20dp" 
					android:visibility="invisible"/>
				<Button 
				    android:id="@+id/btnFcraDate"
				    android:layout_width="0dip"
					android:layout_weight="2"
					android:gravity="left"
					android:text=""
					android:textSize="20dp"
					android:visibility="invisible" />
				</TableRow>
			    
				<TableRow>
				<TextView 
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="Country"
						android:textColor="#FFFFFF"
					android:textSize="20dp" />
				<Spinner
				android:id="@+id/sGetCountry"
				android:layout_width="0dip"
				android:layout_height="wrap_content"
				android:entries="@array/country_prompt"
				android:prompt="@string/country"
				android:layout_weight="2" />
		
				</TableRow>
				<TableRow>
				<TextView 
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="State"
						android:textColor="#FFFFFF"
					android:textSize="20dp" />
				<Spinner
				android:id="@+id/sGetStates"
				android:layout_width="0dip"
				android:layout_height="wrap_content"
				android:layout_weight="2"
				android:prompt="@string/state" />
				</TableRow>
		
				<TableRow>
				<TextView 
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="City"
						android:textColor="#FFFFFF"
					android:textSize="20dp" />
				<Spinner
				android:id="@+id/sGetCity"
				android:layout_width="0dip"
				android:layout_height="wrap_content"
				android:layout_weight="2" 
				android:prompt="@string/city"/>
				</TableRow>	
		
				<TableRow>
				<TextView 
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="Address"
						android:textColor="#FFFFFF"
					android:textSize="20dp" />
				<EditText 
				    android:id="@+id/etGetAddr"
				    android:layout_width="0dip"
					android:layout_weight="2"
					android:hint="Tap to enter address"
					android:inputType="textPostalAddress" />
				</TableRow>
			
				<TableRow>

				<TextView
				    android:layout_width="0dip"
				    android:layout_weight="1"
				    android:text="Postal code"
				    android:textColor="#FFFFFF"
				    android:textSize="20dp" />

				<EditText 
				    	android:id="@+id/sGetPostal"
				    	android:layout_width="0dip"
						android:layout_weight="2"
						android:hint="Tap to enter postal code"
						android:inputType="phone" />
				</TableRow>
		
				<TableRow>

				<TextView
				    android:layout_width="0dip"
				    android:layout_weight="1"
				    android:text="Phone no."
				    android:textColor="#FFFFFF"
				    android:textSize="20dp" />

				<EditText 
				    	android:id="@+id/eGetPhone"
				    	android:layout_width="0dip"
						android:layout_weight="2"
						android:hint="Tap to enter phone number" 
						android:inputType="phone"/>
				</TableRow>
				<TableRow>

				<TextView
				    android:layout_width="0dip"
				    android:layout_weight="1"
				    android:text="Fax no."
				    android:textColor="#FFFFFF"
				    android:textSize="20dp" />

				<EditText 
				    	android:id="@+id/eGetFax"
				    	android:layout_width="0dip"
						android:layout_weight="2"
						android:hint="Tap to enter fax number" 
						android:inputType="phone"/>
				</TableRow>
				<TableRow>
				<TextView 
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="E-mail ID"
						android:textColor="#FFFFFF"
					android:textSize="20dp" />
				<EditText 
				    	android:id="@+id/eGetEmailid"
				    	android:layout_width="0dip"
						android:layout_weight="2"
						android:hint="Tap to enter e-mail iD"
						android:inputType="textEmailAddress"/>
				</TableRow>
		
				<TableRow>
				<TextView 
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="Website"
						android:textColor="#FFFFFF"
					android:textSize="20dp" />
				<EditText 
					android:id="@+id/etGetWebSite"
				    	android:layout_width="0dip"
						android:layout_weight="2"
						android:hint="Tap to enter website"
						android:inputType="textEmailAddress"/>
				</TableRow>
		
				<TableRow>

				<TextView
				    android:id="@+id/tvMVATnum"
				    android:layout_width="0dip"
				    android:layout_weight="1"
				    android:text="VAT no."
				    android:textColor="#FFFFFF"
				    android:textSize="20dp"
				    android:visibility="invisible" />

				<EditText 
				    android:id="@+id/etMVATnum"
				    android:layout_width="0dip"
					android:layout_weight="2"
					android:hint="Tap to enter VAT number"
					android:visibility="invisible"/>
				</TableRow>
		
				<TableRow>
				<TextView 
				    	android:id="@+id/tvServiceTaxnum"
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="Service tax no."
						android:textColor="#FFFFFF"
					android:textSize="20dp"
					android:visibility="invisible" />
				<EditText 
				    android:id="@+id/etServiceTaxnum"
				    android:layout_width="0dip"
					android:layout_weight="2"
					android:hint="Tap to enter service tax number"
					android:visibility="invisible"/>
				</TableRow>
		
				<TableRow>
				<TextView 
				    	android:layout_width="0dip"
						android:layout_weight="1"
						android:text="PAN no."
						android:textColor="#FFFFFF"
					android:textSize="20dp" />
				<EditText 
				    	android:id="@+id/etPanNo"
				    	android:layout_width="0dip"
						android:layout_weight="2"
						android:hint="Tap to enter parmanent account number"/>
				</TableRow>
		</TableLayout>
		</ScrollView>
				
				<LinearLayout
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="horizontal"
			android:layout_weight="20"
			android:weightSum="100"
			android:layout_alignParentBottom="true"
			android:background="@drawable/blackbutton" >

			<Button
			    android:id="@+id/btnOrgDetailSave"
			    android:layout_width="wrap_content"
			    android:layout_height="match_parent"
			    android:layout_gravity="center_vertical"
			    android:layout_weight="50"
			    android:text="Save"
			    android:textSize="20dp" />
		
			<Button
			    android:id="@+id/btnSkip"
			    android:layout_width="wrap_content"
			    android:layout_height="match_parent"
			    android:layout_gravity="center_vertical"
			    android:layout_weight="50"
			    android:text="Skip"
			    android:textSize="20dp" />

		    </LinearLayout>
		</LinearLayout>

.. image:: images/org_details.png
	   :name: ABT main page
	   :align: center

* The associated java code to save these details along with organisation name, type and financial year is included in below file,

**File src/com/example/gkaakash/orgDetails.java**

* It contains the essential and required import like,

	.. code-block:: java
	
		package com.example.gkaakash;

		import java.util.ArrayList;
		import java.util.Calendar;
		import java.util.List;
		import com.gkaakash.controller.*;
		import android.app.Activity;
		import android.app.AlertDialog;
		import android.app.DatePickerDialog;
		import android.app.Dialog;
		import android.app.ProgressDialog;
		import android.content.Context;
		import android.content.DialogInterface;
		import android.content.Intent;
		import android.os.Bundle;
		import android.view.Menu;
		import android.view.MenuItem;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.widget.AdapterView;
		import android.widget.AdapterView.OnItemSelectedListener;
		import android.widget.ArrayAdapter;
		import android.widget.Button;
		import android.widget.DatePicker;
		import android.widget.EditText;
		import android.widget.Spinner;
		import android.widget.TextView;
		import android.widget.Toast;

* The activity intializes all the essential parameters and variables.

	.. code-block:: java
	
		public class orgDetails extends Activity{
			//Declaring variables 
			Button btnorgDetailSave, btnRegDate, btnFcraDate,btnSkip;
			int year, month, day;
			static final int REG_DATE_DIALOG_ID = 0;
			static final int FCRA_DATE_DIALOG_ID = 1;
			String getSelectedOrgType,getToDate,getOrgName, getFromDate, selectedCounrty;
			TextView tvRegNum, tvRegDate, tvFcraNum, tvFcraDate, tvMVATnum, tvServiceTaxnum;
			EditText etRegNum, etFcraNum, etMVATnum, etServiceTaxnum;
			String selectedStateName;
			String selectedCityName;
			private int group1Id = 1;
			int Edit = Menu.FIRST; 
			int Delete = Menu.FIRST +1;
			int Finish = Menu.FIRST +2;
			AlertDialog dialog;
			final Context context = this;
			private String orgaddress;
			Spinner getstate, getcity;
			static Integer client_id;
			private Startup startup;
			private Object[] deployparams;
			protected ProgressDialog progressBar;
			private EditText etGetAddr;
			private EditText sGetPostal;
			private EditText eGetPhone;
			private EditText eGetEmailid;
			private EditText etPanNo;
			private EditText etGetWebSite;
			private EditText eGetFax;
			private Spinner scountry;
			protected Object[] orgparams;
			private String getAddr, getPin,eGetTelNo,eGetFaxNO,etGetWeb,eGetEmail,
			etPan,etMVATno,etServiceTaxno,etRegNo,RegDate,FcraDate,etFcraNo;
			private Organisation org;
			private boolean setOrgDetails;
			Boolean editDetailsflag;
			String save_edit;
			ArrayAdapter<String> dataAdapter;
			ArrayAdapter<String> dataAdapter1;
			String setfromday,setfrommonth,setfromyear,setfromday1,setfrommonth1,setfromyear1;
			ArrayList<String> detailsList_foredit ;
			String orgcode;
			String reg_date,fcra_date;
			static String orgtype;

* OnCreate method calls all required methods at load time.

	.. code-block:: java

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Calling org_details.xml
			setContentView(R.layout.org_details);
			// creating instance of startup to get the connection
			startup = new Startup();
			org = new Organisation();
		
			editDetailsflag = MainActivity.editDetails;
			
			//lets get reference of all fields
			btnorgDetailSave = (Button) findViewById(R.id.btnOrgDetailSave);
			getstate = (Spinner) findViewById(R.id.sGetStates);
			getcity = (Spinner) findViewById(R.id.sGetCity);
			btnSkip = (Button) findViewById(R.id.btnSkip);
			btnSkip.setText("Skip");
			tvRegNum = (TextView) findViewById(R.id.tvRegNum);
			etRegNum = (EditText) findViewById(R.id.etRegNum);
			tvRegDate = (TextView) findViewById(R.id.tvRegDate);
			btnRegDate = (Button) findViewById(R.id.btnRegDate);
			tvFcraNum = (TextView) findViewById(R.id.tvFcraNum);
			etFcraNum = (EditText) findViewById(R.id.etFcraNum);
			tvFcraDate = (TextView) findViewById(R.id.tvFcraDate);
			btnFcraDate = (Button) findViewById(R.id.btnFcraDate);
			tvMVATnum = (TextView) findViewById(R.id.tvMVATnum);
			etMVATnum = (EditText) findViewById(R.id.etMVATnum);
			etGetAddr =(EditText) findViewById(R.id.etGetAddr);
			tvServiceTaxnum = (TextView) findViewById(R.id.tvServiceTaxnum);
			etServiceTaxnum = (EditText) findViewById(R.id.etServiceTaxnum);
			sGetPostal=(EditText) findViewById(R.id.sGetPostal);
			eGetFax=(EditText) findViewById(R.id.eGetFax);
			eGetPhone=(EditText) findViewById(R.id.eGetPhone);
			scountry=(Spinner)findViewById(R.id.sGetCountry);
			eGetEmailid=(EditText) findViewById(R.id.eGetEmailid);
			etPanNo =(EditText) findViewById(R.id.etPanNo);
		
			etGetWebSite=(EditText) findViewById(R.id.etGetWebSite);

* if we are in edit organisation detail page(``Master maenu >> Preferences >> Edit organisation details``), get the value of each field which was saved before and set it in edittext.

* and set the visibility of fields on screen depending upon organisation type.

	.. code-block:: java
	
		if(editDetailsflag==true){
			detailsList_foredit=menu.accdetailsList;
			//System.out.println("cuming from menu page:"+menu.orgtype);

			orgtype=detailsList_foredit.get(1);
			//System.out.println("OT"+orgtype);

			orgcode=detailsList_foredit.get(0);
			//System.out.println("org code:"+orgcode);
			etGetAddr.setText(detailsList_foredit.get(3));
			sGetPostal.setText(detailsList_foredit.get(5));
			eGetPhone.setText(detailsList_foredit.get(8));
			eGetFax.setText(detailsList_foredit.get(9));
			eGetEmailid.setText(detailsList_foredit.get(11));
			etGetWebSite.setText(detailsList_foredit.get(10));
			etMVATnum.setText(detailsList_foredit.get(13));
			etServiceTaxnum.setText(detailsList_foredit.get(14));
			etPanNo.setText(detailsList_foredit.get(12));
			etMVATnum.setText(detailsList_foredit.get(13));
			etServiceTaxnum.setText(detailsList_foredit.get(14)); 
			etRegNum.setText(detailsList_foredit.get(15)); 
			etFcraNum.setText(detailsList_foredit.get(17)); 
			btnRegDate.setText(detailsList_foredit.get(16));
			System.out.println("Reg Date:"+detailsList_foredit.get(16));
			reg_date=detailsList_foredit.get(16);
			btnFcraDate.setText(detailsList_foredit.get(18));
			fcra_date=detailsList_foredit.get(18);
			//setting text for skip button 
			btnSkip.setText("Reset");
		}
		 
		// Retrieving the organisation type flag value from the previous page(create organisation page)
		if(editDetailsflag==false){ 
			getSelectedOrgType=createOrg.orgTypeFlag;
		}
		else {
			getSelectedOrgType=orgtype; 
		}
		if("NGO".equals(getSelectedOrgType))
		{
			tvRegNum.setVisibility(TextView.VISIBLE);
			etRegNum.setVisibility(EditText.VISIBLE);
			tvRegDate.setVisibility(TextView.VISIBLE);
			btnRegDate.setVisibility(EditText.VISIBLE);
			tvFcraNum.setVisibility(TextView.VISIBLE); 
			etFcraNum.setVisibility(EditText.VISIBLE);
			tvFcraDate.setVisibility(TextView.VISIBLE);
			btnFcraDate.setVisibility(EditText.VISIBLE);
			tvMVATnum.setVisibility(TextView.GONE);
			etMVATnum.setVisibility(EditText.GONE);
			tvServiceTaxnum.setVisibility(TextView.GONE);
			etServiceTaxnum.setVisibility(EditText.GONE);
		}
		else
		{
			tvRegNum.setVisibility(TextView.GONE);
			etRegNum.setVisibility(EditText.GONE);
			tvRegDate.setVisibility(TextView.GONE);
			btnRegDate.setVisibility(EditText.GONE);
			tvFcraNum.setVisibility(TextView.GONE);
			etFcraNum.setVisibility(EditText.GONE);
			tvFcraDate.setVisibility(TextView.GONE);
			btnFcraDate.setVisibility(EditText.GONE);
			tvMVATnum.setVisibility(TextView.VISIBLE);
			etMVATnum.setVisibility(EditText.VISIBLE);
			tvServiceTaxnum.setVisibility(TextView.VISIBLE);
			etServiceTaxnum.setVisibility(EditText.VISIBLE);
		}
		if(editDetailsflag==false){
			// Declaring new method for setting current date into "Registration Date"
			setCurrentDateOnButton();
		}
		// creating new method do event on button 
		addListenerOnButton();
		// Method to get list Of States
		getStates();
		//creating interface to listen activity on Item 
		addListenerOnItem();
		
* setCurrentDateOnButton method is used to set current date on registration and FCRA button.

	.. code-block:: java
	
		private void setCurrentDateOnButton() {
			//for creating calendar object and linking with its 'getInstance' method, for getting a default instance of this class for general use
			final Calendar c = Calendar.getInstance();
			year = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day = c.get(Calendar.DAY_OF_MONTH);
			//set current date to registration button
			btnRegDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year));
			//set current date to FCRA registration button
			btnFcraDate.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(day).append("-").append(month + 1).append("-")
			.append(year));
		}
		
* Below method is used to set onclicklistener on ``Reset``, ``Save``, ``Registration no.`` and ``FCRA no.`` button.

* If we are in edit organisation page, ``Skip`` button behaves like ``Reset`` button which will reset all the fields.

* Save button will call ``savedeatils`` method which gets value of each field and pass to the backend through controller. 

* Registration no. and FCRA no. button can be used to set Registration and date respectively.
		
	.. code-block:: java
		
		//Attach a listener to the click event for the button
		private void addListenerOnButton() {
			final Context context = this;
			// get flag values which is static
			getOrgName=createOrg.organisationName;
			//getOrgName =Startup.getOrgansationname();
			System.out.println("orgname (create org) :"+getOrgName);
			getFromDate=createOrg.fromdate;
			getToDate=createOrg.todate;
			//Create a class implementing “OnClickListener” and set it as the on click listener for the button
			btnSkip.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(editDetailsflag==false){
						savedeatils();
					}else {
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
				        	builder.setMessage("Are you sure, you want to reset all fields? ")
				                .setCancelable(false)
				                .setPositiveButton("Yes",
				                        new DialogInterface.OnClickListener() {
				                            public void onClick(DialogInterface dialog, int id) {
												etGetAddr.setText("");
												sGetPostal.setText("");
												eGetPhone.setText("");
												eGetFax.setText("");
												eGetEmailid.setText("");
												etGetWebSite.setText("");
												etMVATnum.setText("");
												etServiceTaxnum.setText("");
												etPanNo.setText("");
												etMVATnum.setText("");
												etServiceTaxnum.setText("");
												etRegNum.setText(""); 
												etFcraNum.setText("");
												btnRegDate.setText(Startup.getfinancialFromDate());
												btnFcraDate.setText(Startup.getfinancialFromDate());
												getstate.setSelection(0);
												getcity.setSelection(0);
				                            }
		                        })
			                .setNegativeButton("No", new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                        dialog.cancel();
			                    }
		                	});
		        		AlertDialog alert = builder.create();
		        		alert.show();
					}
				}
			}); 
			
			btnorgDetailSave.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					savedeatils();
				}
			}); 
			
			btnRegDate.setOnClickListener(new OnClickListener() {
				@Override	
				public void onClick(View v) {
					//for showing a date picker dialog that allows the user to select a date (Registration Date)
					String regDate = (String) btnRegDate.getText();
					String dateParts[] = regDate.split("-");
					setfromday  = dateParts[0];
					setfrommonth = dateParts[1];
					setfromyear = dateParts[2];
		 
					System.out.println("regdate is:"+regDate);
					showDialog(REG_DATE_DIALOG_ID);
				}
			});
			
			btnFcraDate.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//for showing a date picker dialog that allows the user to select a date (FCRA Registration Date)
					String fcraDate = (String) btnFcraDate.getText();
					String dateParts[] = fcraDate.split("-");
					setfromday1  = dateParts[0];
					setfrommonth1 = dateParts[1];
					setfromyear1 = dateParts[2];
	 
					//System.out.println("fcradate is:"+setfromday1);
					//System.out.println("fcradate is:"+setfrommonth1);
					//System.out.println("fcradate is:"+setfromyear1);
					//System.out.println("fcradate is:"+fcraDate);
					showDialog(FCRA_DATE_DIALOG_ID);
				}
			});
		}
		
		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case REG_DATE_DIALOG_ID:
				
					// set date picker as current date
					return new DatePickerDialog(this, regdatePickerListener, 
							Integer.parseInt(setfromyear), Integer.parseInt(setfrommonth)-1,Integer.parseInt(setfromday));
				
			case FCRA_DATE_DIALOG_ID:
				
					// set date picker as current date
					return new DatePickerDialog(this, fcradatePickerListener, 
							Integer.parseInt(setfromyear1), Integer.parseInt(setfrommonth1)-1,Integer.parseInt(setfromday1));
				
			}
			return null;
		}
		
		private DatePickerDialog.OnDateSetListener regdatePickerListener 
	    	= new DatePickerDialog.OnDateSetListener() {
	 
			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {
					year = selectedYear; 
					month = selectedMonth; 
					day = selectedDay;
					btnRegDate.setText(new StringBuilder()
					// Month is 0 based, just add 1
					.append(day).append("-").append(month + 1).append("-")
					.append(year));
			}
		};
		
		private DatePickerDialog.OnDateSetListener fcradatePickerListener 
		= new DatePickerDialog.OnDateSetListener() {
	
			// when dialog box is closed, below method will be called.
			public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {
				year = selectedYear;
				month = selectedMonth;
				day = selectedDay;
				btnFcraDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(day).append("-").append(month + 1).append("-")
				.append(year));
			}
		};	
		
		
		
		// Method getStates
		void getStates(){
			// call the getStates method to get all States
			Object[] StateList =  startup.getStates();
			List<String> statelist = new ArrayList<String>();
	   
			// for loop to iterate list of state name and add to list
			for(Object st : StateList)
			{
				Object[] s = (Object[]) st;
				statelist.add((String) s[0]);
			}
			if(editDetailsflag==false){
				// creating array adaptor to take list of state
				dataAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, statelist);
				//set resource layout of spinner to that adaptor
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				//set adaptor contain states list to spinner 
				getstate.setAdapter(dataAdapter);
			}else {
				String state1 = detailsList_foredit.get(6);
				dataAdapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, statelist);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				int pos1 = dataAdapter.getPosition(state1);
				getstate.setAdapter(dataAdapter);
				getstate.setSelection(pos1);
			}
	   
		}// End of getStates()
		
* Method ``addListenerOnItem`` is used to set the items in ``city spinner``, depending upon which item is selected from ``state spinner``.

* and at the end get the selected country, state and city from spinner. 	
		
	.. code-block:: java

		void addListenerOnItem(){
			//Attach a listener to the states Type Spinner to get dynamic list of cities
			getstate.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					//Retrieving the selected state from the Spinner and assigning it to a variable 
					selectedStateName = parent.getItemAtPosition(position).toString();
					Object[] stateparmas;
					// checks for the selected value of item is not null
					if(selectedStateName!=null){
						// array of selected state name of type Object
						stateparmas = new Object[]{selectedStateName};
						// call the getCities method to get all related cities of given selected state name 
						Object[] CityList = startup.getCities(stateparmas);
						List<String> citylist = new ArrayList<String>();
		   
						// for loop to iterate list of city name and add to list
						for(Object st : CityList)
							citylist.add((String) st);
								if(editDetailsflag==false){
									// creating array adaptor to take list of city 
									dataAdapter1 = new ArrayAdapter<String>(context,
											android.R.layout.simple_spinner_item, citylist);
									// set resource layout of spinner to that adaptor
									dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
									// set Adaptor contain cities list to spinner 
									getcity.setAdapter(dataAdapter1);
								}else {
									String city = detailsList_foredit.get(4).trim();
									dataAdapter1 = new ArrayAdapter<String>(context,
											android.R.layout.simple_spinner_item, citylist);
									dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		   
									//System.out.println("city name"+dataAdapter1.getItem(2).trim().);
									int pos = dataAdapter1.getPosition(city);
									getcity.setAdapter(dataAdapter1);
									getcity.setSelection(pos);
		   
		   
								}
		   
					}// End of if condition
				} // End of onItemSelected()

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});// End of getstate.setOnItemSelectedListener
			
			getcity.setOnItemSelectedListener(new OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					//Retrieving the selected state from the Spinner and assigning it to a variable 
					selectedCityName = parent.getItemAtPosition(position).toString();
				}
		
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});
			scountry.setOnItemSelectedListener(new OnItemSelectedListener(){
		
				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					//Retrieving the selected state from the Spinner and assigning it to a variable 
					selectedCounrty = parent.getItemAtPosition(position).toString();
				}
		
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
				}
			});
		} // end of addListenerOnItem()
		
		
* get value of each field and pass to the backend through controller.
		
	.. code-block:: java
	
		private void savedeatils() {
			// TODO Auto-generated method stub
			getOrgName = createOrg.organisationName;
			getFromDate = createOrg.fromdate;
			getToDate = createOrg.todate;
			getAddr = etGetAddr.getText().toString();
			getPin = sGetPostal.getText().toString();
			eGetTelNo = eGetPhone.getText().toString();
			eGetFaxNO = eGetFax.getText().toString();
			etGetWeb = etGetWebSite.getText().toString();
			eGetEmail = eGetEmailid.getText().toString();	
			etPan = etPanNo.getText().toString();
			etMVATno = etMVATnum.getText().toString();
			etServiceTaxno = etServiceTaxnum.getText().toString();
			etRegNo = etRegNum.getText().toString();
			RegDate = btnRegDate.getText().toString();
			etFcraNo = etFcraNum.getText().toString();
			FcraDate = btnFcraDate.getText().toString();
			/*//progress bar moving image to show wait state
			progressBar = new ProgressDialog(context);
			progressBar.setCancelable(false);
			progressBar.setMessage("Please Wait, Saving Organisation Details ...");
			progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressBar.setProgress(0);
			progressBar.setMax(1000);
			progressBar.show();*/ 
		      	//list of input parameters type of Object 
			deployparams = new Object[]{getOrgName,getFromDate,getToDate,getSelectedOrgType}; // parameters pass to core_engine xml_rpc functions
			
			if(editDetailsflag==false){
				orgparams = new Object[]{getOrgName,getSelectedOrgType,selectedCounrty,selectedStateName,
						selectedCityName,getAddr,getPin,eGetTelNo,eGetFaxNO,etGetWeb,eGetEmail,
						etPan,etMVATno,etServiceTaxno,etRegNo,
						RegDate,etFcraNo,FcraDate }; 
				//call method deploy from startup.java 
				client_id = startup.deploy(deployparams);
				setOrgDetails = org.setOrganisation(orgparams,client_id);
			}else {
				orgparams = new Object[]{orgcode,getAddr,selectedCounrty,selectedStateName,selectedCityName,getPin, 
							eGetTelNo,eGetFaxNO,eGetEmail,etGetWeb,etMVATno,etServiceTaxno,etRegNo,RegDate,etFcraNo,FcraDate ,etPan};
				client_id = startup.login(deployparams);
				save_edit = (String)org.updateOrg(orgparams, client_id);
				toastValidationMessage("Organisation details edited successfully");
				
			}
				   
			if (setOrgDetails==true){
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Organisation "+getOrgName+" with details saved successfully") 
			        .setCancelable(false)
			        .setPositiveButton("Ok",
			                new DialogInterface.OnClickListener() {
			                    public void onClick(DialogInterface dialog, int id) {
			                    	 //To pass on the activity to the next page
			        		        Intent intent = new Intent(context, preferences.class);
			        		        startActivity(intent); 
			                    }
			                });
			        
			AlertDialog alert = builder.create();
			alert.show();
			
			}
		}
	
* * The below method bulids an alert dialog for displaying message.	
		
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
    		
* On back pressed, below method takes you back to the specified activity depending upon flag.

	.. code-block:: java
	
		public void onBackPressed() {
			if(editDetailsflag==false){
				Intent intent = new Intent(getApplicationContext(), createOrg.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}else {
				Intent intent = new Intent(getApplicationContext(), menu.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			};
		}	
		
	
		
Select existing organisation
++++++++++++++++++++++++++++

* On clicking ``Select existing organisation`` button from welcome page, application loads  ``select_org.xml``.

**File  res/layout/select_org.xml**

	.. code-block:: xml

		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			   android:layout_width="fill_parent"
			   android:layout_height="fill_parent"
			   android:background="@drawable/dark_gray_background"
			   android:orientation="vertical"
			   android:padding="20dp" >

			   <LinearLayout
			       android:layout_width="match_parent"
			       android:layout_height="wrap_content"
			       android:orientation="vertical" >

			       <LinearLayout
				   android:layout_width="match_parent"
				   android:layout_height="81dp" >

				   <TableLayout
				android:layout_width="fill_parent"
				android:layout_height="wrap_content">
		       
				 <TableRow
				     android:layout_width="wrap_content"
				     android:layout_height="wrap_content" >

				   <TextView
				       android:id="@+id/textView1"
				       android:layout_width="wrap_content"
				       android:layout_height="wrap_content"
				       android:layout_weight="0.5"
				       android:text="Organisation name"
				       android:textColor="#FFFFFF"
				       android:textSize="20dp" />

				   <Spinner
				       android:id="@+id/sGetOrgNames"
				       android:layout_width="0dip"
				       android:layout_height="wrap_content"
				       android:layout_weight="2.5"
				       android:prompt="@string/orgName_prompt" />

				   </TableRow>
				   </TableLayout>
			       </LinearLayout>
			   </LinearLayout>

			   <LinearLayout
			       android:layout_width="match_parent"
			       android:layout_height="wrap_content" >

			     <TableRow
				 android:layout_width="fill_parent"
				 android:layout_height="wrap_content" >

			       <TextView
				   android:id="@+id/textView2"
				   android:layout_width="wrap_content"
				   android:layout_height="wrap_content"
				   android:layout_weight="0.5"
				   android:text="Financial year         "
				   android:textColor="#FFFFFF"
				   android:textSize="20dp" />

			       <Spinner
				   android:id="@+id/sGetFinancialYear"
				   android:layout_width="0dip"
				   android:layout_height="wrap_content"
				   android:layout_weight="2.5"
				   android:prompt="@string/financialyear_prompt"/>
				</TableRow>
			   </LinearLayout>

			    
			    <LinearLayout
			android:layout_width="fill_parent"
			android:layout_height="wrap_content"
			android:orientation="horizontal"
			android:layout_weight="20">

			<Button
			    android:id="@+id/btnDeleteOrg"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_gravity="center_vertical"
			    android:layout_weight="0.90"
			    android:text="Delete organisation"
			    android:textSize="20dp" />
	
			<Button
			    android:id="@+id/bProceed"
			    android:layout_width="wrap_content"
			    android:layout_height="wrap_content"
			    android:layout_weight="0.96"
			    android:text="  Proceed >>"
			    android:textSize="20dp" 
			    android:layout_gravity="center_vertical"/>

		</LinearLayout>
		</LinearLayout>

.. image:: images/select_org.png
   :name: ABT main page
   :align: center

* The associated activity to above xml page is ``selectOrg.java``.

**File src/com/example/gkaakash/selectOrg.java**

* This page allows to select the already created or existing organisation from database
  with a particular financial year.

* Its activiy is explained below along with code. 

* The activity contains the essential and required import like

	.. code-block:: java

		import java.util.ArrayList;
		import java.util.List;
		import com.gkaakash.controller.Startup;
		import android.app.Activity;
		import android.app.AlertDialog;
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
		import android.widget.Spinner;
		import android.widget.SpinnerAdapter;

* The activity intializes all the essential parameters and variables.

	.. code-block:: java
	

		Object[] orgNameList;
		Spinner getOrgNames;
		private Spinner getFinancialyear;
		private Startup startup;
		private Button bProceed;
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

* onCreate method loads all the required methods at load time. 

	.. code-block:: java
	

		public void onCreate(Bundle savedInstanceState) {
		    	super.onCreate(savedInstanceState);
		    	setContentView(R.layout.select_org);
		    	
		    	MainActivity.no_dailog = true; //comment this line if running this app on emulator
		    	
		    	// set flag to true , if we are in existing organisation
		    	//existingOrgFlag="true";
		    	
		    	// call startup to get client connection 
		    	startup = new Startup();
		    	
		    	getOrgNames = (Spinner) findViewById(R.id.sGetOrgNames);
		    	getFinancialyear = (Spinner) findViewById(R.id.sGetFinancialYear);
		    	getOrgNames.setMinimumWidth(100);
		    	getFinancialyear.setMinimumWidth(250);
		    	
		    	bProceed = (Button) findViewById(R.id.bProceed);
		    	btnDeleteOrg = (Button) findViewById(R.id.btnDeleteOrg);
		    	
		    	getExistingOrgNames();
		    	addListenerOnItem();
		    	addListenerOnButton();
	        }// End of onCreate


* The below method loads all the ``organisation name`` from the database and populates 
  organization name ``spinner``.

	.. code-block:: java

		// getExistingOrgNames()
		void getExistingOrgNames(){
		
			//call getOrganisationNames method 
		    	orgNameList = startup.getOrgnisationName();
		    	System.out.println(orgNameList);
		    	List<String> list = new ArrayList<String>();
		    	
		    	for(Object st : orgNameList)
		    		list.add((String) st);
	
		    	// creating array adaptor to take list of existing organisation name
		    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		    			android.R.layout.simple_spinner_item, list);
		    	//set resource layout of spinner to that adaptor
		    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    //set adaptor with orglist in spinner
		    	getOrgNames.setAdapter(dataAdapter);
	    	
		}// End of getExistingOrgNames()


* The below method attaches onclick listener to two buttons ie. ``Proceed and Delete`` .

	.. code-block:: java

		//Attach a listener to the click event for the button
		private void addListenerOnButton(){
		
			final Context context = this;
			bProceed.setOnClickListener(new OnClickListener() {
		
				private Object[] deployparams;

				@Override
				public void onClick(android.view.View v) {
			
					if(orgNameList.length>0)
					{
						//parameters pass to core_engine xml_rpc functions
						deployparams=new Object[]{selectedOrgName,fromDate,toDate};
						//call method login from startup.java 
						client_id = startup.login(deployparams);
						//System.out.println("login "+ client_id);
						//To pass on the activity to the next page  
						Intent intent = new Intent(context,menu.class);
						startActivity(intent); 
					}else{
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setMessage("Please create organisation")
						.setCancelable(false)
						.setPositiveButton("Ok",
						        new DialogInterface.OnClickListener() {
						            public void onClick(DialogInterface dialog, int id) {
						            	//parameters pass to core_engine xml_rpc functions
						            	//To pass on the activity to the next page  
					    					Intent intent = new Intent(context,MainActivity.class);
					    	                startActivity(intent); 
						            }
						        });
						
					AlertDialog alert = builder.create();
					alert.show();
					}
				}
			});
			
			btnDeleteOrg.setOnClickListener(new OnClickListener() {
		
				private Object[] deleteprgparams;
				private Boolean deleted;

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Are you sure you want to permanetly delete "+selectedOrgName+" for financialyear "+fromDate+" To "+toDate+"?\n" +
						"if you will delete an item , It will be permanetly lost ")
					.setCancelable(false)
					
					.setPositiveButton("Delete",
					        new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog, int id) {
					            
					           	//parameters pass to core_engine xml_rpc functions
							deleteprgparams=new Object[]{selectedOrgName,fromDate,toDate};
							deleted = startup.deleteOrgnisationName(deleteprgparams);
							getExistingOrgNames();
						    	addListenerOnItem();
						    	addListenerOnButton();
						    	
							//Intent intent = new Intent(context,selectOrg.class);
							//startActivity(intent);
									
					            }
					        })
					        
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int id) {
					        dialog.cancel();
					    }
					});
				AlertDialog alert = builder.create();
				alert.show();
			       
				}
			});
		}
		
