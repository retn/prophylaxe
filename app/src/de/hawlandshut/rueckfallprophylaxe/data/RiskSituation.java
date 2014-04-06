package de.hawlandshut.rueckfallprophylaxe.data;

public class RiskSituation {

	private int id;
	private String text;
	
	public RiskSituation(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}
	
}
