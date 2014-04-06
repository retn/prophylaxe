package de.hawlandshut.rueckfallprophylaxe.data;

import java.util.Date;

public class DiaryEntry {

	int id;
	String title;
	String content;
	Date created;
	int emotionId; // 0 if no Emotion was given
	Media[] media;

	public DiaryEntry(int id, String title, String content, Date created,
			int emotionId, Media[] media) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.created = created;
		this.emotionId = emotionId;
		this.media = media;
	}

	public DiaryEntry(int id, String title, String content, Date created,
			Media[] media) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.created = created;
		this.emotionId = 0;
		this.media = media;
	}

	public DiaryEntry(int id, String title, String content, Date created,
			int emotionId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.created = created;
		this.emotionId = emotionId;
	}

	public DiaryEntry(int id, String title, String content, Date created) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.created = created;
		this.emotionId = 0;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Date getCreated() {
		return created;
	}

	public int getEmotionId() {
		return emotionId;
	}

	public Media[] getMedia() {
		return media;
	}

	public void setMedia(Media[] medias) {
		this.media=medias;
		
	}

	public void setEmotionId(int id) {
		this.id=id;
		
	}
}
