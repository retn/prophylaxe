package de.hawlandshut.rueckfallprophylaxe.net;

import java.util.List;

public class JsonAddress {

	private List<JsonAddressData> results;
	private String status;
	
	public JsonAddress() {
		
	}

	public List<JsonAddressData> getResults() {
		return results;
	}

	public void setResults(List<JsonAddressData> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
