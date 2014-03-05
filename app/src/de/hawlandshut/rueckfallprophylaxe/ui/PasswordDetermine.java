package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Das hier ist die Seite wo der Benutzer aufgefordert wird einen PIN zu erstellen, die
//nur beim ersten Aufruf der App benutzt wird. Sobald ich weiß wie ich diese Seite hier
//zur Startseite der App mache und zwar so das sie das nur beim ersten mal macht 
//werde ich das machen.
public class PasswordDetermine extends Activity implements OnClickListener {
	
	TextView PIN_entry1, PIN_entry2;
	Button best_button;
	int PersonalIdentificationNumber;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_determine);
		
		
		PIN_entry1 =(TextView)findViewById(R.id.PIN_entry1);
		PIN_entry2 =(TextView)findViewById(R.id.PIN_entry2);
		
		best_button =(Button)findViewById(R.id.best_button);
		
		best_button.setOnClickListener(this);
	}

	public void onClick(View v) {
		int input1 = Integer.parseInt(PIN_entry1.getText().toString());
		int input2 = Integer.parseInt(PIN_entry2.getText().toString());
		
		if(input1 != input2)
		{
			Toast.makeText(this, "Ihre erste Eingabe stimmt nicht mit der zweiten überrein", Toast.LENGTH_LONG).show();
		}
		else if(input1 < 1000 || input2 < 1000 || input1 > 9999 || input2 > 9999)
		{
			Toast.makeText(this,"Bitte einen vierstelligen PIN eingeben", Toast.LENGTH_LONG).show();
		}
		else
		{
			Intent intent = new Intent(this, Startseite.class);
			startActivity(intent);
			PersonalIdentificationNumber = input1;
		}
		
	}
	
	int get_PIN()
	{
		return PersonalIdentificationNumber;
	}
}
