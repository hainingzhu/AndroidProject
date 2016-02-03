package kr.mintech.sleep.tight.fragments;

import java.util.ArrayList;
import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.Pie;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateSpinnerTimeActivity extends Activity {
	private Spinner _spinner;
	private TimePicker _timePicker;
	private Button _okBtn, _cancelBtn;
	
	private ArrayList<String> _dateTimeArr;
	private DateTime _day, _prevDay;
	
	public static boolean _isWakeTime = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_date_time);
		
		_day = new DateTime();
		
		_spinner = (Spinner) findViewById(R.id.spinner_date);
		_timePicker = (TimePicker) findViewById(R.id.time_picker);
		_okBtn = (Button) findViewById(R.id.btn_ok);
		_cancelBtn = (Button) findViewById(R.id.btn_cancel);
		
		String kCurrentString = _day.getStringTime(SleepTightConstants.TitleDayFormat);
		_prevDay = new DateTime(_day);
		_prevDay = _prevDay.toPrevDay(_prevDay);
		String kPrevDateString = _prevDay.getStringTime(SleepTightConstants.TitleDayFormat);
		
		_dateTimeArr = new ArrayList<String>();
		_dateTimeArr.add(kCurrentString);
		_dateTimeArr.add(kPrevDateString);
		
		ArrayAdapter kSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, _dateTimeArr);
		_spinner.setAdapter(kSpinnerAdapter);
		
		if (_isWakeTime) {
			if (Pie.getInst().wakeTime == null) {
				int hour = _day.get(Calendar.HOUR_OF_DAY);
				if (hour > 12) {
					hour -= 12;
				}
				_timePicker.setCurrentHour(hour);
				_timePicker.setCurrentMinute(_day.get(Calendar.MINUTE));
			} else {
				_timePicker.setCurrentHour(Pie.getInst().wakeTime.get(Calendar.HOUR_OF_DAY));
				_timePicker.setCurrentMinute(Pie.getInst().wakeTime.get(Calendar.MINUTE));
			}
		} else {
			if (Pie.getInst().outBedTime == null) {
				_timePicker.setCurrentHour(Pie.getInst().wakeTime.get(Calendar.HOUR_OF_DAY));
				_timePicker.setCurrentMinute(Pie.getInst().wakeTime.get(Calendar.MINUTE));
			} else {
				_timePicker.setCurrentHour(Pie.getInst().outBedTime.get(Calendar.HOUR_OF_DAY));
				_timePicker.setCurrentMinute(Pie.getInst().outBedTime.get(Calendar.MINUTE));
			}
		}
		
		_okBtn.setOnClickListener(okBtnClickListener);
		_cancelBtn.setOnClickListener(cancelBtnClickListener);
	}
	
	private static final int TIMEPICKER_INTERVAL = 5;
	private boolean bIgnoreEvent = false;
	private TimePicker.OnTimeChangedListener timePickerListener = new OnTimeChangedListener() {	
		@Override
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
			if (bIgnoreEvent) {
				return;
			}
			
			if (minute % TIMEPICKER_INTERVAL != 0) {
				int minuteFloor = minute - (minute % TIMEPICKER_INTERVAL);
				minute = minuteFloor + (minute == minuteFloor + 1 ? TIMEPICKER_INTERVAL : 0);
	            if (minute == 60) {
	                minute = 0;
	            }
	            bIgnoreEvent = true;
	            view.setCurrentMinute(minute);
	            bIgnoreEvent=false;
			}
		}
	};
	
	private OnClickListener cancelBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setResult(RESULT_CANCELED);
			finish();
		}
	};
	
	private OnClickListener okBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// CLEANUP // Log.w("shakej", "selected = " + _spinner.getSelectedItemPosition());
			DateTime kDateTime = null;
			if (_spinner.getSelectedItemPosition() == 0)
			{
				kDateTime = new DateTime(_day, _timePicker.getCurrentHour(), _timePicker.getCurrentMinute());
			}
			else
			{
				kDateTime = new DateTime(_prevDay, _timePicker.getCurrentHour(), _timePicker.getCurrentMinute());
			}
						
			// CLEANUP // Log.w("DateTimeSpinner", "Selet Date = " + kDateTime.getStringTime(SleepTightConstants.NetworkFormat));
			if (_isWakeTime)
			{
				Pie.getInst().wakeTime = kDateTime;
			}
			else
			{
				Pie.getInst().outBedTime = kDateTime;
			}
			
			setResult(RESULT_OK);
			finish();
		}
	};
}
