package Util;

import android.content.Context;
import android.os.PowerManager;

public class ScreenWakeUpUtil
{
	private static PowerManager.WakeLock _screenWakeLock;
	
	
	public static void screenOn(Context $context)
	{
		if (_screenWakeLock != null)
			screenOff();
		
		PowerManager pm = (PowerManager) $context.getSystemService(Context.POWER_SERVICE);
		
		_screenWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, $context.getPackageName());
		_screenWakeLock.acquire();
	}
	
	
	public static void screenFullOn(Context $context)
	{
		if (_screenWakeLock != null)
			screenOff();
		
		PowerManager pm = (PowerManager) $context.getSystemService(Context.POWER_SERVICE);
		
		_screenWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, $context.getPackageName());
		_screenWakeLock.acquire();
	}
	
	
	public static void screenOff()
	{
		if (_screenWakeLock != null)
		{
			_screenWakeLock.release();
			_screenWakeLock = null;
		}
	}
}
