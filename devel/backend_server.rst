.. toctree::
   :numbered:

=======
ABTcore
=======

	**Aakash Business Tool** is a client-server based ``Accounting`` application.
	

About
=====

	This is the backbone of **Aakash Business Tool**.
	**Aakash Business Tool core(ABTcore)** provides a Back-end to ABT 
	which is derived from core_engine of abt.
	This provides all the accounting functionality completely written in `Python
	<http://docs.python.org/2/tutorial/>`_ language,and used twisted
	liabrary to implement xmlrpc server and to access xmlrpc functions and
	android-xmlrpc client access liabrary at client side and used Sqlite3
	database server to maintain data.
	We have completely dropped an idea of ``stored procedures``, 
	instead we have implemented an Object relational mapping using SQLAlchemy.
	
	
Installation of Dependencies:
=============================
	Note: We are highly recommand to use Ubuntu based system for installation 
	and also use ``python`` vesion 2.5 and above
	
On Ubuntu machine 
~~~~~~~~~~~~~~~~~
	
        **Dependencies**
	
	- libpcre3 
    	- libpcre3-dev 
    	- libreadline5 
    	- libreadline6-dev 
    	- libpq5 
    	- sqlite3 
    	- python-pip 
    	- python-sqlalchemy 
    	- python-twisted 

	On an Ubuntu machine, these dependencies can be installed using apt-get
		
	.. code-block:: bash

		sudo apt-get install libpcre3 libpcre3-dev
		sudo apt-get install libreadline5 libreadline6-dev libpq5
		sudo apt-get install python-pip python-sqlalchemy
		sudo apt-get install python-twisted


	Now, you have done with installation of all the dependencies needed to work ABTcore(server)
	
On Aakash Device
~~~~~~~~~~~~~~~~

	You have set up chroot environment to work on Aakash for ABT.
	See `Setting up Chroot environment <chroot_setup.html>`_
	
How to run ABTcore(server):
===========================

On Ubuntu machine 
~~~~~~~~~~~~~~~~~
	Lets, first clone the ABTcore repository 
	
	.. code-block:: bash
	
		git clone https://github.com/androportal/ABTcore.git

        then , navigate through your ABTcore on your system as below
        
        .. code-block:: bash
         
        	cd ABTcore
        	
        then run below command 
        
        .. code-block:: bash
         
        	sudo ./abtstart
        
        Note: abtstart will start the server who listen the port ``7081``, so it should not be in use.
        	
        check for these message ``initialising application`` and ``starting server``
        else ``inside exception`` will occure the run below command.
        
        ``inside exception`` it tells either ``7081`` port is already in use or
        you have missed somthing to be done (as above)
        
        
        .. code-block:: bash
         
        	sudo killall abtstart 
        	
        Or
        
        .. code-block:: bash
        
        	sudo killall python
        	
        and again run ``sudo ./abtstart`` to start ABTcore. 
        
        Now, you are ready to start with ABT(client) , please see `setting up frontend <frontend_javaclient.html>`_ for client-side setup.
        After doing well with client setup , you are able to work on it.
        
On Aakash Device(ARM-arch)
~~~~~~~~~~~~~~~~~~~~~~~~~~ 

	You have to setup a PATH for adb on your system, please refer ABT's README section "Importing ABT as an eclipse project". Once you have downloaded the SDK, update it to API-15(Icream Sandwich). and export adb's PATH using

	**SYNTAX**

	``export PATH=/home/${HOME}/<path-to-your-sdk/platform-tools:$PATH``

	for example, if you have downloaded Android's SDK in $HOME, then your command should be

	``export PATH=/home/andro/android-sdk-linux/platform-tools:$PATH``

	assuming $USER is andro in this case.

	Please remember that currently adb only supports 32-bit system, if your system is 64-bit, you have to install ia32-libs-multiarch library to support multi-architechture. On Ubuntu system, install ia32-libs-multiarch using

	.. code-block:: bash
	
		sudo apt-get install ia32-libs-multiarch

	Once adb is in place, attach USB data cable provided with Aakash to your linux system and other end(micro-socket) to Aakash. Now you can push``the content of ``ABTcore/ directory inside Aakash to PATH ``/data/local/abt/root/ABTcore`` (please refer this link for adb usage). 
	Please note that we have a chroot environment under ``/data/local/abt`` on Aakash. Details of chroot'ing is not provided 
	here. We will soon upload an chroot image which can be downloaded and should be kept in ``/mnt/sdcard/`` of Aakash.

	Once ABTcore is pushed inside the device, do
	
	.. code-block:: bash
	
		adb shell

	to get bash prompt on device. You have to enter chroot environment using

	.. code-block:: bash
	
		cd /data/local/
		
		sh debug.sh

	Note: if debug.sh does not exit in /data/local/, push it to Aakash's /data/local/ path. Visit install directory within ABTcore (your cloned repo)

	.. code-block:: bash
	
		cd ABTcore/install/

	and push debug.sh to /data/local/

	.. code-block:: bash
	
		./adb push debug.sh /data/local/

	If your bash prompt says root@localhost, you are inside the chroot!. to start the server type

	.. code-block:: bash

		cd /root/ABTcore
		
		./abtstart
		
	Now you can install an APK and start working  

  
Start with programming (coding):
=================================
	** You must have atleast basic knowledge of ``Python`` language and SqlAlchemy.
	
	** As you have clone of ``ABTcore`` you can see there is ``abtserver`` it conatins all rpc modules 
	
	
abtserver
~~~~~~~~~

        + It is a full package of ``ABTcore`` which contain all the ``rpc`` modules each single rpc files does specific work.
       
	.. toctree::
	   :numbered:
	   
	   rpc_main
	   dbconnect
	   rpc_groups
	   rpc_account
	   rpc_getaccountsbyrule
	   rpc_transactions
	   rpc_data
	   rpc_reports
	   rpc_organisation
           
           
	** Note: 
		 #. Input parameter`s ``queryParams`` for any function will be the list of input values.
		 #. to remove blanckespaces from input values used 
		    ``blankspace.remove_whitespaces(queryParams)`` from ``modules`` of ``ABTcore``.
		 #. to convert String date time to sqlite datetime format used ``from datetime import datetime, time``. 
       





	   

			
			
		 	
