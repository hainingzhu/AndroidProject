package Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BasePreferenceUtil
{
	private static SharedPreferences _preference;
	
	
	public static SharedPreferences getInst()
	{
		if (_preference == null)
			_preference = PreferenceManager.getDefaultSharedPreferences(ContextUtil.CONTEXT);
		return _preference;
	}
	
	
	public static SharedPreferences getInst(Context $context)
	{
		if (ContextUtil.CONTEXT == null)
		{
			ContextUtil.CONTEXT = $context;
		}
		
		if (_preference == null)
			_preference = PreferenceManager.getDefaultSharedPreferences($context);
		return _preference;
	}
	
	
	/**
	 * Set key
	 * @param key
	 * @param value 
	 */
	public static void setKey(String $key, String $value)
	{
		SharedPreferences p = getInst();
		SharedPreferences.Editor editor = p.edit();
		editor.putString($key, $value);
		editor.commit();
	}
	
	
	/**
	 * Get String 
	 * @param key 
	 * @return String (default = null)
	 */
	public static String getString(String $key)
	{
		SharedPreferences p = getInst();
		return p.getString($key, null);
	}
	
	
	/**
	 * Get String
	 * @param key
	 * @return String (default = "")
	 */
	public static String getStringWithNullToBlank(String $key)
	{
		SharedPreferences p = getInst();
		return p.getString($key, "");
	}
	
	
	/**
	 * Set key
	 * @param key 
	 * @param value 
	 */
	public static void setKey(String $key, boolean $value)
	{
		SharedPreferences p = getInst();
		SharedPreferences.Editor editor = p.edit();
		editor.putBoolean($key, $value);
		editor.commit();
	}
	
	
	/**
	 * Get Boolean
	 * @param key 
	 * @param default 
	 * @return Boolean
	 */
	public static boolean getBoolean(String $key, boolean $default)
	{
		SharedPreferences p = getInst();
		return p.getBoolean($key, $default);
	}
	
	
	/**
	 * Set key
	 * @param key 
	 * @param value 
	 */
	public static void setKey(String $key, int $value)
	{
		SharedPreferences p = getInst();
		SharedPreferences.Editor editor = p.edit();
		editor.putInt($key, $value);
		editor.commit();
	}
	
	
	/**
	 * Get int 
	 * @param key 
	 * @param default 
	 * @return int
	 */
	public static int getInt(String $key, int $default)
	{
		SharedPreferences p = getInst();
		return p.getInt($key, $default);
	}
}
