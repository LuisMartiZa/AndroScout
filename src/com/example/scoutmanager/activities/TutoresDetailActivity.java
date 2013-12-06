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
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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

public class TutoresDetailActivity extends Activity {
	
	private ActionBar actionbar;
	private Tutor tutor = new Tutor();
	private ListView educandosListView;
    private ArrayAdapter<Educando> educandosListViewAdapter;
	private ArrayList<Educando> arrayListEducandos= new ArrayList<Educando>();
	private ArrayList<Educando> arrayListEducandosOlder= new ArrayList<Educando>();
	private ArrayList<String> educandosSelected;
	private ImageButton addEducando;
	private String modo= "";

	private AlertDialog.Builder popUpGuardar;

	private static final int SELECT_REQUEST= 188;  
	
	private View.OnClickListener onClick =  new View.OnClickListener() {
		
		public void onClick(View v) {
			executeShowListSelectable();

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
    	setContentView(R.layout.tutor_detail_activity);

    	try {
    		Bundle intentExtras = this.getIntent().getExtras();
    		
			actionbar= this.getActionBar();
			actionbar.setTitle("TUTOR/A");
			
			if (intentExtras != null)
				actionbar.setSubtitle("Editar tutor");
			else
				actionbar.setSubtitle("Nuevo tutor");
			
			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
			actionbar.setHomeButtonEnabled(true);

			modo=intentExtras.getString("modo");
			
			educandosListView =(ListView)findViewById(R.id.hijosListView);
			addEducando= (ImageButton)findViewById(R.id.addHijosButton);
			addEducando.setOnClickListener(onClick);
			
			initializeActivity();
			
	    	initializeTypeface();

		} catch (Exception e) {
			Log.v("ONVIEWCREATED", "Mensaje "+e);

		}	
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
          if (requestCode == SELECT_REQUEST && resultCode == RESULT_OK) {

        	  Log.v("MODO",	 modo);
        	  try {
				DataBase.Context.EducandosSet.fill();
			} catch (AdaFrameworkException e) {
				// TODO Auto-generated catch block
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
	        	saveDialog();
	            return true;
	            
	        case R.id.discard:
	            executeDeleteCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void saveDialog(){
		popUpGuardar = new AlertDialog.Builder(this);
    	
   	    popUpGuardar.setMessage("Para crear el tutor/a, debe tener al menos un hijo asignado")
   	    .setTitle("CREAR TUTOR")
   	    .setPositiveButton("OK", new DialogInterface.OnClickListener()  {
   	           public void onClick(DialogInterface dialog, int id) {
   	            	dialog.cancel();
   	               }
   	           });
   	    if(arrayListEducandos.size() == 0){
   	    	popUpGuardar.show();
   	    }else{
   	        executeSaveCommand(false);

   	    }
	}
	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras.getString("modo").equals("editar")){
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
				
				String wherePattern = "tTutor_ID = ?";
				
				List<Educando> educandosList= new ArrayList<Educando>();
				
			    educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_TUTORES, false, null, wherePattern, new String[] { tutor.getID().toString() }, "tTutor_ID ASC", null, null, null, null);
			    
			    for(int i=0; i<educandosList.size();i++)
			    {	for(int j=0; j<educandosList.get(i).getTutores().size();j++){
			    		if(educandosList.get(i).getTutores().get(j).getID() == tutor.getID()){
			    			Educando educando = educandosList.get(i);
			    			educando.getTutores().remove(j);
			    			educando.setStatus(Entity.STATUS_UPDATED);
			    			
			    			DataBase.Context.EducandosSet.save(educando);
			    		}

			    	}
			    }
								
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
						Log.v("STATUS", "Status " + tutor.getStatus());

						if(modo.equals("editar")){

							for(int i=0;i<arrayListEducandosOlder.size();i++){
								Educando older= arrayListEducandosOlder.get(i);
								if(older.getID() != educando.getID()){
									older.setStatus(Entity.STATUS_UPDATED);
									for(int j=0; j<older.getTutores().size(); j++){
										if(older.getTutores().get(j).getID() == tutor.getID())
											older.getTutores().remove(j);
									}

									DataBase.Context.EducandosSet.save(older);
								}
							}
							educando.addTutor(DataBase.Context.TutoresSet.getElementByID(tutor.getID()));
							
						}else{
							Log.v("NUMBER1", "Number of older " + arrayListEducandosOlder.size());
							Log.v("STATUS", "Status " + tutor.getStatus());
							educando.addTutor(DataBase.Context.TutoresSet.get(DataBase.Context.TutoresSet.size()-1));

						}
						
						DataBase.Context.EducandosSet.save(educando);
						
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
	
	private void initializeListView() throws AdaFrameworkException {
		    	
    	if (educandosListView != null) {
    		educandosListViewAdapter= new EducandosListAdapter(TutoresDetailActivity.this, R.layout.tutores_row, arrayListEducandos);
    		educandosListView.setAdapter(this.educandosListViewAdapter);
    	}
    }
	
	private void fillArrayListEducandos() throws AdaFrameworkException{
		DataBase.Context.EducandosSet.fill();
		arrayListEducandos= new ArrayList<Educando>();
		arrayListEducandosOlder= new ArrayList<Educando>();
		
		String wherePattern = "tTutor_ID = ?";
        List<Educando> educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_TUTORES, false, null, wherePattern, new String[] { tutor.getID().toString() }, "tTutor_ID ASC", null, null, null, null);

		for(int i=0; i<educandosList.size();++i){
			Educando educando = educandosList.get(i);
			arrayListEducandos.add(educando);
		}
		
		arrayListEducandosOlder=arrayListEducandos;
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
        //TEXTVIEW
        TextView nombre = (TextView) this.findViewById(R.id.nombreTutorText);
        TextView apellidos = (TextView) this.findViewById(R.id.apellidosTutorText);
        TextView movil = (TextView) this.findViewById(R.id.movilTutorText);
        TextView email = (TextView) this.findViewById(R.id.emailTutorText);
        TextView tipo = (TextView) this.findViewById(R.id.tipoTutorText);
        TextView educandosTutor = (TextView) this.findViewById(R.id.educandosTutorText);

        nombre.setTypeface(tf);
        nombre.setTextColor((Color.parseColor("#5d2f89")));
        nombre.setTextSize(20);
        
        apellidos.setTypeface(tf);
        apellidos.setTextColor((Color.parseColor("#5d2f89")));
        apellidos.setTextSize(20);
        
        movil.setTypeface(tf);
        movil.setTextColor((Color.parseColor("#5d2f89")));
        movil.setTextSize(20);
        
        email.setTypeface(tf);
        email.setTextColor((Color.parseColor("#5d2f89")));
        email.setTextSize(20);
        
        tipo.setTypeface(tf);
        tipo.setTextColor((Color.parseColor("#5d2f89")));
        tipo.setTextSize(20);
        
        educandosTutor.setTypeface(tf);
        educandosTutor.setTextColor((Color.parseColor("#5d2f89")));
        educandosTutor.setTextSize(20);
        
        //EDITTEXT
        EditText nombreField = (EditText) this.findViewById(R.id.nombreTutor);
        EditText apellidosField = (EditText) this.findViewById(R.id.apellidosTutor);
        EditText movilField = (EditText) this.findViewById(R.id.telefonoTutor);
        EditText emailField = (EditText) this.findViewById(R.id.emailTutor);
        EditText tipoField = (EditText) this.findViewById(R.id.tipoTutor);
        
        nombreField.setTypeface(tf);
        apellidosField.setTypeface(tf);
        movilField.setTypeface(tf);
        emailField.setTypeface(tf);
        tipoField.setTypeface(tf);

	}

}