package kr.mintech.sleep.tight.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTime implements Comparable<DateTime>
{
	private Calendar calTime;
	
	
	public static int getCurrentTime(int field)
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(field);
	}
	
	
	public static int getTargetTime(DateTime $target, int field)
	{
		Calendar cal = $target.getCalTime();
		return cal.get(field);
	}
	
	
	public DateTime()
	{
		calTime = Calendar.getInstance();
		calTime.set(Calendar.SECOND, 0);
		calTime.set(Calendar.MILLISECOND, 0);
	}
	
	
	public DateTime(String $timeString)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Date kDate = null;
		try
		{
			kDate = dateFormat.parse($timeString);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		calTime = Calendar.getInstance();
		calTime.setTime(kDate);
		calTime.setTimeZone(TimeZone.getDefault());
	}
	
	
	public DateTime(int hr, int min)
	{
		calTime = Calendar.getInstance();
		calTime.set(Calendar.HOUR_OF_DAY, hr);
		calTime.set(Calendar.MINUTE, min);
		calTime.set(Calendar.SECOND, 0);
		calTime.set(Calendar.MILLISECOND, 0);
	}
	
	
	public DateTime(DateTime $targetTime, int hr, int min)
	{
		calTime = (Calendar) $targetTime.getCalTime().clone();
		calTime.set(Calendar.HOUR_OF_DAY, hr);
		calTime.set(Calendar.MINUTE, min);
		calTime.set(Calendar.SECOND, 0);
		calTime.set(Calendar.MILLISECOND, 0);
	}
	
	
	public DateTime(int millsec)
	{
		calTime = Calendar.getInstance();
		calTime.set(Calendar.HOUR_OF_DAY, 0);
		calTime.set(Calendar.MINUTE, 0);
		calTime.set(Calendar.SECOND, 0);
		calTime.set(Calendar.MILLISECOND, millsec);
	}
	
	
	public DateTime(DateTime other)
	{
		calTime = (Calendar) other.getCalTime().clone();
		calTime.set(Calendar.SECOND, 0);
		calTime.set(Calendar.MILLISECOND, 0);
	}
	
	
	public Calendar getCalTime()
	{
		return calTime;
	}
	
	
	public void setTime(int hr, int min)
	{
		calTime.set(Calendar.HOUR_OF_DAY, hr);
		calTime.set(Calendar.MINUTE, min);
		calTime.set(Calendar.SECOND, 0);
		calTime.set(Calendar.MILLISECOND, 0);
	}
	
	
	public void setTime(SimpleDateFormat mSDF, String str)
	{
		try
		{
			TimeZone tz = TimeZone.getDefault();
			mSDF.setTimeZone(tz);
			
			Date d = mSDF.parse(str);
			calTime.setTime(d);
		} catch (Exception e)
		{
			System.out.println(e.getStackTrace());
		}
	}
	
	
	public void setDate(int year, int month, int day)
	{
		calTime.set(Calendar.YEAR, year);
		calTime.set(Calendar.MONTH, month);
		calTime.set(Calendar.DAY_OF_MONTH, day);
	}
	
	
	public int get(int field)
	{
		return calTime.get(field);
	}
	
	
	public long getTimeInMillis()
	{
		return calTime.getTimeInMillis();
	}
	
	public int diffInDays(DateTime other) {
		long diffInMills = calTime.getTimeInMillis() - other.getTimeInMillis();
		return (int) (diffInMills / (24 * 60 * 60 * 1000));
	}
	
	public DateTime setTimeInMillis(DateTime $targetTime, long $millis)
	{
		calTime = (Calendar) $targetTime.getCalTime().clone();
		calTime.set(Calendar.SECOND, 0);
		calTime.set(Calendar.MILLISECOND, 0);
		calTime.setTimeInMillis($millis);
		return this;
	}
	
	
	public void add(int field, int value)
	{
		calTime.add(field, value);
	}
	
	
	public int compareTo(DateTime other)
	{
		return calTime.compareTo(other.getCalTime());
	}
	
	
	public String getStringTime(SimpleDateFormat mSDF)
	{
		TimeZone tz = TimeZone.getDefault();
		mSDF.setTimeZone(tz);
		
		return mSDF.format(calTime.getTime());
	}
	
	
	public DateTime toToBedTime()
	{
		if (calTime.get(Calendar.AM_PM) == Calendar.PM)
		{
			calTime.add(Calendar.DATE, -1);
		}
//		calTime.setTimeZone(TimeZone.getTimeZone("GMT"));
		return this;
	}
	
	
	public DateTime toUTCTime(DateTime $date)
	{
		calTime = $date.getCalTime();
		calTime.setTimeZone(TimeZone.getTimeZone("GMT"));
		return this;
	}
	
	
	public DateTime toPrevDay(DateTime $date)
	{
		calTime = $date.getCalTime();
		
		calTime.add(Calendar.DATE, -1);
//		calTime.setTimeZone(TimeZone.getTimeZone("GMT"));
		return this;
	}
	
	
	public DateTime toNextDay(DateTime $date)
	{
		calTime = $date.getCalTime();
		
		calTime.add(Calendar.DATE, +1);
		calTime.setTimeZone(TimeZone.getTimeZone("GMT"));
		return this;
	}
	
	/**
	 * Output this date and time as an ISO-formatted date string.
	 */
	public String toString() {
		SimpleDateFormat isoFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		return isoFormatter.format(calTime.getTime());
	}
	
	public boolean equals(DateTime other) {
		return other != null && this.compareTo(other) == 0; 
	}
	
	/**
	 * Returns true if the date ranges "a" and "b" represented by [a1, a2]
	 * and [b1, b2] intersect.
	 * 
	 * pre: a1 <= a2
	 *      b1 <= b2
	 *      a1, a2, b1, b2 all not null
	 * 
	 * @param a1
	 * @param a2
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static boolean rangesIntersect(DateTime a1, DateTime a2, DateTime b1, DateTime b2) {
		return (a1.compareTo(b2) <= 0 && b2.compareTo(a2) <= 0) || 
				(a1.compareTo(b1) <= 0 && b1.compareTo(a2) <= 0) ||
				(b1.compareTo(a2) <= 0 && a2.compareTo(b2) <= 0);
	}	
}
