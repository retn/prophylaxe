package de.hawlandshut.rueckfallprophylaxe.data.json;

public class Distractions {

	int distractionID;
	String text;
	String emotion_text;
	String emotionID_fk;
	
	public Distractions() {
		
	}

	public int getDistractionID() {
		return distractionID;
	}

	public void setDistractionID(int distractionID) {
		this.distractionID = distractionID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEmotion_text() {
		return emotion_text;
	}

	public void setEmotion_text(String emotion_text) {
		this.emotion_text = emotion_text;
	}

	public String getEmotionID_fk() {
		return emotionID_fk;
	}

	public void setEmotionID_fk(String emotionID_fk) {
		this.emotionID_fk = emotionID_fk;
	}
	
}
