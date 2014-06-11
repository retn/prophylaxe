package de.hawlandshut.rueckfallprophylaxe.ui;

import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntryPicture;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

public class DiaryImageViewOnClickListener implements View.OnClickListener {
	private Activity myActivity;
	private DiaryEntryPicture pic;
	
	DiaryImageViewOnClickListener(DiaryEntryPicture pic, Activity activity) {
		Log.d("DiaryOnClickListenerNormal","constructor");
		this.pic = pic;
		this.myActivity = activity;
	}

	@Override
	public void onClick(View v) {
		Log.d("DiaryOnClickListenerNormal", "onClick called");
    	/*
    	Intent intent = new Intent(myActivity, DiaryImageViewActivity.class);
    	Bitmap compressedPic;
    	compressedPic = DiaryNewEntryPictureManager.scaleDownBitmap(pic.getImg(), 150, myActivity);
    	intent.putExtra("BitmapImage", compressedPic);
    	myActivity.startActivity(intent);
    	*/
    	
	}

}
