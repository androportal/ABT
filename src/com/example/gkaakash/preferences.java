package com.example.gkaakash;

import java.util.ArrayList;
import java.util.List;

import com.gkaakash.controller.Organisation;
import com.gkaakash.controller.Preferences;
import com.gkaakash.controller.Startup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
 
 
public class preferences extends Activity {
    //Declaring variables
    CheckBox cbProject, cbAccCode;
    EditText etProject,etdynamic;
    Button bCreateAcc;
    static String accCodeflag;
    private Button btnSavePref;
    private Preferences preference;
    private Integer client_id;
    protected Boolean setpref;
    protected String projectname;
    protected Context context;
    private Button btnQuit,btnaddproj;
    static String refNoflag;
    private TableLayout projectTable;
    int rowsSoFar=0;
    int count;
    static int idCount;
    public TableLayout list;
    public TableRow rowToBeRemoved;
    ArrayList<String> finalProjlist;
    protected ArrayList<String>[] projectnamelist;
    private Organisation organisation;
    boolean projectExistsFlag = false;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Calling preferences.xml
        setContentView(R.layout.preferences);
        // get Client_id return by Deploy method
        organisation = new Organisation();
       	client_id= Startup.getClient_id();
        etProject = (EditText) findViewById(R.id.etProject);
        
      //for visibility of account tab layout
        MainActivity.tabFlag = false;
        
        //String from = Startup.getfinancialFromDate();
        //String to = Startup.getFinancialToDate();
        //System.out.println("from and to are...");
        //System.out.println(from+"-"+to);
       
        btnaddproj = (Button) findViewById(R.id.addProj);
        btnSavePref =(Button) findViewById(R.id.btnSavePref);
        btnSavePref.setVisibility(Button.GONE);
        //setting visibility
        etProject.setVisibility(EditText.GONE);
        btnaddproj.setVisibility(Button.GONE);
        // create object of Preference class
        preference = new Preferences();
        accCodeflag=preference.getPreferences(new Object[]{"2"},client_id );
        refNoflag=preference.getPreferences(new Object[]{"1"},client_id);
        addListenerOnButton();
        addListenerOnChkIos();
        Button addButton = (Button) findViewById( R.id.addProj );
        // Every time the "+" button is clicked,
        // add a new row to the table.
        addButton.setOnClickListener( new OnClickListener() {
        public void onClick(View view) {
                    addButton(); }
                });
           
        projectTable = (TableLayout) findViewById( R.id.projtable );
    }

    private void addListenerOnChkIos() {
        cbProject = (CheckBox) findViewById(R.id.cbProject);
        etProject = (EditText) findViewById(R.id.etProject);
        cbProject.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                //for setting the visibility of EditText:'etProject' depending upon the condition
            	   if (((CheckBox) v).isChecked()) {
                       etProject.setVisibility(EditText.VISIBLE);
                       btnaddproj.setVisibility(EditText.VISIBLE);
                       btnSavePref.setVisibility(Button.VISIBLE);
                       refNoflag = "optional";
                   }
                   else {
                       etProject.setVisibility(EditText.GONE);
                       btnaddproj.setVisibility(EditText.GONE);
                       btnSavePref.setVisibility(Button.GONE);
                       refNoflag = "mandatory";
                   }
            }
            });
       
        cbAccCode = (CheckBox) findViewById(R.id.cbAccCode);
       
        cbAccCode.setOnClickListener(new OnClickListener() {
             
            @Override
            public void onClick(View v) {
                //Setting the account code flag value
            	 if (((CheckBox) v).isChecked()) {
                     
                     accCodeflag = "manually";
                     Object[] params = new Object[]{"1",refNoflag,"2",accCodeflag};
                     //Object[] params1 = new Object[]{finalProjlist};
                     setpref = preference.setPreferences(params, finalProjlist, client_id);
                 }
                 else {
                     accCodeflag = "automatic";
                     Object[] params = new Object[]{"1",refNoflag,"2",accCodeflag};
                     //Object[] params1 = new Object[]{finalProjlist};
                     setpref = preference.setPreferences(params, finalProjlist, client_id);
                 }
            }
            });
    }
   
    private void addListenerOnButton() {
        bCreateAcc = (Button) findViewById(R.id.btnCreateAcc);
       
        final Context context = this;
        //Create a class implementing “OnClickListener” and set it as the on click listener for the button
        bCreateAcc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
               
                //To pass on the activity to the next page
                Intent intent = new Intent(context, createAccount.class);
                //intent.putExtra("finish_flag","login");
                startActivity(intent);  
            }
        });
        btnSavePref =(Button) findViewById(R.id.btnSavePref);
        //Create a class implementing “OnClickListener” and set it as the on click listener for the button
        btnSavePref.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(android.view.View v) {
                        EditText projectName;
                        String proj_name;
                        View v1 = null;
                        List<String> secondProjlist=new ArrayList<String>();
                        projectname = etProject.getText().toString();
                        System.out.println("projectname:"+projectname);
                       
                       
                       
                        for(int i = 1; i <= idCount ; i++){  
                             v1 = findViewById(i);
                            if(v1 != null){
                                projectName = (EditText) findViewById(i);
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
                        System.out.println("final project list");
                        System.out.println(finalProjlist);
                        
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
    					System.out.println("second project list");
    					System.out.println(projectnamelist);
    					
    					String ac;
    					boolean  flag = false;
    					String nameExists = "";
    					
    					for (int i = 0; i < finalProjlist.size(); i++) {
    						ac = finalProjlist.get(i);
    						for (int j = 0; j < finalProjlist.size(); j++)
    						{
    							if (i!=j)
    							{
    								System.out.println("next element"+finalProjlist.get(j));
    								if(ac.equals(finalProjlist.get(j)))
    								{
    									System.out.println("equal");
    									flag = true;
    									break;
    								}
    							}
    							else
    							{
    								flag = false;
    								System.out.println("not equal");
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
	    					
	    					 if(refNoflag.equals("optional")&& etProject.length()<1){
	    						 String message = "Please enter project name";
	     						toastValidationMessage(message);
	                         }
	                         else if(projectExistsFlag == true){
	                        	 String message = "Project "+nameExists+" already exists";
		     					toastValidationMessage(message);
		     					}
	                         else
	                          {
	                               Object[] params = new Object[]{"1",refNoflag,"2",accCodeflag};
	                               //Object[] params1 = new Object[]{finalProjlist};
	                               setpref = preference.setPreferences(params, finalProjlist, client_id);
	                               //To pass on the activity to the next page
	                               
	                               AlertDialog.Builder builder = new AlertDialog.Builder(context);
		       		               builder.setMessage("Preferences have been saved successfully!");
		       		               AlertDialog alert = builder.create();
		       		               alert.setCancelable(true);
		       		               alert.setCanceledOnTouchOutside(true);
		       		               alert.show();
	                               
		       				        
		       				       cbAccCode.setChecked(false);
	                               cbProject.setChecked(false);
	                               etProject.setVisibility(EditText.GONE);
	                               etProject.setText("");
	                               btnaddproj.setVisibility(EditText.GONE);
	                               projectTable.removeAllViews();
	                               btnSavePref.setVisibility(Button.GONE);
	                           }
    					}
                       

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
                });
        btnQuit =(Button) findViewById(R.id.btnQuit);
        btnQuit.setOnClickListener(new OnClickListener() {
           
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed(){
        //To pass on the activity to the next page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
   
}