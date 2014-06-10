package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.google.android.gms.internal.cd;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntryPicture;
import de.hawlandshut.rueckfallprophylaxe.data.Emotions;
import de.hawlandshut.rueckfallprophylaxe.data.Media;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.db.DiaryEntryDatabase;
import de.hawlandshut.rueckfallprophylaxe.db.MyTables;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.text.format.Time;
import android.util.Log;
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
import android.widget.Toast;


/**
 * TODO: PS: Bitte kommentieren -> JavaDoc
 *
 */
public class DiaryNewEntryActivity extends Activity {
	private DiaryEntry existingEntry;
	private DiaryNewEntryPictureManager pictureManager;
	private TextView DateEntry;
	private static int REQUEST_LOAD_IMAGE_FILE = 1;
	private static int REQUEST_LOAD_IMAGE_CAMERA = 2;
	private static final int removePicture = 0;
	private Date selectedDate;
	private Database data;
	private DiaryDatePickerFragment datePicker;
	
	
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

		// Let the datePicker appear when user clicks the date text field
		addListenerOnEntryDateText(); 
		
		pictureManager = new DiaryNewEntryPictureManager(this);
		datePicker = new DiaryDatePickerFragment(this);
		
		Intent i = getIntent();
		
		// Load selected Entry
		existingEntry = (DiaryEntry)i.getSerializableExtra("diaryEntry");
		
		try {
			// Fills input fields with default values or existing ones
			fillInputFields();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
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
	    datePicker.show(getFragmentManager(), "datePicker");
	    
	}

	/**
	 * Fills input fields with default or existing values
	 * @throws ParseException
	 */
	private void fillInputFields() throws ParseException {
		
		// New entry
		if (existingEntry == null) {
			// New entry -> Current date
			Time currentTime = new Time();
			currentTime.setToNow();
			fillInputField_date(currentTime.monthDay, currentTime.month, currentTime.year);
		}
		
		// Existing entry
		else {
			// Date
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
			String currentDate = dateFormat.format(existingEntry.getCreated());
			
			// Set datePicker text field to current date string
			TextView datePickerText = (TextView) findViewById(R.id.entryDateText);
			datePickerText.setText(currentDate);
	
			// Title
			TextView title = (TextView) this.findViewById(R.id.title);
			title.setText(existingEntry.getTitle());
			
			// Entry
			TextView entry = (EditText) this.findViewById(R.id.entry);
			entry.setText(existingEntry.getContent());
			
			// Mood
			Spinner emotionSpinner = (Spinner) this.findViewById(R.id.spinnerMood);
			Toast.makeText(this, "Geladene emotion id: "+existingEntry.getEmotionId(),
					Toast.LENGTH_LONG).show();
			
			emotionSpinner.setSelection(existingEntry.getEmotionId()-1);
			
			// Add existing pictures to pictureManager
			Media[] media = existingEntry.getMedia();
			if (media != null) {
				for (Media myMedia:media) {
					Log.d("showCurrentPictures","existing pic found");
					try {
						Bitmap myImage = myMedia.getImage();
						pictureManager.addExistingPicture(myImage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			} else {
				Log.d("showCurrentPictures","Diary entry has no pics");
			}
		}

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
				if (validateInput()) { // Input okay
					
					// Collect data
						// Title text
						EditText title = (EditText) findViewById(R.id.title);
						String titleText = title.getText().toString();
					
						// Entry text
						EditText entryTextView = (EditText) findViewById(R.id.entry);
						String entryText = entryTextView.getText().toString();
					
						// Date
						selectedDate = datePicker.getSelectedDate();
						Log.d("Datum", selectedDate.toString());
					

					
					// Get database object
					PinShare myApp = PinShare.getInstance();
					String pin = myApp.getPin();
					Database db = new Database(this);
					db.InitializeSQLCipher(pin);
					MyTables myTables = db.getTables();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
					
					// Create hash map for main table
					HashMap<String, String> myHashMap = new HashMap<String, String>();
					myHashMap.put("title", titleText);
					myHashMap.put("content", entryText);
					myHashMap.put("created", dateFormat.format(selectedDate));
					
					// Get id of selected emotion
					Spinner emotionSpinner = (Spinner) findViewById(R.id.spinnerMood);
					TextView emotionView = (TextView) emotionSpinner.getSelectedView();
					
					Emotions emotions = new Emotions();
					String selectedMoodID = ""+emotions.getIDbyName((String) emotionView.getText());
					
					Log.d("DiaryEntrySave", "Ausgewählte Emotion ID: "+selectedMoodID);
					
					int DiaryEntryID;
					
					// Create new entry
					if (existingEntry == null) {
						
						
						// Get ID of new entry
						int newDiaryEntryID = DiaryEntryDatabase.getLastID()+1;
						DiaryEntryID = newDiaryEntryID;
						String DiaryEntryIDString = ""+newDiaryEntryID;
						
						Log.d("DiaryNewEntrySave", "Erstelle neuen Eintrag mit ID: "+newDiaryEntryID + " und mood id "+selectedMoodID);
						
						// Insert main table data
						myTables.insert("spl_diary_entry", myHashMap);
						
						// Create hashmap for emotions
						HashMap<String, String> myHashMap1 = new HashMap<String, String>();
						myHashMap1.put("entryID", DiaryEntryIDString);
						myHashMap1.put("emotionID", selectedMoodID);
						
						myTables.insert("spl_diary_entry_has_mood", myHashMap1);
						
						
						// TODO: Create DiaryEntry object and set to existingEntry
						// DiaryEntry newEntry = new DiaryEntry(0, titleText, entryText, selectedDate);
					} 
					// Update existing entry
					else {
						// ID of existing entry
						DiaryEntryID = existingEntry.getId();
						String DiaryEntryIDString = ""+existingEntry.getId();
						// Log.d("DiaryNewEntrySave","Update Entry "+DiaryEntryIDString);
						
						myTables.update("spl_diary_entry", myHashMap, DiaryEntryIDString, "id");
						
						// Create hashmap for emotions
						HashMap<String, String> emotionHashmap = new HashMap<String, String>();
						emotionHashmap.put("entryID", DiaryEntryIDString);
						emotionHashmap.put("emotionID", selectedMoodID);
						
						myTables.update("spl_diary_entry_has_mood", emotionHashmap, DiaryEntryIDString, "entryID");
						Log.d("DiaryEntrySave","Update Entry "+DiaryEntryIDString+" mit Emotion ID "+selectedMoodID);
						
						// Delete pictures that were moved to trash
						DiaryEntryDatabase diaryEntryDB = new DiaryEntryDatabase(existingEntry, this);
						for (DiaryEntryPicture pic:pictureManager.getTrash()) {
							diaryEntryDB.deletePictureFromDB(pic.getId());
						}
					}
					
					// Save new pictures
					ArrayList<byte[]> newPics = pictureManager.getNewPicturesAsBlob();
					for (byte[] newPic:newPics) {
						Log.d("DiaryEntrySave", "saving picture for diaryEntryID "+DiaryEntryID);
						myTables.insertEntryPicture(DiaryEntryID, newPic);
					}
					
					// Refresh db
					ControllerData cd = new ControllerData(db,true);
					cd.fetchDiaryEntries();
					
					db.close();
					
					Toast.makeText(this, "Eintrag gespeichert",
							Toast.LENGTH_LONG).show();
					
				}
				return true;
			case R.id.action_addAttachment:
				// Dialog - Take picture from camera or gallery
				showAttachmentDialog();
				
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Validates title & entry text
	 * @return
	 */
	private boolean validateInput() {
		return (validateInputTitle() && validateInputEntry());
	}
	
	/**
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
	
	/**
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
			
		    builder.setTitle("Bildquelle w���hlen")
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
			showNeutralErrorDialog("Zul��ssige Anzahl von Bildern ��berschritten", "Das ist ein Bild zuviel. Es sind maximal "+DiaryNewEntryPictureManager.getMAX_PICTURES_ALLOWED()+" Bilder erlaubt.");
			
		}
	}
	

	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	     
	     if (requestCode == REQUEST_LOAD_IMAGE_CAMERA && resultCode == RESULT_OK) {
	    	 
	    	 // showNeutralErrorDialog("Debug", mCurrentPhotoPath);
	    	 pictureManager.addCameraPicture();
	         
	         pictureManager.drawCurrentPictures();
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
	         
	         pictureManager.drawCurrentPictures();
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
