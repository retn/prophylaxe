package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.Emotion;
import de.hawlandshut.rueckfallprophylaxe.data.Distraction;
import java.util.HashMap;

public class DistractionActivity extends Activity {
	String emotion;	
	Emotion emotion1;
	HashMap<Integer, Emotion> emotions;
	
	List<Distraction> distractions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distraction);
		emotions = ControllerData.getEmotions();		
		Bundle bundle = getIntent().getExtras();
		emotion = (String) bundle.getString("EMOTION");
				
		getEmotion();
	
		//distractions = emotion1.getDistractions();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.distraction, menu);
		return true;
	}
	
	public void getEmotion(){
		String emotionNames = "test";
		int i = 0;
		for(Emotion e: emotions.values()){
			i++;
			Toast.makeText(this, i + " ", Toast.LENGTH_LONG).show();
			emotionNames = emotionNames + " " + i + " " + e.getName();
			if(e.getName().trim().toLowerCase() == emotion.trim().toLowerCase()){
				emotion1 = e;
				Toast.makeText(this, emotion1.getName(), Toast.LENGTH_LONG).show();
			}
		}
		//Toast.makeText(this, emotionNames, Toast.LENGTH_LONG).show();		
	}

}
