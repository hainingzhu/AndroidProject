package kr.mintech.sleep.tight.activities.sleepdiarys;

import java.util.ArrayList;
import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.popups.DeleteTracksActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.sleepdiarys.SleepController;
import kr.mintech.sleep.tight.fragments.DateSpinnerTimeActivity;
import kr.mintech.sleep.tight.fragments.DurationPickerFragment;
import kr.mintech.sleep.tight.fragments.TimePickerFragment;
import kr.mintech.sleep.tight.fragments.DurationPickerFragment.DurationPickerListener;
import kr.mintech.sleep.tight.fragments.TimePickerFragment.TimePickerListener;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.services.SleepTightService;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.utils.Util;
import kr.mintech.sleep.tight.views.TimeLineCanvasView;
import Util.Logg;
import Util.PopupUtil;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class AddSleepDiaryActivity extends FragmentActivity 
	implements TimePickerListener, DurationPickerListener, TextWatcher, OnClickListener {
	
	private TextView _beforeBedAct, _beforeSleepDisturb;
	
	private Button timeButton = null;
	private Button toBedTimeButton = null;
	private Button wakeTimeButton = null;
	private Button outBedTimeButton = null;
	private Button durationButton = null;
	private Button minAsleepButton = null;
	private Button minAwakeButton = null;
	
	private DateTime dtToBedTime = null;
	private DateTime dtSleepTime = null;
	private DateTime dtWakeTime = null;
	private DateTime dtOutBedTime = null;
//	private long lSleepDuration = 0;
	
	private int hrAsleepDuration = 0;
	private int minAsleepDuration = 0;
	private int hrAwakeDuration = 0;
	private int minAwakeDuration = 0;
	
	private RadioGroup rgSleepQuality;
	private int sleepQuality;
	
	private ArrayList<String> selDisturbance = new ArrayList<String>();
	private ArrayList<String> selActivity = new ArrayList<String>();
	private DateTime dDiaryDate = null;
	private EditText et_numWakening = null;
	
	private SleepController _sleepCtrl;
	public static boolean _changeWakeTime = false;
	private int _sleepLetency = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_add_sleep_diary);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	
		String source = Pie.getInst().widgetLocation;
		Pie.getInst().wakeTime = null;
		Pie.getInst().outBedTime = null;
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_wtdiary);
		layout.requestFocus();
		
		_sleepCtrl = new SleepController(onRequestEndListener);
		
		et_numWakening = (EditText) findViewById(R.id.edittextNumWakening);
		et_numWakening.addTextChangedListener(this);
		
		_beforeBedAct = (TextView) findViewById(R.id.text_before_bed_act);
		_beforeSleepDisturb = (TextView) findViewById(R.id.text_sleep_disturb);
		Pie.getInst().beforeBedActArr = new ArrayList<String>();
		Pie.getInst().sleepDisturbArr = new ArrayList<String>();
		Pie.getInst().beforeBedActIdArr = new ArrayList<String>();
		Pie.getInst().sleepDisturbIdArr = new ArrayList<String>();
		
		toBedTimeButton = (Button) findViewById(R.id.buttonToBedTime);
		wakeTimeButton = (Button) findViewById(R.id.buttonWakeUpTime);
		outBedTimeButton = (Button) findViewById(R.id.buttonOutOfBed);
		wakeTimeButton.setOnClickListener(timeBtnClickListener);
		outBedTimeButton.setOnClickListener(timeBtnClickListener);
		
		minAsleepButton = (Button) findViewById(R.id.buttonSleepLatency);
		minAwakeButton = (Button) findViewById(R.id.buttonTimeStayedAwake);
		rgSleepQuality = (RadioGroup) findViewById(R.id.radiogroup_sleepquality);
		
		dDiaryDate = new DateTime(0, 0);
		dDiaryDate.add(Calendar.DATE, -1);
		String strDiaryDate = dDiaryDate.getStringTime(SleepTightConstants.LastEditDateFormat); // "Thu, Dec 19 2012"
		setTitle("Add sleep for " + strDiaryDate);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		EventLogger.log("pause", 
				"what", "add_sleep");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		EventLogger.log("resume", 
				"what", "add_sleep");		
	}
	
	@Override
	public void onBackPressed()	{
		super.onBackPressed();
		EventLogger.log("cancel", 
				"what", "add_sleep");
		finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case 0:			
					if (Pie.getInst().beforeBedActArr != null) {
						String kRemakeStr = Pie.getInst().beforeBedActArr.toString().replace("[", "");
						String kDisplayStr = kRemakeStr.replace("]", "");
						_beforeBedAct.setText(kDisplayStr);
					}
				break;
				
				case 1:
					if (Pie.getInst().sleepDisturbArr != null) {
						String kRemakeStr = Pie.getInst().sleepDisturbArr.toString().replace("[", "");
						String kDisplayStr = kRemakeStr.replace("]", "");
						_beforeSleepDisturb.setText(kDisplayStr);
					}		
				break;
				
				case 2:
					DateTime time = Pie.getInst().wakeTime;
					if (time != null) {
						long diff = time.getTimeInMillis() - dtToBedTime.getTimeInMillis();
						if (diff < 0) {
							Pie.getInst().wakeTime = dtWakeTime;
							String toastMessage = "Wake up time should be later than the to bed time";
							Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();					
						} else {
							double hour = diff / 1000 / 60/ 60;
							if (hour >= 12) {
								PopupUtil.showMessageDialog(this, "Sleep Duration", "Please confirm that your sleep duration is more than 12 hours.", confirmListener, "Save to Confirm", null, "Cancel");
							} else {
								dtWakeTime = Pie.getInst().wakeTime;
								dtOutBedTime = dtWakeTime;
								Pie.getInst().outBedTime = dtOutBedTime;
								String strTime = Pie.getInst().wakeTime.getStringTime(SleepTightConstants.AMPM_TimeFormat);
								wakeTimeButton.setText(strTime);						
								outBedTimeButton.setText(strTime);						
								outBedTimeButton.setClickable(true);
								outBedTimeButton.setEnabled(true);
							}						
						}
					}
					break;
					
				case 3:
					if (Pie.getInst().outBedTime != null) {
						if (Pie.getInst().outBedTime.compareTo(dtWakeTime) < 0) {
							Pie.getInst().outBedTime = dtOutBedTime;
							String toastMessage = "Out of bed time should be later than the wake up time";
							Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
						} else {
							dtOutBedTime = Pie.getInst().outBedTime;
							String strTime = Pie.getInst().outBedTime.getStringTime(SleepTightConstants.AMPM_TimeFormat);
							outBedTimeButton.setText(strTime);
						}
					}
					break;
			}
		}
	}

	private android.content.DialogInterface.OnClickListener confirmListener = new android.content.DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			outBedTimeButton.setEnabled(true);
			dtWakeTime = Pie.getInst().wakeTime;
			dtOutBedTime = dtWakeTime;
			Pie.getInst().outBedTime = dtOutBedTime;

			String strTime = Pie.getInst().wakeTime.getStringTime(SleepTightConstants.AMPM_TimeFormat);
			wakeTimeButton.setText(strTime);						
			outBedTimeButton.setText(strTime);						
			outBedTimeButton.setClickable(true);
			outBedTimeButton.setEnabled(true);
		}
	};

	private android.view.View.OnClickListener timeBtnClickListener = new android.view.View.OnClickListener() {
		@Override
		public void onClick(View v)	{
			switch (v.getId()) {
				case R.id.buttonWakeUpTime:
					_changeWakeTime = true;
					DateSpinnerTimeActivity._isWakeTime = true;
					Intent kIntent = new Intent(AddSleepDiaryActivity.this, DateSpinnerTimeActivity.class);
					startActivityForResult(kIntent, 2);
					break;
					
				case R.id.buttonOutOfBed:
					_changeWakeTime = false;
					DateSpinnerTimeActivity._isWakeTime = false;
					Intent kOutOfBedIntent = new Intent(AddSleepDiaryActivity.this, DateSpinnerTimeActivity.class);
					startActivityForResult(kOutOfBedIntent, 3);
					break;
					
				default:
					break;
			}
		}
	};
	
//	public boolean checkDiaryValidity() {
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//		DateTime yesterday = new DateTime(0, 0);
//		yesterday.add(Calendar.DATE, -1);
//		return true;
//	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE) { // OK
			dDiaryDate = new DateTime(0, 0);
			dDiaryDate.add(Calendar.DATE, -1);
			
			String strDiaryDate = dDiaryDate.getStringTime(SleepTightConstants.LastEditDateFormat);
			setTitle("Wake Time Diary for " + strDiaryDate);
			
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.cancel(0);
		} else { // Cancel
			setResult(RESULT_CANCELED);
			finish();
		}
	}
	
	public void openTimeDialog(View view) {
		TimePickerFragment newFragment = new TimePickerFragment();
		
		timeButton = (Button) view;
		String curTime = timeButton.getText().toString();
		newFragment.setTime(curTime);
		newFragment.show(getFragmentManager(), "timePicker");
	}
	
	
	@Override
	public void onDialogTimeSet(DialogFragment dialog, DateTime time) {
		String strTime = time.getStringTime(SleepTightConstants.AMPM_TimeFormat);
		
		toBedTimeButton.setText(strTime);
		dtToBedTime = time.toToBedTime();
		minAsleepButton.setClickable(true);
		wakeTimeButton.setClickable(true);
		wakeTimeButton.setEnabled(true);
		wakeTimeButton.setText(getResources().getString(R.string.button_pick_time));
				
	}
	
	public void openDurationDialog(View view) {
		DurationPickerFragment newFragment = new DurationPickerFragment();
		
		durationButton = (Button) view;
		
		String curDuration = durationButton.getText().toString();
		DateTime time = Util.extractTime(curDuration);
		newFragment.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE));
		
		if (view.getId() == R.id.buttonSleepLatency) {
			newFragment.setHeader(R.string.header_sleepLatency);
		} else {
			newFragment.setHeader(R.string.header_timeStayedAwake);
		}
		
		newFragment.show(getFragmentManager(), "durationPicker");
	}
	
	@Override
	public void onDialogDurationSet(int hour, int minute) {
		if (durationButton == minAsleepButton) {
			minAsleepButton.setText(Util.writeTime(hour, minute));
			hrAsleepDuration = hour;
			minAsleepDuration = minute;
			_sleepLetency = hour * 60 + minute;
		} else if (durationButton == minAwakeButton) {
			minAwakeButton.setText(Util.writeTime(hour, minute));
			hrAwakeDuration = hour;
			minAwakeDuration = minute;
		}
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		View focusView = getCurrentFocus();
		if (!(focusView instanceof EditText)) {
			return;
		}
		
		EditText editText = (EditText) focusView;
		String str = editText.getText().toString();
		
		Button b = (Button) findViewById(R.id.buttonTimeStayedAwake);
		int numFrequency = Util.parseInt(str);
		b.setClickable(numFrequency > 0);
		b.setEnabled(numFrequency > 0);
		if (numFrequency == 0) {
			b.setHint(getString(R.string.button_zero_mins));
			b.setText("");
		} else {
			b.setText(getString(R.string.button_zero_mins));
		}
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	
	}
	
	public int getSleepQuality() {
		switch (rgSleepQuality.getCheckedRadioButtonId()) {
			case R.id.radio_qualty1:
				sleepQuality = 1;
				break;
				
			case R.id.radio_qualty2:
				sleepQuality = 2;
				break;
				
			case R.id.radio_qualty3:
				sleepQuality = 3;
				break;
				
			case R.id.radio_qualty4:
				sleepQuality = 4;
				break;
				
			case R.id.radio_qualty5:
				sleepQuality = 5;
				break;
		}
		return sleepQuality;
	}
	
	public void checkActivity(View v) {
		Intent intent = new Intent(this, SelectBeforeBedActivity.class);
		startActivityForResult(intent, 0);
	}
	
	public void checkDisturbance(View v) {
		Intent intent = new Intent(this, SelectSleepDisturbActivity.class);
		int length = selDisturbance.size();
		intent.putExtra(getString(R.string.count_SelDisturbance), length);
		intent.putExtra(getString(R.string.extra_fromSettings), false);
		
		for (int i = 0; i < length; i++) {
			intent.putExtra(getString(R.string.name_SelDisturbance) + Integer.toString(i), (String) selDisturbance.get(i));
		}
		startActivityForResult(intent, 1); // 1 = requestCode for "List of Sleep Disturbances" activity
	}
	
	public void onSaveClick(View v) {
//		boolean bValid = checkDiaryValidity();
//		
//		if (bValid)	{
			EditText numWakening = (EditText) findViewById(R.id.edittextNumWakening);
			Button timeStayedAwake = (Button) findViewById(R.id.buttonTimeStayedAwake);
			RadioGroup radiogroupSleepQuality = (RadioGroup) findViewById(R.id.radiogroup_sleepquality);
			
			if (toBedTimeButton.getText().toString().equals(getString(R.string.button_pick_time))) {
				Toast.makeText(this, "Please fill out the to-bed time", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (wakeTimeButton.getText().toString().equals(getString(R.string.button_pick_time))) {
				Toast.makeText(this, "Please fill out the wake up time", Toast.LENGTH_LONG).show();
				return;
			}
			
			if (radiogroupSleepQuality.getCheckedRadioButtonId() == -1)	{
				Toast.makeText(this, "Please fill out your sleep quality", Toast.LENGTH_LONG).show();
				return;
			}
			
			getSleepQuality();
			updateData();
			
			EventLogger.log("add_sleep", 
					"where", Pie.getInst().widgetLocation,
					"diary_date", dDiaryDate);
			
			setResult(RESULT_OK);
			finish();
//		}
	}
	
	
	private void updateData() {
		String strToBedTime = dtToBedTime.getStringTime(SleepTightConstants.NetworkFormat);
		String strWakeUpTime = dtWakeTime.getStringTime(SleepTightConstants.NetworkFormat);
		String strOutBedTime = dtOutBedTime.getStringTime(SleepTightConstants.NetworkFormat);		
		String strDiaryDate = dDiaryDate.getStringTime(SleepTightConstants.NetworkFormat);
		
		int kAwakeCount = 0;
		if (et_numWakening.getText().toString() != null || !et_numWakening.getText().toString().equals("")) {
			try {
				kAwakeCount = Integer.parseInt(et_numWakening.getText().toString());
			} catch (Exception e) {
				
			}
		}
		
		int awakeDuration = hrAwakeDuration * 60 + minAwakeDuration;
		_sleepCtrl.requestAddSleepInfo(strToBedTime, _sleepLetency, strWakeUpTime, strOutBedTime, strDiaryDate, sleepQuality, kAwakeCount, awakeDuration, 
				Pie.getInst().beforeBedActIdArr, Pie.getInst().sleepDisturbIdArr);
	}
	
	public void onCancelClick(View v) {
		String toastMessage = "Wake Time Diary not saved";
		Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
		
		EventLogger.log("cancel", 
				"what", "add_sleep");

		setResult(RESULT_CANCELED);
		finish();
	}
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener()
	{
		@Override
		public void onRequestEnded(int $tag, Object $object)
		{
			switch ($tag)
			{
				case NumberConst.requestSuccess:
					// CLEANUP // Logg.w("AddSleepDiaryView | enclosing_method()", "Sleep info: saved successfully!");
					Toast.makeText(AddSleepDiaryActivity.this, "Successfully saved the sleep diary.", Toast.LENGTH_SHORT).show();
					SleepTightService kService = new SleepTightService();
					kService.widgetUpdate();
					break;
					
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddSleepDiaryView | enclosing_method()", "Sleep info: not saved!");
					Toast.makeText(AddSleepDiaryActivity.this, "We could not save the sleep diary. Please try again later.", Toast.LENGTH_SHORT).show();
					break;
			}
		}
		
		
		@Override
		public void onRequest(Object $unit)
		{
		}
		
		
		@Override
		public void onRequestError(int $tag, String $errorStr)
		{
		}
	};
}
