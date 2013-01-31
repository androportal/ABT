%%%%%%%%%%
How to install
%%%%%%%%%%
Simple and recommended install procedure
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  #. This process requires a working internet connection. 
  #. If you're behind proxy, then do appropriate settings to bypass
     your proxy server. You will find proxy setting in ``Settings`` ->
     ``WiFi`` -> ``Advance Options``. Also bypass ``127.0.0.1`` proxy
     settings.  This is **not** required for direct internet
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
  
Alternate installation
~~~~~~~~~~~~~~~~~~~~~~

  #. Download compressed image ``abt.img`` to your computer from this
     `link <http://aakashlabs.org/builds/abt.tar.gz>`_. The untar or
     unzip process of above file will produce ``abt.img``. Copy
     ``abt.img`` to sdcard(internal or external) of your Aakash
     tablet.
  #. Then install `ABT.apk <http://aakashlabs.org/builds/ABT.apk>`_ in
     your Aakash, ``shutdown`` and ``start`` your Aakash to finish
     installation. Now locate **ABT icon** in your android menu.
	
Yet another alternate Installation
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

  #. Download and extract `install.zip
     <https://github.com/downloads/androportal/ABTcore/install.zip>`_
     in your system
  #. Connect Aakash to your system using an USB data cable.
  #. Download compressed image ``abt.img`` from this `link
     <http://aakashlabs.org/builds/abt.tar.gz>`_ , uncompress and copy
     to ``install`` directory ::
       
       cp -v abt.img install

  #. ``cd`` to ``install`` directory ::
      
       cd install
      
  #. and execute ``install.sh`` ::
     
       sudo ./install.sh

  Wait for the script to copy all necessary files to Aakash. After
  successful installation the device will reboot for changes to take
  effect.
   
   
