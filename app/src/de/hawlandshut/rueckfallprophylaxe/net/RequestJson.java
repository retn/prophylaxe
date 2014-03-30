package de.hawlandshut.rueckfallprophylaxe.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.util.Log;

/**
 * This class sends an HTTP-Request to the server and processes received data.
 * With the patients email and token {@link #sendRequest()} returns the patients
 * data in form of a JSON-String. This string will be processed with Google Gson
 * in {@link #getData()} which returns an object of the Type {@link JsonData} in
 * which all the data from the JSON-String is stored.
 * 
 * @author Patrick
 * 
 */
public class RequestJson {

	private static final String URL = "http://spl.bumsi.net/app/get-patient";

	private String email;
	private String token;

	/**
	 * RequestJson constructor.
	 * 
	 * @param email
	 *            patients email
	 * @param token
	 *            patients token
	 * @throws IOException
	 */
	public RequestJson(String email, String token) throws IOException {
		this.email = email;
		this.token = token;
	}

	/**
	 * Creates objects filled with data collected from the JSON-String
	 * 
	 * @return JSON-Data as an Object
	 * @throws IOException
	 * @throws JsonSyntaxException
	 */
	public JsonData getData() throws JsonSyntaxException, IOException {
		Gson gson = new Gson();
		JsonData data = gson.fromJson(sendRequest(), JsonData.class);

		Log.d("json", "json data loaded");
		return data;
	}

	/**
	 * Sends an HTTP-Request to the server and returns the received JSON-String
	 * 
	 * @return JSON-String
	 * @throws IOException
	 */
	private String sendRequest() throws IOException {
		URL obj = new URL(URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");

		String urlParameters = "token=" + token + "&email=" + email;
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		Log.d("httprequest", "Sending 'POST' request to URL : " + URL);
		Log.d("httprequest", "Response Code : " + responseCode);

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
