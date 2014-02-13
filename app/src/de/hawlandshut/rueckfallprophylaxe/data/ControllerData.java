package de.hawlandshut.rueckfallprophylaxe.data;

import java.util.HashMap;
import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.db.Database;

public class ControllerData {

	Database db;
	List<Maxim> maxims;
	HashMap<Integer, Emotion> emotions;
	List<HelpPerson> helpPeople;
	List<DiaryEntry> diaryEntries;
	
	public ControllerData(Database database) {
		db = database;
		//maxims = db.getMaxims();
		//emotions = db.getEmotions();
		//helpPeople = db.getHelpPeople();
		//diaryEntries = db.getDiaryEntries();
	}

	public List<Maxim> getMaxims() {
		return maxims;
	}

	public HashMap<Integer, Emotion> getEmotions() {
		return emotions;
	}

	public List<HelpPerson> getHelpPeople() {
		return helpPeople;
	}

	public List<DiaryEntry> getDiaryEntries() {
		return diaryEntries;
	}
	
	
}
