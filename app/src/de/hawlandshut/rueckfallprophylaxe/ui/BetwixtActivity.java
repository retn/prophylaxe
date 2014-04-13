package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//This Activity will start when the user is Clickin on the yellow Button at the 
//TrafficLightActivity
public class BetwixtActivity extends Activity implements OnClickListener{
	
	Button betwixt1, betwixt2, betwixt3;

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.betwixt);
	        
	        betwixt1 = (Button)findViewById(R.id.betwixt1);
	        betwixt2 = (Button)findViewById(R.id.betwixt2);
	        betwixt3 = (Button)findViewById(R.id.betwixt3);
	        
	        betwixt1.setOnClickListener(this);
	        betwixt2.setOnClickListener(this);
	        betwixt3.setOnClickListener(this);
	        
	 }

	@Override
	public void onClick(View v) {
		
		if(v == betwixt1)
		{
			Intent intent = new Intent(this, DiaryActivity.class);
			startActivity(intent);
		}
		if(v == betwixt2)
		{
			Intent intent = new Intent(this, DistractionActivity.class);
			startActivity(intent);
		}
		if(v == betwixt3)
		{
			Intent intent = new Intent(this, NotfallKofferActivity.class);
			startActivity(intent);
		}
		
	}
}
