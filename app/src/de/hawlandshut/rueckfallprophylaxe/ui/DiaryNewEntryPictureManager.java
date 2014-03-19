package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntryPicture;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntryPictureType;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;


/**
 * TODO: PS: Bitte kommentieren -> JavaDoc
 *
 */
/*
 * Managed das hinzuf�gen von Bildern eines (neues) Tagebucheintrages
 */
public class DiaryNewEntryPictureManager {
	private Activity myActivity;
	private ArrayList<DiaryEntryPicture> pictures = new ArrayList<DiaryEntryPicture>();
	private int lastPictureID = 0;

	String mCurrentPhotoPath; // Current camera photo path
	private static int MAX_PICTURES_ALLOWED = 4;
	
	DiaryNewEntryPictureManager(Activity myActivity) {
		this.myActivity = myActivity;
	}
	
	
	public void addEventListenerOnPicture() {
		myActivity.registerForContextMenu((ImageView) myActivity.findViewById(R.id.imgView1));
		myActivity.registerForContextMenu((ImageView) myActivity.findViewById(R.id.imgView2));
		myActivity.registerForContextMenu((ImageView) myActivity.findViewById(R.id.imgView3));
		myActivity.registerForContextMenu((ImageView) myActivity.findViewById(R.id.imgView4));
	}
	
	private void resetImageView(ImageViewWithContextMenuInfo img) {
		img.setImageResource(android.R.color.transparent);
	}
	
	public void removePicture(ImageViewWithContextMenuInfo img) throws Exception {

		// Newly added gallery picture:
		for(DiaryEntryPicture pic: pictures) {
			if (pic.getType() == DiaryEntryPictureType.NEW_GALLERY_PICTURE) {

		        if (img.getTag().toString().equals(pic.getIdString())) {
		        	Log.d("removePicture", "Equal");
		        	resetImageView(img); // Hide
		        	pictures.remove(pic); // Remove from list
		        }
		        
			}
		}
		
		// Newly added camera picture:
		// TODO: delete instantly from disk
		// TODO: remove from list
		
		// Existing picture:
		// TODO: add to trash
		
		
		
	}
	
	public void addGalleryPicture(String picturePath) {
		DiaryEntryPicture newPic = new DiaryEntryPicture(DiaryEntryPictureType.NEW_GALLERY_PICTURE, picturePath, getNewID());
		pictures.add(newPic);
		
		addEventListenerOnPicture();
	}
	
	public void addCameraPicture() {
		DiaryEntryPicture newPic = new DiaryEntryPicture(DiaryEntryPictureType.NEW_CAMERA_PICTURE, mCurrentPhotoPath, getNewID());
		pictures.add(newPic);
	}
	
	public int getNewID() {
		lastPictureID++;
		return lastPictureID;
	}
	
	public Bitmap getThumbnail(String path){
	    Bitmap imgthumBitmap=null;
	     try    
	     {

	         final int THUMBNAIL_SIZE = 300;

	         FileInputStream fis = new FileInputStream(path);
	          imgthumBitmap = BitmapFactory.decodeStream(fis);

	         imgthumBitmap = Bitmap.createScaledBitmap(imgthumBitmap,
	                THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

	        ByteArrayOutputStream bytearroutstream = new ByteArrayOutputStream(); 
	        imgthumBitmap.compress(Bitmap.CompressFormat.JPEG, 100,bytearroutstream);


	     }
	     catch(Exception ex) {

	     }
	     return imgthumBitmap;
	}
	
	public int countPictures() {
		int i = 0;
		
		for(@SuppressWarnings("unused") DiaryEntryPicture item: pictures){
	        i++;
		}
		
		// TODO: Count & subtract removed database pictures
		
		return i;
	}
	
	/*
	 * Found no other way to dynamically create id attributes
	 */
	private ImageView getThumbnailIDAttribute(int i, Activity activity) {
		if (i == 1)
			return (ImageView) activity.findViewById(R.id.imgView1);
		
		if (i == 2)
			return (ImageView) activity.findViewById(R.id.imgView2);
		
		if (i == 3)
			return (ImageView) activity.findViewById(R.id.imgView3);
		
		if (i == 4)
			return (ImageView) activity.findViewById(R.id.imgView4);
		
		return null;
	}
	
	/*
	 * Shows thumbnails of all current pictures
	 */
	public void showCurrentPictures(Activity activity) {
		int i = 1;
		
		// Show new pictures from gallery & camera
		for(DiaryEntryPicture pic: pictures){
	        ImageView imageView = getThumbnailIDAttribute(i, activity);
	        imageView.setImageBitmap(getThumbnail(pic.getPath()));
	        Log.d("showCurrentPictures - Pic id: ", ""+pic.getIdString());
	        imageView.setTag(pic.getId()); // id
	        i++;
		}
		
		// TODO: Show existing pictures from database which were not moved to trash
		
	}
	
	
	public boolean newPicturesAllowed() {
		return (MAX_PICTURES_ALLOWED > countPictures());
	}

	public static int getMAX_PICTURES_ALLOWED() {
		return MAX_PICTURES_ALLOWED;
	}
	
	public File createImageFile(Activity activity) throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
	    String imageFileName = "JPEG_" + timeStamp + "_";
	    File storageDir = activity.getExternalFilesDir(
	            Environment.DIRECTORY_PICTURES);
	    
	    
	    File image = File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        storageDir      /* directory */
	    );

	    // Save a file: path for use with ACTION_VIEW intents
	    mCurrentPhotoPath = image.getAbsolutePath();
	    return image;
	}

}
