package de.hawlandshut.rueckfallprophylaxe.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import android.util.Log;

/**
 * This class sends an HTTP-Request to the server and processes received data.
 * With the patients email and token {@link #sendRequestPatient()} returns the
 * patients data in form of a JSON-String. This string will be processed with
 * Google Gson in {@link #getData()} which returns an object of the Type
 * {@link Data} in which all the data from the JSON-String is stored. The same
 * works for ContactPoint and ContactPointTimestamp with the difference, that
 * they don't need email and token.
 * 
 * @author Patrick
 * 
 */
public class RequestJson {

	private static final String URL_APP = "http://spl.bumsi.net/app/";
	private static final String URL_MAPS = "http://maps.google.com/maps/api/geocode/json";

	/**
	 * RequestJson constructor.
	 * 
	 * @throws IOException
	 */
	public RequestJson() throws IOException {

	}

	/**
	 * Creates objects filled with data collected from the get-patient
	 * JSON-String
	 * 
	 * @param email
	 * @param token
	 * @return get-patient JSON-Data
	 * @throws JsonSyntaxException
	 * @throws IOException
	 */
	public Data getData(String email, String token) throws JsonSyntaxException,
			IOException {
		Gson gson = new Gson();
		Data data = gson.fromJson(sendRequestPatient(email, token), Data.class);

		Log.d("json", "json data loaded");
		return data;
	}

	/**
	 * Creates objects filled with data collected from the get-contactpoints
	 * JSON-String
	 * 
	 * @return get-contactpoints JSON-Data
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	public List<JsonContactPoint> getContactPoints()
			throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		List<JsonContactPoint> contactPoints = gson.fromJson(
				sendRequestContactPoint(),
				new TypeToken<List<JsonContactPoint>>() {
				}.getType());
		
		for (JsonContactPoint cp: contactPoints) {
			String search = URLEncoder
					.encode(cp.getStreet().replace("str.", "straße") + " "
							+ cp.getPlz() + " " + cp.getTown() + " Deutschland",
							"UTF-8");

			JsonAddress address = getAddress(search);

			double lat = 0;
			double lng = 0;
			if(address.getResults().size() != 0) {
				lat = address.getResults().get(0).getGeometry()
						.getLocation().getLat();
				lng = address.getResults().get(0).getGeometry()
						.getLocation().getLng();
			} else {
				Log.d("LatLngNotFound", search);
			}
			
			cp.setLat(lat);
			cp.setLng(lng);
		}
		
		return contactPoints;
	}
	
	public JsonAddress getAddress(String search) throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		JsonAddress address = gson.fromJson(sendRequestMapAddress(search), JsonAddress.class);
		return address;
	}

	/**
	 * Creates objects filled with data collected from the
	 * get-contactpoint-timestamp JSON-String
	 * 
	 * @return get-contactpoint-timestamp JSON-Data
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	public String getContactPointTimestamp() throws JsonSyntaxException,
			IOException {
		Gson gson = new Gson();
		JsonContactPointTimestamp timestamp = gson.fromJson(
				sendRequestContactPointTimestamp(),
				JsonContactPointTimestamp.class);
		return timestamp.getTimestamp();
	}

	/**
	 * Sends an HTTP-Request to the server and returns the received get-patient
	 * JSON-String
	 * 
	 * @param email
	 * @param token
	 * @return get-patient JSON-String
	 * @throws IOException
	 */
	private String sendRequestPatient(String email, String token)
			throws IOException {
		URL obj = new URL(URL_APP + "get-patient");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");

		String urlParameters = "token=" + token + "&email=" + email;
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	/**
	 * Sends an HTTP-Request to the server and returns the received
	 * get-contactpoints JSON-String
	 * 
	 * @param email
	 * @param token
	 * @return get-contactpoints JSON-String
	 * @throws IOException
	 */
	private String sendRequestContactPoint() throws IOException {
		URL obj = new URL(URL_APP + "get-contactpoints");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	/**
	 * Sends an HTTP-Request to the server and returns the received
	 * get-contactpoint-timestamp JSON-String
	 * 
	 * @param email
	 * @param token
	 * @return get-get-contactpoint-timestamp JSON-String
	 * @throws IOException
	 */
	private String sendRequestContactPointTimestamp() throws IOException {
		URL obj = new URL(URL_APP + "get-contactpoint-timestamp");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
	
	private String sendRequestMapAddress(String search) throws IOException {
		URL obj = new URL(URL_MAPS + "?address=" + search + "&sensor=false");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.flush();
		wr.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
}
