package kr.mintech.sleep.tight.controllers.sleepdiarys;

import java.util.ArrayList;

import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.NetHelper;

public class SleepController extends BaseController
{
	private NetHelper _netHelper;
	
	
	public SleepController(OnRequestEndListener $requestEndListener)
	{
		super();
		_requestEndListener = $requestEndListener;
	}
	
	
	public SleepController()
	{
		super();
	}
	
	
	public void requestAddSleepInfo(String $inBedTime, int $sleepLetancy, String $wakeUpTime, String $outOfBedTime, String $diaryDate, int $sleepQ, int $awakeCount, int $totalWakeTime, 
			ArrayList<String> $sleepRitual, ArrayList<String> $sleepDisturbance)
	{
		_netHelper = new NetHelper(this, "didEndRequest");
		_netHelper.requestAddSleepInfo($inBedTime, $sleepLetancy, $wakeUpTime, $outOfBedTime, $diaryDate, $sleepQ, $awakeCount, $totalWakeTime, $sleepRitual, $sleepDisturbance);
	}
	
	
	/**
	 * Requesting the SleepTrack info
	 *
	 */
	public void didEndRequest(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
		else
		{
			_requestEndListener.onRequestEnded(NumberConst.requestSuccess, "");
		}
		
	}
}
