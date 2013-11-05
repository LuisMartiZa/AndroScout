package com.example.scoutmanager.adapters;

import java.util.ArrayList;

import com.example.scoutmanager.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
public class EducandosEventosListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> values;
 
	public EducandosEventosListAdapter(Context context, ArrayList<String> educandos) {
		super(context, R.layout.educandos_evento, educandos);
		this.context = context;
		this.values = educandos;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.educandos_evento, parent, false);
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
	                "fonts/Roboto-Light.ttf");
		TextView textView = (TextView) rowView.findViewById(R.id.educandoEvento);
		textView.setTypeface(tf);
		textView.setText(values.get(position));
 
		return rowView;
	}
}