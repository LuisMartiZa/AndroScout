package com.example.scoutmanager.adapters;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Tutor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;


public class TutoresListAdapter extends ArrayAdapter<Tutor> {
	 
    private int resource;
	private Context context;
	private ArrayList<Tutor> data = new ArrayList<Tutor>();
 
    public TutoresListAdapter(Context context, int resource, ArrayList<Tutor> data) {
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
        
        Tutor tutor =  data.get(position);
        TextView name= (TextView) itemView.findViewById(R.id.tipoTutorList);
        name.setTypeface(tf);
        name.setText(tutor.getNombre() + " " + tutor.getTipo() + " de");
        name.setTextSize(20);
        
        TextView surname= (TextView) itemView.findViewById(R.id.hijosTutorList);
        surname.setTypeface(tf);
        //surname.setText(tutor.getHijos().get(0).getNombre());
        surname.setTextSize(20);
        
        return itemView;
    }
}