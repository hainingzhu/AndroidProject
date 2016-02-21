package kr.mintech.sleep.tight.activities;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.consts.StringConst;
import kr.mintech.sleep.tight.controllers.RegisterController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.utils.PreferenceUtil;
import Util.ContextUtil;
import Util.Logg;
import Util.SystemUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {
	private boolean _isKillAppFlag = false;
	private RegisterController _ctrl;

	private TextView _descriptionText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.enableDefaults();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_start);

		Log.w("shakej", "Start!");
		ContextUtil.CONTEXT = this;
		_descriptionText = (TextView) findViewById(R.id.text_start_text);
		_descriptionText.setText("Connecting...");

		if (PreferenceUtil.getFirstRun()) {
			Log.w("first run", "i am a virgin");
			PreferenceUtil.setFirstRun();
			PreferenceUtil.setDiaryReminder("false");
			PreferenceUtil.setDiaryReminderTime("3:36 AM");
			PreferenceUtil.setDiaryReminderDate("Jun 04 2013 05:12 AM");
			PreferenceUtil.setUserActivity("Caffeine:Meal:Medication:Alcohol:Exercise:Tobacco");
			String kUuid = SystemUtil.getAndroidId(this);
			// CLEANUP // Logg.w("MainActivity | setFirstRun()", "Initiation :" + kUuid);
			PreferenceUtil.setAndroidId(kUuid);
		}
		//requestIsRegister();
		// goUserInfoRegister();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		_isKillAppFlag = intent
				.getBooleanExtra(StringConst.KEY_KILL_APP, false);
	}

	@Override
	protected void onResume() {
		Log.w("onResume", "fuck you!");
		super.onResume();
		if (_isKillAppFlag) {
			finish();
			return;
		} else {
			if (SystemUtil.isConnectNetwork()) {
				Log.w("onResume", "The server is not available. I fuck your mama");
				requestIsRegister();
			} else {
				Toast.makeText(this, "Inappropriate Network Connection", Toast.LENGTH_SHORT)
						.show();
				return;
			}
		}
	}

	private void goUserInfoRegister() {
		if (_ctrl.unit.id != -1) {
			Intent kIntent = new Intent(this, UserInfoRegister.class);
			kIntent.putExtra("user_id", _ctrl.unit.id);
			startActivity(kIntent);
			finish();
		} else {
			Toast.makeText(this, "Didn't receive the User ID. Please try again.",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	// Is this phone registered?
	public void requestIsRegister() {
		Log.w("StartActivity", "RequestIsRegister");
		_descriptionText.setText("Find User...");
		_ctrl = new RegisterController(onRequestEndListener);
		_ctrl.requestIsRegister();
	}

	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
			case NumberConst.requestEndIsRegister:
				Log.w("StartActivity", "Successfully loaded the registering info. New User?"
						+ _ctrl._isNewUser + " / user.id = " + _ctrl.unit.id);
				if (_ctrl.unit.name == null) {
					goUserInfoRegister();
				} else {
					allRequestEnded();
				}
				break;
				
			case NumberConst.requestFail:
				Log.w("StartActivity", "Failed to load the registering info. ");
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

	// After finishing the server request 
	private void allRequestEnded() {
		Intent kIntent = new Intent(this, MainActivity.class);
		startActivity(kIntent);
		finish();
	}
}
