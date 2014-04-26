package de.hawlandshut.rueckfallprophylaxe.ui;

import android.app.Application;

public class PinShare extends Application {
	 private String pin; //make getter and setter
	    
	 public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	private static PinShare singleInstance = null;

	    public static PinShare getInstance()
	    {
	        return singleInstance;
	    }

	    @Override
	    public void onCreate() {
	        super.onCreate();
	        singleInstance = this;
	    }
	    
}
