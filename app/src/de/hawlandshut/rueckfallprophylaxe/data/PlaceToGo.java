package de.hawlandshut.rueckfallprophylaxe.data;

public class PlaceToGo {

	int id;
	String name;
	String street;
	String plz;
	String town;
	String phone_number;
	String email;

	public PlaceToGo(int id, String name, String street, String plz,
			String town, String phone_number, String email) {
		this.id = id;
		this.name = name;
		this.street = street;
		this.plz = plz;
		this.town = town;
		this.phone_number = phone_number;
		this.email = email;
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

}
