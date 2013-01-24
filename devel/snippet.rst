Lets start with coding
==================

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
        * The first layout of the android APK is activity_main.xml.
        
        * Create this XML layout file in “res/layout/” folder.
        
        * It contains the features for the first screen that displays two buttons:
        	#. Create new organisation
        	#. Select existing organisation, besides the main logo of Aakash Business Tool(ABT).
        	
        * File  res/layout/activity_main.xml
        
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
		::
		
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
