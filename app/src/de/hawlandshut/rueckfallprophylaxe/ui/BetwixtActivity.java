package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
//This Activity will start when the user is Clickin on the yellow Button at the 
//TrafficLightActivity
public class BetwixtActivity extends Activity implements OnClickListener{
	
	Button betwixt1, betwixt2, betwixt3;
	
	Spinner spinnemotion2;

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.betwixt);
	        
	        spinnemotion2 = (Spinner) findViewById(R.id.spinnemotion2);
	        
	        betwixt1 = (Button)findViewById(R.id.betwixt1);
	        betwixt2 = (Button)findViewById(R.id.betwixt2);
	        betwixt3 = (Button)findViewById(R.id.betwixt3);
	        
	        betwixt1.setOnClickListener(this);
	        betwixt2.setOnClickListener(this);
	        betwixt3.setOnClickListener(this);
	        
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
					R.array.mood_array, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnemotion2.setAdapter(adapter);
	        
	 }

	@Override
	public void onClick(View v) {
		
		if(v == betwixt1)
		{
			Intent intent = new Intent(this, DiaryEntryListActivity.class);
			startActivity(intent);
		}
		if(v == betwixt2)
		{
			String selectedEmotion = ((CharSequence) spinnemotion2.getSelectedItem()).toString();
			
			Intent intent = new Intent(this, EmotionActivity.class);
			intent.putExtra("EMOTION", selectedEmotion);
			startActivity(intent);
		}
		if(v == betwixt3)
		{
			Intent intent = new Intent(this, EmergencyCaseActivity.class);
			startActivity(intent);
		}
		
	}
}
