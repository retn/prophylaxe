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

public class RequestJson {

	private static final String URL = "http://spl.bumsi.net/app/get-patient";
	Gson gson;
	String email;
	String token;
	URL obj;
	HttpURLConnection con;
	int statuscode;

	/**
	 * This class sends an HTTP-Request to the server and processes received
	 * data
	 * 
	 * @param email
	 *            Authentication email
	 * @param token
	 *            Authentication token
	 * @throws IOException
	 */
	public RequestJson(String email, String token) throws IOException {
		gson = new Gson();
		this.email = email;
		this.token = token;
		obj = new URL(URL);
		con = (HttpURLConnection) obj.openConnection();
	}

	/**
	 * Sends an HTTP-Request to the server and returns the received JSON-String
	 * 
	 * @return JSON-String
	 * @throws IOException
	 * @throws SendRequestException 
	 */
	private String sendRequest() throws IOException {
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

	/**
	 * Creates objects filled with data from the JSON-String
	 * 
	 * @return JSON-Data as an Object
	 * @throws IOException
	 * @throws JsonSyntaxException
	 * @throws RequestJsonException 
	 * @throws SendRequestException 
	 */
	public JsonData getData() throws JsonSyntaxException, IOException {
		JsonData data = gson.fromJson(sendRequest(), JsonData.class);
		
		Log.d("json", "json data loaded");
		return data;
	}
	
	

}
