package kr.mintech.sleep.tight.activities.popups;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.MainActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.controllers.actions.ActionController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import Util.Logg;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActionActivity extends Activity {
	private EditText _editActivity;
	private Button _saveBtn, _cancelBtn;
	
	private ActionController _controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_add_activity);
		
		_controller = new ActionController(onRequestEndListener);
		
		_editActivity = (EditText) findViewById(R.id.edittext_add_activity);
		_saveBtn = (Button) findViewById(R.id.btn_add_activity_select);
		_cancelBtn = (Button) findViewById(R.id.btn_add_activity_cancel);
		
		_saveBtn.setOnClickListener(addActivityBtnClickListener);
		_cancelBtn.setOnClickListener(addActivityBtnClickListener);
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(_editActivity, InputMethodManager.SHOW_IMPLICIT);
	}
	
	private OnClickListener addActivityBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn_add_activity_cancel:
					finish();
					break;
					
				case R.id.btn_add_activity_select:					
					if (_editActivity.getText().toString() == null || _editActivity.getText().toString().equals("")) {
						Toast.makeText(AddActionActivity.this, "Fill out the activity name", Toast.LENGTH_SHORT).show();
						return;
					}
					// CLEANUP // Logg.w("AddActionActivity | enclosing_method()", "requestAddActions!");
					_controller.requestAddActions(_editActivity.getText().toString());
					
					break;
				
				default:
					break;
			}
		}
	};
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener() {
		@Override
		public void onRequestEnded(int $tag, Object $object) {
			switch ($tag) {
				case NumberConst.requestEndAddAction:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Successfully added the action");
					finish();
					break;
				
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Failed to add the action");
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