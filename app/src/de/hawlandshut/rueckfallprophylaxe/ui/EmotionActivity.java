package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.Maxim;

public class EmotionActivity extends Activity {

	String emotion;			//contains the selected Emotion-String
	List<Maxim> maxims;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emotion);
		maxims = ControllerData.getMaxims();
		
		Bundle bundle = getIntent().getExtras();
		emotion = (String) bundle.getString("EMOTION");
		
		int randomnumber = (int)(Math.random()*maxims.size());
		Maxim maxim = maxims.get(randomnumber);
		
		TextView textView = (TextView)findViewById(R.id.ermutigungs_text);
		textView.setText(maxim.getText());
		
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
		intent.putExtra("EMOTION",emotion);
		startActivity(intent);
		
	}


	
	
	
	
    

}
