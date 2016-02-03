package kr.mintech.sleep.tight.controllers.actions;

import java.util.ArrayList;

import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.networks.NetHelper;
import kr.mintech.sleep.tight.units.ActionUnit;
import kr.mintech.sleep.tight.units.ActivityTrackUnit;
import kr.mintech.sleep.tight.utils.Pie;

import org.json.JSONArray;

public class ActionController extends BaseController
{
	public ArrayList<ActionUnit> _actionUnits;
	public ActivityTrackUnit unit;
	private NetHelper _netHelper;
	
	
	public ActionController(OnRequestEndListener $requestEndListener)
	{
		super();
		_actionUnits = new ArrayList<ActionUnit>();
		_requestEndListener = $requestEndListener;
	}
	
	
	public ActionController()
	{
		super();
		_actionUnits = new ArrayList<ActionUnit>();
	}
	
	
	public void clear()
	{
		_actionUnits.clear();
	}
	
	
	//Action Request
	public void requestActions()
	{
		_netHelper = new NetHelper(this, "didEndRequestActions");
		_netHelper.requestActions();
	}
	
	
	public void requestActionsShowAll()
	{
		_netHelper = new NetHelper(this, "didEndRequestActions");
		_netHelper.requestActionsShowAll();
	}
	

// Don't understand why we need this:
//	public void requestActionsWithLimit(int $limitCount)
//	{
//		_netHelper = new NetHelper(this, "didEndRequestActionsWithLimit");
//		_netHelper.requestActionsWithLimit($limitCount);
//	}
	
	
	//Action Request
	public void requestAddActions(String $actionName)
	{
		_netHelper = new NetHelper(this, "didEndRequestAddActions");
		_netHelper.requestAddAction($actionName);
	}
	
	
	//Action Delete 
	public void requestRemoveActions(int $id)
	{
		_netHelper = new NetHelper(this, "didEndRequestRemoveActions");
		_netHelper.requestRemoveAction($id);
	}
	
	
	public void requestManageActions(ArrayList<String> $hideArr, ArrayList<String> $showArr)
	{
		_netHelper = new NetHelper(this, "didEndRequestManageActions");
		_netHelper.requestManageAction($hideArr, $showArr);
	}
	
	
	public void requestManageSortActions(ArrayList<String> $sortArr)
	{
		_netHelper = new NetHelper(this, "didEndRequestManageActions");
		_netHelper.requestManageSortAction($sortArr);
	}
	
	
	/**
	 * After requesting the ActivityTracks info
	 *
	 */
	public void didEndRequestActions(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		JSONArray kJsonArray = kNode.getTopJsonArray();
		
		if (_actionUnits.size() > 0)
		{
			_actionUnits.clear();
		}
		
		try
		{
			for (int i = 0; i < kJsonArray.length(); i++)
			{
				JsonNode kOneNode = new JsonNode(kJsonArray.get(i).toString());
				ActionUnit kUnit = new ActionUnit(kOneNode);
				
				_requestEndListener.onRequest(kUnit);
				_actionUnits.add(kUnit);
			}
			Pie.getInst().actionkUnit = _actionUnits;
			_requestEndListener.onRequestEnded(NumberConst.requestEndActions, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * After requesting the ActivityTracks info (Limit)
	 *
	 */
	public void didEndRequestActionsWithLimit(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		JSONArray kJsonArray = kNode.getTopJsonArray();
		
		if (_actionUnits.size() > 0)
		{
			_actionUnits.clear();
		}
		
		try
		{
			for (int i = 0; i < kJsonArray.length(); i++)
			{
				JsonNode kOneNode = new JsonNode(kJsonArray.get(i).toString());
				ActionUnit kUnit = new ActionUnit(kOneNode);
				
				_requestEndListener.onRequest(kUnit);
				_actionUnits.add(kUnit);
			}
			Pie.getInst().actionkUnitForWidget = _actionUnits;
			_requestEndListener.onRequestEnded(NumberConst.requestEndActionsWithLimit, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			e.printStackTrace();
		}
	}
	
	/**
	 * After requesting to add the ActivityTracks info
	 *
	 */
	public void didEndRequestAddActions(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
		else
		{
			_requestEndListener.onRequestEnded(NumberConst.requestEndAddAction, null);
		}
	}
	
	
	/**
	 * After requesting to change the order of ActivityTracks and activity visibility setting
	 *
	 */
	public void didEndRequestManageActions(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
		else
		{
			_requestEndListener.onRequestEnded(NumberConst.requestSuccess, null);
		}
	}
	
	
	public void didEndRequestRemoveActions(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
		else
		{
			_requestEndListener.onRequestEnded(NumberConst.requestEndRemoveAction, null);
		}
	}
}
