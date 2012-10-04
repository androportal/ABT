package com.example.gkaakash;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class createVoucher extends Activity {
	TableLayout list;
	int rowsSoFar = 0;
	String amount;
	AlertDialog dialog;
	final Context context = this;
	TextView voucherDate;
	final List<String> list1=new ArrayList<String>();
	final List<String> dr_cr=new ArrayList<String>();
	ListView v_option_list, v_option_list2,v_option_list3,v_option_list4;;
	final Calendar c = Calendar.getInstance();
	int day, month, year;
	static final int VOUCHER_DATE_DIALOG_ID = 1;
	private SimpleAdapter adapter,adapter2,adapter3,adapter4;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	       	super.onCreate(savedInstanceState);
	       	setContentView(R.layout.create_voucher);
	       	v_option_list =  (ListView)findViewById(R.id.voucher_list);
	       	v_option_list.setTextFilterEnabled(true);
			v_option_list.setCacheColorHint(color.transparent);
			
			v_option_list4 =  (ListView)findViewById(R.id.voucher_list4);
	       	v_option_list4.setTextFilterEnabled(true);
			v_option_list4.setCacheColorHint(color.transparent);
	       	
	       	list1.add("Miscellaneous Expenses(Asset)");
	       	list1.add("Current Assets");
	       	list1.add("1");
	       	list1.add("Reserve");
	       	list1.add("loans liability"); 
	        
	        Button addButton = (Button) findViewById( R.id.add );
	        // Every time the "+" button is clicked,
	        // add a new row to the table.
	        addButton.setOnClickListener( new OnClickListener() {
				public void onClick(View view) { 
					addButton(); }
				});
	        
	        list = (TableLayout) findViewById( R.id.Vouchertable );
	        // Start with one row.
	        addButton();
	        simpleArray1();
	        simpleArray4();
	        OnClickListener1();
	        
	        
    }
    
    private void OnClickListener1() {
		// TODO Auto-generated method stub
    	Button btnResetVoucher = (Button) findViewById( R.id.btnResetVoucher );
    	btnResetVoucher.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
			}
		});
    	
	}

	private void simpleArray4() {
		// TODO Auto-generated method stub
    	String[] title = new String[]{"Select project"};
		String[] subItem = new String[]{"No Project"};
		
		
		
		String[] abc = new String[] {"rowid", "col_1"};
		int[] pqr = new int[] { R.id.tvRowTitle1, R.id.tvSubItem1};
	
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
	
		for(int i = 0; i < title.length; i++){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rowid", "" + title[i]);
		map.put("col_1", "" + subItem[i]);
		fillMaps.add(map);
		}
	
		adapter4 = new SimpleAdapter(this, fillMaps, R.layout.child_row1, abc, pqr);
		v_option_list4.setAdapter(adapter4);
		v_option_list4.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				v_option_list4.setCacheColorHint(color.transparent);
				if(position == 0){
					final TextView subtitle = (TextView)view.findViewById(R.id.tvSubItem1);
					final CharSequence[] items = { "GNUKhata", "Spoken Tutorial" };
					//creating a dialog box for popup
			        AlertDialog.Builder builder = new AlertDialog.Builder(context);
			        //setting title
			        builder.setTitle("Select Preference");
			        //adding items
			        builder.setItems(items, new DialogInterface.OnClickListener() {
			        @SuppressLint("ParserError")
					public void onClick(DialogInterface dialog, int pos) {
			        	//code for the actions to be performed on clicking popup item goes here ...
			            switch (pos) {
			                case 0:
			                              {
			                            	  subtitle.setText(items[pos]);
			                              }break;
			                case 1:
			                              {
			                            	  subtitle.setText(items[pos]);
			                              }break;
			            }
			        }
			        });
			        //building a complete dialog
					dialog=builder.create();
					dialog.show();
					
				}
			}
			
		});
	}

	

	private void simpleArray1() {
		// TODO Auto-generated method stub
    	year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		String[] title = new String[]{"Voucher Date"};
		String[] subItem = new String[]{day+"-"+month+"-"+year};
		
		
		
		String[] abc = new String[] {"rowid", "col_1"};
		int[] pqr = new int[] { R.id.tvRowTitle1, R.id.tvSubItem1};
	
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
	
		for(int i = 0; i < title.length; i++){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rowid", "" + title[i]);
		map.put("col_1", "" + subItem[i]);
		fillMaps.add(map);
		}
	
		adapter = new SimpleAdapter(this, fillMaps, R.layout.child_row1, abc, pqr);
		v_option_list.setAdapter(adapter);
		
		v_option_list.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				v_option_list.setCacheColorHint(color.transparent);
					
				if(position == 0)
				{
					showDialog(VOUCHER_DATE_DIALOG_ID);
				}	
			}
		});
	}
    
    @Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case VOUCHER_DATE_DIALOG_ID:
			// set 'from date' date picker as current date
			   return new DatePickerDialog(this, fromdatePickerListener, 
	                         year, month,day);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener fromdatePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
		year = selectedYear;
		month = selectedMonth;
		day = selectedDay;
		
		voucherDate =  (TextView)findViewById(R.id.tvSubItem1);   
		// set selected date into textview
		voucherDate.setText(new StringBuilder().append(day).append("-").append(month + 1)
				   .append("-").append(year)
		   .append(" "));	
		}
};

	/***
     * Gets all the information necessary to delete itself from the constructor.
     * Deletes itself when the button is pressed.
     */
    private static class RowRemover implements OnClickListener {
    	private TableLayout list;
    	private TableRow rowToBeRemoved;
    	
    	/***
    	 * @param list	The list that the button belongs to
    	 * @param row	The row that the button belongs to
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
    	TableRow newRow = new TableRow( list.getContext() );
    	newRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
    	//newRow.addView(child, width, height)
    	
    	TextView tvac = new TextView(newRow.getContext());
      	tvac.setText( "Account Type " );
      	
    	Spinner sp1 = new Spinner( newRow.getContext() );
    	dr_cr.add("Cr");
    	dr_cr.add("Dr");
    	ArrayAdapter<String> da1 = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,dr_cr);
  	   	da1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(da1);
        
    	TextView tv = new TextView(newRow.getContext());
    	tv.setText("        Account Name");
    	
    	Spinner actionButton = new Spinner( newRow.getContext() );
    	ArrayAdapter<String> da = new ArrayAdapter<String>(createVoucher.this, android.R.layout.simple_spinner_item,list1);
    	da.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionButton.setAdapter(da);
        actionButton.setMinimumWidth(283);
        
        TextView tv1 = new TextView(newRow.getContext());
    	tv1.setText( "        Amount" );
    	
    	//tv1.setWidth(100);
    	EditText et = new EditText(newRow.getContext());
    	et.setText( "0.00" );
    	et.setWidth(80);
    	et.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
    	
    	//actionButton.setText( "Action: " + ++rowsSoFar );
    	Button removeSelfButton = new Button( newRow.getContext() );
    	removeSelfButton.setText( "  -  " );
    	
    	//removeSelfButton.setBackgroundResource(R.drawable.button_plus_green);
    	//removeSelfButton.setBackgroundColor(color)
    	// pass on all the information necessary for deletion
    	removeSelfButton.setOnClickListener( new RowRemover( list, newRow ));
    	newRow.addView(tvac);
    	newRow.addView(sp1);
    	newRow.addView(tv);
    	newRow.addView( actionButton );
    	newRow.addView(tv1);
    	newRow.addView(et);
    	newRow.addView( removeSelfButton );
    	list.addView(newRow);
    }
}