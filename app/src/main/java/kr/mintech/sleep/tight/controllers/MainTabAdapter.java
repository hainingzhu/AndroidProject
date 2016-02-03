package kr.mintech.sleep.tight.controllers;

import kr.mintech.sleep.tight.views.AddActivityView;
import kr.mintech.sleep.tight.views.ComparisonView;
import kr.mintech.sleep.tight.views.SummaryView;
import Util.ContextUtil;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;

public class MainTabAdapter extends FragmentPagerAdapter
{
	@SuppressWarnings("unused")
	private LayoutInflater mInflater;
	private static final String[] names = { "ADD ACTIVITY", "SLEEP SUMMARY", "COMPARISON" };
	
	@SuppressWarnings("unused")
	private Context _context;
	
	
	public MainTabAdapter(FragmentManager fm)
	{
		super(fm);
		_context = ContextUtil.CONTEXT;
	}
	
	
	@Override
	public Fragment getItem(int i)
	{
		switch (i)
		{
			case 0:
				Fragment kAddActivtiyView = new AddActivityView();
				return kAddActivtiyView;
				
			case 1:
				Fragment kSummaryView = new SummaryView();
				return kSummaryView;
				
			case 2:
//				Fragment kSleepSummaryView = new SleepSummaryView();
//				return kSleepSummaryView;
//				
//			case 3:
				Fragment kComparisionView = new ComparisonView();
				return kComparisionView;
		}
		return null;
	}
	
	
	@Override
	public int getCount()
	{
		return names.length;
	}
	
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		return names[position];
	}
	
	
	@Override
	public float getPageWidth(int position)
	{
		return 1.0f;
	}	
}
