=======================================
Setting the application on the Android
=======================================

.. toctree::
   :numbered:

Introduction
------------------
In this tutorial, we show you how to setting Aakash Business Tool(ABT) application in Eclipse IDE + ADT plugin, and run it with Android Virtual Device (AVD). Developing an application on **Aakash**, the basic software platform
utilised was Eclipse (Indigo).  The Android Version 4.0 was the
supporting version to develop.

Setting up the Android Envionment
--------------------------------------------------
+ **Android SDK**
	
	* The Android SDK provides a set of tools and APIs to develop Android applications, using Java.
	
	* Download the android SDK from the official website `developer.android.com <http://developer.android.com/sdk/index.html>`_
	
	* The basic command for ubuntu users to check their configured version is 
		::

	   		uname -a
	   		
	* Type the command on the terminal and check for the system is 64 or 32 bit. The linux users should install the proper version, as there is availabilty of two versions 64 and 32 bit.
	
	* Please remember that currently adb only supports 32-bit system, 
	  if your system is 64-bit, then install ``ia32-libs-multiarch`` library to support multi-architecture. 
	  On Ubuntu system, install ``ia32-libs-multiarch`` using 
		::

	   		sudo apt-get install ia32-libs-multiarch


+ **Installing the Packages in SDK Manager**
	
	* Configure the SDK manager and download tools and packages for different Android versions.
	
	* For example the Android 2.2 (froyo), Android 2.3
          (ginerbread), Android 3.0 or 3.1 (honeycomb), Android 4.0
          (ice cream sandwich) and so on.

	* ``Note``: Before starting the package downloading process, apply net settings in the Android SDK Manager, 
	                select ``Tools`` > ``Options`` and configure the internet settings.

+ **Software platform- Eclipse(Indigo)**
	
	* The basic software platform to develop an application is to install Eclipse.
	
	* Download eclipse package from the official website for `eclipse <http://www.eclipse.org/downloads/>`_.

	* or Linux users can install eclipse by typing a command,
		::

	   		sudo apt-get install eclipse

	* `Note`: we have used Eclipse version 3.7.2(Indigo)

+ **Install ADT Eclipse plugin**
 Android offers a custom plugin for the Eclipse IDE, called Android Development Tools (ADT). The Eclipse ADT plugin provided easy Android project creation and management, components drag and drop, auto-complete and many useful features to speed up your Android development cycles. To integrate Android SDK with Eclipse IDE, you need to install Eclipse ADT plugin. Refer to this official guide – `Installing the ADT Plugin <http://developer.android.com/tools/sdk/eclipse-adt.html#installing>`_.
	
	
 - **Setups for configuring** :
			
  `NOTE` : before starting the ADT plugin process (if installing from
  the internet), apply net settings in the Eclipse, select Windows >
  Preferences . On General option , click on Network and configure the
  internet settings.

  #. Start Eclipse, select Help > Check for updates.
  
  #. then select Help > Install New Software.
			
  #. Click Add, in the top-right corner.
			
  #. In the Add Repository dialog that appears, enter "ADT Plugin" for
      the Name and the following URL for the location
      `https://dl-ssl.google.com/android/eclipse/ <https://dl-ssl.google.com/android/eclipse/>`_
			
  #. Click OK. `Note`, If you have trouble acquiring the plugin, try
      using ``http`` in the Location URL, instead of ``https`` (``https`` is
      preferred for security reasons).
			
  #. In the Available Software dialog, select the checkbox next to
      Developer Tools and click Next.
			
  #. In the next window, you'll see a list of the tools to be
     downloaded. Click Next.
			
  #. Read and accept the license agreements, then click Finish.
			
  #. When the installation completes, restart Eclipse.


 - **Configure the ADT Plugin**
  
  #. After you've installed ADT and restarted Eclipse, you must specify
      the location of your Android SDK directory.

  #. Select Window > Preferences... to open the Preferences panel (on Mac
     OS X, select Eclipse > Preferences).

  #. Select Android from the left panel.

  #. For the SDK Location in the main panel, click Browse... and locate
      your downloaded Android SDK directory (such as android-sdk-windows).

  #. Click Apply, then OK.


+ **Create an Android Virtual Device(AVD)**
	
	* In Eclipse, you can access the “Android Virtual Device (AVD)” in Eclipse toolbar.
	
	* Create a new avd in AVD manager and mention proper memory size of the SDcard and launch the emulator.


+ **Clone APK**
	
	* Android(4.0.3, API-15) code for user interface of Aakash Business Tool is located at `github <https://github.com/androportal/ABT>`_.

	* User can clone this repo by typing command,
		::

	   		git clone https://github.com/androportal/ABT.git

	* **ics** is the default branch. If you want to clone code from specific branch, type
		::

		   	git clone -b branch_name https://github.com/androportal/ABT.git

+ **Import ABT project in eclipse**

	* After cloning the required branch, start eclipse

		#. Go to ``File`` menu > ``Import``.
		
		#. From the ``Import`` dialog box, select ``Android``.
		
		#. From ``Android`` section, select ``Existing Android Code Into Workspace`` and click ``Next`` button.
 		    
		#. You will be taken to ``Import Projects`` dialog box, click ``Browse`` button and select the cloned repository.
	

Referenced Libraries
-----------------------------
+ **ant-1.6.jar**

	Start eclipse, There is a ``jar`` file called ``ant-1.6.jar`` in the ABT project which is used to untar a tar file programmatically from java into a certain directory, to add that file in the referenced libraries do the following steps:

	* Right click on ant-1.6.jar and select Build Path > Add to Build Path.

	* To confirm, go to Referenced Libraries and check if ant-1.6.jar is added.

	* **Note**: If you have trouble in adding jar file to reference libraries, add following line in  ABT/.classpath.
	
		::
	
			<classpathentry exported="true" kind="lib" path="ant-1.6.jar"/>

	* and for information, ant-1.6.jar.tar can be downloaded from `ant.jar <http://www.java2s.com/Code/Jar/a/Downloadant16jar.htm>`_
	

+ **android-xmlrpc.jar**

	* android-xmlrpc.jar is located at ABT/libs.
	
	* As ABT is a client-server application, to access the remote procedure call we have used android-xmlrpc client access method on remote server.
	
	* To download visit `android-xmlrpc <http://www.java2s.com/Code/Jar/a/Downloadandroidxmlrpcjar.htm>`_.	
		

How to start ABT
----------------

+ **Run on a Real Device**

	If you have a real Android-powered device, here's how you can install and run ABT:

    	* Plug in your device to your development machine with a USB cable.
    	* Enable USB debugging on your device.

	* To run the app from Eclipse:

    		- Open one of ABT project's files and click Run from the toolbar.
    		- In the Run as window that appears, select Android Application and click OK.

		- Eclipse installs the app on your connected device and starts it.

	* Or to run ABT from a command line:

    		- Change directories to the root of ABT project and execute:

    		::
    		
    			ant debug

    		- Make sure the Android SDK platform-tools/ directory is included in your PATH environment variable. To setup a PATH for adb on your system, export adb's PATH using

			SYNTAX
			::
			
				export PATH=/home/${HOME}/<path-to-your-sdk/platform-tools:$PATH

			for example, if you have downloaded Android's SDK in $HOME, then your command should be

			::
		
				export PATH=/home/andro/android-sdk-linux/platform-tools:$PATH

		   	assuming $USER is andro in this case.

		- then execute: 
		    	::
		    		
		    		adb install bin/gkAakash.apk

    		- On your device, locate ABT and open it.


+ **Run on the Emulator**

	Whether you're using Eclipse or the command line, to run ABT on the emulator you need to first start AVD. After the emulator boots up, unlock the emulator screen.

	* To run the app from Eclipse:

		- Open one of ABT project's files and click Run from the toolbar.
	    	- In the Run as window that appears, select Android Application and click OK.

		- Eclipse installs the app on your AVD(emulator) and starts it.

	* Or to run your app from the command line:

	    	- Change directories to the root of ABT project and execute,
	    	::
	    		
	    		adb debug

	    	- Make sure the Android SDK platform-tools/ directory is included in your PATH environment variable, then execute: 
		    	::
		    		
		    		adb install bin/gkAakash.apk

	    	- On the emulator, locate ABT and open it.


+ Start back-end server
	Before running ABT android application on eclipse, make sure that gkAakashCore is started and not inside an  exception. To start the server, go through `this <backend_server.html#how-to-run-gkaakashcore-server>`_ link.	

	
+ Run ABT on eclipse

	* To test ABT on emulator go to ABT/src/com/gkaakash/coreconection/CoreConnection.java and change the url from  ``http://127.0.0.1:7081`` to ``http://10.0.2.2:7081``.

	* Lauch emulator.
	
	* Right click on ABT project > Run As > Android Application.
	
