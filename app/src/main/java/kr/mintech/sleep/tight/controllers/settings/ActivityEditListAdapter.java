package kr.mintech.sleep.tight.controllers.settings;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
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

public class ActivityEditListAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context _context;
	private LayoutInflater _inflate;
	public ArrayList<ActionUnit> _itemList = new ArrayList<ActionUnit>();
	
	
	public ActivityEditListAdapter(Context $context) {
		_context = $context;
		_inflate = LayoutInflater.from($context);
		_itemList = new ArrayList<ActionUnit>();
	}
	
	@Override
	public int getCount() {
		return _itemList.size();
	}
	
	public void putItems(ArrayList<ActionUnit> $items) {
		_itemList.clear();
		_itemList.addAll($items);
		
		notifyDataSetChanged();
	}
		
	public void putItem(ActionUnit $item) {
		_itemList.add($item);
	}
	
	@Override
	public Object getItem(int $position) {
		return $position;
	}
	
	@Override
	public long getItemId(int $position) {
		return _itemList.get($position).id;
	}
	
	@Override
	public View getView(int $position, View $convertView, ViewGroup $parent) {
		
		$convertView = _inflate.inflate(R.layout.view_act_edit_list_item, null, false);
		
		TextView kActivity = (TextView) $convertView.findViewById(R.id.text_item);
		ImageView kImageView = (ImageView) $convertView.findViewById(R.id.image_item);
		
		kActivity.setText(_itemList.get($position).name);
		if (_itemList.get($position).name != null) {
			if (_itemList.get($position).name.contains("Caffeine")) {
				kImageView.setImageResource(R.drawable.coffee);
			}
			else if (_itemList.get($position).name.equals("Medication")) {
				kImageView.setImageResource(R.drawable.med);
			}
			else if (_itemList.get($position).name.equals("Meal")) {
				kImageView.setImageResource(R.drawable.meal);
			}
			else if (_itemList.get($position).name.contains("Alcohol")) {
				kImageView.setImageResource(R.drawable.alcohol);
			}
			else if (_itemList.get($position).name.equals("Exercise")) {
				kImageView.setImageResource(R.drawable.exercise);
			}
			else if (_itemList.get($position).name.contains("Tobacco")) {
				kImageView.setImageResource(R.drawable.tobacco);
			}
			else {
//				kImageView.setBackgroundColor(Color.parseColor(_itemList.get($position).color)); // To-do: decrease the size of the rectangle
				
				Paint rectPaint = new Paint();
				rectPaint.setColor(Color.parseColor(_itemList.get($position).color));
				Rect rect = new Rect(0, 0, 19, 19);
				
				Bitmap.Config conf = Bitmap.Config.ARGB_8888;
				Bitmap bmp = Bitmap.createBitmap(rect.width(), rect.height(), conf);
				
				Canvas canvas = new Canvas (bmp);
				canvas.drawRect(rect, rectPaint);
				
				kImageView.setImageBitmap(bmp);
			}
		}
		return $convertView;
	}
}
