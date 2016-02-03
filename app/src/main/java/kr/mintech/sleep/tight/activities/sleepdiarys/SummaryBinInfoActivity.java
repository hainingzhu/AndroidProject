package kr.mintech.sleep.tight.activities.sleepdiarys;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.sleepsummary.BinInfoListAdapter;
import kr.mintech.sleep.tight.controllers.sleepsummary.SleepSummaryController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.utils.DateTime;
import Util.Logg;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SummaryBinInfoActivity extends Activity {
	private TextView _dateText;
	
	private SleepSummaryController _summaryController;
	private String id;
	
	//to bed time, fall asleep, duration, efficiency
	private TextView _toBedTimeText, _fallAsleepText, _durationText, _efficiencyText;
	private ImageView _sleepQuality;
	
	private ListView _binInfoList;
	private BinInfoListAdapter _adapter;
	
	private Button _okayBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_summary_bin_info);
		
		String kDate = getIntent().getStringExtra("select_date");
		DateTime dtSelectDate = new DateTime(kDate);
		id = getIntent().getStringExtra("id");
		
		_summaryController = new SleepSummaryController(onRequestEndListener);
		
		_dateText = (TextView) findViewById(R.id.text_bin_info_date);
		_dateText.setText(dtSelectDate.getStringTime(SleepTightConstants.LastEditDateFormat));
		
		_sleepQuality = (ImageView) findViewById(R.id.image_sleep_quality);
		_toBedTimeText = (TextView) findViewById(R.id.text_bin_info_to_bed_time);
		_fallAsleepText = (TextView) findViewById(R.id.text_bin_info_time_to_fall_asleep);
		_durationText = (TextView) findViewById(R.id.text_bin_info_sleep_duration);
		_efficiencyText = (TextView) findViewById(R.id.text_bin_info_sleep_eficiency);
		
		_binInfoList = (ListView) findViewById(R.id.bin_info_list);
		_adapter = new BinInfoListAdapter(this);
		_binInfoList.setAdapter(_adapter);
		
		_okayBtn = (Button) findViewById(R.id.btn_okay);
		
		_okayBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		loadDayBinInfo();
	}
	
	private void loadDayBinInfo() {
		_summaryController.requestDailyBinInfo(id);
	}
	
	private void loadWeekBinInfo() {
		_summaryController.requestWeeklyBinInfo(id);
	}
	
	private void addLastActivities() {
		_adapter.putItems(_summaryController.dayBinUnit._binActivityUnit);
	}
	
//	private String minToHour(Double $min) {
//		String kHourStr = null;
//		Double kHour = $min / 60;
//		Double kMin = $min % 60;
//		kHourStr = Math.round(kHour) + "h " + Math.round(kMin) + "m";
//		return kHourStr;
//	}
	
	private String minToHour(int $min) {
		String kHourStr = null;
		int kHour = $min / 60;
		int kMin = $min % 60;
		kHourStr = kHour + "h " + kMin + "m";
		return kHourStr;
	}
	
	private void setLabel() {
		if (_summaryController.dayBinUnit.toBedTime != null) {
			String kToBedTime = _summaryController.dayBinUnit.toBedTime;
			DateTime kBedTime = new DateTime(kToBedTime);
			String kToBedTiemStr = kBedTime.getStringTime(SleepTightConstants.AMPM_TimeFormat);
			_toBedTimeText.setText(kToBedTiemStr);
		}
		
		_fallAsleepText.setText(_summaryController.dayBinUnit.timeToFallAsleep + "min");
		_durationText.setText(minToHour(Integer.parseInt(_summaryController.dayBinUnit.sleepDuration)));
		_efficiencyText.setText(_summaryController.dayBinUnit.sleepEfficiency + "%");		
		
		int kQuality = 5;
		try {
			kQuality = Integer.parseInt(_summaryController.dayBinUnit.sleepQuality);
		} catch (Exception e) {
			kQuality = (int) Double.parseDouble(_summaryController.dayBinUnit.sleepQuality);
		}

		switch (kQuality) {
			case 1:
				_sleepQuality.setImageResource(R.drawable.quality1);
				break;
				
			case 2:
				_sleepQuality.setImageResource(R.drawable.quality2);
				break;
				
			case 3:
				_sleepQuality.setImageResource(R.drawable.quality3);
				break;
				
			case 4:
				_sleepQuality.setImageResource(R.drawable.quality4);
				break;
				
			case 5:
				_sleepQuality.setImageResource(R.drawable.quality5);
				break;
			
			default:
				break;
		}
	}
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndSleepSummaryDayBinInfo:
					// CLEANUP // Logg.w("SleepSummaryView | enclosing_method()", "Bin Info: successfully loaded!");
					setLabel();
					addLastActivities();
					break;
					
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Network Failed");
					break;
			}
		}
		
		@Override
		public void onRequest(Object $unit) {
			
		}
		
		
		@Override
		public void onRequestError(int $tag, String $errorStr) {
		
		}
	};
}