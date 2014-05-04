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
import de.hawlandshut.rueckfallprophylaxe.data.RiskSituation;
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
        
		List<EmergencyCase> cases=new ArrayList<EmergencyCase>();
		cases = ControllerData.getEmergencyCase();
		
		EmergencyCase case0=cases.get(0);
		//Toast.makeText(this, case0.toString(),Toast.LENGTH_LONG).show();    
		
		List<RiskSituation> situations=new ArrayList<RiskSituation>(); 
		situations = ControllerData.getRiskSituation();
		

        final ListView listview = (ListView) findViewById(R.id.list_view_koffer);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < situations.size(); ++i) {
            list.add(situations.get(i).getText());
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
