package de.hawlandshut.rueckfallprophylaxe.ui;

import de.hawlandshut.rueckfallprophylaxe.data.ContactPoint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ContactpointViewActivity extends Activity {

	public static ContactPoint cp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactpoint_view);		
		
		TextView txtName = (TextView) findViewById(R.id.cpTitle);
		TextView txtStreet = (TextView) findViewById(R.id.street);
		TextView txtCity = (TextView) findViewById(R.id.city);
		TextView txtPhone = (TextView) findViewById(R.id.phone);
		TextView txtEmail = (TextView) findViewById(R.id.mail);
		TextView btnCall = (Button) findViewById(R.id.btnCall);
		TextView btnMap = (Button) findViewById(R.id.btnMap);
		
		 txtName.setText(cp.getName());
	     txtCity.setText(cp.getPlz() + " " + cp.getTown());
	     txtStreet.setText(cp.getStreet());
	     txtPhone.setText(cp.getPhone_number());
	     if(cp.getEmail() != null && cp.getEmail() != "") {
	    	 txtEmail.setText(cp.getEmail());
	     } else {
	    	 txtEmail.setText("-");
	     }
	     
	        
	        if(cp.getLat() == 0 && cp.getLng() == 0) {
	        	btnMap.setClickable(false);
	        	btnMap.setEnabled(false);
	        } else {
	        	btnMap.setEnabled(true);
	        	btnMap.setClickable(true);
	        	btnMap.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
		                Intent intent = new Intent(ContactpointViewActivity.this, ContactpointMapActivity.class);
		                intent.putExtra("CP_CENTER", cp.getId());
		                startActivity(intent);
					}
	        	});
	        }
	        if(cp.getPhone_number() == "0" || cp.getPhone_number() == null) {
	        	btnCall.setClickable(false);
	        	btnCall.setEnabled(false);
	        } else {
	        	btnMap.setEnabled(true);
	        	btnMap.setClickable(true);
		        btnCall.setOnClickListener(new OnClickListener() {
		        	@Override
		        	public void onClick(View v) {
		        		Intent intent = new Intent(Intent.ACTION_DIAL);
		        		intent.setData(Uri.parse("tel:" + cp.getPhone_number()));
		        		startActivity(intent);
		        	}
		        });
	        }
		
	}
	
}
