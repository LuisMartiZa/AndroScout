package com.example.scoutmanager.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.EducandosListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Evento;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EventosDetailActivity extends Activity {
	
	private Evento ev = new Evento();
	private ActionBar actionbar;
	private ListView educandosListView;
    private ArrayAdapter<Educando> educandosListViewAdapter;
	private ArrayList<Educando> arrayListEducandos= new ArrayList<Educando>();
	private ArrayList<String> educandosSelected;
	private ImageButton addEducando;
	
	private AlertDialog.Builder builder;

	private static final int SELECT_REQUEST= 188;  

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.eventos_detail_activity);
		
		educandosListView =(ListView)findViewById(R.id.listEducandosEvent);
			
		addEducando= (ImageButton)findViewById(R.id.addEducandoEventoButton);
		addEducando.setOnClickListener(onClick);
		
		try {
    		initializePopUp();
			initializeActivity();
			
			actionbar= this.getActionBar();
			actionbar.setTitle("EVENTOS");
			
			Bundle intentExtras = this.getIntent().getExtras();
			if (intentExtras != null)
				actionbar.setSubtitle("Editar evento");
			else
				actionbar.setSubtitle("Nuevo evento");
			
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		initializeTypeface();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
		if (requestCode == SELECT_REQUEST && resultCode == RESULT_OK) {
			try {
				DataBase.Context.EducandosSet.fill();
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
			}
					  
			Bundle intentExtras = data.getExtras();
					  
			arrayListEducandos=  new ArrayList<Educando>();
							  
			ArrayList<Integer> educandosID = intentExtras.getIntegerArrayList("educandosID");
					  
			for(int i=0; i< educandosID.size(); i++){
				arrayListEducandos.add(DataBase.Context.EducandosSet.get(educandosID.get(i)));
			}
			
			try {
				initializeListView();
			} catch (AdaFrameworkException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null){
			executeShowCommand(intentExtras.getLong("eventoID"));
		}else{
			initializeListView();
		}
	}
	
	
	public void executeShowCommand(Long pIndex) {
		try {

			DataBase.Context.TutoresSet.fill();
			ev = DataBase.Context.EventosSet.getElementByID(pIndex);
			ev.setStatus(Entity.STATUS_UPDATED);
			ev.bind(this);
			
			fillArrayListEducandos();
			initializeListView();
			
		} catch (Exception e) {
			Log.v("EXECUTESHOWCOMMAND", "Mensaje "+e);
		}
	}
	
 	public void executeDeleteCommand() {
		try {
			if (ev.getID() != null) {
			
				ev.setStatus(Entity.STATUS_DELETED);
								
				DataBase.Context.EventosSet.save(ev);

				setResult(Activity.RESULT_OK);
				finish();
			}
			
		} catch (Exception e) {
			Log.v("DELETECOMMAND", "Mensaje "+e);

		}
	}
	
	
	public void executeSaveCommand(boolean assing) {
		try {
			
			ev.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			
			if (ev.validate(this)) {
				
				if (ev.getID() == null) {
					DataBase.Context.EventosSet.add(ev);
				}
				DataBase.Context.EventosSet.save(ev);
				
				setResult(Activity.RESULT_OK);
				
				Educando educando= new Educando();
				DataBase.Context.EventosSet.fill();
				
				if(arrayListEducandos.size()>0){
					for(int n=0; n< arrayListEducandos.size(); n++){
						educando= arrayListEducandos.get(n);
						
						educando.setStatus(Entity.STATUS_UPDATED);
						educando.addEvento(DataBase.Context.EventosSet.get(DataBase.Context.EventosSet.size()-1));
						
						DataBase.Context.EducandosSet.save(educando);
						
					}
				}else{
					DataBase.Context.EducandosSet.fill();
					
					String wherePattern = "tEvento_ID = ?";
			        List<Educando> educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_EVENTOS, false, null, wherePattern, new String[] { DataBase.Context.EventosSet.get(DataBase.Context.EventosSet.size()-1).getID().toString() }, "tEvento_ID ASC", null, null, null, null);

					for(int i=0; i<educandosList.size();++i){
						Educando aux = educandosList.get(i);
						aux.setStatus(Entity.STATUS_UPDATED);
						aux.resetEventos();
						
						DataBase.Context.EducandosSet.save(aux);

					}
				}
				
				if(!assing)
					finish();
				
			} else {
				Toast.makeText(this, ev.getValidationResultString("-"), Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Log.v("SAVECOMMAND", "Mensaje "+e);

		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.detail_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.accept:
	            executeSaveCommand(false);
	            return true;
	            
	        case R.id.discard:
	            executeDeleteCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void executeShowListSelectable() {
		try {
				getSelectedEducandos();

				Bundle educandosEvento = new Bundle();    
			    educandosEvento.putStringArrayList("selectedEducandos", educandosSelected);
				
				Intent detailIntent = new Intent(this, EducandosListSelectable.class);
			    detailIntent.putExtras(educandosEvento);
			    
				startActivityForResult(detailIntent, SELECT_REQUEST);
			} catch (Exception e) {
				Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
			}
	}
	
	private View.OnClickListener onClick =  new View.OnClickListener() {
		
		public void onClick(View v) {
			if(ev.getID() == null){
				builder.show();
			}else{
				executeShowListSelectable();

			}
		}
	};
	
	private void initializePopUp(){
		 builder = new AlertDialog.Builder(this);
	
	     builder.setMessage("Para asignar Educandos, el evento debe estar creado ÀQuŽ desea hacer?")
	     .setTitle("CREAR TUTOR")
	     .setPositiveButton("Crear", new DialogInterface.OnClickListener()  {
	            public void onClick(DialogInterface dialog, int id) {
	            	executeSaveCommand(true);
					executeShowListSelectable();
	            	dialog.cancel();
	                }
	            })
	     .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int id) {
	                     Log.i("Dialogos", "Confirmacion Cancelada.");
	                     dialog.cancel();
	                }
	            });
	}
	
	private void initializeListView() throws AdaFrameworkException {
		    	
    	if (educandosListView != null) {
    		educandosListViewAdapter= new EducandosListAdapter(EventosDetailActivity.this, R.layout.tutores_row, arrayListEducandos);
    		educandosListView.setAdapter(this.educandosListViewAdapter);
    	}
    }
		
	private void fillArrayListEducandos() throws AdaFrameworkException{
		
		DataBase.Context.EducandosSet.fill();
		arrayListEducandos= new ArrayList<Educando>();
		
		String wherePattern = "tEvento_ID = ?";
        List<Educando> educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_EVENTOS, false, null, wherePattern, new String[] { ev.getID().toString() }, "tEvento_ID ASC", null, null, null, null);

		for(int i=0; i<educandosList.size();++i){
			Educando educando = educandosList.get(i);
			arrayListEducandos.add(educando);
		}
	}
	
	private void getSelectedEducandos(){
		Educando educando;
		
		educandosSelected= new ArrayList<String>();
		
		for(int n=0; n< arrayListEducandos.size(); n++){
			educando= arrayListEducandos.get(n);
			
			educandosSelected.add(educando.getNombre()+" "+educando.getApellidos());
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