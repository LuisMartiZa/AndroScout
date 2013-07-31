package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import android.os.Bundle;
public class Promesa extends SlidingActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_promesa);
        setBehindContentView(R.layout.activity_menu);


	}
}
