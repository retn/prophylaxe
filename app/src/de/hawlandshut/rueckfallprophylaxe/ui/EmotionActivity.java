package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.Distraction;
import de.hawlandshut.rueckfallprophylaxe.data.Emotion;
import de.hawlandshut.rueckfallprophylaxe.data.Maxim;

public class EmotionActivity extends Activity {

	Spinner spinnemotion;
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
		emotions = ControllerData.getEmotions();
		
		int randomnumber = (int)(Math.random()*maxims.size());
		Maxim maxim = maxims.get(randomnumber);
		
		TextView textView = (TextView)findViewById(R.id.ermutigungs_text);
		textView.setText(maxim.getText());
		
		spinnemotion = (Spinner) findViewById(R.id.spinnemotion);
        
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.mood_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnemotion.setAdapter(adapter);
		spinnemotion.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String e1 = ((String) parent.getSelectedItem()).trim().toLowerCase(Locale.getDefault());
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
		        lv.setAdapter(new ArrayAdapter<String>(EmotionActivity.this, android.R.layout.simple_list_item_1, dis_texts));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
			
		});
	}
	
	public void startPlaylist(View view){ 
		Intent intent = new Intent("android.intent.action.MUSIC_PLAYER"); 
		startActivity(intent); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emotion, menu);
		return true;
	}   

}
