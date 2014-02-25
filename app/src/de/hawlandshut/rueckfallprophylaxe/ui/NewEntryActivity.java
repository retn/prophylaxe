package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NewEntryActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_entry);
		
		// Initialize the spinner
		Spinner spinner = (Spinner) findViewById(R.id.spinnerMood);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.mood_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		
		// Set date into date picker text field
		// TODO: Get date of existing entry
		// TODO: Date picker modal
		
		// New entry -> Current date
		// Create current date string
		Time currentTime = new Time();
		currentTime.setToNow();
		String currentDate = currentTime.format("%d.%m.%Y - %H:%M");
		
		// Set datePicker text field to current date string
		EditText datePickerText = (EditText) findViewById(R.id.entryDateText);
		datePickerText.setText(currentDate);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_entry, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_createEntry:
				// TODO: save the entry
				return true;
			case R.id.action_addAttachment:
				// TODO: add an attachment
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
