package com.example.scoutmanager.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.scoutmanager.R;
import com.example.scoutmanager.model.DataBase;
import com.example.scoutmanager.model.entities.Asistencia;
import com.example.scoutmanager.model.entities.Educando;
import com.example.scoutmanager.model.entities.Etapa;
import com.example.scoutmanager.model.entities.Seccion;
import com.example.scoutmanager.model.entities.Tutor;
import com.mobandme.ada.DataBinder;
import com.mobandme.ada.Entity;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
	private List<String> etapasArray =  new ArrayList<String>();
	private List<String> seccionesArray =  new ArrayList<String>();
	private Educando educando = new Educando();
	
	private ImageView mImageView;
	private ImageView AddImage;
	private static final int CAMERA_PIC_REQUEST = 1337;
	private static final int GALLERY_PIC_REQUEST = 1338;  
	private String mCurrentPhotoPath = "";
	private static final String CAMERA_DIR = "/dcim/";
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	
	private AlertDialog.Builder popUpImagen;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
    	setContentView(R.layout.educandos_detail_activity);
    	    	
    	try {
    		initializeSecciones();
			initializeEtapas(0);
			initializeActivity();
			
			ActionBar actionbar;
			actionbar= this.getActionBar();
			actionbar.setTitle("EDUCANDO");
			
			Bundle intentExtras = this.getIntent().getExtras();
			if (intentExtras != null)
				actionbar.setSubtitle("Editar educando");
			else
				actionbar.setSubtitle("Nuevo educando");
			

			actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5d2f89")));
			actionbar.setHomeButtonEnabled(true);
			
			mImageView= (ImageView) findViewById(R.id.educandoImagenAdd);
			mImageView.setClickable(true);
	        mImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	
                	//Options for the dialogue menu
                    final CharSequence[] items = {"Camara", "Galer’a"};

                    popUpImagen = new AlertDialog.Builder(EducandosDetailActivity.this);
                    
                    popUpImagen.setTitle("Escoge una opci—n");
                    popUpImagen.setItems(items, new DialogInterface.OnClickListener() {
                        
                        public void onClick(DialogInterface dialog, int item) {

                            // Camera option
                            if (item == 0){

                            	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                                
                            }else{
                                
                            	Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            	photoPickerIntent.setType("image/*");
                            	startActivityForResult(photoPickerIntent, GALLERY_PIC_REQUEST);
                            	
                            } 
                        }
                        
                    });
                    
                    popUpImagen.show();
                }
	         });

			
		} catch (Exception e) {
			Log.v("ONVIEWCREATED", "Mensaje "+e);

		}
    	
    	initializeTypeface();
		
	}
	
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {	 
          if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
        	  if (data.getAction() != null){ 
        		  Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
	              mImageView.setImageBitmap(thumbnail);
	              try {
					saveImage(thumbnail);
		            galleryAddPic();

	              } catch (IOException e) {
					e.printStackTrace();
	              }
	              AddImage= (ImageView) findViewById(R.id.imageEducando);
				  AddImage.setVisibility(View.INVISIBLE);
        	  }         
          }
          
          if (requestCode == GALLERY_PIC_REQUEST && resultCode == RESULT_OK) {
        	  
        	  Uri selectedImage = data.getData();
              String[] filePathColumn = {MediaStore.Images.Media.DATA};

              Cursor cursor = getContentResolver().query(
                                 selectedImage, filePathColumn, null, null, null);
              cursor.moveToFirst();

              int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
              String filePath = cursor.getString(columnIndex);
              cursor.close();


              Bitmap imageSelected = null;
			try {
				imageSelected = decodeUri(selectedImage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	  
              mImageView.setImageBitmap(imageSelected);
              try {
				saveImage(imageSelected);
	            galleryAddPic();

              } catch (IOException e) {
				e.printStackTrace();
              }
              AddImage= (ImageView) findViewById(R.id.imageEducando);
			  AddImage.setVisibility(View.INVISIBLE);       
          }
	 }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.detail_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.accept:
	            executeSaveCommand(false);
	            return true;
	            
	        case R.id.discard:
	            executeDeleteCommand();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void initializeActivity() throws AdaFrameworkException {
		Bundle intentExtras = this.getIntent().getExtras();
		if (intentExtras != null){
			executeShowCommand(intentExtras.getInt("educandoID"));
		}else{
			seccion.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long id) {
					initializeEtapas(pPosition);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
		    });
			
		}
			
	}
	
	private void executeShowCommand(int pIndex) {
		try {

			DataBase.Context.EducandosSet.fill();
			educando = DataBase.Context.EducandosSet.get(pIndex);
			educando.setStatus(Entity.STATUS_UPDATED);
			educando.bind(this);
			
			int seccionPosition = seccionesAdapter.getPosition(educando.getSeccionEducando().getNombre());
			
			//set the default according to value
			seccion.setSelection(seccionPosition,false);
			
			initializeEtapas(seccionPosition);
									
			if(!educando.getImagen().equals("")){
				mImageView= (ImageView) findViewById(R.id.educandoImagenAdd);
		        mImageView.setClickable(true);
		        mImageView.setOnClickListener(new OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                	
	                	//Options for the dialogue menu
	                    final CharSequence[] items = {"Camara", "Galer’a"};

	                    popUpImagen = new AlertDialog.Builder(EducandosDetailActivity.this);
	                    
	                    popUpImagen.setTitle("Escoge una opci—n");
	                    popUpImagen.setItems(items, new DialogInterface.OnClickListener() {
	                        
	                        public void onClick(DialogInterface dialog, int item) {

	                            // Camera option
	                            if (item == 0){

	                            	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	                                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
	                                
	                            }else{
	                                
	                            	Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
	                            	photoPickerIntent.setType("image/*");
	                            	startActivityForResult(photoPickerIntent, GALLERY_PIC_REQUEST);
	                            	
	                            } 
	                        }
	                    });
	                	
	                    popUpImagen.show();
	                }
		         });
				
				File image = new  File(educando.getImagen()); 
				
			    if(image.exists()){
					Bitmap bmp = BitmapFactory.decodeFile(image.getAbsolutePath());
					mImageView.setImageBitmap(bmp);
			    }
			    
			    AddImage= (ImageView) findViewById(R.id.imageEducando);
			    AddImage.setVisibility(View.INVISIBLE);
			    
			}else{
				mImageView= (ImageView) findViewById(R.id.imageEducando);
				mImageView.setClickable(true);
		        mImageView.setOnClickListener(new OnClickListener() {
	                @Override
	                public void onClick(View v) {
	                	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
	                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

	                }
		         });
			}
			
			int etapaPosition = etapasAdapter.getPosition(educando.getEtapaEducando().getNombre());
			
			//set the default according to value
			etapas.setSelection(etapaPosition,true);
			
			seccion.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> pParent, View pView, int pPosition, long id) {
					initializeEtapas(pPosition);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
		    });
			
		} catch (Exception e) {
			Log.v("EXECUTESHOWCOMMAND", "Mensaje "+e);
		}
	}
	
 	private void executeDeleteCommand() {
		try {
			
			if (educando.getID() != null) {
			
				educando.setStatus(Entity.STATUS_DELETED);
				String wherePattern = "tTutor_ID = ?";

				for(int i=0; i<educando.getTutores().size(); i++)
				{
			        List<Educando> educandosList = DataBase.Context.EducandosSet.search(Educando.TABLE_EDUCANDOS_JOIN_TUTORES, false, null, wherePattern, new String[] { educando.getTutores().get(i).getID().toString() }, "tTutor_ID ASC", null, null, null, null);
			        
			        if(educandosList.size() == 1)
			        {
			        	Tutor tutor = educando.getTutores().get(i);
			        	tutor.setStatus(Entity.STATUS_DELETED);
			        	
			        	DataBase.Context.TutoresSet.save(tutor);
			        }
				}
				
				DataBase.Context.EducandosSet.save();
				
				setResult(Activity.RESULT_OK);
				finish();
			}
			
		} catch (Exception e) {
			Log.v("DELETECOMMAND", "Mensaje "+e);

		}
	}
	
	private void executeSaveCommand(boolean parents) {
		try {
			
			educando.bind(this, DataBinder.BINDING_UI_TO_ENTITY);
			
			DataBase.Context.SeccionsSet.fill();
			int nseccion = DataBase.Context.SeccionsSet.size();
			Seccion seccionAux;
			
			for(int n=0; n< nseccion; n++){
				seccionAux= DataBase.Context.SeccionsSet.get(n);				

				if(seccionAux.getNombre().equals(seccion.getSelectedItem()))
				{
					educando.setSeccionEducando(seccionAux);

				}
					
			}
			DataBase.Context.EtapasSet.fill();
			int netapa = DataBase.Context.EtapasSet.size();
			Etapa etapaAux;

			for(int n=0; n< netapa; n++){
				etapaAux= DataBase.Context.EtapasSet.get(n);
				
				if(etapaAux.getNombre().equals(etapas.getSelectedItem()))
				{
					educando.setEtapaEducando(etapaAux);
				}
			}
			if(!mCurrentPhotoPath.contentEquals(""))
				educando.setImagen(mCurrentPhotoPath);
									
			if (educando.validate(this)) {
				
				if (educando.getID() == null) {
					Asistencia asistencia = new Asistencia("Ronda 2013/2014", 0);
					DataBase.Context.AsistenciaSet.add(asistencia);
					DataBase.Context.AsistenciaSet.save();
					
					educando.setAsistencia(asistencia);
					
					DataBase.Context.EducandosSet.add(educando);
				}
				DataBase.Context.EducandosSet.save();
				
				setResult(Activity.RESULT_OK);
				if(!parents)
					finish();
				
			} else {
				Toast.makeText(this, educando.getValidationResultString("-"), Toast.LENGTH_SHORT).show();
			}
			
		} catch (Exception e) {
			Log.v("SAVECOMMAND", "Mensaje "+e);

		}
	}
	
	private void initializeSecciones()
	{
		seccionesArray.add("Castores");
		seccionesArray.add("Manada");
		seccionesArray.add("Tropa");
		seccionesArray.add("Unidad");
		seccionesArray.add("Clan");
	    
	    seccionesAdapter = new ArrayAdapter<String>(EducandosDetailActivity.this, android.R.layout.simple_spinner_item, seccionesArray){

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
	    
	    seccionesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    seccion = (Spinner) findViewById(R.id.spinnerSeccion);
	    seccion.setAdapter(seccionesAdapter);
	}
	
	private void initializeEtapas(int pPosition)
	{
		etapasArray.clear();
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
							Log.d("ScoutManager", "failed to create directory");
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
			 mCurrentPhotoPath= imageF.getAbsolutePath();
			return imageF;
		}

		
		private void galleryAddPic() {
		    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
			File f = new File(mCurrentPhotoPath);
		    Uri contentUri = Uri.fromFile(f);
		    mediaScanIntent.setData(contentUri);
		    this.sendBroadcast(mediaScanIntent);
		}
		
		private void saveImage(Bitmap finalBitmap) throws IOException {

		    File file =createImageFile();
		    mCurrentPhotoPath= file.getAbsolutePath();
		    if (file.exists ()) file.delete (); 
		    try {
		           FileOutputStream out = new FileOutputStream(file);
		           finalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
		           out.flush();
		           out.close();

		    } catch (Exception e) {
		           e.printStackTrace();

		    }
		}
		
		private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

	        // Decode image size
	        BitmapFactory.Options o = new BitmapFactory.Options();
	        o.inJustDecodeBounds = true;
	        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

	        // The new size we want to scale to
	        final int REQUIRED_SIZE = 140;

	        // Find the correct scale value. It should be the power of 2.
	        int width_tmp = o.outWidth, height_tmp = o.outHeight;
	        int scale = 1;
	        while (true) {
	            if (width_tmp / 2 < REQUIRED_SIZE
	               || height_tmp / 2 < REQUIRED_SIZE) {
	                break;
	            }
	            width_tmp /= 2;
	            height_tmp /= 2;
	            scale *= 2;
	        }

	        // Decode with inSampleSize
	        BitmapFactory.Options o2 = new BitmapFactory.Options();
	        o2.inSampleSize = scale;
	        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

	    }
		
		private void initializeTypeface(){
			Typeface tf = Typeface.createFromAsset(this.getAssets(),
	                "fonts/Roboto-Light.ttf");
	        //TEXTVIEW
	        TextView name = (TextView) this.findViewById(R.id.nameText);
	        TextView surname = (TextView) this.findViewById(R.id.apellidosText);
	        TextView dir = (TextView) this.findViewById(R.id.dirText);
	        TextView birthday = (TextView) this.findViewById(R.id.birthdayText);
	        TextView email = (TextView) this.findViewById(R.id.emailText);
	        TextView telefono = (TextView) this.findViewById(R.id.movilText);
	        TextView seccion = (TextView) this.findViewById(R.id.seccionText);
	        TextView etapa = (TextView) this.findViewById(R.id.etapaText);

	        name.setTypeface(tf);
	        name.setTextColor((Color.parseColor("#5d2f89")));
	        name.setTextSize(20);
	        
	        surname.setTypeface(tf);
	        surname.setTextColor((Color.parseColor("#5d2f89")));
	        surname.setTextSize(20);
	        
	        dir.setTypeface(tf);
	        dir.setTextColor((Color.parseColor("#5d2f89")));
	        dir.setTextSize(20);
	        
	        birthday.setTypeface(tf);
	        birthday.setTextColor((Color.parseColor("#5d2f89")));
	        birthday.setTextSize(20);
	        
	        email.setTypeface(tf);
	        email.setTextColor((Color.parseColor("#5d2f89")));
	        email.setTextSize(20);
	        
	        telefono.setTypeface(tf);
	        telefono.setTextColor((Color.parseColor("#5d2f89")));
	        telefono.setTextSize(20);
	        
	        seccion.setTypeface(tf);
	        seccion.setTextColor((Color.parseColor("#5d2f89")));
	        seccion.setTextSize(20);
	        
	        etapa.setTypeface(tf);
	        etapa.setTextColor((Color.parseColor("#5d2f89")));
	        etapa.setTextSize(20);
	        
	        //EDITTEXT
	        EditText nameField = (EditText) this.findViewById(R.id.nameEducando);
	        EditText surnameField = (EditText) this.findViewById(R.id.surnameEducando);
	        EditText dirField = (EditText) this.findViewById(R.id.dirEducando);
	        EditText birthdayField = (EditText) this.findViewById(R.id.educandoBirthday);
	        EditText emailField = (EditText) this.findViewById(R.id.educandoEmail);
	        EditText telefonoField = (EditText) this.findViewById(R.id.telefonoEducando);

	        
	        nameField.setTypeface(tf);
	        surnameField.setTypeface(tf);
	        dirField.setTypeface(tf);
	        birthdayField.setTypeface(tf);

	        emailField.setTypeface(tf);
	        telefonoField.setTypeface(tf);
		}
}