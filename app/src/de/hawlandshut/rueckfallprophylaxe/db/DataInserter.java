package de.hawlandshut.rueckfallprophylaxe.db;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import de.hawlandshut.rueckfallprophylaxe.net.Data;
import de.hawlandshut.rueckfallprophylaxe.net.JsonContactPoint;
import de.hawlandshut.rueckfallprophylaxe.net.JsonDistraction;
import de.hawlandshut.rueckfallprophylaxe.net.JsonEmergencyCase;
import de.hawlandshut.rueckfallprophylaxe.net.JsonHelpPerson;
import de.hawlandshut.rueckfallprophylaxe.net.JsonLimitRelapse;
import de.hawlandshut.rueckfallprophylaxe.net.JsonMaxim;
import de.hawlandshut.rueckfallprophylaxe.net.JsonRiskSituation;
import de.hawlandshut.rueckfallprophylaxe.net.JsonSafetyAction;
import de.hawlandshut.rueckfallprophylaxe.net.JsonSafetyThought;

public class DataInserter {

	private MyTables tables;

	public DataInserter(Database db) {
		tables = db.getTables();
	}

	public void insertData(Data data) {
		insertMaxims(data.getData().getMaxims());
		insertEmergencyCase(data.getData().getEmergencyCase());
		insertDistractions(data.getData().getDistractions());
	}

	private void insertMaxims(List<JsonMaxim> maxims) {
		for (JsonMaxim maxim : maxims) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("text", maxim.getText());

			tables.insert("spl_maxim", hashMap);
		}
	}

	private void insertEmergencyCase(JsonEmergencyCase emergencyCase) {
		List<JsonHelpPerson> helpPeople = emergencyCase.getHelp_persons_array();
		List<JsonLimitRelapse> limitRelapses = emergencyCase
				.getLimit_relapses_array();
		List<JsonRiskSituation> riskSituations = emergencyCase
				.getRisk_situations_array();
		List<JsonSafetyAction> safetyActions = emergencyCase
				.getSafety_actions_array();
		List<JsonSafetyThought> safetyThoughts = emergencyCase
				.getSafety_thoughts_array();

		for (JsonHelpPerson helpPerson : helpPeople) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("name", helpPerson.getName());
			hashMap.put("phone_number", helpPerson.getPhone_number());
			tables.insert("spl_ec_help_person", hashMap);
		}

		for (JsonLimitRelapse limitRelapse : limitRelapses) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("text", limitRelapse.getText());
			tables.insert("spl_ec_limit_relapse", hashMap);
		}

		for (JsonRiskSituation riskSituation : riskSituations) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("text", riskSituation.getText());
			tables.insert("spl_ec_risk_situation", hashMap);
		}

		for (JsonSafetyAction safetyAction : safetyActions) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("text", safetyAction.getText());
			tables.insert("spl_ec_safety_action", hashMap);
		}

		for (JsonSafetyThought safetyThought : safetyThoughts) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("text", safetyThought.getText());
			tables.insert("spl_ec_safety_thought", hashMap);
		}

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("addict_drughotline", emergencyCase.getAddict_drughotline());
		hashMap.put("prop_advice_centre", emergencyCase.getProp_advice());
		hashMap.put("my_therapist", emergencyCase.getMy_therapist());
		// "emergency_casecol" was kommt da rein?
		hashMap.put("risk_danger", emergencyCase.getRisk_danger());
		hashMap.put("risk_situation", emergencyCase.getRisk_situation());
		hashMap.put("risk_temptation", emergencyCase.getRisk_temptation());
		hashMap.put("temptation_thought", emergencyCase.getTemptation_thought());
		hashMap.put("temptation_thought_abstinence",
				emergencyCase.getTemptation_thought_abstinence());
		hashMap.put("temptation_behaviour",
				emergencyCase.getTemptation_behaviour());

		tables.insert("spl_emergency_case", hashMap);
	}

	private void insertDistractions(List<JsonDistraction> distractions) {
		HashMap<String, String> emos = new HashMap<String, String>();
		for (JsonDistraction distraction : distractions) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			emos.put(distraction.getEmotionID_fk(), distraction.getEmotion_text());
			hashMap.put("emotionID_fk", distraction.getEmotionID_fk());
			hashMap.put("text", distraction.getText());

			tables.insert("spl_distraction", hashMap);
		}
		
		Set<String> emo_keys = emos.keySet();
		for(String emo_id: emo_keys) {
			Log.d("emotions_db", emo_id + " ");
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("emotionID", emo_id);
			hashMap.put("emotion", emos.get(emo_id));
			
			tables.insert("spl_emotion", hashMap);
		}
	}

	public void updateContactPoints(List<JsonContactPoint> contactPoints) {
		for (JsonContactPoint contactPoint : contactPoints) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("name", contactPoint.getName());
			hashMap.put("street", contactPoint.getStreet());
			hashMap.put("town", contactPoint.getTown());
			hashMap.put("plz", contactPoint.getPlz());
			hashMap.put("phone_number", contactPoint.getPhone_number());
			hashMap.put("email", contactPoint.getEmail());

			tables.insert("spl_place_to_go", hashMap);
		}
	}

}
