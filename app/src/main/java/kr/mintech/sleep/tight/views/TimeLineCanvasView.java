package kr.mintech.sleep.tight.views;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.Pie;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class TimeLineCanvasView extends View {
	private Paint paint = new Paint();
	private Paint _handlePaint = new Paint();
	
	private Context _context;
	
	float canvasHeight;
	int sleepQuality;
	
	final float scale = getContext().getResources().getDisplayMetrics().density;
	
	float lineStartY = 0;
	float lineEndY;

	float timeHandleWidth = (int) (250.0f * scale + 0.5f);
	float timeHandleRadius = (int) (10.0f * scale + 0.5f);
	
	float paddingBottom = timeHandleRadius;
	float paddingTop = timeHandleRadius;
	
	private int sleepBarWidth = (int) (25.0f * scale + 0.5f);
	private float availableTimeLineHeight;

	private int tickNum = 24;
	private int tickWidth = (int) (1.0f * scale + 0.5f);
	float tickHighlight = 3;
	float tickLongWidth = (int) (4.0f * scale + 0.5f);
	float tickShortWidth = (int) (2.0f * scale + 0.5f);
	int tickTextSize = 10;
	
	
	private int iconWidth = (int) (25.0f * scale + 0.5f);
	private int left = (int) (51.0f * scale + 0.5f) - sleepBarWidth - tickWidth;
	private float iconStartX = left + tickWidth + sleepBarWidth;
	private float activityTrackHeight = (int) (2.0f * scale + 0.5f);
	
	RectF _handleRect = new RectF();
	float _touchX, _touchY;
	public static boolean _firstLoad = true;
	public static boolean _handleLoad = false;
	private DateTime initialTime;
	private DateTime handleTime;
	
	public TimeLineCanvasView(Context context) {
		super(context);
		_context = context;
	}
	
	public TimeLineCanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;
	}
	
	@Override
	public void onDraw(Canvas $canvas) {
		this.setBackgroundColor(_context.getResources().getColor(R.color.LightGray));

		DateTime midnight = new DateTime(Pie.getInst().baseTime);
		midnight.setTime(0, 0);
		float midnightY = timetoPosY(midnight);
		paint.setColor(_context.getResources().getColor(R.color.YesterdayColor));
		$canvas.drawRect(iconStartX, 0, getWidth(), midnightY, paint);
		
		lineEndY = this.getHeight();
		paint.setColor(_context.getResources().getColor(R.color.TickColor));
		paint.setStrokeWidth(tickWidth);
		$canvas.drawLine(left, lineStartY, left, lineEndY, paint);
		
		drawTicks($canvas);
		
		if (_firstLoad == true || _touchY > this.getHeight() - timeHandleRadius) {
			// CLEANUP // Log.w("TimeLineCanvasView | onDraw()", "CurrentTime Set :" + Pie.getInst().baseTime.getStringTime(SleepTightConstants.DateForamt));
			_touchY = timetoPosY(Pie.getInst().baseTime);
			_firstLoad = false;
		}
		if (_handleLoad == true) {
			_touchY = timetoPosY(Pie.getInst().handleTime);
			_handleLoad = false;			
		}
		if (_touchY == 0) {
			_touchY = timetoPosY(Pie.getInst().baseTime);
		}
		
		drawTimeBar(_context, $canvas);
		
		if (Pie.getInst().activityTrackUnit != null) {
			for (int i = 0; i < Pie.getInst().activityTrackUnit.size(); i++) {
				int kActivityId = Pie.getInst().activityTrackUnit.get(i).activityId;
				String kActivityName = Pie.getInst().activityTrackUnit.get(i).activityName;
				DateTime kStartDateTime = Pie.getInst().activityTrackUnit.get(i).actionStartedAtDateTime;
				DateTime kEndDateTime = Pie.getInst().activityTrackUnit.get(i).actionEndedAtDateTime;
				String kColor = Pie.getInst().activityTrackUnit.get(i).color;
				String kRecordType = Pie.getInst().activityTrackUnit.get(i).recordType;
				int kSortPosition = Pie.getInst().activityTrackUnit.get(i).sortPosition;
				
				// Sorted position
				Paint kPaint = new Paint();
				if (kColor == null) {
					kPaint.setColor(_context.getResources().getColor(R.color.time_line_caffeine));
				} else {
					kPaint.setColor(Color.parseColor(kColor));
				}
				if (kRecordType.equals("frequency")) {
					$canvas.drawRect(getPositionStartX(kSortPosition), timetoPosY(kStartDateTime) - activityTrackHeight, getPositionStartX(kSortPosition + 1), timetoPosY(kStartDateTime), kPaint);
				} else {
					$canvas.drawRect(getPositionStartX(kSortPosition), timetoPosY(kStartDateTime), getPositionStartX(kSortPosition + 1), timetoPosY(kEndDateTime), kPaint);
				}
			}
		}
		
		// Time Handler
		timeHandleWidth = this.getWidth() * 4/5;
		_handlePaint.setColor(_context.getResources().getColor(R.color.MarineBlue));
		_handlePaint.setStrokeWidth(tickWidth);
		_handlePaint.setTextSize(tickTextSize * scale);
		$canvas.drawLine(left, _touchY, left + timeHandleWidth, _touchY, _handlePaint);
		$canvas.drawCircle(left + timeHandleWidth, _touchY, timeHandleRadius, _handlePaint);
		// CLEANUP // Log.w("shakej", "posYTime Handler touchY position =" + _touchY);
		String timeLabel = posYToTime(_touchY);
		_handlePaint.setTextAlign(Align.CENTER);
		$canvas.drawText(timeLabel, (left * 2 + timeHandleWidth)*2/3, _touchY - (5*scale), _handlePaint);
	}

	public void drawTicks(Canvas $canvas) {
		DateTime TickHourTime = new DateTime(Pie.getInst().baseTime);
		int tickHour = TickHourTime.get(Calendar.HOUR_OF_DAY);
		int currentMin = TickHourTime.get(Calendar.MINUTE);
		
		// CLEANUP // Log.w("TimeLineCanvasView | onDraw()", "tickHour : " + tickHour + "currentMin : " + currentMin);
		
		DateTime dtTickHour = new DateTime(Pie.getInst().baseTime, tickHour, 0);
		
		// CLEANUP // Log.w("TimeLineCanvasView | onDraw()", "dtTickHour : " + dtTickHour.getStringTime(SleepTightConstants.DateForamt));
		boolean bPM = tickHour >= 12 && tickHour < 24;
		tickHour = tickHour % 12;
		
		// Drawing Timeline Ticks & Writing Tick Labels
		for (int j = tickNum; j >= 0; j--, tickHour--) {
			boolean bnewPM = bPM;
			if (tickHour <= 0) {
				tickHour += 12;
				if (bPM) {
					bnewPM = false;
				} else {
					bnewPM = true;
				}
			}
			
			float yPos = timetoPosY(dtTickHour);
			
			paint.setColor(_context.getResources().getColor(R.color.TickColor));
			paint.setStrokeWidth(tickWidth);
			
			// Draw Ticks
			if (tickHour % tickHighlight == 0) {
				paint.setStrokeWidth(tickWidth);
				
				$canvas.drawLine(left, yPos, left - tickLongWidth, yPos, paint);
				String strHour = Integer.toString(tickHour) + (bPM ? "P" : "A");
				bPM = bnewPM;
				paint.setTextAlign(Align.CENTER);
				paint.setTextSize(tickTextSize * scale);
				paint.setColor(_context.getResources().getColor(R.color.Black));
				$canvas.drawText(strHour, left/2, yPos + (tickTextSize * scale)/2, paint);
			}
			paint.setColor(_context.getResources().getColor(R.color.TickColor));
			$canvas.drawLine(left, yPos, left - tickShortWidth, yPos, paint);
			dtTickHour.add(Calendar.HOUR_OF_DAY, -1);
		}
	}
	
	
	private float getPositionStartX(int $sortPosition)	{
		return iconStartX + ($sortPosition - 1) * iconWidth; 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		_touchX = event.getX();
		_touchY = event.getY();
		
		if (_touchY > lineEndY || _touchY < paddingTop) {
			return true;
		} else {
			invalidate();
			return true;
		}
	}
	
	public float timetoPosY(DateTime dt) {
		float availableTimeLineHeight = lineEndY - paddingBottom - lineStartY - paddingTop;
		initialTime = new DateTime(Pie.getInst().baseTime);
		initialTime.add(Calendar.HOUR_OF_DAY, -24);
		
		float lOffsetY = (dt.getTimeInMillis() - initialTime.getTimeInMillis());
		float posY = availableTimeLineHeight * lOffsetY / (TimeUnit.HOURS.toMillis(24));
		posY = posY + lineStartY + paddingTop;
		
		return posY;
	}
	
	public String posYToTime(float $posY) {
		$posY = $posY - lineStartY - paddingTop;
		Pie pie = Pie.getInst();
		handleTime = new DateTime(pie.baseTime);
		
		availableTimeLineHeight = lineEndY - paddingBottom - lineStartY - paddingTop;
		
		if ($posY <= availableTimeLineHeight) {		
			initialTime = new DateTime(pie.baseTime);
			initialTime.add(Calendar.HOUR_OF_DAY, -24);
			long allTimeMillis = TimeUnit.HOURS.toMillis(24);
	
			long initMillis = initialTime.getTimeInMillis();
			float yMillis = initMillis + ($posY * allTimeMillis / availableTimeLineHeight);
					
			handleTime.setTimeInMillis(pie.baseTime, (long) (yMillis));
		} 
		
		String kYTime = handleTime.getStringTime(SleepTightConstants.HandleForamt);
		pie.handleTime = handleTime;
		return kYTime;
	}
	
	
	private void drawTimeBar(Context context, Canvas c) {
		float yToBed = 0;
		float yToSleep = 0;
		float yWakeUp = 0;
		float yOutBed = 0;
		
		float timebarY = timetoPosY(Pie.getInst().baseTime);
		
		// Gray Bar
		paint.setColor(context.getResources().getColor(R.color.MediumGray));
		c.drawRect(left + tickWidth - 1, lineStartY, left + tickWidth + sleepBarWidth, lineEndY, paint);
		
		// Calculate currentTimeX
//		DateTime now = new DateTime();
//		currentTimeY = timetoPosY(Pie.getInst().baseTime);
		
		if (Pie.getInst().sleepTrackUnit != null && Pie.getInst().sleepTrackUnit.size() > 0) {
			for (int i = 0; i < Pie.getInst().sleepTrackUnit.size(); i++) {
				String kToBedTime = Pie.getInst().sleepTrackUnit.get(i).inBedTime;
				String kToSleepTime = Pie.getInst().sleepTrackUnit.get(i).sleepTime;
				String kToWakeUpTime = Pie.getInst().sleepTrackUnit.get(i).wakeUpTime;
				String kOutOfBedTime = Pie.getInst().sleepTrackUnit.get(i).outOfBedTime;
				sleepQuality = Pie.getInst().sleepTrackUnit.get(i).sleepQuality;
				
				DateTime kToBedTimeDateTime = new DateTime(kToBedTime);
				if (kToSleepTime != null)
				{
					DateTime kSleepDateTime = new DateTime(kToSleepTime);
					yToSleep = timetoPosY(kSleepDateTime);
				}
				DateTime kWakeUpDateTime = new DateTime(kToWakeUpTime);
				DateTime kOutOfBedDateTime = new DateTime(kOutOfBedTime);
				
				yToBed = timetoPosY(kToBedTimeDateTime);
				
				yWakeUp = timetoPosY(kWakeUpDateTime);
				yOutBed = timetoPosY(kOutOfBedDateTime);
				
				// Sleep Latency
				int y = (int) (yToSleep - yToBed) / 2;
				for (int j = 0; j < y; j++) {
					paint.setColor(context.getResources().getColor(R.color.HotPink));
					c.drawLine(left + tickWidth - 1, yToBed, left + tickWidth + sleepBarWidth - 1, yToBed, paint);
					paint.setColor(context.getResources().getColor(R.color.White));
					c.drawLine(left + tickWidth - 1, yToBed + 1, left + tickWidth + sleepBarWidth - 1, yToBed + 1, paint);
					yToBed = yToBed + 2;
				}
				
				switch (sleepQuality)
				{
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
				
				// CLEANUP // Log.w("TimeLineCanvasView | drawTimeBar()", "yToSleep : " + yToSleep + "/" + yWakeUp);
				c.drawRect(left + tickWidth - 1, yToSleep, left + tickWidth + sleepBarWidth, yWakeUp, paint);
				
				int x = (int) (yOutBed - yWakeUp) / 2;
				for (int j = 0; j < x; j++) {
					paint.setColor(context.getResources().getColor(R.color.HotPink));
					c.drawLine(left + tickWidth - 1, yWakeUp, left + tickWidth + sleepBarWidth - 1, yWakeUp, paint);
					paint.setColor(context.getResources().getColor(R.color.White));
					c.drawLine(left + tickWidth - 1, yWakeUp + 1, left + tickWidth + sleepBarWidth - 1, yWakeUp + 1, paint);
					yWakeUp = yWakeUp + 2;
				}
			}
		}
	}
}
