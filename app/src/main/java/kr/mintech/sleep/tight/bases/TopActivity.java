package kr.mintech.sleep.tight.bases;

import kr.mintech.sleep.tight.R;
import Util.ContextUtil;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class TopActivity extends Activity
{
	private ViewGroup _layoutBody;
	private ProgressBar _progressBar;
	private ImageView _logoImageView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_top);
		
		ContextUtil.CONTEXT = this;
		
		_logoImageView = (ImageView) findViewById(android.R.id.home);
		_logoImageView.setImageResource(R.drawable.ic_launcher);
		
		if (Build.VERSION.SDK_INT >= 14)
		{
			getActionBar().setHomeButtonEnabled(true);
		}
		
		_layoutBody = (ViewGroup) findViewById(R.id.layout_body);
		_progressBar = (ProgressBar) findViewById(R.id.progress_bar_top);
	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}
	
	
	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * Adding a view
	 * @param $resId
	 */
	protected void putBodyView(int $resId)
	{
		if (_layoutBody != null)
			_layoutBody.removeAllViews();
		getLayoutInflater().inflate($resId, _layoutBody);
	}
	
	
	/**
	 * Title
	 * @param $title
	 */
	protected void putTitle(String $title)
	{
		setTitle($title);
	}
	
	
	protected void putTitle(int $resId)
	{
		putTitle(getString($resId));
	}
	
	
	// ==============================
	// Progress Bar
	// ==============================
	/**
	 * Show the progress bar
	 */
	protected void startProgress()
	{
		_progressBar.setVisibility(View.VISIBLE);
	}
	
	
	/**
	 * Hide the progress bar
	 */
	protected void stopProgress()
	{
		_progressBar.setVisibility(View.GONE);
	}
	
}
