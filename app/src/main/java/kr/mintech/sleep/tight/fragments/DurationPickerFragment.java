package kr.mintech.sleep.tight.fragments;

import kr.mintech.sleep.tight.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class DurationPickerFragment extends DialogFragment
{
	
	public interface DurationPickerListener
	{
		public void onDialogDurationSet(int hour, int minute);
	}
	
	DurationPickerListener mListener;
	
	private int currentMinute = 0;
	private int currentHour = 0;
	private int currentHeader;
	
	
	public void setTime(int hour, int minute)
	{
		currentHour = hour;
		currentMinute = minute;
	}
	
	
	public void setHeader(int header)
	{
		currentHeader = header;
	}
	
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try
		{
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (DurationPickerListener) activity;
		} catch (ClassCastException e)
		{
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
	}
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		View v = inflater.inflate(R.layout.fragment_dialog_duration, null);
		final NumberPicker npHour = (NumberPicker) v.findViewById(R.id.npDurationHour);
		final NumberPicker npMinute = (NumberPicker) v.findViewById(R.id.npDurationMinute);
		
		builder.setView(v)
		// Add action buttons
				.setPositiveButton(R.string.button_set, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						
						int hour = npHour.getValue();
						int minute = npMinute.getValue();
						mListener.onDialogDurationSet(hour, minute);
					}
				}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						DurationPickerFragment.this.getDialog().cancel();
					}
				});
		builder.setTitle(currentHeader);
		
		npHour.setMaxValue(24);
		npHour.setMinValue(0);
		npHour.setWrapSelectorWheel(true);
		
		npMinute.setMaxValue(59);
		npMinute.setMinValue(0);
		npMinute.setWrapSelectorWheel(true);
		
		try
		{
			npHour.setValue(currentHour);
			npMinute.setValue(currentMinute);
		} catch (Exception e)
		{
			
		}
		return builder.create();
	}
}
