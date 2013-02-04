%%%%%%%%%%
How to install
%%%%%%%%%%
Simple and recommended install procedure
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  #. This process requires a working Internet connection. 
  #. If you're behind proxy, then do appropriate settings to bypass
     your proxy server. You will find proxy setting in ``Settings`` ->
     ``WiFi`` -> ``Advance Options``. Also bypass ``127.0.0.1`` proxy
     settings.  This is **not** required for direct Internet
     connections
  #. Open the browser on Aakash and visit this link:
     **http://aakashlabs.org/builds/ABT.apk**. Download and install the
     APK.
  #. Click on **ABT icon** from android's application menu and allow
     it to download ``~300MB`` image file.
  #. The download and uncompress process will take some while, so
     please be patience. When download completes, it will prompt for
     reboot. Please say ``Yes`` to reboot.
  #. After reboot, you can again visit android's application menu and
     click on **ABT icon**
  
	
Alternate installation from your system
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  #. Connect Aakash to your system using an USB data cable.
  #. Download and extract `install.zip
     <http://aakashlabs.org/builds/install.zip>`_ on your system

  #. Extract the zip file using ::

       unzip install.zip

  #. ``cd`` to ``install`` directory ::
     
       cd install

  #. and execute ``install.sh`` ::
       
       sudo ./install.sh

  Wait for the script to copy all necessary files to Aakash. After
  successful installation the device will reboot for changes to take
  effect.

Manual installation
~~~~~~~~~~~~~~~~~~~

  #. Download compressed image to your computer from this `link
     <http://aakashlabs.org/builds/abt.tar.gz>`_. Extract it using ::
       tar -xvzf abt.tar.gz
  #. The untar process of above file will produce ``abt.img``. Copy
     ``abt.img`` to sdcard(internal or external) of your Aakash
     tablet.
  #. Then install `ABT.apk <http://aakashlabs.org/builds/ABT.apk>`_ on
     Aakash, ``shutdown`` and ``start`` Aakash to finish
     installation. Now locate **ABT icon** in your android menu to
     start using *Aakash Business Tool*
