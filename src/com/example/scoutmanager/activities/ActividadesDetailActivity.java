package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Actividades;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActividadesDetailActivity extends Activity {
	
	private Actividades actividad = new Actividades();
	
	public void onCreate(View pView, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.actividades_detail_activity);
		
		try {
			initializeActivity();
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		initializeTypeface();
		
	}
	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null)
			executeShowCommand(intentExtras.getInt("actividadID"));
	}
	
	private void executeShowCommand(int pIndex) {
		try {

			actividad = DataBase.Context.ActividadesSet.get(pIndex);
			actividad.setStatus(Entity.STATUS_UPDATED);
			actividad.bind(this);
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initializeTypeface(){
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
        //TEXTVIEW
        TextView name = (TextView) this.findViewById(R.id.nameActividadText);
        TextView participantes = (TextView) this.findViewById(R.id.participantesText);
        TextView descripcion = (TextView) this.findViewById(R.id.descripText);
        TextView descripcionField = (TextView) this.findViewById(R.id.descripActividad);

        name.setTypeface(tf);
        participantes.setTypeface(tf);
        descripcion.setTypeface(tf);
        descripcionField.setTypeface(tf);


        
        //EDITTEXT
        EditText nameField = (EditText) this.findViewById(R.id.nameEducando);
        EditText participantesField = (EditText) this.findViewById(R.id.participantesActividad);
    
        nameField.setTypeface(tf);
        participantesField.setTypeface(tf);
	}

}