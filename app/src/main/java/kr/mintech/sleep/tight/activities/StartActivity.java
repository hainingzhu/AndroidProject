package kr.mintech.sleep.tight.activities;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.Local_db.Users;
import kr.mintech.sleep.tight.activities.Local_db.backgroundTask;
import kr.mintech.sleep.tight.activities.Local_db.dbHelper_local;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends Activity {
	public static int curUID = -1;
	private boolean _isKillAppFlag = false;
	private RegisterController _ctrl;

	private TextView _descriptionText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.enableDefaults();
		StrictMode.allowThreadDiskReads();
		StrictMode.allowThreadDiskWrites();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_start);

		Log.w("shakej", "Start!");

		ContextUtil.CONTEXT = this;
		_descriptionText = (TextView) findViewById(R.id.text_start_text);
		_descriptionText.setText("Connecting...");

		if (PreferenceUtil.getFirstRun()) {
			Log.w("WHJ", "First run, i am a xxx");
			PreferenceUtil.setFirstRun();
			PreferenceUtil.setDiaryReminder("false");
			PreferenceUtil.setDiaryReminderTime("3:36 AM");
			PreferenceUtil.setDiaryReminderDate("Feb 04 2016 05:12 AM");
			PreferenceUtil.setUserActivity("Caffeine:Meal:Medication:Alcohol:Exercise:Tobacco");
			String kUuid = SystemUtil.getAndroidId(this);
			// CLEANUP // Logg.w("MainActivity | setFirstRun()", "Initiation :" + kUuid);
			PreferenceUtil.setAndroidId(kUuid);
		}
		//requestIsRegister();
		//goUserInfoRegister();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		_isKillAppFlag = intent
				.getBooleanExtra(StringConst.KEY_KILL_APP, false);
	}

	@Override
	protected void onResume() {
		Log.w("WHJ", "xxxx you!");
		super.onResume();
		if (_isKillAppFlag) {
			finish();
			return;
		} else {
			if (SystemUtil.isConnectNetwork()) {
				Log.w("WHJ", "The server is available.");
				requestIsRegister();
			} else {
				Toast.makeText(this, "Inappropriate Network Connection. Offline mode", Toast.LENGTH_SHORT)
						.show();
                if (userInLocalDB())
                    allRequestEnded();
                else
                    _descriptionText.setText("Internet is not available. And there is no registered user.");
			}
		}
        return;
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


	public boolean userInLocalDB() {
        Log.w("WHJ", "Test whether the user information is available locally");
		_descriptionText.setText("Find User locally");
		int uid = dbHelper_local.curUserID(this);
        if (uid != -100) {
            return true;
        } else
            return false;
	}

	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
			case NumberConst.requestEndIsRegister:
				Log.w("StartActivity", "Successfully loaded the registering info. New User?"
						+ _ctrl._isNewUser + " / user.id = " + _ctrl.unit.id);
				curUID = _ctrl.unit.id;
				if (_ctrl.unit.name == null) {
					goUserInfoRegister();
				} else {
					allRequestEnded();
                    // if user is not available locally,
                    // but he's registered in the server
                    // create a dummy user in local DB
                    if (!userInLocalDB()) {
                        Users u = new Users(_ctrl.unit.id, "", 2016, "", "");
                        dbHelper_local.insertUser(u, StartActivity.this);
                    }
				}

                // add user info to local database
                /*
				Intent msi = new Intent(StartActivity.this, backgroundTask.class);
                msi.setData(Uri.parse(String.format("/addusers?id=%d&nickname=%s&gender=%s", _ctrl.unit.id, _ctrl.unit.name, _ctrl.unit.gender)));
                startService(msi);
                */
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
