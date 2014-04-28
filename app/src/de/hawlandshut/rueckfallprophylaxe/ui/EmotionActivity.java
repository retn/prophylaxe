package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class EmotionActivity extends Activity {

	String emotion;			//contains the selected Emotion-String
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emotion);
		
		Bundle bundle = getIntent().getExtras();
		emotion = (String) bundle.getString("EMOTION");
		
		Toast.makeText(this, emotion, Toast.LENGTH_LONG).show();
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
