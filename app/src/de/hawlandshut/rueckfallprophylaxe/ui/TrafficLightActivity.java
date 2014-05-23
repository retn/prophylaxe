package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
//This is the Traffic Light Activity, where the user can click on three Colored Buttons.
//For now i just made a Red Circle for a button. i will create of course 
//a yellow and a green one of the same size.
public class TrafficLightActivity extends Activity implements OnClickListener {

	ImageButton redButton, greenButton, yellowButton;
	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.trafficlight);
	        
	        
	 redButton = (ImageButton)findViewById(R.id.redButton);
	 yellowButton = (ImageButton)findViewById(R.id.yellowButton);
	 greenButton = (ImageButton)findViewById(R.id.greenButton);
	 
	 redButton.setOnClickListener(this);
	 yellowButton.setOnClickListener(this);
	 greenButton.setOnClickListener(this);
	 
	 }

	@Override
	public void onClick(View v) {
		
		if(v == redButton)
		{
			Intent intent = new Intent(this, ContactpointListActivity.class );
			startActivity(intent);
		}
		if(v == yellowButton)
		{
			Intent intent = new Intent(this, BetwixtActivity.class );
			startActivity(intent);
		}
		if(v == greenButton)
		{
			Intent intent = new Intent(this, HomeActivity.class );
			startActivity(intent);
		}
		
	}
	
	@Override
    public void onBackPressed() {
      finish();
      System.exit(0);
    }
}
