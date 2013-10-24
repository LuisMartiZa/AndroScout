package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.ActividadesListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Actividades;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActividadesListActivity extends Activity {
	
	private ListView actividadesListView;
    private ArrayAdapter<Actividades> actividadesListViewAdapter; 
    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent detailIntent = new Intent(pView.getContext(), ActividadesDetailActivity.class);
				detailIntent.putExtra("actividadID", pPosition);
				
				startActivity(detailIntent);
			
				
	        } catch (Exception e) {
				Toast.makeText(pView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.actividades_list_activity);
		
		try {
			initializeActivity();
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void initializeActivity() throws AdaFrameworkException {
				
    	this.actividadesListView = (ListView) this.findViewById(R.id.ActividadesListView);
    	
    	if (this.actividadesListView != null) {
    		this.actividadesListView.setOnItemClickListener(itemClickListener);
    		this.actividadesListViewAdapter= new ActividadesListAdapter(this, R.layout.actividades_row);
    		this.actividadesListView.setAdapter(this.actividadesListViewAdapter);
    		
    		DataBase.Context.ActividadesSet.setAdapter(this.actividadesListViewAdapter);
    		DataBase.Context.ActividadesSet.fill();
    		Actividades actividad = DataBase.Context.ActividadesSet.get(0);
    		
    		Log.w("TAG", "NOMBRE ACTIVIDAD" + actividad.getNombre());
    	}
    }
}
