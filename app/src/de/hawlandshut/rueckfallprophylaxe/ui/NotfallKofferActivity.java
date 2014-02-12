package de.hawlandshut.rueckfallprophylaxe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Anton
 * Date: 14.12.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class NotfallKofferActivity extends Activity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koffer_1);

        final ListView listview = (ListView) findViewById(R.id.list_view_koffer);
        String[] values = new String[] { "Ich hatte Streit und bin aggressiv","Ich bin gerade vor einer Bar", "Ich habe alte Freunde wieder getroffen", "Ich habe ein Glas getrunken" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final KofferArrayAdapter adapter = new KofferArrayAdapter(this,
                R.id.message_text, list);
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
                Intent intent = new Intent(NotfallKofferActivity.this, NotfallKofferZweiActivity.class);
                NotfallKofferActivity.this.startActivity(intent);
				
			}
		});

        ImageView imageView=(ImageView) findViewById(R.id.image_koffer);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast=Toast.makeText(NotfallKofferActivity.this, "Wähle Foto oder mache selbst eines", Toast.LENGTH_LONG);
                toast.show();		
            }
        });

    }
}
