package Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil
{
	// Get day of the week
	public static String getTodayOfTheWeek()
	{
		String kDay = null;
		Calendar kCalendar = Calendar.getInstance();
		int kDayOfWeek = kCalendar.get(Calendar.DAY_OF_WEEK);
		if (kDayOfWeek == 1)
		{
			kDay = "Sun";
		}
		else if (kDayOfWeek == 2)
		{
			kDay = "Mon";
		}
		else if (kDayOfWeek == 3)
		{
			kDay = "Tue";
		}
		else if (kDayOfWeek == 4)
		{
			kDay = "Wed";
		}
		else if (kDayOfWeek == 5)
		{
			kDay = "Thu";
		}
		else if (kDayOfWeek == 6)
		{
			kDay = "Fri";
		}
		else if (kDayOfWeek == 7)
		{
			kDay = "Sat";
		}
		
		return kDay;
	}
	
	
	// Get day of the week 
	public static String getDayOfTheWeek(int $day)
	{
		String kDay = null;
		if ($day == 1)
		{
			kDay = "Sun";
		}
		else if ($day == 2)
		{
			kDay = "Mon";
		}
		else if ($day == 3)
		{
			kDay = "Tue";
		}
		else if ($day == 4)
		{
			kDay = "Wed";
		}
		else if ($day == 5)
		{
			kDay = "Thu";
		}
		else if ($day == 6)
		{
			kDay = "Fri";
		}
		else if ($day == 7)
		{
			kDay = "Sat";
		}
		
		return kDay;
	}
	
	
	/*
	 * 7-day Date and Day
	 */
	public static ArrayList<String> getDaysOfTheWeekWithDate()
	{
		ArrayList<String> kDayArr = new ArrayList<String>();
		String kDay;
		
		for (int i = -7; i < 0; i++)
		{
			Calendar kCalendar = Calendar.getInstance();
			kCalendar.add(Calendar.DATE, i);
			int kDayOfWeek = kCalendar.get(Calendar.DAY_OF_WEEK);
			int kDate = kCalendar.get(Calendar.DAY_OF_MONTH);
			kDay = getDayOfTheWeek(kDayOfWeek) + " " + Integer.toString(kDate);
			kDayArr.add(kDay);
			
		}
		return kDayArr;
	}
	
	
	/*
	 * 7-week long Date info
	 */
	public static ArrayList<String> getWeeksOfTheSevenWeek()
	{
		ArrayList<String> kWeeksArr = new ArrayList<String>();
		String kWeeks = null;
		for (int i = -24; i < 25; i++)
		{
			if (i == -24)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kMonth = kCalendar.get(Calendar.MONTH) + 1;
				int kDate = kCalendar.get(Calendar.DATE);
				kWeeks = Integer.toString(kMonth) + "/\n" + Integer.toString(kDate);
			}
			else if (i == -18)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kDate = kCalendar.get(Calendar.DATE);
				String kFinalWeeksString = kWeeks + "-" + kDate;
				kWeeksArr.add(kFinalWeeksString);
				kWeeks = "";
			}
			
			if (i == -17)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kMonth = kCalendar.get(Calendar.MONTH) + 1;
				int kDate = kCalendar.get(Calendar.DATE);
				kWeeks = Integer.toString(kMonth) + "/\n" + Integer.toString(kDate);
			}
			else if (i == -11)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kDate = kCalendar.get(Calendar.DATE);
				String kFinalWeeksString = kWeeks + "-" + kDate;
				kWeeksArr.add(kFinalWeeksString);
				kWeeks = "";
			}
			
			if (i == -10)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kMonth = kCalendar.get(Calendar.MONTH) + 1;
				int kDate = kCalendar.get(Calendar.DATE);
				kWeeks = Integer.toString(kMonth) + "/\n" + Integer.toString(kDate);
			}
			else if (i == -4)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kDate = kCalendar.get(Calendar.DATE);
				String kFinalWeeksString = kWeeks + "-" + kDate;
				kWeeksArr.add(kFinalWeeksString);
				kWeeks = "";
			}
			
			if (i == -3)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kMonth = kCalendar.get(Calendar.MONTH) + 1;
				int kDate = kCalendar.get(Calendar.DATE);
				kWeeks = Integer.toString(kMonth) + "/\n" + Integer.toString(kDate);
			}
			else if (i == 3)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kDate = kCalendar.get(Calendar.DATE);
				String kFinalWeeksString = kWeeks + "-" + kDate;
				kWeeksArr.add(kFinalWeeksString);
				kWeeks = "";
			}
			
			if (i == 4)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kMonth = kCalendar.get(Calendar.MONTH) + 1;
				int kDate = kCalendar.get(Calendar.DATE);
				kWeeks = Integer.toString(kMonth) + "/\n" + Integer.toString(kDate);
			}
			else if (i == 10)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kDate = kCalendar.get(Calendar.DATE);
				String kFinalWeeksString = kWeeks + "-" + kDate;
				kWeeksArr.add(kFinalWeeksString);
				kWeeks = "";
			}
			
			if (i == 11)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kMonth = kCalendar.get(Calendar.MONTH) + 1;
				int kDate = kCalendar.get(Calendar.DATE);
				kWeeks = Integer.toString(kMonth) + "/\n" + Integer.toString(kDate);
			}
			else if (i == 17)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kDate = kCalendar.get(Calendar.DATE);
				String kFinalWeeksString = kWeeks + "-" + kDate;
				kWeeksArr.add(kFinalWeeksString);
				kWeeks = "";
			}
			
			if (i == 18)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kMonth = kCalendar.get(Calendar.MONTH) + 1;
				int kDate = kCalendar.get(Calendar.DATE);
				kWeeks = Integer.toString(kMonth) + "/\n" + Integer.toString(kDate);
			}
			else if (i == 24)
			{
				Calendar kCalendar = Calendar.getInstance();
				kCalendar.add(Calendar.DATE, i);
				int kDate = kCalendar.get(Calendar.DATE);
				String kFinalWeeksString = kWeeks + "-" + kDate;
				kWeeksArr.add(kFinalWeeksString);
				kWeeks = "";
			}
		}
		return kWeeksArr;
	}
	
	
	// Get current time
	public static String getCurrentTime()
	{
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("HH:mm").format(date);
	}
}
