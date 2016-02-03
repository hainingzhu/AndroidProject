package kr.mintech.sleep.tight.bases;

import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.networks.NetworkResultUnit;
import Util.ContextUtil;
import android.util.Log;
import android.widget.Toast;

public class BaseController
{
	protected OnRequestEndListener _requestEndListener;
	
	
	/**
	 * Check whether the saved values are valid
	 * @param $resultUnit
	 * @return
	 */
	protected boolean isSuccess(Object $resultUnit)
	{
		NetworkResultUnit kUnit = (NetworkResultUnit) $resultUnit;
		Log.w("shakej", "ResponseCode: " + kUnit.resultCode);
		
		if (kUnit.resultCode == 422)
		{
			Toast.makeText(ContextUtil.CONTEXT, "The same data already exists in the server.", Toast.LENGTH_SHORT).show();
		}
		
		if (kUnit.resultCode > 199 && kUnit.resultCode < 301)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * @param $networkResultUnit
	 * @return
	 */
	protected String getResultString(Object $networkResultUnit)
	{
		NetworkResultUnit kUnit = (NetworkResultUnit) $networkResultUnit;
		
		return kUnit.resultString;
	}
}