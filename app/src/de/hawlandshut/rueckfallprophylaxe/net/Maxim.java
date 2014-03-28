package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only contains
 * variables and Getters/Setters.
 * @author Patrick
 * @see JsonData
 */
public class Maxim {
	
	private int maximID;
	private String text;
	
	public Maxim() {
		
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
