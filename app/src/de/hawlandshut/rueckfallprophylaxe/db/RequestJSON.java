package de.hawlandshut.rueckfallprophylaxe.db;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class RequestJSON {
	
	final String URL = "http://spl.bumsi.net/app/get-patient";
	String email;
	String token;
	URL obj; 
	HttpURLConnection con;
	String json;			//contains the servers response
	boolean valid;			//true if request was valid

	public RequestJSON(String email, String token) throws IOException {
		this.email = email;
		this.token = token;
		valid = false;
		obj = new URL(URL);
		con = (HttpURLConnection) obj.openConnection();
	}
	
	public void sendRequest() throws IOException {
		//add reuqest header
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
		Log.d("httprequest", "Post parameters : " + urlParameters);
		Log.d("httprequest", "Response Code : " + responseCode);

		if(responseCode == 1) {
			valid = true;
		} else {
			valid = false;
		}
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		json = response.toString();
	}
	
	public String getJSON() {
		return json;
	}
	
	public boolean isValid() {
		if (valid)
			return true;
		else 
			return false;
	}
}
