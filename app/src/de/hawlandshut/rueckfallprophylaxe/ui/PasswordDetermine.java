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
		String input1 = PIN_entry1.getText().toString();
		String input2 = PIN_entry2.getText().toString();
		
		if(!input1.equals(input2))
		{
			Toast.makeText(this, "Ihre erste Eingabe stimmt nicht mit der zweiten �berrein", Toast.LENGTH_LONG).show();
		}
		else if(!input1.matches("[0-9][0-9][0-9][0-9]"))
		{
			Toast.makeText(this,"Bitte einen vierstelligen PIN eingeben", Toast.LENGTH_LONG).show();
		}
		else
		{
			Database db = new Database(this);
			db.InitializeSQLCipher(input1);
			db.close();
			Intent intent = new Intent(this, PasswordVerify.class);
			startActivity(intent);
		}
	}
}
