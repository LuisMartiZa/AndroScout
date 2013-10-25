package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LeyYPromesa extends Activity {

	private ImageButton ley;
	private ImageButton promesa;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_leypromesa);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("LEY Y PROMESA");
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        
        TextView leyText = (TextView) findViewById(R.id.textLey);
        TextView promesaText = (TextView) findViewById(R.id.textPromesa);

        leyText.setTypeface(tf);
        promesaText.setTypeface(tf);



		ley = (ImageButton) findViewById(R.id.imageButtonLey);
		promesa = (ImageButton) findViewById(R.id.imageButtonPromesa);
		
		promesa.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(getBaseContext(), Promesa.class);
		        startActivity(myIntent);
			}
		});
		
		ley.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(getBaseContext(), Ley.class);
		        startActivity(myIntent);
			}
		});
	}
}
