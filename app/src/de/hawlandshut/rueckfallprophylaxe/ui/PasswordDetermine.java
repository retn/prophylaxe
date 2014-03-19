package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//@Stefan Roth
//Das hier ist die Seite wo der Benutzer aufgefordert wird einen PIN zu erstellen, die
//nur beim ersten Aufruf der App benutzt wird. Sobald ich wei� wie ich diese Seite hier
//zur Startseite der App mache und zwar so das sie das nur beim ersten mal macht 
//werde ich das machen.
public class PasswordDetermine extends Activity implements OnClickListener {
	
	TextView PIN_entry1, PIN_entry2;
	Button best_button;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_determine);
		
		
		PIN_entry1 =(TextView)findViewById(R.id.PIN_entry1);
		PIN_entry2 =(TextView)findViewById(R.id.PIN_entry2);
		
		best_button =(Button)findViewById(R.id.best_button);
		
		best_button.setOnClickListener(this);
	}

	public void onClick(View v) {
		/*
		 * TODO: 
		 *  - Aus PIN_Entry den Text holen, alle Buchstaben löschen
		 *  - Die Länge überprüfen des Strings > 4 Stellen -> OK weiter prüfen
		 */
		int input1 = Integer.parseInt(PIN_entry1.getText().toString());
		int input2 = Integer.parseInt(PIN_entry2.getText().toString());
		
		if(input1 != input2)
		{
			Toast.makeText(this, "Ihre erste Eingabe stimmt nicht mit der zweiten �berrein", Toast.LENGTH_LONG).show();
		}
		else if(input1 < 1000 || input2 < 1000 || input1 > 9999 || input2 > 9999)
		{
			Toast.makeText(this,"Bitte einen vierstelligen PIN eingeben", Toast.LENGTH_LONG).show();
		}
		else
		{
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			//Hier wird sobald ich wei� wie ich das in die DB speichere input1 als PIN in der DB
			//gespeichert.
		}
	}
}