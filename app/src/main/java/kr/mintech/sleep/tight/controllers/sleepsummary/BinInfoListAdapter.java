package kr.mintech.sleep.tight.controllers.sleepsummary;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.units.BinInfoLastActivityUnit;
import kr.mintech.sleep.tight.utils.DateTime;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BinInfoListAdapter extends BaseAdapter
{
	@SuppressWarnings("unused")
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<BinInfoLastActivityUnit> _itemList = new ArrayList<BinInfoLastActivityUnit>();
	
	
	public BinInfoListAdapter(Context $context)
	{
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_itemList = new ArrayList<BinInfoLastActivityUnit>();
	}
	
	
	@Override
	public int getCount()
	{
		return _itemList.size();
	}
	
	
	public void putItems(ArrayList<BinInfoLastActivityUnit> $items)
	{
		_itemList.clear();
		_itemList.addAll($items);
		
		notifyDataSetChanged();
	}
	
	
	public void putItem(BinInfoLastActivityUnit $item)
	{
		_itemList.add($item);
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
		
		$convertView = _inflate.inflate(R.layout.view_sleep_summary_bin_act_list_item, null, false);
		
		TextView kTitle = (TextView) $convertView.findViewById(R.id.title_activity);
		TextView kLastActivity = (TextView) $convertView.findViewById(R.id.text_last_activity);
		
		kTitle.setText(_itemList.get($position).name);
		if (_itemList.get($position).endTime == null)
		{
			kLastActivity.setText(_itemList.get($position).content);
		}
		else
		{
			if (_itemList.get($position).endTime != null)
			{
				try
				{
					DateTime kLastTime = new DateTime(_itemList.get($position).endTime);
					String kLastTimeStr = kLastTime.getStringTime(SleepTightConstants.AMPM_TimeFormat);
					int count = _itemList.get($position).count;
					String kCount = Integer.toString(count) + " time";
					if (count != 1) {
						kCount = kCount + "s";
					}
					kLastActivity.setText(kCount + ", last activity at " + kLastTimeStr);
				} catch (Exception e)
				{
					int count = _itemList.get($position).count;
					String kCount = Integer.toString(count) + " time";
					if (count != 1) {
						kCount = kCount + "s";
					}
					kLastActivity.setText(kCount + ",\nLast activity " + _itemList.get($position).endTime);
				}
			}
		}
		return $convertView;
	}
}
