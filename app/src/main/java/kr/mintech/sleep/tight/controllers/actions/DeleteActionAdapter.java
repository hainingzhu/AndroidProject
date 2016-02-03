package kr.mintech.sleep.tight.controllers.actions;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.units.ActionUnit;
import Util.Logg;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeleteActionAdapter extends BaseAdapter
{
	@SuppressWarnings("unused")
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<ActionUnit> _tracks = new ArrayList<ActionUnit>();
	
	
	public DeleteActionAdapter(Context $context)
	{
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_tracks = new ArrayList<ActionUnit>();
	}
	
	
	public void putItems(ArrayList<ActionUnit> $items)
	{
		_tracks.clear();
		_tracks.addAll($items);
		
		notifyDataSetChanged();
	}
	
	
	public void putItem(ActionUnit $item)
	{
		_tracks.add($item);
	}
	
	
	@Override
	public int getCount()
	{
		return _tracks.size();
	}
	
	
	@Override
	public Object getItem(int $position)
	{
		return $position;
	}
	
	
	@Override
	public long getItemId(int $position)
	{
		return _tracks.get($position).id;
	}
	
	
	@Override
	public View getView(int $position, View $convertView, ViewGroup $parent)
	{
		$convertView = _inflate.inflate(R.layout.view_delete_action_item_list, null, false);
		
		TextView kName = (TextView) $convertView.findViewById(R.id.text_item);
		
		Logg.w("DeleteActionAdapter | getView()", " : " + _tracks.get($position).name);
		kName.setText(_tracks.get($position).name);
		kName.setTextColor(Color.parseColor(_tracks.get($position).color));
		return $convertView;
	}
}
