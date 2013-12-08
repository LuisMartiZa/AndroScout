package com.example.scoutmanager.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.ActividadesListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Actividades;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActividadesListActivity extends Activity {
	
	private ListView actividadesListView;
    private ArrayAdapter<Actividades> actividadesListViewAdapter;
	private ArrayList<Actividades> arrayListActividades= new ArrayList<Actividades>();
	private String tipo="";
    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				Intent detailIntent = new Intent(pView.getContext(), ActividadesDetailActivity.class);
				detailIntent.putExtra("actividadID", arrayListActividades.get(pPosition).getID());
				detailIntent.putExtra("tipo", tipo);
				
				startActivity(detailIntent);
			
				
	        } catch (Exception e) {
				Toast.makeText(pView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.actividades_list_activity);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("ACTIVIDADES");
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
		actionbar.setHomeButtonEnabled(true);
		
		Bundle intentExtras = this.getIntent().getExtras();
		tipo= intentExtras.getString("tipo");

		try {
			fillArrayListActividades(intentExtras.getString("tipo"));
			initializeListView();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void initializeListView() throws AdaFrameworkException {
				
    	this.actividadesListView = (ListView) this.findViewById(R.id.ActividadesListView);
    	
    	if (this.actividadesListView != null) {
    		this.actividadesListView.setOnItemClickListener(itemClickListener);
    		this.actividadesListViewAdapter= new ActividadesListAdapter(this, R.layout.actividades_row, arrayListActividades);
    		this.actividadesListView.setAdapter(this.actividadesListViewAdapter);
    	}
    }
	
	private void fillArrayListActividades(String type) throws AdaFrameworkException{
		
		DataBase.Context.ActividadesSet.fill();
		
		arrayListActividades= new ArrayList<Actividades>();
		
		String wherePattern = "tipoActividad = ?";
        List<Actividades> actividadesList = DataBase.Context.ActividadesSet.search("tActividades", false, null, wherePattern, new String[] { type }, null, null, null, null, null);

		
		for(int i=0; i<actividadesList.size();++i){
			
			arrayListActividades.add(actividadesList.get(i));
		}
	}
}
