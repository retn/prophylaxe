package de.hawlandshut.rueckfallprophylaxe.net;

import java.util.List;

public class JsonAddressData {

	private List<JsonAddressComponent> address_components;
	private String formatted_address;
	private JsonAddressGeometry geometry;
	private List<String> types;

	public JsonAddressData() {

	}

	public List<JsonAddressComponent> getAddress_components() {
		return address_components;
	}

	public void setAddress_components(
			List<JsonAddressComponent> address_components) {
		this.address_components = address_components;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public JsonAddressGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(JsonAddressGeometry geometry) {
		this.geometry = geometry;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	
}
