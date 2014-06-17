package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.data.ContactPoint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactpointListAdapter extends BaseAdapter {
    private static List<ContactPoint> contactPoints;
 
    private LayoutInflater mInflater;
 
    public ContactpointListAdapter(Context context, List<ContactPoint> placesToGo) {
        contactPoints = placesToGo;
        mInflater = LayoutInflater.from(context);
    }
 
    public int getCount() {
        return contactPoints.size();
    }
 
    public Object getItem(int position) {
        return contactPoints.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.map_list_item, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.mapListTitle);
            holder.txtCity = (TextView) convertView.findViewById(R.id.mapListCity);
 
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.txtName.setText(contactPoints.get(position).getName());
        holder.txtCity.setText(contactPoints.get(position).getTown()); 
        return convertView;
    }
    
    static class ViewHolder {
        TextView txtName;
        TextView txtCity;

    }
}
