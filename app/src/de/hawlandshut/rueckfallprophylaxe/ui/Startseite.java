package de.hawlandshut.rueckfallprophylaxe.ui;

import java.io.IOException;

import de.hawlandshut.rueckfallprophylaxe.db.Database;
import de.hawlandshut.rueckfallprophylaxe.net.JsonData;
import de.hawlandshut.rueckfallprophylaxe.net.RequestJson;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Startseite extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startseite);
        Database database=new Database(this);
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
    
    public void callNotfallkoffer(View view) {
    	Intent intent = new Intent(this, NotfallKofferActivity.class);
    	startActivity(intent);
    }
 
    public void callEmotion(View view) {
    	Intent intent = new Intent(this, EmotionActivity.class);
    	startActivity(intent);
    }
    
}
