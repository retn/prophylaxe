package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.Distraction;
import de.hawlandshut.rueckfallprophylaxe.data.Emotion;
import de.hawlandshut.rueckfallprophylaxe.data.Maxim;

public class EmotionActivity extends Activity {

	String emotionString;			//contains the selected Emotion-String
	Emotion emotion;
	List<Maxim> maxims;
	HashMap<Integer, Emotion> emotions;
	List<Distraction> distractions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emotion);
		maxims = ControllerData.getMaxims();
		
		Bundle bundle = getIntent().getExtras();
		emotionString = (String) bundle.getString("EMOTION");
		emotions = ControllerData.getEmotions();
		
		int randomnumber = (int)(Math.random()*maxims.size());
		Maxim maxim = maxims.get(randomnumber);
		
		TextView textView = (TextView)findViewById(R.id.ermutigungs_text);
		textView.setText(maxim.getText());
		
		String e1 = emotionString.trim().toLowerCase(Locale.getDefault());
		for(Emotion e: emotions.values()){
			String e2 = e.getName().trim().toLowerCase(Locale.getDefault());
			if(e1.equals(e2)) {
				emotion = e;
			}
		}
	
		distractions = emotion.getDistractions();
		
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
		getMenuInflater().inflate(R.menu.emotion, menu);
		return true;
	}   

}
