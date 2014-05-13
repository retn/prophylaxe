package de.hawlandshut.rueckfallprophylaxe.data;

public class Distraction {

	int id;
	int emotionId;
	String text;

	public Distraction(int id, int emotionId, String text) {
		this.id = id;
		this.emotionId = emotionId;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public int getEmotion() {
		return emotionId;
		
	}
	
	public String getText(){
		return text;
	}
	

}
