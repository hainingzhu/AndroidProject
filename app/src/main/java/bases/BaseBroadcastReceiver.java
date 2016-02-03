package bases;

import Util.ContextUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BaseBroadcastReceiver extends BroadcastReceiver
{
	protected Context _context;
	
	@Override
	public void onReceive(Context $context, Intent $intent)
	{
		_context = $context;
		
		if (ContextUtil.CONTEXT == null)
		{
			ContextUtil.CONTEXT = $context;
		}
	}
}
