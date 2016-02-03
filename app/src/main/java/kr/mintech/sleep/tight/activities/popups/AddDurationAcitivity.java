package kr.mintech.sleep.tight.activities.popups;

import java.util.ArrayList;
import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.timeline.TimeLineController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.views.TimeLineCanvasView;
import Util.Logg;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;

public class AddDurationAcitivity extends Activity {
	private int _trackId;
	private int _activityId;
	private String _startTime;
	private String _endTime;
	private boolean _isNewAdd;
	
	private TimePicker _startTimePicker, _endTimePicker;
	private TimeLineController _controller;
	private Button _saveBtn, _cancelBtn;
	private DateTime _baseTime;
	private Spinner _startSpinner, _endSpinner;
	private ArrayList<String> _startTimeArr, _endTimeArr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_add_duration);
		
		_isNewAdd = getIntent().getBooleanExtra("is_new_add", false);
		_trackId = getIntent().getIntExtra("track_id", -1);
		_startTime = getIntent().getStringExtra("start_time");
		_activityId = getIntent().getIntExtra("activity_id", -1);
		_endTime = getIntent().getStringExtra("end_time");
		
		_controller = new TimeLineController(onRequestEndListener);
		_startSpinner = (Spinner) findViewById(R.id.spinner_start_time_date);
		_endSpinner = (Spinner) findViewById(R.id.spinner_end_time_date);
		_startTimePicker = (TimePicker) findViewById(R.id.time_picker_start_time);
		_endTimePicker = (TimePicker) findViewById(R.id.time_picker_end_time);
		_saveBtn = (Button) findViewById(R.id.btn_save);
		_cancelBtn = (Button) findViewById(R.id.btn_cancel);
		
		_startSpinner.setOnItemSelectedListener(startSpinnerClickListener);
		_startTimePicker.setOnTimeChangedListener(onTimeChangedListener);
		
		_cancelBtn.setOnClickListener(cancelClickListener);
		
		_baseTime = new DateTime(_startTime);
		
		settingSpinner();
		
		_startTimePicker.setCurrentHour(_baseTime.get(Calendar.HOUR_OF_DAY));
		_startTimePicker.setCurrentMinute(_baseTime.get(Calendar.MINUTE));
		if (_endTime == null) {
			_endTimePicker.setCurrentHour(_baseTime.get(Calendar.HOUR_OF_DAY));
			_endTimePicker.setCurrentMinute(_baseTime.get(Calendar.MINUTE));
		} else {
			DateTime kEndTime = new DateTime(_endTime);
			_endTimePicker.setCurrentHour(kEndTime.get(Calendar.HOUR_OF_DAY));
			_endTimePicker.setCurrentMinute(kEndTime.get(Calendar.MINUTE));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void settingSpinner() {
		_startTimeArr = new ArrayList<String>();
		_endTimeArr = new ArrayList<String>();
		
		DateTime kPrevDateTime = new DateTime(_baseTime);
		kPrevDateTime.add(Calendar.DATE, -1);
		String kPrevTime = kPrevDateTime.getStringTime(SleepTightConstants.TitleDayFormat);
		String kCurrentTime = _baseTime.getStringTime(SleepTightConstants.TitleDayFormat);
		DateTime kNextDateTime = new DateTime(_baseTime);
		kNextDateTime.add(Calendar.DATE, 1);
		String kNextTime = kNextDateTime.getStringTime(SleepTightConstants.TitleDayFormat);
		
		_startTimeArr.add(kPrevTime);
		_startTimeArr.add(kCurrentTime);
		_startTimeArr.add(kNextTime);
		
		if (_endTime == null) {
			_endTimeArr.add(kPrevTime);
			_endTimeArr.add(kCurrentTime);
			_endTimeArr.add(kNextTime);
		} else {
			DateTime kEndTime = new DateTime(_endTime);
			DateTime kPrevDateEndTime = new DateTime(kEndTime);
			kPrevDateEndTime.add(Calendar.DATE, -1);
			String kPrevEndTime = kPrevDateTime.getStringTime(SleepTightConstants.TitleDayFormat);
			String kCurrentEndTime = _baseTime.getStringTime(SleepTightConstants.TitleDayFormat);
			DateTime kNextDateEndTime = new DateTime(_endTime);
			kNextDateEndTime.add(Calendar.DATE, 1);
			String kNextEndTime = kNextDateTime.getStringTime(SleepTightConstants.TitleDayFormat);
			
			_endTimeArr.add(kPrevEndTime);
			_endTimeArr.add(kCurrentEndTime);
			_endTimeArr.add(kNextEndTime);
		}
		
		ArrayAdapter kSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, _startTimeArr);
		_startSpinner.setAdapter(kSpinnerAdapter);
		_startSpinner.setSelection(1);
		
		ArrayAdapter kSpinnerEndAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, _endTimeArr);
		_endSpinner.setAdapter(kSpinnerEndAdapter);
		_endSpinner.setSelection(1);
	}
	
	private OnItemSelectedListener startSpinnerClickListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			int i = _startSpinner.getSelectedItemPosition();
			_endSpinner.setSelection(i);			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnTimeChangedListener onTimeChangedListener = new OnTimeChangedListener() {
		@Override
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
			_endTimePicker.setCurrentHour(_startTimePicker.getCurrentHour());
			_endTimePicker.setCurrentMinute(_startTimePicker.getCurrentMinute());
		}
	};
	
	private void requestEditTracks(String $startedAt, String $endedAt) {
		_controller.requestEditActivityTrack(_trackId, _activityId, $startedAt, $endedAt);
	}
	
	private void requestAddTracks(String $startedAt, String $endedAt) {
		_controller.reqeustAddActivityTrack(_activityId, $startedAt, $endedAt);
	}
	
	public void onSaveClick(View v) {
		String kStartTimeAt = null, kEndTimeAt = null;
		
		DateTime dtStart = new DateTime(_baseTime);
		DateTime dtEnd = new DateTime(_baseTime);
		
		if (_startSpinner.getSelectedItemPosition() == 0) {
			dtStart.add(Calendar.DATE, -1);
		} else if (_startSpinner.getSelectedItemPosition() == 2) {
			dtStart.add(Calendar.DATE, 1);
		}
		
		dtStart.setTime(_startTimePicker.getCurrentHour(), _startTimePicker.getCurrentMinute());
		kStartTimeAt = dtStart.getStringTime(SleepTightConstants.NetworkFormat);		
		
		if (_endSpinner.getSelectedItemPosition() == 0)
		{
			dtEnd.add(Calendar.DATE, -1);
		}
		else if (_endSpinner.getSelectedItemPosition() == 2)
		{
			dtEnd.add(Calendar.DATE, +1);
		}
		dtEnd.setTime(_endTimePicker.getCurrentHour(), _endTimePicker.getCurrentMinute());
		kEndTimeAt = dtEnd.getStringTime(SleepTightConstants.NetworkFormat);

		if (dtEnd.compareTo(dtStart) < 0) {
			Toast.makeText(this, "The End Time should be later than the Start Time", Toast.LENGTH_SHORT).show();
		} else {
			TimeLineCanvasView._handleLoad = true;
			Pie.getInst().handleTime = dtStart;
			
			if (_isNewAdd) {
				requestAddTracks(kStartTimeAt, kEndTimeAt);
			} else {
				requestEditTracks(kStartTimeAt, kEndTimeAt);
			}
		}
	}
	
	private OnClickListener cancelClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setResult(RESULT_CANCELED);
			finish();
		}
	};
	
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
				case NumberConst.requestEndEditTracksSuccess:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Edit: success!");
					_controller.updateWidget(AddDurationAcitivity.this);
					finish();
					break;
					
				case NumberConst.requestSuccess:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Add: success!");
					EventLogger.log("add_activity_result",
							"type", "duration",
							"result", "success");
					
					Intent intent = new Intent();
					intent.setAction("kr.mintech.main.refresh");
					sendBroadcast(intent);
					_controller.updateWidget(AddDurationAcitivity.this);
					setResult(RESULT_OK);
					finish();
					break;
					
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Network Fail");					
					Toast.makeText(AddDurationAcitivity.this, "Fail", Toast.LENGTH_SHORT).show();
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