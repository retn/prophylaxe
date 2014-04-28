package de.hawlandshut.rueckfallprophylaxe.ui;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.PlaceToGo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ContactpointMapActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	private int ptgCenterId;

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
	 * function to load map. If map is not created it will create it for you
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

		for (PlaceToGo ptg : ControllerData.getPlacesToGo()) {
			LatLng latlng = new LatLng(ptg.getLat(), ptg.getLng());

			if (ptg.getId() == ptgCenterId) {
				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,
						13));
			}

			googleMap.addMarker(new MarkerOptions()
					.title(ptg.getName())
					.snippet(
							ptg.getStreet() + " " + ptg.getPlz() + " "
									+ ptg.getTown())
					.position(latlng));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}
}
