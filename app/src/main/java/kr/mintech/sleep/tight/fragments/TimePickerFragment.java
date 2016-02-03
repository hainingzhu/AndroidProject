package kr.mintech.sleep.tight.fragments;

import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.utils.DateTime;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class TimePickerFragment extends DialogFragment 
	implements TimePickerDialog.OnTimeSetListener
{
	public interface TimePickerListener
	{
		public void onDialogTimeSet(DialogFragment dialog, DateTime dt);
	}
	
	TimePickerListener mListener;
	
	private String currentTime = "";
	private DateTime dtFromTimePicker;
	
	
	public void setTime(String time)
	{
		currentTime = time;
	}
	
	
	public DateTime getDateTime()
	{
		return dtFromTimePicker;
	}
	
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			mListener = (TimePickerListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dtFromTimePicker = new DateTime();
		dtFromTimePicker.setTime(SleepTightConstants.AMPM_TimeFormat, currentTime);
		
		int hour = dtFromTimePicker.get(Calendar.HOUR_OF_DAY);
		if (hour < 12) {
			hour += 12;
		}
		int minute = dtFromTimePicker.get(Calendar.MINUTE);
//		minute = minute - (minute % 5);
		
		return new TimePickerDialog(getActivity(), this, hour, minute, false);
	}
	
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
		DateTime dt = new DateTime();
		dt.setTime(hourOfDay, minute);
		
		mListener.onDialogTimeSet(this, dt);
	}
}