package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.DiaryEntry;
import de.hawlandshut.rueckfallprophylaxe.data.EmergencyCase;
import de.hawlandshut.rueckfallprophylaxe.db.Database;

/**
 * Created with IntelliJ IDEA.
 * User: Anton
 * Date: 14.12.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class EmergencyCaseActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_1);
        
		List<EmergencyCase> Entries= new ArrayList<EmergencyCase>();
		
		PinShare myApp = PinShare.getInstance();
		String pin = myApp.getPin();
		Database db = new Database(this);
		db.InitializeSQLCipher(pin);
		// Load data
		Entries = ControllerData.getEmergencyCase();
		
		db.close();
		
        

        final ListView listview = (ListView) findViewById(R.id.list_view_koffer);
        String[] values = new String[] { "Ich hatte Streit und bin aggressiv","Ich bin gerade vor einer Bar", "Ich habe alte Freunde wieder getroffen", "Ich habe ein Glas getrunken" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final CaseArrayAdapter adapter = new CaseArrayAdapter(this,
                R.id.message_text, list);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
                Intent intent = new Intent(EmergencyCaseActivity.this, EmergencyCaseTwoActivity.class);
                EmergencyCaseActivity.this.startActivity(intent);
				
			}
		});

        ImageView imageView=(ImageView) findViewById(R.id.image_koffer);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(EmergencyCaseActivity.this, "W�hle Foto oder mache selbst eines", Toast.LENGTH_LONG);
                toast.show();		
            }
        });

    }
}
