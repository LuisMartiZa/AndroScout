package com.example.scoutmanager.adapters;

import java.io.File;
import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.entities.Educando;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EducandosGridAdapter extends ArrayAdapter<Educando> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<Educando> data = new ArrayList<Educando>();

	public EducandosGridAdapter(Context context, int layoutResourceId,
			ArrayList<Educando> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.imageTitle = (TextView) row.findViewById(R.id.nameEducandoGrid);
			holder.image = (ImageView) row.findViewById(R.id.educandoImageGrid);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		Educando educando = data.get(position);
		holder.imageTitle.setText(educando.getNombre());
		
		if(!educando.getImagen().equals("")){
			File image = new  File(educando.getImagen()); 
			
		    if(image.exists()){
				Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath());
				holder.image.setImageBitmap(bmp);
		    }
		    
		}else{
					
	        if(educando.getSeccionEducando().getNombre().equals("Castores"))
	        {
	        	if(educando.getEtapaEducando().getNombre().equals("Castor sin paletas")){
	        		holder.image.setImageResource(R.drawable.integracioncastores);
	        	}else if (educando.getEtapaEducando().getNombre().equals("Castor con paletas")) {
	        		holder.image.setImageResource(R.drawable.participacioncastores);
				}else if (educando.getEtapaEducando().getNombre().equals("Castor Keoo")) {
	        		holder.image.setImageResource(R.drawable.animacioncastores);
				}
	        }
	        
	        if(educando.getSeccionEducando().getNombre().equals("Manada"))
	        {
	        	if(educando.getEtapaEducando().getNombre().equals("Huella de Akela")){
	        		holder.image.setImageResource(R.drawable.integracionmanada);
	        	}else if (educando.getEtapaEducando().getNombre().equals("Huella de Baloo")) {
	        		holder.image.setImageResource(R.drawable.participacionmanada);
				}else if (educando.getEtapaEducando().getNombre().equals("Huella de Bagheera")) {
	        		holder.image.setImageResource(R.drawable.animacionmanada);
				}
	        }
	        
	        if(educando.getSeccionEducando().getNombre().equals("Tropa"))
	        {
	        	if(educando.getEtapaEducando().getNombre().equals("Integraci—n")){
	        		holder.image.setImageResource(R.drawable.integraciontropa);
	        	}else if (educando.getEtapaEducando().getNombre().equals("Participaci—n")) {
	        		holder.image.setImageResource(R.drawable.participaciontropa);
				}else if (educando.getEtapaEducando().getNombre().equals("Animaci—n")) {
	        		holder.image.setImageResource(R.drawable.animaciontropa);
				}
	        }
	        
	        if(educando.getSeccionEducando().getNombre().equals("Unidad"))
	        {
	        	if(educando.getEtapaEducando().getNombre().equals("Integraci—n")){
	        		holder.image.setImageResource(R.drawable.integracionunidad);
	        	}else if (educando.getEtapaEducando().getNombre().equals("Participaci—n")) {
	        		holder.image.setImageResource(R.drawable.participacionunidad);
				}else if (educando.getEtapaEducando().getNombre().equals("Animaci—n")) {
	        		holder.image.setImageResource(R.drawable.animacionunidad);
				}
	        }
	        
	        if(educando.getSeccionEducando().getNombre().equals("Clan"))
	        {
	        	if(educando.getEtapaEducando().getNombre().equals("Integraci—n")){
	        		holder.image.setImageResource(R.drawable.integracionclan);
	        	}else if (educando.getEtapaEducando().getNombre().equals("Participaci—n")) {
	        		holder.image.setImageResource(R.drawable.participacionclan);
				}else if (educando.getEtapaEducando().getNombre().equals("Animaci—n")) {
	        		holder.image.setImageResource(R.drawable.animacionclan);
				}
	        }
	        	
		}
		
		return row;
	}

	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
}