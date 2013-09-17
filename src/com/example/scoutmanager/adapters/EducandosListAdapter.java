package com.example.scoutmanager.adapters;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Educando;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


public class EducandosListAdapter extends ArrayAdapter<Educando> {
	 
    private int resource;
	private LayoutInflater inflater;
 
    public EducandosListAdapter(Context context, int resource) {
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
        Educando educando = (Educando) getItem(position);
        ((TextView) itemView.findViewById(R.id.nameEducandoList)).setText(educando.getNombre());
        ((TextView) itemView.findViewById(R.id.surnameEducandoList)).setText(educando.getApellidos());
        
        return itemView;
    }
}