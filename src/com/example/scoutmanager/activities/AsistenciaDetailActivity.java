package com.example.scoutmanager.activities;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.AsistenciaListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AsistenciaDetailActivity extends Activity {
	
	private ListView educandosListView;
    private ArrayAdapter<Educando> asistenciaListViewAdapter;
	private ArrayList<Educando> arrayListEducandos= new ArrayList<Educando>();
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.asistencia_detail_activity);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("ASISTENCIA");
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
		actionbar.setHomeButtonEnabled(true);

		try {
			fillArrayListEducandosAsistencias();
			initializeListView();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	
	private void initializeListView() throws AdaFrameworkException {
				
    	this.educandosListView = (ListView) this.findViewById(R.id.asistenciaListView);
    	
    	if (this.educandosListView != null) {
    		this.asistenciaListViewAdapter= new AsistenciaListAdapter(this, R.layout.asistencia_row, arrayListEducandos);
    		this.educandosListView.setAdapter(this.asistenciaListViewAdapter);
    	}
    }
	
	private void fillArrayListEducandosAsistencias() throws AdaFrameworkException{
		
		DataBase.Context.EducandosSet.fill();
		
		arrayListEducandos= new ArrayList<Educando>();

		for(int i=0; i<DataBase.Context.EducandosSet.size();++i){
			
			arrayListEducandos.add(DataBase.Context.EducandosSet.get(i));
		}
	}
}