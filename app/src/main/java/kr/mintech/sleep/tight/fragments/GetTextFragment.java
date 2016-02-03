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
import android.view.WindowManager;
import android.widget.EditText;

public class GetTextFragment extends DialogFragment
{
	public interface GetTextListener
	{
		public void onDialogTextSet(String setText);
	}
	
	GetTextListener mListener;
	
	private AlertDialog dialog = null;
	private String currentText = "";
	private int currentHeader;
	
	
	public void setText(String text)
	{
		currentText = text;
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
			mListener = (GetTextListener) activity;
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
		View v = inflater.inflate(R.layout.fragment_get_text, null);
		final EditText etGetText = (EditText) v.findViewById(R.id.edittextGetTextDialog);
		
		builder.setView(v)
		// Add action buttons
				.setPositiveButton(R.string.button_add, new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						
						String getText = etGetText.getText().toString();
						mListener.onDialogTextSet(getText);
					}
				}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						GetTextFragment.this.getDialog().cancel();
					}
				});
		builder.setTitle(currentHeader);
		
		try
		{
			etGetText.setText(currentText);
		} catch (Exception e)
		{
			
		}
		
		// etGetText.setOnFocusChangeListener(this);
		
		dialog = builder.create();
		dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		return dialog;
	}
}
