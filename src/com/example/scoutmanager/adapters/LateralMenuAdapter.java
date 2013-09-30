package com.example.scoutmanager.adapters;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Menu_items;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LateralMenuAdapter extends ArrayAdapter<Object> {

	private Context context;
	private ArrayList<Menu_items> items;

	public LateralMenuAdapter(Context context, ArrayList<Menu_items> items) {
		super(context, R.layout.menu_row);
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Create a new view into the list.
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.menu_row, parent, false);

		// Set data into the view.
		
		TextView tvTitle = (TextView) rowView.findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
		tvTitle.setTypeface(tf);
		tvTitle.setText(this.items.get(position).getTitle());
		tvTitle.setTextSize(20);
		tvTitle.setTextColor(Color.WHITE);
        rowView.setBackgroundColor(Color.parseColor("#99CC00"));
		
		ImageView ivItem = (ImageView) rowView.findViewById(R.id.imageView1);
		ivItem.setImageResource(this.items.get(position).getImage());

		return rowView;
	}

}