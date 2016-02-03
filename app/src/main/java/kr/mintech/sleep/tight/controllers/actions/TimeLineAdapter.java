package kr.mintech.sleep.tight.controllers.actions;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.StringConst;
import kr.mintech.sleep.tight.test.TestTrunk;
import kr.mintech.sleep.tight.utils.DateToPositionUtil;
import kr.mintech.sleep.tight.utils.Pie;
import Util.CalendarUtil;
import Util.Logg;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeLineAdapter extends BaseAdapter
{
	private Context _context;
	private LayoutInflater _inflate;
	private ArrayList<String> _itemList = new ArrayList<String>();
	
	
	public TimeLineAdapter(Context $context)
	{
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_itemList = new ArrayList<String>(TestTrunk.getInst().numbers.length);
		for (String s : TestTrunk.getInst().numbers)
		{
			_itemList.add(s);
		}
	}
	
	
	@Override
	public int getCount()
	{
		return _itemList.size();
	}
	
	
	@Override
	public Object getItem(int $position)
	{
		return $position;
	}
	
	
	@Override
	public long getItemId(int $position)
	{
		return $position;
	}
	
	
	@Override
	public View getView(int $position, View $convertView, ViewGroup $parent)
	{
		TimeLineViewHolder kViewHolder;
		kViewHolder = new TimeLineViewHolder();
		$convertView = _inflate.inflate(R.layout.view_time_list_list_item, null, false);
		
		kViewHolder.time = (TextView) $convertView.findViewById(R.id.text_time);
		kViewHolder.timeItemLayout = (LinearLayout) $convertView.findViewById(R.id.layout_time_line_item);
		
		kViewHolder.caffeineView = (View) $convertView.findViewById(R.id.color_code_caffein);
		kViewHolder.mealView = (View) $convertView.findViewById(R.id.color_code_meal);
		kViewHolder.medicationView = (View) $convertView.findViewById(R.id.color_code_medication);
		kViewHolder.alcoholView = (View) $convertView.findViewById(R.id.color_code_alchohol);
		kViewHolder.exerciseView = (View) $convertView.findViewById(R.id.color_code_excercise);
		kViewHolder.tobaccoView = (View) $convertView.findViewById(R.id.color_code_tobacco);
		kViewHolder.dividerView = (View) $convertView.findViewById(R.id.divider_view);
		
		String kCurrentTime = CalendarUtil.getCurrentTime();
		int kNowPosition = DateToPositionUtil.TimeToPosition(kCurrentTime);
		
		kViewHolder.time.setText(_itemList.get($position));
		
		if ($position == kNowPosition)
		{
			kViewHolder.timeItemLayout.setBackgroundColor(_context.getResources().getColor(R.color.time_line_current_time));
		}
		
		if (Pie.getInst().activity != null)
		{
			for (int i = DateToPositionUtil.TimeToPosition(Pie.getInst().startTime); i < DateToPositionUtil.TimeToPosition(Pie.getInst().endTime); i++)
			{
				if ($position == i)
				{
					Logg.w("TimeLineAdapter | getView()", "Set ColorChangePosition : " + $position);
					if (Pie.getInst().activity.equals(StringConst.ACTIVITY_CAFFEINE))
					{
						kViewHolder.caffeineView.setVisibility(View.VISIBLE);
					}
					else if (Pie.getInst().activity.equals(StringConst.ACTIVITY_MEAL))
					{
						kViewHolder.mealView.setVisibility(View.VISIBLE);
					}
					else if (Pie.getInst().activity.equals(StringConst.ACTIVITY_MEDICATION))
					{
						kViewHolder.medicationView.setVisibility(View.VISIBLE);
					}
					else if (Pie.getInst().activity.equals(StringConst.ACTIVITY_ALCOHOL))
					{
						kViewHolder.alcoholView.setVisibility(View.VISIBLE);
					}
					else if (Pie.getInst().activity.equals(StringConst.ACTIVITY_EXERCISE))
					{
						kViewHolder.exerciseView.setVisibility(View.VISIBLE);
					}
					else if (Pie.getInst().activity.equals(StringConst.ACTIVITY_TOBACCO))
					{
						kViewHolder.tobaccoView.setVisibility(View.VISIBLE);
					}
				}
			}
		}
		
		if ($position % 4 == 0)
		{
			kViewHolder.dividerView.setVisibility(View.VISIBLE);
		}
		
		kViewHolder.time.setVisibility(_itemList.get($position).contains("A") || _itemList.get($position).contains("P") ? View.VISIBLE : View.INVISIBLE);
		
		return $convertView;
	}
}
