package com.example.scoutmanager.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.UploadRequest;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.android.AuthActivity;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxFileSizeException;
import com.dropbox.client2.exception.DropboxIOException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.example.scoutmanager.R;
import com.example.scoutmanager.activities.Upload.API_Listener;
import com.example.scoutmanager.model.DataBase;

public class ImportarExportarActivity extends Activity implements API_Listener{
	
	private TableRow importar;
	private TableRow exportar;
	
	private AlertDialog.Builder popUpShare;
	
	private static final int SELECT_FILE= 7;
	
	final static private String APP_KEY = "tl91rcymbic00ep";
	final static private String APP_SECRET = "djla7mxma5j8cfs";
	
	 // You don't need to change these, leave them alone.
    final static private String ACCOUNT_PREFS_NAME = "prefs";
    final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";

    private boolean mLoggedIn;
    
    private boolean mode;


	// In the class declaration section:
	private DropboxAPI<AndroidAuthSession> mDBApi;
	
	
	 @Override
     public void onSuccess(int requestnumber, Object obj)
     {
            try
            {
                   if(requestnumber == 8)
                   {
                         boolean sucess=(Boolean) obj;
                         if(sucess)
                         {
                            Toast.makeText(ImportarExportarActivity.this, "BASE DE DATOS SUBIDA A DROPBOX CORRECTAMENTE", Toast.LENGTH_LONG).show();
                            Intent i=new Intent(ImportarExportarActivity.this,ImportarExportarActivity.class);
                            startActivity(i);
                            finish();
                         }
                   }
                   
                   if(requestnumber == 9)
                   {
                         boolean sucess=(Boolean) obj;
                         if(sucess)
                         {
                            Toast.makeText(ImportarExportarActivity.this, "BASE DE DATOS DESCARGADA DE DROPBOX CORRECTAMENTE", Toast.LENGTH_LONG).show();
                            Intent i=new Intent(ImportarExportarActivity.this,ImportarExportarActivity.class);
                            startActivity(i);
                            finish();
                         }
                   }
            }
            catch (Exception e)
            {
                   e.printStackTrace();
            }
           
     }
     @Override
     public void onFail(String errormessage)
     {
           
           
     }
	
	 @Override
		public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        // We create a new AuthSession so that we can use the Dropbox API.
	        AndroidAuthSession session = buildSession();
	        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

	        checkAppKeySetup();
	        
	        setContentView(R.layout.imexportar_activity);
	        
	        ActionBar actionbar;
			actionbar= getActionBar();
			actionbar.setTitle("AJUSTES");
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
			    	
			    	//Intent intent = new Intent(getApplicationContext(), FileChooserActivity.class);
		    	    //startActivityForResult(intent, SELECT_FILE);
		    	    
		    	    
		    	    if (mLoggedIn) {
			    		try {
							downloadDB();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
				             .show();
							} catch (DropboxException e) {
							// TODO Auto-generated catch block
								Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
					             .show();
								}
	                   // logOut();
	                } else {
	                    // Start the remote authentication
	                	mode=false;
	                	mDBApi.getSession().startOAuth2Authentication(ImportarExportarActivity.this);
	                   
	                }
		    	    
			    }
			});
			
			exportar.setOnClickListener(new View.OnClickListener() 
			{                   
			    @Override
			    public void onClick(View v)
			    {
			    	if (mLoggedIn) {
			    		try {
							uploadDB();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							Toast.makeText(getBaseContext(), "COPIA DE SEGURIDAD EXITOSA", Toast.LENGTH_LONG)
				             .show();
							} catch (DropboxException e) {
							// TODO Auto-generated catch block
								Toast.makeText(getBaseContext(), "COPIA DE SEGURIDAD EXITOSA", Toast.LENGTH_LONG)
					             .show();
							}
	                   // logOut();
	                } else {
	                    // Start the remote authentication
	                	mode=true;
	                   mDBApi.getSession().startOAuth2Authentication(ImportarExportarActivity.this);
	                }
			    	
			    	//exportDB();
			    	
			    }
			});
			
	        setLoggedIn(mDBApi.getSession().isLinked(),"");

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

     private void uploadDB() throws FileNotFoundException, DropboxException{
    	 File file = new File(Environment.getExternalStorageDirectory() + "/BackupFolder/BACKUP_database.db.bak");
    	 //FileInputStream inputStream = new FileInputStream(file);
    	 
    	 Upload upload = new Upload(8,ImportarExportarActivity.this, mDBApi,"/BACKUP_database.db.bak",file);
         upload.execute();
    
     }
     
     private void downloadDB() throws FileNotFoundException, DropboxException{
    	 File file = new File(Environment.getExternalStorageDirectory() + "/BackupFolder/BACKUP_database.db.bak");
    	 
    	Download download = new Download(9, ImportarExportarActivity.this, mDBApi, file);
    	download.execute();
    
     }
	 
	 private void initializeTypeface(){
			
			Typeface tf = Typeface.createFromAsset(getAssets(),
	                "fonts/Roboto-Light.ttf");
	        
	        TextView importarText = (TextView) findViewById(R.id.importarText);
	        TextView exportarText = (TextView) findViewById(R.id.exportarText);
	        TextView BDText = (TextView) findViewById(R.id.BDText);
	        
	        BDText.setTextColor((Color.parseColor("#5d2f89")));
	        BDText.setTextSize(20);
	        BDText.setTypeface(tf);

	        importarText.setTypeface(tf);
	        exportarText.setTypeface(tf);

	  }
	 
	 @Override
	    protected void onResume() {
	        super.onResume();
	        AndroidAuthSession session = mDBApi.getSession();

	        // The next part must be inserted in the onResume() method of the
	        // activity from which session.startAuthentication() was called, so
	        // that Dropbox authentication completes properly.
	        if (session.authenticationSuccessful()) {
	            try {
	                // Mandatory call to complete the auth
	                session.finishAuthentication();

	                // Store it locally in our app for later use
	                storeAuth(session);
	                
	                if(mode){
	                	setLoggedIn(true, "UP");
	                }else{
		                setLoggedIn(true, "DOWN");
	                }
	                
	            } catch (IllegalStateException e) {
	                showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
	                Log.i("DBoauth", "Error authenticating", e);
	            }
	        }
	    }

	    private void logOut() {
	        // Remove credentials from the session
	        mDBApi.getSession().unlink();

	        // Clear our stored keys
	        clearKeys();
	        // Change UI state to display logged out version
	        setLoggedIn(false,"");
	    }

	    /**
	     * Convenience function to change UI state based on being logged in
	     */
	    private void setLoggedIn(boolean loggedIn, String mode) {
	    	mLoggedIn = loggedIn;
	    	
	    	if(mode == "UP"){
		    	try {
					uploadDB();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DropboxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	
	    	if(mode == "DOWN"){
		    	try {
					downloadDB();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DropboxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }

	    private void checkAppKeySetup() {
	        // Check to make sure that we have a valid app key
	        if (APP_KEY.startsWith("CHANGE") ||
	                APP_SECRET.startsWith("CHANGE")) {
	            showToast("You must apply for an app key and secret from developers.dropbox.com, and add them to the AndroScout ap before trying it.");
	            finish();
	            return;
	        }

	        // Check if the app has set up its manifest properly.
	        Intent testIntent = new Intent(Intent.ACTION_VIEW);
	        String scheme = "db-" + APP_KEY;
	        String uri = scheme + "://" + AuthActivity.AUTH_VERSION + "/test";
	        testIntent.setData(Uri.parse(uri));
	        PackageManager pm = getPackageManager();
	        if (0 == pm.queryIntentActivities(testIntent, 0).size()) {
	            showToast("URL scheme in your app's " +
	                    "manifest is not set up correctly. You should have a " +
	                    "com.dropbox.client2.android.AuthActivity with the " +
	                    "scheme: " + scheme);
	            finish();
	        }
	    }

	    private void showToast(String msg) {
	        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
	        error.show();
	    }

	    /**
	     * Shows keeping the access keys returned from Trusted Authenticator in a local
	     * store, rather than storing user name & password, and re-authenticating each
	     * time (which is not to be done, ever).
	     */
	    private void loadAuth(AndroidAuthSession session) {
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	        String key = prefs.getString(ACCESS_KEY_NAME, null);
	        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
	        if (key == null || secret == null || key.length() == 0 || secret.length() == 0) return;

	        if (key.equals("oauth2:")) {
	            // If the key is set to "oauth2:", then we can assume the token is for OAuth 2.
	            session.setOAuth2AccessToken(secret);
	        } else {
	            // Still support using old OAuth 1 tokens.
	            session.setAccessTokenPair(new AccessTokenPair(key, secret));
	        }
	    }

	    /**
	     * Shows keeping the access keys returned from Trusted Authenticator in a local
	     * store, rather than storing user name & password, and re-authenticating each
	     * time (which is not to be done, ever).
	     */
	    private void storeAuth(AndroidAuthSession session) {
	        // Store the OAuth 2 access token, if there is one.
	        String oauth2AccessToken = session.getOAuth2AccessToken();
	        if (oauth2AccessToken != null) {
	            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	            Editor edit = prefs.edit();
	            edit.putString(ACCESS_KEY_NAME, "oauth2:");
	            edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
	            edit.commit();
	            return;
	        }
	        // Store the OAuth 1 access token, if there is one.  This is only necessary if
	        // you're still using OAuth 1.
	        AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
	        if (oauth1AccessToken != null) {
	            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	            Editor edit = prefs.edit();
	            edit.putString(ACCESS_KEY_NAME, oauth1AccessToken.key);
	            edit.putString(ACCESS_SECRET_NAME, oauth1AccessToken.secret);
	            edit.commit();
	            return;
	        }
	    }

	    private void clearKeys() {
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	        Editor edit = prefs.edit();
	        edit.clear();
	        edit.commit();
	    }

	    private AndroidAuthSession buildSession() {
	        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);

	        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
	        loadAuth(session);
	        return session;
	    }
}

class Upload extends AsyncTask<Void, Long, Boolean>
{

       private DropboxAPI<?> mApi;
       private String mPath;

       private UploadRequest mRequest;
       private Context mContext;

       private String mErrorMsg;

       //new class variables:
       int mFilesUploaded;
       private File mFilesToUpload;
       int mCurrentFileIndex;
       int totalBytes = 0;

       API_Listener api_Listener;
       ImportarExportarActivity importarExportar_Activity;
       private int requestNumber;

       public Upload(int request_num,ImportarExportarActivity activity, DropboxAPI<?> api, String dropboxPath, File filesToUpload)
       {
              // We set the context this way so we don't accidentally leak activities
              mContext = activity;      
              api_Listener=(API_Listener) activity;
              mApi = api;
              mPath = dropboxPath;
              requestNumber=request_num;

              //set number of files uploaded to zero.
              mFilesUploaded = 0;
              mFilesToUpload = filesToUpload;
              mCurrentFileIndex = 0;
       }


       @Override
       protected void onPreExecute()
       {
              super.onPreExecute();

       }
       @Override
       protected Boolean doInBackground(Void... params)
       {
              FileInputStream fis;
              try
              {                         
                     
	               File file = mFilesToUpload;
		
	               // By creating a request, we get a handle to the putFile
	               // operation,
	               // so we can cancel it later if we want to
	               fis = new FileInputStream(file);                      
	               String path = mPath + file.getName();
	               mRequest = mApi.putFileOverwriteRequest(mPath, fis,file.length(),null);                  
	              
	               mRequest.upload();
                     
                   return true;
              } catch (DropboxUnlinkedException e) {
                     // This session wasn't authenticated properly or user unlinked
                     mErrorMsg = "This app wasn't authenticated properly.";
              } catch (DropboxFileSizeException e) {
                     // File size too big to upload via the API
                     mErrorMsg = "This file is too big to upload";
              } catch (DropboxPartialFileException e) {
                     // We canceled the operation
                     mErrorMsg = "Upload canceled";
              } catch (DropboxServerException e) {
                     // Server-side exception. These are examples of what could happen,
                     // but we don't do anything special with them here.
                     if (e.error == DropboxServerException._401_UNAUTHORIZED) {
                           // Unauthorized, so we should unlink them. You may want to
                           // automatically log the user out in this case.
                     } else if (e.error == DropboxServerException._403_FORBIDDEN) {
                           // Not allowed to access this
                     } else if (e.error == DropboxServerException._404_NOT_FOUND) {
                           // path not found (or if it was the thumbnail, can't be
                           // thumbnailed)
                     } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
                           // user is over quota
                     } else {
                           // Something else
                     }
                     // This gets the Dropbox error, translated into the user's language
                     mErrorMsg = e.body.userError;
                     if (mErrorMsg == null) {
                           mErrorMsg = e.body.error;
                     }
              } catch (DropboxIOException e) {
                     // Happens all the time, probably want to retry automatically.
                     mErrorMsg = "Network error.  Try again.";
              } catch (DropboxParseException e) {
                     // Probably due to Dropbox server restarting, should retry
                     mErrorMsg = "Dropbox error.  Try again.";
              } catch (DropboxException e) {
                     // Unknown error
                     mErrorMsg = "Unknown error.  Try again.";
              } catch (FileNotFoundException e) {
              }

              return false;
       }

       @Override
       protected void onPostExecute(Boolean result)
       {

              if (result)
              {
                     api_Listener.onSuccess(requestNumber, result);               
              }
              else
              {
                     showToast(mErrorMsg);
              }
       }
       private void showToast(String msg)
       {
              Toast error = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
              error.show();
       }
       
       public interface API_Listener
       {
              public void onSuccess(int requestnumber, Object obj); 
              public void onFail(String errormessage);
       }
}

class Download extends AsyncTask<Void, Long, Boolean>
{

       private DropboxAPI<?> mApi;
       private String mPath;

       private UploadRequest mRequest;
       private Context mContext;

       private String mErrorMsg;

       //new class variables:
       int mFilesUploaded;
       private File mFilesToUpload;
       int mCurrentFileIndex;
       int totalBytes = 0;

       API_Listener api_Listener;
       ImportarExportarActivity importarExportar_Activity;
       private int requestNumber;

       public Download(int request_num,ImportarExportarActivity activity, DropboxAPI<?> api, File filesToDownload)
       {
              // We set the context this way so we don't accidentally leak activities
              mContext = activity;      
              api_Listener=(API_Listener) activity;
              mApi = api;
              requestNumber=request_num;

              //set number of files uploaded to zero.
              mFilesUploaded = 0;
              mFilesToUpload = filesToDownload;
              mCurrentFileIndex = 0;
       }


       @Override
       protected void onPreExecute()
       {
              super.onPreExecute();

       }
       @Override
       protected Boolean doInBackground(Void... params)
       {
              FileOutputStream fis;
              try
              {                         
                     
	               File file = mFilesToUpload;
		
	               // By creating a request, we get a handle to the putFile
	               // operation,
	               // so we can cancel it later if we want to
	               fis = new FileOutputStream(file);                   	               
	               
	               DropboxFileInfo mDownloaded = mApi.getFile("/BACKUP_database.db.bak", null, fis, null);
                     
                   return true;
              } catch (DropboxUnlinkedException e) {
                     // This session wasn't authenticated properly or user unlinked
                     mErrorMsg = "This app wasn't authenticated properly.";
              } catch (DropboxFileSizeException e) {
                     // File size too big to upload via the API
                     mErrorMsg = "This file is too big to upload";
              } catch (DropboxPartialFileException e) {
                     // We canceled the operation
                     mErrorMsg = "Upload canceled";
              } catch (DropboxServerException e) {
                     // Server-side exception. These are examples of what could happen,
                     // but we don't do anything special with them here.
                     if (e.error == DropboxServerException._401_UNAUTHORIZED) {
                           // Unauthorized, so we should unlink them. You may want to
                           // automatically log the user out in this case.
                     } else if (e.error == DropboxServerException._403_FORBIDDEN) {
                           // Not allowed to access this
                     } else if (e.error == DropboxServerException._404_NOT_FOUND) {
                           // path not found (or if it was the thumbnail, can't be
                           // thumbnailed)
                     } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
                           // user is over quota
                     } else {
                           // Something else
                     }
                     // This gets the Dropbox error, translated into the user's language
                     mErrorMsg = e.body.userError;
                     if (mErrorMsg == null) {
                           mErrorMsg = e.body.error;
                     }
              } catch (DropboxIOException e) {
                     // Happens all the time, probably want to retry automatically.
                     mErrorMsg = "Network error.  Try again.";
              } catch (DropboxParseException e) {
                     // Probably due to Dropbox server restarting, should retry
                     mErrorMsg = "Dropbox error.  Try again.";
              } catch (DropboxException e) {
                     // Unknown error
                     mErrorMsg = "Unknown error.  Try again.";
              } catch (FileNotFoundException e) {
              }

              return false;
       }

       @Override
       protected void onPostExecute(Boolean result)
       {

              if (result)
              {
                     api_Listener.onSuccess(requestNumber, result);               
              }
              else
              {
                     showToast(mErrorMsg);
              }
       }
       private void showToast(String msg)
       {
              Toast error = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
              error.show();
       }
       
}
