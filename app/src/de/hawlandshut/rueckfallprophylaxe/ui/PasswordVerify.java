package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * TODO: PS: Bitte kommentieren -> JavaDoc's
 *
 */
//Das hier ist die Klasse wo der Benutzer bei jedem nutzen der App die PIN eingeben muss. 
public class PasswordVerify extends Activity implements OnClickListener {
	
	TextView PIN_verify;
	Button best_button2;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_verify);
		
		PIN_verify = (TextView)findViewById(R.id.PIN_verify);
		
		best_button2 = (Button)findViewById(R.id.best_button2);
		best_button2.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		//int input3 = Integer.parseInt(PIN_verify.getText().toString());
		
		//Funktioniert das hier mit der Abfrage mit get_PIN aus PasswordDetermine
		/*
		 if(input3 == Hier wird dann mit einer DB-Abfrage die PIN geholt und gepr�ft ob 
		 			//die Eingabe richtig war. Sobald ich das kapiert habe wie das geht mache
		 			//ich das.)
		 {
		  	Intent intent = new Intent(this, Startseite.class);
		    startActivity(intent);
		 }
		 else
		 {
		 	Toast.makeText(this, "Ihre Eingabe ist falsch", Toast.LENGTH_LONG).show();
		 }
		 */
	}
}
