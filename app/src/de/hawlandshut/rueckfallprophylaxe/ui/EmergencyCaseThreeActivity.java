package de.hawlandshut.rueckfallprophylaxe.ui;

import java.util.ArrayList;
import java.util.List;
import de.hawlandshut.rueckfallprophylaxe.data.ControllerData;
import de.hawlandshut.rueckfallprophylaxe.data.EmergencyCase;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
		
		String text=getString(R.string.mein_e_therapeut_in_)+case0.getMyTherapist()+"\n\n";
		textView.append(text);
		
		textView= (TextView) findViewById(R.id.ec_text_2);
		text=getString(R.string.risiko_);
		textView.append(text);
		textView.setTextSize(18);
		text=getString(R.string.ich_bin_in_gefahr_wieder_zur_flasche_zu_greifen_wenn_ich_)+case0.getRiskDanger()+"\n";
		text=text+getString(R.string.diese_situation_ist_schwierig_f_r_mich_weil_)+case0.getRiskSituation()+"\n";
		text=text+getString(R.string._versuchung_der_kleine_teufel_auf_meiner_schulter_fl_stert_mir_zu_trink_doch_ein_gl_schen_dann_)+case0.getRiskTemptation()+"\n\n";
		textView.append(text);
		
		textView= (TextView) findViewById(R.id.ec_text_3);
		text=getString(R.string.der_versuchung_widerstehen_);
		textView.append(text);
		textView.setTextSize(18);
		text=getString(R.string._bew_ltigungsgedanken_der_kleine_engel_auf_meiner_schulter_sagt_mir_nein_wenn_du_trinkst_dann_)+case0.getTemptationThought()+"\n";
		text=text+getString(R.string.in_deiner_abstinenz_hast_du_schon_viel_erreicht_z_b_)+case0.getTemptationThoughtAbstinence()+"\n";
		text=text+getString(R.string._bew_ltigungsverhalten_was_kann_ich_tun_um_meine_abstinenz_zu_sch_tzen_)+case0.getTemptationBehaviour()+"\n";
		textView.append(text);
	}

	//opens tel number and let you call it
	private void setHotlines(final EmergencyCase case0) {
		
        Button hotlineButton=(Button)findViewById(R.id.hotline_button);
        hotlineButton.setText(R.string.sucht_drogenhotline_anrufen_+case0.getAddictDrugholtine());
        hotlineButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse(getString(R.string.tel_)+case0.getAddictDrugholtine()));
				startActivity(intent);
			}
		});
        
        Button adviceButton=(Button)findViewById(R.id.advice_button);
        adviceButton.setText(R.string.prop_beratungstelle_anrufen_+case0.getPropAdvice());
        adviceButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL);
				intent.setData(Uri.parse(getString(R.string.tel_)+case0.getPropAdvice()));
				startActivity(intent);
				
			}
		});

		
		
	}
	

}
