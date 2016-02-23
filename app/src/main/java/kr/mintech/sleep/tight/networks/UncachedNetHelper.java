package kr.mintech.sleep.tight.networks;

import java.util.ArrayList;

import kr.mintech.sleep.tight.consts.StringConst;
import kr.mintech.sleep.tight.utils.PreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import bases.MTObject;

/**
 * The original version of NetHelper, without caching.
 * @author mjskay
 *
 */
public class UncachedNetHelper extends MTObject
{
	private static String TAG = "nethelper";

	private DataRequestTask _dataRequestTask;
	private DataRequestPostTask _dataRequestPostTask;
	private DataRequestDeleteTask _dataRequestDeleteTask;
	
	
	//Network communication
	// Get -> Request Info   
	// Post -> Send request to the server +Body(String, ...)  
	// Put -> Edit data that's saved on the server  
	// Delete 
	
	public UncachedNetHelper(Object $target, String $method)
	{
		registerCallback($target, $method);
	}
	
	
	public UncachedNetHelper()
	{
		super();
	}
	
	
	// Cancel server request
	public void onRequestCancel()
	{
		if (_dataRequestTask != null)
		{
			_dataRequestTask.cancel(true);
		}
		
		if (_dataRequestPostTask != null)
		{
			_dataRequestPostTask.cancel(true);
		}
	}
	
	
	// This is where you enter your server hosting address 
	private StringBuilder getServerUrl()
	{
		StringBuilder kData = new StringBuilder();
//		kData.append("http://rocky-taiga-5665.herokuapp.com");  // Testing server
//		kData.append("http://192.168.1.146:3000"); // home
//		kData.append("http://173.250.149.196:3000"); // rcb
//		kData.append("http://depts.washington.edu/sleeptgt/sleeptight-rails-project/"); // server
//		kData.append("http://10.0.2.2:3000"); // host system of the emulator
		kData.append("http://dsquare.ist.psu.edu:8080");
		return kData;
	}
	
	
	/*
	 * Register Info
	 */
	public void requestIsRegister()
	{
		String kJson = makeRegisterInfo(PreferenceUtil.getAndroidId());
		StringBuilder kData = getServerUrl();
		Log.w("WHJ", "connecting server " + kJson);
		kData.append(StringConst.API_REGISTER_USER);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/*
	 * Put User Register Info
	 */
	public void requestEditRegisterInfo(int $targetUserId, String $userName, int $birthYear, String $gender, String $sleepCondition)
	{
		String kJson = makeAddRegisterInfo($userName, $birthYear, $gender, $sleepCondition);
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_REGISTER_UPDATE_USER);
		kData.append("/");
		kData.append($targetUserId);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/**
	 * Requesting ActionTracks
	 */
	public void requestActivityTracks(String $baseTime, int $agoHours)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_TRACKS);
		kData.append("?base_time=");
		kData.append($baseTime);
		kData.append("&hours_ago=");
		kData.append($agoHours);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/**
	 * Requesting to add ActionTracks
	 */
	public void requestAddActivityTrack(int $activityId, String $actionStartedAt, String $actionEndedAt)
	{
		String kJson = makeAddActivityTrackJson($activityId, $actionStartedAt, $actionEndedAt);
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_TRACKS);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/**
	 * Requesting to edit ActionTracks
	 */
	public void requestEditActivityTrack(int $trackId, int $activityId, String $actionStartedAt, String $actionEndedAt)
	{
		String kJson = makeAddActivityTrackJson($activityId, $actionStartedAt, $actionEndedAt);
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_TRACKS);
		kData.append("/");
		kData.append($trackId);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/*
	 * Requesting to remove ActionTracks
	 */
	public void requestRemoveActivityTrack(int $id)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_TRACKS);
		kData.append("/");
		kData.append($id);
		_dataRequestDeleteTask = new DataRequestDeleteTask();
		_dataRequestDeleteTask.execute(kData.toString());
	}
	
	
	/**
	 * Requesting Sleep Track info
	 */
	public void requestSleepTracks(String $baseTime, int $agoHours)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_SLEEP_TRACK);
		kData.append("?base_time=");
		kData.append($baseTime);
		kData.append("&hours_ago=");
		kData.append($agoHours);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/**
	 * Requesting to check whether a Sleep Diary exists
	 */
	public void requestSleepDiaryExist(String $baseTime, int $agoHours)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_SLEEP_TRACK);
		kData.append("/in_diary_date?base_time=");
		kData.append($baseTime);
		if ($agoHours > 0)
		{
			kData.append("&hours_ago=");
			kData.append($agoHours);
		}
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/**
	 * Requesting to add Sleep Info
	 */
	public void requestAddSleepInfo(String $inBedTime, int $sleepLetancy, String $wakeUpTime, String $outOfBedTime, String $diaryDate, int $sleepQ, int $awakeCount, int $totalWakeTime, 
			ArrayList<String> $sleepRitual, ArrayList<String> $sleepDisturbance)
	{
		String kJson = makeSleepInfoJson($inBedTime, $sleepLetancy, $wakeUpTime, $outOfBedTime, $diaryDate, $sleepQ, $awakeCount, $totalWakeTime, $sleepRitual, $sleepDisturbance);
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_SLEEP_TRACK);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/**
	 * Requesting Activities (Caffeine, Meal ...)
	 */
	public void requestActions()
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIONS);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	public void requestActionsShowAll()
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIONS);
		kData.append("?hide_option=show_all");
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/*
	 * Requesting to remove ActionTracks
	 */
	public void requestRemoveAction(int $id)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIONS);
		kData.append("/");
		kData.append($id);
		_dataRequestDeleteTask = new DataRequestDeleteTask();
		_dataRequestDeleteTask.execute(kData.toString());
	}
	
	
	/*
	 * Requesting a certain number of actions [for widget]
	 */
	public void requestActionsWithLimit(int $limitCount)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIONS);
		kData.append("?limit=");
		kData.append($limitCount);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/**
	 * Requesting to add Actions
	 */
	public void requestAddAction(String $actionName)
	{
		String kJson = makeActionJson($actionName);
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIONS);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/**
	 * Requesting to hide Actions
	 * @type ACTIVITY_HIDE, ACTIVITY_SHOW, ACTIVITY_SORT
	 */
	public void requestManageAction(ArrayList<String> $hideArr, ArrayList<String> $showArr)
	{
		JSONObject kJson = new JSONObject();
		try
		{
			JSONArray kJSONArray = new JSONArray($hideArr);
			JSONArray kJSONShowArray = new JSONArray($showArr);
			kJson.put("hide_ids", kJSONArray);
			kJson.put("show_ids", kJSONShowArray);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_HIDE_SHOW_ACTIONS);
		
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson.toString());
	}
	
	
	/**
	 * Actions Sort
	 */
	public void requestManageSortAction(ArrayList<String> $sortArr)
	{
		JSONObject kJson = new JSONObject();
		try
		{
			JSONArray kJSONArray = new JSONArray($sortArr);
			kJson.put("ids", kJSONArray);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_SORT_ACTIONS);
		
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson.toString());
	}
	
	
	/*
	 * Requesting the sleep disturbance list
	 */
	public void requestSleepDisturb()
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_SLEEP_DISTURB);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/*
	 * Requesting to add a sleep disturbance
	 */
	public void requestAddSleepDisturb(String $name)
	{
		String kJson = makeSleepDisturb($name);
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_SLEEP_DISTURB);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/*
	 * Requesting to delete a sleep disturbance
	 */
	public void requestDeleteSleepDisturb(int $id)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_SLEEP_DISTURB);
		kData.append("/");
		kData.append($id);
		_dataRequestDeleteTask = new DataRequestDeleteTask();
		_dataRequestDeleteTask.execute(kData.toString());
	}
	
	
	/*
	 * Requesting the "Activities Before Bed" list
	 */
	public void requestBeforeBedAct()
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_BEFORE_BED_ACT);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/*
	 * Requesting to add an item to the "Activities Before Bed" list
	 */
	public void requestAddBeforeBedAct(String $name)
	{
		String kJson = makeBeforeBedAct($name);
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_BEFORE_BED_ACT);
		_dataRequestPostTask = new DataRequestPostTask();
		_dataRequestPostTask.execute(kData.toString(), kJson);
	}
	
	
	/*
	 * Requesting to delete an item from the "Activities Before Bed" list
	 */
	public void requestDeleteBeforeBedAct(int $id)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_BEFORE_BED_ACT);
		kData.append("/");
		kData.append($id);
		_dataRequestDeleteTask = new DataRequestDeleteTask();
		_dataRequestDeleteTask.execute(kData.toString());
	}
	
	
	/*
	 * ****************************** Sleep Summary
	 */
	public void requestDailySleepSummary(String $endDate, int $numBin) {
		StringBuilder kData = getServerUrl(); // http://10.0.1.131:3000
		kData.append(StringConst.API_SLEEP_SUMMARY_DAILY); // /api/daily_sleep_summary
		kData.append("?end_date="); // ?end_date=
		kData.append($endDate); // 2013-04-03
		kData.append("&num_bin=");
		kData.append($numBin);
		
		//http://10.0.1.131:3000/api/daily_sleep_summary?end_date=2013-04-03
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	public void requestWeeklySleepSummary(String $endDate)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_SLEEP_SUMMARY_WEEKLY);
		kData.append("?end_date=");
		kData.append($endDate);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	public void requestDailyBinInfo(String $id)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_ACTIVITY_SLEEP_TRACK);
		kData.append("/");
		kData.append($id);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	public void requestWeeklyBinInfo(String $id)
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_BIN_INFO_WEEKLY);
		kData.append("/");
		kData.append($id);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	/*
	 * ***************Comparison
	 */
	public void requestComparision()
	{
		StringBuilder kData = getServerUrl();
		kData.append(StringConst.API_COMPARISION);
		_dataRequestTask = new DataRequestTask();
		_dataRequestTask.execute(kData.toString());
	}
	
	
	// ==============================
	// Create json
	// ==============================
	
	public static String makeRegisterInfo(String $uuid)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("uuid", $uuid);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
	public static String makeAddRegisterInfo(String $userName, int $birthYear, String $gender, String $sleepCondition)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("name", $userName);
			json.put("birth_year", $birthYear);
			json.put("gender", $gender);
			json.put("sleep_condition", $sleepCondition);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
	public static String makeAddActivityTrackJson(int $activityId, String $actionStartedAt, String $actionEndedAt)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("activity_id", $activityId);
			json.put("action_started_at", $actionStartedAt);
			json.put("action_ended_at", $actionEndedAt);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
	public static String makeSleepInfoJson(String $inBedTime, int $sleepLetancy, String $wakeUpTime, String $outOfBedTime, String $diaryDate, int $sleepQ, int $awakeCount, int $totalWakeTime, 
			ArrayList<String> $sleepRitual, ArrayList<String> $sleepDisturbance)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("in_bed_time", $inBedTime);
			json.put("sleep_latency", Integer.toString($sleepLetancy));
			json.put("wake_up_time", $wakeUpTime);
			json.put("out_of_bed_time", $outOfBedTime);
			json.put("diary_date", $diaryDate);
			json.put("sleep_quality", $sleepQ);
			json.put("awake_count", $awakeCount);
			json.put("total_time_awake", $totalWakeTime);
	
			if (!$sleepRitual.isEmpty()) {
				JSONArray jsonArray = new JSONArray($sleepRitual);
				json.put("sleep_ritual_ids", jsonArray);
			}
			
			if (!$sleepDisturbance.isEmpty()) {
				JSONArray jsonArray = new JSONArray($sleepDisturbance);
				json.put("sleep_disturbance_ids", jsonArray);
			}
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
	public static String makeActionJson(String $actionName)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("name", $actionName);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
	public static String makeActionIdsJson(ArrayList<String> $ids)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("ids", $ids);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
	public static String makeSleepDisturb(String $name)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("name", $name);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		JSONObject kRemakejson = new JSONObject();
		try
		{
			kRemakejson.put("sleep_disturbance", json);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return kRemakejson.toString();
	}
	
	
	public static String makeBeforeBedAct(String $name)
	{
		JSONObject json = new JSONObject();
		try
		{
			json.put("name", $name);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		JSONObject kRemakejson = new JSONObject();
		try
		{
			kRemakejson.put("sleep_ritual", json);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		return kRemakejson.toString();
	}
	
	/**********************************
	 * This class receives requested json from the server
	 **********************************/
	private class DataRequestTask extends AsyncTask<String, Integer, NetworkResultUnit>
	{
		@Override
		protected NetworkResultUnit doInBackground(String... $params)
		{
			// CLEANUP // 
//			Log.d(TAG, "NetHelper.DataRequestTask | doInBackground() to server :      ###################################### " + "\n" +
//			           "NetHelper.DataRequestTask | doInBackground() to server : " + $params[0] + "\n" +
//			           "NetHelper.DataRequestTask | doInBackground() to server :      ###################################### ");
			
			return new HttpRequestHandler(15 * 1000).requestGet($params[0]);
		}
		
		
		@Override
		protected void onCancelled()
		{
			// CLEANUP // Log.d(TAG, "NetHelper.DataRequestTask | onCancelled()");
			super.onCancelled();
			_dataRequestTask = null;
		}
		
		
		@Override
		protected void onPostExecute(NetworkResultUnit $result)
		{
			super.onPostExecute($result);
		
			// CLEANUP // 
//			Log.d(TAG, "NetHelper.DataRequestTask | onPostExecute() from server : @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + "\n" +
//			           "NetHelper.DataRequestTask | onPostExecute() from server : " + $result.isSuccess + " | " + $result.resultCode + " | " + $result.resultString + "\n" +
//			           "NetHelper.DataRequestTask | onPostExecute() from server : @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
			
			_dataRequestTask = null;
			invokeCallback($result);
		}
	}
	
	/**********************************
	 * This class receives requested json from the server [POST]
	 **********************************/
	private class DataRequestPostTask extends AsyncTask<String, Integer, NetworkResultUnit>
	{
		@Override
		protected NetworkResultUnit doInBackground(String... $params)
		{
			// CLEANUP // 
//			Log.d(TAG, "NetHelper.DataRequestPostTask | doInBackground() to server :      ###################################### \n" + 
//			           "NetHelper.DataRequestPostTask | doInBackground() to server : " + $params[0] + "\n" + 
//			           "NetHelper.DataRequestPostTask | doInBackground() to server : " + $params[1] + "\n" +
//			           "NetHelper.DataRequestPostTask | doInBackground() to server :      ###################################### ");
			
			return new HttpRequestHandler(15 * 1000).requestPost($params[0], $params[1]);
		}
		
		
		@Override
		protected void onCancelled()
		{
			super.onCancelled();
			
			_dataRequestPostTask = null;
		}
		
		
		@Override
		protected void onPostExecute(NetworkResultUnit $result)
		{
			super.onPostExecute($result);

			// CLEANUP // 
//			Log.d(TAG, "NetHelper.DataRequestPostTask | onPostExecute() from server : @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + "\n" +
//			           "NetHelper.DataRequestPostTask | onPostExecute() from server : " + $result.isSuccess + " | " + $result.resultCode + " | " + $result.resultString + "\n" +
//			           "NetHelper.DataRequestPostTask | onPostExecute() from server : @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
			
			_dataRequestPostTask = null;
			invokeCallback($result);
		}
	}
	
	/**********************************
	 * This class receives requested json from the server [DELETE]
	 **********************************/
	private class DataRequestDeleteTask extends AsyncTask<String, Integer, NetworkResultUnit>
	{
		@Override
		protected NetworkResultUnit doInBackground(String... $params)
		{
			// CLEANUP // 
//			Log.d(TAG, "NetHelper.DataRequestDeleteTask | doInBackground() to server :      ###################################### " + "\n" +
//			           "NetHelper.DataRequestDeleteTask | doInBackground() to server : " + $params[0] + "\n" +
//			           "NetHelper.DataRequestDeleteTask | doInBackground() to server :      ###################################### ");
			return new HttpRequestHandler(15 * 1000).requestDelete($params[0]);
		}
		
		
		@Override
		protected void onCancelled()
		{
			super.onCancelled();
		}
		
		
		@Override
		protected void onPostExecute(NetworkResultUnit $result)
		{
			super.onPostExecute($result);

			// CLEANUP // 
//			Log.d(TAG, "NetHelper.DataRequestDeleteTask | onPostExecute() from server : @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + "\n" +
//			           "NetHelper.DataRequestDeleteTask | onPostExecute() from server : " + $result.isSuccess + " | " + $result.resultCode + " | " + $result.resultString + "\n" +
//			           "NetHelper.DataRequestDeleteTask | onPostExecute() from server : @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ");
			
			invokeCallback($result);
		}
	}
}