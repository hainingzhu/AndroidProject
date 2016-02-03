package kr.mintech.sleep.tight.controllers;

import java.util.ArrayList;

import kr.mintech.sleep.tight.bases.BaseController;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.JsonNode;
import kr.mintech.sleep.tight.networks.NetHelper;
import kr.mintech.sleep.tight.units.UserUnit;

public class RegisterController extends BaseController
{
	public ArrayList<UserUnit> _userUnits;
	public UserUnit unit;
	private NetHelper _netHelper;
	
	public boolean _isNewUser;
	
	
	public RegisterController(OnRequestEndListener $requestEndListener)
	{
		super();
		_userUnits = new ArrayList<UserUnit>();
		_requestEndListener = $requestEndListener;
	}
	
	
	public RegisterController()
	{
		super();
		_userUnits = new ArrayList<UserUnit>();
	}
	
	
	public void clear()
	{
		_userUnits.clear();
	}
	
	
	//Request is login? 
	public void requestIsRegister()
	{
		_netHelper = new NetHelper(this, "didEndRequestIsRegister");
		_netHelper.requestIsRegister();
	}
	
	
	//Request Add User 
	public void requestAddRegisterInfo(int $targetUserId, String $userName, int $birthYear, String $gender, String $sleepCondition)
	{
		_netHelper = new NetHelper(this, "didEndRequestAddRegister");
		_netHelper.requestEditRegisterInfo($targetUserId, $userName, $birthYear, $gender, $sleepCondition);
	}
	
	
	/**
	 * After requesting the register info
	 *
	 */
	public void didEndRequestIsRegister(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
			return;
		}
		
		JsonNode kNode = new JsonNode(getResultString($resultUnit));
		_isNewUser = kNode.getBoolean("new_user");
		
		String kUserString = kNode.getString("user");
		JsonNode kUserNode = new JsonNode(kUserString);
		try
		{
			unit = new UserUnit(kUserNode);
			_requestEndListener.onRequestEnded(NumberConst.requestEndIsRegister, null);
		} catch (Exception e)
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
	}
	
	
	/**
	 * Requesting to add the register info
	 *
	 */
	public void didEndRequestAddRegister(Object $resultUnit)
	{
		if (!isSuccess($resultUnit))
		{
			_requestEndListener.onRequestError(NumberConst.requestFail, "");
		}
		else
		{
			_requestEndListener.onRequestEnded(NumberConst.requestEndAddRegister, null);
		}
	}
}
