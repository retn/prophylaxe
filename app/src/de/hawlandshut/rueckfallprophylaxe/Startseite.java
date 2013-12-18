package de.hawlandshut.rueckfallprophylaxe;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class Startseite extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.startseite, menu);
        return true;
    }
    
    public void callTagebuch(View view) {
    	Intent intent = new Intent(this, DiaryActivity.class);
    	startActivity(intent);
    }
    
    public void callAnlaufstellen(View view) {
    	Intent intent = new Intent(this, ContactpointListActivity.class);
    	startActivity(intent);
    }
    /* Fehlt noch die Notfallkoffer klasse
    public void callNotfallkoffer(View view) {
    	Intent intent = new Intent(this, Notfallkoffer.class);
    	startActivity(intent);
    }*/
    /* Fehlt noch die Ablenkungen klasse
    public void callAblenkungen(View view) {
    	Intent intent = new Intent(this, Ablenkungen.class);
    	startActivity(intent);
    }*/
    
}
