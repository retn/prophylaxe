package de.hawlandshut.rueckfallprophylaxe.net;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class is only used for storing data from the JSON-String. It only contains
 * variables and Getters/Setters.
 * @author Patrick
 * @see Data
 */
public class JsonData {
	
	@SerializedName("emergency-case")
	private JsonEmergencyCase emergencyCase;
	private List<JsonMaxim> maxims;
	private List<JsonDistraction> distractions;
	private JsonStatus status;
	
	public JsonData() {
		
	}

	public JsonEmergencyCase getEmergencyCase() {
		return emergencyCase;
	}

	public void setEmergencyCase(JsonEmergencyCase emergencyCase) {
		this.emergencyCase = emergencyCase;
	}

	public List<JsonMaxim> getMaxims() {
		return maxims;
	}

	public void setMaxims(List<JsonMaxim> maxims) {
		this.maxims = maxims;
	}

	public List<JsonDistraction> getDistractions() {
		return distractions;
	}

	public void setDistractions(List<JsonDistraction> distractions) {
		this.distractions = distractions;
	}

	public JsonStatus getStatus() {
		return status;
	}

	public void setStatus(JsonStatus status) {
		this.status = status;
	}
	
}
