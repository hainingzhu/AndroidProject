package kr.mintech.sleep.tight.controllers.timeline;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.units.ActivityTrackUnit;
import kr.mintech.sleep.tight.utils.DateTime;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EditTrackAdapter extends BaseAdapter
{
	@SuppressWarnings("unused")
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<ActivityTrackUnit> _tracks = new ArrayList<ActivityTrackUnit>();
	
	
	public EditTrackAdapter(Context $context)
	{
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_tracks = new ArrayList<ActivityTrackUnit>();
	}
	
	
	public void putItems(ArrayList<ActivityTrackUnit> $items)
	{
		_tracks.clear();
		_tracks.addAll($items);
		
		notifyDataSetChanged();
	}
	
	
	public void putItem(ActivityTrackUnit $item)
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
		return _tracks.get($position);
	}
	
	
	@Override
	public long getItemId(int $position)
	{
		return _tracks.get($position).id;
	}
	
	
	@Override
	public View getView(int $position, View $convertView, ViewGroup $parent)
	{
		EditTrackViewHolder kViewHolder;
		kViewHolder = new EditTrackViewHolder();
		
		if ($convertView == null)
		{
			$convertView = _inflate.inflate(R.layout.view_edit_track_list_item, null, false);
			kViewHolder.trackName = (TextView) $convertView.findViewById(R.id.text_track_name);
			kViewHolder.trackTime = (TextView) $convertView.findViewById(R.id.text_track_time);
			$convertView.setTag(kViewHolder);
		}
		else
		{
			kViewHolder = (EditTrackViewHolder) $convertView.getTag();
		}
		
		kViewHolder.trackName.setText(_tracks.get($position).activityName);
		if (_tracks.get($position).recordType.equals("duration"))
		{
			DateTime kTime = new DateTime(_tracks.get($position).actionStartedAt);
			String kStartAtStr = kTime.getStringTime(SleepTightConstants.DateTimeFormat);
			DateTime kEndTime = new DateTime(_tracks.get($position).actionEndedAt);
			String kEndAtStr = kEndTime.getStringTime(SleepTightConstants.DateTimeFormat);
			kViewHolder.trackTime.setText(kStartAtStr + "~" + kEndAtStr);
		}
		else
		{
			DateTime kTime = new DateTime(_tracks.get($position).actionStartedAt);
			String kStartAtStr = kTime.getStringTime(SleepTightConstants.DateTimeFormat);
			kViewHolder.trackTime.setText(kStartAtStr);
		}
		
		return $convertView;
	}
}
