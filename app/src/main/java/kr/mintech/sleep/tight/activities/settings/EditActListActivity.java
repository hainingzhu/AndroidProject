package kr.mintech.sleep.tight.activities.settings;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.popups.AddActionActivity;
import kr.mintech.sleep.tight.activities.popups.DeleteActionActivity;
import kr.mintech.sleep.tight.bases.TopActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.StringConst;
import kr.mintech.sleep.tight.controllers.actions.ActionController;
import kr.mintech.sleep.tight.controllers.actions.ActionItemListAdapter;
import kr.mintech.sleep.tight.controllers.settings.ActivityEditListAdapter;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.units.ActionUnit;
import kr.mintech.sleep.tight.utils.DragAndDropListView;
import Util.Logg;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.Toast;

public class EditActListActivity extends TopActivity implements DragAndDropListView.DragListener, DragAndDropListView.DropListener {
	private DragAndDropListView _activityList;
	private ActionController _actionController;
	private ActivityEditListAdapter _activityAdapter;
	private boolean _isDrag = false;
	
	private Button _btnSave;
	private Button _btnDelete;
	private Button _btnAdd;
	
	private ArrayList<String> _sortArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		putBodyView(R.layout.act_setting_edit_act);
		
		_sortArray = new ArrayList<String>();
		_btnSave = (Button) findViewById(R.id.btn_save);
		_btnDelete = (Button) findViewById(R.id.btn_delete);
		_btnAdd = (Button) findViewById(R.id.btn_add);
		
		_btnSave.setOnClickListener(endClcikListener);
		_btnDelete.setOnClickListener(deleteBtnClickListener);
		_btnAdd.setOnClickListener(addBtnClickListener);
		
		_actionController = new ActionController(onRequestEndListener);
		_activityAdapter = new ActivityEditListAdapter(this);
		_activityList = (DragAndDropListView) findViewById(R.id.listview_activity);
		_activityList.setDragListener(this);
		_activityList.setDropListener(this);
		
		_activityList.setOnItemLongClickListener(longClickListener);
		_activityList.setAdapter(_activityAdapter);
		_activityList.setOnItemClickListener(itemClickListener);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadActions();
	}
	
	private void loadActions() {
		startProgress();
		if (_actionController._actionUnits.size() > 0) {
			_actionController._actionUnits.clear();
		}
		_actionController.requestActions();
	}
	
	private void addActions() {
		startProgress();
		_activityAdapter.putItems(_actionController._actionUnits);
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long $id) {
			Log.w("EditActListActivity | enclosing_method()", "Click : ");
		}
	};
	
	private OnItemLongClickListener longClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long $id) {
			Log.w("EditActListActivity | enclosing_method()", "LongClick : ");
			_actionController.requestRemoveActions((int) $id);
			return false;
		}
	};
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestSuccess:
					Logg.w("AddActivityView | enclosing_method()", "Events sorting: success!");
					Toast.makeText(EditActListActivity.this, "Events sorting: success!", Toast.LENGTH_SHORT).show();
					stopProgress();
					finish();
					break;
				case NumberConst.requestEndActions:
					Logg.w("AddActivityView | enclosing_method()", "Activity sucessfully loaded");
					stopProgress();
					addActions();
					break;
				
				case NumberConst.requestEndRemoveAction:
					loadActions();
					break;
				
				case NumberConst.requestFail:
					Logg.w("AddActivityView | enclosing_method()", "Network Failed");
					Toast.makeText(EditActListActivity.this, "Network status is bad", Toast.LENGTH_SHORT).show();
					break;
			}
		}
		
		@Override
		public void onRequest(Object $unit) {
		}
		
		@Override
		public void onRequestError(int $tag, String $errorStr) {
		}
	};
	
	public void drag(int from, int to) {
		if (!_isDrag) {
			_isDrag = true;
			Log.i("Drag and Drop : drag", "from : " + from + ", to : " + to);
		}
	}
	
	public void drop(int from, int to) {
		if (_isDrag) {
			Log.i("Drag and Drop : drop", "from : " + from + ", to : " + to);
			if (from == to)
				return;
			
			ActionUnit item = _activityAdapter._itemList.remove(from);
			_activityAdapter._itemList.add(to, item);
			
			_isDrag = false;
			_activityAdapter.notifyDataSetChanged();
		}
	}
	
	/*
	 * Listener
	 */
	private OnClickListener endClcikListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (_sortArray.size() > 0) {
				_sortArray.clear();
			}
			
			for (int i = 0; i < _activityAdapter._itemList.size(); i++) {
				_sortArray.add(Integer.toString(_activityAdapter._itemList.get(i).id));
			}
			
			Log.w("EditActListActivity | enclosing_method()", "_sortArray : " + _sortArray);
			
			_actionController.requestManageSortActions(_sortArray);			
		}
	};
	
	private OnClickListener deleteBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent kIntent = new Intent(EditActListActivity.this, DeleteActionActivity.class);
			startActivity(kIntent);
		}
	};
	
	private OnClickListener addBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (_activityAdapter.getCount() > 7) {
				Toast.makeText(EditActListActivity.this, "Cannot add more than 8 activities.", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Intent kIntent = new Intent(EditActListActivity.this, AddActionActivity.class);
			startActivity(kIntent);
		}
	};
}
