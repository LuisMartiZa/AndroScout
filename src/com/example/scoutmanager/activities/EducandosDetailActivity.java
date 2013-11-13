package com.example.scoutmanager.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Etapa;
import com.example.scoutmanager.model.entities.Seccion;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EducandosDetailActivity extends Activity {
	
	private Spinner etapas;
	private Spinner seccion;
	private ArrayAdapter<String> etapasAdapter;
	private ArrayAdapter<String> seccionesAdapter;
	private Educando educando = new Educando();
	
	private ImageView mImageView;
	private static final int CAMERA_PIC_REQUEST = 1337;  
	public final static String APP_PATH_SD_CARD = "/scoutmanager";
	private String mCurrentPhotoPath;
	private static final String CAMERA_DIR = "/dcim/";


	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
    	setContentView(R.layout.educandos_detail_activity);
    	
    	try {	
			initializeActivity();
			
			ActionBar actionbar;
			actionbar= this.getActionBar();
			actionbar.setTitle("EDUCANDO");
			
	        mImageView = (ImageView) findViewById(R.id.educandoImagenAdd);
	        mImageView.setClickable(true);
	        mImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                  
                    File f = null;
        			
        			try {
        				f = setUpPhotoFile();
        				mCurrentPhotoPath = f.getAbsolutePath();
        				Log.v("PATH", "photoPath " + mCurrentPhotoPath);
        				Log.v("PATHURI", "photoPathURI " + Uri.fromFile(f));
        				Log.v("ALBUMNAME", "Album name" + getAlbumName());
        				cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        			} catch (IOException e) {
        				e.printStackTrace();
        				f = null;
        				mCurrentPhotoPath = null;
        			}
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                }
	         });
		} catch (Exception e) {
			Log.v("ONVIEWCREATED", "Mensaje "+e);

		}
    	
    	initializeTypeface();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.educando_detail_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.accept_educando:
	            executeSaveCommand();
	            return true;
	            
	        case R.id.discard_educando:
	            executeDeleteCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	private void initializeTypeface(){
		Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "fonts/Roboto-Light.ttf");
        //TEXTVIEW
        TextView name = (TextView) this.findViewById(R.id.nameText);
        TextView surname = (TextView) this.findViewById(R.id.surnameText);
        TextView dir = (TextView) this.findViewById(R.id.dirText);
        TextView birthday = (TextView) this.findViewById(R.id.birthdayText);
        TextView seccion = (TextView) this.findViewById(R.id.sectionText);
        TextView etapa = (TextView) this.findViewById(R.id.etapaText);
        TextView padres = (TextView) this.findViewById(R.id.padresText);

        name.setTypeface(tf);
        surname.setTypeface(tf);
        dir.setTypeface(tf);
        birthday.setTypeface(tf);
        seccion.setTypeface(tf);
        etapa.setTypeface(tf);
        padres.setTypeface(tf);
        
        //EDITTEXT
        EditText nameField = (EditText) this.findViewById(R.id.nameEducando);
        EditText surnameField = (EditText) this.findViewById(R.id.surnameEducando);
        EditText dirField = (EditText) this.findViewById(R.id.dirEducando);
        EditText birthdayField = (EditText) this.findViewById(R.id.educandoBirthday);
        
        nameField.setTypeface(tf);
        surnameField.setTypeface(tf);
        dirField.setTypeface(tf);
        birthdayField.setTypeface(tf);
	}
	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null) {
			initializeSpinners();
			executeShowCommand(intentExtras.getInt("educandoID"));			    
		}else{
		
			initializeSpinners();
		}
	}
	
	public void executeShowCommand(int pIndex) {
		try {

			educando = DataBase.Context.EducandosSet.get(pIndex);
			educando.setStatus(Entity.STATUS_UPDATED);
			educando.bind(this);
			
			int seccionPosition = this.seccionesAdapter.getPosition(educando.getSeccionEducando().getNombre());
			
			//set the default according to value
			this.seccion.setSelection(seccionPosition,true);
						
			int etapaPosition = this.etapasAdapter.getPosition(educando.getEtapaEducando().getNombre());

			//set the default according to value
			this.etapas.setSelection(etapaPosition,true);
			
		} catch (Exception e) {
			Log.v("EXECUTESHOWCOMMAND", "Mensaje "+e);

		}
	}
	
 	public void executeDeleteCommand() {
		try {
			
			if (educando.getID() != null) {
			
				educando.setStatus(Entity.STATUS_DELETED);
				DataBase.Context.EducandosSet.save();
				
				setResult(Activity.RESULT_OK);
				finish();
			}
			
		} catch (Exception e) {
			Log.v("DELETECOMMAND", "Mensaje "+e);

		}
	}
	
	public void executeSaveCommand() {
		try {
			
			educando.bind(this, DataBinder.BINDING_UI_TO_ENTITY);

			int nseccion = DataBase.Context.SeccionsSet.size();
			Seccion seccionAux;
			
			for(int n=0; n< nseccion; n++){
				seccionAux= DataBase.Context.SeccionsSet.get(n);

				if(seccionAux.getNombre().equals(seccion.getSelectedItem()))
				{
					educando.setSeccionEducando(seccionAux);

				}
					
			}
			
			int netapa = DataBase.Context.EtapasSet.size();
			Etapa etapaAux;
			
			for(int n=0; n< netapa; n++){
				etapaAux= DataBase.Context.EtapasSet.get(n);
				
				if(etapaAux.getNombre().equals(etapas.getSelectedItem()))
				{
					educando.setEtapaEducando(etapaAux);
				}
				
			}
			if (educando.validate(this)) {
				
				if (educando.getID() == null) {
					DataBase.Context.EducandosSet.add(educando);
				}
				DataBase.Context.EducandosSet.save();
				
				setResult(Activity.RESULT_OK);
				finish();
				
			} else {
				Toast.makeText(this, educando.getValidationResultString("-"), Toast.LENGTH_SHORT).show();
			}
			
		} catch (Exception e) {
			Log.v("SAVECOMMAND", "Mensaje "+e);

		}
	}
	
	public void initializeSpinners()
	{
		List<String> seccionesArray =  new ArrayList<String>();
	    seccionesArray.add("Castores");
	    seccionesArray.add("Manada");
	    seccionesArray.add("Tropa");
	    seccionesArray.add("Unidad");
	    seccionesArray.add("Clan");

	    this.seccionesAdapter = new ArrayAdapter<String>(EducandosDetailActivity.this, android.R.layout.simple_spinner_item, seccionesArray){

	         public View getView(int position, View convertView, ViewGroup parent) {
	                 View v = super.getView(position, convertView, parent);

	                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
	                 ((TextView) v).setTypeface(externalFont);

	                 return v;
	         }


	         public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
	                  View v =super.getDropDownView(position, convertView, parent);

	                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
	                 ((TextView) v).setTypeface(externalFont);

	                 return v;
	         }
	 };
	    this.seccionesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    this.seccion = (Spinner) this.findViewById(R.id.spinnerSeccion);
	    this.seccion.setAdapter(seccionesAdapter);
	    this.seccion.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long id) {
				try {
					List<String> etapasArray =  new ArrayList<String>();
					
				    switch (pPosition) {
			        case 0:
			        	etapasArray.add("Castor sin paletas");
					    etapasArray.add("Castor con paletas");
					    etapasArray.add("Castor Keeo");
			            break;
			        case 1:
			        	etapasArray.add("Huella de Akela");
					    etapasArray.add("Huella de Baloo");
					    etapasArray.add("Huella de Bagheera");
			            break;
			        default:
			        	etapasArray.add("Integraci—n");
					    etapasArray.add("Participaci—n");
					    etapasArray.add("Animaci—n");
					    break;
				    }
				    
				    etapasAdapter = new ArrayAdapter<String>(EducandosDetailActivity.this, android.R.layout.simple_spinner_item, etapasArray){

				         public View getView(int position, View convertView, ViewGroup parent) {
				                 View v = super.getView(position, convertView, parent);

				                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
				                 ((TextView) v).setTypeface(externalFont);

				                 return v;
				         }


				         public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
				                  View v =super.getDropDownView(position, convertView, parent);

				                 Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
				                 ((TextView) v).setTypeface(externalFont);

				                 return v;
				         }
				 };
				    etapasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				    etapas = (Spinner) findViewById(R.id.spinnerEtapa);
				    etapas.setAdapter(etapasAdapter);
		        	
		        } catch (Exception e) {
					Log.v("ONITEMSELECTED", "Mensaje "+e);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
	}
	
	 @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 
          if (requestCode == CAMERA_PIC_REQUEST) {
        	  if (data != null){ 
	              Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	              mImageView.setImageBitmap(thumbnail);
        	  }         
          }
        
    }
	 
	 /* Photo album for this application */
		private String getAlbumName() {
			return "ScoutManager";
		}

		
		private File getAlbumDir() {
			File storageDir = null;

			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
				
				storageDir = new File (
						Environment.getExternalStorageDirectory()
						+ CAMERA_DIR
						+ getAlbumName()
				);

				if (storageDir != null) {
					if (! storageDir.mkdirs()) {
						if (! storageDir.exists()){
							Log.d("CameraSample", "failed to create directory");
							return null;
						}
					}
				}
				
			} else {
				Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
			}
			
			return storageDir;
		}
		
		private File createImageFile() throws IOException {
			// Create an image file name
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
			File albumF = getAlbumDir();
			File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
			return imageF;
		}

		private File setUpPhotoFile() throws IOException {
			
			File f = createImageFile();
			mCurrentPhotoPath = f.getAbsolutePath();
			
			return f;
		}
		
		private void galleryAddPic() {
		    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
			File f = new File(mCurrentPhotoPath);
		    Uri contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    this.sendBroadcast(mediaScanIntent);
	}
	 
}