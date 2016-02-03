package kr.mintech.sleep.tight.activities.popups;

import java.util.ArrayList;
import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.MainActivity;
import kr.mintech.sleep.tight.test.TestTrunk;
import kr.mintech.sleep.tight.utils.DateToPositionUtil;
import kr.mintech.sleep.tight.utils.Pie;
import Util.Logg;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimeActivitySelectActivity extends Activity {
	private TimePicker _endTimePicker;
	private TextView _startTimeText;
	private int _startTime;
	
	private Spinner _activitySelectSpinner;
	
	private Button _btnCancel, _btnOk;
	
	@SuppressWarnings("unused")
	private ArrayList<String> _activityList;
	
	private String startTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_time_activity_select);
		
		Pie.getInst().activity = null;
		Pie.getInst().endTime = null;
		Pie.getInst().startTime = null;
		
		_startTimeText = (TextView) findViewById(R.id.text_start_time);
		_endTimePicker = (TimePicker) findViewById(R.id.time_picker);
		_btnCancel = (Button) findViewById(R.id.btn_time_activity_cancel);
		_btnOk = (Button) findViewById(R.id.btn_time_activity_select);
		_activitySelectSpinner = (Spinner) findViewById(R.id.spinner_item_select);
		
		_endTimePicker.setOnTimeChangedListener(timePickerChangedListener);
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int min = Calendar.getInstance().get(Calendar.MINUTE);
		updateDisplay(_endTimePicker, hour, min);
		
		_btnCancel.setOnClickListener(cancelBtnClickListener);
		_btnOk.setOnClickListener(okBtnClickListener);
		
		_startTime = getIntent().getIntExtra("starttime", 0);
		
		settingTimePicker();
		settingActivitySpinner();
	}
	
	private TimePicker.OnTimeChangedListener timePickerChangedListener = new TimePicker.OnTimeChangedListener() {
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
			updateDisplay(view, hourOfDay, minute);
		}
	};
	
	private TimePicker.OnTimeChangedListener mNullTimeChangedListener = new TimePicker.OnTimeChangedListener() {
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		}
	};
	
	
	private void settingTimePicker() {
		startTime = DateToPositionUtil.PositionToTime(_startTime);
		_startTimeText.setText("Start Time " + startTime);
	}
	
	
	private void settingActivitySpinner() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		ArrayAdapter kSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, TestTrunk.getInst().activiesLst);
		_activitySelectSpinner.setAdapter(kSpinnerAdapter);
	}
	
	/*
	 * Listener
	 */
	private OnClickListener cancelBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	
	private OnClickListener okBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int kPickHour = _endTimePicker.getCurrentHour();
			int kPickMinute = _endTimePicker.getCurrentMinute();
			
			Logg.w("TimeActivitySelectActivity | enclosing_method()", "What time? Hour: " + kPickHour + "Minute: " + kPickMinute);
			
			String kActivity = _activitySelectSpinner.getSelectedItem().toString();
			Logg.w("TimeActivitySelectActivity | enclosing_method()", "Type of the activity selected : " + kActivity);
			
			Pie.getInst().startTime = startTime;
			Pie.getInst().endTime = kPickHour + ":" + kPickMinute;
			Pie.getInst().activity = kActivity;
			
			Intent kIntent = new Intent(TimeActivitySelectActivity.this, MainActivity.class);
			kIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(kIntent);
			finish();
		}
	};
	
	
	private void updateDisplay(TimePicker timePicker, int hourOfDay, int minute) {
		int nextMinute = 0;
		
		timePicker.setOnTimeChangedListener(mNullTimeChangedListener);
		
		if (minute >= 45 && minute <= 59)
			nextMinute = 45;
		else if (minute >= 30)
			nextMinute = 30;
		else if (minute >= 15)
			nextMinute = 15;
		else if (minute > 0)
			nextMinute = 0;
		else {
			nextMinute = 45;
		}
		
		if (minute - nextMinute == 1) {
			if (minute >= 45 && minute <= 59)
				nextMinute = 00;
			else if (minute >= 30)
				nextMinute = 45;
			else if (minute >= 15)
				nextMinute = 30;
			else if (minute > 0)
				nextMinute = 15;
			else {
				nextMinute = 15;
			}
		}
		timePicker.setCurrentMinute(nextMinute);
		timePicker.setOnTimeChangedListener(timePickerChangedListener);
	}
}