package de.hawlandshut.rueckfallprophylaxe.ui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;



public class NewEntryActivity extends Activity {

	private TextView DateEntry;
	
	
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
		
		try {
			// Fills input fields with default values or existing ones
			fillInputFields();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		// Let the datePicker appear when user clicks the date text field
		addListenerOnEntryDateText(); 
	}
	
	public void addListenerOnEntryDateText() {
		 
		DateEntry = (TextView) findViewById(R.id.entryDateText);
 
		DateEntry.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}
 
		});
 
	}
	
	private void showDatePickerDialog() {
		DialogFragment newFragment = new DiaryDatePickerFragment(this);
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	private void fillInputFields() throws ParseException {

		// Set date into date picker text field
		// TODO: Get date of existing entry
		
		// New entry -> Current date
		Time currentTime = new Time();
		currentTime.setToNow();
		fillInputField_date(currentTime.monthDay, currentTime.month, currentTime.year);
	}
	
	public void fillInputField_date(int day, int month, int year) throws ParseException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
		String currentDate = dateFormat.format(dateFormat.parse(day+"."+month+"."+year));
		
		// Set datePicker text field to current date string
		TextView datePickerText = (TextView) findViewById(R.id.entryDateText);
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
				if (validateInput()) {
					// TODO: save the entry
				}
				return true;
			case R.id.action_addAttachment:
				// TODO: add an attachment
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private boolean validateInput() {
		// Validate title & entry
		return (validateInputTitle() && validateInputEntry());
	}
	
	
	private boolean validateInputTitle() {
		EditText title = (EditText) findViewById(R.id.title);
		String titleText = title.getText().toString();
		
		// Empty
		if (titleText.isEmpty()) {
			showNeutralErrorDialog("Fehler", "Du hast keinen Titel eingegeben.");
			return false;
		}
			
		// Max length
		int maxTitleChars = 15;
		if (titleText.length() > maxTitleChars) {
			int exceededChars = titleText.length()-maxTitleChars;
			showNeutralErrorDialog("Fehler", "Der Titel ist um "+exceededChars+" Zeichen zu lang. Maximal sind "+maxTitleChars+" Zeichen erlaubt.");
			return false;
		}
			
		return true;
	}
	
	private boolean validateInputEntry() {
		EditText title = (EditText) findViewById(R.id.entry);
		String titleText = title.getText().toString();
		
		// Empty
		if (titleText.isEmpty()) {
			showNeutralErrorDialog("Fehler", "Du hast keinen Text eingegeben.");
			return false;
		}
			
		// Max length
		int maxTitleChars = 10000; // ?
		if (titleText.length() > maxTitleChars) {
			int exceededChars = titleText.length()-maxTitleChars;
			showNeutralErrorDialog("Fehler", "Der Eintrag ist um "+exceededChars+" Zeichen zu lang. Maximal sind "+maxTitleChars+" Zeichen erlaubt.");
			return false;
		}
			
		return true;
	}
	
	
	private void showNeutralErrorDialog(String title, String msg) {
		new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(msg)
	    .setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // Do nothing
	        }
	     })
	     .show();
	}

}
