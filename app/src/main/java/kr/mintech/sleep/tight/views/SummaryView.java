package kr.mintech.sleep.tight.views;

import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.sleepsummary.SleepSummaryController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.utils.Util;
import Util.ContextUtil;
import Util.Logg;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SummaryView extends Fragment {
	private Context _context;
	
	private Button _btnSevenDays, _btnTwoWeeks, _btnFourWeeks;
	private Button _prevBtn, _nextBtn;
	public static int buttonClick = 0;
	
	private TextView _textSummaryDescription;
	private TextView _textDay;
	
	//Controller -> Network 
	private SleepSummaryController _summaryController;
	private SummaryCanvasView _summaryCanvasView;
	
	private SUMMARY_DURATION _duration = SUMMARY_DURATION.SEVENDAYS;
	
	//Sleep summary chart 
	private TextView _toBedTime;
	private TextView _outBedTime;
	private TextView _minToFallAsleep;
	private TextView _numAwakening;
	private TextView _sleepDuration;
	private TextView _sleepEfficiency;
	
	private RelativeLayout _progressBar;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		_context = ContextUtil.CONTEXT;
		View rootView = inflater.inflate(R.layout.view_summary, container, false);
		
		_summaryCanvasView = (SummaryCanvasView) rootView.findViewById(R.id.canvas_time_line);
		_progressBar = (RelativeLayout) rootView.findViewById(R.id.layout_progressbar);
		
		_prevBtn = (Button) rootView.findViewById(R.id.btn_prev);
		_nextBtn = (Button) rootView.findViewById(R.id.btn_next);
		_btnSevenDays = (Button) rootView.findViewById(R.id.btn_sevendays);
		_btnTwoWeeks = (Button) rootView.findViewById(R.id.btn_twoweeks);
		_btnFourWeeks = (Button) rootView.findViewById(R.id.btn_fourweeks);
		_textSummaryDescription = (TextView) rootView.findViewById(R.id.text_summary_description);
		_textDay = (TextView) rootView.findViewById(R.id.text_day);
		
		_summaryCanvasView.numBin = 7;
				
		_toBedTime = (TextView) rootView.findViewById(R.id.text_toBedTime);
		_outBedTime = (TextView) rootView.findViewById(R.id.text_outBedTime);
		_minToFallAsleep = (TextView) rootView.findViewById(R.id.text_minFallAsleep);
		_numAwakening = (TextView) rootView.findViewById(R.id.text_numAwakenings);
		_sleepDuration = (TextView) rootView.findViewById(R.id.text_sleepDuration);
		_sleepEfficiency = (TextView) rootView.findViewById(R.id.text_sleepEfficiency);
		
		_prevBtn.setOnClickListener(prevNextClickListener);
		_nextBtn.setOnClickListener(prevNextClickListener);
		_btnSevenDays.setOnClickListener(dayWeekMonthClickListener);
		_btnTwoWeeks.setOnClickListener(dayWeekMonthClickListener);
		_btnFourWeeks.setOnClickListener(dayWeekMonthClickListener);

		_summaryController = new SleepSummaryController(onRequestEndListener);
		
		Pie pie = Pie.getInst();
		if (pie.graphEndDate == null) {
			pie.graphEndDate = new DateTime();
		}
		
		loadSleepTracks();
		updateDurationText();

		return rootView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	private void loadSleepTracks() {
		String kCurrentDateTimeStr = Pie.getInst().graphEndDate.getStringTime(SleepTightConstants.NetworkFormat);
		_summaryController.requestSleepTracks(kCurrentDateTimeStr, 24 * _summaryCanvasView.numBin);
		_summaryController.requestDailySleepSummary(kCurrentDateTimeStr, _summaryCanvasView.numBin);
		
		// CLEANUP // Logg.w("SummaryView | loadSleepTracks()", "Base Time : " + kCurrentDateTimeStr);
	}
	
	private void setAvgTodayTextSetting() {
		_sleepDuration.setText(minToHour(Double.parseDouble(_summaryController.unit.sleepDurationAvg)));
		_sleepEfficiency.setText(_summaryController.unit.sleepEfficiencyAvg + "%");
		_minToFallAsleep.setText(_summaryController.unit.sleepTimeToFallAsleepAvg + " mins");
		_numAwakening.setText(_summaryController.unit.awake_countAvg);
		_toBedTime.setText(_summaryController.unit.toBedTimeAvg);
		_outBedTime.setText(_summaryController.unit.outBedTimeAvg);
	}
	
	// Min to Hour
	private String minToHour(Double $min) {
		String kHourStr = null;
		int kHour = (int)($min / 60);
		int kMin = (int)Math.round($min % 60);
		kHourStr = kHour + "h " + kMin + "m";
		return kHourStr;
	}
	
	private String minToHourWithInt(int $min) {
		String kHourStr = null;
		int kHour = $min / 60;
		int kMin = $min % 60;
		kHourStr = kHour + "h " + kMin + "m";
		return kHourStr;
	}
	
	private void updateDurationText() {
		DateTime date = new DateTime(Pie.getInst().graphEndDate, 0, 0);
		date.add(Calendar.DAY_OF_MONTH, -1); // This is the "End Date" in the date duration TextView
		String endDate = date.getStringTime(SleepTightConstants.TitleDayFormat);
		
		DateTime lastDiaryDate = new DateTime(0,0);
		lastDiaryDate.add(Calendar.DAY_OF_MONTH, -1);

		_nextBtn.setVisibility(View.GONE);		
		if (date.compareTo(lastDiaryDate) < 0) {
			_nextBtn.setVisibility(View.VISIBLE);
		}
		
		date.add(Calendar.DAY_OF_MONTH, -(_summaryCanvasView.numBin-1));
		String startDate = date.getStringTime(SleepTightConstants.TitleDayFormat);
		_textDay.setText(startDate + " - " + endDate);
	}
	
	public enum SUMMARY_DURATION { SEVENDAYS, TWOWEEKS, FOURWEEKS };
	
	/*
	 * Click Listener
	 */
	private OnClickListener prevNextClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Pie pie = Pie.getInst();
			switch (v.getId()) {
				case R.id.btn_prev:
					DateTime kPrevDate = new DateTime(pie.graphEndDate);
					kPrevDate.add(Calendar.DATE, -_summaryCanvasView.numBin);
					pie.graphEndDate = kPrevDate;
					
					EventLogger.log("view_summary",
							"direction", "previous");
					break;
					
				case R.id.btn_next:
					DateTime kNextDate = new DateTime(pie.graphEndDate);
					kNextDate.add(Calendar.DATE, _summaryCanvasView.numBin);
					pie.graphEndDate = kNextDate;
					
					EventLogger.log("view_summary",
							"direction", "next");
					break;
			}
			loadSleepTracks();
			updateDurationText();
		}
	};
	
	private OnClickListener dayWeekMonthClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) { 
			switch (v.getId()) {
				case R.id.btn_sevendays:
					Pie.getInst().currentRange = "1-week";
					EventLogger.log("view_summary", 
							"range", "1-week");
					
					_duration = SUMMARY_DURATION.SEVENDAYS;
					_summaryCanvasView.numBin = 7;
					_textSummaryDescription.setText("7-Day Average");
					break;
				
				case R.id.btn_twoweeks:
					Pie.getInst().currentRange = "2-weeks";
					EventLogger.log("view_summary", 
							"range", "2-weeks");

					_duration = SUMMARY_DURATION.TWOWEEKS;
					_summaryCanvasView.numBin = 14;
					_textSummaryDescription.setText("2-Week Average");
					break;
				
				case R.id.btn_fourweeks:
					Pie.getInst().currentRange = "4-weeks";
					EventLogger.log("view_summary", 
							"range", "4-weeks");

					_duration = SUMMARY_DURATION.FOURWEEKS;
					_summaryCanvasView.numBin = 28;
					_textSummaryDescription.setText("4-Week Average");
					break;
			}
			Pie.getInst().graphEndDate = new DateTime();
			loadSleepTracks();
			updateDurationText();
		}
	};
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndSleepTracks:
					_summaryCanvasView.invalidate();
					break;
				
				case NumberConst.requestEndSleepSummary:
					// CLEANUP // Logg.w("SummaryView | enclosing_method()", "Daily sleep summary info loading: success!");
					setAvgTodayTextSetting();
					_progressBar.setVisibility(View.GONE);
					break;
					
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("SummaryView | enclosing_method()", "Network Failure");
					Toast.makeText(_context, "Network status is bad", Toast.LENGTH_SHORT).show();
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
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if (this.isVisible()) {
			if (isVisibleToUser) {
				loadSleepTracks();
				
				Pie.getInst().isRefreshing = true;
			}
		}
	}
}