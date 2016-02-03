package kr.mintech.sleep.tight.views;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.sleepdiarys.SummaryBinInfoActivity;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.units.SleepTrackUnit;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SummaryCanvasView extends View {
	private Paint paint = new Paint();
	private Paint _handlePaint = new Paint();
	
	private Context _context;
	
	float canvasHeight;
	int sleepQuality;
	
	final float scale = getContext().getResources().getDisplayMetrics().density;
//	float top = 30;
//	float right = 45;
	float paddingBottom = (10.0f * scale + 0.5f);
	float paddingTop = (10.0f * scale + 0.5f);
	
	float lineStartY = 0;
	float lineEndY;
//	float timelineHeight = 40;
//	float timebarHeight = 20;
//	float timeHandleWidth = 500;
//	float timeHandleRadius = 20;
	
	public int numBin;  // default = 7, Day View	
	private float sleepBarWidth;
	private float sleepBarSpacing;
	private float availableCanvasHeightforGraphs;
	private float availableCanvasWidthforGraphs;
	private int binToSpaceRatio = 7;

	private int tickNum = 24;
	private int tickWidth = (int) (1.0f * scale + 0.5f);
	float tickHighlight = 3;
	float tickLongWidth = (int) (4.0f * scale + 0.5f);
	float tickShortWidth = (int) (2.0f * scale + 0.5f);
	int tickTextSize = 10;
	
	private int bottomIconY = 710;
	private int bottomCircleY = 730;
	private int iconWidth = (int) (25.0f * scale + 0.5f);
	private int left = (int) (25.0f * scale + 0.5f);
	private float timeBarStartX = left + tickWidth;
	private float iconStartX = left + tickWidth + sleepBarWidth;
	private float activityTrackHeight = 5;
	
	RectF _handleRect = new RectF();
	float _touchX, _touchY;
	public static boolean _firstLoad = true;
	public static boolean _handleLoad = false;
	private DateTime initialTime;
	private DateTime handleTime;
	
	private Hashtable<Integer, Integer> idx2idx = new Hashtable<Integer, Integer>();
	
	public SummaryCanvasView(Context context) {
		super(context);
		_context = context;
	}
	
	public SummaryCanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;
	}
	
	@Override
	public void onDraw(Canvas $canvas) {
		this.setBackgroundColor(_context.getResources().getColor(R.color.LightGray));
		
		lineEndY = this.getHeight();
		paint.setColor(_context.getResources().getColor(R.color.TickColor));
		paint.setStrokeWidth(tickWidth);
		$canvas.drawLine(left, lineStartY, left, lineEndY, paint);
		
		drawTicks($canvas);		
		drawTimeBar(_context, $canvas);
	}

	public void drawTicks(Canvas $canvas) {
		DateTime TickHourTime = new DateTime(Pie.getInst().graphEndDate);
		int tickHour = TickHourTime.get(Calendar.HOUR_OF_DAY);
		int currentMin = TickHourTime.get(Calendar.MINUTE);
		
		// CLEANUP // Log.w("SummaryCanvasView | onDraw()", "tickHour : " + tickHour + "currentMin : " + currentMin);
		
		DateTime dtTickHour = new DateTime(Pie.getInst().graphEndDate, tickHour, 0);
		
		// CLEANUP // Log.w("SummaryCanvasView | onDraw()", "dtTickHour : " + dtTickHour.getStringTime(SleepTightConstants.DateForamt));
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
			
			float yPos = timetoPosY(dtTickHour, 1);
			
			paint.setColor(_context.getResources().getColor(R.color.TickColor));
			paint.setStrokeWidth(tickWidth);
			
			// Draw Ticks
			if (tickHour % tickHighlight == 0) {
				paint.setStrokeWidth(tickWidth);
				
				$canvas.drawLine(left, yPos, left - tickLongWidth, yPos, paint);
				String strHour = Integer.toString(tickHour) + (bPM ? "P" : "A");
				bPM = bnewPM;
				paint.setColor(_context.getResources().getColor(R.color.Black));
				paint.setTextAlign(Align.CENTER);
				paint.setTextSize(tickTextSize * scale);
				$canvas.drawText(strHour, left/2, yPos + (tickTextSize * scale)/2, paint);
			}
			paint.setColor(_context.getResources().getColor(R.color.TickColor));
			$canvas.drawLine(left, yPos, left - tickShortWidth, yPos, paint);
			dtTickHour.add(Calendar.HOUR_OF_DAY, -1);
		}
	}
	
	private int barIndexDown = -1;
	@SuppressLint("UseValueOf")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		_touchX = event.getX();
		_touchY = event.getY();

		if (numBin == 7 && _touchX > timeBarStartX) {
			//xPosition = timeBarStartX + (numBin - dayDiff) * (sleepBarWidth + sleepBarSpacing);
			int barIdx = (int)((_touchX - timeBarStartX) / (sleepBarWidth + sleepBarSpacing));
			
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				barIndexDown = barIdx;
				return true;
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (barIdx == barIndexDown) {
					Integer arrayIdx = idx2idx.get(new Integer(7 - barIdx));
					if (arrayIdx != null) {
						SleepTrackUnit aUnit = Pie.getInst().sleepTrackUnitArray.get(arrayIdx.intValue());
						
						EventLogger.log("view_dailySummary", 
								"date", aUnit.diaryDate);
						
						Intent kIntent = new Intent(_context, SummaryBinInfoActivity.class);
						kIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						kIntent.putExtra("select_date", aUnit.diaryDate);
						kIntent.putExtra("id", Integer.toString(aUnit.id));
						super.getContext().startActivity(kIntent);
					}
				}
				barIndexDown = -1;
			}
		}
		
		return true;
	}
	
	public float timetoPosY(DateTime dt, int nPrevDays) {
		float availableTimeLineHeight = lineEndY - paddingBottom - lineStartY - paddingTop;
		initialTime = new DateTime(Pie.getInst().graphEndDate);
		initialTime.add(Calendar.HOUR_OF_DAY, -24 * nPrevDays);
		
		float lOffsetY = (dt.getTimeInMillis() - initialTime.getTimeInMillis());
		float posY = availableTimeLineHeight * lOffsetY / (TimeUnit.HOURS.toMillis(24));
		posY = posY + lineStartY + paddingTop;
		
		return posY;
	}

	
	public String posYToTime(float $posY) {
		$posY = $posY - lineStartY - paddingTop;
		handleTime = new DateTime(Pie.getInst().baseTime);
		
		availableCanvasHeightforGraphs = lineEndY - paddingBottom - lineStartY - paddingTop;
		
		if ($posY < availableCanvasHeightforGraphs) {		
			initialTime = new DateTime(Pie.getInst().baseTime);
			initialTime.add(Calendar.HOUR_OF_DAY, -24);
			long allTimeMillis = TimeUnit.HOURS.toMillis(24);
	
			long initMillis = initialTime.getTimeInMillis();
			float yMillis = initMillis + ($posY * allTimeMillis / availableCanvasHeightforGraphs);
					
			handleTime.setTimeInMillis(Pie.getInst().baseTime, (long) (yMillis));
		} 
		
		String kYTime = handleTime.getStringTime(SleepTightConstants.DateForamt);
		String kApiRequestTime = handleTime.getStringTime(SleepTightConstants.NetworkFormat);
		Pie.getInst().handleTime = handleTime; //kApiRequestTime;
		return kYTime;
	}
	
	private void calculateBarWidth (int numBin) {
	
		float barSpacing;
		float barWidth;
		availableCanvasWidthforGraphs = this.getWidth() - timeBarStartX;
		barSpacing = availableCanvasWidthforGraphs / numBin / (binToSpaceRatio + 1);
		barWidth = binToSpaceRatio * barSpacing;
		
		sleepBarWidth = barWidth;
		sleepBarSpacing = barSpacing;
	}
	
	private void drawTimeBar(Context context, Canvas c) {
		float yToBed = 0;
		float yToSleep = 0;
		float yWakeUp = 0;
		float yOutBed = 0;
		float xPosition;
		
		float timebarY = timetoPosY(Pie.getInst().baseTime, 1);
	
		DateTime dtToBed = new DateTime();
		DateTime dtToSleep = new DateTime();
		DateTime dtWakeUp = new DateTime();
		DateTime dtOutBed = new DateTime();
		DateTime dtDiaryDate = new DateTime();
		
		String strToBed;
		String strToSleep;
		String strWakeUp;
		String strOutBed;
		String strDiaryDate;
		
		calculateBarWidth(numBin);
		
		
		// Calculate currentTimeX
		DateTime now = new DateTime(Pie.getInst().graphEndDate);
//		currentTimeY = timetoPosY(Pie.getInst().baseTime);		
		
		if (Pie.getInst().sleepTrackUnitArray != null && Pie.getInst().sleepTrackUnitArray.size() > 0) {
			int numSleepTracks = Pie.getInst().sleepTrackUnitArray.size();
			for (int i = numSleepTracks - 1; i >= 0; i--) {
				SleepTrackUnit aUnit = Pie.getInst().sleepTrackUnitArray.get(i);
				strToBed = aUnit.inBedTime;
				strToSleep = aUnit.sleepTime;
				strWakeUp = aUnit.wakeUpTime;
				strOutBed = aUnit.outOfBedTime;
				strDiaryDate = aUnit.diaryDate;
				sleepQuality = aUnit.sleepQuality;
				
				dtToBed = new DateTime(strToBed);
				dtToSleep = new DateTime(strToSleep);
				dtWakeUp = new DateTime(strWakeUp);
				dtOutBed = new DateTime(strOutBed);
				dtDiaryDate = new DateTime(strDiaryDate);
				
				int dayDiff = now.diffInDays(dtDiaryDate);
				xPosition = timeBarStartX + (numBin - dayDiff) * (sleepBarWidth + sleepBarSpacing);
				idx2idx.put(new Integer(dayDiff), new Integer(i));
				
				yToBed = timetoPosY(dtToBed, dayDiff);
				yToSleep = timetoPosY(dtToSleep, dayDiff);
				yWakeUp = timetoPosY(dtWakeUp, dayDiff);
				yOutBed = timetoPosY(dtOutBed, dayDiff);
				
				// Gray Bar
				paint.setColor(context.getResources().getColor(R.color.MediumGray));
				c.drawRect(xPosition - 1, lineStartY, xPosition + sleepBarWidth, lineEndY, paint);

				
				// Sleep Latency
				int y = (int) (yToSleep - yToBed) / 2;
				for (int j = 0; j < y; j++) {
					paint.setColor(context.getResources().getColor(R.color.HotPink));
					c.drawLine(xPosition - 1, yToBed, xPosition + sleepBarWidth - 1, yToBed, paint);
					paint.setColor(context.getResources().getColor(R.color.White));
					c.drawLine(xPosition - 1, yToBed + 1, xPosition + sleepBarWidth - 1, yToBed + 1, paint);
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
				
				// CLEANUP // Log.w("SummaryCanvasView | drawTimeBar()", "yToSleep : " + yToSleep + "/" + yWakeUp);
				c.drawRect(xPosition - 1, yToSleep, xPosition + sleepBarWidth, yWakeUp, paint);
				
				int x = (int) (yOutBed - yWakeUp) / 2;
				for (int j = 0; j < x; j++)
				{
					paint.setColor(context.getResources().getColor(R.color.HotPink));
					c.drawLine(xPosition - 1, yWakeUp, xPosition + sleepBarWidth - 1, yWakeUp, paint);
					paint.setColor(context.getResources().getColor(R.color.White));
					c.drawLine(xPosition - 1, yWakeUp + 1, xPosition + sleepBarWidth - 1, yWakeUp + 1, paint);
					yWakeUp = yWakeUp + 2;
				}
			}
		}
	}
}
