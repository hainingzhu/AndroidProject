package kr.mintech.sleep.tight.controllers.sleepdiarys;

import java.util.ArrayList;

import org.json.simple.ItemList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.units.BeforeBedActUnit;
import kr.mintech.sleep.tight.utils.Pie;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SelectBeforeBedActAdatper extends BaseAdapter
{
	@SuppressWarnings("unused")
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<BeforeBedActUnit> _itemList = new ArrayList<BeforeBedActUnit>();
	public ArrayList<String> checkedList;
	public ArrayList<String> checkedIDList;
	
	
	public SelectBeforeBedActAdatper(Context $context)
	{
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_itemList = new ArrayList<BeforeBedActUnit>();
		//checkedList = new ArrayList<String>();
		//checkedIDList = new ArrayList<String>();
	}
	
	
	@Override
	public int getCount()
	{
		return _itemList.size();
	}
	
	
	public void putItems(ArrayList<BeforeBedActUnit> $items)
	{
		_itemList.clear();
		_itemList.addAll($items);
		checkedList = (ArrayList<String>) Pie.getInst().beforeBedActArr.clone();
		checkedIDList = (ArrayList<String>) Pie.getInst().beforeBedActIdArr.clone();
		notifyDataSetChanged();
	}
	
	
	public void putItem(BeforeBedActUnit $item)
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
		BeforeBedActUnit ritual = (BeforeBedActUnit) _itemList.get($position);
		kName.setText(ritual.name);
		
		if (checkedList != null)
		{
			for (int i = 0; i < checkedList.size(); i++)
			{
				if (checkedList.get(i).contains(ritual.name))
				{
					kName.setChecked(true);
					//checkedList.add(ritual.name);
					//checkedIDList.add(Integer.toString(ritual.id));
				}
			}
		}
		
		kName.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				BeforeBedActUnit ritual = (BeforeBedActUnit) _itemList.get($position);
				if (isChecked)
				{
					checkedList.add(ritual.name);
					checkedIDList.add(Integer.toString(ritual.id));
				}
				else
				{
					checkedList.remove(_itemList.get($position).name);
					checkedIDList.remove(Integer.toString(ritual.id));
				}
			}
		});
		return $convertView;
	}
}
