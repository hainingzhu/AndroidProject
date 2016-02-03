//package kr.mintech.sleep.tight.views;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//
//import kr.mintech.sleep.tight.R;
//import kr.mintech.sleep.tight.activities.sleepdiarys.SummaryBinInfoActivity;
//import kr.mintech.sleep.tight.consts.NumberConst;
//import kr.mintech.sleep.tight.consts.SleepTightConstants;
//import kr.mintech.sleep.tight.consts.StringConst;
//import kr.mintech.sleep.tight.controllers.sleepsummary.SleepSummaryController;
//import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
//import kr.mintech.sleep.tight.utils.DateTime;
//import Util.ContextUtil;
//import Util.Logg;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class SleepSummaryView extends Fragment
//{
//	private Context _context;
//	
//	private Button _btnDay, _btnWeek;
//	private TextView _textSummaryDescription;
//	private boolean _isDays;
//	
//	//Top SleepSummary Chart
//	private HorizontalListView _summaryList;
//	private LinearLayout _contentLayout;
//	
//	private ArrayList<Double> summaryArr;
//	private ArrayList<String> dateLabelArr;
//	private double highest;
//	private int[] summaryChartheight;
//	
//	private TextView _summaryTitle, _summaryAverage;
//	
//	//SleepDuration Chart 
//	private ListView _sleepDurationChart;
//	private ArrayList<Double> sleepDurationArr;
//	private LinearLayout _sleepDurationLayout;
//	
//	//SleepEfficiency Chart
//	private ListView _sleepEfficiencyChart;
//	private ArrayList<Double> sleepEfficiencyArr;
//	private LinearLayout _sleepEfficienyLayout;
//	
//	//Time To Sleep Fail Chart
//	private ListView _timeToFailAsleepChart;
//	private ArrayList<Double> timeToFailAsleepArr;
//	private LinearLayout _timeToFallAsleepLayout;
//	
//	private SleepSummaryController _summaryController;
//	
//	//Bottom 
//	private TextView _sleepDurationToday, _sleepDurationAvg;
//	private TextView _sleepEfficiencyToday, _sleepEfficiencyAvg;
//	private TextView _timeToFallAsleepToday, _timeToFallAsleepAvg;
//	private TextView _qualityText;
//	
//	private RelativeLayout _progressBar;
//	
//	private Button _prevBtn, _nextBtn;
//	private DateTime _baseTime;
//	
//	
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//	{
//		_context = ContextUtil.CONTEXT;
//		View rootView = inflater.inflate(R.layout.view_sleep_summary, container, false);
//		
//		_isDays = true;
//		
//		_progressBar = (RelativeLayout) rootView.findViewById(R.id.layout_progressbar);
//		_contentLayout = (LinearLayout) rootView.findViewById(R.id.layout_sleep_summary_view);
//		_summaryList = (HorizontalListView) rootView.findViewById(R.id.summary_chart_listview);
//		
//		_prevBtn = (Button) rootView.findViewById(R.id.btn_prev);
//		_nextBtn = (Button) rootView.findViewById(R.id.btn_next);
//		_btnDay = (Button) rootView.findViewById(R.id.btn_days);
//		_btnWeek = (Button) rootView.findViewById(R.id.btn_weeks);
//		_textSummaryDescription = (TextView) rootView.findViewById(R.id.text_sleep_summary_description);
//		
//		_summaryTitle = (TextView) rootView.findViewById(R.id.text_title_summary_chart);
//		_summaryAverage = (TextView) rootView.findViewById(R.id.text_average_summary_chart);
//		
//		_sleepDurationChart = (ListView) rootView.findViewById(R.id.chart_sleep_duration);
//		_sleepEfficiencyChart = (ListView) rootView.findViewById(R.id.chart_sleep_efficiency);
//		_timeToFailAsleepChart = (ListView) rootView.findViewById(R.id.chart_time_to_fail_asleep);
//		
//		_sleepDurationLayout = (LinearLayout) rootView.findViewById(R.id.layout_sleep_summary);
//		_sleepEfficienyLayout = (LinearLayout) rootView.findViewById(R.id.layout_sleep_efficiency);
//		_timeToFallAsleepLayout = (LinearLayout) rootView.findViewById(R.id.layout_time_to_fail_asleep);
//		
//		_sleepDurationToday = (TextView) rootView.findViewById(R.id.text_sleep_duration_today);
//		_sleepDurationAvg = (TextView) rootView.findViewById(R.id.text_sleep_duration_avg);
//		_sleepEfficiencyToday = (TextView) rootView.findViewById(R.id.text_sleep_efficiency_today);
//		_sleepEfficiencyAvg = (TextView) rootView.findViewById(R.id.text_sleep_efficiency_avg);
//		_timeToFallAsleepToday = (TextView) rootView.findViewById(R.id.text_sleep_time_to_fall_asleep_today);
//		_timeToFallAsleepAvg = (TextView) rootView.findViewById(R.id.text_sleep_time_to_fall_asleep_avg);
//		_qualityText = (TextView) rootView.findViewById(R.id.textview_quality);
//		
//		_prevBtn.setOnClickListener(prevNextClickListener);
//		_nextBtn.setOnClickListener(prevNextClickListener);
//		_btnDay.setOnClickListener(dayWeekClickListener);
//		_btnWeek.setOnClickListener(dayWeekClickListener);
//		_sleepDurationLayout.setOnClickListener(layoutClickListener);
//		_sleepEfficienyLayout.setOnClickListener(layoutClickListener);
//		_timeToFallAsleepLayout.setOnClickListener(layoutClickListener);
//		
//		_summaryController = new SleepSummaryController(onRequestEndListener);
//		
//		if (_baseTime == null)
//		{
//			_baseTime = new DateTime();
//		}
//		requestDailySleepSummary();
//		return rootView;
//	}
//	
//	
//	private void requestDailySleepSummary()
//	{
//		_progressBar.setVisibility(View.VISIBLE);
//		String kBaseTimeStr = _baseTime.getStringTime(SleepTightConstants.NetworkFormat);
//		_summaryController.requestDailySleepSummary(kBaseTimeStr, 7);
//	}
//	
//	
//	private void requestWeeklySleepSummary()
//	{
//		_progressBar.setVisibility(View.VISIBLE);
//		String kBaseTimeStr = _baseTime.getStringTime(SleepTightConstants.NetworkFormat);
//		_summaryController.requestWeeklySleepSummary(kBaseTimeStr);
//	}
//	
//	/*
//	 * Network Listener
//	 */
//	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener()
//	{
//		@Override
//		public void onRequestEnded(int $tag, Object $object)
//		{
//			switch ($tag)
//			{
//				case NumberConst.requestEndSleepSummary:
//					Logg.w("SleepSummaryView | enclosing_method()", "Sleep Summary Days Info loading: success!");
//					setAvgTodayTextSetting();
//					_progressBar.setVisibility(View.GONE);
//					break;
//					
//				case NumberConst.requestEndSleepSummaryWeekly:
//					Logg.w("SleepSummaryView | enclosing_method()", "Sleep Summary Weeks Info loading: success!");
//					setAvgTodayTextSetting();
//					_progressBar.setVisibility(View.GONE);
//					break;
//					
//				case NumberConst.requestFail:
//					Logg.w("AddActivityView | enclosing_method()", "Network Failure");
//					Toast.makeText(_context, "Network status is bad", Toast.LENGTH_SHORT).show();
//					break;
//			}
//		}
//		
//		
//		@Override
//		public void onRequest(Object $unit)
//		{
//		}
//		
//		
//		@Override
//		public void onRequestError(int $tag, String $errorStr)
//		{
//		}
//	};
//	
//	
//	// Min to Hour
//	private String minToHour(Double $min)
//	{
//		String kHourStr = null;
//		int kHour = (int)($min / 60);
//		int kMin = (int)Math.round($min % 60);
//		kHourStr = kHour + "h " + kMin + "m";
//		return kHourStr;
//	}
//	
//	
//	private String minToHourWithInt(int $min)
//	{
//		String kHourStr = null;
//		int kHour = $min / 60;
//		int kMin = $min % 60;
//		kHourStr = kHour + "h " + kMin + "m";
//		return kHourStr;
//	}
//	
//	
//	private void setAvgTodayTextSetting()
//	{
//		if (_isDays)
//		{
//			_textSummaryDescription.setText("TODAY VS. 7-DAY AVG");
//		}
//		else
//		{
//			_textSummaryDescription.setText("THIS WEEK VS. OVERALL");
//		}
//		
//		// _summaryController.unit.dayUnits.get(6)
//		_qualityText.setText("Today Score :" + _summaryController.unit.dayUnits.get(6).sleepQuality + "\n" + "Avg Score :" + _summaryController.unit.sleepQualityAvg);
//		
//		_sleepDurationToday.setText(minToHourWithInt(_summaryController.unit.dayUnits.get(6).sleepDuration));
//		_sleepDurationAvg.setText(minToHour(Double.parseDouble(_summaryController.unit.sleepDurationAvg)));
//		
//		_sleepEfficiencyToday.setText(Integer.toString(_summaryController.unit.dayUnits.get(6).sleepEfficiency) + "%");
//		_sleepEfficiencyAvg.setText(_summaryController.unit.sleepEfficiencyAvg + "%");
//		
//		_timeToFallAsleepToday.setText(Integer.toString(_summaryController.unit.dayUnits.get(6).sleepTimeToFallAsleep));
//		_timeToFallAsleepAvg.setText(_summaryController.unit.sleepTimeToFallAsleepAvg);
//		
//		setBottomChart();
//	}
//	
//	
//	private void setBottomChart()
//	{
//		sleepDurationArr = new ArrayList<Double>();
//		sleepDurationArr.add(Double.valueOf(_summaryController.unit.dayUnits.get(6).sleepDuration));
//		sleepDurationArr.add(Double.valueOf(_summaryController.unit.sleepDurationAvg));
//		sleepEfficiencyArr = new ArrayList<Double>();
//		sleepEfficiencyArr.add(Double.valueOf(_summaryController.unit.dayUnits.get(6).sleepEfficiency));
//		sleepEfficiencyArr.add(Double.valueOf(_summaryController.unit.sleepEfficiencyAvg));
//		timeToFailAsleepArr = new ArrayList<Double>();
//		timeToFailAsleepArr.add(Double.valueOf(_summaryController.unit.dayUnits.get(6).sleepTimeToFallAsleep));
//		timeToFailAsleepArr.add(Double.valueOf(_summaryController.unit.sleepTimeToFallAsleepAvg));
//		
//		setSleepDurationChart();
//		setEfficiencyChart();
//		setTimeToFailAsleepChart();
//		setSummaryChart(StringConst.CHART_SUMMARY_TYPE_SLEEP_DUARTION);
//	}
//	
//	
//	private void setSummaryChart(String $type)
//	{
//		_summaryTitle.setText($type);
//		summaryArr = new ArrayList<Double>();
//		
//		if (summaryArr.size() > 0)
//		{
//			summaryArr.clear();
//		}
//		
//		if ($type.equals(StringConst.CHART_SUMMARY_TYPE_SLEEP_DUARTION))
//		{
//			if (_isDays)
//			{
//				_summaryAverage.setText("7-Day Arg " + minToHour(Double.parseDouble(_summaryController.unit.sleepDurationAvg)));
//			}
//			else
//			{
//				_summaryAverage.setText("7-Weeks Arg " + minToHour(Double.parseDouble(_summaryController.unit.sleepDurationAvg)));
//			}
//			for (int i = 0; i < _summaryController.unit.dayUnits.size(); i++)
//			{
//				summaryArr.add(Double.valueOf(_summaryController.unit.dayUnits.get(i).sleepDuration));
//			}
//		}
//		else if ($type.equals(StringConst.CHART_SUMMARY_TYPE_SLEEP_EFFICIENCY))
//		{
//			if (_isDays)
//			{
//				_summaryAverage.setText("7-Day Arg " + _summaryController.unit.sleepEfficiencyAvg + "%");
//			}
//			else
//			{
//				_summaryAverage.setText("7-Weeks Arg " + _summaryController.unit.sleepEfficiencyAvg + "%");
//			}
//			
//			for (int i = 0; i < _summaryController.unit.dayUnits.size(); i++)
//			{
//				summaryArr.add(Double.valueOf(_summaryController.unit.dayUnits.get(i).sleepEfficiency));
//			}
//		}
//		else if ($type.equals(StringConst.CHART_SUMMARY_TYPE_SLEEP_TIME_TO_FALL_ASLEEP))
//		{
//			if (_isDays)
//			{
//				_summaryAverage.setText("7-Day Arg " + _summaryController.unit.sleepTimeToFallAsleepAvg);
//			}
//			else
//			{
//				_summaryAverage.setText("7-Weeks Arg " + _summaryController.unit.sleepTimeToFallAsleepAvg);
//			}
//			
//			for (int i = 0; i < _summaryController.unit.dayUnits.size(); i++)
//			{
//				summaryArr.add(Double.valueOf(_summaryController.unit.dayUnits.get(i).sleepTimeToFallAsleep));
//			}
//		}
//		
//		highest = (Collections.max(summaryArr));
//		summaryChartheight = new int[summaryArr.size()];
//		
//		setDateLabel(StringConst.CHART_SUMMARY_TYPE_SLEEP_DUARTION);
//		updateSummaryListSizeInfo($type);
//	}
//	
//	
//	private void updateSummaryListSizeInfo(String $type)
//	{
//		int h;
//		h = (int) (_contentLayout.getHeight() * 0.12);
//		
//		if (h == 0)
//		{
//			h = 123;
//		}
//		
//		for (int i = 0; i < summaryArr.size(); i++)
//		{
//			summaryChartheight[i] = (int) ((h * summaryArr.get(i)) / highest);
//		}
//		_summaryList.setAdapter(new SummaryChartAdapter(_context, dateLabelArr, $type));
//	}
//	
//	
//	//DateLabels Setting
//	private void setDateLabel(String $type)
//	{
//		dateLabelArr = new ArrayList<String>();
//		if (dateLabelArr.size() > 0)
//		{
//			dateLabelArr.clear();
//		}
//		
//		if (_isDays)
//		{
//			for (int i = 0; i < summaryArr.size(); i++)
//			{
//				String kDiaryDate = _summaryController.unit.dayUnits.get(i).diaryDate;
//				DateTime kDateTime = new DateTime(kDiaryDate);
//				String kDateTimeStr = kDateTime.getStringTime(SleepTightConstants.DayForamt);
//				dateLabelArr.add(kDateTimeStr);
//			}
//		}
//		else
//		{
//			for (int i = 0; i < summaryArr.size(); i++)
//			{
//				String kDiaryDate = _summaryController.unit.dayUnits.get(i).diaryDate;
//				dateLabelArr.add(kDiaryDate);
//			}
//		}
//	}
//	
//	
//	//SleepDuration Chart Setting
//	private void setSleepDurationChart()
//	{
//		double kHighest = (Collections.max(sleepDurationArr));
//		int[] kDurationWidths = new int[sleepDurationArr.size()];
//		updateChartListSizeInfo(StringConst.CHART_TYPE_SLEEP_DURATION, kHighest, kDurationWidths);
//	}
//	
//	
//	//SleepEfficiencyChart Setting
//	private void setEfficiencyChart()
//	{
//		double kHighest = (Collections.max(sleepEfficiencyArr));
//		int[] kDurationWidths = new int[sleepEfficiencyArr.size()];
//		updateChartListSizeInfo(StringConst.CHART_TYPE_SLEEP_EFFICIENCY, kHighest, kDurationWidths);
//	}
//	
//	
//	//TimeToFailAsleepChart Setting
//	private void setTimeToFailAsleepChart()
//	{
//		double kHighest = (Collections.max(timeToFailAsleepArr));
//		
//		int[] kDurationWidths = new int[timeToFailAsleepArr.size()];
//		updateChartListSizeInfo(StringConst.CHART_TYPE_SLEEP_FAIL, kHighest, kDurationWidths);
//	}
//	
//	/*
//	 * Summary Chart
//	 */
//	public class SummaryChartAdapter extends BaseAdapter
//	{
//		Context cntx;
//		ArrayList<String> array;
//		String type;
//		
//		
//		public SummaryChartAdapter(Context context, ArrayList<String> arr, String $type)
//		{
//			this.cntx = context;
//			this.array = arr;
//			this.type = $type;
//		}
//		
//		
//		public int getCount()
//		{
//			return 7;
//		}
//		
//		
//		public Object getItem(int position)
//		{
//			return position;
//		}
//		
//		
//		public long getItemId(int position)
//		{
//			return position;
//		}
//		
//		
//		public View getView(final int position, View convertView, ViewGroup parent)
//		{
//			View kRow = null;
//			LayoutInflater inflater = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			kRow = inflater.inflate(R.layout.view_sleep_summary_list_item, null);
//			
//			final TextView kTitle = (TextView) kRow.findViewById(R.id.title);
//			TextView kColumn = (TextView) kRow.findViewById(R.id.colortext01);
//			
//			TextView kGraphTopTextView = (TextView) kRow.findViewById(R.id.text01);
//			
//			kColumn.setHeight(summaryChartheight[position]);
//			kTitle.setText(dateLabelArr.get(position));
//			
//			kGraphTopTextView.setText(minToHour(summaryArr.get(position)));
//			
//			if (type.equals(StringConst.CHART_SUMMARY_TYPE_SLEEP_DUARTION))
//			{
//				kColumn.setBackgroundColor(Color.parseColor("#008000"));
//			}
//			else if (type.equals(StringConst.CHART_SUMMARY_TYPE_SLEEP_EFFICIENCY))
//			{
//				kColumn.setBackgroundColor(Color.parseColor("#800080"));
//			}
//			else if (type.equals(StringConst.CHART_SUMMARY_TYPE_SLEEP_TIME_TO_FALL_ASLEEP))
//			{
//				kColumn.setBackgroundColor(Color.parseColor("#FF0000"));
//			}
//			
//			kColumn.setOnClickListener(new OnClickListener()
//			{
//				
//				public void onClick(View v)
//				{
//					Intent kIntent = new Intent(_context, SummaryBinInfoActivity.class);
//					kIntent.putExtra("select_date", kTitle.getText().toString());
//					kIntent.putExtra("id", _summaryController.unit.dayUnits.get(position).id);
//					if (_isDays)
//					{
//						kIntent.putExtra("type", "day");
//					}
//					else
//					{
//						kIntent.putExtra("type", "week");
//					}
//					startActivity(kIntent);
//				}
//			});
//			
//			return kRow;
//		}
//	}
//	
//	
//	/*
//	 * Bottom Chart
//	 */
//	//TYPE : SLEEP_DURATION, SLEEP_EFFICIENCY, SLEEP_FAIL (StringConst)
//	private void updateChartListSizeInfo(String $type, Double $hightValue, int[] $widthArr)
//	{
//		int kWidth;
//		kWidth = (int) (_contentLayout.getWidth() * 0.2);
//		if (kWidth == 0)
//		{
//			kWidth = 200;
//		}
//		if ($type.equals(StringConst.CHART_TYPE_SLEEP_DURATION))
//		{
//			for (int i = 0; i < sleepDurationArr.size(); i++)
//			{
//				$widthArr[i] = (int) ((kWidth * sleepDurationArr.get(i)) / $hightValue);
//			}
//			_sleepDurationChart.setAdapter(new BottomChartAdapter(_context, sleepDurationArr, $type, $widthArr));
//		}
//		else if ($type.equals(StringConst.CHART_TYPE_SLEEP_EFFICIENCY))
//		{
//			for (int i = 0; i < sleepEfficiencyArr.size(); i++)
//			{
//				$widthArr[i] = (int) ((kWidth * sleepEfficiencyArr.get(i)) / $hightValue);
//			}
//			_sleepEfficiencyChart.setAdapter(new BottomChartAdapter(_context, sleepEfficiencyArr, $type, $widthArr));
//		}
//		else
//		{
//			for (int i = 0; i < timeToFailAsleepArr.size(); i++)
//			{
//				$widthArr[i] = (int) ((kWidth * timeToFailAsleepArr.get(i)) / $hightValue);
//			}
//			_timeToFailAsleepChart.setAdapter(new BottomChartAdapter(_context, timeToFailAsleepArr, $type, $widthArr));
//			
//		}
//	}
//	
//	public class BottomChartAdapter extends BaseAdapter
//	{
//		Context context;
//		ArrayList<Double> array;
//		String type;
//		int[] widthArr;
//		
//		
//		public BottomChartAdapter(Context context, ArrayList<Double> arr, String $type, int[] $widthArr)
//		{
//			this.context = context;
//			this.array = arr;
//			this.type = $type;
//			this.widthArr = $widthArr;
//		}
//		
//		
//		public int getCount()
//		{
//			return 2;
//		}
//		
//		
//		public Object getItem(int position)
//		{
//			return position;
//		}
//		
//		
//		public long getItemId(int position)
//		{
//			return position;
//		}
//		
//		
//		public View getView(final int position, View convertView, ViewGroup parent)
//		{
//			View kRow = null;
//			
//			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			kRow = inflater.inflate(R.layout.view_bottom_chart_list_item, null);
//			
//			TextView kColumn = (TextView) kRow.findViewById(R.id.colortext01);
//			kColumn.setWidth(widthArr[position]);
//			
//			//Set column color
//			if (position == 1)
//			{
//				kColumn.setBackgroundColor(Color.parseColor("#808080"));
//			}
//			else
//			{
//				if (type.equals(StringConst.CHART_TYPE_SLEEP_DURATION))
//				{
//					kColumn.setBackgroundColor(Color.parseColor("#008000"));
//				}
//				else if (type.equals(StringConst.CHART_TYPE_SLEEP_EFFICIENCY))
//				{
//					kColumn.setBackgroundColor(Color.parseColor("#800080"));
//				}
//				else
//				{
//					kColumn.setBackgroundColor(Color.parseColor("#FF0000"));
//				}
//			}
//			kColumn.setClickable(false);
//			kColumn.setOnClickListener(new OnClickListener()
//			{
//				public void onClick(View v)
//				{
//				}
//			});
//			
//			return kRow;
//		}
//	}
//	
//	/*
//	 * Listener
//	 */
//	private OnClickListener layoutClickListener = new OnClickListener()
//	{
//		@Override
//		public void onClick(View v)
//		{
//			switch (v.getId())
//			{
//				case R.id.layout_sleep_summary:
//					setSummaryChart(StringConst.CHART_SUMMARY_TYPE_SLEEP_DUARTION);
//					break;
//				
//				case R.id.layout_sleep_efficiency:
//					setSummaryChart(StringConst.CHART_SUMMARY_TYPE_SLEEP_EFFICIENCY);
//					break;
//				
//				case R.id.layout_time_to_fail_asleep:
//					setSummaryChart(StringConst.CHART_SUMMARY_TYPE_SLEEP_TIME_TO_FALL_ASLEEP);
//					break;
//				
//				default:
//					break;
//			}
//			
//		}
//	};
//	
//	private OnClickListener prevNextClickListener = new OnClickListener()
//	{
//		@Override
//		public void onClick(View v)
//		{
//			switch (v.getId())
//			{
//				case R.id.btn_prev:
//					if (_isDays)
//					{
//						DateTime kPrevDate = new DateTime(_baseTime);
//						kPrevDate.add(Calendar.DATE, -1);
//						_baseTime = kPrevDate;
//						requestDailySleepSummary();
//					}
//					else
//					{
//						DateTime kPrevDate = new DateTime(_baseTime);
//						kPrevDate.add(Calendar.DATE, -7);
//						_baseTime = kPrevDate;
//						requestWeeklySleepSummary();
//					}
//					break;
//				case R.id.btn_next:
//					if (_isDays)
//					{
//						DateTime kPrevDate = new DateTime(_baseTime);
//						kPrevDate.add(Calendar.DATE, +1);
//						_baseTime = kPrevDate;
//						requestDailySleepSummary();
//					}
//					else
//					{
//						DateTime kPrevDate = new DateTime(_baseTime);
//						kPrevDate.add(Calendar.DATE, +7);
//						_baseTime = kPrevDate;
//						requestWeeklySleepSummary();
//					}
//					break;
//				default:
//					break;
//			}
//		}
//	};
//	
//	private OnClickListener dayWeekClickListener = new OnClickListener()
//	{
//		@Override
//		public void onClick(View v)
//		{
//			switch (v.getId())
//			{
//				case R.id.btn_days:
//					_isDays = true;
//					_baseTime = new DateTime();
//					requestDailySleepSummary();
//					break;
//				case R.id.btn_weeks:
//					_isDays = false;
//					_baseTime = new DateTime();
//					requestWeeklySleepSummary();
//					break;
//				default:
//					break;
//			}
//		}
//	};
//}