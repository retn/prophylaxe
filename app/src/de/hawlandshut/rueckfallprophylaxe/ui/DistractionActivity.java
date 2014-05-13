package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
				
		emotion1 = getEmotion();
	
		distractions = emotion1.getDistractions();
		
		List<String> dis_texts = new ArrayList<String>();
		for(Distraction d: distractions) {
			dis_texts.add(d.getText());
		}
		
		final ListView lv = (ListView) findViewById(R.id.distractionListView);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dis_texts));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.distraction, menu);
		return true;
	}
	
	public Emotion getEmotion(){
		for(Emotion e: emotions.values()){
			if(e.getName().trim().toLowerCase().equals(emotion.trim().toLowerCase())) {
				return e;
			}
		}
		return null;
	}

}
