package kr.mintech.sleep.tight.activities.popups;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.controllers.timeline.EditTrackAdapter;
import kr.mintech.sleep.tight.controllers.timeline.TimeLineController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.units.ActivityTrackUnit;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.views.TimeLineCanvasView;
import Util.Logg;
import Util.PopupUtil;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteTracksActivity extends Activity {
	private ListView _listTracks;
	private Button _btnBack;
	
	private TimeLineController _timeLineController;
	private EditTrackAdapter _editTrackAdapter;
	public static DateTime _loadBaseTime;
	
	private ActivityTrackUnit _delActivityTrackUnit;
	private int _deletedId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_edit_track);
		
		_listTracks = (ListView) findViewById(R.id.list_tracks);
		_btnBack = (Button) findViewById(R.id.btn_back);
		_btnBack.setOnClickListener(backClickListener);
		
		_timeLineController = new TimeLineController(onRequestEndListener);
		_editTrackAdapter = new EditTrackAdapter(this);
		
		_listTracks.setOnItemClickListener(itemClickListener);
		_listTracks.setAdapter(_editTrackAdapter);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		loadActivityTracks();
	}
	
	@Override
	public void onBackPressed()	{
		super.onBackPressed();
		finish();
	}
	
	
	private void loadActivityTracks() {
		//hackishly load current set of visible activity tracks. Assume we're being called from 
		//something that has instantiated Pie and populated it with some activity tracks. 
		//(e.g. AddActivityView) and just use those.
		
//		_timeLineController.clear();
		DateTime handleDateTime = new DateTime(_loadBaseTime);
//		handleDateTime.add(Calendar.HOUR_OF_DAY, 1);
//		String kCurrentDateTimeStr = handleDateTime.getStringTime(SleepTightConstants.NetworkFormat);
//		Logg.w("AddActivityView | loadActivityTracks()", "Base Time : " + kCurrentDateTimeStr);
//		_timeLineController.requestActivityTracks(kCurrentDateTimeStr, 2);
		

		//define a range of +- 1 hour around the handle time
		DateTime handleDateTimeLower = new DateTime(handleDateTime);
		handleDateTimeLower.add(Calendar.HOUR, -1);
		DateTime handleDateTimeUpper = new DateTime(handleDateTime);
		handleDateTimeUpper.add(Calendar.HOUR, 1);

		ArrayList<ActivityTrackUnit> activityTracks = Pie.getInst().activityTrackUnit;
		//filter visible activity tracks into those "nearby" the handle (within the range defined above)
		ArrayList<ActivityTrackUnit> nearbyActivityTracks = new ArrayList<ActivityTrackUnit>();
		for (ActivityTrackUnit activityTrack : activityTracks) {
			if (DateTime.rangesIntersect(
					handleDateTimeLower, handleDateTimeUpper, 
					activityTrack.actionStartedAtDateTime, activityTrack.actionEndedAtDateTime)) {
				nearbyActivityTracks.add(activityTrack);
			}
		}
		
		_timeLineController._activityTrackUnits = nearbyActivityTracks;
		if (nearbyActivityTracks.size() == 0) {
			String handleTime = handleDateTime.getStringTime(SleepTightConstants.AMPM_TimeFormat);
			Toast.makeText(this, "There is no event close to " + handleTime + ".", Toast.LENGTH_LONG).show();
			finish();
		} else {
			addActivityTracks();
		}
	}
	
	
	private void addActivityTracks() {
		_editTrackAdapter.putItems(_timeLineController._activityTrackUnits);
	}
	
	/*
	 * Listener
	 */
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long $trackId) {
			_delActivityTrackUnit = (ActivityTrackUnit) _editTrackAdapter.getItem(pos);
			_deletedId = (int) $trackId;
			PopupUtil.showMessageDialog(DeleteTracksActivity.this, "Delete Event", "Do you want to delete this (" + _delActivityTrackUnit.activityName + ") event?", deleteClickListener, "Delete", null, "Cancel");
		}
		
		private android.content.DialogInterface.OnClickListener deleteClickListener = new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				_timeLineController.reqeustRemoveActivityTrack((int) _deletedId);
				Intent intent = new Intent();
				TimeLineCanvasView._firstLoad = true;
				intent.setAction("kr.mintech.main.refresh");
				sendBroadcast(intent);
				
				EventLogger.log("delete_activity", 
						"where", "app", 
						"type", _delActivityTrackUnit.recordType,
						"activityID", _delActivityTrackUnit.activityId,
						"deletedID", _deletedId);
				
				finish();
			}
		};
	};
	
//	private OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {
//		@Override
//		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long $trackId) {
//			Intent kIntent = new Intent(DeleteTracksActivity.this, AddDurationAcitivity.class);
//			kIntent.putExtra("track_id", (int) $trackId);
//			kIntent.putExtra("activity_id", (int) _timeLineController._activityTrackUnits.get(pos).activityId);
//			kIntent.putExtra("start_time", _timeLineController._activityTrackUnits.get(pos).actionStartedAt);
//			if (_timeLineController._activityTrackUnits.get(pos).actionEndedAt == null) {
//			} else {
//				kIntent.putExtra("end_time", _timeLineController._activityTrackUnits.get(pos).actionEndedAt);
//			}
//			startActivity(kIntent);
//			return false;
//		}
//	};
	
	private OnClickListener backClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndActivityTracks:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Activity successfully loaded");
					addActivityTracks();
					break;
				
				case NumberConst.requestEndRemoveAction:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Activity successfully removed");
					EventLogger.log("delete_activity_result",
							"result", "success");

					_timeLineController.updateWidget(DeleteTracksActivity.this);
					loadActivityTracks();
					break;
					
				case NumberConst.requestFail:
					Logg.w("AddActivityView | enclosing_method()", "Failed to remove the activity");
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
}