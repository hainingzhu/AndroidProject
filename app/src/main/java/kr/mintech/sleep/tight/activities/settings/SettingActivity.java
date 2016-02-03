package kr.mintech.sleep.tight.activities.settings;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.SleepTightConstants;
import kr.mintech.sleep.tight.consts.StringConst;
import kr.mintech.sleep.tight.controllers.actions.ActionController;
import kr.mintech.sleep.tight.controllers.settings.ActivityEditListAdapter;
import kr.mintech.sleep.tight.fragments.TimePickerFragment;
import kr.mintech.sleep.tight.fragments.TimePickerFragment.TimePickerListener;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.services.SleepTightService;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.DragAndDropListView;
import kr.mintech.sleep.tight.utils.PreferenceUtil;
import Util.Logg;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends Activity implements TimePickerListener

{
	private CheckBox _reminderCheckBox;
	private Button _btnReminderPickTime;

	private CheckBox _chTrackingCaffeine, _chTrackingAlcohol,
			_chTrackingTobacco, _chTrackingMedication;

	private Button _btnMainActivityEdit, _btnSleepDisturbances,
			_btnActivityBeforeBedTime;

	private Button _closeBtn;

	private Button timeButton = null;

	private ActionController _actionController;
	private String _caffeineId, _alcoholId, _idTobacco, _idMedication;
	private ArrayList<String> showArr, hideArr;

	private boolean _isOptionChanged = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_setting);

		_isOptionChanged = false;
		showArr = new ArrayList<String>();
		hideArr = new ArrayList<String>();

		_actionController = new ActionController(onRequestEndListener);

		_reminderCheckBox = (CheckBox) findViewById(R.id.chk_sleep_diary_reminder);
		_btnReminderPickTime = (Button) findViewById(R.id.btn_reminder_pick_time);

		_chTrackingCaffeine = (CheckBox) findViewById(R.id.chk_tracking_caffeine);
		_chTrackingAlcohol = (CheckBox) findViewById(R.id.chk_tracking_alcohol);
		_chTrackingTobacco = (CheckBox) findViewById(R.id.chk_tracking_tobacco);
		_chTrackingMedication = (CheckBox) findViewById(R.id.chk_tracking_medication);

		_btnMainActivityEdit = (Button) findViewById(R.id.btn_main_activity);
		_btnSleepDisturbances = (Button) findViewById(R.id.btn_sleep_disturbances);
		_btnActivityBeforeBedTime = (Button) findViewById(R.id.btn_before_activity_bed_time);

		_closeBtn = (Button) findViewById(R.id.btn_setting_save);

		reminderCheckBoxSetting();

		_reminderCheckBox.setOnCheckedChangeListener(reminderCheckListener);
		_btnReminderPickTime.setOnClickListener(reminderPickTimeClickListener);

		_chTrackingCaffeine.setOnCheckedChangeListener(trackOptionCheckListener);
		_chTrackingAlcohol
				.setOnCheckedChangeListener(trackOptionCheckListener);
		_chTrackingTobacco.setOnCheckedChangeListener(trackOptionCheckListener);
		_chTrackingMedication
				.setOnCheckedChangeListener(trackOptionCheckListener);

		_btnMainActivityEdit.setOnClickListener(editBtnClickListener);
		_btnSleepDisturbances.setOnClickListener(editBtnClickListener);
		_btnActivityBeforeBedTime.setOnClickListener(editBtnClickListener);

		_closeBtn.setOnClickListener(settingEndClickListener);
		_actionController.requestActionsShowAll();
	}

	/*
	 * UI
	 */
	private void reminderCheckBoxSetting() {
		String kReminderState = PreferenceUtil.getDiaryReminder();
		String kReminderTime = PreferenceUtil.getDiaryReminderTime();

		_btnReminderPickTime.setText(kReminderTime);

		if (kReminderState.equals("true")) {
			_reminderCheckBox.setChecked(true);
			_btnReminderPickTime.setEnabled(true);
		} else {
			_reminderCheckBox.setChecked(false);
			_btnReminderPickTime.setEnabled(false);
		}
	}

	public void openTimeDialog(View view) {
		TimePickerFragment newFragment = new TimePickerFragment();

		timeButton = (Button) view;
		String curTime = timeButton.getText().toString();
		newFragment.setTime(curTime);
		newFragment.show(getFragmentManager(), "timePicker");
	}

	/*
	 * reminder Check State Listener
	 */
	private OnCheckedChangeListener reminderCheckListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton $button, boolean $status) {
			Logg.w("SettingActivity | enclosing_method()", "Reminder status: "
					+ $status);
			if ($status) {
				PreferenceUtil.setDiaryReminder("true");
				_btnReminderPickTime.setEnabled(true);
			} else {
				PreferenceUtil.setDiaryReminder("false");
				_btnReminderPickTime.setEnabled(false);
			}
		}
	};

	private OnCheckedChangeListener trackOptionCheckListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
				case R.id.chk_tracking_caffeine:
					_isOptionChanged = true;
					if (isChecked) {
						showArr.add(_caffeineId);
						hideArr.remove(_caffeineId);
					} else {
						hideArr.add(_caffeineId);
						for (int i = 0; i < showArr.size(); i++) {
							if (showArr.get(i).equals(_caffeineId)) {
								showArr.remove(_caffeineId);
							}
						}
	
					}
					break;
					
				case R.id.chk_tracking_alcohol:
					_isOptionChanged = true;
					if (isChecked) {
						showArr.add(_alcoholId);
						hideArr.remove(_alcoholId);
					} else {
						hideArr.add(_alcoholId);
						for (int i = 0; i < showArr.size(); i++) {
							if (showArr.get(i).equals(_alcoholId)) {
								showArr.remove(_alcoholId);
							}
						}
					}
					break;
					
				case R.id.chk_tracking_medication:
					_isOptionChanged = true;
					if (isChecked) {
						showArr.add(_idMedication);
						hideArr.remove(_idMedication);
					} else {
						hideArr.add(_idMedication);
						for (int i = 0; i < showArr.size(); i++) {
							if (showArr.get(i).equals(_idMedication)) {
								showArr.remove(_idMedication);
							}
						}
					}
					break;
					
				case R.id.chk_tracking_tobacco:
					_isOptionChanged = true;
					if (isChecked) {
						showArr.add(_idTobacco);
						hideArr.remove(_idTobacco);
					} else {
						hideArr.add(_idTobacco);
						for (int i = 0; i < showArr.size(); i++) {
							if (showArr.get(i).equals(_idTobacco)) {
								showArr.remove(_idTobacco);
							}
						}
					}
					break;

				default:
					break;
			}
		}
	};

	// Time Pick Click
	private OnClickListener reminderPickTimeClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			openTimeDialog(v);
		}
	};

	/*
	 * Edit Distub, before Activity
	 */
	private OnClickListener editBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_main_activity:
					Intent kMainActEditIntent = new Intent(SettingActivity.this,
							EditActListActivity.class);
					startActivity(kMainActEditIntent);
					break;
					
				case R.id.btn_sleep_disturbances:
					Intent kSleepDisEditIntent = new Intent(SettingActivity.this,
							SleepDisturbEditActivity.class);
					startActivity(kSleepDisEditIntent);
					break;
					
				case R.id.btn_before_activity_bed_time:
					Intent kBeforeEidtIntent = new Intent(SettingActivity.this,
							BeforeBedActEditActivity.class);
					startActivity(kBeforeEidtIntent);
					break;
					
				default:
					break;
			}

		}
	};

	/*
	 * Setting Save & Cancel
	 */
	private OnClickListener settingEndClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (_isOptionChanged) {
				_actionController.requestManageActions(hideArr, showArr);
			} else {
				Intent kIntent = new Intent();
				kIntent.setAction("kr.mintech.main.refresh");
				sendBroadcast(kIntent);
				finish();
			}
		}
	};

	@Override
	public void onDialogTimeSet(DialogFragment dialog, DateTime time) {
		String strTime = time
				.getStringTime(SleepTightConstants.AMPM_TimeFormat);
		String strDate = time.getStringTime(SleepTightConstants.DateTimeFormat);
		Logg.w("SettingActivity | onDialogTimeSet()", "Reminder set time: "
				+ strDate);

		PreferenceUtil.setDiaryReminderTime(strTime);
		PreferenceUtil.setDiaryReminderDate(strDate);
		_btnReminderPickTime.setText(strTime);

		startService(new Intent(SettingActivity.this, SleepTightService.class));
		return;
	}

	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndActions:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Activity successfully loaded!");
					// Caffeine: 3, Alcohol: 6, Tabacco: 4, Medication: 5
					if (_actionController._actionUnits != null) {
						for (int i = 0; i < _actionController._actionUnits.size(); i++) {
							String kNameString = _actionController._actionUnits
									.get(i).name;
							if (kNameString.contains("Caff")
									|| kNameString.contains("Alco")
									|| kNameString.contains("Toba")
									|| kNameString.contains("Medi")) {
								if (kNameString.contains("Caffeine")) {
									_caffeineId = Integer
											.toString(_actionController._actionUnits
													.get(i).id);
									if (_actionController._actionUnits.get(i).isHide) {
										hideArr.add(_caffeineId);
										_chTrackingCaffeine.setChecked(false);
									} else {
										// showArr.add(_caffeinId);
										_chTrackingCaffeine.setChecked(true);
									}
	
								}
	
								if (kNameString.contains("Alco")) {
									_alcoholId = Integer
											.toString(_actionController._actionUnits
													.get(i).id);
									if (_actionController._actionUnits.get(i).isHide) {
										hideArr.add(_alcoholId);
										_chTrackingAlcohol.setChecked(false);
									} else {
										// showArr.add(_alcoholId);
										_chTrackingAlcohol.setChecked(true);
									}
	
								}
								if (kNameString.contains("Tobacco")) {
									_idTobacco = Integer
											.toString(_actionController._actionUnits
													.get(i).id);
									if (_actionController._actionUnits.get(i).isHide) {
										hideArr.add(_idTobacco);
										_chTrackingTobacco.setChecked(false);
									} else {
										// showArr.add(_idTobacco);
										_chTrackingTobacco.setChecked(true);
									}
								}
								if (kNameString.contains("Medi")) {
									_idMedication = Integer
											.toString(_actionController._actionUnits
													.get(i).id);
									if (_actionController._actionUnits.get(i).isHide) {
										hideArr.add(_idMedication);
										_chTrackingMedication.setChecked(false);
									} else {
										// showArr.add(_idMedication);
										_chTrackingMedication.setChecked(true);
									}
								}
							} else {
								// showArr.add(Integer.toString(_actionController._actionUnits.get(i).id));
							}
						}
						Log.w("SettingActivity | enclosing_method()",
								"SHOW&HIDE : " + showArr + "////" + hideArr);
					}
					_isOptionChanged = false;
					break;
					
				case NumberConst.requestSuccess:
					Intent intent = new Intent();
					intent.setAction("kr.mintech.main.refresh");
					sendBroadcast(intent);
					finish();
					break;
					
				case NumberConst.requestFail:
					Logg.w("AddActivityView | enclosing_method()", "Network Fail");
					Toast.makeText(SettingActivity.this, "Network status is bad",
							Toast.LENGTH_SHORT).show();
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
