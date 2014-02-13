package de.hawlandshut.rueckfallprophylaxe.data;

public class Media {

	enum Type {Sound, Image, Video};
	
	int id;
	int entryId;
	Type type;
	
	public Media(int id, int entryId, Type type) {
		this.id = id;
		this.entryId = entryId;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public int getEntryId() {
		return entryId;
	}
	public Type getType() {
		return type;
	}
	
	
	
}
