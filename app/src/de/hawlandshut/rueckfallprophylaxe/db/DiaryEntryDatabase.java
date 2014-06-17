package de.hawlandshut.rueckfallprophylaxe.db;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonSyntaxException;

import android.content.Context;
import android.util.Log;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;
import de.hawlandshut.rueckfallprophylaxe.ui.PinShare;

public class DiaryEntryDatabase {

	DiaryEntry entry;
	Context context;
	
	public DiaryEntryDatabase(DiaryEntry entry, Context context) {
		this.entry = entry;
		this.context = context;
	}
	
	/**
	 * Returns the id of the latest diary entry
	 * @return
	 */
	public static int getLastID() {
		List<DiaryEntry> Entries;
		
		Entries = ControllerData.getDiaryEntries();
		
		if (Entries.size() > 0) {
			DiaryEntry lastDiaryEntry = Entries.get(Entries.size()-1);
			
			Log.d("DiaryEntryDatabase","Letzter Key lautet "+lastDiaryEntry.getId());
			
			return lastDiaryEntry.getId();
		}
		
		return 0;

	}
	
	public void deletePictureFromDB(int id) {
		Log.d("DiaryEntryDatabase", "Trying to delete pic with id: "+id);
		
		// Initialize database
		PinShare myApp = PinShare.getInstance();
		String pin = myApp.getPin();
		Database db = new Database(context);
		db.InitializeSQLCipher(pin);
		
		MyTables myTables = db.getTables();		
		
		// Delete main table
		myTables.delete("spl_diary_entry_has_picture", ""+id, "id");

		// Fetch diary entries
		new ControllerData(db,true);

		db.close();	
	}
	
	public void deleteFromDB() throws JsonSyntaxException, IOException  {
		
		// Initialize database
		PinShare myApp = PinShare.getInstance();
		String pin = myApp.getPin();
		Database db = new Database(context);
		db.InitializeSQLCipher(pin);
		
		MyTables myTables = db.getTables();
		String idString = ""+entry.getId(); // Cast to string
		
		
		// Delete main table
		myTables.delete("spl_diary_entry", idString, "id");
		
		// Delete picture table
		myTables.delete("spl_diary_entry_has_picture", idString, "entryID");
		
		// Delete mood table
		myTables.delete("spl_diary_entry_has_mood", idString, "entryID");

		// Fetch diary entries
		new ControllerData(db,true);

		db.close();
	}
}
