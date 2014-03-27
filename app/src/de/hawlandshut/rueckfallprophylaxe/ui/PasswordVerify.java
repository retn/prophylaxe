package de.hawlandshut.rueckfallprophylaxe.ui;

import de.hawlandshut.rueckfallprophylaxe.db.Database;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * TODO: PS: Bitte kommentieren -> JavaDoc's
 *
 */
//Das hier ist die Klasse wo der Benutzer bei jedem nutzen der App die PIN eingeben muss. 
public class PasswordVerify extends Activity implements OnClickListener {
	
	TextView PIN_verify;
	Button best_button2;
	Database data;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_verify);
		data = new Database(this);
		
		if(!data.databaseExists())
		{
			Intent intent = new Intent(this, PasswordDetermine.class);
			startActivity(intent);
		} 
		
		PIN_verify = (TextView)findViewById(R.id.PIN_verify);
		
		best_button2 = (Button)findViewById(R.id.best_button2);
		best_button2.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		String input = PIN_verify.getText().toString();
		
		//Funktioniert das hier mit der Abfrage mit get_PIN aus PasswordDetermine
		
		try {
			data.InitializeSQLCipher(input);
			
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
		} catch (Exception e){
			Toast.makeText(this, "Ihre Eingabe ist falsch", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

	}
}
