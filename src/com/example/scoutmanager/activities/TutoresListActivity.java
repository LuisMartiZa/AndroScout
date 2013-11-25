package com.example.scoutmanager.activities;

import java.io.IOException;
import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.activities.EducandosDetailActivity;
import com.example.scoutmanager.adapters.TutoresListAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Tutor;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TutoresListActivity extends Activity {
	
	private ListView tutoresListView;
    private ArrayAdapter<Tutor> tutoresListViewAdapter;
	private ArrayList<Tutor> arrayListTutores= new ArrayList<Tutor>();
	private AlertDialog.Builder builder;
	
	private static final int NEW_REQUEST = 188;
	private static final int SELECT_REQUEST = 189;  

	Bundle intentExtras;
	
	ArrayList<Integer> tutoresID= new ArrayList<Integer>();

    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> pParent, View pView, int pPosition, long id) {
			try {
	        	
				/*Intent deatailIntent = new Intent(TutoresListActivity.this, TutoresDetailActivity.class);
				deatailIntent.putExtra("tutorID", pPosition);
				startActivityForResult(deatailIntent, 1);*/
	        	
	        } catch (Exception e) {
				Toast.makeText(TutoresListActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
    };
    
	@Override
	public void onCreate(Bundle savedInstanceState) {   
		
		super.onCreate(savedInstanceState);
    	setContentView(R.layout.tutores_list_activity);
    	
    	builder = new AlertDialog.Builder(this);

        try {
        	Bundle intentExtras = this.getIntent().getExtras();
        	
        	if (intentExtras != null)
				Log.v("EducandoID", "ID " + intentExtras.getLong("educandoID"));
        		fillArrayListTutores(intentExtras.getLong("educandoID"));
        	
        	initializeListView();
        	initializePopUp();
			ActionBar actionbar;
			actionbar= getActionBar();
			actionbar.setTitle("TUTORES");
        	
        } catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
          if (requestCode == NEW_REQUEST) {
        	try {
				DataBase.Context.TutoresSet.fill();
	      		Tutor tutor= DataBase.Context.TutoresSet.get(DataBase.Context.TutoresSet.size()-1);
	      		
	      		arrayListTutores.add(tutor);
	      		
	      		initializeListView();
			} catch (AdaFrameworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          }
          if (requestCode == SELECT_REQUEST) {
        	  //TODO: Recuperar el intent y coger cada id y meterlo en el arraylisttutores.
        	  tutoresID = intentExtras.getIntegerArrayList("tutoresID");
        	  
          }
	 }
	
	private void initializeListView() throws AdaFrameworkException {
				
    	this.tutoresListView = (ListView) findViewById(R.id.tutoresListView);
    	
    	if (this.tutoresListView != null) {
    		this.tutoresListView.setOnItemClickListener(itemClickListener);
    		this.tutoresListViewAdapter= new TutoresListAdapter(TutoresListActivity.this, R.layout.tutores_row, arrayListTutores);
    		this.tutoresListView.setAdapter(this.tutoresListViewAdapter);
    	}
    }
    
    public void executeAddNewCommand() {
    	try {
    		
    		/*Intent detailIntent = new Intent(TutoresListActivity.this, TutoresDetailActivity.class);
    		startActivityForResult(detailIntent, 1);*/
    		
    	} catch (Exception e) {
 			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.tutor_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.newAction:
	        	builder.show();
	        	return true;
	            
	        case R.id.accept:
	            //TODO: Mandar los id's de los padres y así guardarlos en el educando.
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void fillArrayListTutores(Long educandoID) throws AdaFrameworkException{
		
		DataBase.Context.EducandosSet.fill();
		Educando educando= DataBase.Context.EducandosSet.getElementByID(educandoID);
		
		Log.v("EDUCANDO", "EducandoID" + educando.getNombre());
		
		arrayListTutores= new ArrayList<Tutor>();
		arrayListTutores= (ArrayList<Tutor>) educando.getTutorEducando();
	}
	
	private void initializePopUp()
	{
        builder.setMessage("¿Qué desea hacer?")
        .setTitle("Tutores")
        .setPositiveButton("Crear nuevo/a Tutor/a", new DialogInterface.OnClickListener()  {
               public void onClick(DialogInterface dialog, int id) {
                    Log.i("Dialogos", "Confirmacion Aceptada.");
                    Toast.makeText(TutoresListActivity.this, "CREAR NUEVO", Toast.LENGTH_SHORT).show();
                    executeAddNewCommand();
                    dialog.cancel();
                   }
               })
        .setNegativeButton("Seleccionar tutores ya creados", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Cancelada.");
                        Toast.makeText(TutoresListActivity.this, "SELECCIONAR YA CREADOS", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                   }
               });
	}

}
