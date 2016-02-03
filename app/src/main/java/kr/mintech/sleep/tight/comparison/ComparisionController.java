package kr.mintech.sleep.tight.comparison;

import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.networks.NetHelper;
import kr.mintech.sleep.tight.units.BinInfoUnit;
import kr.mintech.sleep.tight.units.ComparisionUnit;

public class ComparisionController extends BaseController
{
	public ComparisionUnit unit;
	private NetHelper _netHelper;
	
	
	public ComparisionController(OnRequestEndListener $requestEndListener)
	{
		super();
		_requestEndListener = $requestEndListener;
	}
	
	
	public ComparisionController()
	{
		super();
	}
	
	
	public void clear()
	{
	}
	
	
	public void requestComparision()
	{
		_netHelper = new NetHelper(this, "didEndRequestComparision");
		_netHelper.requestComparision();
	}
	
	
	/**
	 * After receiving the Sleep Summary 
	 *
	 */
	public void didEndRequestComparision(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		
		unit = new ComparisionUnit(kNode);
		_requestEndListener.onRequestEnded(NumberConst.requestEndComparision, null);
	}
	
	
//	public void didEndRequestBinInfo(Object $resultUnit)
//	{
//		if (!isSuccess($resultUnit))
//		{
//			_requestEndListener.onRequestError(NumberConst.requestFail, "");
//			return;
//		}
//		
//		JsonNode kNode = new JsonNode(getResultString($resultUnit));
//		
//		dayBinUnit = new BinInfoUnit(kNode);
//		_requestEndListener.onRequestEnded(NumberConst.requestEndSleepSummaryDayBinInfo, null);
//	}
//	
}
