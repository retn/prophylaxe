package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only
 * contains variables and Getters/Setters.
 * 
 * @author Patrick
 * @see Data
 */
public class JsonLimitRelapse {

	private int elrID;
	private String text;
	private String ecId_fk;

	public JsonLimitRelapse() {

	}

	public int getElrID() {
		return elrID;
	}

	public void setElrID(int elrID) {
		this.elrID = elrID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEcId_fk() {
		return ecId_fk;
	}

	public void setEcId_fk(String ecId_fk) {
		this.ecId_fk = ecId_fk;
	}

}
