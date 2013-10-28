package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Evento;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EventosDetailActivity extends Activity {
	
	//private Actividades actividad = new Actividades();
	private Evento ev = new Evento();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.eventos_detail_activity);
		
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
			executeShowCommand(intentExtras.getInt("eventoID"));
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("EVENTOS");
	}
	
	private void executeShowCommand(int pIndex) {
		try {

			ev = DataBase.Context.EventosSet.get(pIndex);
			ev.setStatus(Entity.STATUS_UPDATED);
			ev.bind(this);
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
 	public void executeDeleteCommand() {
		try {
			
			if (ev.getID() != null) {
			
				ev.setStatus(Entity.STATUS_DELETED);
				DataBase.Context.EventosSet.save();
				
				this.setResult(Activity.RESULT_OK);
				this.finish();
			}
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void executeSaveCommand() {
		try {
			
			ev.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			
			/*int netapa = DataBase.Context.EtapasSet.size();
			Etapa etapaAux;
			
			for(int n=0; n< netapa; n++){
				etapaAux= DataBase.Context.EtapasSet.get(n);
				
				if(etapaAux.getNombre() == etapas.getSelectedItem())
				{
					educando.setEtapaEducando(etapaAux);
					break;

				}
				
			}*/
			
			if (ev.validate(this)) {
				
				if (ev.getID() == null) {
					DataBase.Context.EventosSet.add(ev);
				}
				DataBase.Context.EventosSet.save();
				
				this.setResult(Activity.RESULT_OK);
				this.finish();
				
			} else {
				Toast.makeText(this, ev.getValidationResultString("-"), Toast.LENGTH_SHORT).show();
			}
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initializeTypeface(){
		/*Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
		Typeface tfb = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Bold.ttf");
        //TEXTVIEW
        TextView name = (TextView) this.findViewById(R.id.nameActividadText);
        TextView participantes = (TextView) this.findViewById(R.id.participantesText);
        TextView descripcion = (TextView) this.findViewById(R.id.descripText);
        TextView descripcionField = (TextView) this.findViewById(R.id.descripActividad);
        TextView nameActividad = (TextView) this.findViewById(R.id.nameActividad);
        TextView participantesActividad = (TextView) this.findViewById(R.id.participantesActividad);

        name.setTypeface(tfb);
        participantes.setTypeface(tfb);
        descripcion.setTypeface(tfb);
        descripcionField.setTypeface(tf);
        nameActividad.setTypeface(tf);
        participantesActividad.setTypeface(tf);*/

	}

}