package de.hawlandshut.rueckfallprophylaxe.data;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Media implements Serializable {

	private static final long serialVersionUID = 1L;

	enum Type {Sound, Image, Video};
	
	int id;
	int entryId;
	Type type;
	byte[] blob;
	
	public Media(int id, int entryId, Type type, byte[] blob) {
		this.id = id;
		this.entryId = entryId;
		this.type = type;
		this.blob = blob;
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
	
	/**
	 * Returns Bitmap of blob
	 * @return
	 * @throws Exception
	 */
	public Bitmap getImage() throws Exception {
		if (type != Type.Image)
			throw new Exception("Cannot get image");
		
		ByteArrayInputStream imageStream = new ByteArrayInputStream(blob);
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		return theImage;
	}
	
	
}
