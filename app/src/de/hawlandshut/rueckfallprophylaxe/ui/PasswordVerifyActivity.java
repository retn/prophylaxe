package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.db.DataInserter;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.net.JsonContactPoint;
import de.hawlandshut.rueckfallprophylaxe.net.RequestJson;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Asks the user for the PIN to decrypt the database and switches to
 * HomeActivity if the correct PIN was given. Switches to
 * {@link PasswordDetermineActivity} if no database exists (if the app is
 * started the first time).
 * 
 * @author Stefan, Patrick
 * 
 */
public class PasswordVerifyActivity extends Activity implements OnClickListener {

	private EditText editTextPin;
	private Database db;

	/**
	 * Set variables, set an OnClickListener and switches to
	 * {@link PasswordDetermineActivity} if database file doesn't exist.
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_verify);
		db = new Database(this);

		if (!db.databaseExists()) {
			Intent intent = new Intent(this, PasswordDetermineActivity.class);
			startActivity(intent);
			
	
		}

		editTextPin = (EditText) findViewById(R.id.PIN_verify);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pass_verify, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_pinEntered) {

			String input = editTextPin.getText().toString();

			try {
				db.InitializeSQLCipher(input);
				// Pin global speichern
				PinShare myApp = PinShare.getInstance();
				myApp.setPin(input);
				
				launchDialog();
			} catch (Exception e) {
				Toast.makeText(this, "Ihre Eingabe ist falsch",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void launchDialog() {
		final SharedPreferences sharedPref = this.getSharedPreferences(
				"de.hawlandshut.rueckfallprophylaxe", Context.MODE_PRIVATE);
		final String cpTimestampKey = "de.hawlandshut.rueckfallprophylaxe.cptimestamp";

		final ProgressDialog progressDialog = ProgressDialog.show(
				PasswordVerifyActivity.this, "Bitte warten...",
				"Deine Daten werden geladen...");
		progressDialog.setCancelable(false);

		progressDialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(final DialogInterface arg0) {
				Intent intent = new Intent(PasswordVerifyActivity.this,
						TrafficLightActivity.class);
				startActivity(intent);
			}
		});

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					DataInserter di = new DataInserter(db);
					RequestJson rj = new RequestJson();

					if (rj.getContactPointTimestamp() == sharedPref.getString(
							cpTimestampKey, "")) {
						List<JsonContactPoint> contactPoints = rj
								.getContactPoints();
						di.updateContactPoints(contactPoints);
						sharedPref.edit().putString(cpTimestampKey, rj.getContactPointTimestamp());
					}

					new ControllerData(db);

					db.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				progressDialog.dismiss();
			}
		}).start();
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
