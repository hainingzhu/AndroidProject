package kr.mintech.sleep.tight.controllers.settings;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.units.EditSleepDisturbUnit;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EditSleepDisturbAdapter extends BaseAdapter
{
	@SuppressWarnings("unused")
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<EditSleepDisturbUnit> _itemList = new ArrayList<EditSleepDisturbUnit>();
	
	
	public EditSleepDisturbAdapter(Context $context)
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
		
		notifyDataSetChanged();
	}
	
	
	public void putItem(EditSleepDisturbUnit $item)
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
		return _itemList.get($position).id;
	}
	
	
	@Override
	public View getView(int $position, View $convertView, ViewGroup $parent) {
		
		$convertView = _inflate.inflate(R.layout.view_disturb_list_item, null, false);
		
		TextView kName = (TextView) $convertView.findViewById(R.id.text_name);
		kName.setText(_itemList.get($position).name);
		return $convertView;
	}
	
}
