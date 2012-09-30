package com.example.gkaakash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.color;
import android.R.string;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Bundle;

public class createVoucher extends Activity{
	
	AlertDialog dialog;
	final Context context = this;
	ListView v_option_list, v_option_list1, v_option_list2;
	private SimpleAdapter adapter, adapter1, adapter2, adapter4;
	final Calendar c = Calendar.getInstance();
	int day, month, year;
	TextView voucherDate;
	static final int VOUCHER_DATE_DIALOG_ID = 1;
	String amount;
	private static final String TAG = "DialogActivity";
    private static final int DLG_EXAMPLE1 = 0;
    private static final int TEXT_ID = 0;
    private List<Map<String, String>> data;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_voucher);
        
        v_option_list =  (ListView)findViewById(R.id.voucher_list);  
		v_option_list1 =  (ListView)findViewById(R.id.voucher_list1); 
		v_option_list2 =  (ListView)findViewById(R.id.voucher_list2);
		
		v_option_list.setTextFilterEnabled(true);
		v_option_list.setCacheColorHint(color.transparent);
		v_option_list1.setCacheColorHint(color.transparent);
		v_option_list2.setCacheColorHint(color.transparent);
		simpleArray1();
		simpleArray2();
		simpleArray3();
	}
	
	private void simpleArray1(){
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		String[] title = new String[]{"Voucher Date","Voucher Number","Narration","Select Project"};
		String[] subItem = new String[]{day+"-"+month+"-"+year,"","","No Project"};
		
		
		
		String[] abc = new String[] {"rowid", "col_1"};
		int[] pqr = new int[] { R.id.tvRowTitle1, R.id.tvSubItem1};
	
		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
	
		for(int i = 0; i < title.length; i++){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("rowid", "" + title[i]);
		map.put("col_1", "" + subItem[i]);
		fillMaps.add(map);
		}
	
		adapter = new SimpleAdapter(this, fillMaps, R.layout.voucher_row_list1, abc, pqr);
		v_option_list.setAdapter(adapter);
		
		v_option_list.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("ParserError")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				v_option_list.setCacheColorHint(color.transparent);
					
				if(position == 0)
				{
					Toast.makeText(createVoucher.this, amount, Toast.LENGTH_LONG).show();
					showDialog(VOUCHER_DATE_DIALOG_ID);
				}	
				if(position == 1)
				{
					final TextView subtitle = (TextView)view.findViewById(R.id.tvSubItem1);
					AlertDialog.Builder editDialog = new AlertDialog.Builder(createVoucher.this);
					editDialog.setTitle("--- Enter Voucher Number ---");
				    
					   final EditText editText = new EditText(createVoucher.this);
					   editText.setText(subtitle.getText());
					   editDialog.setView(editText);
					   editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						    // do something when the button is clicked
						   public void onClick(DialogInterface arg0, int arg1) {
							     subtitle.setText(editText.getText().toString());
							     }
					   });
					   editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					          // do something when the button is clicked
					    public void onClick(DialogInterface arg0, int arg1) {
					    //...
					     }
					    });
					   editDialog.show();
					   
					    
				}
				
				if(position == 2)
				{
					final TextView subtitle = (TextView)view.findViewById(R.id.tvSubItem1);
					AlertDialog.Builder editDialog = new AlertDialog.Builder(createVoucher.this);
					editDialog.setTitle("--- Enter Narration ---");
				    
					   final EditText editText = new EditText(createVoucher.this);
					   editText.setText(subtitle.getText());
					   editDialog.setView(editText);
					   editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						    // do something when the button is clicked
						   public void onClick(DialogInterface arg0, int arg1) {
							     subtitle.setText(editText.getText().toString());
							     }
					   });
					   editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					          // do something when the button is clicked
					    public void onClick(DialogInterface arg0, int arg1) {
					    //...
					     }
					    });

					   editDialog.show();	    
				}
				if(position == 3){
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

@SuppressLint({ "ParserError", "ParserError", "ParserError" })
private void simpleArray2() {
	// TODO Auto-generated method stub
	String firstRowFlag = "true";
	String[] title1 = new String[]{"Account Name"};
	String[] title24 = new String[]{"Amount"};
	
	String[] abc1 = new String[] {"rowid1","rowid24"};
	int[] pqr1 = new int[] { R.id.tvRowTitle2, R.id.tvRowTitle24};

	List<HashMap<String, String>> fillMaps1 = new ArrayList<HashMap<String, String>>();

	for(int i = 0; i < title1.length; i++){
	HashMap<String, String> map1 = new HashMap<String, String>();
	map1.put("rowid1", "" + title1[i]);
	map1.put("rowid24", "" + title24[i]);
	fillMaps1.add(map1);
	}

	adapter1 = new SimpleAdapter(this, fillMaps1, R.layout.voucher_row_list2, abc1, pqr1);
	v_option_list1.setAdapter(adapter1);
	
	v_option_list1.setOnItemClickListener(new OnItemClickListener() {
	
		@SuppressLint("ParserError")
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			if (position == 0){
				EditText abc = (EditText) findViewById(R.id.editText1);
				amount = abc.getText().toString();
				
			}
		}
	});
	
	/*HashMap<String, String> map1 = new HashMap<String, String>();
	map1.put("rowid1", "" + "Account Name");
	fillMaps1.add(map1);
	adapter1 = new SimpleAdapter(this, fillMaps1, R.layout.voucher_row_list2, abc1, pqr1);
	v_option_list1.setAdapter(adapter1);*/
}

private void simpleArray3() {
	// TODO Auto-generated method stub
	String[] title2 = new String[]{"Account Name"};
	String[] title23 = new String[]{"Amount"};
	
	String[] abc2 = new String[] {"rowid2","rowid23"};
	int[] pqr2 = new int[] { R.id.tvRowTitle3, R.id.tvRowTitle23};

	List<HashMap<String, String>> fillMaps2 = new ArrayList<HashMap<String, String>>();

	for(int i = 0; i < title2.length; i++){
	HashMap<String, String> map2 = new HashMap<String, String>();
	map2.put("rowid2", "" + title2[i]);
	map2.put("rowid23", "" + title23[i]);
	fillMaps2.add(map2);
	}

	adapter2 = new SimpleAdapter(this, fillMaps2, R.layout.voucher_row_list3, abc2, pqr2);
	v_option_list2.setAdapter(adapter2);
}

public void addRow(View v){
	
	
	
	
	String[] title1 = new String[]{"Account Name","Account code"};
	String[] title24 = new String[]{"Amount","code"};
	
	String[] abc1 = new String[] {"rowid1","rowid24"};
	int[] pqr1 = new int[] { R.id.tvRowTitle2, R.id.tvRowTitle24};

	List<HashMap<String, String>> fillMaps4 = new ArrayList<HashMap<String, String>>();
	
	for(int i = 0; i < title1.length; i++){
	HashMap<String, String> map1 = new HashMap<String, String>();
	map1.put("rowid1", "" + title1[i]);
	map1.put("rowid24", "" + title24[i]);
	fillMaps4.add(map1);
	}
	
	adapter4 = new SimpleAdapter(this, fillMaps4, R.layout.voucher_row_list2, abc1, pqr1);
	v_option_list1.getAdapter();
	v_option_list1.setAdapter(adapter4);
}
}
