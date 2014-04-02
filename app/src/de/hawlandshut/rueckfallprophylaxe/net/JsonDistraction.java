package de.hawlandshut.rueckfallprophylaxe.net;

/**
 * This class is only used for storing data from the JSON-String. It only
 * contains variables and Getters/Setters.
 * 
 * @author Patrick
 * @see Data
 */
public class JsonDistraction {

	private int distractionID;
	private String text;
	private String emotion_text;
	private String emotionID_fk;

	public JsonDistraction() {

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
