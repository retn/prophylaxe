package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

/*
 * Managed das hinzufügen von Bildern eines (neues) Tagebucheintrages
 */
public class DiaryNewEntry_pictureManager {
	private ArrayList<String> newGalleryPicturesPathes = new ArrayList<String>();
	private ArrayList<String> newCameraPicturesPathes = new ArrayList<String>();
	String mCurrentPhotoPath; // Current camera photo path
	private static int MAX_PICTURES_ALLOWED = 4;
	
	
	public void addGalleryPicture(String picturePath) {
		newGalleryPicturesPathes.add(picturePath); // Add to array
	}
	
	public void addCameraPicture() {
		newCameraPicturesPathes.add(mCurrentPhotoPath); // Add to array
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
		
		// Count new Gallery pictures
		for(@SuppressWarnings("unused") String picturePath: newGalleryPicturesPathes){
	        i++;
		}
		
		// Count new camera pictures
		for(@SuppressWarnings("unused") String picturePath: newCameraPicturesPathes){
	        i++;
		}
		
		// TODO: Count existing database pictures
		
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
		
		// Allow max 4 pictures
		
		// Show existing pictures
		
		// Show new pictures from camera (newGalleryPicturesPathes)
		for(String picturePath: newGalleryPicturesPathes){
	        ImageView imageView = getThumbnailIDAttribute(i, activity);
	        imageView.setImageBitmap(getThumbnail(picturePath));
	        i++;
		}
		
		// Show new pictures from gallery
		for(String picturePath: newCameraPicturesPathes){
	        ImageView imageView = getThumbnailIDAttribute(i, activity);
	        imageView.setImageBitmap(getThumbnail(picturePath));
	        i++;
		}
	}
	
	public boolean newPicturesAllowed() {
		return (MAX_PICTURES_ALLOWED > countPictures());
	}

	public static int getMAX_PICTURES_ALLOWED() {
		return MAX_PICTURES_ALLOWED;
	}
	
	public File createImageFile(Activity activity) throws IOException {
	    // Create an image file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
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
