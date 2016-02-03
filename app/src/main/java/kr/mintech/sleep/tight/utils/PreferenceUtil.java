package kr.mintech.sleep.tight.utils;

import Util.BasePreferenceUtil;
import Util.StringUtil;

public final class PreferenceUtil extends BasePreferenceUtil
{
	/***********************
	 * key
	 **********************/
	// First run
	private static final String KEY_IS_FIRST_RUN = "first_run";
	private static final String KEY_UNIQUE_PHONE_ID = "phone_uuid";
	
	private static final String KEY_USER_ACTIVITY = "user_activity";
	
	//User reminder Key
	private static final String KEY_REMINDER = "diary_reminder";
	
	//Reminder Time
	private static final String KEY_REMINDER_TIME = "reminder_time";
	private static final String KEY_REMINDER_DATE = "reminder_date";
	
	
	/***********************
	 * method
	 **********************/
	/**
	 * Called after the first run
	 */
	public static void setFirstRun()
	{
		setKey(KEY_IS_FIRST_RUN, false);
	}
	
	
	/**
	 * Is this the first run?
	 * @return
	 */
	public static boolean getFirstRun()
	{
		return getBoolean(KEY_IS_FIRST_RUN, true);
	}
	
	
	/**
	 * Reminder Date Get
	 * @return
	 */
	public static String getAndroidId()
	{
		return getString(KEY_UNIQUE_PHONE_ID);
	}
	
	
	/**
	 * Reminder Date Set
	 */
	public static void setAndroidId(String $androidId)
	{
		setKey(KEY_UNIQUE_PHONE_ID, $androidId);
	}
	
	
	/**
	 * Get user activity
	 * @return
	 */
	public static String getUserActivity()
	{
		return getString(KEY_USER_ACTIVITY);
	}
	
	
	/**
	 * Set user activity
	 */
	public static void setUserActivity(String $activityArrString)
	{
		setKey(KEY_USER_ACTIVITY, $activityArrString);
	}
	
	
	/**
	 * Reminder Notification
	 * @return
	 */
	public static String getDiaryReminder()
	{
		return getString(KEY_REMINDER);
	}
	
	
	/**
	 * Reminder Notification
	 */
	public static void setDiaryReminder(String $isTrue)
	{
		setKey(KEY_REMINDER, $isTrue);
	}
	
	
	/**
	 * Reminder Time Get
	 * @return
	 */
	public static String getDiaryReminderTime()
	{
		return getString(KEY_REMINDER_TIME);
	}
	
	
	/**
	 * Reminder Time Set
	 */
	public static void setDiaryReminderTime(String $time)
	{
		setKey(KEY_REMINDER_TIME, $time);
	}
	
	
	/**
	 * Reminder Date Get
	 * @return
	 */
	public static String getDiaryReminderDate()
	{
		return getString(KEY_REMINDER_DATE);
	}
	
	
	/**
	 * Reminder Date Set
	 */
	public static void setDiaryReminderDate(String $date)
	{
		setKey(KEY_REMINDER_DATE, $date);
	}
	
}
