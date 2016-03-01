package kr.mintech.sleep.tight.controllers.actions;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.StringConst;
import kr.mintech.sleep.tight.units.ActionUnit;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionItemListAdapter extends BaseAdapter {
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<ActionUnit> _actions = new ArrayList<ActionUnit>();
		
	public ActionItemListAdapter(Context $context) {
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_actions = new ArrayList<ActionUnit>();
	}
	
	public void putItems(ArrayList<ActionUnit> $items) {
		_actions.clear();
		_actions.addAll($items);
		notifyDataSetChanged();
	}
		
	public void putItem(ActionUnit $item) {
		_actions.add($item);
	}
		
	@Override
	public int getCount() {
		return _actions.size();
	}
		
	@Override
	public Object getItem(int $position) {
		return $position;
	}

	public String getItemName(int pos) { return _actions.get(pos).name; }
		
	@Override
	public long getItemId(int $position) {
		return _actions.get($position).id;
	}
		
	@Override
	public View getView(int $position, View $convertView, ViewGroup $parent) {
		ActionItemViewHolder kViewHolder;
		kViewHolder = new ActionItemViewHolder();
		
		if ($convertView == null) {
			$convertView = _inflate.inflate(R.layout.view_action_list_item, null, false);
			
			kViewHolder.activity = (TextView) $convertView.findViewById(R.id.text_did_item);
			kViewHolder.activityImage = (ImageView) $convertView.findViewById(R.id.image_did_item);
			$convertView.setTag(kViewHolder);
		}
		else {
			kViewHolder = (ActionItemViewHolder) $convertView.getTag();
		}
		
		kViewHolder.activity.setText(_actions.get($position).name);
		if (_actions.get($position).name != null) {
			if (_actions.get($position).name.contains(StringConst.ACTIVITY_CAFFEINE)) {
				kViewHolder.activityImage.setImageResource(R.drawable.coffee);
			}
			else if (_actions.get($position).name.equals(StringConst.ACTIVITY_MEAL)) {
				kViewHolder.activityImage.setImageResource(R.drawable.meal);
			}
			else if (_actions.get($position).name.equals(StringConst.ACTIVITY_MEDICATION)) {
				kViewHolder.activityImage.setImageResource(R.drawable.med);
			}
			else if (_actions.get($position).name.equals(StringConst.ACTIVITY_ALCOHOL)) {
				kViewHolder.activityImage.setImageResource(R.drawable.alcohol);
			}
			else if (_actions.get($position).name.equals(StringConst.ACTIVITY_EXERCISE)) {
				kViewHolder.activityImage.setImageResource(R.drawable.exercise);
			}
			else if (_actions.get($position).name.equals(StringConst.ACTIVITY_TOBACCO)) {
				kViewHolder.activityImage.setImageResource(R.drawable.tobacco);
			}
			else {
				if (_actions.get($position).color != null) {
//					kViewHolder.activityImage.setBackgroundColor(Color.parseColor(_actions.get($position).color));
					
					Paint rectPaint = new Paint();
					rectPaint.setColor(Color.parseColor(_actions.get($position).color));
					Rect rect = new Rect(0, 0, 19, 19);
					
					Bitmap.Config conf = Bitmap.Config.ARGB_8888;
					Bitmap bmp = Bitmap.createBitmap(rect.width(), rect.height(), conf);
					
					Canvas canvas = new Canvas (bmp);
					canvas.drawRect(rect, rectPaint);
					
					kViewHolder.activityImage.setImageBitmap(bmp);
				}
			}
		}
		return $convertView;
	}
}