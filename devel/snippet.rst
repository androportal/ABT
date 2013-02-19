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

Referenced Libraries
--------------------
+ **ant-1.6.jar**

  Start eclipse, There is a ``jar`` file called ``ant-1.6.jar`` in ABT/libs which is used to untar a tar file programmatically from java into a certain directory. For information, ant-1.6.jar.tar can be downloaded from `ant.jar <http://www.java2s.com/Code/Jar/a/Downloadant16jar.htm>`_.
    

+ **android-xmlrpc.jar**

  android-xmlrpc.jar is located at ABT/libs. As ABT is a client-server application, to access the remote procedure call we have used android-xmlrpc client access method on remote server. To download visit `android-xmlrpc <http://www.java2s.com/Code/Jar/a/Downloadandroidxmlrpcjar.htm>`_.   


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


	* **File  res/layout/create_org.xml**

		::

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

	**File  res/layout/select_org.xml**

		::

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


* **File src/com/example/gkaakash/selectOrg.java**

	* This page allows to select the already created or existing organisation from database
	  with a particular financial year.

	* Its activiy is explained below along with code. 

	* The activity contains the essential and required import like

		::

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

	* onCreate method loads all the required methods at load time. 

		::

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


	* The below method loads all the organisation name from the database and populates 
	  organization name spinner.

		::

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

		::

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

	 **File  res/layout/create_voucher.xml**

		::

			<?xml version="1.0" encoding="utf-8"?>
			<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
			 android:layout_width="fill_parent" 
			 android:layout_height="fill_parent"
			 android:orientation="vertical"
			 android:weightSum="100"
			 android:background="@drawable/dark_gray_background">
			 
			    <LinearLayout
				   android:orientation="horizontal"
				   android:layout_width="400dp"
				   android:layout_height="3dp"
				   android:paddingLeft="20dp"
				   android:paddingRight="20dp"
				   android:background="#60AFFE"/>
			   
			    <LinearLayout
				   android:orientation="horizontal"
				   android:layout_width="match_parent"
				   android:layout_height="3dp"
				   android:paddingLeft="20dp"
				   android:paddingRight="20dp"
				   android:background="#60AFFE"/>
			    
			<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
			    android:layout_width="fill_parent"
			    android:layout_height="fill_parent"
			    android:layout_weight="80"
			    android:background="@drawable/dark_gray_background" >
				<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    android:orientation="vertical"
				    android:layout_width="match_parent"
				    android:layout_height="wrap_content"
				    android:paddingTop="5dp" >
			 
					<TableLayout
					    xmlns:android="http://schemas.android.com/apk/res/android"
					    android:layout_width="match_parent"
					    android:layout_height="wrap_content"
					    android:orientation="vertical"
					    android:paddingLeft="20dp"
					    android:paddingRight="20dp"
					    android:stretchColumns="7" >

					    <TableRow
						android:layout_width="match_parent"
						android:layout_height="wrap_content" >

						   	
					    <Spinner
						android:id="@+id/sDrCr"
						android:layout_width="wrap_content"
						android:layout_height="wrap_content"
						android:entries="@array/SearchBy_arrays"
						android:prompt="@string/Search_prompt" />
						
					    <TextView
						android:id="@+id/accountName"
						android:layout_width="wrap_content"
						android:layout_height="wrap_content"
						android:text="        Account name"
						android:textSize="14dp"
						android:textColor="#FFFFFF" />

					    <Spinner
						android:id="@+id/getAccountByRule"
						android:layout_width="259px"
						android:layout_height="wrap_content"
						android:entries="@array/accountName_arrays"
						android:prompt="@string/accountName_prompt" />
			    
					    <TextView
						android:id="@+id/amount"
						android:layout_width="wrap_content"
						android:layout_height="wrap_content"
						android:text="        Amount"
						android:textSize="14dp"
						android:textColor="#FFFFFF" />
					    
					     <TextView
						android:id="@+id/rupeeSym"
						android:layout_width="wrap_content"
						android:layout_height="wrap_content"
						android:text="@string/Rs"
						android:textColor="#FFFFFF"
						android:textSize="19dp"
						android:paddingRight="5dp"
						android:paddingLeft="10dp"/>
					     
			    
						  <EditText
						android:id="@+id/etDrCrAmount"
						android:layout_width="fill_parent"
						android:layout_height="wrap_content"
						android:layout_weight="2"
						android:inputType="numberDecimal"
						android:text="0.00         " >

					  <requestFocus />
						  </EditText>
						   
						 <Button
						    android:id="@+id/add"
						    android:layout_weight="0.3"
						    android:text=" +  " />
			   
					    </TableRow>
					</TableLayout>
		
					<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
				    android:id="@+id/Vouchertable"
				    android:orientation="vertical"
				    android:layout_width="fill_parent"
				    android:layout_height="fill_parent" 
		    		    android:stretchColumns="7" 
		    		    android:paddingLeft="20dp"
		    		    android:paddingRight="20dp"
		    		    android:paddingBottom="5dp"
			    		/>
		
					<LinearLayout 
				    android:orientation="horizontal"
				    android:layout_width="match_parent"
				    android:layout_height="1dp"
				    android:paddingLeft="20dp"
				    android:paddingRight="20dp"
				    android:weightSum="100" 
				    android:background="@android:color/darker_gray">
		
		
		
					</LinearLayout>

				<ListView
				android:id="@+id/voucher_list"
				android:layout_width="match_parent"
				android:layout_height="wrap_content">
				</ListView>
	
				<LinearLayout 
				    android:orientation="horizontal"
				    android:layout_width="match_parent"
				    android:layout_height="0.01dp"
				    android:paddingLeft="20dp"
				    android:paddingRight="20dp"
				    android:background="@android:color/darker_gray"/>
	
				<ListView
				android:id="@+id/voucher_list4"
				android:layout_width="match_parent"
				android:layout_height="wrap_content">
				</ListView>
	
				<LinearLayout 
				    android:orientation="horizontal"
				    android:layout_width="match_parent"
				    android:layout_height="0.01dp"
				    android:paddingLeft="20dp"
				    android:paddingRight="20dp"
				    android:background="@android:color/darker_gray"/>
	
				<LinearLayout
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:orientation="horizontal"
				android:weightSum="100"
				android:paddingTop="3dp"
				android:paddingRight="20dp"
				android:gravity="left">
				<TextView
				    android:id="@+id/tvRefNumber"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:text="Voucher reference number"
				    android:layout_weight="70"
				    android:textSize="14dp"
				    android:paddingLeft="20dp"
				    android:textColor="#FFFFFF"/>
	
				<EditText
					android:id="@+id/etRefNumber"
					android:layout_width="305dp"
					android:layout_height="wrap_content"
					android:layout_weight="30"
					android:hint="Tap to enter reference number" 
					android:paddingTop="2dp"/>
				</LinearLayout>
	
				<LinearLayout 
					android:orientation="horizontal"
					android:layout_width="match_parent"
					android:layout_height="0.01dp"
					android:paddingLeft="20dp"
					android:paddingRight="20dp"
					android:background="@android:color/darker_gray"/>
	
				<LinearLayout
				android:layout_width="fill_parent"
				android:layout_height="wrap_content"
				android:orientation="horizontal"
				android:weightSum="100"
				android:paddingTop="3dp"
				android:paddingRight="20dp"
				android:gravity="left">
				<TextView
					android:id="@+id/tvVoucherNarration"
					android:layout_width="wrap_content"
					android:layout_height="wrap_content"
					android:text="Voucher narration"
					android:layout_weight="70"
					android:textSize="14dp"
					android:paddingLeft="20dp"
					android:textColor="#FFFFFF"/>
	
				<EditText
					android:id="@+id/etVoucherNarration"
					android:layout_width="283dp"
					android:layout_height="wrap_content"
					android:layout_weight="30"
					android:hint="Tap to enter voucher narration" 
					android:paddingTop="2dp"
					android:inputType="textCapSentences"/>
				</LinearLayout>
	
				</LinearLayout>
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
				    android:id="@+id/btnSaveVoucher"
				    android:layout_width="wrap_content"
				    android:layout_height="match_parent"
				    android:layout_gravity="center_vertical"
				    android:layout_weight="50"
				    android:text="Save"
				    android:textSize="20dp" />
		
				<Button
				    android:id="@+id/btnResetVoucher"
				    android:layout_width="wrap_content"
				    android:layout_height="match_parent"
				    android:layout_gravity="center_vertical"
				    android:layout_weight="50"
				    android:text="Reset"
				    android:textSize="20dp" />

			    </LinearLayout>
			</LinearLayout>

.. image:: images/create_voucher.png
	   :name: ABT main page
	   :align: center

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

	  **File  res/layout/search_voucher.xml**
		
		::

			<?xml version="1.0" encoding="utf-8"?>
			<LinearLayout 
			    android:id="@+id/LinearLayout01"
			    android:layout_width="fill_parent"
				android:layout_height="fill_parent"
			    xmlns:android="http://schemas.android.com/apk/res/android"
			    android:orientation="vertical"
			    android:background="@drawable/dark_gray_background">

			    <LinearLayout
				   android:orientation="horizontal"
				   android:layout_width="400dp"
				   android:layout_height="3dp"
				   android:layout_gravity="right"
				   android:paddingLeft="20dp"
				   android:paddingRight="20dp"
				   android:background="#60AFFE"/>
			   
			    <LinearLayout
				   android:orientation="horizontal"
				   android:layout_width="match_parent"
				   android:layout_height="3dp"
				   android:paddingLeft="20dp"
				   android:paddingRight="20dp"
				   android:background="#60AFFE"/>

			    <LinearLayout
				android:layout_width="match_parent"
				android:layout_height="wrap_content" 
				android:paddingBottom="3dp"
				android:paddingTop="3dp">

				<TextView
				    android:id="@+id/tvVFromdate"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_weight="1"
				    android:paddingLeft="10dp"
				    android:textSize="15dp"
				    android:textColor="#FFFFFF"/>

				<Button
				    android:id="@+id/btnSearchVoucher"
				    style="?android:attr/buttonStyleSmall"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:background="@drawable/ic_action_search"/>

				<TextView
				    android:id="@+id/tvVTodate"
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:layout_weight="1"
				    android:gravity="right"
				    android:paddingRight="10dp"
				    android:textSize="15dp"
				    android:textColor="#FFFFFF"/>

			    </LinearLayout>
			    
			    
			    <LinearLayout
				    xmlns:android="http://schemas.android.com/apk/res/android"
				    android:id="@+id/layout_root"
				    android:layout_width="fill_parent"
				    android:layout_height="fill_parent"
				    android:paddingLeft="10dp"
				    android:paddingRight="10dp"
				    android:paddingBottom="10dp" >
					 <HorizontalScrollView 
			       			android:layout_height="fill_parent"
				    	android:layout_width="fill_parent"
				    	android:fillViewport="true">
				    
				      <ScrollView
				    android:layout_width="wrap_content"
				    android:layout_height="wrap_content"
				    android:fillViewport="true">
					<TableLayout
				    android:layout_width="wrap_content"
				    android:layout_height="0dp"
				    android:stretchColumns=",1,2,3,4"
				    android:id="@+id/maintable"
				    android:background="#696565" >
				       </TableLayout>
				
				    </ScrollView>
				    </HorizontalScrollView>
				 </LinearLayout>

			</LinearLayout>

.. image:: images/search_voucher.png
	   :name: ABT main page
	   :align: center

* **File src/com/example/gkaakash/SearchVoucher.java**

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

Account management
+++++++++++++++++++

	- Account management covers three major parts ie. Create,Search and Delete .

	- All the three options are included in a path ``src/com.example.gkaakash/account_tab.java`` .

	- It maintains all account details such as opening balances,closeing balances etc.

	- **File src/com/example/gkaakash/account_tab.java**

	- Its activity is explained below along with code.

	- The activity contains the essential and required import like

		::			

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

	* onCreate method creates two Tabspec and include them in Tabhost.

	* It sets Create account as bydefault tab.

		::
		
			String accCodeCheckFlag;
			TextView tab1 = null;
			TextView tab2 = null;
			private Integer client_id;
			private Preferences preferences;

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


	- **File  res/layout/create_account.xml**

		::

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

* **File src/com/example/gkaakash/createAccount.java**

	* The activity contains the essential and required import like

		::

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
	
	* OnCreate method calls all required methods at load time.

		::

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

	* The below method sets the credit opening,debit opening and difference in opening balance in their respective
	  text fields.  

		::


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

		::

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

		::

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

		::

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

		::

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

		::


			// method for blank fields
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

		::


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

		::

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

	- **File  res/layout/edit_account.xml**

		::

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



* **File src/com/example/gkaakash/edit_account.java**

	* The activity contains the essential and required import like

		::

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
	
	* OnCreate method calls all required methods at load time. 

		::

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

		::

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

	* Allow to delete account, if that particular account is not under any transaction or the account is not having 	  opening balance.

		::			

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

		::
		
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

		::


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

		::

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

		::

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

Master menu
++++++++++++

	- Master menu is the main page of the application.

	* This page contains links to Account management, Transaction management, Maintaining organisations
	  Setting up preferences,Help and About page.

	- Its activity is explained below, along with the code.

	* **File src/com/example/gkaakash/menu.java**

	* The activity contains the essential and required import like

		::

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
	
	* OnCreate method adds OnItemClickListener to listView.

		::

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
			    
			//adding list items to the newly created menu list
			String[] menuOptions = new String[] { "Create account", "Transaction", "Reports",
			    "Preferences","Bank Reconciliation","Help","About" };

			@Override
			public void onBackPressed() {
			 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 startActivity(intent); 
			}

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

	* Below section of code takes the user to create/Edit acocunt page.

		::		

				if(position == 0)
				{
				    MainActivity.tabFlag = true;
				    Intent intent = new Intent(context, account_tab.class);
				    // To pass on the value to the next page
				    startActivity(intent);
				}
		
	* Below section of code takes the user to voucherMenu page.

		::

				//for "transaction"
				if(position == 1)
				{
				    Intent intent = new Intent(context, voucherMenu.class);
				    // To pass on the value to the next page
				    startActivity(intent);
				}

	* Below section of code take the user to reportMenu page.

		::
	
				AlertDialog help_dialog;
				//for "reports"
				if(position == 2)
				{
				    Intent intent = new Intent(context, reportMenu.class);
				    // To pass on the value to the next page
				    startActivity(intent);                     
				}
		
	* It builds a dialog with two new option ie. Edit organisation details and Add/Edit/Delete Project.

		::

				//for "adding project", adding popup menu ...
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

	* Gets all accout names from the database in list format.

	* Checks the size of the list. if list size is less than or equal to ``0`` it throws an error message. 

		::

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

	 * Below section of code builds help dialog for the application.

		::

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

	* It builds about page dialog.

		::

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
	






