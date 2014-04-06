package de.hawlandshut.rueckfallprophylaxe.data;

public class EmergencyCase {

	private int id;
	private String addictDrugholtine;
	private String propAdvice;
	private String myTherapist;
	private String emergencyCasecol;
	private String riskDanger;
	private String riskSituation;
	private String riskTemptation;
	private String temptationThought;
	private String temptationThoughtAbstinence;
	private String temptationBehaviour;

	public EmergencyCase(int id, String addictDrugholtine, String propAdvice,
			String myTherapist, String emergencyCasecol, String riskDanger,
			String riskSituation, String riskTemptation,
			String temptationThought, String temptationThoughtAbstinence,
			String temptationBehaviour) {
		this.id = id;
		this.addictDrugholtine = addictDrugholtine;
		this.propAdvice = propAdvice;
		this.myTherapist = myTherapist;
		this.emergencyCasecol = emergencyCasecol;
		this.riskDanger = riskDanger;
		this.riskSituation = riskSituation;
		this.riskTemptation = riskTemptation;
		this.temptationThought = temptationThought;
		this.temptationThoughtAbstinence = temptationThoughtAbstinence;
		this.temptationBehaviour = temptationBehaviour;
	}

	public int getId() {
		return id;
	}

	public String getAddictDrugholtine() {
		return addictDrugholtine;
	}

	public String getPropAdvice() {
		return propAdvice;
	}

	public String getMyTherapist() {
		return myTherapist;
	}

	public String getEmergencyCasecol() {
		return emergencyCasecol;
	}

	public String getRiskDanger() {
		return riskDanger;
	}

	public String getRiskSituation() {
		return riskSituation;
	}

	public String getRiskTemptation() {
		return riskTemptation;
	}

	public String getTemptationThought() {
		return temptationThought;
	}

	public String getTemptationThoughtAbstinence() {
		return temptationThoughtAbstinence;
	}

	public String getTemptationBehaviour() {
		return temptationBehaviour;
	}

}
