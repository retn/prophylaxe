package de.hawlandshut.rueckfallprophylaxe.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

//The Spinner which will be used to select a Emotion before Clicking at the EmotionActivity
//isnt working yet so i commented it out for now. I will try to fix it the next days.
public class HomeActivity extends Activity {
	
	Spinner spinnemotion;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        spinnemotion = (Spinner) findViewById(R.id.spinnemotion);
        
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.mood_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnemotion.setAdapter(adapter);
	}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.startseite, menu);
        return true;
    }
    
    public void callTagebuch(View view) {
    	Intent intent = new Intent(this, DiaryEntryListActivity.class);
    	startActivity(intent);
    }
    
    public void callAnlaufstellen(View view) {
    	Intent intent = new Intent(this, ContactpointListActivity.class);
    	startActivity(intent);
    }
    
    public void callNotfallkoffer(View view) {
    	Intent intent = new Intent(this, EmergencyCaseActivity.class);
    	startActivity(intent);
    }
 
    public void callEmotion(View view) {
    	String selectedEmotion = ((CharSequence) spinnemotion.getSelectedItem()).toString();
    	
    	Intent intent = new Intent(this, EmotionActivity.class);
    	intent.putExtra("EMOTION", selectedEmotion);
    	startActivity(intent);
    }

    
}
