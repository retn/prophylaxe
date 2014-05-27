package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.ContactPoint;
import de.hawlandshut.rueckfallprophylaxe.db.DataInserter;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.net.JsonContactPoint;
import de.hawlandshut.rueckfallprophylaxe.net.RequestJson;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnDismissListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContactpointListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactpoint_list);
		
		final ListView lv = (ListView) findViewById(R.id.contactpointListView);
        lv.setAdapter(new ContactpointListAdapter(this, ControllerData.getPlacesToGo()));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
        
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.contactpoints, menu);
	    return true;
	}
	
	public void callMap(View view) {
		Intent intent = new Intent(this, ContactpointMapActivity.class);
		startActivity(intent);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_update_contactpoints) {

			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
	       
	        if(networkInfo != null && (networkInfo.isConnected()))
	        {
	        	final SharedPreferences sharedPref = this.getSharedPreferences(
	    				"de.hawlandshut.rueckfallprophylaxe", Context.MODE_PRIVATE);
	    		final String cpTimestampKey = "de.hawlandshut.rueckfallprophylaxe.cptimestamp";

	    		final ProgressDialog progressDialog = ProgressDialog.show(
	    				ContactpointListActivity.this, "Bitte warten...",
	    				"Anlaufstellen werden aktualisiert...");
	    		progressDialog.setCancelable(false);
	    		
	    		progressDialog.setOnDismissListener(new OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						final ListView lv = (ListView) findViewById(R.id.contactpointListView);
				        lv.setAdapter(new ContactpointListAdapter(ContactpointListActivity.this, ControllerData.getPlacesToGo()));
					}
	    		});
	    		
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Database db = new Database(ContactpointListActivity.this);
							
							db.InitializeSQLCipher(PinShare.getInstance().getPin());
							
							DataInserter di = new DataInserter(db);
							RequestJson rj = new RequestJson();
								
							Log.d("debug", "rj = " + rj.getContactPointTimestamp() + " sp = " + sharedPref.getString(cpTimestampKey, ""));
							if (!(rj.getContactPointTimestamp().equals(sharedPref.getString(cpTimestampKey, "")))) {
								List<JsonContactPoint> contactPoints = rj
										.getContactPoints();
								di.updateContactPoints(contactPoints);
								sharedPref.edit().putString(cpTimestampKey, rj.getContactPointTimestamp()).commit();
								new ControllerData(db, 1);
								runOnUiThread(new Runnable() {
				                    public void run() {
				                    	Toast.makeText(ContactpointListActivity.this, "Anlaufstellen aktualisiert", Toast.LENGTH_LONG).show();
				                    }
				                });
							} else {
								runOnUiThread(new Runnable() {
				                    public void run() {
				                    	Toast.makeText(ContactpointListActivity.this, "Anlaufstellen bereits aktuell", Toast.LENGTH_LONG).show();
				                    }
				                });
							}
							
							progressDialog.dismiss();
							db.close();
						} catch (Exception e) {
							e.printStackTrace();
							progressDialog.dismiss();
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(ContactpointListActivity.this, "Sorry! Etwas lief falsch!", Toast.LENGTH_LONG).show();
								}
							});
						}
					}
				}).start();
	        } else {
	        	Toast.makeText(ContactpointListActivity.this, "Keine Internetverbindung möglich!", Toast.LENGTH_LONG).show();
	        }
		}
		return super.onOptionsItemSelected(item);
	}

}
