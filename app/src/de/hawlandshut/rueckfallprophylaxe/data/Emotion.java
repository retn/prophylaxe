package de.hawlandshut.rueckfallprophylaxe.data;

import java.util.List;

public class Emotion {

	int id;
	String name;
	List<Distraction> distractions;

	public Emotion(int id, String name, List<Distraction> distractions) {
		this.id = id;
		this.name = name;
		this.distractions = distractions;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Distraction> getDistractions() {
		return distractions;
	}

}
