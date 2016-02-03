package kr.mintech.sleep.tight.widgets;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.MainActivity;
import kr.mintech.sleep.tight.activities.sleepdiarys.AddSleepDiaryActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.actions.ActionController;
import kr.mintech.sleep.tight.controllers.timeline.TimeLineController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.services.SleepTightService;
import kr.mintech.sleep.tight.units.ActionUnit;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.utils.Util;
import Util.Logg;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.opengl.Visibility;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

public class LockScreenProvider extends AppWidgetProvider {
	private static final int MAX_NUMICONS = 5;
	
	private TimeLineController _timeLineCtrl;
	private ActionController _actionCtrl;
	private Context _context;
	
	private DateTime dtToBedTime = new DateTime();
	private DateTime dtToSleepTime = new DateTime();
	private DateTime dtWakeTime = new DateTime();
	private DateTime dtOutBedTime = new DateTime();
	private int sleepQuality;
	
	private Paint paint = new Paint();
	
	float scale; 
	
	float lineY = 40;
	float timelineHeight = 100;
	float timebarHeight = 20;
	float timehandleHeight = 110;
	float timehandleRadius = 5;
	float left = 0;
	float top = 100;
	float right = 320;
	float bottom1 = 110;
	float bottom2 = 300;
	float bottom3 = 720;
	float currentTimeX;
	float tobedTimeX = 300;
	float tosleepTimeX = 350;
	float wakeupTimeX = 550;
	float outbedTimeX = 600;
	
	float paddingX10 = 10;
	float paddingX50 = 50;
	float paddingY = 20;
	float paddingRight = 20;
	float lineThickness = 2;
	
	float rectWidth = 30;
	
	float tickWidth = 1;
	float tickShortHeight = 3;
	float tickLongHeight = 5;
	private int tickNum = 18;
	float tickHighlight = 3;
	int tickTextSize = 11;
	
	//Color code Y Position
	private int activityTrackWidth = 3;
	private int activityTrackHeight = 14;
	private int iconStartY = (int) (lineY + tickLongHeight + 1);
	
	private int[] actionButtonIds;
	
	/*
	 * @see
	 * android.appwidget.AppWidgetProvider#onReceive(android.content.Context, android.content.Intent)
	 */
	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action == "com.example.sleeptight.savebroadcast") {
			initActionButtonIds();

			//Intent 
			DateTime now = new DateTime();
			int numIcons = 0;
			PendingIntent[] intent_AddActionPendingIntents = null;
			if (Pie.getInst().actionkUnit != null) {
				numIcons = Math.min(Pie.getInst().actionkUnit.size(), MAX_NUMICONS);
				intent_AddActionPendingIntents = new PendingIntent[numIcons];
				
				for (int i = 0; i < numIcons; i++) {
					Intent intent_AddAction = new Intent();
					intent_AddAction.setAction("kr.mintech.add.action");
					intent_AddAction.putExtra("activityId", Pie.getInst().actionkUnit.get(i).id);
					intent_AddActionPendingIntents[i] = PendingIntent.getBroadcast(context, i + 2, intent_AddAction, PendingIntent.FLAG_UPDATE_CURRENT);
				}
			}
			
			Intent intent_AddSleep = new Intent(context, AddSleepDiaryActivity.class);
			intent_AddSleep.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			Pie.getInst().widgetLocation = "homeScreenWidget";
			PendingIntent pendingIntent_AddSleep = PendingIntent.getActivity(context, 0, intent_AddSleep, PendingIntent.FLAG_UPDATE_CURRENT);
			
			Intent intent_GoToAddActivity = new Intent(context, MainActivity.class);
			intent_GoToAddActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent_GoToAddActivity.putExtra("goToCurrentTime", true);
			intent_GoToAddActivity.putExtra("startPage", 0);
			PendingIntent pendingIntent_AppTimeLine = PendingIntent.getActivity(context, 1, intent_GoToAddActivity, PendingIntent.FLAG_UPDATE_CURRENT);

			Intent intent_GoToSleepSummary = new Intent(context, MainActivity.class);
			intent_GoToSleepSummary.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent_GoToSleepSummary.putExtra("goToCurrentTime", false);
			intent_GoToSleepSummary.putExtra("startPage", 1);
			PendingIntent pendingIntent_AppSleepSummary = PendingIntent.getActivity(context, 2, intent_GoToSleepSummary, PendingIntent.FLAG_UPDATE_CURRENT);

			// CLEANUP // Log.w("LockScreenProvider | onReceive()", "Widget Receive! : " + action);
			DateTime yesterday = new DateTime(0, 0);
			yesterday.add(Calendar.DATE, -1);
			
			RemoteViews rvWidgetLayout = new RemoteViews(context.getPackageName(), R.layout.widget_lock_screen);
			
			// Link to the Sleep Diary Button 
			// When the sleep diary exists, disable the link and show the previous night's sleep duration and sleep quality
			for (int i = 0; i < numIcons; i++)	{
				ActionUnit kUnit = Pie.getInst().actionkUnit.get(i);
				setDefaultIcon(context, i, rvWidgetLayout, kUnit.name, kUnit.defaultType, kUnit.color);
			}
			
			// CLEANUP // Log.w("LockScreenProvider | onReceive()", " Pie.getInst().isEmptySleepDiary: " + Pie.getInst().isEmptySleepDiary);
			rvWidgetLayout.setInt(R.id.rlAddSleep, "setBackgroundColor", context.getResources().getColor(R.color.LightGray));
			if (Pie.getInst().isEmptySleepDiary) {
				rvWidgetLayout.setOnClickPendingIntent(R.id.rlAddSleep, pendingIntent_AddSleep);
				rvWidgetLayout.setViewVisibility(R.id.ibAddSleep, View.VISIBLE);
				rvWidgetLayout.setViewVisibility(R.id.bAddSleep, View.VISIBLE);
				rvWidgetLayout.setViewVisibility(R.id.tvSleepDuration, View.GONE);
				rvWidgetLayout.setViewVisibility(R.id.tvAddSleep, View.GONE);
				rvWidgetLayout.setImageViewResource(R.id.ibSleepQuality, R.drawable.addquality);
			} else { // yesterday's sleep diary exists
				rvWidgetLayout.setOnClickPendingIntent(R.id.rlAddSleep, pendingIntent_AppSleepSummary);
				rvWidgetLayout.setViewVisibility(R.id.ibAddSleep, View.GONE);
				
				int kTotalSleepDuration = 0;
				if (Pie.getInst().sleepTrackUnit.size() > 0) {
					int j = Pie.getInst().sleepTrackUnit.size() - 1;
					sleepQuality = Pie.getInst().sleepTrackUnit.get(j).sleepQuality;
					kTotalSleepDuration = Pie.getInst().sleepTrackUnit.get(j).sleepDuration;
				}
				
				String strSleepDuration;
				if (kTotalSleepDuration != 0) {
					int kHours = kTotalSleepDuration / 60;
					int kMins = kTotalSleepDuration % 60;
					strSleepDuration = Integer.toString(kHours) + "h " + Integer.toString(kMins) + "m";
				} else {
					strSleepDuration = "0h";
				}
				
				switch (sleepQuality) {
					case 1:
						rvWidgetLayout.setImageViewResource(R.id.ibSleepQuality, R.drawable.quality1);
						break;
					case 2:
						rvWidgetLayout.setImageViewResource(R.id.ibSleepQuality, R.drawable.quality2);
						break;
					case 3:
						rvWidgetLayout.setImageViewResource(R.id.ibSleepQuality, R.drawable.quality3);
						break;
					case 4:
						rvWidgetLayout.setImageViewResource(R.id.ibSleepQuality, R.drawable.quality4);
						break;
					case 5:
						rvWidgetLayout.setImageViewResource(R.id.ibSleepQuality, R.drawable.quality5);
						break;
				}
				
				rvWidgetLayout.setViewVisibility(R.id.bAddSleep, View.GONE);
				rvWidgetLayout.setTextViewText(R.id.tvSleepDuration, strSleepDuration);
				rvWidgetLayout.setViewVisibility(R.id.tvSleepDuration, View.VISIBLE);
				rvWidgetLayout.setTextViewText(R.id.tvAddSleep, "of sleep last night");
				rvWidgetLayout.setViewVisibility(R.id.tvAddSleep, View.VISIBLE);
				rvWidgetLayout.setInt(R.id.tvAddSleep, "setTextColor", context.getResources().getColor(R.color.DarkGray));
			}
			
			// Widget Timeline 
			Bitmap.Config conf = Bitmap.Config.ARGB_8888;
			Bitmap bmp = Bitmap.createBitmap(320, 180, conf);
			Canvas c = new Canvas(bmp);
			
			
			paint.setColor(context.getResources().getColor(R.color.LightGray));
			
			paint.setColor(context.getResources().getColor(R.color.DarkGray));
			c.drawLine(left, lineY, right, lineY, paint);
			c.drawLine(left, lineY + timelineHeight, right, lineY + timelineHeight, paint);
			
			int tickHour = now.getCurrentTime(Calendar.HOUR_OF_DAY);
			int currentMin = now.getCurrentTime(Calendar.MINUTE);
			currentTimeX = timetoPosX(now);
			
			boolean bPM = tickHour >= 12 && tickHour < 24; // CurrentTime is PM
			DateTime dtTickHour = new DateTime(tickHour, 0);
			tickHour = tickHour % 12;
			
			for (int j = tickNum; j >= 0; j--, tickHour--) {
				boolean bnewPM = bPM;
				if (tickHour <= 0) {
					tickHour += 12;
					if (bPM) {
						bnewPM = false;
					}
					else {
						bnewPM = true;
					}
				}
				
				float xPos = timetoPosX(dtTickHour);
				
				paint.setColor(context.getResources().getColor(R.color.LightGray));
				paint.setStrokeWidth(tickWidth);
				if (tickHour % tickHighlight == 0) {
					paint.setColor(context.getResources().getColor(R.color.DarkGray));
					paint.setStrokeWidth(tickWidth);
					
					c.drawLine(xPos, lineY, xPos, lineY + tickLongHeight, paint);
					String strHour = Integer.toString(tickHour) + (bPM ? "P" : "A");
					bPM = bnewPM;
					paint.setTextAlign(Align.CENTER);
					paint.setColor(context.getResources().getColor(R.color.Black));
					paint.setTextSize(tickTextSize);
					c.drawText(strHour, xPos, lineY - 5, paint);
				}
				paint.setColor(context.getResources().getColor(R.color.DarkGray));
				c.drawLine(xPos, lineY + 1, xPos, lineY + tickShortHeight, paint);
				dtTickHour.add(Calendar.HOUR_OF_DAY, -1);
			}
			
			drawTimeBar(context, c);
			
			// Draw Current Time Handle
			paint.setColor(context.getResources().getColor(R.color.MarineBlue));
			paint.setStrokeWidth(tickWidth);
			c.drawLine(currentTimeX, lineY, currentTimeX, lineY + timehandleHeight, paint);
			c.drawCircle(currentTimeX, lineY + timehandleHeight, timehandleRadius, paint);
			
			//Color Code
			if (Pie.getInst().activityTrackUnitForWidget != null) {
				// CLEANUP // Log.w("LockScreenProvider | onReceive()", "Widget activityTrackUnitForWidget != null : ");
				if (Pie.getInst().activityTrackUnitForWidget.size() > 0) {
					for (int i = 0; i < Pie.getInst().activityTrackUnitForWidget.size(); i++) {
						int kActivityId = Pie.getInst().activityTrackUnitForWidget.get(i).activityId;
						String kActivityName = Pie.getInst().activityTrackUnitForWidget.get(i).activityName;
						String kStartedAt = Pie.getInst().activityTrackUnitForWidget.get(i).actionStartedAt;
						String kEndedAt = Pie.getInst().activityTrackUnitForWidget.get(i).actionEndedAt;
						String kColor = Pie.getInst().activityTrackUnitForWidget.get(i).color;
						String kRecordType = Pie.getInst().activityTrackUnitForWidget.get(i).recordType;
						int kSortPosition = Pie.getInst().activityTrackUnitForWidget.get(i).sortPosition;
						
						Paint kPaint = new Paint();
						kPaint.setColor(Color.parseColor(kColor));
						
						if (kSortPosition < 6) { 
							if (kRecordType.equals("frequency")) {
								DateTime kStartDateTime = new DateTime(kStartedAt);
								c.drawRect(timetoPosX(kStartDateTime) - activityTrackWidth, getPositionStartY(kSortPosition), timetoPosX(kStartDateTime), getPositionStartY(kSortPosition + 1) - 1, kPaint);
							} else {
								DateTime kStartDateTime = new DateTime(kStartedAt);
								DateTime kEndDateTime = new DateTime(kEndedAt);
								c.drawRect(timetoPosX(kStartDateTime), getPositionStartY(kSortPosition), timetoPosX(kEndDateTime), getPositionStartY(kSortPosition + 1) - 1, kPaint);
							}
						}
					}
				}
			}
			
			// Set OnClickPendingIntent to each icon at the button
			for (int i = 0; i < numIcons; i++) {
				rvWidgetLayout.setOnClickPendingIntent(actionButtonIds[i], intent_AddActionPendingIntents[i]);				
			}
			
			rvWidgetLayout.setViewVisibility(R.id.layout_loading, View.GONE);
			rvWidgetLayout.setImageViewBitmap(R.id.ivCanvas, bmp);
			rvWidgetLayout.setViewVisibility(R.id.ivCanvas, View.VISIBLE);
			
			rvWidgetLayout.setOnClickPendingIntent(R.id.ivCanvas, pendingIntent_AppTimeLine);
			
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			ComponentName thisWidget = new ComponentName(context, LockScreenProvider.class);
			manager.updateAppWidget(thisWidget, rvWidgetLayout);
		}
		
		super.onReceive(context, intent);
	}

	public void initActionButtonIds() {
		actionButtonIds = new int[MAX_NUMICONS];
		actionButtonIds[0] = R.id.btn_1_action_add;
		actionButtonIds[1] = R.id.btn_2_action_add;
		actionButtonIds[2] = R.id.btn_3_action_add;
		actionButtonIds[3] = R.id.btn_4_action_add;
		actionButtonIds[4] = R.id.btn_5_action_add;
	}
	
	private int getPositionStartY(int $sortPosition) {
		return iconStartY + ($sortPosition - 1) * activityTrackHeight;
	}
		
	/*
	 * @see
	 * android.appwidget.AppWidgetProvider#onUpdate(android.content.Context,
	 * android.appwidget.AppWidgetManager, int[])
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		//paint.setColor(Color.GREEN);
		
		Intent kIntent = new Intent(context, SleepTightService.class);
		context.startService(kIntent);
		
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			
			// Get the layout for the App Widget
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_lock_screen);
			
			Intent intent_AddSleep = new Intent(context, AddSleepDiaryActivity.class);
			PendingIntent pendingIntent_AddSleep = PendingIntent.getActivity(context, 0, intent_AddSleep, PendingIntent.FLAG_UPDATE_CURRENT);
			
			Intent intent_GoToApp = new Intent(context, MainActivity.class);
			intent_GoToApp.putExtra("fromLockScreen", true);
			PendingIntent pendingIntent_AppTimeLine = PendingIntent.getActivity(context, 1, intent_GoToApp, PendingIntent.FLAG_UPDATE_CURRENT);
			
			//views.setViewVisibility(R.id.btn_add, View.GONE);
			views.setViewVisibility(R.id.layout_loading, View.VISIBLE);
			views.setViewVisibility(R.id.ivCanvas, View.GONE);
			views.setOnClickPendingIntent(R.id.rlAddSleep, pendingIntent_AddSleep);
			views.setOnClickPendingIntent(R.id.ivCanvas, pendingIntent_AppTimeLine);
			
			// Tell the AppWidgetManager to perform an update on the current app widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
		ComponentName myWidget = new ComponentName(context, LockScreenProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(context);
		manager.updateAppWidget(myWidget, remoteViews);
	}
	
	public float timetoPosX(DateTime dt) {
		float availableTimeLineWidth = right - paddingRight - left - paddingX10;
		DateTime dtInitialTime = new DateTime();
		dtInitialTime.add(Calendar.HOUR_OF_DAY, -18);
		long lOffsetX = dt.getTimeInMillis() - dtInitialTime.getTimeInMillis();
		float posX = availableTimeLineWidth * lOffsetX / TimeUnit.HOURS.toMillis(18);
		posX = posX + left + paddingX10;
		
		return posX;
	}
	
	private void drawTimeBar(Context context, Canvas c) {		
		if (Pie.getInst().sleepTrackUnit != null && Pie.getInst().sleepTrackUnit.size() > 0) {
			int i = Pie.getInst().sleepTrackUnit.size() - 1; // latest sleep diary info 
			String kToBedTime = Pie.getInst().sleepTrackUnit.get(i).inBedTime;
			String kToSleepTime = Pie.getInst().sleepTrackUnit.get(i).sleepTime;
			String kToWakeUpTime = Pie.getInst().sleepTrackUnit.get(i).wakeUpTime;
			String kOutOfBedTime = Pie.getInst().sleepTrackUnit.get(i).outOfBedTime;
			sleepQuality = Pie.getInst().sleepTrackUnit.get(i).sleepQuality;
			
			dtToBedTime = new DateTime(kToBedTime);
			if (kToSleepTime != null) {
				dtToSleepTime = new DateTime(kToSleepTime);
			}
			dtWakeTime = new DateTime(kToWakeUpTime);
			dtOutBedTime = new DateTime(kOutOfBedTime);
			
			float xToBed = timetoPosX(dtToBedTime);
			float xToSleep = timetoPosX(dtToSleepTime);
			float xWakeUp = timetoPosX(dtWakeTime);
			float xOutBed = timetoPosX(dtOutBedTime);
			float timebarY = lineY + timelineHeight - timebarHeight;
			
			DateTime now = new DateTime();
			currentTimeX = timetoPosX(now);
			
			// Gray Bar
			paint.setColor(context.getResources().getColor(R.color.LightGray));
			c.drawRect(left, timebarY, currentTimeX, timebarY + timebarHeight, paint);
			
			// Sleep Latency
			int x = (int) (xToSleep - xToBed) / 2;
			for (int j = 0; j < x; j++) {
				paint.setColor(context.getResources().getColor(R.color.HotPink));
				c.drawLine(xToBed, timebarY, xToBed, timebarY + timebarHeight, paint);
				paint.setColor(context.getResources().getColor(R.color.White));
				c.drawLine(xToBed + 1, timebarY, xToBed + 1, timebarY + timebarHeight, paint);
				xToBed = xToBed + 2;
			}
			
			switch (sleepQuality) {
				case 1:
					paint.setColor(context.getResources().getColor(R.color.SleepQ1));
					break;
				case 2:
					paint.setColor(context.getResources().getColor(R.color.SleepQ2));
					break;
				case 3:
					paint.setColor(context.getResources().getColor(R.color.SleepQ3));
					break;
				case 4:
					paint.setColor(context.getResources().getColor(R.color.SleepQ4));
					break;
				case 5:
					paint.setColor(context.getResources().getColor(R.color.SleepQ5));
					break;
			}
			// CLEANUP // Log.w("LockScreenProvider | drawTimeBar()", "xToSleep : " + xToSleep + "/" + xWakeUp);
			c.drawRect(xToSleep, timebarY, xWakeUp, timebarY + timebarHeight, paint);
			
			int y = (int) (xOutBed - xWakeUp) / 2;
			for (int k = 0; k < y; k++) {
				paint.setColor(context.getResources().getColor(R.color.HotPink));
				c.drawLine(xWakeUp, timebarY, xWakeUp, timebarY + timebarHeight, paint);
				paint.setColor(context.getResources().getColor(R.color.White));
				c.drawLine(xWakeUp + 1, timebarY, xWakeUp + 1, timebarY + timebarHeight, paint);
				xWakeUp = xWakeUp + 2;
			}
		}
	}
	
	private void setDefaultIcon(Context $context, int $position, RemoteViews $remoteView, String $name, boolean $defaultType, String $color) {
		if ($defaultType) {
			if ($name.contains("Caffeine"))	{
				$remoteView.setImageViewResource(actionButtonIds[$position], R.drawable.addcoffee);
			} else if ($name.contains("Meal")) {
				$remoteView.setImageViewResource(actionButtonIds[$position], R.drawable.addmeal);
			} else if ($name.contains("Medication")) {
				$remoteView.setImageViewResource(actionButtonIds[$position], R.drawable.addmed);
			} else if ($name.contains("Tobacco")) {
				$remoteView.setImageViewResource(actionButtonIds[$position], R.drawable.addtobacco);
			} else if ($name.contains("Exercise")) {
				$remoteView.setImageViewResource(actionButtonIds[$position], R.drawable.addexercise);
			} else if ($name.contains("Alcohol")) {
				$remoteView.setImageViewResource(actionButtonIds[$position], R.drawable.addalcohol);
			}
		} else {
			if ($color != null)	{
				//There's no designated icon for custom action -> Need to assign color instead. 
				int kColor = Color.parseColor($color);
				Paint rectPaint = new Paint();
				rectPaint.setColor(kColor);
				
				scale = $context.getResources().getDisplayMetrics().density;
				int rectWidthScaled = (int) (rectWidth * scale + 0.5f);
				Rect rect = new Rect(0, 0, rectWidthScaled, rectWidthScaled);
				
				Bitmap.Config conf = Bitmap.Config.ARGB_8888;
				Bitmap bmp = Bitmap.createBitmap(rect.width(), rect.height(), conf);
				
				Canvas canvas = new Canvas (bmp);
				canvas.drawRect(rect, rectPaint);
				
				$remoteView.setImageViewBitmap(actionButtonIds[$position], bmp);
			}
		}
	}
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object)
		{
			switch ($tag)
			{
				case NumberConst.requestEndActivityTracks:
					DateTime baseTime = new DateTime();
					String kCurrentDateTimeStr = baseTime.getStringTime(SleepTightConstants.NetworkFormat);
					_timeLineCtrl.requestSleepTracks(kCurrentDateTimeStr, NumberConst.SLEEPTRACK_REQUEST_DURATION);
					break;
					
				case NumberConst.requestEndSleepTracks:
// Don't understand why we need this:
//					_actionCtrl.requestActionsWithLimit(MAX_NUMICONS);
//					break;
//					
//				case NumberConst.requestEndActionsWithLimit:
					Pie.getInst().activityTrackUnitForWidget = _timeLineCtrl._activityTrackUnits;

					Intent intent = new Intent();
					intent.setAction("com.example.sleeptight.savebroadcast");
					_context.sendBroadcast(intent);
					break;
				
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Network Failure");
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
}
