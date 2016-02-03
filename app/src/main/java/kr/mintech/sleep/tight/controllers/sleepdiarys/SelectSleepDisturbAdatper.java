package kr.mintech.sleep.tight.controllers.sleepdiarys;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.units.EditSleepDisturbUnit;
import kr.mintech.sleep.tight.utils.Pie;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectSleepDisturbAdatper extends BaseAdapter
{
	@SuppressWarnings("unused")
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<EditSleepDisturbUnit> _itemList = new ArrayList<EditSleepDisturbUnit>();
	public ArrayList<String> checkedList;
	public ArrayList<String> checkedIdList;
	
	public SelectSleepDisturbAdatper(Context $context)
	{
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_itemList = new ArrayList<EditSleepDisturbUnit>();
	}
	
	
	@Override
	public int getCount()
	{
		return _itemList.size();
	}
	
	
	public void putItems(ArrayList<EditSleepDisturbUnit> $items)
	{
		_itemList.clear();
		_itemList.addAll($items);
		checkedList = (ArrayList<String>) Pie.getInst().sleepDisturbArr.clone();// new ArrayList<String>();
		checkedIdList = (ArrayList<String>) Pie.getInst().sleepDisturbIdArr.clone();// new ArrayList<String>();
		notifyDataSetChanged();
	}
	
	
	public void putItem(EditSleepDisturbUnit $item)
	{
		_itemList.add($item);
	}
	
	
	@Override
	public Object getItem(int $position)
	{
		return _itemList.get($position);
	}
	
	
	@Override
	public long getItemId(int $position)
	{
		return _itemList.get($position).id;
	}
	
	
	@Override
	public View getView(final int $position, View $convertView, ViewGroup $parent)
	{
		$convertView = _inflate.inflate(R.layout.view_select_before_bed_act_list_item, null, false);
		
		CheckBox kName = (CheckBox) $convertView.findViewById(R.id.chk_item);
		kName.setText(_itemList.get($position).name);
		
		if (checkedList != null)
		{
			for (int i = 0; i < checkedList.size(); i++)
			{
				if (checkedList.get(i).contains(_itemList.get($position).name))
				{
					kName.setChecked(true);
				}
			}
		}
		
		kName.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				String strText = _itemList.get($position).name;
				if (isChecked)
				{
					checkedList.add(strText);
					checkedIdList.add(Integer.toString(_itemList.get($position).id));
				}
				else
				{
					checkedList.remove(_itemList.get($position).name);
					checkedIdList.remove(Integer.toString(_itemList.get($position).id));
				}
			}
		});
		return $convertView;
	}
}
