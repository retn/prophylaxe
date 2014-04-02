package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is used for storing the data collected from the JSON-String. An
 * object of this class is generated in {@link RequestJson#getData()} and that
 * object contains all data collected from the JSON-String.
 * 
 */
public class Data {

	private JsonData data;

	public Data() {

	}

	public JsonData getData() {
		return data;
	}

	public void setData(JsonData data) {
		this.data = data;
	}

}
