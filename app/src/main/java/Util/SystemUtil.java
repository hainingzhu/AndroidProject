package Util;

import java.util.Calendar;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

public class SystemUtil
{
	/**
	 * 버전 이름을 가져온다
	 * @return 0.0.1
	 */
	public static String versionName()
	{
		PackageInfo info;
		try
		{
			info = ContextUtil.CONTEXT.getPackageManager().getPackageInfo(ContextUtil.CONTEXT.getPackageName(), PackageManager.GET_META_DATA);
			return info.versionName;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
			return "0.0.1";
		}
	}
	
	
	/**
	 * verseion code 를 가져온다
	 * @return
	 */
	public static int versionCode()
	{
		PackageInfo info;
		try
		{
			info = ContextUtil.CONTEXT.getPackageManager().getPackageInfo(ContextUtil.CONTEXT.getPackageName(), PackageManager.GET_META_DATA);
			return info.versionCode;
		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
			return 1;
		}
	}
	
	
	/**
	 * 폰 번호 가져오기
	 * @return
	 */
	public static String phoneNumber()
	{
		TelephonyManager tm = (TelephonyManager) ContextUtil.CONTEXT.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}
	
	
	/**
	 * 프로세스가 최상위로 실행중인지 검사한다.
	 * @return true=최상위
	 */
	public static boolean isForeground(Context $context)
	{
		ActivityManager am = (ActivityManager) $context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		ComponentName cn = list.get(0).topActivity;
		String name = cn.getPackageName();
		
		return name.indexOf($context.getPackageName()) > -1;
	}
	
	
	/**
	 * 앱이 살아있는지 검사
	 * @param $content
	 * @return
	 */
	public static boolean isAlive()
	{
		ActivityManager am = (ActivityManager) ContextUtil.CONTEXT.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		
		for (RunningTaskInfo task : list)
		{
			ComponentName cn = task.topActivity;
			String name = cn.getPackageName();
			if (name.indexOf(ContextUtil.CONTEXT.getPackageName()) > -1)
				return true;
		}
		return false;
	}
	
	
	/**
	 * 해당 액티비티가 최상위로 떠 있는지 검사
	 * @param name 검사 할 액티비티 이름 <br>
	 *            Chat.class.getName() = com.mintech.mintTalk.chat.Chat
	 * @return
	 */
	public static boolean isTopActivity(String name)
	{
		ActivityManager am = (ActivityManager) ContextUtil.CONTEXT.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		ComponentName cn = list.get(0).topActivity;
		
		return cn.getClassName().equals(name);
	}
	
	
	/**
	 * 스택의 가장 아래에 있는 베이스 액티비티 이름이 맞는지 확인
	 * @param $name
	 * @return
	 */
	public static boolean isBaseActivity(String $name)
	{
		ActivityManager am = (ActivityManager) ContextUtil.CONTEXT.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		ComponentName cn = list.get(0).baseActivity;
		
		return cn.getClassName().equals($name);
	}
	
	
	/**
	 * Wi-Fi가 연결되어 있는가
	 * @return true=연결됨
	 */
	public static boolean isConnectedWiFi()
	{
		ConnectivityManager manager = (ConnectivityManager) ContextUtil.CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
	}
	
	
	/**
	 * 3G가 연결되어 있는가
	 * @return true=연결됨
	 */
	public static boolean isConnected3G()
	{
		boolean kResult = false;
		
		try
		{
			ConnectivityManager manager = (ConnectivityManager) ContextUtil.CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
			kResult = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
		} catch (Exception e)
		{
//			e.printStackTrace();
		}
		
		return kResult;
	}
	
	
	/**
	 * 네트웍에 연결되어 있는가
	 * @return
	 */
	public static boolean isConnectNetwork()
	{
		return isConnectedWiFi() || isConnected3G();
	}
	
	
	/**
	 * 화면 너비 구하기
	 * @return 너비
	 */
	public static int getScreenWidth()
	{
		return ((WindowManager) ContextUtil.CONTEXT.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}
	
	
	/**
	 * 화면 높이 구하기
	 * @return 높이
	 */
	public static int getScreenHeight()
	{
		return ((WindowManager) ContextUtil.CONTEXT.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
	}
	
	
	/**
	 * 가로모드인가
	 * @return true=가로
	 */
	public static boolean isLandscape()
	{
		return getScreenWidth() > getScreenHeight();
	}
	
	
	/**
	 * 폰의 조명켜짐 시간을 가져온다
	 * @return xxx 초
	 */
	public static int getScreenOffDuration()
	{
		return Settings.System.getInt(ContextUtil.CONTEXT.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 15000);
	}
	
	
	/**
	 * 화면이 켜져 있는가
	 * @return
	 */
	public static boolean isScreenOn()
	{
		PowerManager pm = (PowerManager) ContextUtil.CONTEXT.getSystemService(Context.POWER_SERVICE);
		return pm.isScreenOn();
	}
	
	
	/**
	 * 폰의 모델명을 가져온다
	 * @return HTC Desire, GT-P7510
	 */
	public static String modelName()
	{
		return Build.MODEL.toString();
	}
	
	
	/**
	 * 맥어드레스를 가져온다
	 * @param $context
	 * @return
	 */
	public static String getMacAddress(Context $context)
	{
		WifiManager wifiManager = (WifiManager) $context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}
	
	
	/**
	 * android_id 가져오기
	 * @param $context
	 * @return
	 */
	public static String getAndroidId(Context $context)
	{
		return Secure.getString($context.getContentResolver(), Secure.ANDROID_ID);
	}
	
	
	/**
	 * 현재 시간을 가져온다
	 * @return Calendar Type
	 */
	public static Calendar getTimeWithCalendarNow()
	{
		Calendar kNowCalendar = Calendar.getInstance();
		return kNowCalendar;
	}
	
	
	/**
	 * menifest에 debuggable 설정값 리턴
	 * @return
	 */
	public static boolean isDebuggable()
	{
		boolean kResult = false;
		
		if (ContextUtil.CONTEXT == null)
			kResult = false;
		else
		{
			kResult = ((ContextUtil.CONTEXT.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
		}
		
		return kResult;
	}
	
	
	/**
	 * 파일을 저장할 공간이 있는가
	 * @return
	 */
	public static boolean isHasSDCard()
	{
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
}
