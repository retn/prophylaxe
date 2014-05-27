package de.hawlandshut.rueckfallprophylaxe.data;

public class ContactPoint {

	private int id;
	private String name;
	private String street;
	private String plz;
	private String town;
	private String phone_number;
	private String email;
	private double latitude;
	private double longitude;

	public ContactPoint(int id, String name, String street, String plz,
			String town, String phone_number, String email, double lat, double lng) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.plz = plz;
		this.town = town;
		this.phone_number = phone_number;
		this.email = email;
		this.latitude = lat;
		this.longitude = lng;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	public String getPlz() {
		return plz;
	}

	public String getTown() {
		return town;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public String getEmail() {
		return email;
	}

	public double getLat() {
		return latitude;
	}
	
	public double getLng() {
		return longitude;
	}
}
