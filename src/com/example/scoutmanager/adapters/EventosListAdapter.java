package com.example.scoutmanager.adapters;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Evento;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


public class EventosListAdapter extends ArrayAdapter<Evento> {
	 
    private int resource;
	private LayoutInflater inflater;
 
    public EventosListAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.resource = resource;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = inflater.inflate(resource, null);
        }
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Light.ttf");
        
        Evento ev = (Evento) getItem(position);
        
        TextView name= (TextView) itemView.findViewById(R.id.nameEventoList);
        name.setTypeface(tf);
        name.setText(ev.getNombre());
        name.setTextSize(20);
        
        return itemView;
    }
}