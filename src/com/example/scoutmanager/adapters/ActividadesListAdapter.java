package com.example.scoutmanager.adapters;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Actividades;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


public class ActividadesListAdapter extends ArrayAdapter<Actividades> {
	 
    private int resource;
    private Context context;
	private ArrayList<Actividades> data = new ArrayList<Actividades>();
	
    public ActividadesListAdapter(Context context, int resource, ArrayList<Actividades> data) {
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
        
        Actividades actividad = data.get(position);
        
        TextView name= (TextView) itemView.findViewById(R.id.nameActividadList);
        name.setTypeface(tf);
        name.setText(actividad.getNombre());
        name.setTextSize(20);
        
        return itemView;
    }
}