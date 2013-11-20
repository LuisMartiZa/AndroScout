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
		
		if(educando.getImagen() != null){
			File image = new  File(educando.getImagen()); 
			
		    if(image.exists()){
				Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath());
				holder.image.setImageBitmap(bmp);
		    }
		    
		}else{
		
	        holder.image= (ImageView) row.findViewById(R.id.imageEducando);
		}
		
		return row;
	}

	static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
}