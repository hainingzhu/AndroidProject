package kr.mintech.sleep.tight.activities.popups;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.settings.BeforeBedActEditActivity;
import kr.mintech.sleep.tight.consts.NumberConst;
import kr.mintech.sleep.tight.controllers.actions.ActionController;
import kr.mintech.sleep.tight.controllers.actions.DeleteActionAdapter;
import kr.mintech.sleep.tight.listeners.OnRequestEndListener;
import kr.mintech.sleep.tight.utils.DateTime;
import kr.mintech.sleep.tight.utils.EventLogger;
import Util.Logg;
import Util.PopupUtil;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DeleteActionActivity extends Activity
{
	private ListView _listView;
	private Button _btnOkay;
	
	private ActionController _actionController;
	private DeleteActionAdapter _adapter;
	
	public static DateTime _loadBaseTime;
	private int _deleteId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_delete_action);
		
		_listView = (ListView) findViewById(R.id.list_item);
		_btnOkay = (Button) findViewById(R.id.btn_okay);
		_btnOkay.setOnClickListener(okayClickListener);
		
		_actionController = new ActionController(onRequestEndListener);
		_adapter = new DeleteActionAdapter(this);
		
		_listView.setOnItemClickListener(itemClickListener);
		_listView.setAdapter(_adapter);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		loadAction();
	}
	
	
	private void loadAction()
	{
		_actionController.clear();
		_actionController.requestActions();
	}
	
	
	private void addActions()
	{
		_adapter.putItems(_actionController._actionUnits);
	}
	
	/*
	 * Listener
	 */
	private OnItemClickListener itemClickListener = new OnItemClickListener()
	{
		public void onItemClick(AdapterView<?> adapterView, View clickedView, int pos, long $id)
		{
			if (_actionController._actionUnits.get(pos).defaultType)
			{
				Toast.makeText(DeleteActionActivity.this, "Cannot delete the default activities.", Toast.LENGTH_SHORT).show();
				return;
			}
			_deleteId = (int) $id;
			PopupUtil.showMessageDialog(DeleteActionActivity.this, "Delete", "Do you want to delete " + _actionController._actionUnits.get(pos).name + "?", deleteClickListener, "Delete", null, "Cancel");
			
		}
	};
	
	private android.content.DialogInterface.OnClickListener deleteClickListener = new android.content.DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			_actionController.requestRemoveActions(_deleteId);
		}
	};
	
	private OnClickListener okayClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			finish();
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
				case NumberConst.requestEndActions:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Activity sucessfully loaded");
					addActions();
					break;
					
				case NumberConst.requestEndRemoveAction:
//					EventLogger.log("delete_activity_result",
//							"result", "success");
					
					loadAction();
					break;
					
				case NumberConst.requestFail:
					// CLEANUP // Logg.w("AddActivityView | enclosing_method()", "Failed to remove the activity");
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
