package com.example.gkaakash;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams; 
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView; 
import com.gkaakash.controller.Report;
import com.gkaakash.controller.Startup;

public class projectStatement extends Activity{
    private Report report;
    static Integer client_id;
    static Object[] projectStatementResult;
    TableLayout projectStatementTable;
    TableRow tr;
    TextView label;
    ArrayList<ArrayList> projectStatementGrid,projectStatementGrid_with_header;
    ArrayList<String> projectStatementResultList;
    String ToDateString;
    Boolean updown=false;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
    String colValue;
    Boolean alertdialog = false;
    ObjectAnimator animation2;
    boolean reportmenuflag;
    int oneTouch = 1;
    TableLayout floating_heading_table;
    LinearLayout Ll;
    ScrollView sv;
    Float result;
    String financialFromDate,projectName;
   	String financialToDate,OrgName,date_format,OrgPeriod ,Period,sFilename;
    String[] pdf_params;
    static String acc_name1;
    ArrayList<String> stringList;
    private int group1Id = 1;
   	int PDF = Menu.FIRST;
   	int CSV=Menu.FIRST+1;
   	module m;
   	String[] ColumnNameList;
   	SpannableString rsSymbol ;
   	
   	@Override
   	public boolean onCreateOptionsMenu(Menu menu) {

   		menu.add(group1Id, PDF, PDF, "Export as PDF");
   		menu.add(group1Id, CSV, CSV, "Export as CSV");
   		return super.onCreateOptionsMenu(menu);
   	}

   	@Override
   	public boolean onOptionsItemSelected(MenuItem item) {
   		switch (item.getItemId()) {
   		case 1:			
   			m.generate_pdf(projectStatement.this, pdf_params,projectStatementGrid);
   			return true;

   		case 2:
   			m.csv_writer(pdf_params,projectStatementGrid_with_header);
   			m.toastValidationMessage(projectStatement.this, "CSV exported");
   			return true;
   		}
   		return super.onOptionsItemSelected(item);
   	}
    
    
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.project_statement_table);
        report = new Report();
        client_id= Startup.getClient_id();
        m=new module();
      //For adding rupee symbol
        rsSymbol = new SpannableString(projectStatement.this.getText(R.string.Rs)); 
        reportmenuflag=MainActivity.reportmenuflag;
        //customizing title bar
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
   
        try {
        	floating_heading_table = (TableLayout)findViewById(R.id.floating_heading_table);
        	floating_heading_table.setVisibility(TableLayout.GONE);
        	/*
        	 * get financial from and to date from startup page
        	 */
        	financialFromDate =Startup.getfinancialFromDate();
        	financialToDate=Startup.getFinancialToDate();
        	ToDateString = reportMenu.givenToDateString;
   
        	projectName = reportMenu.selectedProject;
   
        	if(!projectName.equalsIgnoreCase("No Project")){
        		TextView tvProjectName = (TextView) findViewById( R.id.tvProjectName );
        		tvProjectName.setText("Project name: " +projectName);
         		
            }
        	  if(reportmenuflag==true){
         	    	
  	    		OrgName = createOrg.organisationName;
  	    		
             }
             else {
          	    OrgName= selectOrg.selectedOrgName;
           
             }
        	/*
        	 * set financial from date and to date in textview
        	 */
        	TextView tvfinancialToDate = (TextView) findViewById( R.id.tvTfinancialToDate );
        	tvfinancialToDate.setText("Period : "+financialFromDate+" to "+ToDateString);
        	/*
        	 * send params to controller report.getProjectStatementReport to get the result
        	 */
        	Object[] params = new Object[]{projectName, financialFromDate,financialFromDate,ToDateString};
        	projectStatementResult = (Object[]) report.getProjectStatementReport(params,client_id);
      
        	projectStatementGrid = new ArrayList<ArrayList>();
        	projectStatementGrid_with_header = new ArrayList<ArrayList>();
        	
        	for(Object tb : projectStatementResult)
        	{
        		Object[] t = (Object[]) tb;
        		projectStatementResultList = new ArrayList<String>();
        		for(int i=0;i<t.length;i++){
           	
        			projectStatementResultList.add((String) t[i].toString());
              
        		}
        		projectStatementGrid.add(projectStatementResultList);
        	}
        	System.out.println("grid:"+projectStatementGrid);
        	projectStatementTable = (TableLayout)findViewById(R.id.maintable);
        	addTable();
        
        	final TextView tvReportTitle = (TextView)findViewById(R.id.tvReportTitle);
        	tvReportTitle.setText("Menu >> "+"Report >> "+"Project Statement");
        	final Button btnSaveRecon = (Button)findViewById(R.id.btnSaveRecon);
        	btnSaveRecon.setVisibility(Button.GONE);
        	 
        	final Button btnScrollDown = (Button)findViewById(R.id.btnScrollDown);
        	btnScrollDown.setOnClickListener(new OnClickListener() {
        		@Override
        		public void onClick(View v) {
        			if(updown==false){
        				ScrollView sv = (ScrollView)findViewById(R.id.ScrollProjStatement);
		                sv.fullScroll(ScrollView.FOCUS_DOWN); 
		                btnScrollDown.setBackgroundResource(R.drawable.up);
		                updown=true;
		           }else {
		                ScrollView sv = (ScrollView)findViewById(R.id.ScrollProjStatement);
		                sv.fullScroll(ScrollView.FOCUS_UP); 
		                btnScrollDown.setBackgroundResource(R.drawable.down);
		                updown=false;
		           }
        		}
        	});
        	 Date date= new Date();
	   		     String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
				 OrgPeriod = "Financial Year:  "+financialFromDate+" to "+financialToDate;
	             Period = financialFromDate+" to "+ToDateString;
	             String project = projectName.replace(" ","");
				 sFilename = "ProjeST"+"_"+project+"_"+date_format;
		         pdf_params = new String[]{"ProjeST",sFilename,OrgName,OrgPeriod,"Project Statement",Period,projectName,String.format("%.2f", Math.abs(result))};
        	
        	
        	animated_dialog();
        	floatingHeader();
        
        } catch (Exception e) {
        	m.toastValidationMessage(projectStatement.this, "Please try again");
        	}
    }
   
    /*
     * this method adds the floating header to the table on touching it.
     * In this case, we have a main table which includes table rows and a header at the load time.
     * and another table(for floating header) is invisible at load time which is located at the top of main table.
     * on the very first touch of the main table, we will add floating header columns and
     * make it visible.
     * at the same time we will set width 0 for the main table header to avoid
     * double headers at the same time.
     */
    private void floatingHeader() {
    	projectStatementTable.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(oneTouch == 1){
					floating_heading_table.setVisibility(TableLayout.VISIBLE);
					
					//System.out.println("we are in if");
					
					int rowcount = projectStatementTable.getChildCount();    
	                View row = projectStatementTable.getChildAt(rowcount-1);
					
	               
	                /** Create a TableRow dynamically **/
	                String[] ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Total debit",rsSymbol+" Total credit"};
	               
	                tr = new TableRow(projectStatement.this);
			        
			        for(int k=0;k<ColumnNameList.length;k++){
			            /** Creating a TextView to add to the row **/
			            addRow(ColumnNameList[k],k);
			            label.setBackgroundColor(Color.parseColor("#348017"));
			            label.setGravity(Gravity.CENTER);
			            LinearLayout l = (LinearLayout)((ViewGroup) row).getChildAt(k);
			            label.setWidth(l.getWidth());
			        }
			        tr.setClickable(false);
			         // Add the TableRow to the TableLayout
			        floating_heading_table.addView(tr, new TableLayout.LayoutParams(
			                LayoutParams.FILL_PARENT,
			                LayoutParams.MATCH_PARENT));
					
					//ledgertable.removeViewAt(0);
					projectStatementTable.getChildAt(0).setVisibility(View.INVISIBLE);
					
					View firstrow = projectStatementTable.getChildAt(0);
					for(int k=0;k<ColumnNameList.length;k++){
						LinearLayout l = (LinearLayout)((ViewGroup) firstrow).getChildAt(k);
						TextView tv = (TextView) l.getChildAt(0);
			            tv.setHeight(0);
			            
			            l.getLayoutParams().height = 0;
					}
					
					//ledgertable.getChildAt(0).setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 0));
				
				}
				oneTouch ++;
				
				return false;
			}
		});
	}

    
	private void animated_dialog() {
		try {
            final LinearLayout Llalert = (LinearLayout)findViewById(R.id.Llalert);
            Llalert.setVisibility(LinearLayout.GONE);
            animation2 = ObjectAnimator.ofFloat(Llalert,
                    "x", 1000);
            animation2.setDuration(1000);
            animation2.start();
            
            final Button btnOrgDetailsDialog = (Button) findViewById(R.id.btnOrgDetailsDialog);
        
           
            btnOrgDetailsDialog.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                   btnOrgDetailsDialog.setAlpha(100);
                    if(alertdialog==false){
                        Llalert.setVisibility(LinearLayout.VISIBLE);
                        TextView tvOrgNameAlert = (TextView)findViewById(R.id.tvOrgNameAlert);
                        
                        if(reportmenuflag==true){
                            tvOrgNameAlert.setText(createOrg.organisationName);
                        }
                        else {
                        	tvOrgNameAlert.setText(selectOrg.selectedOrgName);
                        }
                        
                        
                        TextView tvOrgTypeAlert = (TextView)findViewById(R.id.tvOrgTypeAlert);
                        tvOrgTypeAlert.setText(reportMenu.orgtype);
                        
                        TextView tvFinancialYearAlert = (TextView)findViewById(R.id.tvFinancialYearAlert);
                        tvFinancialYearAlert.setText(reportMenu.financialFromDate+" to "+ reportMenu.financialToDate);
                        
                        animation2 = ObjectAnimator.ofFloat(Llalert,
                                  "x", 300);
                        alertdialog=true;
                    }else {
                         
                        animation2 = ObjectAnimator.ofFloat(Llalert,
                                  "x", 1000);
                        alertdialog=false;
                     }
                      
                     animation2.setDuration(1000);
                     animation2.start();
                }
                
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
	}

	
	private void addTable() {
        addHeader();
        /** Create a TableRow dynamically **/
        for(int i=0;i<projectStatementGrid.size();i++){
            ArrayList<String> columnValue = new ArrayList<String>();
            columnValue.addAll(projectStatementGrid.get(i));
            tr = new TableRow(this);
           
            for(int j=0;j<columnValue.size();j++){
                /** Creating a TextView to add to the row **/
                addRow(columnValue.get(j),i);   
                if ((i + 1) % 2 == 0)
					label.setBackgroundColor(Color.parseColor("#474335"));
				else
					label.setBackgroundColor(Color.BLACK);
                /*
                 * set right aligned gravity for amount and for others set center gravity
                 */
                if(j==3 || j==4){
                    label.setGravity(Gravity.RIGHT);
                    //For adding rupee symbol
                    if(columnValue.get(j).length() > 0){
                    	colValue=columnValue.get(j);
                    	if(!"".equals(colValue)){
                    		//System.out.println("m in ");
                    		if(!"0.00".equals(colValue)){
                    			// for checking multiple \n and pattern matching
                    			Pattern pattern = Pattern.compile("\\n");
                    			Matcher matcher = pattern.matcher(colValue);
                    			boolean found = matcher.find();
                    			//System.out.println("value:"+found);
                    			if(found==false){
                    				double amount = Double.parseDouble(colValue);	
                    				label.setText(formatter.format(amount));
                    			}else {
                    				label.setText(colValue);
                    			}
                   
                    		}else {
                    			label.setText(colValue);
                    		}
                    	}
                    }
                }
                else{
                    label.setGravity(Gravity.CENTER);
                }
            }
            // Add the TableRow to the TableLayout
            projectStatementTable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        
        /*
         * display the difference between total dr and total cr
         */
        ArrayList<String> lastrow=projectStatementGrid.get(projectStatementGrid.size()-1);
        result=Float.parseFloat(lastrow.get(4))-Float.parseFloat(lastrow.get(3));
        TextView difference = (TextView) findViewById(R.id.tvdifference);
        
       
        difference.setText("Difference in Opening Balances : "+rsSymbol+" "+(String.format("%.2f", Math.abs(result))));
    }

	
    /*
     * add column heads to the table
     */
    void addHeader(){
    	
        /** Create a TableRow dynamically **/
        ColumnNameList = new String[] { "Sr. no.","Account name","Group name",rsSymbol+" Total debit",rsSymbol+" Total credit"};
       
        tr = new TableRow(this);
        
        for(int k=0;k<ColumnNameList.length;k++){
            /** Creating a TextView to add to the row **/
            addRow(ColumnNameList[k],k);
            label.setBackgroundColor(Color.parseColor("#348017"));
            label.setGravity(Gravity.CENTER);
            label.setClickable(false);
        }
       
        //to convert string array to arary of array for csv file format
        stringList = new ArrayList<String>();
	    for (String s : ColumnNameList) { 
	    	
	        stringList.add(s);
	    }
	    projectStatementGrid_with_header.add(stringList);
	    projectStatementGrid_with_header.addAll(projectStatementGrid);
	    tr.setClickable(false);
        // Add the TableRow to the TableLayout
        projectStatementTable.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.MATCH_PARENT));
    }
    
    
    /*
     * this function add the value to the row
     */
    void addRow(String param,final int i){
    	tr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {				
				
				String accname = projectStatementGrid.get(i).get(1).toString();
				
				if(!(accname.equalsIgnoreCase("") ||
						accname.equalsIgnoreCase("Account Name"))){
					//change the row color(black/gray to orange) when clicked
					View row = projectStatementTable.getChildAt(i+1);
					for (int j = 0; j < ColumnNameList.length; j++) {
						LinearLayout l = (LinearLayout) ((ViewGroup) row)
								.getChildAt(j);
						TextView t = (TextView) l.getChildAt(0);
						ObjectAnimator colorFade = ObjectAnimator.ofObject(t, "backgroundColor", new ArgbEvaluator(), Color.parseColor("#FBB117"), Color.parseColor("#000000"));
						colorFade.setDuration(100);
						colorFade.start();
					}					
					
					acc_name1 = accname;
					Intent intent = new Intent(getApplicationContext(),ledger.class);
					intent.putExtra("flag", "from_projStatement");
					startActivity(intent);
				}
			}
    	});
    	
        label = new TextView(this);
        label.setText(param);
        label.setTextSize(18);
        label.setTextColor(Color.WHITE);
        label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        label.setPadding(2, 2, 2, 2);
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 1, 1);
        Ll.addView(label,params);
        tr.addView((View)Ll); // Adding textView to tablerow.
       
    }
    public void onBackPressed() {
		
		MainActivity.nameflag = false;
		Intent intent = new Intent(getApplicationContext(),reportMenu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);

}

}
