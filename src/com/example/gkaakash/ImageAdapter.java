package com.example.gkaakash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
	private Context context;
	private final String[] MENU_OPTIONS;
 
	public ImageAdapter(Context context, String[] MENU_OPTIONS) {
		this.context = context;
		this.MENU_OPTIONS = MENU_OPTIONS;
	}
 
	public View getView(int position, View convertView, ViewGroup parent) {
 
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View gridView;
 
		if (convertView == null) {
 
			gridView = new View(context);
 
			// get layout from mobile.xml
			gridView = inflater.inflate(R.layout.menu_grid, null);
 
			// set value into textview
			TextView textView = (TextView) gridView
					.findViewById(R.id.grid_item_label);
			textView.setText(MENU_OPTIONS[position]);
 
			// set image based on selected text
			ImageView imageView = (ImageView) gridView
					.findViewById(R.id.grid_item_image);
 
			if (MENU_OPTIONS[position].equalsIgnoreCase("Create account")) {
				imageView.setImageResource(R.drawable.account_logo);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("Transaction")) {
				imageView.setImageResource(R.drawable.money_image);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("Reports")) {
				imageView.setImageResource(R.drawable.report_logo);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("Bank Reconciliation")) {
				imageView.setImageResource(R.drawable.help_logo);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("Preferences")) {
				imageView.setImageResource(R.drawable.settings_logo);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("RollOver")) {
				imageView.setImageResource(R.drawable.settings_logo);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("Export organisation")) {
				imageView.setImageResource(R.drawable.export_logo);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("Help")) {
				imageView.setImageResource(R.drawable.help_logo);
			} else if (MENU_OPTIONS[position].equalsIgnoreCase("Account Settings")){
				imageView.setImageResource(R.drawable.settings_logo);
			}
 
		} else {
			gridView = (View) convertView;
		}
 
		return gridView;
	}
 
	@Override
	public int getCount() {
		return MENU_OPTIONS.length;
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
}