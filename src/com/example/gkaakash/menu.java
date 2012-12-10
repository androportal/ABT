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
    Button add;
    private TableLayout projectTable;
    int rowsSoFar=0;
    int count;
	static String fromday, frommonth, fromyear, today, tomonth, toyear; 
    static int idCount;
    EditText etProject,etdynamic;
    private Integer client_id;
    private Account account;
    private Preferences preferences;
    private Organisation organisation;
    ArrayList<String> finalProjlist;
    protected String projectname;
    protected ArrayList<String>[] projectnamelist;
    boolean projectExistsFlag = false;
    private boolean setProject;
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
    
    //adding list items to the newly created menu list
    String[] menuOptions = new String[] { "Create account", "Transaction", "Reports",
            "Add projects","Bank Reconciliation","Help","About" };

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
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.add_project, (ViewGroup) findViewById(R.id.layout_root));
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(layout);
                    builder.setTitle("Add projects");
                    etProject = (EditText)layout.findViewById(R.id.etProjectname);
                    projectTable = (TableLayout)layout.findViewById( R.id.projecttable );
                    add=(Button) layout.findViewById(R.id.addProject);
                    add.setOnClickListener(new OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            addButton();
                        }

                        
                    });
                    builder.setPositiveButton("Add",new  DialogInterface.OnClickListener(){

                        public void onClick(DialogInterface arg0, int which) {
                            EditText projectName;
                            String proj_name;
                            View v1 = null;
                            List<String> secondProjlist=new ArrayList<String>();
                            projectname = etProject.getText().toString();
                           
                            for(int i = 1; i <= idCount ; i++){  
                                 v1 = dialog.findViewById(i);
                                if(v1 != null){
                                    
                                    projectName = (EditText)dialog.findViewById(i);
                                    proj_name= projectName.getText().toString();
                                    if(!"".equals(proj_name)){
                                        secondProjlist.add(proj_name);
                                    }
                                }
                            }
                            
                            finalProjlist = new ArrayList<String>();
                            if(!"".equals(projectname)){
                                finalProjlist.add(projectname);
                            }
                           
                            finalProjlist.addAll(secondProjlist);
                            AlertDialog help_dialog;
                          //call the getAllProjects method to get all projects
                            Object[] projectnames = (Object[]) organisation.getAllProjects(client_id);
                            // create new array list of type String to add gropunames
                            List<String> projectnamelist = new ArrayList<String>();
                            projectnamelist.add("No Project");
                            for(Object pn : projectnames)
                            {    
                                Object[] p = (Object[]) pn;
                                projectnamelist.add((String) p[1]); //p[0] is project code & p[1] is projectname
                            }    
                            
                            String ac;
                            boolean  flag = false;
                            String nameExists = "";
                            
                            for (int i = 0; i < finalProjlist.size(); i++) {
                                ac = finalProjlist.get(i);
                                for (int j = 0; j < finalProjlist.size(); j++)
                                {
                                    if (i!=j)
                                    {
                                        if(ac.equals(finalProjlist.get(j)))
                                        {
                                            flag = true;
                                            break;
                                        }
                                    }
                                    else
                                    {
                                        flag = false;
                                    }
                                }
                            }
                            
                            if(flag == true){
                                String message = "Project names can not be same";
                                toastValidationMessage(message);
                            }
                            else{
                                for(int i=0;i<finalProjlist.size();i++){
                                    for(int j=0;j<projectnamelist.size();j++){
                                        if((finalProjlist.get(i).toString()).equals(projectnamelist.get(j).toString())){
                                            projectExistsFlag = true;
                                            nameExists = finalProjlist.get(i).toString();
                                            break;
                                        }
                                        else{
                                            projectExistsFlag = false;
                                        }
                                    }
                                    if(projectExistsFlag == true){
                                        break;
                                    }
                                }
                                
                                 if(etProject.length()<1){
                                     String message = "Please enter project name";
                                     toastValidationMessage(message);
                                 }
                                 else if(projectExistsFlag == true){
                                    String message = "Project "+nameExists+" already exists";
                                    toastValidationMessage(message);
                                    }
                                 else
                                  {
                                    setProject = preferences.setProjects(finalProjlist,client_id);
                                     //To pass on the activity to the next page
                                    
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setMessage("Project added successfully!")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok",
                                                    new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int id) {
                                                            
                                                        }
                                                    });
                                            
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                    etProject.setText("");
                                    projectTable.removeAllViews();
                                   }
                            }
                           

                        }
                            
                             
                         });
                    
                    builder.setNegativeButton("Cancel",new  DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            
                        }
                        });
                    dialog=builder.create();
                    ((Dialog) dialog).show();
                      WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    //customizing the width and location of the dialog on screen 
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.height = 300;
                    dialog.getWindow().setAttributes(lp);
                    }
                
                //bank reconcilition
                if(position == 4){
                	String message = "This functionality is not implemented yet";
                	toastValidationMessage(message);
                	
                	/*
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
					*/
                }
                
                //for help
                if(position == 5){
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.help_popup,
                            (ViewGroup) findViewById(R.id.layout_root));
            
                    // builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(menu.this);
                    builder.setView(layout);
                    builder.setIcon(R.drawable.logo);
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
                            builder.setTitle("GNUKhata");
                            builder.setIcon(R.drawable.logo);
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
    
     /***
     * Gets all the information necessary to delete itself from the constructor.
     * Deletes itself when the button is pressed.
     */
    private static class RowRemover implements OnClickListener {
        public TableLayout list;
        public TableRow rowToBeRemoved;
       
        /***
         * @param list    The list that the button belongs to
         * @param row    The row that the button belongs to
         */
        public RowRemover( TableLayout list, TableRow row ) {
            this.list = list;
            this.rowToBeRemoved = row;
        }
       
        public void onClick( View view ) {
            list.removeView( rowToBeRemoved );
           
        }
    }
    
    public void addButton() {
        //projectTable.setVisibility(TableLayout.VISIBLE);
        TableRow newRow = new TableRow( projectTable.getContext() );
        newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        //newRow.addView(child, width, height)
       
        EditText etdynamic = new EditText(newRow.getContext());
        etdynamic.setText( "" );
        etdynamic.setHint("Tap to enter                              ");
        etdynamic.setWidth(215); //for emulator 215
        etdynamic.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etdynamic.setId(++rowsSoFar);
       
       
        idCount ++;
         
        //actionButton.setText( "Action: " + ++rowsSoFar );
        Button removeSelfButton = new Button( newRow.getContext() );
        removeSelfButton.setText( "  -  " ); //for tablet ***** add  space
       
        //removeSelfButton.setBackgroundResource(R.drawable.button_plus_green);
        //removeSelfButton.setBackgroundColor(color)
        // pass on all the information necessary for deletion
        removeSelfButton.setOnClickListener( new RowRemover( projectTable, newRow ));
       
        newRow.addView(etdynamic);
       
       
        newRow.addView( removeSelfButton );
        projectTable.addView(newRow);
       
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