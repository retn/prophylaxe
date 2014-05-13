package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;

public class DiaryOnClickListener implements OnClickListener
{

  DiaryEntry entry;
  Activity activity;
  public DiaryOnClickListener(DiaryEntry entry, Activity activity) {
       this.entry = entry;
       this.activity = activity;
  }

  @Override
  public void onClick(View v)
  {
	  	Log.d("DiaryOnClickListener", "onClick method called");
  		Intent intent = new Intent(activity, DiaryNewEntryActivity.class);
  		intent.putExtra("diaryEntry", entry);
  		activity.startActivity(intent); 

  }
  

};