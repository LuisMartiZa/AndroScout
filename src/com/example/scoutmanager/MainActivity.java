package com.example.scoutmanager;

import java.util.Calendar;
import java.util.Date;

import com.example.scoutmanager.model.datacontexts.ApplicationDataContext;
import com.example.scoutmanager.model.entities.Educando;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends SlidingActivity {
	
	private ApplicationDataContext dataContext;
	private SlidingMenu slidingMenu;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        slidingMenu = new SlidingMenu(this);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidth(5);
        slidingMenu.setFadeDegree(0.0f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setBehindWidth(200);
        slidingMenu.setMenu(R.menu.main);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        try {
			dataContext = new ApplicationDataContext(this);
			//populateDataBase();
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
    		
    		//dataContext.EducandosSet.add(educando);
    		//dataContext.EducandosSet.save();
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return false;
        
    }
    
}
