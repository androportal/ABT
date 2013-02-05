package com.example.gkaakash;


import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gkaakash.controller.Account;
import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;

import android.R.drawable;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.text.InputType;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class menu extends ListActivity{
    //adding a class property to hold a reference to the controls
    String  voucherTypeFlag;
    private int group1Id = 1;
    int Edit = Menu.FIRST;
    int Delete = Menu.FIRST +1;
    int Finish = Menu.FIRST +2;
    AlertDialog dialog;
    final Context context = this;
	static String fromday, frommonth, fromyear, today, tomonth, toyear; 
    private Integer client_id;
    private Account account;
    private Preferences preferences;
    private Organisation organisation;
    AlertDialog help_dialog;
    static String financialFromDate;
	static String financialToDate;
	static String givenfromDateString;
	static String givenToDateString;
	DecimalFormat mFormat;
	static boolean validateDateFlag;
	static String selectedAccount;
	static boolean cleared_tran_flag;
	static boolean narration_flag;
	static ArrayList<String> accdetailsList;
    static String orgtype;
    String orgname;
    
    //adding list items to the newly created menu list
    String[] menuOptions = new String[] { "Create account", "Transaction", "Reports",
            "Preferences","Bank Reconciliation","Help","About" };

    /*
    //adding options to the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(group1Id, Edit, Edit, "Edit");
    menu.add(group1Id, Delete, Delete, "Delete");
    menu.add(group1Id, FinishAlertDialog help_dialog;, Finish, "Finish");
    return super.onCreateOptionsMenu(menu); 
    }
    
    //code for the actions to be performed on clicking options menu goes here ...
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
        Toast msg = Toast.makeText(menu.this, "Menu 1", Toast.LENGTH_LONG);
        msg.show();
        return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
    
     @Override
     public void onBackPressed() {
    	 Intent intent = new Intent(getApplicationContext(), MainActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         startActivity(intent); 
     }
     
    //on load...
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = new Account();
        preferences = new Preferences();
        organisation = new Organisation();
        client_id= Startup.getClient_id();
        
        //get financial from and to date, split and store day, month and year in seperate variable
       	financialFromDate =Startup.getfinancialFromDate();  	   	
	   	String dateParts[] = financialFromDate.split("-");
	   	fromday  = dateParts[0];
	   	frommonth = dateParts[1];
	   	fromyear = dateParts[2];
	   	
	   	financialToDate = Startup.getFinancialToDate();
	   	String dateParts1[] = financialToDate.split("-");
	   	today  = dateParts1[0];
	   	tomonth = dateParts1[1];
	   	toyear = dateParts1[2];
	   	
	   	//for two digit format date for dd and mm
	  	mFormat= new DecimalFormat("00");
	  	mFormat.setRoundingMode(RoundingMode.DOWN);
        
        //calling menu.xml and adding menu list into the page
        setListAdapter(new ArrayAdapter<String>(this, R.layout.menu,menuOptions));
 
        //getting the list view and setting background
        final ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setBackgroundColor(R.drawable.dark_gray_background);
        listView.setCacheColorHint(Color.TRANSPARENT);
        
        
        
        //when menu list items are clicked, code for respective actions goes here ...
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                if(position == 0)
                {
                    MainActivity.tabFlag = true;
                    Intent intent = new Intent(context, account_tab.class);
                    // To pass on the value to the next page
                    startActivity(intent);
                }
                
                //for "transaction"
                if(position == 1)
                {
                    Intent intent = new Intent(context, voucherMenu.class);
                    // To pass on the value to the next page
                    startActivity(intent);
                }
                AlertDialog help_dialog;
                //for "reports"
                if(position == 2)
                {
                    Intent intent = new Intent(context, reportMenu.class);
                    // To pass on the value to the next page
                    startActivity(intent);                     
                }
                
                //for "adding project", adding popup menu ...
                if(position == 3)
                {                	
                	final CharSequence[] items = { "Edit organisation details", "Add/Edit/Delete project" };
                	//creating a dialog box for popup
                	AlertDialog.Builder builder = new AlertDialog.Builder(context);
                	//setting title
                	builder.setTitle("Select preference");
                	//adding items
                	builder.setItems(items, new DialogInterface.OnClickListener() {
                		public void onClick(DialogInterface dialog1, int pos) {
                			//code for the actions to be performed on clicking popup item goes here ...
                			switch (pos) {
     	      	        case 0:
     	      	        {
     	      	        	
     	      	        	MainActivity.editDetails=true;
     	      	        	Object[] editDetails = (Object[])organisation.getOrganisation(client_id);
     	      	        	accdetailsList = new ArrayList<String>();
     	      	        	for(Object row2 : editDetails){
     	      	        		Object[] a2=(Object[])row2;
     	      	        		ArrayList<String> accdetails = new ArrayList<String>();
     	                        for(int i=0;i<a2.length;i++){
     	                        	accdetails.add((String) a2[i].toString());
     	                        }
     	                        accdetailsList.addAll(accdetails);
     	      	        	}
     	                             
     	      	        	//System.out.println("details:"+accdetailsList);
     	                           
     	      	        	Intent intent = new Intent(context, orgDetails.class);
     	      	        	// To pass on the value to the next page
     	      	        	startActivity(intent);
     	      	        }break;
     	      	        case 1:
     	      	        {
     	      	        	Intent intent = new Intent(context, addProject.class);
     	      	        	// To pass on the value to the next page
     	      	        	startActivity(intent);
     	      	        	
     	      	        }break;
                			}
                		}
                	});
                	//building a complete dialog
                	dialog=builder.create();
                	dialog.show();
     	
                }
                //bank reconcilition
                if(position == 4){
                	
                	//call the getAllBankAccounts method to get all bank account names
					Object[] accountnames = (Object[]) account.getAllBankAccounts(client_id);
					// create new array list of type String to add account names
					List<String> accountnamelist = new ArrayList<String>();
					for(Object an : accountnames)
					{	
						accountnamelist.add((String) an); 
					}	
					
					if(accountnamelist.size() <= 0){
						String message = "Bank reconciliation statement cannot be displayed, Please create bank account!";
						toastValidationMessage(message);
						}
					else{
                	
	                	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
						View layout = inflater.inflate(R.layout.bank_recon_index, (ViewGroup) findViewById(R.id.layout_root));
						//Building DatepPcker dialog
						AlertDialog.Builder builder = new AlertDialog.Builder(context);
						builder.setView(layout);
						builder.setTitle("Bank reconcilition");
						
						//populate all bank account names in accountname dropdown(spinner)
						final Spinner sBankAccounts = (Spinner)layout.findViewById(R.id.sBankAccounts);
						ArrayAdapter<String> da = new ArrayAdapter<String>(menu.this, 
													android.R.layout.simple_spinner_item,accountnamelist);
				  	   	da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				  	   	sBankAccounts.setAdapter(da);
						
						final DatePicker ReconFromdate = (DatePicker) layout.findViewById(R.id.dpsetReconFromdate);
						ReconFromdate.init(Integer.parseInt(fromyear),(Integer.parseInt(frommonth)-1),Integer.parseInt(fromday), null);
					   	
					   	final DatePicker ReconT0date = (DatePicker) layout.findViewById(R.id.dpsetReconT0date);
					   	ReconT0date.init(Integer.parseInt(toyear),(Integer.parseInt(tomonth)-1),Integer.parseInt(today), null);
						
					   	final CheckBox cbClearedTransaction = (CheckBox)layout.findViewById(R.id.cbClearedTransaction);
					   	final CheckBox cbNarration = (CheckBox)layout.findViewById(R.id.cbReconNarration);
					   	
						builder.setPositiveButton("View",new  DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
							
								if(cbClearedTransaction.isChecked()){
							   		cleared_tran_flag = true;
							   	}
							   	else{
							   		cleared_tran_flag = false;
							   	}
							   	
							   	if(cbNarration.isChecked()){
							   		narration_flag = true;
							   	}
							   	else{
							   		narration_flag = false;
							   	}
								
								selectedAccount = sBankAccounts.getSelectedItem().toString();
								
								System.out.println("i am account"+selectedAccount);
								validateDate(ReconFromdate, ReconT0date, "validatebothFromToDate");
								
								
								if(validateDateFlag){
									Intent intent = new Intent(context, bankReconciliation.class);
									// To pass on the value to the next page
									startActivity(intent);
								}
							}
	
							
							
						});
						
						builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
	
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
							
						});
						dialog=builder.create();
		        		dialog.show();
		        		
		        		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
						//customizing the width and location of the dialog on screen 
						lp.copyFrom(dialog.getWindow().getAttributes());
						lp.width = 700;
						dialog.getWindow().setAttributes(lp);
					}
					
                }
                
                //for help
                if(position == 5){
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.help_popup,
                            (ViewGroup) findViewById(R.id.layout_root));
            
                    // builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
                    builder.setView(layout);
                    builder.setTitle("Help");
                    CheckBox cbHelp = (CheckBox)layout.findViewById(R.id.cbHelp);
                    cbHelp.setVisibility(CheckBox.GONE);
                    help_dialog = builder.create();
                    help_dialog.show();
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    // customizing the width and location of the dialog on screen
                    lp.copyFrom(help_dialog.getWindow().getAttributes());
                    lp.width = 700;
                    help_dialog.getWindow().setAttributes(lp);
                    help_dialog.setCancelable(true);
                }
                
                //for about
                if(position == 6){
                    AlertDialog about_dialog;
                    final SpannableString s = 
                            new SpannableString(context.getText(R.string.about_para));
                            Linkify.addLinks(s, Linkify.WEB_URLS);
                            

                            // Building DatepPcker dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    context);
                            builder.setTitle("Aakash Business Tool");
                            builder.setMessage( s );
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    
                                }
                              
                            });
                            
                            about_dialog = builder.create();
                            about_dialog.show();
                            
                            ((TextView)about_dialog.findViewById(android.R.id.message))
                            .setMovementMethod(LinkMovementMethod.getInstance());
                            
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            // customizing the width and location of the dialog on screen
                            lp.copyFrom(about_dialog.getWindow().getAttributes());
                            lp.width = 600;
                            
                            about_dialog.getWindow().setAttributes(lp);
                }
            } 
        });
    }
     
    
    private boolean validateDate(DatePicker fromdate, DatePicker todate, String flag){
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = sdf.parse(financialFromDate);
	    	Date date2 = sdf.parse(financialToDate);
			
	    	Calendar cal1 = Calendar.getInstance(); //financial from date
	    	Calendar cal2 = Calendar.getInstance(); //financial to date
	    	Calendar cal3 = Calendar.getInstance(); //from date
	    	Calendar cal4 = Calendar.getInstance(); //to date
	    	
	    	cal1.setTime(date1);
	    	cal2.setTime(date2);
	    	
			if("validatebothFromToDate".equals(flag)){
				int FromDay = fromdate.getDayOfMonth();
			   	int FromMonth = fromdate.getMonth();
			   	int FromYear = fromdate.getYear();
			   	
			   	givenfromDateString = mFormat.format(Double.valueOf(FromDay))+ "-" 
			   	+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(FromMonth))))+ 1))) + "-" 
			   	+ FromYear;
			   	
			   	Date date3 = sdf.parse(givenfromDateString);
			   	cal3.setTime(date3);
			}
			
			int T0Day = todate.getDayOfMonth();
		   	int T0Month = todate.getMonth();
		   	int T0Year = todate.getYear();
		   	
		   	givenToDateString = mFormat.format(Double.valueOf(T0Day))+ "-" 
		   	+(mFormat.format(Double.valueOf(Integer.parseInt((mFormat.format(Double.valueOf(T0Month))))+ 1))) + "-" 
		   	+ T0Year;
		   	
		   	Date date4 = sdf.parse(givenToDateString);
		   	cal4.setTime(date4);  
			
	    	//System.out.println("all dates are...........");
	    	//System.out.println(financialFromDate+"---"+financialToDate+"---"+givenfromDateString+"---"+givenToDateString);
	    	
	    	if("validatebothFromToDate".equals(flag)){
	    		if(((cal3.after(cal1)&&(cal3.before(cal2))) || (cal3.equals(cal1) || (cal3.equals(cal2)))) 
	        			&& ((cal4.after(cal1) && (cal4.before(cal2))) || (cal4.equals(cal2)) || (cal4.equals(cal1)))){
	        		
	        		validateDateFlag = true;
	        	}
	        	else{
	        		String message = "Please enter proper date";
	        		toastValidationMessage(message);
	        		validateDateFlag = false;
	        	}
	    	}
	    	else {
	    		if((cal4.after(cal1) && cal4.before(cal2)) || cal4.equals(cal1) || cal4.equals(cal2) ){
					
	    			validateDateFlag = true;
	        	}
	        	else{
	        		String message = "Please enter proper date";
	        		toastValidationMessage(message);
	        		validateDateFlag = false;
	        	}
	    	}
    	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return validateDateFlag;
	}
    
    public void toastValidationMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                
                            }
                        });
                
        AlertDialog alert = builder.create();
        alert.show();
        
    } 
   
    
}
