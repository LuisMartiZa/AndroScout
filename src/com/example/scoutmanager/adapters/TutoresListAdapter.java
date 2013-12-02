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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.view.LayoutInflater;


public class TutoresListAdapter extends ArrayAdapter<Tutor> implements Filterable {
	 
    private int resource;
	private Context context;
	private Filter filter;
	private ArrayList<Tutor> mOriginalValues = new ArrayList<Tutor>(); // Original Values
	private ArrayList<Tutor> mDisplayedValues  = new ArrayList<Tutor>();   // Values to be displayed

 
    public TutoresListAdapter(Context context, int resource, ArrayList<Tutor> data) {
        super(context, resource, data);
        
        this.resource = resource;
        this.context = context;
		this.mOriginalValues = new ArrayList<Tutor>(data);
		this.mDisplayedValues = new ArrayList<Tutor>(data);
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
        
        Tutor tutor =  mDisplayedValues.get(position);
        
    	TextView name= (TextView) itemView.findViewById(R.id.tipoTutorList);
        name.setTypeface(tf);
        name.setTextSize(20);
        
        TextView surname= (TextView) itemView.findViewById(R.id.hijosTutorList);
        surname.setTypeface(tf);
        surname.setTextSize(20);
        
        name.setText(tutor.getNombre() );
        surname.setText(tutor.getApellidos());

        return itemView;
    }
    
    @Override
    public Filter getFilter()
    {
        if (filter == null)
            filter = new TutoresFilter();

        return filter;
    }

    private class TutoresFilter extends Filter
    {
            @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {   
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0)
            {
                ArrayList<Tutor> list = new ArrayList<Tutor>(mOriginalValues);
                results.values = list;
                results.count = list.size();
            }
            else
            {
                final ArrayList<Tutor> list = new ArrayList<Tutor>(mOriginalValues);
                final ArrayList<Tutor> nlist = new ArrayList<Tutor>();
                int count = list.size();

                for (int i=0; i<count; i++)
                {
                    final Tutor tutor = list.get(i);
                    final String value = tutor.getNombre().toLowerCase();

                    if (value.startsWith(prefix))
                    {
                        nlist.add(tutor);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDisplayedValues = (ArrayList<Tutor>)results.values;

            clear();
            int count = mDisplayedValues.size();
            for (int i=0; i<count; i++)
            {
                Tutor tutor = (Tutor)mDisplayedValues.get(i);
                add(tutor);
            }
        }

    }
}