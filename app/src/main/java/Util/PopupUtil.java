package Util;

import java.lang.reflect.Method;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class PopupUtil
{
	private static Toast _toast;
	private static Dialog _dialog;
	private static ProgressDialog _progressDialog;
	
	
	/**
	 * show short toast message
	 * @param $context
	 * @param $msg
	 */
	public static void showToast(Context $context, CharSequence $msg)
	{
		if (_toast != null)
			_toast = null;
		
		_toast = Toast.makeText($context, $msg, Toast.LENGTH_SHORT);
		_toast.show();
	}
	
	
	/**
	 * show long toast message
	 * @param $context
	 * @param $msg
	 * @param $isLong
	 */
	public static void showLongToast(Context $context, CharSequence $msg)
	{
		if (_toast != null)
			_toast = null;
		
		_toast = Toast.makeText($context, $msg, Toast.LENGTH_LONG);
		_toast.show();
	}
	
	
	/**
	 * show dialog with error icon
	 * @param $title 
	 * @param $msg 
	 */
	public static void showErrorDialog(Context $context, CharSequence $title, CharSequence $msg)
	{
		try
		{
			if (_dialog != null && _dialog.isShowing())
				_dialog.dismiss();
			
			_dialog = new AlertDialog.Builder($context).setTitle($title).setMessage($msg).setPositiveButton(android.R.string.ok, null).show();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * show dialog with information icon
	 * @param $title 
	 * @param $msg 
	 */
	public static void showInformationDialog(Context $context, CharSequence $title, CharSequence $msg)
	{
		try
		{
			if (_dialog != null && _dialog.isShowing())
				_dialog.dismiss();
			
			_dialog = new AlertDialog.Builder($context).setTitle($title).setMessage($msg).setPositiveButton(android.R.string.ok, null).show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * auto-close pop-up
	 * @param $context
	 * @param $title 
	 * @param $msg 
	 * @param $closeListener 
	 * @param $btnTitle
	 * @param $duration 
	 */
	public static void showTimerDialog(Context $context, CharSequence $title, CharSequence $msg, DialogInterface.OnDismissListener $closeListener, CharSequence $btnTitle, int $duration)
	{
		try
		{
			if (_dialog != null && _dialog.isShowing())
				_dialog.dismiss();
			
			CharSequence btnTitle = $btnTitle;
			if (btnTitle == null)
				btnTitle = $context.getText(android.R.string.ok);
			
			_dialog = new AlertDialog.Builder($context).setTitle($title).setMessage($msg).setPositiveButton(btnTitle, null).show();
			_dialog.setOnDismissListener($closeListener);
			
			if ($duration > 0)
			{
				Runnable runnable = new Runnable()
				{
					@Override
					public void run()
					{
						_dialog.dismiss();
					}
				};
				Handler handler = new Handler();
				float kDuration = $duration * 1000;
				handler.postDelayed(runnable, (int) kDuration);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * click to close
	 * @param $context
	 * @param $title 제목
	 * @param $msg 내용
	 * @param $okListener
	 * @param $okTitle 
	 * @param $cancelListener 
	 * @param $cancelTitle 
	 */
	public static void showMessageDialog(Context $context, CharSequence $title, CharSequence $msg, DialogInterface.OnClickListener $okListener, CharSequence $okTitle,
			DialogInterface.OnClickListener $cancelListener, CharSequence $cancelTitle)
	{
		try
		{
			if (_dialog != null && _dialog.isShowing())
				_dialog.dismiss();
			
			CharSequence okTitle = $okTitle;
			if ($okTitle == null)
				okTitle = $context.getText(android.R.string.ok);
			
			CharSequence cancelTitle = $cancelTitle;
			if ($cancelTitle == null)
				cancelTitle = $context.getText(android.R.string.cancel);
			
			if ($cancelTitle != null)
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setMessage($msg).setPositiveButton(okTitle, $okListener).setNegativeButton(cancelTitle, $cancelListener).show();
				_dialog.setCancelable(false);
			}
			else
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setMessage($msg).setPositiveButton(okTitle, $okListener).show();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Returns whether the message dialog is open
	 * @return
	 */
	public static boolean isShowingDialog()
	{
		return (_dialog != null && _dialog.isShowing());
	}
	
	
	/**
	 * Message dialog with ok button
	 * @param $context
	 * @param $title 
	 * @param $msg 
	 * @param $okTitle 
	 * @param $okListener 
	 */
	public static void showMessageDialog(Context $context, CharSequence $title, CharSequence $msg, CharSequence $okTitle, DialogInterface.OnClickListener $okListener)
	{
		showMessageDialog($context, $title, $msg, $okListener, $okTitle, null, null);
	}
	
	
	/**
	 * Message dialog with edit text
	 * @param context
	 * @param title 
	 * @param l 
	 */
	public static void showEditTextDialog(Context $context, CharSequence $title, EditText $input, DialogInterface.OnClickListener $listener)
	{
		try
		{
			if (_dialog != null && _dialog.isShowing())
				_dialog.dismiss();
			
			_dialog = new AlertDialog.Builder($context).setTitle($title).setView($input).setPositiveButton(android.R.string.ok, $listener).setNegativeButton(android.R.string.cancel, null)
					.setCancelable(false).show();
			
			// 키보드 바로 보여주기
			$input.setOnFocusChangeListener(new View.OnFocusChangeListener()
			{
				@Override
				public void onFocusChange(View v, boolean hasFocus)
				{
					if (hasFocus)
						_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				}
			});
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param $context
	 * @param $view 표시할 view
	 * @param $okListener 
	 */
	public static void showSetViewDialog(Context $context, String $title, View $view, CharSequence $okTitle, DialogInterface.OnClickListener $okListener, CharSequence $cancelTitle,
			DialogInterface.OnClickListener $cancelListnenr)
	{
		try
		{
			closeDialog();
			
			if ($cancelTitle == null)
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setView($view).setPositiveButton($okTitle, $okListener).setCancelable(false).show();
			}
			else
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setView($view).setPositiveButton($okTitle, $okListener).setNegativeButton($cancelTitle, $cancelListnenr)
						.setCancelable(false).show();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Keyboard set to visible 
	 * @param $context
	 * @param $view 
	 * @param $okListener 
	 */
	public static void showSetViewDialogWithAutoKeyboardShow(Context $context, String $title, View $view, CharSequence $okTitle, DialogInterface.OnClickListener $okListener, CharSequence $cancelTitle,
			DialogInterface.OnClickListener $cancelListnenr)
	{
		try
		{
			closeDialog();
			
			if ($cancelTitle == null)
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setView($view).setPositiveButton($okTitle, $okListener).setCancelable(false).show();
			}
			else
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setView($view).setPositiveButton($okTitle, $okListener).setNegativeButton($cancelTitle, $cancelListnenr)
						.setCancelable(false).show();
			}
			_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param $context
	 * @param $view 
	 * @param $okListener 
	 */
	public static void showSetViewWithOutButtonDialog(Context $context, String $title, View $view, CharSequence $okTitle, DialogInterface.OnClickListener $okListener, CharSequence $cancelTitle,
			DialogInterface.OnClickListener $cancelListnenr)
	{
		try
		{
			closeDialog();
			
			if ($cancelTitle == null)
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setView($view).setCancelable(true).show();
			}
			else
			{
				_dialog = new AlertDialog.Builder($context).setTitle($title).setView($view).setNegativeButton($cancelTitle, $cancelListnenr).setCancelable(true).show();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void closeDialog()
	{
		if (_dialog != null)
			_dialog.dismiss();
	}
	
	
	public static void showProgressDialog(Context $context, CharSequence $msg)
	{
		showProgressDialog($context, $msg, -1);
	}
	
	public static void showProgressDialog(Context $context, CharSequence $msg, int $progressImgResId)
	{
		showProgressDialog($context, $msg, -1, $progressImgResId);
	}

	public static void showProgressDialog(Context $context, CharSequence $msg, float $duration)
	{
		showProgressDialog($context, $msg, $duration, null, null);
	}
	
	public static void showProgressDialog(Context $context, CharSequence $msg, float $duration, int $progressImgResId)
	{
		showProgressDialog($context, $msg, $duration, null, null, $progressImgResId);
	}
	
	public static void showProgressDialog(Context $context, CharSequence $msg, float $duration, final Object $target, final String $method)
	{
		showProgressDialog($context, $msg, $duration, $target, $method, -1);
	}
	
	public static void showProgressDialog(Context $context, CharSequence $msg, float $duration, final Object $target, final String $method, int $progressImgResId)
	{
		if (_progressDialog != null)
			closeProgressDialog();
		
		_progressDialog = ProgressDialog.show($context, "", $msg);
		_progressDialog.setCancelable(true);
		if ($progressImgResId != -1)
		{
			_progressDialog.setIndeterminateDrawable(UiUtil.getDrawableFromResId($context, $progressImgResId));
		}
		
		if ($duration > 0)
		{
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					invokeCallback($target, $method);
					closeProgressDialog();
				}
			};
			Handler handler = new Handler();
			float kDuration = $duration * 1000;
			handler.postDelayed(runnable, (int) kDuration);
		}
	}

	public static void showProgressDialog(Context $context, CharSequence $msg, float $duration, DialogInterface.OnDismissListener $closeListener)
	{
		showProgressDialog($context, $msg, $duration, $closeListener, -1);
	}
	
	public static void showProgressDialog(Context $context, CharSequence $msg, float $duration, DialogInterface.OnDismissListener $closeListener, int $progressImgResId)
	{
		if (_progressDialog != null)
			closeProgressDialog();
		
		_progressDialog = ProgressDialog.show($context, "", $msg);
		if ($progressImgResId != -1)
		{
			_progressDialog.setIndeterminateDrawable($context.getResources().getDrawable($progressImgResId));
		}
		
		if ($closeListener != null)
			_progressDialog.setOnDismissListener($closeListener);
		
		if ($duration > 0)
		{
			Runnable runnable = new Runnable()
			{
				@Override
				public void run()
				{
					closeProgressDialog();
				}
			};
			Handler handler = new Handler();
			float kDuration = $duration * 1000;
			handler.postDelayed(runnable, (int) kDuration);
		}
	}
	
	public static void closeProgressDialog()
	{
		if (_progressDialog != null)
		{
			_progressDialog.dismiss();
			_progressDialog = null;
		}
	}
	
	
	protected static void invokeCallback(Object $target, String $method)
	{
		if ($target == null || $method == null)
			return;
		
		try
		{
			Class<?> kClass = $target.getClass();
			Method kInvocation = kClass.getMethod($method);
			kInvocation.invoke($target);
		} catch (Exception $e)
		{
			// Nothing
		}
	}
	
}
