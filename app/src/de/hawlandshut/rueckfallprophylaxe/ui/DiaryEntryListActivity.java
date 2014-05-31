package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils.TruncateAt;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.db.DiaryEntryDatabase;

//TODO: Load existing entrys and add to the view
public class DiaryEntryListActivity extends Activity {
	
	List<DiaryEntry> Entries;
	ScrollView scrollView;
	
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

		super.onResume();
		
		// Toast.makeText(getApplicationContext(), "Resume..",Toast.LENGTH_LONG).show();
		
		Entries = ControllerData.getDiaryEntries();
		removeAllViewsAndDraw();
	}

	//Creating Context Menu
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Eintrag löschen");
		menu.add(Menu.NONE, 0, Menu.NONE, "Bestätigen");
	}
	
	public boolean onContextItemSelected(MenuItem item) {
	
		TableLayoutWithContextMenuInfo.TableLayoutContextMenuInfo menuInfo = (TableLayoutWithContextMenuInfo.TableLayoutContextMenuInfo) item.getMenuInfo();  
		TableLayoutWithContextMenuInfo tableLayout = (TableLayoutWithContextMenuInfo) menuInfo.targetView;
		
	    DiaryEntry myTag = (DiaryEntry) tableLayout.getTag();
	    
		switch (item.getItemId()) {
		case 0:
			DiaryEntryDatabase diaryEntryDB = new DiaryEntryDatabase(myTag, this);
			try {
				
				// Delete from db and refresh data
				diaryEntryDB.deleteFromDB();
				Entries = ControllerData.getDiaryEntries();
				
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			removeAllViewsAndDraw();

			Toast.makeText(getApplicationContext(), "Eintrag wurde entfernt",Toast.LENGTH_LONG).show();
			
			break;

		}
		return super.onContextItemSelected(item);
	}
	
	private void removeAllViewsAndDraw() {
		scrollView.removeAllViews();
		drawEntries();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}
	
	private void loadAndDrawEntries() {
				
		// Get database object
		PinShare myApp = PinShare.getInstance();
		String pin = myApp.getPin();
		Database db = new Database(this);
		db.InitializeSQLCipher(pin);
	
		// Load and save entries
		Entries = new ArrayList<DiaryEntry>();
		Entries = ControllerData.getDiaryEntries();
		
		// Close database
		db.close();
		
		// Debug
		Toast.makeText(this, "Einträge geladen: "+Entries.size(),
				Toast.LENGTH_LONG).show();
		
		// Draw entries into activity
		drawEntries();
	}
	
	@SuppressWarnings("deprecation")
	private void drawEntries() {
		
		// Create scroll view
		scrollView = new ScrollView(this);
		
		// Create TableLayout
		TableLayout tbl = new TableLayout(this);
		tbl.setPadding(30, 30, 30, 30);
		TableRow.LayoutParams fieldparams = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		
		// Loop through all entries
		for (DiaryEntry entry:Entries) {
			
			// Create onclick listener
			DiaryOnClickListener onClickListener = new DiaryOnClickListener(entry, this);

			// Create TableRow container
			TableLayoutWithContextMenuInfo trContainer = new TableLayoutWithContextMenuInfo(this);
			
			// Add OnClick Listener
			trContainer.setOnClickListener(onClickListener);
			trContainer.setTag(entry);
			
			// Add context menu
			registerForContextMenu(trContainer);
			
			// Create TableRow for entry date
			
				TableRow trDate=new TableRow(this);
			
				// Create TextView for date
				TextView date = new TextView(this);
				
				// Set and style text
				String dateFormatted = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(entry.getCreated());
				date.setText(dateFormatted);
				date.setTypeface(null, Typeface.ITALIC);
				
				trDate.addView(date);
				
				// trDate.setOnClickListener(onclickListener);

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
				content.setEllipsize(TruncateAt.END);
				content.setMaxLines(3);
				content.setLayoutParams(fieldparams);
				
				// Add content TesxtView
				trContent.addView(content);
				
			// Add rows to table view
			trContainer.addView(trDate);
			trContainer.addView(trTitle);
			trContainer.addView(trContent);
			tbl.addView(trContainer);

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
