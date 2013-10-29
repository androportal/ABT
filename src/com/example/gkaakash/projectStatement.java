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
import android.app.Dialog;
import android.app.ActionBar.LayoutParams; 
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
   	static String IPaddr;
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
   			LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			String password = m.setPasswordForPdfFile(projectStatement.this,inflater, R.layout.sign_up, 0, pdf_params,projectStatementGrid, null);
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
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.project_statement_table);
        IPaddr = MainActivity.IPaddr;
		System.out.println("in createorg"+IPaddr);
        report = new Report(IPaddr);
        client_id= Startup.getClient_id();
        m=new module();
      //For adding rupee symbol
        rsSymbol = new SpannableString(projectStatement.this.getText(R.string.Rs)); 
        reportmenuflag=MainActivity.reportmenuflag;
        //customizing title bar
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.bank_recon_title);
   
        try {
        	floating_heading_table = (TableLayout)findViewById(R.id.floating_heading_table);
        	floating_heading_table.setVisibility(TableLayout.GONE);
        	/*
        	 * get financial from and to date from startup page
        	 */
        	financialFromDate =Startup.getfinancialFromDate();
        	financialToDate=Startup.getFinancialToDate();
        	ToDateString = reportMenu.givenToDateString;
   
        	projectName = reportMenu.input;
        	
        	TextView tvaccountName = (TextView) findViewById( R.id.tvaccountName);
        	tvaccountName.setText("Project Statement");
        	
        	if(!projectName.equalsIgnoreCase("No Project")){
        		TextView tvProjectName = (TextView) findViewById( R.id.tvProjectName );
        		tvProjectName.setVisibility(View.VISIBLE);
        		tvProjectName.setText("Project: " +projectName);
         		
            }
        	
        	OrgName = MainActivity.organisationName;
        	
        	/*
        	 * set financial from date and to date in textview
        	 */
        	TextView tv = (TextView) findViewById( R.id.tvfinancialToDate );
        	//to get month in words
			SimpleDateFormat read = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat write = new SimpleDateFormat("dd-MMM-yyyy");
			String str_fromDate = write.format(read.parse(financialFromDate));
			String str_toDate = write.format(read.parse(ToDateString));
	    	
	    	tv.setText("Period : "+str_fromDate+" to "+str_toDate);  
	    	
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
        
        	//set title
			TextView org = (TextView)findViewById(R.id.org_name);
			org.setText(OrgName + ", "+reportMenu.orgtype);
			TextView tvdate = (TextView)findViewById(R.id.date);
			tvdate.setText(m.changeDateFormat(financialFromDate)+" To "+m.changeDateFormat(financialToDate));
        	floatingHeader();
        	
        	createMenuOptions();
        	changeInputs();
        } catch (Exception e) {
        	m.toastValidationMessage(projectStatement.this, "Please try again");
        	}
    }
   
    private void changeInputs() {
		Button btn_changeInputs = (Button)findViewById(R.id.btn_changeInputs);
		btn_changeInputs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportMenu reportM = new reportMenu();
				reportM.callReport(projectStatement.this, "P", projectStatement.class);
			}
		});
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
			            label.setBackgroundColor(Color.WHITE);
						label.setTextColor(Color.BLACK); //blue theme
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

    public void createMenuOptions() {
    	Button btn_optionsMenu = (Button)findViewById(R.id.btn_optionsMenu);
		
		btn_optionsMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				 Date date= new Date();
	   		     String date_format = new SimpleDateFormat("dMMMyyyy_HHmmss").format(date);
				 OrgPeriod = "Financial Year:  "+financialFromDate+" to "+financialToDate;
	             Period = financialFromDate+" to "+ToDateString;
	             String project = projectName.replace(" ","");
				 sFilename = "ProjeST"+"_"+ OrgName.replace(" ", "")+"_"+project+ "_" +
							financialFromDate.substring(8)+"-"+financialToDate.substring(8)+"_"+date_format;
		         pdf_params = new String[]{"ProjeST",sFilename,OrgName,OrgPeriod,"Project Statement",Period,projectName,String.format("%.2f", Math.abs(result))};
				CharSequence[] items = new CharSequence[]{ "Export as PDF","Export as CSV"};
				
				AlertDialog dialog;
				//creating a dialog box for popup
				AlertDialog.Builder builder = new AlertDialog.Builder(projectStatement.this);
				//setting title
				builder.setTitle("Select");
				//adding items
				builder.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int pos) {
						if(pos == 0){
							LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
							String password = m.setPasswordForPdfFile(projectStatement.this,inflater, R.layout.sign_up, 0, pdf_params,projectStatementGrid, null);
				   			
				   			
						}else if(pos == 1){
							m.csv_writer(pdf_params,projectStatementGrid_with_header);
				   			m.toastValidationMessage(projectStatement.this,"CSV exported please see at /mnt/sdcard/"+pdf_params[1]);
						}
					}
				});
				dialog=builder.create();
				((Dialog) dialog).show();
			}
			
		});
		
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
					label.setBackgroundColor(Color.parseColor("#085e6b")); //blue theme
				else
					label.setBackgroundColor(Color.parseColor("#2f2f2f")); //gray theme
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
            label.setBackgroundColor(Color.WHITE);
			label.setTextColor(Color.BLACK); //blue theme
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
    	tr.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {			
				
				String accname = projectStatementGrid.get(i).get(1).toString();
				
				if(!(accname.equalsIgnoreCase("") ||
						accname.equalsIgnoreCase("Account Name"))){
					//change the row color(black/gray to orange) when clicked
					View row = projectStatementTable.getChildAt(i+1);
					for (int j = 0; j < ColumnNameList.length; j++) {
						LinearLayout l = (LinearLayout) ((ViewGroup) row).getChildAt(j);
						TextView t = (TextView) l.getChildAt(0);
						ColorDrawable drawable = (ColorDrawable)t.getBackground();
						ObjectAnimator colorFade = ObjectAnimator.ofObject(t, "backgroundColor", new ArgbEvaluator(), Color.parseColor("#FBB117"),drawable.getColor());
						colorFade.setDuration(100);
						colorFade.start();
					}					
					
					acc_name1 = accname;
					Intent intent = new Intent(getApplicationContext(),ledger.class);
					intent.putExtra("flag", "from_projStatement");
					startActivity(intent);
				}
				return false;
			}
    	});
    	
        label = new TextView(this);
        label.setText(param);
        label.setTextSize(18);
        label.setTextColor(Color.WHITE);
        label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        label.setPadding(2, 2, 2, 2);
        LinearLayout Ll = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
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
