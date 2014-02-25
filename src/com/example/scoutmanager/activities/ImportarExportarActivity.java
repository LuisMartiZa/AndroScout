package com.example.scoutmanager.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.R.drawable;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scoutmanager.R;

public class ImportarExportarActivity extends Activity{
	
	private TableRow importar;
	private TableRow exportar;
	
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
			    	
			    	importDB();

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
	 
	 //importing database
     private void importDB() {
         // TODO Auto-generated method stub

         try {
             File sd = Environment.getExternalStorageDirectory();
             File data  = Environment.getDataDirectory();

             if (sd.canWrite()) {
                 String  currentDBPath= "//data//" + "com.example.scoutmanager"
                         + "//databases//" + "database.db";
                 String backupDBPath  = "/BackupFolder/database.db";
                 File  backupDB= new File(data, currentDBPath);
                 File currentDB  = new File(sd, backupDBPath);

                 FileChannel src = new FileInputStream(currentDB).getChannel();
                 FileChannel dst = new FileOutputStream(backupDB).getChannel();
                 dst.transferFrom(src, 0, src.size());
                 src.close();
                 dst.close();
                 Toast.makeText(getBaseContext(), backupDB.toString(),
                         Toast.LENGTH_LONG).show();

             }
         } catch (Exception e) {

             Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                     .show();

         }
     }
     
     //exporting database 
     private void exportDB() {
         // TODO Auto-generated method stub

         try {
             File sd = Environment.getExternalStorageDirectory();
             File data = Environment.getDataDirectory();

             if (sd.canWrite()) {
            	 String  currentDBPath= "//data//" + "com.example.scoutmanager"
                         + "//databases//" + "database.db";
                 String backupDBPath  = "/BackupFolder/database.db";
                 File currentDB = new File(data, currentDBPath);
                 File backupDB = new File(sd, backupDBPath);

                 FileChannel src = new FileInputStream(currentDB).getChannel();
                 FileChannel dst = new FileOutputStream(backupDB).getChannel();
                 dst.transferFrom(src, 0, src.size());
                 src.close();
                 dst.close();
                 Toast.makeText(getBaseContext(), backupDB.toString(),
                         Toast.LENGTH_LONG).show();

             }
         } catch (Exception e) {

             Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                     .show();

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
