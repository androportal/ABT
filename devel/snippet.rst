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

	.. toctree::
	   :numbered:
	   
	   install_ABT
	   maintaining_organisation
	   master_menu
	   account_management
	   transaction_management


