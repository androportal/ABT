=====================
Aakash Business Tool
=====================

**Aakash Business Tool** is a portable accounting platform on Android
intended for Accountants and Students. `Aakash
<http://aakashlabs.org>`_ is a low cost computing device/tablet for
students, the project is initiated and funded by MHRD, Govt. of
India. Aakash already runs Android 4.2 with many educational apps
developed at IIT Bombay. Please refer to `androportal
<https://github.com/androportal/>`_ for some of the apps. Aakash
Accounting provides an easy to use interface specially for
students/newbies who have just started accounting. We have tried to
make the user's experience simple and elegant. This initial version 1.0
covers basic account management, creating vouchers and reports.

It was initially derived from `GNUkhata <http://www.gnukhata.org>`_, a
web based free accounting software which is mostly based on Python
framework.

Features
  #. Managing Organizations
  
	.. image::  assets/create_org.png
	   :name: Create Organisation
	   :align: center
	   :height: 200pt
	   :width: 350pt
  
  #. User authentication
  
	.. image::  assets/login_user.png
	   :name: Create Organisation
	   :align: center
	   :height: 200pt
	   :width: 350pt
  
  #. Main menu
  
	.. image::  assets/admin_master.png
	   :name: Master menu
	   :align: center
	   :height: 200pt
	   :width: 350pt
  
  #. Maintaining books of accounts
  
	.. image::  assets/create_account.png
	   :name: Create account
	   :align: center
	   :height: 200pt
	   :width: 350pt
  
  #. Recording, cloning and editing transactions and Recording of transactions 
     under particular project
  
	.. image::  assets/create_voucher.png
	   :name: Create voucher
	   :align: center
	   :height: 200pt
	   :width: 350pt
  
  #. Generating reports such as Ledger Accounts, Trial Balance(Net,
     Gross, Extended), Profit and Loss Account, Project Statement,
     Cash Flow and Balance Sheet, Bank Reconciliation.

	.. image::  assets/ledger.png
	   :name: Ledger
	   :align: center
	   :height: 200pt
	   :width: 350pt     
     
  #. Rollover
  #. Export reports in PDF/CSV format
  
	.. image::  assets/pdf_file_security.png
	   :name: PDF file security
	   :align: center
	   :height: 200pt
	   :width: 350pt
  
  #. Import/Export of organization data to other device
  
	.. image::  assets/import_organisation.png
	   :name: Import Organisation
	   :align: center
	   :height: 200pt
	   :width: 350pt

  #. ABT running using remote server
  
	.. image::  assets/remote_location.png
	   :name: Set remote location address
	   :align: center
	   :height: 200pt
	   :width: 350pt  


ABTcore - a backend to Aakash Business Tool
--------------------------------------------

The entire backend has Python code base. `ABTcore
<https://github.com/androportal/ABTcore>`_ provides a backend to
**Aakash Business Tool**. On Aakash tablet, it runs in a ``chroot``
environment(your device need to be rooted for this!). More information
can be found `here
<https://github.com/androportal/ABTcore/blob/master/README.rst>`_


How to install
---------------
Simple and recommended install procedure
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  #. This process requires a working Internet connection. 
  #. If you're behind proxy, then do appropriate settings to bypass
     your proxy server. You will find proxy setting in ``Settings`` ->
     ``WiFi`` -> ``Advance Options``. Also bypass ``127.0.0.1`` proxy
     settings.  This is **not** required for direct Internet
     connections.
  #. Open the browser on Aakash and visit this link:
     **http://www.it.iitb.ac.in/AakashApps/repo/ABT.apk**. Download and install the
     APK.
  #. Click on **ABT icon** from android's application menu and allow
     it to download ``~300MB`` image file.
  #. The download and uncompress process will take some while, so
     please be patience. When download completes, it will prompt for
     reboot. Please say ``Yes`` to reboot.
  #. After reboot, you can again visit android's application menu and
     click on **ABT icon**.
  
Manual installation
~~~~~~~~~~~~~~~~~~~

  #. Download compressed image to your computer from this `link
     <http://www.it.iitb.ac.in/AakashApps/repo/abt.tar.gz>`_. Extract it using ::
       tar -xvzf abt.tar.gz
  #. The untar process of above file will produce ``abt.img``. Copy
     ``abt.img`` to sdcard(internal or external) of your Aakash
     tablet.
  #. Then install `ABT.apk <http://www.it.iitb.ac.in/AakashApps/repo/ABT.apk>`_ on
     Aakash, ``shutdown`` and ``start`` Aakash to finish
     installation. Now locate **ABT icon** in your android menu to
     start using *Aakash Business Tool*.  

Usage 
------

This `branch` contains an Android(4.2, API-17) code for the User
Interface of Aakash Business Tool.

User's can clone this repo by typing ::

   git clone -b holo_theme https://github.com/androportal/ABT.git

if you want to clone code for Android(4.0.3, API-15), type ::

   git clone -b ics https://github.com/androportal/ABT.git


if you want to checkout other branches then type, ::

   git checkout -b holo_theme remotes/origin/holo_theme
   git checkout -b docs remotes/origin/docs
   git checkout -b froyo remotes/origin/froyo
   git checkout -b ics remotes/origin/ics

Importing **ABT** as an `eclipse <http://www.eclipse.org/>`_ project
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

- For setting up Android SDK and AVD in eclipse, please visit this `link
  <http://developer.android.com/sdk/index.html>`_
- With a single download, the ADT Bundle includes everything you need to 
  begin developing apps:
	#. Eclipse + ADT plugin
    	#. Android SDK Tools
    	#. Android Platform-tools
    	#. The latest Android platform
    	#. The latest Android system image for the emulator

  
 
After cloning the required branch, start eclipse

- go-to ``File`` menu -> ``Import``.
- from the ``Import`` dialog box, select ``Android``.
- from ``Android`` section, select ``Existing Android Code Into
  Workspace`` and click ``Next`` button.
- you will be taken to ``Import Projects`` dialog box, click ``Browse``
  button and select the cloned repository.

Documentation
-------------

For User and developer's guide, please visit
`http://aakashlabs.org/docs/abt/index.html
<http://aakashlabs.org/app/webroot/docs/abt/index.html>`_

Documentation in raw `sphinx <http://sphinx.pocoo.org/>`_ format can
be cloned from ::

   git clone -b docs https://github.com/androportal/ABT.git

please refer ``README.rst`` on how to generate html docs

Help, bugs, feedback
--------------------

#. Users can mail their queries, feedback and suggestions at
   accounting-on-aakash@googlegroups.com
#. Developers/Contributor can raise issues at `issues
   <https://github.com/androportal/ABT/issues>`_
#. Pull requests are most welcome


License
-------

GNU GPL Version 3, 29 June 2007.

Please refer this `link <http://www.gnu.org/licenses/gpl-3.0.txt>`_
for detailed description.

All rights belong to the National Mission on
Education through ICT, MHRD, Government of India.
