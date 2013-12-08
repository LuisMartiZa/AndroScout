package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActividadesActivity extends Activity {

	private ImageButton danzas;
	private ImageButton juegos;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_actividades);
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle("ACTIVIDADES");
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
		actionbar.setHomeButtonEnabled(true);
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        
        TextView danzaText = (TextView) findViewById(R.id.textDanzas);
        TextView juegoText = (TextView) findViewById(R.id.textJuegos);

        danzaText.setTypeface(tf);
        juegoText.setTypeface(tf);



		danzas = (ImageButton) findViewById(R.id.imageButtonDanzas);
		juegos = (ImageButton) findViewById(R.id.imageButtonJuegos);
		
		danzas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(getBaseContext(), ActividadesListActivity.class);
				myIntent.putExtra("tipo", "danza");
		        startActivity(myIntent);
			}
		});
		
		juegos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(getBaseContext(), ActividadesListActivity.class);
				myIntent.putExtra("tipo", "juego");
		        startActivity(myIntent);
			}
		});
	}
}
