package kr.mintech.sleep.tight.consts;

import java.text.SimpleDateFormat;

public class SleepTightConstants {
	public static SimpleDateFormat LastEditDateFormat = new SimpleDateFormat("E, MMM dd yyyy");
	public static SimpleDateFormat AMPM_TimeFormat = new SimpleDateFormat("h:mm a");
	public static SimpleDateFormat DateTimeFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a"); // May 1 2013 11:10 PM
	public static SimpleDateFormat DurationFormat = new SimpleDateFormat("HH: mm");
	public static SimpleDateFormat DateForamt = new SimpleDateFormat("MM-dd hh:mm a");
	public static SimpleDateFormat HandleForamt = new SimpleDateFormat("h:mm a - MMM, dd");
	
	public static SimpleDateFormat DayForamt = new SimpleDateFormat("MM/dd");
	public static SimpleDateFormat NetworkFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'Z");
	
	public static SimpleDateFormat TitleDayFormat = new SimpleDateFormat("MMM dd");
	public static SimpleDateFormat DayOnlyFormat = new SimpleDateFormat("dd");
}