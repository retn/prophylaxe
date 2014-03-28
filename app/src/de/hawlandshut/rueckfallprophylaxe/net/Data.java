package de.hawlandshut.rueckfallprophylaxe.net;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class is only used for storing data from the JSON-String. It only contains
 * variables and Getters/Setters.
 * @author Patrick
 * @see JsonData
 */
public class Data {
	
	@SerializedName("emergency-case")
	private EmergencyCase emergencyCase;
	private List<Maxim> maxims;
	private List<Distractions> distractions;
	private Status status;
	
	public Data() {
		
	}

	public EmergencyCase getEmergencyCase() {
		return emergencyCase;
	}

	public void setEmergencyCase(EmergencyCase emergencyCase) {
		this.emergencyCase = emergencyCase;
	}

	public List<Maxim> getMaxims() {
		return maxims;
	}

	public void setMaxims(List<Maxim> maxims) {
		this.maxims = maxims;
	}

	public List<Distractions> getDistractions() {
		return distractions;
	}

	public void setDistractions(List<Distractions> distractions) {
		this.distractions = distractions;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
