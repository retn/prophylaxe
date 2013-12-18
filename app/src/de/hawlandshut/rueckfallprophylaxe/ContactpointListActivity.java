package de.hawlandshut.rueckfallprophylaxe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ContactpointListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactpoint_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	public void callMap(View view) {
		Intent intent = new Intent(this, ContactpointMapActivity.class);
		startActivity(intent);
	}

}
