package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only
 * contains variables and Getters/Setters.
 * 
 * @author Patrick
 * @see Data
 */
public class JsonSafetyAction {

	private int esaID;
	private String text;

	public JsonSafetyAction() {

	}

	public int getEsaID() {
		return esaID;
	}

	public void setEsaID(int esaID) {
		this.esaID = esaID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
