%%%%%%%%%%
How to install
%%%%%%%%%%
Simple and recommended install procedure
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  #. This process requires a working Internet connection. 
  #. If you're behind proxy, then do appropriate settings to bypass
     your proxy server. You will find proxy setting in ``Settings`` ->
     ``WiFi`` -> ``Advance Options`` and also bypass ``127.0.0.1`` proxy
     settings.  This is **not** required for direct Internet
     connections.
  #. Open the browser on Aakash and visit this link:
     **http://aakashlabs.org/builds/ABT.apk**. Download and install the
     APK.
  #. Click on **ABT icon** from the list of applications installed on device and allow
     it to download image file.
  #. Downloading and extraction process will take some time. Once done with this process, it will ask to reboot the device.
  #. After reboot, application is ready to use.
  
	
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
  successful installation reboot the device.

Manual installation
~~~~~~~~~~~~~~~~~~~

  #. Download compressed image to your computer from this `link
     <http://aakashlabs.org/builds/abt.tar.gz>`_. Extract it using ::
       tar -xvzf abt.tar.gz
  #. Extraction process will produce ``abt.img``. Copy
     ``abt.img`` to sdcard(internal or external) of your Aakash
     tablet.
  #. Then install `ABT.apk <http://aakashlabs.org/builds/ABT.apk>`_ on
     Aakash, it will ask the user to reboot the device to finish
     installation. Now user can locate **ABT icon** in the application list.
