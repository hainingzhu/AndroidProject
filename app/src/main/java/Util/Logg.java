package Util;

import android.util.Log;

/**
 * release ëª¨ë“œì—�ì„œ log í‘œì‹œ ì•ˆí•˜ê²Œ
 * @author susemi99
 */
public class Logg
{
	private Logg()
	{
	}
	
	
	/**
	 * verbose
	 * @param $tag
	 * @param $msg
	 */
	public static void v(String $tag, String $msg)
	{
		if (SystemUtil.isDebuggable())
			Log.v($tag, $msg);
	}
	
	
	/**
	 * debug
	 * @param $tag
	 * @param $msg
	 */
	public static void d(String $tag, String $msg)
	{
		if (SystemUtil.isDebuggable())
			Log.d($tag, $msg);
	}
	
	
	/**
	 * info
	 * @param $tag
	 * @param $msg
	 */
	public static void i(String $tag, String $msg)
	{
		if (SystemUtil.isDebuggable())
			Log.i($tag, $msg);
	}
	
	
	/**
	 * warning
	 * @param $tag
	 * @param $msg
	 */
	public static void w(String $tag, String $msg)
	{
		if (SystemUtil.isDebuggable())
			Log.w($tag, $msg);
	}
	
	
	/**
	 * error
	 * @param $tag
	 * @param $msg
	 */
	public static void e(String $tag, String $msg)
	{
		if (SystemUtil.isDebuggable())
			Log.e($tag, $msg);
	}	
}
