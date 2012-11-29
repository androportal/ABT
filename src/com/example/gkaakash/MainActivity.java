package com.example.gkaakash;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.gkaakash.controller.Startup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


public class MainActivity extends Activity {
    //Add a class property to hold a reference to the button
    Button create_org;
    Startup startup;
    private View select_org;
    private Object[] orgNameList;
    Spinner getOrgNames;
    String checkFlag;
    final Context context = this;
    static Boolean tabFlag = false;
    private int group1Id = 1;
    int Help = Menu.FIRST;
    AlertDialog help_dialog;
    int help_option_menu_flag = 0;
    static Boolean searchFlag=false;
    static Boolean nameflag=false;
    static boolean reportmenuflag;

     public boolean onCreateOptionsMenu(Menu menu) {
            menu.add(group1Id, Help, Help, "Help");
          
            return super.onCreateOptionsMenu(menu); 
            }    
            //code for the actions to be performed on clicking options menu goes here ...
             @Override
             public boolean onOptionsItemSelected(MenuItem item) {
               
                switch (item.getItemId()) {
                case 1:
                    help_option_menu_flag = 1;
                    help_popup();
                }
                return super.onOptionsItemSelected(item);
            }
    
    
    
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
        //help_popup();
        
   }// End Of onCreate method
    
    public void help_popup() {
        File help_flag = new File("/data/data/com.example.gkaakash/files/help_flag.txt");
        
        if(help_option_menu_flag == 1 || !help_flag.exists()){
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.help_popup,
                    (ViewGroup) findViewById(R.id.layout_root));
    
            // builder
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setView(layout);
            builder.setIcon(R.drawable.logo);
            builder.setTitle("Help");
            CheckBox cbHelp = (CheckBox)layout.findViewById(R.id.cbHelp);
            
            if(help_option_menu_flag == 1) { 
                if (help_flag.exists()) {
                    cbHelp.setChecked(true);
                }
            }
            else {
                cbHelp.setChecked(false);
            }
            
            cbHelp.setOnClickListener(new OnClickListener() {
    
                public void onClick(View v) {
              
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
                    }
                }
    
                private void RunAsRoot(String[] command) {
                    // run as a system command
                    try {
                        Process process = Runtime.getRuntime().exec("su");
                        DataOutputStream os = new DataOutputStream(process.getOutputStream());
                        for (String tmpmd : command){
                                os.writeBytes(tmpmd +"\n" );
                        }              
                        os.writeBytes("exit\n");
                        os.flush();
                      
                    }catch (IOException e) {
                           e.printStackTrace();
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
    }
    
    
     //Attach a listener to the click event for the button
    void addListenerOnButton() {
    
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
        		AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Please check server connection")
                        .setCancelable(false)
                        .setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       //do nothing
                                    }
                                });
                       
                AlertDialog alert = builder.create();
                alert.show();    
        	}
        }// end of onClick
        });// end of select_org.setOnClickListener
}// end of addListenerOnButton() method


    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
        .setCancelable(false)
        .setPositiveButton("Yes",
        new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        
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