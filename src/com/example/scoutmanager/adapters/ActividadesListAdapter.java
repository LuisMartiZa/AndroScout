package com.example.scoutmanager.adapters;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Actividades;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


public class ActividadesListAdapter extends ArrayAdapter<Actividades> {
	 
    private int resource;
	private LayoutInflater inflater;
 
    public ActividadesListAdapter(Context context, int resource) {
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
        
        Actividades actividad = (Actividades) getItem(position);
        
        TextView name= (TextView) itemView.findViewById(R.id.nameActividadList);
        name.setTypeface(tf);
        name.setText(actividad.getNombre());
        name.setTextSize(20);
        
        return itemView;
    }
}