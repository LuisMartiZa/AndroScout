package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
public class Promesa extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_promesa);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("PROMESA SCOUT");
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        
        TextView promesa1 = (TextView) findViewById(R.id.promesa1);
        TextView promesa2 = (TextView) findViewById(R.id.promesa2);
        TextView promesa3 = (TextView) findViewById(R.id.promesa3);
        TextView promesa4 = (TextView) findViewById(R.id.promesa4);

        promesa1.setTypeface(tf);
        promesa2.setTypeface(tf);
        promesa3.setTypeface(tf);
        promesa4.setTypeface(tf);


	}
}
