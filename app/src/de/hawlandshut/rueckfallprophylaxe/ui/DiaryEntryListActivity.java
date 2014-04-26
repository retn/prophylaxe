package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.db.MyTables;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

//TODO: Load existing entrys and add to the view
public class DiaryEntryListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Get database object
		PinShare myApp = PinShare.getInstance();
		String pin = myApp.getPin();
		Database db = new Database(this);
		db.InitializeSQLCipher(pin);
		
		List<DiaryEntry> Entries= new ArrayList<DiaryEntry>();
		
		// Load data
		Entries = ControllerData.getDiaryEntries();
		
		db.close();
		
		// Immer 0 .....
		Toast.makeText(this, "Einträge geladen: "+Entries.size(),
				Toast.LENGTH_LONG).show();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

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
