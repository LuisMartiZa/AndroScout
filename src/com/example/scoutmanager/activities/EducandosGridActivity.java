package com.example.scoutmanager.activities;

import java.util.ArrayList;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.EducandosGridAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Tutor;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class EducandosGridActivity extends Activity {
	private GridView gridView;
	private EducandosGridAdapter educandosGridAdapter;
	private ArrayList<Educando> arrayListEducando;
	private AlertDialog.Builder builder;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.educandos_grid_activity);
		
    	builder = new AlertDialog.Builder(this);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("EDUCANDOS");
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));

		try {
			fillArrayListEducando();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		gridView = (GridView) findViewById(R.id.gridView);
		educandosGridAdapter = new EducandosGridAdapter(this, R.layout.grid_row, arrayListEducando);
		gridView.setAdapter(educandosGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent deatailIntent = new Intent(EducandosGridActivity.this, EducandosDetailActivity.class);
				deatailIntent.putExtra("educandoID", position);
				startActivityForResult(deatailIntent, 1);
			}

		});
		
		gridView.setOnItemLongClickListener(new OnItemLongClickListener() { 
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					initializePopUp(position);
					builder.show();
				} catch (AdaFrameworkException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return true;
			}
	    });

	}
	
	private void executeAddNewCommand() {
    	try {
    		
    		Intent detailIntent = new Intent(EducandosGridActivity.this, EducandosDetailActivity.class);
    		startActivityForResult(detailIntent, 1);
    		
    	} catch (Exception e) {
 			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
 		}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.list_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.newAction:
	            executeAddNewCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void fillArrayListEducando() throws AdaFrameworkException{
		DataBase.Context.EducandosSet.fill();
		int nEducandos= DataBase.Context.EducandosSet.size();
		
		arrayListEducando= new ArrayList<Educando>();
		for(int i=0; i<nEducandos;++i){
			Educando aux= DataBase.Context.EducandosSet.get(i);
			
			arrayListEducando.add(aux);
		}
	}
	
	private void executeDeleteCommand(int position) {
		try {
			DataBase.Context.EducandosSet.fill();
			Educando educando = DataBase.Context.EducandosSet.get(position);
			
			educando.setStatus(Entity.STATUS_DELETED);
			String wherePattern = "tTutor_ID = ?";

			for(int i=0; i<educando.getTutores().size(); i++)
			{
		        List<Educando> educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_TUTORES, false, null, wherePattern, new String[] { educando.getTutores().get(i).getID().toString() }, "tTutor_ID ASC", null, null, null, null);
		        
		        if(educandosList.size() == 1)
		        {
		        	Tutor tutor = educando.getTutores().get(i);
		        	tutor.setStatus(Entity.STATUS_DELETED);
		        	
		        	DataBase.Context.TutoresSet.save(tutor);
		        }
			}
			
			DataBase.Context.EducandosSet.save();
			
			Intent refresh = new Intent(this, EducandosGridActivity.class);
	        startActivity(refresh);
	        this.finish();
		        
		} catch (Exception e) {
			Log.v("DELETECOMMAND", "Mensaje "+e);

		}
	}
	
	private void initializePopUp(final int position) throws AdaFrameworkException
	{
		DataBase.Context.EducandosSet.fill();
		 
        builder.setMessage("Seguro que desea eliminar al educando " + DataBase.Context.EducandosSet.get(position).getNombre()+ "?")
        .setTitle("Educandos")
        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
               public void onClick(DialogInterface dialog, int id) {
                    Log.i("Dialogos", "Confirmacion Aceptada.");
                    executeDeleteCommand(position);
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
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if(resultCode==RESULT_OK){
        Intent refresh = new Intent(this, EducandosGridActivity.class);
        startActivity(refresh);
        this.finish();
     }
    }

}