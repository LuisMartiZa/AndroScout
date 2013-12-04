package com.example.scoutmanager.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.EducandosListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Tutor;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
//import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
//import android.widget.TextView;
import android.widget.Toast;

public class TutoresDetailActivity extends Activity {
	
	private ActionBar actionbar;
	private Tutor tutor = new Tutor();
	private ListView educandosListView;
    private ArrayAdapter<Educando> educandosListViewAdapter;
	private ArrayList<Educando> arrayListEducandos= new ArrayList<Educando>();
	private ArrayList<String> educandosSelected;
	private ImageButton addEducando;


	private AlertDialog.Builder builder;

	private static final int SELECT_REQUEST= 188;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
    	setContentView(R.layout.tutor_detail_activity);
    	
		educandosListView =(ListView)findViewById(R.id.hijosListView);
		addEducando= (ImageButton)findViewById(R.id.addHijosButton);
		addEducando.setOnClickListener(onClick);

    	try {
    		initializePopUp();
			initializeActivity();
			
			actionbar= this.getActionBar();
			actionbar.setTitle("TUTOR/A");
			Bundle intentExtras = this.getIntent().getExtras();
			if (intentExtras != null)
				actionbar.setSubtitle("Editar tutor");
			else
				actionbar.setSubtitle("Nuevo tutor");

		} catch (Exception e) {
			Log.v("ONVIEWCREATED", "Mensaje "+e);

		}
    	
    	//initializeTypeface();
		
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
          if (requestCode == SELECT_REQUEST && resultCode == RESULT_OK) {

        	  try {
				DataBase.Context.EducandosSet.fill();
			} catch (AdaFrameworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	  
        	  Bundle intentExtras = data.getExtras();
        	  
         	 Log.v("LISTEDUCANDOS", "ID's " + intentExtras.getIntegerArrayList("educandosID"));

         	 arrayListEducandos=  new ArrayList<Educando>();

        	  
        	  ArrayList<Integer> educandosID = intentExtras.getIntegerArrayList("educandosID");
        	  
	        	  for(int i=0; i< educandosID.size(); i++){
	        		  arrayListEducandos.add(DataBase.Context.EducandosSet.get(educandosID.get(i)));
	        	  }
	        	  try {
	        		  initializeListView();
					} catch (AdaFrameworkException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
	
	/*private void initializeTypeface(){
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
        //TEXTVIEW
        TextView name = (TextView) this.findViewById(R.id.nameText);
        TextView surname = (TextView) this.findViewById(R.id.surnameText);
        TextView dir = (TextView) this.findViewById(R.id.dirText);
        TextView birthday = (TextView) this.findViewById(R.id.birthdayText);
        TextView seccion = (TextView) this.findViewById(R.id.sectionText);
        TextView etapa = (TextView) this.findViewById(R.id.etapaText);
        TextView padres = (TextView) this.findViewById(R.id.padresText);

        name.setTypeface(tf);
        surname.setTypeface(tf);
        dir.setTypeface(tf);
        birthday.setTypeface(tf);
        seccion.setTypeface(tf);
        etapa.setTypeface(tf);
        padres.setTypeface(tf);
        
        //EDITTEXT
        EditText nameField = (EditText) this.findViewById(R.id.nametutor);
        EditText surnameField = (EditText) this.findViewById(R.id.surnametutor);
        EditText dirField = (EditText) this.findViewById(R.id.dirtutor);
        EditText birthdayField = (EditText) this.findViewById(R.id.tutorBirthday);
        
        nameField.setTypeface(tf);
        surnameField.setTypeface(tf);
        dirField.setTypeface(tf);
        birthdayField.setTypeface(tf);
	}*/
	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null){
			executeShowCommand(intentExtras.getLong("tutorID"));
		}else{
			initializeListView();
		}
	}
	
	public void executeShowCommand(Long pIndex) {
		try {

			DataBase.Context.TutoresSet.fill();
			tutor = DataBase.Context.TutoresSet.getElementByID(pIndex);
			tutor.setStatus(Entity.STATUS_UPDATED);
			tutor.bind(this);
			
			fillArrayListEducandos();
			initializeListView();
			
		} catch (Exception e) {
			Log.v("EXECUTESHOWCOMMAND", "Mensaje "+e);
		}
	}
	
 	public void executeDeleteCommand() {
		try {
			if (tutor.getID() != null) {
			
				tutor.setStatus(Entity.STATUS_DELETED);
								
				DataBase.Context.TutoresSet.save(tutor);

				setResult(Activity.RESULT_OK);
				finish();
			}
			
		} catch (Exception e) {
			Log.v("DELETECOMMAND", "Mensaje "+e);

		}
	}
	
	public void executeSaveCommand(boolean assing) {
		try {
			
			tutor.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
		
			if (tutor.validate(this)) {
				
				if (tutor.getID() == null) {
					DataBase.Context.TutoresSet.add(tutor);
				}
				DataBase.Context.TutoresSet.save(tutor);
				
				setResult(Activity.RESULT_OK);
				
				Educando educando= new Educando();
				DataBase.Context.TutoresSet.fill();
				
				if(arrayListEducandos.size()>0){
					
					for(int n=0; n< arrayListEducandos.size(); n++){
						educando= arrayListEducandos.get(n);
						
						educando.setStatus(Entity.STATUS_UPDATED);
						if(tutor.getStatus() == Entity.STATUS_UPDATED){
							educando.addTutor(DataBase.Context.TutoresSet.getElementByID(tutor.getID()));

						}else{
							educando.addTutor(DataBase.Context.TutoresSet.get(DataBase.Context.TutoresSet.size()-1));

						}
						
						DataBase.Context.EducandosSet.save(educando);
						
					}
				}else{
					DataBase.Context.EducandosSet.fill();
					if(DataBase.Context.EducandosSet.size() != 0){
						String wherePattern = "tTutor_ID = ?";
						
						List<Educando> educandosList= new ArrayList<Educando>();
						
						if(tutor.getStatus() == Entity.STATUS_UPDATED){
					        educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_TUTORES, false, null, wherePattern, new String[] { DataBase.Context.TutoresSet.getElementByID(tutor.getID()).toString() }, "tTutor_ID ASC", null, null, null, null);
	
						}else{
					        educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_TUTORES, false, null, wherePattern, new String[] { DataBase.Context.TutoresSet.get(DataBase.Context.TutoresSet.size()-1).getID().toString() }, "tTutor_ID ASC", null, null, null, null);
	
						}
	
						for(int i=0; i<educandosList.size();++i){
							Educando aux = educandosList.get(i);
							aux.setStatus(Entity.STATUS_UPDATED);
							aux.resetTutores();
							
							DataBase.Context.EducandosSet.save(aux);
	
						}
					}
				}
				if(!assing)
					finish();
				
			} else {
				Toast.makeText(this, tutor.getValidationResultString("-"), Toast.LENGTH_SHORT).show();
			}
			
		} catch (Exception e) {
			Log.v("SAVECOMMAND", "Mensaje "+e);

		}
	}
	
	private void executeShowListSelectable() {
	try {
			getSelectedEducandos();

			Bundle hijosTutor = new Bundle();    
		    hijosTutor.putStringArrayList("selectedEducandos", educandosSelected);
		    
			Bundle tutoresView = new Bundle();    
			tutoresView.putBoolean("tutoresView", true);
			
			Intent detailIntent = new Intent(this, EducandosListSelectable.class);
		    detailIntent.putExtras(hijosTutor);
		    detailIntent.putExtras(tutoresView);
		    
			startActivityForResult(detailIntent, SELECT_REQUEST);
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private View.OnClickListener onClick =  new View.OnClickListener() {
		
		public void onClick(View v) {
			if(tutor.getID() == null){
				builder.show();
			}else{
				executeShowListSelectable();

			}
		}
	};
	
	private void initializePopUp(){
		 builder = new AlertDialog.Builder(this);
	
	     builder.setMessage("Para asignar Hijos, el tutor debe estar creado ÀQuŽ desea hacer")
	     .setTitle("CREAR TUTOR")
	     .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
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
    		educandosListViewAdapter= new EducandosListAdapter(TutoresDetailActivity.this, R.layout.tutores_row, arrayListEducandos);
    		educandosListView.setAdapter(this.educandosListViewAdapter);
    	}
    }
	
	private void fillArrayListEducandos() throws AdaFrameworkException{
		DataBase.Context.EducandosSet.fill();
		arrayListEducandos= new ArrayList<Educando>();
		
		String wherePattern = "tTutor_ID = ?";
        List<Educando> educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_TUTORES, false, null, wherePattern, new String[] { tutor.getID().toString() }, "tTutor_ID ASC", null, null, null, null);

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

}