package kr.mintech.sleep.tight.utils;

import java.util.ArrayList;

import kr.mintech.sleep.tight.units.ActionUnit;
import kr.mintech.sleep.tight.units.ActivityTrackUnit;
import kr.mintech.sleep.tight.units.SleepTrackUnit;

public class Pie {
	private static Pie _instance;
	
	public String startTime, endTime, activity;
	public ArrayList<String> beforeBedActIdArr = new ArrayList<String>();
	public ArrayList<String> sleepDisturbIdArr = new ArrayList<String>();
	public ArrayList<String> beforeBedActArr = new ArrayList<String>();
	public ArrayList<String> sleepDisturbArr = new ArrayList<String>();
	
	public DateTime wakeTime, outBedTime;
	public boolean isEmptySleepDiary;
	public boolean isRefreshing = false;
	/**
	 * Go to current time once loaded? Used to force the AddActivityView
	 * to go to the current time after it loads.
	 */
	public boolean goToCurrentTimeAfterLoad = false;
	
	public DateTime baseTime;
	public DateTime toBedTime, toSleepTime, toWakeTime, toOutBedTime;
	public DateTime graphEndDate;
	public ArrayList<ActivityTrackUnit> activityTrackUnit = new ArrayList<ActivityTrackUnit>();
	public ArrayList<ActivityTrackUnit> activityTrackUnitForWidget = new ArrayList<ActivityTrackUnit>();
	public ArrayList<SleepTrackUnit> sleepTrackUnit = new ArrayList<SleepTrackUnit>();
	public ArrayList<SleepTrackUnit> sleepTrackUnitArray = new ArrayList<SleepTrackUnit>();
	public ArrayList<ActionUnit> actionkUnit = new ArrayList<ActionUnit>();
	public ArrayList<ActionUnit> actionkUnitForWidget = new ArrayList<ActionUnit>();
	
	public DateTime handleTime;
	
	public String widgetLocation = "homeScreenWidget";
	public String currentPage = "addActivity";
	public String currentRange = "1-week";
	
	public static Pie getInst()	{
		if (_instance == null)
			_instance = new Pie();
		return _instance;
	}
}
