package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Evento;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EventosDetailActivity extends Activity {
	
	private Evento ev = new Evento();
	
	private ImageButton addEducando;
	
	private String[] EDUCANDOS = null;

	
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
		
		addEducando = (ImageButton) findViewById(R.id.addEducandoEventoButton);
		
		addEducando.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EventosDetailActivity.this, EducandosListSelectable.class);
				
				// Create a bundle object
		        Bundle b = new Bundle();
		        b.putStringArray("selectedEducandos", EDUCANDOS);
		 
		        // Add the bundle to the intent.
		        //intent.putExtras(b);
		        finish();
		        startActivity(intent);
			}
		});
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.evento_detail_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.accept_evento:
	            executeSaveCommand();
	            return true;
	            
	        case R.id.discard_evento:
	            executeDeleteCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void initializeTypeface(){
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
		Typeface tfb = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Bold.ttf");
        //TEXTVIEW
        TextView name = (TextView) this.findViewById(R.id.nameEvento);
        TextView lugar = (TextView) this.findViewById(R.id.lugarEvento);
        TextView fecha = (TextView) this.findViewById(R.id.fechaEvento);
        
        EditText nameField = (EditText) this.findViewById(R.id.nameEventoField);
        EditText lugarField = (EditText) this.findViewById(R.id.lugarEventoField);
        EditText fechaField = (EditText) this.findViewById(R.id.fechaEventoField);

        name.setTypeface(tfb);
        lugar.setTypeface(tfb);
        fecha.setTypeface(tfb);
        nameField.setTypeface(tf);
        lugarField.setTypeface(tf);
        fechaField.setTypeface(tf);

	}

}