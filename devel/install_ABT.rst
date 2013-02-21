Auto installation of ABT dependencies through apk
=================================================

This tutorial includes ABT's **installation** process. It is as follows:

* This process requires a ``working Internet connection``.

* apk checks for the internet connection, if it is ``ON``, it downloads the ABT.img file in ``/mnt/sdcard/`` of device and if ``OFF``, exits the application.
  
* When download completes, it will prompt for ``reboot``.

**The main activity**

* The activity ``src/com/example/gkaakash/MainActivity.java`` was entiltled as ``first`` ABT Activity.

* The activity contains the essential and required import like

	.. code-block:: java

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

* ``OnCreate`` method calls all the required methods at load time. 

	.. code-block:: java

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
        			if(checkFlag=="false"){
        				help_dialog.dismiss();
        			}
        	
       	 		}
		}


* The below method fetches data``(preinstall.sh and aakash.sh)`` from **asset** folder and copies them 
  to their appropriate locations.

* It also writes a file naming ``copyFilesFlag.txt``, once both the files are copied to their locations.

	.. code-block:: java

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

* The below method gives ``root`` access to run command as root user.

	.. code-block:: java

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

	.. code-block:: java

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

				    if(isInternetOn()) {
					// INTERNET IS AVAILABLE, DO STUFF..
					Toast.makeText(context, "Connected to network", Toast.LENGTH_SHORT).show();

					/**
					 * download image from aakashlabs.org
					 **/
					String url = "http://aakashlabs.org/builds/abt.tar.gz";
					String dest;
	
					/*
					 * check free space available in /mnt/sdcard, it should be greater than 380MB(292+78)
					 * if YES, download abt.tar.gz
					 */
	
					if(getAvailableSpaceInMB("mnt/sdcard") > 380L){
		
						Toast.makeText(context, "Downloading abt.tar.gz in /mnt/sdcard", Toast.LENGTH_SHORT).show();
						dest = "mnt/sdcard";
						startDownloadProgressBar(dest);
						new DownloadFileAsync().execute(url,dest);
		
					}else{
						AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
						builder.setMessage("No enough space on sdcard, exiting the application!")
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
					/*else{
		
						if(getAvailableSpaceInMB("mnt/extsd") > 380L){
			
							Toast.makeText(context, "we are in extsd", Toast.LENGTH_SHORT).show();
							dest = "mnt/extsd";
							startDownloadProgressBar(dest);
			
							//below code invokes the class DownloadFileAsync which asynchronously starts downloading.
			
					    		new DownloadFileAsync().execute(url,dest);
					    
						}else{
			
							Toast.makeText(context, "failed to download abt.tar.gz, No space available on sdcard", Toast.LENGTH_SHORT).show();
						}

					} */

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

					} 
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


* Below method is used to start the ``progress bar`` for downloading. Using ``cancel button`` user can stop downloading process.

	.. code-block:: java	
		
		private void startDownloadProgressBar(final String dest) {
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
				                         String[] command = {"rm "+dest+"/abt.tar.gz"};
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


* The below method checks for ``internet availability`` and returns a boolean result.

	.. code-block:: java
	
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

* The below method creates a progress bar for ``extraction`` and invokes ``Extract_TAR_GZ_FILE()`` 
  class for extraction.   

	.. code-block:: java
		
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

* The below method starts the application and shows the initial ``help dialog`` for the application 
  with a ``check box``,if checked it will never the show the dialog again.

* If check box is ``checked`` it writes a file naming ``help_flag.txt`` in the internel storage.

* If check box is again ``unchecked`` it deletes the file from the respective location.

	.. code-block:: java

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

* The below class carries out the complete ``downloading process`` in number of steps in asynchronous manner.

	.. code-block:: java

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
		}

* It deletes files such as aakash.sh, help_flag and copyFilesFlag.txt from apk's file storage location ie. ``/data/data/package_name/files/file_name`` once the application is ``uninstalled`` 
  .

	.. code-block:: java
	
		//delete internal files during un-installation 
		public boolean deleteFile (String name){
		    name = "aakash.sh";
		    name = "help_flag";
		    name = "copyFilesFlag.txt";
		    return false;
	        }

* The below class carries out the complete ``extraction`` process in back ground.

	.. code-block:: java

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



* The below method helps to ``reboot`` the device so that the filesystem is mounted.

	.. code-block:: java

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


* The below method provides ``Yes/No`` option before exiting the application. 

* If the user clicks on ``Yes`` it kills the process, otherwise resumes the process.

	.. code-block:: java

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
