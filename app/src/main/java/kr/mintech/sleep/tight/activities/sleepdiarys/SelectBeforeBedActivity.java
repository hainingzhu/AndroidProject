package kr.mintech.sleep.tight.activities.sleepdiarys;

import java.util.ArrayList;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.settings.AddBeforeBedActActivity;
import kr.mintech.sleep.tight.activities.settings.BeforeBedActEditActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.controllers.settings.BeforeBedActController;
import kr.mintech.sleep.tight.controllers.settings.EditSleepDisturbController;
import kr.mintech.sleep.tight.controllers.sleepdiarys.SelectBeforeBedActAdatper;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.units.BeforeBedActUnit;
import kr.mintech.sleep.tight.utils.Pie;
import Util.Logg;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SelectBeforeBedActivity extends Activity
{
	private BeforeBedActController _controller;
	private SelectBeforeBedActAdatper _adapter;
	
	private ListView _list;
	private Button _okBtn;
	private ProgressBar _progressBar;
	
	private Button _addBtn;
	
	
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
		_controller = new BeforeBedActController(onRequestEndListener);
		_adapter = new SelectBeforeBedActAdatper(this);
		
		_list.setAdapter(_adapter);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		loadBeforeBedAct();
	}
	
	
	private void loadBeforeBedAct()
	{
		_progressBar.setVisibility(View.VISIBLE);
		_controller.requestBeforeBedAct();
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
				case NumberConst.requestEndBeforeBedAct:
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
			Intent kIntent = new Intent(SelectBeforeBedActivity.this, AddBeforeBedActActivity.class);
			startActivity(kIntent);
		}
	};
	
	private OnClickListener okClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Pie.getInst().beforeBedActArr = (ArrayList<String>) _adapter.checkedList.clone();
			Pie.getInst().beforeBedActIdArr = (ArrayList<String>) _adapter.checkedIDList.clone();
			setResult(RESULT_OK);
			finish();
		}
	};

	public void onCancelClick(View v) {
		setResult(RESULT_CANCELED);
		finish();
	}
}
