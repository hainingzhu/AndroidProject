package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;

public final class UiUtil
{
	/**
	 * 키보드 숨기기
	 * @param $view 아무 view
	 */
	public static void hideKeyboard(View $view)
	{
		InputMethodManager imm = (InputMethodManager) ContextUtil.CONTEXT.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow($view.getWindowToken(), 0);
	}
	
	
	/**
	 * 키보드 보이기
	 * @param $view 아무 view
	 */
	public static void showKeyboard(View $view)
	{
		InputMethodManager imm = (InputMethodManager) ContextUtil.CONTEXT.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput($view, InputMethodManager.SHOW_IMPLICIT);
	}
	
	
	/**
	 * r.drawable.xxx 를 가져온다.
	 * @param $context
	 * @param $fileName r.drawable.xxx 이면 xxx 만 입력
	 * @return
	 */
	public static Drawable getDrawableFromFileName(Context $context, String $fileName)
	{
		Drawable result = null;
		int i = $context.getResources().getIdentifier($fileName, "drawable", $context.getPackageName());
		if (i != 0)
		{
			result = $context.getResources().getDrawable(i);
		}
		return result;
	}
	
	
	/**
	 * 리소스id 로 drawable을 가져온다.
	 * @param $context
	 * @param $resId R.drawable.xxx
	 * @return
	 */
	public static Drawable getDrawableFromResId(Context $context, int $resId)
	{
		return $context.getResources().getDrawable($resId);
	}
	
	
	/**
	 * bitmap 에서 drawable을 구해온다
	 * @param $bitmap
	 * @return
	 */
	public static Drawable getDrawableFromBitmap(Bitmap $bitmap)
	{
		return (Drawable) new BitmapDrawable(ContextUtil.CONTEXT.getResources(), $bitmap);
	}
	
	// 기본 스케일
	private static final float DEFAULT_HDIP_DENSITY_SCALE = 1.5f;
	
	
	/**
	 * 픽셀단위를 현재 디스플레이 화면에 비례한 크기로 반환합니다.
	 * @param pixel 픽셀
	 * @return 변환된 값 (DP)
	 */
	public static int DPFromPixel(Context $context, int $pixel)
	{
		float scale = $context.getResources().getDisplayMetrics().density;
		return (int) ($pixel / DEFAULT_HDIP_DENSITY_SCALE * scale);
	}
	
	
	/**
	 * 현재 디스플레이 화면에 비례한 DP단위를 픽셀 크기로 반환합니다.
	 * @param DP 픽셀
	 * @return 변환된 값 (pixel)
	 */
	public static int PixelFromDP(Context $context, int $dp)
	{
		float scale = $context.getResources().getDisplayMetrics().density;
		return (int) ($dp / scale * DEFAULT_HDIP_DENSITY_SCALE);
	}
	
	
	/**
	 * 몇 초 후에 뷰 숨기기
	 * @param $view 숨길 뷰
	 * @param $duration x 초 후에 숨긴다
	 */
	public static void hideViewWithDuration(final View $view, int $duration)
	{
		$view.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				$view.setVisibility(View.GONE);
			}
		}, $duration * 1000);
	}
	
	
	/**
	 * BitmapDrawable을 파일로 저장한 뒤에 Uri를 가져온다
	 * @param $bitmapDrawable 저장할 BitmapDrawable
	 * @param $filePath 저장할
	 *            경로(Environment.getExternalStoragePublicDirectory(Environment
	 *            .DIRECTORY_DOWNLOADS)+"/xxx.png")
	 * @return Uri
	 */
	public static Uri getUriFromBitmapDrawable(BitmapDrawable $bitmapDrawable, String $filePath)
	{
		writeFileFromBitmap($bitmapDrawable.getBitmap(), $filePath);
		return getUriFromFile($filePath);
	}
	
	
	/**
	 * Bitmap을 파일로 저장하기
	 * @param $bitmap 저장할 Bitmap
	 * @param $filePath 저장할
	 *            경로(Environment.getExternalStoragePublicDirectory(Environment
	 *            .DIRECTORY_DOWNLOADS)+"/xxx.png")
	 */
	public static void writeFileFromBitmap(Bitmap $bitmap, String $filePath)
	{
		FileOutputStream out = null;
		try
		{
			File file = new File($filePath);
			out = new FileOutputStream(file);
			$bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (out != null)
					out.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * file 에서 uri 가져오기
	 * @param $filePath 파일 경로
	 *            Environment.getExternalStoragePublicDirectory(Environment
	 *            .DIRECTORY_DOWNLOADS)+"/susemi99.png"
	 * @return
	 */
	public static Uri getUriFromFile(String $filePath)
	{
		return Uri.fromFile(new File($filePath));
	}
	
	
	/**
	 * File의 MimeType을 알아내기
	 * @param $filePath 파일 경로
	 *            Environment.getExternalStoragePublicDirectory(Environment
	 *            .DIRECTORY_DOWNLOADS)+"/susemi99.png"
	 * @return image/png, video/xxx
	 */
	public static String getMimeTypeFromFile(String $filePath)
	{
		MimeTypeMap type = MimeTypeMap.getSingleton();
		return type.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl($filePath));
	}
	
	
	/**
	 * 파일을 다른 경로에 복사한다
	 * @param $fromFilePath 원본 파일 전체
	 *            경로(/mnt/sdcard/LGSmartTV/18-2_20120210_131803.jpg)
	 * @param $toFilePath 복사할 파일 전체
	 *            경로(/data/data/com.lge.tv.remoteapps/files/18-
	 *            2_20120210_131803.jpg)
	 * @param $isDeleteFromFile 복사 후에 원본 파일을 지울것인가(true=삭제)
	 */
	public static void copyToAnotherPath(String $fromFilePath, String $toFilePath, boolean $isDeleteFromFile)
	{
		File fromFile = new File($fromFilePath);
		File toFile = new File($toFilePath);
		
		FileChannel src = null;
		FileChannel dst = null;
		
		try
		{
			src = new FileInputStream(fromFile).getChannel();
			dst = new FileOutputStream(toFile).getChannel();
			src.transferTo(0, src.size(), dst);
//			dst.transferFrom(src, 0, src.size());
			src.close();
			dst.close();
			
			if ($isDeleteFromFile)
				fromFile.delete();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				src.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			try
			{
				dst.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	public static Bitmap resizeBitmap(Bitmap $bitmap, double $width, double $height)
	{
		return Bitmap.createScaledBitmap($bitmap, (int) $width, (int) $height, true);
	}
	
	
	public static Bitmap resizeBitmapQuarter(Bitmap $bitmap)
	{
		return resizeBitmap($bitmap, $bitmap.getWidth() * 0.25, $bitmap.getHeight() * 0.25);
	}
	
	
	public static Bitmap fastblur(Bitmap sentBitmap, int radius)
	{
		
		// Stack Blur v1.0 from
		// http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
		//
		// Java Author: Mario Klingemann <mario at quasimondo.com>
		// http://incubator.quasimondo.com
		// created Feburary 29, 2004
		// Android port : Yahel Bouaziz <yahel at kayenko.com>
		// http://www.kayenko.com
		// ported april 5th, 2012
		
		// This is a compromise between Gaussian Blur and Box blur
		// It creates much better looking blurs than Box Blur, but is
		// 7x faster than my Gaussian Blur implementation.
		//
		// I called it Stack Blur because this describes best how this
		// filter works internally: it creates a kind of moving stack
		// of colors whilst scanning through the image. Thereby it
		// just has to add one new block of color to the right side
		// of the stack and remove the leftmost color. The remaining
		// colors on the topmost layer of the stack are either added on
		// or reduced by one, depending on if they are on the right or
		// on the left side of the stack.
		//
		// If you are using this algorithm in your code please add
		// the following line:
		//
		// Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
		
		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
		
		if (radius < 1)
		{
			return sentBitmap;
		}
		
//		int kOriginalWidth = bitmap.getWidth();
//		int kOriginalHeight = bitmap.getHeight();
//		bitmap = resizeBitmapQuarter(bitmap);
		
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		
		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);
		
		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;
		
		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];
		
		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++)
		{
			dv[i] = (i / divsum);
		}
		
		yw = yi = 0;
		
		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;
		
		for (y = 0; y < h; y++)
		{
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++)
			{
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				rbs = r1 - Math.abs(i);
				rsum += sir[0] * rbs;
				gsum += sir[1] * rbs;
				bsum += sir[2] * rbs;
				if (i > 0)
				{
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				}
				else
				{
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
			}
			stackpointer = radius;
			
			for (x = 0; x < w; x++)
			{
				
				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];
				
				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;
				
				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];
				
				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];
				
				if (y == 0)
				{
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];
				
				sir[0] = (p & 0xff0000) >> 16;
				sir[1] = (p & 0x00ff00) >> 8;
				sir[2] = (p & 0x0000ff);
				
				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];
				
				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;
				
				stackpointer = (stackpointer + 1) % div;
				sir = stack[(stackpointer) % div];
				
				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];
				
				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];
				
				yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++)
		{
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++)
			{
				yi = Math.max(0, yp) + x;
				
				sir = stack[i + radius];
				
				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];
				
				rbs = r1 - Math.abs(i);
				
				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;
				
				if (i > 0)
				{
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				}
				else
				{
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}
				
				if (i < hm)
				{
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++)
			{
				pix[yi] = 0xff000000 | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];
				
				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;
				
				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];
				
				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];
				
				if (x == 0)
				{
					vmin[y] = Math.min(y + r1, hm) * w;
				}
				p = x + vmin[y];
				
				sir[0] = r[p];
				sir[1] = g[p];
				sir[2] = b[p];
				
				rinsum += sir[0];
				ginsum += sir[1];
				binsum += sir[2];
				
				rsum += rinsum;
				gsum += ginsum;
				bsum += binsum;
				
				stackpointer = (stackpointer + 1) % div;
				sir = stack[stackpointer];
				
				routsum += sir[0];
				goutsum += sir[1];
				boutsum += sir[2];
				
				rinsum -= sir[0];
				ginsum -= sir[1];
				binsum -= sir[2];
				
				yi += w;
			}
		}
		
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		
//		bitmap = resizeBitmap(bitmap, kOriginalWidth, kOriginalHeight);
		
		return (bitmap);
	}
}
