package kr.mintech.sleep.tight.activities.settings;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.controllers.settings.BeforeBedActController;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import Util.Logg;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddBeforeBedActActivity extends Activity
{
	private EditText _editActivity;
	private Button _saveBtn, _cancelBtn;
	
	private BeforeBedActController _controller;
	private TextView _title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_add_activity);
		
		_controller = new BeforeBedActController(onRequestEndListener);
		
		_editActivity = (EditText) findViewById(R.id.edittext_add_activity);
		_saveBtn = (Button) findViewById(R.id.btn_add_activity_select);
		_cancelBtn = (Button) findViewById(R.id.btn_add_activity_cancel);
		
		_title = (TextView) findViewById(R.id.text_popup_title);
		_title.setText("Add before bed activity");
		
		_saveBtn.setOnClickListener(addActivityBtnClickListener);
		_cancelBtn.setOnClickListener(addActivityBtnClickListener);
	}
	
	private OnClickListener addActivityBtnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.btn_add_activity_cancel:
					finish();
					break;
				case R.id.btn_add_activity_select:
					if (_editActivity.getText().toString() == null || _editActivity.getText().toString().equals(""))
					{
						Toast.makeText(AddBeforeBedActActivity.this, "Name is null", Toast.LENGTH_SHORT).show();
						return;
					}
					_controller.requestAddBeforeBedAct(_editActivity.getText().toString());
					break;
				
				default:
					break;
			}
		}
	};
	
	/*
	 * Network Listener
	 */
	private OnRequestEndListener onRequestEndListener = new OnRequestEndListener()
	{
		@Override
		public void onRequestEnded(int $tag, Object $object)
		{
			switch ($tag)
			{
				case NumberConst.requestEndAddAction:
					Logg.w("AddActivityView | enclosing_method()", "Add Action: success!");
					finish();
					break;
				
				case NumberConst.requestFail:
					Logg.w("AddActivityView | enclosing_method()", "Add Action: fail!");
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