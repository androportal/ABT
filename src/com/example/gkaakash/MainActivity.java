package com.example.gkaakash;

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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;

import com.gkaakash.controller.Organisation;
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
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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
	static String checkFlag;
	int help_option_menu_flag = 0;
	String result;
	boolean fstab_flag = true;
	File checkImg;
	File checkImgextsd;
	File checkTar;
	File checkTarExtsd;
	File help_flag;
	static boolean no_dailog=false;
	module m;
	private AlertDialog dialog;


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 'Help' menu to main page options menu
		menu.add(1,1,1,"Help");
		menu.add(1,2,2,"Import");
		return super.onCreateOptionsMenu(menu);	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * when user clicks help menu on main page,
		 * help_option_menu_flag is set to 1
		 */
		if(item.getItemId() == 1){
			//Toast.makeText(context, "help_flag_option is set to 1", Toast.LENGTH_SHORT).show();
			help_option_menu_flag = 1;
			//if running this app on emulator, comment the below line
			help_popup();
		}
		if(item.getItemId() == 2){
			//Toast.makeText(context, "help_flag_option is set to 2", Toast.LENGTH_SHORT).show();
			importorganisation();
		}
		return super.onOptionsItemSelected(item);
	}//if running this app on emulator, comment the below line


	private void importorganisation() {
		try {
			File export = new File("/mnt/sdcard/export");
			if(export.exists()){
				//copy export dir from /opt/abt/ to sdcard
				String[] command = {"rm -r /data/local/abt/opt/abt/export/","busybox cp /mnt/sdcard/export/ /data/local/abt/opt/abt/ -R"};
				module.RunAsRoot(command);

				final Organisation organisation = new Organisation();
				//get list of all exported organisations
				Object[] result = (Object[]) organisation.getAllExportedOrganisations();
				final ArrayList<ArrayList> Grid = new ArrayList<ArrayList>();
				for(Object tb : result)
				{
					Object[] t = (Object[]) tb;
					ArrayList<String> resultList = new ArrayList<String>();
					for(int i=0;i<t.length;i++){

						resultList.add((String) t[i].toString());

					}
					Grid.add(resultList);
				}

				System.out.println("grid1:"+Grid);
				//call xml for creating layout inflater(customised dialog) 
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.import_organisation, (ViewGroup) findViewById(R.id.layout_login));
				final AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
				TextView tvalertHead1 =(TextView) layout.findViewById(R.id.tvalertHead1);
				tvalertHead1.setText("Import organisation");
				
				Button btnImport =  (Button) layout.findViewById(R.id.btnImport);
				Button btnCancel =  (Button) layout.findViewById(R.id.btnCancel);
				final Spinner sOrganisation =  (Spinner) layout.findViewById(R.id.sOrganisation);
				final Spinner sYear =  (Spinner) layout.findViewById(R.id.sYear);

				//lets find duplicate words in java array and return array with unique words 
				ArrayList<String> org = new ArrayList<String>();
				Set<String> set = new HashSet<String>(Grid.get(0));
				//Converting a set to an array for String representation
				String[] array = set.toArray(new String[0]);
				
				ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, array);
				dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sOrganisation.setAdapter(dataAdapter1);
				
				sOrganisation.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						ArrayList<String> year = new ArrayList<String>();
						for (int i = 0; i < Grid.get(0).size(); i++) {
							if(sOrganisation.getSelectedItem().toString()
									.equalsIgnoreCase((String) Grid.get(0).get(i))){
								year.add((String) Grid.get(1).get(i));

							}
						}
						//add financial year
						ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
								android.R.layout.simple_spinner_item, year);
						dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						sYear.setAdapter(dataAdapter);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

				btnImport.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v) {
						System.out.println(sOrganisation.getSelectedItem());
						System.out.println(sYear.getSelectedItem().toString().substring(0,10));
						System.out.println(sYear.getSelectedItem().toString().substring(14,24));
						Object[] params = new Object[]{sOrganisation.getSelectedItem(),
								sYear.getSelectedItem().toString().substring(0,10),
								sYear.getSelectedItem().toString().substring(14,24),
								Grid.get(2).get(sOrganisation.getSelectedItemPosition()),
								Grid.get(3).get(sOrganisation.getSelectedItemPosition())};
						organisation.Import(params);
						TextView warning =  (TextView) layout.findViewById(R.id.tvWarning);
						warning.setVisibility(TextView.VISIBLE);
						warning.setText(Html.fromHtml("Organisation <b>"+sOrganisation.getSelectedItem()+
								"</b> for the financial year <b>"+sYear.getSelectedItem().toString()+
								"</b> imported successfully!"));
						
					}
				});

				btnCancel.setOnClickListener(new View.OnClickListener(){
					public void onClick(View v) {
						dialog.dismiss();
					}
				});


				dialog = builder.create();
				dialog.show();
				dialog.setCanceledOnTouchOutside(true);
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
				//customizing the width and location of the dialog on screen 
				lp.copyFrom(dialog.getWindow().getAttributes());
				lp.width = 750;
				dialog.getWindow().setAttributes(lp);
			}else{
				m.toastValidationMessage(context, "No backup file in /mnt/sdcard/export to import organisation");
			}
		} catch (Exception e) {
			m.toastValidationMessage(context, "Please try again!");
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Calling activity_main.xml which is first page of GNUKhata
		setContentView(R.layout.activity_main);
		//create object of Startup to access connection
		startup = new Startup();
		m=new module();
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
			loadDataFromAsset();
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
					m.toastValidationMessage(context, "Please check server connection");
				}
			}// end of onClick
		});// end of select_org.setOnClickListener
	}// end of addListenerOnButton() method


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


	////////////////////////////
	private void help_popup() {
		/**
		 * checks existance of:
		 * 1) /data/local/linux/etc/fstab
		 * 2) /mnt/sdcard/abt.img
		 * 3) /mnt/sdcard/abt.tar.gz or /mnt/extsd/abt.tar.gz
		 * 4) /data/data/com.example.gkaakash/files/help_flag.txt
		 * 
		 **/
		File fstab = new File("/data/local/abt/etc/fstab");
		checkImg = new File("/mnt/sdcard/abt.img");
		checkImgextsd = new File("/mnt/extsd/abt.img");
		checkTar = new File("/mnt/sdcard/abt.tar.gz");
		checkTarExtsd = new File("/mnt/extsd/abt.tar.gz");
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
			spinner("mnt/sdcard");
		} 
		else if(checkTarExtsd.exists()) {
			// extract
			// reboot
			spinner("mnt/extsd");
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

	////////////////////////////


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

	private void spinner(String extraction_source) {
		/*
		 * this method will start spinner first to show extraction progress
		 * and then extraction
		 */
		progressBar = new ProgressDialog(context);
		progressBar.setCancelable(false);
		progressBar.setMessage("Extracting files, please wait...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.show();

		// start actual extraction
		new Extract_TAR_GZ_FILE().execute(extraction_source);
	}


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
					//Toast.makeText(context, "TRUE", Toast.LENGTH_SHORT).show();
					checkFlag = "true";
				}
				else {
					//Toast.makeText(context, "FALSE", Toast.LENGTH_SHORT).show();
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






	// DOWNLOAD ??
	class DownloadFileAsync extends AsyncTask<String, String, String> {
		/**
		 * download tar.gz from URL and write in destination mnt/sdcard or mnt/extsd
		 **/
		String download_destination;
		@Override        	
		public void onPreExecute() {
			super.onPreExecute();
		}

		public String doInBackground(String... aurl) {
			int count;

			try {
				URL url = new URL(aurl[0]);
				download_destination = aurl[1];
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(
						download_destination+"/abt.tar.gz");

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
			File checkTarExists = new File(download_destination+"/abt.tar.gz");
			if (checkTarExists.exists()){
				/*
				 * Get length of tar file in MB, it should be grater than 78MB
				 * if YES, start spinner for extraction
				 */

				long fileSizeInMB = checkTarExists.length()/(1024 * 1024);      		
				if (fileSizeInMB >= 78) {
					spinner(download_destination);
				}else{
					String[] command = {"busybox rm -r "+download_destination+"/abt.tar.gz"};
					RunAsRoot(command);
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Failed to download complete file, Exiting the application!")
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
		//delete internal files during un-installation 
		public boolean deleteFile (String name){
			name = "aakash.sh";
			name = "help_flag";
			name = "copyFilesFlag.txt";
			return false;

		}
	}

	// EXTRACT CLASS
	public class Extract_TAR_GZ_FILE extends AsyncTask<String, String, String>{
		/**
		 * extract an image asynchronously to '/mnt/sdcard' or 'mnt/extsd'
		 **/
		String img_destination;
		@Override
		public void onPreExecute() {

			super.onPreExecute();
			System.out.println("finnaly in  jjj");
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
			// 
			try {      
				img_destination = params[0];
				String strSourceFile = img_destination+"/abt.tar.gz";
				String strDest = img_destination;
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
			/*
			 * check the size of abt.img before rebooting the device
			 * if it is greater than 292 MB, reboot
			 */
			File checkImgExists = new File(img_destination+"/abt.img");
			if (checkImgExists.exists()){
				long fileSizeInMB = checkImgExists.length()/(1024 * 1024);      		
				if (fileSizeInMB >= 292) {

					reboot();

				}else{
					String[] command = {"busybox rm -r "+img_destination+"/abt.img"};
					RunAsRoot(command);
					AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
					builder.setMessage("Failed to download complete abt.img, exiting the application")
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
			else {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Failed to download abt.img, exiting the application")
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


	/**
	 * @return Number of Mega bytes available on dir
	 */
	public static long getAvailableSpaceInMB(String dir){
		final long SIZE_KB = 1024L;
		final long SIZE_MB = SIZE_KB * SIZE_KB;
		long availableSpace = -1L;
		StatFs stat = new StatFs(dir);
		availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
		return availableSpace/SIZE_MB;
	}


	/**
	 * @return Number of bytes available on external storage extSD

	    public long getExternalAvailableSpaceInBytes() {
	        long availableSpace = -1L;
	        try {
	            StatFs stat = new StatFs("mnt/extsd");
	            availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return availableSpace;
	    }
	 */ 
}