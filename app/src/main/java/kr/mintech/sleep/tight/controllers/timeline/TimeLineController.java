package kr.mintech.sleep.tight.controllers.timeline;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.Local_db.dbHelper_local;
import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.networks.NetHelper;
import kr.mintech.sleep.tight.units.ActivityTrackUnit;
import kr.mintech.sleep.tight.units.SleepTrackUnit;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.widgets.LockScreenProvider;

import org.json.JSONArray;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import Util.Logg;

public class TimeLineController extends BaseController
{
	//Network Unit -> A model composed of values received from the server  
	public ArrayList<ActivityTrackUnit> _activityTrackUnits;
	public ArrayList<ActivityTrackUnit> _activityTrackUnitsForWidget;
	public ArrayList<SleepTrackUnit> _sleepUnits;
	public ActivityTrackUnit unit;
	private NetHelper _netHelper;
	
	
	public TimeLineController(OnRequestEndListener $requestEndListener)
	{
		super();
		_activityTrackUnits = new ArrayList<ActivityTrackUnit>();
		_activityTrackUnitsForWidget = new ArrayList<ActivityTrackUnit>();
		_sleepUnits = new ArrayList<SleepTrackUnit>();
		_requestEndListener = $requestEndListener;
	}
	
	
	public TimeLineController()
	{
		super();
		_activityTrackUnits = new ArrayList<ActivityTrackUnit>();
		_activityTrackUnitsForWidget = new ArrayList<ActivityTrackUnit>();
		_sleepUnits = new ArrayList<SleepTrackUnit>();
	}
	
	
	public void clear()
	{
		_activityTrackUnits.clear();
		_sleepUnits.clear();
	}
	
	
	//Activity Tracks Request
	public void requestActivityTracks(String $baseDate, int $agoHour)
	{
		_netHelper = new NetHelper(this, "didEndRequest");
		_netHelper.requestActivityTracks($baseDate, $agoHour);
	}

    /**
     * Use local DB to fill in contents
     * @param baseDate
     * @param startDate
     * @param cont
     */
    public void requestActivityTracks_fromLocalDB(String baseDate, String startDate, Context cont)
    {
        Log.w("WHJ", startDate + "\t" + baseDate);
        _activityTrackUnits = dbHelper_local.searchActivityTracks(startDate, baseDate, cont);
        Pie.getInst().activityTrackUnit = new ArrayList<ActivityTrackUnit>(_activityTrackUnits);
    }

	
	//Activity Tracks Request
	public void requestActivityTracksForWidget(String $baseDate, int $agoHour)
	{
		_netHelper = new NetHelper(this, "didEndRequestForWidget");
		_netHelper.requestActivityTracks($baseDate, $agoHour);
	}
	
	
	//Activity Track Add Request
	public void reqeustAddActivityTrack(int $activityId, String $startedAt, String $endedAt)
	{
		_netHelper = new NetHelper(this, "didEndRequestAddActivity");
		_netHelper.requestAddActivityTrack($activityId, $startedAt, $endedAt);
	}
	
	
	//Activity Track Edit Request
	public void requestEditActivityTrack(int $trackId, int $activityId, String $startedAt, String $endedAt)
	{
		_netHelper = new NetHelper(this, "didEndRequestEditActivity");
		_netHelper.requestEditActivityTrack($trackId, $activityId, $startedAt, $endedAt);
		
	}
	
	
	//Activity Track Remove Request
	public void reqeustRemoveActivityTrack(int $id)
	{
		_netHelper = new NetHelper(this, "didEndRequestRemoveActivity");
		_netHelper.requestRemoveActivityTrack($id);
	}
	
	
	//Sleep Tracks Request
	public void requestSleepTracks(String $baseDate, int $agoHour)
	{
		_netHelper = new NetHelper(this, "didEndRequestSleepRequest");
		_netHelper.requestSleepTracks($baseDate, $agoHour);
	}
	
	
	//Sleep Track exist Request
	public void requestSleepDiaryExist(String $baseDate, int $agoHour)
	{
		_netHelper = new NetHelper(this, "didEndRequestSleepRequestExist");
		_netHelper.requestSleepDiaryExist($baseDate, $agoHour);
	}
	
	/**
	 * HACK: Forcibly update the widget. Should be called when activity track data is changed.
	 * TODO: This should really be changed into an event-based model at some point.
	 * 
	 * @param context	{@link Context} to broadcast {@link Intent} from in order to 
	 * 					tell the widget to update. 
	 */
	public void updateWidget(Context context) {		// Tell the widget to update
		// CLEANUP // Log.d("nethelper", "--------------------------------------BROADCAST INTENT TO UPDATE WIDGET");
		Intent intent = new Intent(context, LockScreenProvider.class);
		intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
		int[] ids = {R.xml.lock_appwidget_provider};
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
		context.sendBroadcast(intent);
	}
	
	
	
	/**
	 * After requesting the ActivityTracks info
	 *
	 */
	public void didEndRequest(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		JSONArray kJsonArray = kNode.getTopJsonArray();
		
		if (_activityTrackUnits.size() > 0)
		{
			_activityTrackUnits.clear();
		}
		
		try
		{
			for (int i = 0; i < kJsonArray.length(); i++)
			{
				JsonNode kOneNode = new JsonNode(kJsonArray.get(i).toString());
				ActivityTrackUnit kUnit = new ActivityTrackUnit(kOneNode);
				
				_requestEndListener.onRequest(kUnit);
				_activityTrackUnits.add(kUnit);
			}
			
			Pie.getInst().activityTrackUnit = new ArrayList<ActivityTrackUnit>(_activityTrackUnits);
			_requestEndListener.onRequestEnded(NumberConst.requestEndActivityTracks, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			e.printStackTrace();
		}
	}
	
	
	/**
	 *  After requesting the ActivityTracks info
	 *
	 */
	public void didEndRequestForWidget(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		JSONArray kJsonArray = kNode.getTopJsonArray();
		
		if (_activityTrackUnitsForWidget.size() > 0)
		{
			_activityTrackUnitsForWidget.clear();
		}
		
		try
		{
			for (int i = 0; i < kJsonArray.length(); i++)
			{
				JsonNode kOneNode = new JsonNode(kJsonArray.get(i).toString());
				ActivityTrackUnit kUnit = new ActivityTrackUnit(kOneNode);
				
				_requestEndListener.onRequest(kUnit);
				_activityTrackUnitsForWidget.add(kUnit);
			}
			Pie.getInst().activityTrackUnitForWidget = _activityTrackUnitsForWidget;
			_requestEndListener.onRequestEnded(NumberConst.requestEndActivityTracksForWidget, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * After requesting to add ActivityTrack info
	 *
	 */
	public void didEndRequestAddActivity(Object $resultUnit)
	{
		if (isSuccess($resultUnit))
		{
			_requestEndListener.onRequestEnded(NumberConst.requestSuccess, "");
		}
		else
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
	}
	
	
	/**
	 * After requesting to edit ActivityTrack info
	 *
	 */
	public void didEndRequestEditActivity(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
		else
		{
			_requestEndListener.onRequestEnded(NumberConst.requestEndEditTracksSuccess, "");
		}
	}
	
	
	/**
	 * Requesting to delete ActivityTrack info
	 *
	 */
	public void didEndRequestRemoveActivity(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
		else
		{
			_requestEndListener.onRequestEnded(NumberConst.requestEndRemoveAction, "");
		}
	}
	
	
	/**
	 * Requesting Sleep Info
	 *
	 */
	public void didEndRequestSleepRequest(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		JSONArray kJsonArray = kNode.getTopJsonArray();
		
		if (_sleepUnits.size() > 0)
		{
			_sleepUnits.clear();
		}
		
		try
		{
			for (int i = 0; i < kJsonArray.length(); i++)
			{
				JsonNode kOneNode = new JsonNode(kJsonArray.get(i).toString());
				SleepTrackUnit kUnit = new SleepTrackUnit(kOneNode);
				
				_requestEndListener.onRequest(kUnit);
				_sleepUnits.add(kUnit);
			}
			Pie.getInst().sleepTrackUnit = _sleepUnits;
			_requestEndListener.onRequestEnded(NumberConst.requestEndSleepTracks, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			e.printStackTrace();
		}
	}
	
	
	public void didEndRequestSleepRequestExist(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		JSONArray kJsonArray = kNode.getTopJsonArray();
		
		// CLEANUP // Logg.w("TimeLineController | didEndRequestSleepRequestExist()", "Sleep Track Exist : " + kJsonArray.length());
		if (kJsonArray.length() == 0)
		{
			Pie.getInst().isEmptySleepDiary = true;
			_requestEndListener.onRequestEnded(NumberConst.requestEndSleepTrackExist, null);
		}
		else
		{
			Pie.getInst().isEmptySleepDiary = false;
			_requestEndListener.onRequestEnded(NumberConst.requestEndSleepTrackExist, null);
		}
	}
}
