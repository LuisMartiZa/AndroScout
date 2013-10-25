package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
public class Ley extends Activity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_ley);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("LEY SCOUT");
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        TextView articulo1 = (TextView) findViewById(R.id.articulo1);
        TextView articulo2 = (TextView) findViewById(R.id.articulo2);
        TextView articulo3 = (TextView) findViewById(R.id.articulo3);
        TextView articulo4 = (TextView) findViewById(R.id.articulo4);
        TextView articulo5 = (TextView) findViewById(R.id.articulo5);
        TextView articulo6 = (TextView) findViewById(R.id.articulo6);
        TextView articulo7 = (TextView) findViewById(R.id.articulo7);
        TextView articulo8 = (TextView) findViewById(R.id.articulo8);
        TextView articulo9 = (TextView) findViewById(R.id.articulo9);
        TextView articulo10 = (TextView) findViewById(R.id.articulo10);
        
        articulo1.setTypeface(tf);
        articulo2.setTypeface(tf);
        articulo3.setTypeface(tf);
        articulo4.setTypeface(tf);
        articulo5.setTypeface(tf);
        articulo6.setTypeface(tf);
        articulo7.setTypeface(tf);
        articulo8.setTypeface(tf);
        articulo9.setTypeface(tf);
        articulo10.setTypeface(tf);
        

	}
}
