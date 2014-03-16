package de.hawlandshut.rueckfallprophylaxe.data;

public class DiaryEntryPicture {
	
	DiaryEntryPictureType type;
	String path;
	int id;

	
	public DiaryEntryPicture(DiaryEntryPictureType type, String path, int id) {
		super();
		this.type = type;
		this.path = path;
		this.id = id;
	}
	
	
	public void delete() {
		
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
