package de.hawlandshut.rueckfallprophylaxe.net;

public class JsonAddressGeometry {
	
	private JsonAddressLocation location;
	private String location_type;
	private JsonAddressViewport viewport;
	
	public JsonAddressGeometry() {
		
	}
	
	
	
	public JsonAddressLocation getLocation() {
		return location;
	}



	public void setLocation(JsonAddressLocation location) {
		this.location = location;
	}



	public String getLocation_type() {
		return location_type;
	}



	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}



	public JsonAddressViewport getViewport() {
		return viewport;
	}

	public void setViewport(JsonAddressViewport viewport) {
		this.viewport = viewport;
	}
}
