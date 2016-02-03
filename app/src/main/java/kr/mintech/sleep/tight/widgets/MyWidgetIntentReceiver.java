package kr.mintech.sleep.tight.widgets;

import kr.mintech.sleep.tight.activities.sleepdiarys.AddSleepDiaryActivity;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.services.SleepTightService;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.utils.Util;
import Util.ContextUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyWidgetIntentReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{	
		String action = intent.getAction();
		if (action == "kr.mintech.add.action") {
			int id = intent.getIntExtra("activityId", -1);
			DateTime now = new DateTime();
			String startTime = now.getStringTime(SleepTightConstants.NetworkFormat);
	
			Log.w("MyWidgetIntentReceiver | onReceive()", "id: " + id + "  startTime:" + startTime);
			
			SleepTightService _service = new SleepTightService();
			_service.requestAddTrack(id, startTime, null);
		} else if (action == "kr.mintech.add.sleep") {
			Intent intent_AddSleep = new Intent(context, AddSleepDiaryActivity.class);
			intent_AddSleep.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Pie.getInst().widgetLocation = Util.getWigetLocation();
			context.startActivity(intent_AddSleep);
		}
	}	
}
