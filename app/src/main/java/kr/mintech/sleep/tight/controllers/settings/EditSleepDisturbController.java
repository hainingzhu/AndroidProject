package kr.mintech.sleep.tight.controllers.settings;

import java.util.ArrayList;

import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.networks.NetHelper;
import kr.mintech.sleep.tight.units.ActivityTrackUnit;
import kr.mintech.sleep.tight.units.EditSleepDisturbUnit;

import org.json.JSONArray;

public class EditSleepDisturbController extends BaseController
{
	public ArrayList<EditSleepDisturbUnit> _units;
	public ActivityTrackUnit unit;
	private NetHelper _netHelper;
	
	public EditSleepDisturbController(OnRequestEndListener $requestEndListener)
	{
		super();
		_units = new ArrayList<EditSleepDisturbUnit>();
		_requestEndListener = $requestEndListener;
	}
	
	public EditSleepDisturbController()
	{
		super();
		_units = new ArrayList<EditSleepDisturbUnit>();
	}
	
	public void clear()
	{
		_units.clear();
	}
	
	//Requesting the Sleep Disturbance List
	public void requestSleepDisturb()
	{
		_netHelper = new NetHelper(this, "didEndRequestActions");
		_netHelper.requestSleepDisturb();
	}
	
	//Adding a Sleep Distrubance
	public void requestAddSleepDisturb(String $name)
	{
		_netHelper = new NetHelper(this, "didEndRequestAddActions");
		_netHelper.requestAddSleepDisturb($name);
	}
	
	//Removing a Sleep Distrubance
	public void requestRemoveSleepDisturb(int $id)
	{
		_netHelper = new NetHelper(this, "didEndRequestAddActions");
		_netHelper.requestDeleteSleepDisturb($id);
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
		
		if (_units.size() > 0)
		{
			_units.clear();
		}
		
		try
		{
			for (int i = 0; i < kJsonArray.length(); i++)
			{
				JsonNode kOneNode = new JsonNode(kJsonArray.get(i).toString());
				EditSleepDisturbUnit kUnit = new EditSleepDisturbUnit(kOneNode);
				
				_requestEndListener.onRequest(kUnit);
				_units.add(kUnit);
			}
			_requestEndListener.onRequestEnded(NumberConst.requestEndSleepDisturb, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * After requesting to add ActivityTracks info
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
}
