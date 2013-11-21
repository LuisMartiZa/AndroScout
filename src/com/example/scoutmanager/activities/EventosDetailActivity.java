package com.example.scoutmanager.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.EducandosEventosListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventosDetailActivity extends Activity {
	
	private Evento ev = new Evento();
	
	private ImageButton addEducando;
	private ArrayList<String> educandos;
	private ListView educandosListView;
	
	public EditText nameField;
    public EditText lugarField;
    public EditText fechaField;
    
	Bundle intentExtras;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.eventos_detail_activity);
		
		this.educandosListView =(ListView)findViewById(R.id.listEducandosEvent);
		
		educandos = new ArrayList<String>();
		nameField = (EditText) this.findViewById(R.id.nameEventoField);
		lugarField = (EditText) this.findViewById(R.id.lugarEventoField);
	    fechaField = (EditText) this.findViewById(R.id.fechaEventoField);
		intentExtras = this.getIntent().getExtras();
		
		try {
			initializeActivity();
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		initializeTypeface();
		
	}
	
	private void initializeActivity() throws AdaFrameworkException {
		if (!intentExtras.getBoolean("newEvent")){
			executeShowCommand(intentExtras.getInt("eventoID"));
		}else{
			if(intentExtras.getBoolean("edited")){
				populateNewEvent();
			}
		}
				
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("EVENTOS");
		
		addEducando = (ImageButton) findViewById(R.id.addEducandoEventoButton);
		addEducando.setOnClickListener(onClick);
	}
	
	private void executeShowCommand(int pIndex) {
		try {
			ev = DataBase.Context.EventosSet.get(pIndex);
			ev.setStatus(Entity.STATUS_UPDATED);
			ev.bind(this);
			
			if(intentExtras.getBoolean("edited")){
				educandos=intentExtras.getStringArrayList("educandosSelected");
			}
			else{
				
				List<Educando> educandosEvento = new ArrayList<Educando>();
				educandosEvento = ev.getEducandosEvento();

				Educando educando;
									
				for(int n=0; n< educandosEvento.size(); n++){
					educando= educandosEvento.get(n);
					
					educandos.add(educando.getNombre()+" "+educando.getApellidos());
				}
			}
			fillListView();
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void populateNewEvent(){
		
		nameField.setText(intentExtras.getString("nombre"));
        lugarField.setText(intentExtras.getString("lugar"));
        fechaField.setText(intentExtras.getString("fecha"));
        educandos = intentExtras.getStringArrayList("educandosSelected");
        
		fillListView();
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
			
			if(intentExtras.getBoolean("edited")){
				if(!intentExtras.getBoolean("newEvent"))
					ev.resetEducandosEvento();
				
				ArrayList<Integer> educandosID = intentExtras.getIntegerArrayList("educandosID");
										
				DataBase.Context.EducandosSet.fill();
	
				Educando educando;
									
				for(int n=0; n< educandosID.size(); n++){
					educando= DataBase.Context.EducandosSet.get(educandosID.get(n));
					
					ev.addEducandoEvento(educando);
				}
			}
						
			if (ev.validate(this)) {
				
				if (ev.getID() == null) {
					DataBase.Context.EventosSet.add(ev);
				}
				DataBase.Context.EventosSet.save();
				
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
	
	private void fillListView(){
		
		this.educandosListView.setAdapter(new EducandosEventosListAdapter(this, educandos));
	}
	
	private View.OnClickListener onClick =  new View.OnClickListener() {
		
		public void onClick(View v) {
			Intent intent = new Intent(EventosDetailActivity.this, EducandosListSelectable.class);
			
			// Create a bundle object
	        Bundle educandosEvento = new Bundle();
	        Bundle nameEvento = new Bundle();
	        Bundle lugarEvento = new Bundle();
	        Bundle fechaEvento = new Bundle();
	        Bundle idEvento = new Bundle();
	        Bundle newEvent = new Bundle();
	        
	        if(!intentExtras.getBoolean("newEvent"))
	        {
		        educandosEvento.putStringArrayList("selectedEducandos", educandos);
	            idEvento.putInt("eventoID", intentExtras.getInt("eventoID"));
	            newEvent.putBoolean("newEvent", false);
	            
	            //Add the bundle to the intent.
	            intent.putExtras(educandosEvento);
	            intent.putExtras(idEvento);
	            intent.putExtras(newEvent);
	            
	        }else{
	        	
	        	educandosEvento.putStringArrayList("selectedEducandos", educandos);
	        	nameEvento.putString("nombre", nameField.getText().toString());
		        lugarEvento.putString("lugar", lugarField.getText().toString());
		        fechaEvento.putString("fecha", fechaField.getText().toString());
	            newEvent.putBoolean("newEvent", true);

	            //Add the bundle to the intent.
	            intent.putExtras(educandosEvento);
	            intent.putExtras(nameEvento);
	            intent.putExtras(lugarEvento);
	            intent.putExtras(fechaEvento);
	            intent.putExtras(newEvent);

	        }
	        
	        // Add the bundle to the intent.
	        finish();
	        startActivity(intent);
		}
	};
	
	private void initializeTypeface(){
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
		Typeface tfb = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Bold.ttf");
        //TEXTVIEW
        TextView name = (TextView) this.findViewById(R.id.nameEvento);
        TextView lugar = (TextView) this.findViewById(R.id.lugarEvento);
        TextView fecha = (TextView) this.findViewById(R.id.fechaEvento);
        
        nameField = (EditText) this.findViewById(R.id.nameEventoField);
        lugarField = (EditText) this.findViewById(R.id.lugarEventoField);
        fechaField = (EditText) this.findViewById(R.id.fechaEventoField);

        name.setTypeface(tfb);
        lugar.setTypeface(tfb);
        fecha.setTypeface(tfb);
        nameField.setTypeface(tf);
        lugarField.setTypeface(tf);
        fechaField.setTypeface(tf);

	}

}