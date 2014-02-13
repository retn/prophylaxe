package de.hawlandshut.rueckfallprophylaxe.data;

public class Distraction {

	int id;
	int emotionId;

	public Distraction(int id, int emotionId) {
		this.id = id;
		this.emotionId = emotionId;
	}

	public int getId() {
		return id;
	}

	public int getEmotion() {
		return emotionId;
	}

}
