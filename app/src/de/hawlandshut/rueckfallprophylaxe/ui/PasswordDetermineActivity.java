package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.db.DataInserter;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.net.Data;
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
 * that's used to encrypt the database. When you {@link #onClick(View) click} on
 * the Button your inputs will be checked. If they are valid a
 * {@link RequestJsonAsyncTask} starts and gets the
 * {@link de.hawlandshut.rueckfallprophylaxe.net.Data} object from
 * {@link de.hawlandshut.rueckfallprophylaxe.net.RequestJson#getData()} and
 * calls {@link #createDatabaseIfEverythingValid(Data)} when finished. If
 * the statuscode stored in the JSON-Data is '1' the users inputs were right and
 * he can now access the app.
 * 
 * @author Stefan, Patrick
 * 
 */
public class PasswordDetermineActivity extends Activity implements
		OnClickListener {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PIN_PATTERN = "[0-9][0-9][0-9][0-9]";

	private EditText editTextToken, editTextEmail, editTextPin1, editTextPin2;
	private Button button;
	private ProgressBar progressBar;
	
	private String email;
	private String token;
	private String pin;
	private int statuscode;

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

		// Progress bar shows when data is processed
		progressBar = (ProgressBar) findViewById(R.id.passVerifyProgressBar);
		progressBar.setVisibility(View.GONE);
	}

	/**
	 * Checks if the given PIN's fulfill all requirements and creates a new
	 * database
	 * 
	 * @param pin
	 */
	public void onClick(View v) {
		progressBar.setVisibility(View.VISIBLE);

		String email = editTextEmail.getText().toString();
		String token = editTextToken.getText().toString();
		String pin1 = editTextPin1.getText().toString();
		String pin2 = editTextPin2.getText().toString();

		// email pattern right?
		if (!email.matches(EMAIL_PATTERN)) {
			progressBar.setVisibility(View.GONE);
			Toast.makeText(this,
					getResources().getString(R.string.toast_thats_no_email),
					Toast.LENGTH_LONG).show();
			// token given?
		} else if (token.equals("")) {
			progressBar.setVisibility(View.GONE);
			Toast.makeText(this,
					getResources().getString(R.string.toast_no_token_entered),
					Toast.LENGTH_LONG).show();
			// inputs equal?
		} else if (!pin1.equals(pin2)) {
			progressBar.setVisibility(View.GONE);
			Toast.makeText(this,
					getResources().getString(R.string.toast_pins_not_equal),
					Toast.LENGTH_LONG).show();
			// input is 4 numbers?
		} else if (!pin1.matches(PIN_PATTERN)) {
			progressBar.setVisibility(View.GONE);
			Toast.makeText(this,
					getResources().getString(R.string.toast_incorrect_pin),
					Toast.LENGTH_LONG).show();
		} else {
			this.pin = pin1;
			try {
				requestJson(email, token);
			} catch (IOException e) {
				Toast.makeText(
						this,
						getResources().getString(
								R.string.toast_something_went_wrong),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * Starts the RequestJasonAsyncTask.
	 * 
	 * @param email
	 *            Authentication email
	 * @param token
	 *            Authentication token
	 * @throws IOException
	 */
	private void requestJson(String email, String token) throws IOException {
		this.email = email;
		this.token = token;
		RequestJson rj = new RequestJson();
		new RequestJsonAsyncTask().execute(rj);
	}

	/**
	 * Creates a toast which explains the current statuscode.
	 * 
	 * @param message
	 *            text shown in Toast
	 */
	private void showMessage() {
		String message;
		switch (statuscode) {
		case 0:
			message = getResources().getString(
					R.string.toast_something_went_wrong);
			break;
		case 2:
			message = getResources().getString(R.string.toast_email_not_found);
			break;
		case 3:
			message = getResources().getString(R.string.toast_incorrect_token);
			break;
		case 4:
			message = getResources().getString(R.string.toast_token_was_used);
			break;
		case 5:
			message = getResources().getString(R.string.toast_data_inclompete);
			break;
		default:
			message = getResources().getString(
					R.string.toast_something_went_wrong);
			break;
		}
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * The method needs an JsonData object returned from
	 * {@link RequestJson#getData()}. It test the statuscode that was received
	 * from the server. If it is not '1', something went wrong and
	 * {@link #showMessage()} will be called. If the statuscode is '1'
	 * everything is right and the database will be generated and the patient
	 * will be forwarded to {@link HomeActivity}.
	 * 
	 * @param data
	 *            JsonData object
	 */
	private void createDatabaseIfEverythingValid(Data data) {
		statuscode = data.getData().getStatus().getStatuscode();

		if (statuscode != 1) {
			progressBar.setVisibility(View.GONE);
			showMessage();
		} else {
			Toast.makeText(
					this,
					getResources().getString(
							R.string.toast_register_successfull),
					Toast.LENGTH_LONG).show();
			Database db = new Database(this);
			db.InitializeSQLCipher(pin);
			DataInserter di = new DataInserter(db);
			di.insertData(data);
			db.close();
			progressBar.setVisibility(View.GONE);

			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
		}
	}

	/**
	 * Runs an AsyncTask which will send the HTTP-Request to the server. Uses
	 * {@link RequestJson#getData()} to get the
	 * {@link de.hawlandshut.rueckfallprophylaxe.net.Data} object and calls
	 * {@link PasswordDetermineActivity#createDatabaseIfEverythingValid(Data)}
	 * when everything worked.
	 * 
	 * 
	 * @author kackspast
	 * 
	 */
	private class RequestJsonAsyncTask extends
			AsyncTask<RequestJson, Integer, Data> {

		@Override
		protected Data doInBackground(RequestJson... params) {
			statuscode = 0;
			Data data = null;
			try {
				// get the JsonData object from RequestJson.getData()
				data = params[0].getData(email, token);
			} catch (JsonSyntaxException e) {
				showMessage();
				e.printStackTrace();
			} catch (IOException e) {
				showMessage();
				e.printStackTrace();
			}
			return data;
		}

		protected void onPostExecute(Data data) {
			if (data != null) {
				createDatabaseIfEverythingValid(data);
			}
		}

	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
}
