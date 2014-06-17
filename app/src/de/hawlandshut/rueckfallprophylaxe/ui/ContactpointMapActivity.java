package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.HashMap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.ContactPoint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ContactpointMapActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	private int ptgCenterId;
	private HashMap<Marker, ContactPoint> markers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contactpoint_map);

		Bundle bundle = getIntent().getExtras();
		ptgCenterId = bundle.getInt("CP_CENTER");

		try {
			// Loading map
			initilizeMap();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

		googleMap.setMyLocationEnabled(true);

        markers = new HashMap<Marker, ContactPoint>();
		for (ContactPoint ptg : ControllerData.getPlacesToGo()) {
			LatLng latlng = new LatLng(ptg.getLat(), ptg.getLng());
			
			Marker m = googleMap.addMarker(new MarkerOptions()
			.title(ptg.getName())
			.snippet(
					ptg.getStreet() + " " + ptg.getPlz() + " "
							+ ptg.getTown())
			.position(latlng));
			markers.put(m, ptg);

			if (ptg.getId() == ptgCenterId) {
				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,
						13));
				m.showInfoWindow();
			}
		}
		
		googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				for(Marker m: markers.keySet()) {
					if(marker.getTitle().equals(m.getTitle())) {
						ContactpointViewActivity.cp = markers.get(m);
						Intent intent = new Intent(ContactpointMapActivity.this, ContactpointViewActivity.class);
						ContactpointMapActivity.this.startActivity(intent);
					}
				}
			}
			
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
}
