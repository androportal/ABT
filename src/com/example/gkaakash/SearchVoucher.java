package com.example.gkaakash;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.gkaakash.R.layout;
import com.gkaakash.controller.Account;
import com.gkaakash.controller.Startup;
import com.gkaakash.controller.Transaction;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class SearchVoucher extends Activity {
	int textlength=0;
	Context context = SearchVoucher.this;
	AlertDialog dialog;
	DecimalFormat mFormat;
	private Transaction transaction;
	static Integer client_id;
	static ArrayList<ArrayList<String>> searchedVoucherGrid;
	static ArrayList<String> searchedVoucherList;
	TableLayout vouchertable;
	TableRow tr;
	TextView label;
	static String financialFromDate;
	static String financialToDate;
	int rowid=0;
	static String vouchertypeflag;
	static ArrayList<String> value;
	static String name,deleted_vouchercode;
	static Boolean cloneflag=false,deletedflag=false;
	String vouchercode;
	LinearLayout.LayoutParams params;
	static int searchVoucherBy = 2; // by date
	protected Boolean deleteVoucher;
	static String searchByNarration;
	static String searchByRefNumber;
	static String voucher_num,Account_Name,voucher_Type;
	DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");
	String colValue;
	int oneTouch = 1;
	TableLayout floating_heading_table;  
	module m;
	Account account;
	String[] ColumnNameList;
	static String IPaddr,vouchertype;
	Button from_btn_ID, to_btn_ID;
	static final int FROM_DATE_DIALOG_ID = 0, TO_DATE_DIALOG_ID = 1;
	String givenfromDateString, givenToDateString;
	String setfromday, setfrommonth, setfromyear, settoday, settomonth, settoyear;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_voucher);
		IPaddr = MainActivity.IPaddr;
		System.out.println("in createorg"+IPaddr);
		
		transaction = new Transaction(IPaddr);
		m= new module();
		account = new Account(IPaddr);
		
		client_id = Startup.getClient_id();
		//for two digit format date for dd and mm
		mFormat= new DecimalFormat("00");
		mFormat.setRoundingMode(RoundingMode.DOWN);
		
		
		vouchertable = (TableLayout)findViewById(R.id.maintable);
		floating_heading_table = (TableLayout) findViewById(R.id.floating_heading_table);
		floating_heading_table.setVisibility(TableLayout.GONE);


		financialFromDate =Startup.getfinancialFromDate(); 
		financialToDate = Startup.getFinancialToDate();

		TextView tvVFromdate = (TextView) findViewById( R.id.tvVFromdate );
		TextView tvVTodate = (TextView) findViewById( R.id.tvVTodate );

		tvVFromdate.setText("Financial from date: " +financialFromDate);
		tvVTodate.setText("Financial to date: " +financialToDate);

		
		vouchertypeflag = createVoucher.vouchertypeflag;

		System.out.println("VOUCHER TYPE"+vouchertypeflag);
		try {
			setOnSearchButtonClick();
			//floatingHeader();


		} catch (Exception e) {
			m.toastValidationMessage(context, "Please try again");
		}
	}

	private void floatingHeader() {
		 System.out.println("we are in floating function");
		 
		 vouchertable.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                if (oneTouch == 1) {
                    floating_heading_table.setVisibility(TableLayout.VISIBLE);

                    // System.out.println("we are in if");

                    int rowcount = vouchertable.getChildCount();
                    View row = vouchertable.getChildAt(rowcount - 1);

                    final SpannableString rsSymbol = new SpannableString(
                            SearchVoucher.this.getText(R.string.Rs));
                    /** Create a TableRow dynamically **/
                    String[] ColumnNameList = new String[] { "V. No.","Reference No","Date","Voucher Type","Account Name","Particular",rsSymbol+"Amount","Narration"};


                    tr = new TableRow(SearchVoucher.this);

                    for (int k = 0; k < ColumnNameList.length; k++) {
                        /** Creating a TextView to add to the row **/
                        addRow(ColumnNameList[k], k);
                        label.setBackgroundColor(Color.parseColor("#348017"));
                        label.setGravity(Gravity.CENTER);

                        LinearLayout l = (LinearLayout) ((ViewGroup) row)
                                .getChildAt(k);
                        label.setWidth(l.getWidth());
                        tr.setClickable(false);
                        params.height = LayoutParams.WRAP_CONTENT;
                        // System.out.println("size is"+l.getWidth());
                    }

                    // Add the TableRow to the TableLayout
                    floating_heading_table.addView(tr,
                            new TableLayout.LayoutParams(
                                    LayoutParams.FILL_PARENT,
                                    LayoutParams.WRAP_CONTENT));
                    // ledgertable.removeViewAt(0);
                    vouchertable.getChildAt(0).setVisibility(View.INVISIBLE);

                    View firstrow = vouchertable.getChildAt(0);
                    for (int k = 0; k < ColumnNameList.length; k++) {
                        LinearLayout l = (LinearLayout) ((ViewGroup) firstrow)
                                .getChildAt(k);
                        TextView tv = (TextView) l.getChildAt(0);
                        tv.setHeight(0);

                        l.getLayoutParams().height = 0;
                    }
                    // ledgertable.getChildAt(0).setLayoutParams(new
                    // TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    // 0));

                }
                oneTouch++;
                
                /*
                 * make table rows clikable now
                 */
                int count = vouchertable.getChildCount();
                for (int i = 0; i < count; i++) {
                    TableRow row = (TableRow) vouchertable.getChildAt(i);
                    row.setClickable(true);    
                }
                return false;
			}
		});
	       
	}

	private void setOnSearchButtonClick() {
		Button btnSearchVoucher = (Button)findViewById(R.id.btnSearchVoucher);
		btnSearchVoucher.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.search_voucher_by, (ViewGroup) findViewById(R.id.timeInterval));
				//Building DatepPcker dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setView(layout);
				builder.setTitle("Search voucher by,");


				String dateParts[] = financialFromDate.split("-");
				setfromday  = dateParts[0];
				setfrommonth = dateParts[1];
				setfromyear = dateParts[2];


				String dateParts1[] = financialToDate.split("-");
				settoday  = dateParts1[0];
				settomonth = dateParts1[1];
				settoyear = dateParts1[2];

				final TextView tvWarning = (TextView)layout.findViewById(R.id.tvWarning);
				
				from_btn_ID = (Button)layout.findViewById(R.id.btnsetLedgerFromdate);
		  	   	from_btn_ID.setText(setfromday+"-"+setfrommonth+"-"+setfromyear);
		  	   	from_btn_ID.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						showDialog(FROM_DATE_DIALOG_ID);
					}
				});
		  	   	to_btn_ID= (Button)layout.findViewById(R.id.btnsetLedgerTodate);
		  	   	to_btn_ID.setText(settoday+"-"+settomonth+"-"+settoyear);
		  	   	to_btn_ID.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						showDialog(TO_DATE_DIALOG_ID);
					}
				});

				final Spinner group_name = (Spinner)layout.findViewById(R.id.saccount_name);
				group_name.setVisibility(Spinner.GONE);
				
				final EditText voucher_number = (EditText)layout.findViewById(R.id.etVoucher_number);
				voucher_number.setVisibility(EditText.GONE);

				final EditText etVoucherCode = (EditText)layout.findViewById(R.id.searchByVCode);
				etVoucherCode.setVisibility(EditText.GONE);

				final EditText etNarration = (EditText)layout.findViewById(R.id.searchByNarration);
				etNarration.setVisibility(EditText.GONE);

				final LinearLayout timeInterval = (LinearLayout)layout.findViewById(R.id.timeInterval);
				timeInterval.setVisibility(LinearLayout.GONE);
				
				final Spinner voucher_type = (Spinner)layout.findViewById(R.id.svoucher_type);
				voucher_type.setVisibility(Spinner.GONE);
				

				final Spinner searchBy = (Spinner) layout.findViewById(R.id.sSearchVoucherBy);
				searchBy.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
						if(position == 0){
							tvWarning.setVisibility(View.GONE);
							etNarration.setVisibility(EditText.GONE);
							timeInterval.setVisibility(LinearLayout.GONE);
							etVoucherCode.setVisibility(EditText.VISIBLE);
							group_name.setVisibility(Spinner.GONE);
							voucher_type.setVisibility(Spinner.GONE);
							voucher_number.setVisibility(EditText.GONE);


						}
						if(position == 1){
							tvWarning.setVisibility(View.GONE);
							etVoucherCode.setVisibility(EditText.GONE);
							etNarration.setVisibility(EditText.GONE);
							timeInterval.setVisibility(LinearLayout.VISIBLE);
							group_name.setVisibility(Spinner.GONE);
							voucher_type.setVisibility(Spinner.GONE);
							voucher_number.setVisibility(EditText.GONE);

						}
						if(position == 2){
							tvWarning.setVisibility(View.GONE);
							etVoucherCode.setVisibility(EditText.GONE);
							timeInterval.setVisibility(LinearLayout.GONE);
							etNarration.setVisibility(EditText.VISIBLE);
							group_name.setVisibility(Spinner.GONE);
							voucher_number.setVisibility(EditText.GONE);


						}
						if(position == 3){
							tvWarning.setVisibility(View.GONE);
							etVoucherCode.setVisibility(EditText.GONE);
							timeInterval.setVisibility(LinearLayout.GONE);
							etNarration.setVisibility(EditText.GONE);
							group_name.setVisibility(Spinner.GONE);
							voucher_type.setVisibility(Spinner.GONE);
							voucher_number.setVisibility(EditText.VISIBLE);

						}
//						if(position == 4){
//							tvWarning.setVisibility(View.GONE);
//							etVoucherCode.setVisibility(EditText.GONE);
//							timeInterval.setVisibility(LinearLayout.GONE);
//							etNarration.setVisibility(EditText.GONE);
//							group_name.setVisibility(Spinner.VISIBLE);
//							voucher_number.setVisibility(EditText.GONE);
//
//					        List<String> groupnamelist = new ArrayList<String>();
//
//					        groupnamelist=m.get_all_groupname();
//					        System.out.println("groupnameList:"+groupnamelist);
//					        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SearchVoucher.this, android.R.layout.simple_spinner_dropdown_item, groupnamelist);
//					        group_name.setAdapter(adapter);
//
//						}
						if(position == 4){
							tvWarning.setVisibility(View.GONE);
							etVoucherCode.setVisibility(EditText.GONE);
							timeInterval.setVisibility(LinearLayout.GONE);
							etNarration.setVisibility(EditText.GONE);
							group_name.setVisibility(Spinner.VISIBLE);
							voucher_type.setVisibility(Spinner.GONE);
							voucher_number.setVisibility(EditText.GONE);

					        List<String> accountnamelist = new ArrayList<String>();
					        //IPaddr = MainActivity.IPaddr;
					        
					        //Object[] subgroupnames = (Object[])group.getSubGroupsByGroupName(params,client_id);
					        Object[] groupname=(Object[]) account.getAllAccountNames(client_id);
		                    // loop through subgroup names list 
		                    for(Object accname : groupname)
		                    {
		                    	accountnamelist.add((String)accname);
		                    }
					       
					        System.out.println("groupnameList:"+accountnamelist);
					        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SearchVoucher.this, android.R.layout.simple_spinner_dropdown_item, accountnamelist);
					        group_name.setAdapter(adapter);

						}
						if(position == 5){
							tvWarning.setVisibility(View.GONE);
							etVoucherCode.setVisibility(EditText.GONE);
							timeInterval.setVisibility(LinearLayout.GONE);
							etNarration.setVisibility(EditText.GONE);
							voucher_type.setVisibility(Spinner.VISIBLE);
							voucher_number.setVisibility(EditText.GONE);
							group_name.setVisibility(Spinner.GONE);

					       List<String> voucherTypes = new ArrayList<String>(Arrays.asList(new String[] {"Contra","Journal","Payment","Receipt","Credit note",
									"Debit note","Sales","Sales return","Purchase","Purchase return"}));
					       
					        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SearchVoucher.this, android.R.layout.simple_spinner_dropdown_item, voucherTypes);
					        voucher_type.setAdapter(adapter);

						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
				Button btnView = (Button)layout.findViewById(R.id.btnView);
			   	Button btnCancel = (Button)layout.findViewById(R.id.btnCancel);
			   	btnView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						int pos = searchBy.getSelectedItemPosition();
						boolean validate = true;

						if(pos == 0){
							searchByRefNumber = etVoucherCode.getText().toString();
							if(searchByRefNumber.length() < 1){
								tvWarning.setVisibility(View.VISIBLE);
								tvWarning.setText("Please enter voucher reference number");
								validate = false;
							}
							else{
								searchVoucherBy = 1; //by reference no
								Object[] params = new Object[]{1,searchByRefNumber};
								getallvouchers(params);
								validate = true;

							}
						}  
						else if(pos == 1){
							Boolean validateDateFlag = m.validateDate(financialFromDate, financialToDate, from_btn_ID.getText().toString(), 
									to_btn_ID.getText().toString(), "validatebothFromToDate",tvWarning);
							if(validateDateFlag){
								givenfromDateString = m.givenfromDateString;
								givenToDateString = m.givenToDateString;
									searchVoucherBy = 2; // by date
									Object[] params = new Object[]{2,"",givenfromDateString,givenToDateString};
									getallvouchers(params);
							}
						}
						else if(pos == 2){
							searchByNarration = etNarration.getText().toString();
							if(searchByNarration.length() < 1){
								tvWarning.setVisibility(View.VISIBLE);
								tvWarning.setText("Please enter narration");
								validate = false;
							}
							else{
								searchVoucherBy = 3; //by narration
								Object[] params = new Object[]{3,searchByNarration};
								getallvouchers(params);
								validate = true;
							}
						}else if (pos==3) {
							voucher_num=voucher_number.getText().toString();
							if(voucher_num.length() < 1){
								tvWarning.setVisibility(View.VISIBLE);
								tvWarning.setText("Please enter voucher number");
								validate = false;
							}else
							{
								searchVoucherBy = 4; //by voucher number
								
								Object[] params = new Object[]{4,voucher_num};
								getallvouchers(params);
								validate = true;
							}
							//dialog.dismiss();

						}
						else if (pos==4) {  
							Account_Name=group_name.getSelectedItem().toString();
							searchVoucherBy = 5; //by account name
							Object[] params = new Object[]{5,financialFromDate,financialToDate,Account_Name};
							getallvouchers(params);
							validate = true;
						}
						else if (pos==5) {
							vouchertypeflag=voucher_type.getSelectedItem().toString();
							searchVoucherBy = 6; //by voucher type name
							Object[] params = new Object[]{6,financialFromDate,financialToDate,vouchertypeflag};
							getallvouchers(params);
							validate = true;
						}
						if(pos==1&&!module.validateDateFlag||validate == false)
						{
							
						}else
						{
							dialog.dismiss();
						}
					}
					
				});

			   	btnCancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog=builder.create();
				dialog.show();

			}
		});
	}


	public void addTable() {

		if(searchedVoucherGrid.size() > 0){
            addHeader();
        }



		/** Create a TableRow dynamically **/
		for(int i=0;i<searchedVoucherGrid.size();i++){
			ArrayList<String> columnValue = new ArrayList<String>();
			columnValue.addAll(searchedVoucherGrid.get(i));
			tr = new TableRow(SearchVoucher.this);

			for(int j=0;j<columnValue.size();j++){
				/** Creating a TextView to add to the row **/
				addRow(columnValue.get(j),i); 
				if ((i + 1) % 2 == 0)
					label.setBackgroundColor(Color.parseColor("#2f2f2f"));
				else
					label.setBackgroundColor(Color.parseColor("#085e6b")); //blue theme
				/*
				 * set center aligned gravity for amount and for others set center gravity
				 */
				if(j==6){

					label.setGravity(Gravity.CENTER|Gravity.RIGHT);

					if(columnValue.get(j).length() > 0){

						colValue=columnValue.get(j);
						if(!"".equals(colValue)){
							System.out.println("m in ");
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
				// tr.setId(i);



			}

			// Add the TableRow to the TableLayout
			vouchertable.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
		}
	}


	/*
	 * add column heads to the table
	 */
	public void addHeader() {

		/** Create a TableRow dynamically **/
		final SpannableString rsSymbol = new SpannableString(SearchVoucher.this.getText(R.string.Rs)); 
		ColumnNameList = new String[] { "V. No.","Reference No","Date","Voucher Type","Account Name","Particular",rsSymbol+"Amount","Narration"};

		tr = new TableRow(SearchVoucher.this);

		for(int k=0;k<ColumnNameList.length;k++){
			/** Creating a TextView to add to the row **/

			addRow(ColumnNameList[k],k);
			params.height=LayoutParams.WRAP_CONTENT;
			label.setTextColor(Color.BLACK); 
			label.setGravity(Gravity.CENTER);
			tr.setClickable(false);
		}

		// Add the TableRow to the TableLayout
		vouchertable.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

	}


	/*
	 * this function add the value to the row
	 */
	public void addRow(String string,final int i) {
		tr.setClickable(true);

		tr.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// Toast.makeText(SearchVoucher.this, tr.getId(), Toast.LENGTH_SHORT).show(); 
				//fade the row color(black/gray to orange) when clicked
				View row = vouchertable.getChildAt(i+1);
				
				for (int j = 0; j < ColumnNameList.length; j++) {
					LinearLayout l = (LinearLayout) ((ViewGroup) row)
							.getChildAt(j);
					TextView t = (TextView) l.getChildAt(0);
					ColorDrawable drawable = (ColorDrawable)t.getBackground();
					System.out.println("color:"+drawable.getColor());

					ObjectAnimator colorFade = ObjectAnimator.ofObject(t, "backgroundColor", new ArgbEvaluator(), Color.parseColor("#FBB117"),drawable.getColor());
					colorFade.setDuration(100);
					colorFade.start();
				}


				try {
					final CharSequence[] items = { "Edit voucher", "Copy voucher","Delete voucher"};
					//creating a dialog box for popup
					AlertDialog.Builder builder = new AlertDialog.Builder(SearchVoucher.this);
					//setting title
					builder.setTitle("Edit/Delete Voucher");
					//adding items
					builder.setItems(items, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int pos) {
							if(pos == 0){
								MainActivity.nameflag=true;
								name="Edit voucher";
								//Toast.makeText(context,"name"+name,Toast.LENGTH_SHORT).show();
								cloneflag=false;

								//System.out.println("in addrow"+i); 
								value=searchedVoucherGrid.get(i);
								//Toast.makeText(SearchVoucher.this,"result"+value, Toast.LENGTH_SHORT).show();

								MainActivity.searchFlag=true;
								vouchertype=value.get(3);
								Intent intent = new Intent(context, transaction_tab.class);
								// To pass on the value to the next page
								startActivity(intent);
							}
							if(pos==1){
								MainActivity.nameflag=true;
								cloneflag=true;
								name="Copy voucher";
								//Toast.makeText(context,"name"+name,Toast.LENGTH_SHORT).show();
								//System.out.println("in addrow"+i); 
								value=searchedVoucherGrid.get(i);
								vouchertypeflag=value.get(3);
								
								//Toast.makeText(SearchVoucher.this,"result"+value, Toast.LENGTH_SHORT).show(); 
								MainActivity.searchFlag=true;
								Intent intent = new Intent(context, transaction_tab.class);
								// To pass on the value to the next page
								startActivity(intent);
								//Toast.makeText(context,"name"+name,Toast.LENGTH_SHORT).show();
							}

							if(pos==2){
								AlertDialog.Builder builder = new AlertDialog.Builder(SearchVoucher.this);
								builder.setMessage("Are you sure you want to detete the Voucher?")
								.setCancelable(false)
								.setPositiveButton("Yes",
										new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										value=searchedVoucherGrid.get(i);
										vouchercode=value.get(0);
										vouchertypeflag=value.get(3);
										Object[] params = new Object[]{vouchercode};
										deleteVoucher = (Boolean) transaction.deleteVoucher(params,client_id);
										if(deleteVoucher.equals(true))
										{
											deletedflag = true;
											deleted_vouchercode = vouchercode;
										}
										Object[] allvouchersparams = new Object[]{2,"",financialFromDate,financialToDate,""};
										getallvouchers(allvouchersparams);

										m.toastValidationMessage(context,"Voucher deleted successfully");
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
					});
					dialog=builder.create();
					((Dialog) dialog).show();

				} catch (Exception e) {
					//System.out.println(e);
				}
				return false;
			}
		});

		label = new TextView(SearchVoucher.this);
		label.setText(string);
		label.setTextSize(18);
		label.setTextSize(15);
		label.setTextColor(Color.WHITE);
		label.setGravity(Gravity.CENTER_VERTICAL);
		label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		label.setPadding(2, 2, 2, 2);
		LinearLayout Ll = new LinearLayout(SearchVoucher.this);
		params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				35);
		params.setMargins(1, 1, 1, 1);
		//Ll.setPadding(10, 5, 5, 5);
		Ll.addView(label,params);
		tr.addView((View)Ll); // Adding textView to tablerow.
	}


	public void getallvouchers(Object[] params){
		
		Object[] searchedVoucher = (Object[])transaction.searchVoucher(params,client_id);
		searchedVoucherGrid = new ArrayList<ArrayList<String>>();
		for(Object voucherRow : searchedVoucher){
			Object[] v = (Object[]) voucherRow;
			searchedVoucherList = new ArrayList<String>();
			for(int i=0;i<v.length;i++){
				if(((String) v[3].toString()).equalsIgnoreCase(vouchertypeflag)||params[0].equals(6)){
					searchedVoucherList.add((String) v[i].toString());
				}
			}
			if(searchedVoucherList.size() != 0){
				searchedVoucherGrid.add(searchedVoucherList);
			}
			
		}
		vouchertable.removeAllViews();
        System.out.println("grid in resume"+searchedVoucherGrid);
        if(searchedVoucherGrid.size() > 0){
                addTable();
        }
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 * to execute code when tab is changed because 
	 * when the tab is clicked onResume is called for that activity
	 */
	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("ON search RESUME");
		vouchertypeflag = createVoucher.vouchertypeflag;  
		System.out.println("VOUCHER TYPE"+vouchertypeflag);
		//
        if(searchVoucherBy == 1){ // by reference number
                Object[] params = new Object[]{1,searchByRefNumber,financialFromDate,financialToDate,""};
                getallvouchers(params);
        }
        else if(searchVoucherBy == 2){ // by date
                Object[] params = new Object[]{2,"",financialFromDate,financialToDate,""};
                getallvouchers(params);
        }
        else if(searchVoucherBy == 3){ // narration
                Object[] params = new Object[]{3,"",financialFromDate,financialToDate,searchByNarration};
                getallvouchers(params);
        }
        else if(searchVoucherBy == 4){ // narration
            Object[] params = new Object[]{4,voucher_num};
            getallvouchers(params);
    }
        else if(searchVoucherBy == 5){ //account name
            Object[] params = new Object[]{5,financialFromDate,financialToDate,Account_Name};
            getallvouchers(params);}
        else if(searchVoucherBy == 6){ // voucher type
            Object[] params = new Object[]{6,financialFromDate,financialToDate,vouchertypeflag};
            getallvouchers(params);}
      
	}
	
	
        /*
         * hide header row from voucher table if floating header is present
         */
//        if(searchedVoucherGrid.size() > 0){
//        if(oneTouch > 1){
//                View firstrow = vouchertable.getChildAt(0);
//                for (int k = 0; k < 8; k++) {
//                        LinearLayout l = (LinearLayout) ((ViewGroup) firstrow)
//                                        .getChildAt(k);
//                        TextView tv = (TextView) l.getChildAt(0);
//                        tv.setHeight(0);
//
//                        l.getLayoutParams().height = 0;
//                }
//        }
//        }

	


	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case FROM_DATE_DIALOG_ID:
			
				// set date picker as current date
				return new DatePickerDialog(this, fromdatePickerListener, 
						Integer.parseInt(setfromyear), Integer.parseInt(setfrommonth)-1,Integer.parseInt(setfromday));
		
		case TO_DATE_DIALOG_ID:
		
				// set date picker as current date
				return new DatePickerDialog(this, todatePickerListener, 
						Integer.parseInt(settoyear), Integer.parseInt(settomonth)-1,Integer.parseInt(settoday));
		}
		return null;
	}
	private DatePickerDialog.OnDateSetListener fromdatePickerListener 
    	= new DatePickerDialog.OnDateSetListener() {
 
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
				int year = selectedYear; 
				int month = selectedMonth; 
				int day = selectedDay;
				from_btn_ID.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(String.format("%02d", day)).append("-").append(String.format("%02d", month+1)).append("-")
				.append(year));
				givenfromDateString = String.format("%02d", day)+"-"+String.format("%02d", month+1)+"-"+(year-1);
		}
	};  
	
	private DatePickerDialog.OnDateSetListener todatePickerListener 
	= new DatePickerDialog.OnDateSetListener() {

	// when dialog box is closed, below method will be called.
	public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
			int year = selectedYear; 
			int month = selectedMonth; 
			int day = selectedDay;
			to_btn_ID.setText(new StringBuilder()
			// Month is 0 based, just add 1
			.append(String.format("%02d", day)).append("-").append(String.format("%02d", month+1)).append("-")
			.append(year));
			givenToDateString = String.format("%02d", day)+"-"+String.format("%02d", month+1)+"-"+(year-1);
	}
};  


	public void onBackPressed() {
		MainActivity.nameflag=false;
		Intent intent = new Intent(getApplicationContext(), menu.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}