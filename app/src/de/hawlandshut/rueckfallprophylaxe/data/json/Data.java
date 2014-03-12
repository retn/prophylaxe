package de.hawlandshut.rueckfallprophylaxe.data.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Data {
	
	@SerializedName("emergency-case")
	EmergencyCase emergencyCase;
	List<Maxim> maxims;
	List<Distractions> distractions;
	Status status;
	
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
