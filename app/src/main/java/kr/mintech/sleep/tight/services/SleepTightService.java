package kr.mintech.sleep.tight.services;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.actions.ActionController;
import kr.mintech.sleep.tight.controllers.timeline.TimeLineController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.PreferenceUtil;
import kr.mintech.sleep.tight.utils.Util;
import Util.ContextUtil;
import Util.Logg;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class SleepTightService extends Service {
	private static final long REFRESHTIME = 300000;		// 5 minutes
	private static final String LOG = "SleepTightService";
	
	NotificationManager _notificationManager;
	TimeLineController _timeLineCtrl;
	ActionController _actionCtrl;
	Handler m_handler;
	Runnable m_statusChecker;

	@Override
	public void onCreate() {
		super.onCreate();
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		
		BroadCastReceiver receiver = new BroadCastReceiver();
		registerReceiver(receiver, filter);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		Log.w("SleepTightService | onStart()", "Service Start!");
		
		ContextUtil.CONTEXT = getApplicationContext();
		try {
			unRegisterRestart();
			checkReminderNotification();
		} catch (Exception e) {
			Log.w("SleepTightService | onStart()", "Service Reminder Time Check: failed -- " + e);
		}

		m_handler = new Handler();
		m_statusChecker = new Runnable() {
			@Override
			public void run() {
				widgetUpdate();
				m_handler.postDelayed(m_statusChecker, REFRESHTIME);
			}
		};

		startRepeatingTask();
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.w("SleepTightService | onDestroy()", "Service Destroy : ");
		registerRestart();
	}

	void startRepeatingTask() {
		m_statusChecker.run();
	}

	void stopRepeatingTask() {
		m_handler.removeCallbacks(m_statusChecker);
	}

	/*
	 * Requesting to add activity tracks to the server from Widget 
	 */
	public void requestAddTrack(int $activityId, String $startedAt, String $endedAt) {
		_timeLineCtrl = new TimeLineController(onRequestEndListener);
		_timeLineCtrl.reqeustAddActivityTrack($activityId, $startedAt, null);		
		
		EventLogger.log("add_activity", 
				"where", Util.getWigetLocation(),
				"type", "frequency",
				"activityId", $activityId);
	}

	/*
	 * WidgetUpdate
	 */
	public void widgetUpdate() {
		_timeLineCtrl = new TimeLineController(onRequestEndListener);
		_actionCtrl = new ActionController(onRequestEndListener);

		DateTime baseTime = new DateTime();
		baseTime.add(Calendar.MINUTE, 2);
		String kCurrentDateTimeStr = baseTime.getStringTime(SleepTightConstants.NetworkFormat);
		_timeLineCtrl.requestActivityTracksForWidget(kCurrentDateTimeStr, NumberConst.WIDGET_TIMELINE_DURATION);
		_timeLineCtrl.requestActivityTracks(kCurrentDateTimeStr, NumberConst.ACTIVITYTRACK_REQUEST_DURATION);
	}

	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndActivityTracksForWidget:
					// CLEANUP // Log.w("SleepTightService | enclosing_method()", "Activity Track loading completed");
					DateTime baseTime = new DateTime();
					String kCurrentDateTimeStr = baseTime.getStringTime(SleepTightConstants.NetworkFormat);
					_timeLineCtrl.requestSleepTracks(kCurrentDateTimeStr, NumberConst.SLEEPTRACK_REQUEST_DURATION);
					break;
					
				case NumberConst.requestEndSleepTracks:
					// CLEANUP // Log.w("SleepTightService | enclosing_method()", "SleepTracks loading completed");
// Don't understand why we need this:
//					_actionCtrl.requestActionsWithLimit(5);
//					break;
//					
//				case NumberConst.requestEndActionsWithLimit:
					// CLEANUP // Log.w("SleepTightService.onRequestEndListener.new OnRequestEndListener() {...} | onRequestEnded()", "Action loading completed");
					DateTime basePrevTime = new DateTime(0, 0);
					basePrevTime.add(Calendar.DATE, -1);
					String kPrevDateTimeStr = basePrevTime.getStringTime(SleepTightConstants.NetworkFormat);
					_timeLineCtrl.requestSleepDiaryExist(kPrevDateTimeStr, 24);
					break;
					
				case NumberConst.requestEndSleepTrackExist:		// Widget Update
					Intent intent = new Intent();
					intent.setAction("com.example.sleeptight.savebroadcast");
					ContextUtil.CONTEXT.sendBroadcast(intent);
					break;
					
				case NumberConst.requestSuccess:	// Called after an activity is tracked from Widget
					EventLogger.log("add_activity_result",
							"result", "success");
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Add Action In Widget");
					widgetUpdate();
					break;
					
				case NumberConst.requestFail:
					EventLogger.log("add_activity_result",
							"result", "fail");
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Network Fail");
					break;
			}
		}

		@Override
		public void onRequest(Object $unit) {
		}

		@Override
		public void onRequestError(int $tag, String $errorStr) {
		}
	};

	public void checkReminderNotification() {
		Intent alarmIntent = new Intent("kr.mintech.reminder");
		PendingIntent pender = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

		Date kNotificationTime = null;
		try {
			kNotificationTime = SleepTightConstants.DateTimeFormat
					.parse(PreferenceUtil.getDiaryReminderDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar kNotificationCalendar = Calendar.getInstance();
		kNotificationCalendar.setTime(kNotificationTime);

		// AlarmManager
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// RTC_WAKEUP = Reminder is on when the device is turned off
		// 24 * 60 * 60 * 1000 = every 24 hours
		am.setRepeating(AlarmManager.RTC_WAKEUP,
				kNotificationCalendar.getTimeInMillis(), 24 * 60 * 60 * 1000,
				pender);
	}

	void registerRestart() {
		Intent kintent = new Intent(SleepTightService.this,	BroadCastReceiver.class);
		kintent.setAction("kr.mintech.restart");
		PendingIntent kSender = PendingIntent.getBroadcast(SleepTightService.this, 0, kintent, 0);
		long firstTime = SystemClock.elapsedRealtime();
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, firstTime, 0, kSender);
	}

	void unRegisterRestart() {
		Intent intent = new Intent(SleepTightService.this, BroadCastReceiver.class);
		intent.setAction("kr.mintech.restart");
		PendingIntent kSender = PendingIntent.getBroadcast(SleepTightService.this, 0, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.cancel(kSender);
	}
}