package com.example.scoutmanager;

//import java.util.Calendar;
//import java.util.Date;

//import com.example.scoutmanager.model.datacontexts.ApplicationDataContext;
//import com.example.scoutmanager.model.entities.Educando;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
import android.view.Menu;

//import android.widget.Toast;

public class MainActivity extends SlidingActivity {
	
	//private ApplicationDataContext dataContext;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.activity_menu);

        
        SlidingMenu menu = getSlidingMenu();

        menu.setMode(SlidingMenu.LEFT);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindWidth(100);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        setSlidingActionBarEnabled(true);
        /*try {
			dataContext = new ApplicationDataContext(this);
			//populateDataBase();
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return false;
        
    }
    
}
