package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only
 * contains variables and Getters/Setters.
 * 
 * @author Patrick
 * @see Data
 */
public class JsonMaxim {

	private int maximID;
	private String text;

	public JsonMaxim() {

	}

	public int getMaximID() {
		return maximID;
	}

	public void setMaximID(int maximID) {
		this.maximID = maximID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
