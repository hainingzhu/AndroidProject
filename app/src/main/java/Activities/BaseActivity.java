package Activities;

import Util.ContextUtil;
import Util.SystemUtil;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

public class BaseActivity extends FragmentActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ContextUtil.CONTEXT = getApplicationContext();
		// For smoother gradient
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
	}
	
	protected void startActivity(Class<?> $class)
	{
		if (!SystemUtil.isConnected3G() && !SystemUtil.isConnectedWiFi()) {
		} else {
			Intent intent = new Intent();
			intent.setClass(this, $class);
			startActivity(intent);
		}
	}
	
	protected void startActivity(Class<?> $class, int $flag)
	{
		Intent intent = new Intent();
		intent.setClass(this, $class);
		intent.addFlags($flag);
		startActivity(intent);
	}
	
	protected void startActivityForResult(Class<?> $class)
	{
		Intent intent = new Intent();
		intent.setClass(this, $class);
		startActivityForResult(intent, 0);
	}
	
	protected void startActivityForResult(Class<?> $class, int $flag)
	{
		Intent intent = new Intent();
		intent.setClass(this, $class);
		intent.addFlags($flag);
		startActivityForResult(intent, 0);
	}	
}
