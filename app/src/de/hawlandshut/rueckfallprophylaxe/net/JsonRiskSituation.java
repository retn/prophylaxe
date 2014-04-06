package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only
 * contains variables and Getters/Setters.
 * 
 * @author Patrick
 * @see Data
 */
public class JsonRiskSituation {

	private int ersID;
	private String text;

	public JsonRiskSituation() {

	}

	public int getErsID() {
		return ersID;
	}

	public void setErsID(int ersID) {
		this.ersID = ersID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
