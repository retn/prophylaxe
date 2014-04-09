package de.hawlandshut.rueckfallprophylaxe.net;

public class JsonAddressViewport {
	
	private JsonAddressLocation northeast;
	private JsonAddressLocation southwest;
	
	public JsonAddressViewport() {
		
	}

	public JsonAddressLocation getNortheast() {
		return northeast;
	}

	public void setNortheast(JsonAddressLocation northeast) {
		this.northeast = northeast;
	}

	public JsonAddressLocation getSouthwest() {
		return southwest;
	}

	public void setSouthwest(JsonAddressLocation southwest) {
		this.southwest = southwest;
	}
	
	
}
