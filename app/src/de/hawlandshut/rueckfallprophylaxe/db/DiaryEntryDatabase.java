package de.hawlandshut.rueckfallprophylaxe.db;

import java.io.IOException;

import com.google.gson.JsonSyntaxException;

import android.content.Context;
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
		
		//ControllerData cd = new ControllerData(db);
		//cd.fetchDiaryEntries();
		
		// Fetch diary entries
		ControllerData cd = new ControllerData(db,true);
		
		//db.close();
		

	}
}