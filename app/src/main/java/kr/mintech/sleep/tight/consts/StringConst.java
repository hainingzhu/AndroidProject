package kr.mintech.sleep.tight.consts;

public class StringConst {
	public static final String SERVER_TEST = "test_server";
	public static final String KEY_KILL_APP = "kill_app";
	
	//Summary Type
	public static final String CHART_SUMMARY_TYPE_SLEEP_DUARTION = "Sleep Duration";
	public static final String CHART_SUMMARY_TYPE_SLEEP_EFFICIENCY = "Sleep Efficiency";
	public static final String CHART_SUMMARY_TYPE_SLEEP_TIME_TO_FALL_ASLEEP = "Time to fall Asleep";
	
	//CHART Type
	public static final String CHART_TYPE_SLEEP_DURATION = "sleep_durtaion";
	public static final String CHART_TYPE_SLEEP_EFFICIENCY = "sleep_efficiency";
	public static final String CHART_TYPE_SLEEP_FALL = "sleep_fall";
	public static final String CHART_TYPE_SLEEP_QUALITY = "sleep_quality";
	
	//Activity
	public static final String ACTIVITY_CAFFEINE = "Caffeine";
	public static final String ACTIVITY_MEAL = "Meal";
	public static final String ACTIVITY_MEDICATION = "Medication";
	public static final String ACTIVITY_ALCOHOL = "Alcohol";
	public static final String ACTIVITY_EXERCISE = "Exercise";
	public static final String ACTIVITY_TOBACCO = "Tobacco";
	
	public static final String ACTIVITY_ACT1 = "act1";
	public static final String ACTIVITY_ACT2 = "act2";
	public static final String ACTIVITY_ACT3 = "act3";
	public static final String ACTIVITY_ACT4 = "act4";
	
	public static final String ACTIVITY_SHOW = "activity_show";
	public static final String ACTIVITY_HIDE = "activity_hide";
	public static final String ACTIVITY_SORT = "activity_sort";
	
	//Before Bed Activity Top5
	public static final String CHART_BEFOREBED_TOP5_GOOD = "before_bed_good";
	public static final String CHART_BEFOREBED_TOP5_NEUTRAL = "before_bed_neutral";
	public static final String CHART_BEFOREBED_TOP5_POOR = "before_bed_poor";
	
	/*
	 * API
	 */
	public static final String API_ACTIVITY_TRACKS = "/api/activity_tracks";
	public static final String API_ACTIVITY_SLEEP_TRACK = "/api/sleep_tracks";
	public static final String API_ACTIONS = "/api/activities";
	public static final String API_REGISTER_USER = "/api/register_user";
	public static final String API_REGISTER_UPDATE_USER = "/api/users";
	
	public static final String API_HIDE_SHOW_ACTIONS = "/api/activities/hide_show_activities";
	public static final String API_SORT_ACTIONS = "/api/activities/sort";
	
	public static final String API_SLEEP_DISTURB = "/api/sleep_disturbances";
	public static final String API_BEFORE_BED_ACT = "/api/sleep_rituals";
	
	public static final String API_SLEEP_SUMMARY_DAILY = "/api/daily_sleep_summary";
	public static final String API_SLEEP_SUMMARY_WEEKLY = "/api/weekly_sleep_summary";
	public static final String API_BIN_INFO_WEEKLY = "/api/track_sets";
	public static final String API_COMPARISION = "/api/comparison";
}
