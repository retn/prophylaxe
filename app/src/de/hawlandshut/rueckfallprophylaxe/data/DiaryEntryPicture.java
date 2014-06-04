package de.hawlandshut.rueckfallprophylaxe.data;

import android.graphics.Bitmap;

public class DiaryEntryPicture {
	
	DiaryEntryPictureType type;
	String path;
	int id;
	Bitmap img;

	public DiaryEntryPicture(DiaryEntryPictureType type, String path, int id) {
		super();
		this.type = type;
		this.path = path;
		this.id = id;
	}
	
	public DiaryEntryPicture(DiaryEntryPictureType type, Bitmap img, int id) {
		super();
		this.type = type;
		this.img = img;
		this.id = id;
	}
	
	public Bitmap getImg() {
		return img;
	}

	public void setImg(Bitmap img) {
		this.img = img;
	}

	public DiaryEntryPictureType getType() {
		return type;
	}
		
	public void setType(DiaryEntryPictureType type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getId() {
		return id;
	}
	
	public String getIdString() {
		return ""+id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
		
}
