package kr.mintech.sleep.tight.controllers.sleepsummary;

import java.util.ArrayList;

import org.json.JSONArray;

import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.networks.NetHelper;
import kr.mintech.sleep.tight.units.BinInfoUnit;
import kr.mintech.sleep.tight.units.SleepSummaryUnit;
import kr.mintech.sleep.tight.units.SleepTrackUnit;
import kr.mintech.sleep.tight.utils.Pie;

public class SleepSummaryController extends BaseController
{
	public SleepSummaryUnit unit;
	public BinInfoUnit dayBinUnit;
	private NetHelper _netHelper;

	public ArrayList<SleepTrackUnit> _sleepUnits;
	
	public SleepSummaryController(OnRequestEndListener $requestEndListener)
	{
		super();
		_sleepUnits = new ArrayList<SleepTrackUnit>();
		_requestEndListener = $requestEndListener;
	}
	
	
	public SleepSummaryController()
	{
		super();
	}
	
	
	public void clear()
	{
	}
	
	
	public void requestDailySleepSummary(String $endDate, int $numBin)
	{
		_netHelper = new NetHelper(this, "didEndRequestSleepSummary");
		_netHelper.requestDailySleepSummary($endDate, $numBin);
	}
	
	
	public void requestWeeklySleepSummary(String $endDate)
	{
		_netHelper = new NetHelper(this, "didEndRequestSleepSummary");
		_netHelper.requestWeeklySleepSummary($endDate);
	}
	
	
	public void requestDailyBinInfo(String $id)
	{
		_netHelper = new NetHelper(this, "didEndRequestBinInfo");
		_netHelper.requestDailyBinInfo($id);
	}
	
	
	public void requestWeeklyBinInfo(String $id)
	{
		_netHelper = new NetHelper(this, "didEndRequestBinInfo");
		_netHelper.requestWeeklyBinInfo($id);
	}
	
	//Sleep Tracks Request
	public void requestSleepTracks(String $baseDate, int $agoHour)
	{
		_netHelper = new NetHelper(this, "didEndRequestSleepRequest");
		_netHelper.requestSleepTracks($baseDate, $agoHour);
	}
	
	/**
	 * After requesting the SleepSummary info
	 *
	 */
	public void didEndRequestSleepSummary(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		
		unit = new SleepSummaryUnit(kNode);
		_requestEndListener.onRequestEnded(NumberConst.requestEndSleepSummary, null);
	}
	
	
	public void didEndRequestBinInfo(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		
		dayBinUnit = new BinInfoUnit(kNode);
		_requestEndListener.onRequestEnded(NumberConst.requestEndSleepSummaryDayBinInfo, null);
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
			Pie.getInst().sleepTrackUnitArray = _sleepUnits;
			_requestEndListener.onRequestEnded(NumberConst.requestEndSleepTracks, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			e.printStackTrace();
		}
	}
}
