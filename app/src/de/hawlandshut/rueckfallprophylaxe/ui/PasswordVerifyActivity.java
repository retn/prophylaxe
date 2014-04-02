package de.hawlandshut.rueckfallprophylaxe.ui;

import de.hawlandshut.rueckfallprophylaxe.db.Database;
import android.app.Activity;
import android.content.Intent;
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
	private Database data;

	/**
	 * Set variables, set an OnClickListener and switches to
	 * {@link PasswordDetermineActivity} if database file doesn't exist.
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_verify);
		data = new Database(this);

		if (!data.databaseExists()) {
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
				data.InitializeSQLCipher(input);

				Intent intent = new Intent(this, HomeActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				Toast.makeText(this, "Ihre Eingabe ist falsch",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
		return super.onOptionsItemSelected(item);
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
