package de.hawlandshut.rueckfallprophylaxe.data.json;

public class LimitRelapse {

	int elrID;
	String text;
	String ecId_fk;
	
	public LimitRelapse() {
		
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
