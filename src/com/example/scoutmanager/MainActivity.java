package com.example.scoutmanager;

import java.util.Calendar;
import java.util.Date;

import com.example.scoutmanager.model.datacontexts.ApplicationDataContext;
import com.example.scoutmanager.model.entities.Educando;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ApplicationDataContext dataContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        try {
			dataContext = new ApplicationDataContext(this);
			populateDataBase();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
    }
    
    private void populateDataBase() throws Exception{
    	if(dataContext != null){
    		Educando educando = new Educando();
    		educando.setNombre("LuisMartiza");
    		educando.setApellidos("JellyBean");
    		
    		Calendar cal = Calendar.getInstance();
    	    cal.set(Calendar.YEAR, 1988);
    	    cal.set(Calendar.MONTH, 8);
    	    cal.set(Calendar.DAY_OF_MONTH, 2);
    	    Date dateRepresentation = cal.getTime();
    		educando.setFechaNacimiento(dateRepresentation);
    		
    		dataContext.EducandosSet.add(educando);
    		dataContext.EducandosSet.save();
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return false;
        
    }
    
}
