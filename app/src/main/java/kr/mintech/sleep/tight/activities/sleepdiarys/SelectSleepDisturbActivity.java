package kr.mintech.sleep.tight.activities.sleepdiarys;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.settings.AddBeforeBedActActivity;
import kr.mintech.sleep.tight.activities.settings.AddSleepDisturbActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.controllers.settings.EditSleepDisturbController;
import kr.mintech.sleep.tight.controllers.sleepdiarys.SelectSleepDisturbAdatper;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.utils.Pie;
import Util.Logg;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class SelectSleepDisturbActivity extends Activity
{
	private EditSleepDisturbController _controller;
	private SelectSleepDisturbAdatper _adapter;
	
	private ListView _list;
	private Button _okBtn;
	private Button _addBtn;
	private ProgressBar _progressBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_select_before_bed_act);
		
		_list = (ListView) findViewById(R.id.list_item);
		_okBtn = (Button) findViewById(R.id.btn_okay);
		_progressBar = (ProgressBar) findViewById(R.id.progressbar);
		_addBtn = (Button) findViewById(R.id.btn_add);
		
		_addBtn.setOnClickListener(addClickListener);
		_okBtn.setOnClickListener(okClickListener);
		_controller = new EditSleepDisturbController(onRequestEndListener);
		_adapter = new SelectSleepDisturbAdatper(this);
		
		_list.setAdapter(_adapter);		
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		loadSleepDisturb();
	}
	
	
	private void loadSleepDisturb()
	{
		_progressBar.setVisibility(View.VISIBLE);
		_controller.requestSleepDisturb();
	}
	
	
	private void addActs()
	{
		_adapter.putItems(_controller._units);
	}
	
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
				case NumberConst.requestEndSleepDisturb:
					_progressBar.setVisibility(View.GONE);
					addActs();
					break;
					
				case NumberConst.requestFail:
					_progressBar.setVisibility(View.GONE);
					Logg.w("AddActivityView | enclosing_method()", "Network Fail");
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
	
	private OnClickListener addClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent kIntent = new Intent(SelectSleepDisturbActivity.this, AddSleepDisturbActivity.class);
			startActivity(kIntent);
		}
	};
	
	private OnClickListener okClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Pie.getInst().sleepDisturbArr = (ArrayList<String>) _adapter.checkedList.clone();
			Pie.getInst().sleepDisturbIdArr = (ArrayList<String>) _adapter.checkedIdList.clone();
			setResult(RESULT_OK);
			finish();
		}
	};

	public void onCancelClick(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}
}
