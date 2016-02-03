package Activities;

import Util.ContextUtil;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

public class BaseFragmentActivity extends FragmentActivity
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
		Intent intent = new Intent();
		intent.setClass(this, $class);
		startActivity(intent);
	}
}
