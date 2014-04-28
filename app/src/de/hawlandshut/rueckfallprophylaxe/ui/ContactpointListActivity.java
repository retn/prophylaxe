package de.hawlandshut.rueckfallprophylaxe.ui;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.PlaceToGo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ContactpointListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactpoint_list);
		
		final ListView lv = (ListView) findViewById(R.id.contactpointListView);
        lv.setAdapter(new ContactpointListAdapter(this, ControllerData.getPlacesToGo()));
 
        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv.getItemAtPosition(position);
                PlaceToGo ptg = (PlaceToGo) o;
                
                Intent intent = new Intent(ContactpointListActivity.this, ContactpointMapActivity.class);
                intent.putExtra("CP_CENTER", ptg.getId());
                startActivity(intent);
            }
        });
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
