package kr.mintech.sleep.tight.views;

import java.util.ArrayList;
import java.util.Collections;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.R.color;
import kr.mintech.sleep.tight.comparison.ComparisionController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.StringConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.units.ComparisionLastActInfoUnit;
import kr.mintech.sleep.tight.units.Top5RitualUnit;
import kr.mintech.sleep.tight.utils.Pie;
import Util.ContextUtil;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ComparisonView extends Fragment {
	private Context _context;
	
	private LinearLayout _contentLayout;
	
	private Button _btnSleepQuality;
	private Button _btnDaytimeActivity;
	private Button _btnBeforeBedActivity;
	
	private LinearLayout _llSleepQualityComparison;
	private LinearLayout _llDaytimeActivityComparison;
	private LinearLayout _llBeforeBedActivityComparison;
	private ScrollView _svComparison;
	
	int rectWidth = 18;
	
	// SleepQuaility Chart
	private TextView _sleepQualityNumDays;
	private ListView _sleepQualityChart;
	private ArrayList<Double> sleepQuailityArr;
	private TextView _sleepQualityGoodText, _sleepQualityNeutralText, _sleepQualityBadText;
	
	// SleepDuration Chart
	private ListView _sleepDurationChart;
	private ArrayList<Double> sleepDurationArr;
	private TextView _sleepDurationGoodText, _sleepDurationNeutralText, _sleepDurationBadText;
	
	// SleepEfficiency Chart
	private ListView _sleepEfficiencyChart;
	private ArrayList<Double> sleepEfficiencyArr;
	private TextView _sleepEfficiencyGoodText, _sleepEfficiencyNeutralText, _sleepEfficiencyBadText;
	
	// Time To Fall Asleep Chart
	private ListView _timeToFallAsleepChart;
	private ArrayList<Double> timeToFallAsleepArr;
	private TextView _sleepTimeToFallAsleepGoodText, _sleepTimeToFallAsleepNeutralText, _sleepTimeToFallAsleepBadText;
	
	// Caffeinated Beverage
	private TextView _caffeinatedBeverageGoodText, _caffeinatedBeverageNeutralText, _caffeinatedBeverageBadText;
	
	// MEAL
	private TextView _mealGoodText, _mealNeutralText, _mealBadText;
	
	// EXCERCISE
	private TextView _exerciseGoodText, _exerciseNeutralText, _exerciseBadText;
	
	// ALCOHOL
	private TextView _alcoholGoodText, _alcoholNeutralText, _alcoholBadText;
	
	// MEDICATION
	private TextView _medicationGoodText, _medicationNeutralText, _medicationBadText;
	
	// MEDICATION
	private TextView _tobaccoGoodText, _tobaccoNeutralText, _tobaccoBadText;
	
	private ListView _beforeBedActTop5Good;
	private ListView _beforeBedActTop5Neutral;
	private ListView _beforeBedActTop5Poor;
	
	private LinearLayout _beforeBedActTop5GoodLayout;
	private LinearLayout _beforeBedActTop5NeutralLayout;
	private LinearLayout _beforeBedActTop5PoorLayout;
	
	private ComparisionController _controller;
	
	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		_context = ContextUtil.CONTEXT;
		rootView = inflater.inflate(R.layout.view_comparison, container, false);
		
		_controller = new ComparisionController(onRequestEndListener);
		_contentLayout = (LinearLayout) rootView.findViewById(R.id.content_layout);
		
		_btnSleepQuality = (Button) rootView.findViewById(R.id.btn_sleep_quality);
		_btnDaytimeActivity = (Button) rootView.findViewById(R.id.btn_daytime_activity);
		_btnBeforeBedActivity = (Button) rootView.findViewById(R.id.btn_before_bed_activity);
		
		_btnSleepQuality.setOnClickListener(comparisonClickListener);
		_btnDaytimeActivity.setOnClickListener(comparisonClickListener);
		_btnBeforeBedActivity.setOnClickListener(comparisonClickListener);
		
		_llSleepQualityComparison = (LinearLayout) rootView.findViewById(R.id.sleep_quality_comparison);
		_llDaytimeActivityComparison = (LinearLayout) rootView.findViewById(R.id.daytime_activity_comparison);
		_llBeforeBedActivityComparison = (LinearLayout) rootView.findViewById(R.id.beforebed_activity_comparison);
		_svComparison = (ScrollView) rootView.findViewById(R.id.comparison_scrollview);
		
		_sleepQualityChart = (ListView) rootView.findViewById(R.id.chart_comparison_sleep_quality);
		_sleepDurationChart = (ListView) rootView.findViewById(R.id.chart_comparison_sleep_duration);
		_sleepEfficiencyChart = (ListView) rootView.findViewById(R.id.chart_comparison_sleep_efficiency);
		_timeToFallAsleepChart = (ListView) rootView.findViewById(R.id.chart_comparison_time_to_fall_asleep);
		
		_sleepQualityNumDays = (TextView) rootView.findViewById(R.id.text_sleepQuality);
		_sleepQualityGoodText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_quality_good);
		_sleepQualityNeutralText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_quality_neutral);
		_sleepQualityBadText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_quality_bad);
		
		_sleepDurationGoodText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_duration_good);
		_sleepDurationNeutralText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_duration_neutral);
		_sleepDurationBadText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_duration_bad);
		
		_sleepEfficiencyGoodText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_efficiency_good);
		_sleepEfficiencyNeutralText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_efficiency_neutral);
		_sleepEfficiencyBadText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_efficiency_bad);
		
		_sleepTimeToFallAsleepGoodText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_time_to_fall_asleep_good);
		_sleepTimeToFallAsleepNeutralText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_time_to_fall_asleep_neutral);
		_sleepTimeToFallAsleepBadText = (TextView) rootView.findViewById(R.id.text_comparision_sleep_time_to_fall_asleep_bad);
				
		_beforeBedActTop5Good = (ListView) rootView.findViewById(R.id.chart_beforebed_activity_comparison_good);
		_beforeBedActTop5Neutral = (ListView) rootView.findViewById(R.id.chart_beforebed_activity_comparison_neutral);
		_beforeBedActTop5Poor = (ListView) rootView.findViewById(R.id.chart_beforebed_activity_comparison_poor);
		
		_beforeBedActTop5GoodLayout = (LinearLayout) rootView.findViewById(R.id.layout_beforebed_activity_top5_good);
		_beforeBedActTop5NeutralLayout = (LinearLayout) rootView.findViewById(R.id.layout_beforebed_activity_top5_neutral);
		_beforeBedActTop5PoorLayout = (LinearLayout) rootView.findViewById(R.id.layout_beforebed_activity_top5_poor);
		
		// setBottomLastActivity();
		
		_controller.requestComparision();
		return rootView;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
	
	@Override
	public void onResume() {
		Pie.getInst().isRefreshing = true;
		
		super.onResume();
	}
	
	private void setChart() {
		sleepQuailityArr = new ArrayList<Double>();
		sleepQuailityArr.add(Double.parseDouble(_controller.unit.sleepQualityGood));
		sleepQuailityArr.add(Double.parseDouble(_controller.unit.sleepQualityNeutral));
		sleepQuailityArr.add(Double.parseDouble(_controller.unit.sleepQualityBad));
		
		sleepDurationArr = new ArrayList<Double>();
		sleepDurationArr.add(Double.parseDouble(_controller.unit.sleepDurationGood));
		sleepDurationArr.add(Double.parseDouble(_controller.unit.sleepDurationNeutral));
		sleepDurationArr.add(Double.parseDouble(_controller.unit.sleepDurationBad));
		
		sleepEfficiencyArr = new ArrayList<Double>();
		sleepEfficiencyArr.add(Double.parseDouble(_controller.unit.sleepEfficiencyGood));
		sleepEfficiencyArr.add(Double.parseDouble(_controller.unit.sleepEfficiencyNeutral));
		sleepEfficiencyArr.add(Double.parseDouble(_controller.unit.sleepEfficiencyBad));
		
		timeToFallAsleepArr = new ArrayList<Double>();
		timeToFallAsleepArr.add(Double.parseDouble(_controller.unit.timeToFallAsleepGood));
		timeToFallAsleepArr.add(Double.parseDouble(_controller.unit.timeToFallAsleepNeutral));
		timeToFallAsleepArr.add(Double.parseDouble(_controller.unit.timeToFallAsleepBad));
		
		setSleepQualityChartSetting();
		setSleepDurationChart();
		setEfficiencyChart();
		setTimeToFallAsleepChart();
		setDaytimeLastActivity();
		setBeforeBedActivityChart();
	}
	
	// SleepQuaility Chart Setting
	private void setSleepQualityChartSetting() {
		_sleepQualityGoodText.setText(_controller.unit.sleepQualityGoodStr);
		_sleepQualityNeutralText.setText(_controller.unit.sleepQualityNeutralStr);
		_sleepQualityBadText.setText(_controller.unit.sleepQualityBadStr);
		
		double kHighest = (Collections.max(sleepQuailityArr));
		
		int[] kDurationWidths = new int[sleepQuailityArr.size()];
		updateChartListSizeInfo(StringConst.CHART_TYPE_SLEEP_QUALITY, kHighest, kDurationWidths);
	}
	
	// SleepDuration Chart Setting
	private void setSleepDurationChart() {
		_sleepDurationGoodText.setText(_controller.unit.sleepDurationGoodStr);
		_sleepDurationNeutralText.setText(_controller.unit.sleepDurationNeutralStr);
		_sleepDurationBadText.setText(_controller.unit.sleepDurationBadStr);
		
		double kHighest = (Collections.max(sleepDurationArr));
		
		int[] kDurationWidths = new int[sleepDurationArr.size()];
		updateChartListSizeInfo(StringConst.CHART_TYPE_SLEEP_DURATION, kHighest, kDurationWidths);
	}
	
	// SleepEfficiencyChart Setting
	private void setEfficiencyChart() {
		_sleepEfficiencyGoodText.setText(_controller.unit.sleepEfficiencyGoodStr);
		_sleepEfficiencyNeutralText.setText(_controller.unit.sleepEfficiencyNeutralStr);
		_sleepEfficiencyBadText.setText(_controller.unit.sleepEfficiencyBadStr);
		
		double kHighest = (Collections.max(sleepEfficiencyArr));
		
		int[] kDurationWidths = new int[sleepEfficiencyArr.size()];
		updateChartListSizeInfo(StringConst.CHART_TYPE_SLEEP_EFFICIENCY, kHighest, kDurationWidths);
	}
	
	// TimeToFailAsleepChart Setting
	private void setTimeToFallAsleepChart() {
		_sleepTimeToFallAsleepGoodText.setText(_controller.unit.timeToFallAsleepGoodStr);
		_sleepTimeToFallAsleepNeutralText.setText(_controller.unit.timeToFallAsleepNeutralStr);
		_sleepTimeToFallAsleepBadText.setText(_controller.unit.timeToFallAsleepBadStr);
		
		double kHighest = (Collections.max(timeToFallAsleepArr));
		
		int[] kDurationWidths = new int[timeToFallAsleepArr.size()];
		updateChartListSizeInfo(StringConst.CHART_TYPE_SLEEP_FALL, kHighest, kDurationWidths);
	}
	
	private void setBeforeBedActivityChart() {
		int kHighestGood = 0;
		int kHighestNeutral = 0;
		int kHighestPoor = 0;
		int[] kbeforeBedActTop5Good = new int[0];
		int[] kbeforeBedActTop5Neutral = new int[0];
		int[] kbeforeBedActTop5Poor = new int[0];
		
		// Top 5 Before Bed Activities for Good Quality Sleep
		int countGood = _controller.unit.top5RitualsGood.size();
		if (countGood > 0) {
			for (int i = 0; i < countGood; i++) {
				Top5RitualUnit aUnit = _controller.unit.top5RitualsGood.get(i);
				String name = aUnit.name.replace("[\"", "");
				int count = aUnit.frequency;
				name = name.replace("\"]", "");
				name = name + formatCount(count);
				TextView textView = createTextView(name);
				_beforeBedActTop5GoodLayout.addView(textView);
			}		
			kHighestGood = _controller.unit.top5RitualsGood.get(0).frequency;
			kbeforeBedActTop5Good = new int[_controller.unit.top5RitualsGood.size()];
		}
		
		// Top 5 Before Bed Activities for Neutral Quality Sleep
		int countNeutral = _controller.unit.top5RitualsNeutral.size();
		if (countNeutral > 0) {
			for (int i = 0; i < countNeutral; i++) {
				Top5RitualUnit aUnit = _controller.unit.top5RitualsNeutral.get(i);
				String name = aUnit.name.replace("[\"", "");
				int count = aUnit.frequency;
				name = name.replace("\"]", "");
				name = name + formatCount(count);
				TextView textView = createTextView(name);
				_beforeBedActTop5NeutralLayout.addView(textView);
			}
			kHighestNeutral = _controller.unit.top5RitualsNeutral.get(0).frequency;
			kbeforeBedActTop5Neutral = new int[_controller.unit.top5RitualsNeutral.size()];
		}
		
		// Top 5 Before Bed Activities for Poor Quality Sleep
		int countPoor = _controller.unit.top5RitualsPoor.size();
		if (countPoor > 0) {
			for (int i = 0; i < countPoor; i++) {
				Top5RitualUnit aUnit = _controller.unit.top5RitualsPoor.get(i);
				String name = aUnit.name.replace("[\"", "");
				int count = aUnit.frequency;
				name = name.replace("\"]", "");
				name = name + formatCount(count);
				TextView textView = createTextView(name);
				_beforeBedActTop5PoorLayout.addView(textView);
			}
			kHighestPoor = _controller.unit.top5RitualsPoor.get(0).frequency;
			kbeforeBedActTop5Poor = new int[_controller.unit.top5RitualsPoor.size()];
		}
		
		double normalizeMax = Math.max(kHighestGood, kHighestNeutral);
		normalizeMax = Math.max(normalizeMax, kHighestPoor);
		
		updateChartListSizeInfo(StringConst.CHART_BEFOREBED_TOP5_GOOD, normalizeMax, kbeforeBedActTop5Good);
		updateChartListSizeInfo(StringConst.CHART_BEFOREBED_TOP5_NEUTRAL, normalizeMax, kbeforeBedActTop5Neutral);
		updateChartListSizeInfo(StringConst.CHART_BEFOREBED_TOP5_POOR, normalizeMax, kbeforeBedActTop5Poor);
	}
	
	private TextView createTextView(String text) {
		TextView textView = new TextView(_context);
		textView.setText(text);
		textView.setGravity(Gravity.CENTER_VERTICAL);
		textView.setTextSize(12);
		textView.setTextColor(getResources().getColor(R.color.Black));
		LinearLayout.LayoutParams textViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
		textViewlp.setMargins(0, 0, 0, 1);
		textView.setLayoutParams(textViewlp);
		return textView;
	}
	
	private String formatCount (int count) {
		String strCount;
		strCount = " (" + count + ")";
		return strCount;
	}
	
	private String formatCount(Float count, String actName, String actTime) {
		String description = "";
		if (count == 1) {
			description = count + " time";
		} else {
			description = count + " times";
		}
		
		if (count > 0) {
			description += ", last at " + actTime;
		}
		
		return description;
	}
	
	// Daytime Last XX Activity Setting
	private void setDaytimeLastActivity() {
		LinearLayout _contentLayout = (LinearLayout) rootView.findViewById(R.id.hidden_container);

		for (int i = 0; i < _controller.unit.activityInfos.size(); i++) {
			ComparisionLastActInfoUnit lastActInfo = _controller.unit.activityInfos.get(i);
			
			String goodText = formatCount(lastActInfo.goodCount, lastActInfo.name, lastActInfo.goodEndTime);
			String neutralText = formatCount(lastActInfo.neutralCount, lastActInfo.name, lastActInfo.neutralEndTime);
			String poorText = formatCount(lastActInfo.badCount, lastActInfo.name, lastActInfo.badEndTime);
			
			if (lastActInfo.name.contains("Caffeine")) {
				_caffeinatedBeverageGoodText = (TextView) rootView.findViewById(R.id.text_comparison_caffenated_beverage_good);
				_caffeinatedBeverageNeutralText = (TextView) rootView.findViewById(R.id.text_comparison_caffenated_beverage_neutral);
				_caffeinatedBeverageBadText = (TextView) rootView.findViewById(R.id.text_comparison_caffenated_beverage_bad);
				
				_caffeinatedBeverageGoodText.setText(goodText);
				_caffeinatedBeverageNeutralText.setText(neutralText);
				_caffeinatedBeverageBadText.setText(poorText);
				
				LinearLayout _temp = (LinearLayout) rootView.findViewById(R.id.daytime_activity_caffeine);
				_contentLayout.removeView(_temp);
				_llDaytimeActivityComparison.addView(_temp);
				
				ImageView divider = new ImageView(_context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
				lp.setMargins(10, 10, 10, 10);
				divider.setLayoutParams(lp);

				//divider.setBackgroundColor(color.MediumGray);
				divider.setBackgroundColor(getResources().getColor(color.MediumGray));

				_llDaytimeActivityComparison.addView(divider);
			} else if (lastActInfo.name.contains("Meal"))	{
				_mealGoodText = (TextView) rootView.findViewById(R.id.text_comparison_meal_good);
				_mealNeutralText = (TextView) rootView.findViewById(R.id.text_comparison_meal_neutral);
				_mealBadText = (TextView) rootView.findViewById(R.id.text_comparison_meal_bad);
				
				_mealGoodText.setText(goodText);
				_mealNeutralText.setText(neutralText);
				_mealBadText.setText(poorText);
				
				LinearLayout _temp = (LinearLayout) rootView.findViewById(R.id.daytime_activity_meal);	
				_contentLayout.removeView(_temp);
				_llDaytimeActivityComparison.addView(_temp);
				
				ImageView divider = new ImageView(_context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
				lp.setMargins(10, 10, 10, 10);
				divider.setLayoutParams(lp);
				//divider.setBackgroundColor(color.MediumGray);
				divider.setBackgroundColor(getResources().getColor(color.MediumGray));

				_llDaytimeActivityComparison.addView(divider);
			} else if (lastActInfo.name.contains("Exercise")) {
				_exerciseGoodText = (TextView) rootView.findViewById(R.id.text_comparison_exercise_good);
				_exerciseNeutralText = (TextView) rootView.findViewById(R.id.text_comparison_exercise_neutral);
				_exerciseBadText = (TextView) rootView.findViewById(R.id.text_comparison_exercise_bad);
				
				_exerciseGoodText.setText(goodText);
				_exerciseNeutralText.setText(neutralText);
				_exerciseBadText.setText(poorText);
				
				LinearLayout _temp = (LinearLayout) rootView.findViewById(R.id.daytime_activity_exercise);	
				_contentLayout.removeView(_temp);
				_llDaytimeActivityComparison.addView(_temp);
				
				ImageView divider = new ImageView(_context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
				lp.setMargins(10, 10, 10, 10);
				divider.setLayoutParams(lp);
				//divider.setBackgroundColor(color.MediumGray);
				divider.setBackgroundColor(getResources().getColor(color.MediumGray));

				_llDaytimeActivityComparison.addView(divider);
			} else if (lastActInfo.name.contains("Alcohol")) {
				_alcoholGoodText = (TextView) rootView.findViewById(R.id.text_comparison_alcohol_good);
				_alcoholNeutralText = (TextView) rootView.findViewById(R.id.text_comparison_alcohol_neutral);
				_alcoholBadText = (TextView) rootView.findViewById(R.id.text_comparison_alcohol_bad);
				
				_alcoholGoodText.setText(goodText);
				_alcoholNeutralText.setText(neutralText);
				_alcoholBadText.setText(poorText);
				
				LinearLayout _temp = (LinearLayout) rootView.findViewById(R.id.daytime_activity_alcohol);	
				_contentLayout.removeView(_temp);
				_llDaytimeActivityComparison.addView(_temp);
				
				ImageView divider = new ImageView(_context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
				lp.setMargins(10, 10, 10, 10);
				divider.setLayoutParams(lp);
				//divider.setBackgroundColor(color.MediumGray);
				divider.setBackgroundColor(getResources().getColor(color.MediumGray));

				_llDaytimeActivityComparison.addView(divider);
			} else if (lastActInfo.name.contains("Medication")) {
				_medicationGoodText = (TextView) rootView.findViewById(R.id.text_comparison_medication_good);
				_medicationNeutralText = (TextView) rootView.findViewById(R.id.text_comparison_medication_neutral);
				_medicationBadText = (TextView) rootView.findViewById(R.id.text_comparison_medication_bad);
				
				_medicationGoodText.setText(goodText);
				_medicationNeutralText.setText(neutralText);
				_medicationBadText.setText(poorText);
				
				LinearLayout _temp = (LinearLayout) rootView.findViewById(R.id.daytime_activity_medication);	
				_contentLayout.removeView(_temp);
				_llDaytimeActivityComparison.addView(_temp);
				
				ImageView divider = new ImageView(_context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
				lp.setMargins(10, 10, 10, 10);
				divider.setLayoutParams(lp);
				//divider.setBackgroundColor(color.MediumGray);
				divider.setBackgroundColor(getResources().getColor(color.MediumGray));

				_llDaytimeActivityComparison.addView(divider);
			} else if (lastActInfo.name.contains("Tobacco")) {
				_tobaccoGoodText = (TextView) rootView.findViewById(R.id.text_comparison_tobacco_good);
				_tobaccoNeutralText = (TextView) rootView.findViewById(R.id.text_comparison_tobacco_neutral);
				_tobaccoBadText = (TextView) rootView.findViewById(R.id.text_comparison_tobacco_bad);
				
				_tobaccoGoodText.setText(goodText);
				_tobaccoNeutralText.setText(neutralText);
				_tobaccoBadText.setText(poorText);
				
				LinearLayout _temp = (LinearLayout) rootView.findViewById(R.id.daytime_activity_tobacco);	
				_contentLayout.removeView(_temp);
				_llDaytimeActivityComparison.addView(_temp);
				
				ImageView divider = new ImageView(_context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
				lp.setMargins(10, 10, 10, 10);
				divider.setLayoutParams(lp);
				//divider.setBackgroundColor(color.MediumGray);
				divider.setBackgroundColor(getResources().getColor(color.MediumGray));

				_llDaytimeActivityComparison.addView(divider);
			} else {
				LinearLayout aLinearLayout = createDayTimeActivity(lastActInfo);
				_llDaytimeActivityComparison.addView(aLinearLayout);
				
				ImageView divider = new ImageView(_context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
				lp.setMargins(10, 10, 10, 10);
				divider.setLayoutParams(lp);
				//divider.setBackgroundColor(color.MediumGray);
				divider.setBackgroundColor(getResources().getColor(color.MediumGray));
						_llDaytimeActivityComparison.addView(divider);
			}
		}
	}
	
	private LinearLayout createDayTimeActivity(ComparisionLastActInfoUnit lastActInfo) {
		String goodText = formatCount(lastActInfo.goodCount, lastActInfo.name, lastActInfo.goodEndTime);
		String neutralText = formatCount(lastActInfo.neutralCount, lastActInfo.name, lastActInfo.neutralEndTime);
		String poorText = formatCount(lastActInfo.badCount, lastActInfo.name, lastActInfo.badEndTime);
		
		LinearLayout parentLayout = new LinearLayout(_context);
		parentLayout.setOrientation(LinearLayout.HORIZONTAL);
		parentLayout.setPadding(0, 5, 0, 5);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		parentLayout.setLayoutParams(lp);
		
		// Child - Header Layout 
		LinearLayout childHeaderLayout = new LinearLayout(_context);
		childHeaderLayout.setOrientation(LinearLayout.VERTICAL);
		childHeaderLayout.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams childHeaderlp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);
		childHeaderLayout.setLayoutParams(childHeaderlp);
		
		ImageView customActivityImageView = new ImageView(_context);
		customActivityImageView.setPadding(3, 3, 3, 3);
		int scale = (int) (this.getResources().getDisplayMetrics().density);
		LinearLayout.LayoutParams customActlp = new LinearLayout.LayoutParams(rectWidth * scale, rectWidth * scale);
		customActivityImageView.setLayoutParams(customActlp);
		
		Paint rectPaint = new Paint();
		rectPaint.setColor(Color.parseColor(lastActInfo.color));
		//float scale = this.getResources().getDisplayMetrics().density;
		
		int rectWidthScaled = (int) (rectWidth * scale + 0.5f);
		Rect rect = new Rect(0, 0, rectWidthScaled, rectWidthScaled);
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		//Bitmap bmp = Bitmap.createBitmap(rect.width(), rect.height(), conf);
		Bitmap bmp = Bitmap.createBitmap(rectWidthScaled, rectWidthScaled, conf);
		Canvas canvas = new Canvas (bmp);
		canvas.drawRect(rect, rectPaint);
		customActivityImageView.setImageBitmap(bmp);
		
		TextView customActivityTextView = new TextView(_context);
		customActivityTextView.setGravity(Gravity.CENTER);
		customActivityTextView.setTextSize(12);
		customActivityTextView.setTextColor(getResources().getColor(R.color.Black));
		LinearLayout.LayoutParams customActTextViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		customActivityTextView.setLayoutParams(customActTextViewlp);
		customActivityTextView.setText(lastActInfo.name.toUpperCase());
		
		childHeaderLayout.addView(customActivityImageView);
		childHeaderLayout.addView(customActivityTextView);
		
		// Child - Quality (Good, Neutral, Poor) Header Layout
		LinearLayout qualityHeaderLayout = new LinearLayout(_context);
		qualityHeaderLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams qualityHeaderlp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 0.8f);
		qualityHeaderLayout.setLayoutParams(qualityHeaderlp);
		
		TextView goodQualityTextView = new TextView(_context);
		goodQualityTextView.setGravity(Gravity.CENTER_VERTICAL);
		goodQualityTextView.setGravity(Gravity.LEFT);
		goodQualityTextView.setTextSize(12);
		goodQualityTextView.setTextColor(getResources().getColor(R.color.Good));
		goodQualityTextView.setTypeface(Typeface.DEFAULT_BOLD);
		goodQualityTextView.setText("GOOD");
		LinearLayout.LayoutParams goodQualityTextViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
		goodQualityTextView.setLayoutParams(goodQualityTextViewlp);
		
		TextView neutralQualityTextView = new TextView(_context);
		neutralQualityTextView.setGravity(Gravity.CENTER_VERTICAL);
		neutralQualityTextView.setGravity(Gravity.LEFT);
		neutralQualityTextView.setTextSize(12);
		neutralQualityTextView.setTextColor(getResources().getColor(R.color.NeutralText));
		neutralQualityTextView.setTypeface(Typeface.DEFAULT_BOLD);
		neutralQualityTextView.setText("NEUTRAL");
		LinearLayout.LayoutParams neutralQualityTextViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
		neutralQualityTextView.setLayoutParams(neutralQualityTextViewlp);
		
		TextView poorQualityTextView = new TextView(_context);
		poorQualityTextView.setGravity(Gravity.CENTER_VERTICAL);
		poorQualityTextView.setGravity(Gravity.LEFT);
		poorQualityTextView.setTextSize(12);
		poorQualityTextView.setTextColor(getResources().getColor(R.color.Poor));
		poorQualityTextView.setTypeface(Typeface.DEFAULT_BOLD);
		poorQualityTextView.setText("POOR");
		LinearLayout.LayoutParams poorQualityTextViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
		poorQualityTextView.setLayoutParams(poorQualityTextViewlp);
		
		qualityHeaderLayout.addView(goodQualityTextView);
		qualityHeaderLayout.addView(neutralQualityTextView);
		qualityHeaderLayout.addView(poorQualityTextView);
		
		// Child - Quality (Good, Neutral, Poor) Values Layout
		LinearLayout countLayout = new LinearLayout(_context);
		countLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams countlp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 2f);
		countLayout.setLayoutParams(countlp);
		
		TextView goodCountTextView = new TextView(_context);
		goodCountTextView.setGravity(Gravity.CENTER_VERTICAL);
		goodCountTextView.setTextSize(12);
		goodCountTextView.setText(goodText);
		goodCountTextView.setTextColor(getResources().getColor(R.color.Black));
		LinearLayout.LayoutParams goodCountTextViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
		goodCountTextView.setLayoutParams(goodCountTextViewlp);
		
		TextView neutralCountTextView = new TextView(_context);
		neutralCountTextView.setGravity(Gravity.CENTER_VERTICAL);
		neutralCountTextView.setTextSize(12);
		neutralCountTextView.setText(neutralText);
		neutralCountTextView.setTextColor(getResources().getColor(R.color.Black));
		LinearLayout.LayoutParams neutralCountTextViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
		neutralCountTextView.setLayoutParams(neutralCountTextViewlp);
		
		TextView poorCountTextView = new TextView(_context);
		poorCountTextView.setGravity(Gravity.CENTER_VERTICAL);
		poorCountTextView.setTextSize(12);
		poorCountTextView.setText(poorText);
		poorCountTextView.setTextColor(getResources().getColor(R.color.Black));
		LinearLayout.LayoutParams poorCountTextViewlp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f);
		poorCountTextView.setLayoutParams(poorCountTextViewlp);
		
		countLayout.addView(goodCountTextView);
		countLayout.addView(neutralCountTextView);
		countLayout.addView(poorCountTextView);
		
		parentLayout.addView(childHeaderLayout);
		parentLayout.addView(qualityHeaderLayout);
		parentLayout.addView(countLayout);
		
		return parentLayout;
	}
	
	/*
	 * Chart
	 */
	private void updateChartListSizeInfo(String $type, Double $widthMax, int[] $widthArr) {
		int adjWidthforChart;
		adjWidthforChart = (int)(_sleepQualityChart.getWidth() * 0.8);
		int adjWidthforTop5;
		adjWidthforTop5 = (int)(_beforeBedActTop5Good.getWidth() * 0.8);
		
		
//		if (adjWidthforChart == 0) {
//			adjWidthforChart = 100;
//		}
		
		if ($type.equals(StringConst.CHART_TYPE_SLEEP_QUALITY)) {
			for (int i = 0; i < sleepQuailityArr.size(); i++) {
				$widthArr[i] = (int) (adjWidthforChart * (sleepQuailityArr.get(i) / $widthMax));
			}
			_sleepQualityChart.setAdapter(new SleepQualityChartAdapter(_context, sleepQuailityArr, $type, $widthArr));
		} else if ($type.equals(StringConst.CHART_TYPE_SLEEP_DURATION)) {
			for (int i = 0; i < sleepDurationArr.size(); i++) {
				$widthArr[i] = (int) ((adjWidthforChart * sleepDurationArr.get(i)) / $widthMax);
			}
			_sleepDurationChart.setAdapter(new SleepQualityChartAdapter(_context, sleepDurationArr, $type, $widthArr));
		} else if ($type.equals(StringConst.CHART_TYPE_SLEEP_EFFICIENCY)) {
			for (int i = 0; i < sleepEfficiencyArr.size(); i++) {
				$widthArr[i] = (int) ((adjWidthforChart * sleepEfficiencyArr.get(i)) / $widthMax);
			}
			_sleepEfficiencyChart.setAdapter(new SleepQualityChartAdapter(_context, sleepEfficiencyArr, $type, $widthArr));
		} else if ($type.equals(StringConst.CHART_TYPE_SLEEP_FALL)) {
			for (int i = 0; i < timeToFallAsleepArr.size(); i++) {
				$widthArr[i] = (int) ((adjWidthforChart * timeToFallAsleepArr.get(i)) / $widthMax);
			}
			_timeToFallAsleepChart.setAdapter(new SleepQualityChartAdapter(_context, timeToFallAsleepArr, $type, $widthArr));
		} else if ($type.equals(StringConst.CHART_BEFOREBED_TOP5_GOOD)) {
			for (int i = 0; i < _controller.unit.top5RitualsGood.size(); i++) {
				$widthArr[i] = (int) ((adjWidthforTop5 * _controller.unit.top5RitualsGood.get(i).frequency) / $widthMax);
			}
			_beforeBedActTop5Good.setAdapter(new BeforeBedTop5ChartAdapter(_context, _controller.unit.top5RitualsGood, StringConst.CHART_BEFOREBED_TOP5_GOOD, $widthArr));
		}  else if ($type.equals(StringConst.CHART_BEFOREBED_TOP5_NEUTRAL)) {
			for (int i = 0; i < _controller.unit.top5RitualsNeutral.size(); i++) {
				$widthArr[i] = (int) ((adjWidthforTop5 * _controller.unit.top5RitualsNeutral.get(i).frequency) / $widthMax);
			}
			_beforeBedActTop5Neutral.setAdapter(new BeforeBedTop5ChartAdapter(_context, _controller.unit.top5RitualsNeutral, StringConst.CHART_BEFOREBED_TOP5_NEUTRAL, $widthArr));
		}   else if ($type.equals(StringConst.CHART_BEFOREBED_TOP5_POOR)) {
			for (int i = 0; i < _controller.unit.top5RitualsPoor.size(); i++) {
				$widthArr[i] = (int) ((adjWidthforTop5 * _controller.unit.top5RitualsPoor.get(i).frequency) / $widthMax);
			}
			_beforeBedActTop5Poor.setAdapter(new BeforeBedTop5ChartAdapter(_context, _controller.unit.top5RitualsPoor, StringConst.CHART_BEFOREBED_TOP5_POOR, $widthArr));
		}
	}
	
	public class SleepQualityChartAdapter extends BaseAdapter	{
		Context context;
		ArrayList<Double> array;
		String type;
		int[] widthArr;
		
		public SleepQualityChartAdapter(Context context, ArrayList<Double> arr, String $type, int[] $widthArr) {
			this.context = context;
			this.array = arr;
			this.type = $type;
			this.widthArr = $widthArr;
		}
		
		public int getCount() {
			return array.size();
		}
		
		public Object getItem(int position) {
			return array.get(position);
		}
		
		public long getItemId(int position) {
			return position;
		}
		
		public View getView(final int position, View convertView, ViewGroup parent)	{
			View kRow = null;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			kRow = inflater.inflate(R.layout.view_bottom_chart_list_item, null);
			
			TextView kColumn = (TextView) kRow.findViewById(R.id.colortext01);
			
			kColumn.setWidth(widthArr[position]);
			
			// Set column color 
			if (position == 0) {
				kColumn.setBackgroundColor(_context.getResources().getColor(R.color.Good));
			} else if (position == 1) {
				kColumn.setBackgroundColor(_context.getResources().getColor(R.color.Neutral));
			} else if (position == 2) {
				kColumn.setBackgroundColor(_context.getResources().getColor(R.color.Poor));
			}
			
			kColumn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				
				}
			});
			
			return kRow;
		}
	}
	
	public class BeforeBedTop5ChartAdapter extends BaseAdapter	{
		Context context;
		ArrayList<Top5RitualUnit> array;
		String type;
		int[] widthArr;
		
		public BeforeBedTop5ChartAdapter(Context context, ArrayList<Top5RitualUnit> arr, String $type, int[] $widthArr) {
			this.context = context;
			this.array = arr;
			this.type = $type;
			this.widthArr = $widthArr;
		}
		
		public int getCount() {
			return array.size();
		}
		
		public Object getItem(int position) {
			return array.get(position);
		}
		
		public long getItemId(int position) {
			return array.get(position).id;
		}
		
		public View getView(final int position, View convertView, ViewGroup parent)	{
			View kRow = null;
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			kRow = inflater.inflate(R.layout.view_top5_chart_list_item, null);
			
			TextView kColumn = (TextView) kRow.findViewById(R.id.top5bar);
			
			kColumn.setWidth(widthArr[position]);
			
			// Set column color 
			if (type == StringConst.CHART_BEFOREBED_TOP5_GOOD) {
				kColumn.setBackgroundColor(_context.getResources().getColor(R.color.Good));
			} else if (type == StringConst.CHART_BEFOREBED_TOP5_NEUTRAL) {
				kColumn.setBackgroundColor(_context.getResources().getColor(R.color.NeutralText));
			} else if (type == StringConst.CHART_BEFOREBED_TOP5_POOR) {
				kColumn.setBackgroundColor(_context.getResources().getColor(R.color.Poor));
			}
			
			kColumn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				
				}
			});
			
			return kRow;
		}
	}
	
	public void jumptoDaytimeActivity (View v) {
		int a = 1;
	}
	
	private OnClickListener comparisonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_sleep_quality:
					_svComparison.smoothScrollTo(0, 0);
					break;
					
				case R.id.btn_daytime_activity:
					_svComparison.smoothScrollTo(0, _llSleepQualityComparison.getHeight() - 10);
					break;
					
				case R.id.btn_before_bed_activity:
					_svComparison.smoothScrollTo(0, _llSleepQualityComparison.getHeight() + _llDaytimeActivityComparison.getHeight() );
				break;
			}
		}
	};
	
	
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndComparision:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Comparision loading: success!");
					setChart();
					break;
				
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Network Failure");
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
				Pie.getInst().isRefreshing = true;
			}
		}
	}
}