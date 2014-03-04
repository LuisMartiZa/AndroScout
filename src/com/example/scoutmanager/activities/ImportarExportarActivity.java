package com.example.scoutmanager.activities;

import java.io.File;

import android.R.drawable;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.daidalos.afiledialog.FileChooserActivity;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;

public class ImportarExportarActivity extends Activity{
	
	private TableRow importar;
	private TableRow exportar;
	
	private AlertDialog.Builder popUpShare;
	
	private static final int SELECT_FILE= 7;
	
	 @Override
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.imexportar_activity);
	        
	        ActionBar actionbar;
			actionbar= getActionBar();
			actionbar.setTitle("OPCIONES BASE DE DATOS");
			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
			
			initializeTypeface();
			
			 File direct = new File(Environment.getExternalStorageDirectory() + "/BackupFolder");

             if(!direct.exists())
              {
                  direct.mkdir();

              }
			
			importar = (TableRow) findViewById(R.id.tableRow1);
			exportar = (TableRow) findViewById(R.id.tableRow2);
			
			importar.setOnClickListener(new View.OnClickListener() 
			{                   
			    @Override
			    public void onClick(View v)
			    {
			    	importar.setBackgroundResource(drawable.list_selector_background);
			    	
			    	Intent intent = new Intent(getApplicationContext(), FileChooserActivity.class);
		    	    startActivityForResult(intent, SELECT_FILE);
			    }
			});
			
			exportar.setOnClickListener(new View.OnClickListener() 
			{                   
			    @Override
			    public void onClick(View v)
			    {
			    	exportar.setBackgroundResource(drawable.list_selector_background);
			    	
			    	exportDB();
			    	
			    }
			});

	 }
	 
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
		    // Inflate the menu items for use in the action bar
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.import_export_menu, menu);
		    return super.onCreateOptionsMenu(menu);
		}
	    
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		            
		        case R.id.share:
		            executeShareCommand();
		            return true;
		            
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}
	 
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
			if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
                
				String folderPath = "";
	                
                Bundle bundle = data.getExtras();
                if(bundle != null)
                {                        
	                File file = (File) bundle.get(FileChooserActivity.OUTPUT_FILE_OBJECT);
	                folderPath = file.getParent();
	                
	                importDB(folderPath);
                      
                }
                        
			}
		}	
		
	 //importing database
     private void importDB(String pathFile) {
        
    	 if(DataBase.Context.restoreDB(pathFile)){
    		 Toast.makeText(getBaseContext(), "BASE DE DATOS RESTAURADA", Toast.LENGTH_LONG)
             .show();
    	 }else{
    		 Toast.makeText(getBaseContext(), "FALLO EN RESTAURACIÓN DE LA BASE DE DATOS", Toast.LENGTH_LONG)
             .show();
    	 }
    	 
     }
     
     //exporting database 
     private void exportDB() {
    	 
    	 if(DataBase.Context.backupDB()){
    		 Toast.makeText(getBaseContext(), "COPIA DE SEGURIDAD EXITOSA", Toast.LENGTH_LONG)
             .show();
    	 }else{
    		 Toast.makeText(getBaseContext(), "FALLO EN COPIA DE SEGURIDAD", Toast.LENGTH_LONG)
             .show();
    	 }
     }
     
     private void executeShareCommand(){
    	 
    	 File f = new File(Environment.getExternalStorageDirectory() + "/BackupFolder/BACKUP_database.db.bak");
    	 
    	 if(f.exists() && !f.isDirectory()) {
    		 
    		 Uri uri = Uri.fromFile(f);

     	    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  

     	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[BACKUP BASE DE DATOS ANDROSCOUT]");  
     	    emailIntent.setType("file/*");  
     	    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Copia de seguridad de la Base de Datos.\n\nMensaje generado por AndroScout.");
     	    emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

     	    startActivity(Intent.createChooser(emailIntent, "Elija una opción"));
        	 
    	 }else{
    		 
    		 popUpShare = new AlertDialog.Builder(this);
    	    	
    			popUpShare.setCancelable(true);
    	   	    popUpShare.setMessage("No es posible compartir la Base de Datos, ya que todavía no se ha exportado. Vuelva a intentarlo tras exportarla.")
    	   	    .setTitle("IMPOSIBLE COMPARTIR")
    	   	    .setPositiveButton("OK", new DialogInterface.OnClickListener()  {
	   	           public void onClick(DialogInterface dialog, int id) {
	   	        	   dialog.cancel();
	   	            }
    	   	     });
    	   	    
    	   	   popUpShare.show();
    		 
    	 }
     }

	 
	 private void initializeTypeface(){
			
			Typeface tf = Typeface.createFromAsset(getAssets(),
	                "fonts/Roboto-Light.ttf");
	        
	        TextView main1 = (TextView) findViewById(R.id.importarText);
	        TextView main2 = (TextView) findViewById(R.id.exportarText);

	        main1.setTypeface(tf);
	        main2.setTypeface(tf);

	    }

}
