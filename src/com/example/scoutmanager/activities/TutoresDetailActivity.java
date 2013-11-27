package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Tutor;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TutoresDetailActivity extends Activity {
	
	private ActionBar actionbar;
	private Tutor tutor = new Tutor();
	private ListView educandosListView;

	private static final int SELECT_REQUEST= 188;  

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
    	setContentView(R.layout.tutor_detail_activity);
    	
		educandosListView =(ListView)findViewById(R.id.listEducandosEvent);
		educandosListView.setOnClickListener(onClick);

    	try {
    		
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
        	  /*//TODO: Recuperar el intent y coger cada id y meterlo en el arraylisttutores.
        	  tutoresID = intentExtras.getIntegerArrayList("tutoresID");
        	  
        	  for(int i=0; i< tutoresID.size(); ++i){
        		  
        	  }*/
        	  
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
	            executeSaveCommand();
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
		if (intentExtras != null)
			executeShowCommand(intentExtras.getLong("tutorID"));
		
		
			
	}
	
	public void executeShowCommand(Long pIndex) {
		try {

			DataBase.Context.TutoresSet.fill();
			tutor = DataBase.Context.TutoresSet.getElementByID(pIndex);
			tutor.setStatus(Entity.STATUS_UPDATED);
			tutor.bind(this);
			
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
	
	public void executeSaveCommand() {
		try {
			
			tutor.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
		
			if (tutor.validate(this)) {
				
				if (tutor.getID() == null) {
					DataBase.Context.TutoresSet.add(tutor);
				}
				DataBase.Context.TutoresSet.save();
				
				setResult(Activity.RESULT_OK);
				
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
			Intent detailIntent = new Intent(this, EducandosListSelectable.class);
			startActivityForResult(detailIntent, SELECT_REQUEST);
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
private View.OnClickListener onClick =  new View.OnClickListener() {
		
		public void onClick(View v) {
			executeShowListSelectable();
		}
	};
}