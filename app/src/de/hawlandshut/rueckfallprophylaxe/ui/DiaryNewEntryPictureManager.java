package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntryPicture;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntryPictureType;


/**
 * TODO: PS: Bitte kommentieren -> JavaDoc
 *
 */
/**
 * Managed das hinzufügen von Bildern eines (neues) Tagebucheintrages
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
	
	private void resetImageView(ImageViewWithContextMenuInfo img) {
		img.setImageResource(android.R.color.transparent);
		img.setVisibility(View.GONE);
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
		
		drawCurrentPictures();
	}
	
	
	public void addGalleryPicture(String picturePath) {
		
		// Add to image list
		DiaryEntryPicture newPic = new DiaryEntryPicture(DiaryEntryPictureType.NEW_GALLERY_PICTURE, picturePath, getNewID());
		pictures.add(newPic);
		addImageView(newPic);

	}
	
	private void addImageView(DiaryEntryPicture pic) {
		// Create & add imageView dynamically
		LinearLayout diaryImgsViewGroup = (LinearLayout) myActivity.findViewById(R.id.diaryImgViews); //the layout you set in `setContentView()`
		
		ImageViewWithContextMenuInfo image = new ImageViewWithContextMenuInfo(myActivity);
		image.setImageBitmap(getThumbnail(pic.getPath()));
		image.setTag(pic.getId());
		image.setId(pic.getId());
		image.setScaleType(ImageView.ScaleType.FIT_START);
		image.setPadding(0, 20, 15, 5);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 1.0f);
		
		diaryImgsViewGroup.addView(image, param);

	}
	
	public void addCameraPicture() {
		DiaryEntryPicture newPic = new DiaryEntryPicture(DiaryEntryPictureType.NEW_CAMERA_PICTURE, mCurrentPhotoPath, getNewID());
		pictures.add(newPic);
		addImageView(newPic);
	}
	
	public int getNewID() {
		lastPictureID++;
		return lastPictureID;
	}
	
	public Bitmap getThumbnail(String path){
	    Bitmap imgthumBitmap=null;
	     try    
	     {

	         final int THUMBNAIL_SIZE = 200;

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
	 * Shows thumbnails of all current pictures
	 */
	public void drawCurrentPictures() {
		// Show new pictures from gallery & camera
		for(DiaryEntryPicture pic: pictures){
	        ImageView imageView = (ImageView) myActivity.findViewById(pic.getId());
	        imageView.setImageBitmap(getThumbnail(pic.getPath()));
	        
	        // Register event listener
	        myActivity.registerForContextMenu((ImageView) myActivity.findViewById(pic.getId()));
	        
	        
	        Log.d("showCurrentPictures - Pic id: ", ""+pic.getIdString());
	     
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
