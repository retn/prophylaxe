package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.net.JsonData;
import de.hawlandshut.rueckfallprophylaxe.net.RequestJson;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

/**
 * Activity that asks for the users email, token and let's the user define a PIN
 * that's used to encrypt the database
 * 
 * @author Stefan, Patrick
 * 
 */
public class PasswordDetermineActivity extends Activity implements
		OnClickListener {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	EditText editTextToken, editTextEmail, editTextPin1, editTextPin2;
	Button button;
	ProgressBar progressBar;
	String pin;
	int statuscode;

	/**
	 * set variables and set an OnClickListener
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_determine);

		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextToken = (EditText) findViewById(R.id.editTextToken);
		editTextPin1 = (EditText) findViewById(R.id.editTextPin2);
		editTextPin2 = (EditText) findViewById(R.id.editTextPin1);

		button = (Button) findViewById(R.id.best_button);
		button.setOnClickListener(this);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.GONE);
	}

	/**
	 * Checks if the given PIN's fulfill all requirements and creates a new
	 * database
	 * 
	 * @param pin
	 */
	public void onClick(View v) {

		String email = editTextEmail.getText().toString();
		String token = editTextToken.getText().toString();
		String pin1 = editTextPin1.getText().toString();
		String pin2 = editTextPin2.getText().toString();

		// email pattern right?
		if (!email.matches(EMAIL_PATTERN)) {
			Toast.makeText(this, "Email nicht korrekt!", Toast.LENGTH_LONG)
					.show();
			// token given?
		} else if (token.equals("")) {
			Toast.makeText(this, "Kein Token angegeben!", Toast.LENGTH_LONG)
					.show();
		} else if (!pin1.equals(pin2)) {
			// inputs equal?
			Toast.makeText(this, "PIN's nicht gleich!", Toast.LENGTH_LONG)
					.show();
		} else if (!pin1.matches("[0-9][0-9][0-9][0-9]")) {
			// input is 4 numbers?
			Toast.makeText(this, "PIN muss 4-stellig sein!", Toast.LENGTH_LONG)
					.show();
		} else {
			this.pin = pin1;
			try {
				requestJson(email, token);
			} catch (IOException e) {
				Toast.makeText(this, "Etwas lief falsch!", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	/**
	 * starts the RequestJasonAsyncTask 
	 * @param email Authentication email
	 * @param token Authentication token
	 * @throws IOException
	 */
	private void requestJson(String email, String token) throws IOException {
		RequestJson rj = new RequestJson(email, token);
		progressBar.setVisibility(View.VISIBLE);
		new RequestJsonAsyncTask().execute(rj);
	}

	/**
	 * creates a Toast with given message
	 * 
	 * @param message
	 *            text shown in Toast
	 */
	private void showMessage() {
		String message;
		switch (statuscode) {
		case 0: 
			message = "Etwas lief schief!";
			break;
		case 2:
			message = "Email nicht gefunden!";
			break;
		case 3:
			message = "Token falsch!";
			break;
		case 4:
			message = "Token bereits verwendet!";
			break;
		case 5:
			message = "Daten nicht komplett!";
			break;
		default:
			message = "";
			break;
		}
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * create new database
	 * 
	 * @param pin
	 *            database PIN
	 */
	private void createDatabaseIfEverythingValid(JsonData data) {
		statuscode = data.getData().getStatus().getStatuscode();
		
		if(statuscode != 1) {
			showMessage();
		} else {
			Toast.makeText(this, "Anmelden erfolgreich!", Toast.LENGTH_LONG).show();
			Database db = new Database(this);
			db.InitializeSQLCipher(pin);
			db.close();
			
			switchToVerify();
		}
	}

	/**
	 * switch to PasswordVerifyActivity
	 */
	private void switchToVerify() {
		Intent intent = new Intent(this, PasswordVerifyActivity.class);
		startActivity(intent);
	}

	private class RequestJsonAsyncTask extends
			AsyncTask<RequestJson, Integer, JsonData> {

		@Override
		protected JsonData doInBackground(RequestJson... params) {
			statuscode = 0;
			JsonData data = null;
			try {
				data = params[0].getData();
			} catch (JsonSyntaxException e) {
				showMessage();
				e.printStackTrace();
			} catch (IOException e) {
				showMessage();
				e.printStackTrace();
			} 
			return data;
		}
		
		protected void onProgressUpdate(Integer... progress){
			progressBar.setProgress(progress[0]);
		}

		protected void onPostExecute(JsonData data) {
			progressBar.setVisibility(View.GONE);
			if (data != null) {
				createDatabaseIfEverythingValid(data);
			}
		}

	}
}
