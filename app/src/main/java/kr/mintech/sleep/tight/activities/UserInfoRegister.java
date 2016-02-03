package kr.mintech.sleep.tight.activities;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.controllers.RegisterController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class UserInfoRegister extends Activity
{
	private EditText _editUserName, _editUserAge;
	private Spinner _spinnerGender, _spinnerSleepCondition;
	
	private LinearLayout _layoutSleepConditionEtc;
	private EditText _editConditionEtc;
	private LinearLayout _layoutUserGenderEtc;
	private EditText _editUserGenderEtc;
	
	private Button _btnSave;
	
	private RegisterController _controller;
	private View _bottomViewGender, _bottomViewSleepCondition;
	
	private ArrayList<String> _genderList;
	private ArrayList<String> _sleepConditionList;
	
	private int _targetUserId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_user_info_register);
		
		_targetUserId = getIntent().getIntExtra("user_id", -1);
		
		_controller = new RegisterController(onRequestEndListener);
		_editUserName = (EditText) findViewById(R.id.edittext_user_name);
		_editUserAge = (EditText) findViewById(R.id.edittext_user_age);
		
		_spinnerGender = (Spinner) findViewById(R.id.spinner_user_gender);
		_spinnerSleepCondition = (Spinner) findViewById(R.id.spinner_user_sleep_condition);
		
		_bottomViewGender = (View) findViewById(R.id.bottom_divider_view_gender);
		_bottomViewSleepCondition = (View) findViewById(R.id.bottom_divider_view_sleepcondition);
		
		_layoutSleepConditionEtc = (LinearLayout) findViewById(R.id.layout_sleep_condition_etc);
		_layoutSleepConditionEtc.setVisibility(View.GONE);
		_layoutUserGenderEtc = (LinearLayout) findViewById(R.id.layout_gender_etc);
		_layoutUserGenderEtc.setVisibility(View.GONE);
		
		_editConditionEtc = (EditText) findViewById(R.id.edittext_user_sleep_contion_etc);
		_editUserGenderEtc = (EditText) findViewById(R.id.edittext_user_gender_etc);
		dataSetting();
		
		_btnSave = (Button) findViewById(R.id.btn_user_info_save);
		_btnSave.setOnClickListener(saveOnClickListener);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void dataSetting()
	{
		_genderList = new ArrayList<String>();
		_genderList.add("Female");
		_genderList.add("Male");
		_genderList.add("Others");
		
		ArrayAdapter kGenderSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, _genderList);
		_spinnerGender.setOnItemSelectedListener(genderItemClickListener);
		_spinnerGender.setAdapter(kGenderSpinnerAdapter);
		
		_sleepConditionList = new ArrayList<String>();
		_sleepConditionList.add("No condition");
		_sleepConditionList.add("Insomnia");
		_sleepConditionList.add("Delayed sleep phase syndrome");
		_sleepConditionList.add("Sleep apnea");
		_sleepConditionList.add("Parasomnias (e.g., sleep walking, night terror)");
		_sleepConditionList.add("Narcolepsy (spontaneously falling asleep)");
		_sleepConditionList.add("Hypersomnia (excessive sleep)");
		_sleepConditionList.add("Others");
		
		ArrayAdapter kSCSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, _sleepConditionList);
		_spinnerSleepCondition.setOnItemSelectedListener(sleepConditionItemClickListener);
		_spinnerSleepCondition.setAdapter(kSCSpinnerAdapter);
	}
	
	/*
	 * Listener
	 */
	private OnClickListener saveOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View arg0)
		{
			if (_editUserName.getText().toString() == null || _editUserName.getText().toString().equals(""))
			{
				Toast.makeText(UserInfoRegister.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if (_editUserAge.getText().toString() == null || _editUserAge.getText().toString().equals(""))
			{
				Toast.makeText(UserInfoRegister.this, "Please enter your year of birth.", Toast.LENGTH_SHORT).show();
				return;
			}
			// Saving the user info
			String kUserName = _editUserName.getText().toString();
			String kUserBirthDay = _editUserAge.getText().toString();
			String kGender = null;
			if (_spinnerGender.getSelectedItemPosition() == 2)
			{
				kGender = _editUserGenderEtc.getText().toString();
			}
			else
			{
				kGender = _spinnerGender.getSelectedItem().toString();
			}
			String kSleepCondition = null;
			if (_spinnerSleepCondition.getSelectedItemPosition() == 7)
			{
				kSleepCondition = _editConditionEtc.getText().toString();
			}
			else
			{
				kSleepCondition = _spinnerSleepCondition.getSelectedItem().toString();
			}
			
			Log.w("UserRegister", kUserName + " " + kUserBirthDay + " " + kGender + " " + kSleepCondition);
			_controller.requestAddRegisterInfo(_targetUserId, kUserName, Integer.parseInt(kUserBirthDay), kGender, kSleepCondition);
		}
	};
	
	private OnItemSelectedListener genderItemClickListener = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
		{
			Log.w("shakej", "position " + position);
			if (position == 2)
			{
				_layoutUserGenderEtc.setVisibility(View.VISIBLE);
				_bottomViewGender.setVisibility(View.GONE);
			}
			else
			{
				_layoutUserGenderEtc.setVisibility(View.GONE);
				_bottomViewGender.setVisibility(View.VISIBLE);
			}
		}
		
		
		@Override
		public void onNothingSelected(AdapterView<?> parentView)
		{
		}
	};
	
	private OnItemSelectedListener sleepConditionItemClickListener = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
		{
			if (position == 7)
			{
				_layoutSleepConditionEtc.setVisibility(View.VISIBLE);
				_bottomViewSleepCondition.setVisibility(View.GONE);
			}
			else
			{
				_layoutSleepConditionEtc.setVisibility(View.GONE);
				_bottomViewSleepCondition.setVisibility(View.VISIBLE);
			}
		}
		
		
		@Override
		public void onNothingSelected(AdapterView<?> parentView)
		{
		}
	};
	
	/*
	 * Network
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener()
	{
		@Override
		public void onRequestEnded(int $tag, Object $object)
		{
			switch ($tag)
			{
				case NumberConst.requestEndAddRegister:
					Log.w("StartActivity", "Successfully registered the user info");
					Intent kIntent = new Intent(UserInfoRegister.this, MainActivity.class);
					startActivity(kIntent);
					finish();
					break;
				case NumberConst.requestFail:
					Log.w("StartActivity", "Failed to register the user info");
					Toast.makeText(UserInfoRegister.this, "Failed to register the user information. Please try again later.", Toast.LENGTH_SHORT).show();
					break;
			}
		}
		
		
		@Override
		public void onRequest(Object $unit)
		{
		}
		
		
		@Override
		public void onRequestError(int $tag, String $errorStr)
		{
		}
	};
	
}