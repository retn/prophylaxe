package de.hawlandshut.rueckfallprophylaxe.data;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.util.Log;

import com.google.gson.JsonSyntaxException;

import android.util.Log;
import de.hawlandshut.rueckfallprophylaxe.data.Media.Type;
import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.db.MyTables;
import de.hawlandshut.rueckfallprophylaxe.net.JsonAddress;
import de.hawlandshut.rueckfallprophylaxe.net.RequestJson;

public class ControllerData {

	private MyTables tables;
	private static List<Maxim> maxims;
	private static HashMap<Integer, Emotion> emotions;
	private static List<HelpPerson> helpPeople;
	private static List<DiaryEntry> diaryEntries;
	private static List<PlaceToGo> placesToGo;
	private static List<LimitRelapse> limitRelapse;
	private static List<RiskSituation> riskSituation;
	private static List<SafetyAction> safetyAction;
	private static List<SafetyThought> safetyThought;
	private static List<EmergencyCase> emergencyCase;

	public ControllerData(Database database) throws JsonSyntaxException,
			IOException {
		tables = database.getTables();
		maxims = fetchMaxims();
		emotions = fetchEmotions();
		helpPeople = fetchHelpPeople();
		diaryEntries = fetchDiaryEntries();
		placesToGo = fetchPlacesToGo();
		limitRelapse=fetchLimitRelapse();
		riskSituation=fetchRiskSituation();
		safetyAction=fetchSafetyAction();
		safetyThought=fetchSafetyThought();
		emergencyCase=fetchEmergencyCase();
	}

	private List<LimitRelapse> fetchLimitRelapse() {
		List<String> ids = tables.query("spl_ec_limit_relapse", "elrID");
		List<String> texts = tables.query("spl_ec_limit_relapse", "text");
		List<LimitRelapse> limits = new ArrayList<LimitRelapse>();
		for (int i = 0; i < ids.size(); i++) {
			LimitRelapse limit = new LimitRelapse(Integer.parseInt(ids.get(i)), texts.get(i));
			limits.add(limit);
		}
		return limits;
	}

	private List<RiskSituation> fetchRiskSituation() {
		List<String> ids = tables.query("spl_ec_risk_situation", "ersID");
		List<String> texts = tables.query("spl_ec_risk_situation", "text");
		List<RiskSituation> risks = new ArrayList<RiskSituation>();
		for (int i = 0; i < ids.size(); i++) {
			RiskSituation risk = new RiskSituation(Integer.parseInt(ids.get(i)), texts.get(i));
			risks.add(risk);
		}
		return risks;
	}

	private List<SafetyAction> fetchSafetyAction() {
		List<String> ids = tables.query("spl_ec_safety_action", "esaID");
		List<String> texts = tables.query("spl_ec_safety_action", "text");
		List<SafetyAction> actions = new ArrayList<SafetyAction>();
		for (int i = 0; i < ids.size(); i++) {
			SafetyAction action = new SafetyAction(Integer.parseInt(ids.get(i)), texts.get(i));
			actions.add(action);
		}
		return actions;
	}

	private List<SafetyThought> fetchSafetyThought() {
		List<String> ids = tables.query("spl_ec_safety_thought", "estID");
		List<String> texts = tables.query("spl_ec_safety_thought", "text");
		List<SafetyThought> thoughts = new ArrayList<SafetyThought>();
		for (int i = 0; i < ids.size(); i++) {
			SafetyThought thought = new SafetyThought(Integer.parseInt(ids.get(i)), texts.get(i));
			thoughts.add(thought);
		}
		return thoughts;
	}

	private List<EmergencyCase> fetchEmergencyCase() {
		List<String> ids = tables.query("spl_emergency_case", "ecID");
		List<String> addictDrugholtine = tables.query("spl_emergency_case", "addict_drughotline");
		List<String> propAdvice = tables.query("spl_emergency_case", "prop_advice_centre");
		List<String> myTherapist = tables.query("spl_emergency_case", "my_therapist");
		List<String> emergencyCasecol = tables.query("spl_emergency_case", "emergency_casecol");
		List<String> riskDanger = tables.query("spl_emergency_case", "risk_danger");
		List<String> riskSituation = tables.query("spl_emergency_case", "risk_situation");
		List<String> riskTemptation = tables.query("spl_emergency_case", "risk_temptation");
		List<String> temptationThought = tables.query("spl_emergency_case", "temptation_thought");
		List<String> temptationThoughtAbstinence = tables.query("spl_emergency_case", "temptation_thought_abstinence");
		List<String> temptationBehaviour = tables.query("spl_emergency_case", "temptation_behaviour");
		List<EmergencyCase> cases = new ArrayList<EmergencyCase>();
		for (int i = 0; i < ids.size(); i++) {
			EmergencyCase case_ = new EmergencyCase(Integer.parseInt(ids.get(i)), addictDrugholtine.get(i),propAdvice.get(i),
					myTherapist.get(i),emergencyCasecol.get(i),riskDanger.get(i),riskSituation.get(i),
					riskTemptation.get(i),temptationThought.get(i),temptationThoughtAbstinence.get(i),temptationBehaviour.get(i));
			cases.add(case_);
		}
		return cases;
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
			int id = Integer.parseInt(ids.get(i));
			List<Distraction> distractions = fetchDistractions(id);
			Emotion emotion = new Emotion(id, texts.get(i), distractions);
			emotions.put(id, emotion);
		}
		return emotions;
	}

	private List<Distraction> fetchDistractions(int emotion_id) {
		List<String> ids = tables.query("spl_distraction", "distractionID");
		List<String> texts=tables.query("spl_distraction", "text");
		List<String> emotionids = tables.query("spl_distraction", "emotionID_fk"); // ?
		List<Distraction> distractions = new ArrayList<Distraction>();
		for (int i = 0; i < ids.size(); i++) {
			if (Integer.parseInt(emotionids.get(i)) == emotion_id) {
				Distraction distraction = new Distraction(Integer.parseInt(ids
						.get(i)), Integer.parseInt(emotionids.get(i)),texts.get(i));
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
		
		Log.d("fetchDiaryEntries ", "Lade Einträge");
		
		for (int i = 0; i < ids.size(); i++) {
			DiaryEntry entry;
			try {
				//Log.d("fetchDiaryEntries ", "Lade Eintrag mit ID: "+ids.get(i));
				Log.d("fetchDiaryEntries ", "Lade Eintrag mit Titel: "+titles.get(i));
				//Log.d("fetchDiaryEntries ", "Lade Eintrag mit Datum: "+createds.get(i));
				
				entry = new DiaryEntry(Integer.parseInt(ids.get(i)),
						titles.get(i), contens.get(i), new SimpleDateFormat(
								"MMMM d, yyyy", Locale.ENGLISH).parse(createds
								.get(i)), 0, null);
				Log.d("fetchDiaryEntries ", "DiaryEntry Objekt erstellt");
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
		
		Log.d("fetchDiaryEntries ", diaryEntries.size()+" Einträge geladen");
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

	private List<PlaceToGo> fetchPlacesToGo() throws JsonSyntaxException,
			IOException {
		List<PlaceToGo> list = new ArrayList<PlaceToGo>();

		List<String> ptgids = tables.query("spl_place_to_go", "ptgID");
		List<String> names = tables.query("spl_place_to_go", "name");
		List<String> streets = tables.query("spl_place_to_go", "street");
		List<String> plzs = tables.query("spl_place_to_go", "plz");
		List<String> towns = tables.query("spl_place_to_go", "town");
		List<String> phoneNumbers = tables.query("spl_place_to_go",
				"phone_number");
		List<String> emails = tables.query("spl_place_to_go", "email");

		for (int i = 0; i < ptgids.size(); i++) {
			RequestJson rj = new RequestJson();
			String search = URLEncoder
					.encode(streets.get(i).replace("str.", "straße") + " "
							+ plzs.get(i) + " " + towns.get(i) + " Deutschland",
							"UTF-8");

			JsonAddress address = rj.getAddress(search);

			double lat = address.getResults().get(0).getGeometry()
					.getLocation().getLat();
			double lng = address.getResults().get(0).getGeometry()
					.getLocation().getLng();

			list.add(new PlaceToGo(Integer.parseInt(ptgids.get(i)), names
					.get(i), streets.get(i), plzs.get(i), towns.get(i),
					phoneNumbers.get(i), emails.get(i), lat, lng));
		}
		return list;
	}

	public static List<Maxim> getMaxims() {
		return maxims;
	}

	public static HashMap<Integer, Emotion> getEmotions() {
		return emotions;
	}

	public static List<HelpPerson> getHelpPeople() {
		return helpPeople;
	}

	public static List<DiaryEntry> getDiaryEntries() {
		return diaryEntries;
	}

	public static List<PlaceToGo> getPlacesToGo() {
		return placesToGo;
	}

	public static List<LimitRelapse> getLimitrelapse() {
		return limitRelapse;
	}

	public static void setLimitrelapse(List<LimitRelapse> limitrelapse) {
		ControllerData.limitRelapse = limitrelapse;
	}

	public static List<RiskSituation> getRiskSituation() {
		return riskSituation;
	}

	public static void setRiskSituation(List<RiskSituation> riskSituation) {
		ControllerData.riskSituation = riskSituation;
	}

	public static List<SafetyAction> getSafetyAction() {
		return safetyAction;
	}

	public static void setSafetyAction(List<SafetyAction> safetyAction) {
		ControllerData.safetyAction = safetyAction;
	}

	public static List<SafetyThought> getSafetyThought() {
		return safetyThought;
	}

	public static void setSafetyThought(List<SafetyThought> safetyThought) {
		ControllerData.safetyThought = safetyThought;
	}

	public static List<EmergencyCase> getEmergencyCase() {
		return emergencyCase;
	}

	public static void setEmergencyCase(List<EmergencyCase> emergencyCase) {
		ControllerData.emergencyCase = emergencyCase;
	}

}
