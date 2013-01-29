Lets start with coding
======================

.. toctree::
   :numbered: 


android-xmlrpc client generation 
--------------------------------
* import the following packages.
	::
		
		import java.net.MalformedURLException;
		import java.net.URL;
		import org.xmlrpc.android.XMLRPCClient;
		import android.os.StrictMode;

* Create class CoreConnection

* xmlrpc-server(gkAakashCore) is running on 7081 port. To connect with that port and generate client, 
 we need to create an object of url and pass it to the XMLRPCClient. 
	::
		
		public class CoreConnection {
		private URL url;
		private XMLRPCClient client; 
	
		public CoreConnection()  { 
			try {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);  
				url = new URL("http://127.0.0.1:7081"); //uncomment for tablet
				//url = new URL("http://10.0.2.2:7081"); // uncomment for emulator  
				client = new XMLRPCClient(url);
				} catch (MalformedURLException e) {
			
				e.printStackTrace();
				}
			}
		}
 
* To access the object of ``XMLRPCClient`` through out the application, create getter method for XMLRPCClient.
	::
	
		public XMLRPCClient getClient() {
		return client;
		}
	
  

   
Connection to back-end(xmlrpc-server)
-------------------------------------
* To establish the connection with the xmlrpc-server, create class Startup.java in ABT/src/com.example.controller/. 

* import below given packages,
	:: 
	
		package com.gkaakash.controller;
		import org.xmlrpc.android.XMLRPCException;
		import com.gkaakash.coreconnection.CoreConnection;

* Create a constructor to create an object of class ``CoreConnection``.
	::
		
		public Startup() {
		conn = new CoreConnection();
		
		}

* ``conn.getClient`` will return the object of XMLRPCClient. To access XMLRPC methods from xmlrpc-server use ``call`` method of XMLRPCClient. It requires parameters such as class name, definition and parameters required for that particular definition.
	::
	
		conn.getClient().call("class_name.definition",parameters);
		
* For example, In ABT following method deploys a database instance for an organisation for a given financial year.
	::
	
		/***
		 * deploy method will call xmlrpc_Deploy from rpc_main.py
		 * params: list containing organisation name,from date,to-date,organisation type
		 * (Note: reference for these parameters will be explained further)
		 * result: will be a list which contains boolean value and client_id.
		 */
		public Integer deploy(Object[] params)
		{
		
			try {
			
				result = (Object[])conn.getClient().call("Deploy",params);
				client_id=(Integer) result[1];
				financialFromDate= (String) params[1];
				financialToDate = (String) params[2];
				
			} catch (XMLRPCException e) {
			
				e.printStackTrace();
			}
			return client_id;
		}

Android AndroidManifest.xml, src and res
----------------------------------------
you should be aware of a few directories and files in the Android project:

* AndroidManifest.xml


	* The most important part for a project to execute, lies in its manifest.xml file.
	* The manifest contains the permission for the internet connection.
	* The permission to read and write from the External Source, handling the sdcard.

		::
	
			<uses-sdk android:minSdkVersion="8" />
			<uses-permission android:name="android.permission.INTERNET" />
			<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

	* The application intilaization and icon and its label information.

		::
	
			<application
			     android:icon="@drawable/logo"
			     android:label="@string/app_name"
			     android:theme="@style/CustomTheme" >
			     <activity
				    android:name=".MainActivity"
				    android:label="@string/title_activity_main" >
				    <intent-filter>
					<action android:name="android.intent.action.MAIN" />
					<category android:name="android.intent.category.LAUNCHER" />
				    </intent-filter>
			     </activity>
			</application>

	* Declare the main activity and its other classes intilaization, label and name information in AndroidManifest.xml

* src/
	Directory for app's main source files. By default, it includes an Activity class that runs when app is launched using the app icon.

* res/
	Contains several sub-directories for app resources. Here are just a few:

	- drawable-hdpi/
        	Directory for drawable objects (such as bitmaps) that are designed for high-density (hdpi) screens. Other drawable directories contain assets designed for other screen densities.
 
	- layout/
        	Directory for files that define app's user interface.

	- values/
        	Directory for other various XML files that contain a collection of resources, such as string and color definitions.




layout and classes
------------------

* The project was entiltled as Aakash Business Tool (ABT).
* The package name : com.example.gkaakash
* In the android project mainly the editing part was in layout,Activity(java class) and in AndroidManifest.xml

+ **Designing the layout for the application**

Maintaining organisations
+++++++++++++++++++++++++

        - The first layout of the android APK is activity_main.xml.
        
        * Create this XML layout file in “res/layout/” folder.
        
        * It contains the features for the first screen that displays two buttons:
        	#. Create new organisation
        	#. Select existing organisation, besides the main logo of Aakash Business Tool(ABT).
        	
        * **File  res/layout/activity_main.xml**
        
        	::
        	
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
	   
	   

+ **The ABT activity**

	* The activity src/com/example/gkaakash/MainActivity.java was entiltled as first ABT Activity.

	* The activity contains the essential and required import like

		::
	
			import java.io.BufferedInputStream;
			import java.io.DataOutputStream;
			import java.io.File; 
			import java.io.FileInputStream;
			import java.io.FileOutputStream;
			import java.io.IOException;
			import java.io.InputStream;
			import java.io.OutputStream;
			import java.net.URL;
			import java.net.URLConnection;
			import java.util.zip.GZIPInputStream;
			import org.apache.tools.tar.TarEntry;
			import org.apache.tools.tar.TarInputStream;
			import com.gkaakash.controller.Startup;
			import android.app.Activity;
			import android.app.AlertDialog;
			import android.app.ProgressDialog;
			import android.content.Context;
			import android.content.DialogInterface;
			import android.content.Intent;
			import android.net.ConnectivityManager;
			import android.net.NetworkInfo;
			import android.os.AsyncTask;
			import android.os.Bundle;
			import android.view.LayoutInflater;
			import android.view.Menu;
			import android.view.MenuItem;
			import android.view.View;
			import android.view.ViewGroup;
			import android.view.WindowManager;
			import android.view.View.OnClickListener;
			import android.widget.Button;
			import android.widget.CheckBox;
			import android.widget.Spinner;
			import android.widget.Toast;

	* The activity intializes all the essential parameters and variables.

	* The ``Help`` page for the application is included in option menu.

	* OnCreate method calls all the required methods at laod time. 
    
		::

			public class MainActivity extends Activity {
			//Add a class property to hold a reference to the button
			    Button create_org;
			    Startup startup;
			    private View select_org;
			    private Object[] orgNameList;
			    Spinner getOrgNames;
			    final Context context = this;
			    static Boolean tabFlag = false;
			    static Boolean searchFlag=false;
			    static Boolean nameflag=false;
			    static boolean reportmenuflag;
			    static boolean editDetails=false;
			    static AlertDialog help_dialog;
			    private ProgressDialog mProgressDialog, progressBar;
			    String checkFlag;
			    int help_option_menu_flag = 0;
			    private int group1Id = 1;
			    int Help = Menu.FIRST;
			    String result;
			    boolean fstab_flag = true;
			    File checkImg;
			    File checkImgextsd;
			    File checkTar;
			    File help_flag;
			    static boolean no_dailog=false;
			     
			    
			    @Override
			    public boolean onCreateOptionsMenu(Menu menu) {
			    	// 'Help' menu to main page options menu
			    	menu.add(group1Id, Help, Help, "Help");
			    	return super.onCreateOptionsMenu(menu);	
			    }

			    @Override
			     public boolean onOptionsItemSelected(MenuItem item) {
			    	/*
			    	* when user clicks help menu on main page,
			    	* help_option_menu_flag is set to 1
			    	*/
			    	switch (item.getItemId()) {
				case 1: 
					//Toast.makeText(context, "help_flag_option is set to 1", Toast.LENGTH_SHORT).show();
				    help_option_menu_flag = 1;
				    //if running this app on emulator, comment the below line
				    help_popup();
				}
				return super.onOptionsItemSelected(item);
			    }//if running this app on emulator, comment the below line
				  
				 
			    @Override
			    public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				//Calling activity_main.xml which is first page of GNUKhata
				setContentView(R.layout.activity_main);
				//create object of Startup to access connection
				startup = new Startup();
				// call the getOrganisationName method from startup
				orgNameList = startup.getOrgnisationName(); // return lists of existing organisations
				//Request a reference to the button from the activity by calling “findViewById”
				//and assign the retrieved button to an instance variable
				create_org = (Button) findViewById(R.id.bcreateOrg);
				select_org =(Button) findViewById(R.id. bselectOrg);
				//Request a reference to the spinner from the activity by calling “findViewById”
				//and assign the retrieved spinner to an instance variable
				getOrgNames = (Spinner)findViewById(R.id.sGetOrgNames);
				//creating new method do event on button
				addListenerOnButton();
	
				// copy 'aakash.sh and 'preinstall.sh to their respective paths'
				File path = new File("/data/data/com.example.gkaakash/files/copyFilesFlag.txt");
				if(!path.exists()){
					//loadDataFromAsset();
				}
				else {
					//Toast.makeText(context, "not copying files from asset", Toast.LENGTH_SHORT).show();
					System.out.println("NOT copying files from asset");
				}
			       if(no_dailog==false){
			    	   //if running this app on emulator, comment the below line
			    	   help_popup(); 
			       }
			       else{
			    	   help_dialog.dismiss();
			       }
			       
			    }

	* The below method handles the click event of Create organization and Select existing organization.

		::

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

	* The below method fetches data(preinstall.sh and aakash.sh) from asset folder and copies them 
          to their appropriate locations.

	* It also writes a file naming ``copyFilesFlag.txt``, once both the files are copied to their locations.

		::

			private void loadDataFromAsset() {
			try {

			    // get input stream for text

			    InputStream is = getAssets().open("aakash.sh");
			    InputStream is2 = getAssets().open("preinstall.sh");

			    // check size

			    int size = is.available();
			    int size2 = is2.available();
			    // create buffer for IO

			    byte[] buffer = new byte[size];
			    byte[] buffer2 = new byte[size2];
		 
			    // get data to buffer

			    is.read(buffer);
			    is2.read(buffer2);

			    try {
				FileOutputStream output = openFileOutput("aakash.sh", Context.MODE_PRIVATE);
				output.write(buffer);
				output.flush();
				output.close();
				is.close();
			      
				OutputStream output2 = new FileOutputStream("/mnt/sdcard/preinstall.sh");
				output2.write(buffer2);
				output2.flush();
				output2.close();
				is2.close();
			      
			      
			    } catch (Exception e) {
				e.printStackTrace();
			    }
			  
			    String[] command = {"busybox mv /mnt/sdcard/preinstall.sh /system/bin/",
			    		"chown root.root /system/bin/preinstall.sh",
			    		"chmod 555 /system/bin/preinstall.sh"};
			    RunAsRoot(command);
			    FileOutputStream output = openFileOutput("copyFilesFlag.txt", Context.MODE_PRIVATE);
			    output.flush();
			    output.close();
			}

			catch (IOException ex) {

			    return;
			}

		      }

	* The below method gives root access for writing files.

		::

			private void RunAsRoot(String[] command2) {
			// run as a system command
			try {
			    Process process = Runtime.getRuntime().exec("su");
			    DataOutputStream os = new DataOutputStream(process.getOutputStream());
			    for (String tmpmd : command2){
				    os.writeBytes(tmpmd +"\n" );
			    }              
			    os.writeBytes("exit\n");
			    os.flush();
			  
			}catch (IOException e) {
				   e.printStackTrace();
			   }
		   	}

	* The below method checks the existance of the following paths, ie. ``/data/local/linux/etc/fstab``, 
          ``/mnt/sdcard/gkaakash.img`` , ``/mnt/sdcard/gkaakash.img.tar.gz``,
          ``/data/data/com.example.gkaakash/files/help_flag.txt``.

	* It provides download option. 

		::

			private void help_popup() {
		    	/**
			 * checks existance of:
			 * 1) /data/local/linux/etc/fstab
			 * 2) /mnt/sdcard/gkaakash.img
			 * 3) /mnt/sdcard/gkaakash.img.tar.gz
			 * 4) /data/data/com.example.gkaakash/files/help_flag.txt
			 * 
			 **/
			File fstab = new File("/data/local/gkaakash/etc/fstab");
			checkImg = new File("/mnt/sdcard/gkaakash.img");
			checkImgextsd = new File("/mnt/extsd/gkaakash.img");
			checkTar = new File("/mnt/sdcard/gkaakash.img.tar.gz");
			help_flag = new File("/data/data/com.example.gkaakash/files/help_flag.txt");
		
			if(fstab.exists()) {
				if ( help_option_menu_flag == 1 || !help_flag.exists()) {
					//Toast.makeText(context, "fstab exist , startapp()", Toast.LENGTH_SHORT).show();
					startApp();
				}
			}
			else if(checkImg.exists()) {
				if((help_option_menu_flag == 1 || !help_flag.exists()) && fstab.exists()) {
					//Toast.makeText(context, "img exists , startapp()", Toast.LENGTH_SHORT).show();
					startApp();
				}
				else {
					fstab_flag = false;	
					//Toast.makeText(context, "fstab false, reboot...", Toast.LENGTH_SHORT).show();
					reboot();	
				}
			}
			else if(checkImgextsd.exists()) {
				if((help_option_menu_flag == 1 || !help_flag.exists()) && fstab.exists()) {
					//Toast.makeText(context, "fstab exist***** , startapp()", Toast.LENGTH_SHORT).show();
					startApp();
				}
				else {
					fstab_flag = false;	
					//Toast.makeText(context, "fstab false, reboot...", Toast.LENGTH_SHORT).show();
					reboot();	
				}
			}
			else if(checkTar.exists()) {
				// extract
				// reboot
				spinner();
			} 
			else {
				// download
				// extract
				// reboot
				//Toast.makeText(context, "start downloading", Toast.LENGTH_SHORT).show();
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			    final View layout = inflater.inflate(R.layout.download_source,
				    (ViewGroup) findViewById(R.id.layout_root));

			    // Building DatepPcker dialog
			    AlertDialog.Builder builder = new AlertDialog.Builder(
				    MainActivity.this);        	        	
			    builder.setView(layout);
			    builder.setTitle("Notice");
			    builder.setCancelable(false);
			    Button btnNO = (Button) layout.findViewById(R.id.btnNo);
			   
			    btnNO.setOnClickListener(new OnClickListener() {
			    	public void onClick(View v) {
			    		finish();
				    	android.os.Process
				    	.killProcess(android.os.Process.myPid());
				}	
			    });	
			  
			    Button btnyes = (Button) layout.findViewById(R.id.btnyes);
			    btnyes.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				    startDownload();
				    mProgressDialog = new ProgressDialog(context);
				    mProgressDialog.setMessage("Downloading file..");
				    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				    mProgressDialog.setCancelable(false);
				    mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
				      
				        public void onClick(DialogInterface dialog, int which) {
				            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				            builder.setMessage("Are you sure you want cancel downloading?")
				                    .setCancelable(false)
				                    .setPositiveButton("Yes",
				                            new DialogInterface.OnClickListener() {
				                                public void onClick(DialogInterface dialog, int id) {
				                                    dialog.dismiss();
				                                    help_dialog.dismiss();
				                                    String[] command = {"rm /mnt/sdcard/gkaakash.img.tar.gz"};
				                                    RunAsRoot(command);  
				                                    finish();
				                                    android.os.Process.killProcess(android.os.Process.myPid());
				                                }
				                            })
				                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
				                        public void onClick(DialogInterface dialog, int id) {
				                            mProgressDialog.show();
				                        }
				                    });
				            AlertDialog alert = builder.create();
				            alert.show();
				          
				        }
				    });
				    mProgressDialog.show();
				}
			    });
			    
			    help_dialog = builder.create();
			    help_dialog.show();
			    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			    // customizing the width and location of the dialog on screen
			    lp.copyFrom(help_dialog.getWindow().getAttributes());
			    lp.width = 500;
			    help_dialog.getWindow().setAttributes(lp);
			    
				}
			}

	* The below method calls ``isInternetOn`` method, if it returns ``true`` it invokes the 
          class DownloadFileAsync which asynchronously starts downloading.

	* If it returns ``false``, then it throws a message.

		::

			private void startDownload() {
			/*if(isInternetOn()) {
		        // INTERNET IS AVAILABLE, DO STUFF..
		            Toast.makeText(context, "Connected to network", Toast.LENGTH_SHORT).show();
		        }else{
		        // NO INTERNET AVAILABLE, DO STUFF..
		            Toast.makeText(context, "Network disconnected", Toast.LENGTH_SHORT).show();
		            //rebootFlag = 1;
		            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		            builder.setMessage("No Connection Found, please check your network setting!")
		                    .setCancelable(false)
		                    .setPositiveButton("OK",
		                            new DialogInterface.OnClickListener() {
		                                public void onClick(DialogInterface dialog, int id) {
		                                    finish();
		                                    android.os.Process
		                                            .killProcess(android.os.Process.myPid());
		                                }
		                            });
		            AlertDialog alert = builder.create();
		            alert.show();
		          
		        }  */
			/**
			 * below link is within IITB
			 **/
	    	
		       String url = "http://10.102.152.27/installer/gkaakash.img.tar.gz";
		       new DownloadFileAsync().execute(url);
		    }    

	* The below method checks for internet availability and returns a boolean result.

		::
		
			private final boolean isInternetOn() {
		    	// check internet connection via wifi   
		    	 	ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		    	 	//NetworkInfo mwifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		    	 	//mwifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			    if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
			    connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
			    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
			    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
			    	//Toast.makeText(this, connectionType + ” connected”, Toast.LENGTH_SHORT).show();
			    	return true;
			    } 
			    else if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  
			    		connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {
			    		//System.out.println(“Not Connected”);
			    		return false;
			    	}
			    	return false;
			    }

	* The below method creates a progress bar for extraction and invokes ``Extract_TAR_GZ_FILE()`` 
          class for extraction.   

		:: 
			
			 private void spinner() {
		    	// will start spinner first and then extraction
		    	//Toast.makeText(context, "we are in extraction spinner",Toast.LENGTH_SHORT).show();
		    	// start spinner to show extraction progress
		    	progressBar = new ProgressDialog(context);
			progressBar.setCancelable(false);
			progressBar.setMessage("Extracting files, please wait...");
			progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressBar.show();
		
			// start actual extraction
			new Extract_TAR_GZ_FILE().execute();
		    }

	* The below method starts the application and shows the initial help dialog for the application 
          with a ``check box``,if checked it will never the show the dialog again.

	* If check box is ``checked`` it writes a file naming ``help_flag.txt`` in the internel storage.

	* If check box is again ``unchecked`` it deletes the file from the respective location.

		::

			// START-APP
			private void startApp() {
				/**
				 * start the application and show initial help pop-up
				 **/ 

			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			final View layout = inflater.inflate(R.layout.help_popup,
				(ViewGroup) findViewById(R.id.layout_root));

			// builder
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setView(layout);
			builder.setTitle("Help");

			CheckBox cbHelp = (CheckBox)layout.findViewById(R.id.cbHelp);

	
			if(help_option_menu_flag == 1) { 
				if (help_flag.exists()) {
					//Toast.makeText(context, "both exist", Toast.LENGTH_SHORT).show();
			    	cbHelp.setChecked(true);
				}
			}
			else {
				//Toast.makeText(context, "only one exists", Toast.LENGTH_SHORT).show();
				cbHelp.setChecked(false);
			}


			cbHelp.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

			//for setting the visibility of EditText:'etProject' depending upon the condition
			    if (((CheckBox) v).isChecked()) {
			    	Toast.makeText(context, "TRUE", Toast.LENGTH_SHORT).show();
				checkFlag = "true";
			    }
			    else {
			    	Toast.makeText(context, "FALSE", Toast.LENGTH_SHORT).show();
				checkFlag = "false";
			    }
			}
			});
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int which) {
				// 
				if(("true").equals(checkFlag)) {
				    try {
				    	FileOutputStream output = openFileOutput("help_flag.txt", Context.MODE_PRIVATE);
					output.flush();
					output.close();
			
				    }
				    catch (IOException e) {
					e.printStackTrace();
				    }
				}
				else if(("false").equals(checkFlag)){
				    String[] command = {"busybox rm -r /data/data/com.example.gkaakash/files/help_flag.txt"};
				    RunAsRoot(command);
					//Toast.makeText(context, "help_flag deleted", Toast.LENGTH_SHORT).show();
				}
			    }
			  
			});

			help_dialog = builder.create();
			help_dialog.show();
			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			// customizing the width and location of the dialog on screen
			lp.copyFrom(help_dialog.getWindow().getAttributes());
			lp.width = 700;
			help_dialog.getWindow().setAttributes(lp);
			}
			// START-APP END

	* The below class carries out the complete downloading process in number of steps in asynchronous manner.

		::
	
			// DOWNLOAD
			class DownloadFileAsync extends AsyncTask<String, String, String> {
			/**
			 * download tar.gz from URL and write in '/mnt/sdcard'
			 **/
			@Override        	
			public void onPreExecute() {
			    super.onPreExecute();
			}

			public String doInBackground(String... aurl) {
			    int count;

			    try {
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(
					"/mnt/sdcard/gkaakash.img.tar.gz");

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
				    total += count;
				    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
				    output.write(data, 0, count);
				}
				output.flush();
				output.close();
				input.close();
			    } catch (Exception e) {
			    }
			    return null;

			}

			public void onProgressUpdate(String... progress) {
			    mProgressDialog.setProgress(Integer.parseInt(progress[0]));
			}

			public void onPostExecute(String unused) {
				mProgressDialog.dismiss();
				help_dialog.dismiss();

				if (checkTar.exists()){
					spinner();
				}
			}

	* It deletes files such as aakash.sh, help_flag and copyFilesFlag.txt once the application is uninstalled 
          from their respective locations.

		:: 
		
			//delete internal files during un-installation 
			public boolean deleteFile (String name){
			    name = "aakash.sh";
			    name = "help_flag";
			    name = "copyFilesFlag.txt";
			    return false;
		        }

	* The below class carries out the complete extraction process in back ground.

		::

			// EXTRACT CLASS
			public class Extract_TAR_GZ_FILE extends AsyncTask<String, String, String>{
			/**
			 * extract an image asynchronouslIy to '/mnt/sdcard'
			 **/
				@Override
			public void onPreExecute() {
					 
			    super.onPreExecute();
			}

			public InputStream getInputStream(String tarFileName) throws Exception{
			System.out.println("finnaly in");
			  if(tarFileName.substring(tarFileName.lastIndexOf(".") + 1, 
					  tarFileName.lastIndexOf(".") + 3).equalsIgnoreCase("gz")){
			     System.out.println("Creating an GZIPInputStream for the file");
			     return new GZIPInputStream(new FileInputStream(new File(tarFileName)));

			  }else{
			     System.out.println("Creating an InputStream for the file");
			     return new FileInputStream(new File(tarFileName));
			  }
			}
			 
			public void untar(InputStream in, String untarDir) throws IOException {
			    
			  System.out.println("Reading TarInputStream... ");
			  System.out.println(in);
			  TarInputStream tin = new TarInputStream(in);
			  System.out.println("Reading TarInputStream... 1");
			  TarEntry tarEntry = tin.getNextEntry();
			  if(new File(untarDir).exists()){
			      while (tarEntry != null){
			    	  
				 File destPath = new File(untarDir + File.separatorChar + tarEntry.getName());
				 System.out.println("Processing " + destPath.getAbsoluteFile());
				 if(!tarEntry.isDirectory()){
							 	 
				    FileOutputStream fout = new FileOutputStream(destPath);
				    tin.copyEntryContents(fout);
				    fout.close();
				 }else{
				    destPath.mkdir();
				 }
				 tarEntry = tin.getNextEntry();
			      }
			      tin.close();
			  }else{
			     System.out.println("That destination directory doesn't exist! " + untarDir);
			  }
			     
			 }
				@Override
				protected String doInBackground(String... params) {
	
					try {      
	
				String strSourceFile = "/mnt/sdcard/gkaakash.img.tar.gz";
				String strDest = "/mnt/sdcard/";
				System.out.println(strSourceFile);
			      //  Toast.makeText(context, "aa"+strSourceFile, Toast.LENGTH_SHORT).show();
				InputStream in = getInputStream(strSourceFile);
				untar(in, strDest);
				    
			    }catch(Exception e) {
			    
				e.printStackTrace();      
				System.out.println(e.getMessage());
			    }

					return null;
				}

				@Override
				protected void onPostExecute(String result) {
					// 
					super.onPostExecute(result);
					progressBar.dismiss();
					if (checkImg.exists()){ 
					reboot();
					}
					else {
						AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Failed to download gkaakash.img, exiting the application")
						.setCancelable(false)
						.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
							    public void onClick(DialogInterface dialog, int id) {
								finish();
								android.os.Process
									.killProcess(android.os.Process.myPid());
							    }
							});
			
			
					AlertDialog alert = builder.create();
					alert.show();
					}
				}
			}
			// EXTRACT CLASS END

	* The below method helps to reboot the device so that the filesystem is mounted.

		::

			// REBOOT
			public void reboot() {
				// reboot the device so that the filesystem is mounted
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				if (fstab_flag == true){
				builder.setMessage("To apply changes, please reboot")
				    .setCancelable(false)
				    .setPositiveButton("Reboot",
					    new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int id) {
					        
					        	String[] rebootCommand = {"reboot"};
					            RunAsRoot(rebootCommand);
					                         
					        }
				    })        	
				     .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int id) {
				    	finish();
				        android.os.Process.killProcess(android.os.Process.myPid());
				    }
				});
				}
				else {
					builder.setMessage("Filesystem not mounted, device requires a reboot")
			    .setCancelable(false)
			    .setPositiveButton("Reboot",
				    new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int id) {
				            
				        	String[] rebootCommand = {"reboot"};
				            RunAsRoot(rebootCommand);
		 							                     	                    		
				        }
				   
				    })
				.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							finish();
				        android.os.Process
				                .killProcess(android.os.Process.myPid());
				    }
				});
			
				}
		
		
			    AlertDialog alert1 = builder.create();
			    alert1.show();
			}
			// REBOOT END


	* The below method provides Yes/No option before exiting the application. 

	* If the user clicks on ``Yes`` it kills the process, otherwise resumes the process.

		::

			public void onBackPressed() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to exit?")
			.setCancelable(false)
			.setPositiveButton("Yes",
			new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			    //Intent intent = new Intent(Intent.ACTION_MAIN);
			    //intent.addCategory(Intent.CATEGORY_HOME);
			    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    //startActivity(intent);
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			
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


* **File src/com/example/gkaakash/createOrg.java**

	* To get started with the application, there should be atleast one organization.

	* This page allow the user to create organizaion with a particular financial year under certain organization type 
          such as,NGO and Profit Making.

	* Its activiy is explained below along with code.

	* The activity contains the essential and required import like 

		::

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

	* onCreate method loads all the required methods at load time.

		::	
	
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

	* The below method sets standard financial ``From`` and ``To`` date, when the page gets loaded.

	* Once the ``From`` date is seted, ``To`` date gets automatically updated by ``12`` months and minus ``1`` day.

		::	
     
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

		::

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
						 + (mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))) + "-" 
						 + year;
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



Transaction management
++++++++++++++++++++++

	- For recording/editing voucher we have two tabs ie. Create voucher and Search/edit voucher.
	
	* These two tabs are included in a path ``src/com.example.gkaakash/transaction_tab.java`` .

	+ Its activiy is explained below along with code. 

	* **File src/com/example/gkaakash/transaction_tab.java**

	+ The activity contains the essential and required import like

		::

			import android.app.AlertDialog;
			import android.app.TabActivity;
			import android.content.Context;
			import android.content.DialogInterface;
			import android.content.Intent;
			import android.graphics.Color;
			import android.os.Bundle;
			import android.view.View;
			import android.view.Window;
			import android.view.WindowManager;
			import android.view.View.OnClickListener;
			import android.widget.Button;
			import android.widget.EditText;
			import android.widget.TabHost;
			import android.widget.TabHost.OnTabChangeListener;
			import android.widget.TabHost.TabSpec;
			import android.widget.TextView;
			import android.widget.Toast;

	* The activity intializes all the essential parameters and variables.

	* onCreate method creates two Tabspec and include them in Tabhost.

	* It sets Create voucher as bydefault tab.

		::

			public class transaction_tab extends TabActivity {
	
			static TextView tab1 = null;
			static TextView tab2 = null;
			AlertDialog dialog;
			final Context context = this;
			Boolean nameflag;
		   	String name;
		    	Boolean edittabflag=false;
		   	static TabHost tabHost;
		   	static String tabname;
		   	EditText etRefNumber;
			  public void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
				setContentView(R.layout.tab);
				//tab name flag
				nameflag=MainActivity.nameflag;
				name=SearchVoucher.name;
				//Toast.makeText(context,"name"+name,Toast.LENGTH_SHORT).show();
			       
				edittabflag=createVoucher.edittabflag;
			      //customizing title bar
				getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.voucher_title);
				final TextView label = (TextView) findViewById(R.id.tvVoucherTitle);
				String vouchertypeflag = voucherMenu.vouchertypeflag;
				label.setText("Menu >> Transaction >> " + vouchertypeflag);
				final Button home = (Button) findViewById(R.id.btnhome);
				home.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(context, menu.class);
							// To pass on the value to the next page
							startActivity(intent);
							}
				});
			
				tabHost = getTabHost();
				//creating TabSpec for create voucher
				TabSpec createspec = tabHost.newTabSpec("tab1");
				tab1 = new TextView(this);
				//setting properties in textView
				tab1.setGravity(android.view.Gravity.CENTER);
				tab1.setTextSize(18.0f);
				tab1.setHeight(50);
				tab1.setTextColor(Color.WHITE);
			
				if(nameflag==true){//setting tab name while editing and cloning
					tab1.setText(name); 
				}else {//setting tab name while creating account
					tab1.setText("Create voucher");
					tabname=(String) tab1.getText();
					} 
				createspec.setIndicator(tab1);//assigning TextView to tab Indicator
				Intent create = new Intent(this, createVoucher.class);
				create.putExtra("flag", vouchertypeflag);
				createspec.setContent(create);
				tabHost.addTab(createspec);  // Adding create tab
			
				//creating TabSpec for edit voucher
				TabSpec editspec = tabHost.newTabSpec("tab2");
				tab2 = new TextView(this);
				//setting properties in textView
				tab2.setGravity(android.view.Gravity.CENTER);
				tab2.setTextSize(18.0f);
				tab2.setHeight(50);
				tab2.setTextColor(Color.WHITE);
				tab2.setText("Search voucher");
				editspec.setIndicator(tab2);//assigning TextView to tab Indicator
				Intent edit = new Intent(this, SearchVoucher.class);
				edit.putExtra("flag",vouchertypeflag);
				editspec.setContent(edit);
				tabHost.addTab(editspec); // Adding edit tab
				tabHost.setCurrentTab(0);//setting tab1 on load
			 }
	
			}

* **File src/com/example/gkaakash/createVoucher.java**

	*  Create voucher layout includes fields such as Account type, Account name, Amount, Voucher date, 
           Project name,Reference No., and Narration(filling naration is not mandatory).

	* Values are sent to database using Save option.

	* All the fields are cleared using Reset option.

	* Its activity is explained below along with code.

	* The activity contains the essential and required import like

		::
		
			import java.math.RoundingMode;
			import java.text.DecimalFormat;
			import java.text.SimpleDateFormat;
			import java.util.ArrayList;
			import java.util.Calendar;
			import java.util.Date;
			import java.util.HashMap;
			import java.util.List;
			import android.R.color;
			import com.gkaakash.controller.*;
			import android.app.Activity;
			import android.app.AlertDialog;
			import android.app.DatePickerDialog;
			import android.app.Dialog;
			import android.content.Context;
			import android.content.DialogInterface;
			import android.content.Intent;
			import android.graphics.Color;
			import android.os.Bundle;
			import android.text.InputType;
			import android.text.method.KeyListener;
			import android.text.style.ClickableSpan;
			import android.view.KeyEvent;
			import android.view.View;
			import android.view.View.OnClickListener;
			import android.view.View.OnFocusChangeListener;
			import android.view.ViewGroup;
			import android.view.ViewGroup.LayoutParams;
			import android.widget.AdapterView;
			import android.widget.AdapterView.OnItemSelectedListener;
			import android.widget.ArrayAdapter;
			import android.widget.Button;
			import android.widget.DatePicker;
			import android.widget.EditText;
			import android.widget.ListView;
			import android.widget.SimpleAdapter;
			import android.widget.Spinner;
			import android.widget.TabHost;
			import android.widget.TableLayout;
			import android.widget.TableRow;
			import android.widget.TextView;
			import android.widget.AdapterView.OnItemClickListener;
			import android.widget.Toast;
			import android.view.View.OnKeyListener;
			import android.view.inputmethod.InputMethodManager;
	
	* The activity intializes all the essential parameters and variables.

	* onCreate method loads all the methods required.

		::

			public class createVoucher extends Activity {
			TableLayout list;
			int rowsSoFar = 0, tableRowCount;
			String amount, financialFromDate, financialToDate, drcramount, vouchertypeflag;
			AlertDialog dialog;
			final Context context = this;
			TextView voucherDate, tvTotalDebit, tvTotalCredit, projectName;
			final List<String> dr_cr=new ArrayList<String>();
			ListView voucher_date,projetct_name;;
			final Calendar c = Calendar.getInstance();
			static int day, month, year;
			static final int VOUCHER_DATE_DIALOG_ID = 1;
			private SimpleAdapter dateAdapter,projectAdapter; 
			static Integer client_id;
			private Transaction transaction; 
			private Organisation organisation;
			static Object[] voucherAccounts;
			static Integer setVoucher; 
			static Integer editVoucher;
			static ArrayAdapter<String> dataAdapter;
			protected String selDrCr;
			Spinner account, actionButton, DrCr, sp1;
			TableRow newRow;
			ArrayList<ArrayList> paramsMaster;
			float totalDr, totalCr;
			static String vDate, vproject; 
			DecimalFormat mFormat;
			EditText firstRowamount, etnarration, et;
			static EditText etRefNumber;
			private Object diffbal;
			Float drcrAmountFirstRow, drcrAmount, amountdrcr;
			boolean addRowFlag = true;
			List<String> accnames=new ArrayList<String>();
			List<String>  DrAccountlist, CrAccountlist;
			static Boolean searchFlag;
			ArrayList otherdetailsrow;
			ArrayAdapter<String> da1 ;
			String proj,searchdate;
			static ArrayList<String> accdetails;
			static ArrayList<ArrayList<String>> accdetailsList;
			String Fsecond_spinner,Ssecond_spinner,Sacctype,Facctype;
			static int FaccnamePosition,SaccnamePosition,SacctypePosition,FacctypePosition;
			String vouchercode;
			static Boolean cloneflag;
			boolean nameflag;
			static boolean edittabflag;
			String name;
	
	
			    @Override 
			    public void onCreate(Bundle savedInstanceState) {
			       	super.onCreate(savedInstanceState);
			       	setContentView(R.layout.create_voucher);
			       
			       	transaction = new Transaction();
			       	organisation = new Organisation();
			       	client_id= Startup.getClient_id();
			    	vouchertypeflag =  voucherMenu.vouchertypeflag;
			       
			     	try {
			     		searchFlag=MainActivity.searchFlag;
			       	
			     		cloneflag=SearchVoucher.cloneflag;
			     		//Toast.makeText(context, "abbbbccc"+searchFlag, Toast.LENGTH_SHORT).show();
			     		//Toast.makeText(context, "clone"+cloneflag, Toast.LENGTH_SHORT).show();
			     		etRefNumber = (EditText)findViewById(R.id.etRefNumber);
			      	
			     		name = SearchVoucher.name;
			     		//Toast.makeText(context,"namecre"+name,Toast.LENGTH_SHORT).show();
			     		// after click om edit voucher Reff Edit text non-editable
			     		if(searchFlag==true&&cloneflag==false){
			     			etRefNumber.setEnabled(false);
			     		}else {
			     			etRefNumber.setEnabled(true); 
			     		}
			       	
			     		etnarration = (EditText)findViewById(R.id.etVoucherNarration);
			     		account = (Spinner) findViewById(R.id.getAccountByRule);
			     		firstRowamount = (EditText) findViewById(R.id.etDrCrAmount);
			     		DrCr = (Spinner) findViewById(R.id.sDrCr);
			     		list = (TableLayout) findViewById( R.id.Vouchertable );
			     		if(searchFlag==false){
			     			//for setting voucher reference number
			     			etRefNumber =  (EditText)findViewById(R.id.etRefNumber);
			     			String reff_no = transaction.getLastReferenceNumber(new Object[]{vouchertypeflag},client_id);
			     			etRefNumber.setText(reff_no.toString());
			     		}
			
			     		//for edit Details
			     		if(searchFlag==true){
			     			//System.err.println("cumning form serach voucher"+SearchVoucher.value);
				       		//list coming from search voucher
				       		ArrayList<String> abc = SearchVoucher.value;
				       		vouchercode=abc.get(0);
				       		Object[] params = new Object[]{vouchercode};
				       		
				       		Object[] VoucherMaster = (Object[]) transaction.getVoucherMaster(params,client_id);
				       		Object[] VoucherDetails = (Object[]) transaction.getVoucherDetails(params,client_id);
				       		
				       		otherdetailsrow = new ArrayList();
				       		for(Object row1 : VoucherMaster){
				       			Object a=(Object)row1;
				       			otherdetailsrow.add(a.toString());//getting vouchermaster details
				       		}
			       	
				       		String refno=(String) otherdetailsrow.get(0);
				       		//String date=(String) row.get(1);
				       		String narration=(String)otherdetailsrow.get(3);
				       		proj=(String)otherdetailsrow.get(4);
				       		searchdate=(String) otherdetailsrow.get(1);
				       		etnarration.setText(narration);
				       		etRefNumber.setText(refno);
			       		
				       		projetct_name =  (ListView)findViewById(R.id.voucher_list4);
				       		projetct_name.setTextFilterEnabled(true);
				       		projetct_name.setCacheColorHint(color.transparent);
				       		setProject();
				
				       		accdetailsList = new ArrayList<ArrayList<String>>();
				       		for(Object row2 : VoucherDetails){
				       			Object[] a2=(Object[])row2;
				       			accdetails = new ArrayList<String>();
				       			for(int i=0;i<a2.length;i++){
				       				accdetails.add((String) a2[i].toString());//getting voucherdetails
				       			}
				       			accdetailsList.add(accdetails);
				       		}
				       		//for filling 1st row amount
				       		firstRowamount.setText(accdetailsList.get(0).get(2));
				       		account.setMinimumWidth(283);
			     		}
			       
		       	
			     		//two digit date format for dd and mm
			     		mFormat= new DecimalFormat("00");
			     		mFormat.setRoundingMode(RoundingMode.DOWN);
			     		list = (TableLayout) findViewById( R.id.Vouchertable );
			       
			     		account.setMinimumWidth(283);
			    	
			     		//add second row and set first & second row account names in spinner
			     		setFirstAndSecondRow();
		       		
			     		//for setting voucher date
			     		voucher_date =  (ListView)findViewById(R.id.voucher_list);
			     		voucher_date.setTextFilterEnabled(true);
			     		voucher_date.setCacheColorHint(color.transparent);
			     		setVoucherDate();
			      
			     		// for setting project 
			     		projetct_name =  (ListView)findViewById(R.id.voucher_list4);
			     		projetct_name.setTextFilterEnabled(true);
			     		projetct_name.setCacheColorHint(color.transparent);
			     		setProject();
				
			     	} catch (Exception ex) {
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
				 //add all onclick events in this method
			     	OnClickListener();
			
				//on dr/cr item selected from dropdown...
				OnDrCrItemSelectedListener();
			
				//move foucs from amount to reference number edittext
				OnAmountFocusChangeListener(); 
		   	 }

	* This methods manages all the activity at the time of focus change(from one edittext to another).

		::

			private void OnAmountFocusChangeListener() {
		    	/*
		    	 * onfocuschange of amount edittext move focus to reference number
		    	 */
		    	tableRowCount = list.getChildCount();
				for(int i=0;i<(tableRowCount);i++){
					View row = list.getChildAt(i);
					//amount edittext
					final EditText e = (EditText)((ViewGroup) row).getChildAt(5);
			
					e.setOnFocusChangeListener(new OnFocusChangeListener() {
				
						@Override
						public void onFocusChange(View v, boolean hasFocus) {
					
							etRefNumber = (EditText)findViewById(R.id.etRefNumber);
							e.setNextFocusDownId(etRefNumber.getId());
						}
					});
				}
			 }

	* This methods checks whether the amount in the amount field is tallied or not, if not it throws an error message.

	* If amount field is left unfilled it automatially updates that field with the value "0.00".

		::

			public void testAmountTally() {
		    	/*
		    	 * this method calculate toatalDr and totalCr
		    	 */
		    	totalDr = 0;
		    	totalCr = 0;
		    	//System.out.println("m in..........");
				//selected dr/cr and amount of the first row
		    	
				String Dr_Cr = DrCr.getSelectedItem().toString();
		
				//System.out.println("sasa:"+Dr_Cr);
		
				String drcramountFirstRow = firstRowamount.getText().toString();
				if(drcramountFirstRow.length()<1)
			{
					drcramountFirstRow="0.00";
			}
				drcrAmountFirstRow = Float.parseFloat(drcramountFirstRow);
		
				if("Dr".equals(Dr_Cr)){
					totalDr = totalDr + drcrAmountFirstRow;
				}
				else if("Cr".equals(Dr_Cr)){
					totalCr = totalCr + drcrAmountFirstRow;
				}
		
				//selected dr/cr and amount of the remaining rows
		
				tableRowCount = list.getChildCount();
		
				for(int i=0;i<(tableRowCount);i++){
					View row = list.getChildAt(i);
					//dr cr spinner
					Spinner s = (Spinner)((ViewGroup) row).getChildAt(0);
					String drcr = s.getSelectedItem().toString();
					//System.out.println("ssdsdSSS:"+drcr);
			
					//amount edittext
					EditText e = (EditText)((ViewGroup) row).getChildAt(5);
					drcramount = e.getText().toString();
			
					if(drcramount.length()<1)
			    {
				drcramount="0.00";
			    }
					drcrAmount = Float.parseFloat(drcramount);
			
					if("Dr".equals(drcr)){
						totalDr = totalDr + drcrAmount;
					}
					else if("Cr".equals(drcr)){
						totalCr = totalCr + drcrAmount;
					}
				}
		
			}

	* This method sets the second row of the table.

	* It fills the dropdown of second and first row of table with respective values according to the account type.

		::
		
			private void setFirstAndSecondRow() {
			/*this onload function takes the account name list 
			 * from voucherMenu.java depending upon getAccountByRule
			 * sets first row account name spinner
			 * add the second row and set the account name in spinner
			 */
			if("Contra".equals(vouchertypeflag) || "Journal".equals(vouchertypeflag)){
				accnames = voucherMenu.Accountlist;
		
				//set first row account name spinner
		
		    	//set resource layout of spinner to that adapter
				if(searchFlag==false){
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accnames);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					account.setAdapter(dataAdapter);
					//add second row
					addButton();
			
					dr_cr.clear();
					dr_cr.add("Dr");
			    	dr_cr.add("Cr");
			    	da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
			  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp1.setAdapter(da1);
				sp1.setSelection(1);
			
					//set adaptor with account name list in second row spinner
			    	actionButton.setAdapter(dataAdapter);
			    	
				}else {//for setting second row for editing
					dr_cr.clear();
					//for setting 1st row's 2nd spinner
					Fsecond_spinner = accdetailsList.get(0).get(0);
					//setting adapter
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accnames);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					FaccnamePosition = dataAdapter.getPosition(Fsecond_spinner);
					account.setAdapter(dataAdapter);
					account.setSelection(FaccnamePosition);
			
					//add second row
					addButton();
			
			    	dr_cr.add("Dr");
			    	dr_cr.add("Cr");
			    	
			    	//for setting 1st spinner of 1st and 2nd row 
			    	Sacctype=accdetailsList.get(1).get(1);
			    	Facctype=accdetailsList.get(0).get(1);
			    	da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
			  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			     	SacctypePosition=da1.getPosition(Sacctype);
			     	FacctypePosition=da1.getPosition(Facctype);
			     	sp1.setAdapter(da1);
				sp1.setSelection(SacctypePosition);//setting spinner selection acc to obtained value
				DrCr.setAdapter(da1);
				DrCr.setSelection(FacctypePosition);//setting spinner selection acc to obtained value
			
		       		//for filling 2nd row amount
				et.setText(accdetailsList.get(1).get(2));
			
					//for setting 2nd row's 2nd spinner
			    	Ssecond_spinner = accdetailsList.get(1).get(0);
			     	SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);
			     	actionButton.setAdapter(dataAdapter);
			    	actionButton.setSelection(SaccnamePosition);//setting spinner selection acc to obtained value
			    	
				tableRowCount = list.getChildCount();
			
					//if row count of 2nd table(list) is more than 1 code bellow will be executed
			
					if(accdetailsList.size()>2){
						for(int i=2;i<accdetailsList.size();i++){
							addButton();
							et.setText(accdetailsList.get(i).get(2));
				
							Ssecond_spinner = accdetailsList.get(i).get(0);
							SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);
							actionButton.setAdapter(dataAdapter);
							actionButton.setSelection(SaccnamePosition);
				    	
				    	
							Sacctype=accdetailsList.get(i).get(1);
							da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
				  	   		da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				  	   		SacctypePosition=da1.getPosition(Sacctype);
				  	   		sp1.setAdapter(da1);
				  	   		sp1.setSelection(SacctypePosition);
				
						}	
					}
			
				}
			}
			else{ 
		
				DrAccountlist = voucherMenu.DrAccountlist;
				CrAccountlist = voucherMenu.CrAccountlist;
				if(searchFlag==false){
					//set first row 
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DrAccountlist);
			    	//set resource layout of spinner to that adapter
			    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					account.setAdapter(dataAdapter);
					//add second row
					addButton();
					dr_cr.clear();
					dr_cr.add("Dr");
					dr_cr.add("Cr");
					ArrayAdapter<String> da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
					da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					sp1.setAdapter(da1);
					sp1.setSelection(1);
			
					//set adaptor with account name list in second row spinner
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CrAccountlist);
					//set resource layout of spinner to that adapter
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					//set adaptor with account name list in spinner
					actionButton.setAdapter(dataAdapter); 
				}else {
					//add second row
					addButton();
					dr_cr.clear();
			    	dr_cr.add("Dr");
			    	dr_cr.add("Cr");
			    	Sacctype=accdetailsList.get(1).get(1);
			    
			    	Facctype=accdetailsList.get(0).get(1);
			    	da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
			  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			     	SacctypePosition=da1.getPosition(Sacctype);
			     	FacctypePosition=da1.getPosition(Facctype);
			     	sp1.setAdapter(da1);
				sp1.setSelection(SacctypePosition);
				DrCr.setAdapter(da1);
				DrCr.setSelection(FacctypePosition);
			
				if("Dr".equals(Facctype)){//if acctype is DR
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DrAccountlist);
				}else {//if acctype is CR
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CrAccountlist);
					}
				Fsecond_spinner = accdetailsList.get(0).get(0);
					dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					FaccnamePosition = dataAdapter.getPosition(Fsecond_spinner);
					account.setAdapter(dataAdapter);
					account.setSelection(FaccnamePosition);
			
			
				et.setText(accdetailsList.get(1).get(2));
			
			
				if("Dr".equals(Sacctype)){
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DrAccountlist);
				}else {
					dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CrAccountlist);
					}
			
			    	Ssecond_spinner = accdetailsList.get(1).get(0);
			    	System.out.println("sdss:"+Ssecond_spinner);
			     	SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);
			     	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			     	actionButton.setAdapter(dataAdapter);
			    	actionButton.setSelection(SaccnamePosition);
			  	   	
				tableRowCount = list.getChildCount();
					if(accdetailsList.size()>2){
						for(int i=2;i<accdetailsList.size();i++){
						addButton();
						et.setText(accdetailsList.get(i).get(2));
				
			
						if("Dr".equals(Sacctype)){
						dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, DrAccountlist);
					}else {
						dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CrAccountlist);
						}
				
				
						Ssecond_spinner = accdetailsList.get(i).get(0);
						//System.out.println("ashagdSec:"+Ssecond_spinner+"");
				     	SaccnamePosition = dataAdapter.getPosition(Ssecond_spinner);
				     	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				     	actionButton.setAdapter(dataAdapter);
				    	actionButton.setSelection(SaccnamePosition);
				    	dr_cr.clear();
				    	dr_cr.add("Dr");
				    	dr_cr.add("Cr");
				    	Sacctype=accdetailsList.get(i).get(1);
				    	da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
				  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				     	SacctypePosition=da1.getPosition(Sacctype);
				     	sp1.setAdapter(da1);
					sp1.setSelection(SacctypePosition);
				
						}	
					}
			
				}	
			}
		 }

	* The below method fills the drop down of account names when respective account type is selected.

		::

			private void OnDrCrItemSelectedListener() {
			/*
			 * to set account names in dropdown when Dr/Cr changed
			 */
			//for first row
	       		 DrCr.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
				// TODO Auto-generated method stub
				selDrCr = parent.getItemAtPosition(position).toString();
				if(selDrCr != null){
					Object[] params = new Object[]{selDrCr};
					getAccountsByRule(params);
					if(searchFlag==false){
						account.setAdapter(dataAdapter);
					}
			
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// ignore this method!!! :)
			}
			});
			//for remaining rows
			sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					// TODO Auto-generated method stub
					String a = parent.getItemAtPosition(position).toString();
					if(a != null){
						if(searchFlag==false){
							Object[] params = new Object[]{a};
							getAccountsByRule(params);
							actionButton.setAdapter(dataAdapter);
						}
				
					}
			
				} 

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
			
				}
			});
		 }

	* The below method populates drop down of account name by rule.

	* For example if transaction type is contra, this method filters the account names that comes under contra
      	  and fill them in the drop down.  

		::

			private void getAccountsByRule(Object[] DrCrFlag) {
			/*
			 * get account name list depending upon voucher type and 
			 * dr/cr flag (standard accounting rule)
			 */
			if("Contra".equals(vouchertypeflag)){
				voucherAccounts = (Object[]) transaction.getContraAccounts(client_id);
			}
			else if("Journal".equals(vouchertypeflag)){
				voucherAccounts = (Object[]) transaction.getJournalAccounts(client_id);
			}
			else if("Receipt".equals(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getReceivableAccounts(DrCrFlag,client_id);
			}
			else if("Payment".equals(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getPaymentAccounts(DrCrFlag,client_id);
			}
			else if("Debit Note".equalsIgnoreCase(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getDebitNoteAccounts(DrCrFlag,client_id);
			}
			else if("Credit Note".equalsIgnoreCase(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getCreditNoteAccounts(DrCrFlag,client_id);
			}
			else if("Sales".equals(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getSalesAccounts(DrCrFlag,client_id);
			}
			else if("Purchase".equals(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getPurchaseAccounts(DrCrFlag,client_id);
			}
			else if("Sales Return".equalsIgnoreCase(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getSalesReturnAccounts(DrCrFlag,client_id);
			}
			else if("Purchase Return".equalsIgnoreCase(vouchertypeflag)){
			
				voucherAccounts = (Object[]) transaction.getPurchaseReturnAccounts(DrCrFlag,client_id);
			}
			List<String> Accountlist = new ArrayList<String>();
			for(Object ac : voucherAccounts)
			{	
				Accountlist.add((String) ac);
			}	
			dataAdapter = new ArrayAdapter<String>(this,
	    		android.R.layout.simple_spinner_item, Accountlist);
		    	//set resource layout of spinner to that adapter
		    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    
			}

	* The below method manages the activity when plus button is clicked,by adding one row.

	* It takes values filled in the fields and calls setTransaction for saving transation.  

	* After saving transaction it resets all the fields.

		::

			private void OnClickListener() { 
			/*
			 * on click method for add, save and reset button
			 * 1. add: Every time the "+" button is clicked, add a new row to the table
			 * 2. save: takes all necessary field values and calls transaction.setTransaction
			 * 			for adding transaction and resets all fileds after adding transaction
			 * 3. reset: resets all fields
			 */
		
			/*==============================================================================
	    		 * Every time the "+" button is clicked, add a new row to the table 
	    		 */
			Button addButton = (Button) findViewById( R.id.add );
	       		 addButton.setOnClickListener( new OnClickListener() {
				public void onClick(View view) { 
					testAmountTally();
					if(totalDr == totalCr){
						String message = "Debit and Credit amount is tally";
						toastValidationMessage(message);
					}
					else if (drcrAmountFirstRow <= 0 || drcrAmount <= 0) {
						String message = "No row can be added,Please fill the existing row";
						toastValidationMessage(message);
					}
					else{
						for(int i=0;i<(tableRowCount);i++){
		                View row = list.getChildAt(i);
		               
		                //amount edittext
		                EditText e = (EditText)((ViewGroup) row).getChildAt(5);
		                drcramount = e.getText().toString();
		                if(drcramount.length()<1)
		                {
		                    drcramount="0.00";
		                }
		                amountdrcr = Float.parseFloat(drcramount);
		                
		                if(amountdrcr<=0){
		                	addRowFlag = false;
		                    break;
		                }
		                else{
		                	addRowFlag = true;
		                }
		            }
					
				if(addRowFlag == true){
					//add new row
					addButton();
					ArrayAdapter<String> da1 = new ArrayAdapter<String>(
					createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
			  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp1.setAdapter(da1);
			      
				//set totalDr and totalCr in textview
				String tvTotalDr = Float.toString(totalDr);
				//tvTotalDebit.setText("Total Debit: "+tvTotalDr+"0");
				
				String tvTotalCr = Float.toString(totalCr);
				//tvTotalCredit.setText("Total Credit: "+tvTotalCr+"0");
				
				DrCr.setOnItemSelectedListener(new OnItemSelectedListener() {
					
						@Override
						public void onItemSelected(AdapterView<?> parent,
						 View v, int position,long id) {
							// TODO Auto-generated method stub
							selDrCr = parent.getItemAtPosition(position).toString();
							if(selDrCr != null){
								Object[] params = new Object[]{selDrCr};
								getAccountsByRule(params);
									account.setAdapter(dataAdapter);
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// ignore this method!!! :)
						}
					});
				
				sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent, 
						View v, int position,long id) {
							// TODO Auto-generated method stub
							String a = parent.getItemAtPosition(position).toString();
							if(a != null){
									Object[] params = new Object[]{a};
									getAccountsByRule(params);
									actionButton.setAdapter(dataAdapter);
							}
						
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						
						}
					});
				
				
			      //set Dr/Cr selected in dropdown according to the condition and set amount in new row
					if(totalDr > totalCr){
						diffbal = totalDr-totalCr;
						et.setText(String.format("%.2f",diffbal ));
						//set 'Cr' selected in Dr/Cr dropdown
						sp1.setSelection(1);
					}
					else{
						diffbal = totalCr-totalDr;
						et.setText(String.format("%.2f",diffbal ));
						//set 'Dr' selected in Dr/Cr dropdown
						sp1.setSelection(0);
					}
				
				}
				else{
					String message = "No row can be added,Please fill the existing row";
					toastValidationMessage(message);
				}
			
			   }
			}

	
		 	});  
		
			/*==============================================================================
		    	 * save transaction
		    	 */
		
		    	Button btnSaveVoucher = (Button) findViewById( R.id.btnSaveVoucher );
		    	btnSaveVoucher.setOnClickListener(new OnClickListener() {

				private String ac;
				private boolean  flag = false;

				@Override
				public void onClick(View v) {
					testAmountTally();
			
					String refNumber = etRefNumber.getText().toString();
			
					if(totalDr == totalCr && !"".equals(refNumber)){
						if(totalDr == 0){
							String message = "Please enter amount";
							toastValidationMessage(message);
						}
						else{
							//main list
							paramsMaster = new ArrayList<ArrayList>(); 
							 ArrayList<String> accNames = new ArrayList();
					
							//first row
							List<String> paramFirstRow = new ArrayList<String>();
							String fistRowDrCr = DrCr.getSelectedItem().toString();
					
							String fistRowAccountName = account.getSelectedItem().toString();
							accNames.add(fistRowAccountName);
					
							EditText firstRowamount = (EditText) findViewById(R.id.etDrCrAmount);
							String firstRowAmount = firstRowamount.getText().toString();
					
							if(searchFlag==false){//for creating account
								paramFirstRow.add(fistRowDrCr);
								paramFirstRow.add(fistRowAccountName);
								paramFirstRow.add(firstRowAmount);
								paramsMaster.add((ArrayList<String>) paramFirstRow);	
							}else if (cloneflag==false) {//for editing account
								if("Dr".equals(fistRowDrCr)){
									paramFirstRow.add(fistRowAccountName);
									paramFirstRow.add(firstRowAmount);
									paramFirstRow.add("0");
									paramsMaster.add((ArrayList<String>) paramFirstRow);
								}else {
									paramFirstRow.add(fistRowAccountName);
									paramFirstRow.add("0");
									paramFirstRow.add(firstRowAmount);
									paramsMaster.add((ArrayList<String>) paramFirstRow);
								}
							}
							else if (cloneflag==true) {//for cloning account
								paramFirstRow.add(fistRowDrCr);
								paramFirstRow.add(fistRowAccountName);
								paramFirstRow.add(firstRowAmount);
								paramsMaster.add((ArrayList<String>) paramFirstRow);
							}
					
				
							//remaining rows
							int tableRowCount = list.getChildCount();
					
							for(int i=0;i<(tableRowCount);i++){
								List<String> paramRow = new ArrayList<String>();
								  
								View row = list.getChildAt(i);
								//drcr flag
								Spinner rowDrCr = (Spinner)((ViewGroup) row).getChildAt(0);
								String drcrFlag = rowDrCr.getSelectedItem().toString();
						
								//account name
								Spinner rowAccountName = (Spinner)((ViewGroup) row).getChildAt(2);
								String accountName = rowAccountName.getSelectedItem().toString();
								accNames.add(accountName);
						
								//amount edittext
								EditText etamount = (EditText)((ViewGroup) row).getChildAt(5);
								String rowAmount = etamount.getText().toString();
								if(searchFlag==false){//for editing account
									paramRow.add(drcrFlag);
									paramRow.add(accountName);
									paramRow.add(rowAmount);
									paramsMaster.add((ArrayList<String>) paramRow);	
								}else if (cloneflag==false) {//for editing account
									if("Dr".equals(drcrFlag)){
										paramRow.add(accountName);
										paramRow.add(rowAmount);
										paramRow.add("0");
										paramsMaster.add((ArrayList<String>) paramRow);	
									}else {
										paramRow.add(accountName);
										paramRow.add("0");
										paramRow.add(rowAmount);
										paramsMaster.add((ArrayList<String>) paramRow);	
									}
								}else if (cloneflag==true) {//for clonning account
									paramRow.add(drcrFlag);
									paramRow.add(accountName);
									paramRow.add(rowAmount);
									paramsMaster.add((ArrayList<String>) paramRow);
								}
							}
					
							for (int i = 0; i < accNames.size(); i++) {
								ac = accNames.get(i);
								for (int j = 0; j < accNames.size(); j++)
								{
									if (i!=j)
									{
										if(ac.equals(accNames.get(j)))
										{
											flag = true;
											break;
										}
								
									}
									else
									{
										flag = false;
									}
									if(flag == true){
										break;
									}
								}
								if(flag == true){
									break;
								}
							}
							if(flag == false)
							{
								//other voucher details...
								etnarration = (EditText)findViewById(R.id.etVoucherNarration);
								String narration = etnarration.getText().toString();
						
								if("".equals(narration)){
									narration = ""; //need to find solution for null
								}
								if(searchFlag==false){//for saving accounts details
									Object[] params_master = 
									new Object[]{refNumber,vDate,vouchertypeflag,vproject,narration};
									setVoucher = (Integer) 
									transaction.setTransaction(params_master,paramsMaster,client_id);
							
									//for satisfying reset condition
									searchFlag=false;
									edittabflag=false;
								}else if (cloneflag==false) {//for saving edited account details
							
									Object[] params_master = 
									new Object[]{vouchercode,vDate,vproject,narration};
									transaction.editVoucher(params_master,paramsMaster,client_id);
									//for satisfying reset condition
									searchFlag=false;
									edittabflag=true;
									MainActivity.nameflag=false;
									transaction_tab.tabHost.setCurrentTab(1);//for changing the tab
									String tabname1 = transaction_tab.tabname;
									transaction_tab.tab1.setText(tabname1);//for changing tab name
								}
								else if (cloneflag==true) {//for saving cloned details 
									Object[] params_master = 
									new Object[]{refNumber,vDate,vouchertypeflag,vproject,narration};
									setVoucher = (Integer) 
									transaction.setTransaction(params_master,paramsMaster,client_id);
							
									//for not getting reseted
									searchFlag=true;
									//this flag is seted for changing tab name on tab change 
									edittabflag=false;
								}
						
								AlertDialog.Builder builder = new AlertDialog.Builder(context);
								if(searchFlag==false && edittabflag==false ){
					 				builder.setMessage("Transaction added successfully");
								}else if (cloneflag==false && edittabflag==true) {
									builder.setMessage("Transaction edited successfully");	
								}else if (cloneflag==true) {
									builder.setMessage("Transaction cloned successfully");
								}
						AlertDialog alert = builder.create();
						alert.setCancelable(true);
						alert.setCanceledOnTouchOutside(true);
						alert.show();
						
					      //reset all fields
						if(searchFlag==false||cloneflag==false){
							
									//etRefNumber.setText("");
							 etRefNumber =  (EditText)findViewById(R.id.etRefNumber);
				  	     	       	String reff_no = transaction.getLastReferenceNumber
									(new Object[]{vouchertypeflag},client_id);
				  	     	       	etRefNumber.setText(reff_no.toString());
									etnarration.setText("");
							
									TextView tvproject = 
									(TextView)projetct_name.findViewById(R.id.tvSubItem1);
									tvproject.setText("No Project");
							
									setVoucherDate(); 
							
									DrCr.setSelection(0); 
									account.setSelection(0);
									firstRowamount.setText("0.00         ");
							
									list.removeAllViews();
									setFirstAndSecondRow();	
						}
						
							}
							else{
								String message = "Account name can not be repeated,
										 please select another account name";
								toastValidationMessage(message);
								}
						}
				
					}
					else if(totalDr != totalCr){
						String message = "Debit and Credit amount is not tally";
						toastValidationMessage(message);
					}
					else if("".equals(refNumber)){
						String message = "Please enter voucher reference number";
						toastValidationMessage(message);
					}
				}

		
				}); 
		    	
		    	/*==============================================================================
		    	 * reset all fields
		    	 */
		    	Button btnResetVoucher = (Button) findViewById( R.id.btnResetVoucher );
		    	btnResetVoucher.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
				
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setMessage("Are you sure, you want reset all fields? ")
						.setCancelable(false)
						.setPositiveButton("Yes",
						        new DialogInterface.OnClickListener() {
						            public void onClick(DialogInterface dialog, int id) {
						            	
						            	name="Create voucher";
						            	// Toast.makeText(context,"namecre"+name,Toast.LENGTH_SHORT).show();
						            	 if("Create voucher".equals(name)){
							     	        	etRefNumber.setEnabled(true);
							     	        }
						            	etRefNumber =  (EditText)findViewById(R.id.etRefNumber);
					      	     	       	String reff_no = transaction.getLastReferenceNumber
										(new Object[]{vouchertypeflag},client_id);
					      	     	       	etRefNumber.setText(reff_no.toString());
										etnarration = (EditText)
										findViewById(R.id.etVoucherNarration);
										etnarration.setText("");
										
										
										TextView tvproject = (TextView)
										projetct_name.findViewById(R.id.tvSubItem1);
										tvproject.setText("No Project");
										
										DrCr = (Spinner) findViewById(R.id.sDrCr);
										DrCr.setSelection(0); 
										
										account = (Spinner) findViewById(R.id.getAccountByRule);
										account.setSelection(0);
										
										firstRowamount = (EditText) 
										findViewById(R.id.etDrCrAmount);
										firstRowamount.setText("0.00");
										searchFlag=false;
										cloneflag=true;
										setVoucherDate();
										 
										// add a keylistener to keep track user input
										
										list.removeAllViews();
										setFirstAndSecondRow();
										String tabname1 = transaction_tab.tabname;
										transaction_tab.tab1.setText(tabname1);
										
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
				});
			}

	* This method populates Project names in the project name drop down. 

	* It sets ``No Project`` as project, bydefault. 

	* If any other Project name is selected from the dropdown, it updates the bydefault Project name.

		::

			private void setProject() {
			/*
			 * set 'No Project' in the subtitle on load and when item is clicked,
			 * populates the list of project names present in database
			 * when item(project name) is selected,
			 * sets selected name in the subtitle
			 */
	    	
			String[] abc = new String[] {"rowid", "col_1"};
			int[] pqr = new int[] { R.id.tvRowTitle1, R.id.tvSubItem1};
			if(searchFlag==true){//this code will be executed while cloning,editing
				List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("rowid", "" + "Select project");
				map.put("col_1", "" + proj);
				fillMaps.add(map);
				projectAdapter = new SimpleAdapter(this, fillMaps, R.layout.child_row1, abc, pqr);
				projetct_name.setAdapter(projectAdapter);
				
			}else {//this code will be executed while creating account
				List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("rowid", "" + "Select project");
				map.put("col_1", "" + "No Project");
				fillMaps.add(map);
				projectAdapter = new SimpleAdapter(this, fillMaps, R.layout.child_row1, abc, pqr);
				projetct_name.setAdapter(projectAdapter);
			
			}
			vproject = "No Project";
		
			projetct_name.setOnItemClickListener(new OnItemClickListener(){

				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					projetct_name.setCacheColorHint(color.transparent);
					if(position == 0){
						projectName = (TextView)view.findViewById(R.id.tvSubItem1);
					
						//call the getAllProjects method to get all projects
						Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
						// create new array list of type String to add gropunames
						List<String> projectnamelist = new ArrayList<String>();
						projectnamelist.add("No Project");
						for(Object pn : projectnames)
						{	
							Object[] p = (Object[]) pn;
							//p[0] is project code & p[1] is projectname
							projectnamelist.add((String) p[1]); 
						}	
					
					
						/*
						 * 'builder.setItems' takes Charsequence Array as a parameter, 
						we have to convert List<Address> to List<String> and 
						then use list.toarray() 
						*/
	 
						final CharSequence[] allProjectNames = projectnamelist.toArray(new String[0]);
					
					
						//creating a dialog box for popup
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					//setting title
					builder.setTitle("Select project");
					//adding allProjectNames
					builder.setItems(allProjectNames, new DialogInterface.OnClickListener() {
						
							@Override
							public void onClick(DialogInterface dialog, int pos) {
								// set project name in project field

									projectName.setText(allProjectNames[pos]);
									vproject = allProjectNames[pos].toString();
							
							
							}
						});
					//building a complete dialog
						dialog=builder.create();
						dialog.show();
					
					}
				}
			
			});
			}

	* This method sets financial date as Voucher date,if no transaction is recorded before. 

	* If any transaction is previously recorded,it sets the Voucher date of the
          previous transaction of the respective Voucher type as current Voucher date.

	* If the date is changed, it updates the bydefault date or previous date with the new date.  

		::

			private void setVoucherDate() {
			/*
			 * set the financial year from date in the subtitle and when date is changed by user,
			 * sets date in the subtitle
			 */
			String fromday,frommonth,fromyear,LastFromDate;
			if(searchFlag==true){
				//will get executed while clonning and editing
				financialFromDate =searchdate;
				String dateParts[] = financialFromDate.split("-");
				fromday  = dateParts[0];
			   	frommonth = dateParts[1];
			   	fromyear = dateParts[2];
			}else {
				//will get executed while creating account 
				financialFromDate =Startup.getfinancialFromDate();
				LastFromDate = transaction.getLastReferenceDate(
				new Object[]{financialFromDate,vouchertypeflag}, client_id);
				String dateParts[] = LastFromDate.split("-");
				fromday  = dateParts[0];
			   	frommonth = dateParts[1];
			   	fromyear = dateParts[2];
			}
		   	financialToDate = Startup.getFinancialToDate();
		   	
		   	
	    		year = Integer.parseInt(fromyear);
			month = Integer.parseInt(frommonth);
			day = Integer.parseInt(fromday);
		
			String[] abc = new String[] {"rowid", "col_1"};
			int[] pqr = new int[] { R.id.tvRowTitle1, R.id.tvSubItem1};
	
			List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("rowid", "" + "Voucher Date");
			map.put("col_1", "" + 
			mFormat.format(Double.valueOf(day))+"-"+mFormat.format(Double.valueOf(month))+"-"+year);
			fillMaps.add(map);
		
			dateAdapter = new SimpleAdapter(this, fillMaps, R.layout.child_row1, abc, pqr);
			voucher_date.setAdapter(dateAdapter);
		
			vDate = mFormat.format(Double.valueOf(day))+"-"+mFormat.format(Double.valueOf(month))+"-"+year;
			voucher_date.setOnItemClickListener(new OnItemClickListener() {

			
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					voucher_date.setCacheColorHint(color.transparent);
					
					if(position == 0)
					{
						showDialog(VOUCHER_DATE_DIALOG_ID);
					}	
				}
			});
			}
		    //build dialog
		    @Override
			protected Dialog onCreateDialog(int id) {
				switch (id) {
				case VOUCHER_DATE_DIALOG_ID:
					// set 'from date' date picker as current date
					   return new DatePickerDialog(this, fromdatePickerListener, 
					         year, month-1,day);
				}
				return null;
			}
	
			private DatePickerDialog.OnDateSetListener fromdatePickerListener 
		    = new DatePickerDialog.OnDateSetListener() {

				// when dialog box is closed, below method will be called.
				public void onDateSet(DatePicker view, int selectedYear,
					int selectedMonth, int selectedDay) {
				year = selectedYear;
				month = selectedMonth;
				day = selectedDay;
		
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date1 = sdf.parse(financialFromDate);
				Date date2 = sdf.parse(financialToDate);
				Date date3 = sdf.parse(mFormat.format(Double.valueOf(day))+"-"
				+mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))+"-"
				+mFormat.format(Double.valueOf(year)));
				Calendar cal1 = Calendar.getInstance(); //financial from date
				Calendar cal2 = Calendar.getInstance(); //financial to date
				Calendar cal3 = Calendar.getInstance(); //voucher date
			
				//24-10-2012 23-10-2013 23-10-2012

			
				cal1.setTime(date1);
				cal2.setTime(date2);
				cal3.setTime(date3);
				/*
				System.out.println(financialFromDate+financialToDate+mFormat.format(Double.valueOf(day))+"-"
		   		+mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1))+"-"
				+mFormat.format(Double.valueOf(year)));
				*/
			
				if((cal3.after(cal1) && cal3.before(cal2)) || cal3.equals(cal1) || cal3.equals(cal2)){
					voucherDate =  (TextView)findViewById(R.id.tvSubItem1); 
				
					// set selected date into textview
					voucherDate.setText(new StringBuilder()
					.append(mFormat.format(Double.valueOf(day))).append("-")
					.append(mFormat.format(Double.valueOf
					(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1)))
					.append("-").append(year));	
					vDate = mFormat.format(Double.valueOf(day))+"-"
							+(mFormat.format(Double.valueOf
							(Integer.parseInt((mFormat.format(Double.valueOf(month))))+ 1)))+"-"
							+year;
				}
				else{
					String message = "Please enter proper voucher date";
						toastValidationMessage(message);
				}
			
				} catch (Exception e) {
					// TODO: handle exception
				}
		
				}
			};

	* The below class provides funtionality to remove row.

	* The method inside the class ie.addButton() allow to add row if required.

		::

			/***
			* Gets all the information necessary to delete itself from the constructor.
			* Deletes itself when the button is pressed.
			*/
			private static class RowRemover implements OnClickListener {
			private TableLayout list;
			private TableRow rowToBeRemoved;

			/***
			 * @param list	The list that the button belongs to
			 * @param row	The row that the button belongs to
			 */
			public RowRemover( TableLayout list, TableRow row ) {
				this.list = list;
				this.rowToBeRemoved = row;
			}

			public void onClick( View view ) {
				int tableRowCount = list.getChildCount();
			    if (tableRowCount == 1){
			    }else{
				list.removeView( rowToBeRemoved );
			    }
			}
			}

			public void addButton() {
			/*
			 * this method add the transaction row to the table
			 */
			newRow = new TableRow( list.getContext() );
			newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			//newRow.addView(child, width, height)

			sp1 = new Spinner( newRow.getContext() );


			TextView tv = new TextView(newRow.getContext());
			tv.setText("        Account Name");
			tv.setTextSize(14); //for emulator 14
			tv.setTextColor(Color.WHITE);

			actionButton = new Spinner( newRow.getContext() );
			actionButton.setMinimumWidth(259);//for emulator keep 283

			OnDrCrItemSelectedListener();

			TextView tv1 = new TextView(newRow.getContext());
			tv1.setText( "        Amount" );
			tv1.setTextSize(14); //****
			tv1.setTextColor(Color.WHITE);

			TextView tv2 = new TextView(newRow.getContext());
			tv2.setText(R.string.Rs);
			tv2.setTextColor(Color.WHITE);
			tv2.setTextSize(19);
			tv2.setPadding(10, 0, 5, 0);


			//tv1.setWidth(100);
			et = new EditText(newRow.getContext());
			et.setText( "0.00" );
			et.setWidth(159); //for emulator 80
			et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);




			//actionButton.setText( "Action: " + ++rowsSoFar );
			Button removeSelfButton = new Button( newRow.getContext() );
			removeSelfButton.setText( "   -    " ); //for tablet ***** add  space

			// pass on all the information necessary for deletion
			removeSelfButton.setOnClickListener( new RowRemover( list, newRow ));

			newRow.addView(sp1);
			newRow.addView(tv);
			newRow.addView(actionButton,259,50);
			newRow.addView(tv1);
			newRow.addView(tv2);
			newRow.addView(et,159,50);
			newRow.addView( removeSelfButton );
			list.addView(newRow);
			OnAmountFocusChangeListener();
			}

	* The below method bulids an alert dialog for displaying message.

		::

			public void toastValidationMessage(String message) {
			/*
			* call this method for alert messages
			* input: a message Strig to be display on alert
			*/
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

	* The below method manages the activity when android back button is pressed.

		::

			public void onBackPressed() {
		    	 MainActivity.searchFlag=false;
		    	 MainActivity.nameflag=false;
		    	
		    	Intent intent = new Intent(getApplicationContext(), voucherMenu.class);
				 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 startActivity(intent);
			}
		        }

* **File src/com/example/gkaakash/SearchVoucher**.java

	* The activity contains the essential and required import like

		::

			import java.math.RoundingMode;
			import java.text.DecimalFormat;
			import java.text.SimpleDateFormat;
			import java.util.ArrayList;
			import java.util.Arrays;
			import java.util.Calendar;
			import java.util.Date;
			import java.util.regex.Matcher;
			import java.util.regex.Pattern;
			import com.example.gkaakash.R.layout;
			import com.gkaakash.controller.Startup;
			import com.gkaakash.controller.Transaction;
			import android.app.Activity;
			import android.app.AlertDialog;
			import android.app.Dialog;
			import android.app.ActionBar.LayoutParams;
			import android.content.Context;
			import android.content.DialogInterface;
			import android.content.Intent;
			import android.graphics.Color;
			import android.os.Bundle;
			import android.text.Layout;
			import android.text.SpannableString;
			import android.view.Gravity;
			import android.view.LayoutInflater;
			import android.view.View;
			import android.view.ViewGroup;
			import android.view.WindowManager;
			import android.view.View.OnClickListener;
			import android.widget.AdapterView;
			import android.widget.AdapterView.OnItemSelectedListener;
			import android.widget.Button;
			import android.widget.DatePicker;
			import android.widget.EditText;
			import android.widget.LinearLayout;
			import android.widget.Spinner;
			import android.widget.TabHost;
			import android.widget.TableLayout;
			import android.widget.TableRow;
			import android.widget.TextView;
			import android.widget.Toast;

	* The activity intializes all the essential parameters and variables.

	* OnCreate method calls all required methods at load time.

		::

			public class SearchVoucher extends Activity {
			int textlength=0;
			Context context = SearchVoucher.this;
			AlertDialog dialog;
			DecimalFormat mFormat;
			private Transaction transaction;
			static Integer client_id;
			static ArrayList<ArrayList<String>> searchedVoucherGrid;
			static ArrayList<String> searchedVoucherList;
			TableLayout vouchertable;
			TableRow tr;
			TextView label;
			static String financialFromDate;
			static String financialToDate;
			int rowid=0;
			static String vouchertypeflag;
			static ArrayList<String> value;
			static String name;
			static Boolean cloneflag=false;
			String vouchercode;
			LinearLayout.LayoutParams params;
			static int searchVoucherBy = 2; // by date
			protected Boolean deleteVoucher;
			static String searchByNarration;
			static String searchByRefNumber;
			DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
		  	String colValue;
		     
			  @Override
			    public void onCreate(Bundle savedInstanceState) {
			    	super.onCreate(savedInstanceState);
				setContentView(R.layout.search_voucher);
			       
				client_id = Startup.getClient_id();
				transaction = new Transaction();
		
				//for two digit format date for dd and mm
					mFormat= new DecimalFormat("00");
					mFormat.setRoundingMode(RoundingMode.DOWN);
		
					financialFromDate =Startup.getfinancialFromDate(); 
					financialToDate = Startup.getFinancialToDate();
		
					TextView tvVFromdate = (TextView) findViewById( R.id.tvVFromdate );
				    TextView tvVTodate = (TextView) findViewById( R.id.tvVTodate );
				      
				    tvVFromdate.setText("Financial from date: " +financialFromDate);
				    tvVTodate.setText("Financial to date: " +financialToDate);
				    
					vouchertypeflag = voucherMenu.vouchertypeflag;
				    
					try {
						 setOnSearchButtonClick();
				
						 Object[] params = new Object[]{2,"",financialFromDate,financialToDate,""};
						 getallvouchers(params);
			
						 
					} catch (Exception e) {
			
						AlertDialog.Builder builder = new AlertDialog.Builder(SearchVoucher.this);
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
		
			       
		
				}

	* The below method manages all the activity when Search button is clicked.

	* We can search voucher in three ways:Search by Reference No., Date ,Narration.

		::

			private void setOnSearchButtonClick() {
		
		
			Button btnSearchVoucher = (Button)findViewById(R.id.btnSearchVoucher);
			btnSearchVoucher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.search_voucher_by, (ViewGroup) findViewById(R.id.timeInterval));
			//Building DatepPcker dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setView(layout);
			builder.setTitle("Search voucher by,");

			 	   	
		   	String dateParts[] = financialFromDate.split("-");
		   	String setfromday  = dateParts[0];
		   	String setfrommonth = dateParts[1];
		   	String setfromyear = dateParts[2];
		   	
		   	
		   	String dateParts1[] = financialToDate.split("-");
		   	String settoday  = dateParts1[0];
		   	String settomonth = dateParts1[1];
		   	String settoyear = dateParts1[2];

		   	DatePicker SearchVoucherFromdate = (DatePicker) layout.findViewById(R.id.dpSearchVoucherFromdate);
		   	SearchVoucherFromdate.init(Integer.parseInt(setfromyear),(Integer.parseInt(setfrommonth)-1),Integer.parseInt(setfromday), null);
		   	
		   	DatePicker SearchVoucherTodate = (DatePicker) layout.findViewById(R.id.dpSearchVoucherTodate);
		   	SearchVoucherTodate.init(Integer.parseInt(settoyear),(Integer.parseInt(settomonth)-1),Integer.parseInt(settoday), null);
		   	
			final EditText etVoucherCode = (EditText)layout.findViewById(R.id.searchByVCode);
			etVoucherCode.setVisibility(EditText.GONE);

			final EditText etNarration = (EditText)layout.findViewById(R.id.searchByNarration);
			etNarration.setVisibility(EditText.GONE);

			final LinearLayout timeInterval = (LinearLayout)layout.findViewById(R.id.timeInterval);
			timeInterval.setVisibility(LinearLayout.GONE);

			final Spinner searchBy = (Spinner) layout.findViewById(R.id.sSearchVoucherBy);
			searchBy.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
					if(position == 0){
						etNarration.setVisibility(EditText.GONE);
						timeInterval.setVisibility(LinearLayout.GONE);
						etVoucherCode.setVisibility(EditText.VISIBLE);
					}
					if(position == 1){
						etVoucherCode.setVisibility(EditText.GONE);
						etNarration.setVisibility(EditText.GONE);
						timeInterval.setVisibility(LinearLayout.VISIBLE);
					}
					if(position == 2){
						etVoucherCode.setVisibility(EditText.GONE);
						timeInterval.setVisibility(LinearLayout.GONE);
						etNarration.setVisibility(EditText.VISIBLE);
					}
		
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
		
				}
			});
			 
			builder.setPositiveButton("View",new  DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					int pos = searchBy.getSelectedItemPosition();
				   	
				   	if(pos == 0){
				   		searchByRefNumber = etVoucherCode.getText().toString();
				   		if(searchByRefNumber.length() < 1){
						toastValidationMessage("Please enter voucher reference number");
				   		}
				   		else{
				   			searchVoucherBy = 1; //by reference no
				   			Object[] params = new Object[]{1,searchByRefNumber,financialFromDate,financialToDate,""};
				   			getallvouchers(params);
				   			
				   		}
				   	}
				   	else if(pos == 1){
				   		final   DatePicker dpSearchVoucherFromdate = (DatePicker) dialog.findViewById(R.id.dpSearchVoucherFromdate);
					   	int SearchVoucherFromDay = dpSearchVoucherFromdate.getDayOfMonth();
					   	int SearchVoucherFromMonth = dpSearchVoucherFromdate.getMonth();
					   	int SearchVoucherFromYear = dpSearchVoucherFromdate.getYear();
					   	
					   	String SearchVoucherFromdate = mFormat.format(Double.valueOf(SearchVoucherFromDay))+ "-" 
					   	+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(SearchVoucherFromMonth))))+ 1))) + "-" 
					   	+ SearchVoucherFromYear;
					   	
					   	final   DatePicker dpSearchVoucherTodate = (DatePicker) dialog.findViewById(R.id.dpSearchVoucherTodate);
					   	int SearchVoucherToDay = dpSearchVoucherTodate.getDayOfMonth();
					   	int SearchVoucherToMonth = dpSearchVoucherTodate.getMonth();
					   	int SearchVoucherToYear = dpSearchVoucherTodate.getYear();
					   	
					   	String SearchVoucherTodate = mFormat.format(Double.valueOf(SearchVoucherToDay))+ "-" 
					   	+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(SearchVoucherToMonth))))+ 1))) + "-" 
					   	+ SearchVoucherToYear;
					   	
					   	try {
					   		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						Date date1 = sdf.parse(financialFromDate);
						Date date2 = sdf.parse(financialToDate);
						Date date3 = sdf.parse(SearchVoucherFromdate);
						Date date4 = sdf.parse(SearchVoucherTodate);
						/*
						System.out.println("all dates are...........");
						System.out.println(financialFromDate+"---"+financialToDate+"---"+SearchVoucherFromdate+"---"+SearchVoucherTodate);
						*/
						Calendar cal1 = Calendar.getInstance(); //financial from date
						Calendar cal2 = Calendar.getInstance(); //financial to date
						Calendar cal3 = Calendar.getInstance(); //from date
						Calendar cal4 = Calendar.getInstance(); //to date
						cal1.setTime(date1);
						cal2.setTime(date2);
						cal3.setTime(date3);
						cal4.setTime(date4);  
				
						if(((cal3.after(cal1)&&(cal3.before(cal2))) || (cal3.equals(cal1) || (cal3.equals(cal2)))) 
								&& ((cal4.after(cal1) && (cal4.before(cal2))) || (cal4.equals(cal2)) || (cal4.equals(cal1)))){
							searchVoucherBy = 2; // by date
							Object[] params = new Object[]{2,"",SearchVoucherFromdate,SearchVoucherTodate,""};
							getallvouchers(params);
						}
						else{
							toastValidationMessage("Please enter proper date");
						}
						} catch (Exception e) {
							// TODO: handle exception
						}
					   	
				   	}
				   	else if(pos == 2){
				   		searchByNarration = etNarration.getText().toString();
						if(searchByNarration.length() < 1){
						toastValidationMessage("Please enter narration");
						}
						else{
							searchVoucherBy = 3; //by narration
							Object[] params = new Object[]{3,"",financialFromDate,financialToDate,searchByNarration};
							getallvouchers(params);
						}
				   	}
		
				}

	
			});

			builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
	
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
		      });
		      }
		       addTable(); }

	* The below method sets all data in a tabular format using for loop.

	* It calls another method ``addHeader()`` for setting table header for the table. 

		::

			public void addTable() {
		
			if(searchedVoucherGrid.size()>1){
				addHeader();
			}
	
			/** Create a TableRow dynamically **/
		    for(int i=0;i<searchedVoucherGrid.size();i++){
		    ArrayList<String> columnValue = new ArrayList<String>();
		    columnValue.addAll(searchedVoucherGrid.get(i));
		    tr = new TableRow(SearchVoucher.this);
		   
		    for(int j=0;j<columnValue.size();j++){
		        /** Creating a TextView to add to the row **/
		        addRow(columnValue.get(j),i);  ////
		       // System.out.println("rowid"+i);
		        label.setBackgroundColor(Color.BLACK);
		        /*
		         * set center aligned gravity for amount and for others set center gravity
		         */
		        if(j==6){
		        	
		  	label.setGravity(Gravity.RIGHT);
		            
		            if(columnValue.get(j).length() > 0){
		            	
		            	colValue=columnValue.get(j);
		            	if(!"".equals(colValue)){
		            		System.out.println("m in ");
		            		if(!"0.00".equals(colValue)){
		            			// for checking multiple \n and pattern matching
		            			Pattern pattern = Pattern.compile("\\n");
		            			Matcher matcher = pattern.matcher(colValue);
		            			boolean found = matcher.find();
		            			System.out.println("value:"+found);
		            			if(found==false){
		            				double amount = Double.parseDouble(colValue);	
		            				label.setText(formatter.format(amount));
		            			}else {
		            				label.setText(colValue);
						}
		            			
		            		}else {
		            			label.setText(colValue);
					}
		            	}
		            }
		        
		        }
		        else{
		            label.setGravity(Gravity.CENTER);
		        }
		        
		    }
		   
		    // Add the TableRow to the TableLayout
		    vouchertable.addView(tr, new TableLayout.LayoutParams(
		            LayoutParams.FILL_PARENT,
		            LayoutParams.WRAP_CONTENT));
		}
		}

	* The below methed sets header for the table.

		::

			/*
			* add column heads to the table
			*/
			public void addHeader() {

				 /** Create a TableRow dynamically **/
				final SpannableString rsSymbol = new SpannableString(SearchVoucher.this.getText(R.string.Rs)); 
			String[] ColumnNameList = new String[] { "V. No.","Reference No","Date","Voucher Type","Account Name","Particular",rsSymbol+"Amount","Narration"};

			tr = new TableRow(SearchVoucher.this);

			for(int k=0;k<ColumnNameList.length;k++){
			    /** Creating a TextView to add to the row **/

			    addRow(ColumnNameList[k],k);
			    params.height=LayoutParams.WRAP_CONTENT;
			    label.setBackgroundColor(Color.parseColor("#348017"));
			    label.setGravity(Gravity.CENTER);
			    tr.setClickable(false);
			}

			 // Add the TableRow to the TableLayout
			vouchertable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

			}

	* The below method add row to the table and make that clickable.

	* It add functionailty to edit/clone/delete Voucher.

		::

			/*
			 * this function add the value to the row
			 */
			public void addRow(String string,final int i) {
			     tr.setClickable(true);
		
			     tr.setOnClickListener(new OnClickListener() {
					 
				@Override
					public void onClick(View v) {
						 
					 try {
						 final CharSequence[] items = { "Edit voucher", "Clone voucher","Delete voucher"};
							//creating a dialog box for popup
						AlertDialog.Builder builder = new AlertDialog.Builder(SearchVoucher.this);
							//setting title
						builder.setTitle("Edit/Delete Voucher");
							//adding items
						builder.setItems(items, new DialogInterface.OnClickListener() {

							@Override
						public void onClick(DialogInterface dialog,
						int pos) {
						if(pos == 0){
							MainActivity.nameflag=true;
						 	name="Edit voucher";
						 	
						 	cloneflag=false;
						 	
							//System.out.println("in addrow"+i); 
							value=searchedVoucherGrid.get(i);
					
						
							MainActivity.searchFlag=true;
							Intent intent = new Intent(context, transaction_tab.class);
								// To pass on the value to the next page
							startActivity(intent);

							}
							if(pos==1){
							 	MainActivity.nameflag=true;
							 	cloneflag=true;
								name="Clone voucher";
						
								value=searchedVoucherGrid.get(i);
						
								MainActivity.searchFlag=true;
								Intent intent = new Intent(context, transaction_tab.class);
								// To pass on the value to the next page
								startActivity(intent);
						
							}
								
							if(pos==2){
							AlertDialog.Builder builder = new AlertDialog.Builder(SearchVoucher.this);
							builder.setMessage("Are you sure you want to detete the Voucher?")
									.setCancelable(false).setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								value=searchedVoucherGrid.get(i);
								vouchercode=value.get(0);
								Object[] params = new Object[]{vouchercode};
								deleteVoucher = (Boolean) transaction.deleteVoucher(params,client_id);
						
								Object[] allvouchersparams = new Object[]{2,"",financialFromDate,financialToDate,""};
							    getallvouchers(allvouchersparams);
							    
								toastValidationMessage("Voucher deleted successfully");
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
							dialog=builder.create();
			  	            ((Dialog) dialog).show();
			  	              WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			  	            //customizing the width and location of the dialog on screen 
			  	            lp.copyFrom(dialog.getWindow().getAttributes());
			  	            lp.height = 600;
			  	            lp.width = 400;
			  	            dialog.getWindow().setAttributes(lp);		
						
						} catch (Exception e) {
							System.out.println(e);
						} 
					}
			
				});
		
				label = new TextView(SearchVoucher.this);
				label.setText(string);
				label.setTextSize(15);
				label.setTextColor(Color.WHITE);
				label.setGravity(Gravity.CENTER_VERTICAL);
				label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
				label.setPadding(2, 2, 2, 2);
				LinearLayout Ll = new LinearLayout(SearchVoucher.this);
				params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				       35);
				params.setMargins(1, 1, 1, 1);
				//Ll.setPadding(10, 5, 5, 5);
				Ll.addView(label,params);
				tr.addView((View)Ll); // Adding textView to tablerow.
			}

	* The below method gets all data from the database.

	* This method calls addtable() method to show all the data retrived from database.

		::

			 public void getallvouchers(Object[] params){
		
			Object[] searchedVoucher = (Object[])transaction.searchVoucher(params,client_id);
			searchedVoucherGrid = new ArrayList<ArrayList<String>>();
			for(Object voucherRow : searchedVoucher){
				Object[] v = (Object[]) voucherRow;
		    searchedVoucherList = new ArrayList<String>();
		    for(int i=0;i<v.length;i++){
		    	
		    	if(((String) v[3].toString()).equalsIgnoreCase(vouchertypeflag)){
		    		searchedVoucherList.add((String) v[i].toString());
		    	}
		    	
		    }
		    searchedVoucherGrid.add(searchedVoucherList);
			}
		
		
			vouchertable = (TableLayout)findViewById(R.id.maintable);
			vouchertable.removeAllViews();
		       
			addTable();
			}

	* The resume method will be called when there is sudden change in activity such as tab change.

		::

			 /*
			 * (non-Javadoc)
			 * @see android.app.Activity#onResume()
			 * to execute code when tab is changed because 
		    	 * when the tab is clicked onResume is called for that activity
			 */
			@Override
			protected void onResume() {
				super.onResume();
			if(searchVoucherBy == 1){ // by reference number
				Object[] params = new Object[]{1,searchByRefNumber,financialFromDate,financialToDate,""};
		   			getallvouchers(params);
			}
			else if(searchVoucherBy == 2){ // by date
				Object[] params = new Object[]{2,"",financialFromDate,financialToDate,""};
		    		getallvouchers(params);
			}
			else if(searchVoucherBy == 3){ // narration
				Object[] params = new Object[]{3,"",financialFromDate,financialToDate,searchByNarration};
					getallvouchers(params);
			}	
			}

		    


