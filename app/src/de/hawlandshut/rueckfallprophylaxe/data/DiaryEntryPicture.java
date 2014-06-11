package de.hawlandshut.rueckfallprophylaxe.data;

import android.graphics.Bitmap;

public class DiaryEntryPicture {
	
	DiaryEntryPictureType type;
	String path;
	int id;
	int databaseID;
	Bitmap img;

	public DiaryEntryPicture(DiaryEntryPictureType type, String path, int id) {
		super();
		this.type = type;
		this.path = path;
		this.id = id;
	}
	
	/**
	 * Constructor for database images
	 * @param type
	 * @param img
	 * @param id
	 * @param databaseID
	 */
	public DiaryEntryPicture(DiaryEntryPictureType type, Bitmap img, int id, int databaseID) {
		super();
		this.type = type;
		this.img = img;
		this.id = id;
		this.databaseID = databaseID;
	}
	
	public int getDatabaseID() {
		return databaseID;
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
