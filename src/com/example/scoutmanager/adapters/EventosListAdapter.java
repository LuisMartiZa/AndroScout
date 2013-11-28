package com.example.scoutmanager.adapters;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Evento;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


public class EventosListAdapter extends ArrayAdapter<Evento> {
	 
	private int resource;
	private Context context;
	private ArrayList<Evento> data = new ArrayList<Evento>();
 
    public EventosListAdapter(Context context, int resource, ArrayList<Evento> data) {
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
        
        Evento evento = data.get(position);
        
        TextView name= (TextView) itemView.findViewById(R.id.nameEventoList);
        name.setTypeface(tf);
        name.setText(evento.getNombre());
        name.setTextSize(20);
        
        return itemView;
    }
}