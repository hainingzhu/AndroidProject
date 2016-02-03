package kr.mintech.sleep.tight.services;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.MainActivity;
import kr.mintech.sleep.tight.activities.sleepdiarys.AddSleepDiaryActivity;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.utils.PreferenceUtil;
import Util.ContextUtil;
import Util.Logg;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager.WakeLock;
import android.sax.StartElementListener;
import android.widget.Toast;

public class BroadCastReceiver extends BroadcastReceiver
{
	NotificationManager _notificationManager;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onReceive(Context context, Intent intent)
	{
		ContextUtil.CONTEXT = context;
		String action = intent.getAction();
		// CLEANUP // Logg.w("shakej", "onReceive Action === " + action);
		
		if (action.equals("android.intent.action.SCREEN_ON")) {
			Pie.getInst().widgetLocation = "lockScreenWidget";			
		} else {
			Pie.getInst().widgetLocation = "homeScreenWidget";
			if (action.equals("kr.mintech.restart")) {
				// CLEANUP // Logg.w("BTReaderReceiver | onReceive()", "Service Restart : ");
				Intent i = new Intent(context, SleepTightService.class);
				context.startService(i);
			} else if (action.equals("kr.mintech.reminder")) {
				try
				{
					if (PreferenceUtil.getDiaryReminder().equals("true"))
					{
						NotificationManager notifier = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						
						Notification notify = new Notification(R.drawable.ic_launcher, "Sleeptight", System.currentTimeMillis());
						
						Intent kIntent = new Intent(context, AddSleepDiaryActivity.class);
						PendingIntent pender = PendingIntent.getActivity(context, 0, kIntent, 0);
						
						notify.setLatestEventInfo(context, "Sleeptight", "Reminder your sleep diary.", pender);
						
						notify.flags |= Notification.FLAG_AUTO_CANCEL;
						notify.number++;
						
						notifier.notify(1, notify);
					}
				} catch (Exception e)
				{
					
				}
			} else if (action.equals("kr.mintech.main.refresh")) {
				Pie.getInst().isRefreshing = true;
				
				Intent kIntent = new Intent(context, MainActivity.class);
				kIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(kIntent);
			} 
		}
	}
}
