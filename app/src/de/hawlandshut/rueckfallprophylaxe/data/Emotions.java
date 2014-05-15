package de.hawlandshut.rueckfallprophylaxe.data;

import android.util.Log;

/**
 * 
 * @author robinhood
 * Contains all valid emotions
 */
public class Emotions {

	private int[] ids;
	private String[] names;
	
	public Emotions() {
		// Fill with hard coded data
		names = new String[7];
		names[0] = "Angst";
		names[1] = "Aggression";
		names[2] = "Einsamkeit";
		names[3] = "Frust";
		names[4] = "Gut";
		names[5] = "Langeweile";
		names[6] = "Suchtdruck";
		
		ids = new int[7];
		ids[0] = 1;
		ids[1] = 2;
		ids[2] = 3;
		ids[3] = 4;
		ids[4] = 5;
		ids[5] = 6;
		ids[6] = 7;
	}
	
	/**
	 * Returns ID for given emotion name
	 * Returns 0 if name cannot be found
	 * @param searchName
	 * @return
	 */
	public int getIDbyName(String searchName) {
		
		Log.d("getIDbyName", "Suche nach "+searchName);
		
		
		int i = 0;
		for (String name:names) {
			if (searchName.equals(name)) {
				i++;
				Log.d("getIDbyName", "gefunden "+i);
				return i;
			}
			
			i++;
		}
		
		Log.d("getIDbyName", "NICHT gefunden");
		return 0;
	}
	
	/**
	 * Returns emotion name of a certain id
	 * @param id
	 * @return
	 */
	public String getNameByID(int id) {
		return names[id-1];
	}
	
	
}
