package de.hawlandshut.rueckfallprophylaxe.ui;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

@SuppressLint("ValidFragment")
public class DiaryDatePickerFragment 
	extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	private DiaryNewEntryActivity newEntryActivity;
	public Date selectedDate;

	public DiaryDatePickerFragment(DiaryNewEntryActivity newEntryActivity) {
		this.newEntryActivity = newEntryActivity;
		setCurrentDate();
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR)-1900;
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	

	public Date getSelectedDate() {
		return selectedDate;
	}
	
	public void setCurrentDate() {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR)-1900;
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		Date date = new Date(year, month, day);
		selectedDate = date;
	}


	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		try {
			this.newEntryActivity.fillInputField_date(day, month, year);
			@SuppressWarnings("deprecation")
			Date date = new Date(year, month, day);
			selectedDate = date;
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
