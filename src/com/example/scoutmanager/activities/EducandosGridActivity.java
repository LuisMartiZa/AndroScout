package com.example.scoutmanager.activities;

import java.util.ArrayList;

import com.example.scoutmanager.R;
import com.example.scoutmanager.adapters.EducandosGridAdapter;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class EducandosGridActivity extends Activity {
	private GridView gridView;
	private EducandosGridAdapter customGridAdapter;
	private ArrayList<Educando> arrayListEducando;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.educandos_grid_activity);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("EDUCANDOS");

		try {
			fillArrayListEducando();
		} catch (AdaFrameworkException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		gridView = (GridView) findViewById(R.id.gridView);
		customGridAdapter = new EducandosGridAdapter(this, R.layout.grid_row, arrayListEducando);
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent deatailIntent = new Intent(EducandosGridActivity.this, EducandosDetailActivity.class);
				deatailIntent.putExtra("educandoID", position);
				startActivityForResult(deatailIntent, 1);
			}

		});

	}
	
	public void executeAddNewCommand() {
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
	    inflater.inflate(R.menu.educando_list_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.new_educando:
	            executeAddNewCommand();;
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