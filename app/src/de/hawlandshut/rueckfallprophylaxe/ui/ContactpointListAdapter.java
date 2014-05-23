package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.data.PlaceToGo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ContactpointListAdapter extends BaseAdapter {
    private static List<PlaceToGo> contactPoints;
    private Context context;
 
    private LayoutInflater mInflater;
 
    public ContactpointListAdapter(Context context, List<PlaceToGo> placesToGo) {
        contactPoints = placesToGo;
        this.context = context;
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
            holder.txtStreet = (TextView) convertView.findViewById(R.id.mapListStreet);
            holder.txtCity = (TextView) convertView.findViewById(R.id.mapListCity);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.mapListPhone);
            holder.txtEmail = (TextView) convertView.findViewById(R.id.mapListMail);
            holder.btnCall = (Button) convertView.findViewById(R.id.mapListBtnCall);
            holder.btnMap = (Button) convertView.findViewById(R.id.mapListBtnMap);
 
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.txtName.setText(contactPoints.get(position).getName());
        holder.txtCity.setText(contactPoints.get(position).getPlz() + " " + contactPoints.get(position).getTown());
        holder.txtStreet.setText(contactPoints.get(position).getStreet());
        holder.txtPhone.setText(contactPoints.get(position).getPhone_number());
        holder.txtEmail.setText(contactPoints.get(position).getEmail());
        holder.btnMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Object o = getItem(position);
                PlaceToGo ptg = (PlaceToGo) o;
                
                Intent intent = new Intent(context, ContactpointMapActivity.class);
                intent.putExtra("CP_CENTER", ptg.getId());
                context.startActivity(intent);
			}
        });
        holder.btnCall.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent = new Intent(Intent.ACTION_DIAL);
        		intent.setData(Uri.parse("tel:" + contactPoints.get(position).getPhone_number()));
        		context.startActivity(intent);
        	}
        });
 
        return convertView;
    }
    
    static class ViewHolder {
        TextView txtName;
        TextView txtStreet;
        TextView txtCity;
        TextView txtPhone;
        TextView txtEmail;
        Button btnMap;
        Button btnCall;
    }
}
