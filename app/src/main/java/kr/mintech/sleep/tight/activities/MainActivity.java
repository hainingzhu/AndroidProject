package kr.mintech.sleep.tight.activities;

import kr.mintech.sleep.tight.R;
import kr.mintech.sleep.tight.activities.settings.SettingActivity;
import kr.mintech.sleep.tight.controllers.MainTabAdapter;
import kr.mintech.sleep.tight.services.SleepTightService;
import kr.mintech.sleep.tight.utils.EventLogger;
import kr.mintech.sleep.tight.utils.Pie;
import kr.mintech.sleep.tight.utils.PreferenceUtil;
import kr.mintech.sleep.tight.utils.Util;
import Util.ContextUtil;
import Util.Logg;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
	private MainTabAdapter _adapter;
	
	/**
	 * True at the beginning of onResume if we were resumed using an Intent
	 */
	private boolean resumedUsingIntent = false;
	private boolean resumedFromLock = false;
	private int startPage = 0;
	
	//Top Level Navigation
	ViewPager mViewPager;
	ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//make sure the event logger is writing logs to disk
		EventLogger.startWritingToDisk();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		startService(new Intent(this, SleepTightService.class));
		
		ContextUtil.CONTEXT = this;
		if (PreferenceUtil.getFirstRun())
		{
			setFirstRun();
		}
		
		_adapter = new MainTabAdapter(getSupportFragmentManager());
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter(_adapter);
		mViewPager.setOffscreenPageLimit(0);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				actionBar.setSelectedNavigationItem(position);
			}
			
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
			}
		});
		
		for (int i = 0; i < _adapter.getCount(); i++)
		{
			actionBar.addTab(actionBar.newTab().setText(_adapter.getPageTitle(i)).setTabListener(this));
		}		
		
		// CLEANUP // Logg.w("MainActivity | onCreate()", "");
	}
	
	
	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
	}
	
	
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		int newTabPosition = tab.getPosition();
		mViewPager.setCurrentItem(newTabPosition);
		
		Util.logPageSwitch(newTabPosition);
	}
	
	
	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		
	}
	
	
	private void setFirstRun()
	{
		
	}
	
	
	@Override
	public void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
		
		// CLEANUP // Logg.w("MainActivity | onNewIntent()", "OnNewIntent : " + intent);
		
		_adapter = new MainTabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(_adapter);

		startPage = intent.getIntExtra("startPage", 0);
		
		resumedFromLock = true;
		resumedUsingIntent = true;
		if (intent.getBooleanExtra("goToCurrentTime", false)) {
			//FIXME: this is kind of a terrible hack
			//if goToCurrentTime is true, pretend we were not resumed with an Intent.
			resumedUsingIntent = false;
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add("Settings");
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Intent kIntent = new Intent(MainActivity.this, SettingActivity.class);
		startActivity(kIntent);
		
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onBackPressed()
	{
		finish();
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();

		if (!resumedUsingIntent) {
			// CLEANUP // Logg.w("MainActivity | onResume()", "GOING TO CURRENT TIME");
			
			Pie.getInst().isRefreshing = false;
			Pie.getInst().goToCurrentTimeAfterLoad = true;
		}

		if (resumedFromLock) {
			actionBar.setSelectedNavigationItem(2);
			actionBar.setSelectedNavigationItem(startPage);
		}	

		EventLogger.log("resume", 
				"what", "main_activity",
				"page", Pie.getInst().currentPage);
		
		resumedFromLock = false;
		resumedUsingIntent = false;
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		EventLogger.log("pause", 
				"what", "main_activity",
				"page", Pie.getInst().currentPage);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
}
