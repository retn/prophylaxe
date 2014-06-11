package de.hawlandshut.rueckfallprophylaxe.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DiaryEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String title;
	String content;
	Date created;
	int emotionId; // 0 if no Emotion was given
	List<Media> media=new ArrayList<Media>();

	public DiaryEntry(int id, String title, String content, Date created,
			int emotionId, List<Media> media) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.created = created;
		this.emotionId = emotionId;
		this.media = media;
	}


	public DiaryEntry(int id, String title, String content, Date created,
			List<Media> media) {
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
	
	@Override
	public String toString() {
		return "DiaryEntry [id=" + id + ", title=" + title + ", content="
				+ content + ", created=" + created + ", emotionId=" + emotionId
				+ ", media=" + media + "]";
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

	public List<Media> getMedia() {
		return media;
	}



	public void setEmotionId(int id) {
		this.emotionId=id;
		
	}

}
