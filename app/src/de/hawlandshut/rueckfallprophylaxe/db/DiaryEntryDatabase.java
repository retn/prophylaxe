package de.hawlandshut.rueckfallprophylaxe.db;

import android.content.Context;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;
import de.hawlandshut.rueckfallprophylaxe.ui.PinShare;

public class DiaryEntryDatabase {

	DiaryEntry entry;
	Context context;
	
	public DiaryEntryDatabase(DiaryEntry entry, Context context) {
		this.entry = entry;
		this.context = context;
	}
	
	public void deleteFromDB() {
		PinShare myApp = PinShare.getInstance();
		String pin = myApp.getPin();
		Database db = new Database(context);
		db.InitializeSQLCipher(pin);
		MyTables myTables = db.getTables();
		String idString = ""+entry.getId();
		
		// Delete main table
		myTables.delete("spl_diary_entry", idString, "id");
		
		// Delete picture table
		myTables.delete("spl_diary_entry_has_picture", idString, "entryID");
		
		// Delete mood table
		myTables.delete("spl_diary_entry_has_mood", idString, "entryID");
		
		db.close();
	}
}
