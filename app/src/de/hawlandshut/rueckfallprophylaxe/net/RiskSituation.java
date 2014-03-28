package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only contains
 * variables and Getters/Setters.
 * @author Patrick
 * @see JsonData
 */
public class RiskSituation {

	private int ersID;
	private String text;
	
	public RiskSituation() {
		
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
