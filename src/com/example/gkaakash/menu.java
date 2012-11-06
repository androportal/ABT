package com.example.gkaakash;


import java.util.ArrayList;
import java.util.List;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
	static int idCount;
	EditText etProject,etdynamic;
	private Integer client_id;
	private Preferences preferences;
	private Organisation organisation;
	ArrayList<String> finalProjlist;
	protected String projectname;
    protected ArrayList<String>[] projectnamelist;
    boolean projectExistsFlag = false;
	private boolean setProject;
    
	//adding list items to the newly created menu list
	String[] menuOptions = new String[] { "Create account", "Transaction", "Reports",
			"Add projects", "Help", "About" };

	/*
	//adding options to the options menu
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(group1Id, Edit, Edit, "Edit");
    menu.add(group1Id, Delete, Delete, "Delete");
    menu.add(group1Id, Finish, Finish, "Finish");
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
		preferences = new Preferences();
		organisation = new Organisation();
	    client_id= Startup.getClient_id();
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
              	  builder.setTitle("Add Projects");
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
				//for administration
				if(position == 4){
					String message = "This functionality is not implemented yet";
					toastValidationMessage(message);
				}
				//for help
				if(position == 5){
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