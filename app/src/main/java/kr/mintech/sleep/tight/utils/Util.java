package kr.mintech.sleep.tight.utils;

import Util.ContextUtil;
import android.app.KeyguardManager;
import android.content.Context;

public class Util
{
	public static DateTime extractTime(String strTime)
	{
		DateTime time = new DateTime();
		
		String delimiter = " ";
		String[] temp = strTime.split(delimiter);
		
		if (temp.length == 2)
		{
			time.setTime(0, Integer.parseInt(temp[0]));
		}
		else if (temp.length == 4)
		{
			time.setTime(Integer.parseInt(temp[0]), Integer.parseInt(temp[2]));
		}
		
		return time;
	}
	
	
	public static String writeTime(int hour, int minute)
	{
		String time = "";
		
		if (hour < 1)
		{
			if (minute < 2)
			{
				time = Integer.toString(minute) + " min";
			}
			else
			{
				time = Integer.toString(minute) + " mins";
			}
		}
		else if (hour == 1)
		{
			if (minute < 2)
			{
				time = "1 hr " + Integer.toString(minute) + " min";
			}
			else
			{
				time = "1 hr " + Integer.toString(minute) + " mins";
			}
		}
		else
		{
			if (minute < 2)
			{
				time = Integer.toString(hour) + " hrs " + Integer.toString(minute) + " min";
			}
			else
			{
				time = Integer.toString(hour) + " hrs " + Integer.toString(minute) + " mins";
			}
		}
		
		return time;
	}
	
	
	public static int parseInt(String str)
	{
		int num = 0;
		
		try
		{
			num = Integer.parseInt(str);
		} catch (Exception e)
		{
			
		}
		
		return num;
	}
	
	public static String getWigetLocation() {
		KeyguardManager kgMgr = (KeyguardManager) ContextUtil.CONTEXT.getSystemService(Context.KEYGUARD_SERVICE);
		boolean showing = kgMgr.inKeyguardRestrictedInputMode();
		String where = "homeScreenWidget";
		if (showing) {
			where = "lockScreenWidget";
		}
		
		return where;
	}
	
	public static void logPageSwitch(int to) {
		EventLogger.log("pause", 
				"what", "main_activity",
				"page", Pie.getInst().currentPage);

		String newPage = "addActivity";
		switch (to) {
			case 1:
				newPage = "sleepSummary";
				EventLogger.log("view_summary",
						"range", Pie.getInst().currentRange);
				break;

			case 2:
				newPage = "comparison";
				break;		
		}
		
		EventLogger.log("resume", 
				"what", "main_activity",
				"page", newPage);
		
		Pie.getInst().currentPage = newPage;
	}
}
