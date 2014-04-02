package de.hawlandshut.rueckfallprophylaxe.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hawlandshut.rueckfallprophylaxe.data.Media.Type;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.db.MyTables;

public class ControllerData {

	MyTables tables;
	List<Maxim> maxims;
	HashMap<Integer, Emotion> emotions;
	List<HelpPerson> helpPeople;
	List<DiaryEntry> diaryEntries;

	public ControllerData(Database database) {
		tables = database.getTables();
		maxims = fetchMaxims();
		emotions = fetchEmotions();
		helpPeople = fetchHelpPeople();
		diaryEntries = fetchDiaryEntries();
	}

	private List<Maxim> fetchMaxims() {
		List<String> ids = tables.query("spl_maxim", "maximID");
		List<String> texts = tables.query("spl_maxim", "text");
		List<Maxim> maxims = new ArrayList<Maxim>();
		for (int i = 0; i < ids.size(); i++) {
			Maxim maxim = new Maxim(Integer.parseInt(ids.get(i)), texts.get(i));
			maxims.add(maxim);
		}
		return maxims;
	}

	private HashMap<Integer, Emotion> fetchEmotions() {
		List<String> ids = tables.query("spl_emotion", "emotionID");
		List<String> texts = tables.query("spl_emotion", "emotion");
		HashMap<Integer, Emotion> emotions = new HashMap<Integer, Emotion>();
		for (int i = 0; i < ids.size(); i++) {
			List<Distraction> distractions = fetchDistractions(i);
			int id = Integer.parseInt(ids.get(i));
			Emotion emotion = new Emotion(id, texts.get(i), distractions);
			emotions.put(id, emotion);
		}
		return emotions;
	}

	private List<Distraction> fetchDistractions(int i2) {
		List<String> ids = tables.query("spl_distraction", "distractionID");
		// List<String> texts=tables.query("spl_distraction", "distraction");
		List<String> emotionids = tables.query("spl_distraction",
				"fk_distraction_emotion1"); // ?
		List<Distraction> distractions = new ArrayList<Distraction>();
		for (int i = 0; i < ids.size(); i++) {
			if (i == i2) {
				Distraction distraction = new Distraction(Integer.parseInt(ids
						.get(i)), Integer.parseInt(emotionids.get(i)));
				distractions.add(distraction);
			}
		}
		return distractions;
	}

	private List<HelpPerson> fetchHelpPeople() {
		List<String> ids = tables.query("spl_ec_help_person", "ehpID");
		List<String> names = tables.query("spl_ec_help_person", "name");
		List<String> phone_numbers = tables.query("spl_ec_help_person",
				"phone_number");
		List<HelpPerson> helpPersons = new ArrayList<HelpPerson>();
		for (int i = 0; i < ids.size(); i++) {
			HelpPerson person = new HelpPerson(Integer.parseInt(ids.get(i)),
					names.get(i), phone_numbers.get(i));
			helpPersons.add(person);
		}
		return helpPersons;
	}

	private List<DiaryEntry> fetchDiaryEntries() {
		List<DiaryEntry> diaryEntries = new ArrayList<DiaryEntry>();

		List<String> ids = tables.query("spl_diary_entry", "id");
		List<String> titles = tables.query("spl_diary_entry", "title");
		List<String> contens = tables.query("spl_diary_entry", "content");
		List<String> createds = tables.query("spl_diary_entry", "created");
		for (int i = 0; i < ids.size(); i++) {
			DiaryEntry entry;
			try {
				entry = new DiaryEntry(Integer.parseInt(ids.get(i)),
						titles.get(i), contens.get(i), new SimpleDateFormat(
								"MMMM d, yyyy", Locale.ENGLISH).parse(createds
								.get(i)), 0, null);
				diaryEntries.add(entry);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		fetchDiaryEntriesPicture(diaryEntries);
		fetchDiaryEntriesMood(diaryEntries);
		return diaryEntries;
	}

	private void fetchDiaryEntriesPicture(List<DiaryEntry> diaryEntries2) {
		List<String> ids = tables.query("spl_diary_entry_has_picture", "iD");
		List<String> entryids = tables.query("spl_diary_entry_has_picture",
				"entryID");

		for (DiaryEntry entry : diaryEntries2) {
			for (int i = 0; i < entryids.size(); i++) {
				if (entry.getId() == Integer.parseInt(entryids.get(i))) {
					Media media = new Media(Integer.parseInt(ids.get(i)),
							entry.getId(), Type.Image);
					entry.setMedia(new Media[] { media });
					;
				}
			}

		}
	}

	private void fetchDiaryEntriesMood(List<DiaryEntry> diaryEntries2) {
		List<String> entryids = tables.query("spl_diary_entry_has_mood",
				"entryID");
		List<String> emotionids = tables.query("spl_diary_entry_has_mood",
				"emotionID");

		for (DiaryEntry entry : diaryEntries2) {
			for (int i = 0; i < entryids.size(); i++) {
				if (entry.getId() == Integer.parseInt(entryids.get(i))) {
					entry.setEmotionId(Integer.parseInt(emotionids.get(i)));
				}
			}

		}

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
	
	public void updateContactPoints() {
		//TODO: write the function that updates ContactPoints 
		//		using: RequestJson.getContactPoints() and RequestJson.getContactPointsTimestamp
	}

}
