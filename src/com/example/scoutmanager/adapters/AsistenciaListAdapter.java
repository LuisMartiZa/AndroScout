package com.example.scoutmanager.adapters;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Asistencia;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;


public class AsistenciaListAdapter extends ArrayAdapter<Educando> {
	 
    private int resource;
	private Context context;
	private ArrayList<Educando> data = new ArrayList<Educando>();
 
    public AsistenciaListAdapter(Context context, int resource, ArrayList<Educando> data) {
        super(context, resource, data);
        
        this.resource = resource;
        this.context = context;
		this.data = data;
    }
    
    static class ViewHolder 
    {
        TextView name;
        TextView surname;
        TextView asistencia;
        ImageView increment;
        ImageView decrement;
        
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
    	View row = convertView;
		final ViewHolder holder;
		
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Light.ttf");

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) row.findViewById(R.id.nombreEducandoAsistencia);
			holder.surname = (TextView) row.findViewById(R.id.apellidosEducandoAsistencia);
			holder.asistencia = (TextView) row.findViewById(R.id.asistenciaEducando);

			holder.increment = (ImageView) row.findViewById(R.id.incrementButton);
			holder.decrement = (ImageView) row.findViewById(R.id.decrementButton);

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		
		final Educando educando = data.get(position);
		
		holder.name.setText(educando.getNombre());
		holder.name.setTypeface(tf);
		holder.name.setTextSize(20);
		
		holder.surname.setText(educando.getApellidos());
		holder.surname.setTypeface(tf);
		holder.surname.setTextSize(20);
		
		holder.asistencia.setText(Integer.toString(educando.getAsistencia().getAsistencias()));
		holder.asistencia.setTypeface(tf);
		holder.asistencia.setTextSize(20);
  
        //define an onClickListener for the ImageView.
        holder.increment.setOnClickListener(new OnClickListener() 
        {           
            public void onClick(View v) 
            {
                int increment = educando.getAsistencia().getAsistencias();
                Asistencia asistencia = educando.getAsistencia();
                
                asistencia.setAsiste(increment+1);
                asistencia.setStatus(Entity.STATUS_UPDATED);
                
                try {
					DataBase.Context.AsistenciaSet.save(asistencia);
				} catch (AdaFrameworkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                holder.asistencia.setText(Integer.toString(increment+1));
            }
        });
        
      //define an onClickListener for the ImageView.
        holder.decrement.setOnClickListener(new OnClickListener() 
        {           
            public void onClick(View v) 
            {
                int decrement = educando.getAsistencia().getAsistencias();
                
                if(decrement!=0){
	                Asistencia asistencia = educando.getAsistencia();
	                
	                asistencia.setAsiste(decrement-1);
	                asistencia.setStatus(Entity.STATUS_UPDATED);
	                
	                try {
						DataBase.Context.AsistenciaSet.save(asistencia);
					} catch (AdaFrameworkException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                holder.asistencia.setText(Integer.toString(decrement-1));
                }
            }
        });
 
        //return the row view.
        return row;
    }
}