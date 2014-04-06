package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only
 * contains variables and Getters/Setters.
 * 
 * @author Patrick
 * @see Data
 */
public class JsonSafetyThought {

	private int estID;
	private String text;

	public JsonSafetyThought() {

	}

	public int getEstID() {
		return estID;
	}

	public void setEstID(int estID) {
		this.estID = estID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
