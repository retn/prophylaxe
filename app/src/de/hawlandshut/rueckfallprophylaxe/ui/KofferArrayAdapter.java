package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Anton
 * Date: 17.12.13
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
public class KofferArrayAdapter extends ArrayAdapter<String> {

    Context context;
    public KofferArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.context=context;
    }


    public View getView(int position, View convertView, ViewGroup parent){
        String text=getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.koffer_list_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.koffer_item);
            convertView.setTag(textView);
            textView.setText(text);
        }

        return convertView;

    }
}
