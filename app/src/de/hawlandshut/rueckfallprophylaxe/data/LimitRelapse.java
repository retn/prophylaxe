package de.hawlandshut.rueckfallprophylaxe.data;

public class LimitRelapse {

	private int id;
	private String text;

	public LimitRelapse(int id, String text) {
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
