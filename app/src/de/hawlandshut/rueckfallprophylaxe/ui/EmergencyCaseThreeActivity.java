package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.ArrayList;
import java.util.List;

import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.EmergencyCase;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created with IntelliJ IDEA.
 * User: Anton
 * Date: 07.05.14
 * Time: 20:23
 * To change this template use File | Settings | File Templates.
 */

public class EmergencyCaseThreeActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_3);
        
		this.setCase();
	}
	
	private void setCase() {
		List<EmergencyCase> cases=new ArrayList<EmergencyCase>();
		cases = ControllerData.getEmergencyCase();
		
		EmergencyCase case0=cases.get(0); 
		
		setHotlines(case0);
		setText(case0);
	}

	private void setText(EmergencyCase case0) {
		TextView textView= (TextView) findViewById(R.id.ec_text_1);
		textView.setTextSize(20);
		
		String text="mein/e Therapeut/in: "+case0.getMyTherapist()+"\n\n";
		textView.append(text);
		
		textView= (TextView) findViewById(R.id.ec_text_2);
		text="Risiko \n";
		textView.append(text);
		textView.setTextSize(18);
		text="Ich bin in Gefahr, wieder zur Flasche zu greifen:\n wenn ich ... "+case0.getRiskDanger()+"\n";
		text=text+"Diese Situation ist schwierig für mich, weil ... : "+case0.getRiskSituation()+"\n";
		text=text+"\"Versuchung\": Der \"kleine Teufel\" auf meiner Schulter flüstert mir zu:\n \"Trink doch ein Gläschen, dann ... "+case0.getRiskTemptation()+"\n\n";
		textView.append(text);
		
		textView= (TextView) findViewById(R.id.ec_text_3);
		text="Der Versuchung widerstehen \n";
		textView.append(text);
		textView.setTextSize(18);
		text="(Bewältigungsgedanken)  Der \"kleine Engel\" auf meiner Schulter sagt mir: \n \"Nein, wenn du trinkst, dann ... "+case0.getTemptationThought()+"\n";
		text=text+"In deiner Abstinenz hast du schon viel erreicht, z.B. ..."+case0.getTemptationThoughtAbstinence()+"\n";
		text=text+"(Bewältigungsverhalten) \n Was kann ich tun, um meine Abstinenz zu schützen? ...  "+case0.getTemptationBehaviour()+"\n";
		textView.append(text);
	}

	private void setHotlines(final EmergencyCase case0) {
		
        Button hotlineButton=(Button)findViewById(R.id.hotline_button);
        hotlineButton.setText("Sucht-+Drogenhotline anrufen\n"+case0.getAddictDrugholtine());
        hotlineButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"+case0.getAddictDrugholtine()));
				startActivity(intent);
			}
		});
        
        Button adviceButton=(Button)findViewById(R.id.advice_button);
        adviceButton.setText("Prop Beratungstelle anrufen\n"+case0.getPropAdvice());
        adviceButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse("tel:"+case0.getPropAdvice()));
				startActivity(intent);
				
			}
		});

		
		
	}
	

}
