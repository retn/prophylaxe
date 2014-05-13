package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.google.gson.JsonSyntaxException;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.db.MyTables;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

//TODO: Load existing entrys and add to the view
public class DiaryEntryListActivity extends Activity {
	
	List<DiaryEntry> Entries;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_diary);
		// Show the Up button in the action bar.
		setupActionBar();
		
		loadAndDrawEntries();
	}
	


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		loadAndDrawEntries();
	}



	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	private void loadAndDrawEntries() {
				
		// Get database object
		/* PinShare myApp = PinShare.getInstance();
		String pin = myApp.getPin();
		Database db = new Database(this);
		db.InitializeSQLCipher(pin);
		
		*/
	
		// Load and save entries
		Entries = new ArrayList<DiaryEntry>();
		Entries = ControllerData.getDiaryEntries();
		
		// Close database
		// db.close();
		
		// Debug
		Toast.makeText(this, "Einträge geladen: "+Entries.size(),
				Toast.LENGTH_LONG).show();
		
		// Draw entries into activity
		drawEntries();
	}
	
	@SuppressWarnings("deprecation")
	private void drawEntries() {
		
		// Create scroll view
		ScrollView scrollView = new ScrollView(this);
		
		// Create TableLayout
		TableLayout tbl=new TableLayout(this);
		tbl.setPadding(30, 30, 30, 30);
		TableRow.LayoutParams fieldparams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		
		// Loop through all entries
		for (DiaryEntry entry:Entries) {

			// Create TableRow for entry date
			
				TableRow trDate=new TableRow(this);
			
				// Create TextView for date
				TextView date = new TextView(this);
				
				// Set and style text
				String dateFormatted = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(entry.getCreated());
				date.setText(dateFormatted);
				date.setTypeface(null, Typeface.ITALIC);
				
				trDate.addView(date);
				
			// Create TableRow for entry title
			TableRow trTitle=new TableRow(this);
			
				// Create TextView for entry title
				TextView title = new TextView(this);
			
				// Set and style text
				title.setText(entry.getTitle());
				title.setTypeface(null, Typeface.BOLD);
			
				// Add entry title TextView
				trTitle.addView(title);
				
			// Create TableRow for entry title
			TableRow trContent=new TableRow(this);
			
			// Create TextView for entry content
			TextView content = new TextView(this);
			
				// Set text
				content.setText(entry.getContent());
				content.setPadding(0, 0, 0, 50);
				
				// Add content TesxtView
				trContent.addView(content);
				
			// Add rows to table view
			tbl.addView(trDate);
			tbl.addView(trTitle);
			tbl.addView(trContent);

		}
		
		scrollView.addView(tbl);
		this.setContentView(scrollView, fieldparams);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.diary, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_newEntry:
				openNewEntry();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void openNewEntry() {
		Intent intent = new Intent(this, DiaryNewEntryActivity.class);
    	startActivity(intent);
	}

}
