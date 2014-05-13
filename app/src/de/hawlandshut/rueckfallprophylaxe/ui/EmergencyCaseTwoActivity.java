package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.ArrayList;
import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.HelpPerson;
import de.hawlandshut.rueckfallprophylaxe.data.LimitRelapse;
import de.hawlandshut.rueckfallprophylaxe.data.RiskSituation;
import de.hawlandshut.rueckfallprophylaxe.data.SafetyAction;
import de.hawlandshut.rueckfallprophylaxe.data.SafetyThought;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Anton
 * Date: 17.12.13
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */
public class EmergencyCaseTwoActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_2);
        
        this.setSafetyActions();
        this.setSafetyThoughts();
        this.setHelpPeople();
        this.setLimitRelapses();

		

    }

	private void setLimitRelapses() {
		List<LimitRelapse> limitRelapses= ControllerData.getLimitrelapse();
		
        final ListView listview = (ListView) findViewById(R.id.limit_relapse);
        if(listview==null)return;
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < limitRelapses.size(); ++i) {
            list.add(limitRelapses.get(i).getText());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setSelector(android.R.color.transparent); 
		
		
	}

	private void setHelpPeople() {
		final List<HelpPerson> helpPeople=ControllerData.getHelpPeople(); 
		
        final ListView listview = (ListView) findViewById(R.id.help_people);
        if(listview==null)return;
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < helpPeople.size(); ++i) {
            list.add(helpPeople.get(i).toString());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"+helpPeople.get(arg2).getPhone_number()));
				startActivity(intent);
				
			}
		});
		
	}

	private void setSafetyThoughts() {
		List<SafetyThought> safetyThoughts=ControllerData.getSafetyThought();
		
        final ListView listview = (ListView) findViewById(R.id.safety_thoughts);
        if(listview==null)return;
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < safetyThoughts.size(); ++i) {
            list.add(safetyThoughts.get(i).getText());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setSelector(android.R.color.transparent); 
		
	}

	private void setSafetyActions() {
		List<SafetyAction> safetyActions= ControllerData.getSafetyAction();
		
        final ListView listview = (ListView) findViewById(R.id.safety_actions);
        if(listview==null)return;
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < safetyActions.size(); ++i) {
            list.add(safetyActions.get(i).getText());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        listview.setSelector(android.R.color.transparent); 

	}
	


}
