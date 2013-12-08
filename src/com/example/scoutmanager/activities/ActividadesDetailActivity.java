package com.example.scoutmanager.activities;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Actividades;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActividadesDetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {

	private Actividades actividad = new Actividades();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.actividades_detail_activity);
		
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras.getString("tipo").equals("danza")){
			
			YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view2);
	        youTubeView.initialize("AIzaSyDnQ2O3kA54DdkgQtPUX6RHUuOgUx_qsrM", this);
	        
		}else
		{
			YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view2);
	        youTubeView.setVisibility(View.INVISIBLE);
	        
	        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) youTubeView.getLayoutParams();
	        params.height = 0;
	        params.width = 0;
	        youTubeView.setLayoutParams(params);
	        
		}
		
		try {
			initializeActivity();
		} catch (AdaFrameworkException e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		initializeTypeface();
		
	}
	
	 @Override
    public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) {
        Toast.makeText(this, "Error inicializando YouTube View", Toast.LENGTH_LONG).show();        
    }
 
    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.loadVideo(actividad.getURLVideo());
          }    
    }

	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		
		ActionBar actionbar;
		actionbar= getActionBar();
		actionbar.setTitle(intentExtras.getString("tipo").toUpperCase());
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
		actionbar.setHomeButtonEnabled(true);
		
		executeShowCommand(intentExtras.getLong("actividadID"));

	}
	
	private void executeShowCommand(Long pIndex) {
		try {

			actividad = DataBase.Context.ActividadesSet.getElementByID(pIndex);
			actividad.setStatus(Entity.STATUS_UPDATED);
			actividad.bind(this);
			
		} catch (Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private void initializeTypeface(){
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
		Typeface tfb = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Bold.ttf");
        //TEXTVIEW
        TextView name = (TextView) this.findViewById(R.id.nameActividadText);
        TextView participantes = (TextView) this.findViewById(R.id.participantesText);
        TextView descripcion = (TextView) this.findViewById(R.id.descripText);
        TextView descripcionField = (TextView) this.findViewById(R.id.descripActividad);
        TextView nameActividad = (TextView) this.findViewById(R.id.nameActividad);
        TextView participantesActividad = (TextView) this.findViewById(R.id.participantesActividad);

        name.setTypeface(tfb);
        participantes.setTypeface(tfb);
        descripcion.setTypeface(tfb);
        descripcionField.setTypeface(tf);
        nameActividad.setTypeface(tf);
        participantesActividad.setTypeface(tf);

	}

}