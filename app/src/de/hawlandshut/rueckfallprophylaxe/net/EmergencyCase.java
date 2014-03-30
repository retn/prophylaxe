package de.hawlandshut.rueckfallprophylaxe.net;

import java.util.List;

/**
 * This class is only used for storing data from the JSON-String. It only
 * contains variables and Getters/Setters.
 * 
 * @author Patrick
 * @see JsonData
 */
public class EmergencyCase {

	private int ecID;
	private String patientID_fk;
	private String addict_drughotline;
	private String prop_advice;
	private String my_therapist;
	private String risk_danger;
	private String risk_situation;
	private String risk_temptation;
	private String temptation_thought;
	private String temptation_thought_abstinence;
	private String temptation_behaviour;
	private List<RiskSituation> risk_situations_array;
	private List<LimitRelapse> limit_relapses_array;
	private List<SafetyThought> safety_thoughts_array;
	private List<SafetyAction> safety_actions_array;
	private List<HelpPerson> help_persons_array;

	public EmergencyCase() {

	}

	public int getEcID() {
		return ecID;
	}

	public void setEcID(int ecID) {
		this.ecID = ecID;
	}

	public String getPatientID_fk() {
		return patientID_fk;
	}

	public void setPatientID_fk(String patientID_fk) {
		this.patientID_fk = patientID_fk;
	}

	public String getAddict_drughotline() {
		return addict_drughotline;
	}

	public void setAddict_drughotline(String addict_drughotline) {
		this.addict_drughotline = addict_drughotline;
	}

	public String getProp_advice() {
		return prop_advice;
	}

	public void setProp_advice(String prop_advice) {
		this.prop_advice = prop_advice;
	}

	public String getMy_therapist() {
		return my_therapist;
	}

	public void setMy_therapist(String my_therapist) {
		this.my_therapist = my_therapist;
	}

	public String getRisk_danger() {
		return risk_danger;
	}

	public void setRisk_danger(String risk_danger) {
		this.risk_danger = risk_danger;
	}

	public String getRisk_situation() {
		return risk_situation;
	}

	public void setRisk_situation(String risk_situation) {
		this.risk_situation = risk_situation;
	}

	public String getRisk_temptation() {
		return risk_temptation;
	}

	public void setRisk_temptation(String risk_temptation) {
		this.risk_temptation = risk_temptation;
	}

	public String getTemptation_thought() {
		return temptation_thought;
	}

	public void setTemptation_thought(String temptation_thought) {
		this.temptation_thought = temptation_thought;
	}

	public String getTemptation_thought_abstinence() {
		return temptation_thought_abstinence;
	}

	public void setTemptation_thought_abstinence(
			String temptation_thought_abstinence) {
		this.temptation_thought_abstinence = temptation_thought_abstinence;
	}

	public String getTemptation_behaviour() {
		return temptation_behaviour;
	}

	public void setTemptation_behaviour(String temptation_behaviour) {
		this.temptation_behaviour = temptation_behaviour;
	}

	public List<RiskSituation> getRisk_situations_array() {
		return risk_situations_array;
	}

	public void setRisk_situations_array(
			List<RiskSituation> risk_situations_array) {
		this.risk_situations_array = risk_situations_array;
	}

	public List<LimitRelapse> getLimit_relapses_array() {
		return limit_relapses_array;
	}

	public void setLimit_relapses_array(List<LimitRelapse> limit_relapses_array) {
		this.limit_relapses_array = limit_relapses_array;
	}

	public List<SafetyThought> getSafety_thoughts_array() {
		return safety_thoughts_array;
	}

	public void setSafety_thoughts_array(
			List<SafetyThought> safety_thoughts_array) {
		this.safety_thoughts_array = safety_thoughts_array;
	}

	public List<SafetyAction> getSafety_actions_array() {
		return safety_actions_array;
	}

	public void setSafety_actions_array(List<SafetyAction> safety_actions_array) {
		this.safety_actions_array = safety_actions_array;
	}

	public List<HelpPerson> getHelp_persons_array() {
		return help_persons_array;
	}

	public void setHelp_persons_array(List<HelpPerson> help_persons_array) {
		this.help_persons_array = help_persons_array;
	}

}
