package kr.mintech.sleep.tight.views;

import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.Local_db.activities;
import kr.mintech.sleep.tight.activities.Local_db.activity_tracks;
import kr.mintech.sleep.tight.activities.Local_db.dbHelper_local;
import kr.mintech.sleep.tight.activities.popups.AddDurationAcitivity;
import kr.mintech.sleep.tight.activities.popups.DeleteTracksActivity;
import kr.mintech.sleep.tight.activities.sleepdiarys.AddSleepDiaryActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.actions.ActionController;
import kr.mintech.sleep.tight.controllers.actions.ActionItemListAdapter;
import kr.mintech.sleep.tight.controllers.timeline.TimeLineController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.units.ActionUnit;
import kr.mintech.sleep.tight.units.SleepTrackUnit;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.utils.Util;
import Util.ContextUtil;
import Util.Logg;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class AddActivityView extends Fragment
{
	private ListView _actionListView;
	
	private Context _context;
	private Button _deleteEventBtn;
	private ImageButton _addSleepBtn;
	private TimeLineView _timeLineView;
	
	private ActionItemListAdapter _actionListAdapter;
	
	//Controller -> Network 
	private TimeLineController _controller;
	private ActionController _actionController;
	
	private ProgressBar _progressBar;
	
	//Default value 
	public static int agoDay = 0;
	
	private TextView _textDay;
	private TextView _goCurrentBtn;
	
	private int _activityID = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		_context = ContextUtil.CONTEXT;
		// CLEANUP // Logg.w("AddActivityView | onCreateView()", " onCreateView: ");
		
		View rootView = inflater.inflate(R.layout.view_add_activity, container, false);
		
		_timeLineView = (TimeLineView) rootView.findViewById(R.id.view_time_line);
		_progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
		
		_actionListView = (ListView) rootView.findViewById(R.id.list_did_item);
		_actionListView.setOnItemClickListener(actionItemClickListener);
		_actionListView.setOnItemLongClickListener(actionItemLongClickListener);
		
		_deleteEventBtn = (Button) rootView.findViewById(R.id.btn_delete);
		_deleteEventBtn.setOnClickListener(deleteEventClickListener);
		
		_addSleepBtn = (ImageButton) rootView.findViewById(R.id.btn_add_sleep);
		_addSleepBtn.setOnClickListener(addSleepClickListener);
		
		_textDay = (TextView) rootView.findViewById(R.id.text_day);
		
		_goCurrentBtn = (Button) rootView.findViewById(R.id.btn_current);
		_goCurrentBtn.setOnClickListener(goCurrentClickListener);
		
		_controller = new TimeLineController(onRequestEndListener);
		_actionController = new ActionController(onRequestEndListener);
		
		_actionListAdapter = new ActionItemListAdapter(_context);
		_actionListView.setAdapter(_actionListAdapter);
		
		_timeLineView._prevBtn.setOnClickListener(daySelectClickListener);
		_timeLineView._nextBtn.setOnClickListener(daySelectClickListener);
		if (agoDay == 0) {
			_timeLineView._nextBtn.setVisibility(View.INVISIBLE);
		}
		
		Pie pie = Pie.getInst();
		if (pie.baseTime == null) {
			pie.baseTime = new DateTime();
		}
		
		loadActivityTracks();
		
		return rootView;
	}
	
	private void updateSleepButton() {
		_addSleepBtn.setVisibility(View.GONE);	
		int count = Pie.getInst().sleepTrackUnit.size();
		
		DateTime yesterday = new DateTime(0, 0);
		yesterday.add(Calendar.DATE, -1);

		if (agoDay == 0) {
			_addSleepBtn.setVisibility(View.VISIBLE);
			if (count > 0) {
				SleepTrackUnit sleepTrack = Pie.getInst().sleepTrackUnit.get(count - 1);
				DateTime diaryDate = new DateTime(sleepTrack.diaryDate);
				if (diaryDate.compareTo(yesterday) == 0) {
					_addSleepBtn.setVisibility(View.GONE);
				}
			}
		}
	}

	@Override
	public void onResume() {
		Pie pie = Pie.getInst();
		if (pie.isRefreshing == false) {
			TimeLineCanvasView._firstLoad = true;
			pie.baseTime = new DateTime();
		}
		pie.isRefreshing = false;
		
		_timeLineView.invalidateCanvasView();

		if (pie.goToCurrentTimeAfterLoad) {
			pie.goToCurrentTimeAfterLoad = false;
			goToCurrentTime();
		}
		
		super.onResume();
	}
	
	@Override
	public void onDestroy()	{
		super.onDestroy();
		Pie.getInst().baseTime = null;
	}
	
	
	private void loadActivityTracks() {
		loadActivityTracks(true);
	}
	
	private void loadActivityTracks(boolean showProgressBar) {
		if (showProgressBar) {
			_progressBar.setVisibility(View.VISIBLE);
		}
			
		_controller.clear();
		
		DateTime baseTime = new DateTime(Pie.getInst().baseTime);
		baseTime.add(Calendar.MINUTE, 2);
		String kCurrentDateTimeStr = baseTime.getStringTime(SleepTightConstants.NetworkFormat);
				
		// CLEANUP // Logg.w("AddActivityView | loadActivityTracks()", "Base Time : " + kCurrentDateTimeStr);
		_controller.requestActivityTracks(kCurrentDateTimeStr, NumberConst.ACTIVITYTRACK_REQUEST_DURATION);
	}
	
	private void loadSleepTracks() {
		String kCurrentDateTimeStr = Pie.getInst().baseTime.getStringTime(SleepTightConstants.NetworkFormat);
		_controller.requestSleepTracks(kCurrentDateTimeStr, NumberConst.SLEEPTRACK_REQUEST_DURATION);
	}
	
	private void loadActions() {
		if (_actionController._actionUnits.size() > 0) {
			_actionController._actionUnits.clear();
		}
		_actionController.requestActions();
	}
	
	//Actions Add Item 
	private void addActions() {
		_actionListAdapter.putItems(_actionController._actionUnits);
	}
	
	/*
	 * Button Click
	 * Listener
	 */
	private OnItemClickListener actionItemClickListener = new OnItemClickListener()	{
		public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long $actionId) {	// When the user clicked an item 
			Pie pie = Pie.getInst();
			String strHandleTime = pie.handleTime.getStringTime(SleepTightConstants.NetworkFormat);
			// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Handle Time: " + strHandleTime + " Action Id " + $actionId);

            // add activity into the Activity table
            String actName = _actionListAdapter.getItemName(pos);
            insertInto_ActivityDB((int)($actionId), actName, strHandleTime);
            Log.w("WHJ", "record activity in local DB");

			_controller.reqeustAddActivityTrack((int) $actionId, strHandleTime, null);

			EventLogger.log("add_activity",
                    "where", "app",
                    "type", "frequency",
                    "activityId", $actionId);
		}
	};


    private void insertInto_ActivityDB(int actionID, String actName, String startT)
    {
        Log.w("WHJ", String.format("insert activity %s with ID %d", actName, actionID));
        int uid = dbHelper_local.curUserID(this._context);
        activities a = new activities(uid, actionID, actName, 0);

        Log.w("WHJ", String.format("User %d with activity %s is inserted", uid, a.activity_name));
        dbHelper_local.insertActivities(a, this._context);
    }

    /**
     * insert data into local database, Table activity tract
     *
     * Input:
     *
     * @param actionID
     * @param actName
     *
     * Table:
     * id - autoincrement
     * activity_id - actionID
     * user_id - in the function. "dbHelper_local.curUserID(this._context)"
     * record_type - None
     * actionStartedAt - startT
     * actionEndedAt - None
     * createTime - new SimpleDateFormat("dd-MM-yyyy").format(new Date());
     * activityName - actName
     * tractType -
     *
     */

    private void insertInto_ActivityTrackDB(int actionID, String actionStartedAt, String actionEndedAt, String create_time, String actName)
    {
        int uid = dbHelper_local.curUserID(this._context);

        activity_tracks at = new activity_tracks(actionID, uid, actionStartedAt, actionEndedAt, create_time, actName);


        Log.w("zzz", String.format("User %d with activity id %d activity name %s is inserted, action start at %s end at %s creat time is %s."
                , uid, actionID, at.activityName, at.actionStartedAt, at.actionEndedAt, at.create_time));
        dbHelper_local.insertActivityTracks(at, this._context);
    }




	
	private OnItemLongClickListener actionItemLongClickListener = new OnItemLongClickListener()	{
		@Override
		public boolean onItemLongClick(AdapterView<?> adapterView, View clickedView, int $position, long $actionId)	{
			_activityID = (int) $actionId;
			Intent kIntent = new Intent(_context, AddDurationAcitivity.class);
			kIntent.putExtra("is_new_add", true);
			kIntent.putExtra("activity_id", (int) $actionId);
			kIntent.putExtra("start_time", Pie.getInst().handleTime.getStringTime(SleepTightConstants.NetworkFormat));
			startActivityForResult(kIntent, 0);
			
			return true;
		}		
	};
		
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
			EventLogger.log("add_activity", 
					"where", "app",
					"type", "duration",
					"activityId", _activityID);				
		}
	}

	private OnClickListener daySelectClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_time_line_prev:
					_timeLineView._nextBtn.setVisibility(View.VISIBLE);
					
					agoDay -= 1;
					Pie.getInst().baseTime.add(Calendar.DATE, -1);
					Pie.getInst().handleTime.add(Calendar.DATE, -1);
					TimeLineCanvasView._firstLoad = true;			
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "kPrevTime : " + Pie.getInst().baseTime.getStringTime(SleepTightConstants.DateForamt));
					
					EventLogger.log("view_timeline",
							"direction", "previous",
							"distance", agoDay);
					
					Intent intent = new Intent();
					intent.setAction("kr.mintech.main.refresh");
					_context.sendBroadcast(intent);
					break;
					
				case R.id.btn_time_line_next:
					if (agoDay == 0) {
						Toast.makeText(_context, "This is the last page", Toast.LENGTH_SHORT).show();
					} else {
						if (agoDay == 1) {
							_timeLineView._nextBtn.setVisibility(View.INVISIBLE);
						}

						agoDay += 1;
						Pie.getInst().baseTime.add(Calendar.DATE, 1);
						Pie.getInst().handleTime.add(Calendar.DATE, 1);
						TimeLineCanvasView._firstLoad = true;					
						// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "kNextTime : " + Pie.getInst().baseTime.getStringTime(SleepTightConstants.DateForamt));

						EventLogger.log("view_timeline",
								"direction", "next",
								"distance", agoDay);

						Intent intentNext = new Intent();
						intentNext.setAction("kr.mintech.main.refresh");
						_context.sendBroadcast(intentNext);
					}
					break;
					
				default:
					break;
			}
		}
	};
	
	private OnClickListener deleteEventClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			DeleteTracksActivity._loadBaseTime = Pie.getInst().handleTime;
			Intent kIntent = new Intent(_context, DeleteTracksActivity.class);
			startActivity(kIntent);
		}
	};
	
	private OnClickListener addSleepClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent_AddSleep = new Intent(_context, AddSleepDiaryActivity.class);
			Pie.getInst().widgetLocation = "App";
			startActivity(intent_AddSleep);
		}
	};
	
	private OnClickListener goCurrentClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			DateTime kCurrentTime = new DateTime();
			Pie pie = Pie.getInst();
			if (!kCurrentTime.equals(pie.handleTime)) {
				goToCurrentTime();
			}
		}
	};
	
	/**
	 * Reset the view to the current time. Sets the
	 * baseTime to the current time and redraws the
	 * view. Also causes the handleTime to be reset
	 * to baseTime on redraw so that the handle goes
	 * to the end of the day.
	 */
	public void goToCurrentTime() {
		agoDay = 0;
		_timeLineView._nextBtn.setVisibility(View.INVISIBLE);

		DateTime kCurrentTime = new DateTime();
		Pie.getInst().baseTime = kCurrentTime;
		TimeLineCanvasView._firstLoad = true;	//make sure the handle position is taken from the current base time on first draw.
		// CLEANUP // Logg.w("AddActivityView.goCurrentClickListener.new OnClickListener() {...} | onClick()", "Current Time : " + pie.baseTime.getStringTime(SleepTightConstants.DateForamt));
		
		loadActivityTracks();
	}
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener()	{
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndActivityTracks:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Activity Track load: success!");
					_actionListAdapter.notifyDataSetChanged();
					loadSleepTracks();
					break;
					
				case NumberConst.requestEndSleepTracks:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Sleep Track load: success!");
					String kCurrentDayStr;
					if (Pie.getInst().baseTime.get(Calendar.DAY_OF_MONTH) == 1) {
						kCurrentDayStr = Pie.getInst().baseTime.getStringTime(SleepTightConstants.TitleDayFormat);
					} else {
						kCurrentDayStr = Pie.getInst().baseTime.getStringTime(SleepTightConstants.DayOnlyFormat);
					}
					DateTime kDateTime = new DateTime(Pie.getInst().baseTime);
					kDateTime.add(Calendar.DATE, -1);
					String kPrevDayStr = kDateTime.getStringTime(SleepTightConstants.TitleDayFormat);
					_textDay.setText(kPrevDayStr + "-" + kCurrentDayStr);
					
					_timeLineView.invalidateCanvasView();
					loadActions();
					updateSleepButton();
					break;
					
				case NumberConst.requestEndActions:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Actions load: success!");
					_progressBar.setVisibility(View.GONE);
					addActions();
					_timeLineView.drawBottomIcon();
					break;
					
				case NumberConst.requestSuccess:
					EventLogger.log("add_activity_result",
							"type", "frequency",
							"result", "success");
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Add: success!");
					//activity has been added successfully, update the widget and reload the activity tracks to reflect this
					_controller.updateWidget(_context);
					_timeLineView.invalidateCanvasView();
					loadActivityTracks(false);
					Toast.makeText(_context, "The activity has been successfully added.", Toast.LENGTH_LONG).show();
					break;
					
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Network Failure");
					Toast.makeText(_context, "Network status is bad", Toast.LENGTH_SHORT).show();
					break;
			}
		}
		
		
		@Override
		public void onRequest(Object $unit)	{
		}
				
		@Override
		public void onRequestError(int $tag, String $errorStr) {
		}
	};
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		
		if (this.isVisible()) {
			if (isVisibleToUser) {
				_timeLineView.invalidateCanvasView();
			}
		}
	}
}