package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
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
import de.hawlandshut.rueckfallprophylaxe.data.Media;

/**
 * Managed das hinzufügen von Bildern eines (neues) Tagebucheintrages
 */
public class DiaryNewEntryPictureManager {
	private Activity myActivity;
	private ArrayList<DiaryEntryPicture> pictures = new ArrayList<DiaryEntryPicture>();
	private ArrayList<DiaryEntryPicture> trash = new ArrayList<DiaryEntryPicture>();
	private int lastPictureID = 0;

	String mCurrentPhotoPath; // Current camera photo path
	private static int MAX_PICTURES_ALLOWED = 4;
	
	DiaryNewEntryPictureManager(Activity myActivity) {
		this.myActivity = myActivity;
	}
	
	/**
	 * Returns an ArrayList<byte[]> of all newly added pictures
	 * @return
	 */
	public ArrayList<byte[]> getNewPicturesAsBlob() {
		ArrayList<byte[]> newPics = new ArrayList<byte[]>();
		
		for (DiaryEntryPicture pic:pictures) {
			if (pic.getType() == DiaryEntryPictureType.NEW_CAMERA_PICTURE || pic.getType() == DiaryEntryPictureType.NEW_GALLERY_PICTURE) {
				// Bitmap laden
				String path = pic.getPath();
				
				try {
					FileInputStream fis = new FileInputStream(path);
					Bitmap imgBitmap = BitmapFactory.decodeStream(fis);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					
					// Resize to max 800px
					int bwidth=imgBitmap.getWidth();
					int bheight=imgBitmap.getHeight();
					int swidth;
					if (bwidth >= 800) {
						swidth=800;
					} else {
						swidth=bwidth;
					}
					int new_width=swidth;
					int new_height = (int) Math.floor((double) bheight *( (double) new_width / (double) bwidth));
					Log.d("PictureConvert", "width: "+new_width+", height: "+new_height);
					Bitmap newbitMap = Bitmap.createScaledBitmap(imgBitmap,new_width,new_height, true);
					
					
					newbitMap.compress(Bitmap.CompressFormat.PNG, 50, bos);
					byte[] bArray = bos.toByteArray();
					Log.d("PictureConvert", "length: "+bArray.length);
					newPics.add(bArray);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				
			}
		}
		return newPics;
	}
	
	/**
	 * Resets an imageView
	 * @param img
	 */
	private void resetImageView(ImageViewWithContextMenuInfo img) {
		img.setImageResource(android.R.color.transparent);
		img.setVisibility(View.GONE);
	}
	
	/**
	 * Deletes a picture from sd card
	 * @param pic
	 * @return
	 */
	public boolean deletePicFromDisk(DiaryEntryPicture pic) {
    	File file = new File(pic.getPath());
    	return file.delete();	
	}
	
	/**
	 * Removes the picture from the newDiaryEntryActivity
	 * - new camera pictures are deleted from disk
	 * - new gallery pictures are only removed from the picture list
	 * - existing database pictures are only moved to the trash list. it should be deleted after user saved the diary entry
	 * @param img
	 * @throws Exception
	 */
	public void removePicture(ImageViewWithContextMenuInfo img) throws Exception {

		
		for(DiaryEntryPicture pic: pictures) {
			
			// Newly added gallery picture:
			if (pic.getType() == DiaryEntryPictureType.NEW_GALLERY_PICTURE) {
		        if (img.getTag().toString().equals(pic.getIdString())) {
		        	Log.d("removePicture", "new gallery picture");
		        	resetImageView(img); // Hide
		        	pictures.remove(pic); // Remove from list
		        }	        
			}
			
			// Newly added camera picture:
			if (pic.getType() == DiaryEntryPictureType.NEW_CAMERA_PICTURE) {
		        if (img.getTag().toString().equals(pic.getIdString())) {
		        	Log.d("removePicture", "new camera picture");
		        	resetImageView(img); // Hide
		        	pictures.remove(pic); // Remove from list
		        	
		        	// Delete instantly from disk
		        	if (deletePicFromDisk(pic) == true) {
		        		Log.d("RemovePicture","Deleted from disk");
		        	} else {
		        		Log.d("RemovePicture", "Not deleted from disk");
		        	}
		        }
			}
			
			// Existing picture
			if (pic.getType() == DiaryEntryPictureType.EXISTING_DATABASE_PICTURE) {
		        if (img.getTag().toString().equals(pic.getIdString())) {
		        	Log.d("removePicture", "Equal");
		        	resetImageView(img); // Hide
		        	pictures.remove(pic); // Remove from list
		        	trash.add(pic); // Add to trash
		        }
			}
			
		}
		
		
		drawCurrentPictures();
	}
	
	/**
	 * Adds picture from the Android gallery
	 * @param picturePath
	 */
	public void addGalleryPicture(String picturePath) {
		
		// Add to image list
		DiaryEntryPicture newPic = new DiaryEntryPicture(DiaryEntryPictureType.NEW_GALLERY_PICTURE, picturePath, getNewID());
		pictures.add(newPic);
		addImageView(newPic);
		drawCurrentPictures();
	}
	
	/**
	 * Adds existing picture from the database
	 * @param media
	 * @throws Exception
	 */
	public void addExistingPicture(Media media) throws Exception {
		
		Bitmap myImage = media.getImage();
		
		// Add to image list
		int newID = getNewID();
		Log.d("showCurrentPictures", "Add new database pic "+newID);
		DiaryEntryPicture newPic = new DiaryEntryPicture(DiaryEntryPictureType.EXISTING_DATABASE_PICTURE, myImage, newID, media.getId());
		pictures.add(newPic);
		addImageView(newPic);
	}
	
	
	/**
	 * Returns the trash with all recently removed database pictures
	 * @return
	 */
	public ArrayList<DiaryEntryPicture> getTrash() {
		return trash;
	}

	
	private void addImageView(DiaryEntryPicture pic) {
		// Create & add imageView dynamically
		LinearLayout diaryImgsViewGroup = (LinearLayout) myActivity.findViewById(R.id.diaryImgViews); //the layout you set in `setContentView()`
		
		ImageViewWithContextMenuInfo image = new ImageViewWithContextMenuInfo(myActivity);
		
		
		if (pic.getType() == DiaryEntryPictureType.EXISTING_DATABASE_PICTURE) {
			image.setImageBitmap(getThumbnailImg(pic.getImg()));
		} else {
			image.setImageBitmap(getThumbnail(pic.getPath()));
		}
			
		image.setTag(pic.getId());
		image.setId(pic.getId());
		image.setScaleType(ImageView.ScaleType.FIT_START);
		image.setPadding(0, 20, 15, 5);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT, 1.0f);
		
		diaryImgsViewGroup.addView(image, param);
		
		myActivity.registerForContextMenu((ImageView) myActivity.findViewById(pic.getId()));

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
	
	public Bitmap getThumbnailImg(Bitmap img){
	    Bitmap imgthumBitmap=null;
	     try    
	     {

	         final int THUMBNAIL_SIZE = 200;
	          imgthumBitmap = img;

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
	
	private void addEventListeners(DiaryEntryPicture pic) {
		 // Register event listener for context menu
        myActivity.registerForContextMenu((ImageView) myActivity.findViewById(pic.getId()));
        
        // Add onClick listener
        Log.d("DiaryOnClickListenerNormal", "precall");
        DiaryImageViewOnClickListener l = new DiaryImageViewOnClickListener(pic, myActivity);
        myActivity.findViewById(pic.getId()).setOnClickListener(l);
	}
	
	/**
	 * Shows thumbnails of all current pictures
	 */
	public void drawCurrentPictures() {
		
		for(final DiaryEntryPicture pic: pictures) {
			
			// Show new pictures from gallery & camera
			if (pic.getType() == DiaryEntryPictureType.NEW_CAMERA_PICTURE || pic.getType() == DiaryEntryPictureType.NEW_GALLERY_PICTURE) {
		        ImageView imageView = (ImageView) myActivity.findViewById(pic.getId());
		        imageView.setImageBitmap(getThumbnail(pic.getPath()));
		        addEventListeners(pic);
 
		        Log.d("showCurrentPictures from gallery & camera- Pic id: ", ""+pic.getIdString());
			} else {
				// Show existing pictures from database which were not moved to trash
			    ImageView imageView = (ImageView) myActivity.findViewById(pic.getId());
			    imageView.setImageBitmap(pic.getImg());
			    addEventListeners(pic);
			        
			    Log.d("showCurrentPictures from database - Pic id: ", ""+pic.getIdString());
			}
		}	
	}
	
	
	/**
	 * Tells you if you can add another picture
	 * @return
	 */
	public boolean newPicturesAllowed() {
		return (MAX_PICTURES_ALLOWED > countPictures());
	}

	/** 
	 * Tells you how many pictures are allowed
	 * @return
	 */
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
	
	 public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

		 final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		 int h= (int) (newHeight*densityMultiplier);
		 int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		 photo=Bitmap.createScaledBitmap(photo, w, h, true);

		 return photo;
		 }

}
