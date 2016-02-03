package kr.mintech.sleep.tight.views;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.units.ActionUnit;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class TimeLineView extends FrameLayout {
	private static final int MAX_NUMICONS = 8;
	
	@SuppressWarnings("unused")
	private Context _context;
	private TimeLineCanvasView _canvasView;
	private ImageView[] _bottomIcons = new ImageView[MAX_NUMICONS];
	
	public Button _prevBtn, _nextBtn;
	
	public TimeLineView(Context $context) {
		super($context);
		_context = $context;
		init($context);
	}
	
	public TimeLineView(Context $context, AttributeSet $attrs) {
		super($context, $attrs);
		_context = $context;
		init($context);
	}
	
	public TimeLineView(Context $context, AttributeSet $attrs, int $defStyle) {
		super($context, $attrs, $defStyle);
		_context = $context;
		init($context);
	}
	
	private void init(Context $context) {
		LayoutInflater kInflate = LayoutInflater.from($context);
		
		View kView = kInflate.inflate(R.layout.view_time_line, null);
		
		_canvasView = (TimeLineCanvasView) kView.findViewById(R.id.canvas_time_line);
		_bottomIcons[0] = (ImageView) kView.findViewById(R.id.img_bottom_icon_1);
		_bottomIcons[1] = (ImageView) kView.findViewById(R.id.img_bottom_icon_2);
		_bottomIcons[2] = (ImageView) kView.findViewById(R.id.img_bottom_icon_3);
		_bottomIcons[3] = (ImageView) kView.findViewById(R.id.img_bottom_icon_4);
		_bottomIcons[4] = (ImageView) kView.findViewById(R.id.img_bottom_icon_5);
		_bottomIcons[5] = (ImageView) kView.findViewById(R.id.img_bottom_icon_6);
		_bottomIcons[6] = (ImageView) kView.findViewById(R.id.img_bottom_icon_7);
		_bottomIcons[7] = (ImageView) kView.findViewById(R.id.img_bottom_icon_8);
		
		_prevBtn = (Button) kView.findViewById(R.id.btn_time_line_prev);
		_nextBtn = (Button) kView.findViewById(R.id.btn_time_line_next);
		addView(kView);
	}
	
	//Icon Re-Draw (Sort)
	public void drawBottomIcon() {
		if (Pie.getInst().actionkUnit != null && Pie.getInst().actionkUnit.size() > 0) {
			for (int i = 0; i < Pie.getInst().actionkUnit.size(); i++) {
				ActionUnit kUnit = Pie.getInst().actionkUnit.get(i);
				setDefaultIcon(i, kUnit.name, kUnit.defaultType, kUnit.color);
			}
		}	
	}
 	
	private void setDefaultIcon(int $position, String $name, boolean $defaultType, String $color) {
		ImageView kImageView = _bottomIcons[$position];
		
		if ($defaultType) {
			if ($name.contains("Caffeine")) {
				kImageView.setImageResource(R.drawable.coffee);
			} else if ($name.contains("Meal")) {
				kImageView.setImageResource(R.drawable.meal);
			} else if ($name.contains("Medication")) {
				kImageView.setImageResource(R.drawable.med);
			} else if ($name.contains("Tobacco")) {
				kImageView.setImageResource(R.drawable.tobacco);
			} else if ($name.contains("Exercise")) {
				kImageView.setImageResource(R.drawable.exercise);
			} else if ($name.contains("Alcohol")) {
				kImageView.setImageResource(R.drawable.alcohol);
			}
		} else if ($color != null) {
			// CLEANUP // Log.w("shakej", "COLOR!!!:" + $color);
			kImageView.setBackgroundColor(Color.parseColor($color));
		}
	}
	
	//CanvasView Re-Draw
	public void invalidateCanvasView() {
		_canvasView.invalidate();
	}
}
