package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Das hier ist die Klasse wo der Benutzer bei jedem nutzen der App die PIN eingeben muss. 
public class Password_verify extends Activity implements OnClickListener {
	
	TextView PIN_verify;
	Button best_button2;
	PasswordDetermine pass;

	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_verify);
		
		
		PIN_verify = (TextView)findViewById(R.id.PIN_verify);
		
		best_button2 = (Button)findViewById(R.id.best_button2);
		best_button2.setOnClickListener(this);
	}


	
	public void onClick(View v) {
		int input3 = Integer.parseInt(PIN_verify.getText().toString());
		
		//Funktioniert das hier mit der Abfrage mit get_PIN aus PasswordDetermine
		
		 if(input3 == pass.get_PIN())
		 {
		  	Intent intent = new Intent(this, Startseite.class);
		    startActivity(intent);
		 }
		 else
		 {
		 	Toast.makeText(this, "Ihre Eingabe ist falsch", Toast.LENGTH_LONG).show();
		 }
			
		 
	}
}
