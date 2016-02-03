package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.format.Time;

public final class DateUtil
{
	private static final String fDate = "yyyy-MM-dd";
	private static final String fYm = "yyyy-MM";
	private static final String fDateTime = "yyyy-MM-dd HH:mm:ss";
	private static final String fYear = "yyyy";
	private static final String fMonth = "MM";
	private static final String fDay = "dd";
	private static final String fTime = "HH:mm:ss";
	private static final String fTimeZone = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	
	
	/**
	 * Date format change
	 * @param format 
	 * @param date 
	 * @return
	 */
	private static String format(String $format, Date $date)
	{
		SimpleDateFormat f = new SimpleDateFormat($format);
		return f.format($date);
	}
	
	
	private static String format(String $format)
	{
		return format($format, new Date());
	}
	
	public static String getDate(String $format)
	{
		return format($format);
	}
	
	/**
	 * Get currenet time
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow()
	{
		return format(fDateTime);
	}
	
	
	/**
	 * Get today's date
	 * @return yyyy-MM-dd
	 */
	public static String getToday()
	{
		return format(fDate);
	}
	
	
	/**
	 * Get current time
	 * @return HH:mm:ss
	 */
	public static String getTime()
	{
		return format(fTime);
	}
	
	
	/**
	 * Get Year
	 * @return
	 */
	public static String getYear()
	{
		return format(fYear);
	}
	
	
	/**
	 * Get Month
	 * @return
	 */
	public static String getMonth()
	{
		return format(fMonth);
	}
	
	
	/**
	 * Get today's date
	 * @return
	 */
	public static String getDay()
	{
		return format(fDay);
	}
	
	
	/**
	 * Get year & month
	 * @return yyyy-MM 2011-04
	 */
	public static String getNowYM()
	{
		return format(fYm);
	}
	
	
	/**
	 * Formatting the date
	 * @param $date Date
	 * @return $format
	 */
	public static String getFromDate(String $format, Date $date)
	{
		return format($format, $date);
	}
	
	
	/**
	 * Formatting the date
	 * @param $fromFormat (yyyy-MM-dd HH:mm:ss)
	 * @param $toFormat (yyyy-MM-dd)
	 * @param $dateStr (yyyy-MM-dd HH:mm:ss)
	 * @return
	 */
	public static String getFromString(String $fromFormat, String $toFormat, String $dateStr)
	{
		try
		{
			Date date = new SimpleDateFormat($fromFormat).parse($dateStr);
			return format($toFormat, date);
		} catch (ParseException e)
		{
			// e.printStackTrace();
			return $dateStr;
		}
	}
	
	
	/**
	 * From Long to Date
	 * @param l 
	 * @return 2011-06-20 11:22:33
	 */
	public static String getFromLong(String $format, Long $dateLong)
	{
		Date date = new Date($dateLong);
		return format($format, date);
	}
	
	
	/**
	 * Adding / subtracting a number of months
	 * @param $add
	 * @return
	 */
	public static String addMonth(int $add)
	{
		Date date = new Date();
		date.setMonth(date.getMonth() + $add);
		return format(fDate, date);
	}
	
	
	/**
	 * Adding / subtracting a number of months
	 * @param $date : yyyy-MM
	 * @param $add 
	 * @return yyyy-MM
	 */
	public static String addMonth(String $date, int $add)
	{
		try
		{
			Date date = new SimpleDateFormat(fYm).parse($date);
			date.setMonth(date.getMonth() + $add);
			return format(fYm, date);
		} catch (ParseException e)
		{
			// e.printStackTrace();
			return $date;
		}
	}
	
	
	/**
	 * Adding / subtracting a number of days
	 * @param $add 
	 * @return
	 */
	public static String addDay(int $add)
	{
		Date date = new Date();
		date.setHours(date.getDay() + $add * 24);
		return format(fDate, date);
	}
	
	
	/**
	 * Formatting
	 * @param $add 
	 * @return
	 */
	public static String addDayWithFormat(int $add, String $format)
	{
		Date date = new Date();
		date.setHours(date.getDay() + $add * 24);
		return format($format, date);
	}
	
	
	/**
	 * Jump to a specific date
	 * @param $year 2012
	 * @param $month 5
	 * @param $day 21
	 * @param $add 
	 * @return getMonth() +1 
	 */
	public static Date addDay(int $year, int $month, int $day, int $add)
	{
		Date kDate = new Date($year, $month - 1, $day);
		kDate.setHours(kDate.getDay() + $add * 24);
		return kDate;
	}
	
	
	/**
	 * Returns the last date of the month
	 * @param $year 
	 * @param $month 
	 * @return 
	 */
	public static int getLastDay(int $year, int $month)
	{
		Date date = new Date($year, $month, 1);
		date.setHours(date.getDay() - 1 * 24);
		return Integer.parseInt(format("dd", date));
	}
	
	
	/**
	 * Returns the later date of the two dates
	 * @param d1: date 1
	 * @param d2: date 2
	 * @return 
	 */
	public static String getLastDateTime(String $format, String $date1, String $date2)
	{
		Date dt1, dt2;
		String result = null;
		try
		{
			dt1 = new SimpleDateFormat($format).parse($date1);
			dt2 = new SimpleDateFormat($format).parse($date2);
			
			if (dt1.compareTo(dt2) > 0)
				result = format($format, dt1);
			else
				result = format($format, dt2);
		} catch (Exception e)
		{
			// e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * @param $format 
	 * @param $date 2011-02-17T07:43:30Z
	 * @return
	 */
	public static String getDateTimeAddLocalTimezone(String $format, String $date)
	{
		try
		{
			Date date = new SimpleDateFormat(fTimeZone).parse($date);
			Time t = new Time();
			Long l = t.normalize(t.isDst == 0); // -32400000 milliesec
			Long between = l / 1000 / 60 / 60; // -9 hours
			date.setHours(date.getHours() - Long.valueOf(between).intValue());
			return format($format, date);
		} catch (ParseException e)
		{
			// e.printStackTrace();
			return $date;
		}
	}
	
	
	/**
	 * @param $format
	 * @return
	 */
	public static String nowUTC(String $format)
	{
		Date date;
		try
		{
			date = new SimpleDateFormat(fDateTime).parse(getNow());
			Time t = new Time();
			Long l = t.normalize(t.isDst == 0); // -32400000 milliesec
			Long between = l / 1000 / 60 / 60; // -9 hours
			date.setHours(date.getHours() + Long.valueOf(between).intValue());
			return format($format, date);
		} catch (ParseException e)
		{
			// e.printStackTrace();
			return getNow();
		}
	}
	
	
	/**
	 * 
	 * @param $year 2012
	 * @param $month 5
	 * @param $day 21
	 * @return 1:Sunday, ~, 7:Saturday
	 */
	public static int getDayOfWeek(int $year, int $month, int $day)
	{
		Calendar kCal = Calendar.getInstance();
		kCal.set($year, $month - 1, $day);
		return kCal.get(Calendar.DAY_OF_WEEK);
	}
	
	
	/**
	 * 해당 날짜가 있는 주의 일요일을 가져옴
	 * @param $year
	 * @param $month
	 * @param $day
	 * @return getMonth()+1 필수
	 */
	public static Date getFirstDateOfWeek(int $year, int $month, int $day)
	{
		return addDay($year, $month, $day, -getDayOfWeek($year, $month, $day) + 1);
	}
	
	
	/**
	 * 해당 날짜가 있는 주의 토요일을 가져옴
	 * @param $year
	 * @param $month
	 * @param $day
	 * @return getMonth()+1 필수
	 */
	public static Date getLastDateOfWeek(int $year, int $month, int $day)
	{
		return addDay($year, $month, $day, 7 - getDayOfWeek($year, $month, $day));
	}
	
	
	/**
	 * 해당 날짜와 오늘 날짜가 같은가
	 * @param $year
	 * @param $month
	 * @param $day
	 * @return
	 */
	public static boolean isToday(int $year, int $month, int $day)
	{
		boolean kResult = false;
		
		if (isThisMonth($year, $month) && Integer.parseInt(getDay()) == $day)
		{
			kResult = true;
		}
		
		return kResult;
	}
	
	
	/**
	 * 해당 월이 이번 달인가
	 * @param $year
	 * @param $month
	 * @return
	 */
	public static boolean isThisMonth(int $year, int $month)
	{
		boolean kResult = false;
		
		if (Integer.parseInt(getYear()) == $year && Integer.parseInt(getMonth()) == $month)
		{
			kResult = true;
		}
		
		return kResult;
	}
	
	
	/**
	 * 해당 날짜가 토요일인가
	 * @param $year
	 * @param $month
	 * @param $day
	 * @return
	 */
	public static boolean isSaturday(int $year, int $month, int $day)
	{
		return getDayOfWeek($year, $month, $day) == Calendar.SATURDAY;
	}
	
	
	/**
	 * 해당 날짜가 일요일인가
	 * @param $year
	 * @param $month
	 * @param $day
	 * @return
	 */
	public static boolean isSunday(int $year, int $month, int $day)
	{
		return getDayOfWeek($year, $month, $day) == Calendar.SUNDAY;
	}
}
