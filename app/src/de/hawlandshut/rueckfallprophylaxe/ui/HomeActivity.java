package de.hawlandshut.rueckfallprophylaxe.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

//The Spinner which will be used to select a Emotion before Clicking at the EmotionActivity
//isnt working yet so i commented it out for now. I will try to fix it the next days.
public class HomeActivity extends Activity {
	
	static boolean isBetwixt;
	static boolean isContactPoint;
	static boolean comeFromContactPoint;
	Spinner spinnemotion;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        String site = getIntent().getStringExtra("SITE");
	}
    
    @Override
    protected void onResume() {
    	super.onResume();
    	 if(isBetwixt) {
         	Button btnContactpoints = (Button) findViewById(R.id.btnAnlaufstellen);
         	btnContactpoints.setVisibility(View.GONE);
         } else if(isContactPoint && comeFromContactPoint) {
         	onBackPressed();
         } else if(!comeFromContactPoint && isContactPoint) {
         	comeFromContactPoint = true;
         	Intent intent = new Intent(this, ContactpointListActivity.class);
         	startActivity(intent);
         }
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
    	Intent intent = new Intent(this, EmotionActivity.class);
    	startActivity(intent);
    }

    
}
