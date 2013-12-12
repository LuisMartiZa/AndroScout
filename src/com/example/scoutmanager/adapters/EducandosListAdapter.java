package com.example.scoutmanager.adapters;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Educando;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


public class EducandosListAdapter extends ArrayAdapter<Educando> {
	 
    private int resource;
	private Context context;
	private ArrayList<Educando> data = new ArrayList<Educando>();
 
    public EducandosListAdapter(Context context, int resource, ArrayList<Educando> data) {
        super(context, resource, data);
        
        this.resource = resource;
        this.context = context;
		this.data = data;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
        	LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemView = inflater.inflate(resource, parent, false);
        }
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Light.ttf");
        
        Educando educando =  data.get(position);
        TextView name= (TextView) itemView.findViewById(R.id.nombreEducandoList);
        name.setTypeface(tf);
        name.setText(educando.getNombre());
        name.setTextSize(20);
        
        TextView surname= (TextView) itemView.findViewById(R.id.apellidoEducandoList);
        surname.setTypeface(tf);
        surname.setText(educando.getApellidos());
        surname.setTextSize(20);
        
        return itemView;
    }
}