package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class EmotionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emotion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emotion, menu);
		return true;
	}
	
	public void switch_to_Distraction(View view){
		Intent intent = new Intent(this, DistractionActivity.class);
		startActivity(intent);
		
	}


}
