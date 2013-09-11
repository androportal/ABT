package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;
import com.gkaakash.controller.Account;
import com.gkaakash.controller.Group;
import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class createAccount<group> extends Activity{
    // Declaring variables
	static Object[] accCodeCheckFlag_Obj;
	String accCodeCheckFlag;
	int setFlag = 0;
    TextView tvaccCode, tvDbOpBal, tvOpBal,tvOpBalRupeeSymbol,tvAccName,tvAccCode;
    EditText etaccCode, etDtOpBal, etOpBal,etAccCode;
    Spinner sgrpName,sSearchBy,sAccName;
    Button btnCreateAccSave,btnCreateAccFinish,btnokdialog;
    private String newsubgrpname;
    static Integer client_id;
    AlertDialog dialog;
    final Context context = this;
    Dialog screenDialog;
    private Group group;
    private Spinner ssubGrpName;
    private TextView tvSubGrp;
    private EditText etSubGrp;
    protected String selGrpName;
    String selSubGrpName;
    private EditText etAccName;
    protected String accountname;
    protected String accountcode;
    protected String openingbalance;
    private Account account;
    private EditText etDrBal;
    private EditText etCrBal;
    private EditText etDiffbal;
    private Object drbal;
    private Object crbal;
    private Object diffbal;
    private Preferences preferencObj;
    static String finishflag;
    static final int ID_SCREENDIALOG = 1;
    private static String groupChar;
    private String account_code;
    String sub_grp_name;
    private String subgroup_exist;
    private String accountcode_exist;
    protected String accountname_exist;   
    module m;
    boolean setGroupFlag = false;
    String setGroupName ,setSubGroupName;
    static String IPaddr;   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Calling create_account.xml
        setContentView(R.layout.create_account);
        m=new module();
        IPaddr = MainActivity.IPaddr;
	    System.out.println("in createorg"+IPaddr);
        etOpBal = (EditText) findViewById(R.id.etOpBal);

        try {
            // create the object of Group class
            group = new Group(IPaddr);
            account = new Account(IPaddr);
            preferencObj= new Preferences(IPaddr);
            
            // getting client id 
            client_id = Startup.getClient_id();
            
            // Request a reference to the button from the activity by calling
            // “findViewById” and assign the retrieved button to an instance variable
            tvaccCode = (TextView) findViewById(R.id.tvAccCode);
            etaccCode = (EditText) findViewById(R.id.etAccCode);
            tvSubGrp = (TextView) findViewById(R.id.tvSubGrp);
            etSubGrp = (EditText) findViewById(R.id.etSubGrp);
            etAccName= (EditText) findViewById(R.id.etAccName);
            sgrpName = (Spinner) findViewById(R.id.sGroupNames);
            ssubGrpName = (Spinner) findViewById(R.id.sSubGrpNames);
            etDrBal = (EditText) findViewById(R.id.etDrBal);
            etCrBal = (EditText) findViewById(R.id.etCrBal);
            etDiffbal = (EditText) findViewById(R.id.etDiffBal);
            
            // call getPrefernece to get set preference related to account code flag   
            accCodeCheckFlag_Obj = preferencObj.getPreferences(new Object[]{"1"},client_id);
			accCodeCheckFlag=(String)accCodeCheckFlag_Obj[0];
			setFlag =(Integer)accCodeCheckFlag_Obj[1];
			
			if(setFlag == 0){
    	 
	            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.ledger, (ViewGroup) findViewById(R.id.layout_root));
				//Building DatepPcker dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
				builder.setTitle("Set manual account code");
				builder.setCancelable(false);
				
				//set invisibility of unwanted elements
				TableRow row = (TableRow)layout.findViewById(R.id.acc_row);
				row.setVisibility(View.GONE);
				row = (TableRow)layout.findViewById(R.id.cleared_row);
				row.setVisibility(View.GONE);
				row = (TableRow)layout.findViewById(R.id.project_row);
				row.setVisibility(View.GONE);
				row = (TableRow)layout.findViewById(R.id.from_btn_row);
				row.setVisibility(View.GONE);
				row = (TableRow)layout.findViewById(R.id.to_btn_row);
				row.setVisibility(View.GONE);
				
				TextView tv = (TextView)layout.findViewById(R.id.tvcbNarration);
				tv.setText("Set manual account code");
				final CheckBox cb = (CheckBox)layout.findViewById(R.id.cbNarrations);
				Button btnView = (Button)layout.findViewById(R.id.btnView);
				btnView.setText("Confirm");
				btnView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						//Setting the account code flag value
						if (cb.isChecked()) {
							//System.out.println("checked:");
							preferencObj.setPreferences(new Object[]{"1","manually",1},client_id);
							
							accCodeCheckFlag = "manually";
			                etaccCode.setVisibility(EditText.VISIBLE);
			                tvaccCode.setVisibility(TextView.VISIBLE);
						}
						else{
							preferencObj.setPreferences(new Object[]{"1","automatic",2},client_id);
						}
						dialog.dismiss();
					}
				});
				 
				Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
				btnCancel.setVisibility(View.GONE);
				
				dialog=builder.create();
	    		dialog.show();
			}
            System.out.println("FLAG IS"+accCodeCheckFlag);
            // Setting visibility depending upon account code flag value
            if (accCodeCheckFlag.equalsIgnoreCase("automatic")) {
                etaccCode.setVisibility(EditText.GONE);
                tvaccCode.setVisibility(TextView.GONE);
            } else {
                etaccCode.setVisibility(EditText.VISIBLE);
                tvaccCode.setVisibility(TextView.VISIBLE);
            }
            
            getTotalBalances();
            
            getExistingGroupNames();
        } catch (Exception e) {
           m.toastValidationMessage(context, "Please try again");
        }
        
        addListeneronButton();
        
        //creating interface to listen activity on Item 
        addListenerOnItem();
        
        addEditTextListner();
    }
    
    
    private void getTotalBalances() {
        drbal = account.getDrOpeningBalance(client_id);
        crbal = account.getCrOpeningBalance(client_id);
        diffbal =  account.getDiffInBalance(client_id);
        
        // setting text values in respective Edit Text fields
        etDrBal.setText(drbal.toString());
        etCrBal.setText(crbal.toString());
        etDiffbal.setText(String.format("%.2f",diffbal ));
    }

    
    // It give list of all existing groupname
    void getExistingGroupNames(){
        //call the getAllGroups method to get all groups
        Object[] groupnames = (Object[]) group.getAllGroups(client_id);
        // create new array list of type String to add gropunames
        List<String> groupnamelist = new ArrayList<String>();
        // create new array list of type Integer to add gropcode
        List<Integer> groupcodelist = new ArrayList<Integer>();
        
        for(Object gs : groupnames)
        {    
            Object[] g = (Object[]) gs;
            groupcodelist.add((Integer) g[0]); //groupcode
            groupnamelist.add((String) g[1]); //groupname
            //groupdesc.add(g[2]); //description
        }    
        // creating array adaptor to take list of existing group name
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, groupnamelist);
        //set resource layout of spinner to that adaptor
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set adaptor with groupname list in spinner
      
       if(setGroupFlag == false)
       {
    	   //Toast.makeText(this,"false", Toast.LENGTH_LONG).show();
        	sgrpName.setAdapter(dataAdapter);
       }else
        {
    	   //Toast.makeText(this,"false", Toast.LENGTH_LONG).show();
        	int groupnameposition = dataAdapter.getPosition(setGroupName);
        	sgrpName.setAdapter(dataAdapter);
        	sgrpName.setSelection(groupnameposition);
        }
        
    }// End getExistingGroupNames()
    
    
    // method addListnerOnItem() will implement OnItemSelectedListner
    void addListenerOnItem(){
    	//Attach a listener to the states Type Spinner to get dynamic list of subgroup name
    	sgrpName.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
                // Retrieving the selected name from the group name Spinner and
                // assigning it to a variable
                selGrpName = parent.getItemAtPosition(position).toString();
                tvOpBal = (TextView) findViewById(R.id.tvOpBal);
                tvOpBalRupeeSymbol = (TextView) findViewById(R.id.tvOpBalRupeeSymbol);
                System.out.println("selcted groupname:"+selGrpName);
                // Comparing the variable value to group name and setting visibility
                if ("Current Asset".equals(selGrpName)
                        | "Investment".equals(selGrpName)
                        | "Loans(Asset)".equals(selGrpName)
                        | "Fixed Assets".equals(selGrpName)
                        | "Miscellaneous Expenses(Asset)".equals(selGrpName)) {
                    etOpBal.setVisibility(EditText.VISIBLE);
                    tvOpBalRupeeSymbol.setVisibility(TextView.VISIBLE);
                    tvOpBal.setVisibility(TextView.VISIBLE);
                    tvOpBal.setText("Debit opening balance");

                } else if ("Direct Income".equals(selGrpName)
                        | "Direct Expense".equals(selGrpName)
                        | "Indirect Income".equals(selGrpName)
                        | "Indirect Expense".equals(selGrpName)) {
                    etOpBal.setVisibility(EditText.GONE);
                    tvOpBalRupeeSymbol.setVisibility(TextView.GONE);
                    tvOpBal.setVisibility(TextView.GONE);
                } else {
                    etOpBal.setVisibility(EditText.VISIBLE);
                    tvOpBal.setVisibility(TextView.VISIBLE);
                    tvOpBalRupeeSymbol.setVisibility(TextView.VISIBLE);
                    tvOpBal.setText("Credit opening balance");
                }
                
                if (selGrpName.equals("Capital"))
                {
                    groupChar = "CP";
                    }else if (selGrpName.equals("Corpus"))
                {
                    groupChar = "CR";
                
                }else if (selGrpName.equals("Current Asset"))
                {
                    groupChar = "CA";
                
                }else if (selGrpName.equals("Current Liability"))
                {
                    groupChar = "CL";
            
                }else if (selGrpName.equals("Direct Income"))
                {
                    groupChar = "DI";
            
                }else if (selGrpName.equals("Direct Expense"))
                {
                    groupChar = "DE";
        
                }else if (selGrpName.equals("Fixed Assets"))
                {
                    groupChar = "FA";
                
                }else if (selGrpName.equals("Indirect Income"))
                {
                    groupChar = "II";
            
                }else if (selGrpName.equals("Indirect Expense"))
                {
                    groupChar = "IE";

                }else if (selGrpName.equals("Investment"))
                {
                    groupChar = "IV";

                }else if (selGrpName.equals("Loans(Asset)"))
                {
                    groupChar = "LA";

                }else if (selGrpName.equals("Reserves"))
                {
                    groupChar = "RS" ;

                }else if (selGrpName.equals("Miscellaneous Expenses(Asset)"))
                {
                    groupChar = "ME";

                }else
                {
                    groupChar = "LL";

                }
                // checks for the selected value of item is not null
                if(selGrpName!=null){
                    // create new array list of type String to add subgroup names
                    List<String> subgroupnamelist = new ArrayList<String>();
                    // input params contains group name
                    Object[] params = new Object[]{selGrpName};
                    // call com.gkaakash.controller.Group.getSubGroupsByGroupName pass params
                    Object[] subgroupnames = (Object[])group.getSubGroupsByGroupName(params,client_id);
                    // loop through subgroup names list 
                    for(Object sbgrp : subgroupnames)
                    {
                        subgroupnamelist.add((String)sbgrp);
                    }
                    // creating array adaptor to take list of subgroups 
                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_item, subgroupnamelist);
                    // set resource layout of spinner to that adaptor
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if(setGroupFlag == false)
                    {
                    	ssubGrpName.setAdapter(dataAdapter1);
                    }else
                     {
                    	
                     	int groupnameposition = dataAdapter1.getPosition(setSubGroupName);
                     	ssubGrpName.setAdapter(dataAdapter1);
                     	ssubGrpName.setSelection(groupnameposition);
                     }
                
                }// End of if condition
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                
            }
        });// End of sgrpName.setOnItemSelectedListener
        
        //Attach a listener to the states Type Spinner to show or hide subgroup name text filed
        ssubGrpName.setOnItemSelectedListener(new OnItemSelectedListener() {
            
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
                // get the current value of subgroup spinner
                selSubGrpName = parent.getItemAtPosition(position).toString();
                
                if("Create New Sub-Group".equals(selSubGrpName))
                {
                    tvSubGrp.setVisibility(EditText.VISIBLE);
                    etSubGrp.setVisibility(TextView.VISIBLE);
                
                }// End of if condition
                else{
                    tvSubGrp.setVisibility(EditText.GONE);
                    etSubGrp.setVisibility(TextView.GONE);
                }// End of else condition
                    
            }// End of onItemSelected

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // 
                
            }
        });
    }
    
    
    private void addListeneronButton() {
        // TODO Auto-generated method stub
        btnCreateAccSave = (Button) findViewById(R.id.btnCreateAccSave);
        btnCreateAccFinish = (Button) findViewById(R.id.btnCreateAccFinish);
        btnCreateAccFinish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
    
                // To pass on the activity to the next page
                Intent intent = new Intent(context, menu.class);
                startActivity(intent);
            }

        });
        // setListner on Save Button
        btnCreateAccSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                
                // get text values from respective Edit Text 
                newsubgrpname = etSubGrp.getText().toString();
                accountname = etAccName.getText().toString();
                accountcode = etaccCode.getText().toString();
                openingbalance= etOpBal.getText().toString();
            
                // check for blank fields
                if("Create New Sub-Group".equals(selSubGrpName)&&newsubgrpname.length()<1||("manually".equals(accCodeCheckFlag)&& accountcode.length()<1))
                {
                    m.toastValidationMessage(context, "Please fill textfield");
                	System.out.println("in if");
                	etaccCode.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
                    
                }else if((accountname.trim().length()<1)||(openingbalance.length()<1))
                {
                	System.out.println("in else");
                	if(accountname.trim().length()<1){
                		etAccName.requestFocus();
                		etAccName.setBackgroundResource(R.drawable.btn_default_focused_holo_light);
                	}else {
                		etOpBal.requestFocus();
                		etOpBal.setBackgroundResource(R.drawable.btn_default_focused_holo_light);

					}
                
                	
                	 m.toastValidationMessage(context, "Please fill textfield");
                    
                }
                else if("Create New Sub-Group".equals(selSubGrpName)&&newsubgrpname.length()>=1)
                {
                    subgroup_exist = group.subgroupExists(new Object[]{newsubgrpname},client_id);
                    if (subgroup_exist.equals("1"))
                    {
                       m.toastValidationMessage(context, "Subgroup "+sub_grp_name+" already exist");
                    }else if(accountname.length()>=1)
                    {
                            accountname_exist = account.checkAccountName(new Object[]{accountname,accCodeCheckFlag,groupChar},client_id);
                            if (accountname_exist.equals("exist"))
                            {
                               m.toastValidationMessage(context, "Account "+accountname+" already exist");
                            }else if("manually".equals(accCodeCheckFlag)&&accountcode.length()>=1)
                            {
                                accountcode_exist = account.checkAccountCode(new Object[]{accountcode},client_id);
                                if (accountcode_exist.equals("1"))
                                {
                                  m.toastValidationMessage(context, "Acountcode "+accountcode+" already exist");
                                
                                }else
                                {    
                                    SaveAccount();
                                }// close else
                            }else
                            {    
                                SaveAccount();
                            }// close else
                            
                    }else
                    {    
                        SaveAccount();
                    }// close else
                    
                }
                else
                {
                    if(accountname.length()>=1)
                    {
                            accountname_exist = account.checkAccountName(new Object[]{accountname,accCodeCheckFlag,groupChar},client_id);
                            if (accountname_exist.equals("exist"))
                            {
                            	  m.toastValidationMessage(context, "Account "+accountname+" already exist");
                            }else if("manually".equals(accCodeCheckFlag)&&accountcode.length()>=1)
                            {
                                accountcode_exist = account.checkAccountCode(new Object[]{accountcode},client_id);
                                if (accountcode_exist.equals("1"))
                                {
                                	 m.toastValidationMessage(context, "Acountcode "+accountcode+" already exist");
                                }else
                                {
                                    SaveAccount();
                                }
                            }else
                            {
                                SaveAccount();
                            }
                    }

                }
            }
        }); // close setOnClickListener
    }
    
    
    private void addEditTextListner()
    {
    	
    	
    	
    	
    etAccName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
          public void onFocusChange(View v, boolean hasFocus) {
            accountname = etAccName.getText().toString();
            
            if(hasFocus)
            {
                etaccCode.setText("");
            }
            else{
                if(accountname.length()>=1)
                {
                    accountcode = account.checkAccountName(new Object[]{accountname,accCodeCheckFlag,groupChar},client_id);
                    if(accountcode.equals("exist"))
                    {
                    	  m.toastValidationMessage(context, "Account "+accountname+" already exist");
                    }else{
                        etaccCode.setText(accountcode);
                        }
                }
                
                }
            
            etAccName.setBackgroundResource(R.drawable.textfield_activated_holo_light);
          }
        });// close addEditTextListner()
    
    
    // It will check for new subgroup name exist 
    etSubGrp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        
        @Override
          public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                sub_grp_name = etSubGrp.getText().toString();
                
                        if(sub_grp_name.length()>=1)
                        {
                            subgroup_exist = group.subgroupExists(new Object[]{sub_grp_name},client_id);
                            if (subgroup_exist.equals("1"))
                            {
                            	 m.toastValidationMessage(context, "Subgroup "+sub_grp_name+" already exist");
                            }
                        }
                }

        });// close setOnFocusChangeListener
    
    // It will check for account code exist 
    etaccCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
              public void onFocusChange(View v, boolean hasFocus) {
                    // TODO Auto-generated method stub
                    account_code = etaccCode.getText().toString();
                    
                            if(account_code.length()>=1)
                            {
                                accountcode_exist = account.checkAccountCode(new Object[]{account_code},client_id);
                                if (accountcode_exist.equals("1"))
                                {
                                	m.toastValidationMessage(context, "Acountcode "+accountcode+" already exist");
                                    etaccCode.setText(account_code);
                                }
                            
                     }
                            etaccCode.setBackgroundResource(R.drawable.textfield_activated_holo_light);
                }

            
            });// close setOnFocusChangeListener
    
    
    etOpBal.setOnFocusChangeListener(new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View arg0, boolean arg1) {
				etOpBal.setBackgroundResource(R.drawable.textfield_activated_holo_light);
		}
	});
    } // close addEditTextListner()
    
    
    
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     * depending upon flag go to respective page on back button pressed
     */
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
       
    }
    
    public void SaveAccount(){
        Object[] params = new Object[]{accCodeCheckFlag,selGrpName,selSubGrpName,newsubgrpname,accountname,accountcode,openingbalance}; 
        // call the setAccount method and pass the above parameters
        account.setAccount(params,client_id);
        getTotalBalances();
        setGroupName = selGrpName;
        setSubGroupName = selSubGrpName;
        setGroupFlag = true;
        getExistingGroupNames();
        //creating interface to listen activity on Item 
        addListenerOnItem();
        module m = new module();
        m.toastSuccessfulMessage(createAccount.this, "Account "+accountname+" have been saved successfully");
        
        etSubGrp.setText("");
        etAccName.setText("");
        etaccCode.setText("");
        etOpBal.setText("0.00");
        
       
    }
}