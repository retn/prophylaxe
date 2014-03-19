package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * TODO: PS: Bitte kommentieren -> JavaDoc
 *
 */
public class DiaryNewEntryActivity extends Activity {

	private DiaryNewEntryPictureManager pictureManager;
	private TextView DateEntry;
	private static int REQUEST_LOAD_IMAGE_FILE = 1;
	private static int REQUEST_LOAD_IMAGE_CAMERA = 2;
	private static final int removePicture = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_entry);
		
		// Initialize the mood spinner
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
		
		pictureManager = new DiaryNewEntryPictureManager(this);
	}
	
	//Creating Context Menu
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Bearbeiten");
		menu.add(Menu.NONE, removePicture, Menu.NONE, "Bild entfernen");
	}
	
	//this method automatically called when user select menu items
	public boolean onContextItemSelected(MenuItem item) {
		ImageViewWithContextMenuInfo.ImageViewContextMenuInfo menuInfo = (ImageViewWithContextMenuInfo.ImageViewContextMenuInfo) item.getMenuInfo();  
	    ImageViewWithContextMenuInfo img = (ImageViewWithContextMenuInfo) menuInfo.targetView;
	    
	    
		switch (item.getItemId()) {
		case removePicture:
			try {
				pictureManager.removePicture(img);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Toast.makeText(getApplicationContext(), "Bild wurde entfernt",Toast.LENGTH_LONG).show();
			
			break;

		}
		return super.onContextItemSelected(item);
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
				
				// Dialog - Take picture from camera or gallery
				showAttachmentDialog();
				
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private boolean validateInput() {
		// Validate title & entry
		
		return (validateInputTitle() && validateInputEntry());
	}
	
	/*
	 * Validates the entry title and pops up an error dialog on failure
	 */
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
	
	/*
	 * Validates the entry text and pops up an error dialog on failure
	 */
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
	
	private void showAttachmentDialog() {
		if (pictureManager.newPicturesAllowed()) {
			String[] items = {"Kamera", "Gallerie"};
			final Activity activity = this;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
		    builder.setTitle("Bildquelle w�hlen")
		           .setItems(items, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   
		               if (which == 0) { // Camera
		            	   
		            	   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		            	    // Ensure that there's a camera activity to handle the intent
		            	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
		            	        // Create the File where the photo should go
		            	        File photoFile = null;
		            	        try {
		            	            photoFile = pictureManager.createImageFile(activity);
		            	        } catch (IOException ex) {
		            	            // Error occurred while creating the File
		            				showNeutralErrorDialog("Fehler", ex.getMessage());
		            				
		            	        }
		            	        // Continue only if the File was successfully created
		            	        if (photoFile != null) {
		            	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
		            	                    Uri.fromFile(photoFile));
		            	            startActivityForResult(takePictureIntent, REQUEST_LOAD_IMAGE_CAMERA);
		            	        }
		            	    }
		               }
		               
		               if (which == 1) { // Gallery
		            	   Intent i = new Intent(
		            			   Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		            			    
		            			   startActivityForResult(i, REQUEST_LOAD_IMAGE_FILE);
		               }
		           }
		    });
		    builder.show();
		} else {
			// Error msg
			showNeutralErrorDialog("Zul�ssige Anzahl von Bildern �berschritten", "Das ist ein Bild zuviel. Es sind maximal "+DiaryNewEntryPictureManager.getMAX_PICTURES_ALLOWED()+" Bilder erlaubt.");
			
		}
	}
	

	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     
	     if (requestCode == REQUEST_LOAD_IMAGE_CAMERA && resultCode == RESULT_OK) {
	    	 
	    	 // showNeutralErrorDialog("Debug", mCurrentPhotoPath);
	    	 pictureManager.addCameraPicture();
	         
	         pictureManager.showCurrentPictures(this);
	     }
	      
	     if (requestCode == REQUEST_LOAD_IMAGE_FILE && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,
	                 filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         String picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         
	         // showNeutralErrorDialog("Debug", picturePath);

	         pictureManager.addGalleryPicture(picturePath);
	         
	         pictureManager.showCurrentPictures(this);
	     }
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
