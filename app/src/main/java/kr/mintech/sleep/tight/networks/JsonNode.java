package kr.mintech.sleep.tight.networks;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONValue;

import Util.ConverterUtil;
import Util.Logg;
import android.text.TextUtils;

public class JsonNode
{
	private HashMap<String, Object> _jsonMap;
	private JSONArray _jsonArray;
	public String error = "";
	
	
	public JsonNode(String $json)
	{
		if (!TextUtils.isEmpty($json))
		{
			if (!$json.startsWith("["))
			{
				_jsonMap = parse($json);
			}
			else
			{
				try
				{
					_jsonArray = new JSONArray($json);
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * parsing json
	 * @param $json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> parse(String $json)
	{
		return (HashMap<String, Object>) JSONValue.parse($json);
	}
	
	
	/**
	 * Has the key?
	 * @param $key
	 * @return
	 */
	public boolean hasKey(String $key)
	{
		return _jsonMap.get($key) != null;
	}
	
	
	/**
	 * Success?
	 * @return
	 */
	public boolean isSuccess()
	{
		boolean kResult = true;
		
		try
		{
			if (hasKey("success"))
			{
				kResult = getBoolean("success");
				error = getString("error");
				if (!kResult)
					Logg.w("JsonNode | isSuccess()", "error : " + error);
			}
		} catch (Exception e)
		{
		}
		
		return kResult;
	}
	
	
	/**
	 * Return $key value
	 * @param $key
	 * @return String
	 */
	public String getString(String $key)
	{
		try
		{
			String kResult = _jsonMap.get($key).toString();
			if (TextUtils.isEmpty(kResult) || kResult.equals("null"))
				kResult = null;
			return kResult;
		} catch (Exception e)
		{
			return null;
		}
	}
	
	
	/**
	 * Return $key value
	 * @param $key
	 * @return Integer
	 */
	public int getInt(String $key)
	{
		try
		{
			return Integer.parseInt(getString($key));
		} catch (Exception e)
		{
			return -1;
		}
	}
	
	
	/**
	 * Return $key value
	 * @param $key
	 * @return Double
	 */
	public Float getDouble(String $key)
	{
		try
		{
			return Float.valueOf(getString($key)).floatValue();
		} catch (Exception e)
		{
			return -1.0f;
		}
	}
	
	
	/**
	 * Return $key value
	 * @param $key
	 * @return boolean
	 */
	public boolean getBoolean(String $key)
	{
		return ConverterUtil.stringToBoolean(getString($key));
	}
	
	
	/**
	 * Return $key array
	 * @param $key
	 * @return
	 */
	public JSONArray getJsonArray(String $key)
	{
		try
		{
			return new JSONArray(_jsonMap.get($key).toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public JSONArray getTopJsonArray()
	{
		return _jsonArray;
	}
}
