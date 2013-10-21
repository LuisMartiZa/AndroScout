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
	
	private View activityView;
	private Actividades actividad = new Actividades();
	
	public void onCreate(View pView, Bundle savedInstanceState) {
		try {
			
			activityView = pView;
			initializeActivity(pView);
			Typeface tf = Typeface.createFromAsset(this.getAssets(),
	                "fonts/Roboto-Light.ttf");
	        //TEXTVIEW
	        TextView name = (TextView) this.findViewById(R.id.nameText);
	        TextView surname = (TextView) this.findViewById(R.id.surnameText);
	       

	        name.setTypeface(tf);
	        surname.setTypeface(tf);
	        
	        //EDITTEXT
	        EditText nameField = (EditText) this.findViewById(R.id.nameEducando);
	        EditText surnameField = (EditText) this.findViewById(R.id.surnameEducando);
	       
	        nameField.setTypeface(tf);
	        surnameField.setTypeface(tf);
	      
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initializeActivity(View pView) throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null)
			executeShowCommand(intentExtras.getInt("actividadID"));
	}
	
	public void executeShowCommand(int pIndex) {
		try {

			actividad = DataBase.Context.ActividadesSet.get(pIndex);
			actividad.setStatus(Entity.STATUS_UPDATED);
			actividad.bind(activityView);
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}

}