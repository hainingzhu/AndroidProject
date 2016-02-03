package kr.mintech.sleep.tight.controllers.settings;

import java.util.ArrayList;

import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.networks.NetHelper;
import kr.mintech.sleep.tight.units.ActivityTrackUnit;
import kr.mintech.sleep.tight.units.BeforeBedActUnit;

import org.json.JSONArray;

public class BeforeBedActController extends BaseController
{
	public ArrayList<BeforeBedActUnit> _units;
	public ActivityTrackUnit unit;
	private NetHelper _netHelper;
	
	
	public BeforeBedActController(OnRequestEndListener $requestEndListener)
	{
		super();
		_units = new ArrayList<BeforeBedActUnit>();
		_requestEndListener = $requestEndListener;
	}
	
	
	public BeforeBedActController()
	{
		super();
		_units = new ArrayList<BeforeBedActUnit>();
	}
	
	
	public void clear()
	{
		_units.clear();
	}
	
	
	//Activity BeforeBedAct Request
	public void requestBeforeBedAct()
	{
		_netHelper = new NetHelper(this, "didEndRequestActions");
		_netHelper.requestBeforeBedAct();
	}
	
	
	//Activity BeforeBedAct Add
	public void requestAddBeforeBedAct(String $name)
	{
		_netHelper = new NetHelper(this, "didEndRequestAddActions");
		_netHelper.requestAddBeforeBedAct($name);
	}
	
	
	//Activity BeforeBedAct Remove
	public void requestRemoveBeforeBedAct(int $id)
	{
		_netHelper = new NetHelper(this, "didEndRequestAddActions");
		_netHelper.requestDeleteBeforeBedAct($id);
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
				BeforeBedActUnit kUnit = new BeforeBedActUnit(kOneNode);
				
				_requestEndListener.onRequest(kUnit);
				_units.add(kUnit);
			}
			_requestEndListener.onRequestEnded(NumberConst.requestEndBeforeBedAct, null);
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
}
